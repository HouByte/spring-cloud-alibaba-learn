package cn.flowboot.e.commerce.controller;


import cn.flowboot.e.commerce.entity.StoreOrder;
import cn.flowboot.e.commerce.service.StoreOrderService;
import cn.flowboot.e.commerce.vo.CommonResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author by Vincent Vic
 * @since 2022-03-08
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/store-order")
public class StoreOrderController {

    private final StoreOrderService storeOrderService;

    @GetMapping("wrongRollbackFor")
    public CommonResponse wrongRollbackFor(){
        try {
            storeOrderService.wrongRollbackFor();
        } catch (Exception e) {
            return CommonResponse.fail("失败");
        }
        return CommonResponse.success();
    }

    @GetMapping("wrongInnweCall")
    public CommonResponse wrongInnweCall(){
        try {
            storeOrderService.wrongInnweCall();
        } catch (Exception e) {
            return CommonResponse.fail("失败");
        }
        return CommonResponse.success();
    }

    @GetMapping("createOrder")
    public StoreOrder createOrder(@RequestParam(defaultValue = "1") Integer goodId){
        try {
            log.info("create order by good id : {}",goodId);
            return storeOrderService.createOrder(goodId);
        } catch (Exception e) {
            log.error("create order fail ",e);
            //throw new RuntimeException("失败");
            return null;
        }

    }
}
