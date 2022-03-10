package cn.flowboot.e.commerce.service;

import cn.flowboot.e.commerce.entity.StoreOrder;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author by Vincent Vic
 * @since 2022-03-08
 */
public interface StoreOrderService extends IService<StoreOrder> {

    void wrongRollbackFor() throws Exception;

    void wrongInnweCall() throws Exception;

    /**
     * 创建订单 减少库存
     * @param goodId
     * @return
     * @throws Exception
     */
    StoreOrder createOrder(Integer goodId) throws Exception;
}
