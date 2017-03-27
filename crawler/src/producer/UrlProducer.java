package producer;

import entity.Curl;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 林志杰 on 2017/3/26.
 */
public abstract class  UrlProducer {

    protected List<Curl> curls = new ArrayList<>();

    public List<Curl> getUrls(){
        return curls;
    }

    protected void addCurl(Curl curl){
        curls.add(curl);
    }

    public abstract List<Curl> produce();

}
