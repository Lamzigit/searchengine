package com.zhaopin.component;

import controller.CrawlerController;
import extractor.YiziLinkExtractor;
import extractor.YiziTextExtractor;
import extractor.ZhilianLinkExtractor;
import extractor.ZhilianTextExtractor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import producer.YiziProducer;
import producer.ZhilianProducer;

/**
 * Created by 林志杰 on 2017/3/29.
 */
@Component
public class AutoCrawler {
    private static Logger logger = LogManager.getLogger(AutoCrawler.class);
    @Scheduled(cron="0 0 1 * * ?")

    public void Zhiliancralwer(){
        logger.info("开始抓取智联招聘");
        CrawlerController controller = new CrawlerController();
        ZhilianProducer zhilianProducer = new ZhilianProducer();
        ZhilianLinkExtractor zhilianLinkExtractor = new ZhilianLinkExtractor(controller);
        ZhilianTextExtractor zhilianTextExtractor  = new ZhilianTextExtractor();
        String path = "../webapps/searchengine/txt";
        controller.initiat(path,zhilianProducer,zhilianLinkExtractor,zhilianTextExtractor);
        controller.crawler();
        logger.info("智联招聘抓取完毕");
    }

    @Scheduled(cron="0 0 2 * * ?")
    public void Yizicralwer(){
        logger.info("开始抓取椅子网");
        CrawlerController controller = new CrawlerController();
        YiziProducer yiziProducer = new YiziProducer();
        YiziLinkExtractor yiziLinkExtractor = new YiziLinkExtractor(controller);
        YiziTextExtractor yiziTextExtractor = new YiziTextExtractor();
        String path = "../webapps/searchengine/txt";
        controller.initiat(path,yiziProducer,yiziLinkExtractor,yiziTextExtractor);
        controller.crawler();
        logger.info("椅子网抓取完毕");
    }

}
