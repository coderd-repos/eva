package com.eva.dao.system.model;

import com.eva.core.constants.OperaType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.Date;

/**
 * 部门
 * @author Eva.Caesar Liu
 * @date 2021/05/16 11:59
 */
@Data
@ApiModel("部门")
public class SystemDepartment implements Serializable {

    @TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "主键", example = "1")
    @NotNull(message = "主键不能为空", groups = {OperaType.Update.class})
    private Integer id;

    @ApiModelProperty(value = "上级部门", example = "1")
    private Integer parentId;

    @ApiModelProperty(value = "部门编码")
    @NotBlank(message = "部门编码不能为空", groups = {OperaType.Create.class, OperaType.Update.class})
    private String code;

    @ApiModelProperty(value = "部门名称")
    @NotBlank(message = "部门名称不能为空", groups = {OperaType.Create.class, OperaType.Update.class})
    private String name;

    @ApiModelProperty(value = "联系电话")
    @Pattern(message = "手机号码格式不正确", regexp = "^\\d*$", groups = {OperaType.Create.class, OperaType.Update.class})
    private String phone;

    @ApiModelProperty(value = "部门邮箱")
    @Email(message = "部门邮箱格式不正确")
    private String email;

    @ApiModelProperty(value = "创建人", example = "1", hidden = true)
    private Integer createUser;

    @ApiModelProperty(value = "更新人", example = "1", hidden = true)
    private Integer updateUser;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

    @ApiModelProperty(value = "是否删除", hidden = true)
    private Boolean deleted;

}