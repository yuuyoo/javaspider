package com.djl.spider.processor;

import com.djl.spider.entity.Book;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

import java.util.ArrayList;
import java.util.List;

/**
 * @author DJL
 * @create 2019-06-17 15:59
 * @desc 豆瓣Java类图书爬虫页面处理类
 **/
public class DoubanPageProcessor implements PageProcessor {

    private Site site = Site.me().setRetryTimes(3).setSleepTime(100).setTimeOut(3000);

    @Override
    public void process(Page page) {
        // 详情url
        List<String> detailUrls =  page.getHtml().xpath("div[@id='subject_list']/ul/li/div[@class='pic']/a/@href").all();
        // 封面图片
        List<String> imgs = page.getHtml().xpath("div[@id='subject_list']/ul/li/div[@class='pic']/a/img/@src").all();
        // 书名
        List<String> bookNames = page.getHtml().xpath("div[@id='subject_list']/ul/li/div[@class='info']/h2/a/text()").all();
        // 评分
        List<String> points = page.getHtml().xpath("div[@id='subject_list']/ul/li/div[@class='info']/div[@class='star clearfix']/span[@class='rating_nums']/text()").all();
        // 评论
        List<String> commentCount = page.getHtml().xpath("div[@id='subject_list']/ul/li/div[@class='info']/div[@class='star clearfix']/span[@class='pl']/text()").all();
        // 评论
        List<String> summarys = page.getHtml().xpath("div[@id='subject_list']/ul/li/div[@class='info']/p/text()").all();
        // 作者 出版社 出版时间 价格 字符串
        List<String> pubInfos = page.getHtml().xpath("div[@id='subject_list']/ul/li/div[@class='info']/div[@class='pub']/text()").all();

        // 数据合并处理
        List<Book> books = new ArrayList<>();
        for (int i = 0; i < detailUrls.size(); i++) {
            Book book = new Book();
            book.setDetailUrl(detailUrls.get(i));
            book.setImg(imgs.get(i));
            book.setBookName(bookNames.get(i).trim());
            if (points.size() == detailUrls.size()) {
                book.setPoint(points.get(i));
            }

            String countStr = commentCount.get(i);
            book.setCommentCount(countStr.substring(countStr.indexOf("(") + 1, countStr.indexOf("人")));
            if (summarys.size() == detailUrls.size()) {
                book.setSummary(summarys.get(i).trim());
            }

            String pubInfo = pubInfos.get(i);
            String[] arr = pubInfo.split("/");
            if (arr.length > 1) {
                book.setPrice(arr[arr.length - 1].trim());
                book.setPubDate(arr[arr.length - 2].trim());
                book.setPublisher(arr[arr.length - 3].trim());

                // 作者合并
                String author = "";
                for (int j = 0; j < arr.length - 3; j++){
                    author = author + arr[j] + "/";
                }
                book.setAuthor(author.trim());
            } else {
                book.setAuthor(pubInfo);
            }


            books.add(book);
        }

        // 分页处理
        String firstUrl = "https://book.douban.com/tag/java";
        if (page.getUrl().get().equals(firstUrl)) {
            // 当前请求的是第一页
            // 获取总分页数
            List<String> allPage = page.getHtml().xpath("div[@class='paginator']/a/text()").all();
            Integer lastPage = Integer.parseInt(allPage.get(allPage.size() - 1));
            // 拼接所有分页连接
            List<String> targetUtl = new ArrayList<>();
            for (int i = 2; i <= lastPage + 1; i++) {
                String pageUrl = firstUrl + "?start=" + ((i-1) * 20);
                System.out.println(pageUrl);
                targetUtl.add(pageUrl);
            }

            // 设置要爬取的链接
            page.addTargetRequests(targetUtl);
        }

        // 传递
        page.putField("data", books);

    }

    @Override
    public Site getSite() {
        return site;
    }

}
