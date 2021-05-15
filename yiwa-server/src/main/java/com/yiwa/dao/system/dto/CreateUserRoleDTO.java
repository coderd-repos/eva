package com.yiwa.dao.system.dto;

import lombok.Data;

import java.util.List;

/**
 * 创建用户角色参数对象
 * @author Caesar Liu
 * @date 2021-03-29 22:24
 */
@Data
public class CreateUserRoleDTO {

    private Integer userId;

    private List<Integer> roleIds;

    private Integer createUser;
}
