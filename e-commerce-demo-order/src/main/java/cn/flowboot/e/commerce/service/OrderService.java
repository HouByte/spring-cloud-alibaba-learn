package cn.flowboot.e.commerce.service;

import cn.flowboot.e.commerce.dto.Good;

import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;

/**
 * <h1></h1>
 *
 * @version 1.0
 * @author: Vincent Vic
 * @since: 2022/03/06
 */
public interface OrderService {

    /**
     * 订单列表
     * @return
     */
    List<Map<String,Object>> list();

    /**
     * 订单详情
     * @return
     */
    Map<String,Object> desc(String orderNo);

    /**
     * 订单详情 通过订单号列表 - 请求商品服务
     * @return
     */
    List<List<Good>> descs(List<String> orderNos);


    /**
     * 订单商品 - 单个重新使用注解合并请求
     * @return
     */
    Future<List<Good>> descGood(String orderNo);

    /**
     * 订单商品 - 合并请求
     * @return
     */
    List<List<Good>> descGoods(List<String> orderNos);
}
