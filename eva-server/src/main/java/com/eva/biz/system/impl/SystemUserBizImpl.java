package com.eva.biz.system.impl;

import com.eva.biz.system.SystemUserBiz;
import com.eva.core.exception.BusinessException;
import com.eva.core.constants.ResponseStatus;
import com.eva.core.utils.Utils;
import com.eva.dao.system.dto.CreateSystemUserDTO;
import com.eva.dao.system.dto.CreateUserRoleDTO;
import com.eva.dao.system.dto.ResetSystemUserPwdDTO;
import com.eva.dao.system.dto.UpdatePwdDto;
import com.eva.dao.system.model.SystemDepartmentUser;
import com.eva.dao.system.model.SystemPositionUser;
import com.eva.dao.system.model.SystemUser;
import com.eva.dao.system.model.SystemUserRole;
import com.eva.service.system.SystemDepartmentUserService;
import com.eva.service.system.SystemPositionUserService;
import com.eva.service.system.SystemUserRoleService;
import com.eva.service.system.SystemUserService;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;

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
    public void deleteById(Integer id) {
        SystemUser user = systemUserService.findById(id);
        if (user == null) {
            return;
        }
        if (user.getFixed()) {
            throw new BusinessException(ResponseStatus.NOT_ALLOWED.getCode(), "请勿删除" + user.getUsername() + "，因为这是固定用户");
        }
        systemUserService.deleteById(id);
    }

    @Override
    @Transactional
    public void deleteByIdInBatch(List<Integer> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            return;
        }
        for (Integer id : ids) {
            this.deleteById(id);
        }
    }

    @Override
    public void updatePwd(UpdatePwdDto dto) {
        SystemUser user = systemUserService.findById(dto.getUserId());
        if (user.getDeleted()) {
            throw new BusinessException(ResponseStatus.DATA_EMPTY.getCode(), "用户不存在或已被删除");
        }
        // 验证原始密码
        if (!user.getPassword().equals(Utils.Secure.encryptPassword(dto.getOldPwd(), user.getSalt()))) {
            throw new BusinessException(ResponseStatus.PWD_INCORRECT.getCode(), "原始密码不正确");
        }
        // 修改密码
        SystemUser newUser = new SystemUser();
        newUser.setId(dto.getUserId());
        newUser.setPassword(Utils.Secure.encryptPassword(dto.getNewPwd(), user.getSalt()));
        systemUserService.updateById(newUser);
    }

    @Override
    public void resetPwd(ResetSystemUserPwdDTO dto) {
        // 查询用户
        SystemUser systemUser = systemUserService.findById(dto.getId());
        if (systemUser == null || systemUser.getDeleted()) {
            throw new BusinessException(ResponseStatus.DATA_EMPTY.getCode(), "用户不存在或已被删除");
        }
        // 修改密码
        SystemUser updateUserDto = new SystemUser();
        updateUserDto.setId(dto.getId());
        updateUserDto.setPassword(Utils.Secure.encryptPassword(dto.getPassword(), systemUser.getSalt()));
        systemUserService.updateById(updateUserDto);
    }

    @Override
    @Transactional
    public void create(CreateSystemUserDTO systemUser) {
        // 验证用户名
        SystemUser queryUserDto = new SystemUser();
        queryUserDto.setUsername(systemUser.getUsername());
        queryUserDto.setDeleted(Boolean.FALSE);
        SystemUser user = systemUserService.findOne(queryUserDto);
        if (user != null) {
            throw new BusinessException(ResponseStatus.DATA_EXISTS.getCode(), "用户名已存在");
        }
        // 验证工号
        if (StringUtils.isNotBlank(systemUser.getEmpNo())) {
            queryUserDto = new SystemUser();
            queryUserDto.setDeleted(Boolean.FALSE);
            queryUserDto.setEmpNo(systemUser.getEmpNo());
            user = systemUserService.findOne(queryUserDto);
            if (user != null) {
                throw new BusinessException(ResponseStatus.DATA_EXISTS.getCode(), "工号已存在");
            }
        }
        // 生成密码盐
        String salt = RandomStringUtils.randomAlphabetic(6);
        // 生成密码
        systemUser.setPassword(Utils.Secure.encryptPassword(systemUser.getPassword(), salt));
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
        if (systemUser.getPositionIds() != null && systemUser.getPositionIds().size() > 0) {
            for (Integer positionId : systemUser.getPositionIds()) {
                SystemPositionUser systemPositionUser = new SystemPositionUser();
                systemPositionUser.setPositionId(positionId);
                systemPositionUser.setUserId(userId);
                systemPositionUser.setOperaUser(systemUser.getCreateUser());
                systemPositionUser.setOperaTime(new Date());
                systemPositionUserService.create(systemPositionUser);
            }
        }
    }

    @Override
    public void updateById(CreateSystemUserDTO systemUser) {
        // 验证用户名
        SystemUser queryUserDto = new SystemUser();
        queryUserDto.setUsername(systemUser.getUsername());
        queryUserDto.setDeleted(Boolean.FALSE);
        SystemUser user = systemUserService.findOne(queryUserDto);
        if (user != null && !user.getId().equals(systemUser.getId())) {
            throw new BusinessException(ResponseStatus.DATA_EXISTS.getCode(), "用户名已存在");
        }
        // 验证工号
        if (StringUtils.isNotBlank(systemUser.getEmpNo())) {
            queryUserDto = new SystemUser();
            queryUserDto.setEmpNo(systemUser.getEmpNo());
            queryUserDto.setDeleted(Boolean.FALSE);
            user = systemUserService.findOne(queryUserDto);
            if (user != null && !user.getId().equals(systemUser.getId())) {
                throw new BusinessException(ResponseStatus.DATA_EXISTS.getCode(), "工号已存在");
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
        if (systemUser.getPositionIds() != null && systemUser.getPositionIds().size() > 0) {
            for (Integer positionId : systemUser.getPositionIds()) {
                SystemPositionUser systemPositionUser = new SystemPositionUser();
                systemPositionUser.setPositionId(positionId);
                systemPositionUser.setUserId(systemUser.getId());
                systemPositionUser.setOperaUser(systemUser.getUpdateUser());
                systemPositionUser.setOperaTime(new Date());
                systemPositionUserService.create(systemPositionUser);
            }
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
