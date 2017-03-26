package util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by 林志杰 on 2017/3/26.
 */
public class StringUtils {
    public static String replaceBlank(String str) {
        String dest = "";
        if (str!=null) {
            Pattern p1 = Pattern.compile("\\s*|\t|\r|\n");
            Pattern p2 = Pattern.compile("\\\\");
            Matcher m = p2.matcher(str);
            dest = m.replaceAll("");
            Matcher m2 = p1.matcher(dest);
            dest = m2.replaceAll("");
        }
        return dest;
    }
    public static String formatTitle(String title){
        //格式化标题
        Pattern pattern = Pattern.compile("【|】");
        Matcher matcher = pattern.matcher(title);
        title = matcher.replaceAll("");

        pattern = Pattern.compile("_");
        matcher = pattern.matcher(title);
        title = matcher.replaceAll("-");

        pattern = Pattern.compile("（");
        matcher = pattern.matcher(title);
        title = matcher.replaceAll("(");

        pattern = Pattern.compile("）");
        matcher = pattern.matcher(title);
        title = matcher.replaceAll(")");

        pattern = Pattern.compile("/");
        matcher = pattern.matcher(title);
        title = matcher.replaceAll("");

        title = StringUtils.replaceBlank(title);
        return title;
    }
}
