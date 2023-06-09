package com.blog.modules.blog.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.blog.annotation.rest.AnonymousGetMapping;
import com.blog.exception.BadRequestException;
import com.blog.modules.blog.entity.*;
import com.blog.modules.blog.entity.vo.CategoriesVo;
import com.blog.modules.blog.entity.vo.InfoVo;
import com.blog.modules.blog.entity.vo.PostsVo;
import com.blog.modules.blog.entity.vo.TagVo;
import com.blog.modules.blog.service.*;
import com.blog.utils.ConvertUtil;
import jakarta.annotation.Resource;
import jakarta.validation.constraints.NotEmpty;
import org.jetbrains.annotations.NotNull;
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
    @Resource
    private InfoService infoService;

    /**
     * 配置最多能递归多少次
     */
    private int avoidRecursion = 0;
    private final int MAX_RECURSION = 2000;


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
        Page<ArticleEntity> articleEntityPage = this.articleService.page(page, Wrappers.<ArticleEntity>lambdaQuery().eq(ArticleEntity::getHide, false).eq(ArticleEntity::getChecked, true).orderByDesc(ArticleEntity::getDate));
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
     * （分类、标签、文章）数量
     *
     * @return （分类、标签、文章）数量
     */
    @AnonymousGetMapping("/data")
    public ResponseEntity<Object> selectData() {
        HashMap<String, Object> dataHashMap = new HashMap<>(3);
        dataHashMap.put("article", articleService.count(Wrappers.<ArticleEntity>lambdaQuery().eq(ArticleEntity::getHide, false).eq(ArticleEntity::getChecked, true)));
        dataHashMap.put("tag", tagService.count());
        dataHashMap.put("categories", categoriesService.count());
        InfoEntity infoEntity = infoService.getOne(Wrappers.<InfoEntity>lambdaQuery().isNotNull(InfoEntity::getId).last("limit 1"));
        InfoVo convert = ConvertUtil.convert(infoEntity, InfoVo.class);
        dataHashMap.put("info", convert);
        return new ResponseEntity<>(dataHashMap, HttpStatus.OK);
    }

    public List<CategoriesVo> getCategoriesVoListTree(List<CategoriesVo> categories) {
        // 1.首先，判断输入的类别集合是否为空，如果为空，则立即返回一个空的列表。
        if (CollectionUtils.isEmpty(categories)) {
            return new ArrayList<>();
        }
        // 以父类id为key，将自己为value添加到父类id名下的集合里面
        // 2.创建三个 Map 变量，分别用于存储类别信息、父子关系信息。categoryMap 存储类别 ID 与对应的 CategoriesVo 对象，parentMap 存储每个类别节点的所有子节点。
        Map<Long, List<CategoriesVo>> parentMap = new HashMap<>(categories.size());
        // 循环集合
        // 3.对于输入的每个类别对象，设置它的 children 属性为一个空的列表；将它加入到 categoryMap 中，并将它的父子关系添加到 parentMap 中。父子关系的构建通过调用computeIfAbsent方法实现。
        categories.forEach(category -> {
            // 创建一个空子类集合集结地
            category.setChildren(new ArrayList<>());
            // 判断 category.getPid()（即当前被循环到的CategoriesVo他的父类Pid） 是否在parentMap（Pid最为kay）里面存在，
            // 如果存在取出对应的value（这里是一个List<CategoriesVo>集合）然后将自身（以add的方式即add(category)）存进去，
            // 如果不存在就调用 lambda 表达式得出一个新（List<CategoriesVo>）集合，然后将自身（以add的方式即add(category)）存进去，
            parentMap.computeIfAbsent(category.getPid(), key -> new ArrayList<>()).add(category);
        });
        // 存放顶级（父类）节点的集合
        // 4.创建一个链表类型的 resultList 变量，用于保存类别树的根节点列表。
        List<CategoriesVo> resultList = new LinkedList<>();
        // 5.再次遍历所有类别对象，找到每个父节点，将它们加入到 resultList 中。
        categories.forEach(root -> {
            if (root.getPid() == 0L) {
                resultList.add(root);
            }
        });
        // forEach 类似 类似 for 里面的操作会改变自身
        resultList.forEach(root -> {
            //ArrayDeque 和 ArrayList 都是 Java 集合框架中提供的 List 类实现，但它们在内部的数据结构和使用上存在一些区别：
            //数据结构实现方式
            //ArrayDeque 内部使用基于数组的数据结构实现，而 ArrayList 也是基于数组的实现。不同之处在于，ArrayDeque 实现了双端队列，即支持快速从队列头和队列尾添加或删除元素，而 ArrayList 只能在数组的尾部添加或删除元素。
            //空间性能
            //ArrayDeque 和 ArrayList 都是动态数组，但是 ArrayDeque 中的元素是被分散存储在数组中的，而 ArrayList 中的元素是连续存储的，这导致在增加和删除元素时，ArrayDeque 往往比 ArrayList 更节省空间。
            //访问性能
            //在访问值方面，ArrayList 要优于 ArrayDeque，因为 ArrayList 中的元素是连续存储的，因此具有更好的缓存局部性，而 ArrayDeque 中的元素是被分散存储的，访问效率相对更低。
            //线程安全
            //ArrayDeque 和 ArrayList 都不是线程安全的，因此在多线程环境中应该采用同步方式进行访问。
            //综上所述，如果需要频繁执行元素的插入和删除操作，并且需要从队列头和尾部进行操作，则应该使用 ArrayDeque，而如果需要在列表中进行频繁的随机访问，则应该使用 ArrayList。

            // 6.对于每个父节点，创建一个空的 stack，将该父节点压入栈中。
            Deque<CategoriesVo> stack = new ArrayDeque<>();
            //ArrayDeque 是一种双端队列数据结构，支持在队列的两端进行插入、删除、访问元素等操作。在实际应用中，ArrayDeque 通常用于实现栈或队列等数据结构，也可以用于实现一些基本的数据结构，如 List、Queue 等。
            stack.push(root);
            while (!stack.isEmpty()) {
                // 7.从栈中弹出一个节点，通过该节点的 ID 从 parentMap 中查找它的子节点列表，将子节点添加到该节点的 children 列表中，并将子节点入栈。
                //pop() 方法会改变原始的 ArrayDeque。调用 pop() 方法会从 ArrayDeque 的头部删除一个元素，并返回被删除的元素，同时修改 ArrayDeque 的大小和顺序。
                CategoriesVo node = stack.pop();
                //在 Map 接口中，执行 remove() 方法时会返回被删除键所对应的值。具体来说，remove(Object key) 方法的返回值为被删除键所对应的值，如果不存在对应的映射，则返回 null。
                // 即以key的方式删除 parentMap里的顶级类 并将被删除的（value）集合返回
                List<CategoriesVo> children = parentMap.remove(node.getId());
                // 判断不为空
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

    /**
     * @param tempMap
     * @param fatherNodeList
     */
    private void getChildNode(Map<Long, List<CategoriesVo>> tempMap, @NotNull List<CategoriesVo> fatherNodeList) {
        for (CategoriesVo categoriesVo : fatherNodeList) {
            Long categoriesVoId = categoriesVo.getId();
            if (tempMap.containsKey(categoriesVoId)) {
                List<CategoriesVo> categoriesVoList = tempMap.get(categoriesVo.getId());
                categoriesVo.getChildren().addAll(categoriesVoList);
                getChildNode(tempMap, tempMap.get(categoriesVo.getId()));
            }
        }
    }

    /**
     * 分类 树形结构
     */
    @NotNull
    private List<CategoriesVo> getCategoriesVoListTree1(List<CategoriesVo> categoriesVoList) {
        if (CollectionUtils.isEmpty(categoriesVoList)) {
            return new ArrayList<>();
        }
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
        if (CollectionUtils.isEmpty(resultList)) {
            return new ArrayList<>();
        }
        for (CategoriesVo result : resultList) {
            getChildNode(tempMap, Collections.singletonList(result));
        }
        return resultList;
    }


}
