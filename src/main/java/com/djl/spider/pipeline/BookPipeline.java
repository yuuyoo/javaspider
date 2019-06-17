package com.djl.spider.pipeline;

import com.djl.spider.dao.BookDao;
import com.djl.spider.entity.Book;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.io.IOException;
import java.io.Reader;
import java.util.List;

/**
 * @author DJL
 * @create 2019-06-17 19:42
 * @desc 豆瓣图书结果处理类
 **/
public class BookPipeline implements Pipeline {

    private  static SqlSessionFactory factory = null;

    // 初始化sqlSessionFactory对象
    static {
        // 读取主配置文件
        Reader reader = null;
        try {
            reader = Resources.getResourceAsReader("mybatis.xml");
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 获取sqlSessionFactory对象
        factory = new SqlSessionFactoryBuilder().build(reader);
    }


    @Override
    public void process(ResultItems resultItems, Task task) {
        List<Book> books = resultItems.get("data");

        // 保存数据到数据库
        save(books);
    }

    // 数据保存至数据库
    private void save(List<Book> books) {
        // 获取SqlSession对象，进行增删改查的操作
        SqlSession session = factory.openSession();
        // 获取接口的代理对象
        BookDao bookDao = session.getMapper(BookDao.class);
        bookDao.addBooks(books);

        // 默认开始事务，执行增删改查需要提交事务
        session.commit();
        // 关闭session
        session.close();

    }
}
