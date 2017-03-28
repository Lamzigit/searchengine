package extractor;

import controller.CrawlerController;
import entity.Curl;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


/**
 * Created by 林志杰 on 2017/3/27.
 */
public class ZhilianLinkExtractor extends LinkExtractor {
    public ZhilianLinkExtractor(CrawlerController crawlerController) {
        super(crawlerController);
    }

    @Override
    public void extract(Curl curl) {
        Document document = Jsoup.parse(curl.getPage());
        Elements desclinknodes = document.select("a[style='font-weight: bold']");
        for (Element desclinknode : desclinknodes){
            String desclink =  desclinknode.attr("href");
            Curl desccurl = new Curl();
            desccurl.setLink(desclink);
            desccurl.setType(1);
            crawlerController.getTodoFrontier().addUrl(desccurl);
        }
    }
}
