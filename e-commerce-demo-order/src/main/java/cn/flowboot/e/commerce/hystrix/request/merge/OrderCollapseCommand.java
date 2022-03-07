package cn.flowboot.e.commerce.hystrix.request.merge;

import cn.flowboot.e.commerce.dto.Good;
import cn.flowboot.e.commerce.service.OrderService;
import com.netflix.hystrix.HystrixCollapser;
import com.netflix.hystrix.HystrixCollapserKey;
import com.netflix.hystrix.HystrixCollapserProperties;
import com.netflix.hystrix.HystrixCommand;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <h1>请求合并器</h1>
 *
 * @version 1.0
 * @author: Vincent Vic
 * @since: 2022/03/07
 */
public class OrderCollapseCommand extends HystrixCollapser<List<List<Good>>,List<Good>,String> {

    private final OrderService orderService;
    private final String orderNo;

    public OrderCollapseCommand(OrderService orderService, String orderNo) {
        super(
                HystrixCollapser.Setter.withCollapserKey(
                        HystrixCollapserKey.Factory.asKey("OrderCollapseCommand")
                ).andCollapserPropertiesDefaults(
                        HystrixCollapserProperties.Setter().withTimerDelayInMilliseconds(300)
                )
        );
        this.orderService = orderService;
        this.orderNo = orderNo;
    }

    /**
     * <h2> getRequestArgument - 获取请求中的参数<h2>
     * version: 1.0 - 2022/3/7
     * @param
     * @return {@link String }
     */
    @Override
    public String getRequestArgument() {
        return this.orderNo;
    }


    /**
     * <h2> createCommand - 创建批量请求<h2>
     * version: 1.0 - 2022/3/7
     * @param collapsedRequests
     * @return {@link HystrixCommand< List< List< Good>>> }
     */
    @Override
    protected HystrixCommand<List<List<Good>>> createCommand(Collection<CollapsedRequest<List<Good>, String>> collapsedRequests) {
        List<String> orderNos = new ArrayList<>(collapsedRequests.size());
        orderNos.addAll(
                collapsedRequests.stream()
                        .map(CollapsedRequest::getArgument)
                        .collect(Collectors.toList())
        );
        return new OrderBatchCommand(orderService,orderNos);
    }


    /**
     * <h2> mapResponseToRequests - 响应分发<h2>
     * version: 1.0 - 2022/3/7
     * @param batchResponse
     * @param collapsedRequests
     */
    @Override
    protected void mapResponseToRequests(List<List<Good>> batchResponse, Collection<CollapsedRequest<List<Good>, String>> collapsedRequests) {
        int count = 0;

        for (CollapsedRequest<List<Good>, String> collapsedRequest : collapsedRequests) {
            //从批量响应集合中按顺序取出结果
            List<Good> goods = batchResponse.get(count++);
            //将结果返回原 Response中
            collapsedRequest.setResponse(goods);

        }
    }
}
