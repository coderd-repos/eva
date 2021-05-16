package com.yiwa.dao.system.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;

/**
 * 岗位
 * @author Caesar Liu
 * @date 2021/05/16 17:03
 */
@Data
@ApiModel("岗位")
public class SystemPosition {

    @ApiModelProperty(value = "主键", example = "1")
    @TableId(type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "岗位名称")
    private String name;

    @ApiModelProperty(value = "排序", example = "1")
    private Integer sort;

    @ApiModelProperty(value = "创建人", example = "1")
    private Integer createUser;

    @ApiModelProperty(value = "更新人", example = "1")
    private Integer updateUser;

    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date createTime;

    @ApiModelProperty(value = "更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date updateTime;

    @ApiModelProperty(value = "是否删除")
    private Boolean deleted;

}