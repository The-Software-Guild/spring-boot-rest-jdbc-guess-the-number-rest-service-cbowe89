-- Use guessGame database
USE guessGame;

-- Add info to game table
INSERT INTO `games`
VALUES (1,'3290',0),
       (2,'9807',1),
       (3,'1290',1),
       (4,'4352',1),
       (5,'9876',1),
       (6,'1254',0),
       (7,'2689',0),
       (8,'5087',0),
       (9,'2383',0),
       (10,'6835',0);

-- Add info to round table
INSERT INTO `rounds`
VALUES (1,1,'2021-08-10 13:20:11','1254','e:4:p:0'),
       (2,2,'2021-04-11 08:47:31','5181','e:0:p:0'),
       (3,3,'2021-05-15 04:36:59','9876','e:4:p:0'),
       (4,4,'2021-01-31 10:03:01','1254','e:4:p:0'),
       (5,5,'2021-10-01 17:55:02','1259','e:1:p:1'),
       (6,6,'2021-08-04 10:20:49','4352','e:4:p:0');

-- Use guessGame_Tests database
USE guessGame_Tests;

-- Add info to game table
INSERT INTO `games`
VALUES (36,'0357',0),
       (37,'1282',0);

-- Add info to round table
INSERT INTO `rounds`
VALUES (9,36,NULL,'1111',NULL),
       (10,37,NULL,'2222',NULL);
