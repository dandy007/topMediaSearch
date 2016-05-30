CREATE TABLE `youtube`.`YOUTUBE_VIDEO` (
  `videoId` VARCHAR(250) NOT NULL,
  `title` VARCHAR(250) NULL,
  `created` DATETIME NULL,
  `viewCount` DECIMAL(20) NULL,
  `viewCountDay` DECIMAL(20) NULL,
  `author` VARCHAR(45) NULL,
  PRIMARY KEY (`videoId`));
