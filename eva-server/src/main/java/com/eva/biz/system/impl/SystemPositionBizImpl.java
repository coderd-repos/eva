package com.eva.biz.system.impl;

import com.eva.biz.system.SystemPositionBiz;
import com.eva.core.exception.BusinessException;
import com.eva.core.model.ResponseStatus;
import com.eva.dao.system.model.SystemPosition;
import com.eva.dao.system.vo.SystemPositionListVO;
import com.eva.service.system.SystemPositionService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    /**
     * 填充子岗位
     * @author Eva
     * @date 2021-03-29 16:09
     */
    private void fillChildren(SystemPositionListVO parentMenu, List<SystemPositionListVO> departments) {
        if (departments.size() == 0) {
            return;
        }
        List<Integer> handledIds = new ArrayList<>();
        for (SystemPositionListVO department : departments) {
            if (department.getParentId().equals(parentMenu.getId())) {
                SystemPositionListVO child = new SystemPositionListVO();
                BeanUtils.copyProperties(department, child, "children");
                child.setChildren(new ArrayList<>());
                parentMenu.getChildren().add(child);
                handledIds.add(department.getId());
            }
        }
        departments.removeIf(menu -> handledIds.contains(menu.getId()));
        parentMenu.setHasChildren(Boolean.TRUE);
        if (parentMenu.getChildren().size() > 0) {
            parentMenu.setHasChildren(Boolean.FALSE);
            for (SystemPositionListVO child : parentMenu.getChildren()) {
                this.fillChildren(child, departments);
            }
        }
    }
}
