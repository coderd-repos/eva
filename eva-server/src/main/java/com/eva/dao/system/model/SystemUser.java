package com.eva.dao.system.model;

import com.eva.core.constants.OperaType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.Date;

/**
 * 系统用户
 * @author Eva.Caesar Liu
 * @date 2021/07/13 22:37
 */
@Data
@ApiModel("系统用户")
public class SystemUser implements Serializable {

    @TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "主键", example = "1")
    @NotNull(message = "主键不能为空", groups = {OperaType.Update.class})
    private Integer id;

    @ApiModelProperty(value = "用户名")
    @NotBlank(message = "用户名不能为空", groups = {OperaType.Create.class, OperaType.Update.class})
    private String username;

    @ApiModelProperty(value = "姓名")
    @NotBlank(message = "姓名不能为空", groups = {OperaType.Create.class, OperaType.Update.class})
    private String realname;

    @ApiModelProperty(value = "工号")
    private String empNo;

    @ApiModelProperty(value = "生日")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date birthday;

    @ApiModelProperty(value = "性别")
    @NotBlank(message = "性别不能为空", groups = {OperaType.Create.class, OperaType.Update.class})
    private String sex;

    @ApiModelProperty(value = "邮箱")
    @Email(message = "邮箱格式不正确", groups = {OperaType.Create.class, OperaType.Update.class})
    private String email;

    @ApiModelProperty(value = "手机号码")
    @Pattern(message = "手机号码格式不正确", regexp = "^\\d*$", groups = {OperaType.Create.class, OperaType.Update.class})
    private String mobile;

    @ApiModelProperty(value = "头像")
    @NotBlank(message = "头像不能为空", groups = {OperaType.Create.class, OperaType.Update.class})
    private String avatar;

    @ApiModelProperty(value = "密码")
    @NotBlank(message = "初始密码不能为空", groups = {OperaType.Create.class})
    private String password;

    @ApiModelProperty(value = "盐")
    private String salt;

    @ApiModelProperty(value = "是否为固定用户", hidden = true)
    private Boolean fixed;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

    @ApiModelProperty(value = "创建人", example = "1", hidden = true)
    private Integer createUser;

    @ApiModelProperty(value = "更新人", example = "1", hidden = true)
    private Integer updateUser;

    @ApiModelProperty(value = "是否已删除", hidden = true)
    private Boolean deleted;

}
