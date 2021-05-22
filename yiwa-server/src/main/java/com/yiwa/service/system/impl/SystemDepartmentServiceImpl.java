package com.yiwa.service.system.impl;

import com.yiwa.core.utils.WrapperUtil;
import com.yiwa.core.model.PageData;
import com.yiwa.core.model.PageWrap;
import com.yiwa.dao.system.SystemDepartmentMapper;
import com.yiwa.dao.system.model.SystemDepartment;
import com.yiwa.dao.system.vo.SystemDepartmentListVO;
import com.yiwa.service.system.SystemDepartmentService;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service实现
 * @author Yiwa
 * @date 2021/05/16 11:59
 */
@Service
public class SystemDepartmentServiceImpl implements SystemDepartmentService {

    @Autowired
    private SystemDepartmentMapper systemDepartmentMapper;

    @Override
    public Integer create(SystemDepartment systemDepartment) {
        systemDepartmentMapper.insert(systemDepartment);
        return systemDepartment.getId();
    }

    @Override
    public void deleteById(Integer id) {
        SystemDepartment systemDepartment = new SystemDepartment();
        systemDepartment.setId(id);
        systemDepartment.setDeleted(Boolean.TRUE);
        this.updateById(systemDepartment);
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
    public void updateById(SystemDepartment systemDepartment) {
        systemDepartmentMapper.updateById(systemDepartment);
    }

    @Override
    public void updateByIdInBatch(List<SystemDepartment> systemDepartments) {
        if (CollectionUtils.isEmpty(systemDepartments)) return;
        for (SystemDepartment systemDepartment: systemDepartments) {
            this.updateById(systemDepartment);
        }
    }

    @Override
    public SystemDepartment findById(Integer id) {
        return systemDepartmentMapper.selectById(id);
    }

    @Override
    public SystemDepartment findOne(SystemDepartment systemDepartment) {
        Wrapper<SystemDepartment> wrapper = new QueryWrapper<>(systemDepartment);
        return systemDepartmentMapper.selectOne(wrapper);
    }

    @Override
    public List<SystemDepartmentListVO> findList() {
        return systemDepartmentMapper.selectManageList();
    }
  
    @Override
    public PageData<SystemDepartment> findPage(PageWrap<SystemDepartment> pageWrap) {
        IPage<SystemDepartment> page = new Page<>(pageWrap.getPage(), pageWrap.getCapacity());
        QueryWrapper<SystemDepartment> queryWrapper = new QueryWrapper<>(WrapperUtil.blankToNull(pageWrap.getModel()));
        for(PageWrap.SortData sortData: pageWrap.getSorts()) {
            if (sortData.getDirection().equalsIgnoreCase("DESC")) {
                queryWrapper.orderByDesc(sortData.getProperty());
            } else {
                queryWrapper.orderByAsc(sortData.getProperty());
            }
        }
        return PageData.from(systemDepartmentMapper.selectPage(page, queryWrapper));
    }

    @Override
    public long count(SystemDepartment systemDepartment) {
        Wrapper<SystemDepartment> wrapper = new QueryWrapper<>(systemDepartment);
        return systemDepartmentMapper.selectCount(wrapper);
    }

    @Override
    public List<Integer> findChildren(Integer departmentId) {
        List<Integer> pool = new ArrayList<>();
        this.fillChildren(pool, Arrays.asList(departmentId));
        return pool;
    }

    /**
     * 获取子部门ID
     * @author Caesar Liu
     * @date 2021-05-22 23:22
     */
    private void fillChildren(List<Integer> pool, List<Integer> parentIds) {
        QueryWrapper<SystemDepartment> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(SystemDepartment::getDeleted, Boolean.FALSE).in(SystemDepartment::getParentId, parentIds);
        List<SystemDepartment> departments = systemDepartmentMapper.selectList(queryWrapper);
        List<Integer> ids = departments.stream().map(SystemDepartment::getId).collect(Collectors.toList());
        if (ids.size() > 0) {
            pool.addAll(ids);
            this.fillChildren(pool, ids);
        }
    }
}