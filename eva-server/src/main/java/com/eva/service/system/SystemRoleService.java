package com.eva.service.system;

import com.eva.core.model.PageData;
import com.eva.core.model.PageWrap;
import com.eva.dao.system.dto.QuerySystemRoleDTO;
import com.eva.dao.system.model.SystemRole;
import com.eva.dao.system.vo.SystemRoleListVO;

import java.util.List;

/**
 * 系统角色Service定义
 * @author Caesar Liu
 * @date 2021/05/15 19:41
 */
public interface SystemRoleService {

    /**
     * 创建
     * @author Caesar Liu
     * @date 2021/05/15 19:41
     */
    Integer create(SystemRole systemRole);

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
    void updateById(SystemRole systemRole);

    /**
     * 批量主键更新
     * @author Caesar Liu
     * @date 2021/05/15 19:41
     */
    void updateByIdInBatch(List<SystemRole> systemRoles);

    /**
     * 主键查询
     * @author Caesar Liu
     * @date 2021/05/15 19:41
     */
    SystemRole findById(Integer id);

    /**
     * 根据用户ID查询
     * @author Caesar Liu
     * @date 2021-03-31 21:01
     */
    List<SystemRole> findByUserId(Integer userId);

    /**
     * 条件查询单条记录
     * @author Caesar Liu
     * @date 2021/05/15 19:41
     */
    SystemRole findOne(SystemRole systemRole);

    /**
     * 条件查询
     * @author Caesar Liu
     * @date 2021/05/15 19:41
     */
    List<SystemRole> findList(SystemRole systemRole);
  
    /**
     * 分页查询
     * @author Caesar Liu
     * @date 2021/05/15 19:41
     */
    PageData<SystemRoleListVO> findPage(PageWrap<QuerySystemRoleDTO> pageWrap);

    /**
     * 条件统计
     * @author Caesar Liu
     * @date 2021/05/15 19:41
     */
    long count(SystemRole systemRole);
}