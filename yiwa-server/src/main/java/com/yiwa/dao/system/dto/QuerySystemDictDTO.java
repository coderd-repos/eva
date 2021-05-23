package com.yiwa.dao.system.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author Yiwa
 * @date 2021-05-16 20:07
 */
@Data
@ApiModel("查询字典列表参数")
public class QuerySystemDictDTO {

    @ApiModelProperty(value = "字典编码")
    private String code;

    @ApiModelProperty(value = "字典名称")
    private String name;
}
