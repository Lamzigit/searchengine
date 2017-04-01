package dao;

import com.sleepycat.je.*;

import java.io.File;
import java.util.concurrent.TimeUnit;

/**
 * Created by 林志杰 on 2017/4/1.
 */
public class CacheDB {

    private static Database bdbinstance = null;
    private static Environment cacheDbEnv = null;
    private volatile static CacheDB cacheDB = null;
    private CacheDB(){
        String filename = "web/resource/cache";
        String dbname = "cachedb";
        DatabaseConfig dbConfig = null;
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
        cacheDbEnv = new Environment(file,environmentConfig);
        //实例一个数据库配置对象
        dbConfig = new DatabaseConfig();
        dbConfig.setAllowCreate(true);
        dbConfig.setTransactional(true);
        dbConfig.setReadOnly(false);
        //打开一个数据库
        if(bdbinstance == null){
            bdbinstance = cacheDbEnv.openDatabase(null,dbname,dbConfig);
        }
    }

    public synchronized static CacheDB getInstance(){
        if(cacheDB != null){
            return cacheDB;
        }else {
            return new CacheDB();
        }
    }

    public Database openDatabase(){
        return bdbinstance;
    }

    public Transaction beginTransaction(){
        Transaction txn = null;
        TransactionConfig txConfig = new TransactionConfig();
        txConfig.setSerializableIsolation(true);
        txn = cacheDbEnv .beginTransaction(null,txConfig);
        return txn;
    }
}
