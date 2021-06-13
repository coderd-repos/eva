SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for SYSTEM_DEPARTMENT
-- ----------------------------
DROP TABLE IF EXISTS `SYSTEM_DEPARTMENT`;
CREATE TABLE `SYSTEM_DEPARTMENT` (
  `ID` int NOT NULL AUTO_INCREMENT COMMENT '主键',
  `PARENT_ID` int DEFAULT NULL COMMENT '上级部门',
  `CODE` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '部门编码',
  `NAME` varchar(50) NOT NULL COMMENT '部门名称',
  `PHONE` varchar(20) DEFAULT NULL COMMENT '联系电话',
  `EMAIL` varchar(200) DEFAULT NULL COMMENT '部门邮箱',
  `CREATE_USER` int NOT NULL COMMENT '创建人',
  `CREATE_TIME` datetime NOT NULL COMMENT '创建时间',
  `UPDATE_USER` int DEFAULT NULL COMMENT '更新人',
  `UPDATE_TIME` datetime DEFAULT NULL COMMENT '更新时间',
  `DELETED` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='部门';

-- ----------------------------
-- Table structure for SYSTEM_DEPARTMENT_USER
-- ----------------------------
DROP TABLE IF EXISTS `SYSTEM_DEPARTMENT_USER`;
CREATE TABLE `SYSTEM_DEPARTMENT_USER` (
  `ID` int NOT NULL AUTO_INCREMENT COMMENT '主键',
  `DEPARTMENT_ID` int NOT NULL COMMENT '部门ID',
  `USER_ID` int NOT NULL COMMENT '用户ID',
  `OPERA_USER` int NOT NULL COMMENT '操作人',
  `OPERA_TIME` datetime NOT NULL COMMENT '操作时间',
  `DELETED` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否已删除',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='部门用户';

-- ----------------------------
-- Table structure for SYSTEM_DICT
-- ----------------------------
DROP TABLE IF EXISTS `SYSTEM_DICT`;
CREATE TABLE `SYSTEM_DICT` (
  `ID` int NOT NULL AUTO_INCREMENT COMMENT '主键',
  `CODE` varchar(50) NOT NULL COMMENT '字典编码',
  `NAME` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '字典名称',
  `REMARK` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '备注',
  `CREATE_USER` int NOT NULL COMMENT '创建人',
  `CREATE_TIME` datetime NOT NULL COMMENT '创建时间',
  `UPDATE_USER` int DEFAULT NULL COMMENT '更新人',
  `UPDATE_TIME` datetime DEFAULT NULL COMMENT '更新时间',
  `DELETED` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=100 DEFAULT CHARSET=utf8 COMMENT='字典';

-- ----------------------------
-- Table structure for SYSTEM_DICT_DATA
-- ----------------------------
DROP TABLE IF EXISTS `SYSTEM_DICT_DATA`;
CREATE TABLE `SYSTEM_DICT_DATA` (
  `ID` int NOT NULL AUTO_INCREMENT COMMENT '主键',
  `DICT_ID` int NOT NULL COMMENT '所属字典',
  `CODE` varchar(50) NOT NULL COMMENT '数据值',
  `LABEL` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '数据标签',
  `SORT` int NOT NULL DEFAULT '0' COMMENT '排序',
  `DISABLED` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否禁用',
  `CREATE_USER` int NOT NULL COMMENT '创建人',
  `CREATE_TIME` datetime NOT NULL COMMENT '创建时间',
  `UPDATE_USER` int DEFAULT NULL COMMENT '更新人',
  `UPDATE_TIME` datetime DEFAULT NULL COMMENT '更新时间',
  `DELETED` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=100 DEFAULT CHARSET=utf8 COMMENT='字典数据';

-- ----------------------------
-- Table structure for SYSTEM_MENU
-- ----------------------------
DROP TABLE IF EXISTS `SYSTEM_MENU`;
CREATE TABLE `SYSTEM_MENU` (
  `ID` int NOT NULL AUTO_INCREMENT COMMENT '主键',
  `PARENT_ID` int DEFAULT NULL COMMENT '上一级菜单',
  `NAME` varchar(50) NOT NULL COMMENT '菜单名称',
  `PATH` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '菜单访问路径',
  `REMARK` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '备注',
  `ICON` varchar(50) DEFAULT NULL COMMENT '图标',
  `DISABLED` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否禁用',
  `SORT` int DEFAULT NULL COMMENT '排序',
  `FIXED` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否为固定菜单',
  `CREATE_USER` int NOT NULL COMMENT '创建人',
  `CREATE_TIME` datetime NOT NULL COMMENT '创建时间',
  `UPDATE_USER` int DEFAULT NULL COMMENT '更新人',
  `UPDATE_TIME` datetime DEFAULT NULL COMMENT '更新时间',
  `DELETED` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否已删除',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=100 DEFAULT CHARSET=utf8 COMMENT='系统菜单';

-- ----------------------------
-- Records of SYSTEM_MENU
-- ----------------------------
BEGIN;
INSERT INTO `SYSTEM_MENU` VALUES (1, NULL, '首页', '/index', '系统初始化创建', 'el-icon-s-home', 0, 0, 1, 1, CURRENT_TIMESTAMP, NULL, NULL, 0);
INSERT INTO `SYSTEM_MENU` VALUES (2, NULL, '系统管理', '', '系统初始化创建', 'el-icon-setting', 0, 1, 1, 1, CURRENT_TIMESTAMP, NULL, NULL, 0);
INSERT INTO `SYSTEM_MENU` VALUES (3, 2, '菜单管理', '/system/menu', '系统初始化创建', 'el-icon-menu', 0, 0, 1, 1, CURRENT_TIMESTAMP, NULL, NULL, 0);
INSERT INTO `SYSTEM_MENU` VALUES (4, 2, '用户管理', '/system/user', '系统初始化创建', 'el-icon-user-solid', 0, 1, 1, 1, CURRENT_TIMESTAMP, NULL, NULL, 0);
INSERT INTO `SYSTEM_MENU` VALUES (5, 2, '角色管理', '/system/role', '系统初始化创建', 'eva-icon-role', 0, 2, 1, 1, CURRENT_TIMESTAMP, NULL, NULL, 0);
INSERT INTO `SYSTEM_MENU` VALUES (6, 2, '权限管理', '/system/permission', '系统初始化创建', 'eva-icon-permission', 0, 3, 1, 1,CURRENT_TIMESTAMP,  NULL, NULL, 0);
INSERT INTO `SYSTEM_MENU` VALUES (7, 2, '部门管理', '/system/department', '系统初始化创建', 'eva-icon-department', 0, 4, 0, 1, CURRENT_TIMESTAMP, NULL, NULL, 0);
INSERT INTO `SYSTEM_MENU` VALUES (8, 2, '岗位管理', '/system/position', '系统初始化创建', 'eva-icon-position', 0, 5, 0, 1, CURRENT_TIMESTAMP, NULL, NULL, 0);
INSERT INTO `SYSTEM_MENU` VALUES (9, 2, '字典管理', '/system/dict', '系统初始化创建', 'eva-icon-dictionary', 0, 6, 0, 1, CURRENT_TIMESTAMP, NULL, NULL, 0);
INSERT INTO `SYSTEM_MENU` VALUES (10, 2, '服务监测', '/system/monitor', '系统初始化创建', 'eva-icon-listener', 0, 7, 0, 1, CURRENT_TIMESTAMP, NULL, NULL, 0);
INSERT INTO `SYSTEM_MENU` VALUES (11, 2, '日志管理', '', '系统初始化创建', 'eva-icon-log', 0, 8, 1, 1, CURRENT_TIMESTAMP, NULL, NULL, 0);
INSERT INTO `SYSTEM_MENU` VALUES (12, 11, '操作日志', '/system/traceLog', '系统初始化创建', 'eva-icon-log-opera', 0, 0, 1, 1, CURRENT_TIMESTAMP, NULL, NULL, 0);
INSERT INTO `SYSTEM_MENU` VALUES (13, 11, '登录日志', '/system/loginLog', '系统初始化创建', 'eva-icon-log-login', 0, 1, 1, 1, CURRENT_TIMESTAMP, NULL, NULL, 0);
INSERT INTO `SYSTEM_MENU` VALUES (14, 2, '地区管理', '/system/location', '系统初始化创建', 'el-icon-location', 0, 9, 1, 1, CURRENT_TIMESTAMP, NULL, NULL, 0);
INSERT INTO `SYSTEM_MENU` VALUES (15, 2, '数据权限', '/system/data-permission', '系统初始化创建', 'el-icon-view', 0, 10, 1, 1, CURRENT_TIMESTAMP, NULL, NULL, 0);
COMMIT;

-- ----------------------------
-- Table structure for SYSTEM_PERMISSION
-- ----------------------------
DROP TABLE IF EXISTS `SYSTEM_PERMISSION`;
CREATE TABLE `SYSTEM_PERMISSION` (
  `ID` int NOT NULL AUTO_INCREMENT COMMENT '主键',
  `CODE` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '权限CODE',
  `NAME` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '权限名称',
  `REMARK` varchar(500) DEFAULT NULL COMMENT '权限备注',
  `FIXED` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否为固定权限',
  `CREATE_USER` int NOT NULL COMMENT '创建人',
  `CREATE_TIME` datetime NOT NULL COMMENT '创建时间',
  `UPDATE_USER` int DEFAULT NULL COMMENT '更新人',
  `UPDATE_TIME` datetime DEFAULT NULL COMMENT '更新时间',
  `DELETED` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否已删除',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=100 DEFAULT CHARSET=utf8 COMMENT='系统权限';

-- ----------------------------
-- Records of SYSTEM_PERMISSION
-- ----------------------------
BEGIN;
INSERT INTO `SYSTEM_PERMISSION` VALUES (1, 'system:permission:query', '查询权限', '系统初始化创建', 1, 1, CURRENT_TIMESTAMP, NULL, NULL, 0);
INSERT INTO `SYSTEM_PERMISSION` VALUES (2, 'system:permission:create', '创建权限', '系统初始化创建', 1, 1, CURRENT_TIMESTAMP, NULL, NULL, 0);
INSERT INTO `SYSTEM_PERMISSION` VALUES (3, 'system:permission:update', '修改权限', '系统初始化创建', 1, 1, CURRENT_TIMESTAMP, NULL, NULL, 0);
INSERT INTO `SYSTEM_PERMISSION` VALUES (4, 'system:permission:delete', '删除权限', '系统初始化创建', 1, 1, CURRENT_TIMESTAMP, NULL, NULL, 0);
INSERT INTO `SYSTEM_PERMISSION` VALUES (5, 'system:menu:query', '查询菜单', '系统初始化创建', 1, 1, CURRENT_TIMESTAMP, NULL, NULL, 0);
INSERT INTO `SYSTEM_PERMISSION` VALUES (6, 'system:menu:create', '创建菜单', '系统初始化创建', 1, 1, CURRENT_TIMESTAMP, NULL, NULL, 0);
INSERT INTO `SYSTEM_PERMISSION` VALUES (7, 'system:menu:update', '修改菜单', '系统初始化创建', 1, 1, CURRENT_TIMESTAMP, NULL, NULL, 0);
INSERT INTO `SYSTEM_PERMISSION` VALUES (8, 'system:menu:delete', '删除菜单', '系统初始化创建', 1, 1, CURRENT_TIMESTAMP, NULL, NULL, 0);
INSERT INTO `SYSTEM_PERMISSION` VALUES (9, 'system:menu:sort', '菜单排序', '系统初始化创建', 1, 1, CURRENT_TIMESTAMP, NULL, NULL, 0);
INSERT INTO `SYSTEM_PERMISSION` VALUES (10, 'system:role:query', '查询角色', '系统初始化创建', 1, 1, CURRENT_TIMESTAMP, NULL, NULL, 0);
INSERT INTO `SYSTEM_PERMISSION` VALUES (11, 'system:role:create', '创建角色', '系统初始化创建', 1, 1, CURRENT_TIMESTAMP, NULL, NULL, 0);
INSERT INTO `SYSTEM_PERMISSION` VALUES (12, 'system:role:update', '修改角色', '系统初始化创建', 1, 1, CURRENT_TIMESTAMP, NULL, NULL, 0);
INSERT INTO `SYSTEM_PERMISSION` VALUES (13, 'system:role:delete', '删除角色', '系统初始化创建', 1, 1, CURRENT_TIMESTAMP, NULL, NULL, 0);
INSERT INTO `SYSTEM_PERMISSION` VALUES (14, 'system:role:createRoleMenu', '配置角色菜单', '系统初始化创建', 1, 1, CURRENT_TIMESTAMP, NULL, NULL, 0);
INSERT INTO `SYSTEM_PERMISSION` VALUES (15, 'system:role:createRolePermission', '配置角色权限', '系统初始化创建', 1, 1, CURRENT_TIMESTAMP, NULL, NULL, 0);
INSERT INTO `SYSTEM_PERMISSION` VALUES (16, 'system:user:query', '查询用户', '系统初始化创建', 1, 1, CURRENT_TIMESTAMP, NULL, NULL, 0);
INSERT INTO `SYSTEM_PERMISSION` VALUES (17, 'system:user:create', '创建用户', '系统初始化创建', 1, 1, CURRENT_TIMESTAMP, NULL, NULL, 0);
INSERT INTO `SYSTEM_PERMISSION` VALUES (18, 'system:user:update', '更新用户', '系统初始化创建', 1, 1, CURRENT_TIMESTAMP, NULL, NULL, 0);
INSERT INTO `SYSTEM_PERMISSION` VALUES (19, 'system:user:delete', '删除用户', '系统初始化创建', 1, 1, CURRENT_TIMESTAMP, NULL, NULL, 0);
INSERT INTO `SYSTEM_PERMISSION` VALUES (20, 'system:user:createUserRole', '配置用户角色', '系统初始化创建', 1, 1, CURRENT_TIMESTAMP, NULL, NULL, 0);
INSERT INTO `SYSTEM_PERMISSION` VALUES (21, 'system:user:resetPwd', '重置用户密码', '系统初始化创建', 1, 1, CURRENT_TIMESTAMP, NULL, NULL, 0);
INSERT INTO `SYSTEM_PERMISSION` VALUES (22, 'system:monitor:query', '查询服务监测', '系统初始化创建', 0, 1, CURRENT_TIMESTAMP, NULL, NULL, 0);
INSERT INTO `SYSTEM_PERMISSION` VALUES (23, 'system:department:create', '创建部门', '系统初始化创建', 0, 1, CURRENT_TIMESTAMP, NULL, NULL, 0);
INSERT INTO `SYSTEM_PERMISSION` VALUES (24, 'system:department:delete', '删除部门', '系统初始化创建', 0, 1, CURRENT_TIMESTAMP, NULL, NULL, 0);
INSERT INTO `SYSTEM_PERMISSION` VALUES (25, 'system:department:update', '更新部门', '系统初始化创建', 0, 1, CURRENT_TIMESTAMP, NULL, NULL, 0);
INSERT INTO `SYSTEM_PERMISSION` VALUES (26, 'system:department:queryUsers', '查看部门人员', '系统初始化创建', 0, 1, CURRENT_TIMESTAMP, NULL, NULL, 0);
INSERT INTO `SYSTEM_PERMISSION` VALUES (27, 'system:department:query', '查询部门', '系统初始化创建', 0, 1, CURRENT_TIMESTAMP, NULL, NULL, 0);
INSERT INTO `SYSTEM_PERMISSION` VALUES (28, 'system:position:create', '创建岗位', '系统初始化创建', 0, 1, CURRENT_TIMESTAMP, NULL, NULL, 0);
INSERT INTO `SYSTEM_PERMISSION` VALUES (29, 'system:position:delete', '删除岗位', '系统初始化创建', 0, 1, CURRENT_TIMESTAMP, NULL, NULL, 0);
INSERT INTO `SYSTEM_PERMISSION` VALUES (30, 'system:position:update', '更新岗位', '系统初始化创建', 0, 1, CURRENT_TIMESTAMP, NULL, NULL, 0);
INSERT INTO `SYSTEM_PERMISSION` VALUES (31, 'system:position:query', '查询岗位', '系统初始化创建', 0, 1, CURRENT_TIMESTAMP, NULL, NULL, 0);
INSERT INTO `SYSTEM_PERMISSION` VALUES (32, 'system:position:queryUsers', '查询岗位人员', '系统初始化创建', 0, 1, CURRENT_TIMESTAMP, NULL, NULL, 0);
INSERT INTO `SYSTEM_PERMISSION` VALUES (33, 'system:dict:create', '创建字典', '系统初始化创建', 0, 1, CURRENT_TIMESTAMP, NULL, NULL, 0);
INSERT INTO `SYSTEM_PERMISSION` VALUES (34, 'system:dict:delete', '删除字典', '系统初始化创建', 0, 1, CURRENT_TIMESTAMP, NULL, NULL, 0);
INSERT INTO `SYSTEM_PERMISSION` VALUES (35, 'system:dict:update', '修改字典', '系统初始化创建', 0, 1, CURRENT_TIMESTAMP, NULL, NULL, 0);
INSERT INTO `SYSTEM_PERMISSION` VALUES (36, 'system:dict:query', '查询字典', '系统初始化创建', 0, 1, CURRENT_TIMESTAMP, NULL, NULL, 0);
INSERT INTO `SYSTEM_PERMISSION` VALUES (37, 'system:traceLog:query', '查询操作日志', '系统初始化创建', 1, 1, CURRENT_TIMESTAMP, NULL, NULL, 0);
INSERT INTO `SYSTEM_PERMISSION` VALUES (38, 'system:loginLog:query', '查询登录日志', '系统初始化创建', 1, 1, CURRENT_TIMESTAMP, NULL, NULL, 0);
INSERT INTO `SYSTEM_PERMISSION` VALUES (39, 'system:location:create', '新建地区表', '系统初始化创建', 0, 1, CURRENT_TIMESTAMP, NULL, NULL, 0);
INSERT INTO `SYSTEM_PERMISSION` VALUES (40, 'system:location:update', '修改地区表', '系统初始化创建', 0, 1, CURRENT_TIMESTAMP, NULL, NULL, 0);
INSERT INTO `SYSTEM_PERMISSION` VALUES (41, 'system:location:query', '查询地区表', '系统初始化创建', 0, 1, CURRENT_TIMESTAMP, NULL, NULL, 0);
INSERT INTO `SYSTEM_PERMISSION` VALUES (42, 'system:datapermission:create', '新建数据权限配置', '', 0, 1, CURRENT_TIMESTAMP, NULL, NULL, 0);
INSERT INTO `SYSTEM_PERMISSION` VALUES (43, 'system:datapermission:delete', '删除数据权限配置', '', 0, 1, CURRENT_TIMESTAMP, NULL, NULL, 0);
INSERT INTO `SYSTEM_PERMISSION` VALUES (44, 'system:datapermission:update', '修改数据权限配置', '', 0, 1, CURRENT_TIMESTAMP, NULL, NULL, 0);
INSERT INTO `SYSTEM_PERMISSION` VALUES (45, 'system:datapermission:query', '查询数据权限配置', '', 0, 1, CURRENT_TIMESTAMP, NULL, NULL, 0);

COMMIT;

-- ----------------------------
-- Table structure for SYSTEM_POSITION
-- ----------------------------
DROP TABLE IF EXISTS `SYSTEM_POSITION`;
CREATE TABLE `SYSTEM_POSITION` (
  `ID` int NOT NULL AUTO_INCREMENT COMMENT '主键',
  `PARENT_ID` int DEFAULT NULL COMMENT '上级岗位',
  `CODE` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '岗位编码',
  `NAME` varchar(50) NOT NULL COMMENT '岗位名称',
  `CREATE_USER` int NOT NULL COMMENT '创建人',
  `CREATE_TIME` datetime NOT NULL COMMENT '创建时间',
  `UPDATE_USER` int DEFAULT NULL COMMENT '更新人',
  `UPDATE_TIME` datetime DEFAULT NULL COMMENT '更新时间',
  `DELETED` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='岗位';

-- ----------------------------
-- Table structure for SYSTEM_POSITION_USER
-- ----------------------------
DROP TABLE IF EXISTS `SYSTEM_POSITION_USER`;
CREATE TABLE `SYSTEM_POSITION_USER` (
  `ID` int NOT NULL AUTO_INCREMENT COMMENT '主键',
  `POSITION_ID` int NOT NULL COMMENT '岗位ID',
  `USER_ID` int NOT NULL COMMENT '用户ID',
  `OPERA_USER` int NOT NULL COMMENT '操作人',
  `OPERA_TIME` datetime NOT NULL COMMENT '操作时间',
  `DELETED` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否已删除',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='岗位用户';

-- ----------------------------
-- Table structure for SYSTEM_ROLE
-- ----------------------------
DROP TABLE IF EXISTS `SYSTEM_ROLE`;
CREATE TABLE `SYSTEM_ROLE` (
  `ID` int NOT NULL AUTO_INCREMENT COMMENT '主键',
  `CODE` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '角色CODE',
  `NAME` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '角色名称',
  `REMARK` varchar(500) DEFAULT NULL COMMENT '角色备注',
  `FIXED` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否为固定角色',
  `CREATE_USER` int NOT NULL COMMENT '创建人',
  `CREATE_TIME` datetime NOT NULL COMMENT '创建时间',
  `UPDATE_USER` int DEFAULT NULL COMMENT '更新人',
  `UPDATE_TIME` datetime DEFAULT NULL COMMENT '更新时间',
  `DELETED` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否已删除',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=100 DEFAULT CHARSET=utf8 COMMENT='系统角色';

-- ----------------------------
-- Records of SYSTEM_ROLE
-- ----------------------------
BEGIN;
INSERT INTO `SYSTEM_ROLE` VALUES (1, 'admin', '超级管理员', '系统初始化创建', 1, 1, CURRENT_TIMESTAMP, NULL, NULL, 0);
INSERT INTO `SYSTEM_ROLE` VALUES (2, 'manage', '管理员', '系统初始化创建', 0, 1, CURRENT_TIMESTAMP, NULL, NULL, 0);
COMMIT;

-- ----------------------------
-- Table structure for SYSTEM_ROLE_MENU
-- ----------------------------
DROP TABLE IF EXISTS `SYSTEM_ROLE_MENU`;
CREATE TABLE `SYSTEM_ROLE_MENU` (
  `ID` int NOT NULL AUTO_INCREMENT COMMENT '主键',
  `ROLE_ID` int NOT NULL COMMENT '角色ID',
  `MENU_ID` int NOT NULL COMMENT '菜单ID',
  `CREATE_USER` int NOT NULL COMMENT '创建人',
  `CREATE_TIME` datetime NOT NULL COMMENT '创建时间',
  `UPDATE_USER` int DEFAULT NULL COMMENT '更新人',
  `UPDATE_TIME` datetime DEFAULT NULL,
  `DELETED` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否已删除',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=100 DEFAULT CHARSET=utf8 COMMENT='角色菜单关联';

-- ----------------------------
-- Records of SYSTEM_ROLE_MENU
-- ----------------------------
BEGIN;
-- 超级管理员角色菜单
INSERT INTO `SYSTEM_ROLE_MENU` VALUES (1, 1, 1, 1, CURRENT_TIMESTAMP, NULL, NULL, 0);
INSERT INTO `SYSTEM_ROLE_MENU` VALUES (2, 1, 2, 1, CURRENT_TIMESTAMP, NULL, NULL, 0);
INSERT INTO `SYSTEM_ROLE_MENU` VALUES (3, 1, 3, 1, CURRENT_TIMESTAMP, NULL, NULL, 0);
INSERT INTO `SYSTEM_ROLE_MENU` VALUES (4, 1, 4, 1, CURRENT_TIMESTAMP, NULL, NULL, 0);
INSERT INTO `SYSTEM_ROLE_MENU` VALUES (5, 1, 5, 1, CURRENT_TIMESTAMP, NULL, NULL, 0);
INSERT INTO `SYSTEM_ROLE_MENU` VALUES (6, 1, 6, 1, CURRENT_TIMESTAMP, NULL, NULL, 0);
INSERT INTO `SYSTEM_ROLE_MENU` VALUES (7, 1, 7, 1, CURRENT_TIMESTAMP, NULL, NULL, 0);
INSERT INTO `SYSTEM_ROLE_MENU` VALUES (8, 1, 8, 1, CURRENT_TIMESTAMP, NULL, NULL, 0);
INSERT INTO `SYSTEM_ROLE_MENU` VALUES (9, 1, 9, 1, CURRENT_TIMESTAMP, NULL, NULL, 0);
INSERT INTO `SYSTEM_ROLE_MENU` VALUES (10, 1, 10, 1, CURRENT_TIMESTAMP, NULL, NULL, 0);
INSERT INTO `SYSTEM_ROLE_MENU` VALUES (11, 1, 11, 1, CURRENT_TIMESTAMP, NULL, NULL, 0);
INSERT INTO `SYSTEM_ROLE_MENU` VALUES (12, 1, 12, 1, CURRENT_TIMESTAMP, NULL, NULL, 0);
INSERT INTO `SYSTEM_ROLE_MENU` VALUES (13, 1, 13, 1, CURRENT_TIMESTAMP, NULL, NULL, 0);
INSERT INTO `SYSTEM_ROLE_MENU` VALUES (20, 1, 14, 1, CURRENT_TIMESTAMP, NULL, NULL, 0);
-- 管理员角色菜单
INSERT INTO `SYSTEM_ROLE_MENU` VALUES (14, 1, 1, 1, CURRENT_TIMESTAMP, NULL, NULL, 0);
INSERT INTO `SYSTEM_ROLE_MENU` VALUES (15, 1, 2, 1, CURRENT_TIMESTAMP, NULL, NULL, 0);
INSERT INTO `SYSTEM_ROLE_MENU` VALUES (16, 1, 4, 1, CURRENT_TIMESTAMP, NULL, NULL, 0);
INSERT INTO `SYSTEM_ROLE_MENU` VALUES (17, 1, 5, 1, CURRENT_TIMESTAMP, NULL, NULL, 0);
INSERT INTO `SYSTEM_ROLE_MENU` VALUES (18, 1, 7, 1, CURRENT_TIMESTAMP, NULL, NULL, 0);
INSERT INTO `SYSTEM_ROLE_MENU` VALUES (19, 1, 8, 1, CURRENT_TIMESTAMP, NULL, NULL, 0);
COMMIT;

-- ----------------------------
-- Table structure for SYSTEM_ROLE_PERMISSION
-- ----------------------------
DROP TABLE IF EXISTS `SYSTEM_ROLE_PERMISSION`;
CREATE TABLE `SYSTEM_ROLE_PERMISSION` (
  `ID` int NOT NULL AUTO_INCREMENT COMMENT '主键',
  `ROLE_ID` int NOT NULL COMMENT '角色ID',
  `PERMISSION_ID` int NOT NULL COMMENT '权限ID',
  `CREATE_USER` int NOT NULL COMMENT '创建人',
  `CREATE_TIME` datetime NOT NULL COMMENT '创建时间',
  `UPDATE_USER` int DEFAULT NULL COMMENT '更新人',
  `UPDATE_TIME` datetime DEFAULT NULL COMMENT '更新时间',
  `DELETED` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否已删除',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=100 DEFAULT CHARSET=utf8 COMMENT='角色权限关联';

-- ----------------------------
-- Records of SYSTEM_ROLE_PERMISSION
-- ----------------------------
BEGIN;
-- 超级管理员权限
INSERT INTO `SYSTEM_ROLE_PERMISSION` VALUES (1, 1, 1, 1, CURRENT_TIMESTAMP, NULL, NULL, 0);
INSERT INTO `SYSTEM_ROLE_PERMISSION` VALUES (2, 1, 2, 1, CURRENT_TIMESTAMP, NULL, NULL, 0);
INSERT INTO `SYSTEM_ROLE_PERMISSION` VALUES (3, 1, 3, 1, CURRENT_TIMESTAMP, NULL, NULL, 0);
INSERT INTO `SYSTEM_ROLE_PERMISSION` VALUES (4, 1, 4, 1, CURRENT_TIMESTAMP, NULL, NULL, 0);
INSERT INTO `SYSTEM_ROLE_PERMISSION` VALUES (5, 1, 5, 1, CURRENT_TIMESTAMP, NULL, NULL, 0);
INSERT INTO `SYSTEM_ROLE_PERMISSION` VALUES (6, 1, 6, 1, CURRENT_TIMESTAMP, NULL, NULL, 0);
INSERT INTO `SYSTEM_ROLE_PERMISSION` VALUES (7, 1, 7, 1, CURRENT_TIMESTAMP, NULL, NULL, 0);
INSERT INTO `SYSTEM_ROLE_PERMISSION` VALUES (8, 1, 8, 1, CURRENT_TIMESTAMP, NULL, NULL, 0);
INSERT INTO `SYSTEM_ROLE_PERMISSION` VALUES (9, 1, 9, 1, CURRENT_TIMESTAMP, NULL, NULL, 0);
INSERT INTO `SYSTEM_ROLE_PERMISSION` VALUES (10, 1, 10, 1, CURRENT_TIMESTAMP, NULL, NULL, 0);
INSERT INTO `SYSTEM_ROLE_PERMISSION` VALUES (11, 1, 11, 1, CURRENT_TIMESTAMP, NULL, NULL, 0);
INSERT INTO `SYSTEM_ROLE_PERMISSION` VALUES (12, 1, 12, 1, CURRENT_TIMESTAMP, NULL, NULL, 0);
INSERT INTO `SYSTEM_ROLE_PERMISSION` VALUES (13, 1, 13, 1, CURRENT_TIMESTAMP, NULL, NULL, 0);
INSERT INTO `SYSTEM_ROLE_PERMISSION` VALUES (14, 1, 14, 1, CURRENT_TIMESTAMP, NULL, NULL, 0);
INSERT INTO `SYSTEM_ROLE_PERMISSION` VALUES (15, 1, 15, 1, CURRENT_TIMESTAMP, NULL, NULL, 0);
INSERT INTO `SYSTEM_ROLE_PERMISSION` VALUES (16, 1, 16, 1, CURRENT_TIMESTAMP, NULL, NULL, 0);
INSERT INTO `SYSTEM_ROLE_PERMISSION` VALUES (17, 1, 17, 1, CURRENT_TIMESTAMP, NULL, NULL, 0);
INSERT INTO `SYSTEM_ROLE_PERMISSION` VALUES (18, 1, 18, 1, CURRENT_TIMESTAMP, NULL, NULL, 0);
INSERT INTO `SYSTEM_ROLE_PERMISSION` VALUES (19, 1, 19, 1, CURRENT_TIMESTAMP, NULL, NULL, 0);
INSERT INTO `SYSTEM_ROLE_PERMISSION` VALUES (20, 1, 20, 1, CURRENT_TIMESTAMP, NULL, NULL, 0);
INSERT INTO `SYSTEM_ROLE_PERMISSION` VALUES (21, 1, 21, 1, CURRENT_TIMESTAMP, NULL, NULL, 0);
INSERT INTO `SYSTEM_ROLE_PERMISSION` VALUES (22, 1, 22, 1, CURRENT_TIMESTAMP, NULL, NULL, 0);
INSERT INTO `SYSTEM_ROLE_PERMISSION` VALUES (23, 1, 23, 1, CURRENT_TIMESTAMP, NULL, NULL, 0);
INSERT INTO `SYSTEM_ROLE_PERMISSION` VALUES (24, 1, 24, 1, CURRENT_TIMESTAMP, NULL, NULL, 0);
INSERT INTO `SYSTEM_ROLE_PERMISSION` VALUES (25, 1, 25, 1, CURRENT_TIMESTAMP, NULL, NULL, 0);
INSERT INTO `SYSTEM_ROLE_PERMISSION` VALUES (26, 1, 26, 1, CURRENT_TIMESTAMP, NULL, NULL, 0);
INSERT INTO `SYSTEM_ROLE_PERMISSION` VALUES (27, 1, 27, 1, CURRENT_TIMESTAMP, NULL, NULL, 0);
INSERT INTO `SYSTEM_ROLE_PERMISSION` VALUES (28, 1, 28, 1, CURRENT_TIMESTAMP, NULL, NULL, 0);
INSERT INTO `SYSTEM_ROLE_PERMISSION` VALUES (29, 1, 29, 1, CURRENT_TIMESTAMP, NULL, NULL, 0);
INSERT INTO `SYSTEM_ROLE_PERMISSION` VALUES (30, 1, 30, 1, CURRENT_TIMESTAMP, NULL, NULL, 0);
INSERT INTO `SYSTEM_ROLE_PERMISSION` VALUES (31, 1, 31, 1, CURRENT_TIMESTAMP, NULL, NULL, 0);
INSERT INTO `SYSTEM_ROLE_PERMISSION` VALUES (32, 1, 32, 1, CURRENT_TIMESTAMP, NULL, NULL, 0);
INSERT INTO `SYSTEM_ROLE_PERMISSION` VALUES (33, 1, 33, 1, CURRENT_TIMESTAMP, NULL, NULL, 0);
INSERT INTO `SYSTEM_ROLE_PERMISSION` VALUES (34, 1, 34, 1, CURRENT_TIMESTAMP, NULL, NULL, 0);
INSERT INTO `SYSTEM_ROLE_PERMISSION` VALUES (35, 1, 35, 1, CURRENT_TIMESTAMP, NULL, NULL, 0);
INSERT INTO `SYSTEM_ROLE_PERMISSION` VALUES (36, 1, 36, 1, CURRENT_TIMESTAMP, NULL, NULL, 0);
INSERT INTO `SYSTEM_ROLE_PERMISSION` VALUES (37, 1, 37, 1, CURRENT_TIMESTAMP, NULL, NULL, 0);
INSERT INTO `SYSTEM_ROLE_PERMISSION` VALUES (38, 1, 38, 1, CURRENT_TIMESTAMP, NULL, NULL, 0);
INSERT INTO `SYSTEM_ROLE_PERMISSION` VALUES (55, 1, 39, 1, CURRENT_TIMESTAMP, NULL, NULL, 0);
INSERT INTO `SYSTEM_ROLE_PERMISSION` VALUES (56, 1, 40, 1, CURRENT_TIMESTAMP, NULL, NULL, 0);
INSERT INTO `SYSTEM_ROLE_PERMISSION` VALUES (57, 1, 41, 1, CURRENT_TIMESTAMP, NULL, NULL, 0);
INSERT INTO `SYSTEM_ROLE_PERMISSION` VALUES (58, 1, 42, 1, CURRENT_TIMESTAMP, NULL, NULL, 0);
INSERT INTO `SYSTEM_ROLE_PERMISSION` VALUES (59, 1, 43, 1, CURRENT_TIMESTAMP, NULL, NULL, 0);
INSERT INTO `SYSTEM_ROLE_PERMISSION` VALUES (60, 1, 44, 1, CURRENT_TIMESTAMP, NULL, NULL, 0);
INSERT INTO `SYSTEM_ROLE_PERMISSION` VALUES (61, 1, 45, 1, CURRENT_TIMESTAMP, NULL, NULL, 0);
-- 管理员权限
INSERT INTO `SYSTEM_ROLE_PERMISSION` VALUES (39, 16, 1, 1, CURRENT_TIMESTAMP, NULL, NULL, 0);
INSERT INTO `SYSTEM_ROLE_PERMISSION` VALUES (40, 17, 1, 1, CURRENT_TIMESTAMP, NULL, NULL, 0);
INSERT INTO `SYSTEM_ROLE_PERMISSION` VALUES (41, 18, 1, 1, CURRENT_TIMESTAMP, NULL, NULL, 0);
INSERT INTO `SYSTEM_ROLE_PERMISSION` VALUES (42, 20, 1, 1, CURRENT_TIMESTAMP, NULL, NULL, 0);
INSERT INTO `SYSTEM_ROLE_PERMISSION` VALUES (43, 21, 1, 1, CURRENT_TIMESTAMP, NULL, NULL, 0);
INSERT INTO `SYSTEM_ROLE_PERMISSION` VALUES (44, 10, 1, 1, CURRENT_TIMESTAMP, NULL, NULL, 0);
INSERT INTO `SYSTEM_ROLE_PERMISSION` VALUES (45, 23, 1, 1, CURRENT_TIMESTAMP, NULL, NULL, 0);
INSERT INTO `SYSTEM_ROLE_PERMISSION` VALUES (46, 24, 1, 1, CURRENT_TIMESTAMP, NULL, NULL, 0);
INSERT INTO `SYSTEM_ROLE_PERMISSION` VALUES (47, 25, 1, 1, CURRENT_TIMESTAMP, NULL, NULL, 0);
INSERT INTO `SYSTEM_ROLE_PERMISSION` VALUES (48, 26, 1, 1, CURRENT_TIMESTAMP, NULL, NULL, 0);
INSERT INTO `SYSTEM_ROLE_PERMISSION` VALUES (49, 27, 1, 1, CURRENT_TIMESTAMP, NULL, NULL, 0);
INSERT INTO `SYSTEM_ROLE_PERMISSION` VALUES (50, 28, 1, 1, CURRENT_TIMESTAMP, NULL, NULL, 0);
INSERT INTO `SYSTEM_ROLE_PERMISSION` VALUES (51, 29, 1, 1, CURRENT_TIMESTAMP, NULL, NULL, 0);
INSERT INTO `SYSTEM_ROLE_PERMISSION` VALUES (52, 30, 1, 1, CURRENT_TIMESTAMP, NULL, NULL, 0);
INSERT INTO `SYSTEM_ROLE_PERMISSION` VALUES (53, 31, 1, 1, CURRENT_TIMESTAMP, NULL, NULL, 0);
INSERT INTO `SYSTEM_ROLE_PERMISSION` VALUES (54, 32, 1, 1, CURRENT_TIMESTAMP, NULL, NULL, 0);
COMMIT;

-- ----------------------------
-- Table structure for SYSTEM_USER
-- ----------------------------
DROP TABLE IF EXISTS `SYSTEM_USER`;
CREATE TABLE `SYSTEM_USER` (
  `ID` int NOT NULL AUTO_INCREMENT COMMENT '主键',
  `USERNAME` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '用户名',
  `REALNAME` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '真实姓名',
  `EMP_NO` varchar(50) DEFAULT NULL COMMENT '工号',
  `BIRTHDAY` date DEFAULT NULL COMMENT '生日',
  `SEX` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '性别',
  `EMAIL` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '邮箱',
  `MOBILE` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '手机号码',
  `AVATAR` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '头像',
  `PASSWORD` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '密码',
  `SALT` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '密码盐',
  `FIXED` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否为固定用户',
  `CREATE_USER` int DEFAULT NULL COMMENT '创建人',
  `CREATE_TIME` datetime NOT NULL COMMENT '创建时间',
  `UPDATE_USER` int DEFAULT NULL COMMENT '更新人',
  `UPDATE_TIME` datetime DEFAULT NULL COMMENT '更新时间',
  `DELETED` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否已删除',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=100 DEFAULT CHARSET=utf8 COMMENT='系统用户';

-- ----------------------------
-- Records of SYSTEM_USER
-- ----------------------------
BEGIN;
INSERT INTO `SYSTEM_USER` VALUES (1, 'admin', '伊娃', NULL, NULL, '1', NULL, NULL, '/avatar/man.png', 'c2b61790649412a0079b4ff9c8196a4d', 'kl38c6', 1, NULL, CURRENT_TIMESTAMP, NULL, NULL, 0);
INSERT INTO `SYSTEM_USER` VALUES (2, 'manager', '管理员', NULL, NULL, '1', NULL, NULL, '/avatar/man.png', 'c2b61790649412a0079b4ff9c8196a4d', 'kl38c6', 1, NULL, CURRENT_TIMESTAMP, NULL, NULL, 0);
COMMIT;

-- ----------------------------
-- Table structure for SYSTEM_USER_ROLE
-- ----------------------------
DROP TABLE IF EXISTS `SYSTEM_USER_ROLE`;
CREATE TABLE `SYSTEM_USER_ROLE` (
  `ID` int NOT NULL AUTO_INCREMENT COMMENT '主键',
  `USER_ID` int NOT NULL COMMENT '用户ID',
  `ROLE_ID` int NOT NULL COMMENT '角色ID',
  `CREATE_USER` int NOT NULL COMMENT '创建人',
  `CREATE_TIME` datetime NOT NULL COMMENT '创建时间',
  `UPDATE_USER` int DEFAULT NULL COMMENT '更新人',
  `UPDATE_TIME` datetime DEFAULT NULL COMMENT '更新时间',
  `DELETED` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否已删除',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=100 DEFAULT CHARSET=utf8 COMMENT='用户角色关联';

-- ----------------------------
-- Records of SYSTEM_USER_ROLE
-- ----------------------------
BEGIN;
INSERT INTO `SYSTEM_USER_ROLE` VALUES (1, 1, 1, 1, CURRENT_TIMESTAMP, NULL, NULL, 0);
INSERT INTO `SYSTEM_USER_ROLE` VALUES (2, 2, 2, 1, CURRENT_TIMESTAMP, NULL, NULL, 0);
COMMIT;

-- ----------------------------
-- Table structure for SYSTEM_TRACE_LOG
-- ----------------------------
DROP TABLE IF EXISTS `SYSTEM_TRACE_LOG`;
CREATE TABLE `SYSTEM_TRACE_LOG` (
  `ID` int NOT NULL AUTO_INCREMENT COMMENT '主键',
  `USER_ID` int DEFAULT NULL COMMENT '用户',
  `USERNAME` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '固化用户名',
  `USER_REALNAME` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '固化用户姓名',
  `USER_ROLES` varchar(2000) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '固化用户角色',
  `USER_PERMISSIONS` varchar(2000) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '固化用户权限',
  `OPERA_MODULE` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '操作模块',
  `OPERA_TYPE` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '操作类型',
  `OPERA_REMARK` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '操作备注',
  `OPERA_TIME` datetime NOT NULL COMMENT '操作开始时间',
  `OPERA_SPEND_TIME` int DEFAULT NULL COMMENT '耗时',
  `REQUEST_METHOD` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '请求方式',
  `REQUEST_URI` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '请求地址',
  `REQUEST_PARAMS` varchar(2000) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '请求参数',
  `REQUEST_RESULT` varchar(2000) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '请求结果',
  `STATUS` tinyint NOT NULL DEFAULT '-1' COMMENT '状态（0操作失败，1操作成功，-1未得到处理）',
  `EXCEPTION_LEVEL` tinyint DEFAULT NULL COMMENT '异常等级',
  `EXCEPTION_STACK` varchar(5000) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '异常信息',
  `SERVER_IP` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '服务器IP',
  `SYSTEM_VERSION` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '接口版本',
  `PLATFORM` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '操作平台',
  `IP` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '用户IP',
  `CLIENT_INFO` varchar(500) DEFAULT NULL COMMENT '客户端信息',
  `OS_INFO` varchar(500) DEFAULT NULL COMMENT '系统信息',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='跟踪日志';

-- ----------------------------
-- Table structure for SYSTEM_LOGIN_LOG
-- ----------------------------
DROP TABLE IF EXISTS `SYSTEM_LOGIN_LOG`;
CREATE TABLE `SYSTEM_LOGIN_LOG` (
  `ID` int NOT NULL AUTO_INCREMENT COMMENT '主键',
  `USER_ID` int DEFAULT NULL COMMENT '登录用户ID',
  `LOGIN_USERNAME` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '登录用户名',
  `IP` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '登录IP',
  `LOCATION` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '登录地址',
  `CLIENT_INFO` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '客户端',
  `OS_INFO` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '操作系统',
  `PLATFORM` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '登录平台',
  `SYSTEM_VERSION` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '系统版本',
  `SERVER_IP` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '服务器IP',
  `SUCCESS` tinyint(1) DEFAULT NULL COMMENT '是否登录成功',
  `REASON` varchar(200) DEFAULT NULL COMMENT '失败原因',
  `LOGIN_TIME` datetime NOT NULL COMMENT '登录时间',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='登录日志';

-- ----------------------------
-- Table structure for SYSTEM_LOCATION
-- ----------------------------
DROP TABLE IF EXISTS `SYSTEM_LOCATION`;
CREATE TABLE `SYSTEM_LOCATION` (
  `ID` int NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `PARENT_ID` int DEFAULT NULL COMMENT '父ID',
  `SHORT_NAME` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '简称',
  `NAME` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '名称',
  `FULL_NAME` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '全称',
  `LEVEL` tinyint NOT NULL COMMENT '层级 1省 2市 3区县',
  `PINYIN` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '拼音',
  `AREA_CODE` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '区号',
  `POSTAL_CODE` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '邮编',
  `FIRST_LETTER` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '首字母',
  `LNG` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '经度',
  `LAT` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '纬度',
  `DISABLED` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否禁用',
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=4000 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='地区';

-- ----------------------------
-- Records of SYSTEM_LOCATION
-- ----------------------------
BEGIN;
INSERT INTO `SYSTEM_LOCATION` VALUES (1, 0, '北京', '北京', '中国,北京', 1, 'beijing', '', '', 'B', '116.405285', '39.904989', 0);
INSERT INTO `SYSTEM_LOCATION` VALUES (2, 1, '北京', '北京市', '中国,北京,北京市', 2, 'beijing', '010', '100000', 'B', '116.405285', '39.904989', 0);
INSERT INTO `SYSTEM_LOCATION` VALUES (3, 2, '东城', '东城区', '中国,北京,北京市,东城区', 3, 'dongcheng', '010', '100010', 'D', '116.41005', '39.93157', 0);
INSERT INTO `SYSTEM_LOCATION` VALUES (4, 2, '西城', '西城区', '中国,北京,北京市,西城区', 3, 'xicheng', '010', '100032', 'X', '116.36003', '39.9305', 0);
INSERT INTO `SYSTEM_LOCATION` VALUES (5, 2, '朝阳', '朝阳区', '中国,北京,北京市,朝阳区', 3, 'chaoyang', '010', '100020', 'C', '116.48548', '39.9484', 0);
INSERT INTO `SYSTEM_LOCATION` VALUES (6, 2, '丰台', '丰台区', '中国,北京,北京市,丰台区', 3, 'fengtai', '010', '100071', 'F', '116.28625', '39.8585', 0);
INSERT INTO `SYSTEM_LOCATION` VALUES (7, 2, '石景山', '石景山区', '中国,北京,北京市,石景山区', 3, 'shijingshan', '010', '100043', 'S', '116.2229', '39.90564', 0);
INSERT INTO `SYSTEM_LOCATION` VALUES (8, 2, '海淀', '海淀区', '中国,北京,北京市,海淀区', 3, 'haidian', '010', '100089', 'H', '116.29812', '39.95931', 0);
INSERT INTO `SYSTEM_LOCATION` VALUES (9, 2, '门头沟', '门头沟区', '中国,北京,北京市,门头沟区', 3, 'mentougou', '010', '102300', 'M', '116.10137', '39.94043', 0);
INSERT INTO `SYSTEM_LOCATION` VALUES (10, 2, '房山', '房山区', '中国,北京,北京市,房山区', 3, 'fangshan', '010', '102488', 'F', '116.14257', '39.74786', 0);
INSERT INTO `SYSTEM_LOCATION` VALUES (11, 2, '通州', '通州区', '中国,北京,北京市,通州区', 3, 'tongzhou', '010', '101149', 'T', '116.65716', '39.90966', 0);
INSERT INTO `SYSTEM_LOCATION` VALUES (12, 2, '顺义', '顺义区', '中国,北京,北京市,顺义区', 3, 'shunyi', '010', '101300', 'S', '116.65417', '40.1302', 0);
INSERT INTO `SYSTEM_LOCATION` VALUES (13, 2, '昌平', '昌平区', '中国,北京,北京市,昌平区', 3, 'changping', '010', '102200', 'C', '116.2312', '40.22072', 0);
INSERT INTO `SYSTEM_LOCATION` VALUES (14, 2, '大兴', '大兴区', '中国,北京,北京市,大兴区', 3, 'daxing', '010', '102600', 'D', '116.34149', '39.72668', 0);
INSERT INTO `SYSTEM_LOCATION` VALUES (15, 2, '怀柔', '怀柔区', '中国,北京,北京市,怀柔区', 3, 'huairou', '010', '101400', 'H', '116.63168', '40.31602', 0);
INSERT INTO `SYSTEM_LOCATION` VALUES (16, 2, '平谷', '平谷区', '中国,北京,北京市,平谷区', 3, 'pinggu', '010', '101200', 'P', '117.12133', '40.14056', 0);
INSERT INTO `SYSTEM_LOCATION` VALUES (17, 2, '密云', '密云县', '中国,北京,北京市,密云县', 3, 'miyun', '010', '101500', 'M', '116.84295', '40.37618', 0);
INSERT INTO `SYSTEM_LOCATION` VALUES (18, 2, '延庆', '延庆县', '中国,北京,北京市,延庆县', 3, 'yanqing', '010', '102100', 'Y', '115.97494', '40.45672', 0);
INSERT INTO `SYSTEM_LOCATION` VALUES (19, 0, '天津', '天津', '中国,天津', 1, 'tianjin', '', '', 'T', '117.190182', '39.125596', 0);
INSERT INTO `SYSTEM_LOCATION` VALUES (20, 19, '天津', '天津市', '中国,天津,天津市', 2, 'tianjin', '022', '300000', 'T', '117.190182', '39.125596', 0);
INSERT INTO `SYSTEM_LOCATION` VALUES (21, 20, '和平', '和平区', '中国,天津,天津市,和平区', 3, 'heping', '022', '300041', 'H', '117.21456', '39.11718', 0);
INSERT INTO `SYSTEM_LOCATION` VALUES (22, 20, '河东', '河东区', '中国,天津,天津市,河东区', 3, 'hedong', '022', '300171', 'H', '117.22562', '39.12318', 0);
INSERT INTO `SYSTEM_LOCATION` VALUES (23, 20, '河西', '河西区', '中国,天津,天津市,河西区', 3, 'hexi', '022', '300202', 'H', '117.22327', '39.10959', 0);
INSERT INTO `SYSTEM_LOCATION` VALUES (24, 20, '南开', '南开区', '中国,天津,天津市,南开区', 3, 'nankai', '022', '300110', 'N', '117.15074', '39.13821', 0);
INSERT INTO `SYSTEM_LOCATION` VALUES (25, 20, '河北', '河北区', '中国,天津,天津市,河北区', 3, 'hebei', '022', '300143', 'H', '117.19697', '39.14816', 0);
INSERT INTO `SYSTEM_LOCATION` VALUES (26, 20, '红桥', '红桥区', '中国,天津,天津市,红桥区', 3, 'hongqiao', '022', '300131', 'H', '117.15145', '39.16715', 0);
INSERT INTO `SYSTEM_LOCATION` VALUES (27, 20, '东丽', '东丽区', '中国,天津,天津市,东丽区', 3, 'dongli', '022', '300300', 'D', '117.31436', '39.0863', 0);
INSERT INTO `SYSTEM_LOCATION` VALUES (28, 20, '西青', '西青区', '中国,天津,天津市,西青区', 3, 'xiqing', '022', '300380', 'X', '117.00927', '39.14123', 0);
INSERT INTO `SYSTEM_LOCATION` VALUES (29, 20, '津南', '津南区', '中国,天津,天津市,津南区', 3, 'jinnan', '022', '300350', 'J', '117.38537', '38.99139', 0);
INSERT INTO `SYSTEM_LOCATION` VALUES (30, 20, '北辰', '北辰区', '中国,天津,天津市,北辰区', 3, 'beichen', '022', '300400', 'B', '117.13217', '39.22131', 0);
INSERT INTO `SYSTEM_LOCATION` VALUES (31, 20, '武清', '武清区', '中国,天津,天津市,武清区', 3, 'wuqing', '022', '301700', 'W', '117.04443', '39.38415', 0);
INSERT INTO `SYSTEM_LOCATION` VALUES (32, 20, '宝坻', '宝坻区', '中国,天津,天津市,宝坻区', 3, 'baodi', '022', '301800', 'B', '117.3103', '39.71761', 0);
INSERT INTO `SYSTEM_LOCATION` VALUES (33, 20, '滨海新区', '滨海新区', '中国,天津,天津市,滨海新区', 3, 'binhaixinqu', '022', '300451', 'B', '117.70162', '39.02668', 0);
INSERT INTO `SYSTEM_LOCATION` VALUES (34, 20, '宁河', '宁河县', '中国,天津,天津市,宁河县', 3, 'ninghe', '022', '301500', 'N', '117.8255', '39.33048', 0);
INSERT INTO `SYSTEM_LOCATION` VALUES (35, 20, '静海', '静海县', '中国,天津,天津市,静海县', 3, 'jinghai', '022', '301600', 'J', '116.97436', '38.94582', 0);
INSERT INTO `SYSTEM_LOCATION` VALUES (36, 20, '蓟县', '蓟县', '中国,天津,天津市,蓟县', 3, 'jixian', '022', '301900', 'J', '117.40799', '40.04567', 0);
COMMIT;

-- ----------------------------
-- Table structure for SYSTEM_DATA_PERMISSION
-- ----------------------------
DROP TABLE IF EXISTS `SYSTEM_DATA_PERMISSION`;
CREATE TABLE `SYSTEM_DATA_PERMISSION` (
  `ID` int NOT NULL AUTO_INCREMENT COMMENT '主键',
  `BUSINESS_CODE` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '业务模块',
  `ROLE_ID` int NOT NULL COMMENT '角色ID',
  `TYPE` smallint NOT NULL COMMENT '权限类型',
  `CUSTOM_DATA` varchar(2000) DEFAULT NULL COMMENT '自定义数据',
  `DISABLED` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否禁用',
  `REMARK` varchar(500) DEFAULT NULL COMMENT '备注',
  `CREATE_USER` int NOT NULL COMMENT '创建人',
  `CREATE_TIME` datetime NOT NULL COMMENT '创建时间',
  `UPDATE_USER` int DEFAULT NULL COMMENT '修改人',
  `UPDATE_TIME` datetime DEFAULT NULL COMMENT '修改时间',
  `DELETED` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否已删除',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 COMMENT='数据权限配置';

SET FOREIGN_KEY_CHECKS = 1;
