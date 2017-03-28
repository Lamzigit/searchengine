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

    public VistedFrontier(String filename) {
        this.filename = filename;
        try{
            //实例一个环境配置器，用于配置环境
            EnvironmentConfig environmentConfig = new EnvironmentConfig();
            environmentConfig.setAllowCreate(true);
            environmentConfig.setTransactional(true);
            environmentConfig.setReadOnly(false);
            environmentConfig.setTxnTimeout(3000, TimeUnit.MILLISECONDS);

            File file = new File(filename);
            if(!file.exists()){
                file.mkdirs();
            }
            //实例一个环境
            visitedDbEnv = new Environment(file,environmentConfig);
            //实例一个数据库配置对象
            dbConfig = new DatabaseConfig();
            dbConfig.setAllowCreate(true);
            dbConfig.setTransactional(true);
            dbConfig.setReadOnly(false);
            //打开一个数据库
            if(visitedDatabase == null){
                visitedDatabase = visitedDbEnv.openDatabase(null,dbName,dbConfig);
            }
        }
        catch(DatabaseException e){
            e.printStackTrace();
        }

    }

    private String filename = "";
    private String dbName = "";
    private Environment visitedDbEnv = null;
    private DatabaseConfig dbConfig = null;
    private Database visitedDatabase = null;

    public void openVisitedDatabase(){

    }
    public synchronized void addUrl(Curl curl){
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
                System.out.println("向数据库" + dbName +"中写入:"+ckey+","+value+"失败,该值已经存在");
            }
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
                DatabaseEntry value = new DatabaseEntry(curl.getLink().getBytes("UTF-8"));
                visitedDatabase.put(txn,ckey,value);
                txn.commit();
                flag = true;
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return flag;
    }
}
