package cn.flowboot.e.commerce.conf;

import com.alibaba.cloud.sentinel.annotation.SentinelRestTemplate;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * <h1>开启服务间的调用保护,需要给RestTemplate做一些包装</h1>
 *
 * @version 1.0
 * @author: Vincent Vic
 * @since: 2022/03/18
 */
@Configuration
public class SentinelConfig {

    /**
     * <h2> restTemplate - 包装RestTemplate<h2>
     * version: 1.0 - 2022/3/18
     * @param
     * @return {@link RestTemplate }
     */
    @Bean
//    @SentinelRestTemplate(
//            fallback = "handleFallback",fallbackClass = RestTemplateExceptionHandler.class,
//            blockHandler = "handleBlock",blockHandlerClass = RestTemplateExceptionHandler.class
//    )
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
}
