package com.eva.dao.system.model;

import com.eva.core.model.OperaType;
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
 * 字典数据
 * @author Eva
 * @date 2021/05/16 20:18
 */
@Data
@ApiModel("字典数据")
public class SystemDictData implements Serializable {

    @TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "主键", example = "1")
    @NotNull(message = "主键不能为空", groups = {OperaType.Update.class})
    private Integer id;

    @ApiModelProperty(value = "所属字典", example = "1")
    @NotNull(message = "所属字典不能为空", groups = {OperaType.Create.class, OperaType.Update.class})
    private Integer dictId;

    @ApiModelProperty(value = "数据值")
    @NotBlank(message = "数据值不能为空", groups = {OperaType.Create.class, OperaType.Update.class})
    private String code;

    @ApiModelProperty(value = "数据标签")
    @NotBlank(message = "数据标签不能为空", groups = {OperaType.Create.class, OperaType.Update.class})
    private String label;

    @ApiModelProperty(value = "排序", example = "1")
    private Integer sort;

    @ApiModelProperty(value = "是否禁用")
    @NotNull(message = "是否禁用不能为空", groups = {OperaType.Create.class, OperaType.Update.class})
    private Boolean disabled;

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