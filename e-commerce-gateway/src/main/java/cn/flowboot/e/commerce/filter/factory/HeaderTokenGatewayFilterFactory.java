package cn.flowboot.e.commerce.filter.factory;

import cn.flowboot.e.commerce.filter.HeaderTokenGatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Component;

/**
 * <h1>token 局部过滤器 </h1>
 * <h2>Gateway 过滤器命名规则: 功能名称 + GatewayFilterFactory  </h2>
 * GatewayFilterFactory 是必须为类名称结尾
 * @version 1.0
 * @author: Vincent Vic
 * @since: 2022/03/04
 */
@Component
public class HeaderTokenGatewayFilterFactory extends AbstractGatewayFilterFactory<Object> {
    @Override
    public GatewayFilter apply(Object config) {
        return new HeaderTokenGatewayFilter();
    }
}
