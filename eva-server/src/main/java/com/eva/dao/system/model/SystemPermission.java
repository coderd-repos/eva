package com.eva.dao.system.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.Date;

/**
 * 系统权限
 * @author Eva
 * @date 2021/05/15 19:41
 */
@Data
@ApiModel("系统权限")
public class SystemPermission {

    @ApiModelProperty(value = "主键", example = "1")
    @TableId(type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "权限CODE")
    private String code;

    @ApiModelProperty(value = "权限名称")
    private String name;

    @ApiModelProperty(value = "权限备注")
    private String remark;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

    @ApiModelProperty(value = "创建者ID", example = "1", hidden = true)
    private Integer createUser;

    @ApiModelProperty(value = "更新者ID", example = "1", hidden = true)
    private Integer updateUser;

    @ApiModelProperty(value = "是否已删除", hidden = true)
    private Boolean deleted;

}