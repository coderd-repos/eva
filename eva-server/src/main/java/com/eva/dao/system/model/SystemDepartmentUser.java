package com.eva.dao.system.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Eva.Caesar Liu
 * @date 2021/07/13 22:37
 */
@Data
@ApiModel("部门用户")
public class SystemDepartmentUser implements Serializable {

    @TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "主键", example = "1")
    private Integer id;

    @ApiModelProperty(value = "部门ID", example = "1")
    private Integer departmentId;

    @ApiModelProperty(value = "用户ID", example = "1")
    private Integer userId;

    @ApiModelProperty(value = "操作人", example = "1", hidden = true)
    private Integer operaUser;

    @ApiModelProperty(value = "操作时间")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date operaTime;

    @ApiModelProperty(value = "是否已删除", hidden = true)
    private Boolean deleted;

}
