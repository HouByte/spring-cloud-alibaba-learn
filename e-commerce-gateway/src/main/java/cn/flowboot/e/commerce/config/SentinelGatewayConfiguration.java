package cn.flowboot.e.commerce.config;



import com.alibaba.csp.sentinel.adapter.gateway.common.SentinelGatewayConstants;
import com.alibaba.csp.sentinel.adapter.gateway.common.api.ApiDefinition;
import com.alibaba.csp.sentinel.adapter.gateway.common.api.ApiPathPredicateItem;
import com.alibaba.csp.sentinel.adapter.gateway.common.api.ApiPredicateItem;
import com.alibaba.csp.sentinel.adapter.gateway.common.api.GatewayApiDefinitionManager;
import com.alibaba.csp.sentinel.adapter.gateway.common.rule.GatewayFlowRule;
import com.alibaba.csp.sentinel.adapter.gateway.common.rule.GatewayRuleManager;
import com.alibaba.csp.sentinel.adapter.gateway.sc.SentinelGatewayFilter;
import com.alibaba.csp.sentinel.adapter.gateway.sc.callback.BlockRequestHandler;
import com.alibaba.csp.sentinel.adapter.gateway.sc.callback.GatewayCallbackManager;
import com.alibaba.csp.sentinel.adapter.gateway.sc.exception.SentinelGatewayBlockExceptionHandler;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.reactive.result.view.ViewResolver;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;
import java.util.*;


/**
 * <h1>Gateway 集成 Sentinel 实现限流</h1>
 *
 * @version 1.0
 * @author: Vincent Vic
 * @since: 2022/03/18
 */
@Slf4j
@RequiredArgsConstructor
@Configuration
public class SentinelGatewayConfiguration {

    /** 视图解析器 */
    private final List<ViewResolver> viewResolvers;
    /** HTTP 请求和响应数据的编解码配置 */
    private final ServerCodecConfigurer serverCodecConfigurer;


    /**
     * <h2>限流异常处理器, 限流异常出现时, 执行到这个 handler</h2>
     * */
    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public SentinelGatewayBlockExceptionHandler sentinelGatewayBlockExceptionHandler() {
        // 默认会返回错误 message, code 429
        return new SentinelGatewayBlockExceptionHandler(
                this.viewResolvers,
                this.serverCodecConfigurer
        );
    }

    /**
     * <h2>限流过滤器, 是 Gateway 全局过滤器, 优先级定义为最高</h2>
     * */
    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public GlobalFilter sentinelGatewayFilter() {
        return new SentinelGatewayFilter();
    }

    /**
     * <h2>初始化限流规则</h2>
     * */
    //@PostConstruct
    public void doInit() {

        log.info("---------------------------------------------------");

        // 加载网关限流规则
        log.info("load sentinel gateway rules (code define)");
        initGatewayRules();

        // 加载自定义限流异常处理器
        initBlockHandler();

        log.info("---------------------------------------------------");
    }

    /**
     * <h2>硬编码网关限流规则</h2>
     * */
    private void initGatewayRules() {

        Set<GatewayFlowRule> rules = new HashSet<>();

//        GatewayFlowRule rule = new GatewayFlowRule();
//        // 指定限流模式, 根据 route_id 做限流, 默认的模式
//        rule.setResourceMode(SentinelGatewayConstants.RESOURCE_MODE_ROUTE_ID);
//        // 指定 route_id -> service id
//        rule.setResource("e-commerce-nacos-client");
//        // 按照 QPS 限流
//        rule.setGrade(RuleConstant.FLOW_GRADE_QPS);
//        // 统计窗口和限流阈值
//        rule.setIntervalSec(3);
//        rule.setCount(1);
//        rules.add(rule);

        // 限流分组, Sentinel 先去找规则定义, 再去找规则中定义的分组
        rules.add(
                new GatewayFlowRule("nacos-client-api-1")
                        .setCount(3).setIntervalSec(60)
        );
        rules.add(
                new GatewayFlowRule("nacos-client-api-2")
                        .setCount(1).setIntervalSec(60)
        );

        // 加载到网关中
        GatewayRuleManager.loadRules(rules);

        // 加载限流分组
        initCustomizedApis();
    }

    /**
     * <h2>自定义限流异常处理器</h2>
     * */
    private void initBlockHandler() {

        // 自定义 BlockRequestHandler
        BlockRequestHandler blockRequestHandler = new BlockRequestHandler() {
            @Override
            public Mono<ServerResponse> handleRequest(ServerWebExchange serverWebExchange,
                                                      Throwable throwable) {

                log.error("------------- trigger gateway sentinel rule -------------");

                Map<String, String> result = new HashMap<>();
                result.put("code", String.valueOf(HttpStatus.TOO_MANY_REQUESTS.value()));
                result.put("message", HttpStatus.TOO_MANY_REQUESTS.getReasonPhrase());
                result.put("route", "e-commerce-nacos-client");

                return ServerResponse
                        .status(HttpStatus.TOO_MANY_REQUESTS)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromValue(result));
            }
        };

        // 设置自定义限流异常处理器
        GatewayCallbackManager.setBlockHandler(blockRequestHandler);
    }

    /**
     * <h2>硬编码网关限流分组</h2>
     * 1. 最大限制 - 演示
     * 2. 具体的分组
     * */
    private void initCustomizedApis() {

        Set<ApiDefinition> definitions = new HashSet<>();

        // nacos-client-api 组, 最大的限制
        ApiDefinition api = new ApiDefinition("nacos-client-api")
                .setPredicateItems(new HashSet<ApiPredicateItem>() {{
                    // 模糊匹配 /ecommerce-nacos-client/ 及其子路径的所有请求
                    add(new ApiPathPredicateItem()
                            .setPattern("/ecommerce-nacos-client/**")
                            // 根据前缀匹配
                            .setMatchStrategy(SentinelGatewayConstants.URL_MATCH_STRATEGY_PREFIX));
                }});

        // nacos-client-api-1 分组
        ApiDefinition api1 = new ApiDefinition("nacos-client-api-1")
                .setPredicateItems(new HashSet<ApiPredicateItem>() {{
                    add(new ApiPathPredicateItem()
                            // 精确匹配 /n/service/instance/e-commerce-nacos-client
                            .setPattern("/s/nacos-client/search/service/instance"));
                }});

        // nacos-client-api-2 分组
        ApiDefinition api2 = new ApiDefinition("nacos-client-api-2")
                .setPredicateItems(new HashSet<ApiPredicateItem>() {{
                    add(new ApiPathPredicateItem()
                            // 精确匹配 /imooc/ecommerce-nacos-client/nacos-client/project-config
                            .setPattern("/ecommerce-nacos-client" +
                                    "/nacos-client/project-config"));
                }});

        definitions.add(api1);
        definitions.add(api2);

        // 加载限流分组
        GatewayApiDefinitionManager.loadApiDefinitions(definitions);
    }
}
