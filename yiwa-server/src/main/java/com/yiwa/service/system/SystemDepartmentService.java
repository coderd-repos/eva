package com.yiwa.service.system;

import com.yiwa.core.model.PageData;
import com.yiwa.core.model.PageWrap;
import com.yiwa.dao.system.model.SystemDepartment;
import com.yiwa.dao.system.vo.SystemDepartmentListVO;

import java.util.List;

/**
 * Service定义
 * @author Caesar Liu
 * @date 2021/05/16 11:59
 */
public interface SystemDepartmentService {

    /**
     * 创建
     * @author Caesar Liu
     * @date 2021/05/16 11:59
     */
    Integer create(SystemDepartment systemDepartment);

    /**
     * 主键删除
     * @author Caesar Liu
     * @date 2021/05/16 11:59
     */
    void deleteById(Integer id);

    /**
     * 批量主键删除
     * @author Caesar Liu
     * @date 2021/05/16 11:59
     */
    void deleteByIdInBatch(List<Integer> ids);

    /**
     * 主键更新
     * @author Caesar Liu
     * @date 2021/05/16 11:59
     */
    void updateById(SystemDepartment systemDepartment);

    /**
     * 批量主键更新
     * @author Caesar Liu
     * @date 2021/05/16 11:59
     */
    void updateByIdInBatch(List<SystemDepartment> systemDepartments);

    /**
     * 主键查询
     * @author Caesar Liu
     * @date 2021/05/16 11:59
     */
    SystemDepartment findById(Integer id);

    /**
     * 条件查询单条记录
     * @author Caesar Liu
     * @date 2021/05/16 11:59
     */
    SystemDepartment findOne(SystemDepartment systemDepartment);

    /**
     * 条件查询
     * @author Caesar Liu
     * @date 2021/05/16 11:59
     */
    List<SystemDepartmentListVO> findList();

    /**
     * 分页查询
     * @author Caesar Liu
     * @date 2021/05/16 11:59
     */
    PageData<SystemDepartment> findPage(PageWrap<SystemDepartment> pageWrap);

    /**
     * 条件统计
     * @author Caesar Liu
     * @date 2021/05/16 11:59
     */
    long count(SystemDepartment systemDepartment);
}