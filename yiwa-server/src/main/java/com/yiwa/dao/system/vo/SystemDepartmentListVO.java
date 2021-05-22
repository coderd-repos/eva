package com.yiwa.dao.system.vo;

import com.yiwa.dao.system.model.SystemDepartment;
import com.yiwa.dao.system.model.SystemUser;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 部门列表视图对象
 * @author Yiwa
 * @date 2021-05-16 15:27
 */
@Data
public class SystemDepartmentListVO extends SystemDepartment {

    @ApiModelProperty("部门人数")
    private long userCount;

    @ApiModelProperty("子部门列表")
    private List<SystemDepartmentListVO> children;

    @ApiModelProperty("是否包含子部门")
    private Boolean hasChildren;

    @ApiModelProperty("创建人信息")
    private SystemUser createUserInfo;

    @ApiModelProperty("更新人信息")
    private SystemUser updateUserInfo;
}
