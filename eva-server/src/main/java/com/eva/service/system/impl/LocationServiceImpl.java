package com.eva.service.system.impl;

import com.eva.core.model.PageData;
import com.eva.core.model.PageWrap;
import com.eva.core.utils.Utils;
import com.eva.dao.system.LocationMapper;
import com.eva.dao.system.model.Location;
import com.eva.service.system.LocationService;
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
 * 地区表Service实现
 * @author Eva
 * @date 2021/06/10 17:09
 */
@Service
public class LocationServiceImpl implements LocationService {

    @Autowired
    private LocationMapper locationMapper;

    @Override
    public Integer create(Location location) {
        locationMapper.insert(location);
        return location.getId();
    }

    @Override
    public void deleteById(Integer id) {
        locationMapper.deleteById(id);
    }

    @Override
    public void delete(Location location) {
        UpdateWrapper<Location> deleteWrapper = new UpdateWrapper<>(location);
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
    public void updateById(Location location) {
        locationMapper.updateById(location);
    }

    @Override
    public void updateByIdInBatch(List<Location> locations) {
        if (CollectionUtils.isEmpty(locations)) {
            return;
        }
        for (Location location: locations) {
            this.updateById(location);
        }
    }

    @Override
    public Location findById(Integer id) {
        return locationMapper.selectById(id);
    }

    @Override
    public Location findOne(Location location) {
        Wrapper<Location> wrapper = new QueryWrapper<>(location);
        return locationMapper.selectOne(wrapper);
    }

    @Override
    public List<Location> findList(Location location) {
        Wrapper<Location> wrapper = new QueryWrapper<>(location);
        return locationMapper.selectList(wrapper);
    }
  
    @Override
    public PageData<Location> findPage(PageWrap<Location> pageWrap) {
        IPage<Location> page = new Page<>(pageWrap.getPage(), pageWrap.getCapacity());
        QueryWrapper<Location> queryWrapper = new QueryWrapper<>();
        Utils.MP.blankToNull(pageWrap.getModel());
        if (pageWrap.getModel().getParentId() != null) {
            queryWrapper.lambda().eq(Location::getParentId, pageWrap.getModel().getParentId());
        }
        if (pageWrap.getModel().getLevel() != null) {
            queryWrapper.lambda().eq(Location::getLevel, pageWrap.getModel().getLevel());
        }
        if (pageWrap.getModel().getName() != null) {
            queryWrapper.lambda().like(Location::getName, pageWrap.getModel().getName());
        }
        if (pageWrap.getModel().getAreaCode() != null) {
            queryWrapper.lambda().like(Location::getAreaCode, pageWrap.getModel().getAreaCode());
        }
        if (pageWrap.getModel().getPostalCode() != null) {
            queryWrapper.lambda().like(Location::getPostalCode, pageWrap.getModel().getPostalCode());
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
    public long count(Location location) {
        Wrapper<Location> wrapper = new QueryWrapper<>(location);
        return locationMapper.selectCount(wrapper);
    }
}
