package com.yiwa.dao.system.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * 系统角色
 * @author Caesar Liu
 * @date 2021/03/27 22:34
 */
@Data
@ApiModel("系统角色")
public class SystemRole {

    @TableId(type= IdType.AUTO)
    @ApiModelProperty(value = "主键", example = "1")
    private Integer id;

    @ApiModelProperty(value = "角色CODE")
    private String code;

    @ApiModelProperty(value = "角色名称")
    private String name;

    @ApiModelProperty(value = "角色备注")
    private String remark;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

    @ApiModelProperty(value = "创建者ID", example = "1")
    private Integer createUser;

    @ApiModelProperty(value = "更新者ID", example = "1")
    private Integer updateUser;

    @ApiModelProperty(value = "是否已删除")
    private Boolean deleted;

}