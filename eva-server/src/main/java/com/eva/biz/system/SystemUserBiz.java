package com.eva.biz.system;

import com.eva.core.model.PageData;
import com.eva.core.model.PageWrap;
import com.eva.dao.system.dto.*;
import com.eva.dao.system.vo.SystemUserListVO;

import java.util.List;

/**
 * 系统用户业务处理
 * @author Eva.Caesar Liu
 * @date 2021-03-29 22:24
 */
public interface SystemUserBiz {

    /**
     * 分页查询
     * @param pageWrap 分页参数
     *
     * @return PageData<SystemUserListVO>
     */
    PageData<SystemUserListVO> findPage (PageWrap<QuerySystemUserDTO> pageWrap);

    /**
     * 删除
     * @author Eva.Caesar Liu
     * @date 2021-05-26 16:34
     */
    void deleteById(Integer id);

    /**
     * 批量删除
     * @author Eva.Caesar Liu
     * @date 2021-05-26 16:37
     */
    void deleteByIdInBatch(List<Integer> ids);

    /**
     * 修改密码
     * @author Eva.Caesar Liu
     * @date 2021-03-31 14:14
     */
    void updatePwd(UpdatePwdDto dto);

    /**
     * 重置密码
     * @author Eva.Caesar Liu
     * @date 2021-03-31 20:01
     */
    void resetPwd(ResetSystemUserPwdDTO dto);

    /**
     * 创建用户
     * @author Eva.Caesar Liu
     * @date 2021-03-31 17:03
     */
    void create(CreateSystemUserDTO systemUser);

    /**
     * 修改用户信息
     * @author Eva.Caesar Liu
     * @date 2021-03-31 22:14
     */
    void updateById(CreateSystemUserDTO systemUser);

    /**
     * 创建用户角色
     * @author Eva.Caesar Liu
     * @date 2021-03-29 22:24
     */
    void createUserRole(CreateUserRoleDTO dto);
}
