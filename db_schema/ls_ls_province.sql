CREATE DATABASE  IF NOT EXISTS `ls` /*!40100 DEFAULT CHARACTER SET gbk */;
USE `ls`;
-- MySQL dump 10.13  Distrib 5.6.13, for Win32 (x86)
--
-- Host: localhost    Database: ls
-- ------------------------------------------------------
-- Server version	5.6.17

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
-- Table structure for table `ls_province`
--

DROP TABLE IF EXISTS `ls_province`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ls_province` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=35 DEFAULT CHARSET=gbk;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ls_province`
--

LOCK TABLES `ls_province` WRITE;
/*!40000 ALTER TABLE `ls_province` DISABLE KEYS */;
INSERT INTO `ls_province` VALUES (1,'北京'),(2,'新疆'),(3,'重庆'),(4,'广东'),(5,'浙江'),(6,'天津'),(7,'澳门'),(8,'广西'),(9,'内蒙古'),(10,'宁夏'),(11,'江西'),(12,'台湾'),(13,'贵州'),(14,'安徽'),(15,'陕西'),(16,'辽宁'),(17,'山西'),(18,'青海'),(19,'香港'),(20,'四川'),(21,'江苏'),(22,'河北'),(23,'西藏'),(24,'福建'),(25,'吉林'),(26,'海南'),(27,'湖北'),(28,'上海'),(29,'云南'),(30,'甘肃'),(31,'湖南'),(32,'河南'),(33,'山东'),(34,'黑龙江');
/*!40000 ALTER TABLE `ls_province` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2014-12-24 20:05:55
