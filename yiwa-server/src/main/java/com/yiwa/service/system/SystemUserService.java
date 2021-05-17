package com.yiwa.service.system;

import com.yiwa.core.model.PageData;
import com.yiwa.core.model.PageWrap;
import com.yiwa.dao.system.dto.QuerySystemUserDTO;
import com.yiwa.dao.system.model.SystemRole;
import com.yiwa.dao.system.model.SystemUser;
import com.yiwa.dao.system.vo.SystemUserListVO;

import java.util.List;

/**
 * 系统用户Service定义
 * @author Caesar Liu
 * @date 2021/05/15 19:41
 */
public interface SystemUserService {

    /**
     * 创建
     * @author Caesar Liu
     * @date 2021/05/15 19:41
     */
    Integer create(SystemUser systemUser);

    /**
     * 主键删除
     * @author Caesar Liu
     * @date 2021/05/15 19:41
     */
    void deleteById(Integer id);

    /**
     * 批量主键删除
     * @author Caesar Liu
     * @date 2021/05/15 19:41
     */
    void deleteByIdInBatch(List<Integer> ids);

    /**
     * 主键更新
     * @author Caesar Liu
     * @date 2021/05/15 19:41
     */
    void updateById(SystemUser systemUser);

    /**
     * 批量主键更新
     * @author Caesar Liu
     * @date 2021/05/15 19:41
     */
    void updateByIdInBatch(List<SystemUser> systemUsers);

    /**
     * 主键查询
     * @author Caesar Liu
     * @date 2021/05/15 19:41
     */
    SystemUser findById(Integer id);

    /**
     * 条件查询单条记录
     * @author Caesar Liu
     * @date 2021/05/15 19:41
     */
    SystemUser findOne(SystemUser systemUser);

    /**
     * 条件查询
     * @author Caesar Liu
     * @date 2021/05/15 19:41
     */
    List<SystemUser> findList(SystemUser systemUser);
  
    /**
     * 分页查询
     * @author Caesar Liu
     * @date 2021/05/15 19:41
     */
    PageData<SystemUserListVO> findPage(PageWrap<QuerySystemUserDTO> pageWrap);

    /**
     * 条件统计
     * @author Caesar Liu
     * @date 2021/05/15 19:41
     */
    long count(SystemUser systemUser);
}