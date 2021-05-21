package com.yiwa.dao.system.vo;

import com.yiwa.dao.system.model.SystemRole;
import com.yiwa.dao.system.model.SystemUser;
import lombok.Data;

import java.util.List;

/**
 * 系统用户列表视图对象
 * @author Yiwa
 * @date 2021-03-29 22:47
 */
@Data
public class SystemUserListVO extends SystemUser {

    private List<SystemRole> roles;

    private SystemUser createUserInfo;

    private SystemUser updateUserInfo;

}
