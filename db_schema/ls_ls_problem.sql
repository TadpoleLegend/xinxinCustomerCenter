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
-- Table structure for table `ls_problem`
--

DROP TABLE IF EXISTS `ls_problem`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ls_problem` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `category` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `problem_category_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK23D5FFE7F66ED508` (`problem_category_id`),
  CONSTRAINT `FK23D5FFE7F66ED508` FOREIGN KEY (`problem_category_id`) REFERENCES `ls_problem_category` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2043 DEFAULT CHARSET=gbk;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ls_problem`
--

LOCK TABLES `ls_problem` WRITE;
/*!40000 ALTER TABLE `ls_problem` DISABLE KEYS */;
INSERT INTO `ls_problem`(`id`,`category`,`name` ,`problem_category_id`) VALUES (1,'员工问题','美容师难招',NULL),(2,'员工问题','工作没激情',NULL),(3,'员工问题','员工留不住',NULL),(4,'员工问题','员工执行力差',NULL),(6,'员工问题','员工不团结',NULL),(7,'员工问题','员工害怕压力',NULL),(8,'员工问题','员工工作消极',NULL),(9,'员工问题','员工老想开店',NULL),(13,'员工问题','员工不想入股',NULL),(14,'其他问题','店不知道怎么装修',NULL),(15,'其他问题','店业绩难提升',NULL),(16,'顾客问题','顾客流失严重',NULL),(17,'顾客问题','新顾客难开发',NULL),(18,'顾客问题','顾客消费降低',NULL),(19,'顾客问题','顾客有钱不买',NULL),(20,'顾客问题','大顾客比较少',NULL),(2038,'其他问题','成本高利润低',NULL),(2039,'其他问题','促销不知道怎么做',NULL),(2040,'其他问题','工资急需改革',NULL),(2041,'其他问题','急需想开分店',NULL),(2042,'其他问题','店制度难以执行',NULL);
/*!40000 ALTER TABLE `ls_problem` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2014-11-25 22:05:44
