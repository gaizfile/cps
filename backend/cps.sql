DROP DATABASE IF EXISTS `cps`;
CREATE DATABASE IF NOT EXISTS `cps` DEFAULT CHARACTER SET latin1 COLLATE latin1_swedish_ci;
USE `cps`;

DROP table if EXISTS `ci_sessions`;
CREATE TABLE IF NOT EXISTS `ci_sessions` (
  `session_id` varchar(40) NOT NULL DEFAULT '0',
  `ip_address` varchar(45) NOT NULL DEFAULT '0',
  `user_agent` varchar(120) NOT NULL,
  `last_activity` int(10) unsigned NOT NULL DEFAULT '0',
  `user_data` text NOT NULL,
  PRIMARY KEY (`session_id`),
  KEY `last_activity_idx` (`last_activity`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


DROP table if EXISTS `user_roles`;
CREATE TABLE IF NOT EXISTS `user_roles` (
  `user_RoleId` int(11) NOT NULL AUTO_INCREMENT,
  `user_RoleName` varchar(50) NOT NULL DEFAULT '',
  PRIMARY KEY (`user_RoleId`)
);


INSERT INTO `user_roles` (`user_RoleId`, `user_RoleName`) VALUES
(1, 'Admin'),
(2, 'Government');



DROP table if EXISTS `user`;
CREATE TABLE IF NOT EXISTS `user` (
  `user_ID` int(11) NOT NULL AUTO_INCREMENT,
  `user_Name` varchar(250) NOT NULL,
  `user_Password` varchar(110) NOT NULL,
  `user_RoleID` int(11) NOT NULL,
  `user_Title` varchar(5) NOT NULL,
  `user_FullName` varchar(30) NOT NULL,
  `user_NIC` varchar(10),
  `user_Passport` varchar(10),
  `user_DOB` varchar(10),
  `user_Telephone` varchar(10),
  `user_MobileNo` varchar(40),
  `user_Gender` char(1),
  `user_Address` varchar(40),
  `user_Email` varchar(255) NOT NULL,
  `user_Create_Date` varchar(40) NOT NULL,
  `user_Last_Update_Date` varchar(40) NOT NULL,
  PRIMARY KEY (`user_ID`),
  FOREIGN KEY (`user_RoleID`) REFERENCES `user_roles`(`user_RoleId`) ON DELETE CASCADE
);


INSERT INTO `user` (`user_ID`, `user_Name`, `user_Password`, `user_RoleID`, `user_Title`, `user_FullName`, `user_NIC`, `user_Passport`, `user_DOB`, `user_Telephone`, `user_MobileNo`, `user_Gender`, `user_Address`,`user_Email`,`user_Create_Date`,`user_Last_Update_Date`) VALUES
(1, 'udara', '1000:b94231cc15370e361533a1cfdce59f53ee5c69e7539cffe2:7d99cee318918d03af37e0918bbeb50a6d76d576de2a1765', 1,'Mr.','Udara Pathirage','901871310v','9985564','1990-07-05','0785227767','0344932044','M','No.272, Wewala, Horana','udarapathirage@gmail.com','2014-02-05','2014-06-08'),
(2, 'ashani', '1000:b94231cc15370e361533a1cfdce59f53ee5c69e7539cffe2:7d99cee318918d03af37e0918bbeb50a6d76d576de2a1765', 2,'Ms.','Ashani Rathnayaka','901771310v','9985565','1990-07-06','0785227768','0344932045','F','No.272, Wewala, Horana','udarapathirage@gmail.com','2014-02-05','2014-06-08'),
(3, 'gayan', '1000:b94231cc15370e361533a1cfdce59f53ee5c69e7539cffe2:7d99cee318918d03af37e0918bbeb50a6d76d576de2a1765', 2,'Mr.','Gayan Lakshan','901881310v','9985566','1990-07-07','0785227769','0344932046','M','No.272, Wewala, Horana','udarapathirage@gmail.com','2014-02-05','2014-06-08'),
(4, 'nirasha', '1000:b94231cc15370e361533a1cfdce59f53ee5c69e7539cffe2:7d99cee318918d03af37e0918bbeb50a6d76d576de2a1765', 1,'Ms.','Nirasha Chathurangi','911771310v','9985563','1990-07-23','0785227767','0112897678','F','Railway Road, Maharagama','chathuranginirasha@gmail.com','2014-02-05','2014-06-08'),
(5, 'nuwan', '1000:b94231cc15370e361533a1cfdce59f53ee5c69e7539cffe2:7d99cee318918d03af37e0918bbeb50a6d76d576de2a1765', 2,'Mr.','Gayan Lakshan','901881310v','9985566','1990-07-07','0785227769','0344932046','M','No.272, Wewala, Horana','udarapathirage@gmail.com','2014-02-05','2014-06-08'),
(6, 'sharmal', '1000:b94231cc15370e361533a1cfdce59f53ee5c69e7539cffe2:7d99cee318918d03af37e0918bbeb50a6d76d576de2a1765', 1,'Mr.','Gayan Lakshan','901881310v','9985566','1990-07-07','0785227769','0344932046','M','No.272, Wewala, Horana','udarapathirage@gmail.com','2014-02-05','2014-06-08'),
(7, 'nethmini', '1000:b94231cc15370e361533a1cfdce59f53ee5c69e7539cffe2:7d99cee318918d03af37e0918bbeb50a6d76d576de2a1765', 2,'Mr.','Gayan Lakshan','901881310v','9985566','1990-07-07','0785227769','0344932046','M','No.272, Wewala, Horana','udarapathirage@gmail.com','2014-02-05','2014-06-08'),
(8, 'lakshan', '1000:b94231cc15370e361533a1cfdce59f53ee5c69e7539cffe2:7d99cee318918d03af37e0918bbeb50a6d76d576de2a1765', 1,'Mr.','Gayan Lakshan','901881310v','9985566','1990-07-07','0785227769','0344932046','M','No.272, Wewala, Horana','udarapathirage@gmail.com','2014-02-05','2014-06-08'),
(9, 'shashidu', '1000:b94231cc15370e361533a1cfdce59f53ee5c69e7539cffe2:7d99cee318918d03af37e0918bbeb50a6d76d576de2a1765', 2,'Mr.','Gayan Lakshan','901881310v','9985566','1990-07-07','0785227769','0344932046','M','No.272, Wewala, Horana','udarapathirage@gmail.com','2014-02-05','2014-06-08'),
(10, 'sahan', '1000:b94231cc15370e361533a1cfdce59f53ee5c69e7539cffe2:7d99cee318918d03af37e0918bbeb50a6d76d576de2a1765', 2,'Mr.','Gayan Lakshan','901881310v','9985566','1990-07-07','0785227769','0344932046','M','No.272, Wewala, Horana','udarapathirage@gmail.com','2014-02-05','2014-06-08'),
(11, 'chinthaka', '1000:b94231cc15370e361533a1cfdce59f53ee5c69e7539cffe2:7d99cee318918d03af37e0918bbeb50a6d76d576de2a1765', 1,'Mr.','Gayan Lakshan','901881310v','9985566','1990-07-07','0785227769','0344932046','M','No.272, Wewala, Horana','udarapathirage@gmail.com','2014-02-05','2014-06-08'),
(12, 'wenura', '1000:b94231cc15370e361533a1cfdce59f53ee5c69e7539cffe2:7d99cee318918d03af37e0918bbeb50a6d76d576de2a1765', 1,'Mr.','Gayan Lakshan','901881310v','9985566','1990-07-07','0785227769','0344932046','M','No.272, Wewala, Horana','udarapathirage@gmail.com','2014-02-05','2014-06-08'),
(13, 'isuru', '1000:b94231cc15370e361533a1cfdce59f53ee5c69e7539cffe2:7d99cee318918d03af37e0918bbeb50a6d76d576de2a1765', 2,'Mr.','Gayan Lakshan','901881310v','9985566','1990-07-07','0785227769','0344932046','M','No.272, Wewala, Horana','udarapathirage@gmail.com','2014-02-05','2014-06-08'),
(14, 'gayashan', '1000:b94231cc15370e361533a1cfdce59f53ee5c69e7539cffe2:7d99cee318918d03af37e0918bbeb50a6d76d576de2a1765', 1,'Mr.','Gayan Lakshan','901881310v','9985566','1990-07-07','0785227769','0344932046','M','No.272, Wewala, Horana','udarapathirage@gmail.com','2014-02-05','2014-06-08'),
(15, 'sajith', '1000:b94231cc15370e361533a1cfdce59f53ee5c69e7539cffe2:7d99cee318918d03af37e0918bbeb50a6d76d576de2a1765', 2,'Mr.','Gayan Lakshan','901881310v','9985566','1990-07-07','0785227769','0344932046','M','No.272, Wewala, Horana','udarapathirage@gmail.com','2014-02-05','2014-06-08'),
(16, 'asiri', '1000:b94231cc15370e361533a1cfdce59f53ee5c69e7539cffe2:7d99cee318918d03af37e0918bbeb50a6d76d576de2a1765', 2,'Mr.','Gayan Lakshan','901881310v','9985566','1990-07-07','0785227769','0344932046','M','No.272, Wewala, Horana','udarapathirage@gmail.com','2014-02-05','2014-06-08'),
(17, 'nayana', '1000:b94231cc15370e361533a1cfdce59f53ee5c69e7539cffe2:7d99cee318918d03af37e0918bbeb50a6d76d576de2a1765', 1,'Mr.','Gayan Lakshan','901881310v','9985566','1990-07-07','0785227769','0344932046','M','No.272, Wewala, Horana','udarapathirage@gmail.com','2014-02-05','2014-06-08');


DROP table if EXISTS `user_log`;
CREATE TABLE IF NOT EXISTS `user_log` (
	`log_ID` int(12) NOT NULL AUTO_INCREMENT,
	`log_user_ID` int(11) NOT NULL,
	`log_section` varchar(250) NOT NULL,
	`log` varchar(250) NOT NULL,
	`log_datetime` varchar(40) NOT NULL,
	PRIMARY KEY (`log_ID`),
	FOREIGN KEY (`log_user_ID`) REFERENCES `user`(`user_ID`) ON DELETE CASCADE
);

INSERT INTO `user_log` (`log_ID`, `log_user_ID`, `log_section`, `log`, `log_datetime`) VALUES
(1, 1, 'Login', 'User loged in', '2014-02-02 12:12:25'),
(6, 1, 'User Logs', 'View UserLog, User :1', '2014-08-24 02:37:35'),
(7, 1, 'User Logs', 'View UserLog, User :17', '2014-08-24 02:37:50'),
(8, 1, 'User Logs', 'View UserLog, User :1', '2014-08-24 02:38:08'),
(9, 1, 'User Logs', 'View UserLog, User :1', '2014-08-24 02:38:13'),
(10, 1, 'User Logs', 'View UserLog, User :1', '2014-08-24 02:38:28'),
(11, 1, 'User Logs', 'View UserLog, User :1', '2014-08-24 02:38:31'),
(12, 1, 'User Logs', 'View UserLog, User :1', '2014-08-24 02:38:32'),
(13, 1, 'User Logs', 'View UserLog, User :1', '2014-08-24 02:38:33'),
(14, 1, 'User Logs', 'View UserLog, User :1', '2014-08-24 02:38:34'),
(15, 1, 'User Logs', 'View UserLog, User :1', '2014-08-24 02:38:34'),
(16, 1, 'Users Manage', 'Add User, User: ddddd', '2014-08-24 02:47:27'),
(17, 1, 'Users Manage', 'Update User, User ID: 21', '2014-08-24 02:47:41'),
(18, 1, 'Users Manage', 'Delete User, User ID: 21', '2014-08-24 02:47:46'),
(19, 1, 'User Logs', 'View UserLog, User :1', '2014-08-24 02:47:53'),
(20, 1, 'User Logs', 'View UserLog, User :1', '2014-08-24 02:49:47'),
(21, 1, 'User Logs', 'View UserLog, User :1', '2014-08-24 02:50:40'),
(22, 1, 'User Logs', 'View UserLog, User :1', '2014-08-24 02:51:20');




CREATE TABLE IF NOT EXISTS `hospital_info` (
  `hospital_ID` varchar(50) NOT NULL,
  `hospital_IP` varchar(11) NOT NULL,
  `hospital_Port` int NOT NULL,
  `hospital_Name` varchar(255) DEFAULT NULL,
  `hospital_District` varchar(50) DEFAULT NULL,
  `hospital_Province` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`hospital_ID`));


INSERT INTO `hospital_info` (`hospital_ID`, `hospital_IP`, `hospital_Port`, `hospital_Name`, `hospital_District`, `hospital_Province`) VALUES
('HR_Base_1', '127.0.0.1', '8080', 'Horana Base Hospital', 'Kaluthara', 'Western'),
('DM_BASE_1', '127.0.0.2', '8080', 'Dompe Base Hospital', 'Gampaha', 'Western');


CREATE TABLE IF NOT EXISTS `patient` (
  `patient_ID` int(11) NOT NULL AUTO_INCREMENT,
  `patient_Title` varchar(5) NOT NULL,
  `patient_FullName` varchar(30) NOT NULL,
  `patient_PersonalUsedName` varchar(30) NOT NULL,
  `patient_NIC` varchar(10) NOT NULL,
  `patient_Passport` varchar(10) NOT NULL,
  `patient_HIN` varchar(255) NOT NULL,
  `patient_Photo` varchar(10) default NULL,
  `patient_DOB` varchar(100) default NULL,
  `patient_Telephone` varchar(10) NOT NULL,
  `patient_Gender` varchar(10) NOT NULL,
  `patient_CivilStatus` varchar(20) NOT NULL,
  `patient_PreferredLanguage` varchar(10) NOT NULL,
  `patient_Citizenship` varchar(20) NOT NULL,
  `patient_Address` varchar(40) NOT NULL,
  `patient_ContactPName` varchar(40) NOT NULL,
  `patient_ContactPNo` varchar(40) NOT NULL,
  `patient_Remarks` varchar(255) DEFAULT NULL,
  `patient_Active` int(11) DEFAULT NULL,
  `patient_Hospital_ID` varchar(40) NOT NULL,
  PRIMARY KEY (`patient_ID`),
  FOREIGN KEY (`patient_Hospital_ID`) REFERENCES `hospital_info`(`hospital_ID`) ON DELETE CASCADE
);

INSERT INTO `patient` (`patient_ID`,`patient_Title`, `patient_FullName`, `patient_PersonalUsedName`, `patient_NIC`, `patient_Passport`, `patient_HIN`, `patient_Photo`, `patient_DOB`, `patient_Telephone`, `patient_Gender`, `patient_CivilStatus`, `patient_PreferredLanguage`, `patient_Citizenship`, `patient_Address`, `patient_ContactPName`, `patient_ContactPNo`, `patient_Remarks`,`patient_Active`,`patient_Hospital_ID`) VALUES
(1,'Mr.', 'Brian Walter', 'Brian', '839133345v', 'JKJKKK', '566/99043', '', '1989-09-27 00:00:00', '0728986544', 'Female', 'Single', 'Tamil', ' Sri Lankan', 'White house,Papiliyana,Nugegoda', ' Adam Walter', ' 0715765343', 'apopuuoy', 1,'HR_Base_1'),
(3,'Mr.', 'dsadas', '', '135778858v', '', '', 'null', '2013-08-13 00:00:00', '', 'Male', 'Single', '', ' ', 'dasd', ' ', ' ', '', 1,'HR_Base_1'),
(4,'Mr.', 'Miyuru', 'De Silva', '892710345V', '3243', '3242', 'null', '1989-08-23 00:00:00', '45345', 'Male', 'Single', 'Sinhala', 'ewrw', 'rewrw', ' ewrrw', ' 3242', 'sdfdsf',1,'HR_Base_1'),
(5,'Mr.', 'Brian Walter', 'Brian', '901871310v', '', '4534', '', '1989-09-27 00:00:00', '0728986544', 'Male', 'Single', 'Tamil', ' Sri Lankan', 'White house,Papiliyana,Nugegoda', ' Adam Walter', ' 0719927551', 'None',1,'HR_Base_1'),
(6,'Mr.', 'Imanka Kodi', 'Imee', '839134306v', '', '11212', '', '1989-09-27 00:00:00', '0728986544', 'Male', 'Single', 'Tamil', ' Sri Lankan', 'White house,Papiliyana,Nugegoda', ' Adam Walter', ' 0719927551', 'None',1,'HR_Base_1'),
(7,'Mr.', 'OO', 'Brian', '839130675v', '', '11141111127', '', '1989-09-27 00:00:00', '0728986544', 'Male', 'Single', 'Tamil', ' Sri Lankan', 'White house,Papiliyana,Nugegoda', ' Adam Walter', ' 0719927551', 'None',1,'HR_Base_1'),
(8,'Mr.', 'LL', 'Brian', '839134345v', '', '11141111127', '', '1989-09-27 00:00:00', '0728986544', 'Male', 'Single', 'Tamil', ' Sri Lankan', 'White house,Papiliyana,Nugegoda', ' Adam Walter', ' 0719927551', 'None',1,'HR_Base_1'),
(9,'Mr.', 'JJ', 'Brian', '911871310v', '', '911871310v', '', '1989-09-27 00:00:00', '0728986544', 'Male', 'Single', 'Tamil', ' Sri Lankan', 'White house,Papiliyana,Nugegoda', ' Adam Walter', ' 0719927551', 'None',1,'HR_Base_1'),
(10,'Mr.', 'ZZZZZ', 'Brian', '839173345v', '', '11141111127', '', '1989-09-27 00:00:00', '0728986544', 'Male', 'Single', 'Tamil', ' Sri Lankan', 'White house,Papiliyana,Nugegoda', ' Adam Walter', ' 0719927551', 'None',1,'HR_Base_1'),
(11,'Mr.', 'KK Walter', 'Brian', '839138345v', '', '11110000020', '', '1989-09-27 00:00:00', '0728986544', 'Male', 'Single', 'Tamil', ' Sri Lankan', 'White house,Papiliyana,Nugegoda', ' Adam Walter', ' 0719927551', 'None',1,'HR_Base_1'),
(12,'Mr.', 'XXXXXX', 'Brian', '839133945v', '', '11110000020', '', '1989-09-27 00:00:00', '0728986544', 'Male', 'Single', 'Tamil', ' Sri Lankan', 'White house,Papiliyana,Nugegoda', ' Adam Walter', ' 0719927551', 'None',1,'HR_Base_1'),
(13,'Mr.', 'Brian Walter', 'Brian', '839123345v', '', '55550000008', '', '1989-09-27 00:00:00', '0728986544', 'Male', 'Single', 'Tamil', ' Sri Lankan', 'White house,Papiliyana,Nugegoda', 'Ann Jerom', ' 0719927551', 'None',1,'HR_Base_1'),
(14,'Mr.', 'Ann Jerom', 'Brian', '839114345v', '', '55550000016', '', '1989-09-27 00:00:00', '0728986544', 'Male', 'Single', 'Tamil', ' Sri Lankan', 'White house,Papiliyana,Nugegoda', 'ffff', ' 0719927551', 'None',1,'HR_Base_1'),
(15,'Mr.', 'Ann Jerom', 'Brian', '839153345v', '', '55550000024', '', '1989-09-27 00:00:00', '0728986544', 'Male', 'Single', 'Tamil', ' Sri Lankan', 'White house,Papiliyana,Nugegoda', 'ffff', ' 0719927551', 'None',1,'HR_Base_1');



CREATE TABLE IF NOT EXISTS `patient_allergy` (
  `allergy_ID` int(11) NOT NULL AUTO_INCREMENT,
  `patient_ID` varchar(10) DEFAULT NULL,
  `allergy_Name` varchar(255) DEFAULT NULL,
  `allergy_Status` varchar(255) DEFAULT NULL,
  `allergy_Remarks` varchar(255) DEFAULT NULL,
  `allergy_Active` int(11) DEFAULT NULL,
  PRIMARY KEY (`allergy_ID`),
  KEY `fk_patient_allergy_patientNIC` (`patient_ID`)
  );

  
INSERT INTO `patient_allergy` (`allergy_ID`, `patient_ID`, `allergy_Name`, `allergy_Status`, `allergy_Remarks`, `allergy_Active`) VALUES
(1, '1', 'sephalexin234509890', 'Current', 'ZZIIJJ::IKK', 1),
(2, '2', 'Peanut', 'Past', 'Vomiting', 0);


CREATE TABLE IF NOT EXISTS `staff_stat` (
  `staff_stat_ID` int NOT NULL AUTO_INCREMENT,
  `staff_stat_hospital` varchar(50) NOT NULL,
  `staff_stat_year` int(4) NOT NULL DEFAULT 0,
  `staff_stat_mDoctor` int(10) Default NULL,
  `staff_stat_fDoctor` int(10) Default NULL,
  `staff_stat_mNurse` int(10) Default NULL,
  `staff_stat_fNurse` int(10) Default NULL,
  `staff_stat_mMLT` int(10) Default NULL,
  `staff_stat_fMLT` int(10) Default NULL,
  `staff_stat_mCheif_Pharmasist`  int(10) Default NULL,
  `staff_stat_fCheif_Pharmasist`  int(10) Default NULL,
  `staff_stat_mAsst_Pharmasist` int(10) Default NULL,
  `staff_stat_fAsst_Pharmasist` int(10) Default NULL,
  PRIMARY KEY (`staff_stat_ID`),
  FOREIGN KEY (`staff_stat_hospital`) REFERENCES `hospital_info`(`hospital_ID`) ON DELETE CASCADE
);

INSERT INTO `staff_stat` (`staff_stat_ID`, `staff_stat_hospital`, `staff_stat_year`, `staff_stat_mDoctor`, `staff_stat_fDoctor`, `staff_stat_mNurse`, `staff_stat_fNurse`, `staff_stat_mMLT`, `staff_stat_fMLT`, `staff_stat_mCheif_Pharmasist`, `staff_stat_fCheif_Pharmasist`, `staff_stat_mAsst_Pharmasist`, `staff_stat_fAsst_Pharmasist`) VALUES
(1, 'HR_Base_1', 2012, 15, 5, 5, 22, 4, 3, 1, 0, 4, 4),
(2, 'HR_Base_1', 2013, 16, 8, 4, 23, 4, 4, 1, 0, 5, 4),
(3, 'HR_Base_1', 2014, 15, 7, 4, 21, 4, 3, 1, 0, 6, 4),
(4, 'HR_Base_1', 2015, 18, 8, 3, 18, 7, 4, 1, 0, 3, 6),
(5, 'HR_Base_1', 2016, 25, 14, 2, 21, 5, 3, 1, 0, 2, 6),
(6, 'HR_Base_1', 2017, 29, 16, 6, 20, 4, 4, 1, 0, 4, 6),
(7, 'HR_Base_1', 2018, 21, 12, 8, 24, 5, 6, 0, 1, 5, 4),
(8, 'HR_Base_1', 2019, 25, 11, 8, 25, 5, 5, 0, 1, 3, 6),
(9, 'HR_Base_1', 2020, 30, 14, 9, 23, 4, 4, 0, 1, 2, 5),
(10, 'DM_BASE_1', 2012, 15, 5, 5, 22, 4, 3, 1, 0, 4, 4),
(11, 'DM_BASE_1', 2013, 16, 8, 4, 23, 4, 4, 1, 0, 5, 4),
(12, 'DM_BASE_1', 2014, 15, 7, 3, 24, 6, 4, 1, 0, 5, 6),
(13, 'DM_BASE_1', 2015, 18, 8, 3, 18, 7, 4, 1, 0, 3, 6),
(14, 'DM_BASE_1', 2016, 17, 7, 2, 21, 5, 3, 1, 0, 2, 6);



CREATE TABLE IF NOT EXISTS `ward_stat` (
  `ward_stat_ID` int NOT NULL AUTO_INCREMENT,
  `ward_stat_hospital` varchar(50) NOT NULL,
  `ward_stat_year` int(4) NOT NULL DEFAULT 0,
  `ward_stat_ward_no` varchar(50) Default NULL,
  `ward_stat_category` varchar(100) Default NULL,
  `ward_stat_gender` char(1) Default NULL,
  `ward_stat_beds` int Default 0,
  PRIMARY KEY (`ward_stat_ID`),
  FOREIGN KEY (`ward_stat_hospital`) REFERENCES `hospital_info`(`hospital_ID`) ON DELETE CASCADE
);

INSERT INTO `ward_stat` (`ward_stat_ID`, `ward_stat_hospital`, `ward_stat_year`, `ward_stat_ward_no`, `ward_stat_category`, `ward_stat_gender`, `ward_stat_beds`) VALUES
(1, 'DM_BASE_1', 2014, 'Ward-01', 'Medical Wards', 'F', 8),
(2, 'DM_BASE_1', 2014, 'Ward-02', 'Medical Wards', 'F', 15),
(3, 'DM_BASE_1', 2014, 'Ward-03', 'Medical Wards', 'M', 20),
(4, 'DM_BASE_1', 2014, 'Ward-04', 'Paediatric Wards', 'M', 8),
(5, 'DM_BASE_1', 2014, 'Ward-05', 'Maternity Wards', 'F', 3),
(6, 'DM_BASE_1', 2014, 'Ward-06', 'Surgical Wards', 'M', 4),
(7, 'DM_BASE_1', 2014, 'Ward-07', 'Chilren Wards', 'F', 16),
(8, 'DM_BASE_1', 2014, 'Ward-08', 'Medical Wards', 'M', 3),
(9, 'DM_BASE_1', 2014, 'Ward-09', 'Surgical Wards', 'M', 4),
(10, 'DM_BASE_1', 2014, 'Ward-10', 'Surgical Wards', 'M', 7),
(11, 'DM_BASE_1', 2014, 'Ward-11', 'Surgical Wards', 'F', 10),
(12, 'DM_BASE_1', 2014, 'Ward-12', 'Chilren Wards', 'M', 14),
(13, 'HR_BASE_1', 2014, 'Ward-01', 'Medical Wards', 'F', 8),
(14, 'HR_BASE_1', 2014, 'Ward-02', 'Medical Wards', 'F', 15),
(15, 'HR_BASE_1', 2014, 'Ward-03', 'Medical Wards', 'M', 20),
(16, 'HR_BASE_1', 2014, 'Ward-04', 'Paediatric Wards', 'M', 8),
(17, 'HR_BASE_1', 2014, 'Ward-05', 'Maternity Wards', 'F', 3),
(18, 'HR_BASE_1', 2014, 'Ward-06', 'Surgical Wards', 'M', 4),
(19, 'HR_BASE_1', 2014, 'Ward-07', 'Chilren Wards', 'F', 16),
(20, 'HR_BASE_1', 2014, 'Ward-08', 'Medical Wards', 'M', 3),
(21, 'HR_BASE_1', 2014, 'Ward-09', 'Surgical Wards', 'M', 4);
