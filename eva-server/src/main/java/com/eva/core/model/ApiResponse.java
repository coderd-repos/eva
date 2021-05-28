package com.eva.core.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.io.Serializable;

/**
 * 接口返回对象
 * @author Eva
 * @date 2021/03/26 19:48
 */
@Data
@AllArgsConstructor
public class ApiResponse<T> implements Serializable {

    private int code;

    private boolean success;

    private String message;

    private T data;

    public ApiResponse () {}

    /**
     * 请求成功
     * @author Eva
     * @date 2021/03/26 19:48
     */
    public static <T> ApiResponse<T> success(T data) {
        return ApiResponse.success("请求成功", data);
    }

    /**
     * 请求成功
     * @author Eva
     * @date 2021/03/26 19:48
     */
    public static <T> ApiResponse<T> success(String message, T data) {
        return new ApiResponse<>(HttpStatus.OK.value(), Boolean.TRUE, message, data);
    }

    /**
     * 请求失败
     * @author Eva
     * @date 2021/03/26 19:48
     */
    public static <T> ApiResponse<T> failed(String message) {
        return ApiResponse.failed(HttpStatus.INTERNAL_SERVER_ERROR.value(), message);
    }

    /**
     * 请求失败
     * @author Eva
     * @date 2021-05-25 11:10
     */
    public static <T> ApiResponse<T> failed(ResponseStatus status) {
        return ApiResponse.failed(status.getCode(), status.getMessage());
    }

    /**
     * 请求失败
     * @author Eva
     * @date 2021/03/26 19:48
     */
    public static <T> ApiResponse<T> failed(Integer code, String message) {
        return new ApiResponse<>(code, Boolean.FALSE, message, null);
    }
}