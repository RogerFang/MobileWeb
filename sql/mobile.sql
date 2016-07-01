/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50625
Source Host           : localhost:3306
Source Database       : mobile

Target Server Type    : MYSQL
Target Server Version : 50625
File Encoding         : 65001

Date: 2016-07-01 19:53:59
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for predict
-- ----------------------------
DROP TABLE IF EXISTS `predict`;
CREATE TABLE `predict` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `model` varchar(20) DEFAULT NULL,
  `predict_month` varchar(20) DEFAULT NULL COMMENT '根据数据所需要预测的月份',
  `predict_month_data` varchar(255) DEFAULT NULL COMMENT '预测所用数据',
  `predict_precision` varchar(20) DEFAULT NULL COMMENT '当predict_month的数据录入时更新',
  `result_path` varchar(255) DEFAULT NULL COMMENT '预测结果数据存放位置',
  `state` int(1) unsigned zerofill DEFAULT NULL COMMENT '是否在预测中, 0:不在预测; 1在预测',
  `update_time` datetime DEFAULT NULL COMMENT '最近更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of predict
-- ----------------------------

-- ----------------------------
-- Table structure for train
-- ----------------------------
DROP TABLE IF EXISTS `train`;
CREATE TABLE `train` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `model` varchar(20) DEFAULT NULL COMMENT '训练所用模型',
  `train_month_data` varchar(255) DEFAULT NULL COMMENT '训练所用的数据文件, eg:201605.txt,201606.txt',
  `model_path` varchar(255) DEFAULT NULL COMMENT '训练得到的数据存储位置',
  `train_precision` varchar(20) DEFAULT NULL COMMENT '模型训练准确率',
  `state` int(1) unsigned zerofill DEFAULT NULL COMMENT '是否在训练:0未训练;1在训练;-1中止训练',
  `update_time` datetime DEFAULT NULL COMMENT '最新一次训练的时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of train
-- ----------------------------

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) DEFAULT NULL,
  `password` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', 'admin', 'admin');
