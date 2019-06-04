/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50721
Source Host           : localhost:3306
Source Database       : dsdata

Target Server Type    : MYSQL
Target Server Version : 50721
File Encoding         : 65001

Date: 2019-05-02 04:48:44
*/

SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for `customer`
-- ----------------------------
DROP TABLE IF EXISTS `customer`;
CREATE TABLE `customer` (
  `id` tinyint(1) NOT NULL AUTO_INCREMENT,
  `address` varchar(255) DEFAULT NULL,
  `contact` double NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of customer
-- ----------------------------

-- ----------------------------
-- Table structure for `employee`
-- ----------------------------
DROP TABLE IF EXISTS `employee`;
CREATE TABLE `employee` (
  `id` tinyint(10) NOT NULL AUTO_INCREMENT,
  `address` varchar(255) DEFAULT NULL,
  `contact` int(10) NOT NULL,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of employee
-- ----------------------------
INSERT INTO `employee` VALUES ('4', 'sdfs', '1111111111', 'dsf');
INSERT INTO `employee` VALUES ('5', 'sfsdf', '222222222', 'dsfdsf');
INSERT INTO `employee` VALUES ('6', 'kadawatha', '711556102', 'Sha');
INSERT INTO `employee` VALUES ('7', 'sdfsf', '711556102', 'sdfsdf');
INSERT INTO `employee` VALUES ('8', 'sdfsf', '716575384', 'sdfsdf');
INSERT INTO `employee` VALUES ('9', 'dsfsd', '711556102', 'sdsd');

-- ----------------------------
-- Table structure for `item`
-- ----------------------------
DROP TABLE IF EXISTS `item`;
CREATE TABLE `item` (
  `code` varchar(255) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `qtyOnHand` int(11) NOT NULL,
  `unitPrice` double NOT NULL,
  PRIMARY KEY (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of item
-- ----------------------------
INSERT INTO `item` VALUES ('fgh', 'fghfg', '5', '10');

-- ----------------------------
-- Table structure for `oder`
-- ----------------------------
DROP TABLE IF EXISTS `oder`;
CREATE TABLE `oder` (
  `oID` varchar(10) NOT NULL,
  `oDate` date NOT NULL,
  `cus_id` varchar(10) NOT NULL,
  PRIMARY KEY (`oID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of oder
-- ----------------------------

-- ----------------------------
-- Table structure for `orderdetails`
-- ----------------------------
DROP TABLE IF EXISTS `orderdetails`;
CREATE TABLE `orderdetails` (
  `oID` varchar(10) NOT NULL,
  `item_id` varchar(40) NOT NULL,
  `qty` int(10) NOT NULL,
  `unit_price` double(10,2) NOT NULL,
  PRIMARY KEY (`oID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of orderdetails
-- ----------------------------

-- ----------------------------
-- Table structure for `supplier_order`
-- ----------------------------
DROP TABLE IF EXISTS `supplier_order`;
CREATE TABLE `supplier_order` (
  `supplier_order_id` int(11) NOT NULL AUTO_INCREMENT,
  `supplier_name` varchar(45) NOT NULL,
  `item_code` varchar(45) NOT NULL,
  `supplier_order_date` varchar(45) NOT NULL,
  `unit_price` varchar(45) NOT NULL,
  `description` varchar(45) NOT NULL,
  `qty` varchar(45) NOT NULL,
  `supplier_order_total` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`supplier_order_id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of supplier_order
-- ----------------------------
INSERT INTO `supplier_order` VALUES ('1', 'Ceylon Biscuts', '1101451', '2019/04/29', '50.00', 'Mari Biscuts', '20', '1000.00');
INSERT INTO `supplier_order` VALUES ('4', ' sdf', 'sdf', '2019-05-06', '23.0', ' sfsdf', '2', '46.0');
INSERT INTO `supplier_order` VALUES ('6', 'dsf', 'dfgg', '2019-05-07', '34.0', 'dfs', '2', '68.0');
INSERT INTO `supplier_order` VALUES ('7', ' sdf', 'sdf', '2019-05-06', '232.0', ' 3232', '2', '464.0');

-- ----------------------------
-- Table structure for `user`
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(256) NOT NULL,
  `user_password` varchar(256) NOT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', 'c1c224b03cd9bc7b6a86d77f5dace40191766c485cd55dc48caf9ac873335d6f', '03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4');
