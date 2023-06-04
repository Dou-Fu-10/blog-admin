package com.blog.modules.blog.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.blog.annotation.rest.AnonymousGetMapping;
import com.blog.exception.BadRequestException;
import com.blog.modules.blog.entity.*;
import com.blog.modules.blog.entity.vo.CategoriesVo;
import com.blog.modules.blog.entity.vo.PostsVo;
import com.blog.modules.blog.entity.vo.TagVo;
import com.blog.modules.blog.service.*;
import com.blog.utils.ConvertUtil;
import jakarta.annotation.Resource;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author ty
 */
@RestController
@RequestMapping("/api/posts")
public class PostsController {

    @Resource
    private ArticleService articleService;
    @Resource
    private ArticleTagService articleTagService;
    @Resource
    private TagService tagService;
    @Resource
    private CategoriesService categoriesService;
    @Resource
    private ArticleCategoriesService articleCategoriesService;

    /**
     * 用于 公共别名访问
     *
     * @param alias 别名
     * @return 单条数据
     */
    @AnonymousGetMapping("/article/{alias}")
    public ResponseEntity<Object> selectOne(@PathVariable @NotEmpty String alias) {
        //
        PostsVo postsVo = ConvertUtil.convert(articleService.getAlias(alias), PostsVo.class);
        if (Objects.isNull(postsVo)) {
            throw new BadRequestException("文章为空");
        }
        // 获取文章所绑定的分类
        List<CategoriesEntity> articleCategoriesByArticleId = articleCategoriesService.getArticleCategoriesByArticleId(postsVo.getId());
        // 判断文章是否绑定分类
        if (CollectionUtils.isNotEmpty(articleCategoriesByArticleId)) {
            // 将分类添加到 文章VO中
            articleCategoriesByArticleId.forEach(categoriesEntity -> postsVo.getCategoriesList().put(categoriesEntity.getId(), categoriesEntity.getName()));
        }

        return new ResponseEntity<>(postsVo, HttpStatus.OK);
    }

    /**
     * 用于 公共别名访问
     *
     * @param page 分页对象
     * @return 单条数据
     */
    @AnonymousGetMapping
    public ResponseEntity<Object> selectAll(Page<ArticleEntity> page) {
        // 获取分类数据
        Page<ArticleEntity> articleEntityPage = this.articleService.page(page, Wrappers.<ArticleEntity>lambdaQuery().eq(ArticleEntity::getHide,false).eq(ArticleEntity::getChecked,true).orderByDesc(ArticleEntity::getDate));
        if (CollectionUtils.isEmpty(articleEntityPage.getRecords())) {
            return new ResponseEntity<>(articleEntityPage, HttpStatus.OK);
        }
        // 将分类数据转换成 PostsVo
        IPage<PostsVo> postsVoConvert = articleEntityPage.convert(article -> ConvertUtil.convert(article, PostsVo.class));

        List<PostsVo> postsVoList = postsVoConvert.getRecords();
        // 通过文章查出 与其绑定的分类
        postsVoList.forEach(postsVo -> {
            List<CategoriesEntity> articleCategoriesByArticleId = articleCategoriesService.getArticleCategoriesByArticleId(postsVo.getId());
            // 判断文章是否绑定分类
            if (CollectionUtils.isNotEmpty(articleCategoriesByArticleId)) {
                articleCategoriesByArticleId.forEach(categoriesEntity -> postsVo.getCategoriesList().put(categoriesEntity.getId(), categoriesEntity.getName()));
            }

        });
        return new ResponseEntity<>(postsVoConvert, HttpStatus.OK);
    }

