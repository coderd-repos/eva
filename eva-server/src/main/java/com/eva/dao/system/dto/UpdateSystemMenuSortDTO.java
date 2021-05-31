package com.eva.dao.system.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author Eva.Caesar Liu
 * @date 2021-03-30 21:41
 */
@Data
@ApiModel("系统菜单排序参数")
public class UpdateSystemMenuSortDTO implements Serializable {

    @ApiModelProperty(value = "菜单ID")
    @NotNull(message = "菜单ID不能为空")
    private Integer id;

    @ApiModelProperty(value = "排序方向，top向上，bottom向下")
    @NotBlank(message = "排序方向不能为空")
    private String direction;
}
