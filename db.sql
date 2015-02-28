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
-- Table structure for table `experimenttype`
--

DROP TABLE IF EXISTS `experimenttype`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `experimenttype` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `NAME` varchar(70) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `experimenttype`
--

LOCK TABLES `experimenttype` WRITE;
/*!40000 ALTER TABLE `experimenttype` DISABLE KEYS */;
INSERT INTO `experimenttype` VALUES (1,'Циклы  (+65 ± 5°С; -65 ± 5 °C) x3 по 8 часов'),(2,'Мороз -65 ± 5 °C 4 часа'),(3,'Тепло +65 ± 5 °C 8 часа');
/*!40000 ALTER TABLE `experimenttype` ENABLE KEYS */;
UNLOCK TABLES;

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
) ENGINE=InnoDB AUTO_INCREMENT=49 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `timetable`
--

LOCK TABLES `timetable` WRITE;
/*!40000 ALTER TABLE `timetable` DISABLE KEYS */;
INSERT INTO `timetable` VALUES (1,3,'2014-01-29 12:00:00','2014-01-29 15:00:00','КЖИС.1234.567','Устройство коммутирующее','440321','012','тепло'),(2,1,'2014-01-29 12:00:00','2014-01-29 15:00:00','КЖИС.1234.567','Устройство коммутирующее','440321','012','мороз'),(3,1,'2014-01-29 15:00:00','2014-01-29 16:00:00','КЖИС.1234.567','Устройство коммутирующее','440123','012','поиск дефектов'),(4,1,'2014-01-29 16:00:00','2014-01-29 19:00:00','КЖИС.1234.567','Устройство коммутирующее','440123','012','мороз'),(5,2,'2014-01-29 08:00:00','2014-01-10 00:00:00','КЖИС.1234.567','Устройство коммутирующее','440123','012','циклы'),(6,2,'2014-01-30 12:00:00','2014-01-30 15:00:00','КЖИС.1234.567','Устройство коммутирующее','440123','012','тепло'),(7,1,'2014-01-29 10:00:00','2014-01-29 10:30:00','KJIS.123456','Block name','111222','012','Exp description'),(8,4,'2014-01-29 10:00:00','2014-01-29 10:30:00','KJIS.123456','Block name','111222','012','Exp description'),(10,1,'2014-02-01 08:00:00','2014-02-01 09:00:00','asd','asdf','asdfasdf','asdf','asdf'),(11,2,'2014-02-01 08:00:00','2014-02-01 09:00:00','КЖИС.12341234','Блок 1','100200','012','фыаудол'),(12,2,'2014-02-02 08:00:00','2014-02-02 09:00:00','adsf','dsf','aasdf','sda','sdaff'),(13,2,'2014-02-03 08:00:00','2014-02-03 09:00:00','asdf','asdf','asdf','asf','asdf'),(14,2,'2014-02-04 08:00:00','2014-02-04 09:00:00','asdf','asdf','asdf','asdf','asdf'),(18,2,'2014-01-01 08:00:00','2014-01-01 09:00:00','asdf','asdf','asdf','asdf','asdf'),(22,2,'2015-02-12 21:41:00','2015-02-12 21:46:00','123','123','123','123','123'),(28,3,'2015-02-13 14:40:00','2015-02-13 15:40:00','КЖИС.123321.012','Блок','123','123','блок'),(30,6,'2015-02-13 20:06:00','2015-02-13 23:06:00','12','123','123','123','123'),(38,2,'2015-02-17 14:18:00','2015-02-17 15:18:00','КЖИС.123456.789','Блок №1','123345','123','Блочишко'),(39,1,'2015-02-19 10:21:00','2016-12-31 23:59:00','ИФШС.123456','Стойка 60','440118','077','поиск дефекта'),(40,2,'2015-02-19 10:50:00','2015-02-19 16:10:00','ГС2.003.021','Р-4М2','110204','078-09','мороз'),(43,2,'2015-02-20 13:56:00','2015-02-20 14:56:00','123','123','123','123','Циклы  (+65 ± 5°С; -65 ± 5 °C) x3 по 8 часов'),(44,9,'2015-02-25 12:36:00','2015-02-25 21:36:00','123','123','123','123','Циклы  (+65 ± 5°С; -65 ± 5 °C) x3 по 8 часов'),(45,6,'2015-02-25 13:21:00','2015-02-25 15:21:00','234','23','324','23','Тепло +65 ± 5 °C 8 часа'),(48,6,'2015-02-25 15:23:00','2015-02-25 18:23:00','df','bgdf','ndfn','ghn','Мороз -65 ± 5 °C 4 часа');
/*!40000 ALTER TABLE `timetable` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `toolplacement`
--

DROP TABLE IF EXISTS `toolplacement`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `toolplacement` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `NAME` varchar(30) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `toolplacement`
--

LOCK TABLES `toolplacement` WRITE;
/*!40000 ALTER TABLE `toolplacement` DISABLE KEYS */;
INSERT INTO `toolplacement` VALUES (1,'СбМЦ'),(2,'Отдел испытаний');
/*!40000 ALTER TABLE `toolplacement` ENABLE KEYS */;
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
  `statement` varchar(10) DEFAULT NULL,
  `certification` datetime NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tools`
--

LOCK TABLES `tools` WRITE;
/*!40000 ALTER TABLE `tools` DISABLE KEYS */;
INSERT INTO `tools` VALUES (1,'001','Камера 1','asdf asdf sdfg dfgqwerq rth dfgh xcvb xcvbasdg sdgh sdfg sdfg','камера','СбМЦ','','2015-12-20 10:00:00'),(2,'002','Камера 2','big camera','камера','СбМЦ','','2015-12-20 10:00:00'),(3,'003','Камера 3','big camera; no cycles','камера','СбМЦ','','2015-12-20 10:00:00'),(4,'004','Камера 4','small camera','камера','СбМЦ','','2015-12-20 10:00:00'),(6,'0019','Камера 2','small camera','камера','Отдел испытаний','','2015-12-20 10:00:00'),(9,'011','Камера 1','big camera','камера','Отдел испытаний','broken','2015-12-20 10:00:00'),(13,'008','234','234','вибростенд','Отдел испытаний','','2015-12-20 10:00:00'),(15,'23','Камера','большая камера','камера','СбМЦ','','2015-02-27 00:00:01');
/*!40000 ALTER TABLE `tools` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tooltype`
--

DROP TABLE IF EXISTS `tooltype`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tooltype` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `NAME` varchar(30) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tooltype`
--

LOCK TABLES `tooltype` WRITE;
/*!40000 ALTER TABLE `tooltype` DISABLE KEYS */;
INSERT INTO `tooltype` VALUES (1,'камера'),(2,'вибростенд');
/*!40000 ALTER TABLE `tooltype` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2015-02-28 13:44:32
