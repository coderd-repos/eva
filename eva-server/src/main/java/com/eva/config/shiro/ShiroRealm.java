package com.eva.config.shiro;

import com.eva.core.model.LoginUserInfo;
import com.eva.dao.system.model.SystemPermission;
import com.eva.dao.system.model.SystemRole;
import com.eva.dao.system.model.SystemUser;
import com.eva.service.system.SystemPermissionService;
import com.eva.service.system.SystemRoleService;
import com.eva.service.system.SystemUserService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 自定义Realm，处理认证和权限
 * @author Eva
 * @date 2021-03-28 14:58
 */
@Component
public class ShiroRealm extends AuthorizingRealm {

    @Autowired
    private SystemUserService systemUserService;

    @Autowired
    private SystemRoleService systemRoleService;

    @Autowired
    private SystemPermissionService systemPermissionService;

    /**
     * 权限处理
     * @author Eva
     * @date 2021-03-27 18:56
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        LoginUserInfo loginUserInfo = (LoginUserInfo)principalCollection.getPrimaryPrincipal();
        // 查询用户角色
        List<SystemRole> roles = systemRoleService.findByUserId(loginUserInfo.getId());
        List<String> roleCodes = new ArrayList<>();
        for (SystemRole role : roles) {
            roleCodes.add(role.getCode());
        }
        // 查询用户权限
        List<SystemPermission> permissions = systemPermissionService.findByUserId(loginUserInfo.getId());
        List<String> permissionCodes = new ArrayList<>();
        for (SystemPermission permission : permissions) {
            permissionCodes.add(permission.getCode());
        }
        // 设置用户角色和权限
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        authorizationInfo.addRoles(roleCodes);
        authorizationInfo.addStringPermissions(permissionCodes);
        return authorizationInfo;
    }

    /**
     * 认证处理
     * @author Eva
     * @date 2021-03-27 18:57
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        // 获取用户名
        String username = authenticationToken.getPrincipal().toString();
        // 根据用户名查询用户对象
        SystemUser queryDto = new SystemUser();
        queryDto.setUsername(username);
        queryDto.setDeleted(Boolean.FALSE);
        SystemUser user = systemUserService.findOne(queryDto);
        if (user == null) {
            return null;
        }
        // 验证用户
        return new SimpleAuthenticationInfo(LoginUserInfo.from(user, systemPermissionService.findByUserId(user.getId())), user.getPassword(), this.getName());
    }

}
