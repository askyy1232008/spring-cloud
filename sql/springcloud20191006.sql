/*
Navicat MySQL Data Transfer

Source Server         : 120.77.81.96
Source Server Version : 50553
Source Host           : 120.77.81.96:3306
Source Database       : springcloud

Target Server Type    : MYSQL
Target Server Version : 50553
File Encoding         : 65001

Date: 2019-10-06 16:57:47
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `sys_log`
-- ----------------------------
DROP TABLE IF EXISTS `sys_log`;
CREATE TABLE `sys_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `username` varchar(50) DEFAULT NULL COMMENT '用户名',
  `operation` varchar(50) DEFAULT NULL COMMENT '用户操作',
  `time` int(11) DEFAULT NULL COMMENT '响应时间',
  `method` varchar(200) DEFAULT NULL COMMENT '请求方法',
  `params` varchar(500) DEFAULT NULL COMMENT '请求参数',
  `ip` varchar(64) DEFAULT NULL COMMENT 'IP地址',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_log
-- ----------------------------
INSERT INTO `sys_log` VALUES ('1', 'lee', '查询用户信息', '964', 'com.cloud.provider.controller.UserController.getUser()', '  id: 1', '0:0:0:0:0:0:0:1', '2019-10-06 16:46:30');
INSERT INTO `sys_log` VALUES ('2', 'lee', '查询用户信息', '176', 'com.cloud.provider.controller.UserController.getUser()', '  id: 3', '192.168.1.36', '2019-10-06 16:52:21');

-- ----------------------------
-- Table structure for `user`
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) NOT NULL,
  `age` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', 'lee', '30');
INSERT INTO `user` VALUES ('2', 'tom', '25');
INSERT INTO `user` VALUES ('3', 'test', '12');
INSERT INTO `user` VALUES ('4', 'test11', '12');
