package com.yiwa.dao.system.dto;

import lombok.Data;

/**
 * 查询系统用户参数
 * @author Caesar Liu
 * @date 2021-03-29 22:46
 */
@Data
public class QuerySystemUserDTO {

    private Integer departmentId;

    private Integer positionId;

    private String username;

    private String realname;

    private String mobile;
}
