package com.yiwa.core.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

/**
 * 接口返回对象
 * @author Yiwa
 * @date 2021/03/26 19:48
 */
@Data
@AllArgsConstructor
public class ApiResponse<T> {

    private Integer code;

    private String message;

    private T data;

    /**
     * 请求成功
     * @author Yiwa
     * @date 2021/03/26 19:48
     */
    public static <T> ApiResponse success(T data) {
        return ApiResponse.success("请求成功", data);
    }

    /**
     * 请求成功
     * @author Yiwa
     * @date 2021/03/26 19:48
     */
    public static <T> ApiResponse success(String message, T data) {
        return new ApiResponse<>(HttpStatus.OK.value(), message, data);
    }

    /**
     * 请求失败
     * @author Yiwa
     * @date 2021/03/26 19:48
     */
    public static ApiResponse failed() {
        return ApiResponse.failed("请求失败");
    }

    /**
     * 请求失败
     * @author Yiwa
     * @date 2021/03/26 19:48
     */
    public static ApiResponse failed(String message) {
        return ApiResponse.failed(HttpStatus.INTERNAL_SERVER_ERROR.value(), message);
    }

    /**
     * 请求失败
     * @author Yiwa
     * @date 2021/03/26 19:48
     */
    public static ApiResponse failed(Integer code, String message) {
        return new ApiResponse<>(code, message, null);
    }
}