package com.djl.spider.entity;

import lombok.Data;

/**
 * @author DJL
 * @create 2019-06-17 17:32
 * @desc 图书实体
 **/
@Data
public class Book {

    private String detailUrl;
    private String img;
    private String bookName;
    private String point;
    private String commentCount;
    private String author;
    private String publisher;
    private String pubDate;
    private String price;
    private String summary;
}
