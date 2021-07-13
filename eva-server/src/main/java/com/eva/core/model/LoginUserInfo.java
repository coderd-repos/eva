package com.eva.core.model;

import com.eva.dao.system.model.SystemPermission;
import com.eva.dao.system.model.SystemRole;
import com.eva.dao.system.model.SystemUser;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 登录用户信息
 * @author Eva.Caesar Liu
 * @date 2021/07/13 22:37
 */
@Data
public class LoginUserInfo implements Serializable {

    private Integer id;

    private String username;

    private String realname;

    private String avatar;

    private Date birthday;

    private String sex;

    private List<String> roles;

    private List<String> permissions;

    public static LoginUserInfo from(SystemUser user, List<SystemRole> roles, List<SystemPermission> permissions) {
        if (user == null) {
            return null;
        }
        // 拷贝用户信息
        LoginUserInfo loginUserInfo = new LoginUserInfo();
        BeanUtils.copyProperties(user, loginUserInfo);
        // 设置角色信息
        List<String> rs = new ArrayList<>();
        for (SystemRole role : roles) {
            rs.add(role.getCode());
        }
        loginUserInfo.setRoles(rs);
        // 设置权限信息
        List<String> pms = new ArrayList<>();
        for (SystemPermission permission : permissions) {
            pms.add(permission.getCode());
        }
        loginUserInfo.setPermissions(pms);
        return loginUserInfo;
    }
}
