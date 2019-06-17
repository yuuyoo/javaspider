package com.djl.spider;

import com.djl.spider.pipeline.BookPipeline;
import com.djl.spider.processor.DoubanPageProcessor;
import us.codecraft.webmagic.Spider;

/**
 * @author DJL
 * @create 2019-06-17 19:58
 * @desc 爬虫启动类
 **/
public class DoubanMain {
    public static void main(String[] args) {
        new Spider(new DoubanPageProcessor())
                // 豆瓣Java图书分类 java更换为其他tag标签
                .addUrl("https://book.douban.com/tag/java")
                // 指定结果处理类
                .addPipeline(new BookPipeline())
                //开启5个线程抓取
                .thread(2)
                //启动爬虫
                .run();
    }
}
