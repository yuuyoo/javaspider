package com.djl.spider.dao;

import com.djl.spider.entity.Book;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author DJL
 * @create 2019-06-17 21:29
 * @desc 数据访问dao接口
 **/
public interface BookDao {

    void addBooks(@Param("books") List<Book> books);
}
