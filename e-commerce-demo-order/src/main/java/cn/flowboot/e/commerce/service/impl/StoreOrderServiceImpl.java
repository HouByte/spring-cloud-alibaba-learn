package cn.flowboot.e.commerce.service.impl;

import cn.flowboot.e.commerce.entity.StoreOrder;
import cn.flowboot.e.commerce.feign.GoodFeignClient;
import cn.flowboot.e.commerce.mapper.StoreOrderMapper;
import cn.flowboot.e.commerce.service.StoreOrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author by Vincent Vic
 * @since 2022-03-08
 */
@RequiredArgsConstructor
@Service
public class StoreOrderServiceImpl extends ServiceImpl<StoreOrderMapper, StoreOrder> implements StoreOrderService {


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

    @Transactional
    @Override
    public void wrongRollbackFor() throws Exception {
        //数据上的操作
        save(getStoreOrder());
        throw new RuntimeException("发生异常,测试@Transactional");
    }

    @Override
    public void wrongInnweCall() throws Exception {
        wrongRollbackFor();
    }


    private final GoodFeignClient goodFeignClient;

    /**
     * 创建订单 减少库存
     * 仅为演示回滚，非实际业务
     * @param goodId
     * @return
     * @throws Exception
     */
    @Override
    @GlobalTransactional(rollbackFor = Exception.class)
    public StoreOrder createOrder(Integer goodId) throws Exception {


        //远程调用减少库存
        goodFeignClient.deductStock(goodId, 1);

        StoreOrder order = new StoreOrder();

        order.setOrderNo(UUID.randomUUID().toString().replace("-",""));
        //仅为演示否则此处需要先获取商品信息，这里演示回滚，非实际业务
        order.setPrice(new BigDecimal(100*goodId));
        order.setGoodId(goodId);
        order.setCreateTime(new Date());
        order.setUpdateTime(new Date());
        save(order);
       throw new Exception("error");
        //return order;
    }


}
