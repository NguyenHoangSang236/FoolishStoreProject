CREATE DATABASE  IF NOT EXISTS `fashionstorewebsite` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `fashionstorewebsite`;
-- MySQL dump 10.13  Distrib 8.0.36, for Linux (x86_64)
--
-- Host: 103.200.20.153    Database: fashionstorewebsite
-- ------------------------------------------------------
-- Server version	8.0.33

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
-- Table structure for table `cart`
--

DROP TABLE IF EXISTS `cart`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cart` (
  `ID` int NOT NULL AUTO_INCREMENT,
  `customer_id` bigint NOT NULL,
  `product_management_id` bigint NOT NULL,
  `quantity` int NOT NULL,
  `buying_status` varchar(20) NOT NULL DEFAULT 'NOT_BOUGHT_YET',
  `Select_status` int DEFAULT NULL,
  `invoice_id` bigint DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_Customer_Cart` (`customer_id`),
  KEY `FK_Product_Cart_idx` (`product_management_id`),
  KEY `invoice_id` (`invoice_id`),
  CONSTRAINT `FK_Customer_Cart` FOREIGN KEY (`customer_id`) REFERENCES `customers` (`id`),
  CONSTRAINT `FK_Invoice_Cart` FOREIGN KEY (`invoice_id`) REFERENCES `invoice` (`ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `FK_Product_Cart` FOREIGN KEY (`product_management_id`) REFERENCES `products_management` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cart`
--

LOCK TABLES `cart` WRITE;
/*!40000 ALTER TABLE `cart` DISABLE KEYS */;
INSERT INTO `cart` VALUES (1,1,304,1,'PENDING',1,1),(2,1,303,1,'PENDING',1,2),(3,29,301,1,'PENDING',1,3),(4,29,12,1,'PENDING',1,5),(5,29,286,1,'BOUGHT',1,6),(6,29,278,1,'PENDING',1,4),(7,29,301,4,'NOT_BOUGHT_YET',0,NULL),(8,29,1,1,'PENDING',1,7),(9,29,278,2,'PENDING',1,7),(10,29,1,3,'NOT_BOUGHT_YET',0,NULL),(11,1,303,1,'BOUGHT',1,8),(12,29,286,2,'NOT_BOUGHT_YET',1,NULL),(14,1,4,2,'NOT_BOUGHT_YET',0,NULL),(15,1,303,2,'NOT_BOUGHT_YET',1,NULL),(16,1,305,3,'NOT_BOUGHT_YET',0,NULL),(17,27,303,1,'PENDING',1,9),(18,27,305,1,'PENDING',1,10),(19,30,12,2,'PENDING',1,12),(20,30,312,1,'PENDING',1,13),(21,30,309,2,'PENDING',1,16),(22,32,12,12,'NOT_BOUGHT_YET',0,NULL),(23,32,320,2,'NOT_BOUGHT_YET',0,NULL),(24,34,288,1,'PENDING',1,17),(26,1,319,1,'NOT_BOUGHT_YET',0,NULL);
/*!40000 ALTER TABLE `cart` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Temporary view structure for view `cart_item_info_for_ui`
--

DROP TABLE IF EXISTS `cart_item_info_for_ui`;
/*!50001 DROP VIEW IF EXISTS `cart_item_info_for_ui`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `cart_item_info_for_ui` AS SELECT 
 1 AS `id`,
 1 AS `customer_id`,
 1 AS `product_management_id`,
 1 AS `quantity`,
 1 AS `buying_status`,
 1 AS `select_status`,
 1 AS `product_id`,
 1 AS `color`,
 1 AS `size`,
 1 AS `name`,
 1 AS `brand`,
 1 AS `selling_price`,
 1 AS `discount`,
 1 AS `image_1`*/;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `catalog`
--

DROP TABLE IF EXISTS `catalog`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `catalog` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `image` varchar(500) NOT NULL DEFAULT 'https://www.google.com/url?sa=i&url=https%3A%2F%2Fwww.pexels.com%2Fsearch%2Fclothing%2F&psig=AOvVaw1Gkld9bmnAEiYd1gp8RxX3&ust=1682415727269000&source=images&cd=vfe&ved=0CBEQjRxqFwoTCOjHi5adwv4CFQAAAAAdAAAAABAD',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `catalog`
--

LOCK TABLES `catalog` WRITE;
/*!40000 ALTER TABLE `catalog` DISABLE KEYS */;
INSERT INTO `catalog` VALUES (1,'Clothing','https://th.bing.com/th?id=OIP.OxETBkx08eL5IVSRa8ZFegHaHa&w=250&h=250&c=8&rs=1&qlt=90&o=6&dpr=1.3&pid=3.1&rm=2'),(2,'Glasses','1sxJBW8O5FurTGUE39cIxdRcRycLg6pnK'),(3,'Shoes','1C22R6MlFTiMwbewDPkzpX1wb_GnNGu08'),(4,'Male','17X-b4LDq0VoHV1QOJE5fMvVhIoejq_2u'),(5,'Female','128ptt1MwwaDE1B-Qd0PYt-z0mooOBU9L'),(6,'Accessories','1vu0bQvTF38IAsJtLBH_rFQHNJAqY1IgM'),(7,'Purses','1GKehSvAvHwZ9dVe82dDoGBzXe1rbw1vz'),(8,'Sport','14Ap5-CSmhnR5wAxbLsJ0XKkbi45vya6X'),(9,'Backpackes','1rdb1BgNCVRL2A5FAVqvRcV0azUd1nBlw');
/*!40000 ALTER TABLE `catalog` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `catalogs_with_products`
--

DROP TABLE IF EXISTS `catalogs_with_products`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `catalogs_with_products` (
  `catalog_id` bigint NOT NULL,
  `product_id` bigint NOT NULL,
  PRIMARY KEY (`catalog_id`,`product_id`),
  KEY `fk_catalogs_with_products_products_idx` (`product_id`),
  CONSTRAINT `fk_catalogs_with_products_catalog` FOREIGN KEY (`catalog_id`) REFERENCES `catalog` (`id`),
  CONSTRAINT `fk_catalogs_with_products_products` FOREIGN KEY (`product_id`) REFERENCES `products` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `catalogs_with_products`
--

LOCK TABLES `catalogs_with_products` WRITE;
/*!40000 ALTER TABLE `catalogs_with_products` DISABLE KEYS */;
INSERT INTO `catalogs_with_products` VALUES (4,1),(8,1),(5,2),(6,2),(7,2),(4,3),(3,4),(4,4),(5,5),(4,6),(8,6),(5,7),(6,7),(9,7),(2,8),(5,8),(3,41),(4,41),(5,41),(8,41),(5,43),(6,43),(7,43),(3,44),(4,44),(5,44),(4,45),(5,45),(6,45),(4,46),(3,47),(4,47),(5,47),(8,47),(4,48),(5,48),(6,48),(8,48),(9,48),(4,49),(5,49),(6,49),(8,49),(4,50),(5,50),(6,50),(8,50),(9,50),(5,51),(3,55),(4,55),(8,55);
/*!40000 ALTER TABLE `catalogs_with_products` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Temporary view structure for view `comment_info_for_ui`
--

DROP TABLE IF EXISTS `comment_info_for_ui`;
/*!50001 DROP VIEW IF EXISTS `comment_info_for_ui`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `comment_info_for_ui` AS SELECT 
 1 AS `id`,
 1 AS `product_id`,
 1 AS `product_color`,
 1 AS `customer_id`,
 1 AS `name`,
 1 AS `avatar`,
 1 AS `comment_content`,
 1 AS `comment_date`,
 1 AS `like_quantity`,
 1 AS `reply_quantity`,
 1 AS `reply_on`*/;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `comment_like`
--

DROP TABLE IF EXISTS `comment_like`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `comment_like` (
  `customer_id` bigint NOT NULL,
  `comment_id` bigint NOT NULL,
  PRIMARY KEY (`customer_id`,`comment_id`),
  KEY `comment_id` (`comment_id`),
  CONSTRAINT `comment_like_ibfk_1` FOREIGN KEY (`customer_id`) REFERENCES `customers` (`id`),
  CONSTRAINT `comment_like_ibfk_2` FOREIGN KEY (`comment_id`) REFERENCES `comments` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `comment_like`
--

LOCK TABLES `comment_like` WRITE;
/*!40000 ALTER TABLE `comment_like` DISABLE KEYS */;
INSERT INTO `comment_like` VALUES (1,1),(2,1),(1,2),(2,2),(3,2),(30,2),(1,13),(29,13),(1,14),(1,15),(1,16),(1,19),(1,28),(1,31),(1,32),(1,40),(1,41),(29,42),(29,45),(30,45),(1,49),(29,49),(1,53),(1,64),(1,85),(1,92),(1,95);
/*!40000 ALTER TABLE `comment_like` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `comments`
--

DROP TABLE IF EXISTS `comments`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `comments` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `product_id` bigint NOT NULL,
  `product_color` varchar(20) NOT NULL DEFAULT 'none',
  `customer_id` bigint NOT NULL,
  `comment_content` text NOT NULL,
  `comment_date` datetime NOT NULL,
  `like_quantity` int NOT NULL DEFAULT '0',
  `reply_quantity` int NOT NULL DEFAULT '0',
  `reply_on` int DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `FK_Customer_Comments` (`customer_id`),
  KEY `FK_Product_Comments` (`product_id`),
  CONSTRAINT `FK_Customer_Comments` FOREIGN KEY (`customer_id`) REFERENCES `customers` (`id`),
  CONSTRAINT `FK_Product_Comments` FOREIGN KEY (`product_id`) REFERENCES `products` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=101 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `comments`
--

LOCK TABLES `comments` WRITE;
/*!40000 ALTER TABLE `comments` DISABLE KEYS */;
INSERT INTO `comments` VALUES (1,1,'red',1,'nai xu','2023-07-30 09:17:33',2,14,0),(2,1,'red',3,'wow','2023-03-03 00:00:00',4,0,1),(4,1,'red',1,'niceee','2023-07-30 10:15:29',0,0,1),(5,1,'red',2,'alooo','2023-07-30 10:15:49',0,0,1),(6,1,'red',1,'huuuhu','2023-07-30 10:16:01',0,0,1),(7,1,'red',1,'huuuhu','2023-07-30 10:16:15',0,2,0),(8,1,'red',1,'shjttttt bro','2023-09-01 10:32:49',0,0,1),(9,1,'red',1,'shjttttt','2023-09-01 10:37:09',0,0,1),(10,1,'red',1,'dep qua','2023-09-01 12:55:56',0,0,0),(11,1,'red',1,'accc','2023-09-01 13:05:36',0,0,0),(12,1,'red',1,'deppp','2023-09-01 13:07:02',0,0,0),(13,1,'red',1,'alo','2023-09-01 13:07:30',2,1,0),(14,1,'red',1,'xinh','2023-09-01 13:08:06',1,2,0),(15,1,'red',1,'hehehehehehe','2023-09-01 13:08:59',1,4,0),(16,1,'red',1,'ak','2023-09-01 13:10:04',1,1,0),(17,1,'red',1,'no','2023-09-01 15:44:32',0,3,0),(18,1,'white',1,'nice Tee','2023-09-01 16:29:01',0,4,0),(19,1,'white',1,'omg','2023-09-01 16:41:15',1,0,0),(20,1,'white',1,'kkkk','2023-09-01 17:06:52',0,0,18),(21,1,'white',1,'hhhh','2023-09-01 17:07:09',0,0,18),(22,1,'red',1,'ok','2023-09-04 15:33:28',0,0,1),(23,1,'red',1,'yyyy','2023-09-04 15:41:00',0,0,1),(24,1,'red',1,'u gud?','2023-09-04 16:32:19',0,0,1),(25,1,'red',1,'love you ','2023-09-04 16:49:49',0,0,1),(26,1,'red',1,'alo 1','2023-09-04 16:51:13',0,0,1),(27,1,'red',1,'alo 2','2023-09-04 17:03:41',0,0,1),(28,1,'red',1,'alo 3','2023-09-04 17:04:05',1,0,1),(29,1,'white',1,'hehehe','2023-09-04 17:05:44',0,0,18),(30,1,'white',1,'heheh','2023-09-04 17:08:35',0,0,18),(31,1,'red',1,'hihi','2023-09-04 17:08:56',1,0,17),(32,1,'red',1,'kkkk','2023-09-04 17:09:09',1,0,17),(33,1,'red',1,'eeee','2023-09-04 17:09:49',0,0,7),(34,1,'red',1,'hhhh','2023-09-04 17:13:38',0,0,7),(35,1,'red',1,'iiii','2023-09-04 17:19:45',0,0,16),(36,1,'red',1,'fff','2023-09-04 17:21:11',0,0,15),(37,1,'red',1,'fff','2023-09-04 17:21:13',0,0,15),(38,1,'red',1,'fff','2023-09-04 17:21:15',0,0,15),(39,1,'red',1,'fff','2023-09-04 17:21:41',0,0,15),(40,1,'red',1,'luv','2023-09-04 17:25:12',1,0,14),(41,1,'red',1,'ac','2023-09-08 08:10:49',1,0,17),(42,7,'white',1,'okkk','2023-09-08 14:39:58',1,1,0),(43,7,'white',29,'Ok','2023-12-21 13:32:25',0,0,42),(44,7,'white',29,'ok','2023-12-21 13:32:47',0,0,0),(45,4,'none',29,'Good!','2023-12-24 07:49:23',2,1,0),(46,4,'none',29,'Thanks','2023-12-24 07:49:44',0,0,45),(47,4,'none',29,'Great!','2023-12-24 07:50:02',0,0,0),(48,46,'black',29,'Đẹp','2024-01-03 11:55:09',0,0,0),(49,1,'red',29,'Check','2024-01-03 13:17:46',2,0,13),(50,50,'black',29,'hhhh','2024-01-04 01:28:43',0,1,0),(51,50,'black',1,'ok','2024-02-28 03:57:53',0,0,50),(52,51,'white',1,'nnn','2024-02-28 11:44:38',0,0,0),(53,1,'white',1,'mmm','2024-02-28 11:59:35',1,1,0),(54,1,'white',1,'nnn','2024-02-28 11:59:59',0,0,53),(55,53,'orange',30,'good','2024-03-05 07:19:35',0,0,0),(56,55,'Yellow',32,'cc','2024-03-13 05:28:22',0,1,0),(57,44,'none',34,'hghu','2024-03-13 07:54:24',0,0,0),(60,1,'red',1,'nice T','2024-04-04 09:34:51',0,1,0),(61,1,'red',1,'hello','2024-04-05 08:27:58',0,0,60),(62,1,'red',1,'gheee','2024-04-05 09:30:45',0,0,14),(63,55,'Yellow',1,'abc','2024-04-06 09:35:28',0,0,56),(64,55,'yellow',1,'non','2024-04-06 09:35:40',1,0,0),(65,55,'yellow',1,'non','2024-04-06 09:35:41',0,0,0),(66,55,'blue',30,'hhh','2024-04-09 10:01:31',0,0,0),(67,55,'blue',1,'aaa','2024-04-10 10:45:30',0,0,0),(68,55,'blue',30,'fffutgrfuf','2024-04-10 10:58:44',0,0,0),(69,55,'blue',30,'uu','2024-04-10 10:59:18',0,0,0),(70,55,'blue',30,'uu','2024-04-10 10:59:18',0,0,0),(71,55,'blue',1,'1','2024-04-11 03:45:41',0,0,0),(72,55,'blue',1,'2','2024-04-11 03:49:26',0,0,0),(73,55,'blue',1,'3','2024-04-11 03:49:47',0,0,0),(74,1,'white',1,'111','2024-04-11 03:51:12',0,0,0),(75,1,'white',1,'222','2024-04-11 03:51:18',0,0,0),(76,1,'white',1,'333','2024-04-11 03:51:23',0,0,0),(77,1,'white',1,'444','2024-04-11 03:51:35',0,0,0),(78,1,'red',30,'aaaaaa','2024-04-11 05:01:48',0,0,0),(79,1,'red',30,'aa','2024-04-11 09:44:21',0,0,0),(80,1,'red',1,'aa','2024-04-11 09:45:17',0,0,0),(81,55,'blue',1,'4','2024-04-11 10:05:26',0,0,0),(82,55,'blue',30,'5','2024-04-11 10:07:21',0,0,0),(83,55,'blue',1,'asfasfasf','2024-04-11 10:09:41',0,0,0),(84,55,'blue',1,'22','2024-04-11 10:11:35',0,0,0),(85,55,'blue',1,'222','2024-04-11 10:12:07',1,0,0),(86,55,'blue',1,'11','2024-04-11 10:28:24',0,0,0),(87,55,'blue',1,'333','2024-04-11 10:28:29',0,0,0),(88,55,'blue',1,'11','2024-04-11 10:32:37',0,0,0),(89,55,'yellow',1,'1','2024-04-11 10:38:55',0,0,0),(90,55,'yellow',1,'2','2024-04-11 10:38:58',0,0,0),(91,55,'yellow',1,'3','2024-04-11 10:39:01',0,0,0),(92,55,'yellow',1,'4','2024-04-11 10:39:03',1,0,0),(93,55,'yellow',1,'5','2024-04-11 10:39:06',0,0,0),(94,55,'yellow',1,'6','2024-04-11 10:39:08',0,0,0),(95,55,'yellow',1,'7','2024-04-11 10:39:11',1,0,0),(96,55,'yellow',1,'8','2024-04-11 10:39:14',0,0,0),(97,55,'yellow',1,'9','2024-04-11 10:39:16',0,0,0),(98,55,'yellow',1,'10','2024-04-11 10:39:19',0,0,0),(99,55,'blue',1,'test','2024-04-19 01:44:51',0,0,0),(100,55,'blue',1,'testSocket','2024-04-19 01:45:08',0,0,0);
/*!40000 ALTER TABLE `comments` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Temporary view structure for view `customer_info_for_ui`
--

DROP TABLE IF EXISTS `customer_info_for_ui`;
/*!50001 DROP VIEW IF EXISTS `customer_info_for_ui`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `customer_info_for_ui` AS SELECT 
 1 AS `id`,
 1 AS `account_id`,
 1 AS `user_name`,
 1 AS `password`,
 1 AS `status`,
 1 AS `name`,
 1 AS `email`,
 1 AS `phone_number`,
 1 AS `address`,
 1 AS `city`,
 1 AS `country`,
 1 AS `avatar`*/;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `customers`
--

DROP TABLE IF EXISTS `customers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `customers` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `email` varchar(50) DEFAULT NULL,
  `phone_number` varchar(10) NOT NULL,
  `account_id` bigint NOT NULL,
  `country` text,
  `address` text,
  `city` text,
  `avatar` varchar(500) DEFAULT 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQaiHNXjxQrlFFFHdMGtUpH1nLDjHzyfTms6A&usqp=CAU',
  PRIMARY KEY (`id`),
  KEY `FK_Customers_Accounts` (`account_id`),
  CONSTRAINT `FK_Customers_Accounts` FOREIGN KEY (`account_id`) REFERENCES `login_accounts` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=37 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customers`
--

LOCK TABLES `customers` WRITE;
/*!40000 ALTER TABLE `customers` DISABLE KEYS */;
INSERT INTO `customers` VALUES (1,'Sang dep trai','nguyenhoangsang2362001@gmail.com','0963883179',2,'Vietnam','Quận 1','Hồ Chí Minh','1gpnNz2Pcvv1pXboqcDS8OdYFCINl8JvZ'),(2,'Nguyen Quynh Nhu','nnhu7721@gmail.com','0213654798',3,NULL,NULL,NULL,'1tVXpd6cg_yKMnd7KQ_qqmtdvSG8tXa8R'),(3,'Nguyen Hoang Sang','19110120@student.hcmute.edu.vn','0977815809',7,NULL,NULL,NULL,'1tVXpd6cg_yKMnd7KQ_qqmtdvSG8tXa8R'),(4,'Nguyen Thi Hoang Trang','pbeltranster@gmail.com','0321654987',9,NULL,NULL,NULL,'1tVXpd6cg_yKMnd7KQ_qqmtdvSG8tXa8R'),(6,'qweqwe','qwe@we','1234567890',11,NULL,NULL,NULL,'1tVXpd6cg_yKMnd7KQ_qqmtdvSG8tXa8R'),(7,'Nguyen Quoc Heng','nqh130901@gmail.com','1234567890',12,NULL,NULL,NULL,'1tVXpd6cg_yKMnd7KQ_qqmtdvSG8tXa8R'),(8,'Duc Ngu Vcl','ducngu@gmail.com','0321654987',13,NULL,NULL,NULL,'1tVXpd6cg_yKMnd7KQ_qqmtdvSG8tXa8R'),(15,'guyen uoc oan\0\0\0','qhoangf@gmail.com','0321654789',50,NULL,NULL,NULL,'1tVXpd6cg_yKMnd7KQ_qqmtdvSG8tXa8R'),(16,'Nguyuen Hoang Anh Kho','anhkhoa123@gmail.com','0321456987',51,NULL,NULL,NULL,'1tVXpd6cg_yKMnd7KQ_qqmtdvSG8tXa8R'),(17,'Huynh Gia Kie','giakien@gmail.com','0312546897',52,NULL,NULL,NULL,'1tVXpd6cg_yKMnd7KQ_qqmtdvSG8tXa8R'),(27,'Nguyen Van A','nguyenvana@gmail.com','0977815809',63,NULL,NULL,NULL,'1tVXpd6cg_yKMnd7KQ_qqmtdvSG8tXa8R'),(28,'Nguyen Van','nguyenvanB@gmail.com','0321564897',64,NULL,NULL,NULL,'1tVXpd6cg_yKMnd7KQ_qqmtdvSG8tXa8R'),(29,'Minh Hie','untilyou5501@gmail.com','0961329175',65,'Việt Nam','9/102 Khu Phố Bình Đức 3','Bình Dương','1i3q6VCuX_6Co6YaD3oG_Jr9Tfc446e6-'),(30,'Seng dep zai','sangdepzaivodich@gmail.com','0321549867',66,'Vietnam','Hoc Mon','Ho Chi Minh','1tVXpd6cg_yKMnd7KQ_qqmtdvSG8tXa8R'),(31,'Cao v','caovy2001.8@gmaill.com','0975543975',67,NULL,NULL,NULL,'1tVXpd6cg_yKMnd7KQ_qqmtdvSG8tXa8R'),(32,'Tuan h','hodinhtuan@hotmail.com','0975072968',68,NULL,'ghdudjdhd',NULL,'1tVXpd6cg_yKMnd7KQ_qqmtdvSG8tXa8R'),(33,'Le Huu Tua','lehuutuanlht@gmail.com','0397771169',69,NULL,NULL,NULL,'1tVXpd6cg_yKMnd7KQ_qqmtdvSG8tXa8R'),(34,'Nguyễn Hoàng Đứ','nhduc12b4@gmail.com','1234567890',70,NULL,NULL,NULL,'1tVXpd6cg_yKMnd7KQ_qqmtdvSG8tXa8R'),(35,'Nguyen Van','nguyenvand@gmail.com','0123456789',73,NULL,NULL,NULL,'1tVXpd6cg_yKMnd7KQ_qqmtdvSG8tXa8R'),(36,'Pham Hoang Phu','phuclateo@gmail.com','0913686664',74,NULL,NULL,NULL,'1tVXpd6cg_yKMnd7KQ_qqmtdvSG8tXa8R');
/*!40000 ALTER TABLE `customers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `delivery`
--

DROP TABLE IF EXISTS `delivery`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `delivery` (
  `id` int NOT NULL AUTO_INCREMENT,
  `invoice_id` bigint NOT NULL,
  `shipping_order_code` varchar(20) NOT NULL,
  `ship_date` datetime DEFAULT NULL,
  `expected_delivery_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_Invoice_delivery` (`invoice_id`),
  CONSTRAINT `FK_Invoice_delivery` FOREIGN KEY (`invoice_id`) REFERENCES `invoice` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `delivery`
--

LOCK TABLES `delivery` WRITE;
/*!40000 ALTER TABLE `delivery` DISABLE KEYS */;
INSERT INTO `delivery` VALUES (1,6,'LF76BP','2024-01-03 15:24:11','2024-01-05 23:59:59'),(2,7,'LF76BK',NULL,'2024-01-05 23:59:59'),(3,8,'LF76BE','2024-01-04 01:36:27','2024-01-05 23:59:59'),(4,9,'LFGGH4',NULL,'2024-03-02 23:59:59'),(5,10,'LFGGHK',NULL,'2024-03-02 23:59:59');
/*!40000 ALTER TABLE `delivery` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `device_fcm_token`
--

DROP TABLE IF EXISTS `device_fcm_token`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `device_fcm_token` (
  `id` int NOT NULL AUTO_INCREMENT,
  `account_id` bigint NOT NULL,
  `phone_fcm_token` text NOT NULL,
  PRIMARY KEY (`id`),
  KEY `account_id` (`account_id`),
  CONSTRAINT `device_fcm_token_ibfk_1` FOREIGN KEY (`account_id`) REFERENCES `login_accounts` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `device_fcm_token`
--

LOCK TABLES `device_fcm_token` WRITE;
/*!40000 ALTER TABLE `device_fcm_token` DISABLE KEYS */;
/*!40000 ALTER TABLE `device_fcm_token` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Temporary view structure for view `hot_discount_products`
--

DROP TABLE IF EXISTS `hot_discount_products`;
/*!50001 DROP VIEW IF EXISTS `hot_discount_products`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `hot_discount_products` AS SELECT 
 1 AS `id`,
 1 AS `product_id`,
 1 AS `name`,
 1 AS `size`,
 1 AS `selling_price`,
 1 AS `discount`,
 1 AS `brand`,
 1 AS `color`,
 1 AS `available_quantity`,
 1 AS `image_1`,
 1 AS `image_2`,
 1 AS `image_3`,
 1 AS `image_4`,
 1 AS `overall_rating`,
 1 AS `description`*/;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `invoice`
--

DROP TABLE IF EXISTS `invoice`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `invoice` (
  `ID` bigint NOT NULL AUTO_INCREMENT,
  `Customer_ID` bigint DEFAULT NULL,
  `invoice_date` datetime(6) DEFAULT NULL,
  `pay_date` datetime DEFAULT NULL,
  `Payment_Status` varchar(20) NOT NULL DEFAULT 'UNPAID',
  `order_status` varchar(255) NOT NULL,
  `admin_acceptance` varchar(30) NOT NULL DEFAULT 'WAITING',
  `Payment_Method` varchar(20) NOT NULL DEFAULT 'COD',
  `Reason` text,
  `Refund_Percentage` double DEFAULT '0',
  `Currency` varchar(10) NOT NULL DEFAULT 'USD',
  `Description` text,
  `total_price` double DEFAULT '0',
  `online_payment_account` text,
  `admin_in_charge_id` bigint NOT NULL DEFAULT '0',
  `note` text,
  `receiver_account_id` int DEFAULT NULL,
  `Delivery_fee` double DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `district_id` int NOT NULL DEFAULT '0',
  `ward_code` varchar(255) NOT NULL DEFAULT '0',
  PRIMARY KEY (`ID`),
  KEY `FK_customer_invoice` (`Customer_ID`),
  KEY `FK_staff_invoice` (`admin_in_charge_id`),
  KEY `receiver_account_id` (`receiver_account_id`),
  CONSTRAINT `FK_customer_invoice` FOREIGN KEY (`Customer_ID`) REFERENCES `customers` (`id`),
  CONSTRAINT `FK_staff_invoice` FOREIGN KEY (`admin_in_charge_id`) REFERENCES `staffs` (`id`),
  CONSTRAINT `invoice_ibfk_1` FOREIGN KEY (`receiver_account_id`) REFERENCES `online_payment_accounts` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `invoice`
--

LOCK TABLES `invoice` WRITE;
/*!40000 ALTER TABLE `invoice` DISABLE KEYS */;
INSERT INTO `invoice` VALUES (1,1,'2024-01-03 00:00:00.000000',NULL,'UNPAID','PACKING','ACCEPTED','COD',NULL,0,'USD',NULL,2402,NULL,1,'',NULL,2,'38B 257, , Hóc Môn, Thành phố Hồ Chí Minh',1459,'22212'),(2,1,'2024-01-03 00:00:00.000000','2024-01-03 14:35:26','PAID','PACKING','CONFIRMED_ONLINE_PAYMENT','BANK_TRANSFER',NULL,0,'USD',NULL,1701,NULL,1,'',2,1,'38B 257, , Hóc Môn, Thành phố Hồ Chí Minh',1459,'22212'),(3,29,'2024-01-03 00:00:00.000000',NULL,'UNPAID','PACKING','ACCEPTED','COD',NULL,0,'USD',NULL,352,NULL,1,'',NULL,2,'Đường tỉnh 743B, Phường Bình Hòa, Thành phố Thuận An, Tỉnh Bình Dương, Việt Nam',1541,'440404'),(4,29,'2024-01-03 00:00:00.000000',NULL,'UNPAID','PACKING','REFUSED','COD',NULL,0,'USD',NULL,1501,NULL,1,'',NULL,1,'Đường tỉnh 743B, Phường Bình Hòa, Thành phố Thuận An, Tỉnh Bình Dương, Việt Nam',1541,'440404'),(5,29,'2024-01-03 00:00:00.000000','2024-01-03 15:22:17','PAID','CUSTOMER_CANCEL','CONFIRMED_ONLINE_PAYMENT','MOMO','Customer cancels order before admin create shipping order, refund 50%',50,'USD',NULL,2338.5,NULL,1,'',1,1,'Đường tỉnh 743B, Phường Bình Hòa, Thành phố Thuận An, Tỉnh Bình Dương, Việt Nam',1541,'440404'),(6,29,'2024-01-03 00:00:00.000000','2024-01-03 15:23:48','PAID','SUCCESS','CONFIRMED_ONLINE_PAYMENT','BANK_TRANSFER',NULL,0,'USD',NULL,2126,NULL,1,'',2,1,'Đường tỉnh 743B, Phường Bình Hòa, Thành phố Thuận An, Tỉnh Bình Dương, Việt Nam',1541,'440404'),(7,29,'2024-01-03 00:00:00.000000','2024-01-03 16:09:38','PAID','FAILED','CONFIRMED_ONLINE_PAYMENT','MOMO',NULL,0,'USD',NULL,3857,NULL,1,'',1,2,'Đường tỉnh 743B, Phường Bình Hòa, Thành phố Thuận An, Tỉnh Bình Dương, Việt Nam',1541,'440404'),(8,1,'2024-01-04 00:00:00.000000','2024-01-04 01:34:42','PAID','SUCCESS','CONFIRMED_ONLINE_PAYMENT','BANK_TRANSFER',NULL,0,'USD',NULL,1701,NULL,1,'',2,1,'VQ2C+CRR, , Thủ Đức, Thành phố Hồ Chí Minh',3695,'90768'),(9,27,'2024-02-29 00:00:00.000000','2024-02-29 14:43:27','PAID','FINISH_PACKING','CONFIRMED_ONLINE_PAYMENT','BANK_TRANSFER',NULL,0,'USD',NULL,1701,NULL,1,'abc',2,1,'38B 257, , Hóc Môn, Thành phố Hồ Chí Minh',1459,'22212'),(10,27,'2024-02-29 00:00:00.000000',NULL,'UNPAID','FINISH_PACKING','ACCEPTED','COD',NULL,0,'USD',NULL,2402,NULL,1,'',NULL,2,'38B 257, , Hóc Môn, Thành phố Hồ Chí Minh',1459,'22212'),(11,30,'2024-03-03 00:00:00.000000',NULL,'UNPAID','PAYMENT_WAITING','PAYMENT_WAITING','MOMO',NULL,0,'USD',NULL,0,NULL,0,'',NULL,0,'65GM+WJ4, , , Khánh Hòa',3213,'410514'),(12,30,'2024-03-03 00:00:00.000000',NULL,'UNPAID','ACCEPTANCE_WAITING','ACCEPTANCE_WAITING','COD',NULL,0,'USD',NULL,4676,NULL,0,'',NULL,1,'65GM+WJ4, , , Khánh Hòa',3213,'410514'),(13,30,'2024-03-05 00:00:00.000000',NULL,'UNPAID','ACCEPTANCE_WAITING','ACCEPTANCE_WAITING','COD',NULL,0,'USD',NULL,2587,NULL,0,'',NULL,37,'10 Đ. Quảng Hàm, , , Khánh Hòa',3213,'410514'),(14,30,'2024-03-05 00:00:00.000000',NULL,'UNPAID','ACCEPTANCE_WAITING','ACCEPTANCE_WAITING','COD',NULL,0,'USD',NULL,0,NULL,0,'',NULL,0,'10 Đ. Quảng Hàm, , , Khánh Hòa',3213,'410514'),(15,30,'2024-03-05 00:00:00.000000',NULL,'UNPAID','PACKING','ACCEPTED','COD',NULL,0,'USD',NULL,0,NULL,1,'',NULL,0,'10 Đ. Quảng Hàm, , , Khánh Hòa',3213,'410514'),(16,30,'2024-03-08 00:00:00.000000',NULL,'UNPAID','PACKING','REFUSED','COD',NULL,0,'USD',NULL,2702,NULL,1,'',NULL,2,'79 Đường Mai Thị Dõng, , , Khánh Hòa',3213,'410514'),(17,34,'2024-03-13 00:00:00.000000',NULL,'UNPAID','ACCEPTANCE_WAITING','ACCEPTANCE_WAITING','COD',NULL,0,'USD',NULL,2126,NULL,0,'',NULL,1,'Đường chưa đặt tên, , , Khánh Hòa',3213,'410514'),(18,34,'2024-03-13 00:00:00.000000',NULL,'UNPAID','ACCEPTANCE_WAITING','ACCEPTANCE_WAITING','COD',NULL,0,'USD',NULL,0,NULL,0,'',NULL,0,'Đường chưa đặt tên, , , Khánh Hòa',3213,'410514');
/*!40000 ALTER TABLE `invoice` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Temporary view structure for view `invoice_with_products_info_for_ui`
--

DROP TABLE IF EXISTS `invoice_with_products_info_for_ui`;
/*!50001 DROP VIEW IF EXISTS `invoice_with_products_info_for_ui`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `invoice_with_products_info_for_ui` AS SELECT 
 1 AS `Invoice_ID`,
 1 AS `product_id`,
 1 AS `Product_Management_ID`,
 1 AS `customer_id`,
 1 AS `name`,
 1 AS `color`,
 1 AS `size`,
 1 AS `selling_price`,
 1 AS `discount`,
 1 AS `Quantity`,
 1 AS `image_1`*/;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `invoices_with_products`
--

DROP TABLE IF EXISTS `invoices_with_products`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `invoices_with_products` (
  `Invoice_ID` bigint NOT NULL,
  `Product_Management_ID` bigint NOT NULL,
  `Quantity` int NOT NULL,
  PRIMARY KEY (`Invoice_ID`,`Product_Management_ID`),
  KEY `FK_from_products_management_idx` (`Product_Management_ID`),
  CONSTRAINT `FK_from_invoice` FOREIGN KEY (`Invoice_ID`) REFERENCES `invoice` (`ID`),
  CONSTRAINT `FK_from_products_management` FOREIGN KEY (`Product_Management_ID`) REFERENCES `products_management` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `invoices_with_products`
--

LOCK TABLES `invoices_with_products` WRITE;
/*!40000 ALTER TABLE `invoices_with_products` DISABLE KEYS */;
INSERT INTO `invoices_with_products` VALUES (1,304,1),(2,303,1),(3,301,1),(4,278,1),(5,12,1),(6,286,1),(7,1,1),(7,278,2),(8,303,1),(9,303,1),(10,305,1),(12,12,2),(13,312,1),(16,309,2),(17,288,1);
/*!40000 ALTER TABLE `invoices_with_products` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `languages`
--

DROP TABLE IF EXISTS `languages`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `languages` (
  `id` int NOT NULL AUTO_INCREMENT,
  `language_code` varchar(10) NOT NULL,
  `name` varchar(50) NOT NULL,
  `flag_image` text,
  PRIMARY KEY (`id`),
  UNIQUE KEY `languagescol_UNIQUE` (`language_code`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `languages`
--

LOCK TABLES `languages` WRITE;
/*!40000 ALTER TABLE `languages` DISABLE KEYS */;
INSERT INTO `languages` VALUES (1,'aa','Afar','assets/image/afar.png'),(2,'ab','Abkhazian','assets/image/abkhazian.png'),(3,'de','German','assets/image/german.png'),(4,'zh','Chinese','assets/image/chinese.png'),(5,'da','Danish','assets/image/danish.jpeg'),(6,'fr','French','assets/image/french.png'),(7,'es','Spanish','assets/image/spanish.png'),(8,'it','Italian','assets/image/italian.png'),(9,'vi','Vietnamese','assets/image/vietnamese.png');
/*!40000 ALTER TABLE `languages` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `login_accounts`
--

DROP TABLE IF EXISTS `login_accounts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `login_accounts` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_name` varchar(50) NOT NULL,
  `password` text NOT NULL,
  `role` varchar(10) NOT NULL,
  `status` varchar(10) NOT NULL DEFAULT 'ALLOWED',
  `current_jwt` text,
  PRIMARY KEY (`id`),
  UNIQUE KEY `User_Name` (`user_name`)
) ENGINE=InnoDB AUTO_INCREMENT=75 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `login_accounts`
--

LOCK TABLES `login_accounts` WRITE;
/*!40000 ALTER TABLE `login_accounts` DISABLE KEYS */;
INSERT INTO `login_accounts` VALUES (0,'unknown','123','ADMIN','ALLOWED',NULL),(1,'admin','$2a$10$7jxw1kP1KDMTFzDEtWeDuOpKfzOmW0lmeRdYKsIKksX8wdZVGEtMe','ADMIN','ALLOWED','eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsImlhdCI6MTcxMjE2Mjg5MywiZXhwIjoxNzEyMjQ5MjkzfQ.EFvnaukjyMvz31V3feLQXJkeyEpdaNaiFpIA3Xxi_Zs'),(2,'user','$2a$10$2YZeENjsawuGzI5aqSxnzOUdVnkKV128kNvIiBeKss8FaFaMpDKNq','CUSTOMER','ALLOWED','eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1c2VyIiwiaWF0IjoxNzEzNDkxMDEyLCJleHAiOjE3MTM1Nzc0MTJ9.YQKJUnHgU9HWIty0gg8VBi8g9_ImxIgnhk_XQL1kiZI'),(3,'nhu0707','$2a$10$7jxw1kP1KDMTFzDEtWeDuOpKfzOmW0lmeRdYKsIKksX8wdZVGEtMe','CUSTOMER','ALLOWED',NULL),(7,'sang236','$2a$10$7jxw1kP1KDMTFzDEtWeDuOpKfzOmW0lmeRdYKsIKksX8wdZVGEtMe','CUSTOMER','ALLOWED',NULL),(8,'shipper','$2a$10$7jxw1kP1KDMTFzDEtWeDuOpKfzOmW0lmeRdYKsIKksX8wdZVGEtMe','SHIPPER','ALLOWED','eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJzaGlwcGVyIiwiaWF0IjoxNjkyODk3MTA1LCJleHAiOjE2OTI5ODM1MDV9.7so_xwVTJfYtuAXNgvhFYSQXZSDEQWxhHWJqUL9ds7s'),(9,'tester','$2a$10$7jxw1kP1KDMTFzDEtWeDuOpKfzOmW0lmeRdYKsIKksX8wdZVGEtMe','CUSTOMER','ALLOWED','eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0ZXIiLCJpYXQiOjE2OTI4MDI4MzMsImV4cCI6MTY5Mjg4OTIzM30.LLZJ_PKOBvcYv8SWoaOGMLW7USOvSYjIH7u-GIgKaSU'),(11,'qweqwe','qweqwe','CUSTOMER','ALLOWED',NULL),(12,'quochoang','123','CUSTOMER','ALLOWED',NULL),(13,'ducngu','123','CUSTOMER','BANNED',NULL),(50,'qhoang','123','CUSTOMER','ALLOWED',NULL),(51,'admin2','$2a$10$7jxw1kP1KDMTFzDEtWeDuOpKfzOmW0lmeRdYKsIKksX8wdZVGEtMe','ADMIN','ALLOWED','eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbjIiLCJpYXQiOjE3MDU5MDU0NDIsImV4cCI6MTcwNTk5MTg0Mn0.nSEvJXHKaclsctG8sKGpqbunun06-6wPnmhlsoSfgik'),(52,'kien','123','CUSTOMER','ALLOWED',NULL),(63,'user1','$2a$10$7jxw1kP1KDMTFzDEtWeDuOpKfzOmW0lmeRdYKsIKksX8wdZVGEtMe','CUSTOMER','ALLOWED','eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1c2VyMSIsImlhdCI6MTcwOTIxNzMyNCwiZXhwIjoxNzA5MzAzNzI0fQ.hVvjp5xhwB2VIV8bNDHDdRcyj_kzRV05r95j8EAbs2Q'),(64,'user3','$2a$10$es2Ds7Q3fdP8PEyYh24d5eSbDBSTeg787rtM6jgAtAu71ZGqGVpD6','CUSTOMER','ALLOWED',NULL),(65,'minhbeo123','$2a$10$YmTsLpwqrcljm7rK8DoF4OC5bPfP9jRD1ppcon5U61KhLZeKtAoBy','CUSTOMER','ALLOWED','eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJtaW5oYmVvMTIzIiwiaWF0IjoxNzExMzUzMTU0LCJleHAiOjE3MTE0Mzk1NTR9.Gx5HV4APaS1LYl5C_iLgPnOpj4Ow_A9d1PmB8AIpHgs'),(66,'sang','$2a$10$je0J1BWnD3MatwVNFxgJ/OLeAY3D2gY3fWkoghOpwdoKYxuK02Fby','CUSTOMER','ALLOWED','eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJzYW5nIiwiaWF0IjoxNzEyODMyMzcwLCJleHAiOjE3MTI5MTg3NzB9.Bs8-TqYd5v7zEm2zIMvWyk5meYiejhbCgVMXKyBqItY'),(67,'caovy2001.8','$2a$10$Zm8xlbRyIxfQT3oJGTtsqO9O1h0pp1tnIzG1gtVTfejnkSXE/nqFG','CUSTOMER','ALLOWED',NULL),(68,'hdt','$2a$10$saCa5qaLGssQ1q.Ie6e6/OlJ47dJhXRKJ7yCRGYsszttwTSFX0M6y','CUSTOMER','ALLOWED','eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJoZHQiLCJpYXQiOjE3MTAzMzU3NjgsImV4cCI6MTcxMDQyMjE2OH0.b5xRFgjUb1vpg3Ce_0BVjENe6hSsXXsSfEjmpK-99sM'),(69,'tuan','$2a$10$mexh4Vcky/9G.XNIOn3IW.GJmtXZ7S9B70PP0ib6ubBdqPhYutIae','CUSTOMER','ALLOWED','eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0dWFuIiwiaWF0IjoxNzEyMzIyMzMwLCJleHAiOjE3MTI0MDg3MzB9.NTfK9fbUwCyQnVmnBCP7QdG1UPhqTbsnaFQvXTWyBzI'),(70,'Đức','$2a$10$P/KCKTNSjtTlGWOHDWcfLO7UAiDedU5Vsrde7M9DpYNLpWQLXc49O','CUSTOMER','ALLOWED','eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiLEkOG7qWMiLCJpYXQiOjE3MTAzMTc1MTYsImV4cCI6MTcxMDQwMzkxNn0.Q6HD6FHbx1WuXcvZayWmTxj1wKPmYpiKuvGhJDqFvUc'),(73,'user4','$2a$10$UcdpmXrtgP.I8aqBNy6FguwSW.H8gv2XRTZTIheBqHXYiFBT690z2','CUSTOMER','ALLOWED',NULL),(74,'phuc','$2a$10$dRyq3p7PXQRC1WItPPee2.EIX3zqnw/Dj9RyoV55pfTyM4YtYvWhG','CUSTOMER','ALLOWED','eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJwaHVjIiwiaWF0IjoxNzExMzQ4OTgwLCJleHAiOjE3MTE0MzUzODB9.A_hyjpFkQ767VO6_V-XevyRntVUs7MmOdTX6BE7bqUo');
/*!40000 ALTER TABLE `login_accounts` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Temporary view structure for view `new_arrival_products`
--

DROP TABLE IF EXISTS `new_arrival_products`;
/*!50001 DROP VIEW IF EXISTS `new_arrival_products`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `new_arrival_products` AS SELECT 
 1 AS `id`,
 1 AS `product_id`,
 1 AS `name`,
 1 AS `size`,
 1 AS `selling_price`,
 1 AS `discount`,
 1 AS `brand`,
 1 AS `color`,
 1 AS `available_quantity`,
 1 AS `image_1`,
 1 AS `image_2`,
 1 AS `image_3`,
 1 AS `image_4`,
 1 AS `overall_rating`,
 1 AS `description`*/;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `notification`
--

DROP TABLE IF EXISTS `notification`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `notification` (
  `ID` int NOT NULL AUTO_INCREMENT,
  `title` varchar(255) DEFAULT NULL,
  `content` text NOT NULL,
  `additional_data` text,
  `notification_date` datetime DEFAULT NULL,
  `topic` varchar(255) DEFAULT NULL,
  `seen` bit(1) NOT NULL DEFAULT b'0',
  `receiver_login_account_id` int DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=55 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `notification`
--

LOCK TABLES `notification` WRITE;
/*!40000 ALTER TABLE `notification` DISABLE KEYS */;
INSERT INTO `notification` VALUES (1,'New order','A new order has been created','{paymentMethod=COD, invoiceId=1, paymentStatus=UNPAID}','2024-01-03 14:22:31','admin',_binary '\0',NULL,NULL),(2,'Your order\'s process','Your order has been accepted','{paymentMethod=COD, invoiceId=1, paymentStatus=UNPAID}','2024-01-03 14:24:11','user',_binary '\0',NULL,NULL),(3,'New order','A new order has been created','{paymentMethod=BANK_TRANSFER, invoiceId=2, paymentStatus=UNPAID}','2024-01-03 14:34:44','admin',_binary '\0',NULL,NULL),(4,'Your order\'s process','We have received your payment','{paymentMethod=BANK_TRANSFER, invoiceId=2, paymentStatus=PAID}','2024-01-03 14:35:27','user',_binary '',NULL,NULL),(5,'New order','A new order has been created','{paymentMethod=COD, invoiceId=3, paymentStatus=UNPAID}','2024-01-03 15:20:10','admin',_binary '',NULL,NULL),(6,'Your order\'s process','Your order has been accepted','{paymentMethod=COD, invoiceId=3, paymentStatus=UNPAID}','2024-01-03 15:20:29','minhbeo123',_binary '\0',NULL,NULL),(7,'New order','A new order has been created','{paymentMethod=COD, invoiceId=4, paymentStatus=UNPAID}','2024-01-03 15:21:12','admin',_binary '',NULL,NULL),(8,'Your order\'s process','Sorry, we have to refuse your order','{paymentMethod=COD, invoiceId=4, paymentStatus=UNPAID}','2024-01-03 15:21:34','minhbeo123',_binary '\0',NULL,NULL),(9,'New order','A new order has been created','{paymentMethod=MOMO, invoiceId=5, paymentStatus=UNPAID}','2024-01-03 15:22:00','admin',_binary '\0',NULL,NULL),(10,'Your order\'s process','We have received your payment','{paymentMethod=MOMO, invoiceId=5, paymentStatus=PAID}','2024-01-03 15:22:18','minhbeo123',_binary '\0',NULL,NULL),(11,'Order Management','A customer has canceled an order','{paymentMethod=MOMO, invoiceId=5, paymentStatus=PAID}','2024-01-03 15:22:33','admin',_binary '\0',NULL,NULL),(12,'Your order\'s process','We has refunded your order, please checkout your account','{invoiceId=5}','2024-01-03 15:23:02','minhbeo123',_binary '\0',NULL,NULL),(13,'New order','A new order has been created','{paymentMethod=BANK_TRANSFER, invoiceId=6, paymentStatus=UNPAID}','2024-01-03 15:23:33','admin',_binary '\0',NULL,NULL),(14,'Your order\'s process','We have received your payment','{paymentMethod=BANK_TRANSFER, invoiceId=6, paymentStatus=PAID}','2024-01-03 15:23:49','minhbeo123',_binary '\0',NULL,NULL),(15,'Your order\'s process','We have finished packing your order','{paymentMethod=BANK_TRANSFER, invoiceId=6, paymentStatus=PAID}','2024-01-03 15:23:56','minhbeo123',_binary '\0',NULL,NULL),(16,'Your order\'s process','We are shipping your order','{paymentMethod=BANK_TRANSFER, invoiceId=6, paymentStatus=PAID}','2024-01-03 15:24:03','minhbeo123',_binary '\0',NULL,NULL),(17,'Your order\'s process','Your order has been success, have a good day!','{paymentMethod=BANK_TRANSFER, invoiceId=6, paymentStatus=PAID}','2024-01-03 15:24:12','minhbeo123',_binary '\0',NULL,NULL),(18,'New order','A new order has been created','{paymentMethod=MOMO, invoiceId=7, paymentStatus=UNPAID}','2024-01-03 16:07:21','admin',_binary '\0',NULL,NULL),(19,'Your order\'s process','We have received your payment','{paymentMethod=MOMO, invoiceId=7, paymentStatus=PAID}','2024-01-03 16:09:39','minhbeo123',_binary '\0',NULL,NULL),(20,'Your order\'s process','We have finished packing your order','{paymentMethod=MOMO, invoiceId=7, paymentStatus=PAID}','2024-01-03 16:10:17','minhbeo123',_binary '\0',NULL,NULL),(21,'Your order\'s process','We are shipping your order','{paymentMethod=MOMO, invoiceId=7, paymentStatus=PAID}','2024-01-03 16:10:33','minhbeo123',_binary '\0',NULL,NULL),(22,'Your order\'s process','Your order has been failed','{paymentMethod=MOMO, invoiceId=7, paymentStatus=PAID}','2024-01-03 16:13:06','minhbeo123',_binary '\0',NULL,NULL),(23,'New order','A new order has been created','{paymentMethod=BANK_TRANSFER, invoiceId=8, paymentStatus=UNPAID}','2024-01-04 01:33:18','admin',_binary '\0',NULL,NULL),(24,'Your order\'s process','We have received your payment','{paymentMethod=BANK_TRANSFER, invoiceId=8, paymentStatus=PAID}','2024-01-04 01:34:42','user',_binary '',NULL,NULL),(25,'Your order\'s process','We have finished packing your order','{paymentMethod=BANK_TRANSFER, invoiceId=8, paymentStatus=PAID}','2024-01-04 01:35:08','user',_binary '',NULL,NULL),(26,'Your order\'s process','We are shipping your order','{paymentMethod=BANK_TRANSFER, invoiceId=8, paymentStatus=PAID}','2024-01-04 01:35:55','user',_binary '',NULL,NULL),(27,'Your order\'s process','Your order has been success, have a good day!','{paymentMethod=BANK_TRANSFER, invoiceId=8, paymentStatus=PAID}','2024-01-04 01:36:28','user',_binary '',NULL,NULL),(28,'New order','A new order has been created','{paymentMethod=BANK_TRANSFER, invoiceId=9, paymentStatus=UNPAID}','2024-02-29 14:39:55','admin',_binary '\0',NULL,NULL),(29,'Your order\'s process','We have received your payment','{paymentMethod=BANK_TRANSFER, invoiceId=9, paymentStatus=PAID}','2024-02-29 14:43:28','user1',_binary '\0',NULL,NULL),(30,'New order','A new order has been created','{paymentMethod=COD, invoiceId=10, paymentStatus=UNPAID}','2024-02-29 14:51:03','admin',_binary '',NULL,NULL),(31,'Your order\'s process','Your order has been accepted','{paymentMethod=COD, invoiceId=10, paymentStatus=UNPAID}','2024-02-29 14:51:49','user1',_binary '\0',NULL,NULL),(32,'Your order\'s process','We have finished packing your order','{paymentMethod=BANK_TRANSFER, invoiceId=9, paymentStatus=PAID}','2024-02-29 15:33:13','user1',_binary '\0',NULL,NULL),(33,'Your order\'s process','We have finished packing your order','{paymentMethod=COD, invoiceId=10, paymentStatus=UNPAID}','2024-02-29 15:57:00','user1',_binary '\0',NULL,NULL),(34,'New order','A new order has been created','{paymentMethod=COD, invoiceId=12, paymentStatus=UNPAID}','2024-03-03 06:52:07','admin',_binary '',NULL,NULL),(35,'New order','A new order has been created','{paymentMethod=COD, invoiceId=13, paymentStatus=UNPAID}','2024-03-05 07:21:24','admin',_binary '',NULL,NULL),(36,'Your order\'s process','Your order has been accepted','{paymentMethod=COD, invoiceId=15, paymentStatus=UNPAID}','2024-03-05 07:27:58','Hong123',_binary '',NULL,NULL),(37,'New order','A new order has been created','{paymentMethod=COD, invoiceId=16, paymentStatus=UNPAID}','2024-03-08 09:10:44','admin',_binary '',NULL,NULL),(38,'Your order\'s process','Sorry, we have to refuse your order','{paymentMethod=COD, invoiceId=16, paymentStatus=UNPAID}','2024-03-08 09:12:16','Hong123',_binary '\0',NULL,NULL),(39,'New order','A new order has been created','{paymentMethod=COD, invoiceId=17, paymentStatus=UNPAID}','2024-03-13 07:55:59','admin',_binary '',NULL,NULL),(40,'Noti title','Noti content here...','{data1=ggsdgsdg, data2=dfgdf gdf}','2024-03-30 04:29:42','/topics/all',_binary '\0',NULL,NULL),(41,'Noti title','Noti content here...','{data1=ggsdgsdg, data2=dfgdf gdf}','2024-03-30 05:00:37','/topics/all',_binary '\0',NULL,NULL),(42,'Noti title','Noti content here...','{data1=ggsdgsdg, data2=dfgdf gdf}','2024-04-01 09:09:26','/topics/all',_binary '\0',NULL,NULL),(43,'Noti title','Noti content here...','{data1=ggsdgsdg, data2=dfgdf gdf}','2024-04-01 09:09:53','/topics/all',_binary '\0',NULL,NULL),(44,'Noti title','Noti content here...','{data1=ggsdgsdg, data2=dfgdf gdf}','2024-04-03 04:45:25','/topics/all',_binary '\0',NULL,NULL),(45,'Noti title','Noti content here...','{data1=ggsdgsdg, data2=dfgdf gdf}','2024-04-04 07:56:42','/topics/all',_binary '\0',NULL,NULL),(46,'Noti title','Noti content here...','{data1=ggsdgsdg, data2=dfgdf gdf}','2024-04-05 05:11:10','/topics/all',_binary '\0',NULL,NULL),(47,'Noti title','Noti content here...','{data1=ggsdgsdg, data2=dfgdf gdf}','2024-04-05 07:22:24','/topics/all',_binary '\0',NULL,NULL),(48,'Noti title','Noti content here...','{data1=ggsdgsdg, data2=dfgdf gdf}','2024-04-05 07:23:52','/topics/all',_binary '\0',NULL,NULL),(49,'Noti title','Noti content here...','{data1=ggsdgsdg, data2=dfgdf gdf}','2024-04-05 07:32:38','/topics/all',_binary '\0',NULL,NULL),(50,'Noti title','Noti content here...','{data1=ggsdgsdg, data2=dfgdf gdf}','2024-04-05 07:34:16','/topics/all',_binary '\0',NULL,NULL),(51,'Noti title','Noti content here...','{data1=ggsdgsdg, data2=dfgdf gdf}','2024-04-05 07:35:09','/topics/all',_binary '\0',NULL,NULL),(52,'Noti title','Noti content here...','{data1=ggsdgsdg, data2=dfgdf gdf}','2024-04-05 07:37:07','/topics/all',_binary '\0',NULL,NULL),(53,'Noti title','Noti content here...','{data1=ggsdgsdg, data2=dfgdf gdf}','2024-04-05 07:38:12','/topics/all',_binary '\0',NULL,NULL),(54,'Noti title','Noti content here...','{data1=ggsdgsdg, data2=dfgdf gdf}','2024-04-05 08:20:06','/topics/all',_binary '\0',NULL,NULL);
/*!40000 ALTER TABLE `notification` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `online_payment_accounts`
--

DROP TABLE IF EXISTS `online_payment_accounts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `online_payment_accounts` (
  `id` int NOT NULL,
  `receiver_account` varchar(100) NOT NULL,
  `receiver_name` varchar(50) NOT NULL,
  `additional_info` varchar(50) DEFAULT NULL,
  `type` varchar(20) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `receiver_account` (`receiver_account`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `online_payment_accounts`
--

LOCK TABLES `online_payment_accounts` WRITE;
/*!40000 ALTER TABLE `online_payment_accounts` DISABLE KEYS */;
INSERT INTO `online_payment_accounts` VALUES (1,'0977815809','NGUYEN HOANG SANG','','MOMO'),(2,'03365672301','NGUYEN HOANG SANG','TpBank','BANK_TRANSFER');
/*!40000 ALTER TABLE `online_payment_accounts` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Temporary view structure for view `product_full_info_for_ui`
--

DROP TABLE IF EXISTS `product_full_info_for_ui`;
/*!50001 DROP VIEW IF EXISTS `product_full_info_for_ui`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `product_full_info_for_ui` AS SELECT 
 1 AS `id`,
 1 AS `product_id`,
 1 AS `name`,
 1 AS `size`,
 1 AS `selling_price`,
 1 AS `original_price`,
 1 AS `discount`,
 1 AS `brand`,
 1 AS `color`,
 1 AS `available_quantity`,
 1 AS `image_1`,
 1 AS `image_2`,
 1 AS `image_3`,
 1 AS `image_4`,
 1 AS `overall_rating`,
 1 AS `description`*/;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `product_images_management`
--

DROP TABLE IF EXISTS `product_images_management`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product_images_management` (
  `product_id` bigint NOT NULL,
  `color` varchar(20) NOT NULL,
  `image_1` text,
  `image_2` text,
  `image_3` text,
  `image_4` text,
  PRIMARY KEY (`product_id`,`color`),
  CONSTRAINT `FK_Product_Images_Management_Products` FOREIGN KEY (`product_id`) REFERENCES `products` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product_images_management`
--

LOCK TABLES `product_images_management` WRITE;
/*!40000 ALTER TABLE `product_images_management` DISABLE KEYS */;
INSERT INTO `product_images_management` VALUES (1,'red','https://assets.adidas.com/images/h_840,f_auto,q_auto,fl_lossy,c_fill,g_auto/0a338a8c8da54898a0bb3b3713ade4a8_9366/Ao_DJau_San_Nha_Manchester_United_23-24_DJo_IP1726_HM1.jpg','https://assets.adidas.com/images/h_840,f_auto,q_auto,fl_lossy,c_fill,g_auto/7369f0c03502466abd365764cf7496d6_9366/Ao_DJau_San_Nha_Manchester_United_23-24_DJo_IP1726_HM3_hover.jpg','https://assets.adidas.com/images/h_840,f_auto,q_auto,fl_lossy,c_fill,g_auto/3ddf0598be9a41c58dbcbe131d6edef0_9366/Ao_DJau_San_Nha_Manchester_United_23-24_DJo_IP1726_HM4.jpg','https://assets.adidas.com/images/h_840,f_auto,q_auto,fl_lossy,c_fill,g_auto/c64eef7b97934addb7a9719da3f10e35_9366/Ao_DJau_San_Nha_Manchester_United_23-24_DJo_IP1726_HM5.jpg'),(1,'white','https://assets.adidas.com/images/h_840,f_auto,q_auto,fl_lossy,c_fill,g_auto/f74783ba556244849a2f8dbb1bae3c82_9366/Ao_DJau_Thu_Ba_Manchester_United_23-24_trang_IP1741_HM1.jpg','https://assets.adidas.com/images/h_840,f_auto,q_auto,fl_lossy,c_fill,g_auto/586a4c880ebf420da6ed52d4cc0adb33_9366/Ao_DJau_Thu_Ba_Manchester_United_23-24_trang_IP1741_HM3_hover.jpg','https://assets.adidas.com/images/h_840,f_auto,q_auto,fl_lossy,c_fill,g_auto/8b01596a2d4a4aa3baa3d7360c2fe479_9366/Ao_DJau_Thu_Ba_Manchester_United_23-24_trang_IP1741_HM4.jpg','https://assets.adidas.com/images/h_840,f_auto,q_auto,fl_lossy,c_fill,g_auto/1e18efba065f42528412d4fe35b15519_9366/Ao_DJau_Thu_Ba_Manchester_United_23-24_trang_IP1741_HM5.jpg'),(2,'black','https://media.gucci.com/style/HEXEAF2DC_Center_0_0_800x800/1653583557/699268_UXX0G_1000_003_100_0000_Light-Gucci-Blondie-shoulder-bag.jpg','https://media.gucci.com/style/HEXEAF2DC_Center_0_0_800x800/1652714104/699268_UXX0G_1000_001_073_0000_Light-Gucci-Blondie-shoulder-bag.jpg','https://media.gucci.com/style/HEXEAF2DC_Center_0_0_800x800/1652714105/699268_UXX0G_1000_002_073_0000_Light-Gucci-Blondie-shoulder-bag.jpg','https://media.gucci.com/style/HEXEAF2DC_Center_0_0_800x800/1653583559/699268_UXX0G_1000_005_100_0000_Light-Gucci-Blondie-shoulder-bag.jpg'),(2,'pink','https://media.gucci.com/style/HEXF1E9FB_Center_0_0_800x800/1670024750/699268_UXX0G_6910_004_100_0000_Light-Gucci-Blondie-shoulder-bag.jpg','https://media.gucci.com/style/HEXF1E9FB_Center_0_0_800x800/1675811856/699268_UXX0G_6910_006_100_0000_Light-Gucci-Blondie-shoulder-bag.jpg','https://media.gucci.com/style/HEXF1E9FB_Center_0_0_800x800/1674688592/699268_UXX0G_6910_007_073_0000_Light-Gucci-Blondie-shoulder-bag.jpg','https://media.gucci.com/style/HEXF1E9FB_Center_0_0_800x800/1674688596/699268_UXX0G_6910_009_073_0000_Light-Gucci-Blondie-shoulder-bag.jpg'),(3,'none','https://media.gucci.com/style/HEXF1E9FB_Center_0_0_800x800/1669920431/724770_XKCWH_8050_002_100_0000_Light-Striped-jacquard-wool-knit-sweater.jpg','https://media.gucci.com/style/HEXF1E9FB_Center_0_0_800x800/1669920428/724770_XKCWH_8050_001_100_0000_Light-Striped-jacquard-wool-knit-sweater.jpg','https://media.gucci.com/style/HEXF1E9FB_Center_0_0_800x800/1669920433/724770_XKCWH_8050_003_100_0000_Light-Striped-jacquard-wool-knit-sweater.jpg','https://media.gucci.com/style/HEXF1E9FB_Center_0_0_800x800/1669920435/724770_XKCWH_8050_005_100_0000_Light-Striped-jacquard-wool-knit-sweater.jpg'),(4,'none','https://static.nike.com/a/images/t_PDP_864_v1/f_auto,b_rgb:f5f5f5/029d86dd-1549-4221-a18b-25d165998d1f/air-max-90-se-shoes-ltJLHs.png','https://static.nike.com/a/images/t_PDP_864_v1/f_auto,b_rgb:f5f5f5/eceb6b53-06fc-4182-a450-b16620dc5295/air-max-90-se-shoes-ltJLHs.png','https://static.nike.com/a/images/t_PDP_864_v1/f_auto,b_rgb:f5f5f5/4bb1f419-b093-4d53-b120-5607311bafeb/air-max-90-se-shoes-ltJLHs.png','https://static.nike.com/a/images/t_PDP_864_v1/f_auto,b_rgb:f5f5f5/92d5efd4-727c-4a43-93ac-14a3099591dc/air-max-90-se-shoes-ltJLHs.png'),(5,'none','https://www.dolcegabbana.com/dw/image/v2/BKDB_PRD/on/demandware.static/-/Sites-15/default/dwb6f5c455/images/zoom/F6GAVTFSA56_HN4YA_0.jpg','https://www.dolcegabbana.com/dw/image/v2/BKDB_PRD/on/demandware.static/-/Sites-15/default/dw8d1d97c3/images/zoom/F6GAVTFSA56_HN4YA_1.jpg','https://www.dolcegabbana.com/dw/image/v2/BKDB_PRD/on/demandware.static/-/Sites-15/default/dw1947d070/images/zoom/F6GAVTFSA56_HN4YA_2.jpg','https://wwwhttps://www.dolcegabbana.com/dw/image/v2/BKDB_PRD/on/demandware.static/-/Sites-15/default/dw30454cfa/images/zoom/F6GAVTFSA56_HN4YA_3.jpg.dolcegabbana.com/dw/image/v2/AAGA_PRD/on/demandware.static/-/Sites-15/default/dwf8349ed3/images/zoom/F6R8DTFUGOI_N0000_11.jpg?sw=742&sh=944&sm=fit'),(6,'none','https://static.nike.com/a/images/t_PDP_864_v1/f_auto,b_rgb:f5f5f5/b75092c8-5853-4d15-bd57-c6dca886dd57/jordan-flight-mvp-fleece-pullover-hoodie-Z7kcns.png','https://static.nike.com/a/images/t_PDP_864_v1/f_auto,b_rgb:f5f5f5/5f7394a7-8c5e-4f5f-9782-40b932a93c36/jordan-flight-mvp-fleece-pullover-hoodie-Z7kcns.png','https://static.nike.com/a/images/t_PDP_864_v1/f_auto,b_rgb:f5f5f5/9a235f1a-fcfa-4c56-85ec-53225a27d5e6/jordan-flight-mvp-fleece-pullover-hoodie-Z7kcns.png','https://static.nike.com/a/images/t_PDP_864_v1/f_auto,b_rgb:f5f5f5/07c47509-10dd-45d5-b7ea-1ef57b8e4aea/jordan-flight-mvp-fleece-pullover-hoodie-Z7kcns.png'),(7,'red','https://www.chanel.com/images//t_one///q_auto:good,f_auto,fl_lossy,dpr_1.2/w_620/large-back-pack-chanel-22-red-white-shiny-calfskin-gold-tone-metal-shiny-calfskin-gold-tone-metal--packshot-artistique-vue1-as3313b09981nm243-9516416729118.jpg','https://www.chanel.com/images//t_one///q_auto:good,f_auto,fl_lossy,dpr_1.2/w_620/large-back-pack-chanel-22-red-white-shiny-calfskin-gold-tone-metal-shiny-calfskin-gold-tone-metal--packshot-artistique-vue2-as3313b09981nm243-9516416794654.jpg','https://www.chanel.com/images//t_one///q_auto:good,f_auto,fl_lossy,dpr_1.2/w_620/large-back-pack-chanel-22-red-white-shiny-calfskin-gold-tone-metal-shiny-calfskin-gold-tone-metal--packshot-artistique-vue3-as3313b09981nm243-9516424790046.jpg','https://www.chanel.com/images//t_one///q_auto:good,f_auto,fl_lossy,dpr_1.2/w_620/large-back-pack-chanel-22-red-white-shiny-calfskin-gold-tone-metal-shiny-calfskin-gold-tone-metal--packshot-artistique-vue4-as3313b09981nm243-9516430884894.jpg'),(7,'white','https://www.chanel.com/images//t_one///q_auto:good,f_auto,fl_lossy,dpr_1.2/w_620/large-back-pack-chanel-22-light-blue-shiny-calfskin-gold-tone-metal-shiny-calfskin-gold-tone-metal--packshot-artistique-vue1-as3313b08037nn268-9521332944926.jpg','https://www.chanel.com/images//t_one///q_auto:good,f_auto,fl_lossy,dpr_1.2/w_620/large-back-pack-chanel-22-light-blue-shiny-calfskin-gold-tone-metal-shiny-calfskin-gold-tone-metal--packshot-artistique-vue2-as3313b08037nn268-9521345888286.jpg','https://www.chanel.com/images//t_one///q_auto:good,f_auto,fl_lossy,dpr_1.2/w_620/large-back-pack-chanel-22-light-blue-shiny-calfskin-gold-tone-metal-shiny-calfskin-gold-tone-metal--packshot-artistique-vue3-as3313b08037nn268-9521535418398.jpg','https://www.chanel.com/images//t_one///q_auto:good,f_auto,fl_lossy,dpr_1.2/w_620/large-back-pack-chanel-22-light-blue-shiny-calfskin-gold-tone-metal-shiny-calfskin-gold-tone-metal--packshot-artistique-vue4-as3313b08037nn268-9521547345950.jpg'),(8,'green','https://www.chanel.com/images//t_one/t_fashion9//b_rgb:F7F7F7,e_brightness:-3/q_auto:good,f_auto,fl_lossy,dpr_1.2/w_620/butterfly-eyeglasses-dark-green-gold-acetate-glass-pearls-acetate-glass-pearls-packshot-default-a75267x08101v1702-9518592688158.jpg','https://www.chanel.com/images//t_one/t_fashion9//b_rgb:F7F7F7,e_brightness:-3/q_auto:good,f_auto,fl_lossy,dpr_1.2/w_620/butterfly-eyeglasses-dark-green-gold-acetate-glass-pearls-acetate-glass-pearls-packshot-alternative-a75267x08101v1702-9518591803422.jpg','https://www.chanel.com/images//t_one/t_fashion9//b_rgb:F7F7F7,e_brightness:-3/q_auto:good,f_auto,fl_lossy,dpr_1.2/w_620/butterfly-eyeglasses-dark-green-gold-acetate-glass-pearls-acetate-glass-pearls-packshot-other-a75267x08101v1702-9518592491550.jpg','https://www.chanel.com/images//t_one/t_fashion9//b_rgb:F7F7F7,e_brightness:-3/q_auto:good,f_auto,fl_lossy,dpr_1.2/w_620/butterfly-eyeglasses-dark-green-gold-acetate-glass-pearls-acetate-glass-pearls-packshot-extra-a75267x08101v1702-9518590033950.jpg'),(8,'red','https://www.chanel.com/images//t_one/t_fashion9//b_rgb:F7F7F7,e_brightness:-3/q_auto:good,f_auto,fl_lossy,dpr_1.2/w_620/butterfly-eyeglasses-brown-gold-acetate-glass-pearls-acetate-glass-pearls-packshot-default-a75267x08101v1722-9518588788766.jpg','https://www.chanel.com/images//t_one/t_fashion9//b_rgb:F7F7F7,e_brightness:-3/q_auto:good,f_auto,fl_lossy,dpr_1.2/w_620/butterfly-eyeglasses-brown-gold-acetate-glass-pearls-acetate-glass-pearls-packshot-alternative-a75267x08101v1722-9518587379742.jpg','https://www.chanel.com/images//t_one/t_fashion9//b_rgb:F7F7F7,e_brightness:-3/q_auto:good,f_auto,fl_lossy,dpr_1.2/w_620/butterfly-eyeglasses-brown-gold-acetate-glass-pearls-acetate-glass-pearls-packshot-other-a75267x08101v1722-9518584725534.jpg','https://www.chanel.com/images//t_one/t_fashion9//b_rgb:F7F7F7,e_brightness:-3/q_auto:good,f_auto,fl_lossy,dpr_1.2/w_620/butterfly-eyeglasses-brown-gold-acetate-glass-pearls-acetate-glass-pearls-packshot-extra-a75267x08101v1722-9518591737886.jpg'),(8,'white','https://www.chanel.com/images//t_one/t_fashion9//b_rgb:F7F7F7,e_brightness:-3/q_auto:good,f_auto,fl_lossy,dpr_1.2/w_620/butterfly-eyeglasses-dark-beige-gold-acetate-glass-pearls-acetate-glass-pearls-packshot-default-a75267x08101v1719-9518590099486.jpg','https://www.chanel.com/images//t_one/t_fashion9//b_rgb:F7F7F7,e_brightness:-3/q_auto:good,f_auto,fl_lossy,dpr_1.2/w_620/butterfly-eyeglasses-dark-beige-gold-acetate-glass-pearls-acetate-glass-pearls-packshot-alternative-a75267x08101v1719-9518592819230.jpg','https://www.chanel.com/images//t_one/t_fashion9//b_rgb:F7F7F7,e_brightness:-3/q_auto:good,f_auto,fl_lossy,dpr_1.2/w_620/butterfly-eyeglasses-dark-beige-gold-acetate-glass-pearls-acetate-glass-pearls-packshot-other-a75267x08101v1719-9518590263326.jpg','https://www.chanel.com/images//t_one/t_fashion9//b_rgb:F7F7F7,e_brightness:-3/q_auto:good,f_auto,fl_lossy,dpr_1.2/w_620/butterfly-eyeglasses-dark-beige-gold-acetate-glass-pearls-acetate-glass-pearls-packshot-extra-a75267x08101v1719-9518587871262.jpg'),(41,'none','https://assets.adidas.com/images/h_840,f_auto,q_auto,fl_lossy,c_fill,g_auto/b06cfa7d7da84a01bee7cd847a6f7fed_9366/Dep_ZPLAASH_DJo_IE5762_01_standard.jpg','https://assets.adidas.com/images/h_840,f_auto,q_auto,fl_lossy,c_fill,g_auto/7951c5cff06349598ad90ddc4efe6b20_9366/Dep_ZPLAASH_DJo_IE5762_02_standard_hover.jpg','https://assets.adidas.com/images/h_840,f_auto,q_auto,fl_lossy,c_fill,g_auto/64e7dd20877348138b35a595b95889e5_9366/Dep_ZPLAASH_DJo_IE5762_03_standard.jpg','https://assets.adidas.com/images/h_840,f_auto,q_auto,fl_lossy,c_fill,g_auto/f0515fc237434a91b1ee8bfa111b280b_9366/Dep_ZPLAASH_DJo_IE5762_04_standard.jpg'),(42,'white','https://balenciaga.dam.kering.com/m/175c83bea0c0ccf9/Medium-781245TQVA11561_G.jpg?v=1','https://balenciaga.dam.kering.com/m/eb10a8ce2f2f9e8/Medium-781245TQVA11561_I.jpg?v=1','https://balenciaga.dam.kering.com/m/ccd53b1d1b805bb/Medium-781245TQVA11561_H.jpg?v=1','https://balenciaga.dam.kering.com/m/57810e574db6af0b/Medium-781245TQVA11561_J.jpg?v=1'),(43,'black','https://balenciaga.dam.kering.com/m/8fccf840ca2a649/Medium-7706912AAUR1000_G.jpg?v=2','https://balenciaga.dam.kering.com/m/6d40590d64db300c/Medium-7706912AAUR1000_F.jpg?v=1','https://balenciaga.dam.kering.com/m/1f7c8f63d2deeb99/Medium-7706912AAUR1000_J.jpg?v=1','https://balenciaga.dam.kering.com/m/39bdf6aeb6f455b0/Medium-7706912AAUR1000_I.jpg?v=1'),(44,'none','https://balenciaga.dam.kering.com/m/291ddf6bc2190d43/Medium-536737W2TST1249_F.jpg?v=2','https://balenciaga.dam.kering.com/m/2c951b3a98445914/Medium-536737W2TST1249_J.jpg?v=2','https://balenciaga.dam.kering.com/m/29ae5183a903a6d/Medium-536737W2TST1249_I.jpg?v=2','https://balenciaga.dam.kering.com/m/4e08129ef2d180b1/Medium-536737W2TST1249_L.jpg?v=2'),(45,'none','https://balenciaga.dam.kering.com/m/7d17c6b8019e99da/Medium-770281TZ7921106_F.jpg?v=2','https://balenciaga.dam.kering.com/m/2a5516c5a4a301bb/Medium-770281TZ7921106_J.jpg?v=1','https://balenciaga.dam.kering.com/m/29732e0263887591/Medium-770281TZ7921106_B.jpg?v=1','https://balenciaga.dam.kering.com/m/575294ad08fb6cac/Medium-770281TZ7921106_G.jpg?v=1'),(46,'black','https://balenciaga.dam.kering.com/m/2e0266f372b8cdab/Large-748022TBP471363_G.jpg?v=1','https://balenciaga.dam.kering.com/m/50ac1ca6bc281a78/Large-748022TBP471363_I.jpg?v=1','https://balenciaga.dam.kering.com/m/7ba0d953b9ba182c/Large-748022TBP471363_H.jpg?v=1','https://balenciaga.dam.kering.com/m/2fac6154e38b5957/Large-748022TBP471363_F.jpg?v=1'),(47,'none','https://static.nike.com/a/images/t_PDP_1728_v1/f_auto,q_auto:eco/f8f86f1d-1deb-436b-80ed-84591c436c9d/air-force-1-07-shoes-dpNPj1.png','https://static.nike.com/a/images/t_PDP_1728_v1/f_auto,q_auto:eco/6f34922c-3ce7-4efd-9af9-6e3ca5dc20ab/air-force-1-07-shoes-dpNPj1.png','https://static.nike.com/a/images/t_PDP_1728_v1/f_auto,q_auto:eco/efb9a10f-c4cd-4d01-983a-53bab3285a1a/air-force-1-07-shoes-dpNPj1.png','https://static.nike.com/a/images/t_PDP_1728_v1/f_auto,q_auto:eco/f1e9c861-1e4c-4236-aab8-125a8b73cd08/air-force-1-07-shoes-dpNPj1.png'),(48,'black','https://static.nike.com/a/images/t_default/c0a88a0d-1335-4cd8-bd5a-def35951b006/heritage-cross-body-bag-Qf2sqq.png','https://static.nike.com/a/images/t_default/94ec6309-ff9a-45f2-9a34-7090a939dabb/heritage-cross-body-bag-Qf2sqq.png','https://static.nike.com/a/images/t_default/bd7b9964-17e2-4ce4-87e6-adb4103c58c1/heritage-cross-body-bag-Qf2sqq.png','https://static.nike.com/a/images/t_default/7dafd25a-5bb6-4fd7-ad66-1ea8bbc53885/heritage-cross-body-bag-Qf2sqq.png'),(49,'black','https://static.nike.com/a/images/t_default/a92ffae2-afa5-49b7-aa9c-d0d23ed53f67/adv-club-structured-aerobill-cap-zmQJBP.png','https://static.nike.com/a/images/t_default/6c090a85-0e40-46e5-961f-96eaa108fe11/adv-club-structured-aerobill-cap-zmQJBP.png','https://static.nike.com/a/images/t_default/485757bb-bb24-472b-8245-bdad2c7acb7a/adv-club-structured-aerobill-cap-zmQJBP.png','https://static.nike.com/a/images/t_default/c573629a-b24a-467a-b6ca-8c052f7eb135/adv-club-structured-aerobill-cap-zmQJBP.png'),(50,'black','https://static.nike.com/a/images/t_default/83d6cecc-0ce9-4584-82e1-c4f27c140e39/gym-club-training-bag-XnJ7qN.png','https://static.nike.com/a/images/t_default/243a578d-8fcf-4261-bca3-716e21c7b58c/gym-club-training-bag-XnJ7qN.png','https://static.nike.com/a/images/t_default/fc2159d2-0b50-4dd0-a07a-19503358d515/gym-club-training-bag-XnJ7qN.png','https://static.nike.com/a/images/t_default/c0558fd8-9b57-4e58-89e3-6c3c489da6e8/gym-club-training-bag-XnJ7qN.png'),(50,'pink','https://static.nike.com/a/images/t_PDP_1728_v1/f_auto,q_auto:eco/94b84465-a022-4322-a48d-8ccaa50fd0d5/gym-club-training-bag-XnJ7qN.png','https://static.nike.com/a/images/t_default/757be55c-ecd4-4d1f-ad07-2b46bf699754/gym-club-training-bag-XnJ7qN.png','https://static.nike.com/a/images/t_default/ffdefd8b-9eba-4274-8cfb-e7f3fe1a29c2/gym-club-training-bag-XnJ7qN.png','https://static.nike.com/a/images/t_default/422e2acd-e559-480d-8436-9e067aad3712/gym-club-training-bag-XnJ7qN.png'),(51,'white','https://www.dolcegabbana.com/dw/image/v2/BKDB_PRD/on/demandware.static/-/Sites-15/default/dwf8073a47/images/zoom/F5P62TGDB8O_W0800_0.jpg','https://www.dolcegabbana.com/dw/image/v2/BKDB_PRD/on/demandware.static/-/Sites-15/default/dwb7659a37/images/zoom/F5P62TGDB8O_W0800_1.jpg','https://www.dolcegabbana.com/dw/image/v2/BKDB_PRD/on/demandware.static/-/Sites-15/default/dw010b7170/images/zoom/F5P62TGDB8O_W0800_2.jpg','https://www.dolcegabbana.com/dw/image/v2/BKDB_PRD/on/demandware.static/-/Sites-15/default/dwaf06633e/images/zoom/F5P62TGDB8O_W0800_3.jpg'),(52,'white','https://static.nike.com/a/images/t_PDP_1728_v1/f_auto,q_auto:eco,u_126ab356-44d8-4a06-89b4-fcdcc8df0245,c_scale,fl_relative,w_1.0,h_1.0,fl_layer_apply/e8594c23-58e9-4e0c-a622-822ffe0dd336/paris-saint-germain-jordan-football-graphic-t-shirt-2cQ905.png','https://static.nike.com/a/images/t_PDP_1728_v1/f_auto,q_auto:eco,u_126ab356-44d8-4a06-89b4-fcdcc8df0245,c_scale,fl_relative,w_1.0,h_1.0,fl_layer_apply/14dc2d57-1ec9-4f7e-962a-faebe4ac68d6/paris-saint-germain-jordan-football-graphic-t-shirt-2cQ905.png','https://static.nike.com/a/images/t_PDP_1728_v1/f_auto,q_auto:eco,u_126ab356-44d8-4a06-89b4-fcdcc8df0245,c_scale,fl_relative,w_1.0,h_1.0,fl_layer_apply/b775b0a8-a0e3-44d5-be2a-453fe8532efd/paris-saint-germain-jordan-football-graphic-t-shirt-2cQ905.png','https://static.nike.com/a/images/t_PDP_1728_v1/f_auto,q_auto:eco,u_126ab356-44d8-4a06-89b4-fcdcc8df0245,c_scale,fl_relative,w_1.0,h_1.0,fl_layer_apply/c000526e-e78c-4891-954a-f024dfe50143/paris-saint-germain-jordan-football-graphic-t-shirt-2cQ905.png'),(53,'orange','https://static.nike.com/a/images/t_PDP_1728_v1/f_auto,q_auto:eco,u_126ab356-44d8-4a06-89b4-fcdcc8df0245,c_scale,fl_relative,w_1.0,h_1.0,fl_layer_apply/c1805e00-c3bf-4064-b5dc-79fc13f615d2/jordan-flight-heritage-graphic-t-shirt-jvkVf9.png','https://static.nike.com/a/images/t_PDP_1728_v1/f_auto,q_auto:eco,u_126ab356-44d8-4a06-89b4-fcdcc8df0245,c_scale,fl_relative,w_1.0,h_1.0,fl_layer_apply/1cdaaf5a-9b05-4e2e-8f78-588c04256470/jordan-flight-heritage-graphic-t-shirt-jvkVf9.png','https://static.nike.com/a/images/t_PDP_1728_v1/f_auto,q_auto:eco,u_126ab356-44d8-4a06-89b4-fcdcc8df0245,c_scale,fl_relative,w_1.0,h_1.0,fl_layer_apply/68853d29-9a1b-4ff0-ac0f-132830b7af24/jordan-flight-heritage-graphic-t-shirt-jvkVf9.png','https://static.nike.com/a/images/t_default/u_126ab356-44d8-4a06-89b4-fcdcc8df0245,c_scale,fl_relative,w_1.0,h_1.0,fl_layer_apply/0caff873-557f-44fa-8676-deb0578991bb/jordan-flight-heritage-graphic-t-shirt-jvkVf9.png'),(54,'white','https://static.nike.com/a/images/t_default/u_126ab356-44d8-4a06-89b4-fcdcc8df0245,c_scale,fl_relative,w_1.0,h_1.0,fl_layer_apply/ebd4e488-3422-4ce9-8707-212869ae5f12/jordan-flight-mvp-85-t-shirt-hdS3DZ.png','https://static.nike.com/a/images/t_default/u_126ab356-44d8-4a06-89b4-fcdcc8df0245,c_scale,fl_relative,w_1.0,h_1.0,fl_layer_apply/a2392d91-fb67-4aa5-a7b7-5560bc84784b/jordan-flight-mvp-85-t-shirt-hdS3DZ.png','https://static.nike.com/a/images/t_default/u_126ab356-44d8-4a06-89b4-fcdcc8df0245,c_scale,fl_relative,w_1.0,h_1.0,fl_layer_apply/416afb18-2ef4-4dd9-b981-931930568d8d/jordan-flight-mvp-85-t-shirt-hdS3DZ.png','https://static.nike.com/a/images/t_default/u_126ab356-44d8-4a06-89b4-fcdcc8df0245,c_scale,fl_relative,w_1.0,h_1.0,fl_layer_apply/43205cd5-1ac5-48de-9824-9fd76609151a/jordan-flight-mvp-85-t-shirt-hdS3DZ.png'),(55,'blue','https://assets.adidas.com/images/h_840,f_auto,q_auto,fl_lossy,c_fill,g_auto/be7aefaccf5a489a8b2483726617ae71_9366/X_Crazyfast.3_Firm_Ground_Boots_Blue_ID9354_01_standard_hover.jpg','https://assets.adidas.com/images/h_840,f_auto,q_auto,fl_lossy,c_fill,g_auto/5dd612f2171e4ea1a8a2436f19643d59_9366/X_Crazyfast.3_Firm_Ground_Boots_Blue_ID9354_22_model.jpg','https://assets.adidas.com/images/h_840,f_auto,q_auto,fl_lossy,c_fill,g_auto/576af1fa528f4d31b944dd91b2624073_9366/X_Crazyfast.3_Firm_Ground_Boots_Blue_ID9354_02_standard.jpg','https://assets.adidas.com/images/h_840,f_auto,q_auto,fl_lossy,c_fill,g_auto/ab47b949a9684ad2bd009350a46d69e8_9366/X_Crazyfast.3_Firm_Ground_Boots_Blue_ID9354_03_standard.jpg'),(55,'yellow','https://assets.adidas.com/images/h_840,f_auto,q_auto,fl_lossy,c_fill,g_auto/1cecd19580ab4d2cafcd0f0235cf0eb7_9366/Giay_X_Crazyfast_Elite_FG_Mau_vang_IE2376_HM3_hover.jpg','https://assets.adidas.com/images/h_840,f_auto,q_auto,fl_lossy,c_fill,g_auto/d915da512cd345d3b5630b727a054f79_9366/Giay_X_Crazyfast_Elite_FG_Mau_vang_IE2376_HM1.jpg','https://assets.adidas.com/images/h_840,f_auto,q_auto,fl_lossy,c_fill,g_auto/3440d5feae754e4199d20bc7390d3b32_9366/Giay_X_Crazyfast_Elite_FG_Mau_vang_IE2376_HM4.jpg','https://assets.adidas.com/images/h_840,f_auto,q_auto,fl_lossy,c_fill,g_auto/61a152ea2a8f496b9dde366ca96ae479_9366/Giay_X_Crazyfast_Elite_FG_Mau_vang_IE2376_HM5.jpg');
/*!40000 ALTER TABLE `product_images_management` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product_import_management`
--

DROP TABLE IF EXISTS `product_import_management`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product_import_management` (
  `id` int NOT NULL AUTO_INCREMENT,
  `product_management_id` bigint NOT NULL,
  `import_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `import_quantity` int DEFAULT NULL,
  `out_of_stock_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_Products_Management_Products_Import_Management` (`product_management_id`),
  CONSTRAINT `FK_Products_Management_Products_Import_Management` FOREIGN KEY (`product_management_id`) REFERENCES `products_management` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=100 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product_import_management`
--

LOCK TABLES `product_import_management` WRITE;
/*!40000 ALTER TABLE `product_import_management` DISABLE KEYS */;
INSERT INTO `product_import_management` VALUES (1,1,'2022-12-01 00:00:00',20,NULL),(2,3,'2022-12-01 00:00:00',20,NULL),(3,4,'2022-12-01 00:00:00',20,NULL),(4,5,'2022-12-01 00:00:00',20,NULL),(5,6,'2022-12-01 00:00:00',20,NULL),(6,7,'2022-12-01 00:00:00',20,NULL),(7,8,'2022-12-01 00:00:00',20,NULL),(8,11,'2022-12-01 00:00:00',20,NULL),(9,12,'2022-12-01 00:00:00',20,NULL),(10,13,'2022-12-01 00:00:00',20,NULL),(11,14,'2022-12-01 00:00:00',20,NULL),(12,17,'2022-12-01 00:00:00',20,NULL),(13,18,'2022-12-01 00:00:00',20,NULL),(14,19,'2022-12-01 00:00:00',20,NULL),(15,20,'2022-12-01 00:00:00',20,NULL),(16,21,'2022-12-01 00:00:00',20,NULL),(17,22,'2022-12-01 00:00:00',20,NULL),(18,23,'2022-12-01 00:00:00',20,NULL),(19,24,'2022-12-01 00:00:00',20,NULL),(20,25,'2022-12-01 00:00:00',20,NULL),(21,26,'2022-12-01 00:00:00',20,NULL),(22,27,'2022-12-01 00:00:00',20,NULL),(23,28,'2022-12-01 00:00:00',20,NULL),(98,276,'2023-12-21 00:00:00',20,NULL),(99,277,'2023-12-21 00:00:00',20,NULL);
/*!40000 ALTER TABLE `product_import_management` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Temporary view structure for view `product_info_for_ui`
--

DROP TABLE IF EXISTS `product_info_for_ui`;
/*!50001 DROP VIEW IF EXISTS `product_info_for_ui`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `product_info_for_ui` AS SELECT 
 1 AS `id`,
 1 AS `product_id`,
 1 AS `name`,
 1 AS `size`,
 1 AS `selling_price`,
 1 AS `discount`,
 1 AS `brand`,
 1 AS `color`,
 1 AS `available_quantity`,
 1 AS `image_1`,
 1 AS `image_2`,
 1 AS `image_3`,
 1 AS `image_4`,
 1 AS `overall_rating`,
 1 AS `description`*/;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `products`
--

DROP TABLE IF EXISTS `products`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `products` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(200) NOT NULL,
  `selling_price` double NOT NULL,
  `original_price` double NOT NULL,
  `brand` varchar(50) NOT NULL,
  `discount` double NOT NULL,
  `description` text,
  `height` int DEFAULT NULL,
  `length` int DEFAULT NULL,
  `weight` int DEFAULT NULL,
  `width` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name_UNIQUE` (`name`),
  KEY `product_name_index` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=58 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `products`
--

LOCK TABLES `products` WRITE;
/*!40000 ALTER TABLE `products` DISABLE KEYS */;
INSERT INTO `products` VALUES (1,'Manchester United 22/23',950,650,'addidas',10,'A supporter jersey made with recycled materials.\n\nTurned up or pressed down, the humble polo collar has played a starring role in many of Manchester United\'s biggest moments. Making a comeback on this adidas football jersey, it joins a shield-style badge and engineered pinstripe graphic to produce an eye-catching look. Moisture-absorbing AEROREADY and mesh panels make it a comfortable choice for passionate supporters.\n\nMade with 100% recycled materials, this product represents just one of our solutions to help end plastic waste.',5,30,200,30),(2,'Gucci Blondie shoulder bag',3600,2500,'gucci',0,'\n\nVintage elements are paired with archival details as an ode to the glamour that permeates Gucci\'s latest collections. This shoulder bag pairs a delicate chain strap with soft leather to infuse the accessory with a timeless feel. Reintroduced in honor of the collection, the rounded silhouette is completed by a historical, rounded iteration of Guccio Gucci\'s monogram.',20,30,500,10),(3,'Striped jacquard wool knit sweater',2600,1400,'gucci',0,'\n\nThe Cruise 2023 Gucci Cosmogonie collection was presented against the backdrop of the historic Castel del Monte in Italy. The show brought together aesthetics from distant eras and geographies and linked elements from the past with the present. This multicolor striped jacquard wool sweater has the image of the jester paired with the Interlocking G on the front.',8,30,430,30),(4,'Nike Air Max 90 SE',2750,1600,'nike',15,'What moves you? Find out in the Air Max 90 SE. Hemp accents, durable textile and playful \"NIKE MOVING CO.\" details celebrate getting going. Snail-trail deco stitching across the mudguard adds a fun take to the outdoorsy aesthetic. And its Waffle outsole and exposed Air cushioning keep the tried-and-tested feel under your feet. So, where next?',15,30,500,20),(5,'CHARMEUSE SHEATH DRESS WITH ROSE GARDEN PRINT',2065,1300,'dolce&gabbana',0,'The spirit of the 1960s is taking over the new Flower Power collection. The focus is a head-to-toe floral look built around pairing and layering rose, anemone and floral bouquet maxi-prints. A cosmopolite poised between playful and sexy, this women interprets the austerity and elegance of the 1960s, harking back to boldly opulent imagery that here at Dolce&Gabbana is condensed and reinterpreted through silhouettes, ample proportions and airy shapes, bringing femininity and sensuality to the forefront.that will highlight all your feminine sensuality. ',5,30,200,30),(6,'Jordan Flight MVP',800,550,'nike',0,'Spring\'s here, gear up. This fleece hoodie will keep you cosy—and a big ol\' Jordan graphic busting through on the back keeps things fun.',8,30,445,30),(7,'Chanel backpack 22',6627,5123,'chanel',10,'Glossy Calfskin & Light Blue Gold Plated Metal',42,35,800,20),(8,'Butterfly glasses',953,721,'chanel',0,'Acetate & Glass Pearls Dark Green & Gold',4,10,70,10),(41,'ZPLAASH SANDALS ',1500,1250,'addidas',0,'Jump right into pool style and radiate aura with every step. The ZPLAASH adilette sandals feature an inflatable upper strap, with inflatable 3-Stripes, letting the iconic signature explode into a fresh and unexpected statement. The cushioned EVA midsole keeps your stride smooth and steady, whether you\'re relaxing at the gazebo or hanging out on the beach.',50,10,500,20),(42,'ARCHIVE SERIES CONNECTED LONG SLEEVE T-SHIRT MEDIUM',1000,500,'balenciaga',10,'Balenciaga Music | Archive Series Connected Long Sleeve T-Shirt Medium Fit in light grey and red vintage jersey\n\nBalenciaga and Archive have collaborated to create an innovative listening experience: Patterns is an exclusive track that is only available via NFC (Near Field Communication) chip, made in a limited edition and embedded within tags sewn into interactive Balenciaga Music | Archive Series pieces.',47,50,51,30),(43,'Bag With Rhinestones',10990,8000,'balenciaga',0,'24/7 Large Bag in black mesh with rhinestones is in several looks of Balenciaga’s Spring 24 Collection. ',74,53,23,11),(44,'MEN\'S TRIPLE S SNEAKER WITH RHINESTONES IN DARK GREY',2500,1800,'balenciaga',15,'Triple S Sneaker in dark grey microfiber and rhinestones',14,57,20,15),(45,'Locker Necklace in Antique Silver ',1150,800,'balenciaga',5,'Locker Necklace in mix of antique silver, antique gold and shiny silver brass and steel ',19,14,22,10),(46,'Oversized Jacket in Black ',3650,2800,'balenciaga',0,'Oversized Jacket in black dry wool twill is from look 6 of Balenciaga’s Spring 24 Collection.\nGarde-Robe is made up of wardrobe staples in elevated cuts, sharp finishing, and luxe materials without any branding other than bold silhouettes and precise tailoring with minimalist construction techniques. ',30,11,45,32),(47,'Nike Air Force 1 \'07',2000,1200,'nike',10,'Score major style points with this legendary hoops classic. Crossing hardwood comfort with off-court flair, this AF-1 pairs smooth leather overlays with subtle design details for a nothing-but-net look. Hidden Air units and durable, era-echoing \'80s construction add the comfort you know and love.',24,56,18,42),(48,'Nike Heritage',500,300,'nike',0,'The Nike Heritage Cross-body Bag gives you a durable design with multiple compartments to help keep you organised when you\'re out and about. An adjustable strap lets you customise your carrying experience.',17,19,24,14),(49,'Nike Storm-FIT ADV Club',500,150,'nike',30,'Made for your next workout, the mid-depth Nike Club Cap has a waterproof, seam-sealed design that\'s ready to tackle wet weather. Lightweight and flexible with a snapback closure, it\'s an easy, go-to cap for any outdoor activity—rain or shine.',45,15,47,53),(50,'Nike Gym Club',2000,1000,'nike',15,'This bag keeps everything you need for your class, workout or adventure one zip away. It has a spacious main compartment and zipped pockets inside and out for quick-grab organisation. Carry it by hand or over your shoulder so your gear is always close. This product is made from at least 65% recycled polyester fibres.',19,54,11,9),(51,'OVERSIZE COTTON SHIRT WITH LACE APPLIQUÉS',3000,1500,'dolce&gabbana',20,'The DNA collection is distinguished by timeless shapes, bringing together three concepts: Sicilian flair, tailoring and sensuality. Nero Sicilia black and optical white predominate and pair with colors that catapult us onto the island of Sicily and make us dream. The designers channel the timeless wardrobe made up of creations that are instantly unmistakable in their simplicity, presenting us with sophisticated beauty loaded with meaning and symbolism that transcends fashion.',47,41,42,53),(52,'Paris Saint-Germain',1500,1000,'nike',10,'Honour two of the greatest with this PSG and Jordan tee. Printed on super-soft cotton, expanded club details on the back mix up your look while keeping it casual.',40,30,30,30),(53,'Jordan Flight Heritage',3000,1500,'nike',15,'You already know the vibe. An absolutely classic graphic on a relaxed tee with a slightly boxy cut. That\'s all you need.',0,0,0,0),(54,'Jordan Flight MVP 85',2000,800,'nike',10,'Here since \'85 and still flying high. Let \'em know with a soft cotton tee that that celebrates Air Jordan\'s inaugural year.',0,0,0,0),(55,'Crazyfast Elite FG shoes',1800,1400,'addidas',5,'kafjksahf sfkjaskjf hkasfh',300,250,250,300);
/*!40000 ALTER TABLE `products` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `products_management`
--

DROP TABLE IF EXISTS `products_management`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `products_management` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `product_id` bigint NOT NULL,
  `color` varchar(20) DEFAULT 'none',
  `size` varchar(10) DEFAULT 'none',
  `available_quantity` int DEFAULT '0',
  `sold_quantity` int DEFAULT '0',
  `One_star_quantity` int DEFAULT '0',
  `Two_star_quantity` int DEFAULT '0',
  `Three_star_quantity` int DEFAULT '0',
  `Four_star_quantity` int DEFAULT '0',
  `Five_star_quantity` int DEFAULT '0',
  `overall_rating` int DEFAULT '0',
  `import_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_Products_Products_Management` (`product_id`),
  CONSTRAINT `FK_Products_Products_Management` FOREIGN KEY (`product_id`) REFERENCES `products` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=329 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `products_management`
--

LOCK TABLES `products_management` WRITE;
/*!40000 ALTER TABLE `products_management` DISABLE KEYS */;
INSERT INTO `products_management` VALUES (1,1,'red','s',19,1,0,0,0,0,2,5,'2022-12-01 00:00:00'),(3,1,'red','l',20,0,0,0,0,0,2,5,'2022-12-01 00:00:00'),(4,1,'white','s',20,0,0,0,0,0,0,0,'2022-12-01 00:00:00'),(5,1,'white','m',20,0,0,0,0,0,0,0,'2022-12-01 00:00:00'),(6,1,'white','l',20,0,0,0,0,0,0,0,'2022-12-01 00:00:00'),(7,2,'pink','none',20,0,0,0,0,0,0,0,'2022-12-01 00:00:00'),(8,2,'black','none',20,0,0,0,1,0,0,3,'2022-12-01 00:00:00'),(11,3,'none','l',20,0,0,0,0,0,0,0,'2022-12-01 00:00:00'),(12,4,'none','40',20,0,0,0,1,0,0,3,'2022-12-01 00:00:00'),(13,4,'none','40.5',20,0,0,0,1,0,0,3,'2022-12-01 00:00:00'),(14,4,'none','41',20,0,0,0,1,0,0,3,'2022-12-01 00:00:00'),(17,5,'none','l',20,0,0,0,0,0,0,0,'2022-12-01 00:00:00'),(18,5,'none','xl',20,0,0,0,0,0,0,0,'2022-12-01 00:00:00'),(19,5,'none','xs',20,0,0,0,0,0,0,0,'2022-12-01 00:00:00'),(20,5,'none','xxl',20,0,0,0,0,0,0,0,'2022-12-01 00:00:00'),(21,6,'none','s',20,0,0,0,0,0,0,0,'2022-12-01 00:00:00'),(22,6,'none','m',20,0,0,0,0,0,0,0,'2022-12-01 00:00:00'),(23,6,'none','l',20,0,0,0,0,0,0,0,'2022-12-01 00:00:00'),(24,7,'white','none',20,0,0,0,0,0,0,0,'2022-12-01 00:00:00'),(25,7,'red','none',20,0,0,0,0,0,0,0,'2022-12-01 00:00:00'),(26,8,'green','none',20,0,0,0,0,0,0,0,'2022-12-01 00:00:00'),(27,8,'white','none',20,0,0,0,0,0,0,0,'2022-12-01 00:00:00'),(28,8,'red','none',20,0,0,0,0,0,0,0,'2022-12-01 00:00:00'),(276,5,'none','s',20,0,0,0,0,0,0,0,'2023-12-21 00:00:00'),(277,5,'none','m',20,0,0,0,0,0,0,0,'2023-12-21 00:00:00'),(278,41,'none','38',19,1,0,0,0,0,0,0,NULL),(279,41,'none','39',20,0,0,0,0,0,0,0,NULL),(280,41,'none','40',20,0,0,0,0,0,0,0,NULL),(281,42,'white','s',20,0,0,0,0,0,0,0,NULL),(282,42,'white','m',20,0,0,0,0,0,0,0,NULL),(283,42,'white','l',20,0,0,0,0,0,0,0,NULL),(284,42,'white','xl',20,0,0,0,0,0,0,0,NULL),(285,43,'black','none',20,0,0,0,0,0,0,0,NULL),(286,44,'none','39',19,1,0,0,0,0,0,0,NULL),(287,44,'none','40',20,0,0,0,0,0,0,0,NULL),(288,44,'none','41',20,0,0,0,0,0,0,0,NULL),(289,44,'none','42',20,0,0,0,0,0,0,0,NULL),(290,45,'none','none',20,0,0,0,0,0,0,0,NULL),(291,46,'black','s',20,0,0,0,0,0,0,0,NULL),(292,46,'black','m',20,0,0,0,0,0,0,0,NULL),(293,46,'black','l',20,0,0,0,0,0,0,0,NULL),(294,47,'none','40',20,0,0,0,0,0,0,0,NULL),(295,47,'none','40.5',20,0,0,0,0,0,0,0,NULL),(296,47,'none','41',20,0,0,0,0,0,0,0,NULL),(297,47,'none','42',20,0,0,0,0,0,0,0,NULL),(298,47,'none','43',20,0,0,0,0,0,0,0,NULL),(299,47,'none','44',20,0,0,0,0,0,0,0,NULL),(300,48,'black','none',20,0,0,0,0,0,0,0,NULL),(301,49,'black','none',19,1,0,0,0,0,0,0,NULL),(302,50,'black','none',20,0,0,0,0,0,0,0,NULL),(303,50,'pink','none',17,3,0,0,0,0,1,5,NULL),(304,51,'white','s',19,1,0,0,0,0,1,5,NULL),(305,51,'white','m',19,1,0,0,0,0,1,5,NULL),(306,51,'white','l',20,0,0,0,0,0,1,5,NULL),(307,52,'white','s',5,0,0,0,0,0,0,0,NULL),(308,52,'white','m',5,0,0,0,0,0,0,0,NULL),(309,52,'white','l',7,-2,0,0,0,0,0,0,NULL),(310,52,'white','xl',10,0,0,0,0,0,0,0,NULL),(311,52,'white','xxl',5,0,0,0,0,0,0,0,NULL),(312,53,'orange','s',5,0,0,0,0,0,0,0,NULL),(313,53,'orange','m',5,0,0,0,0,0,0,0,NULL),(314,53,'orange','l',5,0,0,0,0,0,0,0,NULL),(315,54,'white','s',5,0,0,0,0,0,0,0,NULL),(316,54,'white','m',5,0,0,0,0,0,0,0,NULL),(317,54,'white','l',5,0,0,0,0,0,0,0,NULL),(318,54,'white','xl',5,0,0,0,0,0,0,0,NULL),(319,55,'yellow','40',5,0,0,0,0,0,1,5,'2023-02-03 00:00:00'),(320,55,'yellow','41',7,0,0,0,0,0,1,5,'2023-02-01 00:00:00'),(321,55,'yellow','41.5',5,0,0,0,0,0,1,5,'2023-02-03 00:00:00'),(328,55,'blue','41',5,0,0,0,0,0,0,0,'2023-02-03 00:00:00');
/*!40000 ALTER TABLE `products_management` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `refund`
--

DROP TABLE IF EXISTS `refund`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `refund` (
  `ID` int NOT NULL AUTO_INCREMENT,
  `invoice_id` int NOT NULL,
  `content` varchar(255) DEFAULT NULL,
  `date` datetime DEFAULT NULL,
  `evident_image` varchar(255) DEFAULT NULL,
  `refund_money` double DEFAULT '0',
  `status` varchar(255) DEFAULT NULL,
  `in_charge_admin_id` int DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK4ny5ywx78sgoir4c4gifedx7e` (`invoice_id`),
  KEY `FKk62gu4uso16ol1i5f2xmjo6n` (`in_charge_admin_id`)
) ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `refund`
--

LOCK TABLES `refund` WRITE;
/*!40000 ALTER TABLE `refund` DISABLE KEYS */;
INSERT INTO `refund` VALUES (1,5,NULL,'2024-01-03 15:23:02','https://drive.google.com/uc?export=view&id=1fyOZVYQmq2RqEpGFa2vDF0N8Ym2XW3lJ',1169.25,'REFUNDED',1);
/*!40000 ALTER TABLE `refund` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Temporary view structure for view `staff_info_for_ui`
--

DROP TABLE IF EXISTS `staff_info_for_ui`;
/*!50001 DROP VIEW IF EXISTS `staff_info_for_ui`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `staff_info_for_ui` AS SELECT 
 1 AS `id`,
 1 AS `account_id`,
 1 AS `user_name`,
 1 AS `password`,
 1 AS `status`,
 1 AS `name`,
 1 AS `email`,
 1 AS `phone_number`,
 1 AS `position`,
 1 AS `avatar`*/;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `staffs`
--

DROP TABLE IF EXISTS `staffs`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `staffs` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `hometown` varchar(50) NOT NULL,
  `position` varchar(50) NOT NULL,
  `birth_date` date NOT NULL,
  `email` varchar(50) NOT NULL,
  `phone_number` varchar(50) NOT NULL,
  `account_id` bigint NOT NULL,
  `avatar` varchar(500) NOT NULL DEFAULT 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQaiHNXjxQrlFFFHdMGtUpH1nLDjHzyfTms6A&usqp=CAU',
  PRIMARY KEY (`id`),
  KEY `staffs_ibfk_1` (`account_id`),
  CONSTRAINT `staffs_ibfk_1` FOREIGN KEY (`account_id`) REFERENCES `login_accounts` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `staffs`
--

LOCK TABLES `staffs` WRITE;
/*!40000 ALTER TABLE `staffs` DISABLE KEYS */;
INSERT INTO `staffs` VALUES (0,'uknown','abc','ADMIN','1900-02-02','aasfasfasf','0111111111',0,'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQaiHNXjxQrlFFFHdMGtUpH1nLDjHzyfTms6A&usqp=CAU'),(1,'Nguyen Hoang Sang','hcm','ADMIN','2001-06-23','nguyenhoangsang236@gmail.com','0123456987',1,'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQaiHNXjxQrlFFFHdMGtUpH1nLDjHzyfTms6A&usqp=CAU'),(2,'Nguyen Anh Duc','hcm','ADMIN','2001-02-02','ducnguvcl@gmail.com','0321654987',51,'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQaiHNXjxQrlFFFHdMGtUpH1nLDjHzyfTms6A&usqp=CAU');
/*!40000 ALTER TABLE `staffs` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Temporary view structure for view `top_8_best_sell_products`
--

DROP TABLE IF EXISTS `top_8_best_sell_products`;
/*!50001 DROP VIEW IF EXISTS `top_8_best_sell_products`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `top_8_best_sell_products` AS SELECT 
 1 AS `id`,
 1 AS `product_id`,
 1 AS `name`,
 1 AS `size`,
 1 AS `selling_price`,
 1 AS `discount`,
 1 AS `brand`,
 1 AS `color`,
 1 AS `available_quantity`,
 1 AS `image_1`,
 1 AS `image_2`,
 1 AS `image_3`,
 1 AS `image_4`,
 1 AS `overall_rating`,
 1 AS `description`*/;
SET character_set_client = @saved_cs_client;

--
-- Final view structure for view `cart_item_info_for_ui`
--

/*!50001 DROP VIEW IF EXISTS `cart_item_info_for_ui`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_0900_ai_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `cart_item_info_for_ui` AS select `c`.`ID` AS `id`,`c`.`customer_id` AS `customer_id`,`c`.`product_management_id` AS `product_management_id`,`c`.`quantity` AS `quantity`,`c`.`buying_status` AS `buying_status`,`c`.`Select_status` AS `select_status`,`pm`.`product_id` AS `product_id`,`pm`.`color` AS `color`,`pm`.`size` AS `size`,`p`.`name` AS `name`,`p`.`brand` AS `brand`,`p`.`selling_price` AS `selling_price`,`p`.`discount` AS `discount`,`pim`.`image_1` AS `image_1` from (((`cart` `c` join `products_management` `pm` on((`pm`.`id` = `c`.`product_management_id`))) join `products` `p` on((`pm`.`product_id` = `p`.`id`))) join `product_images_management` `pim` on((`pim`.`product_id` = `p`.`id`))) where ((`c`.`buying_status` = 0) and (`pim`.`color` = `pm`.`color`)) order by `c`.`ID` */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `comment_info_for_ui`
--

/*!50001 DROP VIEW IF EXISTS `comment_info_for_ui`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_0900_ai_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `comment_info_for_ui` AS select `com`.`id` AS `id`,`com`.`product_id` AS `product_id`,`com`.`product_color` AS `product_color`,`com`.`customer_id` AS `customer_id`,`cus`.`name` AS `name`,`cus`.`avatar` AS `avatar`,`com`.`comment_content` AS `comment_content`,`com`.`comment_date` AS `comment_date`,`com`.`like_quantity` AS `like_quantity`,`com`.`reply_quantity` AS `reply_quantity`,`com`.`reply_on` AS `reply_on` from (`comments` `com` join `customers` `cus` on((`com`.`customer_id` = `cus`.`id`))) order by `com`.`id` desc */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `customer_info_for_ui`
--

/*!50001 DROP VIEW IF EXISTS `customer_info_for_ui`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_0900_ai_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `customer_info_for_ui` AS select `c`.`id` AS `id`,`c`.`account_id` AS `account_id`,`la`.`user_name` AS `user_name`,`la`.`password` AS `password`,`la`.`status` AS `status`,`c`.`name` AS `name`,`c`.`email` AS `email`,`c`.`phone_number` AS `phone_number`,`c`.`address` AS `address`,`c`.`city` AS `city`,`c`.`country` AS `country`,`c`.`avatar` AS `avatar` from (`login_accounts` `la` join `customers` `c` on((`la`.`id` = `c`.`account_id`))) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `hot_discount_products`
--

/*!50001 DROP VIEW IF EXISTS `hot_discount_products`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_0900_ai_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `hot_discount_products` AS select `pm`.`id` AS `id`,`pm`.`product_id` AS `product_id`,`p`.`name` AS `name`,`pm`.`size` AS `size`,`p`.`selling_price` AS `selling_price`,`p`.`discount` AS `discount`,`p`.`brand` AS `brand`,`pm`.`color` AS `color`,`pm`.`available_quantity` AS `available_quantity`,`pim`.`image_1` AS `image_1`,`pim`.`image_2` AS `image_2`,`pim`.`image_3` AS `image_3`,`pim`.`image_4` AS `image_4`,`pm`.`overall_rating` AS `overall_rating`,`p`.`description` AS `description` from (((`products` `p` left join `products_management` `pm` on((`p`.`id` = `pm`.`product_id`))) join `product_images_management` `pim` on((`p`.`id` = `pim`.`product_id`))) join (select min(`pm`.`id`) AS `id`,`pim`.`product_id` AS `product_id`,`pim`.`color` AS `color` from (`product_images_management` `pim` left join `products_management` `pm` on(((`pim`.`product_id` = `pm`.`product_id`) and (`pim`.`color` = `pm`.`color`)))) group by `pim`.`product_id`,`pim`.`color`) `tmp` on((`tmp`.`id` = `pm`.`id`))) where ((`pim`.`color` = `pm`.`color`) and (`p`.`discount` > 0)) order by `p`.`discount` desc limit 8 */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `invoice_with_products_info_for_ui`
--

/*!50001 DROP VIEW IF EXISTS `invoice_with_products_info_for_ui`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_0900_ai_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `invoice_with_products_info_for_ui` AS select `iwp`.`Invoice_ID` AS `Invoice_ID`,`p`.`id` AS `product_id`,`iwp`.`Product_Management_ID` AS `Product_Management_ID`,`c`.`id` AS `customer_id`,`p`.`name` AS `name`,`pm`.`color` AS `color`,`pm`.`size` AS `size`,`p`.`selling_price` AS `selling_price`,`p`.`discount` AS `discount`,`iwp`.`Quantity` AS `Quantity`,`pim`.`image_1` AS `image_1` from (((((`invoices_with_products` `iwp` join `products_management` `pm` on((`iwp`.`Product_Management_ID` = `pm`.`id`))) join `invoice` `i` on((`iwp`.`Invoice_ID` = `i`.`ID`))) join `customers` `c` on((`i`.`Customer_ID` = `c`.`id`))) join `products` `p` on((`p`.`id` = `pm`.`product_id`))) join `product_images_management` `pim` on((`p`.`id` = `pim`.`product_id`))) where (`pim`.`color` = `pm`.`color`) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `new_arrival_products`
--

/*!50001 DROP VIEW IF EXISTS `new_arrival_products`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_0900_ai_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `new_arrival_products` AS select `pm`.`id` AS `id`,`pm`.`product_id` AS `product_id`,`p`.`name` AS `name`,`pm`.`size` AS `size`,`p`.`selling_price` AS `selling_price`,`p`.`discount` AS `discount`,`p`.`brand` AS `brand`,`pm`.`color` AS `color`,`pm`.`available_quantity` AS `available_quantity`,`pim`.`image_1` AS `image_1`,`pim`.`image_2` AS `image_2`,`pim`.`image_3` AS `image_3`,`pim`.`image_4` AS `image_4`,`pm`.`overall_rating` AS `overall_rating`,`p`.`description` AS `description` from (((`products` `p` left join `products_management` `pm` on((`p`.`id` = `pm`.`product_id`))) join `product_images_management` `pim` on((`p`.`id` = `pim`.`product_id`))) join (select min(`pm`.`id`) AS `id`,`pim`.`product_id` AS `product_id`,`pim`.`color` AS `color` from (`product_images_management` `pim` left join `products_management` `pm` on(((`pim`.`product_id` = `pm`.`product_id`) and (`pim`.`color` = `pm`.`color`)))) group by `pim`.`product_id`,`pim`.`color` order by `id` desc limit 8) `tmp` on((`tmp`.`id` = `pm`.`id`))) where (`pim`.`color` = `pm`.`color`) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `product_full_info_for_ui`
--

/*!50001 DROP VIEW IF EXISTS `product_full_info_for_ui`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_0900_ai_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `product_full_info_for_ui` AS select `pm`.`id` AS `id`,`pm`.`product_id` AS `product_id`,`p`.`name` AS `name`,`pm`.`size` AS `size`,`p`.`selling_price` AS `selling_price`,`p`.`original_price` AS `original_price`,`p`.`discount` AS `discount`,`p`.`brand` AS `brand`,`pm`.`color` AS `color`,`pm`.`available_quantity` AS `available_quantity`,`pim`.`image_1` AS `image_1`,`pim`.`image_2` AS `image_2`,`pim`.`image_3` AS `image_3`,`pim`.`image_4` AS `image_4`,`pm`.`overall_rating` AS `overall_rating`,`p`.`description` AS `description` from ((`products` `p` left join `products_management` `pm` on((`p`.`id` = `pm`.`product_id`))) join `product_images_management` `pim` on((`p`.`id` = `pim`.`product_id`))) where (`pim`.`color` = `pm`.`color`) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `product_info_for_ui`
--

/*!50001 DROP VIEW IF EXISTS `product_info_for_ui`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_0900_ai_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `product_info_for_ui` AS select `pm`.`id` AS `id`,`pm`.`product_id` AS `product_id`,`p`.`name` AS `name`,`pm`.`size` AS `size`,`p`.`selling_price` AS `selling_price`,`p`.`discount` AS `discount`,`p`.`brand` AS `brand`,`pm`.`color` AS `color`,`pm`.`available_quantity` AS `available_quantity`,`pim`.`image_1` AS `image_1`,`pim`.`image_2` AS `image_2`,`pim`.`image_3` AS `image_3`,`pim`.`image_4` AS `image_4`,`pm`.`overall_rating` AS `overall_rating`,`p`.`description` AS `description` from ((`products` `p` left join `products_management` `pm` on((`p`.`id` = `pm`.`product_id`))) join `product_images_management` `pim` on((`p`.`id` = `pim`.`product_id`))) where ((`pim`.`color` = `pm`.`color`) and (`pm`.`id`,`pm`.`product_id`,`pim`.`color`) in (select min(`pm`.`id`) AS `id`,`pim`.`product_id`,`pim`.`color` from (`product_images_management` `pim` left join `products_management` `pm` on(((`pim`.`product_id` = `pm`.`product_id`) and (`pim`.`color` = `pm`.`color`)))) group by `pim`.`product_id`,`pim`.`color`)) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `staff_info_for_ui`
--

/*!50001 DROP VIEW IF EXISTS `staff_info_for_ui`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_0900_ai_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `staff_info_for_ui` AS select `c`.`id` AS `id`,`c`.`account_id` AS `account_id`,`la`.`user_name` AS `user_name`,`la`.`password` AS `password`,`la`.`status` AS `status`,`c`.`name` AS `name`,`c`.`email` AS `email`,`c`.`phone_number` AS `phone_number`,`c`.`position` AS `position`,`c`.`avatar` AS `avatar` from (`login_accounts` `la` join `staffs` `c` on((`la`.`id` = `c`.`account_id`))) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `top_8_best_sell_products`
--

/*!50001 DROP VIEW IF EXISTS `top_8_best_sell_products`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_0900_ai_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `top_8_best_sell_products` AS select `pm`.`id` AS `id`,`pm`.`product_id` AS `product_id`,`p`.`name` AS `name`,`pm`.`size` AS `size`,`p`.`selling_price` AS `selling_price`,`p`.`discount` AS `discount`,`p`.`brand` AS `brand`,`pm`.`color` AS `color`,`pm`.`available_quantity` AS `available_quantity`,`pim`.`image_1` AS `image_1`,`pim`.`image_2` AS `image_2`,`pim`.`image_3` AS `image_3`,`pim`.`image_4` AS `image_4`,`pm`.`overall_rating` AS `overall_rating`,`p`.`description` AS `description` from (((`products` `p` left join `products_management` `pm` on((`p`.`id` = `pm`.`product_id`))) join `product_images_management` `pim` on((`p`.`id` = `pim`.`product_id`))) join (select `pm`.`id` AS `id` from ((((`products_management` `pm` join `invoices_with_products` `iwp` on((`pm`.`id` = `iwp`.`Product_Management_ID`))) join `invoice` `i` on((`iwp`.`Invoice_ID` = `i`.`ID`))) join `products` `p` on((`p`.`id` = `pm`.`product_id`))) join `product_images_management` `pim` on((`pim`.`product_id` = `p`.`id`))) where ((`i`.`invoice_date` <= cast(now() as date)) and (`i`.`invoice_date` >= cast((now() - interval 30 day) as date))) group by `pm`.`id` order by (select sum(`invoices_with_products`.`Quantity`) from ((`invoices_with_products` join `products_management` `pm` on((`pm`.`id` = `invoices_with_products`.`Product_Management_ID`))) join `products` `p` on((`p`.`id` = `pm`.`product_id`))) where (`p`.`name` = `p`.`name`)) desc limit 8) `tmp` on((`tmp`.`id` = `pm`.`id`))) where (`pim`.`color` = `pm`.`color`) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-04-20 13:50:55
