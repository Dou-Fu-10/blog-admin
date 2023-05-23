package com.blog.modules.logging.service.impl;

import cn.hutool.core.lang.Dict;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.blog.base.PageInfo;
import com.blog.base.QueryHelpMybatisPlus;
import com.blog.base.impl.CommonServiceImpl;
import com.blog.modules.logging.annotation.Log;
import com.blog.modules.logging.service.LogService;
import com.blog.modules.logging.service.dto.LogErrorDTO;
import com.blog.modules.logging.service.dto.LogQueryParam;
import com.blog.modules.logging.service.dto.LogSmallDTO;
import com.blog.modules.logging.service.mapper.LogMapper;
import com.blog.utils.*;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.*;

/**
* @author jinjin
* @date 2020-09-27
*/
@Slf4j
@Service
@AllArgsConstructor
// @CacheConfig(cacheNames = LogService.CACHE_KEY)
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class LogServiceImpl extends CommonServiceImpl<LogMapper, com.blog.modules.logging.domain.Log> implements LogService {

    // private final RedisUtils redisUtils;
    private final LogMapper logMapper;

    @Override
    public Object queryAll(LogQueryParam query, Pageable pageable) {
        IPage<com.blog.modules.logging.domain.Log> page = PageUtil.toMybatisPage(pageable);
        IPage<com.blog.modules.logging.domain.Log> pageList = logMapper.selectPage(page, QueryHelpMybatisPlus.getPredicate(query));
        String status = "ERROR";
        if (status.equals(query.getLogType())) {
            return ConvertUtil.convertPage(pageList, LogErrorDTO.class);
        }
        return ConvertUtil.convertPage(pageList, com.blog.modules.logging.domain.Log.class);
    }

    @Override
    public List<com.blog.modules.logging.domain.Log> queryAll(LogQueryParam query){
        return logMapper.selectList(QueryHelpMybatisPlus.getPredicate(query));
    }

    @Override
    public PageInfo<LogSmallDTO> queryAllByUser(LogQueryParam query, Pageable pageable) {
        IPage<com.blog.modules.logging.domain.Log> page = PageUtil.toMybatisPage(pageable);
        IPage<com.blog.modules.logging.domain.Log> pageList = logMapper.selectPage(page, QueryHelpMybatisPlus.getPredicate(query));
        return ConvertUtil.convertPage(pageList, LogSmallDTO.class);
    }

    @Override
    // @Cacheable(key = "'id:' + #p0")
    public com.blog.modules.logging.domain.Log findById(Long id) {
        return getById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean removeByLogType(String logType) {
        return lambdaUpdate().eq(com.blog.modules.logging.domain.Log::getLogType, logType).remove();
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(String username, String browser, String ip, ProceedingJoinPoint joinPoint, com.blog.modules.logging.domain.Log log) {

        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        Log aopLog = method.getAnnotation(Log.class);

        // 方法路径
        String methodName = joinPoint.getTarget().getClass().getName() + "." + signature.getName() + "()";
        
        // 描述
        if (log != null) {
            log.setDescription(aopLog.value());
        }
        assert log != null;
        log.setRequestIp(ip);
        
        log.setAddress(StringUtils.getCityInfo(log.getRequestIp()));
        log.setMethod(methodName);
        log.setUsername(username);
        log.setParams(getParameter(method, joinPoint.getArgs()));
        log.setBrowser(browser);
        if (log.getId() == null) {
            logMapper.insert(log);
        } else {
            logMapper.updateById(log);
        }
    }
    
    /**
     * 根据方法和传入的参数获取请求参数
     */
    private String getParameter(Method method, Object[] args) {
        List<Object> argList = new ArrayList<>();
        Parameter[] parameters = method.getParameters();
        for (int i = 0; i < parameters.length; i++) {
            //将RequestBody注解修饰的参数作为请求参数
            RequestBody requestBody = parameters[i].getAnnotation(RequestBody.class);
            if (requestBody != null) {
                argList.add(args[i]);
            }
            //将RequestParam注解修饰的参数作为请求参数
            RequestParam requestParam = parameters[i].getAnnotation(RequestParam.class);
            if (requestParam != null) {
                Map<String, Object> map = new HashMap<>();
                String key = parameters[i].getName();
                if (!StringUtils.isEmpty(requestParam.value())) {
                    key = requestParam.value();
                }
                map.put(key, args[i]);
                argList.add(map);
            }
        }
        if (argList.size() == 0) {
            return "";
        }
        return argList.size() == 1 ? JSONUtil.toJsonStr(argList.get(0)) : JSONUtil.toJsonStr(argList);
    }
    
    @Override
    public Object findByErrDetail(Long id) {
        com.blog.modules.logging.domain.Log log = findById(id);
        ValidationUtil.isNull(log.getId(), "Log", "id", id);
        byte[] details = log.getExceptionDetail();
        return Dict.create().set("exception", new String(ObjectUtil.isNotNull(details) ? details : "".getBytes()));
    }

    @Override
    public void download(List<com.blog.modules.logging.domain.Log> logs, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (com.blog.modules.logging.domain.Log log : logs) {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("用户名", log.getUsername());
            map.put("IP", log.getRequestIp());
            map.put("IP来源", log.getAddress());
            map.put("描述", log.getDescription());
            map.put("浏览器", log.getBrowser());
            map.put("请求耗时/毫秒", log.getTime());
            map.put("异常详情", new String(ObjectUtil.isNotNull(log.getExceptionDetail()) ? log.getExceptionDetail() : "".getBytes()));
            map.put("创建日期", log.getCreateTime());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delAllByError() {
        this.removeByLogType("ERROR");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delAllByInfo() {
        this.removeByLogType("INFO");
    }
}
