package com.eva.service.system;

import com.eva.core.model.PageData;
import com.eva.core.model.PageWrap;
import com.eva.dao.system.model.Location;
import java.util.List;

/**
 * 地区表Service定义
 * @author Eva
 * @date 2021/06/10 17:09
 */
public interface LocationService {

    /**
     * 创建
     * 
     * @param location 实体对象
     * @return Integer
     */
    Integer create(Location location);

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
    void delete(Location location);

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
    void updateById(Location location);

    /**
     * 批量主键更新
     *
     * @param locations 实体集
     */
    void updateByIdInBatch(List<Location> locations);

    /**
     * 主键查询
     *
     * @param id 主键
     * @return Location
     */
    Location findById(Integer id);

    /**
     * 条件查询单条记录
     *
     * @param location 实体对象
     * @return Location
     */
    Location findOne(Location location);

    /**
     * 条件查询
     *
     * @param location 实体对象
     * @return List<Location>
     */
    List<Location> findList(Location location);
  
    /**
     * 分页查询
     *
     * @param pageWrap 分页对象
     * @return PageData<Location>
     */
    PageData<Location> findPage(PageWrap<Location> pageWrap);

    /**
     * 条件统计
     *
     * @param location 实体对象
     * @return long
     */
    long count(Location location);
}
