package com.eva.core.exception;

import com.eva.core.model.ResponseStatus;
import lombok.Data;

/**
 * 业务异常对象
 * @author Eva.Caesar Liu
 * @date 2021-03-31 14:22
 */
@Data
public class BusinessException extends RuntimeException {

    private Integer code;

    public BusinessException(Integer code, String message) {
        super(message);
        this.code = code;
    }

    public BusinessException(Integer code, String message, Throwable e) {
        super(message, e);
        this.code = code;
    }

    public BusinessException(ResponseStatus status) {
        super(status.getMessage());
        this.code = status.getCode();
    }

    public BusinessException(ResponseStatus status, Throwable e) {
        super(status.getMessage(), e);
        this.code = status.getCode();
    }
}
