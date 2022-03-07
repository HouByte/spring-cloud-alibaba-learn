package cn.flowboot.e.commerce.controller;

import cn.flowboot.e.commerce.dto.Good;
import cn.flowboot.e.commerce.dto.SearchGoodByIdsDto;
import cn.flowboot.e.commerce.feign.GoodFeignClient;
import cn.flowboot.e.commerce.hystrix.*;
import cn.flowboot.e.commerce.hystrix.request.merge.OrderCollapseCommand;
import cn.flowboot.e.commerce.service.OrderService;
import cn.flowboot.e.commerce.vo.CommonResponse;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import rx.Observable;
import rx.Observer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * <h1></h1>
 *
 * @version 1.0
 * @author: Vincent Vic
 * @since: 2022/03/03
 */
@Slf4j
@RestController
@RequestMapping("order")
public class OrderController {

    @GetMapping("info")
    public Map<String,Object> info(){
        Map<String,Object> map = new HashMap<>();
        map.put("info","good");
        return map;
    }


    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/create/order")
    public Map<String,Object> createOrder(@RequestParam(defaultValue = "1,2,3") List<Integer> ids){


        //第一种方式:写死url
        //String requestUrL = "http://127.0.0.1:8800/demo-good/good/search/list";
        String requestUrL = "http://e-commerce-demo-good/demo-good/good/search/list";

        log.info("RestTemplate request url [{}] and body [{}]:",requestUrL,ids);

        SearchGoodByIdsDto searchGoodByIdsDto = new SearchGoodByIdsDto();
        searchGoodByIdsDto.setIds(ids);


        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        CommonResponse response = restTemplate.postForObject(requestUrL, new HttpEntity<>(JSON.toJSONString(searchGoodByIdsDto), headers), CommonResponse.class);
        if (!response.isSuccess()){
            throw new RuntimeException("fallback");
        }
        Map<String,Object> map = new HashMap<>();
        map.put("result",response.getData());
        return map;
    }

    @Autowired
    private GoodFeignClient goodFeignClient;

    @GetMapping("/feign/create/order")
    public Map<String,Object> createOrderByFeign(@RequestParam(defaultValue = "1,2,3") List<Integer> ids){

        log.info("GoodFeignClient request  body [{}]:",ids);

        SearchGoodByIdsDto searchGoodByIdsDto = new SearchGoodByIdsDto();
        searchGoodByIdsDto.setIds(ids);

        CommonResponse response = goodFeignClient.list(searchGoodByIdsDto);
        if (!response.isSuccess()){
            throw new RuntimeException("fallback");
        }
        Map<String,Object> map = new HashMap<>();
        map.put("result",response.getData() );
        return map;
    }

    @Autowired
    private UseHystrixCommandAnnotation useHystrixCommandAnnotation;

    @GetMapping("/list")
    public List<Map<String,Object>> list(){
        log.info("Hystrix Command test");
        return useHystrixCommandAnnotation.list();
    }
    @Autowired
    private OrderHystrixCommand orderHystrixCommand;

    /**
     * <h2> executeList - 测试编程方式服务熔断<h2>
     * version: 1.0 - 2022/3/6
     * @return {@link List< Map< String, Object>> }
     */
    @GetMapping("/execute/list")
    public List<Map<String,Object>> executeList(){
        log.info("Hystrix Command test");
        return orderHystrixCommand.execute();
    }

    /**
     * <h2> queueList - 测试编程方式服务熔断 - 采用异步<h2>
     * version: 1.0 - 2022/3/6
     * @param
     * @return {@link List< Map< String, Object>> }
     */
    @GetMapping("/queue/list")
    public List<Map<String,Object>> queueList() throws ExecutionException, InterruptedException {
        log.info("queue Hystrix Command test");
        Future<List<Map<String, Object>>> future = orderHystrixCommand.queue();
        //异步执行，这里可以进行其他操作
        return future.get();
    }

