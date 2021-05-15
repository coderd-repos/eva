package com.yiwa.dao.system.vo;

import com.yiwa.dao.system.model.SystemMenu;
import com.yiwa.dao.system.model.SystemUser;
import lombok.Data;

import java.util.List;

/**
 * 菜单列表视图对象
 * @author Caesar Liu
 * @date 2021-03-29 20:21
 */
@Data
public class SystemMenuListVO extends SystemMenu {

    private List<SystemMenuListVO> children;

    private Boolean hasChildren;

    private SystemUser createUserInfo;

    private SystemUser updateUserInfo;
}
