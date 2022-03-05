package cn.flowboot.e.commerce.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <h1>配置登录请求转发规则</h1>
 * */
@Configuration
public class RouteLocatorConfig {

    /**
     * <h2>使用代码定义路由规则, 在网关层面拦截下登录和注册接口</h2>
     * */
    @Bean
    public RouteLocator loginRouteLocator(RouteLocatorBuilder builder) {

        // 手动定义 Gateway 路由规则需要指定 id、path 和 uri
        return builder.routes()
                .route(
                        "e_commerce_authority",
                        r -> r.path(
                                "/x/e-commerce/login",
                                "/x/e-commerce/register"
                                ).uri("http://localhost:9000/")
                ).build();
    }
}
