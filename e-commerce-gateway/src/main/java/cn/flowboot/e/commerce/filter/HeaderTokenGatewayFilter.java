package cn.flowboot.e.commerce.filter;

import org.apache.commons.lang3.StringUtils;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * <h1>Http携带Token验证过滤器</h1>
 *
 * @version 1.0
 * @author: Vincent Vic
 * @since: 2022/03/04
 */
public class HeaderTokenGatewayFilter implements GatewayFilter, Ordered {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        //从Http Header 中寻找 key为 token,value的键值对
        String value = exchange.getRequest().getHeaders().getFirst("token");

        //这里仅仅判断token是否存在
        if (StringUtils.isNotBlank(value)) {
            return chain.filter(exchange);
        }

        //不存在标记没有权限拦截
        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
        return exchange.getResponse().setComplete();
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
