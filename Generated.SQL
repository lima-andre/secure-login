/*
Created: 2016/02/09
Modified: 2016/02/11
Model: MySQL 5.6
Database: MySQL 5.6
*/


-- Create tables section -------------------------------------------------

-- Table UserAccount

CREATE TABLE `UserAccount`
(
  `userid` Int NOT NULL AUTO_INCREMENT,
  `lastname` Varchar(50) NOT NULL,
  `firstname` Varchar(20) NOT NULL,
  `username` Varchar(16) NOT NULL,
  `userpwd` Varchar(16) NOT NULL,
  `email` Varchar(50) NOT NULL,
  `adminaccount` Bool NOT NULL,
  `activeaccount` Bool NOT NULL,
  `borndate` Date NOT NULL,
  `mobilephone` Varchar(15),
  `homephone` Varchar(15),
  `workphone` Varchar(15),
  PRIMARY KEY (`userid`),
 UNIQUE `userid` (`userid`)
)
;

-- Table ConnectionHistory

CREATE TABLE `ConnectionHistory`
(
  `idconnection` Int NOT NULL AUTO_INCREMENT,
  `userid` Int NOT NULL,
  `timestampcreation` Timestamp NOT NULL,
  `validconnection` Bool NOT NULL,
  `ipconnection` Varchar(20) NOT NULL,
  PRIMARY KEY (`idconnection`),
 UNIQUE `idconnection` (`idconnection`)
)
;

CREATE INDEX `IX_Relationship6` ON `ConnectionHistory` (`userid`)
;

-- Table OneHourBlock

CREATE TABLE `OneHourBlock`
(
  `id` Int NOT NULL AUTO_INCREMENT,
  `userid` Int,
  `ipconnection` Varchar(20),
  `timestempcreation` Timestamp NOT NULL,
  PRIMARY KEY (`id`),
 UNIQUE `id` (`id`)
)
;

CREATE INDEX `IX_Relationship3` ON `OneHourBlock` (`userid`)
;

-- Table OneDayBlock

CREATE TABLE `OneDayBlock` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `ipConnection` varchar(255) DEFAULT NULL,
  `timestampCreation` datetime DEFAULT NULL,
  `userId` int(11) DEFAULT NULL,
  `userName` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
);
CREATE INDEX `IX_Relationship4` ON `OneDayBlock` (`userid`)
;

-- Table BlackList

CREATE TABLE `BlackList`
(
  `id` Int NOT NULL AUTO_INCREMENT,
  `userid` Int,
  `ipconnection` Varchar(20),
  `timestempcreation` Timestamp NOT NULL,
  PRIMARY KEY (`id`),
 UNIQUE `id` (`id`)
)
;

CREATE INDEX `IX_Relationship5` ON `BlackList` (`userid`)
;

-- Create relationships section ------------------------------------------------- 

ALTER TABLE `OneHourBlock` ADD CONSTRAINT `userOneHourBlock` FOREIGN KEY (`userid`) REFERENCES `UserAccount` (`userid`) ON DELETE NO ACTION ON UPDATE NO ACTION
;

ALTER TABLE `24HoursBlock` ADD CONSTRAINT `user24HoursBlock` FOREIGN KEY (`userid`) REFERENCES `UserAccount` (`userid`) ON DELETE NO ACTION ON UPDATE NO ACTION
;

ALTER TABLE `BlackList` ADD CONSTRAINT `userBlackList` FOREIGN KEY (`userid`) REFERENCES `UserAccount` (`userid`) ON DELETE NO ACTION ON UPDATE NO ACTION
;

ALTER TABLE `ConnectionHistory` ADD CONSTRAINT `userConnectionHistory` FOREIGN KEY (`userid`) REFERENCES `UserAccount` (`userid`) ON DELETE NO ACTION ON UPDATE NO ACTION
;

alter table onehourblock add column timestampcreation timestamp;

alter table onehourblock add column username varchar(16);

alter table connectionhistory add column username varchar(16);

alter table connectionhistory add column duration varchar(5);

alter table OneDayBlock add column username varchar(16);
