package writer;

import controller.CrawlerController;
import entity.Curl;
import util.StringUtils;

import java.io.*;

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
        //以公司域名作为分类文件夹
        String path = curl.getLink().substring(curl.getLink().indexOf("http://")+7,curl.getLink().indexOf(".com")+4);
        path = crawlerController.getPath() + "\\" + path;
        //获取页面标题作为文件名
        String title = curl.getPage().substring(curl.getPage().indexOf("title=")+6,curl.getPage().indexOf("company"));
        String completepath = path + "\\" + StringUtils.formatTitle(title)+".txt";
        File file = new File(completepath);
        System.out.println("正在写入："+completepath);
        //System.out.println("已写入文件数："+haswritefile);
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(file);
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream);
            outputStreamWriter.write(curl.getPage());
            outputStreamWriter.close();
        } catch (FileNotFoundException e) {
            System.out.println("in loader writeFile   title:" + title);
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
