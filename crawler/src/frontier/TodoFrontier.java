package frontier;

import controller.CrawlerController;
import entity.Curl;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 林志杰 on 2017/3/27.
 */
public class TodoFrontier {
    public TodoFrontier(CrawlerController controller) {
        this.controller = controller;
    }

    CrawlerController controller = null;
    List<Curl> curls = new ArrayList<>();

    public synchronized Curl getCurl(){
        if(isEmpty()){
            return null;
        }else{
            return curls.remove(0);
        }

    }
    public synchronized void addUrl(Curl curl){
        if(controller.getVistedFrontier().accept(curl)){
            curls.add(curl);
            System.out.println("Todo已添加链接："+curl.getLink());
        }
        else {
            System.out.println("已抓过链接：" + curl.getLink());
        }
    }
    public synchronized boolean isEmpty(){
        return curls.size()==0;
    }

    public int size(){
        return curls.size();
    }
}
