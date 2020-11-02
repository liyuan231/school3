-- MySQL dump 10.13  Distrib 8.0.19, for Win64 (x86_64)
--
-- Host: localhost    Database: school
-- ------------------------------------------------------
-- Server version	8.0.19

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
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
/*!50503 SET character_set_client = utf8 */;
CREATE TABLE `likes` (
  `id` int NOT NULL AUTO_INCREMENT,
  `likeUserId` int DEFAULT NULL COMMENT '代表“我”',
  `likedUserId` int DEFAULT NULL COMMENT '喜欢 她',
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
) ENGINE=InnoDB AUTO_INCREMENT=348 DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `likes`
--

LOCK TABLES `likes` WRITE;
/*!40000 ALTER TABLE `likes` DISABLE KEYS */;
INSERT INTO `likes` VALUES (338,3,4,'2020-10-27 12:21:17','2020-10-22 17:31:54',0,'广东外语外贸大学','中山大学ZSDX'),(339,4,3,'2020-10-27 11:27:03','2020-10-22 17:32:21',0,'中山大学ZSDX1','广东外语外贸大学'),(340,4,3,'2020-10-27 11:24:33','2020-10-22 17:35:24',0,'中山大学ZSDX','广东外语外贸大学'),(341,3,4,'2020-10-27 12:21:17','2020-10-22 17:31:54',0,'广东外语外贸大学','中山大学ZSDX'),(342,4,1,'2020-10-27 11:24:33','2020-10-27 11:22:27',0,'中山大学ZSDX1','广东无语外贸大学'),(343,4,1,'2020-10-27 11:26:26','2020-10-27 11:26:18',0,'中山大学ZSDX1','广东无语外贸大学'),(344,4,2,'2020-10-27 11:27:03','2020-10-27 11:26:31',0,'中山大学ZSDX1','GDUFS'),(345,4,1,'2020-10-27 11:34:28','2020-10-27 11:26:42',0,'中山大学ZSDX1','广东无语外贸大学'),(346,1,4,'2020-10-27 12:21:17','2020-10-27 11:27:19',0,'广东无语外贸大学','中山大学ZSDX1'),(347,4,3,'2020-10-27 12:21:17','2020-10-27 11:27:53',0,'中山大学ZSDX1','广东外语外贸大学');
/*!40000 ALTER TABLE `likes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pics`
--

DROP TABLE IF EXISTS `pics`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8 */;
CREATE TABLE `pics` (
  `id` int NOT NULL AUTO_INCREMENT,
  `userId` int DEFAULT NULL,
  `location` varchar(200) DEFAULT NULL,
  `type` int NOT NULL COMMENT '1头像，2logo，3签章',
  `add_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `deleted` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `pics_user__fk` (`userId`),
  CONSTRAINT `pics_user__fk` FOREIGN KEY (`userId`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci COMMENT='由于一开始设计没考虑到，因此在此说明此表存所有用户传上来的资源，如图片，doc文档...,1:头像;2:学校logo;3:校长签章;4:mou模板';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pics`
--

LOCK TABLES `pics` WRITE;
/*!40000 ALTER TABLE `pics` DISABLE KEYS */;
INSERT INTO `pics` VALUES (1,3,'d79b8d63-af3b-4d45-a12d-78048299aa18.jpg',2,'2020-10-22 16:13:39','2020-10-27 12:21:16',0),(2,3,'4eda0774-6f40-4935-b7f6-2a0435cd4e29.jpg',3,'2020-10-22 16:14:43','2020-10-27 12:21:16',0),(3,4,'632b10a5-8c65-48d4-baf5-c81ae63e0232.jpg',2,'2020-10-22 16:59:52','2020-10-27 12:21:16',0),(4,4,'6686c7dd-1aca-4044-9946-3ae8db7ded41.jpg',3,'2020-10-22 17:00:07','2020-10-27 12:21:16',0),(5,2,'56eeaff8-f3e2-40a9-9a07-9199bc43f63b.docx',4,'2020-10-22 18:21:27','2020-10-27 12:21:17',0);
/*!40000 ALTER TABLE `pics` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role`
--

DROP TABLE IF EXISTS `role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8 */;
CREATE TABLE `role` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(200) DEFAULT NULL,
  `desc` text,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role`
--

LOCK TABLES `role` WRITE;
/*!40000 ALTER TABLE `role` DISABLE KEYS */;
INSERT INTO `role` VALUES (1,'ADMINISTRATOR',NULL),(2,'USER',NULL);
/*!40000 ALTER TABLE `role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `roletoauthorities`
--

DROP TABLE IF EXISTS `roletoauthorities`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8 */;
CREATE TABLE `roletoauthorities` (
  `id` int NOT NULL AUTO_INCREMENT,
  `roleId` int DEFAULT NULL,
  `authority` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `roletoauthorities_role__fk` (`roleId`),
  CONSTRAINT `roletoauthorities_role__fk` FOREIGN KEY (`roleId`) REFERENCES `role` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `roletoauthorities`
--

LOCK TABLES `roletoauthorities` WRITE;
/*!40000 ALTER TABLE `roletoauthorities` DISABLE KEYS */;
/*!40000 ALTER TABLE `roletoauthorities` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sign`
--

DROP TABLE IF EXISTS `sign`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8 */;
CREATE TABLE `sign` (
  `id` int NOT NULL AUTO_INCREMENT,
  `signUserId` int DEFAULT NULL COMMENT '签约的一方',
  `signedUserId` int DEFAULT NULL COMMENT '被签约的一方\n',
  `update_time` datetime DEFAULT NULL,
  `add_time` datetime DEFAULT NULL,
  `deleted` tinyint(1) DEFAULT '0',
  `status` int DEFAULT '1' COMMENT '该则签约的状态，公示状态或隐藏状态',
  `signSchoolName` varchar(200) DEFAULT NULL,
  `signedSchoolName` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `sign_user__fk` (`signUserId`),
  KEY `sign_user__fk_2` (`signedUserId`),
  CONSTRAINT `sign_user__fk` FOREIGN KEY (`signUserId`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `sign_user__fk_2` FOREIGN KEY (`signedUserId`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sign`
--

LOCK TABLES `sign` WRITE;
/*!40000 ALTER TABLE `sign` DISABLE KEYS */;
INSERT INTO `sign` VALUES (14,3,4,'2020-10-27 12:21:15','2020-10-22 17:40:31',0,1,'广东外语外贸大学','中山大学ZSDX'),(17,1,4,'2020-10-27 12:21:16','2020-10-27 11:27:19',0,1,'广东无语外贸大学','中山大学ZSDX1');
/*!40000 ALTER TABLE `sign` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `id` int NOT NULL AUTO_INCREMENT,
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
  `accountStatus` int DEFAULT NULL COMMENT '账户状态',
  `profession` varchar(60) DEFAULT NULL COMMENT '职务',
  `country` varchar(255) DEFAULT NULL COMMENT '学校所在国家',
  `website` varchar(1024) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=337 DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'807496188@qq.com','$2a$10$/Ru00AZDO/SNTa1GjKBYBekFYITnVx5JIRAUwzGpNW5kBZnF3vEDC','广东无语外贸大学','家禧','南苑','110','2020-10-27 12:21:14',NULL,'局域网IP无法定位','2020-10-11 04:05:05','58.62.42.64','2020-10-18 19:08:28',0,'default.png',1,'老师','中国','https://www.gdufs.edu.cn'),(2,'3011543110@qq.com','$2a$10$4ME7YB1jOc4tL8d6SuPwz.lTdIVgsmssNEzn6jcQYheAhsBi7RYF6','GDUFS','振宇','南苑','120','2020-10-25 16:28:13','111','局域网IP无法定位','2020-10-13 12:01:35','192.168.1.105','2020-10-25 16:28:12',0,'default.png',1,'管理员','中国','https://www.gdufs.edu.cn'),(3,'2812329425@qq.com','$2a$10$OFygDixcEYxcPXa32IAFaugF1056hO36NMDUPFcyuZqmysOmOjLwC','广东外语外贸大学','元夕','小谷围广外东路','18719138325','2020-10-27 12:21:15','1234','IP无法定位','2020-10-22 16:00:57','0:0:0:0:0:0:0:1','2020-10-22 21:45:05',0,'default.png',1,'学生','中国','https://www.gdufs.edu.cn'),(4,'1987151116@qq.com','$2a$10$m8SaUEAAO6oe3bb5XO25o.93QXzKiba7o17k7GZiYM7NJNd5SX6De','中山大学ZSDX1','doctor Sun1','无1','137267795991','2020-10-27 12:21:15',NULL,'IP无法定位','2020-10-22 16:53:10','0:0:0:0:0:0:0:1','2020-10-22 16:56:08',0,'default.jpg',1,'学生1','中国','https://www.gdufs.edu.cn1');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usertorole`
--

DROP TABLE IF EXISTS `usertorole`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8 */;
CREATE TABLE `usertorole` (
  `id` int NOT NULL AUTO_INCREMENT,
  `userId` int DEFAULT NULL,
  `roleId` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `usertorole_role__fk` (`roleId`),
  KEY `usertorole_user__fk` (`userId`),
  CONSTRAINT `usertorole_role__fk` FOREIGN KEY (`roleId`) REFERENCES `role` (`id`),
  CONSTRAINT `usertorole_user__fk` FOREIGN KEY (`userId`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=225 DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usertorole`
--

LOCK TABLES `usertorole` WRITE;
/*!40000 ALTER TABLE `usertorole` DISABLE KEYS */;
INSERT INTO `usertorole` VALUES (1,1,2),(2,2,1),(3,3,2),(4,4,2);
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

-- Dump completed on 2020-10-27 18:57:48
