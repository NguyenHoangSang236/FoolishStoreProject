CREATE DATABASE  IF NOT EXISTS `fashionstorewebsite` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `fashionstorewebsite`;
-- MySQL dump 10.13  Distrib 8.0.32, for Linux (x86_64)
--
-- Host: localhost    Database: fashionstorewebsite
-- ------------------------------------------------------
-- Server version	8.0.33-0ubuntu0.22.04.2

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
  `id` bigint NOT NULL AUTO_INCREMENT,
  `customer_id` bigint NOT NULL,
  `product_management_id` bigint NOT NULL,
  `quantity` int NOT NULL,
  `buying_status` varchar(20) NOT NULL DEFAULT 'NOT_BOUGHT_YET',
  `select_status` tinyint DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `FK_Customer_Cart` (`customer_id`),
  KEY `FK_Product_Cart_idx` (`product_management_id`),
  CONSTRAINT `FK_Customer_Cart` FOREIGN KEY (`customer_id`) REFERENCES `customers` (`id`),
  CONSTRAINT `FK_Product_Cart` FOREIGN KEY (`product_management_id`) REFERENCES `products_management` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=35 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cart`
--

LOCK TABLES `cart` WRITE;
/*!40000 ALTER TABLE `cart` DISABLE KEYS */;
INSERT INTO `cart` VALUES (1,1,3,2,'NOT_BOUGHT_YET',0),(5,1,4,6,'BOUGHT',1),(9,1,25,5,'NOT_BOUGHT_YET',0),(19,1,22,2,'NOT_BOUGHT_YET',0),(23,1,13,4,'NOT_BOUGHT_YET',0),(27,1,27,2,'NOT_BOUGHT_YET',0),(28,1,7,1,'NOT_BOUGHT_YET',0),(29,1,8,1,'NOT_BOUGHT_YET',0),(30,1,24,1,'NOT_BOUGHT_YET',0),(31,1,26,1,'PENDING',1),(32,1,28,1,'PENDING',1),(33,1,17,1,'PENDING',1),(34,1,11,1,'PENDING',1);
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
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `catalog`
--

LOCK TABLES `catalog` WRITE;
/*!40000 ALTER TABLE `catalog` DISABLE KEYS */;
INSERT INTO `catalog` VALUES (1,'Jackets','1SJd_q9XgP6t3IcZyr9yge-YWpB7m4r6p'),(2,'Glasses','1sxJBW8O5FurTGUE39cIxdRcRycLg6pnK'),(3,'Shoes','1C22R6MlFTiMwbewDPkzpX1wb_GnNGu08'),(4,'Male','17X-b4LDq0VoHV1QOJE5fMvVhIoejq_2u'),(5,'Female','128ptt1MwwaDE1B-Qd0PYt-z0mooOBU9L'),(6,'Accessories','1vu0bQvTF38IAsJtLBH_rFQHNJAqY1IgM'),(7,'Purses','1GKehSvAvHwZ9dVe82dDoGBzXe1rbw1vz'),(8,'Sport','14Ap5-CSmhnR5wAxbLsJ0XKkbi45vya6X'),(9,'Backpackes','1rdb1BgNCVRL2A5FAVqvRcV0azUd1nBlw');
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
INSERT INTO `catalogs_with_products` VALUES (4,1),(8,1),(5,2),(6,2),(7,2),(4,3),(3,4),(4,4),(5,5),(4,6),(8,6),(5,7),(6,7),(9,7),(2,8),(5,8);
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
INSERT INTO `comment_like` VALUES (1,1),(2,1),(1,2),(2,2),(3,2),(1,13),(1,14),(1,15),(1,16),(1,28),(1,31),(1,32),(1,41);
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
  `reply_on` bigint unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `FK_Customer_Comments` (`customer_id`),
  KEY `FK_Product_Comments` (`product_id`),
  CONSTRAINT `FK_Customer_Comments` FOREIGN KEY (`customer_id`) REFERENCES `customers` (`id`),
  CONSTRAINT `FK_Product_Comments` FOREIGN KEY (`product_id`) REFERENCES `products` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=43 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `comments`
--

LOCK TABLES `comments` WRITE;
/*!40000 ALTER TABLE `comments` DISABLE KEYS */;
INSERT INTO `comments` VALUES (1,1,'red',1,'nai xu','2023-07-30 09:17:33',2,13,0),(2,1,'red',3,'wow','2023-03-03 00:00:00',3,0,1),(4,1,'red',1,'niceee','2023-07-30 10:15:29',0,0,1),(5,1,'red',2,'alooo','2023-07-30 10:15:49',0,0,1),(6,1,'red',1,'huuuhu','2023-07-30 10:16:01',0,0,1),(7,1,'red',1,'huuuhu','2023-07-30 10:16:15',0,2,0),(8,1,'red',1,'shjttttt bro','2023-09-01 10:32:49',0,0,1),(9,1,'red',1,'shjttttt','2023-09-01 10:37:09',0,0,1),(10,1,'red',1,'dep qua','2023-09-01 12:55:56',0,0,0),(11,1,'red',1,'accc','2023-09-01 13:05:36',0,0,0),(12,1,'red',1,'deppp','2023-09-01 13:07:02',0,0,0),(13,1,'red',1,'alo','2023-09-01 13:07:30',1,0,0),(14,1,'red',1,'xinh','2023-09-01 13:08:06',1,1,0),(15,1,'red',1,'hehehehehehe','2023-09-01 13:08:59',1,4,0),(16,1,'red',1,'ak','2023-09-01 13:10:04',1,1,0),(17,1,'red',1,'no','2023-09-01 15:44:32',0,3,0),(18,1,'white',1,'nice Tee','2023-09-01 16:29:01',0,4,0),(19,1,'white',1,'omg','2023-09-01 16:41:15',0,0,0),(20,1,'white',1,'kkkk','2023-09-01 17:06:52',0,0,18),(21,1,'white',1,'hhhh','2023-09-01 17:07:09',0,0,18),(22,1,'red',1,'ok','2023-09-04 15:33:28',0,0,1),(23,1,'red',1,'yyyy','2023-09-04 15:41:00',0,0,1),(24,1,'red',1,'u gud?','2023-09-04 16:32:19',0,0,1),(25,1,'red',1,'love you ','2023-09-04 16:49:49',0,0,1),(26,1,'red',1,'alo 1','2023-09-04 16:51:13',0,0,1),(27,1,'red',1,'alo 2','2023-09-04 17:03:41',0,0,1),(28,1,'red',1,'alo 3','2023-09-04 17:04:05',1,0,1),(29,1,'white',1,'hehehe','2023-09-04 17:05:44',0,0,18),(30,1,'white',1,'heheh','2023-09-04 17:08:35',0,0,18),(31,1,'red',1,'hihi','2023-09-04 17:08:56',1,0,17),(32,1,'red',1,'kkkk','2023-09-04 17:09:09',1,0,17),(33,1,'red',1,'eeee','2023-09-04 17:09:49',0,0,7),(34,1,'red',1,'hhhh','2023-09-04 17:13:38',0,0,7),(35,1,'red',1,'iiii','2023-09-04 17:19:45',0,0,16),(36,1,'red',1,'fff','2023-09-04 17:21:11',0,0,15),(37,1,'red',1,'fff','2023-09-04 17:21:13',0,0,15),(38,1,'red',1,'fff','2023-09-04 17:21:15',0,0,15),(39,1,'red',1,'fff','2023-09-04 17:21:41',0,0,15),(40,1,'red',1,'luv','2023-09-04 17:25:12',0,0,14),(41,1,'red',1,'ac','2023-09-08 08:10:49',1,0,17),(42,7,'white',1,'okkk','2023-09-08 14:39:58',0,0,0);
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
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customers`
--

