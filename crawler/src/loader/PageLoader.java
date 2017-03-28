package loader;

import entity.Curl;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import util.ResponseUtils;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by 林志杰 on 2017/3/26.
 */
public class PageLoader{
//    Curl curl;
//    CrawlerController crawlerController;

//    public PageLoader(Curl curl, CrawlerController crawlerController) {
//        this.curl = curl;
//        this.crawlerController = crawlerController;
//    }

    public void load(Curl curl){
        String html = "";
        //httpclient配置
        RequestConfig config = RequestConfig.custom()
                .setCookieSpec(CookieSpecs.STANDARD)
                .setConnectionRequestTimeout(6000)
                .setConnectTimeout(6000)
                .build();
        CloseableHttpClient httpClient = HttpClients.custom()
                .setDefaultRequestConfig(config)
                .build();
        //请求配置
        HttpGet httpGet = new HttpGet(curl.getLink());
        System.out.println(curl.getLink());
        httpGet.addHeader("User-Agent","Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/42.0.2311.152 Safari/537.36");

        CloseableHttpResponse response = null;
        try{
            Thread.sleep(5000);
            response = httpClient.execute(httpGet);
            InputStream in = response.getEntity().getContent();
            html = ResponseUtils.readResponse(in);

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            if(response != null){
                try {
                    response.getEntity().getContent().close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        //测试
        curl.setPage(html);
        if(curl.getType()==0){
            System.out.println("列表页");
        }else{
            System.out.println("详情页");
        }
    }

//    @Override
//    public void run() {
//        System.out.print("pageloader\n");
//        //下载页面
//        load(curl);
//        //若是列表页链接，就从中提   取详情页链接
//        if(curl.getType() == 0){
//            List<Curl> curlList = crawlerController.getLinkExtractor().extract(curl);
//            for(Curl curl : curlList){
//                new PageLoader(curl,crawlerController);
//            }
//        }
//        //如是详情链接且未曾下载，就提取文本，写入文件
//        if(curl.getType() == 1){
//            crawlerController.getTextExtractor().extract(curl);
//            new Thread(new PageWriter(crawlerController,curl)).start();
//        }
//
//    }
}
