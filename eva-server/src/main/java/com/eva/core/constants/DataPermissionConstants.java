package com.eva.core.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 数据权限常量
 * @author Eva.Caesar Liu
 * @date 2021-06-11 23:27
 */
public interface DataPermissionConstants {

    /**
     * 数据权限模块
     */
    @Getter
    @AllArgsConstructor
    enum Module {
        DEPARTMENT("DEPARTMENT", "部门管理"),
        POSITION("POSITION", "岗位管理"),
        ;
        private String businessCode;

        private String moduleName;

        public static List<Map<String, Object>> valueList () {
            List<Map<String, Object>> list = new ArrayList<>();
            for (Module module: Module.values()) {
                list.add(new HashMap<String, Object>(){{
                    this.put("businessCode", module.getBusinessCode());
                    this.put("moduleName", module.getModuleName());
                }});
            }
            return list;
        }
    }

    /**
     * 数据权限类型
     */
    @Getter
    @AllArgsConstructor
    enum Type {
        ALL((byte)0, "全部"),
        CUSTOM((byte)1, "自定义"),
        USER_RELATION((byte)2, "用户所属及其子孙节点"),
        USER_CHILDREN((byte)3, "用户所属的子孙节点"),
        USER_CHILD((byte)4, "用户所属的子节点"),
        USER((byte)5, "仅用户所属"),
        ;

        private byte code;

        private String remark;

        public static List<Map<String, Object>> valueList () {
            List<Map<String, Object>> list = new ArrayList<>();
            for (Type type: Type.values()) {
                list.add(new HashMap<String, Object>(){{
                    this.put("code", type.getCode());
                    this.put("remark", type.getRemark());
                }});
            }
            return list;
        }
    }
}
