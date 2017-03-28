package producer;

import entity.Curl;
import frontier.TodoFrontier;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 林志杰 on 2017/3/27.
 */
public class ZhilianProducer extends UrlProducer {

    @Override
    public void produce(TodoFrontier todoFrontier) {
        String part1 = "http://sou.zhaopin.com/jobs/searchresult.ashx?bj=160000&jl=";
        String part2 = "&p=";
        File citys = new File("crawler/src/sourcetxt/citys.txt");
        List<String> links = new ArrayList<>();
        try {
            //读取文件中的城市名，将其拼接到URL中
            FileInputStream fileReader = new FileInputStream(citys);
            InputStreamReader inputStreamReader = new InputStreamReader(fileReader,"utf-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String line = bufferedReader.readLine();
            while (line!=null){
                line = java.net.URLEncoder.encode(line,"UTF-8");
                for(int page_no=0;page_no<2;page_no++){
                    String link = part1 + line + part2 + page_no;
                    System.out.println("++++++"+link);
                    //生成链接对象
                    Curl curl = new Curl();
                    curl.setLink(link);
                    curl.setType(0);
                    //加入数组
                    todoFrontier.addUrl(curl);
                }
                line = bufferedReader.readLine();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("文件输入流出错");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            System.out.println("输入流读取器出错");
        } catch (IOException e) {
            System.out.println("读取失败");
            e.printStackTrace();
        }
        System.out.println("链接已生成\n");
    }
}
