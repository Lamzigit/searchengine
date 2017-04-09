package extractor;

import entity.Curl;
import loader.PageLoader;
import org.jsoup.Jsoup;
import util.StringUtils;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by 林志杰 on 2017/4/9.
 */
public class YiziTextExtractor extends TextExtractor {

    private static Map<String,String> citys = new HashMap<>();

    static {
        try {
            File yizicitys = new File("F:\\Java\\crawler01\\yizicitys.txt");
            InputStream inputStream = new FileInputStream(yizicitys);
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String line = "";
            while ((line=bufferedReader.readLine())!=null){
                String[] city = line.split(":");
                if(city.length==2){
                    citys.put(city[0],city[1]);
                }
                else
                    continue;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected String getPageNum(){
        return htmldoc.select("img[id='noAttentionImg']").get(0).attr("onclick").replace("javascript:searchJob.addAttention('","").replace("',this);","");
    }

    private String jobdetailjson = "";

    protected void setJobdetailJson(String PageNum){
        String jsonpath = "http://www.yizijob.com/www/personnel/jobchance/queryJobDetail.do?postId="+PageNum+"&_=1489039276048";
        Curl curl = new Curl();
        curl.setLink(jsonpath);
        curl.setType(1);
        new PageLoader().load(curl);
        this.jobdetailjson = curl.getPage().substring(curl.getPage().indexOf("\"post\":"),curl.getPage().indexOf("\"company\":"));
    }
    @Override
    public void extract(Curl curl){
        link = curl.getLink();
        htmldoc = Jsoup.parse(curl.getPage());
        setJobdetailJson(getPageNum());
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

    @Override
    protected String getSource() {
        return "椅子网";
    }

    @Override
    protected String getTitle() {
        String title = htmldoc.select("title").get(0).text();
        title = StringUtils.formatTitle(title);
        return title;
    }

    @Override
    protected String getCompany() {
        String company = htmldoc.select("p[style='margin-top:12px;'] a").text();
        return company;
    }

    @Override
    protected String getSalary() {
        return htmldoc.select("span[class='salary']").get(0).text();
    }

    @Override
    protected String getCity() {
        int i = jobdetailjson.indexOf("\"postCity\":\"")+12;
        String citycode = jobdetailjson.substring(i,i+3);
        String city = citys.get(citycode);
        return city;
    }

    @Override
    protected String getExperience() {
        int i = jobdetailjson.indexOf("\"reqJobExp\":\"")+13;
        String expcode = jobdetailjson.substring(i,i+2);
        switch (expcode){
            case "-1":return "不限";
            case "00":return "在校生";
            case "01":return "应届生";
            case "02":return "1-3年";
            case "03":return "3-5年";
            case "04":return "5-10年";
            case "05":return "10年以上";
            default:return "不限";
        }
    }

    @Override
    protected String getAttribute() {
        int i = jobdetailjson.indexOf("\"postWorktype\":\"")+16;
        String attrcode = jobdetailjson.substring(i,i+2);
        switch (attrcode){
            case "01":return "全职";
            case "02":return "兼职";
            case "03":return "实习";
            default:return "不限";
        }
    }

    @Override
    protected String getEducation() {
        int i = jobdetailjson.indexOf("\"reqAcademic\":\"")+15;
        String educode = jobdetailjson.substring(i,i+2);
        switch (educode){
            case "11":return "不限";
            case "05":return "大专";
            case "06":return "本科";
            case "07":return "硕士";
            case "08":return "博士";
            case "00":return "其他";
            default:return "不限";
        }
    }

    @Override
    protected String getPublishTime() {
        return htmldoc.select("p[class='text_top']:contains(发布时间)").text().replace("发布时间：","");
    }

    @Override
    protected String getDescription() {
        return htmldoc.select("div[class='enterprise enterpriseB'] dl[class='duty break-word']").text();
    }

    @Override
    protected String getAddress() {
        int start = jobdetailjson.indexOf("\"postWorkplace\":\"")+17;
        int end = jobdetailjson.indexOf("\",\"createTime\"");
        return jobdetailjson.substring(start,end);
    }
}
