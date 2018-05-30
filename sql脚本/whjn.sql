/*
SQLyog Ultimate v12.5.0 (64 bit)
MySQL - 5.7.18-log : Database - whjn
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`whjn` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `whjn`;

/*Table structure for table `dfwdsj_equipment` */

DROP TABLE IF EXISTS `dfwdsj_equipment`;

CREATE TABLE `dfwdsj_equipment` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `propertyField` varchar(50) NOT NULL,
  `propertyValue` varchar(50) DEFAULT NULL,
  `qcId` int(11) NOT NULL,
  `typeId` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8;

/*Data for the table `dfwdsj_equipment` */

insert  into `dfwdsj_equipment`(`ID`,`propertyField`,`propertyValue`,`qcId`,`typeId`) values 
(1,'ID','1',1,3),
(2,'name','射孔枪测试1',1,3),
(3,'code','SQKCS1',1,3),
(10,'km','13',1,3);

/*Table structure for table `dfwdsj_equipmentfield` */

DROP TABLE IF EXISTS `dfwdsj_equipmentfield`;

CREATE TABLE `dfwdsj_equipmentfield` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `FIELDCODE` varchar(50) NOT NULL,
  `FIELDLENGTH` int(11) DEFAULT NULL,
  `FIELDNAME` varchar(50) NOT NULL,
  `FIELDTYPE` varchar(20) NOT NULL,
  `TYPEID` bigint(20) DEFAULT NULL,
  `FIELDCONTENT` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK7A55902526FF8ECF` (`TYPEID`),
  CONSTRAINT `FK7A55902526FF8ECF` FOREIGN KEY (`TYPEID`) REFERENCES `dfwdsj_equipmenttype` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;

/*Data for the table `dfwdsj_equipmentfield` */

insert  into `dfwdsj_equipmentfield`(`ID`,`FIELDCODE`,`FIELDLENGTH`,`FIELDNAME`,`FIELDTYPE`,`TYPEID`,`FIELDCONTENT`) values 
(1,'ID',50,'ID','textfield',3,''),
(2,'name',150,'名称','textfield',3,''),
(3,'ID',50,'ID','textfield',4,'11111'),
(4,'code',150,'编码','textfield',3,''),
(5,'name',150,'名称','textfield',4,''),
(6,'code',150,'编码','textfield',4,''),
(7,'km',100,'孔密','combo',3,'SKQ_KM'),
(8,'slt',100,'缩略图','box',3,NULL);

/*Table structure for table `dfwdsj_equipmenttype` */

DROP TABLE IF EXISTS `dfwdsj_equipmenttype`;

CREATE TABLE `dfwdsj_equipmenttype` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `CREATETIME` datetime DEFAULT NULL,
  `ISLEAF` smallint(6) NOT NULL,
  `LASTEDITTIME` datetime DEFAULT NULL,
  `PARENTID` bigint(20) DEFAULT NULL,
  `CODE` varchar(50) NOT NULL,
  `NAME` varchar(50) NOT NULL,
  `ORGID` bigint(20) DEFAULT NULL,
  `CUSER` bigint(20) DEFAULT NULL,
  `EUSER` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK1CBF0A8FCD924C18` (`ORGID`),
  KEY `FK1CBF0A8F26832AE4` (`CUSER`),
  KEY `FK1CBF0A8F269F59E6` (`EUSER`),
  CONSTRAINT `FK1CBF0A8F26832AE4` FOREIGN KEY (`CUSER`) REFERENCES `t_sys_user` (`ID`),
  CONSTRAINT `FK1CBF0A8F269F59E6` FOREIGN KEY (`EUSER`) REFERENCES `t_sys_user` (`ID`),
  CONSTRAINT `FK1CBF0A8FCD924C18` FOREIGN KEY (`ORGID`) REFERENCES `t_sys_org` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;

/*Data for the table `dfwdsj_equipmenttype` */

