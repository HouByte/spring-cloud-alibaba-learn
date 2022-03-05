package cn.flowboot.e.commerce.controller;

import cn.flowboot.e.commerce.dto.Good;
import cn.flowboot.e.commerce.dto.SearchGoodByIdsDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <h1></h1>
 *
 * @version 1.0
 * @author: Vincent Vic
 * @since: 2022/03/03
 */
@Slf4j
@RestController
@RequestMapping("good")
public class GoodController {

    @GetMapping("info")
    public Map<String,Object> info(){
        Map<String,Object> map = new HashMap<>();
        map.put("info","good");
        return map;
    }

    @PostMapping("/search/list")
    public List<Good> list(@RequestBody SearchGoodByIdsDto searchGoodByIdsDto){

        log.info("search Good list : {}" , searchGoodByIdsDto);
        List<Good> list = new ArrayList<>();
        if (searchGoodByIdsDto.getIds() != null && searchGoodByIdsDto.getIds().size() > 0) {
            for (Integer id : searchGoodByIdsDto.getIds()) {
                Good good = new Good();
                good.setId(id);
                good.setName("good_"+id);
                good.setPrice(new BigDecimal(id * 10));
                list.add(good);
            }
        }
        return list;
    }
}