LOCK TABLES `customers` WRITE;
/*!40000 ALTER TABLE `customers` DISABLE KEYS */;
INSERT INTO `customers` VALUES (1,'Sang dep trai','nguyenhoangsang2362001@gmail.com','0321456987',2,'Vietnam',NULL,NULL,'1jH7eFrRWMLFvE-HD7h8LGDfbjprFEoAL'),(2,'Nguyen Quynh Nhu','nnhu7721@gmail.com','0213654798',3,NULL,NULL,NULL,'1tVXpd6cg_yKMnd7KQ_qqmtdvSG8tXa8R'),(3,'Nguyen Hoang Sang','19110120@student.hcmute.edu.vn','0977815809',7,NULL,NULL,NULL,'1tVXpd6cg_yKMnd7KQ_qqmtdvSG8tXa8R'),(4,'Nguyen Thi Hoang Trang','pbeltranster@gmail.com','0321654987',9,NULL,NULL,NULL,'1tVXpd6cg_yKMnd7KQ_qqmtdvSG8tXa8R'),(6,'qweqwe','qwe@we','1234567890',11,NULL,NULL,NULL,'1tVXpd6cg_yKMnd7KQ_qqmtdvSG8tXa8R'),(7,'Nguyen Quoc Heng','nqh130901@gmail.com','1234567890',12,NULL,NULL,NULL,'1tVXpd6cg_yKMnd7KQ_qqmtdvSG8tXa8R'),(8,'Duc Ngu Vcl','ducngu@gmail.com','0321654987',13,NULL,NULL,NULL,'1tVXpd6cg_yKMnd7KQ_qqmtdvSG8tXa8R'),(15,'guyen uoc oan\0\0\0','qhoangf@gmail.com','0321654789',50,NULL,NULL,NULL,'1tVXpd6cg_yKMnd7KQ_qqmtdvSG8tXa8R'),(16,'Nguyuen Hoang Anh Kho','anhkhoa123@gmail.com','0321456987',51,NULL,NULL,NULL,'1tVXpd6cg_yKMnd7KQ_qqmtdvSG8tXa8R'),(17,'Huynh Gia Kie','giakien@gmail.com','0312546897',52,NULL,NULL,NULL,'1tVXpd6cg_yKMnd7KQ_qqmtdvSG8tXa8R'),(27,'Nguyen Van A','nguyenvana@gmail.com','0123456789',63,NULL,NULL,NULL,'1tVXpd6cg_yKMnd7KQ_qqmtdvSG8tXa8R'),(28,'Nguyen Van','nguyenvanB@gmail.com','0321564897',64,NULL,NULL,NULL,'1tVXpd6cg_yKMnd7KQ_qqmtdvSG8tXa8R');
/*!40000 ALTER TABLE `customers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `delivery`
--

DROP TABLE IF EXISTS `delivery`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `delivery` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `invoice_id` bigint NOT NULL,
  `shipping_order_code` varchar(20) NOT NULL,
  `ship_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_Invoice_delivery` (`invoice_id`),
  CONSTRAINT `FK_Invoice_delivery` FOREIGN KEY (`invoice_id`) REFERENCES `invoice` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `delivery`
--

LOCK TABLES `delivery` WRITE;
/*!40000 ALTER TABLE `delivery` DISABLE KEYS */;
INSERT INTO `delivery` VALUES (6,1,'1',NULL);
/*!40000 ALTER TABLE `delivery` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `delivery_info_for_ui`
--

DROP TABLE IF EXISTS `delivery_info_for_ui`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `delivery_info_for_ui` (
  `invoice_id` int NOT NULL,
  `additional_shipper_comment` varchar(255) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `city` varchar(255) DEFAULT NULL,
  `country` varchar(255) DEFAULT NULL,
  `current_delivery_status` varchar(255) DEFAULT NULL,
  `customer_avatar` varchar(255) DEFAULT NULL,
  `customer_id` varchar(255) DEFAULT NULL,
  `customer_name` varchar(255) DEFAULT NULL,
  `customer_phone_number` varchar(255) DEFAULT NULL,
  `delivery_date` datetime DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `evidence_image` varchar(255) DEFAULT NULL,
  `expected_delivery_date` datetime DEFAULT NULL,
  `id` varchar(255) DEFAULT NULL,
  `invoice_date` datetime DEFAULT NULL,
  `invoice_delivery_status` varchar(255) DEFAULT NULL,
  `note` varchar(255) DEFAULT NULL,
  `payment_method` varchar(255) DEFAULT NULL,
  `payment_status` varchar(255) DEFAULT NULL,
  `shipper_avatar` varchar(255) DEFAULT NULL,
  `shipper_id` int DEFAULT NULL,
  `shipper_name` varchar(255) DEFAULT NULL,
  `shipper_phone_number` varchar(255) DEFAULT NULL,
  `total_price` double DEFAULT NULL,
  PRIMARY KEY (`invoice_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `delivery_info_for_ui`
--

LOCK TABLES `delivery_info_for_ui` WRITE;
/*!40000 ALTER TABLE `delivery_info_for_ui` DISABLE KEYS */;
/*!40000 ALTER TABLE `delivery_info_for_ui` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `delivery_type`
--

DROP TABLE IF EXISTS `delivery_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `delivery_type` (
  `id` int NOT NULL,
  `name` varchar(45) NOT NULL,
  `price` double NOT NULL,
  `condition` text,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `delivery_type`
--

LOCK TABLES `delivery_type` WRITE;
/*!40000 ALTER TABLE `delivery_type` DISABLE KEYS */;
INSERT INTO `delivery_type` VALUES (1,'NORMAL_DELIVERY',3,'Get package after 2-8 days depends on distance'),(2,'EXPRESS_DELIVERY',6,'Get package after no more than 4 days');
/*!40000 ALTER TABLE `delivery_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `device_fcm_token`
--

DROP TABLE IF EXISTS `device_fcm_token`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `device_fcm_token` (
  `id` bigint NOT NULL,
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
  `Invoice_Date` date NOT NULL,
  `pay_date` datetime DEFAULT NULL,
  `Payment_Status` varchar(20) NOT NULL DEFAULT 'UNPAID',
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
  `intent` varchar(255) DEFAULT NULL,
  `receiver_account_id` int DEFAULT NULL,
  `Delivery_fee` double DEFAULT NULL,
  `Delivery_Status` varchar(255) DEFAULT NULL,
  `Delivery_type` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_customer_invoice` (`Customer_ID`),
  KEY `FK_staff_invoice` (`admin_in_charge_id`),
  KEY `receiver_account_id` (`receiver_account_id`),
  CONSTRAINT `FK_customer_invoice` FOREIGN KEY (`Customer_ID`) REFERENCES `customers` (`id`),
  CONSTRAINT `FK_staff_invoice` FOREIGN KEY (`admin_in_charge_id`) REFERENCES `staffs` (`id`),
  CONSTRAINT `invoice_ibfk_1` FOREIGN KEY (`receiver_account_id`) REFERENCES `online_payment_accounts` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `invoice`
--

LOCK TABLES `invoice` WRITE;
/*!40000 ALTER TABLE `invoice` DISABLE KEYS */;
INSERT INTO `invoice` VALUES (1,1,'2023-10-02',NULL,'UNPAID','ACCEPTED','COD',NULL,0,'USD','abcd',3600,NULL,0,NULL,NULL,NULL,NULL,NULL,NULL),(2,1,'2023-10-02',NULL,'UNPAID','PAYMENT_WAITING','BANK_TRANSFER','',0,'USD','',5618,'',0,'',NULL,NULL,NULL,NULL,NULL),(3,1,'2023-10-02',NULL,'UNPAID','PAYMENT_WAITING','PAYPAL','',0,'USD','',959,'',0,'',NULL,NULL,NULL,NULL,NULL);
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
INSERT INTO `invoices_with_products` VALUES (1,7,1),(2,11,1),(2,17,1),(2,28,1),(3,26,1);
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
) ENGINE=InnoDB AUTO_INCREMENT=65 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `login_accounts`
--

