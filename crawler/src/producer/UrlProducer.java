package producer;

import entity.Curl;
import frontier.TodoFrontier;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 林志杰 on 2017/3/26.
 */
public abstract class  UrlProducer {

    private List<Curl> curls = new ArrayList<>();

    protected List<Curl> getUrls(){
        return curls;
    }

    protected void addCurl(Curl curl){
        curls.add(curl);
    }

    public abstract void produce(TodoFrontier todoFrontier);

}
