package com.eva.service.aware;

import com.eva.core.aware.DataPermissionMapping;
import com.eva.core.aware.DefaultDataPermissionAware;
import com.eva.core.constants.DataPermissionConstants;
import com.eva.dao.system.model.SystemPosition;
import com.eva.dao.system.model.SystemPositionUser;
import com.eva.dao.system.vo.SystemPositionListVO;
import com.eva.service.system.SystemPositionService;
import com.eva.service.system.SystemPositionUserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
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
    public DataPermissionConstants.Module getModule() {
        return DataPermissionConstants.Module.POSITION;
    }

    /**
     * 自定义
     * @param customData 自定义数据ID集
     *
     * @return List<SystemPositionListVO>
     */
    @DataPermissionMapping(value = DataPermissionConstants.Type.POSITION_CUSTOM, priority = 1, injectCustomData = true)
    public List<SystemPositionListVO> custom(String customData) {
        if (StringUtils.isBlank(customData)) {
            return Collections.emptyList();
        }
        List<Integer> ids = new ArrayList<>();
        String[] stringIds = customData.split(",");
        for(String stringId : stringIds) {
            ids.add(Integer.valueOf(stringId));
        }
        return this.getRootList(toSystemPositionListVOs(systemPositionService.findByIds(ids)));
    }

    /**
     * 用户所属岗位及其子孙岗位
     * @param userId 用户ID
     *
     * @return List<SystemPositionListVO>
     */
    @DataPermissionMapping(value = DataPermissionConstants.Type.POSITION_CHILDREN, priority = 2, injectUser = true)
    public List<SystemPositionListVO> userChildren(Integer userId) {
        return this.getRootList(getUserChildren(userId));
    }

    /**
     * 用户所属岗位及其子岗位
     * @param userId 用户ID
     *
     * @return List<SystemPositionListVO>
     */
    @DataPermissionMapping(value = DataPermissionConstants.Type.POSITION_CHILD, priority = 3, injectUser = true)
    public List<SystemPositionListVO> userChild(Integer userId) {
        List<SystemPositionListVO> children = this.getRootList(getUserChildren(userId));
        for (SystemPositionListVO root : children) {
            if (CollectionUtils.isEmpty(root.getChildren())) {
                continue;
            }
            for (SystemPositionListVO child : root.getChildren()) {
                if (CollectionUtils.isEmpty(child.getChildren())) {
                    continue;
                }
                child.setHasChildren(Boolean.TRUE);
                child.setChildren(null);
            }
        }
        return children;
    }

    /**
     * 仅用户所属岗位
     * @param userId 用户ID
     *
     * @return List<SystemPositionListVO>
     */
    @DataPermissionMapping(value = DataPermissionConstants.Type.POSITION, priority = 4, injectUser = true)
    public List<SystemPositionListVO> user(Integer userId) {
        List<SystemPositionListVO> userPositions = this.getUserPositions(userId);
        if (CollectionUtils.isEmpty(userPositions)) {
            return Collections.emptyList();
        }
        return this.getRootList(userPositions);
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
        for (SystemPositionListVO rootPosition : rootPositions) {
            positions.removeIf(position -> position.getId().equals(rootPosition.getId()));
        }
        // 填充子岗位
        for (SystemPositionListVO child : rootPositions) {
            this.fillChildren(child, positions);
        }
        return rootPositions;
    }

    /**
     * 获取用户岗位及其子孙岗位
     */
    private List<SystemPositionListVO> getUserChildren(Integer userId) {
        List<SystemPositionListVO> userPositions = this.getUserPositions(userId);
        if (CollectionUtils.isEmpty(userPositions)) {
            return Collections.emptyList();
        }
        // 查询用户所在岗位以下岗位
        List<SystemPositionListVO> positionListVos = new ArrayList<>();
        for (SystemPositionListVO userPosition : userPositions) {
            List<SystemPosition> underPositions = systemPositionService.findByIds(systemPositionService.findChildren(userPosition.getId()));
            for (SystemPosition underPosition : underPositions) {
                if(positionListVos.stream().anyMatch(item -> item.getId().equals(underPosition.getId()))) {
                    continue;
                }
                SystemPositionListVO vo = new SystemPositionListVO();
                BeanUtils.copyProperties(underPosition, vo);
                positionListVos.add(vo);
            }
            if(positionListVos.stream().noneMatch(item -> item.getId().equals(userPosition.getId()))) {
                positionListVos.add(userPosition);
            }
        }
        return positionListVos;
    }

    /**
     * 获取用户岗位
     */
    private List<SystemPositionListVO> getUserPositions(Integer userId) {
        SystemPositionUser queryDto = new SystemPositionUser();
        queryDto.setUserId(userId);
        queryDto.setDeleted(Boolean.FALSE);
        List<SystemPositionUser> positionUsers = systemPositionUserService.findList(queryDto);
        if (CollectionUtils.isEmpty(positionUsers)) {
            return null;
        }
        List<Integer> ids = new ArrayList<>();
        for (SystemPositionUser positionUser : positionUsers) {
            ids.add(positionUser.getPositionId());
        }
        return toSystemPositionListVOs(systemPositionService.findByIds(ids));
    }

    /**
     * 转为ListVO对象集合
     */
    private List<SystemPositionListVO> toSystemPositionListVOs (List<SystemPosition> systemPositions) {
        List<SystemPositionListVO> positionListVOs = new ArrayList<>();
        for (SystemPosition systemPosition : systemPositions) {
            SystemPositionListVO positionListVO = new SystemPositionListVO();
            BeanUtils.copyProperties(systemPosition, positionListVO);
            positionListVOs.add(positionListVO);
        }
        return positionListVOs;
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
