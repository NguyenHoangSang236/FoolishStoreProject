CREATE DATABASE  IF NOT EXISTS `fashionstorewebsite` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `fashionstorewebsite`;
-- MySQL dump 10.13  Distrib 8.0.29, for Linux (x86_64)
--
-- Host: localhost    Database: fashionstorewebsite
-- ------------------------------------------------------
-- Server version	8.0.32-0ubuntu0.22.04.2

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
  PRIMARY KEY (`id`),
  KEY `FK_Customer_Cart` (`customer_id`),
  KEY `FK_Product_Cart_idx` (`product_management_id`),
  CONSTRAINT `FK_Customer_Cart` FOREIGN KEY (`customer_id`) REFERENCES `customers` (`id`),
  CONSTRAINT `FK_Product_Cart` FOREIGN KEY (`product_management_id`) REFERENCES `products_management` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cart`
--

LOCK TABLES `cart` WRITE;
/*!40000 ALTER TABLE `cart` DISABLE KEYS */;
INSERT INTO `cart` VALUES (1,1,5,2,'NOT_BOUGHT_YET'),(2,1,1,2,'NOT_BOUGHT_YET'),(3,1,11,2,'NOT_BOUGHT_YET'),(4,1,3,2,'BOUGHT'),(5,1,4,6,'BOUGHT');
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
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `catalog`
--

LOCK TABLES `catalog` WRITE;
/*!40000 ALTER TABLE `catalog` DISABLE KEYS */;
INSERT INTO `catalog` VALUES (1,'Jackets'),(2,'Glasses'),(3,'Shoes'),(4,'Male'),(5,'Female'),(6,'Accessories'),(7,'Bags and Purses'),(8,'Sport');
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
INSERT INTO `catalogs_with_products` VALUES (4,1),(8,1),(5,2),(6,2),(7,2),(4,3),(3,4),(4,4),(5,5);
/*!40000 ALTER TABLE `catalogs_with_products` ENABLE KEYS */;
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
  `customer_id` bigint NOT NULL,
  `comment_content` text NOT NULL,
  `comment_date` datetime NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_Customer_Comments` (`customer_id`),
  KEY `FK_Product_Comments` (`product_id`),
  CONSTRAINT `FK_Customer_Comments` FOREIGN KEY (`customer_id`) REFERENCES `customers` (`id`),
  CONSTRAINT `FK_Product_Comments` FOREIGN KEY (`product_id`) REFERENCES `products` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `comments`
--

