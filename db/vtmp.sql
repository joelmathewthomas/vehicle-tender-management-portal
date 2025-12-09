-- MySQL dump 10.13  Distrib 8.0.43, for Linux (x86_64)
--
-- Host: localhost    Database: vtmp
-- ------------------------------------------------------
-- Server version	8.0.43

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
-- Table structure for table `drivers`
--

DROP TABLE IF EXISTS `drivers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `drivers` (
  `driver_id` int NOT NULL AUTO_INCREMENT,
  `owner_id` int NOT NULL,
  `driver_fname` varchar(45) NOT NULL,
  `driver_mname` varchar(45) DEFAULT NULL,
  `driver_lname` varchar(45) NOT NULL,
  `driver_phone` varchar(45) NOT NULL,
  `driver_address` varchar(45) NOT NULL,
  `driver_aadhaar` varchar(12) NOT NULL,
  `driver_status` enum('approved','unapproved') NOT NULL,
  PRIMARY KEY (`driver_id`),
  UNIQUE KEY `driver_phone_UNIQUE` (`driver_phone`),
  UNIQUE KEY `driver_aadhaar_UNIQUE` (`driver_aadhaar`),
  KEY `fk_drivers_1_idx` (`owner_id`),
  CONSTRAINT `fk_drivers_1` FOREIGN KEY (`owner_id`) REFERENCES `owners` (`owner_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `locations`
--

DROP TABLE IF EXISTS `locations`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `locations` (
  `location_id` int NOT NULL AUTO_INCREMENT,
  `location_name` varchar(45) NOT NULL,
  PRIMARY KEY (`location_id`),
  UNIQUE KEY `location_name_UNIQUE` (`location_name`)
) ENGINE=InnoDB AUTO_INCREMENT=123 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `owners`
--

DROP TABLE IF EXISTS `owners`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `owners` (
  `owner_id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NOT NULL,
  `owner_fname` varchar(45) NOT NULL,
  `owner_mname` varchar(45) DEFAULT NULL,
  `owner_lname` varchar(45) NOT NULL,
  `owner_phone` varchar(13) NOT NULL,
  `owner_address` varchar(45) NOT NULL,
  `owner_aadhaar` varchar(12) NOT NULL,
  PRIMARY KEY (`owner_id`),
  UNIQUE KEY `user_id_UNIQUE` (`user_id`),
  UNIQUE KEY `owner_phone_UNIQUE` (`owner_phone`),
  UNIQUE KEY `owner_aadhaar_UNIQUE` (`owner_aadhaar`),
  CONSTRAINT `fk_owner_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `payments`
--

DROP TABLE IF EXISTS `payments`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `payments` (
  `payment_id` int NOT NULL AUTO_INCREMENT,
  `tender_id` int NOT NULL,
  `payment_amount` float NOT NULL,
  PRIMARY KEY (`payment_id`),
  UNIQUE KEY `tender_id_UNIQUE` (`tender_id`),
  KEY `fk_payments_1_idx` (`tender_id`),
  CONSTRAINT `fk_payments_1` FOREIGN KEY (`tender_id`) REFERENCES `tenders` (`tender_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tenders`
--

DROP TABLE IF EXISTS `tenders`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tenders` (
  `tender_id` int NOT NULL AUTO_INCREMENT,
  `driver_id` int DEFAULT NULL,
  `vehicle_id` int DEFAULT NULL,
  `location_id` int NOT NULL,
  `tender_date` date NOT NULL,
  `tender_distance` float NOT NULL,
  `tender_fuel_rate` float NOT NULL,
  `tender_salary` float NOT NULL,
  `tender_status` enum('open','accept','closed','paid') NOT NULL,
  PRIMARY KEY (`tender_id`),
  KEY `fk_tenders_1_idx` (`vehicle_id`),
  KEY `fk_tenders_2_idx` (`driver_id`),
  KEY `fk_tenders_3_idx` (`location_id`),
  CONSTRAINT `fk_tenders_1` FOREIGN KEY (`vehicle_id`) REFERENCES `vehicles` (`vehicle_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_tenders_2` FOREIGN KEY (`driver_id`) REFERENCES `drivers` (`driver_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_tenders_3` FOREIGN KEY (`location_id`) REFERENCES `locations` (`location_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `user_id` int NOT NULL AUTO_INCREMENT,
  `user_name` varchar(45) NOT NULL,
  `user_password` varchar(45) DEFAULT NULL,
  `user_role` enum('admin','owner') DEFAULT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `user_name_UNIQUE` (`user_name`)
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `vehicles`
--

DROP TABLE IF EXISTS `vehicles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `vehicles` (
  `vehicle_id` int NOT NULL AUTO_INCREMENT,
  `owner_id` int NOT NULL,
  `vehicle_no` varchar(45) NOT NULL,
  `vehicle_type` enum('bus','car') NOT NULL,
  `vehicle_status` enum('approved','unapproved') NOT NULL,
  PRIMARY KEY (`vehicle_id`),
  UNIQUE KEY `vehicle_no_UNIQUE` (`vehicle_no`),
  KEY `fk_vehicles_1_idx` (`owner_id`),
  CONSTRAINT `fk_vehicles_1` FOREIGN KEY (`owner_id`) REFERENCES `owners` (`owner_id`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-12-09  6:39:47
