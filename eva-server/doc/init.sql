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
-- Records of SYSTEM_DEPARTMENT
-- ----------------------------
BEGIN;
INSERT INTO `SYSTEM_DEPARTMENT`(`ID`, `PARENT_ID`, `CODE`, `NAME`, `PHONE`, `EMAIL`, `CREATE_USER`, `CREATE_TIME`, `UPDATE_USER`, `UPDATE_TIME`, `DELETED`) VALUES (1, NULL, 'TECH', '技术部门', '', '', 1, CURRENT_TIMESTAMP, NULL, NULL, 0);
COMMIT;

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
-- Records of SYSTEM_DEPARTMENT_USER
-- ----------------------------
BEGIN;
INSERT INTO `SYSTEM_DEPARTMENT_USER`(`ID`, `DEPARTMENT_ID`, `USER_ID`, `OPERA_USER`, `OPERA_TIME`, `DELETED`) VALUES (1, 1, 1, 1, CURRENT_TIMESTAMP, 0);
INSERT INTO `SYSTEM_DEPARTMENT_USER`(`ID`, `DEPARTMENT_ID`, `USER_ID`, `OPERA_USER`, `OPERA_TIME`, `DELETED`) VALUES (2, 1, 2, 1, CURRENT_TIMESTAMP, 0);
COMMIT;

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
INSERT INTO `SYSTEM_PERMISSION` VALUES (39, 'system:location:create', '新建地区', '系统初始化创建', 0, 1, CURRENT_TIMESTAMP, NULL, NULL, 0);
INSERT INTO `SYSTEM_PERMISSION` VALUES (40, 'system:location:update', '修改地区', '系统初始化创建', 0, 1, CURRENT_TIMESTAMP, NULL, NULL, 0);
INSERT INTO `SYSTEM_PERMISSION` VALUES (41, 'system:location:query', '查询地区', '系统初始化创建', 0, 1, CURRENT_TIMESTAMP, NULL, NULL, 0);
INSERT INTO `SYSTEM_PERMISSION` VALUES (42, 'system:datapermission:create', '新建数据权限', '系统初始化创建', 0, 1, CURRENT_TIMESTAMP, NULL, NULL, 0);
INSERT INTO `SYSTEM_PERMISSION` VALUES (43, 'system:datapermission:delete', '删除数据权限', '系统初始化创建', 0, 1, CURRENT_TIMESTAMP, NULL, NULL, 0);
INSERT INTO `SYSTEM_PERMISSION` VALUES (44, 'system:datapermission:update', '修改数据权限', '系统初始化创建', 0, 1, CURRENT_TIMESTAMP, NULL, NULL, 0);
INSERT INTO `SYSTEM_PERMISSION` VALUES (45, 'system:datapermission:query', '查询数据权限', '系统初始化创建', 0, 1, CURRENT_TIMESTAMP, NULL, NULL, 0);
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
INSERT INTO `SYSTEM_ROLE_MENU` VALUES (21, 1, 15, 1, CURRENT_TIMESTAMP, NULL, NULL, 0);
-- 管理员角色菜单
INSERT INTO `SYSTEM_ROLE_MENU` VALUES (14, 2, 1, 1, CURRENT_TIMESTAMP, NULL, NULL, 0);
INSERT INTO `SYSTEM_ROLE_MENU` VALUES (15, 2, 2, 1, CURRENT_TIMESTAMP, NULL, NULL, 0);
INSERT INTO `SYSTEM_ROLE_MENU` VALUES (16, 2, 4, 1, CURRENT_TIMESTAMP, NULL, NULL, 0);
INSERT INTO `SYSTEM_ROLE_MENU` VALUES (17, 2, 5, 1, CURRENT_TIMESTAMP, NULL, NULL, 0);
INSERT INTO `SYSTEM_ROLE_MENU` VALUES (18, 2, 7, 1, CURRENT_TIMESTAMP, NULL, NULL, 0);
INSERT INTO `SYSTEM_ROLE_MENU` VALUES (19, 2, 8, 1, CURRENT_TIMESTAMP, NULL, NULL, 0);
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
INSERT INTO `SYSTEM_ROLE_PERMISSION` VALUES (39, 2, 16, 1, CURRENT_TIMESTAMP, NULL, NULL, 0);
INSERT INTO `SYSTEM_ROLE_PERMISSION` VALUES (40, 2, 17, 1, CURRENT_TIMESTAMP, NULL, NULL, 0);
INSERT INTO `SYSTEM_ROLE_PERMISSION` VALUES (41, 2, 18, 1, CURRENT_TIMESTAMP, NULL, NULL, 0);
INSERT INTO `SYSTEM_ROLE_PERMISSION` VALUES (42, 2, 20, 1, CURRENT_TIMESTAMP, NULL, NULL, 0);
INSERT INTO `SYSTEM_ROLE_PERMISSION` VALUES (43, 2, 21, 1, CURRENT_TIMESTAMP, NULL, NULL, 0);
INSERT INTO `SYSTEM_ROLE_PERMISSION` VALUES (44, 2, 10, 1, CURRENT_TIMESTAMP, NULL, NULL, 0);
INSERT INTO `SYSTEM_ROLE_PERMISSION` VALUES (45, 2, 23, 1, CURRENT_TIMESTAMP, NULL, NULL, 0);
INSERT INTO `SYSTEM_ROLE_PERMISSION` VALUES (46, 2, 24, 1, CURRENT_TIMESTAMP, NULL, NULL, 0);
INSERT INTO `SYSTEM_ROLE_PERMISSION` VALUES (47, 2, 25, 1, CURRENT_TIMESTAMP, NULL, NULL, 0);
INSERT INTO `SYSTEM_ROLE_PERMISSION` VALUES (48, 2, 26, 1, CURRENT_TIMESTAMP, NULL, NULL, 0);
INSERT INTO `SYSTEM_ROLE_PERMISSION` VALUES (49, 2, 27, 1, CURRENT_TIMESTAMP, NULL, NULL, 0);
INSERT INTO `SYSTEM_ROLE_PERMISSION` VALUES (50, 2, 28, 1, CURRENT_TIMESTAMP, NULL, NULL, 0);
INSERT INTO `SYSTEM_ROLE_PERMISSION` VALUES (51, 2, 29, 1, CURRENT_TIMESTAMP, NULL, NULL, 0);
INSERT INTO `SYSTEM_ROLE_PERMISSION` VALUES (52, 2, 30, 1, CURRENT_TIMESTAMP, NULL, NULL, 0);
INSERT INTO `SYSTEM_ROLE_PERMISSION` VALUES (53, 2, 31, 1, CURRENT_TIMESTAMP, NULL, NULL, 0);
INSERT INTO `SYSTEM_ROLE_PERMISSION` VALUES (54, 2, 32, 1, CURRENT_TIMESTAMP, NULL, NULL, 0);
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
INSERT INTO `SYSTEM_USER` VALUES (2, 'manager', '管理员', NULL, NULL, '1', NULL, NULL, '/avatar/man.png', 'c2b61790649412a0079b4ff9c8196a4d', 'kl38c6', 0, NULL, CURRENT_TIMESTAMP, NULL, NULL, 0);
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
