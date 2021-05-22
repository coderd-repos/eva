package com.yiwa.biz.system.impl;

import com.yiwa.biz.system.SystemUserBiz;
import com.yiwa.core.model.BusinessException;
import com.yiwa.core.model.ResponseStatus;
import com.yiwa.core.utils.SecureUtil;
import com.yiwa.dao.system.dto.CreateSystemUserDTO;
import com.yiwa.dao.system.dto.CreateUserRoleDTO;
import com.yiwa.dao.system.dto.ResetSystemUserPwdDTO;
import com.yiwa.dao.system.dto.UpdatePwdDto;
import com.yiwa.dao.system.model.SystemDepartmentUser;
import com.yiwa.dao.system.model.SystemPositionUser;
import com.yiwa.dao.system.model.SystemUser;
import com.yiwa.dao.system.model.SystemUserRole;
import com.yiwa.service.system.SystemDepartmentUserService;
import com.yiwa.service.system.SystemPositionUserService;
import com.yiwa.service.system.SystemUserRoleService;
import com.yiwa.service.system.SystemUserService;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.Date;

@Service
public class SystemUserBizImpl implements SystemUserBiz {

    @Autowired
    private SystemUserService systemUserService;

    @Autowired
    private SystemUserRoleService systemUserRoleService;

    @Autowired
    private SystemDepartmentUserService systemDepartmentUserService;

    @Autowired
    private SystemPositionUserService systemPositionUserService;

    @Override
    public void updatePwd(UpdatePwdDto dto) {
        Assert.notNull(dto.getUserId(), "缺少参数");
        Assert.notNull(dto.getOldPwd(), "缺少参数");
        Assert.notNull(dto.getNewPwd(), "缺少参数");
        SystemUser user = systemUserService.findById(dto.getUserId());
        if (user.getDeleted()) {
            throw new BusinessException(ResponseStatus.DATA_EMPTY.getCode(), "用户不存在或已被删除");
        }
        // 验证原始密码
        if (!user.getPassword().equals(SecureUtil.encryptPassword(dto.getOldPwd(), user.getSalt()))) {
            throw new BusinessException(ResponseStatus.PWD_INCORRECT.getCode(), "原始密码不正确");
        }
        // 修改密码
        SystemUser newUser = new SystemUser();
        newUser.setId(dto.getUserId());
        newUser.setPassword(SecureUtil.encryptPassword(dto.getNewPwd(), user.getSalt()));
        systemUserService.updateById(newUser);
    }

    @Override
    public void resetPwd(ResetSystemUserPwdDTO dto) {
        Assert.notNull(dto.getId(), "缺少参数");
        Assert.notNull(dto.getPassword(), "缺少密码");
        Assert.notNull(dto.getOperaUserId(), "缺少参数");
        // 查询用户
        SystemUser systemUser = systemUserService.findById(dto.getId());
        if (systemUser == null || systemUser.getDeleted()) {
            throw new BusinessException(ResponseStatus.DATA_EMPTY.getCode(), "用户不存在或已被删除");
        }
        // 修改密码
        SystemUser updateUserDto = new SystemUser();
        updateUserDto.setId(dto.getId());
        updateUserDto.setPassword(SecureUtil.encryptPassword(dto.getPassword(), systemUser.getSalt()));
        systemUserService.updateById(updateUserDto);
    }

