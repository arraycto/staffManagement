/*
SQLyog v10.2 
MySQL - 8.0.22 : Database - staff_information_management
*********************************************************************
*/


/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`staff_information_management` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;

USE `staff_information_management`;

/*Table structure for table `announcement` */

DROP TABLE IF EXISTS `announcement`;

CREATE TABLE `announcement` (
  `id` char(19) NOT NULL COMMENT 'id',
  `title` varchar(20) DEFAULT NULL COMMENT '公告标题',
  `content` varchar(5000) DEFAULT NULL COMMENT '备注',
  `time` datetime DEFAULT NULL COMMENT '发布时间',
  `is_deleted` int DEFAULT '0',
  `gmt_create` datetime DEFAULT NULL,
  `gmt_modified` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='公告表';

/*Data for the table `announcement` */

insert  into `announcement`(`id`,`title`,`content`,`time`,`is_deleted`,`gmt_create`,`gmt_modified`) values ('1340588733192654849','放假通知','<p>1月1日元旦放假一天！</p>','2020-12-30 08:00:00',0,'2020-12-20 17:23:56','2020-12-20 17:23:56'),('1340589777301721090','加班通知','<p>12月21日加班两小时</p>','2020-12-20 17:27:17',0,'2020-12-20 17:28:05','2020-12-20 17:28:05'),('1340607801471676417','放假通知','<p>明日放假一天</p>','2020-12-20 18:39:23',0,'2020-12-20 18:39:42','2020-12-20 18:39:42');

/*Table structure for table `attendance` */

DROP TABLE IF EXISTS `attendance`;

CREATE TABLE `attendance` (
  `id` char(19) NOT NULL COMMENT 'id',
  `name` varchar(20) DEFAULT NULL COMMENT '考勤名称',
  `status` char(1) DEFAULT '0' COMMENT '正常0,事假1,迟到2,早退3,病假4,旷工5,休补6',
  `time` datetime DEFAULT NULL COMMENT '考勤日期',
  `audit` char(1) DEFAULT '0' COMMENT '审核状态:0正在审核，1审核通过，2审核不通过',
  `department_id` char(19) DEFAULT NULL COMMENT '部门id',
  `clerk_id` char(19) DEFAULT NULL COMMENT '员工id',
  `is_deleted` int DEFAULT '0',
  `gmt_create` datetime DEFAULT NULL,
  `gmt_modified` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='考勤表';

/*Data for the table `attendance` */

insert  into `attendance`(`id`,`name`,`status`,`time`,`audit`,`department_id`,`clerk_id`,`is_deleted`,`gmt_create`,`gmt_modified`) values ('1340588910213255169',NULL,'4','2020-12-01 00:00:00','1','1340584045776916482','1340584292292939778',0,'2020-12-20 17:24:38','2020-12-20 17:26:03'),('1340605025320579074',NULL,'1','2020-12-23 00:00:00','1','1340603143789359106','1340603961473118210',0,'2020-12-20 18:28:40','2020-12-20 18:29:29');

/*Table structure for table `clerk` */

DROP TABLE IF EXISTS `clerk`;

CREATE TABLE `clerk` (
  `id` char(19) NOT NULL COMMENT 'id',
  `name` varchar(20) DEFAULT NULL COMMENT '职员姓名',
  `sex` char(1) DEFAULT NULL COMMENT '性别:0->女,1->男',
  `phone_number` char(11) DEFAULT NULL COMMENT '手机号码',
  `photo` varchar(255) DEFAULT NULL COMMENT '照片',
  `id_card` char(18) DEFAULT NULL COMMENT '身份证号',
  `address` varchar(50) DEFAULT NULL COMMENT '家庭住址',
  `position` varchar(25) DEFAULT NULL COMMENT '职位',
  `department_id` char(19) DEFAULT NULL COMMENT '部门编号',
  `user_id` char(19) DEFAULT NULL COMMENT '用户账号编号',
  `is_deleted` int DEFAULT '0',
  `gmt_create` datetime DEFAULT NULL,
  `gmt_modified` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='职员表';

/*Data for the table `clerk` */

insert  into `clerk`(`id`,`name`,`sex`,`phone_number`,`photo`,`id_card`,`address`,`position`,`department_id`,`user_id`,`is_deleted`,`gmt_create`,`gmt_modified`) values ('1340584292292939778','董悦','0','13470085017','https://food-photo.oss-cn-beijing.aliyuncs.com/2020/12/20/2b1ec46e322b46308c26331bf31ccce7file.png','142601199902147611','翻斗花园','经理','1340584045776916482','1340584292255191041',1,'2020-12-20 17:06:17','2020-12-20 17:06:17'),('1340585971394465793','胡图图','1','18842696491','https://food-photo.oss-cn-beijing.aliyuncs.com/2020/12/20/f03e12a8c73947b29b7b0d79d0d7bc70file.png','211303199810290041','翻斗花园','职工','1340584967022235649','1340585971365105666',0,'2020-12-20 17:12:57','2020-12-20 17:12:57'),('1340586440527368193','刘美美','0','13470052148','https://food-photo.oss-cn-beijing.aliyuncs.com/2020/12/20/3f0568f7ec2d45ca93bf56389e492c51file.png','211303199511010017','乾清宫','组长','1340584967022235649','1340586440502202369',1,'2020-12-20 17:14:49','2020-12-20 17:15:07'),('1340603961473118210','张红','0','15804940246','https://food-photo.oss-cn-beijing.aliyuncs.com/2020/12/20/cff75cb7c54f44d2a371a54b0a8c75a2file.png','211303199945210014','辽宁省朝阳市','部门经理','1340603143789359106','1340603961473118209',0,'2020-12-20 18:24:26','2020-12-20 18:24:26');

/*Table structure for table `clerk_through` */

DROP TABLE IF EXISTS `clerk_through`;

CREATE TABLE `clerk_through` (
  `id` char(19) NOT NULL COMMENT 'id',
  `clerk_id` char(19) NOT NULL COMMENT '员工id',
  `position` varchar(25) DEFAULT NULL COMMENT '职位',
  `department_id` char(19) DEFAULT NULL COMMENT '部门编号',
  `is_deleted` int DEFAULT '0',
  `gmt_create` datetime DEFAULT NULL,
  `gmt_modified` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='员工经历表';

/*Data for the table `clerk_through` */

insert  into `clerk_through`(`id`,`clerk_id`,`position`,`department_id`,`is_deleted`,`gmt_create`,`gmt_modified`) values ('1340584292368437249','1340584292292939778','经理','1340584045776916482',0,'2020-12-20 17:06:17','2020-12-20 17:06:17'),('1340585971432214529','1340585971394465793','职工','1340584967022235649',0,'2020-12-20 17:12:57','2020-12-20 17:12:57'),('1340586440552534018','1340586440527368193','组织','1340584967022235649',0,'2020-12-20 17:14:49','2020-12-20 17:14:49'),('1340586516209389570','1340586440527368193','组长','1340584967022235649',0,'2020-12-20 17:15:07','2020-12-20 17:15:07'),('1340603961548615682','1340603961473118210','部门经理','1340603143789359106',0,'2020-12-20 18:24:26','2020-12-20 18:24:26');

/*Table structure for table `contract` */

DROP TABLE IF EXISTS `contract`;

CREATE TABLE `contract` (
  `id` char(19) NOT NULL COMMENT 'id',
  `name` varchar(20) DEFAULT NULL COMMENT '合同名称',
  `time` datetime DEFAULT NULL COMMENT '签约日期',
  `clerk_id` char(19) DEFAULT NULL COMMENT '员工id',
  `is_deleted` int DEFAULT '0',
  `gmt_create` datetime DEFAULT NULL,
  `gmt_modified` datetime DEFAULT NULL,
  `timeout` datetime DEFAULT NULL COMMENT '截至时间',
  `url` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='合同表';

/*Data for the table `contract` */

insert  into `contract`(`id`,`name`,`time`,`clerk_id`,`is_deleted`,`gmt_create`,`gmt_modified`,`timeout`,`url`) values ('1340588132354412545','董悦的一年卖身契','2019-12-01 08:00:00','1340584292292939778',0,'2020-12-20 17:21:32','2020-12-20 17:21:32','2020-12-20 17:20:50','https://food-photo.oss-cn-beijing.aliyuncs.com/2020/12/20/74ae0aaf02cc4118a280f7a166ce5a6afile.png'),('1340604665474461698','张红的在职合同','2020-12-20 18:26:48','1340603961473118210',0,'2020-12-20 18:27:14','2020-12-20 18:27:14','2020-12-20 18:27:04',NULL);

/*Table structure for table `department` */

DROP TABLE IF EXISTS `department`;

CREATE TABLE `department` (
  `id` char(19) NOT NULL COMMENT 'id',
  `name` varchar(20) DEFAULT NULL COMMENT '部门名称',
  `note` varchar(50) DEFAULT NULL COMMENT '备注',
  `is_deleted` int DEFAULT '0',
  `gmt_create` datetime DEFAULT NULL,
  `gmt_modified` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='部门表';

/*Data for the table `department` */

insert  into `department`(`id`,`name`,`note`,`is_deleted`,`gmt_create`,`gmt_modified`) values ('1340584045776916482','人事部门',NULL,0,'2020-12-20 17:05:18','2020-12-20 18:20:26'),('1340584967022235649','运营部门',NULL,0,'2020-12-20 17:08:58','2020-12-20 18:22:14'),('1340585021044871170','市场部',NULL,0,'2020-12-20 17:09:11','2020-12-20 17:09:11'),('1340603143789359106','后勤部',NULL,1,'2020-12-20 18:21:11','2020-12-20 18:21:11'),('1340603278481043457','后勤部',NULL,0,'2020-12-20 18:21:43','2020-12-20 18:21:43');

/*Table structure for table `rewards_punishments` */

DROP TABLE IF EXISTS `rewards_punishments`;

CREATE TABLE `rewards_punishments` (
  `id` char(19) NOT NULL COMMENT 'id',
  `type` char(1) DEFAULT NULL COMMENT '奖惩类型:0奖励，1惩罚',
  `time` datetime DEFAULT NULL COMMENT '奖惩日期',
  `reason` varchar(50) DEFAULT NULL COMMENT '奖惩原因',
  `amount` decimal(19,4) DEFAULT NULL COMMENT '奖惩金额',
  `note` varchar(100) DEFAULT NULL COMMENT '备注',
  `clerk_id` char(19) DEFAULT NULL COMMENT '员工id',
  `is_deleted` int DEFAULT '0',
  `gmt_create` datetime DEFAULT NULL,
  `gmt_modified` datetime DEFAULT NULL,
  `department_id` char(19) DEFAULT NULL COMMENT '部门编号',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='奖惩表';

/*Data for the table `rewards_punishments` */

insert  into `rewards_punishments`(`id`,`type`,`time`,`reason`,`amount`,`note`,`clerk_id`,`is_deleted`,`gmt_create`,`gmt_modified`,`department_id`) values ('1340587640354164738','1','2020-08-31 08:00:00','旷工','10.0000',NULL,'1340586440527368193',0,'2020-12-20 17:19:35','2020-12-20 17:19:35','1340584967022235649'),('1340604436192833537','1','2020-12-20 18:26:00','旷工',NULL,NULL,'1340603961473118210',0,'2020-12-20 18:26:19','2020-12-20 18:26:19','1340603143789359106');

/*Table structure for table `role` */

DROP TABLE IF EXISTS `role`;

CREATE TABLE `role` (
  `id` char(19) NOT NULL COMMENT 'id',
  `userid` char(19) DEFAULT NULL COMMENT '用户id',
  `name` varchar(20) DEFAULT NULL COMMENT '权限名称',
  `is_deleted` int DEFAULT '0',
  `gmt_create` datetime DEFAULT NULL,
  `gmt_modified` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='权限表';

/*Data for the table `role` */

insert  into `role`(`id`,`userid`,`name`,`is_deleted`,`gmt_create`,`gmt_modified`) values ('1','1','管理员',0,'2020-12-15 08:22:01','2020-12-15 08:22:03'),('1340584292318105602','1340584292255191041','职员',1,'2020-12-20 17:06:17','2020-12-20 17:06:17'),('1340584379555434498','1340584292255191041','人事经理',1,'2020-12-20 17:06:38','2020-12-20 17:06:38'),('1340585971415437313','1340585971365105666','职员',0,'2020-12-20 17:12:57','2020-12-20 17:12:57'),('1340586440544145409','1340586440502202369','职员',1,'2020-12-20 17:14:49','2020-12-20 17:14:49'),('1340603961473118211','1340603961473118209','职员',0,'2020-12-20 18:24:26','2020-12-20 18:24:26'),('1340605448387440641','1340603961473118209','人事经理',1,'2020-12-20 18:30:21','2020-12-20 18:30:21'),('1340606056737681409','1340603961473118209','人事经理',1,'2020-12-20 18:32:46','2020-12-20 18:32:46');

/*Table structure for table `user` */

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `id` char(19) NOT NULL COMMENT '用户id',
  `username` varchar(20) DEFAULT NULL COMMENT '用户账号',
  `password` varchar(200) DEFAULT NULL COMMENT '密码',
  `name` varchar(20) DEFAULT NULL COMMENT '用户名',
  `introduction` varchar(255) DEFAULT NULL COMMENT '用户介绍',
  `avatar` varchar(255) DEFAULT NULL COMMENT '用户头像',
  `is_deleted` int DEFAULT '0',
  `gmt_create` datetime DEFAULT NULL,
  `gmt_modified` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户表';

/*Data for the table `user` */

insert  into `user`(`id`,`username`,`password`,`name`,`introduction`,`avatar`,`is_deleted`,`gmt_create`,`gmt_modified`) values ('1','admin','e10adc3949ba59abbe56e057f20f883e','超级管理员','公司老板','https://food-photo.oss-cn-beijing.aliyuncs.com/2020/12/14/b3664f3621894d72939e1a9763d10817%E5%85%AC%E5%8F%B8.png',0,'2020-12-15 08:21:25','2020-12-15 08:21:26'),('1340584292255191041','13470085017','e10adc3949ba59abbe56e057f20f883e','董悦',NULL,'https://food-photo.oss-cn-beijing.aliyuncs.com/2020/12/20/2b1ec46e322b46308c26331bf31ccce7file.png',1,'2020-12-20 17:06:17','2020-12-20 17:06:17'),('1340585971365105666','18842696491','e10adc3949ba59abbe56e057f20f883e','胡图图',NULL,'https://food-photo.oss-cn-beijing.aliyuncs.com/2020/12/20/f03e12a8c73947b29b7b0d79d0d7bc70file.png',0,'2020-12-20 17:12:57','2020-12-20 17:12:57'),('1340586440502202369','13470052148','e10adc3949ba59abbe56e057f20f883e','刘美美',NULL,'https://food-photo.oss-cn-beijing.aliyuncs.com/2020/12/20/3f0568f7ec2d45ca93bf56389e492c51file.png',1,'2020-12-20 17:14:49','2020-12-20 17:15:07'),('1340603961473118209','15804940246','e10adc3949ba59abbe56e057f20f883e','张红',NULL,'https://food-photo.oss-cn-beijing.aliyuncs.com/2020/12/20/cff75cb7c54f44d2a371a54b0a8c75a2file.png',0,'2020-12-20 18:24:26','2020-12-20 18:36:46');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
