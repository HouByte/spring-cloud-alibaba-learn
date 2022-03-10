package cn.flowboot.e.commerce.service;

import cn.flowboot.e.commerce.entity.StoreGood;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author by Vincent Vic
 * @since 2022-03-08
 */
public interface StoreGoodService extends IService<StoreGood> {

    /**
     * 通过订单减少库存
     * @param goodId
     * @param deduction
     * @return
     */
    boolean deductStock(Integer goodId,Integer deduction);
}
