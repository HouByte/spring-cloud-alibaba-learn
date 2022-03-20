package cn.flowboot.e.commerce.controller;

import cn.flowboot.e.commerce.block.handler.MyBlockHandler;
import cn.flowboot.e.commerce.vo.CommonResponse;
import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

/**
 * <h1>流控规则硬编码</h1>
 *
 * @version 1.0
 * @author: Vincent Vic
 * @since: 2022/03/17
 */
@Slf4j
@RestController
@RequestMapping("/code")
public class FlowRuleCodeController {

    /**
     * 初始化流控规则
     */
    @PostConstruct
    public void init(){
        //流控规则集合
        List<FlowRule> flowRules = new ArrayList<>();
        //创建流控规则
        FlowRule flowRule = new FlowRule();
        //设置流控规则QPS，限流阈值类型CQPS,并发线程数
        flowRule.setGrade(RuleConstant.FLOW_GRADE_QPS);
        //流量控制手段
        flowRule.setControlBehavior(RuleConstant.CONTROL_BEHAVIOR_DEFAULT);
        //设置受保护的资源
        flowRule.setResource("flowRuleCode");
        //设置受保护的资源的阈值
        flowRule.setCount(1);
        flowRules.add(flowRule);

        //加载配置好的规则
        FlowRuleManager.loadRules(flowRules);
    }

    /**
     * <h1>采用硬编码的限流规则的Controller方法</h1>
     * @return
     */
    @GetMapping("/flow-rule")
    //@SentinelResource(value = "flowRuleCode")
    //@SentinelResource(value = "flowRuleCode",blockHandler = "handleException")
    @SentinelResource(value = "flowRuleCode",blockHandler = "myHandleException",blockHandlerClass = MyBlockHandler.class)
    public CommonResponse<String> flowRuleCode(){
        log.info("request flowRuleCode");
        return CommonResponse.success();
    }

    /**
     * <h2>当限流异常抛出时,指定调用的方法</h2>
     * 是一个兜底策略
     */
    public CommonResponse handleException(BlockException e){
        log.error("has block exception : [{}]", JSONObject.toJSONString(e.getRule()));
        return CommonResponse.fail("flow rule exception",e.getClass().getCanonicalName());
    }

}
