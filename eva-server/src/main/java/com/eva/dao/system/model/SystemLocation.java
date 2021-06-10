package com.eva.dao.system.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 地区表
 * @author Eva
 * @date 2021/06/10 17:09
 */
@Data
@ApiModel("地区表")
@TableName("SYSTEM_LOCATION")
public class SystemLocation {

    @TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "ID", example = "1")
    private Integer id;

    @ApiModelProperty(value = "父id", example = "1")
    private Integer parentId;

    @ApiModelProperty(value = "简称")
    private String shortName;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "全称")
    private String fullName;

    @ApiModelProperty(value = "层级 0 1 2 省市区县", example = "1")
    private Byte level;

    @ApiModelProperty(value = "拼音")
    private String pinyin;

    @ApiModelProperty(value = "区号")
    private String areaCode;

    @ApiModelProperty(value = "邮编")
    private String postalCode;

    @ApiModelProperty(value = "首字母")
    private String firstLetter;

    @ApiModelProperty(value = "经度")
    private String lng;

    @ApiModelProperty(value = "纬度")
    private String lat;

    @ApiModelProperty(value = "是否禁用")
    private Boolean disabled;

}
