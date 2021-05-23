package com.yiwa.dao.system.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import java.util.Date;

/**
 * 部门
 * @author Yiwa
 * @date 2021/05/16 11:59
 */
@Data
@ApiModel("部门")
public class SystemDepartment {

    @TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "主键", example = "1")
    private Integer id;

    @ApiModelProperty(value = "父部门", example = "1")
    private Integer parentId;

    @ApiModelProperty(value = "部门编码")
    private String code;

    @ApiModelProperty(value = "部门名称")
    private String name;

    @ApiModelProperty(value = "联系电话")
    private String phone;

    @ApiModelProperty(value = "部门邮箱")
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