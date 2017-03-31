package util;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 林志杰 on 2017/3/27.
 */
public class FileHelper {


    public static List<File> getFiles(String dirpath){
        List<File> files = new ArrayList<>();
        File dir = new File(dirpath);
        if(dir.exists()&&dir.isDirectory()){
            for (File file : dir.listFiles()){
                files.add(file);
            }
            return files;
        }else {
            return null;
        }
    }

    public static String readFile(String filepath,String charset){
        File file = new File(filepath);
        String content = "";
        if(file.isDirectory()){

        }
        try {
            InputStream inputStream = new FileInputStream(file);
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream,charset);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String line = "";
            while ((line=bufferedReader.readLine())!=null){
                content += line;
            }
        } catch (FileNotFoundException e) {
            System.out.println("找不到该文件");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("读取内容时异常");
            e.printStackTrace();
        }
        return content;
    }

}
