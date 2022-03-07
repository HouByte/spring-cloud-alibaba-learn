package cn.flowboot.e.commerce.hystrix;


import cn.flowboot.e.commerce.service.OrderService;
import com.netflix.hystrix.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import rx.Observable;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * <h1> OrderService 实现包装 信号量处理</h1>
 * Hystrix舱壁模式:
 * 1。线程池
 * 2.信号量 ： 有限信号机
 * @version 1.0
 * @author: Vincent Vic
 * @since: 2022/03/06
 */
@Slf4j
@Service
public class OrderHystrixObservableCommand extends HystrixObservableCommand<List<Map<String,Object>>> {

    private final OrderService orderService;

    protected OrderHystrixObservableCommand(OrderService orderService) {
        super(HystrixObservableCommand.Setter
                .withGroupKey(HystrixCommandGroupKey.Factory.asKey("OrderService"))
                .andCommandKey(HystrixCommandKey.Factory.asKey("OrderHystrixObservableCommand"))
                .andCommandPropertiesDefaults(
                        HystrixCommandProperties.Setter()
                                .withFallbackEnabled(true)
                                .withCircuitBreakerEnabled(true)
                )
        );


        this.orderService = orderService;
    }




    /**
     * Implement this method with code to be executed when {@link #observe()} or {@link #toObservable()} are invoked.
     *
     * @return R response type
     */
    @Override
    protected Observable<List<Map<String, Object>>> construct() {
        log.info("Hystrix Observable Command construct to Get Order List: [{}]", Thread.currentThread().getName());
        return Observable.create(subscriber->{
            //Observable有三个关键的事件方法，分别是onNext、onCompleted、onError
            try {
                if (!subscriber.isUnsubscribed()){
                    log.info("subscriber command task : [{}]",Thread.currentThread().getName());
                    //这里可以批量操作
                    //仅为演示
//                    for (int i = 0; i < 10; i++) {
//                        subscriber.onNext(orderService.list());
//                    }
                    subscriber.onNext(orderService.list());
                    subscriber.onCompleted();
                }
            } catch (Exception exception){
                subscriber.onError(exception);
            }
        });
    }

    /**
     * <h2> resumeWithFallback - 服务熔断后处理<h2>
     * version: 1.0 - 2022/3/6
     * @param
     * @return {@link Observable< List< Map< String, Object>>> }
     */
    @Override
    protected Observable<List<Map<String, Object>>> resumeWithFallback() {
        log.warn("trigger hystrix fallback: [{}]", Thread.currentThread().getName());
        return Observable.create(subscriber->{
            //Observable有三个关键的事件方法，分别是onNext、onCompleted、onError
            try {
                if (!subscriber.isUnsubscribed()){
                    log.info("[Fallback]subscriber command task : [{}]",Thread.currentThread().getName());
                    subscriber.onNext(Collections.emptyList());
                    subscriber.onCompleted();
                }
            } catch (Exception exception){
                subscriber.onError(exception);
            }
        });
    }
}
