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
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `username` varchar(50) NOT NULL,
  `email` varchar(100) NOT NULL,
  `password` varchar(255) NOT NULL,
  `full_name` varchar(100) NOT NULL,
  `role` enum('ADMIN','USER') NOT NULL,
  `is_active` tinyint(1) DEFAULT '1',
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `reset_token` varchar(255) DEFAULT NULL,
  `reset_token_expiry` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`),
  UNIQUE KEY `email` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'john','john@example.com','$2a$10$gWamWsDC25h8r84k3GyY7OhvktyR4C8Cq/d3fI1BYSDlHKIORNXGK','John Doe','USER',1,'2025-12-13 02:05:20','2025-12-13 13:56:23',NULL,NULL),(3,'jane','jane@example.com','$2a$10$gWamWsDC25h8r84k3GyY7OhvktyR4C8Cq/d3fI1BYSDlHKIORNXGK','Jane Smith','USER',1,'2025-12-13 02:05:20','2025-12-13 11:07:16',NULL,NULL),(4,'testuser','test@example.com','$2a$10$gWamWsDC25h8r84k3GyY7OhvktyR4C8Cq/d3fI1BYSDlHKIORNXGK','Test User','USER',1,'2025-12-12 20:41:23','2025-12-13 11:07:16',NULL,NULL),(12,'admin','admin@example.com','$2a$10$IYEZj7n7XRMy3FzZgU0mhuliLO95rbbKPDfcuhK5nn2yyzP3otk5.','Admin User','ADMIN',1,'2025-12-13 04:16:43','2025-12-19 09:50:32',NULL,NULL),(15,'testuser2','test@exampl2e.com','$2a$10$M2L.k7F7RxgEch7tMjT/luL90HjbaN6dHji6WJ2RIJ98OWIDhjQNq','Tes2t User','USER',1,'2025-12-13 04:12:21','2025-12-13 04:12:21',NULL,NULL),(16,'testusedr2','tec3st@example.com','$2a$10$yRQy2eMdVEGVYU/3cD/e0uxXB4uRVe7kwiwcBi25nXGnRKFPQW2gK','Test dUser','USER',1,'2025-12-13 07:07:47','2025-12-13 07:07:47',NULL,NULL),(17,'testuszer','test@exzample.com','$2a$10$jj4rTkdsOf.OcmLsBqEP5eHGluRQo7HgtVBiTWrC2FXLVFJLxYave','Test User','USER',1,'2025-12-13 08:40:46','2025-12-13 08:40:46',NULL,NULL),(18,'testusdasdader','test@examczple.com','$2a$10$UDCfeh.3EwLZX28leZTeju1PIqS3zbTHicyB3WY8bLFjbqDlDWR46','Test Udser','USER',1,'2025-12-19 09:35:19','2025-12-19 09:35:19',NULL,NULL);
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
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