insert  into `dfwdsj_equipmenttype`(`ID`,`CREATETIME`,`ISLEAF`,`LASTEDITTIME`,`PARENTID`,`CODE`,`NAME`,`ORGID`,`CUSER`,`EUSER`) values 
(1,'2018-03-12 16:23:51',0,'2018-03-21 17:24:56',-1,'SKQ','射孔枪',1,1,1),
(3,'2018-03-12 16:24:43',1,'2018-03-22 16:43:28',1,'SKQ_A','射孔枪A型',1,1,1),
(4,'2018-03-12 16:25:18',1,'2018-03-20 18:03:20',1,'SKQ_B','射孔枪B型',1,1,NULL);

/*Table structure for table `t_sys_comcode` */

DROP TABLE IF EXISTS `t_sys_comcode`;

CREATE TABLE `t_sys_comcode` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `code` varchar(30) NOT NULL,
  `comments` varchar(128) DEFAULT NULL,
  `createTime` datetime DEFAULT NULL,
  `lastEditTime` datetime DEFAULT NULL,
  `name` varchar(255) NOT NULL,
  `parentId` bigint(20) DEFAULT NULL,
  `statue` smallint(6) NOT NULL,
  `type` smallint(6) NOT NULL,
  `value` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `code` (`code`)
) ENGINE=InnoDB AUTO_INCREMENT=103 DEFAULT CHARSET=utf8;

/*Data for the table `t_sys_comcode` */

insert  into `t_sys_comcode`(`id`,`code`,`comments`,`createTime`,`lastEditTime`,`name`,`parentId`,`statue`,`type`,`value`) values 
(1,'XTGL','系统管理','2017-11-10 16:55:53','2017-11-27 16:54:20','系统管理',-1,1,0,''),
(2,'CDGL','菜单管理','2017-11-08 14:52:54','2017-11-08 14:52:58','菜单管理',1,1,0,''),
(3,'MENU_CDLX','菜单类型','2017-11-08 14:54:41','2017-11-08 14:54:50','菜单类型',2,1,1,''),
(4,'CDLX_YMCD','页面菜单，末级菜单','2017-11-08 14:55:37','2017-11-28 11:04:36','页面菜单',3,1,2,'1'),
(5,'CDLX_AN','按钮','2017-11-08 14:56:06','2017-11-28 11:04:45','按钮',3,1,2,'2'),
(49,'GGDMGL','公共代码管理','2017-11-24 15:03:45','2017-11-24 15:03:48','公共代码管理',1,1,0,''),
(50,'GGDMGL_DMLX','代码类型','2017-11-24 15:05:30','2017-11-24 15:05:35','公共代码类型',49,1,1,''),
(51,'DMLX_FC','分层','2017-11-24 15:06:54','2017-11-24 15:06:58','分层',50,1,2,'0'),
(52,'DMLX_DM','代码','2017-11-24 15:07:16','2017-11-24 15:07:14','代码',50,1,2,'1'),
(53,'DMLX_SJ','数据','2017-11-24 15:07:44','2017-11-24 15:07:46','数据',50,1,2,'2'),
(54,'GLOBAL_ZT','状态','2017-11-24 15:09:24','2017-11-24 15:09:36','状态',57,1,1,''),
(55,'ZT_QY','启用','2017-11-24 15:09:59','2017-11-24 15:10:01','启用',54,1,2,'1'),
(56,'ZT_JY','停用','2017-11-24 15:10:18','2017-11-27 17:01:17','停用',54,1,2,'0'),
(57,'GLOBAL','全局代码','2017-11-24 15:11:33','2017-11-24 15:11:38','全局代码',1,1,0,''),
(76,'GLOBAL_YES_NO','是否标志位','2017-11-28 09:01:08','2017-11-28 09:01:08','是否标志位',57,1,1,''),
(77,'YES','代表是或者肯定','2017-11-28 09:01:47','2017-11-28 09:01:47','是',76,1,2,'1'),
(78,'NO','代表否或者否定','2017-11-28 09:02:16','2017-11-28 09:02:16','否',76,1,2,'0'),
(79,'CDLX_FCCD','分层菜单','2017-11-28 11:05:21','2017-11-28 11:05:21','分层菜单',3,1,2,'0'),
(80,'ZZTXGL','组织体系管理','2017-12-01 15:07:49','2017-12-01 15:07:49','组织体系管理',1,1,0,''),
(81,'ORG_TYPE','组织体系类别','2017-12-01 15:08:28','2017-12-01 15:09:46','组织体系类别',80,1,1,''),
(82,'ORG_ATTR','组织体系性质','2017-12-01 15:10:18','2017-12-01 15:10:18','组织体系性质',80,1,1,''),
(83,'ORG_ATTR_GS','公司','2017-12-01 15:11:41','2017-12-01 15:11:41','公司',82,1,2,'1'),
(84,'ORG_ATTR_BM','部门','2017-12-01 15:12:48','2017-12-01 15:12:57','部门',82,1,2,'2'),
(85,'ORG_ATTR_BZ','班组','2017-12-01 15:13:24','2017-12-01 15:13:28','班组',82,1,2,'3'),
(86,'ORG_TYPE_WBZZ','外部组织','2017-12-01 15:19:54','2017-12-01 15:20:25','外部组织',81,1,2,'0'),
(87,'ORG_TYPE_NBZZ','内部组织','2017-12-01 15:20:15','2017-12-01 15:20:31','内部组织',81,1,2,'1'),
(88,'DFDDSJ','','2018-03-12 15:19:20','2018-03-12 15:19:20','定方位定射角',-1,1,0,''),
(89,'QCSJK','','2018-03-12 15:19:46','2018-03-12 15:19:46','器材数据库',88,1,0,''),
(90,'ZDLX','字段类型','2018-03-12 15:20:34','2018-03-12 15:20:34','字段类型',89,1,1,''),
(91,'varchar','','2018-03-15 11:19:34','2018-03-21 19:36:09','textfield',90,1,2,'textfield'),
(92,'dataTime','','2018-03-15 11:19:45','2018-03-21 19:36:26','datetime',90,1,2,'datetime'),
(93,'comBox','','2018-03-20 14:42:34','2018-03-21 19:36:18','combo',90,1,2,'combo'),
(94,'file','','2018-03-20 14:42:53','2018-03-21 19:36:22','box',90,1,2,'box'),
(95,'QCFL','','2018-03-21 17:41:46','2018-03-21 17:41:46','器材分类',89,1,0,''),
(97,'SKQ','','2018-03-21 17:55:21','2018-03-21 17:55:21','射孔枪',95,1,0,'SKQ'),
(98,'SKQ_KM','','2018-03-21 17:56:33','2018-03-22 10:19:52','孔密',97,1,1,'SKQ_KM'),
(100,'13','','2018-03-21 17:57:31','2018-03-21 17:57:31','13',98,1,2,'13'),
(101,'16','','2018-03-21 17:57:44','2018-03-21 17:57:44','16',98,1,2,'16'),
(102,'24','','2018-03-21 17:57:49','2018-03-21 17:57:49','24',98,1,2,'24');