LOCK TABLES `comments` WRITE;
/*!40000 ALTER TABLE `comments` DISABLE KEYS */;
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
  `country` varchar(50) DEFAULT NULL,
  `address` varchar(50) DEFAULT NULL,
  `city` varchar(50) DEFAULT NULL,
  `avatar` varchar(500) DEFAULT 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQaiHNXjxQrlFFFHdMGtUpH1nLDjHzyfTms6A&usqp=CAU',
  PRIMARY KEY (`id`),
  KEY `FK_Customers_Accounts` (`account_id`),
  CONSTRAINT `FK_Customers_Accounts` FOREIGN KEY (`account_id`) REFERENCES `login_accounts` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customers`
--

LOCK TABLES `customers` WRITE;
/*!40000 ALTER TABLE `customers` DISABLE KEYS */;
INSERT INTO `customers` VALUES (1,'Pham Hoang Phuc','phuclateo@gmail.com','0123456987',2,NULL,NULL,NULL,'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQaiHNXjxQrlFFFHdMGtUpH1nLDjHzyfTms6A&usqp=CAU'),(2,'Nguyen Quynh Nhu','nnhu7721@gmail.com','0213654798',3,NULL,NULL,NULL,'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQaiHNXjxQrlFFFHdMGtUpH1nLDjHzyfTms6A&usqp=CAU'),(3,'Nguyen Hoang Sang','19110120@student.hcmute.edu.vn','0977815809',7,NULL,NULL,NULL,'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQaiHNXjxQrlFFFHdMGtUpH1nLDjHzyfTms6A&usqp=CAU'),(4,'Nguyen Thi Hoang Trang','pbeltranster@gmail.com','0321654987',9,NULL,NULL,NULL,'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQaiHNXjxQrlFFFHdMGtUpH1nLDjHzyfTms6A&usqp=CAU'),(6,'qweqwe','qwe@we','1234567890',11,NULL,NULL,NULL,'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQaiHNXjxQrlFFFHdMGtUpH1nLDjHzyfTms6A&usqp=CAU'),(7,'Nguyen Quoc Heng','nqh130901@gmail.com','1234567890',12,NULL,NULL,NULL,'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQaiHNXjxQrlFFFHdMGtUpH1nLDjHzyfTms6A&usqp=CAU'),(8,'Duc Ngu Vcl','ducngu@gmail.com','0321654987',13,NULL,NULL,NULL,'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQaiHNXjxQrlFFFHdMGtUpH1nLDjHzyfTms6A&usqp=CAU'),(12,'guyen an\0\0','nguyenvana@gmail.com','0123456789',47,NULL,NULL,NULL,NULL);
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
  `shipper_id` bigint NOT NULL,
  `delivery_date` date DEFAULT NULL,
  `current_status` varchar(20) NOT NULL,
  `additional_shipper_comment` text,
  `evidence_image` longblob,
  PRIMARY KEY (`id`),
  KEY `FK_Invoice_delivery` (`invoice_id`),
  KEY `FK_Staff_delivery` (`shipper_id`),
  CONSTRAINT `FK_Invoice_delivery` FOREIGN KEY (`invoice_id`) REFERENCES `invoice` (`ID`),
  CONSTRAINT `FK_Staff_delivery` FOREIGN KEY (`shipper_id`) REFERENCES `staffs` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `delivery`
--

LOCK TABLES `delivery` WRITE;
/*!40000 ALTER TABLE `delivery` DISABLE KEYS */;
/*!40000 ALTER TABLE `delivery` ENABLE KEYS */;
UNLOCK TABLES;

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
  `Payment_Status` tinyint NOT NULL DEFAULT '0',
  `Delivery_Status` varchar(20) NOT NULL DEFAULT 'WAITING_ACCEPTANCE',
  `Refund_Percentage` double DEFAULT '0',
  `Reason` text,
  `Currency` varchar(10) NOT NULL DEFAULT 'USD',
  `Payment_Method` varchar(10) NOT NULL DEFAULT 'COD',
  `Description` text,
  `Intent` text,
  `admin_acceptance` varchar(20) NOT NULL DEFAULT 'WAITING',
  `total_price` double DEFAULT '0',
  `online_payment_account` text,
  PRIMARY KEY (`ID`),
  KEY `FK_customer_invoice` (`Customer_ID`),
  CONSTRAINT `FK_customer_invoice` FOREIGN KEY (`Customer_ID`) REFERENCES `customers` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `invoice`
--

LOCK TABLES `invoice` WRITE;
/*!40000 ALTER TABLE `invoice` DISABLE KEYS */;
INSERT INTO `invoice` VALUES (1,1,'2023-03-10',1,'SHIPPED',0,NULL,'USD','PAYPAL',NULL,NULL,'ACCEPTED',0,'sb-2437s98400372@personal.example.com'),(2,1,'2023-03-10',1,'CANCEL',100,'admin\'s reasons are written herer ...','USD','VNPAY',NULL,'akdgfg','ACCEPTED',0,NULL),(3,1,'2023-03-10',0,'ACCEPTANCE_WAITING',0,NULL,'USD','COD','alo alo',NULL,'WAITING',0,NULL),(4,1,'2023-03-10',0,'CANCEL',0,'The shop caught fire !!','USD','COD','alo alo',NULL,'REFUSED',0,NULL),(5,2,'2023-03-10',0,'ACCEPTANCE_WAITING',0,NULL,'USD','COD','nguyen quynh nhu',NULL,'WAITING',0,NULL),(6,1,'2023-03-10',1,'CANCEL',0,'shipper comments are written here...','USD','PAYPAL','alo alo','hhhhhhhhhhhhhhhhhh','ACCEPTED',0,'sb-2437s98400372@personal.example.com'),(7,1,'2023-03-10',0,'PACKING',0,NULL,'USD','COD','alo alo','hhhhhhhhhhhhhhhhhh','ACCEPTED',0,NULL),(8,2,'2023-03-10',0,'SHIPPING',0,NULL,'USD','COD','huhuhuhuu','kakakakak','ACCEPTED',0,NULL),(9,2,'2023-03-10',0,'NOT_SHIPPED',0,'customer canceled order before admin accept','USD','COD','huhuhuhuu','kakakakak','WAITING',0,''),(10,1,'2023-03-17',0,'PACKING',0,'','USD','COD','alo alo','hhhhhhhhhhhhhhhhhh','ACCEPTED',0,''),(11,2,'2023-03-17',1,'SHIPPING',0,'','USD','PAYPAL','duc ngu','phuc ngu','ACCEPTED',0,'sb-2437s98400372@personal.example.com');
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
 1 AS `Product_Management_ID`,
 1 AS `Quantity`,
 1 AS `product_id`,
 1 AS `color`,
 1 AS `size`,
 1 AS `one_star_quantity`,
 1 AS `two_star_quantity`,
 1 AS `three_star_quantity`,
 1 AS `four_star_quantity`,
 1 AS `five_star_quantity`,
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
INSERT INTO `invoices_with_products` VALUES (1,3,4),(1,4,1),(1,11,5),(2,1,1),(2,3,4),(3,11,3),(3,12,3),(4,1,1),(5,4,4),(6,18,1),(7,12,1),(7,20,13),(8,1,3),(9,1,3),(10,20,5),(11,13,1),(11,17,1),(11,19,5),(11,20,5);
/*!40000 ALTER TABLE `invoices_with_products` ENABLE KEYS */;
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
  `password` varchar(50) NOT NULL,
  `role` varchar(10) NOT NULL,
  `status` varchar(10) NOT NULL DEFAULT 'allowed',
  PRIMARY KEY (`id`),
  UNIQUE KEY `User_Name` (`user_name`)
) ENGINE=InnoDB AUTO_INCREMENT=48 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `login_accounts`
--

LOCK TABLES `login_accounts` WRITE;
/*!40000 ALTER TABLE `login_accounts` DISABLE KEYS */;
INSERT INTO `login_accounts` VALUES (1,'admin','123','ADMIN','allowed'),(2,'user','123','CUSTOMER','allowed'),(3,'nhu0707','B1czXA9LT3','CUSTOMER','allowed'),(7,'sang236','123','CUSTOMER','banned'),(8,'shipper','123','SHIPPER','allowed'),(9,'tester','123','CUSTOMER','allowed'),(11,'qweqwe','qweqwe','CUSTOMER','allowed'),(12,'quochoang','123','CUSTOMER','allowed'),(13,'ducngu','123','CUSTOMER','allowed'),(44,'manhngu','123','CUSTOMER','allowed'),(47,'user1','123','CUSTOMER','allowed');
/*!40000 ALTER TABLE `login_accounts` ENABLE KEYS */;
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
INSERT INTO `product_images_management` VALUES (1,'red','https://assets.adidas.com/images/h_840,f_auto,q_auto,fl_lossy,c_fill,g_auto/49808757050946de8bedae29011926b5_9366/Manchester_United_22-23_Home_Jersey_Red_H13881_21_model.jpg','https://assets.adidas.com/images/h_840,f_auto,q_auto,fl_lossy,c_fill,g_auto/49808757050946de8bedae29011926b5_9366/Manchester_United_22-23_Home_Jersey_Red_H13881_21_model.jpg','https://assets.adidas.com/images/h_840,f_auto,q_auto,fl_lossy,c_fill,g_auto/49808757050946de8bedae29011926b5_9366/Manchester_United_22-23_Home_Jersey_Red_H13881_21_model.jpg','https://assets.adidas.com/images/h_840,f_auto,q_auto,fl_lossy,c_fill,g_auto/49808757050946de8bedae29011926b5_9366/Manchester_United_22-23_Home_Jersey_Red_H13881_21_model.jpg'),(1,'white','https://assets.adidas.com/images/h_840,f_auto,q_auto,fl_lossy,c_fill,g_auto/2700b53701664eb8b277ae2f00df6e5d_9366/Ao_DJau_San_Khach_Manchester_United_22-23_trang_H13880_21_model.jpg','https://assets.adidas.com/images/h_840,f_auto,q_auto,fl_lossy,c_fill,g_auto/2700b53701664eb8b277ae2f00df6e5d_9366/Ao_DJau_San_Khach_Manchester_United_22-23_trang_H13880_21_model.jpg','https://assets.adidas.com/images/h_840,f_auto,q_auto,fl_lossy,c_fill,g_auto/2700b53701664eb8b277ae2f00df6e5d_9366/Ao_DJau_San_Khach_Manchester_United_22-23_trang_H13880_21_model.jpg','https://assets.adidas.com/images/h_840,f_auto,q_auto,fl_lossy,c_fill,g_auto/2700b53701664eb8b277ae2f00df6e5d_9366/Ao_DJau_San_Khach_Manchester_United_22-23_trang_H13880_21_model.jpg'),(2,'black','https://media.gucci.com/style/HEXEAF2DC_Center_0_0_800x800/1653583557/699268_UXX0G_1000_003_100_0000_Light-Gucci-Blondie-shoulder-bag.jpg','https://media.gucci.com/style/HEXEAF2DC_Center_0_0_800x800/1652714104/699268_UXX0G_1000_001_073_0000_Light-Gucci-Blondie-shoulder-bag.jpg','https://media.gucci.com/style/HEXEAF2DC_Center_0_0_800x800/1652714105/699268_UXX0G_1000_002_073_0000_Light-Gucci-Blondie-shoulder-bag.jpg','https://media.gucci.com/style/HEXEAF2DC_Center_0_0_800x800/1653583559/699268_UXX0G_1000_005_100_0000_Light-Gucci-Blondie-shoulder-bag.jpg'),(2,'pink','https://media.gucci.com/style/HEXF1E9FB_Center_0_0_800x800/1670024750/699268_UXX0G_6910_004_100_0000_Light-Gucci-Blondie-shoulder-bag.jpg','https://media.gucci.com/style/HEXF1E9FB_Center_0_0_800x800/1675811856/699268_UXX0G_6910_006_100_0000_Light-Gucci-Blondie-shoulder-bag.jpg','https://media.gucci.com/style/HEXF1E9FB_Center_0_0_800x800/1674688592/699268_UXX0G_6910_007_073_0000_Light-Gucci-Blondie-shoulder-bag.jpg','https://media.gucci.com/style/HEXF1E9FB_Center_0_0_800x800/1674688596/699268_UXX0G_6910_009_073_0000_Light-Gucci-Blondie-shoulder-bag.jpg'),(3,'none','https://media.gucci.com/style/HEXF1E9FB_Center_0_0_800x800/1669920431/724770_XKCWH_8050_002_100_0000_Light-Striped-jacquard-wool-knit-sweater.jpg','https://media.gucci.com/style/HEXF1E9FB_Center_0_0_800x800/1669920428/724770_XKCWH_8050_001_100_0000_Light-Striped-jacquard-wool-knit-sweater.jpg','https://media.gucci.com/style/HEXF1E9FB_Center_0_0_800x800/1669920433/724770_XKCWH_8050_003_100_0000_Light-Striped-jacquard-wool-knit-sweater.jpg','https://media.gucci.com/style/HEXF1E9FB_Center_0_0_800x800/1669920435/724770_XKCWH_8050_005_100_0000_Light-Striped-jacquard-wool-knit-sweater.jpg'),(4,'none','https://static.nike.com/a/images/t_PDP_864_v1/f_auto,b_rgb:f5f5f5/029d86dd-1549-4221-a18b-25d165998d1f/air-max-90-se-shoes-ltJLHs.png','https://static.nike.com/a/images/t_PDP_864_v1/f_auto,b_rgb:f5f5f5/eceb6b53-06fc-4182-a450-b16620dc5295/air-max-90-se-shoes-ltJLHs.png','https://static.nike.com/a/images/t_PDP_864_v1/f_auto,b_rgb:f5f5f5/4bb1f419-b093-4d53-b120-5607311bafeb/air-max-90-se-shoes-ltJLHs.png','https://static.nike.com/a/images/t_PDP_864_v1/f_auto,b_rgb:f5f5f5/92d5efd4-727c-4a43-93ac-14a3099591dc/air-max-90-se-shoes-ltJLHs.png'),(5,'none','https://www.dolcegabbana.com/dw/image/v2/AAGA_PRD/on/demandware.static/-/Sites-15/default/dwa68a4484/images/zoom/F6R8DTFUGOI_N0000_1.jpg?sw=742&sh=944&sm=fit','https://www.dolcegabbana.com/dw/image/v2/AAGA_PRD/on/demandware.static/-/Sites-15/default/dw06bbc59a/images/zoom/F6R8DTFUGOI_N0000_2.jpg?sw=742&sh=944&sm=fit','https://www.dolcegabbana.com/dw/image/v2/AAGA_PRD/on/demandware.static/-/Sites-15/default/dw24c729fa/images/zoom/F6R8DTFUGOI_N0000_3.jpg?sw=742&sh=944&sm=fit','https://www.dolcegabbana.com/dw/image/v2/AAGA_PRD/on/demandware.static/-/Sites-15/default/dwf8349ed3/images/zoom/F6R8DTFUGOI_N0000_11.jpg?sw=742&sh=944&sm=fit');
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
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product_import_management`
--

