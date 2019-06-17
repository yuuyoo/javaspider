CREATE SCHEMA `db_book` DEFAULT CHARACTER SET utf8 ;
use db_book;
CREATE TABLE `db_book`.`tb_book` (
  `id` INT NOT NULL AUTO_INCREMENT COMMENT '图书序号，主键自增',
  `detailUrl` VARCHAR(200) NULL COMMENT '图书连接地址',
  `img` VARCHAR(200) NULL COMMENT '图书图片',
  `bookname` VARCHAR(200) NULL COMMENT '书名',
  `point` VARCHAR(20) NULL COMMENT '评分',
  `commentCount` VARCHAR(10) NULL COMMENT '评论数',
  `author` VARCHAR(255) NULL COMMENT '作者',
  `publisher` VARCHAR(50) NULL COMMENT '出版社',
  `price` VARCHAR(20) NULL COMMENT '图书价格',
  `summary` VARCHAR(255) NULL COMMENT '图书简介',
  PRIMARY KEY (`id`))
COMMENT = '图书表';