    @AnonymousGetMapping("/tag")
    public ResponseEntity<Object> selectTags() {
        // 获取标签
        List<TagEntity> tagEntityList = tagService.list();
        if (CollectionUtils.isEmpty(tagEntityList)) {
            throw new BadRequestException("标签列表为空");
        }
        List<TagVo> tagVoList = ConvertUtil.convertList(tagEntityList, TagVo.class);


        Set<Long> idCollect = tagVoList.stream().map(TagVo::getId).collect(Collectors.toSet());

        List<ArticleTagEntity> articleTagByTagIdList = articleTagService.getArticleTagByTagIdList(idCollect);

        if (CollectionUtils.isNotEmpty(articleTagByTagIdList)) {
            HashMap<Long, Integer> tagMap = new HashMap<>(8);
            articleTagByTagIdList.forEach(articleTagEntity -> {
                Long tid = articleTagEntity.getTid();
                int temp = 1;
                if (tagMap.containsKey(tid)) {
                    temp = tagMap.get(tid) + 1;
                }
                tagMap.put(tid, temp);
            });
            for (TagVo tagVo : tagVoList) {
                Long id = tagVo.getId();
                if (tagMap.containsKey(id)) {
                    tagVo.setArticleNum(tagMap.get(id));
                }
            }
        }

        return new ResponseEntity<>(ConvertUtil.convertList(tagVoList, TagVo.class), HttpStatus.OK);
    }

    /**
     * 用于 公共别名访问
     *
     * @return 单条数据
     */
    @AnonymousGetMapping("/categories")
    public ResponseEntity<Object> selectCategories() {
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

        return new ResponseEntity<>(categoriesVoListTree, HttpStatus.OK);
    }

    /**
     * 分类 树形结构
     */
    private List<CategoriesVo> getCategoriesVoListTree(List<CategoriesVo> categoriesVoList) {
        // 存储非根（顶级级别）节点
        Map<Long, List<CategoriesVo>> tempMap = new HashMap<>(10);
        // 去重
        categoriesVoList = new ArrayList<>(new HashSet<>(categoriesVoList));
        // 存储最终的树形结构结果
        List<CategoriesVo> resultList = new LinkedList<>();

        // 遍历集合,如果 对象的父id==0 代表他是根节点添加到最终结果中（即 resultList ）,否则为非根节点添加到临时节点
        for (CategoriesVo categoriesVo : categoriesVoList) {
            categoriesVo.setChildren(new ArrayList<>());
            // 结果为true 即 根节点
            if (categoriesVo.getPid() == 0L) {
                resultList.add(categoriesVo);
            } else {
                // Java 集合类中的 Map.containsKey() 方法判断 Map 集合对象中是否包含指定的键名。如果 Map 集合中包含指定的键名，则返回 true，否则返回 false。
                // 判断 子节点是否存在 存在即将结果添加进 现有的字节的中
                if (tempMap.containsKey(categoriesVo.getPid())) {
                    // 添加进 现有的字节的中
                    tempMap.get(categoriesVo.getPid()).add(categoriesVo);
                } else {
                    // 当前 字节的为空 将以 上级id为key 自身为 value
                    List<CategoriesVo> sList = new ArrayList<>();
                    sList.add(categoriesVo);
                    tempMap.put(categoriesVo.getPid(), sList);
                }
            }
        }
        // 遍历所有根节点,通过根节点和非根节点集合,找到这个根节点的所有子节点
        for (CategoriesVo s : resultList) {
            getChildNode(tempMap, Collections.singletonList(s));
        }
        return resultList;
    }


    /**
     * @param tempMap
     * @param fatherNodeList
     */
    private static void getChildNode(Map<Long, List<CategoriesVo>> tempMap, List<CategoriesVo> fatherNodeList) {
        for (CategoriesVo categoriesVo : fatherNodeList) {
            Long categoriesVoId = categoriesVo.getId();
            if (tempMap.containsKey(categoriesVoId)) {
                List<CategoriesVo> categoriesVoList = tempMap.get(categoriesVo.getId());
                categoriesVo.getChildren().addAll(categoriesVoList);
                getChildNode(tempMap, tempMap.get(categoriesVo.getId()));
            }
        }
    }

}