/*Table structure for table `t_sys_file` */

DROP TABLE IF EXISTS `t_sys_file`;

CREATE TABLE `t_sys_file` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `CREATETIME` datetime DEFAULT NULL,
  `FielName` varchar(50) NOT NULL,
  `FileSize` bigint(20) NOT NULL,
  `FileType` varchar(50) NOT NULL,
  `CUSER` bigint(20) DEFAULT NULL,
  `ObjID` bigint(20) NOT NULL,
  `objTb` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FKD288575926832AE4` (`CUSER`),
  CONSTRAINT `FKD288575926832AE4` FOREIGN KEY (`CUSER`) REFERENCES `t_sys_user` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `t_sys_file` */

/*Table structure for table `t_sys_menu` */

DROP TABLE IF EXISTS `t_sys_menu`;

CREATE TABLE `t_sys_menu` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `CREATETIME` datetime DEFAULT NULL,
  `ISDELETE` smallint(6) NOT NULL,
  `ISEDIT` smallint(6) NOT NULL,
  `LASTEDITTIME` datetime DEFAULT NULL,
  `CODE` varchar(50) NOT NULL,
  `NAME` varchar(50) NOT NULL,
  `PARENTID` bigint(20) DEFAULT NULL,
  `STATUE` smallint(6) NOT NULL,
  `TYPE` smallint(6) NOT NULL,
  `URL` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=61 DEFAULT CHARSET=utf8;

/*Data for the table `t_sys_menu` */

insert  into `t_sys_menu`(`ID`,`CREATETIME`,`ISDELETE`,`ISEDIT`,`LASTEDITTIME`,`CODE`,`NAME`,`PARENTID`,`STATUE`,`TYPE`,`URL`) values 
(1,'2017-11-28 16:58:19',0,1,'2017-11-28 16:58:08','XTGL','系统管理',-1,1,0,''),
(2,'2017-11-28 16:58:22',0,1,'2017-11-28 16:58:14','SYS_CDGL','菜单管理',1,1,1,'/sysManage/menuManage/mainpage.jsp'),
(3,'2017-11-28 16:58:23',0,1,'2017-11-28 16:58:16','ADD','新增',2,1,2,''),
(4,'2017-11-28 16:59:02',0,1,'2017-11-28 16:58:29','EDIT','编辑',2,1,2,''),
(5,'2017-11-28 16:59:04',0,1,'2017-11-28 16:58:31','VIEW','查看',2,1,2,''),
(6,'2017-11-28 16:59:06',0,1,'2017-11-28 16:58:34','DELE','删除',2,1,2,''),
(9,'2017-11-28 16:59:15',0,1,'2017-11-28 16:58:41','DFWDSJ','定方位定射角',-1,1,0,''),
(11,'2017-11-28 16:59:20',0,1,'2017-11-28 16:58:47','SYS_GGDM','公共代码管理',1,1,1,'/sysManage/comCodeManage/mainpage.jsp'),
(13,'2017-11-28 16:59:26',0,1,'2017-11-28 16:58:52','ADD','新增',11,1,2,''),
(14,'2017-11-28 16:59:29',0,1,'2017-11-28 16:58:55','EDIT','编辑',11,1,2,''),
(15,'2017-11-28 16:59:32',0,1,'2017-11-28 16:58:57','VIEW','查看',11,1,2,''),
(16,'2017-11-28 16:59:34',0,1,'2017-11-28 16:58:59','DELE','删除',11,1,2,''),
(25,'2017-11-29 09:44:39',0,1,'2017-11-29 09:44:39','SYS_QXGL','权限管理',1,1,0,''),
(26,'2017-11-29 09:56:15',0,0,'2017-11-29 09:56:15','SYS_ZZTXGL','组织体系管理',1,1,0,''),
(27,'2017-11-29 10:15:44',1,1,'2017-11-29 10:15:44','ZZDYWH','组织单元维护',26,1,1,'/sysManage/orgManage/org/mainpage.jsp'),
(28,'2017-11-29 10:16:35',1,1,'2017-11-29 10:16:35','YHGL','用户维护',26,1,1,'/sysManage/orgManage/user/mainpage.jsp'),
(29,'2017-11-29 18:01:32',1,1,'2017-11-29 18:01:29','GWWH','岗位维护',26,1,1,'/sysManage/orgManage/post/mainpage.jsp'),
(30,'2017-11-29 18:02:17',1,1,'2017-11-29 18:02:14','ZWWH','职位维护',26,1,1,'/sysManage/orgManage/position/mainpage.jsp'),
(31,'2017-11-30 17:06:03',1,1,'2017-11-30 17:06:03','JSWH','角色维护',25,1,1,'/sysManage/authorityManage/role/mainpage.jsp'),
(32,'2017-11-30 17:07:11',1,1,'2017-11-30 17:07:11','JSSQ','角色授权',25,1,1,'/sysManage/authorityManage/roleAuthorize/mainpage.jsp'),
(33,'2017-12-01 16:57:01',1,1,'2017-12-01 16:57:01','ADD','新增',27,1,2,''),
(34,'2017-12-01 16:57:17',1,1,'2017-12-01 16:57:17','EDIT','编辑',27,1,2,''),
(35,'2017-12-01 16:57:23',1,1,'2017-12-01 16:57:23','DELE','删除',27,1,2,''),
(36,'2017-12-05 16:12:22',1,1,'2017-12-05 16:12:22','ADD','新增',28,1,2,''),
(37,'2017-12-05 16:12:29',1,1,'2017-12-05 16:12:29','EDIT','编辑',28,1,2,''),
(38,'2017-12-05 16:12:38',1,1,'2017-12-05 16:12:38','DELE','删除',28,1,2,''),
(39,'2017-12-13 17:06:15',1,1,'2017-12-13 17:06:15','ADD','新增',31,1,2,''),
(40,'2017-12-13 17:06:25',1,1,'2017-12-13 17:06:25','EDIT','编辑',31,1,2,''),
(41,'2017-12-13 17:06:35',1,1,'2017-12-13 17:06:35','DELE','删除',31,1,2,''),
(42,'2017-12-19 15:19:05',1,1,'2017-12-19 15:19:11','SAVE','保存授权',32,1,2,''),
(43,'2018-03-07 16:57:36',1,1,'2018-03-07 16:57:36','DFWDSJ_QCSJK','器材数据库维护',9,1,0,''),
(44,'2018-01-03 20:17:19',1,1,'2018-03-07 16:55:54','DFWDSJ_QCFL','器材分类维护',43,1,1,'/dfwdsj/materialManage/category/mainpage.jsp'),
(45,'2018-01-03 20:19:23',1,1,'2018-01-03 20:19:23','DFWDSJ_JCSJWH','基础数据维护',9,1,1,'222'),
(46,'2018-01-03 20:19:45',1,1,'2018-01-03 20:19:45','DFWDSJ_FASJ','方案设计',9,1,0,''),
(47,'2018-01-03 20:20:53',1,1,'2018-01-03 20:20:53','GZSJ','管柱设计',46,1,1,'123'),
(48,'2018-01-03 20:21:12',1,1,'2018-01-03 20:21:12','PPSJ','排炮设计',46,1,1,'11'),
(49,'2018-01-03 20:21:34',1,1,'2018-01-03 21:59:21','FWSJ','方位设计',46,1,1,'123'),
(50,'2018-01-03 20:21:56',1,1,'2018-01-03 20:21:56','SJSJ','射角设计',46,1,1,'123'),
(51,'2018-01-03 20:22:43',1,1,'2018-01-03 20:22:43','SYJLFXYWH','试验记录分析与维护',9,1,1,'123'),
(52,'2018-01-03 20:23:07',1,1,'2018-01-03 20:23:07','SGGCJLFX','施工过程记录分析',9,1,1,'22'),
(53,'2018-03-06 10:26:36',1,1,'2018-03-06 10:26:36','ADD','新增',44,1,2,''),
(54,'2018-03-06 10:26:44',1,1,'2018-03-29 15:22:08','SAVE','保存',44,1,2,''),
(55,'2018-03-06 10:27:00',1,1,'2018-03-06 10:27:00','DELE','删除',44,1,2,''),
(56,'2018-03-07 16:56:59',1,1,'2018-03-07 16:56:59','DFWDSJ_QCWH','器材维护',43,1,1,'/dfwdsj/materialManage/equipment/mainpage.jsp'),
(57,'2018-03-29 15:24:44',1,1,'2018-03-29 15:24:44','ADD','新增',56,1,2,''),
(58,'2018-03-29 15:24:52',1,1,'2018-03-29 15:24:52','EDIT','编辑',56,1,2,''),
(59,'2018-03-29 15:24:59',1,1,'2018-03-29 15:24:59','DELE','删除',56,1,2,''),
(60,'2018-05-30 16:19:24',1,1,'2018-05-30 16:19:24','VIEW','查看',56,1,2,'');

/*Table structure for table `t_sys_org` */

DROP TABLE IF EXISTS `t_sys_org`;

CREATE TABLE `t_sys_org` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `ATTR` smallint(6) DEFAULT NULL,
  `CREATETIME` datetime DEFAULT NULL,
  `ORGCODE` varchar(50) NOT NULL,
  `ORGNAME` varchar(50) NOT NULL,
  `PARENTID` bigint(20) DEFAULT NULL,
  `TYPE` smallint(6) DEFAULT NULL,
  `STATUE` smallint(6) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8;

/*Data for the table `t_sys_org` */

insert  into `t_sys_org`(`ID`,`ATTR`,`CREATETIME`,`ORGCODE`,`ORGNAME`,`PARENTID`,`TYPE`,`STATUE`) values 
(1,1,'2017-12-11 17:06:06','WHJN','西安物华巨能爆破器材有限责任公司',-1,1,1),
(2,2,'2017-09-08 15:04:39','YFZX','研发中心',1,1,1),
(3,2,'2017-12-04 16:50:02','XXYRLZYGLB','行政与人力资源管理部',1,1,1),
(4,2,'2017-12-04 16:50:20','JYGHB','经营规划部',1,1,1),
(5,2,'2017-12-04 16:50:32','CWB','财务部',1,1,1),
(6,2,'2017-12-04 16:50:48','PZB','品质部',1,1,1),
(7,2,'2017-12-04 16:51:05','AQHBB','安全环保部',1,1,1),
(8,2,'2017-12-04 16:51:25','SCGLB','生产管理部',1,1,1),
(9,2,'2017-12-04 16:51:49','CGB','采供部',1,1,1),
(10,2,'2017-12-04 16:52:25','SCYXB','市场营销部',1,1,1),
(11,2,'2017-12-04 16:53:01','GCJSFWB','工程技术服务部',1,1,1),
(12,3,'2017-12-04 17:25:37','YFYZ','研发一组',2,1,1),
(13,3,'2017-12-04 17:25:53','YFEZ','研发二组',2,1,1),
(14,3,'2017-12-04 17:26:07','YFSZ','研发三组',2,1,1);

/*Table structure for table `t_sys_role` */

DROP TABLE IF EXISTS `t_sys_role`;

CREATE TABLE `t_sys_role` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `CREATETIME` datetime DEFAULT NULL,
  `ROLECODE` varchar(50) NOT NULL,
  `ROLENAME` varchar(50) NOT NULL,
  `ROLETYPEID` bigint(20) DEFAULT NULL,
  `comments` varchar(128) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FKD28DE25358453EE4` (`ROLETYPEID`),
  KEY `FKD28DE253D428ED86` (`ROLETYPEID`),
  CONSTRAINT `FKD28DE25358453EE4` FOREIGN KEY (`ROLETYPEID`) REFERENCES `t_sys_org` (`ID`),
  CONSTRAINT `FKD28DE253D428ED86` FOREIGN KEY (`ROLETYPEID`) REFERENCES `t_sys_roletype` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

