package cn.flowboot.e.commerce.feign.fallback;

import cn.flowboot.e.commerce.dto.Good;
import cn.flowboot.e.commerce.dto.SearchGoodByIdsDto;
import cn.flowboot.e.commerce.feign.GoodFeignClient;
import cn.flowboot.e.commerce.vo.CommonResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

/**
 * <h1>后备回退</h1>
 *
 * @version 1.0
 * @author: Vincent Vic
 * @since: 2022/03/07
 */
@Slf4j
@Component
public class GoodFeignClientFallback implements GoodFeignClient {
    /**
     * <h2> list - feign 访问 <h2>
     * version: 1.0 - 2022/3/5
     *
     * @param searchGoodByIdsDto
     * @return {@link List< Good> }
     */
    @Override
    public CommonResponse list(SearchGoodByIdsDto searchGoodByIdsDto) {
        log.warn("feign list fail");
        return CommonResponse.fail();
    }

    @Override
    public List<Good> listByOrderNo(String orderNo) {
        log.warn("feign listByOrderNo fail");
        return Collections.emptyList();
    }

    /**
     * 减少商品库存
     *
     * @param goodId
     * @param deduction
     * @return
     */
    @Override
    public boolean deductStock(Integer goodId, Integer deduction) {
        return false;
    }
}
