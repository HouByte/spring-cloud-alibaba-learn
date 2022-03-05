package cn.flowboot.e.commerce.feign;

import cn.flowboot.e.commerce.dto.Good;
import cn.flowboot.e.commerce.dto.SearchGoodByIdsDto;
import cn.flowboot.e.commerce.vo.CommonResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * <h1>与Good 服务通信的feign Client接口定义</h1>
 * contextId 对定义同一个服务的区分
 * @version 1.0
 * @author: Vincent Vic
 * @since: 2022/03/05
 */
@FeignClient(contextId = "GoodFeignClient",value = "e-commerce-demo-good")
public interface GoodFeignClient {

    /**
     * <h2> list - feign 访问 <h2>
     * version: 1.0 - 2022/3/5
     * @param searchGoodByIdsDto
     * @return {@link List< Good> }
     */
    @RequestMapping(value = "/demo-good/good/search/list",method = RequestMethod.POST,consumes = "application/json",produces = "application/json")
    CommonResponse list(SearchGoodByIdsDto searchGoodByIdsDto);
}
