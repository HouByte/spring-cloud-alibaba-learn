package cn.flowboot.e.commerce.service.impl;

import cn.flowboot.e.commerce.entity.StoreOrder;
import cn.flowboot.e.commerce.service.StoreOrderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;


import static org.junit.jupiter.api.Assertions.*;

/**
 * <h1></h1>
 *
 * @version 1.0
 * @author: Vincent Vic
 * @since: 2022/03/08
 */
@SpringBootTest
public class StoreOrderServiceImplTest {

    @Autowired
    private StoreOrderService storeOrderService;

    /**
     * <h2> testTransactional01 - 测试事务时会回滚事务 - 执行后会回滚<h2>
     * version: 1.0 - 2022/3/8
     */
    @Transactional
    @Test
    public void testTransactional01(){
        StoreOrder order = getStoreOrder();
        storeOrderService.save(order);
    }

    /**
     * <h2> getStoreOrder - 获取模拟数据<h2>
     * version: 1.0 - 2022/3/8
     * @return {@link StoreOrder }
     */
    private StoreOrder getStoreOrder() {
        StoreOrder order = new StoreOrder();
        order.setOrderNo("1234556");
        order.setGoodId(1);
        order.setPrice(new BigDecimal("120"));

        order.setCreateTime(new Date());
        order.setUpdateTime(new Date());
        return order;
    }

    /**
     * <h2> testTransactional02 - 测试事务时会回滚事务 - 指定不回滚 - 即使发生异常也不会回滚<h2>
     * version: 1.0 - 2022/3/8
     */
    @Rollback(value = false)
    @Transactional
    @Test
    public void testTransactional02(){
        StoreOrder order = getStoreOrder();
        storeOrderService.save(order);
        throw new RuntimeException("error");
    }

}
