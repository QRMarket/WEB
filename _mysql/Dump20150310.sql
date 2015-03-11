CREATE DATABASE  IF NOT EXISTS `QR_Market_DB` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `QR_Market_DB`;
-- MySQL dump 10.13  Distrib 5.6.17, for osx10.6 (i386)
--
-- Host: localhost    Database: QR_Market_DB
-- ------------------------------------------------------
-- Server version	5.6.20

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `address`
--

DROP TABLE IF EXISTS `address`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `address` (
  `aid` varchar(45) NOT NULL,
  `city` varchar(45) NOT NULL,
  `borough` varchar(45) NOT NULL,
  `locality` varchar(45) NOT NULL,
  PRIMARY KEY (`aid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `address`
--

LOCK TABLES `address` WRITE;
/*!40000 ALTER TABLE `address` DISABLE KEYS */;
INSERT INTO `address` VALUES ('c_06_000','Ankara','Çankaya','Emek'),('c_06_001','Ankara','Çankaya','Bahçelievler'),('c_06_002','Ankara','Çankaya','Besevler'),('c_06_003','Ankara','Çankaya','Balgat'),('c_06_004','Ankara','Çankaya','Dikmen'),('c_06_005','Ankara','Çankaya','Maltepe'),('c_06_006','Ankara','Çankaya','G.O.P'),('c_06_007','Ankara','Çankaya','Bilkent'),('c_06_008','Ankara','Keçiören','Bilkent'),('c_06_009','Ankara','Mamak','Dikimevi'),('c_06_010','Ankara','Sincan','Sincan'),('c_06_011','Ankara','Etimesgut','Eryaman'),('c_06_012','Ankara','Çankaya','Kizilay'),('c_06_013','Ankara','Çankaya','Kolej'),('c_06_014','Ankara','Çankaya','ODTU'),('c_06_015','Ankara','Çankaya','Oran');
/*!40000 ALTER TABLE `address` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `companies`
--

DROP TABLE IF EXISTS `companies`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `companies` (
  `cid` varchar(45) NOT NULL,
  `companyName` varchar(45) NOT NULL,
  PRIMARY KEY (`cid`),
  UNIQUE KEY `cid_UNIQUE` (`cid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `companies`
--

LOCK TABLES `companies` WRITE;
/*!40000 ALTER TABLE `companies` DISABLE KEYS */;
INSERT INTO `companies` VALUES ('c_34567','Migros'),('c_34568','Real'),('c_34569','Bim');
/*!40000 ALTER TABLE `companies` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cpRelation`
--

DROP TABLE IF EXISTS `cpRelation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cpRelation` (
  `cprID` varchar(45) NOT NULL,
  `c_id` varchar(45) NOT NULL,
  `p_id` varchar(45) NOT NULL,
  `p_price` double NOT NULL,
  PRIMARY KEY (`cprID`),
  KEY `cid_idx` (`c_id`),
  KEY `product_idx` (`p_id`),
  CONSTRAINT `company` FOREIGN KEY (`c_id`) REFERENCES `companies` (`cid`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `product` FOREIGN KEY (`p_id`) REFERENCES `products` (`pid`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cpRelation`
--

LOCK TABLES `cpRelation` WRITE;
/*!40000 ALTER TABLE `cpRelation` DISABLE KEYS */;
INSERT INTO `cpRelation` VALUES ('cpr_001','c_34568','p_123456',2.25),('cpr_002','c_34567','p_123456',2.15),('cpr_003','c_34568','p_123456',2.4),('cpr_004','c_34567','p_123457',7),('cpr_005','c_34567','p_123458',3.4);
/*!40000 ALTER TABLE `cpRelation` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `distributerAddress`
--

DROP TABLE IF EXISTS `distributerAddress`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `distributerAddress` (
  `disaddID` varchar(45) NOT NULL,
  `disID` varchar(45) NOT NULL,
  `addID` varchar(45) NOT NULL,
  `address_type` varchar(45) DEFAULT NULL,
  `minPriceForDeliver` int(11) DEFAULT NULL,
  `priceType` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`disaddID`),
  KEY `dissadd_disID_idx` (`disID`),
  KEY `disadd_addID_idx` (`addID`),
  CONSTRAINT `disadd_addID` FOREIGN KEY (`addID`) REFERENCES `address` (`aid`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `dissadd_disID` FOREIGN KEY (`disID`) REFERENCES `distributers` (`distID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `distributerAddress`
--

LOCK TABLES `distributerAddress` WRITE;
/*!40000 ALTER TABLE `distributerAddress` DISABLE KEYS */;
/*!40000 ALTER TABLE `distributerAddress` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `distributers`
--

DROP TABLE IF EXISTS `distributers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `distributers` (
  `distID` varchar(45) NOT NULL,
  `comID` varchar(45) NOT NULL,
  PRIMARY KEY (`distID`),
  KEY `c_id_idx` (`comID`),
  CONSTRAINT `dist_com_id` FOREIGN KEY (`comID`) REFERENCES `companies` (`cid`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `distributers`
--

LOCK TABLES `distributers` WRITE;
/*!40000 ALTER TABLE `distributers` DISABLE KEYS */;
/*!40000 ALTER TABLE `distributers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `marketUser`
--

DROP TABLE IF EXISTS `marketUser`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `marketUser` (
  `mu_id` varchar(45) NOT NULL,
  `mu_mail` varchar(45) NOT NULL,
  `mu_password` varchar(45) NOT NULL,
  `mu_name` varchar(45) DEFAULT NULL,
  `mu_surname` varchar(45) DEFAULT NULL,
  `mu_phone` varchar(45) DEFAULT NULL,
  `mu_type` varchar(45) NOT NULL DEFAULT 'PRECIOUS',
  PRIMARY KEY (`mu_id`),
  UNIQUE KEY `mu_id_UNIQUE` (`mu_id`),
  UNIQUE KEY `mu_mail_UNIQUE` (`mu_mail`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `marketUser`
--

LOCK TABLES `marketUser` WRITE;
/*!40000 ALTER TABLE `marketUser` DISABLE KEYS */;
INSERT INTO `marketUser` VALUES ('123456','kskaraca@gmail.com','12345','kemal sami','karaca',NULL,'PRECIOUS'),('123457','celal.selcuk@gmail.com','12345','celal selcuk','karaca',NULL,'PRECIOUS'),('123458','root','root','root','root',NULL,'DEALER'),('123459','admin','admin','admin','admin',NULL,'MARKETADMIN'),('123461','kemal','kemal','kemal',NULL,NULL,'PRECIOUS');
/*!40000 ALTER TABLE `marketUser` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `opRelation`
--

DROP TABLE IF EXISTS `opRelation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `opRelation` (
  `oid` varchar(45) NOT NULL,
  `pid` varchar(45) NOT NULL,
  `quantity` double NOT NULL,
  KEY `o_id_idx` (`oid`),
  KEY `p_id_idx` (`pid`),
  CONSTRAINT `o_id` FOREIGN KEY (`oid`) REFERENCES `orders` (`oid`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `p_id` FOREIGN KEY (`pid`) REFERENCES `cpRelation` (`cprID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `opRelation`
--

LOCK TABLES `opRelation` WRITE;
/*!40000 ALTER TABLE `opRelation` DISABLE KEYS */;
INSERT INTO `opRelation` VALUES ('or_71e57d9d-05fe-423d-b8c7-ab32778f8d1c','cpr_003',4),('or_71e57d9d-05fe-423d-b8c7-ab32778f8d1c','cpr_005',7),('or_71e57d9d-05fe-423d-b8c7-ab32778f8d1c','cpr_001',1),('order_4dad4b64-9139-4bbe-ae22-a61a1637c092','cpr_001',5),('order_a84902b0-b195-4861-a307-02e419766ff4','cpr_001',1),('order_da90c44d-1dbc-47dc-a9f3-eb455b4079fa','cpr_001',1),('order_da90c44d-1dbc-47dc-a9f3-eb455b4079fa','cpr_005',1),('order_c425546e-7b73-4060-aab1-a406f1127e07','cpr_005',1),('order_c425546e-7b73-4060-aab1-a406f1127e07','cpr_003',1),('order_c425546e-7b73-4060-aab1-a406f1127e07','cpr_003',1);
/*!40000 ALTER TABLE `opRelation` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `orders`
--

DROP TABLE IF EXISTS `orders`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `orders` (
  `oid` varchar(45) NOT NULL,
  `type` varchar(45) NOT NULL DEFAULT 'PAST',
  `date` datetime DEFAULT NULL,
  `user_id` varchar(45) NOT NULL,
  `comp_id` varchar(45) NOT NULL DEFAULT 'c_34567',
  PRIMARY KEY (`oid`),
  KEY `u_id_idx` (`user_id`),
  KEY `c_id_idx` (`comp_id`),
  CONSTRAINT `c_id` FOREIGN KEY (`comp_id`) REFERENCES `companies` (`cid`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `u_id` FOREIGN KEY (`user_id`) REFERENCES `marketUser` (`mu_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orders`
--

LOCK TABLES `orders` WRITE;
/*!40000 ALTER TABLE `orders` DISABLE KEYS */;
INSERT INTO `orders` VALUES ('order_4dad4b64-9139-4bbe-ae22-a61a1637c092','UNDELIVERED','2015-02-25 02:08:56','123457','c_34567'),('order_a84902b0-b195-4861-a307-02e419766ff4','UNDELIVERED','2015-02-25 02:18:04','123457','c_34567'),('order_c425546e-7b73-4060-aab1-a406f1127e07','UNDELIVERED','2015-03-10 00:06:30','123461','c_34567'),('order_da90c44d-1dbc-47dc-a9f3-eb455b4079fa','UNDELIVERED','2015-03-09 23:59:12','123461','c_34567'),('or_71e57d9d-05fe-423d-b8c7-ab32778f8d1c','DELIVERED','2008-03-02 00:00:00','123456','c_34567');
/*!40000 ALTER TABLE `orders` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `piRelation`
--

DROP TABLE IF EXISTS `piRelation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `piRelation` (
  `pi_id` varchar(45) NOT NULL,
  `productID` varchar(45) NOT NULL,
  `imageID` varchar(45) NOT NULL,
  PRIMARY KEY (`pi_id`),
  KEY `pid_idx` (`productID`),
  CONSTRAINT `pid` FOREIGN KEY (`productID`) REFERENCES `products` (`pid`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `piRelation`
--

LOCK TABLES `piRelation` WRITE;
/*!40000 ALTER TABLE `piRelation` DISABLE KEYS */;
INSERT INTO `piRelation` VALUES ('pi_cc1','p_123456','pim061002.jpg'),('pi_cc2','p_123457','default.jpg'),('pi_cc3','p_123458','pim061001.jpg'),('pi_cc4','p_123458','pim061003.jpg');
/*!40000 ALTER TABLE `piRelation` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `products`
--

DROP TABLE IF EXISTS `products`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `products` (
  `pid` varchar(45) NOT NULL,
  `productName` varchar(45) NOT NULL,
  `productPriceType` varchar(45) NOT NULL,
  `productBranch` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`pid`),
  UNIQUE KEY `pid_UNIQUE` (`pid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `products`
--

LOCK TABLES `products` WRITE;
/*!40000 ALTER TABLE `products` DISABLE KEYS */;
INSERT INTO `products` VALUES ('p_123456','Torku','TL',NULL),('p_123457','Nutella','TL',NULL),('p_123458','Coca-cola','TL',NULL);
/*!40000 ALTER TABLE `products` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `uaRelation`
--

DROP TABLE IF EXISTS `uaRelation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `uaRelation` (
  `ua_id` varchar(45) CHARACTER SET latin5 NOT NULL,
  `uid` varchar(45) NOT NULL,
  `aid` varchar(45) NOT NULL,
  `street` varchar(45) DEFAULT NULL,
  `avenue` varchar(45) DEFAULT NULL,
  `desc` varchar(245) DEFAULT NULL,
  PRIMARY KEY (`ua_id`),
  KEY `ua_uid_idx` (`uid`),
  KEY `ua_aid_idx` (`aid`),
  CONSTRAINT `ua_aid` FOREIGN KEY (`aid`) REFERENCES `address` (`aid`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `ua_uid` FOREIGN KEY (`uid`) REFERENCES `marketUser` (`mu_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `uaRelation`
--

LOCK TABLES `uaRelation` WRITE;
/*!40000 ALTER TABLE `uaRelation` DISABLE KEYS */;
INSERT INTO `uaRelation` VALUES ('ua_001','123461','c_06_000','8','20',NULL);
/*!40000 ALTER TABLE `uaRelation` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `udRelation`
--

DROP TABLE IF EXISTS `udRelation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `udRelation` (
  `udID` int(11) NOT NULL,
  `mu_id` varchar(45) NOT NULL,
  `dis_id` varchar(45) NOT NULL,
  `employeeType` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`udID`),
  KEY `ud_mu_id_idx` (`mu_id`),
  KEY `ud_dis_id_idx` (`dis_id`),
  CONSTRAINT `ud_dis_id` FOREIGN KEY (`dis_id`) REFERENCES `distributers` (`distID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `ud_mu_id` FOREIGN KEY (`mu_id`) REFERENCES `marketUser` (`mu_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `udRelation`
--

LOCK TABLES `udRelation` WRITE;
/*!40000 ALTER TABLE `udRelation` DISABLE KEYS */;
/*!40000 ALTER TABLE `udRelation` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2015-03-10 15:10:50
