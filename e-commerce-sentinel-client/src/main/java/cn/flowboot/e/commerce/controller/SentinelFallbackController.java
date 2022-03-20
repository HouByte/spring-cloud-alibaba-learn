package cn.flowboot.e.commerce.controller;

import cn.flowboot.e.commerce.conf.RestTemplateExceptionHandler;
import cn.flowboot.e.commerce.fallback.MyFallbackHandler;
import cn.flowboot.e.commerce.vo.CommonResponse;
import com.alibaba.csp.sentinel.annotation.SentinelResource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

/**
 * <h1>使用Sentinel保护RestTemplate服务调用</h1>
 *
 * @version 1.0
 * @author: Vincent Vic
 * @since: 2022/03/18
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/fallback")
public class SentinelFallbackController {

    //注入普通
    private final RestTemplate restTemplate;

    /**
     * <h2> remoteConsumer - <h2>
     * 容错降级:对于服务不可用时不能生效
     * version: 1.0 - 2022/3/18
     * @return {@link Map< String, Object> }
     */
    @GetMapping("/remote/consumer")
    @SentinelResource(
            value = "remoteConsumerFallback",
            fallback = "remoteConsumerFallback",
            fallbackClass = { MyFallbackHandler.class }
    )

    public CommonResponse remoteConsumer(){


        String requestUrL = "http://localhost:8500/sentinel-client/rest/remote/producer";
        log.info("RestTemplate request url [{}] ",requestUrL);
        return restTemplate.getForObject(requestUrL, CommonResponse.class);
    }

    /**
     * <h2>让 Sentinel 忽略一些异常</h2>
     * */
    @GetMapping("/ignore-exception")
    @SentinelResource(
            value = "ignoreException",
            fallback = "ignoreExceptionFallback",
            fallbackClass = { MyFallbackHandler.class },
            exceptionsToIgnore = { NullPointerException.class }
    )
    public CommonResponse ignoreException(@RequestParam(defaultValue = "1") Integer code) {

        if (code % 2 == 0) {
            throw new NullPointerException("yout input code is: " + code);
        } else if ( code % 3 == 0){
            throw new RuntimeException("yout input code is: " + code);
        }

        return CommonResponse.success();
    }

}