    /**
     * <h2> observeList - 测试编程方式服务熔断 - 热响应调用<h2>
     * version: 1.0 - 2022/3/6
     * @param
     * @return {@link List< Map< String, Object>> }
     */
    @GetMapping("/observe/list")
    public List<Map<String,Object>> observeList() throws ExecutionException, InterruptedException {
        log.info("observe Hystrix Command test");
        Observable<List<Map<String, Object>>> observe = orderHystrixCommand.observe();
        //异步执行，这里可以进行其他操作
        return observe.toBlocking().single();
    }

    /**
     * <h2> toObservableList - 测试编程方式服务熔断 - 冷响应调用<h2>
     * version: 1.0 - 2022/3/6
     * @param
     * @return {@link List< Map< String, Object>> }
     */
    @GetMapping("/toObservable/list")
    public List<Map<String,Object>> toObservableList() throws ExecutionException, InterruptedException {
        log.info("toObservable Hystrix Command test");
        Observable<List<Map<String, Object>>> toObservable = orderHystrixCommand.toObservable();
        //异步执行，这里可以进行其他操作
        return toObservable.toBlocking().single();
    }


    @Autowired
    private OrderHystrixObservableCommand orderHystrixObservableCommand;

    /**
     * <h2> executeObsList - 测试编程方式服务熔断 信号量异步执行<h2>
     * version: 1.0 - 2022/3/6
     * @return {@link List< Map< String, Object>> }
     */
    @GetMapping("/execute/obs/list")
    public List<Map<String,Object>> executeObsList(){
        log.info("Hystrix Observe Command test");
        List<Map<String, Object>> results = new ArrayList<>();
        //异步执行
        Observable<List<Map<String, Object>>> observe = orderHystrixObservableCommand.observe();
        observe.subscribe(new Observer<List<Map<String, Object>>>() {
            @Override
            public void onCompleted() {
                log.info("subscriber onCompleted : [{}]",Thread.currentThread().getName());
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
            }

            @Override
            public void onNext(List<Map<String, Object>> list) {
                results.addAll(list);
            }
        });
        log.info("observe command results : [{}],[{}]",JSON.toJSONString(results),Thread.currentThread().getName());
        return results;
    }

    /**
     * <h2> executeToObsList - 测试编程方式服务熔断<h2>
     * version: 1.0 - 2022/3/6
     * @return {@link List< Map< String, Object>> }
     */
    @GetMapping("/execute/to/obs/list")
    public List<Map<String,Object>> executeToObsList(){
        log.info("Hystrix toObservable Command test");
        return orderHystrixObservableCommand.toObservable().toBlocking().single();
    }

    @Autowired
    private OrderService orderService;

    /**
     * <h2> queueList - 测试编程方式服务熔断 - 缓存测试<h2>
     * version: 1.0 - 2022/3/6
     * @param
     * @return {@link List< Map< String, Object>> }
     */
    @GetMapping("/cache/desc")
    public CommonResponse cacheHystrixCommand(@RequestParam(defaultValue = "123456") String orderNo) throws ExecutionException, InterruptedException {
        log.info("cache Hystrix Command test");
        CacheHystrixCommand command1 = new CacheHystrixCommand(orderService,orderNo);
        CacheHystrixCommand command2 = new CacheHystrixCommand(orderService,orderNo);
        Map<String, Object> result01 = command1.execute();
        Map<String, Object> result02 = command2.execute();
        log.info("result01 [{}] result02 [{}]",JSON.toJSONString(result01),JSON.toJSONString(result02));
        //清除缓存
        CacheHystrixCommand.flushRequestCache(orderNo);
        //继续发起
        CacheHystrixCommand command3 = new CacheHystrixCommand(orderService,orderNo);
        CacheHystrixCommand command4 = new CacheHystrixCommand(orderService,orderNo);
        Map<String, Object> result03 = command3.execute();
        Map<String, Object> result04 = command4.execute();
        log.info("result03 [{}] result04 [{}]",JSON.toJSONString(result03),JSON.toJSONString(result04));
        return CommonResponse.success();
    }

    @Autowired
    private CacheHystrixCommandAnnotation cacheHystrixCommandAnnotation;

