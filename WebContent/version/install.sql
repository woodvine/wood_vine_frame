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

insert  into `tb_about_me`(`id`,`name`,`birthday`,`hometown`,`graduate_college`,`skill`,`hobby`,`telephone`,`blog_url`,`introduction`) values (1,'WoodWang','1988-09-21','谷城','武汉工业学院','java','乒乓球','010-55656','http://blog.csdn.net/wojiushiwo945you','觉悟吧，少年!——认真编码，认真生活！');

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

insert  into `tb_authority`(`id`,`authority_desc`,`authority_name`,`authority_url`,`t_order`,`type`,`parent_id`) values (1,'首页','首页','introduce',1,1,NULL),(2,'加入WoodVine','加入WoodVine','toRegister',2,1,NULL),(3,'用户管理','用户管理','userManage',3,1,NULL),(4,'权限控制:给用户分配权限','权限控制','authorityManage',4,1,NULL),(5,'内容管理：网站基本功能（日志、备忘）','碎碎叨叨','contentManage',5,1,NULL),(6,'我观天气：天气预报链接','今日天气','weatherGet',6,1,NULL),(7,'关于阿木，网站开发者个人简介','关于阿木','aboutMe',7,1,NULL),(8,'我爱学英语','我爱学英语','englishList',2,1,5),(9,'每日一句','每日一句','dailyOne',3,1,5),(10,'心上的时光','心上的时光','diary',4,1,5);

/*Table structure for table `tb_daily_english` */

DROP TABLE IF EXISTS `tb_daily_english`;

