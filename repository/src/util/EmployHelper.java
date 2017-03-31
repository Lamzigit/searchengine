package util;

import entity.Employ;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.lucene.document.Document;

import java.io.*;
import java.lang.reflect.InvocationTargetException;

/**
 * Created by 林志杰 on 2017/3/28.
 */
public class EmployHelper {

    /**
     *
     * @param f
     * @return
     * 传入一个解析详情页后得到的TXT文件，
     * 每一行对应employ对象的一个属性，
     * 代码逐行读取并装配employ对象最后返回该对象
     */
    public static Employ getemploy(File f){
        Employ employ = new Employ();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(f));
            String line = reader.readLine();
            while(line != null){
                String[] data = null;
                data = line.split("=");
                if(data.length==2){
                    BeanUtils.setProperty(employ,data[0],data[1]);
                    line = reader.readLine();
                }
                else{
                    return null;
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return employ;
    }

    public static Employ toEmploy(Document document){
        Employ employ = null;
        try {
            employ = Employ.class.newInstance();
            java.lang.reflect.Field[] fields = Employ.class.getDeclaredFields();
            for(java.lang.reflect.Field field : fields){
                field.setAccessible(true);
                String fieldname = field.getName();
                String fielvalue = document.get(fieldname);
                BeanUtils.setProperty(employ,fieldname,fielvalue);
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        return employ;
    }
}