    @Override
    @Transactional
    public void create(CreateSystemUserDTO systemUser) {
        Assert.notNull(systemUser.getUsername(), "缺少参数");
        Assert.notNull(systemUser.getRealname(), "缺少参数");
        Assert.notNull(systemUser.getPassword(), "缺少参数");
        // 验证用户名
        SystemUser queryUserDto = new SystemUser();
        queryUserDto.setDeleted(Boolean.FALSE);
        queryUserDto.setUsername(systemUser.getUsername());
        SystemUser user = systemUserService.findOne(queryUserDto);
        if (user != null) {
            throw new BusinessException(ResponseStatus.DATA_EXISTS.getCode(), "用户名已被占用");
        }
        // 验证工号
        if (StringUtils.isNotBlank(systemUser.getEmpNo())) {
            queryUserDto = new SystemUser();
            queryUserDto.setDeleted(Boolean.FALSE);
            queryUserDto.setEmpNo(systemUser.getEmpNo());
            user = systemUserService.findOne(queryUserDto);
            if (user != null) {
                throw new BusinessException(ResponseStatus.DATA_EXISTS.getCode(), "工号已被占用");
            }
        }
        // 生成密码盐
        String salt = RandomStringUtils.randomAlphabetic(6);
        // 生成密码
        systemUser.setPassword(SecureUtil.encryptPassword(systemUser.getPassword(), salt));
        systemUser.setSalt(salt);
        // 创建用户记录
        Integer userId = systemUserService.create(systemUser);
        // 设置部门
        if (systemUser.getDepartmentId() != null) {
            SystemDepartmentUser systemDepartmentUser = new SystemDepartmentUser();
            systemDepartmentUser.setDepartmentId(systemUser.getDepartmentId());
            systemDepartmentUser.setUserId(userId);
            systemDepartmentUser.setOperaUser(systemUser.getCreateUser());
            systemDepartmentUser.setOperaTime(new Date());
            systemDepartmentUserService.create(systemDepartmentUser);
        }
        // 设置岗位
        if (systemUser.getPositionId() != null) {
            SystemPositionUser systemPositionUser = new SystemPositionUser();
            systemPositionUser.setPositionId(systemUser.getPositionId());
            systemPositionUser.setUserId(userId);
            systemPositionUser.setOperaUser(systemUser.getCreateUser());
            systemPositionUser.setOperaTime(new Date());
            systemPositionUserService.create(systemPositionUser);
        }
    }

    @Override
    public void updateById(CreateSystemUserDTO systemUser) {
        Assert.notNull(systemUser.getUsername(), "缺少参数");
        Assert.notNull(systemUser.getRealname(), "缺少参数");
        // 验证用户名
        SystemUser queryUserDto = new SystemUser();
        queryUserDto.setUsername(systemUser.getUsername());
        queryUserDto.setDeleted(Boolean.FALSE);
        SystemUser user = systemUserService.findOne(queryUserDto);
        if (user != null && !user.getId().equals(systemUser.getId())) {
            throw new BusinessException(ResponseStatus.DATA_EXISTS.getCode(), "用户名已被占用");
        }
        // 验证工号
        if (StringUtils.isNotBlank(systemUser.getEmpNo())) {
            queryUserDto = new SystemUser();
            queryUserDto.setEmpNo(systemUser.getEmpNo());
            queryUserDto.setDeleted(Boolean.FALSE);
            user = systemUserService.findOne(queryUserDto);
            if (user != null && !user.getId().equals(systemUser.getId())) {
                throw new BusinessException(ResponseStatus.DATA_EXISTS.getCode(), "工号已被占用");
            }
        }
        // 修改用户
        systemUserService.updateById(systemUser);
        // 设置部门
        SystemDepartmentUser deleteDepartmentDto = new SystemDepartmentUser();
        deleteDepartmentDto.setUserId(systemUser.getId());
        systemDepartmentUserService.delete(deleteDepartmentDto);
        if (systemUser.getDepartmentId() != null) {
            SystemDepartmentUser systemDepartmentUser = new SystemDepartmentUser();
            systemDepartmentUser.setDepartmentId(systemUser.getDepartmentId());
            systemDepartmentUser.setUserId(systemUser.getId());
            systemDepartmentUser.setOperaUser(systemUser.getUpdateUser());
            systemDepartmentUser.setOperaTime(new Date());
            systemDepartmentUserService.create(systemDepartmentUser);
        }
        // 设置岗位
        SystemPositionUser deletePositionDto = new SystemPositionUser();
        deletePositionDto.setUserId(systemUser.getId());
        systemPositionUserService.delete(deletePositionDto);
        if (systemUser.getPositionId() != null) {
            SystemPositionUser systemPositionUser = new SystemPositionUser();
            systemPositionUser.setPositionId(systemUser.getPositionId());
            systemPositionUser.setUserId(systemUser.getId());
            systemPositionUser.setOperaUser(systemUser.getUpdateUser());
            systemPositionUser.setOperaTime(new Date());
            systemPositionUserService.create(systemPositionUser);
        }
    }

    @Override
    public void createUserRole(CreateUserRoleDTO dto) {
        // 删除关联角色
        SystemUserRole deleteDto = new SystemUserRole();
        deleteDto.setUserId(dto.getUserId());
        systemUserRoleService.delete(deleteDto);
        // 新增新的角色
        for (Integer roleId : dto.getRoleIds()) {
            SystemUserRole newUserRole = new SystemUserRole();
            newUserRole.setUserId(dto.getUserId());
            newUserRole.setRoleId(roleId);
            systemUserRoleService.create(newUserRole);
        }
    }
}
