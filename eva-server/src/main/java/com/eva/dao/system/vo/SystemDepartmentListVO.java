package com.eva.dao.system.vo;

import com.eva.dao.system.model.SystemDepartment;
import com.eva.dao.system.model.SystemUser;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author Caesar Liu
 * @date 2021-05-16 15:27
 */
@Data
@ApiModel("部门列表视图对象")
public class SystemDepartmentListVO extends SystemDepartment {

    @ApiModelProperty(value = "部门人数")
    private long userCount;

    @ApiModelProperty(value = "子部门列表")
    private List<SystemDepartmentListVO> children;

    @ApiModelProperty(value = "是否包含子部门")
    private Boolean hasChildren;

    @ApiModelProperty(value = "创建人信息")
    private SystemUser createUserInfo;

    @ApiModelProperty(value = "更新人信息")
    private SystemUser updateUserInfo;
}
