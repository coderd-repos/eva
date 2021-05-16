package com.yiwa.service.system.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yiwa.core.utils.WrapperUtil;
import com.yiwa.core.model.PageData;
import com.yiwa.core.model.PageWrap;
import com.yiwa.dao.system.SystemDictDataMapper;
import com.yiwa.dao.system.dto.QuerySystemDictDataDTO;
import com.yiwa.dao.system.model.SystemDictData;
import com.yiwa.dao.system.vo.SystemDictDataListVO;
import com.yiwa.service.system.SystemDictDataService;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * 字典数据Service实现
 * @author Caesar Liu
 * @date 2021/05/16 20:18
 */
@Service
public class SystemDictDataServiceImpl implements SystemDictDataService {

    @Autowired
    private SystemDictDataMapper systemDictDataMapper;

    @Override
    public Integer create(SystemDictData systemDictData) {
        systemDictDataMapper.insert(systemDictData);
        return systemDictData.getId();
    }

    @Override
    public void deleteById(Integer id) {
        systemDictDataMapper.deleteById(id);
    }

    @Override
    public void deleteByIdInBatch(List<Integer> ids) {
        if (CollectionUtils.isEmpty(ids)) return;
        systemDictDataMapper.deleteBatchIds(ids);
    }

    @Override
    public void updateById(SystemDictData systemDictData) {
        systemDictDataMapper.updateById(systemDictData);
    }

    @Override
    public void updateByIdInBatch(List<SystemDictData> systemDictDatas) {
        if (CollectionUtils.isEmpty(systemDictDatas)) return;
        for (SystemDictData systemDictData: systemDictDatas) {
            this.updateById(systemDictData);
        }
    }

    @Override
    public SystemDictData findById(Integer id) {
        return systemDictDataMapper.selectById(id);
    }

    @Override
    public SystemDictData findOne(SystemDictData systemDictData) {
        Wrapper<SystemDictData> wrapper = new QueryWrapper<>(systemDictData);
        return systemDictDataMapper.selectOne(wrapper);
    }

    @Override
    public List<SystemDictData> findList(SystemDictData systemDictData) {
        Wrapper<SystemDictData> wrapper = new QueryWrapper<>(systemDictData);
        return systemDictDataMapper.selectList(wrapper);
    }
  
    @Override
    public PageData<SystemDictDataListVO> findPage(PageWrap<QuerySystemDictDataDTO> pageWrap) {
        PageHelper.startPage(pageWrap.getPage(), pageWrap.getCapacity());
        return PageData.from(new PageInfo<>(systemDictDataMapper.selectList(pageWrap.getModel())));
    }

    @Override
    public long count(SystemDictData systemDictData) {
        Wrapper<SystemDictData> wrapper = new QueryWrapper<>(systemDictData);
        return systemDictDataMapper.selectCount(wrapper);
    }
}