/*Data for the table `t_sys_role` */

insert  into `t_sys_role`(`ID`,`CREATETIME`,`ROLECODE`,`ROLENAME`,`ROLETYPEID`,`comments`) values 
(1,'2017-09-08 15:05:51','XTGLY','系统管理员',1,''),
(2,'2017-09-08 15:07:17','PTYH','普通用户',2,''),
(3,'2017-09-08 15:49:41','YFZXGLY','研发中心管理员',3,'');

/*Table structure for table `t_sys_role_menu` */

DROP TABLE IF EXISTS `t_sys_role_menu`;

CREATE TABLE `t_sys_role_menu` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `CREATETIME` datetime DEFAULT NULL,
  `MENUID` bigint(20) DEFAULT NULL,
  `ROLEID` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=352 DEFAULT CHARSET=utf8;

/*Data for the table `t_sys_role_menu` */

insert  into `t_sys_role_menu`(`ID`,`CREATETIME`,`MENUID`,`ROLEID`) values 
(289,'2018-05-30 16:19:56',53,2),
(290,'2018-05-30 16:19:56',54,2),
(291,'2018-05-30 16:19:56',55,2),
(292,'2018-05-30 16:19:56',44,2),
(293,'2018-05-30 16:19:56',56,2),
(294,'2018-05-30 16:19:56',43,2),
(295,'2018-05-30 16:19:56',45,2),
(296,'2018-05-30 16:19:56',47,2),
(297,'2018-05-30 16:19:56',48,2),
(298,'2018-05-30 16:19:56',49,2),
(299,'2018-05-30 16:19:56',50,2),
(300,'2018-05-30 16:19:56',46,2),
(301,'2018-05-30 16:19:56',51,2),
(302,'2018-05-30 16:19:56',52,2),
(303,'2018-05-30 16:19:56',9,2),
(304,'2018-05-30 16:24:49',3,1),
(305,'2018-05-30 16:24:49',4,1),
(306,'2018-05-30 16:24:49',5,1),
(307,'2018-05-30 16:24:49',6,1),
(308,'2018-05-30 16:24:49',2,1),
(309,'2018-05-30 16:24:49',13,1),
(310,'2018-05-30 16:24:49',14,1),
(311,'2018-05-30 16:24:49',15,1),
(312,'2018-05-30 16:24:49',16,1),
(313,'2018-05-30 16:24:49',11,1),
(314,'2018-05-30 16:24:49',39,1),
(315,'2018-05-30 16:24:49',40,1),
(316,'2018-05-30 16:24:49',41,1),
(317,'2018-05-30 16:24:49',31,1),
(318,'2018-05-30 16:24:49',42,1),
(319,'2018-05-30 16:24:49',32,1),
(320,'2018-05-30 16:24:49',25,1),
(321,'2018-05-30 16:24:49',33,1),
(322,'2018-05-30 16:24:49',34,1),
(323,'2018-05-30 16:24:49',35,1),
(324,'2018-05-30 16:24:49',27,1),
(325,'2018-05-30 16:24:49',36,1),
(326,'2018-05-30 16:24:49',37,1),
(327,'2018-05-30 16:24:49',38,1),
(328,'2018-05-30 16:24:49',28,1),
(329,'2018-05-30 16:24:49',29,1),
(330,'2018-05-30 16:24:49',30,1),
(331,'2018-05-30 16:24:49',26,1),
(332,'2018-05-30 16:24:49',1,1),
(333,'2018-05-30 16:24:49',53,1),
(334,'2018-05-30 16:24:49',54,1),
(335,'2018-05-30 16:24:49',55,1),
(336,'2018-05-30 16:24:49',44,1),
(337,'2018-05-30 16:24:49',57,1),
(338,'2018-05-30 16:24:49',58,1),
(339,'2018-05-30 16:24:49',59,1),
(340,'2018-05-30 16:24:49',60,1),
(341,'2018-05-30 16:24:49',56,1),
(342,'2018-05-30 16:24:49',43,1),
(343,'2018-05-30 16:24:49',45,1),
(344,'2018-05-30 16:24:49',47,1),
(345,'2018-05-30 16:24:49',48,1),
(346,'2018-05-30 16:24:49',49,1),
(347,'2018-05-30 16:24:49',50,1),
(348,'2018-05-30 16:24:49',46,1),
(349,'2018-05-30 16:24:49',51,1),
(350,'2018-05-30 16:24:49',52,1),
(351,'2018-05-30 16:24:49',9,1);

