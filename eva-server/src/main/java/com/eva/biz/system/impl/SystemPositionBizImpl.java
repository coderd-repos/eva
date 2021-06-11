package com.eva.biz.system.impl;

import com.eva.biz.system.SystemPositionBiz;
import com.eva.core.exception.BusinessException;
import com.eva.core.constants.ResponseStatus;
import com.eva.dao.system.model.SystemPosition;
import com.eva.dao.system.vo.SystemPositionListVO;
import com.eva.service.system.SystemPositionService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class SystemPositionBizImpl implements SystemPositionBiz {

    @Autowired
    private SystemPositionService systemPositionService;

    @Override
    public Integer create(SystemPosition systemPosition) {
        SystemPosition queryDto = new SystemPosition();
        queryDto.setCode(systemPosition.getCode());
        queryDto.setDeleted(Boolean.FALSE);
        SystemPosition position = systemPositionService.findOne(queryDto);
        if (position != null) {
            throw new BusinessException(ResponseStatus.DATA_EXISTS.getCode(), "岗位编码已存在");
        }
        return systemPositionService.create(systemPosition);
    }

    @Override
    public void updateById(SystemPosition systemPosition) {
        SystemPosition queryDto = new SystemPosition();
        queryDto.setCode(systemPosition.getCode());
        queryDto.setDeleted(Boolean.FALSE);
        SystemPosition position = systemPositionService.findOne(queryDto);
        if (position != null && !position.getId().equals(systemPosition.getId())) {
            throw new BusinessException(ResponseStatus.DATA_EXISTS.getCode(), "岗位编码已存在");
        }
        systemPositionService.updateById(systemPosition);
    }

    @Override
    public List<SystemPositionListVO> findTree() {
        List<SystemPositionListVO> positions = systemPositionService.findList();
        List<SystemPositionListVO> rootPositions = new ArrayList<>();
        // 添加根菜单
        for (SystemPositionListVO position : positions) {
            if (position.getParentId() == null) {
                SystemPositionListVO rootMenu = new SystemPositionListVO();
                BeanUtils.copyProperties(position, rootMenu, "children");
                rootMenu.setChildren(new ArrayList<>());
                rootPositions.add(rootMenu);
            }
        }
        positions.removeIf(position -> position.getParentId() == null);
        for (SystemPositionListVO child : rootPositions) {
            this.fillChildren(child, positions);
        }
        return rootPositions;
    }

    @Override
    public void deleteById(Integer id) {
        List<Integer> ids = systemPositionService.findChildren(id);
        ids.add(id);
        systemPositionService.deleteByIdInBatch(ids);
    }

    @Override
    @Transactional
    public void deleteByIdInBatch(List<Integer> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            return;
        }
        for (Integer id : ids) {
            this.deleteById(id);
        }
    }

    /**
     * 填充子岗位
     * @author Eva.Caesar Liu
     * @date 2021-03-29 16:09
     */
    private void fillChildren(SystemPositionListVO parent, List<SystemPositionListVO> departments) {
        if (departments.size() == 0) {
            return;
        }
        List<Integer> handledIds = new ArrayList<>();
        for (SystemPositionListVO department : departments) {
            if (parent.getId().equals(department.getParentId())) {
                SystemPositionListVO child = new SystemPositionListVO();
                BeanUtils.copyProperties(department, child, "children");
                child.setChildren(new ArrayList<>());
                parent.getChildren().add(child);
                handledIds.add(department.getId());
            }
        }
        departments.removeIf(menu -> handledIds.contains(menu.getId()));
        parent.setHasChildren(Boolean.TRUE);
        if (parent.getChildren().size() > 0) {
            parent.setHasChildren(Boolean.FALSE);
            for (SystemPositionListVO child : parent.getChildren()) {
                this.fillChildren(child, departments);
            }
        }
    }
}
