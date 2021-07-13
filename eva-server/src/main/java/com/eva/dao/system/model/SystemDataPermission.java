package com.eva.dao.system.model;

import com.eva.core.constants.OperaType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * 数据权限配置
 * @author Eva.Caesar Liu
 * @date 2021/07/13 22:37
 */
@Data
@ApiModel("数据权限配置")
@TableName("SYSTEM_DATA_PERMISSION")
public class SystemDataPermission implements Serializable {

    @TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "主键", example = "1")
    @NotNull(message = "主键不能为空", groups = {OperaType.Update.class, OperaType.UpdateStatus.class})
    private Integer id;

    @ApiModelProperty(value = "业务模块")
    @NotBlank(message = "业务模块不能为空", groups = {OperaType.Create.class, OperaType.Update.class})
    private String businessCode;

    @ApiModelProperty(value = "角色ID", example = "1")
    @NotNull(message = "角色ID不能为空", groups = {OperaType.Create.class, OperaType.Update.class})
    private Integer roleId;

    @ApiModelProperty(value = "权限类型（0全部，1自定义，2仅用户所属，3用户所属及其子数据）", example = "1")
    @NotNull(message = "权限类型不能为空", groups = {OperaType.Create.class, OperaType.Update.class})
    private Short type;

    @ApiModelProperty(value = "自定义数据")
    private String customData;

    @ApiModelProperty(value = "是否禁用")
    @NotNull(message = "是否禁用不能为空", groups = OperaType.UpdateStatus.class)
    private Boolean disabled;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "创建人", example = "1")
    private Integer createUser;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "修改人", example = "1")
    private Integer updateUser;

    @ApiModelProperty(value = "修改时间")
    private Date updateTime;

    @ApiModelProperty(value = "是否已删除")
    private Boolean deleted;

}
