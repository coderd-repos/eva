package com.eva.service.system.impl;

import com.eva.core.model.PageData;
import com.eva.core.model.PageWrap;
import com.eva.core.utils.Utils;
import com.eva.dao.system.SystemLoginLogMapper;
import com.eva.dao.system.model.SystemLoginLog;
import com.eva.service.system.SystemLoginLogService;
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
 * 登录日志Service实现
 * @author Eva.Caesar Liu
 * @date 2021/05/30 22:54
 */
@Service
public class SystemLoginLogServiceImpl implements SystemLoginLogService {

    @Autowired
    private SystemLoginLogMapper systemLoginLogMapper;

    @Override
    public Integer create(SystemLoginLog systemLoginLog) {
        systemLoginLogMapper.insert(systemLoginLog);
        return systemLoginLog.getId();
    }

    @Override
    public void deleteById(Integer id) {
        systemLoginLogMapper.deleteById(id);
    }

    @Override
    public void delete(SystemLoginLog systemLoginLog) {
        UpdateWrapper<SystemLoginLog> deleteWrapper = new UpdateWrapper<>(systemLoginLog);
        systemLoginLogMapper.delete(deleteWrapper);
    }

    @Override
    public void deleteByIdInBatch(List<Integer> ids) {
        if (CollectionUtils.isEmpty(ids)) return;
        systemLoginLogMapper.deleteBatchIds(ids);
    }

    @Override
    public void updateById(SystemLoginLog systemLoginLog) {
        systemLoginLogMapper.updateById(systemLoginLog);
    }

    @Override
    public void updateByIdInBatch(List<SystemLoginLog> systemLoginLogs) {
        if (CollectionUtils.isEmpty(systemLoginLogs)) return;
        for (SystemLoginLog systemLoginLog: systemLoginLogs) {
            this.updateById(systemLoginLog);
        }
    }

    @Override
    public SystemLoginLog findById(Integer id) {
        return systemLoginLogMapper.selectById(id);
    }

    @Override
    public SystemLoginLog findOne(SystemLoginLog systemLoginLog) {
        Wrapper<SystemLoginLog> wrapper = new QueryWrapper<>(systemLoginLog);
        return systemLoginLogMapper.selectOne(wrapper);
    }

    @Override
    public List<SystemLoginLog> findList(SystemLoginLog systemLoginLog) {
        Wrapper<SystemLoginLog> wrapper = new QueryWrapper<>(systemLoginLog);
        return systemLoginLogMapper.selectList(wrapper);
    }
  
    @Override
    public PageData<SystemLoginLog> findPage(PageWrap<SystemLoginLog> pageWrap) {
        IPage<SystemLoginLog> page = new Page<>(pageWrap.getPage(), pageWrap.getCapacity());
        QueryWrapper<SystemLoginLog> queryWrapper = new QueryWrapper<>(Utils.MP.blankToNull(pageWrap.getModel()));
        for(PageWrap.SortData sortData: pageWrap.getSorts()) {
            if (sortData.getDirection().equalsIgnoreCase("DESC")) {
                queryWrapper.orderByDesc(sortData.getProperty());
            } else {
                queryWrapper.orderByAsc(sortData.getProperty());
            }
        }
        return PageData.from(systemLoginLogMapper.selectPage(page, queryWrapper));
    }

    @Override
    public long count(SystemLoginLog systemLoginLog) {
        Wrapper<SystemLoginLog> wrapper = new QueryWrapper<>(systemLoginLog);
        return systemLoginLogMapper.selectCount(wrapper);
    }
}