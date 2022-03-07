package cn.flowboot.e.commerce.hystrix.request.merge;

import cn.flowboot.e.commerce.dto.Good;
import cn.flowboot.e.commerce.service.OrderService;
import com.alibaba.fastjson.JSON;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import lombok.extern.slf4j.Slf4j;

import java.util.Collections;
import java.util.List;

/**
 * <h1>批量请求</h1>
 *
 * @version 1.0
 * @author: Vincent Vic
 * @since: 2022/03/07
 */

@Slf4j
public class OrderBatchCommand extends HystrixCommand<List<List<Good>>> {

    private final OrderService orderService;
    private final List<String> orderNos;

    public OrderBatchCommand(OrderService orderService, List<String> orderNos) {
        super(
                HystrixCommand.Setter.withGroupKey(
                        HystrixCommandGroupKey.Factory.asKey("OrderBatchCommand")
                )
        );
        this.orderService = orderService;
        this.orderNos = orderNos;
    }

    /**
     * Implement this method with code to be executed when {@link #execute()} or {@link #queue()} are invoked.
     *
     * @return R response type
     * @throws Exception if command execution fails
     */
    @Override
    protected List<List<Good>> run() throws Exception {
        log.info("user batch command to get result {}", JSON.toJSONString(orderNos));

        return orderService.descs(orderNos);
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
    protected List<List<Good>> getFallback() {
        log.warn("user batch command to get result failure");
        return Collections.emptyList();
    }
}
