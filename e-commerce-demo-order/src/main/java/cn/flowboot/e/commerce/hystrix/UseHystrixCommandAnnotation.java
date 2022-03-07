package cn.flowboot.e.commerce.hystrix;

import cn.flowboot.e.commerce.service.OrderService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * <h1> 使用 HystrixCommand 注解 </h1>
 *
 * @version 1.0
 * @author: Vincent Vic
 * @since: 2022/03/06
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UseHystrixCommandAnnotation {

    private final OrderService orderService;


    @HystrixCommand(
            // 用于对 Hystrix 命令进行分组, 分组之后便于统计展示于仪表盘、上传报告和预警等等
            // 内部进行度量统计时候的分组标识, 数据上报和统计的最小维度就是 groupKey
            groupKey = "OrderService",
            // HystrixCommand 的名字, 默认是当前类的名字, 主要方便 Hystrix 进行监控、报警等
            commandKey = "OrderService",
            // 舱壁模式
            threadPoolKey = "OrderService",
            // 后备模式
            fallbackMethod = "listFallback",
            // 断路器模式
            commandProperties = {
                    // 超时时间, 单位毫秒, 超时进 fallback
                    @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "1500"),
                    // 判断熔断的最少请求数, 默认是10; 只有在一定时间内请求数量达到该值, 才会进行成功率的计算
                    @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "10"),
                    // 熔断的阈值默认值 50, 表示在一定时间内有50%的请求处理失败, 会触发熔断
                    @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "10"),
            },
            // 舱壁模式
            threadPoolProperties = {
                    @HystrixProperty(name = "coreSize", value = "30"),
                    @HystrixProperty(name = "maxQueueSize", value = "101"),
                    @HystrixProperty(name = "keepAliveTimeMinutes", value = "2"),
                    @HystrixProperty(name = "queueSizeRejectionThreshold", value = "15"),
                    // 在时间窗口中, 收集统计信息的次数; 在 1440ms 的窗口中一共统计 12 次
                    @HystrixProperty(name = "metrics.rollingStats.numBuckets", value = "12"),
                    // 时间窗口, 从监听到第一次失败开始计时
                    @HystrixProperty(name = "metrics.rollingStats.timeInMilliseconds", value = "1440")
            }
    )
    public List<Map<String,Object>> list() {

        log.info("use hystrix command annotation to order list: [{}]",
                Thread.currentThread().getName());
        return orderService.list();
    }

    /**
     * <h2>getNacosClientInfo 的兜底策略 - Hystrix 后备模式</h2>
     * */
    public List<Map<String,Object>> listFallback() {

        log.warn("trigger hystrix fallback: [{}]",
                Thread.currentThread().getName());
        return Collections.emptyList();
    }
}
