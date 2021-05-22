package com.yiwa.dao.system.vo;

import com.yiwa.dao.system.model.SystemPosition;
import com.yiwa.dao.system.model.SystemUser;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class SystemPositionListVO extends SystemPosition {

    @ApiModelProperty("岗位人数")
    private long userCount;

    @ApiModelProperty("子岗位列表")
    private List<SystemPositionListVO> children;

    @ApiModelProperty("是否包含子部门")
    private Boolean hasChildren;

    @ApiModelProperty("创建人信息")
    private SystemUser createUserInfo;

    @ApiModelProperty("更新人信息")
    private SystemUser updateUserInfo;
}
