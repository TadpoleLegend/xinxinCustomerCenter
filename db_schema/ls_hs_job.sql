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
-- Table structure for table `hs_job`
--

DROP TABLE IF EXISTS `hs_job`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `hs_job` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `clientIp` varchar(255) CHARACTER SET gbk DEFAULT NULL,
  `lastGrabEnd` varchar(255) CHARACTER SET gbk DEFAULT NULL,
  `lastGrabStart` varchar(255) CHARACTER SET gbk DEFAULT NULL,
  `name` varchar(255) CHARACTER SET gbk DEFAULT NULL,
  `password` varchar(255) CHARACTER SET gbk DEFAULT NULL,
  `restartInHours` int(11) DEFAULT NULL,
  `start` varchar(255) CHARACTER SET gbk DEFAULT NULL,
  `status` varchar(255) CHARACTER SET gbk DEFAULT NULL,
  `stop` varchar(255) CHARACTER SET gbk DEFAULT NULL,
  `type` varchar(255) CHARACTER SET gbk DEFAULT NULL,
  `username` varchar(255) CHARACTER SET gbk DEFAULT NULL,
  `locked` tinyint(1) DEFAULT NULL,
  `mode` varchar(255) CHARACTER SET gbk DEFAULT NULL,
  `dbName` varchar(255) CHARACTER SET gbk DEFAULT NULL,
  `dbPassword` varchar(255) CHARACTER SET gbk DEFAULT NULL,
  `dbUsernname` varchar(255) CHARACTER SET gbk DEFAULT NULL,
  `autoJobRunning` tinyint(1) DEFAULT NULL,
  `clientEnd` varchar(255) CHARACTER SET gbk DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hs_job`
--

LOCK TABLES `hs_job` WRITE;
/*!40000 ALTER TABLE `hs_job` DISABLE KEYS */;
INSERT INTO `hs_job` VALUES (1,'http://hanthink.gnway.org:88','2014-11-10 22:42:25','2014-11-10 22:41:38','欧尚自动导入数据任务','shyf1356',1,'00:35','任务空闲中','23:59','authan','AC1356',0,'debug','测试帐套',NULL,NULL,1,'/hanthinkserver/service1.asmx'),(2,'https://auchan.chinab2bi.com/security/login.hlt','','','帐套A','shyf1356',1,'00:00','未启动','23:00','authan','AC1356',NULL,NULL,'测试帐套B',NULL,NULL,0,NULL),(4,'','','','运行中','33',NULL,'2','未启动','33','authan','3',NULL,NULL,'测试帐套C',NULL,NULL,0,NULL),(5,'','','','运行中','33',NULL,'2','未启动','33','authan','3',NULL,NULL,'测试帐套D',NULL,NULL,0,NULL);
/*!40000 ALTER TABLE `hs_job` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2014-11-16 23:39:06
