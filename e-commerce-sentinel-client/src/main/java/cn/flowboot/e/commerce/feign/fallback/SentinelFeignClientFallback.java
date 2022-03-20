package cn.flowboot.e.commerce.feign.fallback;

import cn.flowboot.e.commerce.feign.SentinelFeignClient;
import cn.flowboot.e.commerce.vo.CommonResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * <h1>Sentinel 对 OpenFeign 接口的降级策略</h1>
 *
 * @version 1.0
 * @author: Vincent Vic
 * @since: 2022/03/18
 */
@Slf4j
@Component
public class SentinelFeignClientFallback implements SentinelFeignClient {

    /**
     * <h2>在 dashboard 中“流控规则”中按照资源名称新增流控规则</h2>
     *
     * @return
     */
    @Override
    public CommonResponse<String> producer() {
        return CommonResponse.fail("服务错误");
    }
}
