package cn.flowboot.e.commerce.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * <h1>通用响应对象定义</h1>
 *
 *  {
 *      "code":0,
 *      "message":"",
 *      "data":{}
 *  }
 * @version 1.0
 * @author: Vincent Vic
 * @since: 2022/03/02
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommonResponse<T> implements Serializable {

    private final static int SUCCESS_CODE = 0;
    private final static String SUCCESS_MSG = "success";
    private final static int FAIL_CODE = -1;
    private final static String FAIL_MSG = "fail";
    /**
     * 错误码
     */
    private Integer code;
    /**
     * 错误消息
     */
    private String message;
    /**
     * 泛型响应数据
     */
    private T data;


    /**
     * <h2> build - 构建通用消息 - 全参数<h2>
     * version: 1.0 - 2022/3/2
     * @param code 状态码
     * @param message 消息
     * @param data 泛型数据
     * @return {@link CommonResponse }
     */
    public static <T> CommonResponse build(Integer code, String message, T data){
        return new CommonResponse<T>(code,message,data);
    }

    /**
     * <h2> build - 构建通用消息 - 状态消息<h2>
     * version: 1.0 - 2022/3/2
     * @param code 状态码
     * @param message 消息
     * @return {@link CommonResponse }
     */
    public static <T> CommonResponse build(Integer code, String message){
        return new CommonResponse<T>(code,message,null);
    }

    /**
     * <h2> success - 默认成功通用消息<h2>
     * version: 1.0 - 2022/3/2
     * @return {@link CommonResponse }
     */
    public static  CommonResponse success(){
        return new CommonResponse(FAIL_CODE,FAIL_MSG,null);
    }

    /**
     * <h2> success - 成功通用消息 - 消息<h2>
     * version: 1.0 - 2022/3/2
     * @param message 消息
     * @return {@link CommonResponse }
     */
    public static  CommonResponse success( String message){
        return new CommonResponse(FAIL_CODE,message,null);
    }

    /**
     * <h2> success - 成功通用消息 - 消息数据<h2>
     * version: 1.0 - 2022/3/2
     * @param message 消息
     * @param data 泛型数据
     * @return {@link CommonResponse }
     */
    public static <T> CommonResponse success(String message,T data){
        return new CommonResponse<T>(FAIL_CODE,message,data);
    }

    /**
     * <h2> fail - 默认失败通用消息<h2>
     * version: 1.0 - 2022/3/2
     * @return {@link CommonResponse }
     */
    public static  CommonResponse fail(){
        return new CommonResponse(FAIL_CODE,FAIL_MSG,null);
    }

    /**
     * 通用消息
     * @param message 消息
     * @return {@link CommonResponse}
     */

    /**
     * <h2> fail - 失败通用消息 - 消息<h2>
     * version: 1.0 - 2022/3/2
     * @param message 消息
     * @return {@link CommonResponse }
     */
    public static  CommonResponse fail( String message){
        return new CommonResponse(FAIL_CODE,message,null);
    }

    /**
     * <h2> fail - 失败通用消息 - 消息数据 <h2>
     * version: 1.0 - 2022/3/2
     * @param message 消息
     * @param data  泛型数据
     * @return {@link CommonResponse }
     */
    public static <T> CommonResponse fail(String message,T data){
        return new CommonResponse<T>(FAIL_CODE,message,data);
    }



}
