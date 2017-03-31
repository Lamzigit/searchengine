package controller;

import cralwer.Crawler;
import extractor.LinkExtractor;
import extractor.TextExtractor;
import frontier.TodoFrontier;
import frontier.VistedFrontier;
import producer.UrlProducer;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by 林志杰 on 2017/3/26.
 */
public class CrawlerController {
    public int threadnum = 0;

    public String getPath() {
        return path;
    }

    private VistedFrontier vistedFrontier = new VistedFrontier("web/resource/BerkeleyDB","visitedDB");

    private TodoFrontier todoFrontier = new TodoFrontier(this);

    public LinkExtractor getLinkExtractor() {
        return linkExtractor;
    }

    public TextExtractor getTextExtractor() {
        return textExtractor;
    }

    public VistedFrontier getVistedFrontier() {
        return vistedFrontier;
    }

    public TodoFrontier getTodoFrontier() {
        return todoFrontier;
    }

    //文件存储路径
    private String path = "";
    //列表页生成器
    //private UrlProducer urlProducer = null;
    //详情页链接提取器
    private LinkExtractor linkExtractor = null;
    //详情页文本提取器
    private TextExtractor textExtractor = null;
    //初始化标志
    private boolean isInit = false;

    public void initiat(String path, UrlProducer urlProducer,LinkExtractor linkExtractor, TextExtractor textExtractor){
        this.path = path;
        //this.urlProducer = urlP;
        this.linkExtractor = linkExtractor;
        this.textExtractor = textExtractor;
        //初始化时将入口链接加入容器
        urlProducer.produce(todoFrontier);
        isInit = true;
    }


    //开始抓取
    public void crawler(){
        System.out.println("================================开始抓取================================");
        ExecutorService executorService = Executors.newFixedThreadPool(200);
        while(isInit){
            if(!todoFrontier.isEmpty()){
                executorService.execute(new Crawler(this));
            }
        }
    }
}