LOCK TABLES `product_import_management` WRITE;
/*!40000 ALTER TABLE `product_import_management` DISABLE KEYS */;
INSERT INTO `product_import_management` VALUES (1,1,'2022-12-01 00:00:00',10,NULL);
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
 1 AS `image_4`*/;
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
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `products`
--

LOCK TABLES `products` WRITE;
/*!40000 ALTER TABLE `products` DISABLE KEYS */;
INSERT INTO `products` VALUES (1,'Manchester United 22/23 Home Jersey',950,650,'addidas',10,'A supporter jersey made with recycled materials.\n\nTurned up or pressed down, the humble polo collar has played a starring role in many of Manchester United\'s biggest moments. Making a comeback on this adidas football jersey, it joins a shield-style badge and engineered pinstripe graphic to produce an eye-catching look. Moisture-absorbing AEROREADY and mesh panels make it a comfortable choice for passionate supporters.\n\nMade with 100% recycled materials, this product represents just one of our solutions to help end plastic waste.'),(2,'Gucci Blondie shoulder bag',3600,2500,'gucci',0,'\n\nVintage elements are paired with archival details as an ode to the glamour that permeates Gucci\'s latest collections. This shoulder bag pairs a delicate chain strap with soft leather to infuse the accessory with a timeless feel. Reintroduced in honor of the collection, the rounded silhouette is completed by a historical, rounded iteration of Guccio Gucci\'s monogram.'),(3,'Striped jacquard wool knit sweater',2600,1400,'gucci',0,'\n\nThe Cruise 2023 Gucci Cosmogonie collection was presented against the backdrop of the historic Castel del Monte in Italy. The show brought together aesthetics from distant eras and geographies and linked elements from the past with the present. This multicolor striped jacquard wool sweater has the image of the jester paired with the Interlocking G on the front.'),(4,'Nike Air Max 90 SE',2750,1600,'nike',15,'What moves you? Find out in the Air Max 90 SE. Hemp accents, durable textile and playful \"NIKE MOVING CO.\" details celebrate getting going. Snail-trail deco stitching across the mudguard adds a fun take to the outdoorsy aesthetic. And its Waffle outsole and exposed Air cushioning keep the tried-and-tested feel under your feet. So, where next?'),(5,'Jersey midi dress with sequins',2065,1300,'dolce&gabbana',0,'Dolce&Gabbana has always loved contrasts and thinks of strong contemporary women with a lot of personality. Garments with enveloping silhouettes, such as this sheath dress with fusible rhinestone embellishment, come in future-looking materials that will highlight all your feminine sensuality. ');
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
  `one_star_quantity` bigint NOT NULL DEFAULT '0',
  `two_star_quantity` bigint NOT NULL DEFAULT '0',
  `three_star_quantity` bigint NOT NULL DEFAULT '0',
  `four_star_quantity` bigint NOT NULL DEFAULT '0',
  `five_star_quantity` bigint NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `FK_Products_Products_Management` (`product_id`),
  CONSTRAINT `FK_Products_Products_Management` FOREIGN KEY (`product_id`) REFERENCES `products` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `products_management`
