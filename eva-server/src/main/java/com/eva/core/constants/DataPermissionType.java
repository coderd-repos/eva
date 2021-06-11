package com.eva.core.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 数据权限类型
 * @author Eva.Caesar Liu
 * @date 2021-06-11 20:34
 */
@Getter
@AllArgsConstructor
public enum DataPermissionType {

    ALL((byte)0, "全部"),
    CUSTOM((byte)1, "自定义"),
    USER((byte)2, "仅用户所属"),
    USER_RELATION((byte)3, "用户所属及其子数据"),
    ;

    private byte code;

    private String remark;
}
