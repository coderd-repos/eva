package com.eva.core.aware;

import com.eva.core.constants.DataPermissionConstants;
import com.eva.core.model.LoginUserInfo;
import com.eva.dao.system.model.SystemDataPermission;
import com.eva.service.system.SystemDataPermissionService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.AnnotationConfigurationException;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Method;
import java.util.*;

/**
 * 默认数据权限控制
 *
 * @author Eva.Caesar Liu
 * @date 2021-06-11 20:22
 */
@Slf4j
@Component
public abstract class DefaultDataPermissionAware<T> implements DataPermissionAware<T> {

    @Autowired
    private SystemDataPermissionService systemDataPermissionService;

    /**
     * 执行数据权限查询
     *
     * @return List<T>
     */
    public List<T> execute() {
        try {
            // 获取登录用户
            LoginUserInfo loginUserInfo = (LoginUserInfo) SecurityUtils.getSubject().getPrincipal();
            // 获取数据权限配置
            List<SystemDataPermission> dataPermissions = systemDataPermissionService.findDataPermission(getModule().getBusinessCode(), loginUserInfo.getRoles());
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
            Method[] methods = this.getClass().getDeclaredMethods();
            Map<Integer, Method> sortedMethods = new TreeMap<>();
            for (Method method : methods) {
                DataPermissionMapping mapping = method.getAnnotation(DataPermissionMapping.class);
                if (mapping == null || mapping.value().equals(DataPermissionConstants.Type.ALL)) {
                    continue;
                }
                if (sortedMethods.get(mapping.priority()) != null) {
                    throw new AnnotationConfigurationException("Data permission contains the same priority.");
                }
                sortedMethods.put(mapping.priority(), method);
            }
            for (Map.Entry<Integer, Method> entry : sortedMethods.entrySet()) {
                Method method = entry.getValue();
                DataPermissionMapping mapping = method.getAnnotation(DataPermissionMapping.class);
                for (SystemDataPermission dataPermission : dataPermissions) {
                    if (dataPermission.getType().equals(mapping.value().getCode())) {
                        if (mapping.injectCustomData() && mapping.injectUser()) {
                            return (List<T>) method.invoke(this, loginUserInfo.getId(), dataPermission.getCustomData());
                        }
                        if (mapping.injectUser()) {
                            return (List<T>) method.invoke(this, loginUserInfo.getId());
                        }
                        if (mapping.injectCustomData()) {
                            return (List<T>) method.invoke(this, dataPermission.getCustomData());
                        }
                        return (List<T>) method.invoke(this);
                    }
                }
            }
        } catch (Exception e) {
            throw new AnnotationConfigurationException("Data permission dead.", e);
        }
        throw new IllegalStateException("No type found of data permission.");
    }

    /**
     * 获取模块
     */
    public abstract DataPermissionConstants.Module getModule();

}