LOCK TABLES `login_accounts` WRITE;
/*!40000 ALTER TABLE `login_accounts` DISABLE KEYS */;
INSERT INTO `login_accounts` VALUES (0,'unknown','123','ADMIN','ALLOWED',NULL),(1,'admin','$2a$10$7jxw1kP1KDMTFzDEtWeDuOpKfzOmW0lmeRdYKsIKksX8wdZVGEtMe','ADMIN','ALLOWED','eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsImlhdCI6MTcwMTU5OTIzNywiZXhwIjoxNzAxNjg1NjM3fQ.d_N8lPN8jrJ_nVca-hiS9W6e9N0egjGryHGcUTd8Rro'),(2,'user','$2a$10$7jxw1kP1KDMTFzDEtWeDuOpKfzOmW0lmeRdYKsIKksX8wdZVGEtMe','CUSTOMER','ALLOWED','eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1c2VyIiwiaWF0IjoxNzAwOTk1Nzk3LCJleHAiOjE3MDEwODIxOTd9.jJqMASA25NyB9EsG0whgQ-idgfa8hOHHuMztCyubMW8'),(3,'nhu0707','$2a$10$7jxw1kP1KDMTFzDEtWeDuOpKfzOmW0lmeRdYKsIKksX8wdZVGEtMe','CUSTOMER','ALLOWED','eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJuaHUwNzA3IiwiaWF0IjoxNjk2MTUyMzk3LCJleHAiOjE2OTYyMzg3OTd9.7POixRXoyzuY1eBDGrcHJhl-skAWN8c-mLcwTjPlVFg'),(7,'sang236','$2a$10$7jxw1kP1KDMTFzDEtWeDuOpKfzOmW0lmeRdYKsIKksX8wdZVGEtMe','CUSTOMER','BANNED',NULL),(8,'shipper','$2a$10$7jxw1kP1KDMTFzDEtWeDuOpKfzOmW0lmeRdYKsIKksX8wdZVGEtMe','SHIPPER','ALLOWED','eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJzaGlwcGVyIiwiaWF0IjoxNjkyODk3MTA1LCJleHAiOjE2OTI5ODM1MDV9.7so_xwVTJfYtuAXNgvhFYSQXZSDEQWxhHWJqUL9ds7s'),(9,'tester','$2a$10$7jxw1kP1KDMTFzDEtWeDuOpKfzOmW0lmeRdYKsIKksX8wdZVGEtMe','CUSTOMER','ALLOWED','eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0ZXIiLCJpYXQiOjE2OTI4MDI4MzMsImV4cCI6MTY5Mjg4OTIzM30.LLZJ_PKOBvcYv8SWoaOGMLW7USOvSYjIH7u-GIgKaSU'),(11,'qweqwe','qweqwe','CUSTOMER','ALLOWED',NULL),(12,'quochoang','123','CUSTOMER','ALLOWED',NULL),(13,'ducngu','123','CUSTOMER','BANNED',NULL),(50,'qhoang','123','CUSTOMER','ALLOWED',NULL),(51,'anhkhoa','123','CUSTOMER','ALLOWED',NULL),(52,'kien','123','CUSTOMER','ALLOWED',NULL),(63,'user1','$2a$10$7jxw1kP1KDMTFzDEtWeDuOpKfzOmW0lmeRdYKsIKksX8wdZVGEtMe','CUSTOMER','ALLOWED',NULL),(64,'user3','$2a$10$es2Ds7Q3fdP8PEyYh24d5eSbDBSTeg787rtM6jgAtAu71ZGqGVpD6','CUSTOMER','ALLOWED',NULL);
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
  `id` bigint NOT NULL AUTO_INCREMENT,
  `type` varchar(20) NOT NULL,
  `content` text NOT NULL,
  `additional_data` text,
  `receiver_login_account_id` bigint NOT NULL,
  `notification_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `receiver_login_account_id` (`receiver_login_account_id`),
  CONSTRAINT `notification_ibfk_1` FOREIGN KEY (`receiver_login_account_id`) REFERENCES `login_accounts` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `notification`
--

LOCK TABLES `notification` WRITE;
/*!40000 ALTER TABLE `notification` DISABLE KEYS */;
INSERT INTO `notification` VALUES (1,'ORDER','New order','',0,'2023-09-10 00:00:00'),(2,'DELIVERY','We are packing your order',NULL,2,'2023-09-12 00:00:00');
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
INSERT INTO `online_payment_accounts` VALUES (1,'0977815809','NGUYEN HOANG SANG','','MOMO'),(2,'03365672301','NGUYEN HOANG SANG','TpBank','BANK_TRANSFER'),(3,'0123456789','NGUYEN HOANG SANG','','PAYPAL');
/*!40000 ALTER TABLE `online_payment_accounts` ENABLE KEYS */;
UNLOCK TABLES;

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
INSERT INTO `product_images_management` VALUES (1,'red','https://assets.adidas.com/images/h_840,f_auto,q_auto,fl_lossy,c_fill,g_auto/49808757050946de8bedae29011926b5_9366/Manchester_United_22-23_Home_Jersey_Red_H13881_21_model.jpg','https://assets.adidas.com/images/h_840,f_auto,q_auto,fl_lossy,c_fill,g_auto/8a9f28ca1b834e2e86ccae2901192e37_9366/Ao_DJau_San_Nha_Manchester_United_22-23_DJo_H13881_22_model.jpg','https://assets.adidas.com/images/h_840,f_auto,q_auto,fl_lossy,c_fill,g_auto/dd570e1feae0414b98e3ae2901193687_9366/Ao_DJau_San_Nha_Manchester_United_22-23_DJo_H13881_23_hover_model.jpg','https://assets.adidas.com/images/h_840,f_auto,q_auto,fl_lossy,c_fill,g_auto/693f5fb49f4c45078f9aae6f00d4c19e_9366/Ao_DJau_San_Nha_Manchester_United_22-23_DJo_H13881_01_laydown.jpg'),(1,'white','https://assets.adidas.com/images/h_840,f_auto,q_auto,fl_lossy,c_fill,g_auto/2700b53701664eb8b277ae2f00df6e5d_9366/Ao_DJau_San_Khach_Manchester_United_22-23_trang_H13880_21_model.jpg','https://assets.adidas.com/images/h_840,f_auto,q_auto,fl_lossy,c_fill,g_auto/4d02848d2ad746f185e3ae2f00df7641_9366/Ao_DJau_San_Khach_Manchester_United_22-23_trang_H13880_22_model.jpg','https://assets.adidas.com/images/h_840,f_auto,q_auto,fl_lossy,c_fill,g_auto/1733a67776594a838a6cae2f00df7e7e_9366/Ao_DJau_San_Khach_Manchester_United_22-23_trang_H13880_23_hover_model.jpg','https://assets.adidas.com/images/h_840,f_auto,q_auto,fl_lossy,c_fill,g_auto/84bd0c3ab78f49808d78ae2f00df8697_9366/Ao_DJau_San_Khach_Manchester_United_22-23_trang_H13880_25_model.jpg'),(2,'black','https://media.gucci.com/style/HEXEAF2DC_Center_0_0_800x800/1653583557/699268_UXX0G_1000_003_100_0000_Light-Gucci-Blondie-shoulder-bag.jpg','https://media.gucci.com/style/HEXEAF2DC_Center_0_0_800x800/1652714104/699268_UXX0G_1000_001_073_0000_Light-Gucci-Blondie-shoulder-bag.jpg','https://media.gucci.com/style/HEXEAF2DC_Center_0_0_800x800/1652714105/699268_UXX0G_1000_002_073_0000_Light-Gucci-Blondie-shoulder-bag.jpg','https://media.gucci.com/style/HEXEAF2DC_Center_0_0_800x800/1653583559/699268_UXX0G_1000_005_100_0000_Light-Gucci-Blondie-shoulder-bag.jpg'),(2,'pink','https://media.gucci.com/style/HEXF1E9FB_Center_0_0_800x800/1670024750/699268_UXX0G_6910_004_100_0000_Light-Gucci-Blondie-shoulder-bag.jpg','https://media.gucci.com/style/HEXF1E9FB_Center_0_0_800x800/1675811856/699268_UXX0G_6910_006_100_0000_Light-Gucci-Blondie-shoulder-bag.jpg','https://media.gucci.com/style/HEXF1E9FB_Center_0_0_800x800/1674688592/699268_UXX0G_6910_007_073_0000_Light-Gucci-Blondie-shoulder-bag.jpg','https://media.gucci.com/style/HEXF1E9FB_Center_0_0_800x800/1674688596/699268_UXX0G_6910_009_073_0000_Light-Gucci-Blondie-shoulder-bag.jpg'),(3,'none','https://media.gucci.com/style/HEXF1E9FB_Center_0_0_800x800/1669920431/724770_XKCWH_8050_002_100_0000_Light-Striped-jacquard-wool-knit-sweater.jpg','https://media.gucci.com/style/HEXF1E9FB_Center_0_0_800x800/1669920428/724770_XKCWH_8050_001_100_0000_Light-Striped-jacquard-wool-knit-sweater.jpg','https://media.gucci.com/style/HEXF1E9FB_Center_0_0_800x800/1669920433/724770_XKCWH_8050_003_100_0000_Light-Striped-jacquard-wool-knit-sweater.jpg','https://media.gucci.com/style/HEXF1E9FB_Center_0_0_800x800/1669920435/724770_XKCWH_8050_005_100_0000_Light-Striped-jacquard-wool-knit-sweater.jpg'),(4,'none','https://static.nike.com/a/images/t_PDP_864_v1/f_auto,b_rgb:f5f5f5/029d86dd-1549-4221-a18b-25d165998d1f/air-max-90-se-shoes-ltJLHs.png','https://static.nike.com/a/images/t_PDP_864_v1/f_auto,b_rgb:f5f5f5/eceb6b53-06fc-4182-a450-b16620dc5295/air-max-90-se-shoes-ltJLHs.png','https://static.nike.com/a/images/t_PDP_864_v1/f_auto,b_rgb:f5f5f5/4bb1f419-b093-4d53-b120-5607311bafeb/air-max-90-se-shoes-ltJLHs.png','https://static.nike.com/a/images/t_PDP_864_v1/f_auto,b_rgb:f5f5f5/92d5efd4-727c-4a43-93ac-14a3099591dc/air-max-90-se-shoes-ltJLHs.png'),(5,'none','https://www.dolcegabbana.com/dw/image/v2/AAGA_PRD/on/demandware.static/-/Sites-15/default/dwa68a4484/images/zoom/F6R8DTFUGOI_N0000_1.jpg?sw=742&sh=944&sm=fit','https://www.dolcegabbana.com/dw/image/v2/AAGA_PRD/on/demandware.static/-/Sites-15/default/dw06bbc59a/images/zoom/F6R8DTFUGOI_N0000_2.jpg?sw=742&sh=944&sm=fit','https://www.dolcegabbana.com/dw/image/v2/AAGA_PRD/on/demandware.static/-/Sites-15/default/dw24c729fa/images/zoom/F6R8DTFUGOI_N0000_3.jpg?sw=742&sh=944&sm=fit','https://www.dolcegabbana.com/dw/image/v2/AAGA_PRD/on/demandware.static/-/Sites-15/default/dwf8349ed3/images/zoom/F6R8DTFUGOI_N0000_11.jpg?sw=742&sh=944&sm=fit'),(6,'none','https://static.nike.com/a/images/t_PDP_864_v1/f_auto,b_rgb:f5f5f5/b75092c8-5853-4d15-bd57-c6dca886dd57/jordan-flight-mvp-fleece-pullover-hoodie-Z7kcns.png','https://static.nike.com/a/images/t_PDP_864_v1/f_auto,b_rgb:f5f5f5/5f7394a7-8c5e-4f5f-9782-40b932a93c36/jordan-flight-mvp-fleece-pullover-hoodie-Z7kcns.png','https://static.nike.com/a/images/t_PDP_864_v1/f_auto,b_rgb:f5f5f5/9a235f1a-fcfa-4c56-85ec-53225a27d5e6/jordan-flight-mvp-fleece-pullover-hoodie-Z7kcns.png','https://static.nike.com/a/images/t_PDP_864_v1/f_auto,b_rgb:f5f5f5/07c47509-10dd-45d5-b7ea-1ef57b8e4aea/jordan-flight-mvp-fleece-pullover-hoodie-Z7kcns.png'),(7,'red','https://www.chanel.com/images//t_one///q_auto:good,f_auto,fl_lossy,dpr_1.2/w_620/large-back-pack-chanel-22-red-white-shiny-calfskin-gold-tone-metal-shiny-calfskin-gold-tone-metal--packshot-artistique-vue1-as3313b09981nm243-9516416729118.jpg','https://www.chanel.com/images//t_one///q_auto:good,f_auto,fl_lossy,dpr_1.2/w_620/large-back-pack-chanel-22-red-white-shiny-calfskin-gold-tone-metal-shiny-calfskin-gold-tone-metal--packshot-artistique-vue2-as3313b09981nm243-9516416794654.jpg','https://www.chanel.com/images//t_one///q_auto:good,f_auto,fl_lossy,dpr_1.2/w_620/large-back-pack-chanel-22-red-white-shiny-calfskin-gold-tone-metal-shiny-calfskin-gold-tone-metal--packshot-artistique-vue3-as3313b09981nm243-9516424790046.jpg','https://www.chanel.com/images//t_one///q_auto:good,f_auto,fl_lossy,dpr_1.2/w_620/large-back-pack-chanel-22-red-white-shiny-calfskin-gold-tone-metal-shiny-calfskin-gold-tone-metal--packshot-artistique-vue4-as3313b09981nm243-9516430884894.jpg'),(7,'white','https://www.chanel.com/images//t_one///q_auto:good,f_auto,fl_lossy,dpr_1.2/w_620/large-back-pack-chanel-22-light-blue-shiny-calfskin-gold-tone-metal-shiny-calfskin-gold-tone-metal--packshot-artistique-vue1-as3313b08037nn268-9521332944926.jpg','https://www.chanel.com/images//t_one///q_auto:good,f_auto,fl_lossy,dpr_1.2/w_620/large-back-pack-chanel-22-light-blue-shiny-calfskin-gold-tone-metal-shiny-calfskin-gold-tone-metal--packshot-artistique-vue2-as3313b08037nn268-9521345888286.jpg','https://www.chanel.com/images//t_one///q_auto:good,f_auto,fl_lossy,dpr_1.2/w_620/large-back-pack-chanel-22-light-blue-shiny-calfskin-gold-tone-metal-shiny-calfskin-gold-tone-metal--packshot-artistique-vue3-as3313b08037nn268-9521535418398.jpg','https://www.chanel.com/images//t_one///q_auto:good,f_auto,fl_lossy,dpr_1.2/w_620/large-back-pack-chanel-22-light-blue-shiny-calfskin-gold-tone-metal-shiny-calfskin-gold-tone-metal--packshot-artistique-vue4-as3313b08037nn268-9521547345950.jpg'),(8,'green','https://www.chanel.com/images//t_one/t_fashion9//b_rgb:F7F7F7,e_brightness:-3/q_auto:good,f_auto,fl_lossy,dpr_1.2/w_620/butterfly-eyeglasses-dark-green-gold-acetate-glass-pearls-acetate-glass-pearls-packshot-default-a75267x08101v1702-9518592688158.jpg','https://www.chanel.com/images//t_one/t_fashion9//b_rgb:F7F7F7,e_brightness:-3/q_auto:good,f_auto,fl_lossy,dpr_1.2/w_620/butterfly-eyeglasses-dark-green-gold-acetate-glass-pearls-acetate-glass-pearls-packshot-alternative-a75267x08101v1702-9518591803422.jpg','https://www.chanel.com/images//t_one/t_fashion9//b_rgb:F7F7F7,e_brightness:-3/q_auto:good,f_auto,fl_lossy,dpr_1.2/w_620/butterfly-eyeglasses-dark-green-gold-acetate-glass-pearls-acetate-glass-pearls-packshot-other-a75267x08101v1702-9518592491550.jpg','https://www.chanel.com/images//t_one/t_fashion9//b_rgb:F7F7F7,e_brightness:-3/q_auto:good,f_auto,fl_lossy,dpr_1.2/w_620/butterfly-eyeglasses-dark-green-gold-acetate-glass-pearls-acetate-glass-pearls-packshot-extra-a75267x08101v1702-9518590033950.jpg'),(8,'red','https://www.chanel.com/images//t_one/t_fashion9//b_rgb:F7F7F7,e_brightness:-3/q_auto:good,f_auto,fl_lossy,dpr_1.2/w_620/butterfly-eyeglasses-brown-gold-acetate-glass-pearls-acetate-glass-pearls-packshot-default-a75267x08101v1722-9518588788766.jpg','https://www.chanel.com/images//t_one/t_fashion9//b_rgb:F7F7F7,e_brightness:-3/q_auto:good,f_auto,fl_lossy,dpr_1.2/w_620/butterfly-eyeglasses-brown-gold-acetate-glass-pearls-acetate-glass-pearls-packshot-alternative-a75267x08101v1722-9518587379742.jpg','https://www.chanel.com/images//t_one/t_fashion9//b_rgb:F7F7F7,e_brightness:-3/q_auto:good,f_auto,fl_lossy,dpr_1.2/w_620/butterfly-eyeglasses-brown-gold-acetate-glass-pearls-acetate-glass-pearls-packshot-other-a75267x08101v1722-9518584725534.jpg','https://www.chanel.com/images//t_one/t_fashion9//b_rgb:F7F7F7,e_brightness:-3/q_auto:good,f_auto,fl_lossy,dpr_1.2/w_620/butterfly-eyeglasses-brown-gold-acetate-glass-pearls-acetate-glass-pearls-packshot-extra-a75267x08101v1722-9518591737886.jpg'),(8,'white','https://www.chanel.com/images//t_one/t_fashion9//b_rgb:F7F7F7,e_brightness:-3/q_auto:good,f_auto,fl_lossy,dpr_1.2/w_620/butterfly-eyeglasses-dark-beige-gold-acetate-glass-pearls-acetate-glass-pearls-packshot-default-a75267x08101v1719-9518590099486.jpg','https://www.chanel.com/images//t_one/t_fashion9//b_rgb:F7F7F7,e_brightness:-3/q_auto:good,f_auto,fl_lossy,dpr_1.2/w_620/butterfly-eyeglasses-dark-beige-gold-acetate-glass-pearls-acetate-glass-pearls-packshot-alternative-a75267x08101v1719-9518592819230.jpg','https://www.chanel.com/images//t_one/t_fashion9//b_rgb:F7F7F7,e_brightness:-3/q_auto:good,f_auto,fl_lossy,dpr_1.2/w_620/butterfly-eyeglasses-dark-beige-gold-acetate-glass-pearls-acetate-glass-pearls-packshot-other-a75267x08101v1719-9518590263326.jpg','https://www.chanel.com/images//t_one/t_fashion9//b_rgb:F7F7F7,e_brightness:-3/q_auto:good,f_auto,fl_lossy,dpr_1.2/w_620/butterfly-eyeglasses-dark-beige-gold-acetate-glass-pearls-acetate-glass-pearls-packshot-extra-a75267x08101v1719-9518587871262.jpg');
/*!40000 ALTER TABLE `product_images_management` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product_import_management`
--

