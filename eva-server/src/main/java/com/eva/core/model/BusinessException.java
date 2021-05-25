package com.eva.core.model;

import lombok.Data;

/**
 * 业务异常对象
 * @author Caesar Liu
 * @date 2021-03-31 14:22
 */
@Data
public class BusinessException extends RuntimeException {

    private Integer code;

    public BusinessException(Integer code, String message) {
        super(message);
        this.code = code;
    }
}
