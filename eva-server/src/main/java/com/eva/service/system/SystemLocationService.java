package com.eva.service.system;

import com.eva.core.model.PageData;
import com.eva.core.model.PageWrap;
import com.eva.dao.system.model.SystemLocation;
import java.util.List;

/**
 * 地区Service定义
 * @author Eva.Caesar Liu
 * @date 2021/07/13 22:37
 */
public interface SystemLocationService {

    /**
     * 创建
     * 
     * @param location 实体对象
     * @return Integer
     */
    Integer create(SystemLocation location);

    /**
     * 主键删除
     *
     * @param id 主键
     */
    void deleteById(Integer id);

    /**
     * 删除
     *
     * @param location 实体对象
     */
    void delete(SystemLocation location);

    /**
     * 批量主键删除
     *
     * @param ids 主键集
     */
    void deleteByIdInBatch(List<Integer> ids);

    /**
     * 主键更新
     *
     * @param location 实体对象
     */
    void updateById(SystemLocation location);

    /**
     * 批量主键更新
     *
     * @param locations 实体集
     */
    void updateByIdInBatch(List<SystemLocation> locations);

    /**
     * 主键查询
     *
     * @param id 主键
     * @return Location
     */
    SystemLocation findById(Integer id);

    /**
     * 条件查询单条记录
     *
     * @param location 实体对象
     * @return Location
     */
    SystemLocation findOne(SystemLocation location);

    /**
     * 条件查询
     *
     * @param location 实体对象
     * @return List<Location>
     */
    List<SystemLocation> findList(SystemLocation location);
  
    /**
     * 分页查询
     *
     * @param pageWrap 分页对象
     * @return PageData<Location>
     */
    PageData<SystemLocation> findPage(PageWrap<SystemLocation> pageWrap);

    /**
     * 条件统计
     *
     * @param location 实体对象
     * @return long
     */
    long count(SystemLocation location);
}
