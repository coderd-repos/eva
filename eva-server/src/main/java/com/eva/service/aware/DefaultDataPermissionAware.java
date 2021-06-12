package com.eva.service.aware;

import com.eva.core.aware.DataPermissionAware;
import com.eva.core.constants.DataPermissionConstants;
import com.eva.core.model.LoginUserInfo;
import com.eva.dao.system.model.SystemDataPermission;
import com.eva.service.system.SystemDataPermissionService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * 默认数据权限控制
 * @author Eva.Caesar Liu
 * @date 2021-06-11 20:22
 */
@Slf4j
@Component
public abstract class DefaultDataPermissionAware<T> implements DataPermissionAware<T> {

    @Autowired
    private SystemDataPermissionService systemDataPermissionService;

    /**
     * 执行数据权限
     * @param businessCode 业务编码
     *
     * @return List<T>
     */
    public List<T> execute (String businessCode) {
        // 获取登录用户
        LoginUserInfo loginUserInfo = (LoginUserInfo) SecurityUtils.getSubject().getPrincipal();
        // 获取数据权限配置
        List<SystemDataPermission> dataPermissions = systemDataPermissionService.findDataPermission(businessCode, loginUserInfo.getRoles());
        if (CollectionUtils.isEmpty(dataPermissions)) {
            // 无数据权限配置视为拥有所有数据权限
            return this.all();
        }
        // 按角色的数据权限优先级返回数据
        for (SystemDataPermission dataPermission : dataPermissions) {
            if (dataPermission.getType().equals(DataPermissionConstants.Type.ALL.getCode())) {
                return this.all();
            }
        }
        for (SystemDataPermission dataPermission : dataPermissions) {
            if (dataPermission.getType().equals(DataPermissionConstants.Type.CUSTOM.getCode())) {
                return this.custom(dataPermission.getCustomData());
            }
        }
        for (SystemDataPermission dataPermission : dataPermissions) {
            if (dataPermission.getType().equals(DataPermissionConstants.Type.USER_CHILDREN.getCode())) {
                return this.userChildren(loginUserInfo.getId());
            }
        }
        for (SystemDataPermission dataPermission : dataPermissions) {
            if (dataPermission.getType().equals(DataPermissionConstants.Type.USER_CHILD.getCode())) {
                return this.userChild(loginUserInfo.getId());
            }
        }
        for (SystemDataPermission dataPermission : dataPermissions) {
            if (dataPermission.getType().equals(DataPermissionConstants.Type.USER.getCode())) {
                return this.user(loginUserInfo.getId());
            }
        }
        throw new IllegalStateException("No type found of data permission.");
    }
}
