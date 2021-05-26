/*
 Navicat Premium Data Transfer

 Source Server         : local
 Source Server Type    : MySQL
 Source Server Version : 80019
 Source Host           : localhost:3306
 Source Schema         : db_eva

 Target Server Type    : MySQL
 Target Server Version : 80019
 File Encoding         : 65001

 Date: 26/05/2021 15:08:28
*/

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
  `UPDATE_USER` int DEFAULT NULL COMMENT '更新人',
  `CREATE_TIME` datetime NOT NULL COMMENT '创建时间',
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
  `UPDATE_USER` int DEFAULT NULL COMMENT '更新人',
  `CREATE_TIME` datetime NOT NULL COMMENT '创建时间',
  `UPDATE_TIME` datetime DEFAULT NULL COMMENT '更新时间',
  `DELETED` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='字典';

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
  `UPDATE_USER` int DEFAULT NULL COMMENT '更新人',
  `CREATE_TIME` datetime NOT NULL COMMENT '创建时间',
  `UPDATE_TIME` datetime DEFAULT NULL COMMENT '更新时间',
  `DELETED` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='字典数据';

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
  `CREATE_TIME` datetime NOT NULL COMMENT '创建时间',
  `UPDATE_TIME` datetime DEFAULT NULL COMMENT '更新时间',
  `CREATE_USER` int NOT NULL COMMENT '创建者ID',
  `UPDATE_USER` int DEFAULT NULL COMMENT '更新者ID',
  `DELETED` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否已删除',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=62 DEFAULT CHARSET=utf8 COMMENT='系统菜单';

-- ----------------------------
-- Records of SYSTEM_MENU
-- ----------------------------
BEGIN;
INSERT INTO `SYSTEM_MENU` VALUES (1, NULL, '系统管理', '', '系统初始化创建', 'el-icon-setting', 0, 0, 1, '2021-05-15 19:11:56', '2021-05-24 02:41:06', 1, 1, 0);
INSERT INTO `SYSTEM_MENU` VALUES (2, 1, '菜单管理', '/system/menu', '系统初始化创建', 'el-icon-menu', 0, 2, 1, '2021-05-15 19:11:56', '2021-05-16 23:08:21', 1, 1, 0);
INSERT INTO `SYSTEM_MENU` VALUES (3, 1, '用户管理', '/system/user', '系统初始化创建', 'el-icon-user-solid', 0, 4, 1, '2021-05-15 19:11:56', '2021-05-23 11:49:05', 1, 1, 0);
INSERT INTO `SYSTEM_MENU` VALUES (4, 1, '角色管理', '/system/role', '系统初始化创建', 'yw-icon-role', 0, 5, 1, '2021-05-15 19:11:56', '2021-05-23 11:49:08', 1, 1, 0);
INSERT INTO `SYSTEM_MENU` VALUES (5, 1, '权限管理', '/system/permission', '系统初始化创建', 'yw-icon-permission', 0, 6, 1, '2021-05-15 19:11:56', '2021-05-23 00:59:52', 1, 1, 0);
INSERT INTO `SYSTEM_MENU` VALUES (6, 1, '服务监测', '/system/monitor', '系统初始化创建', 'yw-icon-listener', 0, 10, 1, '2021-05-15 19:11:56', '2021-05-23 01:35:23', 1, 1, 0);
INSERT INTO `SYSTEM_MENU` VALUES (21, 1, '字典管理', '/system/dict', '系统初始化创建', 'yw-icon-dictionary', 0, 9, 1, '2021-05-16 07:03:34', '2021-05-23 01:33:09', 1, 1, 0);
INSERT INTO `SYSTEM_MENU` VALUES (32, 1, '部门管理', '/system/department', '系统初始化创建', 'yw-icon-department', 0, 7, 1, '2021-05-17 10:13:16', '2021-05-23 01:19:29', 1, 1, 0);
INSERT INTO `SYSTEM_MENU` VALUES (33, 1, '岗位管理', '/system/position', '系统初始化创建', 'yw-icon-position', 0, 8, 1, '2021-05-17 10:13:42', '2021-05-23 01:26:59', 1, 1, 0);
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
  `CREATE_TIME` datetime NOT NULL COMMENT '创建时间',
  `UPDATE_TIME` datetime DEFAULT NULL COMMENT '更新时间',
  `CREATE_USER` int NOT NULL COMMENT '创建者ID',
  `UPDATE_USER` int DEFAULT NULL COMMENT '更新者ID',
  `DELETED` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否已删除',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=41 DEFAULT CHARSET=utf8 COMMENT='系统权限';

