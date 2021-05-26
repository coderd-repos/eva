package com.eva.dao.system.model;

import com.eva.core.model.OperaType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * 系统权限
 * @author Eva
 * @date 2021/05/15 19:41
 */
@Data
@ApiModel("系统权限")
public class SystemPermission {

    @TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "主键", example = "1")
    @NotNull(message = "主键不能为空", groups = {OperaType.Update.class})
    private Integer id;

    @ApiModelProperty(value = "权限编码")
    @NotBlank(message = "权限编码不能为空", groups = {OperaType.Create.class,OperaType.Update.class})
    private String code;

    @ApiModelProperty(value = "权限名称")
    @NotBlank(message = "权限名称不能为空", groups = {OperaType.Create.class,OperaType.Update.class})
    private String name;

    @ApiModelProperty(value = "权限备注")
    private String remark;

    @ApiModelProperty(value = "是否为固定权限", hidden = true)
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