/*Table structure for table `t_sys_role_user` */

DROP TABLE IF EXISTS `t_sys_role_user`;

CREATE TABLE `t_sys_role_user` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `CREATETIME` datetime DEFAULT NULL,
  `ROLEID` bigint(20) DEFAULT NULL,
  `USERID` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

/*Data for the table `t_sys_role_user` */

insert  into `t_sys_role_user`(`ID`,`CREATETIME`,`ROLEID`,`USERID`) values 
(1,'2017-09-08 15:06:41',1,1),
(2,'2017-09-08 15:08:42',2,2),
(3,'2017-09-08 15:50:29',3,1);

/*Table structure for table `t_sys_roletype` */

DROP TABLE IF EXISTS `t_sys_roletype`;

CREATE TABLE `t_sys_roletype` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `CREATETIME` datetime DEFAULT NULL,
  `ORGID` bigint(20) DEFAULT NULL,
  `ROLECODE` varchar(50) NOT NULL,
  `ROLENAME` varchar(50) NOT NULL,
  `comments` varchar(128) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK3986080DCD924C18` (`ORGID`),
  CONSTRAINT `FK3986080DCD924C18` FOREIGN KEY (`ORGID`) REFERENCES `t_sys_org` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

/*Data for the table `t_sys_roletype` */

insert  into `t_sys_roletype`(`ID`,`CREATETIME`,`ORGID`,`ROLECODE`,`ROLENAME`,`comments`) values 
(1,'2017-12-14 17:07:33',1,'GLY','管理员',''),
(2,'2017-12-01 11:41:48',1,'PTYH','普通用户',''),
(3,'2017-12-01 11:44:59',2,'GLY','管理员','');

/*Table structure for table `t_sys_user` */

DROP TABLE IF EXISTS `t_sys_user`;

CREATE TABLE `t_sys_user` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `CREATETIME` datetime DEFAULT NULL,
  `EMAIL` varchar(30) DEFAULT NULL,
  `LASTLOGINTIME` datetime DEFAULT NULL,
  `PASSWORD` varchar(32) DEFAULT '015212c7a321358ef41b6c7dc7fff356',
  `REALNAME` varchar(30) DEFAULT NULL,
  `STATUE` smallint(6) DEFAULT NULL,
  `TEL` varchar(15) DEFAULT NULL,
  `USERNAME` varchar(30) NOT NULL,
  `ORGID` bigint(20) DEFAULT NULL,
  `BASEORGID` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `USERNAME` (`USERNAME`),
  KEY `FKD28F4DA8CD924C18` (`ORGID`),
  KEY `FKD28F4DA85BDCDFE7` (`BASEORGID`),
  CONSTRAINT `FKD28F4DA85BDCDFE7` FOREIGN KEY (`BASEORGID`) REFERENCES `t_sys_org` (`ID`),
  CONSTRAINT `FKD28F4DA8CD924C18` FOREIGN KEY (`ORGID`) REFERENCES `t_sys_org` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

/*Data for the table `t_sys_user` */

insert  into `t_sys_user`(`ID`,`CREATETIME`,`EMAIL`,`LASTLOGINTIME`,`PASSWORD`,`REALNAME`,`STATUE`,`TEL`,`USERNAME`,`ORGID`,`BASEORGID`) values 
(1,'2017-09-01 16:33:48','414737645@qq.com','2018-05-30 16:23:47','e10adc3949ba59abbe56e057f20f883e','陈才',1,'18706730558','admin',14,1),
(2,'2018-01-04 10:42:53',NULL,'2018-01-04 10:48:12','e10adc3949ba59abbe56e057f20f883e','kenix',1,NULL,'test',14,1);

/* Function  structure for function  `getParentOrg` */

/*!50003 DROP FUNCTION IF EXISTS `getParentOrg` */;
DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` FUNCTION `getParentOrg`(rootId varchar(100)) RETURNS varchar(1000) CHARSET utf8
BEGIN   
DECLARE fid varchar(100) default '';   
DECLARE str varchar(1000) default rootId;   
  
WHILE rootId is not null  do   
    SET fid =(SELECT parentid FROM t_sys_org WHERE id = rootId);   
    IF fid is not null THEN   
        SET str = concat(str, ',', fid);   
        SET rootId = fid;   
    ELSE   
        SET rootId = fid;   
    END IF;   
END WHILE;   
return str;  
END */$$
DELIMITER ;

/* Function  structure for function  `getUser` */

/*!50003 DROP FUNCTION IF EXISTS `getUser` */;
DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` FUNCTION `getUser`(orgId INT) RETURNS varchar(4000) CHARSET utf8
BEGIN
	DECLARE sTemp VARCHAR(4000);
	DECLARE sTempChd VARCHAR(4000);
	SET sTemp='$';
	SET sTempChd = CAST(orgId AS CHAR);
	WHILE sTempChd IS NOT NULL DO
	SET sTemp= CONCAT(sTemp,',',sTempChd);
	SELECT GROUP_CONCAT(id) INTO sTempChd FROM t_sys_org WHERE FIND_IN_SET(parentId,sTempChd)>0;
	END WHILE;
	RETURN sTemp;
    END */$$
DELIMITER ;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
