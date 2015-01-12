/*
SQLyog 企业版 - MySQL GUI v8.14 
MySQL - 5.5.37-log : Database - wood_vine
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`wood_vine` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `wood_vine`;

/*Table structure for table `tb_about_me` */

DROP TABLE IF EXISTS `tb_about_me`;

CREATE TABLE `tb_about_me` (
  `id` int(11) NOT NULL,
  `name` varchar(100) DEFAULT NULL,
  `birthday` varchar(11) DEFAULT NULL,
  `hometown` varchar(100) DEFAULT NULL,
  `graduate_college` varchar(50) DEFAULT NULL,
  `skill` varchar(500) DEFAULT NULL,
  `hobby` varchar(100) DEFAULT NULL,
  `telephone` varchar(20) DEFAULT NULL,
  `blog_url` varchar(100) DEFAULT NULL,
  `introduction` varchar(500) DEFAULT NULL COMMENT '个人简介',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `tb_about_me` */

insert  into `tb_about_me`(`id`,`name`,`birthday`,`hometown`,`graduate_college`,`skill`,`hobby`,`telephone`,`blog_url`,`introduction`) values (1,'WoodWang','1988-11-21','谷城','武汉工业学院','java','乒乓球','010-55656','http://blog.csdn.net/wojiushiwo945you','觉悟吧，少年!——认真编码，认真生活！');

/*Table structure for table `tb_authority` */

DROP TABLE IF EXISTS `tb_authority`;

CREATE TABLE `tb_authority` (
  `id` int(20) NOT NULL,
  `authority_desc` varchar(100) DEFAULT NULL COMMENT '操作描述',
  `authority_name` varchar(30) NOT NULL COMMENT '操作名称',
  `authority_url` varchar(100) NOT NULL COMMENT '功能地址',
  `t_order` int(2) DEFAULT NULL COMMENT '功能序号',
  `type` int(1) NOT NULL COMMENT '1:菜单；2：功能函数',
  `parent_id` int(20) DEFAULT NULL COMMENT '父级菜单编号（只有两级功能）',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `tb_authority` */

insert  into `tb_authority`(`id`,`authority_desc`,`authority_name`,`authority_url`,`t_order`,`type`,`parent_id`) values (1,'用户管理，启用、禁用用户','用户管理','/userManage',1,1,NULL),(2,'权限控制，给用户分配权限','权限控制','/authorityManage',2,1,NULL),(3,'内容管理，个人日记等','碎碎叨叨','/contentManage',3,1,NULL),(4,'我观天气，天气预报链接','今日天气','/weatherGet',4,1,NULL),(5,'关于阿木，网站开发者个人简介','关于阿木','/aboutMe',5,1,NULL),(6,'叨叨英语','叨叨英语','/dailyEnglish',6,1,3),(7,'我爱记单词','单词本','/vocabulary',7,1,3),(8,'近日备忘','近日备忘','/remind',8,1,3),(9,'心上的时光','心上的时光','/memory',9,1,3);

/*Table structure for table `tb_daily_english` */

DROP TABLE IF EXISTS `tb_daily_english`;

CREATE TABLE `tb_daily_english` (
  `id` int(11) NOT NULL,
  `type` smallint(1) DEFAULT NULL COMMENT '类型：1（词汇），2（句子）',
  `value` varchar(500) DEFAULT NULL COMMENT '值',
  `explain` varchar(500) DEFAULT NULL COMMENT '解释',
  `create_time` int(11) DEFAULT NULL COMMENT '创建时间:Unix时间11位的整数',
  PRIMARY KEY (`id`),
  KEY `create_index` (`create_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `tb_daily_english` */

/*Table structure for table `tb_diary` */

DROP TABLE IF EXISTS `tb_diary`;

CREATE TABLE `tb_diary` (
  `id` int(20) NOT NULL,
  `type` smallint(1) DEFAULT NULL COMMENT '类型：1（diary）、2（memo)、3(daily english)',
  `create_time` int(11) DEFAULT NULL COMMENT '创建时间:Unix时间11位的整数',
  `update_time` int(11) DEFAULT NULL,
  `content` text COMMENT '内容',
  `auhtor` varchar(50) DEFAULT NULL COMMENT '作者',
  PRIMARY KEY (`id`),
  KEY `create_index` (`create_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `tb_diary` */

/*Table structure for table `tb_role` */

DROP TABLE IF EXISTS `tb_role`;

CREATE TABLE `tb_role` (
  `id` int(20) NOT NULL,
  `role_name` varchar(50) NOT NULL COMMENT '角色名称',
  `role_desc` varchar(100) DEFAULT NULL COMMENT '角色描述',
  `status` smallint(1) DEFAULT '1' COMMENT '状态：默认1正常，0：禁用',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `tb_role` */

insert  into `tb_role`(`id`,`role_name`,`role_desc`,`status`) values (1,'admin','系统管理员',1),(2,'audit','内容审计',1),(3,'user','普通用户',1);

/*Table structure for table `tb_role_authority` */

DROP TABLE IF EXISTS `tb_role_authority`;

CREATE TABLE `tb_role_authority` (
  `role_id` int(20) NOT NULL COMMENT '角色id',
  `authority_id` int(20) NOT NULL COMMENT '权限Id'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `tb_role_authority` */

insert  into `tb_role_authority`(`role_id`,`authority_id`) values (1,1),(1,2),(1,4),(1,5),(3,3),(3,4),(3,6),(3,7),(3,8),(3,9);

/*Table structure for table `tb_user` */

DROP TABLE IF EXISTS `tb_user`;

CREATE TABLE `tb_user` (
  `id` int(20) NOT NULL AUTO_INCREMENT COMMENT '记录主键，无实际意义',
  `login_name` varchar(30) NOT NULL COMMENT '登陆用户名（必须唯一）',
  `user_name` varchar(50) NOT NULL COMMENT '用户名称',
  `user_pwd` varchar(30) NOT NULL COMMENT '登陆密码（存储密文）',
  `nickname` varchar(30) DEFAULT NULL COMMENT '昵称',
  `pic_url` varchar(100) DEFAULT NULL COMMENT '用户头像地址',
  `email` varchar(100) DEFAULT NULL COMMENT '邮箱地址（找回密码需要使用）',
  `status` int(1) DEFAULT '1' COMMENT '状态（默认1：正常；0：禁用）',
  `role_id` int(10) DEFAULT NULL COMMENT '关联的角色ID',
  `create_time` int(11) DEFAULT NULL COMMENT '创建时间',
  `last_login_time` int(11) DEFAULT NULL COMMENT '上次登陆时间:Unix时间11位的整数',
  PRIMARY KEY (`id`),
  KEY `create_index` (`create_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `tb_user` */

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
