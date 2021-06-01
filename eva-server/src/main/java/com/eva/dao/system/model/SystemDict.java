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
 * 字典
 * @author Eva.Caesar Liu
 * @date 2021/05/16 17:40
 */
@Data
@ApiModel("字典")
public class SystemDict implements Serializable {

    @TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "主键", example = "1")
    @NotNull(message = "主键不能为空", groups = {OperaType.Update.class})
    private Integer id;

    @ApiModelProperty(value = "字典编码")
    @NotBlank(message = "字典编码不能为空", groups = {OperaType.Create.class, OperaType.Update.class})
    private String code;

    @ApiModelProperty(value = "字典名称")
    @NotBlank(message = "字典名称不能为空", groups = {OperaType.Create.class, OperaType.Update.class})
    private String name;

    @ApiModelProperty(value = "备注")
    private String remark;

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