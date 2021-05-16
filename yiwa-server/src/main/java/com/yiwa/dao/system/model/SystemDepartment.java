package com.yiwa.dao.system.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;

/**
 * 
 * @author Caesar Liu
 * @date 2021/05/16 11:59
 */
@Data
@ApiModel("")
public class SystemDepartment {

    @ApiModelProperty(value = "主键", example = "1")
    private Integer id;

    @ApiModelProperty(value = "父部门", example = "1")
    private Integer parentId;

    @ApiModelProperty(value = "部门名称")
    private String name;

    @ApiModelProperty(value = "排序", example = "1")
    private Integer sort;

    @ApiModelProperty(value = "联系电话")
    private String phone;

    @ApiModelProperty(value = "部门邮箱")
    private String email;

    @ApiModelProperty(value = "创建人", example = "1")
    private Integer createUser;

    @ApiModelProperty(value = "更新人", example = "1")
    private Integer updateUser;

    @ApiModelProperty(value = "创建人")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date createTime;

    @ApiModelProperty(value = "更新人")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date updateTime;

    @ApiModelProperty(value = "是否删除")
    private Boolean deleted;

}