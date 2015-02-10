-- MySQL dump 10.13  Distrib 5.6.22, for Win32 (x86)
--
-- Host: localhost    Database: climate
-- ------------------------------------------------------
-- Server version	5.6.22-log

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
-- Table structure for table `timetable`
--

DROP TABLE IF EXISTS `timetable`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `timetable` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `CAMERA_ID` int(11) NOT NULL,
  `START_TIME` datetime NOT NULL,
  `END_TIME` datetime NOT NULL,
  `DEC_NUMBER` varchar(30) NOT NULL,
  `NAME` varchar(60) DEFAULT NULL,
  `SERIAL_NUMBER` varchar(30) DEFAULT NULL,
  `ORDER` varchar(30) DEFAULT NULL,
  `DESCRIPTION` text,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `timetable`
--

LOCK TABLES `timetable` WRITE;
/*!40000 ALTER TABLE `timetable` DISABLE KEYS */;
INSERT INTO `timetable` VALUES (1,3,'2014-01-29 12:00:00','2014-01-29 15:00:00','КЖИС.1234.567','Устройство коммутирующее','440321','012','тепло'),(2,1,'2014-01-29 12:00:00','2014-01-29 15:00:00','КЖИС.1234.567','Устройство коммутирующее','440321','012','мороз'),(3,1,'2014-01-29 15:00:00','2014-01-29 16:00:00','КЖИС.1234.567','Устройство коммутирующее','440123','012','поиск дефектов'),(4,1,'2014-01-29 16:00:00','2014-01-29 19:00:00','КЖИС.1234.567','Устройство коммутирующее','440123','012','мороз'),(5,2,'2014-01-29 08:00:00','2014-01-10 00:00:00','КЖИС.1234.567','Устройство коммутирующее','440123','012','циклы'),(6,2,'2014-01-30 12:00:00','2014-01-30 15:00:00','КЖИС.1234.567','Устройство коммутирующее','440123','012','тепло'),(7,1,'2014-01-29 10:00:00','2014-01-29 10:30:00','KJIS.123456','Block name','111222','012','Exp description'),(8,4,'2014-01-29 10:00:00','2014-01-29 10:30:00','KJIS.123456','Block name','111222','012','Exp description'),(9,5,'2014-01-29 10:00:00','2014-01-29 10:30:00','KJIS.123456','Block name','111222','012','Exp description'),(10,1,'2014-02-01 08:00:00','2014-02-01 09:00:00','asd','asdf','asdfasdf','asdf','asdf'),(11,2,'2014-02-01 08:00:00','2014-02-01 09:00:00','КЖИС.12341234','Блок 1','100200','012','фыаудол'),(12,2,'2014-02-02 08:00:00','2014-02-02 09:00:00','adsf','dsf','aasdf','sda','sdaff'),(13,2,'2014-02-03 08:00:00','2014-02-03 09:00:00','asdf','asdf','asdf','asf','asdf'),(14,2,'2014-02-04 08:00:00','2014-02-04 09:00:00','asdf','asdf','asdf','asdf','asdf'),(15,5,'2014-02-01 08:00:00','2014-02-01 09:00:00','sfad','fsda','asdf','asdf','sadf'),(17,2,'2014-03-01 08:00:00','2014-03-01 09:00:00','asdf','asdf','asdf','asdf','asdf'),(18,2,'2014-01-01 08:00:00','2014-01-01 09:00:00','asdf','asdf','asdf','asdf','asdf');
/*!40000 ALTER TABLE `timetable` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tools`
--

DROP TABLE IF EXISTS `tools`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tools` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `serial_number` varchar(30) DEFAULT NULL,
  `NAME` varchar(30) NOT NULL,
  `DESCRIPTION` text,
  `TOOL_TYPE` varchar(30) DEFAULT NULL,
  `PLACEMENT` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tools`
--

LOCK TABLES `tools` WRITE;
/*!40000 ALTER TABLE `tools` DISABLE KEYS */;
INSERT INTO `tools` VALUES (1,'001','Camera 1','big camera','camera','sbmc'),(2,'002','Camera 2','big camera','camera','sbmc'),(3,'003','Camera 3','big camera; no cycles','camera','sbmc'),(4,'004','Camera 4','small camera','camera','sbmc'),(5,'123','Camera 5','big camera','camera','sbmc');
/*!40000 ALTER TABLE `tools` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2015-02-10 20:37:38
