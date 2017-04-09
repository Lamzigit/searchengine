package extractor;

import controller.CrawlerController;
import entity.Curl;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Created by 林志杰 on 2017/4/9.
 */
public class YiziLinkExtractor extends LinkExtractor{
    public YiziLinkExtractor(CrawlerController crawlerController) {
        super(crawlerController);
    }

    @Override
    public void extract(Curl curl) {
        Document document = Jsoup.parse(curl.getPage());
        Elements desclinknodes = document.select("h5[class='fl'] a");
        for (Element desclinknode : desclinknodes){
            String desclink = "http://www.yizijob.com" + desclinknode.attr("href");
            Curl desccurl = new Curl();
            desccurl.setLink(desclink);
            desccurl.setType(1);
            crawlerController.getTodoFrontier().addUrl(desccurl);
        }
    }
}
