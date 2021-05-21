package com.yiwa.dao.system.dto;

import lombok.Data;

/**
 * 系统菜单排序
 * @author Yiwa
 * @date 2021-03-30 21:41
 */
@Data
public class UpdateSystemMenuSortDTO {

    private Integer id;

    // 方向，top向上，bottom向下
    private String direction;
}
