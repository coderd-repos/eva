package com.eva.dao.system.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author Caesar Liu
 * @date 2021-03-29 15:54
 */
@Data
@ApiModel("菜单节点视图对象")
public class SystemMenuNodeVO {

    @ApiModelProperty(value = "菜单ID")
    private Integer id;

    @ApiModelProperty(value = "菜单名称")
    private String label;

    @ApiModelProperty(value = "菜单唯一标识（前端专用）")
    private String index;

    @ApiModelProperty(value = "图标")
    private String icon;

    @ApiModelProperty(value = "菜单地址")
    private String url;

    @ApiModelProperty(value = "子菜单")
    private List<SystemMenuNodeVO> children;
}