-- ----------------------------
-- Records of SYSTEM_PERMISSION
-- ----------------------------
BEGIN;
INSERT INTO `SYSTEM_PERMISSION` VALUES (1, 'system:permission:query', '查询权限', '系统初始化创建', 1, '2021-05-15 19:11:56', NULL, 1, NULL, 0);
INSERT INTO `SYSTEM_PERMISSION` VALUES (2, 'system:permission:create', '创建权限', '系统初始化创建', 1, '2021-05-15 19:11:56', NULL, 1, NULL, 0);
INSERT INTO `SYSTEM_PERMISSION` VALUES (3, 'system:permission:update', '修改权限', '系统初始化创建', 1, '2021-05-15 19:11:56', NULL, 1, NULL, 0);
INSERT INTO `SYSTEM_PERMISSION` VALUES (4, 'system:permission:delete', '删除权限', '系统初始化创建', 1, '2021-05-15 19:11:56', NULL, 1, NULL, 0);
INSERT INTO `SYSTEM_PERMISSION` VALUES (5, 'system:menu:query', '查询菜单', '系统初始化创建', 1, '2021-05-15 19:11:56', NULL, 1, NULL, 0);
INSERT INTO `SYSTEM_PERMISSION` VALUES (6, 'system:menu:create', '创建菜单', '系统初始化创建', 1, '2021-05-15 19:11:56', NULL, 1, NULL, 0);
INSERT INTO `SYSTEM_PERMISSION` VALUES (7, 'system:menu:update', '修改菜单', '系统初始化创建', 1, '2021-05-15 19:11:56', NULL, 1, NULL, 0);
INSERT INTO `SYSTEM_PERMISSION` VALUES (8, 'system:menu:delete', '删除菜单', '系统初始化创建', 1, '2021-05-15 19:11:56', NULL, 1, NULL, 0);
INSERT INTO `SYSTEM_PERMISSION` VALUES (9, 'system:menu:sort', '菜单排序', '系统初始化创建', 1, '2021-05-15 19:11:56', NULL, 1, NULL, 0);
INSERT INTO `SYSTEM_PERMISSION` VALUES (10, 'system:role:query', '查询角色', '系统初始化创建', 1, '2021-05-15 19:11:56', NULL, 1, NULL, 0);
INSERT INTO `SYSTEM_PERMISSION` VALUES (11, 'system:role:create', '创建角色', '系统初始化创建', 1, '2021-05-15 19:11:56', NULL, 1, NULL, 0);
INSERT INTO `SYSTEM_PERMISSION` VALUES (12, 'system:role:update', '修改角色', '系统初始化创建', 1, '2021-05-15 19:11:56', NULL, 1, NULL, 0);
INSERT INTO `SYSTEM_PERMISSION` VALUES (13, 'system:role:delete', '删除角色', '系统初始化创建', 1, '2021-05-15 19:11:56', NULL, 1, NULL, 0);
INSERT INTO `SYSTEM_PERMISSION` VALUES (14, 'system:role:createRoleMenu', '配置角色菜单', '系统初始化创建', 1, '2021-05-15 19:11:56', NULL, 1, NULL, 0);
INSERT INTO `SYSTEM_PERMISSION` VALUES (15, 'system:role:createRolePermission', '配置角色权限', '系统初始化创建', 1, '2021-05-15 19:11:56', NULL, 1, NULL, 0);
INSERT INTO `SYSTEM_PERMISSION` VALUES (16, 'system:user:query', '查询用户', '系统初始化创建', 1, '2021-05-15 19:11:56', NULL, 1, NULL, 0);
INSERT INTO `SYSTEM_PERMISSION` VALUES (17, 'system:user:create', '创建用户', '系统初始化创建', 1, '2021-05-15 19:11:56', NULL, 1, NULL, 0);
INSERT INTO `SYSTEM_PERMISSION` VALUES (18, 'system:user:update', '更新用户', '系统初始化创建', 1, '2021-05-15 19:11:56', NULL, 1, NULL, 0);
INSERT INTO `SYSTEM_PERMISSION` VALUES (19, 'system:user:delete', '删除用户', '系统初始化创建', 1, '2021-05-15 19:11:56', NULL, 1, NULL, 0);
INSERT INTO `SYSTEM_PERMISSION` VALUES (20, 'system:user:createUserRole', '配置用户角色', '系统初始化创建', 1, '2021-05-15 19:11:56', NULL, 1, NULL, 0);
INSERT INTO `SYSTEM_PERMISSION` VALUES (21, 'system:user:resetPwd', '重置用户密码', '系统初始化创建', 1, '2021-05-15 19:11:56', NULL, 1, NULL, 0);
INSERT INTO `SYSTEM_PERMISSION` VALUES (22, 'system:monitor:query', '查询服务监测', '系统初始化创建', 1, '2021-05-15 19:11:56', NULL, 1, NULL, 0);
INSERT INTO `SYSTEM_PERMISSION` VALUES (24, 'system:department:create', '创建部门', '系统初始化创建', 1, '2021-05-16 01:01:12', NULL, 1, NULL, 0);
INSERT INTO `SYSTEM_PERMISSION` VALUES (25, 'system:department:delete', '删除部门', '系统初始化创建', 1, '2021-05-16 01:01:20', NULL, 1, NULL, 0);
INSERT INTO `SYSTEM_PERMISSION` VALUES (26, 'system:department:update', '更新部门', '系统初始化创建', 1, '2021-05-16 01:01:28', NULL, 1, NULL, 0);
INSERT INTO `SYSTEM_PERMISSION` VALUES (27, 'system:department:query', '查询部门', '系统初始化创建', 1, '2021-05-16 01:01:37', NULL, 1, NULL, 0);
INSERT INTO `SYSTEM_PERMISSION` VALUES (28, 'system:position:create', '创建岗位', '系统初始化创建', 1, '2021-05-16 04:31:26', NULL, 1, NULL, 0);
INSERT INTO `SYSTEM_PERMISSION` VALUES (29, 'system:position:delete', '删除岗位', '系统初始化创建', 1, '2021-05-16 04:31:36', NULL, 1, NULL, 0);
INSERT INTO `SYSTEM_PERMISSION` VALUES (30, 'system:position:update', '更新岗位', '系统初始化创建', 1, '2021-05-16 04:32:30', NULL, 1, NULL, 0);
INSERT INTO `SYSTEM_PERMISSION` VALUES (31, 'system:position:query', '查询岗位', '系统初始化创建', 1, '2021-05-16 04:32:46', NULL, 1, NULL, 0);
INSERT INTO `SYSTEM_PERMISSION` VALUES (32, 'system:dict:create', '创建字典', '系统初始化创建', 1, '2021-05-16 07:03:58', NULL, 1, NULL, 0);
INSERT INTO `SYSTEM_PERMISSION` VALUES (33, 'system:dict:delete', '删除字典', '系统初始化创建', 1, '2021-05-16 07:04:08', NULL, 1, NULL, 0);
INSERT INTO `SYSTEM_PERMISSION` VALUES (34, 'system:dict:update', '修改字典', '系统初始化创建', 1, '2021-05-16 07:04:20', NULL, 1, NULL, 0);
INSERT INTO `SYSTEM_PERMISSION` VALUES (35, 'system:dict:query', '查询字典', '系统初始化创建', 1, '2021-05-16 07:04:31', '2021-05-21 02:53:30', 1, NULL, 0);
INSERT INTO `SYSTEM_PERMISSION` VALUES (39, 'system:department:queryUsers', '查看部门人员', '系统初始化创建', 1, '2021-05-23 22:58:05', NULL, 1, NULL, 0);
INSERT INTO `SYSTEM_PERMISSION` VALUES (40, 'system:position:queryUsers', '查询岗位人员', '系统初始化创建', 1, '2021-05-24 01:41:58', NULL, 1, NULL, 0);
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
  `UPDATE_USER` int DEFAULT NULL COMMENT '更新人',
  `CREATE_TIME` datetime NOT NULL COMMENT '创建时间',
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
  `CREATE_TIME` datetime NOT NULL COMMENT '创建时间',
  `UPDATE_TIME` datetime DEFAULT NULL COMMENT '更新时间',
  `CREATE_USER` int NOT NULL COMMENT '创建者ID',
  `UPDATE_USER` int DEFAULT NULL COMMENT '更新者ID',
  `DELETED` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否已删除',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8 COMMENT='系统角色';

