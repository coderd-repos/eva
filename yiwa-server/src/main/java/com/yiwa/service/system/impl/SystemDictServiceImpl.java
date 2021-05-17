package com.yiwa.service.system.impl;

import com.github.pagehelper.PageInfo;
import com.yiwa.core.utils.WrapperUtil;
import com.yiwa.core.model.PageData;
import com.yiwa.core.model.PageWrap;
import com.yiwa.dao.system.SystemDictMapper;
import com.yiwa.dao.system.dto.QuerySystemDictDTO;
import com.yiwa.dao.system.model.SystemDict;
import com.yiwa.dao.system.vo.SystemDictListVO;
import com.yiwa.service.system.SystemDictService;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * 字典Service实现
 * @author Caesar Liu
 * @date 2021/05/16 17:40
 */
@Service
public class SystemDictServiceImpl implements SystemDictService {

    @Autowired
    private SystemDictMapper systemDictMapper;

    @Override
    public Integer create(SystemDict systemDict) {
        systemDictMapper.insert(systemDict);
        return systemDict.getId();
    }

    @Override
    public void deleteById(Integer id) {
        systemDictMapper.deleteById(id);
    }

    @Override
    public void deleteByIdInBatch(List<Integer> ids) {
        if (CollectionUtils.isEmpty(ids)) return;
        systemDictMapper.deleteBatchIds(ids);
    }

    @Override
    public void updateById(SystemDict systemDict) {
        systemDictMapper.updateById(systemDict);
    }

    @Override
    public void updateByIdInBatch(List<SystemDict> systemDicts) {
        if (CollectionUtils.isEmpty(systemDicts)) return;
        for (SystemDict systemDict: systemDicts) {
            this.updateById(systemDict);
        }
    }

    @Override
    public SystemDict findById(Integer id) {
        return systemDictMapper.selectById(id);
    }

    @Override
    public SystemDict findOne(SystemDict systemDict) {
        Wrapper<SystemDict> wrapper = new QueryWrapper<>(systemDict);
        return systemDictMapper.selectOne(wrapper);
    }

    @Override
    public List<SystemDict> findList(SystemDict systemDict) {
        Wrapper<SystemDict> wrapper = new QueryWrapper<>(systemDict);
        return systemDictMapper.selectList(wrapper);
    }
  
    @Override
    public PageData<SystemDictListVO> findPage(PageWrap<QuerySystemDictDTO> pageWrap) {
        return PageData.from(new PageInfo<>(systemDictMapper.selectManageList(pageWrap.getModel())));
    }

    @Override
    public long count(SystemDict systemDict) {
        Wrapper<SystemDict> wrapper = new QueryWrapper<>(systemDict);
        return systemDictMapper.selectCount(wrapper);
    }
}