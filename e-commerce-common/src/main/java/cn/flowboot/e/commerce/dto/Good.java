package cn.flowboot.e.commerce.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * <h1></h1>
 *
 * @version 1.0
 * @author: Vincent Vic
 * @since: 2022/03/05
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Good {
    private Integer id;
    private String name;
    private BigDecimal price;
}
