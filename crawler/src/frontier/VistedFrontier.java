package frontier;

import com.sleepycat.je.*;
import entity.Curl;
import util.StringUtils;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.concurrent.TimeUnit;

/**
 * Created by 林志杰 on 2017/3/27.
 */
public class VistedFrontier {

    private String filename = "";
    private String dbname = "";
    private Environment visitedDbEnv = null;
    private DatabaseConfig dbConfig = null;
    private Database visitedDatabase = null;

    public VistedFrontier(String filename, String dbname) {
        this.filename = filename;
        this.dbname = dbname;
        //实例一个环境配置器，用于配置环境
        EnvironmentConfig environmentConfig = new EnvironmentConfig();
        environmentConfig.setAllowCreate(true);
        environmentConfig.setTransactional(true);
        environmentConfig.setReadOnly(false);
        environmentConfig.setTxnTimeout(10000, TimeUnit.MILLISECONDS);
        environmentConfig.setLockTimeout(10000, TimeUnit.MILLISECONDS);
        //数据库文件
        File file = new File(filename);
        if(!file.exists()){
            file.mkdirs();
        }
        //实例一个数据库环境
        visitedDbEnv = new Environment(file,environmentConfig);
        //实例一个数据库配置对象
        dbConfig = new DatabaseConfig();
        dbConfig.setAllowCreate(true);
        dbConfig.setTransactional(true);
        dbConfig.setReadOnly(false);
        //打开一个数据库
        if(visitedDatabase == null){
            visitedDatabase = visitedDbEnv.openDatabase(null,dbname,dbConfig);
        }
        System.out.println("==============visitedDatabase数据库已打开==============");
    }

    public void addUrl(Curl curl){
        try{
            DatabaseEntry ckey = new DatabaseEntry(curl.getLink().getBytes("UTF-8"));
            DatabaseEntry value = new DatabaseEntry(curl.getLink().getBytes("UTF-8"));
            OperationStatus res = null;
            Transaction txn = null;
            TransactionConfig txConfig = new TransactionConfig();
            txConfig.setSerializableIsolation(true);
            txn = visitedDbEnv.beginTransaction(null,txConfig);
            res = visitedDatabase.put(txn,ckey,value);
            if(res == OperationStatus.KEYEXIST){
                System.out.println("向数据库" + dbname +"中写入:"+ckey+","+value+"失败,该值已经存在");
            }
            System.out.println("visited已添加链接："+curl.getLink());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public boolean accept(Curl curl){
        boolean flag = false;
        try{
            DatabaseEntry ckey = new DatabaseEntry(curl.getLink().getBytes("UTF-8"));
            DatabaseEntry data = new DatabaseEntry();
            Transaction txn = null;
            TransactionConfig txConfig = new TransactionConfig();
            txConfig.setSerializableIsolation(true);
            txn = visitedDbEnv.beginTransaction(null,txConfig);
            OperationStatus res = visitedDatabase.get(txn,ckey,data,LockMode.DEFAULT);
            if(res == OperationStatus.SUCCESS){
                txn.commit();
            }else if(res == OperationStatus.NOTFOUND){
                //DatabaseEntry value = new DatabaseEntry(curl.getLink().getBytes("UTF-8"));
                //visitedDatabase.put(txn,ckey,value);
                txn.commit();
                flag = true;
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return flag;
    }

    public void close(){
        if(visitedDatabase != null){
            visitedDatabase.close();
        }
        if(visitedDbEnv !=  null){
            visitedDbEnv.cleanLog();
            visitedDbEnv.close();
        }
        System.out.println("==============visitedDatabase数据库已关闭==============");
    }

}
