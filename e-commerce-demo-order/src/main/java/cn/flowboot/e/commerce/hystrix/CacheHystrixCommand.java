package cn.flowboot.e.commerce.hystrix;

import cn.flowboot.e.commerce.service.OrderService;
import com.google.common.collect.Maps;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandKey;
import com.netflix.hystrix.HystrixRequestCache;
import com.netflix.hystrix.strategy.concurrency.HystrixConcurrencyStrategy;
import com.netflix.hystrix.strategy.concurrency.HystrixConcurrencyStrategyDefault;
import lombok.extern.slf4j.Slf4j;

import java.util.Collections;
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
public class CacheHystrixCommand extends HystrixCommand<Map<String,Object>> {
    private final OrderService orderService;
    private static final HystrixCommandKey CACHE_KEY = HystrixCommandKey.Factory.asKey("CacheHystrixCommand");

    private final String orderNo;

    public CacheHystrixCommand(OrderService orderService,String orderNo) {
        super(
                HystrixCommand.Setter
                        .withGroupKey(HystrixCommandGroupKey.Factory.asKey("CacheHystrixCommandGroup"))
                        .andCommandKey(CACHE_KEY)
        );
        this.orderService = orderService;
        this.orderNo = orderNo;
    }

    /**
     * Implement this method with code to be executed when {@link #execute()} or {@link #queue()} are invoked.
     *
     * @return R response type
     * @throws Exception if command execution fails
     */
    @Override
    protected Map<String, Object> run() throws Exception {
        log.info("CacheHystrixCommand In Hystrix Command to get service instance:[{}], [{}]",orderNo,Thread.currentThread().getName());
        return orderService.desc(orderNo);
    }

    @Override
    protected Map<String, Object> getFallback() {
        return Maps.newConcurrentMap();
    }

    @Override
    protected String getCacheKey() {
        return orderNo;
    }

    /**
     * <h2> flushRequestCache - 根据缓存key清理在一次Hystrix请求上下文中的缓存 <h2>
     * version: 1.0 - 2022/3/6
     * @param orderNo 订单号
     */
    public static void flushRequestCache(String orderNo){
        HystrixRequestCache.getInstance(CACHE_KEY, HystrixConcurrencyStrategyDefault.getInstance()).clear(orderNo);
        log.info("flush request cacheIin hystrix command:[{}],[{}]",orderNo,Thread.currentThread().getName());
    }
}
