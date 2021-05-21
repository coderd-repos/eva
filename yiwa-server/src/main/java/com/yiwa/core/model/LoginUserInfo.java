package com.yiwa.core.model;

import com.yiwa.dao.system.model.SystemPermission;
import com.yiwa.dao.system.model.SystemUser;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 登录用户信息
 * @author Yiwa
 * @date 2021-03-28 15:28
 */
@Data
public class LoginUserInfo {

    private Integer id;

    private String username;

    private String realname;

    private String avatar;

    private Date birthday;

    private String sex;

    private List<Permission> permissions;

    public static LoginUserInfo from(SystemUser user, List<SystemPermission> permissions) {
        if (user == null) {
            return null;
        }
        // 拷贝用户信息
        LoginUserInfo loginUserInfo = new LoginUserInfo();
        BeanUtils.copyProperties(user, loginUserInfo);
        // 拷贝权限信息
        List<Permission> pms = new ArrayList<>();
        for (SystemPermission permission : permissions) {
            Permission p = new Permission();
            BeanUtils.copyProperties(permission, p);
            pms.add(p);
        }
        loginUserInfo.setPermissions(pms);
        return loginUserInfo;
    }

    @Data
    public static class Permission {

        private String code;

        private String name;
    }
}
