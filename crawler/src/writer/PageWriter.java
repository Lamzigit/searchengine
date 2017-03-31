package writer;

import controller.CrawlerController;
import entity.Curl;
import util.StringUtils;

import java.io.*;
import java.util.Calendar;


/**
 * Created by 林志杰 on 2017/3/26.
 */
public class PageWriter implements Runnable{
    CrawlerController crawlerController;
    Curl curl;

    public PageWriter(CrawlerController crawlerController,Curl curl) {
        this.crawlerController = crawlerController;
        this.curl = curl;
    }

    public void run(){
        //txt文件夹路径
        String path = crawlerController.getPath();
        //用日期标识新爬取的文件夹
        Calendar calendar = Calendar.getInstance();
        //格式化月份
        int mon = calendar.get(Calendar.MONTH)+1;
        String month = mon>9?String.valueOf(mon):"0"+String.valueOf(mon);
        //格式化日号
        int dat = calendar.get(Calendar.DATE);
        String date = dat>9?String.valueOf(dat):"0"+String.valueOf(dat);
        //拼接日期字符串
        String datestr = String.valueOf(calendar.get(Calendar.YEAR))+month+date;
        //以公司域名作为分类文件夹
        String compstr = curl.getLink().substring(curl.getLink().indexOf("http://")+7,curl.getLink().indexOf(".com")+4);
        //最终文件夹路径
        path = path + "/"+ datestr + "/"+compstr;
        File dir = new File(path);
        if(!dir.exists()){
            dir.mkdirs();
        }
        //获取页面标题作为文件名
        String title = curl.getPage().substring(curl.getPage().indexOf("title=")+6,curl.getPage().indexOf("company"));
        String completepath = path + "/" + StringUtils.formatTitle(title)+".txt";
        File file = new File(completepath);
        System.out.println("正在写入："+completepath);
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(file);
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream);
            outputStreamWriter.write(curl.getPage());
            outputStreamWriter.close();
            crawlerController.getVistedFrontier().addUrl(curl);
        } catch (FileNotFoundException e) {
            System.out.println("in loader writeFile   title:" + title);
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
