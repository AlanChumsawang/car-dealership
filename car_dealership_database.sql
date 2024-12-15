CREATE DATABASE  IF NOT EXISTS `car_dealership_database` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `car_dealership_database`;
-- MySQL dump 10.13  Distrib 8.0.34, for macos13 (arm64)
--
-- Host: 127.0.0.1    Database: car_dealership_database
-- ------------------------------------------------------
-- Server version	8.0.40

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
-- Table structure for table `Dealerships`
--

DROP TABLE IF EXISTS `Dealerships`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Dealerships` (
  `DealershipID` int NOT NULL AUTO_INCREMENT,
  `Name` varchar(50) NOT NULL,
  `Address` varchar(50) NOT NULL,
  `Phone` varchar(12) DEFAULT NULL,
  PRIMARY KEY (`DealershipID`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Dealerships`
--

LOCK TABLES `Dealerships` WRITE;
/*!40000 ALTER TABLE `Dealerships` DISABLE KEYS */;
INSERT INTO `Dealerships` VALUES (1,'Chums Auto','123 Cherry Rd, Rock Hill','803-123-4567'),(2,'Budget Cars','456 Elm St, Charlotte','704-987-6543'),(3,'Premium Cars','789 Oak Ave, Monroe','980-321-7654'),(4,'Electric Cars','101 Green Way, Concord','704-246-8100'),(5,'Trucks Galore','202 Country Rd, Midland','704-135-2468');
/*!40000 ALTER TABLE `Dealerships` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Inventory`
--

DROP TABLE IF EXISTS `Inventory`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Inventory` (
  `DealershipID` int NOT NULL,
  `VIN` int NOT NULL,
  UNIQUE KEY `VIN` (`VIN`),
  KEY `DealershipID` (`DealershipID`),
  CONSTRAINT `inventory_ibfk_1` FOREIGN KEY (`DealershipID`) REFERENCES `Dealerships` (`DealershipID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Inventory`
--

LOCK TABLES `Inventory` WRITE;
/*!40000 ALTER TABLE `Inventory` DISABLE KEYS */;
INSERT INTO `Inventory` VALUES (1,12321),(1,14001),(1,14341),(1,16431),(2,15601),(2,22001),(3,39021),(4,43301);
/*!40000 ALTER TABLE `Inventory` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `lease_contracts`
--

DROP TABLE IF EXISTS `lease_contracts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `lease_contracts` (
  `contract_id` int NOT NULL,
  `VIN` int NOT NULL,
  `lease_start_date` date NOT NULL,
  `total_price` decimal(10,2) NOT NULL,
  `lease_fee` decimal(10,2) NOT NULL,
  `expected_ending_value` decimal(10,2) NOT NULL,
  `monthly_payment` decimal(10,2) NOT NULL,
  `customer_name` varchar(255) NOT NULL,
  `customer_email` varchar(255) NOT NULL,
  `customer_id` int NOT NULL,
  PRIMARY KEY (`VIN`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `lease_contracts`
--

LOCK TABLES `lease_contracts` WRITE;
/*!40000 ALTER TABLE `lease_contracts` DISABLE KEYS */;
INSERT INTO `lease_contracts` VALUES (8,9023,'2024-11-11',1041540.00,70000.00,500000.00,30833.33,'Emma Mcmandy','emma.mandwand@wanda.com',0),(8,12321,'2024-12-15',24420.00,1540.00,11000.00,678.33,'Alan Chumsawang','alanchumsawang@yahoo.com',8),(7,90243,'2024-11-11',1041540.00,70000.00,500000.00,30833.33,'Emma Mcmandy','emma.mandwand@wanda.com',7);
/*!40000 ALTER TABLE `lease_contracts` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Sales_Contracts`
--

DROP TABLE IF EXISTS `Sales_Contracts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Sales_Contracts` (
  `Contract_ID` int NOT NULL AUTO_INCREMENT,
  `VIN` int NOT NULL,
  `isFinanced` tinyint(1) DEFAULT NULL,
  `Loan_Term` int DEFAULT NULL,
  `Total_Price` decimal(10,2) DEFAULT NULL,
  `Sale_Date` date DEFAULT NULL,
  `customerName` varchar(50) DEFAULT NULL,
  `customerEmail` varchar(100) DEFAULT NULL,
  `customerId` int DEFAULT NULL,
  PRIMARY KEY (`Contract_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Sales_Contracts`
--

LOCK TABLES `Sales_Contracts` WRITE;
/*!40000 ALTER TABLE `Sales_Contracts` DISABLE KEYS */;
INSERT INTO `Sales_Contracts` VALUES (1,14001,1,48,38000.00,'2024-02-20','John Smith','john.smith@example.com',1),(2,15601,0,0,55000.00,'2024-03-10','Emily Johnson','emily.johnson@example.com',2),(3,16431,1,36,18000.00,'2024-01-15','Michael Brown','michael.brown@example.com',3),(4,22001,1,24,25000.00,'2024-03-25','Sarah Davis','sarah.davis@example.com',4),(5,39021,0,0,30000.00,'2024-04-05','James Wilson','james.wilson@example.com',5),(6,43301,1,24,50995.00,'2024-05-15','Olivia Martinez','olivia.martinez@example.com',6);
/*!40000 ALTER TABLE `Sales_Contracts` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Vehicles`
--

DROP TABLE IF EXISTS `Vehicles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Vehicles` (
  `VIN` int NOT NULL,
  `Make` varchar(15) DEFAULT NULL,
  `Model` varchar(20) DEFAULT NULL,
  `Type` varchar(15) DEFAULT NULL,
  `Color` varchar(10) DEFAULT NULL,
  `Odometer` int NOT NULL,
  `Price` decimal(10,2) DEFAULT NULL,
  `Sold` tinyint(1) NOT NULL DEFAULT '0',
  `Year` int DEFAULT NULL,
  PRIMARY KEY (`VIN`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Vehicles`
--

LOCK TABLES `Vehicles` WRITE;
/*!40000 ALTER TABLE `Vehicles` DISABLE KEYS */;
INSERT INTO `Vehicles` VALUES (12321,'Toyota','Camry','Sedan','Blue',45000,22000.00,0,2023),(14001,'Chevrolet','Tahoe','SUV','Silver',60000,38000.00,1,2020),(14341,'Ford','F-150','Truck','Black',75000,30000.00,0,2022),(15601,'BMW','X5','SUV','Black',25000,55000.00,1,2019),(16431,'Honda','Civic','Sedan','White',50000,18000.00,1,2021),(22001,'Subaru','Outback','Wagon','Blue',45000,25000.00,1,2023),(39021,'Dodge','Charger','Sedan','Red',40000,30000.00,1,2022),(43301,'Tesla','Model Y','Electric','Silver',15000,48000.00,1,2024),(60032,'Porsche','911','Coupe','Red',0,100000.00,0,2024),(90243,'Bai-Bai','V3','Falcon 9','Blue',2000000,1000000.00,0,2024);
/*!40000 ALTER TABLE `Vehicles` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-12-15 12:15:56
