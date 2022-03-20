package cn.flowboot.e.commerce.block.handler;

import cn.flowboot.e.commerce.vo.CommonResponse;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;

/**
 * <h1>自定义通用处理逻辑</h1>
 *
 * @version 1.0
 * @author: Vincent Vic
 * @since: 2022/03/17
 */
@Slf4j
public class MyBlockHandler {

    /**
     * <h2>通用限流处理方法</h2>
     * 这个方法必须是static的
     **/
    public static CommonResponse<String> myHandleException(BlockException e){
        log.error("has block exception : [{}], [{}]", JSONObject.toJSONString(e.getRule()),e.getRuleLimitApp());
        return CommonResponse.fail("trigger flow rule exception",e.getClass().getCanonicalName());
    }

}
