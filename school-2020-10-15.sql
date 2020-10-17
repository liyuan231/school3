-- MySQL dump 10.13  Distrib 8.0.18, for Linux (x86_64)
--
-- Host: localhost    Database: school
-- ------------------------------------------------------
-- Server version	8.0.18

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `likes`
--

DROP TABLE IF EXISTS `likes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `likes` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `likeUserId` int(11) DEFAULT NULL COMMENT '代表“我”',
  `likedUserId` int(11) DEFAULT NULL COMMENT '喜欢 她',
  `update_time` datetime DEFAULT NULL,
  `add_time` datetime DEFAULT NULL,
  `deleted` tinyint(1) DEFAULT '0',
  `likeSchoolName` varchar(200) DEFAULT NULL,
  `likedSchoolName` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `likes_user__fk` (`likeUserId`),
  KEY `likes_user__fk_2` (`likedUserId`),
  CONSTRAINT `likes_user__fk` FOREIGN KEY (`likeUserId`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `likes_user__fk_2` FOREIGN KEY (`likedUserId`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=269 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `likes`
--

LOCK TABLES `likes` WRITE;
/*!40000 ALTER TABLE `likes` DISABLE KEYS */;
INSERT INTO `likes` VALUES (150,288,315,'2020-10-13 13:01:05','2020-10-13 13:01:05',0,'学校41','学校68'),(151,315,302,'2020-10-13 13:01:05','2020-10-13 13:01:05',0,'学校68','学校55'),(152,256,255,'2020-10-13 13:01:05','2020-10-13 13:01:05',0,'学校9','学校8'),(153,294,320,'2020-10-13 13:01:05','2020-10-13 13:01:05',0,'学校47','学校73'),(154,248,261,'2020-10-13 13:01:05','2020-10-13 13:01:05',0,'学校1','学校14'),(155,292,250,'2020-10-13 13:01:05','2020-10-13 13:01:05',0,'学校45','学校3'),(156,249,284,'2020-10-13 13:01:05','2020-10-13 13:01:05',0,'学校2','学校37'),(157,249,301,'2020-10-13 13:01:05','2020-10-13 13:01:05',0,'学校2','学校54'),(158,294,302,'2020-10-13 13:01:05','2020-10-13 13:01:05',0,'学校47','学校55'),(159,280,305,'2020-10-13 13:01:05','2020-10-13 13:01:05',0,'学校33','学校58'),(160,311,303,'2020-10-13 13:01:05','2020-10-13 13:01:05',0,'学校64','学校56'),(161,290,259,'2020-10-13 13:01:05','2020-10-13 13:01:05',0,'学校43','学校12'),(162,257,287,'2020-10-13 13:01:05','2020-10-13 13:01:05',0,'学校10','学校40'),(163,304,308,'2020-10-13 13:01:05','2020-10-13 13:01:05',0,'学校57','学校61'),(164,249,314,'2020-10-13 13:01:05','2020-10-13 13:01:05',0,'学校2','学校67'),(165,296,267,'2020-10-13 13:01:05','2020-10-13 13:01:05',0,'学校49','学校20'),(166,274,262,'2020-10-13 13:01:05','2020-10-13 13:01:05',0,'学校27','学校15'),(167,257,304,'2020-10-13 13:01:05','2020-10-13 13:01:05',0,'学校10','学校57'),(168,295,298,'2020-10-13 13:01:05','2020-10-13 13:01:05',0,'学校48','学校51'),(169,249,285,'2020-10-13 13:01:05','2020-10-13 13:01:05',0,'学校2','学校38'),(170,275,295,'2020-10-13 13:01:05','2020-10-13 13:01:05',0,'学校28','学校48'),(171,274,265,'2020-10-13 13:01:05','2020-10-13 13:01:05',0,'学校27','学校18'),(172,278,253,'2020-10-13 13:01:05','2020-10-13 13:01:05',0,'学校31','学校6'),(173,279,310,'2020-10-13 13:01:05','2020-10-13 13:01:05',0,'学校32','学校63'),(174,282,313,'2020-10-13 13:01:05','2020-10-13 13:01:05',0,'学校35','学校66'),(175,265,322,'2020-10-13 13:01:05','2020-10-13 13:01:05',0,'学校18','学校75'),(176,280,317,'2020-10-13 13:01:05','2020-10-13 13:01:05',0,'学校33','学校70'),(177,283,269,'2020-10-13 13:01:05','2020-10-13 13:01:05',0,'学校36','学校22'),(178,298,279,'2020-10-13 13:01:05','2020-10-13 13:01:05',0,'学校51','学校32'),(179,297,300,'2020-10-13 13:01:05','2020-10-13 13:01:05',0,'学校50','学校53'),(180,305,302,'2020-10-13 13:01:05','2020-10-13 13:01:05',0,'学校58','学校55'),(181,263,307,'2020-10-13 13:01:05','2020-10-13 13:01:05',0,'学校16','学校60'),(182,274,304,'2020-10-13 13:01:05','2020-10-13 13:01:05',0,'学校27','学校57'),(183,258,321,'2020-10-13 13:01:05','2020-10-13 13:01:05',0,'学校11','学校74'),(184,251,257,'2020-10-13 13:01:05','2020-10-13 13:01:05',0,'学校4','学校10'),(185,251,281,'2020-10-13 13:01:05','2020-10-13 13:01:05',0,'学校4','学校34'),(186,304,289,'2020-10-13 13:01:05','2020-10-13 13:01:05',0,'学校57','学校42'),(187,250,297,'2020-10-13 13:01:05','2020-10-13 13:01:05',0,'学校3','学校50'),(188,269,282,'2020-10-13 13:01:05','2020-10-13 13:01:05',0,'学校22','学校35'),(189,292,281,'2020-10-13 13:01:05','2020-10-13 13:01:05',0,'学校45','学校34'),(190,264,273,'2020-10-13 13:01:05','2020-10-13 13:01:05',0,'学校17','学校26'),(191,249,262,'2020-10-13 13:01:05','2020-10-13 13:01:05',0,'学校2','学校15'),(193,281,264,'2020-10-13 13:02:08','2020-10-13 13:02:08',0,'学校34','学校17'),(194,285,275,'2020-10-13 13:02:08','2020-10-13 13:02:08',0,'学校38','学校28'),(195,313,297,'2020-10-13 13:02:08','2020-10-13 13:02:08',0,'学校66','学校50'),(196,258,279,'2020-10-13 13:02:08','2020-10-13 13:02:08',0,'学校11','学校32'),(197,287,321,'2020-10-13 13:02:08','2020-10-13 13:02:08',0,'学校40','学校74'),(198,322,260,'2020-10-13 13:02:08','2020-10-13 13:02:08',0,'学校75','学校13'),(199,317,260,'2020-10-13 13:02:08','2020-10-13 13:02:08',0,'学校70','学校13'),(200,281,262,'2020-10-13 13:02:08','2020-10-13 13:02:08',0,'学校34','学校15'),(202,291,289,'2020-10-13 13:02:08','2020-10-13 13:02:08',0,'学校44','学校42'),(203,296,247,'2020-10-13 13:02:08','2020-10-13 13:02:08',0,'学校49','学校0'),(204,253,315,'2020-10-13 13:02:08','2020-10-13 13:02:08',0,'学校6','学校68'),(205,320,312,'2020-10-13 13:02:08','2020-10-13 13:02:08',0,'学校73','学校65'),(206,280,311,'2020-10-13 13:02:08','2020-10-13 13:02:08',0,'学校33','学校64'),(207,259,278,'2020-10-13 13:02:08','2020-10-13 13:02:08',0,'学校12','学校31'),(208,248,254,'2020-10-13 13:02:08','2020-10-13 13:02:08',0,'学校1','学校7'),(209,248,294,'2020-10-13 13:02:08','2020-10-13 13:02:08',0,'学校1','学校47'),(210,248,320,'2020-10-13 13:02:08','2020-10-13 13:02:08',0,'学校1','学校73'),(211,313,307,'2020-10-13 13:02:08','2020-10-13 13:02:08',0,'学校66','学校60'),(212,280,318,'2020-10-13 13:02:08','2020-10-13 13:02:08',0,'学校33','学校71'),(213,288,252,'2020-10-13 13:02:09','2020-10-13 13:02:09',0,'学校41','学校5'),(214,293,249,'2020-10-13 17:34:21','2020-10-13 13:02:09',1,'学校46','学校2'),(215,303,252,'2020-10-13 13:02:09','2020-10-13 13:02:09',0,'学校56','学校5'),(216,278,266,'2020-10-13 13:02:09','2020-10-13 13:02:09',0,'学校31','学校19'),(217,248,281,'2020-10-13 13:02:09','2020-10-13 13:02:09',0,'学校1','学校34'),(218,279,276,'2020-10-13 13:02:09','2020-10-13 13:02:09',0,'学校32','学校29'),(219,270,262,'2020-10-13 13:02:09','2020-10-13 13:02:09',0,'学校23','学校15'),(220,299,298,'2020-10-13 13:02:09','2020-10-13 13:02:09',0,'学校52','学校51'),(221,301,290,'2020-10-13 13:02:09','2020-10-13 13:02:09',0,'学校54','学校43'),(222,294,285,'2020-10-13 13:02:09','2020-10-13 13:02:09',0,'学校47','学校38'),(223,298,300,'2020-10-13 13:02:09','2020-10-13 13:02:09',0,'学校51','学校53'),(224,292,308,'2020-10-13 13:02:09','2020-10-13 13:02:09',0,'学校45','学校61'),(225,258,261,'2020-10-13 13:02:09','2020-10-13 13:02:09',0,'学校11','学校14'),(226,304,296,'2020-10-13 13:02:09','2020-10-13 13:02:09',0,'学校57','学校49'),(228,281,304,'2020-10-13 13:02:09','2020-10-13 13:02:09',0,'学校34','学校57'),(229,250,276,'2020-10-13 13:02:09','2020-10-13 13:02:09',0,'学校3','学校29'),(230,289,274,'2020-10-13 13:02:09','2020-10-13 13:02:09',0,'学校42','学校27'),(232,299,313,'2020-10-13 13:02:09','2020-10-13 13:02:09',0,'学校52','学校66'),(233,264,303,'2020-10-13 13:02:09','2020-10-13 13:02:09',0,'学校17','学校56'),(235,288,261,'2020-10-13 13:02:09','2020-10-13 13:02:09',0,'学校41','学校14'),(236,286,247,'2020-10-13 13:02:09','2020-10-13 13:02:09',0,'学校39','学校0'),(237,273,274,'2020-10-13 13:02:09','2020-10-13 13:02:09',0,'学校26','学校27'),(238,300,247,'2020-10-13 13:02:09','2020-10-13 13:02:09',0,'学校53','学校0'),(239,280,271,'2020-10-13 13:02:09','2020-10-13 13:02:09',0,'学校33','学校24'),(240,321,309,'2020-10-13 13:02:09','2020-10-13 13:02:09',0,'学校74','学校62'),(241,269,267,'2020-10-13 13:02:09','2020-10-13 13:02:09',0,'学校22','学校20'),(242,279,291,'2020-10-13 13:02:09','2020-10-13 13:02:09',0,'学校32','学校44'),(243,321,267,'2020-10-13 13:02:09','2020-10-13 13:02:09',0,'学校74','学校20'),(244,316,261,'2020-10-13 13:02:09','2020-10-13 13:02:09',0,'学校69','学校14'),(245,310,275,'2020-10-13 13:02:09','2020-10-13 13:02:09',0,'学校63','学校28'),(247,259,318,'2020-10-13 13:02:09','2020-10-13 13:02:09',0,'学校12','学校71'),(248,263,287,'2020-10-13 13:02:09','2020-10-13 13:02:09',0,'学校16','学校40'),(249,247,260,'2020-10-13 13:02:09','2020-10-13 13:02:09',0,'学校0','学校13'),(250,253,286,'2020-10-13 13:02:09','2020-10-13 13:02:09',0,'学校6','学校39'),(251,310,320,'2020-10-13 13:02:09','2020-10-13 13:02:09',0,'学校63','学校73'),(252,253,318,'2020-10-13 13:02:09','2020-10-13 13:02:09',0,'学校6','学校71'),(253,303,295,'2020-10-13 13:02:09','2020-10-13 13:02:09',0,'学校56','学校48'),(254,320,281,'2020-10-13 13:02:09','2020-10-13 13:02:09',0,'学校73','学校34'),(255,291,266,'2020-10-13 13:02:09','2020-10-13 13:02:09',0,'学校44','学校19'),(256,277,248,'2020-10-13 13:02:09','2020-10-13 13:02:09',0,'学校30','学校1'),(257,296,290,'2020-10-13 13:02:09','2020-10-13 13:02:09',0,'学校49','学校43'),(258,264,306,'2020-10-13 13:02:09','2020-10-13 13:02:09',0,'学校17','学校59'),(259,271,285,'2020-10-13 13:02:09','2020-10-13 13:02:09',0,'学校24','学校38'),(260,288,264,'2020-10-13 13:02:09','2020-10-13 13:02:09',0,'学校41','学校17'),(261,322,255,'2020-10-13 13:02:09','2020-10-13 13:02:09',0,'学校75','学校8'),(262,291,261,'2020-10-13 13:02:09','2020-10-13 13:02:09',0,'学校44','学校14'),(263,254,251,'2020-10-13 13:02:09','2020-10-13 13:02:09',0,'学校7','学校4'),(264,296,268,'2020-10-13 13:02:09','2020-10-13 13:02:09',0,'学校49','学校21'),(265,309,311,'2020-10-13 13:02:09','2020-10-13 13:02:09',0,'学校62','学校64'),(266,313,315,'2020-10-13 13:02:09','2020-10-13 13:02:09',0,'学校66','学校68'),(267,283,298,'2020-10-13 13:02:09','2020-10-13 13:02:09',0,'学校36','学校51'),(268,283,318,'2020-10-13 13:02:09','2020-10-13 13:02:09',0,'学校36','学校71');
/*!40000 ALTER TABLE `likes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pics`
--

DROP TABLE IF EXISTS `pics`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `pics` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userId` int(11) DEFAULT NULL,
  `location` varchar(200) DEFAULT NULL,
  `type` int(11) DEFAULT NULL,
  `add_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `deleted` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `pics_user__fk` (`userId`),
  CONSTRAINT `pics_user__fk` FOREIGN KEY (`userId`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pics`
