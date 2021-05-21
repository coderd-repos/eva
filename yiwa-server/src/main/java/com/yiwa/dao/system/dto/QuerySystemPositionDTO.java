package com.yiwa.dao.system.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author Yiwa
 * @date 2021-05-16 17:17
 */
@Data
@ApiModel("查询岗位参数对象")
public class QuerySystemPositionDTO {

    @ApiModelProperty("岗位名称")
    private String name;
}
