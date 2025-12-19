-- MySQL dump 10.13  Distrib 8.0.44, for Win64 (x86_64)
--
-- Host: localhost    Database: customer_management
-- ------------------------------------------------------
-- Server version	8.0.44

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
-- Table structure for table `customers`
--

DROP TABLE IF EXISTS `customers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `customers` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `customer_code` varchar(20) NOT NULL,
  `full_name` varchar(100) NOT NULL,
  `email` varchar(100) NOT NULL,
  `phone` varchar(20) DEFAULT NULL,
  `address` text,
  `status` enum('ACTIVE','INACTIVE') NOT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `customer_code` (`customer_code`),
  UNIQUE KEY `email` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=34 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customers`
--

LOCK TABLES `customers` WRITE;
/*!40000 ALTER TABLE `customers` DISABLE KEYS */;
INSERT INTO `customers` VALUES (5,'C005','Alice Jochanson','updated.email@exzzzcample.com','+15559101112','456 New Street, NY','ACTIVE','2025-12-06 02:06:24','2025-12-12 09:41:13'),(7,'C007','David Miller','davidd.miller@example.com','+15540106111','999 Broadway, Seattle, WA 98101','ACTIVE','2025-12-05 20:52:17','2025-12-05 20:52:17'),(9,'C009','Grace Phillips','grace.phillips@example.com','+1-555-0109','580 Cherry Ave, Atlanta, GA 30301','ACTIVE','2025-12-06 09:06:24','2025-12-06 09:06:24'),(10,'C010','Henry Adams','henry.adams@example.com','+1-555-0110','120 Palm Beach Rd, Orlando, FL 32801','INACTIVE','2025-12-06 09:06:24','2025-12-06 09:06:24'),(11,'C011','Emily Johnson','emily.johnson@example.com','+1-555-0107','742 Birch St, Seattle, WA 98101','INACTIVE','2025-12-06 09:06:24','2025-12-06 09:06:24'),(12,'C012','Isabella Moore','isabella.moore@example.com','+1-555-0112','45 Sunset Blvd, Los Angeles, CA 90001','ACTIVE','2025-12-06 09:06:24','2025-12-06 09:06:24'),(13,'C013','Jack Turner','jack.turner@example.com','+1-555-0113','390 Hillcrest Ave, San Diego, CA 92101','INACTIVE','2025-12-06 09:06:24','2025-12-06 09:06:24'),(14,'C014','Karen Scott','karen.scott@example.com','+1-555-0114','800 River St, Portland, OR 97035','ACTIVE','2025-12-06 09:06:24','2025-12-06 09:06:24'),(15,'C015','Leonard Hughes','leonard.hughes@example.com','+1-555-0115','1020 Cedar Rd, Nashville, TN 37011','INACTIVE','2025-12-06 09:06:24','2025-12-06 09:06:24'),(16,'C016','Megan Foster','megan.foster@example.com','+1-555-0116','612 Willow Ln, Charlotte, NC 28202','ACTIVE','2025-12-06 09:06:24','2025-12-06 09:06:24'),(17,'C017','Nathan Reed','nathan.reed@example.com','+1-555-0117','150 Orchard Dr, Columbus, OH 43004','ACTIVE','2025-12-06 09:06:24','2025-12-06 09:06:24'),(18,'C018','Olivia Ramirez','olivia.ramirez@example.com','+1-555-0118','303 Pine Lake Rd, Minneapolis, MN 55401','INACTIVE','2025-12-06 09:06:24','2025-12-06 09:06:24'),(19,'C019','Patrick Diaz','patrick.diaz@example.com','+1-555-0119','712 Evergreen St, Kansas City, MO 64101','ACTIVE','2025-12-06 09:06:24','2025-12-06 09:06:24'),(20,'C020','Quinn Walker','quinn.walker@example.com','+1-555-0120','58 Ridgeview Rd, Salt Lake City, UT 84101','ACTIVE','2025-12-06 09:06:24','2025-12-06 09:06:24'),(21,'C021','Rachel Cooper','rachel.cooper@example.com','+1-555-0121','200 Timberline St, Boise, ID 83701','INACTIVE','2025-12-06 09:06:24','2025-12-06 09:06:24'),(22,'C022','Samuel Wright','samuel.wright@example.com','+1-555-0122','19 Brookstone Dr, Milwaukee, WI 53201','ACTIVE','2025-12-06 09:06:24','2025-12-06 09:06:24'),(23,'C023','Tina Edwards','tina.edwards@example.com','+1-555-0123','840 Cypress St, Omaha, NE 68101','INACTIVE','2025-12-06 09:06:24','2025-12-06 09:06:24'),(24,'C024','Umar Patel','umar.patel@example.com','+1-555-0124','220 Highland Ave, Raleigh, NC 27601','ACTIVE','2025-12-06 09:06:24','2025-12-06 09:06:24'),(25,'C025','Victoria Barnes','victoria.barnes@example.com','+1-555-0125','175 Summit St, Tampa, FL 33601','INACTIVE','2025-12-06 09:06:24','2025-12-06 09:06:24'),(26,'C026','William Carter','william.carter@example.com','+1-555-0126','504 Crescent Dr, Richmond, VA 23220','ACTIVE','2025-12-06 09:06:24','2025-12-06 09:06:24'),(27,'C027','Xavier Brooks','xavier.brooks@example.com','+1-555-0127','812 Valley View Rd, Louisville, KY 40201','INACTIVE','2025-12-06 09:06:24','2025-12-06 09:06:24'),(28,'C028','Yasmin Clark','yasmin.clark@example.com','+1-555-0128','329 Cliffside Dr, Albuquerque, NM 87101','ACTIVE','2025-12-06 09:06:24','2025-12-06 09:06:24'),(29,'C029','Zachary Lewis','zachary.lewis@example.com','+1-555-0129','98 Springhill Rd, Tulsa, OK 74101','ACTIVE','2025-12-06 09:06:24','2025-12-06 09:06:24'),(30,'C030','Amber Rivera','amber.rivera@example.com','+1-555-0130','462 Meadowbrook Ln, Detroit, MI 48201','INACTIVE','2025-12-06 09:06:24','2025-12-06 09:06:24'),(31,'C031','Brandon Kelly','brandon.kelly@example.com','+1-555-0131','710 Forest Park Dr, Cleveland, OH 44101','ACTIVE','2025-12-06 09:06:24','2025-12-06 09:06:24'),(33,'C121','Alice Jochnson','alice.johnson@exampdle.com','+15551101111','143 Main St, NY','ACTIVE','2025-12-12 09:34:03','2025-12-12 09:34:03');
/*!40000 ALTER TABLE `customers` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-12-19 23:56:07
