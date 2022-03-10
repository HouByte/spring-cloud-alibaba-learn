package cn.flowboot.e.commerce.feign.fallback;

import cn.flowboot.e.commerce.dto.Good;
import cn.flowboot.e.commerce.dto.SearchGoodByIdsDto;
import cn.flowboot.e.commerce.feign.GoodFeignClient;
import cn.flowboot.e.commerce.vo.CommonResponse;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

/**
 * <h1>工厂模式</h1>
 *
 * @version 1.0
 * @author: Vincent Vic
 * @since: 2022/03/07
 */
@Slf4j
@Component
public class GoodFeignClientFallbackFactory implements FallbackFactory<GoodFeignClient> {
    @Override
    public GoodFeignClient create(Throwable throwable) {
        log.info("good feign client get list by feign request (FallbackFactory) [{}]",throwable.getMessage());

        return new GoodFeignClient() {
            @Override
            public CommonResponse list(SearchGoodByIdsDto searchGoodByIdsDto) {
                return CommonResponse.fail(throwable.getMessage());
            }

            @Override
            public List<Good> listByOrderNo(String orderNo) {
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
        };
    }
}
