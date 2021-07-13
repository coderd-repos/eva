package com.eva.service.system.impl;

import com.eva.core.model.PageData;
import com.eva.core.model.PageWrap;
import com.eva.core.utils.Utils;
import com.eva.dao.system.SystemLocationMapper;
import com.eva.dao.system.model.SystemLocation;
import com.eva.service.system.SystemLocationService;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * 地区Service实现
 * @author Eva.Caesar Liu
 * @date 2021/07/13 22:37
 */
@Service
public class SystemLocationServiceImpl implements SystemLocationService {

    @Autowired
    private SystemLocationMapper locationMapper;

    @Override
    public Integer create(SystemLocation location) {
        locationMapper.insert(location);
        return location.getId();
    }

    @Override
    public void deleteById(Integer id) {
        locationMapper.deleteById(id);
    }

    @Override
    public void delete(SystemLocation location) {
        UpdateWrapper<SystemLocation> deleteWrapper = new UpdateWrapper<>(location);
        locationMapper.delete(deleteWrapper);
    }

    @Override
    public void deleteByIdInBatch(List<Integer> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            return;
        }
        locationMapper.deleteBatchIds(ids);
    }

    @Override
    public void updateById(SystemLocation location) {
        locationMapper.updateById(location);
    }

    @Override
    public void updateByIdInBatch(List<SystemLocation> locations) {
        if (CollectionUtils.isEmpty(locations)) {
            return;
        }
        for (SystemLocation location: locations) {
            this.updateById(location);
        }
    }

    @Override
    public SystemLocation findById(Integer id) {
        return locationMapper.selectById(id);
    }

    @Override
    public SystemLocation findOne(SystemLocation location) {
        Wrapper<SystemLocation> wrapper = new QueryWrapper<>(location);
        return locationMapper.selectOne(wrapper);
    }

    @Override
    public List<SystemLocation> findList(SystemLocation location) {
        Wrapper<SystemLocation> wrapper = new QueryWrapper<>(location);
        return locationMapper.selectList(wrapper);
    }
  
    @Override
    public PageData<SystemLocation> findPage(PageWrap<SystemLocation> pageWrap) {
        IPage<SystemLocation> page = new Page<>(pageWrap.getPage(), pageWrap.getCapacity());
        QueryWrapper<SystemLocation> queryWrapper = new QueryWrapper<>();
        Utils.MP.blankToNull(pageWrap.getModel());
        if (pageWrap.getModel().getParentId() != null) {
            queryWrapper.lambda().eq(SystemLocation::getParentId, pageWrap.getModel().getParentId());
        }
        if (pageWrap.getModel().getLevel() != null) {
            queryWrapper.lambda().eq(SystemLocation::getLevel, pageWrap.getModel().getLevel());
        }
        if (pageWrap.getModel().getName() != null) {
            queryWrapper.lambda().like(SystemLocation::getName, pageWrap.getModel().getName());
        }
        if (pageWrap.getModel().getAreaCode() != null) {
            queryWrapper.lambda().like(SystemLocation::getAreaCode, pageWrap.getModel().getAreaCode());
        }
        if (pageWrap.getModel().getPostalCode() != null) {
            queryWrapper.lambda().like(SystemLocation::getPostalCode, pageWrap.getModel().getPostalCode());
        }
        for(PageWrap.SortData sortData: pageWrap.getSorts()) {
            if (sortData.getDirection().equalsIgnoreCase(PageWrap.DESC)) {
                queryWrapper.orderByDesc(sortData.getProperty());
            } else {
                queryWrapper.orderByAsc(sortData.getProperty());
            }
        }
        return PageData.from(locationMapper.selectPage(page, queryWrapper));
    }

    @Override
    public long count(SystemLocation location) {
        Wrapper<SystemLocation> wrapper = new QueryWrapper<>(location);
        return locationMapper.selectCount(wrapper);
    }
}
