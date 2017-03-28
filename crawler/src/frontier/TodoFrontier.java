package frontier;

import entity.Curl;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 林志杰 on 2017/3/27.
 */
public class TodoFrontier {

    List<Curl> curls = new ArrayList<>();

    public synchronized Curl getCurl(){
        if(isEmpty()){
            return null;
        }else{
            return curls.remove(0);
        }

    }
    public synchronized void addUrl(Curl curl){
        System.out.println("已添加："+curl.getLink());
        curls.add(curl);
    }
    public synchronized boolean isEmpty(){
        return curls.size()==0;
    }
}
