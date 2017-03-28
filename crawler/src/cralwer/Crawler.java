package cralwer;

import controller.CrawlerController;
import entity.Curl;
import frontier.TodoFrontier;
import loader.PageLoader;
import writer.PageWriter;

/**
 * Created by 林志杰 on 2017/3/27.
 */
public class Crawler implements Runnable{
    Curl curl = null;
    CrawlerController controller;
    public Crawler(CrawlerController controller) {
        this.controller = controller;
    }

    @Override
    public void run() {
        synchronized (this){
            controller.threadnum++;
            System.out.println("当前线程数为："+controller.threadnum);
        }
        TodoFrontier todoFrontier = controller.getTodoFrontier();
        if(todoFrontier.isEmpty()){
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        curl = todoFrontier.getCurl();
        //curl若不为空则抓取
        if(curl != null){
            System.out.println("开始线程");
            //下载页面
            new PageLoader().load(curl);
            //type是0为列表页，1为详情页
            //若是列表提取详情页链接，加入容器
            if(curl.getType() == 0){
                controller.getLinkExtractor().extract(curl);
            }
            //若是详情页，则提取文本，写入文件
            //else if(curl.getType()==1 && controller.getVistedFrontier().accept(curl)){
            if(curl.getType()==1){
                controller.getTextExtractor().extract(curl);
                new PageWriter(controller,curl).write();
            }
            synchronized (this){
                controller.threadnum--;
            }
        }

    }
}
