package com.eva.service.aware;

import com.eva.dao.system.model.SystemPosition;
import com.eva.dao.system.model.SystemPositionUser;
import com.eva.dao.system.vo.SystemPositionListVO;
import com.eva.service.system.SystemPositionService;
import com.eva.service.system.SystemPositionUserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * 岗位数据权限控制
 * @author Eva.Caesar Liu
 * @date 2021-06-11 23:43
 */
@Component
public class PositionDataPermissionAware extends DefaultDataPermissionAware<SystemPositionListVO> {

    @Autowired
    private SystemPositionService systemPositionService;

    @Autowired
    private SystemPositionUserService systemPositionUserService;

    @Override
    public List<SystemPositionListVO> all() {
        return this.getRootList(systemPositionService.findList());
    }

    @Override
    public List<SystemPositionListVO> custom(String customData) {
        List<Integer> ids = new ArrayList<>();
        String[] stringIds = customData.split(",");
        for(String stringId : stringIds) {
            ids.add(Integer.valueOf(stringId));
        }
        List<SystemPositionListVO> positionListVo = new ArrayList<>();
        List<SystemPosition> systemDepartments = systemPositionService.findByIds(ids);
        for (SystemPosition systemDepartment : systemDepartments) {
            SystemPositionListVO vo = new SystemPositionListVO();
            BeanUtils.copyProperties(systemDepartment, vo);
            positionListVo.add(vo);
        }
        return this.getRootList(positionListVo);
    }

    @Override
    public List<SystemPositionListVO> user(Integer userId) {
        SystemPositionListVO userPosition = this.getUserPosition(userId);
        if (userPosition == null) {
            return Collections.emptyList();
        }
        return Arrays.asList(userPosition);
    }

    @Override
    public List<SystemPositionListVO> userRelation(Integer userId) {
        SystemPositionListVO userPosition = this.getUserPosition(userId);
        if (userPosition == null) {
            return Collections.emptyList();
        }
        // 查询用户所在岗位以下岗位
        List<SystemPositionListVO> positionListVo = new ArrayList<>();
        List<SystemPosition> systemPositions = systemPositionService.findByIds(systemPositionService.findChildren(userPosition.getId()));
        for (SystemPosition systemDepartment : systemPositions) {
            SystemPositionListVO vo = new SystemPositionListVO();
            BeanUtils.copyProperties(systemDepartment, vo);
            positionListVo.add(vo);
        }
        positionListVo.add(0, userPosition);
        return this.getRootList(positionListVo);
    }

    /**
     * 获取根岗位
     */
    private List<SystemPositionListVO> getRootList(List<SystemPositionListVO> positions) {
        List<SystemPositionListVO> rootPositions = new ArrayList<>();
        // 添加根岗位
        for (SystemPositionListVO currentPosition : positions) {
            boolean hasParent = false;
            for (SystemPositionListVO position: positions) {
                if (position.getId().equals(currentPosition.getParentId())) {
                    hasParent = true;
                    break;
                }
            }
            if (!hasParent) {
                rootPositions.add(currentPosition);
            }
        }
        // 移除根岗位
        for (SystemPositionListVO rootDepartment : rootPositions) {
            positions.removeIf(position -> position.getId().equals(rootDepartment.getId()));
        }
        // 填充子岗位
        for (SystemPositionListVO child : rootPositions) {
            this.fillChildren(child, positions);
        }
        return rootPositions;
    }

    /**
     * 获取用户岗位
     */
    private SystemPositionListVO getUserPosition(Integer userId) {
        SystemPositionUser queryDto = new SystemPositionUser();
        queryDto.setUserId(userId);
        queryDto.setDeleted(Boolean.FALSE);
        SystemPositionUser positionUser = systemPositionUserService.findOne(queryDto);
        if (positionUser == null) {
            return null;
        }
        SystemPosition systemPosition = systemPositionService.findById(positionUser.getPositionId());
        SystemPositionListVO positionListVO = new SystemPositionListVO();
        BeanUtils.copyProperties(systemPosition, positionListVO);
        return positionListVO;
    }

    /**
     * 填充子岗位
     */
    private void fillChildren(SystemPositionListVO parent, List<SystemPositionListVO> positions) {
        if (positions.size() == 0) {
            return;
        }
        if (parent.getChildren() == null) {
            parent.setChildren(new ArrayList<>());
        }
        List<Integer> handledIds = new ArrayList<>();
        for (SystemPositionListVO position : positions) {
            if (parent.getId().equals(position.getParentId())) {
                SystemPositionListVO child = new SystemPositionListVO();
                BeanUtils.copyProperties(position, child, "children");
                child.setChildren(new ArrayList<>());
                parent.getChildren().add(child);
                handledIds.add(position.getId());
            }
        }
        positions.removeIf(menu -> handledIds.contains(menu.getId()));
        parent.setHasChildren(Boolean.TRUE);
        if (parent.getChildren().size() > 0) {
            parent.setHasChildren(Boolean.FALSE);
            for (SystemPositionListVO child : parent.getChildren()) {
                this.fillChildren(child, positions);
            }
        }
    }
}