--

LOCK TABLES `pics` WRITE;
/*!40000 ALTER TABLE `pics` DISABLE KEYS */;
INSERT INTO `pics` VALUES (19,321,'5a4b6460-de4b-49ea-89a8-d87874de46ca.png',2,'2020-10-14 19:30:34','2020-10-14 21:48:40',0);
/*!40000 ALTER TABLE `pics` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role`
--

DROP TABLE IF EXISTS `role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(200) DEFAULT NULL,
  `desc` text,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role`
--

LOCK TABLES `role` WRITE;
/*!40000 ALTER TABLE `role` DISABLE KEYS */;
INSERT INTO `role` VALUES (1,'ADMINISTRATOR',NULL),(2,'USER',NULL),(3,'管理员',NULL),(4,'管理员plus',NULL);
/*!40000 ALTER TABLE `role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `roletoauthorities`
--

DROP TABLE IF EXISTS `roletoauthorities`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `roletoauthorities` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `roleId` int(11) DEFAULT NULL,
  `authority` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `roletoauthorities_role__fk` (`roleId`),
  CONSTRAINT `roletoauthorities_role__fk` FOREIGN KEY (`roleId`) REFERENCES `role` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `roletoauthorities`
--

LOCK TABLES `roletoauthorities` WRITE;
/*!40000 ALTER TABLE `roletoauthorities` DISABLE KEYS */;
INSERT INTO `roletoauthorities` VALUES (1,1,'/1'),(2,2,'/2'),(3,3,'/3'),(4,4,'/4'),(16,2,'/like');
/*!40000 ALTER TABLE `roletoauthorities` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sign`
--

DROP TABLE IF EXISTS `sign`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sign` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `signUserId` int(11) DEFAULT NULL COMMENT '签约的一方',
  `signedUserId` int(11) DEFAULT NULL COMMENT '被签约的一方\n',
  `update_time` datetime DEFAULT NULL,
  `add_time` datetime DEFAULT NULL,
  `deleted` tinyint(1) DEFAULT '0',
  `status` int(11) DEFAULT '1' COMMENT '该则签约的状态，公示状态或隐藏状态',
  `signSchoolName` varchar(200) DEFAULT NULL,
  `signedSchoolName` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `sign_user__fk` (`signUserId`),
  KEY `sign_user__fk_2` (`signedUserId`),
  CONSTRAINT `sign_user__fk` FOREIGN KEY (`signUserId`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `sign_user__fk_2` FOREIGN KEY (`signedUserId`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=389 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sign`
--

LOCK TABLES `sign` WRITE;
/*!40000 ALTER TABLE `sign` DISABLE KEYS */;
INSERT INTO `sign` VALUES (292,247,286,'2020-10-13 12:35:43','2020-10-13 12:35:43',0,1,'学校0','学校39'),(293,300,312,'2020-10-13 12:35:43','2020-10-13 12:35:43',0,1,'学校53','学校65'),(294,263,318,'2020-10-13 12:35:43','2020-10-13 12:35:43',0,1,'学校16','学校71'),(295,267,301,'2020-10-13 12:35:43','2020-10-13 12:35:43',0,1,'学校20','学校54'),(296,280,273,'2020-10-13 12:35:43','2020-10-13 12:35:43',0,1,'学校33','学校26'),(298,249,303,'2020-10-13 12:35:43','2020-10-13 12:35:43',0,1,'学校2','学校56'),(299,263,308,'2020-10-13 12:35:43','2020-10-13 12:35:43',0,1,'学校16','学校61'),(300,281,249,'2020-10-13 12:35:43','2020-10-13 12:35:43',0,1,'学校34','学校2'),(301,286,282,'2020-10-13 12:35:43','2020-10-13 12:35:43',0,1,'学校39','学校35'),(302,301,292,'2020-10-13 12:35:43','2020-10-13 12:35:43',0,1,'学校54','学校45'),(303,262,247,'2020-10-13 12:35:43','2020-10-13 12:35:43',0,1,'学校15','学校0'),(304,297,247,'2020-10-13 12:35:43','2020-10-13 12:35:43',0,1,'学校50','学校0'),(305,253,317,'2020-10-13 12:35:44','2020-10-13 12:35:44',0,1,'学校6','学校70'),(307,302,255,'2020-10-13 12:35:44','2020-10-13 12:35:44',0,1,'学校55','学校8'),(308,319,308,'2020-10-13 12:35:44','2020-10-13 12:35:44',0,1,'学校72','学校61'),(309,284,310,'2020-10-13 12:35:44','2020-10-13 12:35:44',0,1,'学校37','学校63'),(310,280,317,'2020-10-13 12:35:44','2020-10-13 12:35:44',0,1,'学校33','学校70'),(311,283,306,'2020-10-13 12:35:44','2020-10-13 12:35:44',0,1,'学校36','学校59'),(312,299,252,'2020-10-13 12:35:44','2020-10-13 12:35:44',0,1,'学校52','学校5'),(314,299,259,'2020-10-13 12:35:44','2020-10-13 12:35:44',0,1,'学校52','学校12'),(315,313,254,'2020-10-13 12:35:44','2020-10-13 12:35:44',0,1,'学校66','学校7'),(316,286,275,'2020-10-13 12:35:44','2020-10-13 12:35:44',0,1,'学校39','学校28'),(317,319,306,'2020-10-13 12:35:44','2020-10-13 12:35:44',0,1,'学校72','学校59'),(318,276,309,'2020-10-13 12:35:44','2020-10-13 12:35:44',0,1,'学校29','学校62'),(319,285,250,'2020-10-13 12:35:44','2020-10-13 12:35:44',0,1,'学校38','学校3'),(320,259,264,'2020-10-13 12:35:44','2020-10-13 12:35:44',0,1,'学校12','学校17'),(321,297,262,'2020-10-13 12:35:44','2020-10-13 12:35:44',0,1,'学校50','学校15'),(322,295,254,'2020-10-13 12:35:44','2020-10-13 12:35:44',0,1,'学校48','学校7'),(323,322,257,'2020-10-13 12:35:44','2020-10-13 12:35:44',0,1,'学校75','学校10'),(324,275,268,'2020-10-13 12:35:44','2020-10-13 12:35:44',0,1,'学校28','学校21'),(325,274,254,'2020-10-13 12:35:44','2020-10-13 12:35:44',0,1,'学校27','学校7'),(326,309,310,'2020-10-13 12:35:44','2020-10-13 12:35:44',0,1,'学校62','学校63'),(327,261,290,'2020-10-13 12:35:44','2020-10-13 12:35:44',0,1,'学校14','学校43'),(328,291,250,'2020-10-13 12:35:44','2020-10-13 12:35:44',0,1,'学校44','学校3'),(329,275,258,'2020-10-13 12:35:44','2020-10-13 12:35:44',0,1,'学校28','学校11'),(330,294,257,'2020-10-13 12:35:44','2020-10-13 12:35:44',0,1,'学校47','学校10'),(331,298,256,'2020-10-13 12:35:44','2020-10-13 12:35:44',0,1,'学校51','学校9'),(332,301,299,'2020-10-13 12:35:44','2020-10-13 12:35:44',0,1,'学校54','学校52'),(335,284,267,'2020-10-13 12:35:44','2020-10-13 12:35:44',0,1,'学校37','学校20'),(336,256,279,'2020-10-13 12:35:44','2020-10-13 12:35:44',0,1,'学校9','学校32'),(337,302,294,'2020-10-13 12:35:44','2020-10-13 12:35:44',0,1,'学校55','学校47'),(338,307,255,'2020-10-13 12:35:44','2020-10-13 12:35:44',0,1,'学校60','学校8'),(339,285,301,'2020-10-13 12:35:44','2020-10-13 12:35:44',0,1,'学校38','学校54'),(340,284,259,'2020-10-13 12:35:44','2020-10-13 12:35:44',0,1,'学校37','学校12'),(341,309,314,'2020-10-13 12:35:44','2020-10-13 12:35:44',0,1,'学校62','学校67'),(342,287,269,'2020-10-13 12:35:44','2020-10-13 12:35:44',0,1,'学校40','学校22'),(343,251,256,'2020-10-13 12:35:44','2020-10-13 12:35:44',0,1,'学校4','学校9'),(344,295,293,'2020-10-13 12:35:44','2020-10-13 12:35:44',0,1,'学校48','学校46'),(345,285,250,'2020-10-13 12:35:44','2020-10-13 12:35:44',0,1,'学校38','学校3'),(346,290,262,'2020-10-13 12:35:44','2020-10-13 12:35:44',0,1,'学校43','学校15'),(347,273,294,'2020-10-13 12:35:44','2020-10-13 12:35:44',0,1,'学校26','学校47'),(349,292,269,'2020-10-13 12:35:44','2020-10-13 12:35:44',0,1,'学校45','学校22'),(350,272,297,'2020-10-13 12:35:44','2020-10-13 12:35:44',0,1,'学校25','学校50'),(351,264,309,'2020-10-13 12:35:44','2020-10-13 12:35:44',0,1,'学校17','学校62'),(352,315,281,'2020-10-13 12:35:44','2020-10-13 12:35:44',0,1,'学校68','学校34'),(353,248,313,'2020-10-13 12:35:44','2020-10-13 12:35:44',0,1,'学校1','学校66'),(354,276,297,'2020-10-13 12:35:44','2020-10-13 12:35:44',0,1,'学校29','学校50'),(355,248,280,'2020-10-13 12:35:44','2020-10-13 12:35:44',0,1,'学校1','学校33'),(356,281,312,'2020-10-13 12:35:44','2020-10-13 12:35:44',0,1,'学校34','学校65'),(357,255,291,'2020-10-13 12:35:44','2020-10-13 12:35:44',0,1,'学校8','学校44'),(358,313,292,'2020-10-13 12:35:44','2020-10-13 12:35:44',0,1,'学校66','学校45'),(359,286,277,'2020-10-13 12:35:44','2020-10-13 12:35:44',0,1,'学校39','学校30'),(360,253,298,'2020-10-13 12:35:44','2020-10-13 12:35:44',0,1,'学校6','学校51'),(361,300,301,'2020-10-13 12:35:44','2020-10-13 12:35:44',0,1,'学校53','学校54'),(363,297,300,'2020-10-13 12:35:44','2020-10-13 12:35:44',0,1,'学校50','学校53'),(364,318,320,'2020-10-13 12:35:44','2020-10-13 12:35:44',0,1,'学校71','学校73'),(365,249,286,'2020-10-13 12:35:44','2020-10-13 12:35:44',0,1,'学校2','学校39'),(366,311,277,'2020-10-13 12:35:44','2020-10-13 12:35:44',0,1,'学校64','学校30'),(367,267,304,'2020-10-13 12:35:44','2020-10-13 12:35:44',0,1,'学校20','学校57'),(368,276,316,'2020-10-13 12:35:44','2020-10-13 12:35:44',0,1,'学校29','学校69'),(369,298,286,'2020-10-13 12:35:44','2020-10-13 12:35:44',0,1,'学校51','学校39'),(370,261,313,'2020-10-13 12:35:44','2020-10-13 12:35:44',0,1,'学校14','学校66'),(371,290,315,'2020-10-13 12:35:44','2020-10-13 12:35:44',0,1,'学校43','学校68'),(372,319,276,'2020-10-13 12:35:44','2020-10-13 12:35:44',0,1,'学校72','学校29'),(373,268,253,'2020-10-13 12:35:44','2020-10-13 12:35:44',0,1,'学校21','学校6'),(374,253,281,'2020-10-13 12:35:44','2020-10-13 12:35:44',0,1,'学校6','学校34'),(375,249,262,'2020-10-13 12:35:44','2020-10-13 12:35:44',0,1,'学校2','学校15'),(376,298,307,'2020-10-13 12:35:44','2020-10-13 12:35:44',0,1,'学校51','学校60'),(377,272,307,'2020-10-13 12:35:44','2020-10-13 12:35:44',0,1,'学校25','学校60'),(378,260,302,'2020-10-13 12:35:44','2020-10-13 12:35:44',0,1,'学校13','学校55'),(379,276,320,'2020-10-13 12:35:44','2020-10-13 12:35:44',0,1,'学校29','学校73'),(380,266,267,'2020-10-13 12:35:44','2020-10-13 12:35:44',0,1,'学校19','学校20'),(381,319,301,'2020-10-13 12:35:44','2020-10-13 12:35:44',0,1,'学校72','学校54'),(382,293,299,'2020-10-13 12:35:44','2020-10-13 12:35:44',0,1,'学校46','学校52'),(383,273,251,'2020-10-13 12:35:44','2020-10-13 12:35:44',0,1,'学校26','学校4'),(384,287,267,'2020-10-13 12:35:44','2020-10-13 12:35:44',0,1,'学校40','学校20'),(385,281,306,'2020-10-13 12:35:44','2020-10-13 12:35:44',0,1,'学校34','学校59'),(386,278,262,'2020-10-13 12:35:44','2020-10-13 12:35:44',0,1,'学校31','学校15'),(387,295,309,'2020-10-13 12:35:44','2020-10-13 12:35:44',0,1,'学校48','学校62'),(388,278,318,'2020-10-13 12:35:44','2020-10-13 12:35:44',0,1,'学校31','学校71');
/*!40000 ALTER TABLE `sign` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(200) DEFAULT NULL,
  `password` varchar(200) DEFAULT NULL,
  `schoolname` varchar(200) DEFAULT NULL,
  `contact` varchar(200) DEFAULT NULL COMMENT '联系人',
  `address` varchar(200) DEFAULT NULL COMMENT '学校详细地址\n',
  `telephone` varchar(200) DEFAULT NULL COMMENT '学校官方电话\n',
  `update_time` datetime DEFAULT NULL,
  `schoolCode` varchar(100) DEFAULT NULL COMMENT '学校代码\n',
  `location` varchar(200) DEFAULT NULL COMMENT '用户上次登录的地址',
  `add_time` datetime DEFAULT NULL,
  `lastLoginIp` varchar(200) DEFAULT NULL COMMENT '上次登录ip',
  `lastLoginTime` datetime DEFAULT NULL COMMENT '上次登录时间',
  `deleted` tinyint(1) DEFAULT '0',
  `avatarUrl` varchar(200) DEFAULT NULL,
  `accountStatus` int(11) DEFAULT NULL COMMENT '账户状态',
  `profession` varchar(60) DEFAULT NULL COMMENT '职务',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=331 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (247,'1987151116@qq.com','$2a$10$XExld8oVTpoiKnVrgSdrVu6zZhXRan0mxhIim.eMHxXkS0hVgyPwW','学校0','联系人0','学校详细地址0','学校电话0','2020-10-15 10:48:45',NULL,'广州市','2020-10-11 04:05:05','58.62.42.80','2020-10-15 10:48:45',0,'default.png',NULL,NULL),(248,'1@qq.com','$2a$10$XExld8oVTpoiKnVrgSdrVu6zZhXRan0mxhIim.eMHxXkS0hVgyPwW','学校1','联系人1','学校详细地址1','学校电话1','2020-10-15 10:36:57',NULL,'广州市','2020-10-11 04:05:05','58.62.42.80','2020-10-15 10:36:57',0,'default.png',NULL,NULL),(249,'2@qq.com','$2a$10$EpFbudzd807rxQTCApHp7eftGxE61Q3QCVVIPS63MEjrh5JPQJWtO','学校2','联系人2','学校详细地址2','学校电话2','2020-10-11 04:05:05',NULL,NULL,'2020-10-11 04:05:05',NULL,NULL,0,'default.png',NULL,NULL),(250,'3@qq.com','$2a$10$DMODRJdQidWVbxWI0211DOSYQ.WpT6YBWAgqGCD7i1LHBrfd3NAa.','学校3','联系人3','学校详细地址3','学校电话3','2020-10-11 04:05:05',NULL,NULL,'2020-10-11 04:05:05',NULL,NULL,0,'default.png',NULL,NULL),(251,'4@qq.com','$2a$10$yzFpAM1h0jSGw52Xh9NZIu2bUzojkyM0v8qTH9nHgUQjir9RYBMM6','学校4','联系人4','学校详细地址4','学校电话4','2020-10-11 04:05:06',NULL,NULL,'2020-10-11 04:05:06',NULL,'2020-10-13 18:45:30',0,'default.png',NULL,NULL),(252,'5@qq.com','$2a$10$c75CIx8WWAJyOpD2mdtkVOk1H1IIwSgxCeeSyUeSrnCuYFFHeBQmy','学校5','联系人5','学校详细地址5','学校电话5','2020-10-11 04:05:06',NULL,NULL,'2020-10-11 04:05:06',NULL,NULL,0,'default.png',NULL,NULL),(253,'6@qq.com','$2a$10$dM4OUn6LRfuD5WOrY0/7N.GWtHpIVGoECQeVPtfAhdje09g4BNJUW','学校6','联系人6','学校详细地址6','学校电话6','2020-10-11 04:05:06',NULL,NULL,'2020-10-11 04:05:06',NULL,'2020-10-13 21:10:20',0,'default.png',NULL,NULL),(254,'7@qq.com','$2a$10$3DSGzGo1XaCAN.BrZ5dNruVQy0wI.1leWoH1iKWSqOl3Uc.02Z7fa','学校7','联系人7','学校详细地址7','学校电话7','2020-10-11 04:05:06',NULL,NULL,'2020-10-11 04:05:06',NULL,NULL,0,'default.png',NULL,NULL),(255,'8@qq.com','$2a$10$BNvWNZSA2RZw/zn6wSnEvOUJR3wUSfqboe1ZVXLOFLpS4jFDmNpu6','学校8','联系人8','学校详细地址8','学校电话8','2020-10-11 04:05:06',NULL,NULL,'2020-10-11 04:05:06',NULL,NULL,0,'default.png',NULL,NULL),(256,'9@qq.com','$2a$10$FSDNzG2d0/mJwqkgqT3s.Ol9/d8sljm6LUjJrzinjj6Q80jDoR/cG','学校9','联系人9','学校详细地址9','学校电话9','2020-10-11 04:05:06',NULL,NULL,'2020-10-11 04:05:06',NULL,NULL,0,'default.png',NULL,NULL),(257,'10@qq.com','$2a$10$yyCprE79Le2vNTifw/d5a.yjY1T/2kvSwHH2Wm1Dadq5xjoKD2Xhe','学校10','联系人10','学校详细地址10','学校电话10','2020-10-11 04:05:06',NULL,NULL,'2020-10-11 04:05:06',NULL,'2020-10-13 18:45:32',0,'default.png',NULL,NULL),(258,'11@qq.com','$2a$10$XJC3qXrEKa36F25rEKjAwuQrFx0C3.tK/ZtyqiPWJ6q9l7n2ZnO..','学校11','联系人11','学校详细地址11','学校电话11','2020-10-11 04:05:06',NULL,NULL,'2020-10-11 04:05:06',NULL,NULL,0,'default.png',NULL,NULL),(259,'12@qq.com','$2a$10$kHdBXu/1MBjqo3wW72ucl.VEvFjJoFuS/zay5EKbNEHlnA7Z4qKSq','学校12','联系人12','学校详细地址12','学校电话12','2020-10-11 04:05:06',NULL,NULL,'2020-10-11 04:05:06',NULL,NULL,0,'default.png',NULL,NULL),(260,'13@qq.com','$2a$10$DjZqq1PQ5q/xwEVny67kteoIysYTJZqtisV29xI8bKQ5Xz/UO.mSu','学校13','联系人13','学校详细地址13','学校电话13','2020-10-11 04:05:06',NULL,NULL,'2020-10-11 04:05:06',NULL,NULL,0,'default.png',NULL,NULL),(261,'14@qq.com','$2a$10$E70ROeEMsKknC5BULzFU2OCrEc.6bDv4v8wfAwm9NJebRZxbftWiW','学校14','联系人14','学校详细地址14','学校电话14','2020-10-11 04:05:07',NULL,NULL,'2020-10-11 04:05:07',NULL,NULL,0,'default.png',NULL,NULL),(262,'15@qq.com','$2a$10$xxdJraguXVuEHvRXDczRpexsIWoZMKHxoVKL1ymKiYqeL6./8FU/i','学校15','联系人15','学校详细地址15','学校电话15','2020-10-11 04:05:07',NULL,NULL,'2020-10-11 04:05:07',NULL,NULL,0,'default.png',NULL,NULL),(263,'16@qq.com','$2a$10$8bw0nWtrz1ja8.gpVmyHzuw2xnGTTHts4s1U27avz8QHQkl0VTfk.','学校16','联系人16','学校详细地址16','学校电话16','2020-10-11 04:05:07',NULL,NULL,'2020-10-11 04:05:07',NULL,NULL,0,'default.png',NULL,NULL),(264,'17@qq.com','$2a$10$WWB3izAAgvn8pcftdtmK8.PmpxOsthObWDf3E5pIq5di.hmN.zhxy','学校17','联系人17','学校详细地址17','学校电话17','2020-10-11 04:05:07',NULL,NULL,'2020-10-11 04:05:07',NULL,NULL,0,'default.png',NULL,NULL),(265,'18@qq.com','$2a$10$h6aDNnuCNfu69VjAFeScv.MwOTzoedyXz/fmWW.y/jPRZerfRGXm.','学校18','联系人18','学校详细地址18','学校电话18','2020-10-11 04:05:07',NULL,NULL,'2020-10-11 04:05:07',NULL,NULL,0,'default.png',NULL,NULL),(266,'19@qq.com','$2a$10$tglwtW1I96t3Aeij3Lh.YejaJQbU5D2MQcQeNyknLbzFdOt4ccPju','学校19','联系人19','学校详细地址19','学校电话19','2020-10-11 04:05:07',NULL,NULL,'2020-10-11 04:05:07',NULL,NULL,0,'default.png',NULL,NULL),(267,'20@qq.com','$2a$10$WudmENGlO6WDkvEsMEnTDuLMhBwAau/eCf4nGZ5tTZvyy2cOoFJX6','学校20','联系人20','学校详细地址20','学校电话20','2020-10-11 04:05:07',NULL,NULL,'2020-10-11 04:05:07',NULL,NULL,0,'default.png',NULL,NULL),(268,'21@qq.com','$2a$10$ROQyuv/NW6bX.Lur05I87uXGLsB2GVXMqdmu8oxjWPPho5nly.n7W','学校21','联系人21','学校详细地址21','学校电话21','2020-10-11 04:05:07',NULL,NULL,'2020-10-11 04:05:07',NULL,NULL,0,'default.png',NULL,NULL),(269,'22@qq.com','$2a$10$cqTfs6vXdHprUddDtVriXOp0ShhLoErStKSX3y7B0fIKJ7AvAGWV.','学校22','联系人22','学校详细地址22','学校电话22','2020-10-11 04:05:07',NULL,NULL,'2020-10-11 04:05:07',NULL,NULL,0,'default.png',NULL,NULL),(270,'23@qq.com','$2a$10$82h554rktPMZgVVLELJ0VOHhCAMdPD7/FVz4bzTWguZ0prZu2XPr6','学校23','联系人23','学校详细地址23','学校电话23','2020-10-11 04:05:07',NULL,NULL,'2020-10-11 04:05:07',NULL,NULL,0,'default.png',NULL,NULL),(271,'24@qq.com','$2a$10$VtuoAYxGCPl/H2RSeYCzSe1Om51VWLMLVnfsGj9Ngho2tmk.BiBbG','学校24','联系人24','学校详细地址24','学校电话24','2020-10-11 04:05:07',NULL,NULL,'2020-10-11 04:05:07',NULL,NULL,0,'default.png',NULL,NULL),(272,'25@qq.com','$2a$10$Z/fXRwdRNzdSzuIXzaRG5O.uqS0IRqVbHJr0iIDPO0p6/jjEqXhJm','学校25','联系人25','学校详细地址25','学校电话25','2020-10-11 04:05:07',NULL,NULL,'2020-10-11 04:05:07',NULL,NULL,0,'default.png',NULL,NULL),(273,'26@qq.com','$2a$10$o7SPkrReAFOE8qy9OZZBROLZqaIQg5ElkuiwbgwcyRKOhdpYtwNUa','学校26','联系人26','学校详细地址26','学校电话26','2020-10-11 04:05:08',NULL,NULL,'2020-10-11 04:05:08',NULL,NULL,0,'default.png',NULL,NULL),(274,'27@qq.com','$2a$10$Gw/ADacwCUw4z0caladvruDNzVPbRICu0boDzEwRBqyywEee3p2q6','学校27','联系人27','学校详细地址27','学校电话27','2020-10-11 04:05:08',NULL,NULL,'2020-10-11 04:05:08',NULL,NULL,0,'default.png',NULL,NULL),(275,'28@qq.com','$2a$10$ltJigPMmYQephpmpVAc1gedtq5AcGV5Sg3lbpldzS.Z7zSd7Va2Jm','学校28','联系人28','学校详细地址28','学校电话28','2020-10-11 04:05:08',NULL,NULL,'2020-10-11 04:05:08',NULL,NULL,0,'default.png',NULL,NULL),(276,'29@qq.com','$2a$10$J1ynKdsMXgcnzsULNBkP..QiVz1tBUH.9KbghRnfC/i9POw9zijuy','学校29','联系人29','学校详细地址29','学校电话29','2020-10-11 04:05:08',NULL,NULL,'2020-10-11 04:05:08',NULL,NULL,0,'default.png',NULL,NULL),(277,'30@qq.com','$2a$10$2mAVezqduGsV1fD/jmcQJeSfODMjJHEiz6rwF4TmqXec4/RiaSmPC','学校30','联系人30','学校详细地址30','学校电话30','2020-10-11 04:05:08',NULL,NULL,'2020-10-11 04:05:08',NULL,NULL,0,'default.png',NULL,NULL),(278,'31@qq.com','$2a$10$0sQhSeiHujsFLJPDe6QFfuI44BCTymMYvCTFZBR6F9Z7XmWm6W5FK','学校31','联系人31','学校详细地址31','学校电话31','2020-10-11 04:05:08',NULL,NULL,'2020-10-11 04:05:08',NULL,NULL,0,'default.png',NULL,NULL),(279,'32@qq.com','$2a$10$00lxZkoS1K/C9VTuoDiWfuf5iBV9187EY1ugUo4PjdLxLZXglRxq6','学校32','联系人32','学校详细地址32','学校电话32','2020-10-11 04:05:08',NULL,NULL,'2020-10-11 04:05:08',NULL,NULL,0,'default.png',NULL,NULL),(280,'33@qq.com','$2a$10$.hL7syUoUMDUUUg4KiuTf.XgzHkL76s/SFresuvlG0O/mrLFI6xye','学校33','联系人33','学校详细地址33','学校电话33','2020-10-11 04:05:08',NULL,NULL,'2020-10-11 04:05:08',NULL,NULL,0,'default.png',NULL,NULL),(281,'34@qq.com','$2a$10$nI3Qadlkr6COn7nDxXBhYO3f6qmDrsuoFU8nGQsVL1JcED0Fgkfj.','学校34','联系人34','学校详细地址34','学校电话34','2020-10-11 04:05:08',NULL,NULL,'2020-10-11 04:05:08',NULL,NULL,0,'default.png',NULL,NULL),(282,'35@qq.com','$2a$10$cKJ3/uTdriYnDP/YVK8lM.8ksTdeT1JtWzgnt0kJ5tI0wpoefyWQy','学校35','联系人35','学校详细地址35','学校电话35','2020-10-11 04:05:08',NULL,NULL,'2020-10-11 04:05:08',NULL,NULL,0,'default.png',NULL,NULL),(283,'36@qq.com','$2a$10$2jn95jx9t7YbLrBJJYjewufk0CnUW.j5ebl1nJnQFZjdV4iTW.3tS','学校36','联系人36','学校详细地址36','学校电话36','2020-10-11 04:05:08',NULL,NULL,'2020-10-11 04:05:08',NULL,NULL,0,'default.png',NULL,NULL),(284,'37@qq.com','$2a$10$E/qaUM2JKANWtI6Zu482ReHZEhpcobDjj2PBcrJ6hquyRRymXS.K.','学校37','联系人37','学校详细地址37','学校电话37','2020-10-11 04:05:09',NULL,NULL,'2020-10-11 04:05:09',NULL,NULL,0,'default.png',NULL,NULL),(285,'38@qq.com','$2a$10$d/WXyZOK/hGrsOgb7bGeu.YSnJDvb4mVJcCzLCU2ogeqWhE3L5STu','学校38','联系人38','学校详细地址38','学校电话38','2020-10-11 04:05:09',NULL,NULL,'2020-10-11 04:05:09',NULL,NULL,0,'default.png',NULL,NULL),(286,'39@qq.com','$2a$10$l3KSdXHYdVqYGQUZ5DGeLO6K92OSJ6eqwa6PkqaqHJfDxP5ty4UFG','学校39','联系人39','学校详细地址39','学校电话39','2020-10-11 04:05:09',NULL,NULL,'2020-10-11 04:05:09',NULL,NULL,0,'default.png',NULL,NULL),(287,'40@qq.com','$2a$10$6eJD3AlMsdwrB8ntSR2ogOnb5KjeOQO1t0p38qOx8kZIgTAwk5m0y','学校40','联系人40','学校详细地址40','学校电话40','2020-10-11 04:05:09',NULL,NULL,'2020-10-11 04:05:09',NULL,NULL,0,'default.png',NULL,NULL),(288,'41@qq.com','$2a$10$QwD4g5KYWuCWwW3myW7vdOmzYRrhMRiyhhUW1XhPh5G40SpCsf3SW','学校41','联系人41','学校详细地址41','学校电话41','2020-10-11 04:05:09',NULL,NULL,'2020-10-11 04:05:09',NULL,NULL,0,'default.png',NULL,NULL),(289,'42@qq.com','$2a$10$JLDdSLftRDJoz7cyuVrsfeQXsdABJeMggp3OPX8h0Ks0NEPEQ0y8O','学校42','联系人42','学校详细地址42','学校电话42','2020-10-11 04:05:09',NULL,NULL,'2020-10-11 04:05:09',NULL,NULL,0,'default.png',NULL,NULL),(290,'43@qq.com','$2a$10$qavHmi7FPAjLdFfVP0vQR.FY8.Pn2RjLVy2v9.nRt70krmaba7cci','学校43','联系人43','学校详细地址43','学校电话43','2020-10-11 04:05:09',NULL,NULL,'2020-10-11 04:05:09',NULL,NULL,0,'default.png',NULL,NULL),(291,'44@qq.com','$2a$10$SjdM5s2Xrnu1APe8r0yOHenSNUz6VJ5iAfZeHkrhma4/GRsu88A.a','学校44','联系人44','学校详细地址44','学校电话44','2020-10-11 04:05:09',NULL,NULL,'2020-10-11 04:05:09',NULL,NULL,0,'default.png',NULL,NULL),(292,'45@qq.com','$2a$10$Q3KHXta70J6r3gK2BJFmp.ZvIPRgJrTyiq2JC8lImzSXl4BsEzWx.','学校45','联系人45','学校详细地址45','学校电话45','2020-10-11 04:05:09',NULL,NULL,'2020-10-11 04:05:09',NULL,NULL,0,'default.png',NULL,NULL),(293,'46@qq.com','$2a$10$7xYuDvcVomd7nbGv7SIXOeT0l04iZ106jbUBQq5byLR5cdNFIGLSa','学校46','联系人46','学校详细地址46','学校电话46','2020-10-11 04:05:09',NULL,NULL,'2020-10-11 04:05:09',NULL,NULL,0,'default.png',NULL,NULL),(294,'47@qq.com','$2a$10$UcXgcO0.NNkywjBsqBYTNeS2OcUQVdhEK5Lx6kujtEFeYC6m5j9fm','学校47','联系人47','学校详细地址47','学校电话47','2020-10-11 04:05:09',NULL,NULL,'2020-10-11 04:05:09',NULL,NULL,0,'default.png',NULL,NULL),(295,'48@qq.com','$2a$10$d2K.Q5AwR1jBq2Mpc7kG1OneghVi2PQNDRE8ZAp9ytufXClMtUIyq','学校48','联系人48','学校详细地址48','学校电话48','2020-10-11 04:05:10',NULL,NULL,'2020-10-11 04:05:10',NULL,NULL,0,'default.png',NULL,NULL),(296,'49@qq.com','$2a$10$W/qcth5KdYKzKFIIJfDNbejIkmgHFcv6n3DAHW9.koMmnJPP7qvjC','学校49','联系人49','学校详细地址49','学校电话49','2020-10-11 04:05:10',NULL,NULL,'2020-10-11 04:05:10',NULL,NULL,0,'default.png',NULL,NULL),(297,'50@qq.com','$2a$10$pRPuQ4u06PF/9NG0mWobfOk7j//M8eK5sGXeBEwMywyd7hlO0I5AW','学校50','联系人50','学校详细地址50','学校电话50','2020-10-11 04:05:10',NULL,NULL,'2020-10-11 04:05:10',NULL,NULL,0,'default.png',NULL,NULL),(298,'51@qq.com','$2a$10$JbdsrUIlkbuC2XJ43tDKcegiA8BTwuuhV6SacGlvzT5I92QSixbp.','学校51','联系人51','学校详细地址51','学校电话51','2020-10-11 04:05:10',NULL,NULL,'2020-10-11 04:05:10',NULL,NULL,0,'default.png',NULL,NULL),(299,'52@qq.com','$2a$10$A9ypRFDthFaXynNVrnEUtOYySvqTegRQr/GF/ur.7Jwa.P3VT3HZe','学校52','联系人52','学校详细地址52','学校电话52','2020-10-11 04:05:10',NULL,NULL,'2020-10-11 04:05:10',NULL,NULL,0,'default.png',NULL,NULL),(300,'53@qq.com','$2a$10$bMOzdmKhoIelql1YpKyGI.Zro9k33gKfroR2NRleciqF49XXFeere','学校53','联系人53','学校详细地址53','学校电话53','2020-10-11 04:05:10',NULL,NULL,'2020-10-11 04:05:10',NULL,NULL,0,'default.png',NULL,NULL),(301,'54@qq.com','$2a$10$X3xlRUQA3.TIDM/rWSEuNO/l9SuT4xhlZ8fTTdt3bA1BJcgDa2xTy','学校54','联系人54','学校详细地址54','学校电话54','2020-10-11 04:05:10',NULL,NULL,'2020-10-11 04:05:10',NULL,NULL,0,'default.png',NULL,NULL),(302,'55@qq.com','$2a$10$PQUv6iCDgd8qauOrRfO9OOMvc/X/ZFm44QQXm72HgcTFsWWOb6dm.','学校55','联系人55','学校详细地址55','学校电话55','2020-10-11 04:05:10',NULL,NULL,'2020-10-11 04:05:10',NULL,NULL,0,'default.png',NULL,NULL),(303,'56@qq.com','$2a$10$FQifeo7d6gfcdyh/W0S19ueKJRakUpPZeSDODHJYtKnI56QhEi3Ya','学校56','联系人56','学校详细地址56','学校电话56','2020-10-11 04:05:10',NULL,NULL,'2020-10-11 04:05:10',NULL,NULL,0,'default.png',NULL,NULL),(304,'57@qq.com','$2a$10$Yum0QLHSh8jUaT0w8pFczu3Z10/5WHXGvqS/rc2Yog7QjK.yDZ5Ly','学校57','联系人57','学校详细地址57','学校电话57','2020-10-11 04:05:10',NULL,NULL,'2020-10-11 04:05:10',NULL,NULL,0,'default.png',NULL,NULL),(305,'58@qq.com','$2a$10$PuIikkkRdQiQqiVzzVPZgOKCfyvgkYlCdcDDkKxiF/.64Q4FAbSRC','学校58','联系人58','学校详细地址58','学校电话58','2020-10-11 04:05:10',NULL,NULL,'2020-10-11 04:05:10',NULL,NULL,0,'default.png',NULL,NULL),(306,'59@qq.com','$2a$10$vEnhcfXArUtxHDFVCWv60O41400hxy5LX0wz3VqXDjah7vhReKHVy','学校59','联系人59','学校详细地址59','学校电话59','2020-10-11 04:05:11',NULL,NULL,'2020-10-11 04:05:11',NULL,NULL,0,'default.png',NULL,NULL),(307,'60@qq.com','$2a$10$TLovhhDe2U1kb6c2GtpWHeb9cgkgyc5HSgEwIew2c8x6tSdvzAak.','学校60','联系人60','学校详细地址60','学校电话60','2020-10-11 04:05:11',NULL,NULL,'2020-10-11 04:05:11',NULL,NULL,0,'default.png',NULL,NULL),(308,'61@qq.com','$2a$10$h3h3J0lU7xtzoNFW83YWH.Ilbs6K/rpInpTj5o0nMgscfKYraKiEq','学校61','联系人61','学校详细地址61','学校电话61','2020-10-11 04:05:11',NULL,NULL,'2020-10-11 04:05:11',NULL,NULL,0,'default.png',NULL,NULL),(309,'62@qq.com','$2a$10$4Ze5P.AdtNJVDk.RUwTrnOpwQ8eTKGO.xyQnf375olKKO83oS3oSm','学校62','联系人62','学校详细地址62','学校电话62','2020-10-11 04:05:11',NULL,NULL,'2020-10-11 04:05:11',NULL,NULL,0,'default.png',NULL,NULL),(310,'63@qq.com','$2a$10$fnjUvrBUQ5REYKejsS137eG2g.fETZ3Q/qN8fHt/25yRWIREvE8pS','学校63','联系人63','学校详细地址63','学校电话63','2020-10-11 04:05:11',NULL,NULL,'2020-10-11 04:05:11',NULL,NULL,0,'default.png',NULL,NULL),(311,'64@qq.com','$2a$10$VhZEAbvySALmn1B.8VQglOFYCDM8sezEX4CidafeTBkFK4WgDFgkO','学校64','联系人64','学校详细地址64','学校电话64','2020-10-11 04:05:11',NULL,NULL,'2020-10-11 04:05:11',NULL,NULL,0,'default.png',NULL,NULL),(312,'65@qq.com','$2a$10$bRIMHcFvIUmCzZRY2XgfheD8SAnCQCUs8x3W2sLPfpRfVHQod1wv2','学校65','联系人65','学校详细地址65','学校电话65','2020-10-11 04:05:11',NULL,NULL,'2020-10-11 04:05:11',NULL,NULL,0,'default.png',NULL,NULL),(313,'66@qq.com','$2a$10$YSZAQFGx4tq3pfTgzuoAU.0HxUvjseKzXXnDpU3mFDf0zRByxp3ma','学校66','联系人66','学校详细地址66','学校电话66','2020-10-11 04:05:11',NULL,NULL,'2020-10-11 04:05:11',NULL,NULL,0,'default.png',NULL,NULL),(314,'67@qq.com','$2a$10$bnz4lHBVDCSpicz5Q8D62uc5T.U5cbEZM80xrmcVnxgtAuTjf.wGu','学校67','联系人67','学校详细地址67','学校电话67','2020-10-11 04:05:11',NULL,NULL,'2020-10-11 04:05:11',NULL,NULL,0,'default.png',NULL,NULL),(315,'68@qq.com','$2a$10$VE9/EUEs6izE6CITxcP3r.FqJ9gFMBz38Y7x4mZFrptiTiuEaBFC.','学校68','联系人68','学校详细地址68','学校电话68','2020-10-11 04:05:11',NULL,NULL,'2020-10-11 04:05:11',NULL,NULL,0,'default.png',NULL,NULL),(316,'69@qq.com','$2a$10$GrqAy0nDLLb1vZsAcaIXz.yZ7OhN.gm6uIX2LGxs8NSUNDYosgyZW','学校69','联系人69','学校详细地址69','学校电话69','2020-10-11 04:05:11',NULL,NULL,'2020-10-11 04:05:11',NULL,NULL,0,'default.png',NULL,NULL),(317,'70@qq.com','$2a$10$wu/..e/VlLu.hWot3jelyODMcgxljvXCpQI4j/znEs6vJBAaa4LMK','学校70','联系人70','学校详细地址70','学校电话70','2020-10-11 04:05:11',NULL,NULL,'2020-10-11 04:05:11',NULL,NULL,0,'default.png',NULL,NULL),(318,'7122@qq.com','$2a$10$db6pfD37WqQIG2VcYYJjNOOAoItqlkWLOiaNTennAiIZQjm6k.cdq','学校71','联系人71','学校详细地址71','学校电话71','2020-10-14 19:53:35',NULL,NULL,'2020-10-11 04:05:12',NULL,NULL,0,'default.png',NULL,NULL),(319,'72@qq.com','$2a$10$iRaKaC25PyT3a/FuhNWCQuDIpIvTlIWxBkAF0pwwb3T8Nc30sBX9a','学校72','联系人72','学校详细地址72','学校电话72','2020-10-11 04:05:12',NULL,NULL,'2020-10-11 04:05:12',NULL,NULL,0,'default.png',NULL,NULL),(320,'73@qq.com','$2a$10$luHbXzeWI3Csak/mUZjS8e02baVprI03ubz0InzQz/hWX9JO.0L2O','学校73','联系人73','学校详细地址73','学校电话73','2020-10-11 04:05:12',NULL,NULL,'2020-10-11 04:05:12',NULL,NULL,0,'default.png',NULL,NULL),(321,'74@qq.com','$2a$10$3gbWPZle8EiO5bMiEyAAjeMgQ8Imkzu3An4skdeOLcG0UF6JJCjhW','学校74','联系人74','学校详细地址74','学校电话74','2020-10-11 04:05:12',NULL,NULL,'2020-10-11 04:05:12',NULL,NULL,0,'default.png',NULL,NULL),(322,'75@qq.com','$2a$10$Slbkp5TMClBDdQDa5yr8YeSsn/LBFVcI9.1AO/zlyK/NLfAJd8tUW','学校75','联系人75','学校详细地址75','学校电话75','2020-10-11 04:05:12',NULL,NULL,'2020-10-11 04:05:12',NULL,NULL,0,'default.png',NULL,NULL),(323,'76@qq.com','$2a$10$fp3GIYGJ85ffTYTIF4Gggeg9dOWVUWn3ao1LDxqVD2GSAeRb2G5Ua','7676学校名','7676联系人','7676详细地址','7676电话','2020-10-13 12:56:34',NULL,NULL,'2020-10-11 04:05:12',NULL,NULL,0,'default.png',NULL,NULL),(324,'123@qq.com','$2a$10$KeYF6XWIUaXezP4BkPbRV.OQBFzNzQFbFrHzWG7U7.EFQobTrJX4C','GDUFS','人名','地址','111','2020-10-13 12:14:44','111','ip无法定位！','2020-10-11 01:02:47','0:0:0:0:0:0:0:1','2020-10-11 09:02:54',1,'default.png',NULL,'教授'),(325,'777@qq.com','$2a$10$TuiM2i.rspgGn9semAabr.1j9K7TdtAktg2MMqiL4eJwwMDquwPC.','GDUFS','人名','地址','111','2020-10-13 12:13:39','111',NULL,'2020-10-13 12:01:35',NULL,NULL,1,'default.png',1,'教授'),(330,'123@qq.com','$2a$10$noVjOCbolaziNK0vQ.JMeeYvIrVOFgsrpE/IDGL3N4yheTiv.1L3S',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,NULL,NULL);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usertorole`
--

DROP TABLE IF EXISTS `usertorole`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `usertorole` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userId` int(11) DEFAULT NULL,
  `roleId` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `usertorole_role__fk` (`roleId`),
  KEY `usertorole_user__fk` (`userId`),
  CONSTRAINT `usertorole_role__fk` FOREIGN KEY (`roleId`) REFERENCES `role` (`id`),
  CONSTRAINT `usertorole_user__fk` FOREIGN KEY (`userId`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=218 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usertorole`
--

LOCK TABLES `usertorole` WRITE;
/*!40000 ALTER TABLE `usertorole` DISABLE KEYS */;
INSERT INTO `usertorole` VALUES (134,247,1),(135,248,2),(136,249,2),(137,250,2),(138,251,2),(139,252,2),(140,253,2),(141,254,2),(142,255,2),(143,256,2),(144,257,2),(145,258,2),(146,259,2),(147,260,2),(148,261,2),(149,262,2),(150,263,2),(151,264,2),(152,265,2),(153,266,2),(154,267,2),(155,268,2),(156,269,2),(157,270,2),(158,271,2),(159,272,2),(160,273,2),(161,274,2),(162,275,2),(163,276,2),(164,277,2),(165,278,2),(166,279,2),(167,280,2),(168,281,2),(169,282,2),(170,283,2),(171,284,2),(172,285,2),(173,286,2),(174,287,2),(175,288,2),(176,289,2),(177,290,2),(178,291,2),(179,292,2),(180,293,2),(181,294,2),(182,295,2),(183,296,2),(184,297,2),(185,298,2),(186,299,2),(187,300,2),(188,301,2),(189,302,2),(190,303,2),(191,304,2),(192,305,2),(193,306,2),(194,307,2),(195,308,2),(196,309,2),(197,310,2),(198,311,2),(199,312,2),(200,313,2),(201,314,2),(202,315,2),(203,316,2),(204,317,2),(205,318,2),(206,319,2),(207,320,2),(208,321,2),(209,322,2),(210,323,2),(211,324,2),(212,325,2);
/*!40000 ALTER TABLE `usertorole` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-10-15 10:49:00
