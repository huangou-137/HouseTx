/*
SQLyog v10.2 
MySQL - 8.0.20 : Database - db_housetx
*********************************************************************
*/


/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`db_housetx` /*!40100 DEFAULT CHARACTER SET utf8 */ /*!80016 DEFAULT ENCRYPTION='N' */;

USE `db_housetx`;

/*Table structure for table `t_admin` */

DROP TABLE IF EXISTS `t_admin`;

CREATE TABLE `t_admin` (
  `admin_id` smallint NOT NULL AUTO_INCREMENT,
  `admin_name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `pass` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `tel` varchar(15) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '固话',
  `email` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `desc` varchar(50) DEFAULT NULL COMMENT '简介',
  PRIMARY KEY (`admin_id`),
  UNIQUE KEY `UK_admin_name` (`admin_name`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

/*Table structure for table `t_area` */

DROP TABLE IF EXISTS `t_area`;

CREATE TABLE `t_area` (
  `area_id` smallint unsigned NOT NULL AUTO_INCREMENT,
  `pid` smallint unsigned NOT NULL DEFAULT '0' COMMENT '所属（父）地区编号',
  `name` varchar(35) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '',
  `type` tinyint(1) NOT NULL DEFAULT '3' COMMENT '默认为县级',
  PRIMARY KEY (`area_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3410 DEFAULT CHARSET=utf8;

/*Table structure for table `t_house` */

DROP TABLE IF EXISTS `t_house`;

CREATE TABLE `t_house` (
  `id` int NOT NULL AUTO_INCREMENT,
  `uid` int NOT NULL COMMENT '房主（登记人）ID',
  `area_id` smallint unsigned NOT NULL DEFAULT '1' COMMENT '所属区域编号',
  `address` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '详细地址',
  `kind_id` tinyint NOT NULL DEFAULT '1' COMMENT '默认为商品房',
  `type_id` tinyint NOT NULL DEFAULT '1' COMMENT '户型编号',
  `size` smallint NOT NULL COMMENT '面积（平方米）',
  `price` double unsigned NOT NULL COMMENT '预期价格',
  `desc` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '描述',
  `state` tinyint NOT NULL DEFAULT '0' COMMENT '0，审核中；-1，审核失败；1，审核成功，待售中；2，交易中；3，交易完成',
  `time` timestamp NOT NULL COMMENT '登记时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1023 DEFAULT CHARSET=utf8;

/*Table structure for table `t_house_kind` */

DROP TABLE IF EXISTS `t_house_kind`;

CREATE TABLE `t_house_kind` (
  `kind_id` tinyint unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '房子类别名称',
  PRIMARY KEY (`kind_id`),
  UNIQUE KEY `UK_houseKind` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8;

/*Table structure for table `t_house_type` */

DROP TABLE IF EXISTS `t_house_type`;

CREATE TABLE `t_house_type` (
  `type_id` tinyint unsigned NOT NULL AUTO_INCREMENT,
  `desc` varchar(15) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '户型描述(?厅?室)',
  PRIMARY KEY (`type_id`),
  UNIQUE KEY `UK_houseType` (`desc`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8;

/*Table structure for table `t_order` */

DROP TABLE IF EXISTS `t_order`;

CREATE TABLE `t_order` (
  `id` int NOT NULL AUTO_INCREMENT,
  `house_id` int NOT NULL COMMENT '房屋编号',
  `seller_id` int NOT NULL COMMENT '卖方ID',
  `buyer_id` int NOT NULL COMMENT '买方ID',
  `state` tinyint NOT NULL DEFAULT '0' COMMENT '-3~2',
  `launch_time` timestamp NOT NULL COMMENT '买方发起时间',
  `accept_time` timestamp NULL DEFAULT NULL COMMENT '卖方接受时间',
  `end_time` timestamp NULL DEFAULT NULL COMMENT '订单结束（取消、拒绝、成功、失败）时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=105 DEFAULT CHARSET=utf8;

/*Table structure for table `t_user` */

DROP TABLE IF EXISTS `t_user`;

CREATE TABLE `t_user` (
  `uid` int NOT NULL AUTO_INCREMENT,
  `username` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户名',
  `login_pass` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '登录密码',
  `tx_pass` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '交易密码',
  `phone` char(11) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '联系方式',
  `email` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '电子邮箱',
  `desc` varchar(50) DEFAULT NULL COMMENT '简介',
  PRIMARY KEY (`uid`),
  UNIQUE KEY `UK_user_name` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
