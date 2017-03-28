package extractor;

import entity.Curl;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 * Created by 林志杰 on 2017/3/26.
 */
public abstract class TextExtractor {
    protected String link = "";

    protected Document htmldoc = null;

    protected abstract String getSource();

    protected String getLink(){
        return link;
    };

    protected abstract String getTitle();

    protected abstract String getCompany();

    protected abstract String getSalary();

    protected abstract String getCity();

    protected abstract String getExperience();

    protected abstract String getAttribute();

    protected abstract String getEducation();

    protected abstract String getPublishTime();

    protected abstract String getDescription();

    protected abstract String getAddress();

    public void extract(Curl curl){
        link = curl.getLink();
        htmldoc = Jsoup.parse(curl.getPage());
        String text =
                "source=" + getSource() + "\n"+
                        "link="+ getLink() +"\n"+
                        "title=" +getTitle() +"\n"+
                        "company=" +getCompany() +"\n"+
                        "publishtime=" +getPublishTime() +"\n"+
                        "address=" +getAddress()  +"\n"+
                        "salary=" +getSalary()  +"\n"+
                        "city=" +getCity()  +"\n"+
                        "experience=" +getExperience() +"\n"+
                        "education=" +getEducation() +"\n"+
                        "attribute=" +getAttribute() +"\n"+
                        "description=" +getDescription();
        curl.setPage(text);
    }
}
