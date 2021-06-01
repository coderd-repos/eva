package com.eva.core.model;

import com.eva.core.constants.ResponseStatus;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.io.Serializable;

/**
 * 接口返回对象
 * @author Eva.Caesar Liu
 * @date 2021/03/26 19:48
 */
@ApiModel("响应对象")
@Data
@AllArgsConstructor
public class ApiResponse<T> implements Serializable {

    @ApiModelProperty(value = "响应码")
    private int code;

    @ApiModelProperty(value = "请求是否成功")
    private boolean success;

    @ApiModelProperty(value = "错误消息")
    private String message;

    @ApiModelProperty(value = "数据")
    private T data;

    @ApiModelProperty(value = "异常消息")
    private String exception;

    public ApiResponse () {}

    /**
     * 请求成功
     */
    public static <T> ApiResponse<T> success(T data) {
        return ApiResponse.success("请求成功", data);
    }

    /**
     * 请求成功
     */
    public static <T> ApiResponse<T> success(String message, T data) {
        return new ApiResponse<>(HttpStatus.OK.value(), Boolean.TRUE, message, data, null);
    }

    /**
     * 请求失败
     */
    public static <T> ApiResponse<T> failed(String message) {
        return ApiResponse.failed(HttpStatus.INTERNAL_SERVER_ERROR.value(), message);
    }

    /**
     * 请求失败
     */
    public static <T> ApiResponse<T> failed(ResponseStatus status) {
        return ApiResponse.failed(status.getCode(), status.getMessage());
    }

    /**
     * 请求失败
     */
    public static <T> ApiResponse<T> failed(ResponseStatus status, Throwable ex) {
        return ApiResponse.failed(status.getCode(), status.getMessage(), ex);
    }

    /**
     * 请求失败
     */
    public static <T> ApiResponse<T> failed(Integer code, String message) {
        return ApiResponse.failed(code, message, null);
    }

    /**
     * 请求失败
     */
    public static <T> ApiResponse<T> failed(Integer code, String message, Throwable ex) {
        if (ex == null) {
            return new ApiResponse<>(code, Boolean.FALSE, message, null, null);
        }
        // 处理异常栈，防止过多内容导致响应内容过大
        StackTraceElement[] trace = ex.getStackTrace();
        StringBuilder exceptionStack = new StringBuilder(ex + "\n");
        for (StackTraceElement traceElement : trace) {
            exceptionStack.append("\tat ").append(traceElement).append("\n");
            if (exceptionStack.length() > 5000) {
                break;
            }
        }
        return new ApiResponse<>(code, Boolean.FALSE, message, null, exceptionStack.toString());
    }
}