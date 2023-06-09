package com.blog;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.blog.exception.BadRequestException;
import com.blog.modules.blog.entity.ArticleCategoriesEntity;
import com.blog.modules.blog.entity.CategoriesEntity;
import com.blog.modules.blog.entity.vo.CategoriesVo;
import com.blog.modules.blog.service.*;
import com.blog.modules.security.service.UserDetailsServiceImpl;
import com.blog.utils.ConvertUtil;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;
import java.util.stream.Collectors;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Slf4j
public class LoginCacheTest {

    @Resource(name = "userDetailsService")
    private UserDetailsServiceImpl userDetailsService;

    @Resource
    private CategoriesService categoriesService;
    @Resource
    private ArticleCategoriesService articleCategoriesService;


    @Test
    public void testCache() {
        long start1 = System.currentTimeMillis();
        int size = 10000;
        for (int i = 0; i < size; i++) {
            userDetailsService.loadUserByUsername("admin");
        }
        long end1 = System.currentTimeMillis();
        //关闭缓存
        userDetailsService.setEnableCache(false);
        long start2 = System.currentTimeMillis();
        for (int i = 0; i < size; i++) {
            userDetailsService.loadUserByUsername("admin");
        }
        long end2 = System.currentTimeMillis();
        System.out.print("使用缓存：" + (end1 - start1) + "毫秒\n 不使用缓存：" + (end2 - start2) + "毫秒");
    }


    @Test
    public void selectCategories() {
        // 获取标签
        List<CategoriesEntity> categoriesEntityList = categoriesService.list();
        if (CollectionUtils.isEmpty(categoriesEntityList)) {
            throw new BadRequestException("分类列表为空");
        }
        // 转 VO
        List<CategoriesVo> categoriesVoList = ConvertUtil.convertList(categoriesEntityList, CategoriesVo.class);
        // 获取标签id列表
        Set<Long> idCollect = categoriesVoList.stream().map(CategoriesVo::getId).collect(Collectors.toSet());
        // 获取与标签相绑定的文章
        List<ArticleCategoriesEntity> articleCategoriesByCategoriesIdList = articleCategoriesService.getArticleCategoriesByCategoriesIdList(idCollect);
        if (CollectionUtils.isNotEmpty(articleCategoriesByCategoriesIdList)) {

            HashMap<Long, Integer> longIntegerHashMap = new HashMap<>(12);
            // 求出标签所绑定的 文章出现的次数
            articleCategoriesByCategoriesIdList.forEach(articleCategoriesEntity -> {
                Long cid = articleCategoriesEntity.getCid();
                int temp = 1;
                if (longIntegerHashMap.containsKey(cid)) {
                    temp = longIntegerHashMap.get(cid) + 1;
                }
                longIntegerHashMap.put(cid, temp);
            });
            // 将文章出现的次数存入对应的标签VO 中
            categoriesVoList.forEach(categoriesVo -> {
                Long categoriesVoId = categoriesVo.getId();
                if (longIntegerHashMap.containsKey(categoriesVoId)) {
                    categoriesVo.setArticlesNumber(longIntegerHashMap.get(categoriesVoId));
                }
            });
        }

        List<CategoriesVo> categoriesVoListTree = getCategoriesVoListTree(categoriesVoList);
        log.info(categoriesVoListTree.toString());
    }


    public List<CategoriesVo> getCategoriesVoListTree(List<CategoriesVo> categories) {
        if (CollectionUtils.isEmpty(categories)) {
            return new ArrayList<>();
        }
        List<CategoriesVo> resultList = new LinkedList<>();
        Map<Long, List<CategoriesVo>> parentMap = new HashMap<>();
        categories.forEach(category -> {
            category.setChildren(new ArrayList<>());
            parentMap.computeIfAbsent(category.getPid(), key -> new ArrayList<>()).add(category);
        });
        categories.forEach(root -> {
            if (root.getPid() == 0L) {
                resultList.add(root);
            }
        });
        resultList.forEach(root -> {
            Deque<CategoriesVo> stack = new ArrayDeque<>();
            stack.push(root);
            while (!stack.isEmpty()) {
                CategoriesVo node = stack.pop();
                List<CategoriesVo> children = parentMap.remove(node.getId());
                if (CollectionUtils.isNotEmpty(children)) {
                    children.forEach(child -> {
                        node.getChildren().add(child);
                        stack.push(child);
                    });
                }
            }
        });
        List<CategoriesVo> allValues = new ArrayList<>();
        for (Long key : parentMap.keySet()) {
            List<CategoriesVo> values = parentMap.get(key);
            if (values != null) {
                allValues.addAll(values);
            }
        }

        return allValues;
    }
}
