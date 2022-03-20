package cn.flowboot.e.commerce.controller;

import cn.flowboot.e.commerce.fallback.MyFallbackHandler;
import cn.flowboot.e.commerce.feign.SentinelFeignClient;
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
@RequestMapping("/feign")
public class SentinelFeignController {

    //注入普通
    private final SentinelFeignClient sentinelFeignClient;

    /**
     * <h2> remoteConsumer - <h2>
     * 容错降级:对于服务不可用时不能生效
     * version: 1.0 - 2022/3/18
     * @return {@link Map< String, Object> }
     */
    @GetMapping("/remote/consumer")
    public CommonResponse remoteConsumer(){
        log.info("Sentinel feign client request ");
        return sentinelFeignClient.producer();
    }

}
