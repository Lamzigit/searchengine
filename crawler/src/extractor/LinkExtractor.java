package extractor;

import controller.CrawlerController;
import cralwer.Crawler;
import entity.Curl;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 林志杰 on 2017/3/26.
 */
public abstract class LinkExtractor {
    CrawlerController crawlerController;

    public LinkExtractor(CrawlerController crawlerController) {
        this.crawlerController = crawlerController;
    }
    public abstract void extract(Curl curl);
}
