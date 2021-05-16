package com.yiwa.dao.system.vo;

import com.yiwa.dao.system.model.SystemPosition;
import com.yiwa.dao.system.model.SystemUser;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SystemPositionListVO extends SystemPosition {

    @ApiModelProperty("岗位人数")
    private long userCount;

    private SystemUser createUserInfo;

    private SystemUser updateUserInfo;
}
