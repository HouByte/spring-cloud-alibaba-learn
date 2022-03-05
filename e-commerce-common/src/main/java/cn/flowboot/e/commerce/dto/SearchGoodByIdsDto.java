package cn.flowboot.e.commerce.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * <h1>搜索商品</h1>
 *
 * @version 1.0
 * @author: Vincent Vic
 * @since: 2022/03/05
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class SearchGoodByIdsDto {
    private List<Integer> ids;
}
