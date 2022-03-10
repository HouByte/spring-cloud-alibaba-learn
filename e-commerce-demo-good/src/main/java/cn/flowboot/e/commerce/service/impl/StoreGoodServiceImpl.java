package cn.flowboot.e.commerce.service.impl;

import cn.flowboot.e.commerce.entity.StoreGood;
import cn.flowboot.e.commerce.mapper.StoreGoodMapper;
import cn.flowboot.e.commerce.service.StoreGoodService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author by Vincent Vic
 * @since 2022-03-08
 */
@Service
public class StoreGoodServiceImpl extends ServiceImpl<StoreGoodMapper, StoreGood> implements StoreGoodService {

    /**
     * 通过订单减少库存
     *
     * @param goodId
     * @param deduction
     * @return
     */
    @Override
    public boolean deductStock(Integer goodId, Integer deduction) {
        StoreGood storeGood = getById(goodId);
        storeGood.setStock(storeGood.getStock() - deduction);
        storeGood.setUpdateTime(new Date());
        return updateById(storeGood);
    }
}