--

LOCK TABLES `products_management` WRITE;
/*!40000 ALTER TABLE `products_management` DISABLE KEYS */;
INSERT INTO `products_management` VALUES (1,1,'red','s',10,1,0,0,0,0,0),(3,1,'red','l',10,1,0,0,0,0,1),(4,1,'white','s',15,0,0,0,0,0,0),(5,1,'white','m',15,3,0,0,0,0,1),(6,1,'white','l',15,2,0,0,0,0,0),(7,2,'pink','none',10,1,0,0,0,1,0),(8,2,'black','none',10,4,0,0,0,0,0),(11,3,'none','l',3,3,0,0,0,0,3),(12,4,'none','40',10,4,0,0,0,0,1),(13,4,'none','40.5',12,3,0,0,0,0,1),(14,4,'none','41',12,5,0,0,0,2,1),(17,5,'none','36',8,3,0,0,0,0,1),(18,5,'none','38',8,3,0,0,0,0,1),(19,5,'none','40',8,3,0,0,0,0,1),(20,5,'none','42',8,3,0,0,0,0,1);
/*!40000 ALTER TABLE `products_management` ENABLE KEYS */;
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
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `staffs`
--

LOCK TABLES `staffs` WRITE;
/*!40000 ALTER TABLE `staffs` DISABLE KEYS */;
INSERT INTO `staffs` VALUES (1,'Nguyen Hoang Sang','hcm','admin','2001-06-23','nguyenhoangsang236@gmail.com','0123456987',1,'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQaiHNXjxQrlFFFHdMGtUpH1nLDjHzyfTms6A&usqp=CAU'),(2,'Nguyen Anh Duc','hcm','shipper','2001-02-02','ducnguvcl@gmail.com','0321654987',8,'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQaiHNXjxQrlFFFHdMGtUpH1nLDjHzyfTms6A&usqp=CAU');
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
 1 AS `name`,
 1 AS `selling_price`,
 1 AS `discount`,
 1 AS `brand`,
 1 AS `color`,
 1 AS `available_quantity`,
 1 AS `image_1`,
 1 AS `image_2`,
 1 AS `image_3`,
 1 AS `image_4`*/;
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
/*!50001 VIEW `cart_item_info_for_ui` AS select `c`.`id` AS `id`,`c`.`customer_id` AS `customer_id`,`c`.`product_management_id` AS `product_management_id`,`c`.`quantity` AS `quantity`,`c`.`buying_status` AS `buying_status`,`pm`.`product_id` AS `product_id`,`pm`.`color` AS `color`,`pm`.`size` AS `size`,`p`.`name` AS `name`,`p`.`brand` AS `brand`,`p`.`selling_price` AS `selling_price`,`p`.`discount` AS `discount`,`pim`.`image_1` AS `image_1` from (((`cart` `c` join `products_management` `pm` on((`pm`.`id` = `c`.`product_management_id`))) join `products` `p` on((`pm`.`product_id` = `p`.`id`))) join `product_images_management` `pim` on((`pim`.`product_id` = `p`.`id`))) where ((`c`.`buying_status` = 0) and (`pim`.`color` = `pm`.`color`)) order by `c`.`id` */;
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
/*!50001 VIEW `invoice_with_products_info_for_ui` AS select `iwp`.`Invoice_ID` AS `Invoice_ID`,`iwp`.`Product_Management_ID` AS `Product_Management_ID`,`iwp`.`Quantity` AS `Quantity`,`pm`.`product_id` AS `product_id`,`pm`.`color` AS `color`,`pm`.`size` AS `size`,`pm`.`one_star_quantity` AS `one_star_quantity`,`pm`.`two_star_quantity` AS `two_star_quantity`,`pm`.`three_star_quantity` AS `three_star_quantity`,`pm`.`four_star_quantity` AS `four_star_quantity`,`pm`.`five_star_quantity` AS `five_star_quantity`,`pim`.`image_1` AS `image_1` from (((((`invoices_with_products` `iwp` join `products_management` `pm` on((`iwp`.`Product_Management_ID` = `pm`.`id`))) join `invoice` `i` on((`iwp`.`Invoice_ID` = `i`.`ID`))) join `customers` `c` on((`i`.`Customer_ID` = `c`.`id`))) join `products` `p` on((`p`.`id` = `pm`.`product_id`))) join `product_images_management` `pim` on((`pim`.`product_id` = `p`.`id`))) */;
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
/*!50001 VIEW `product_info_for_ui` AS select `pm`.`id` AS `id`,`p`.`id` AS `product_id`,`p`.`name` AS `name`,`pm`.`size` AS `size`,`p`.`selling_price` AS `selling_price`,`p`.`discount` AS `discount`,`p`.`brand` AS `brand`,`pm`.`color` AS `color`,`pm`.`available_quantity` AS `available_quantity`,`pim`.`image_1` AS `image_1`,`pim`.`image_2` AS `image_2`,`pim`.`image_3` AS `image_3`,`pim`.`image_4` AS `image_4` from ((`products` `p` join `product_images_management` `pim` on((`p`.`id` = `pim`.`product_id`))) left join `products_management` `pm` on((`pm`.`product_id` = `p`.`id`))) group by `pm`.`color`,`p`.`id` */;
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
/*!50001 VIEW `top_8_best_sell_products` AS select `p`.`id` AS `id`,`p`.`name` AS `name`,`p`.`selling_price` AS `selling_price`,`p`.`discount` AS `discount`,`p`.`brand` AS `brand`,`pm`.`color` AS `color`,`pm`.`available_quantity` AS `available_quantity`,`pim`.`image_1` AS `image_1`,`pim`.`image_2` AS `image_2`,`pim`.`image_3` AS `image_3`,`pim`.`image_4` AS `image_4` from ((((`products_management` `pm` join `invoices_with_products` `iwp` on((`pm`.`id` = `iwp`.`Product_Management_ID`))) join `invoice` `i` on((`iwp`.`Invoice_ID` = `i`.`ID`))) join `products` `p` on((`p`.`id` = `pm`.`product_id`))) join `product_images_management` `pim` on((`pim`.`product_id` = `p`.`id`))) where ((`i`.`Invoice_Date` <= cast(now() as date)) and (`i`.`Invoice_Date` >= cast((now() - interval 30 day) as date))) group by `p`.`name` order by (select sum(`invoices_with_products`.`Quantity`) from ((`invoices_with_products` join `products_management` `pm` on((`pm`.`id` = `invoices_with_products`.`Product_Management_ID`))) join `products` `p` on((`p`.`id` = `pm`.`product_id`))) where (`p`.`name` = `p`.`name`)) desc limit 8 */;
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

-- Dump completed on 2023-04-22  0:23:59
