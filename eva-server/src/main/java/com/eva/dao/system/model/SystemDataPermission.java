package com.eva.dao.system.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 数据权限配置
 * @author Eva.Caesar Liu
 * @date 2021/06/11 20:28
 */
@Data
@ApiModel("数据权限配置")
@TableName("SYSTEM_DATA_PERMISSION")
public class SystemDataPermission implements Serializable {

    @TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "主键", example = "1")
    private Integer id;

    @ApiModelProperty(value = "业务模块")
    private String businessCode;

    @ApiModelProperty(value = "角色ID", example = "1")
    private Integer roleId;

    @ApiModelProperty(value = "权限类型（0全部，1自定义，2仅用户所属，3用户所属及其子数据）", example = "1")
    private Byte type;

    @ApiModelProperty(value = "自定义数据")
    private String customData;

    @ApiModelProperty(value = "是否禁用")
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
