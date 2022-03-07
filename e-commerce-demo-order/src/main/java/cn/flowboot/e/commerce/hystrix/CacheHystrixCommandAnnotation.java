package cn.flowboot.e.commerce.hystrix;

import cn.flowboot.e.commerce.service.OrderService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.cache.annotation.CacheKey;
import com.netflix.hystrix.contrib.javanica.cache.annotation.CacheRemove;
import com.netflix.hystrix.contrib.javanica.cache.annotation.CacheResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <h1></h1>
 *
 * @version 1.0
 * @author: Vincent Vic
 * @since: 2022/03/06
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class CacheHystrixCommandAnnotation {

    private final OrderService orderService;

    // 第一种 Hystrix Cache 注解的使用方法

    @CacheResult(cacheKeyMethod = "getCacheKey")
    @HystrixCommand(commandKey = "CacheHystrixCommandAnnotation")
    public Map<String,Object> useCacheByAnnotation01(String orderNo){
        log.info("use cache01 to get order client info:[{}]",orderNo);
        return orderService.desc(orderNo);
    }

    @CacheRemove(commandKey = "CacheHystrixCommandAnnotation",
            cacheKeyMethod = "getCacheKey")
    @HystrixCommand
    public void flushCacheByAnnotation01(String cacheId) {
        log.info("flush hystrix cache key: [{}]", cacheId);
    }

    public String getCacheKey(String cacheId) {
        return cacheId;
    }

    // 第二种 Hystrix Cache 注解的使用方法
    @CacheResult
    @HystrixCommand(commandKey = "CacheHystrixCommandAnnotation")
    public Map<String,Object> useCacheByAnnotation02(@CacheKey String orderNo) {

        log.info("use cache02 to get order client info: [{}]", orderNo);
        return orderService.desc(orderNo);
    }

    @CacheRemove(commandKey = "CacheHystrixCommandAnnotation")
    @HystrixCommand
    public void flushCacheByAnnotation02(@CacheKey String cacheId) {
        log.info("flush hystrix cache key: [{}]", cacheId);
    }

    // 第三种 Hystrix Cache 注解的使用方法
    @CacheResult
    @HystrixCommand(commandKey = "CacheHystrixCommandAnnotation")
    public Map<String,Object> useCacheByAnnotation03(String orderNo) {

        log.info("use cache03 to get order info: [{}]", orderNo);
        return orderService.desc(orderNo);
    }

    @CacheRemove(commandKey = "CacheHystrixCommandAnnotation")
    @HystrixCommand
    public void flushCacheByAnnotation03(String cacheId) {
        log.info("flush hystrix cache key: [{}]", cacheId);
    }



}
