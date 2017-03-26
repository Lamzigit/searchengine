package util;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by 林志杰 on 2017/3/26.
 */
public class ResponseUtils {

    public static String readResponse(InputStream in) throws Exception{

        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        StringBuffer stringBuffer = new StringBuffer();
        String line = reader.readLine();
        while (line!= null) {
            stringBuffer.append(line);
            //System.out.println(line);
            line = reader.readLine();
        }
        return stringBuffer.toString();
    }

    public static String extractTitle(String html){
        Document doc = Jsoup.parse(html);
        Elements element =doc.getElementsByTag("title");
        String title = element.get(0).text();
        title = StringUtils.replaceBlank(title);
        title = title.replace("/","-");
        return title;
    }
}
