package cn.flowboot.e.commerce.controller;


import cn.flowboot.e.commerce.annotation.IgnoreResponseAdvice;
import cn.flowboot.e.commerce.entity.StoreGood;
import cn.flowboot.e.commerce.service.StoreGoodService;
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
@RequestMapping("/store-good")
public class StoreGoodController {

    private final StoreGoodService storeGoodService;

    /**
     * 创建订单
     * @param goodId
     * @param deduction
     * @return
     */
    @IgnoreResponseAdvice
    @GetMapping("/deductStock")
    public boolean deductStock(@RequestParam("goodId") Integer goodId, @RequestParam("deduction") Integer deduction){
        log.info("deduct stock by goodId: {} deduction numbers:{}",goodId,deduction);
        return storeGoodService.deductStock(goodId, deduction);
    }

}
