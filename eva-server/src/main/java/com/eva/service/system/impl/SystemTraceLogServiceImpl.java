package com.eva.service.system.impl;

import com.eva.core.utils.WrapperUtil;
import com.eva.core.model.PageData;
import com.eva.core.model.PageWrap;
import com.eva.dao.system.SystemTraceLogMapper;
import com.eva.dao.system.model.SystemTraceLog;
import com.eva.service.system.SystemTraceLogService;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * 跟踪日志Service实现
 * @author Eva
 * @date 2021/05/29 13:53
 */
@Service
public class SystemTraceLogServiceImpl implements SystemTraceLogService {

    @Autowired
    private SystemTraceLogMapper systemTraceLogMapper;

    @Override
    public Integer create(SystemTraceLog systemTraceLog) {
        systemTraceLogMapper.insert(systemTraceLog);
        return systemTraceLog.getId();
    }

    @Override
    public void deleteById(Integer id) {
        systemTraceLogMapper.deleteById(id);
    }

    @Override
    public void deleteByIdInBatch(List<Integer> ids) {
        if (CollectionUtils.isEmpty(ids)) return;
        systemTraceLogMapper.deleteBatchIds(ids);
    }

    @Override
    public void updateById(SystemTraceLog systemTraceLog) {
        systemTraceLogMapper.updateById(systemTraceLog);
    }

    @Override
    public void updateByIdInBatch(List<SystemTraceLog> systemTraceLogs) {
        if (CollectionUtils.isEmpty(systemTraceLogs)) return;
        for (SystemTraceLog systemTraceLog: systemTraceLogs) {
            this.updateById(systemTraceLog);
        }
    }

    @Override
    public SystemTraceLog findById(Integer id) {
        return systemTraceLogMapper.selectById(id);
    }

    @Override
    public SystemTraceLog findOne(SystemTraceLog systemTraceLog) {
        Wrapper<SystemTraceLog> wrapper = new QueryWrapper<>(systemTraceLog);
        return systemTraceLogMapper.selectOne(wrapper);
    }

    @Override
    public List<SystemTraceLog> findList(SystemTraceLog systemTraceLog) {
        Wrapper<SystemTraceLog> wrapper = new QueryWrapper<>(systemTraceLog);
        return systemTraceLogMapper.selectList(wrapper);
    }
  
    @Override
    public PageData<SystemTraceLog> findPage(PageWrap<SystemTraceLog> pageWrap) {
        IPage<SystemTraceLog> page = new Page<>(pageWrap.getPage(), pageWrap.getCapacity());
        QueryWrapper<SystemTraceLog> queryWrapper = new QueryWrapper<>(WrapperUtil.blankToNull(pageWrap.getModel()));
        for(PageWrap.SortData sortData: pageWrap.getSorts()) {
            if (sortData.getDirection().equalsIgnoreCase(PageWrap.DESC)) {
                queryWrapper.orderByDesc(sortData.getProperty());
            } else {
                queryWrapper.orderByAsc(sortData.getProperty());
            }
        }
        return PageData.from(systemTraceLogMapper.selectPage(page, queryWrapper));
    }

    @Override
    public long count(SystemTraceLog systemTraceLog) {
        Wrapper<SystemTraceLog> wrapper = new QueryWrapper<>(systemTraceLog);
        return systemTraceLogMapper.selectCount(wrapper);
    }
}