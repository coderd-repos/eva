package com.eva.dao.system.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author Eva
 * @date 2021-03-30 21:41
 */
@Data
@ApiModel("系统菜单排序参数")
public class UpdateSystemMenuSortDTO {

    @ApiModelProperty(value = "菜单ID")
    private Integer id;

    @ApiModelProperty(value = "排序方向，top向上，bottom向下")
    private String direction;
}
