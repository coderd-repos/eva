package com.yiwa.service.system;

import com.yiwa.core.model.PageData;
import com.yiwa.core.model.PageWrap;
import com.yiwa.dao.system.dto.QuerySystemPositionDTO;
import com.yiwa.dao.system.model.SystemPosition;
import com.yiwa.dao.system.vo.SystemPositionListVO;

import java.util.List;

/**
 * 岗位Service定义
 * @author Yiwa
 * @date 2021/05/16 17:03
 */
public interface SystemPositionService {

    /**
     * 创建
     * @author Yiwa
     * @date 2021/05/16 17:03
     */
    Integer create(SystemPosition systemPosition);

    /**
     * 主键删除
     * @author Yiwa
     * @date 2021/05/16 17:03
     */
    void deleteById(Integer id);

    /**
     * 批量主键删除
     * @author Yiwa
     * @date 2021/05/16 17:03
     */
    void deleteByIdInBatch(List<Integer> ids);

    /**
     * 主键更新
     * @author Yiwa
     * @date 2021/05/16 17:03
     */
    void updateById(SystemPosition systemPosition);

    /**
     * 批量主键更新
     * @author Yiwa
     * @date 2021/05/16 17:03
     */
    void updateByIdInBatch(List<SystemPosition> systemPositions);

    /**
     * 主键查询
     * @author Yiwa
     * @date 2021/05/16 17:03
     */
    SystemPosition findById(Integer id);

    /**
     * 条件查询单条记录
     * @author Yiwa
     * @date 2021/05/16 17:03
     */
    SystemPosition findOne(SystemPosition systemPosition);

    /**
     * 条件查询
     * @author Yiwa
     * @date 2021/05/16 17:03
     */
    List<SystemPosition> findList(SystemPosition systemPosition);
  
    /**
     * 分页查询
     * @author Yiwa
     * @date 2021/05/16 17:03
     */
    PageData<SystemPositionListVO> findPage(PageWrap<QuerySystemPositionDTO> pageWrap);

    /**
     * 条件统计
     * @author Yiwa
     * @date 2021/05/16 17:03
     */
    long count(SystemPosition systemPosition);
}