DROP TABLE IF EXISTS `product_import_management`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product_import_management` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `product_management_id` bigint NOT NULL,
  `import_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `import_quantity` bigint NOT NULL DEFAULT '0',
  `out_of_stock_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_Products_Management_Products_Import_Management` (`product_management_id`),
  CONSTRAINT `FK_Products_Management_Products_Import_Management` FOREIGN KEY (`product_management_id`) REFERENCES `products_management` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=98 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product_import_management`
--

LOCK TABLES `product_import_management` WRITE;
/*!40000 ALTER TABLE `product_import_management` DISABLE KEYS */;
INSERT INTO `product_import_management` VALUES (1,1,'2022-12-01 00:00:00',10,NULL),(2,3,'2022-12-01 00:00:00',10,NULL),(3,4,'2022-12-01 00:00:00',10,NULL),(4,5,'2022-12-01 00:00:00',10,NULL),(5,6,'2022-12-01 00:00:00',10,NULL),(6,7,'2022-12-01 00:00:00',10,NULL),(7,8,'2022-12-01 00:00:00',10,NULL),(8,11,'2022-12-01 00:00:00',10,NULL),(9,12,'2022-12-01 00:00:00',10,NULL),(10,13,'2022-12-01 00:00:00',10,NULL),(11,14,'2022-12-01 00:00:00',10,NULL),(12,17,'2022-12-01 00:00:00',10,NULL),(13,18,'2022-12-01 00:00:00',10,NULL),(14,19,'2022-12-01 00:00:00',10,NULL),(15,20,'2022-12-01 00:00:00',10,NULL),(16,21,'2022-12-01 00:00:00',10,NULL),(17,22,'2022-12-01 00:00:00',10,NULL),(18,23,'2022-12-01 00:00:00',10,NULL),(19,24,'2022-12-01 00:00:00',10,NULL),(20,25,'2022-12-01 00:00:00',10,NULL),(21,26,'2022-12-01 00:00:00',10,NULL),(22,27,'2022-12-01 00:00:00',10,NULL),(23,28,'2022-12-01 00:00:00',10,NULL);
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
 1 AS `description`,
 1 AS `import_date`*/;
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
  PRIMARY KEY (`id`),
  UNIQUE KEY `name_UNIQUE` (`name`),
  KEY `product_name_index` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=41 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `products`
--

LOCK TABLES `products` WRITE;
/*!40000 ALTER TABLE `products` DISABLE KEYS */;
INSERT INTO `products` VALUES (1,'Manchester United 22/23',950,650,'addidas',10,'A supporter jersey made with recycled materials.\n\nTurned up or pressed down, the humble polo collar has played a starring role in many of Manchester United\'s biggest moments. Making a comeback on this adidas football jersey, it joins a shield-style badge and engineered pinstripe graphic to produce an eye-catching look. Moisture-absorbing AEROREADY and mesh panels make it a comfortable choice for passionate supporters.\n\nMade with 100% recycled materials, this product represents just one of our solutions to help end plastic waste.'),(2,'Gucci Blondie shoulder bag',3600,2500,'gucci',0,'\n\nVintage elements are paired with archival details as an ode to the glamour that permeates Gucci\'s latest collections. This shoulder bag pairs a delicate chain strap with soft leather to infuse the accessory with a timeless feel. Reintroduced in honor of the collection, the rounded silhouette is completed by a historical, rounded iteration of Guccio Gucci\'s monogram.'),(3,'Striped jacquard wool knit sweater',2600,1400,'gucci',0,'\n\nThe Cruise 2023 Gucci Cosmogonie collection was presented against the backdrop of the historic Castel del Monte in Italy. The show brought together aesthetics from distant eras and geographies and linked elements from the past with the present. This multicolor striped jacquard wool sweater has the image of the jester paired with the Interlocking G on the front.'),(4,'Nike Air Max 90 SE',2750,1600,'nike',15,'What moves you? Find out in the Air Max 90 SE. Hemp accents, durable textile and playful \"NIKE MOVING CO.\" details celebrate getting going. Snail-trail deco stitching across the mudguard adds a fun take to the outdoorsy aesthetic. And its Waffle outsole and exposed Air cushioning keep the tried-and-tested feel under your feet. So, where next?'),(5,'Jersey midi dress with sequins',2065,1300,'dolce&gabbana',0,'Dolce&Gabbana has always loved contrasts and thinks of strong contemporary women with a lot of personality. Garments with enveloping silhouettes, such as this sheath dress with fusible rhinestone embellishment, come in future-looking materials that will highlight all your feminine sensuality. '),(6,'Jordan Flight MVP',800,550,'nike',0,'Spring\'s here, gear up. This fleece hoodie will keep you cosy—and a big ol\' Jordan graphic busting through on the back keeps things fun.'),(7,'Chanel backpack 22',6627,5123,'chanel',10,'Glossy Calfskin & Light Blue Gold Plated Metal'),(8,'Butterfly glasses',953,721,'chanel',0,'Acetate & Glass Pearls Dark Green & Gold');
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
  `available_quantity` bigint NOT NULL DEFAULT '0',
  `sold_quantity` bigint NOT NULL DEFAULT '0',
  `one_star_quantity` bigint DEFAULT '0',
  `two_star_quantity` bigint DEFAULT '0',
  `three_star_quantity` bigint DEFAULT '0',
  `four_star_quantity` bigint DEFAULT '0',
  `five_star_quantity` bigint DEFAULT '0',
  `overall_rating` int DEFAULT '0',
  `import_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_Products_Products_Management` (`product_id`),
  CONSTRAINT `FK_Products_Products_Management` FOREIGN KEY (`product_id`) REFERENCES `products` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=276 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `products_management`
--

LOCK TABLES `products_management` WRITE;
/*!40000 ALTER TABLE `products_management` DISABLE KEYS */;
INSERT INTO `products_management` VALUES (1,1,'red','s',10,1,1,0,2,0,2,3,'2022-12-01 00:00:00'),(3,1,'red','l',10,1,1,0,2,0,2,3,'2022-12-01 00:00:00'),(4,1,'white','s',15,0,0,0,0,1,1,4,'2022-12-01 00:00:00'),(5,1,'white','m',15,0,0,0,0,1,1,4,'2022-12-01 00:00:00'),(6,1,'white','l',15,2,0,0,0,1,2,4,'2022-12-01 00:00:00'),(7,2,'pink','none',10,1,0,0,0,1,1,4,'2022-12-01 00:00:00'),(8,2,'black','none',10,4,0,0,1,0,0,3,'2022-12-01 00:00:00'),(11,3,'none','l',3,3,0,0,0,0,4,5,'2022-12-01 00:00:00'),(12,4,'none','40',10,4,0,1,0,0,1,3,'2022-12-01 00:00:00'),(13,4,'none','40.5',12,3,0,1,0,0,1,3,'2022-12-01 00:00:00'),(14,4,'none','41',12,5,0,1,0,2,1,3,'2022-12-01 00:00:00'),(17,5,'none','36',8,3,0,0,0,1,1,4,'2022-12-01 00:00:00'),(18,5,'none','38',8,3,0,0,0,1,1,4,'2022-12-01 00:00:00'),(19,5,'none','40',8,3,0,0,0,1,1,4,'2022-12-01 00:00:00'),(20,5,'none','42',8,3,0,0,0,1,1,4,'2022-12-01 00:00:00'),(21,6,'none','s',10,1,0,0,0,2,0,4,'2022-12-01 00:00:00'),(22,6,'none','m',7,0,0,0,0,1,0,4,'2022-12-01 00:00:00'),(23,6,'none','l',7,1,0,0,0,2,0,4,'2022-12-01 00:00:00'),(24,7,'white','none',10,2,0,0,0,1,1,4,'2022-12-01 00:00:00'),(25,7,'red','none',8,3,0,0,0,1,2,4,'2022-12-01 00:00:00'),(26,8,'green','none',9,3,0,1,0,1,0,3,'2022-12-01 00:00:00'),(27,8,'white','none',6,2,0,0,0,1,2,4,'2022-12-01 00:00:00'),(28,8,'red','none',9,6,1,0,0,3,1,3,'2022-12-01 00:00:00');
/*!40000 ALTER TABLE `products_management` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `refund`
--

DROP TABLE IF EXISTS `refund`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `refund` (
  `invoice_id` int NOT NULL AUTO_INCREMENT,
  `content` varchar(255) DEFAULT NULL,
  `date` datetime DEFAULT NULL,
  `evident_image` varchar(255) DEFAULT NULL,
  `refund_money` double DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `in_charge_admin_id` int DEFAULT NULL,
  PRIMARY KEY (`invoice_id`),
  KEY `FKk62gu4uso16ol1i5f2xmjo6n` (`in_charge_admin_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `refund`
