package com.yiwa.dao.system.dto;

import lombok.Data;

import java.util.List;

/**
 * 创建角色权限参数对象
 * @author Yiwa
 * @date 2021-03-30 10:50
 */
@Data
public class CreateRolePermissionDTO {

    private Integer roleId;

    private List<Integer> permissionIds;

    private Integer createUser;
}
