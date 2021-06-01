package com.eva.dao.system.model;

import com.eva.core.constants.OperaType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * 系统角色
 * @author Eva.Caesar Liu
 * @date 2021/05/15 19:41
 */
@Data
@ApiModel("系统角色")
public class SystemRole implements Serializable {

    @TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "主键", example = "1")
    @NotNull(message = "主键不能为空", groups = {OperaType.Update.class})
    private Integer id;

    @ApiModelProperty(value = "角色编码")
    @NotBlank(message = "角色编码不能为空", groups = {OperaType.Create.class, OperaType.Update.class})
    private String code;

    @ApiModelProperty(value = "角色名称")
    @NotBlank(message = "角色名称不能为空", groups = {OperaType.Create.class, OperaType.Update.class})
    private String name;

    @ApiModelProperty(value = "角色备注")
    private String remark;

    @ApiModelProperty(value = "是否为固定角色", hidden = true)
    private Boolean fixed;

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