--

LOCK TABLES `refund` WRITE;
/*!40000 ALTER TABLE `refund` DISABLE KEYS */;
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
  KEY `FK_Staffs_Accounts` (`account_id`),
  CONSTRAINT `FK_Staffs_Accounts` FOREIGN KEY (`account_id`) REFERENCES `login_accounts` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `staffs`
--

LOCK TABLES `staffs` WRITE;
/*!40000 ALTER TABLE `staffs` DISABLE KEYS */;
INSERT INTO `staffs` VALUES (0,'uknown','abc','ADMIN','1900-02-02','aasfasfasf','0111111111',0,'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQaiHNXjxQrlFFFHdMGtUpH1nLDjHzyfTms6A&usqp=CAU'),(1,'Nguyen Hoang Sang','hcm','ADMIN','2001-06-23','nguyenhoangsang236@gmail.com','0123456987',1,'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQaiHNXjxQrlFFFHdMGtUpH1nLDjHzyfTms6A&usqp=CAU'),(2,'Nguyen Anh Duc','hcm','SHIPPER','2001-02-02','ducnguvcl@gmail.com','0321654987',8,'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQaiHNXjxQrlFFFHdMGtUpH1nLDjHzyfTms6A&usqp=CAU');
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
/*!50001 VIEW `cart_item_info_for_ui` AS select `c`.`id` AS `id`,`c`.`customer_id` AS `customer_id`,`c`.`product_management_id` AS `product_management_id`,`c`.`quantity` AS `quantity`,`c`.`buying_status` AS `buying_status`,`c`.`select_status` AS `select_status`,`pm`.`product_id` AS `product_id`,`pm`.`color` AS `color`,`pm`.`size` AS `size`,`p`.`name` AS `name`,`p`.`brand` AS `brand`,`p`.`selling_price` AS `selling_price`,`p`.`discount` AS `discount`,`pim`.`image_1` AS `image_1` from (((`cart` `c` join `products_management` `pm` on((`pm`.`id` = `c`.`product_management_id`))) join `products` `p` on((`pm`.`product_id` = `p`.`id`))) join `product_images_management` `pim` on((`pim`.`product_id` = `p`.`id`))) where ((`c`.`buying_status` = 0) and (`pim`.`color` = `pm`.`color`)) order by `c`.`id` */;
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
/*!50001 VIEW `product_info_for_ui` AS select `pm`.`id` AS `id`,`pm`.`product_id` AS `product_id`,`p`.`name` AS `name`,`pm`.`size` AS `size`,`p`.`selling_price` AS `selling_price`,`p`.`original_price` AS `original_price`,`p`.`discount` AS `discount`,`p`.`brand` AS `brand`,`pm`.`color` AS `color`,`pm`.`available_quantity` AS `available_quantity`,`pim`.`image_1` AS `image_1`,`pim`.`image_2` AS `image_2`,`pim`.`image_3` AS `image_3`,`pim`.`image_4` AS `image_4`,`pm`.`overall_rating` AS `overall_rating`,`p`.`description` AS `description`,`pm`.`import_date` AS `import_date` from ((`products` `p` left join `products_management` `pm` on((`p`.`id` = `pm`.`product_id`))) join `product_images_management` `pim` on((`p`.`id` = `pim`.`product_id`))) where ((`pim`.`color` = `pm`.`color`) and (`pm`.`id`,`pm`.`product_id`,`pim`.`color`) in (select min(`pm`.`id`) AS `id`,`pim`.`product_id`,`pim`.`color` from (`product_images_management` `pim` left join `products_management` `pm` on(((`pim`.`product_id` = `pm`.`product_id`) and (`pim`.`color` = `pm`.`color`)))) group by `pim`.`product_id`,`pim`.`color`)) */;
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
/*!50001 VIEW `top_8_best_sell_products` AS select `pm`.`id` AS `id`,`pm`.`product_id` AS `product_id`,`p`.`name` AS `name`,`pm`.`size` AS `size`,`p`.`selling_price` AS `selling_price`,`p`.`discount` AS `discount`,`p`.`brand` AS `brand`,`pm`.`color` AS `color`,`pm`.`available_quantity` AS `available_quantity`,`pim`.`image_1` AS `image_1`,`pim`.`image_2` AS `image_2`,`pim`.`image_3` AS `image_3`,`pim`.`image_4` AS `image_4`,`pm`.`overall_rating` AS `overall_rating`,`p`.`description` AS `description` from (((`products` `p` left join `products_management` `pm` on((`p`.`id` = `pm`.`product_id`))) join `product_images_management` `pim` on((`p`.`id` = `pim`.`product_id`))) join (select `pm`.`id` AS `id` from ((((`products_management` `pm` join `invoices_with_products` `iwp` on((`pm`.`id` = `iwp`.`Product_Management_ID`))) join `invoice` `i` on((`iwp`.`Invoice_ID` = `i`.`ID`))) join `products` `p` on((`p`.`id` = `pm`.`product_id`))) join `product_images_management` `pim` on((`pim`.`product_id` = `p`.`id`))) where ((`i`.`Invoice_Date` <= cast(now() as date)) and (`i`.`Invoice_Date` >= cast((now() - interval 30 day) as date))) group by `pm`.`id` order by (select sum(`invoices_with_products`.`Quantity`) from ((`invoices_with_products` join `products_management` `pm` on((`pm`.`id` = `invoices_with_products`.`Product_Management_ID`))) join `products` `p` on((`p`.`id` = `pm`.`product_id`))) where (`p`.`name` = `p`.`name`)) desc limit 8) `tmp` on((`tmp`.`id` = `pm`.`id`))) where (`pim`.`color` = `pm`.`color`) */;
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

-- Dump completed on 2023-12-03 17:40:47
