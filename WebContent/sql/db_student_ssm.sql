/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50505
Source Host           : localhost:3306
Source Database       : db_student_ssm

Target Server Type    : MYSQL
Target Server Version : 50505
File Encoding         : 65001

Date: 2020-05-18 01:02:19
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for clazz
-- ----------------------------
DROP TABLE IF EXISTS `clazz`;
CREATE TABLE `clazz` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `gradeId` int(11) NOT NULL,
  `name` varchar(32) NOT NULL,
  `remark` varchar(512) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `gradeId` (`gradeId`),
  CONSTRAINT `clazz_ibfk_1` FOREIGN KEY (`gradeId`) REFERENCES `grade` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of clazz
-- ----------------------------
INSERT INTO `clazz` VALUES ('13', '1', '软件工程1101', '软件工程1101班');
INSERT INTO `clazz` VALUES ('14', '12', '计算机1101', '计算机专业1101班');
INSERT INTO `clazz` VALUES ('25', '23', '2018级软件工程8班', '软件系足球队');

-- ----------------------------
-- Table structure for grade
-- ----------------------------
DROP TABLE IF EXISTS `grade`;
CREATE TABLE `grade` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(32) NOT NULL,
  `remark` varchar(512) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of grade
-- ----------------------------
INSERT INTO `grade` VALUES ('1', '软件工程2011届', '计算机学院软件工程专业2011届');
INSERT INTO `grade` VALUES ('12', '计算机2011届', '计算机学院计算机专业2011');
INSERT INTO `grade` VALUES ('23', '软件工程系', '方向：软件开发，软件测试，互联网金融');

-- ----------------------------
-- Table structure for student
-- ----------------------------
DROP TABLE IF EXISTS `student`;
CREATE TABLE `student` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `clazzId` int(11) NOT NULL,
  `sn` varchar(32) NOT NULL,
  `username` varchar(32) NOT NULL,
  `password` varchar(32) NOT NULL,
  `sex` varchar(8) NOT NULL,
  `photo` varchar(128) NOT NULL,
  `remark` varchar(512) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `gradeId` (`clazzId`),
  CONSTRAINT `student_ibfk_1` FOREIGN KEY (`clazzId`) REFERENCES `clazz` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=44 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of student
-- ----------------------------
INSERT INTO `student` VALUES ('24', '13', 'S1543327958875', '张三', '123', '男', '/StudentManagerSSM/upload/1543408794070.jpg', '张三是河南人。');
INSERT INTO `student` VALUES ('42', '14', 'S1589700769651', '王首立', '11111', '男', '/SSMStudentManageSystem/upload/1589700761560.jpg', 'dddddd');
INSERT INTO `student` VALUES ('43', '25', 'S1589707421871', 'wsy', '123456', '男', '/SSMStudentManageSystem/upload/1589707394054.jpg', '哈哈哈哈哈');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(32) NOT NULL,
  `password` varchar(32) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', 'admin', 'admin');
INSERT INTO `user` VALUES ('17', 'wsy', '123456');
