package com.blog.modules.system.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.blog.modules.system.service.mapper.DictDetailMapper;
import com.blog.modules.system.service.mapper.DictMapper;
import lombok.AllArgsConstructor;
import com.blog.base.PageInfo;
import com.blog.base.QueryHelpMybatisPlus;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.blog.modules.system.domain.Dict;
import com.blog.utils.CacheKey;
import com.blog.utils.ConvertUtil;
import com.blog.modules.system.domain.DictDetail;
import com.blog.modules.system.service.DictDetailService;
import com.blog.modules.system.service.dto.DictDetailDto;
import com.blog.modules.system.service.dto.DictDetailQueryParam;
import com.blog.utils.PageUtil;
import com.blog.commom.redis.service.RedisService;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
// 默认不使用缓存
//import org.springframework.cache.annotation.CacheConfig;
//import org.springframework.cache.annotation.CacheEvict;
//import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author ty
 */
@Service
@AllArgsConstructor
@CacheConfig(cacheNames = "dictDetail")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class DictDetailServiceImpl extends ServiceImpl<DictDetailMapper, DictDetail> implements DictDetailService {

    private final DictDetailMapper dictDetailMapper;
    private final DictMapper dictMapper;
    private final RedisService redisService;

    @Override
    public PageInfo<DictDetailDto> queryAll(DictDetailQueryParam query, Pageable pageable) {
        IPage<DictDetail> page = PageUtil.toMybatisPage(pageable);
        IPage<DictDetail> pageList = dictDetailMapper.selectPage(page, QueryHelpMybatisPlus.getPredicate(query));
        return ConvertUtil.convertPage(pageList, DictDetailDto.class);
    }

    @Override
    public List<DictDetailDto> queryAll(DictDetailQueryParam query) {
        return ConvertUtil.convertList(dictDetailMapper.selectList(QueryHelpMybatisPlus.getPredicate(query)), DictDetailDto.class);
    }

    @Override
    public List<DictDetailDto> getDictByName(String dictName) {
        Dict dict = dictMapper.lambdaQuery().eq(Dict::getName, dictName).one();
        List<DictDetailDto> ret = dictDetailMapper.getDictDetailsByDictName(dictName);
        redisService.set(CacheKey.DICTDEAIL_DICTID + dict.getId(), ret);
        return ret;
    }

    @Override
    public PageInfo<DictDetailDto> getDictByName(String dictName, Pageable pageable) {
        IPage<DictDetailDto> page = PageUtil.toMybatisPage(pageable, true);
        return ConvertUtil.convertPage(dictDetailMapper.getDictDetailsByDictName(dictName, page), DictDetailDto.class);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateById(DictDetailDto resources) {
        DictDetail detail = ConvertUtil.convert(resources, DictDetail.class);
        detail.setDictId(resources.getDict().getId());
        boolean ret = dictDetailMapper.updateById(detail) > 0;
        // 清理缓存
        delCaches(detail.getDictId());
        return ret;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean save(DictDetailDto resources) {
        DictDetail detail = ConvertUtil.convert(resources, DictDetail.class);
        detail.setDictId(resources.getDict().getId());
        boolean ret = dictDetailMapper.insert(detail) > 0;
        // 清理缓存
        delCaches(detail.getDictId());
        return ret;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean removeById(Long id) {
        DictDetail dictDetail = dictDetailMapper.selectById(id);
        boolean ret = dictDetailMapper.deleteById(id) > 0;
        // 清理缓存
        delCaches(dictDetail.getDictId());
        return ret;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean removeByDictId(Long id) {
        boolean ret = lambdaUpdate().eq(DictDetail::getDictId, id).remove();
        delCaches(id);
        return ret;
    }

    private void delCaches(Long dictId) {
        redisService.del(CacheKey.DICTDEAIL_DICTID + dictId);
    }
}
