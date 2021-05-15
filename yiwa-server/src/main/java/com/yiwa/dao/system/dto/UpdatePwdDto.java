package com.yiwa.dao.system.dto;

import lombok.Data;

/**
 * 修改密码参数对象
 * @author Caesar Liu
 * @date 2021-03-31 14:12
 */
@Data
public class UpdatePwdDto {

    private Integer userId;

    private String oldPwd;

    private String newPwd;
}