-- ----------------------------
-- Records of SYSTEM_ROLE
-- ----------------------------
BEGIN;
INSERT INTO `SYSTEM_ROLE` VALUES (1, 'admin', '超级管理员', '系统初始化创建', 1, '2021-05-15 19:11:56', NULL, 1, NULL, 0);
COMMIT;

-- ----------------------------
-- Table structure for SYSTEM_ROLE_MENU
-- ----------------------------
DROP TABLE IF EXISTS `SYSTEM_ROLE_MENU`;
CREATE TABLE `SYSTEM_ROLE_MENU` (
  `ID` int NOT NULL AUTO_INCREMENT COMMENT '主键',
  `ROLE_ID` int NOT NULL COMMENT '角色ID',
  `MENU_ID` int NOT NULL COMMENT '菜单ID',
  `CREATE_TIME` datetime NOT NULL COMMENT '创建时间',
  `UPDATE_TIME` datetime DEFAULT NULL,
  `CREATE_USER` int NOT NULL COMMENT '创建者ID',
  `UPDATE_USER` int DEFAULT NULL COMMENT '更新者ID',
  `DELETED` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否已删除',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色菜单关联';

-- ----------------------------
-- Table structure for SYSTEM_ROLE_PERMISSION
-- ----------------------------
DROP TABLE IF EXISTS `SYSTEM_ROLE_PERMISSION`;
CREATE TABLE `SYSTEM_ROLE_PERMISSION` (
  `ID` int NOT NULL AUTO_INCREMENT COMMENT '主键',
  `ROLE_ID` int NOT NULL COMMENT '角色ID',
  `PERMISSION_ID` int NOT NULL COMMENT '权限ID',
  `CREATE_TIME` datetime NOT NULL COMMENT '创建时间',
  `UPDATE_TIME` datetime DEFAULT NULL COMMENT '更新时间',
  `CREATE_USER` int NOT NULL COMMENT '创建者ID',
  `UPDATE_USER` int DEFAULT NULL COMMENT '更新人ID',
  `DELETED` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否已删除',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色权限关联';

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
  `SALT` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '盐',
  `FIXED` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否为固定用户',
  `CREATE_TIME` datetime NOT NULL COMMENT '创建时间',
  `UPDATE_TIME` datetime DEFAULT NULL COMMENT '更新时间',
  `CREATE_USER` int DEFAULT NULL COMMENT '创建者ID',
  `UPDATE_USER` int DEFAULT NULL COMMENT '更新者ID',
  `DELETED` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否已删除',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8 COMMENT='系统用户';

-- ----------------------------
-- Records of SYSTEM_USER
-- ----------------------------
BEGIN;
INSERT INTO `SYSTEM_USER` VALUES (1, 'admin', '超级管理员', '', NULL, '1', '', '', '/avatar/man.png', 'c2b61790649412a0079b4ff9c8196a4d', 'kl38c6', 1, '2021-05-15 19:11:56', '2021-05-20 03:18:45', NULL, 1, 0);
COMMIT;

-- ----------------------------
-- Table structure for SYSTEM_USER_ROLE
-- ----------------------------
DROP TABLE IF EXISTS `SYSTEM_USER_ROLE`;
CREATE TABLE `SYSTEM_USER_ROLE` (
  `ID` int NOT NULL AUTO_INCREMENT COMMENT '主键',
  `USER_ID` int NOT NULL COMMENT '用户ID',
  `ROLE_ID` int NOT NULL COMMENT '角色ID',
  `CREATE_TIME` datetime NOT NULL COMMENT '创建时间',
  `UPDATE_TIME` datetime DEFAULT NULL COMMENT '更新时间',
  `CREATE_USER` int NOT NULL COMMENT '创建者ID',
  `UPDATE_USER` int DEFAULT NULL COMMENT '更新者ID',
  `DELETED` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否已删除',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=35 DEFAULT CHARSET=utf8 COMMENT='用户角色关联';

-- ----------------------------
-- Records of SYSTEM_USER_ROLE
-- ----------------------------
BEGIN;
INSERT INTO `SYSTEM_USER_ROLE` VALUES (1, 1, 1, '2021-05-15 19:11:56', '2021-05-26 01:49:23', 1, NULL, 0);
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
