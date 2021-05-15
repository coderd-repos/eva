package com.yiwa.dao.system.dto;

import lombok.Data;

/**
 * 重置用户密码参数对象
 * @author Caesar Liu
 * @date 2021-03-31 20:00
 */
@Data
public class ResetSystemUserPwdDTO {

    private Integer id;

    private String password;

    private Integer operaUserId;
}
