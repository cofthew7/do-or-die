-- phpMyAdmin SQL Dump
-- version 3.4.9
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Apr 12, 2012 at 11:18 PM
-- Server version: 5.1.62
-- PHP Version: 5.3.6-13ubuntu3.6

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `do-or-die`
--

-- --------------------------------------------------------

--
-- Table structure for table `Comment`
--

CREATE TABLE IF NOT EXISTS `Comment` (
  `id` int(11) NOT NULL,
  `todoId` int(11) NOT NULL,
  `uid` int(11) NOT NULL,
  `content` varchar(100) CHARACTER SET latin1 NOT NULL,
  `createDate` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_Comment_uid` (`uid`),
  KEY `fk_Comment_todoId` (`todoId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `Monitoring`
--

CREATE TABLE IF NOT EXISTS `Monitoring` (
  `id` int(11) NOT NULL,
  `todoId` int(11) NOT NULL,
  `uid` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_Monitoring_todoId` (`todoId`),
  KEY `fk_Monitoring_uid` (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `Relation`
--

CREATE TABLE IF NOT EXISTS `Relation` (
  `myId` int(11) NOT NULL,
  `friendId` int(11) NOT NULL,
  PRIMARY KEY (`myId`,`friendId`),
  KEY `fk_Relation_user` (`myId`),
  KEY `fk_Relation_user2` (`friendId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `Todo`
--

CREATE TABLE IF NOT EXISTS `Todo` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) CHARACTER SET latin1 NOT NULL,
  `deadline` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `isMonitored` tinyint(4) NOT NULL DEFAULT '0',
  `createDate` timestamp NULL DEFAULT NULL,
  `uid` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_Todo_uid` (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `User`
--

CREATE TABLE IF NOT EXISTS `User` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) CHARACTER SET latin1 NOT NULL,
  `password` varchar(20) CHARACTER SET latin1 NOT NULL,
  `avatar` varchar(100) CHARACTER SET latin1 DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `Comment`
--
ALTER TABLE `Comment`
  ADD CONSTRAINT `fk_Comment_todoId` FOREIGN KEY (`todoId`) REFERENCES `Todo` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_Comment_uid` FOREIGN KEY (`uid`) REFERENCES `User` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `Monitoring`
--
ALTER TABLE `Monitoring`
  ADD CONSTRAINT `fk_Monitoring_todoId` FOREIGN KEY (`todoId`) REFERENCES `Todo` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_Monitoring_uid` FOREIGN KEY (`uid`) REFERENCES `User` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `Relation`
--
ALTER TABLE `Relation`
  ADD CONSTRAINT `fk_Relation_user` FOREIGN KEY (`myId`) REFERENCES `User` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_Relation_user2` FOREIGN KEY (`friendId`) REFERENCES `User` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `Todo`
--
ALTER TABLE `Todo`
  ADD CONSTRAINT `fk_Todo_uid` FOREIGN KEY (`uid`) REFERENCES `User` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
