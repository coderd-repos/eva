package com.yiwa.biz.system;

import com.yiwa.dao.system.dto.CreateSystemUserDTO;
import com.yiwa.dao.system.dto.CreateUserRoleDTO;
import com.yiwa.dao.system.dto.ResetSystemUserPwdDTO;
import com.yiwa.dao.system.dto.UpdatePwdDto;
import com.yiwa.dao.system.model.SystemUser;

/**
 * 系统用户业务处理
 * @author Yiwa
 * @date 2021-03-29 22:24
 */
public interface SystemUserBiz {

    /**
     * 修改密码
     * @author Yiwa
     * @date 2021-03-31 14:14
     */
    void updatePwd(UpdatePwdDto dto);

    /**
     * 重置密码
     * @author Yiwa
     * @date 2021-03-31 20:01
     */
    void resetPwd(ResetSystemUserPwdDTO dto);

    /**
     * 创建用户
     * @author Yiwa
     * @date 2021-03-31 17:03
     */
    void create(CreateSystemUserDTO systemUser);

    /**
     * 修改用户信息
     * @author Yiwa
     * @date 2021-03-31 22:14
     */
    void updateById(SystemUser systemUser);

    /**
     * 创建用户角色
     * @author Yiwa
     * @date 2021-03-29 22:24
     */
    void createUserRole(CreateUserRoleDTO dto);
}
