package cn.flowboot.e.commerce.service.impl;

import cn.flowboot.e.commerce.dto.Good;
import cn.flowboot.e.commerce.dto.SearchGoodByIdsDto;
import cn.flowboot.e.commerce.feign.GoodFeignClient;
import cn.flowboot.e.commerce.service.OrderService;
import cn.flowboot.e.commerce.vo.CommonResponse;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCollapser;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.Future;

/**
 * <h1></h1>
 *
 * @version 1.0
 * @author: Vincent Vic
 * @since: 2022/03/06
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class OrderServiceImpl implements OrderService {

    private final GoodFeignClient goodFeignClient;
    /**
     * 订单列表
     * 测试
     * @return
     */
    @Override
    public List<Map<String, Object>> list() {
        Map<String, Object> map = Maps.newHashMap();
        map.put("orderNo","1234567890");
        log.info("now time : {}", LocalDateTime.now());
        throw  new RuntimeException("error");
//        try {
//            Thread.sleep(3000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//
//        log.info("now time : {}", LocalDateTime.now());
//        return Lists.newArrayList(map);
    }

    /**
     * 订单详情
     *
     * @param orderNo
     * @return
     */
    @Override
    public Map<String, Object> desc(String orderNo) {
        Map<String, Object> map = Maps.newHashMap();
        map.put("orderNo",orderNo);
        map.put("price",1000);
        map.put("desc","商品_"+orderNo);
        return map;
    }

    /**
     * 订单详情 通过订单号列表 - 请求商品服务
     *
     * @param orderNos
     * @return
     */
    @Override
    public List<List<Good>> descs(List<String> orderNos) {
        log.info("request order to get good infos: {}", JSON.toJSONString(orderNos));
        List<List<Good>> result = new ArrayList<>();

        orderNos.forEach(o ->{
            List<Good> goods = goodFeignClient.listByOrderNo(o);
            if (goods != null){
                result.add(goods);
            }
        } );
        log.info("descs return result {}",JSON.toJSONString(result));
        return result;
    }

    /**
     * 订单商品 - 单个重新使用注解合并请求
     *
     * @param orderNo
     * @return
     */
    @Override
    @HystrixCollapser(
            batchMethod = "descGoods",
            scope = com.netflix.hystrix.HystrixCollapser.Scope.GLOBAL,
            collapserProperties = {
                    @HystrixProperty(name = "timerDelayInMilliseconds",value = "300")
            }
    )
    public Future<List<Good>> descGood(String orderNo) {
        throw new RuntimeException("This method body should not be executed!");
    }

    /**
     * 订单商品 - 合并请求
     *
     * @param orderNos
     * @return
     */
    @Override
    @HystrixCommand
    public List<List<Good>> descGoods(List<String> orderNos) {
        return descs(orderNos);
    }


}
