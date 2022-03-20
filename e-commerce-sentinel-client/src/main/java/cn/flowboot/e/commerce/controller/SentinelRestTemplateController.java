package cn.flowboot.e.commerce.controller;

import cn.flowboot.e.commerce.block.handler.MyBlockHandler;
import cn.flowboot.e.commerce.dto.SearchGoodByIdsDto;
import cn.flowboot.e.commerce.vo.CommonResponse;
import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.fastjson.JSON;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
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
@RequestMapping("/rest")
public class SentinelRestTemplateController {

    private final RestTemplate restTemplate;

    /**
     * <h2> remoteConsumer - <h2>
     * 容错降级:对于服务不可用时不能生效
     * version: 1.0 - 2022/3/18
     * @return {@link Map< String, Object> }
     */
    @GetMapping("/remote/consumer")
    public CommonResponse remoteConsumer(){


        String requestUrL = "http://localhost:8500/sentinel-client/rest/remote/producer";
        log.info("RestTemplate request url [{}] ",requestUrL);
        return restTemplate.getForObject(requestUrL, CommonResponse.class);
    }

    /**
     * <h2>在 dashboard 中“流控规则”中按照资源名称新增流控规则</h2>
     * @return
     */
    @GetMapping("/remote/producer")
    public CommonResponse<String> producer(){
        log.info("coming in rate limit controller by resource");
        return CommonResponse.success("producer");
    }
}