CREATE TABLE `tb_daily_english` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `type` smallint(1) DEFAULT NULL COMMENT '类型：1（词汇），2（句子）',
  `content` varchar(500) DEFAULT NULL COMMENT '值',
  `value_cn` varchar(500) DEFAULT NULL COMMENT '中文解释',
  `create_time` int(11) DEFAULT NULL COMMENT '创建时间:Unix时间11位的整数',
  `value_desc` varchar(500) DEFAULT NULL COMMENT '单词描述：1类型时，必须的属性',
  `author` int(11) NOT NULL COMMENT '创建者',
  PRIMARY KEY (`id`),
  KEY `create_index` (`create_time`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

/*Data for the table `tb_daily_english` */

/*Table structure for table `tb_daily_good` */

DROP TABLE IF EXISTS `tb_daily_good`;

CREATE TABLE `tb_daily_good` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `content` varchar(500) DEFAULT NULL COMMENT '名句内容',
  `author` varchar(50) DEFAULT NULL COMMENT '出处',
  `chinese` varchar(500) DEFAULT NULL COMMENT '释义',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

/*Data for the table `tb_daily_good` */

insert  into `tb_daily_good`(`id`,`content`,`author`,`chinese`) values (1,'I am a slow walker,but I never walk backwards.','亚伯拉罕.林肯美国','我走得很慢，但是我从来不会后退。'),(2,'The man who has made up his mind to win will never say \"impossible \".','法国皇帝 拿破仑. B.','凡是决心取得胜利的人是从来不说“不可能的”。'),(3,'Never, never, never, never give up','英国首相 丘吉尔','永远不要、不要、不要、不要放弃。'),(4,'Cease to struggle and you cease to live.','卡莱尔','生命不止，奋斗不息。'),(5,'We must accept finite disappointment, but we must never lose infinite hope.','马丁 · 路德 · 金','我们必须接受失望，因为它是有限的，但千万不可失去希望，因为它是无穷的。'),(6,'Do not go gently into that good night.','星际穿越-电影','不要掉入生活那温柔的陷阱，生命在于奋斗。');

/*Table structure for table `tb_diary` */

DROP TABLE IF EXISTS `tb_diary`;

CREATE TABLE `tb_diary` (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `type` smallint(1) DEFAULT NULL COMMENT '类型：1（diary）、2（memo)',
  `create_time` int(11) DEFAULT NULL COMMENT '创建时间:Unix时间11位的整数',
  `update_time` int(11) DEFAULT NULL,
  `content` varchar(500) DEFAULT NULL COMMENT '内容',
  `author` int(20) DEFAULT NULL COMMENT '作者(用户ID)',
  `title` varchar(50) DEFAULT NULL COMMENT '标题',
  PRIMARY KEY (`id`),
  KEY `create_index` (`create_time`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

/*Data for the table `tb_diary` */

/*Table structure for table `tb_regin` */

DROP TABLE IF EXISTS `tb_regin`;

CREATE TABLE `tb_regin` (
  `code` varchar(30) NOT NULL COMMENT '区域码',
  `name` varchar(50) DEFAULT NULL COMMENT '区域名称',
  `type` int(1) DEFAULT NULL COMMENT '1:省；2：城市',
  `regin_desc` varchar(100) DEFAULT NULL COMMENT '区域描述',
  PRIMARY KEY (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `tb_regin` */

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

insert  into `tb_role`(`id`,`role_name`,`role_desc`,`status`) values (1,'admin','系统管理员',1),(2,'audit','内容审计',1),(3,'user','普通用户',1),(4,'wander','游客',1);

/*Table structure for table `tb_role_authority` */

DROP TABLE IF EXISTS `tb_role_authority`;

CREATE TABLE `tb_role_authority` (
  `role_id` int(20) NOT NULL COMMENT '角色id',
  `authority_id` int(20) NOT NULL COMMENT '权限Id'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `tb_role_authority` */

insert  into `tb_role_authority`(`role_id`,`authority_id`) values (1,1),(1,3),(1,4),(3,1),(3,6),(3,7),(3,8),(3,10),(4,2),(4,1),(1,6),(1,7),(4,6),(3,9),(4,9);

/*Table structure for table `tb_user` */

DROP TABLE IF EXISTS `tb_user`;

CREATE TABLE `tb_user` (
  `id` int(20) NOT NULL AUTO_INCREMENT COMMENT '记录主键，无实际意义',
  `login_name` varchar(30) NOT NULL COMMENT '登陆用户名（必须唯一）',
  `user_name` varchar(50) NOT NULL COMMENT '用户名称',
  `user_pwd` varchar(100) NOT NULL COMMENT '登陆密码（存储密文）',
  `nickname` varchar(30) DEFAULT NULL COMMENT '昵称',
  `pic_url` varchar(100) DEFAULT NULL COMMENT '用户头像地址',
  `email` varchar(100) DEFAULT NULL COMMENT '邮箱地址（找回密码需要使用）',
  `status` int(1) DEFAULT '1' COMMENT '状态（默认1：正常；0：禁用）',
  `role_id` int(10) DEFAULT NULL COMMENT '关联的角色ID',
  `create_time` int(11) DEFAULT NULL COMMENT '创建时间',
  `last_login_time` int(11) DEFAULT NULL COMMENT '上次登陆时间:Unix时间11位的整数',
  `old_pwd` varchar(30) DEFAULT NULL COMMENT '原密码：密码修改时验证使用',
  PRIMARY KEY (`id`),
  KEY `create_index` (`create_time`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

/*Data for the table `tb_user` */

insert  into `tb_user`(`id`,`login_name`,`user_name`,`user_pwd`,`nickname`,`pic_url`,`email`,`status`,`role_id`,`create_time`,`last_login_time`,`old_pwd`) values (1,'admin','admin','admin','admin',NULL,'admin@126.com',1,1,1420703513,1421386174,NULL),(2,'test','test','123456','test',NULL,'test@126.com',0,3,1420703513,1421136259,NULL),(3,'audit','audit','123456','audit',NULL,'audit@126.com',0,2,1420703513,1420703513,NULL),(4,'wood','阿木','wood','阿木',NULL,'woodvine@126.com',1,3,1421386202,1421395849,NULL),(5,'zhouzhou','舟舟','0ec173788c65dd08da60575219707632','舟舟',NULL,'zz@126.com',1,3,1422240198,1422240647,NULL),(6,'pine','pine','15dc8bed4e380a261b7794769b97a74d','pine',NULL,'pine@126.com',1,3,1422347946,1422605163,NULL),(7,'w','w','f1290186a5d0b1ceab27f4e77c0c5d68','w',NULL,'w@126.com',1,3,1422431700,1422592094,NULL);

/*Table structure for table `tb_weather` */

DROP TABLE IF EXISTS `tb_weather`;

CREATE TABLE `tb_weather` (
  `code` varchar(20) NOT NULL COMMENT '区域码',
  `name` varchar(50) DEFAULT NULL COMMENT '区域名称',
  `weather` varchar(1000) DEFAULT NULL COMMENT '天气信息',
  `date` varchar(20) DEFAULT NULL COMMENT '发布时间',
  PRIMARY KEY (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `tb_weather` */

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
