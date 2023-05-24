//
//package com.blog.config;
//
//import com.alibaba.fastjson2.JSON;
//import com.fasterxml.jackson.annotation.JsonAutoDetect;
//import com.fasterxml.jackson.annotation.JsonTypeInfo;
//import com.fasterxml.jackson.annotation.PropertyAccessor;
//import com.fasterxml.jackson.databind.DeserializationFeature;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.fasterxml.jackson.databind.SerializationFeature;
//import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
//import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.commons.codec.digest.DigestUtils;
//import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
//import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
//import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
//import org.springframework.boot.context.properties.EnableConfigurationProperties;
//import org.springframework.cache.Cache;
//import org.springframework.cache.annotation.CachingConfigurer;
//import org.springframework.cache.annotation.EnableCaching;
//import org.springframework.cache.interceptor.CacheErrorHandler;
//import org.springframework.cache.interceptor.KeyGenerator;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.redis.connection.RedisConnectionFactory;
//import org.springframework.data.redis.core.RedisOperations;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.data.redis.serializer.*;
//import java.util.HashMap;
//import java.util.Map;
//
///**
// * @author ty
// */
//@Slf4j
//@Configuration
//@EnableCaching
//@ConditionalOnClass(RedisOperations.class)
//@EnableConfigurationProperties(RedisProperties.class)
//public class RedisConfig implements CachingConfigurer {
//
//    /**
//     *  设置 redis 数据默认过期时间，默认2小时
//     *  设置@cacheable 序列化方式
//     */
////    @Bean
////    public RedisCacheConfiguration redisCacheConfiguration(){
////        FastJsonRedisSerializer<Object> fastJsonRedisSerializer = new FastJsonRedisSerializer<>(Object.class);
////        RedisCacheConfiguration configuration = RedisCacheConfiguration.defaultCacheConfig();
////        configuration = configuration.serializeValuesWith(RedisSerializationContext.SerializationPair
////                .fromSerializer(fastJsonRedisSerializer)).entryTtl(Duration.ofHours(6));
////        return configuration;
////    }
//
//    @SuppressWarnings("all")
//    @Bean(name = "redisTemplate")
//    @ConditionalOnMissingBean(name = "redisTemplate")
//    public RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory factory){
//        RedisTemplate<Object, Object> redisTemplate = new RedisTemplate<>();
//        redisTemplate.setConnectionFactory(factory);
//        //使用Jackson2JsonRedisSerializer来序列化和反序列化redis的value值
//        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
//        ObjectMapper objectMapper = new ObjectMapper();
//        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
//        //反序列化时候遇到不匹配的属性并不抛出异常
//        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
//        //序列化时候遇到空对象不抛出异常
//        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
//        //反序列化的时候如果是无效子类型,不抛出异常
//        objectMapper.configure(DeserializationFeature.FAIL_ON_INVALID_SUBTYPE, false);
//        //不使用默认的dateTime进行序列化,
//        objectMapper.configure(SerializationFeature.WRITE_DATE_KEYS_AS_TIMESTAMPS, false);
//        //使用JSR310提供的序列化类,里面包含了大量的JDK8时间序列化类
//        objectMapper.registerModule(new JavaTimeModule());
//        //启用反序列化所需的类型信息,在属性中添加@class
//        objectMapper.activateDefaultTyping(LaissezFaireSubTypeValidator.instance, ObjectMapper.DefaultTyping.NON_FINAL, JsonTypeInfo.As.PROPERTY);
//        jackson2JsonRedisSerializer.setObjectMapper(objectMapper);
//        RedisSerializer<?> stringSerializer = new StringRedisSerializer();
//        //key序列化
//        redisTemplate.setKeySerializer(stringSerializer);
//        //value序列化
//        redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
//        //Hash key序列化
//        redisTemplate.setHashKeySerializer(stringSerializer);
//        // Hashvalue序列化
//        redisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer);
//        redisTemplate.afterPropertiesSet();
//        return redisTemplate;
//    }
//    /**
//     * 自定义缓存key生成策略，默认将使用该策略
//     */
//    @Bean
//    @Override
//    public KeyGenerator keyGenerator() {
//        return (target, method, params) -> {
//            Map<String,Object> container = new HashMap<>(3);
//            Class<?> targetClassClass = target.getClass();
//            // 类地址
//            container.put("class",targetClassClass.toGenericString());
//            // 方法名称
//            container.put("methodName",method.getName());
//            // 包名称
//            container.put("package",targetClassClass.getPackage());
//            // 参数列表
//            for (int i = 0; i < params.length; i++) {
//                container.put(String.valueOf(i),params[i]);
//            }
//            // 转为JSON字符串
//            String jsonString = JSON.toJSONString(container);
//            // 做SHA256 Hash计算，得到一个SHA256摘要作为Key
//            return DigestUtils.sha256Hex(jsonString);
//        };
//    }
//
//    @Bean
//    @Override
//    public CacheErrorHandler errorHandler() {
//        // 异常处理，当Redis发生异常时，打印日志，但是程序正常走
//        log.info("初始化 -> [{}]", "Redis CacheErrorHandler");
//        return new CacheErrorHandler() {
//            @Override
//            public void handleCacheGetError(RuntimeException e, Cache cache, Object key) {
//                log.error("Redis occur handleCacheGetError：key -> [{}]", key, e);
//            }
//
//            @Override
//            public void handleCachePutError(RuntimeException e, Cache cache, Object key, Object value) {
//                log.error("Redis occur handleCachePutError：key -> [{}]；value -> [{}]", key, value, e);
//            }
//
//            @Override
//            public void handleCacheEvictError(RuntimeException e, Cache cache, Object key) {
//                log.error("Redis occur handleCacheEvictError：key -> [{}]", key, e);
//            }
//
//            @Override
//            public void handleCacheClearError(RuntimeException e, Cache cache) {
//                log.error("Redis occur handleCacheClearError：", e);
//            }
//        };
//    }
//
//}
