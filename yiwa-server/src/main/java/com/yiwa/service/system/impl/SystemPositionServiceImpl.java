package com.yiwa.service.system.impl;

import com.github.pagehelper.PageInfo;
import com.yiwa.core.utils.WrapperUtil;
import com.yiwa.core.model.PageData;
import com.yiwa.core.model.PageWrap;
import com.yiwa.dao.system.SystemPositionMapper;
import com.yiwa.dao.system.dto.QuerySystemPositionDTO;
import com.yiwa.dao.system.model.SystemPosition;
import com.yiwa.dao.system.vo.SystemPositionListVO;
import com.yiwa.service.system.SystemPositionService;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * 岗位Service实现
 * @author Caesar Liu
 * @date 2021/05/16 17:03
 */
@Service
public class SystemPositionServiceImpl implements SystemPositionService {

    @Autowired
    private SystemPositionMapper systemPositionMapper;

    @Override
    public Integer create(SystemPosition systemPosition) {
        systemPositionMapper.insert(systemPosition);
        return systemPosition.getId();
    }

    @Override
    public void deleteById(Integer id) {
        SystemPosition systemPosition = new SystemPosition();
        systemPosition.setId(id);
        systemPosition.setDeleted(Boolean.TRUE);
        this.updateById(systemPosition);
    }

    @Override
    @Transactional
    public void deleteByIdInBatch(List<Integer> ids) {
        if (CollectionUtils.isEmpty(ids)) return;
        for (Integer id : ids) {
            this.deleteById(id);
        }
    }

    @Override
    public void updateById(SystemPosition systemPosition) {
        systemPositionMapper.updateById(systemPosition);
    }

    @Override
    public void updateByIdInBatch(List<SystemPosition> systemPositions) {
        if (CollectionUtils.isEmpty(systemPositions)) return;
        for (SystemPosition systemPosition: systemPositions) {
            this.updateById(systemPosition);
        }
    }

    @Override
    public SystemPosition findById(Integer id) {
        return systemPositionMapper.selectById(id);
    }

    @Override
    public SystemPosition findOne(SystemPosition systemPosition) {
        Wrapper<SystemPosition> wrapper = new QueryWrapper<>(systemPosition);
        return systemPositionMapper.selectOne(wrapper);
    }

    @Override
    public List<SystemPosition> findList(SystemPosition systemPosition) {
        Wrapper<SystemPosition> wrapper = new QueryWrapper<>(systemPosition);
        return systemPositionMapper.selectList(wrapper);
    }
  
    @Override
    public PageData<SystemPositionListVO> findPage(PageWrap<QuerySystemPositionDTO> pageWrap) {
        return PageData.from(new PageInfo<>(systemPositionMapper.selectManageList(pageWrap.getModel())));
    }

    @Override
    public long count(SystemPosition systemPosition) {
        Wrapper<SystemPosition> wrapper = new QueryWrapper<>(systemPosition);
        return systemPositionMapper.selectCount(wrapper);
    }
}