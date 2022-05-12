/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50712
Source Host           : localhost:3306
Source Database       : p2p

Target Server Type    : MYSQL
Target Server Version : 50712
File Encoding         : 65001

Date: 2022-05-12 17:56:08
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for account
-- ----------------------------
DROP TABLE IF EXISTS `account`;
CREATE TABLE `account` (
  `id` bigint(20) NOT NULL,
  `tradePassword` varchar(255) DEFAULT NULL,
  `usableAmount` decimal(18,4) NOT NULL,
  `freezedAmount` decimal(18,4) NOT NULL,
  `borrowLimit` decimal(18,4) NOT NULL,
  `version` int(11) NOT NULL,
  `unReceiveInterest` decimal(18,4) NOT NULL,
  `unReceivePrincipal` decimal(18,4) NOT NULL,
  `unReturnAmount` decimal(18,4) NOT NULL,
  `remainBorrowLimit` decimal(18,4) NOT NULL,
  `verifyCode` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for accountflow
-- ----------------------------
DROP TABLE IF EXISTS `accountflow`;
CREATE TABLE `accountflow` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `accountActionType` tinyint(4) NOT NULL,
  `amount` decimal(18,4) NOT NULL,
  `note` varchar(255) NOT NULL,
  `balance` decimal(18,4) NOT NULL,
  `freezed` decimal(18,4) NOT NULL,
  `actionTime` datetime NOT NULL,
  `account_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for bid
-- ----------------------------
DROP TABLE IF EXISTS `bid`;
CREATE TABLE `bid` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `actualRate` decimal(8,4) NOT NULL,
  `availableAmount` decimal(18,4) NOT NULL,
  `bidRequestId` bigint(20) NOT NULL,
  `bidUser_id` bigint(20) NOT NULL,
  `bidTime` datetime NOT NULL,
  `bidRequestTitle` varchar(255) DEFAULT NULL,
  `bidRequestState` tinyint(4) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for bidrequest
-- ----------------------------
DROP TABLE IF EXISTS `bidrequest`;
CREATE TABLE `bidrequest` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `version` int(11) NOT NULL,
  `bidRequestType` tinyint(4) NOT NULL,
  `bidRequestState` tinyint(4) NOT NULL,
  `bidRequestAmount` decimal(18,4) NOT NULL,
  `currentRate` decimal(8,4) NOT NULL,
  `monthes2Return` tinyint(4) NOT NULL,
  `bidCount` int(11) NOT NULL,
  `totalRewardAmount` decimal(18,4) NOT NULL,
  `currentSum` decimal(18,4) NOT NULL,
  `title` varchar(255) NOT NULL,
  `description` varchar(255) NOT NULL,
  `note` varchar(255) DEFAULT NULL,
  `disableDate` datetime DEFAULT NULL,
  `createuser_id` bigint(20) NOT NULL,
  `disableDays` tinyint(4) NOT NULL,
  `minBidAmount` decimal(18,4) NOT NULL,
  `applyTime` datetime DEFAULT NULL,
  `publishTime` datetime DEFAULT NULL,
  `returnType` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for bidrequestaudit
-- ----------------------------
DROP TABLE IF EXISTS `bidrequestaudit`;
CREATE TABLE `bidrequestaudit` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `state` tinyint(4) NOT NULL,
  `remark` varchar(255) DEFAULT NULL,
  `auditTime` datetime DEFAULT NULL,
  `applyTime` datetime NOT NULL,
  `auditor_id` bigint(20) DEFAULT NULL,
  `applier_id` bigint(20) NOT NULL,
  `bidRequestId` bigint(20) NOT NULL,
  `auditType` tinyint(4) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for expaccount
-- ----------------------------
DROP TABLE IF EXISTS `expaccount`;
CREATE TABLE `expaccount` (
  `id` bigint(20) NOT NULL,
  `version` tinyint(4) NOT NULL,
  `usableAmount` decimal(10,0) NOT NULL,
  `freezedAmount` decimal(10,0) NOT NULL,
  `unReturnExpAmount` decimal(10,0) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for expaccountflow
-- ----------------------------
DROP TABLE IF EXISTS `expaccountflow`;
CREATE TABLE `expaccountflow` (
  `id` bigint(20) NOT NULL,
  `expAccountId` bigint(20) NOT NULL,
  `actionTime` datetime NOT NULL,
  `amount` decimal(10,0) DEFAULT NULL,
  `actionType` tinyint(4) DEFAULT NULL,
  `note` varchar(255) DEFAULT NULL,
  `usableAmount` decimal(10,0) DEFAULT NULL,
  `freezedAmount` decimal(10,0) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for expaccountgrantrecord
-- ----------------------------
DROP TABLE IF EXISTS `expaccountgrantrecord`;
CREATE TABLE `expaccountgrantrecord` (
  `id` bigint(20) NOT NULL,
  `grantUserId` bigint(20) DEFAULT NULL,
  `amount` varchar(255) DEFAULT NULL,
  `grantDate` datetime DEFAULT NULL,
  `returnDate` datetime DEFAULT NULL,
  `grantType` tinyint(4) DEFAULT NULL,
  `note` varchar(255) DEFAULT NULL,
  `state` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for iplog
-- ----------------------------
DROP TABLE IF EXISTS `iplog`;
CREATE TABLE `iplog` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `ip` varchar(50) NOT NULL,
  `state` tinyint(4) NOT NULL,
  `username` varchar(50) DEFAULT NULL,
  `logintime` datetime NOT NULL,
  `userType` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for logininfo
-- ----------------------------
DROP TABLE IF EXISTS `logininfo`;
CREATE TABLE `logininfo` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `state` tinyint(4) DEFAULT NULL,
  `userType` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=86 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for mailverify
-- ----------------------------
DROP TABLE IF EXISTS `mailverify`;
CREATE TABLE `mailverify` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `userId` bigint(20) NOT NULL,
  `sendTime` datetime NOT NULL,
  `uuid` varchar(255) NOT NULL,
  `email` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for moneywithdraw
-- ----------------------------
DROP TABLE IF EXISTS `moneywithdraw`;
CREATE TABLE `moneywithdraw` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `accountNumber` varchar(50) NOT NULL,
  `bankforkname` varchar(50) NOT NULL,
  `bankName` varchar(50) DEFAULT NULL,
  `amount` decimal(18,4) NOT NULL,
  `state` tinyint(4) NOT NULL,
  `charge` decimal(18,4) NOT NULL,
  `remark` varchar(255) DEFAULT NULL,
  `auditTime` datetime DEFAULT NULL,
  `applyTime` datetime NOT NULL,
  `auditor_id` bigint(20) DEFAULT NULL,
  `applier_id` bigint(20) NOT NULL,
  `accountname` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for paymentschedule
-- ----------------------------
DROP TABLE IF EXISTS `paymentschedule`;
CREATE TABLE `paymentschedule` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `deadLine` datetime NOT NULL,
  `payDate` datetime DEFAULT NULL,
  `totalAmount` decimal(18,4) NOT NULL,
  `principal` decimal(18,4) NOT NULL,
  `interest` decimal(18,4) NOT NULL,
  `monthIndex` tinyint(4) NOT NULL,
  `state` tinyint(4) NOT NULL,
  `bidRequestType` tinyint(4) NOT NULL,
  `returnType` tinyint(4) NOT NULL,
  `bidRequestId` bigint(20) NOT NULL,
  `borrowUser_id` bigint(20) NOT NULL,
  `bidRequestTitle` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for paymentscheduledetail
-- ----------------------------
DROP TABLE IF EXISTS `paymentscheduledetail`;
CREATE TABLE `paymentscheduledetail` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `bidAmount` decimal(18,4) NOT NULL,
  `bidId` bigint(20) NOT NULL,
  `totalAmount` decimal(18,4) NOT NULL,
  `principal` decimal(18,4) NOT NULL,
  `interest` decimal(18,4) NOT NULL,
  `monthIndex` tinyint(4) NOT NULL,
  `deadline` datetime NOT NULL,
  `bidrequest_id` bigint(20) NOT NULL,
  `paydate` datetime DEFAULT NULL,
  `returntype` tinyint(4) NOT NULL,
  `paymentscheduleId` bigint(20) NOT NULL,
  `fromLogininfo_id` bigint(20) DEFAULT NULL,
  `toLogininfoId` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for platformbank
-- ----------------------------
DROP TABLE IF EXISTS `platformbank`;
CREATE TABLE `platformbank` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `bankName` varchar(255) DEFAULT NULL,
  `accountName` varchar(255) DEFAULT NULL,
  `bankNumber` varchar(255) DEFAULT NULL,
  `forkName` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for realauth
-- ----------------------------
DROP TABLE IF EXISTS `realauth`;
CREATE TABLE `realauth` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `realName` blob,
  `sex` tinyint(4) NOT NULL,
  `bornDate` varchar(50) DEFAULT NULL,
  `idNumber` blob,
  `address` varchar(255) NOT NULL,
  `state` tinyint(4) NOT NULL,
  `image1` varchar(255) NOT NULL,
  `image2` varchar(255) NOT NULL,
  `remark` varchar(255) DEFAULT NULL,
  `auditTime` datetime DEFAULT NULL,
  `applyTime` datetime NOT NULL,
  `auditor_id` bigint(20) DEFAULT NULL,
  `applier_id` bigint(20) NOT NULL,
  `realName1` varchar(50) DEFAULT NULL,
  `idNumber1` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for rechargeoffline
-- ----------------------------
DROP TABLE IF EXISTS `rechargeoffline`;
CREATE TABLE `rechargeoffline` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `state` tinyint(4) NOT NULL,
  `remark` varchar(255) DEFAULT NULL,
  `auditTime` datetime DEFAULT NULL,
  `applyTime` datetime NOT NULL,
  `auditor_id` bigint(20) DEFAULT NULL,
  `applier_id` bigint(20) NOT NULL,
  `tradeCode` varchar(255) NOT NULL,
  `tradeTime` datetime NOT NULL,
  `amount` decimal(18,4) NOT NULL,
  `note` varchar(255) DEFAULT NULL,
  `bankinfo_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for systemaccount
-- ----------------------------
DROP TABLE IF EXISTS `systemaccount`;
CREATE TABLE `systemaccount` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `version` int(11) NOT NULL,
  `totalbalance` decimal(18,4) NOT NULL,
  `freezedamount` decimal(18,4) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for systemaccountflow
-- ----------------------------
DROP TABLE IF EXISTS `systemaccountflow`;
CREATE TABLE `systemaccountflow` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `createdDate` datetime NOT NULL,
  `accountactiontype` tinyint(4) NOT NULL,
  `amount` decimal(18,4) NOT NULL,
  `note` varchar(255) DEFAULT NULL,
  `balance` decimal(18,4) NOT NULL,
  `freezedAmount` decimal(18,4) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for systemdictionary
-- ----------------------------
DROP TABLE IF EXISTS `systemdictionary`;
CREATE TABLE `systemdictionary` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `sn` varchar(50) NOT NULL,
  `title` varchar(50) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for systemdictionaryitem
-- ----------------------------
DROP TABLE IF EXISTS `systemdictionaryitem`;
CREATE TABLE `systemdictionaryitem` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `parentId` bigint(20) NOT NULL,
  `title` varchar(50) NOT NULL,
  `sequence` tinyint(4) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for userbankinfo
-- ----------------------------
DROP TABLE IF EXISTS `userbankinfo`;
CREATE TABLE `userbankinfo` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `bankforkname` varchar(255) NOT NULL,
  `bankname` varchar(255) NOT NULL,
  `accountnumber` varchar(255) NOT NULL,
  `loginInfo_id` bigint(20) NOT NULL,
  `accountname` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for userfile
-- ----------------------------
DROP TABLE IF EXISTS `userfile`;
CREATE TABLE `userfile` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `state` tinyint(4) NOT NULL,
  `remark` varchar(255) DEFAULT NULL,
  `auditTime` datetime DEFAULT NULL,
  `applyTime` datetime NOT NULL,
  `auditor_id` bigint(20) DEFAULT NULL,
  `applier_id` bigint(20) NOT NULL,
  `score` tinyint(4) NOT NULL,
  `image` varchar(255) NOT NULL,
  `filetype_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=131 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for userinfo
-- ----------------------------
DROP TABLE IF EXISTS `userinfo`;
CREATE TABLE `userinfo` (
  `id` bigint(20) NOT NULL,
  `version` int(11) DEFAULT NULL,
  `bitState` bigint(20) DEFAULT NULL,
  `realName` varchar(30) DEFAULT NULL,
  `idNumber` varchar(30) DEFAULT NULL,
  `phoneNumber` varchar(30) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `incomeGrade_id` bigint(20) DEFAULT NULL,
  `marriage_id` bigint(20) DEFAULT NULL,
  `kidCount_id` bigint(20) DEFAULT NULL,
  `educationBackground_id` bigint(20) DEFAULT NULL,
  `houseCondition_id` bigint(20) DEFAULT NULL,
  `score` int(11) DEFAULT NULL,
  `anonymousRealName` varchar(255) DEFAULT NULL,
  `anonymousIdNumber` varchar(255) DEFAULT NULL,
  `realAuthId` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for videoauth
-- ----------------------------
DROP TABLE IF EXISTS `videoauth`;
CREATE TABLE `videoauth` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `state` tinyint(4) NOT NULL,
  `remark` varchar(255) DEFAULT NULL,
  `auditTime` datetime DEFAULT NULL,
  `applyTime` datetime NOT NULL,
  `auditor_id` bigint(20) DEFAULT NULL,
  `applier_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;
