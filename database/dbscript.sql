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
-- Dumping data for table `cart`
--

LOCK TABLES `cart` WRITE;
/*!40000 ALTER TABLE `cart` DISABLE KEYS */;
INSERT INTO `cart` VALUES (1,1,5,2,'NOT_BOUGHT_YET'),(2,1,1,2,'NOT_BOUGHT_YET'),(3,1,11,2,'NOT_BOUGHT_YET'),(4,1,3,2,'BOUGHT'),(5,1,4,6,'BOUGHT');
/*!40000 ALTER TABLE `cart` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `catalog`
--

LOCK TABLES `catalog` WRITE;
/*!40000 ALTER TABLE `catalog` DISABLE KEYS */;
INSERT INTO `catalog` VALUES (1,'Jackets','1SJd_q9XgP6t3IcZyr9yge-YWpB7m4r6p'),(2,'Glasses','1sxJBW8O5FurTGUE39cIxdRcRycLg6pnK'),(3,'Shoes','1C22R6MlFTiMwbewDPkzpX1wb_GnNGu08'),(4,'Male','17X-b4LDq0VoHV1QOJE5fMvVhIoejq_2u'),(5,'Female','128ptt1MwwaDE1B-Qd0PYt-z0mooOBU9L'),(6,'Accessories','1vu0bQvTF38IAsJtLBH_rFQHNJAqY1IgM'),(7,'Purses','1GKehSvAvHwZ9dVe82dDoGBzXe1rbw1vz'),(8,'Sport','14Ap5-CSmhnR5wAxbLsJ0XKkbi45vya6X'),(9,'Backpackes','1rdb1BgNCVRL2A5FAVqvRcV0azUd1nBlw');
/*!40000 ALTER TABLE `catalog` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `catalogs_with_products`
--

LOCK TABLES `catalogs_with_products` WRITE;
/*!40000 ALTER TABLE `catalogs_with_products` DISABLE KEYS */;
INSERT INTO `catalogs_with_products` VALUES (4,1),(8,1),(5,2),(6,2),(7,2),(4,3),(3,4),(4,4),(5,5),(4,6),(8,6),(5,7),(6,7),(9,7),(2,8),(5,8);
/*!40000 ALTER TABLE `catalogs_with_products` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `comments`
--

LOCK TABLES `comments` WRITE;
/*!40000 ALTER TABLE `comments` DISABLE KEYS */;
/*!40000 ALTER TABLE `comments` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `customers`
--

LOCK TABLES `customers` WRITE;
/*!40000 ALTER TABLE `customers` DISABLE KEYS */;
INSERT INTO `customers` VALUES (1,'Pham Hoang Phuc','phuclateo@gmail.com','0123456987',2,NULL,NULL,NULL,'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQaiHNXjxQrlFFFHdMGtUpH1nLDjHzyfTms6A&usqp=CAU'),(2,'Nguyen Quynh Nhu','nnhu7721@gmail.com','0213654798',3,NULL,NULL,NULL,'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQaiHNXjxQrlFFFHdMGtUpH1nLDjHzyfTms6A&usqp=CAU'),(3,'Nguyen Hoang Sang','19110120@student.hcmute.edu.vn','0977815809',7,NULL,NULL,NULL,'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQaiHNXjxQrlFFFHdMGtUpH1nLDjHzyfTms6A&usqp=CAU'),(4,'Nguyen Thi Hoang Trang','pbeltranster@gmail.com','0321654987',9,NULL,NULL,NULL,'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQaiHNXjxQrlFFFHdMGtUpH1nLDjHzyfTms6A&usqp=CAU'),(6,'qweqwe','qwe@we','1234567890',11,NULL,NULL,NULL,'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQaiHNXjxQrlFFFHdMGtUpH1nLDjHzyfTms6A&usqp=CAU'),(7,'Nguyen Quoc Heng','nqh130901@gmail.com','1234567890',12,NULL,NULL,NULL,'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQaiHNXjxQrlFFFHdMGtUpH1nLDjHzyfTms6A&usqp=CAU'),(8,'Duc Ngu Vcl','ducngu@gmail.com','0321654987',13,NULL,NULL,NULL,'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQaiHNXjxQrlFFFHdMGtUpH1nLDjHzyfTms6A&usqp=CAU'),(12,'guyen an\0\0','nguyenvana@gmail.com','0123456789',47,NULL,NULL,NULL,NULL),(13,'guyen oang an\0\0\0','aaaa@gmail.com','0963452781',48,NULL,NULL,NULL,NULL),(14,'San','sang@gmail.com','012345678',49,NULL,NULL,NULL,NULL);
/*!40000 ALTER TABLE `customers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `delivery`
--

LOCK TABLES `delivery` WRITE;
/*!40000 ALTER TABLE `delivery` DISABLE KEYS */;
/*!40000 ALTER TABLE `delivery` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `invoice`
--

LOCK TABLES `invoice` WRITE;
/*!40000 ALTER TABLE `invoice` DISABLE KEYS */;
INSERT INTO `invoice` VALUES (1,1,'2023-03-10',1,'SHIPPED',0,NULL,'USD','PAYPAL',NULL,NULL,'ACCEPTED',0,'sb-2437s98400372@personal.example.com'),(2,1,'2023-03-10',1,'CANCEL',100,'admin\'s reasons are written herer ...','USD','VNPAY',NULL,'akdgfg','ACCEPTED',0,NULL),(3,1,'2023-03-10',0,'ACCEPTANCE_WAITING',0,NULL,'USD','COD','alo alo',NULL,'WAITING',0,NULL),(4,1,'2023-03-10',0,'CANCEL',0,'The shop caught fire !!','USD','COD','alo alo',NULL,'REFUSED',0,NULL),(5,2,'2023-03-10',0,'ACCEPTANCE_WAITING',0,NULL,'USD','COD','nguyen quynh nhu',NULL,'WAITING',0,NULL),(6,1,'2023-03-10',1,'CANCEL',0,'shipper comments are written here...','USD','PAYPAL','alo alo','hhhhhhhhhhhhhhhhhh','ACCEPTED',0,'sb-2437s98400372@personal.example.com'),(7,1,'2023-03-10',0,'PACKING',0,NULL,'USD','COD','alo alo','hhhhhhhhhhhhhhhhhh','ACCEPTED',0,NULL),(8,2,'2023-03-10',0,'SHIPPING',0,NULL,'USD','COD','huhuhuhuu','kakakakak','ACCEPTED',0,NULL),(9,2,'2023-03-10',0,'NOT_SHIPPED',0,'customer canceled order before admin accept','USD','COD','huhuhuhuu','kakakakak','WAITING',0,''),(10,1,'2023-03-17',0,'PACKING',0,'','USD','COD','alo alo','hhhhhhhhhhhhhhhhhh','ACCEPTED',0,''),(11,2,'2023-03-17',1,'SHIPPING',0,'','USD','PAYPAL','duc ngu','phuc ngu','ACCEPTED',0,'sb-2437s98400372@personal.example.com');
/*!40000 ALTER TABLE `invoice` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `invoices_with_products`
--

LOCK TABLES `invoices_with_products` WRITE;
/*!40000 ALTER TABLE `invoices_with_products` DISABLE KEYS */;
INSERT INTO `invoices_with_products` VALUES (1,3,4),(1,4,1),(1,11,5),(2,1,1),(2,3,4),(3,11,3),(3,12,3),(4,1,1),(5,4,4),(6,18,1),(7,12,1),(7,20,13),(8,1,3),(9,1,3),(10,20,5),(11,13,1),(11,17,1),(11,19,5),(11,20,5);
/*!40000 ALTER TABLE `invoices_with_products` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `login_accounts`
--

LOCK TABLES `login_accounts` WRITE;
/*!40000 ALTER TABLE `login_accounts` DISABLE KEYS */;
INSERT INTO `login_accounts` VALUES (1,'admin','123','ADMIN','allowed'),(2,'user','123','CUSTOMER','allowed'),(3,'nhu0707','B1czXA9LT3','CUSTOMER','allowed'),(7,'sang236','123','CUSTOMER','banned'),(8,'shipper','123','SHIPPER','allowed'),(9,'tester','123','CUSTOMER','allowed'),(11,'qweqwe','qweqwe','CUSTOMER','allowed'),(12,'quochoang','123','CUSTOMER','allowed'),(13,'ducngu','123','CUSTOMER','allowed'),(44,'manhngu','123','CUSTOMER','allowed'),(47,'user1','123','CUSTOMER','allowed'),(48,'testuser','123','CUSTOMER','allowed'),(49,'user3','123','CUSTOMER','allowed');
/*!40000 ALTER TABLE `login_accounts` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `product_images_management`
--

LOCK TABLES `product_images_management` WRITE;
/*!40000 ALTER TABLE `product_images_management` DISABLE KEYS */;
INSERT INTO `product_images_management` VALUES (1,'red','https://assets.adidas.com/images/h_840,f_auto,q_auto,fl_lossy,c_fill,g_auto/49808757050946de8bedae29011926b5_9366/Manchester_United_22-23_Home_Jersey_Red_H13881_21_model.jpg','https://assets.adidas.com/images/h_840,f_auto,q_auto,fl_lossy,c_fill,g_auto/8a9f28ca1b834e2e86ccae2901192e37_9366/Ao_DJau_San_Nha_Manchester_United_22-23_DJo_H13881_22_model.jpg','https://assets.adidas.com/images/h_840,f_auto,q_auto,fl_lossy,c_fill,g_auto/dd570e1feae0414b98e3ae2901193687_9366/Ao_DJau_San_Nha_Manchester_United_22-23_DJo_H13881_23_hover_model.jpg','https://assets.adidas.com/images/h_840,f_auto,q_auto,fl_lossy,c_fill,g_auto/693f5fb49f4c45078f9aae6f00d4c19e_9366/Ao_DJau_San_Nha_Manchester_United_22-23_DJo_H13881_01_laydown.jpg'),(1,'white','https://assets.adidas.com/images/h_840,f_auto,q_auto,fl_lossy,c_fill,g_auto/2700b53701664eb8b277ae2f00df6e5d_9366/Ao_DJau_San_Khach_Manchester_United_22-23_trang_H13880_21_model.jpg','https://assets.adidas.com/images/h_840,f_auto,q_auto,fl_lossy,c_fill,g_auto/4d02848d2ad746f185e3ae2f00df7641_9366/Ao_DJau_San_Khach_Manchester_United_22-23_trang_H13880_22_model.jpg','https://assets.adidas.com/images/h_840,f_auto,q_auto,fl_lossy,c_fill,g_auto/1733a67776594a838a6cae2f00df7e7e_9366/Ao_DJau_San_Khach_Manchester_United_22-23_trang_H13880_23_hover_model.jpg','https://assets.adidas.com/images/h_840,f_auto,q_auto,fl_lossy,c_fill,g_auto/84bd0c3ab78f49808d78ae2f00df8697_9366/Ao_DJau_San_Khach_Manchester_United_22-23_trang_H13880_25_model.jpg'),(2,'black','https://media.gucci.com/style/HEXEAF2DC_Center_0_0_800x800/1653583557/699268_UXX0G_1000_003_100_0000_Light-Gucci-Blondie-shoulder-bag.jpg','https://media.gucci.com/style/HEXEAF2DC_Center_0_0_800x800/1652714104/699268_UXX0G_1000_001_073_0000_Light-Gucci-Blondie-shoulder-bag.jpg','https://media.gucci.com/style/HEXEAF2DC_Center_0_0_800x800/1652714105/699268_UXX0G_1000_002_073_0000_Light-Gucci-Blondie-shoulder-bag.jpg','https://media.gucci.com/style/HEXEAF2DC_Center_0_0_800x800/1653583559/699268_UXX0G_1000_005_100_0000_Light-Gucci-Blondie-shoulder-bag.jpg'),(2,'pink','https://media.gucci.com/style/HEXF1E9FB_Center_0_0_800x800/1670024750/699268_UXX0G_6910_004_100_0000_Light-Gucci-Blondie-shoulder-bag.jpg','https://media.gucci.com/style/HEXF1E9FB_Center_0_0_800x800/1675811856/699268_UXX0G_6910_006_100_0000_Light-Gucci-Blondie-shoulder-bag.jpg','https://media.gucci.com/style/HEXF1E9FB_Center_0_0_800x800/1674688592/699268_UXX0G_6910_007_073_0000_Light-Gucci-Blondie-shoulder-bag.jpg','https://media.gucci.com/style/HEXF1E9FB_Center_0_0_800x800/1674688596/699268_UXX0G_6910_009_073_0000_Light-Gucci-Blondie-shoulder-bag.jpg'),(3,'none','https://media.gucci.com/style/HEXF1E9FB_Center_0_0_800x800/1669920431/724770_XKCWH_8050_002_100_0000_Light-Striped-jacquard-wool-knit-sweater.jpg','https://media.gucci.com/style/HEXF1E9FB_Center_0_0_800x800/1669920428/724770_XKCWH_8050_001_100_0000_Light-Striped-jacquard-wool-knit-sweater.jpg','https://media.gucci.com/style/HEXF1E9FB_Center_0_0_800x800/1669920433/724770_XKCWH_8050_003_100_0000_Light-Striped-jacquard-wool-knit-sweater.jpg','https://media.gucci.com/style/HEXF1E9FB_Center_0_0_800x800/1669920435/724770_XKCWH_8050_005_100_0000_Light-Striped-jacquard-wool-knit-sweater.jpg'),(4,'none','https://static.nike.com/a/images/t_PDP_864_v1/f_auto,b_rgb:f5f5f5/029d86dd-1549-4221-a18b-25d165998d1f/air-max-90-se-shoes-ltJLHs.png','https://static.nike.com/a/images/t_PDP_864_v1/f_auto,b_rgb:f5f5f5/eceb6b53-06fc-4182-a450-b16620dc5295/air-max-90-se-shoes-ltJLHs.png','https://static.nike.com/a/images/t_PDP_864_v1/f_auto,b_rgb:f5f5f5/4bb1f419-b093-4d53-b120-5607311bafeb/air-max-90-se-shoes-ltJLHs.png','https://static.nike.com/a/images/t_PDP_864_v1/f_auto,b_rgb:f5f5f5/92d5efd4-727c-4a43-93ac-14a3099591dc/air-max-90-se-shoes-ltJLHs.png'),(5,'none','https://www.dolcegabbana.com/dw/image/v2/AAGA_PRD/on/demandware.static/-/Sites-15/default/dwa68a4484/images/zoom/F6R8DTFUGOI_N0000_1.jpg?sw=742&sh=944&sm=fit','https://www.dolcegabbana.com/dw/image/v2/AAGA_PRD/on/demandware.static/-/Sites-15/default/dw06bbc59a/images/zoom/F6R8DTFUGOI_N0000_2.jpg?sw=742&sh=944&sm=fit','https://www.dolcegabbana.com/dw/image/v2/AAGA_PRD/on/demandware.static/-/Sites-15/default/dw24c729fa/images/zoom/F6R8DTFUGOI_N0000_3.jpg?sw=742&sh=944&sm=fit','https://www.dolcegabbana.com/dw/image/v2/AAGA_PRD/on/demandware.static/-/Sites-15/default/dwf8349ed3/images/zoom/F6R8DTFUGOI_N0000_11.jpg?sw=742&sh=944&sm=fit'),(6,'none','https://static.nike.com/a/images/t_PDP_864_v1/f_auto,b_rgb:f5f5f5/b75092c8-5853-4d15-bd57-c6dca886dd57/jordan-flight-mvp-fleece-pullover-hoodie-Z7kcns.png','https://static.nike.com/a/images/t_PDP_864_v1/f_auto,b_rgb:f5f5f5/5f7394a7-8c5e-4f5f-9782-40b932a93c36/jordan-flight-mvp-fleece-pullover-hoodie-Z7kcns.png','https://static.nike.com/a/images/t_PDP_864_v1/f_auto,b_rgb:f5f5f5/9a235f1a-fcfa-4c56-85ec-53225a27d5e6/jordan-flight-mvp-fleece-pullover-hoodie-Z7kcns.png','https://static.nike.com/a/images/t_PDP_864_v1/f_auto,b_rgb:f5f5f5/07c47509-10dd-45d5-b7ea-1ef57b8e4aea/jordan-flight-mvp-fleece-pullover-hoodie-Z7kcns.png'),(7,'red','https://www.chanel.com/images//t_one///q_auto:good,f_auto,fl_lossy,dpr_1.2/w_620/large-back-pack-chanel-22-red-white-shiny-calfskin-gold-tone-metal-shiny-calfskin-gold-tone-metal--packshot-artistique-vue1-as3313b09981nm243-9516416729118.jpg','https://www.chanel.com/images//t_one///q_auto:good,f_auto,fl_lossy,dpr_1.2/w_620/large-back-pack-chanel-22-red-white-shiny-calfskin-gold-tone-metal-shiny-calfskin-gold-tone-metal--packshot-artistique-vue2-as3313b09981nm243-9516416794654.jpg','https://www.chanel.com/images//t_one///q_auto:good,f_auto,fl_lossy,dpr_1.2/w_620/large-back-pack-chanel-22-red-white-shiny-calfskin-gold-tone-metal-shiny-calfskin-gold-tone-metal--packshot-artistique-vue3-as3313b09981nm243-9516424790046.jpg','https://www.chanel.com/images//t_one///q_auto:good,f_auto,fl_lossy,dpr_1.2/w_620/large-back-pack-chanel-22-red-white-shiny-calfskin-gold-tone-metal-shiny-calfskin-gold-tone-metal--packshot-artistique-vue4-as3313b09981nm243-9516430884894.jpg'),(7,'white','https://www.chanel.com/images//t_one///q_auto:good,f_auto,fl_lossy,dpr_1.2/w_620/large-back-pack-chanel-22-light-blue-shiny-calfskin-gold-tone-metal-shiny-calfskin-gold-tone-metal--packshot-artistique-vue1-as3313b08037nn268-9521332944926.jpg','https://www.chanel.com/images//t_one///q_auto:good,f_auto,fl_lossy,dpr_1.2/w_620/large-back-pack-chanel-22-light-blue-shiny-calfskin-gold-tone-metal-shiny-calfskin-gold-tone-metal--packshot-artistique-vue2-as3313b08037nn268-9521345888286.jpg','https://www.chanel.com/images//t_one///q_auto:good,f_auto,fl_lossy,dpr_1.2/w_620/large-back-pack-chanel-22-light-blue-shiny-calfskin-gold-tone-metal-shiny-calfskin-gold-tone-metal--packshot-artistique-vue3-as3313b08037nn268-9521535418398.jpg','https://www.chanel.com/images//t_one///q_auto:good,f_auto,fl_lossy,dpr_1.2/w_620/large-back-pack-chanel-22-light-blue-shiny-calfskin-gold-tone-metal-shiny-calfskin-gold-tone-metal--packshot-artistique-vue4-as3313b08037nn268-9521547345950.jpg'),(8,'green','https://www.chanel.com/images//t_one/t_fashion9//b_rgb:F7F7F7,e_brightness:-3/q_auto:good,f_auto,fl_lossy,dpr_1.2/w_620/butterfly-eyeglasses-dark-green-gold-acetate-glass-pearls-acetate-glass-pearls-packshot-default-a75267x08101v1702-9518592688158.jpg','https://www.chanel.com/images//t_one/t_fashion9//b_rgb:F7F7F7,e_brightness:-3/q_auto:good,f_auto,fl_lossy,dpr_1.2/w_620/butterfly-eyeglasses-dark-green-gold-acetate-glass-pearls-acetate-glass-pearls-packshot-alternative-a75267x08101v1702-9518591803422.jpg','https://www.chanel.com/images//t_one/t_fashion9//b_rgb:F7F7F7,e_brightness:-3/q_auto:good,f_auto,fl_lossy,dpr_1.2/w_620/butterfly-eyeglasses-dark-green-gold-acetate-glass-pearls-acetate-glass-pearls-packshot-other-a75267x08101v1702-9518592491550.jpg','https://www.chanel.com/images//t_one/t_fashion9//b_rgb:F7F7F7,e_brightness:-3/q_auto:good,f_auto,fl_lossy,dpr_1.2/w_620/butterfly-eyeglasses-dark-green-gold-acetate-glass-pearls-acetate-glass-pearls-packshot-extra-a75267x08101v1702-9518590033950.jpg'),(8,'red','https://www.chanel.com/images//t_one/t_fashion9//b_rgb:F7F7F7,e_brightness:-3/q_auto:good,f_auto,fl_lossy,dpr_1.2/w_620/butterfly-eyeglasses-brown-gold-acetate-glass-pearls-acetate-glass-pearls-packshot-default-a75267x08101v1722-9518588788766.jpg','https://www.chanel.com/images//t_one/t_fashion9//b_rgb:F7F7F7,e_brightness:-3/q_auto:good,f_auto,fl_lossy,dpr_1.2/w_620/butterfly-eyeglasses-brown-gold-acetate-glass-pearls-acetate-glass-pearls-packshot-alternative-a75267x08101v1722-9518587379742.jpg','https://www.chanel.com/images//t_one/t_fashion9//b_rgb:F7F7F7,e_brightness:-3/q_auto:good,f_auto,fl_lossy,dpr_1.2/w_620/butterfly-eyeglasses-brown-gold-acetate-glass-pearls-acetate-glass-pearls-packshot-other-a75267x08101v1722-9518584725534.jpg','https://www.chanel.com/images//t_one/t_fashion9//b_rgb:F7F7F7,e_brightness:-3/q_auto:good,f_auto,fl_lossy,dpr_1.2/w_620/butterfly-eyeglasses-brown-gold-acetate-glass-pearls-acetate-glass-pearls-packshot-extra-a75267x08101v1722-9518591737886.jpg'),(8,'white','https://www.chanel.com/images//t_one/t_fashion9//b_rgb:F7F7F7,e_brightness:-3/q_auto:good,f_auto,fl_lossy,dpr_1.2/w_620/butterfly-eyeglasses-dark-beige-gold-acetate-glass-pearls-acetate-glass-pearls-packshot-default-a75267x08101v1719-9518590099486.jpg','https://www.chanel.com/images//t_one/t_fashion9//b_rgb:F7F7F7,e_brightness:-3/q_auto:good,f_auto,fl_lossy,dpr_1.2/w_620/butterfly-eyeglasses-dark-beige-gold-acetate-glass-pearls-acetate-glass-pearls-packshot-alternative-a75267x08101v1719-9518592819230.jpg','https://www.chanel.com/images//t_one/t_fashion9//b_rgb:F7F7F7,e_brightness:-3/q_auto:good,f_auto,fl_lossy,dpr_1.2/w_620/butterfly-eyeglasses-dark-beige-gold-acetate-glass-pearls-acetate-glass-pearls-packshot-other-a75267x08101v1719-9518590263326.jpg','https://www.chanel.com/images//t_one/t_fashion9//b_rgb:F7F7F7,e_brightness:-3/q_auto:good,f_auto,fl_lossy,dpr_1.2/w_620/butterfly-eyeglasses-dark-beige-gold-acetate-glass-pearls-acetate-glass-pearls-packshot-extra-a75267x08101v1719-9518587871262.jpg');
/*!40000 ALTER TABLE `product_images_management` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `product_import_management`
--

LOCK TABLES `product_import_management` WRITE;
/*!40000 ALTER TABLE `product_import_management` DISABLE KEYS */;
INSERT INTO `product_import_management` VALUES (1,1,'2022-12-01 00:00:00',10,NULL);
/*!40000 ALTER TABLE `product_import_management` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `products`
--

LOCK TABLES `products` WRITE;
/*!40000 ALTER TABLE `products` DISABLE KEYS */;
INSERT INTO `products` VALUES (1,'Manchester United 22/23',950,650,'addidas',10,'A supporter jersey made with recycled materials.\n\nTurned up or pressed down, the humble polo collar has played a starring role in many of Manchester United\'s biggest moments. Making a comeback on this adidas football jersey, it joins a shield-style badge and engineered pinstripe graphic to produce an eye-catching look. Moisture-absorbing AEROREADY and mesh panels make it a comfortable choice for passionate supporters.\n\nMade with 100% recycled materials, this product represents just one of our solutions to help end plastic waste.',5),(2,'Gucci Blondie shoulder bag',3600,2500,'gucci',0,'\n\nVintage elements are paired with archival details as an ode to the glamour that permeates Gucci\'s latest collections. This shoulder bag pairs a delicate chain strap with soft leather to infuse the accessory with a timeless feel. Reintroduced in honor of the collection, the rounded silhouette is completed by a historical, rounded iteration of Guccio Gucci\'s monogram.',4),(3,'Striped jacquard wool knit sweater',2600,1400,'gucci',0,'\n\nThe Cruise 2023 Gucci Cosmogonie collection was presented against the backdrop of the historic Castel del Monte in Italy. The show brought together aesthetics from distant eras and geographies and linked elements from the past with the present. This multicolor striped jacquard wool sweater has the image of the jester paired with the Interlocking G on the front.',5),(4,'Nike Air Max 90 SE',2750,1600,'nike',15,'What moves you? Find out in the Air Max 90 SE. Hemp accents, durable textile and playful \"NIKE MOVING CO.\" details celebrate getting going. Snail-trail deco stitching across the mudguard adds a fun take to the outdoorsy aesthetic. And its Waffle outsole and exposed Air cushioning keep the tried-and-tested feel under your feet. So, where next?',5),(5,'Jersey midi dress with sequins',2065,1300,'dolce&gabbana',0,'Dolce&Gabbana has always loved contrasts and thinks of strong contemporary women with a lot of personality. Garments with enveloping silhouettes, such as this sheath dress with fusible rhinestone embellishment, come in future-looking materials that will highlight all your feminine sensuality. ',5),(6,'Jordan Flight MVP',800,550,'nike',0,'Spring\'s here, gear up. This fleece hoodie will keep you cosy—and a big ol\' Jordan graphic busting through on the back keeps things fun.',4),(7,'Chanel backpack 22',6627,5123,'chanel',10,'Glossy Calfskin & Light Blue Gold Plated Metal',5),(8,'Butterfly glasses',953,721,'chanel',0,'Acetate & Glass Pearls Dark Green & Gold',4);
/*!40000 ALTER TABLE `products` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `products_management`
--

LOCK TABLES `products_management` WRITE;
/*!40000 ALTER TABLE `products_management` DISABLE KEYS */;
INSERT INTO `products_management` VALUES (1,1,'red','s',10,1,0,0,0,0,0),(3,1,'red','l',10,1,0,0,0,0,1),(4,1,'white','s',15,0,0,0,0,0,0),(5,1,'white','m',15,3,0,0,0,0,1),(6,1,'white','l',15,2,0,0,0,0,0),(7,2,'pink','none',10,1,0,0,0,1,0),(8,2,'black','none',10,4,0,0,0,0,0),(11,3,'none','l',3,3,0,0,0,0,3),(12,4,'none','40',10,4,0,0,0,0,1),(13,4,'none','40.5',12,3,0,0,0,0,1),(14,4,'none','41',12,5,0,0,0,2,1),(17,5,'none','36',8,3,0,0,0,0,1),(18,5,'none','38',8,3,0,0,0,0,1),(19,5,'none','40',8,3,0,0,0,0,1),(20,5,'none','42',8,3,0,0,0,0,1),(21,6,'none','s',10,1,0,0,0,1,0),(22,6,'none','m',7,0,0,0,0,0,0),(23,6,'none','l',7,1,0,0,0,1,0),(24,7,'white','none',10,2,0,0,0,0,1),(25,7,'red','none',8,3,0,0,0,0,2),(26,8,'green','none',9,3,0,0,0,1,0),(27,8,'white','none',6,2,0,0,0,1,0),(28,8,'red','none',9,6,0,0,0,3,1);
/*!40000 ALTER TABLE `products_management` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `staffs`
--

LOCK TABLES `staffs` WRITE;
/*!40000 ALTER TABLE `staffs` DISABLE KEYS */;
INSERT INTO `staffs` VALUES (1,'Nguyen Hoang Sang','hcm','admin','2001-06-23','nguyenhoangsang236@gmail.com','0123456987',1,'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQaiHNXjxQrlFFFHdMGtUpH1nLDjHzyfTms6A&usqp=CAU'),(2,'Nguyen Anh Duc','hcm','shipper','2001-02-02','ducnguvcl@gmail.com','0321654987',8,'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQaiHNXjxQrlFFFHdMGtUpH1nLDjHzyfTms6A&usqp=CAU');
/*!40000 ALTER TABLE `staffs` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-05-08  1:16:09
