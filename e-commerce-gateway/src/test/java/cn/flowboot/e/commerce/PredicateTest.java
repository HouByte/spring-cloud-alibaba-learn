package cn.flowboot.e.commerce;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

/**
 * <h1></h1>
 *
 * @version 1.0
 * @author: Vincent Vic
 * @since: 2022/03/03
 */
@SpringBootTest
public class PredicateTest {

    public static List<String> MICEO_SERVICE = Arrays.asList(
            "nacos","authority","gateway","ribbon","feign", "hystrix", "e-commerce"

    );

    /**
     * <h2> testPredicateTest - test 方法主要用于参数符不符合规则,返回值是 boolean <h2>
     * version: 1.0 - 2022/3/3
     * @param
     */
    @Test
    public void testPredicateTest(){
        Predicate<String> letterLengthLimit = s -> s.length() > 5;

        MICEO_SERVICE.stream().filter(letterLengthLimit).forEach(System.out::println);
    }

    /**
     * <h2> testPredicateAnd - and 且 && <h2>
     * version: 1.0 - 2022/3/3
     * @param
     */
    @Test
    public void testPredicateAnd(){
        Predicate<String> letterLengthLimit = s -> s.length() > 5;
        Predicate<String> letterStartWith = s -> s.startsWith("gate");

        MICEO_SERVICE.stream().filter(letterLengthLimit.and(letterStartWith)).forEach(System.out::println);
    }

    /**
     * <h2> testPredicateOr - or 或 || <h2>
     * version: 1.0 - 2022/3/3
     * @param
     */
    @Test
    public void testPredicateOr(){
        Predicate<String> letterLengthLimit = s -> s.length() > 6;
        Predicate<String> letterStartWith = s -> s.startsWith("nacos");

        MICEO_SERVICE.stream().filter(letterLengthLimit.or(letterStartWith)).forEach(System.out::println);
    }

    /**
     * <h2> testPredicateNegate - negate 逻辑非 ! <h2>
     * version: 1.0 - 2022/3/3
     * @param
     */
    @Test
    public void testPredicateNegate(){
        Predicate<String> letterStartWith = s -> s.startsWith("nacos");
        MICEO_SERVICE.stream().filter(letterStartWith.negate()).forEach(System.out::println);
    }

    /**
     * <h2> testPredicateNegate - 类似equals() 区别于先判空 <h2>
     * version: 1.0 - 2022/3/3
     * @param
     */
    @Test
    public void testPredicateIsEqual(){
        Predicate<String> equalGateway = s -> Predicate.isEqual("gateway").test(s);
        MICEO_SERVICE.stream().filter(equalGateway).forEach(System.out::println);
    }
}
