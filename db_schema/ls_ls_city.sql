CREATE DATABASE  IF NOT EXISTS `ls` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `ls`;
-- MySQL dump 10.13  Distrib 5.6.17, for Win32 (x86)
--
-- Host: localhost    Database: ls
-- ------------------------------------------------------
-- Server version	5.6.19-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `ls_city`
--

DROP TABLE IF EXISTS `ls_city`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ls_city` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(20) NOT NULL DEFAULT '',
  `province_id` int(11) DEFAULT NULL,
  `url` varchar(255) DEFAULT NULL,
  `city_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK1AC7A123341D8D36` (`city_id`),
  CONSTRAINT `FK1AC7A123341D8D36` FOREIGN KEY (`city_id`) REFERENCES `ls_user_city` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=150 DEFAULT CHARSET=gbk;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ls_city`
--

LOCK TABLES `ls_city` WRITE;
/*!40000 ALTER TABLE `ls_city` DISABLE KEYS */;
INSERT INTO `ls_city` VALUES (101,'苏州',1,'http://su.58.com/',NULL),(102,'南京',1,'http://nj.58.com/',NULL),(103,'无锡',1,'http://wx.58.com/',NULL),(104,'常州',1,'http://cz.58.com/',NULL),(105,'徐州',1,'http://xz.58.com/',NULL),(106,'南通',1,'http://nt.58.com/',NULL),(107,'扬州',1,'http://yz.58.com/',NULL),(108,'盐城',1,'http://yancheng.58.com/',NULL),(109,'淮安',1,'http://ha.58.com/',NULL),(110,'连云港',1,'http://lyg.58.com/',NULL),(111,'泰州',1,'http://taizhou.58.com/',NULL),(112,'宿迁',1,'http://suqian.58.com/',NULL),(113,'镇江',1,'http://zj.58.com/',NULL),(114,'沭阳',1,'http://shuyang.58.com/',NULL),(115,'大丰',1,'http://dafeng.58.com/',NULL),(116,'杭州',2,'http://hz.58.com/',NULL),(117,'宁波',2,'http://nb.58.com/',NULL),(118,'温州',2,'http://wz.58.com/',NULL),(119,'金华',2,'http://jh.58.com/',NULL),(120,'嘉兴',2,'http://jx.58.com/',NULL),(121,'台州',2,'http://tz.58.com/',NULL),(122,'绍兴',2,'http://sx.58.com/',NULL),(123,'湖州',2,'http://huzhou.58.com/',NULL),(124,'丽水',2,'http://lishui.58.com/',NULL),(125,'衢州',2,'http://quzhou.58.com/',NULL),(126,'舟山',2,'http://zhoushan.58.com/',NULL),(127,'乐清',2,'http://yueqingcity.58.com/',NULL),(128,'瑞安',2,'http://ruiancity.58.com/',NULL),(129,'义乌',2,'http://yiwu.58.com/',NULL),(130,'合肥',3,'http://hf.58.com/',NULL),(131,'芜湖',3,'http://wuhu.58.com/',NULL),(132,'蚌埠',3,'http://bengbu.58.com/',NULL),(133,'阜阳',3,'http://fy.58.com/',NULL),(134,'淮南',3,'http://hn.58.com/',NULL),(135,'安庆',3,'http://anqing.58.com/',NULL),(136,'宿州',3,'http://suzhou.58.com/',NULL),(137,'六安',3,'http://la.58.com/',NULL),(138,'淮北',3,'http://huaibei.58.com/',NULL),(139,'滁州',3,'http://chuzhou.58.com/',NULL),(140,'马鞍山',3,'http://mas.58.com/',NULL),(141,'铜陵',3,'http://tongling.58.com/',NULL),(142,'宣城',3,'http://xuancheng.58.com/',NULL),(143,'亳州',3,'http://bozhou.58.com/',NULL),(144,'黄山',3,'http://huangshan.58.com/',NULL),(145,'池州',3,'http://chizhou.58.com/',NULL),(146,'巢湖',3,'http://ch.58.com/',NULL),(147,'和县',3,'http://hexian.58.com/',NULL),(148,'霍邱',3,'http://hq.58.com/',NULL),(149,'桐城',3,'http://tongcheng.58.com/',NULL);
/*!40000 ALTER TABLE `ls_city` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2014-11-16 23:39:05
