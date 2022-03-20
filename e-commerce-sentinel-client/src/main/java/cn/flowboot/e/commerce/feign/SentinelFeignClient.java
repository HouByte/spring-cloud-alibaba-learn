package cn.flowboot.e.commerce.feign;

import cn.flowboot.e.commerce.feign.fallback.SentinelFeignClientFallback;
import cn.flowboot.e.commerce.vo.CommonResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * <h1>通过 Sentinel 对 OpenFeign 实现熔断降级</h1>
 *
 * @version 1.0
 * @author: Vincent Vic
 * @since: 2022/03/18
 */
@FeignClient(
        value = "e-commerce-sentinel-client-1",
        fallback = SentinelFeignClientFallback.class
)
public interface SentinelFeignClient {

    /**
     * <h2>在 dashboard 中“流控规则”中按照资源名称新增流控规则</h2>
     * @return
     */
    @GetMapping("/sentinel-client/rest/remote/producer")
    CommonResponse<String> producer();
}
