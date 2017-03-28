package extractor;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import test.ZhilianTest;
import util.StringUtils;

/**
 * Created by 林志杰 on 2017/3/27.
 */
public class ZhilianTextExtractor extends TextExtractor {
    @Override
    protected String getSource() {
        return "智联招聘";
    }

    @Override
    protected String getTitle() {
        String title =htmldoc.select("title").get(0).text();
        title = StringUtils.formatTitle(title);
        return title;
    }

    @Override
    protected String getCompany() {
        return htmldoc.select("h5 a[rel='nofollow']").get(0).text();
    }

    @Override
    protected String getSalary() {
        return htmldoc.select("ul[class='terminal-ul clearfix'] li strong").get(0).text();
    }

    @Override
    protected String getCity() {
        return htmldoc.select("ul[class='terminal-ul clearfix'] li strong").get(1).text();
    }

    @Override
    protected String getExperience() {
        return htmldoc.select("ul[class='terminal-ul clearfix'] li strong").get(4).text();
    }

    @Override
    protected String getAttribute() {
        return htmldoc.select("ul[class='terminal-ul clearfix'] li strong").get(3).text();
    }

    @Override
    protected String getEducation() {
        return htmldoc.select("ul[class='terminal-ul clearfix'] li strong").get(5).text();
    }

    @Override
    protected String getPublishTime() {
        return htmldoc.select("ul[class='terminal-ul clearfix'] li strong").get(2).text();
    }

    @Override
    protected String getDescription() {
        String desc = "";
        Elements elements = htmldoc.select("div[class='terminalpage-main clearfix'] > div[class='tab-cont-box'] > div[class='tab-inner-cont']:eq(0) p");
        for(Element element : elements){
            desc = desc + element.text() + "\n";
        }
        desc = desc.replace("&nbsp","");
        return StringUtils.replaceBlank(desc);
    }

    @Override
    protected String getAddress() {
        String address = StringUtils.replaceBlank(htmldoc.select("div[class='tab-inner-cont'] h2").text());
        address = address.replace("查看职位地图"," ");
        return address;
    }
}
