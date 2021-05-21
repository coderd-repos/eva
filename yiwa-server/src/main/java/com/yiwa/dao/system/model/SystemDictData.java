package com.yiwa.dao.system.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;

/**
 * 字典数据
 * @author Yiwa
 * @date 2021/05/16 20:18
 */
@Data
@ApiModel("字典数据")
public class SystemDictData {

    @ApiModelProperty(value = "主键", example = "1")
    @TableId(type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "所属字典", example = "1")
    private Integer dictId;

    @ApiModelProperty(value = "数据值")
    private String code;

    @ApiModelProperty(value = "数据标签")
    private String label;

    @ApiModelProperty(value = "排序", example = "1")
    private Integer sort;

    @ApiModelProperty(value = "是否禁用")
    private Boolean disabled;

    @ApiModelProperty(value = "创建人", example = "1")
    private Integer createUser;

    @ApiModelProperty(value = "更新人", example = "1")
    private Integer updateUser;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

    @ApiModelProperty(value = "是否删除")
    private Boolean deleted;

}