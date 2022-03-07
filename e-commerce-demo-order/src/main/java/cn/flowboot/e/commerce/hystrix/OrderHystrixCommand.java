package cn.flowboot.e.commerce.hystrix;


import cn.flowboot.e.commerce.service.OrderService;
import com.netflix.hystrix.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * <h1> OrderService 实现包装</h1>
 * Hystrix舱壁模式:
 * 1。线程池
 * 2.信号量 ： 有限信号机
 * @version 1.0
 * @author: Vincent Vic
 * @since: 2022/03/06
 */
@Slf4j

@Service
public class OrderHystrixCommand extends HystrixCommand<List<Map<String,Object>>> {

    private final OrderService orderService;

    protected OrderHystrixCommand(OrderService orderService) {
        super(Setter.withGroupKey(
                HystrixCommandGroupKey.Factory.asKey("OrderService"))
                .andCommandKey(HystrixCommandKey.Factory.asKey("OrderHystrixCommand"))
                .andThreadPoolKey(HystrixThreadPoolKey.Factory.asKey("OrderClientPool"))
                .andCommandPropertiesDefaults(
                        HystrixCommandProperties.Setter()
                                .withExecutionIsolationStrategy(HystrixCommandProperties.ExecutionIsolationStrategy.THREAD) //线程池隔离策略
                                .withFallbackEnabled(true)
                                .withCircuitBreakerEnabled(true)
                )
        );

        //可以配置信号量隔离策略
//        Setter semaphore = Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("OrderService"))
//                .andCommandKey(HystrixCommandKey.Factory.asKey("OrderHystrixCommand"))
//                .andCommandPropertiesDefaults(
//                        HystrixCommandProperties.Setter()
//                                .withCircuitBreakerRequestVolumeThreshold(10)
//                                .withCircuitBreakerSleepWindowInMilliseconds(5000)
//                                .withExecutionIsolationStrategy(HystrixCommandProperties.ExecutionIsolationStrategy.SEMAPHORE)
//                );

        this.orderService = orderService;
    }

    /**
     * Implement this method with code to be executed when {@link #execute()} or {@link #queue()} are invoked.
     *
     * @return R response type
     * @throws Exception if command execution fails
     */
    @Override
    protected List<Map<String, Object>> run() throws Exception {
        log.info("hystrix command to Get Order List: [{}]",
                Thread.currentThread().getName());
        return orderService.list();
    }

    /**
     * If {@link #execute()} or {@link #queue()} fails in any way then this method will be invoked to provide an opportunity to return a fallback response.
     * <p>
     * This should do work that does not require network transport to produce.
     * <p>
     * In other words, this should be a static or cached result that can immediately be returned upon failure.
     * <p>
     * If network traffic is wanted for fallback (such as going to MemCache) then the fallback implementation should invoke another {@link HystrixCommand} instance that protects against that network
     * access and possibly has another level of fallback that does not involve network access.
     * <p>
     * DEFAULT BEHAVIOR: It throws UnsupportedOperationException.
     *
     * @return R or throw UnsupportedOperationException if not implemented
     */
    @Override
    protected List<Map<String, Object>> getFallback() {
        log.warn("trigger hystrix fallback: [{}]",
                Thread.currentThread().getName());
        return Collections.emptyList();
    }
}