    /**
     * <h2> queueList - 测试编程方式服务熔断 - 缓存注解形式测试<h2>
     * version: 1.0 - 2022/3/6
     * @param
     * @return {@link List< Map< String, Object>> }
     */
    @GetMapping("/cache/desc/annotation")
    public CommonResponse cacheHystrixCommandByAnnotation(@RequestParam(defaultValue = "123456") String orderNo) throws ExecutionException, InterruptedException {
        log.info("cache Annotation Hystrix Command test");
        Map<String, Object> result01 = cacheHystrixCommandAnnotation.useCacheByAnnotation01(orderNo);
        Map<String, Object> result02 = cacheHystrixCommandAnnotation.useCacheByAnnotation01(orderNo);
        log.info("result01 [{}] result02 [{}]",JSON.toJSONString(result01),JSON.toJSONString(result02));
        //清除缓存
        cacheHystrixCommandAnnotation.flushCacheByAnnotation01(orderNo);
        //继续发起
        Map<String, Object> result03 = cacheHystrixCommandAnnotation.useCacheByAnnotation02(orderNo);
        Map<String, Object> result04 = cacheHystrixCommandAnnotation.useCacheByAnnotation02(orderNo);
        log.info("result03 [{}] result04 [{}]",JSON.toJSONString(result03),JSON.toJSONString(result04));
        //清除缓存
        cacheHystrixCommandAnnotation.flushCacheByAnnotation02(orderNo);
        //继续发起
        Map<String, Object> result05 = cacheHystrixCommandAnnotation.useCacheByAnnotation03(orderNo);
        Map<String, Object> result06 = cacheHystrixCommandAnnotation.useCacheByAnnotation03(orderNo);
        log.info("result05 [{}] result06 [{}]",JSON.toJSONString(result05),JSON.toJSONString(result06));
        //清除缓存
        cacheHystrixCommandAnnotation.flushCacheByAnnotation03(orderNo);
        return CommonResponse.success();
    }

    @GetMapping("/good/desc")
    public List<List<Good>> descs(@RequestParam(defaultValue = "1,2,3") List<String> orderNos){
        return orderService.descs(orderNos);
    }

    @GetMapping("/merge/good/desc")
    public CommonResponse descsMerge() throws Exception {

        OrderCollapseCommand collapseCommand01 = new OrderCollapseCommand(orderService,"1");
        OrderCollapseCommand collapseCommand02 = new OrderCollapseCommand(orderService,"2");
        OrderCollapseCommand collapseCommand03 = new OrderCollapseCommand(orderService,"3");

        Future<List<Good>> queue01 = collapseCommand01.queue();
        Future<List<Good>> queue02 = collapseCommand02.queue();
        Future<List<Good>> queue03 = collapseCommand03.queue();

        List<Good> goods01 = queue01.get();
        List<Good> goods02 = queue02.get();
        List<Good> goods03 = queue03.get();
        log.info("result 01 {} ,result 02 {} , result 03 {} ",goods01,goods02,goods03);
        Thread.sleep(2000);

        OrderCollapseCommand collapseCommand04 = new OrderCollapseCommand(orderService,"4");

        Future<List<Good>> queue04 = collapseCommand04.queue();
        List<Good> goods04 = queue04.get();
        log.info("result 04 {} ",goods04);

        return CommonResponse.success();
    }

    @GetMapping("/merge/annotation/good/desc")
    public CommonResponse descsMergeByAnnotation() throws Exception {


        Future<List<Good>> future01 = orderService.descGood("1");
        Future<List<Good>> future02 = orderService.descGood("2");
        Future<List<Good>> future03 = orderService.descGood("3");


        List<Good> goods01 = future01.get();
        List<Good> goods02 = future02.get();
        List<Good> goods03 = future03.get();
        log.info("result 01 {} ,result 02 {} , result 03 {} ",goods01,goods02,goods03);
        Thread.sleep(2000);

        OrderCollapseCommand collapseCommand04 = new OrderCollapseCommand(orderService,"4");

        Future<List<Good>> future04 = orderService.descGood("4");
        List<Good> goods04 = future04.get();
        log.info("result 04 {} ",goods04);

        return CommonResponse.success();
    }
}
