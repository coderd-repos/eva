package com.yiwa.service.system;

import com.yiwa.dao.system.model.SystemDepartmentUser;
import java.util.List;

/**
 * 部门用户Service定义
 * @author Caesar Liu
 * @date 2021/05/22 11:57
 */
public interface SystemDepartmentUserService {

    /**
     * 创建
     * @author Caesar Liu
     * @date 2021/05/22 11:57
     */
    Integer create(SystemDepartmentUser systemDepartmentUser);

    /**
     * 主键删除
     * @author Caesar Liu
     * @date 2021/05/22 11:57
     */
    void deleteById(Integer id);

    /**
     * 删除
     * @author Caesar Liu
     * @date 2021-05-22 15:11
     */
    void delete(SystemDepartmentUser dto);

    /**
     * 批量主键删除
     * @author Caesar Liu
     * @date 2021/05/22 11:57
     */
    void deleteByIdInBatch(List<Integer> ids);

    /**
     * 主键更新
     * @author Caesar Liu
     * @date 2021/05/22 11:57
     */
    void updateById(SystemDepartmentUser systemDepartmentUser);

    /**
     * 批量主键更新
     * @author Caesar Liu
     * @date 2021/05/22 11:57
     */
    void updateByIdInBatch(List<SystemDepartmentUser> systemDepartmentUsers);

    /**
     * 主键查询
     * @author Caesar Liu
     * @date 2021/05/22 11:57
     */
    SystemDepartmentUser findById(Integer id);

    /**
     * 条件查询单条记录
     * @author Caesar Liu
     * @date 2021/05/22 11:57
     */
    SystemDepartmentUser findOne(SystemDepartmentUser systemDepartmentUser);

    /**
     * 条件查询
     * @author Caesar Liu
     * @date 2021/05/22 11:57
     */
    List<SystemDepartmentUser> findList(SystemDepartmentUser systemDepartmentUser);

    /**
     * 条件统计
     * @author Caesar Liu
     * @date 2021/05/22 11:57
     */
    long count(SystemDepartmentUser systemDepartmentUser);
}