package producer;

import entity.Curl;
import frontier.TodoFrontier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;

/**
 * Created by 林志杰 on 2017/4/9.
 */
public class YiziProducer extends UrlProducer {
    private static Logger logger = LogManager.getLogger(YiziProducer.class);
    @Override
    public void produce(TodoFrontier todoFrontier) {
        logger.info("====开始生产椅子网列表链接");
        String part1 = "http://www.yizijob.com/zhaopin/gongzuo/list.html?compsize=-999&natureWork=-999&sort=new&rowStart=";
        String page_no = "";
        String part2 = "&workExperience=-999&searchText=";
        String indus = "";
        String part3 = "&salary=-999&industry=-999&education=-999&city=-999";
        File file = new File("F:\\Java\\crawler01\\yiziindus.txt");
        try {
            InputStream inputStream = new FileInputStream(file);
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            while ((indus=bufferedReader.readLine())!= null){
                for(int i=1;i<=3;i++){
                    page_no = String.valueOf((i-1)*15);
                    String link = part1+page_no+part2+indus+part3;
                    Curl curl = new Curl();
                    curl.setType(0);
                    curl.setLink(link);
                    todoFrontier.addUrl(curl);
                }
            }
            logger.info("====椅子网列表链接生成完毕");
        } catch (FileNotFoundException e) {
            logger.error("找不到文件，错误信息：["+e+"]");
            e.printStackTrace();
        } catch (IOException e) {
            logger.error("IO出错，错误信息：["+e+"]");
            e.printStackTrace();
        }
    }
}
