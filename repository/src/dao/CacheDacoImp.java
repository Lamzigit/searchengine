package dao;

import com.sleepycat.je.DatabaseEntry;
import com.sleepycat.je.LockMode;
import com.sleepycat.je.Transaction;
import entity.Employ;
import util.BytesHelper;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 林志杰 on 2017/4/1.
 */
public class CacheDacoImp {

    public void add(Employ employ){
        CacheDB cacheDB = CacheDB.getInstance();
        Transaction txn = cacheDB.beginTransaction();
        try {
            DatabaseEntry ckey = new DatabaseEntry(String.valueOf(employ.getId()).getBytes());
            DatabaseEntry value = new DatabaseEntry(BytesHelper.objectToBytes(employ));
//            OperationStatus res = null;
//            res = cacheDB.openDatabase().put(txn,ckey,value);
            cacheDB.openDatabase().put(txn,ckey,value);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        txn.commit();
    }

    public void add(List<Employ> employLis){
        CacheDB cacheDB = CacheDB.getInstance();
        Transaction txn = cacheDB.beginTransaction();
        try {
            for(Employ employ : employLis){
                DatabaseEntry ckey = new DatabaseEntry(String.valueOf(employ.getId()).getBytes());
                DatabaseEntry value = new DatabaseEntry(String.valueOf(employ).getBytes("utf-8"));
                cacheDB.openDatabase().put(txn,ckey,value);
            }

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        txn.commit();
    }

    public Employ get (int id){
        CacheDB cacheDB = CacheDB.getInstance();
        Transaction txn = cacheDB.beginTransaction();
        Employ employ = null;

        DatabaseEntry key = new DatabaseEntry(String.valueOf(id).getBytes());
        DatabaseEntry data = new DatabaseEntry();
        cacheDB.openDatabase().get(txn,key,data,LockMode.DEFAULT);

        txn.commit();
        try {
            employ = (Employ) BytesHelper.bytesToObject(data.getData());
        } catch (Exception e) {
            return null;
            //e.printStackTrace();
        }

        return employ;
    }

    public List<Employ> get(int[] ids){
        CacheDB cacheDB = CacheDB.getInstance();
        Transaction txn = cacheDB.beginTransaction();
        List<Employ> employList = new ArrayList<>();
        for(int id : ids){
            DatabaseEntry key = new DatabaseEntry(String.valueOf(id).getBytes());
            DatabaseEntry data = new DatabaseEntry();
            cacheDB.openDatabase().get(txn,key,data,LockMode.DEFAULT);
            try {
                Employ employ = (Employ) BytesHelper.bytesToObject(data.getData());
                employList.add(employ);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        txn.commit();
        return employList;
    }

    public static void main(String[] a) throws Exception {
        CacheDacoImp cacheDacoImp = new CacheDacoImp();
//        Employ employ = new Employ();
//
//        employ.setDescription("hhhhhhhhhhhhhhhhhh");
//        employ.setCompany("llllllllllllllllll");
////        int i = 1;
////        while(i<=500){
////            employ.setId(i);
////            employ.setTitle("test"+i);
////            berkeleyDacoImp.add(employ);
////            System.out.println("已存入："+employ.getTitle());
////            i++;
////        }
//        Employ employ1 = cacheDacoImp.get(3000);
//        if(employ1==null){
//            System.out.println("没有该对象");
//        }else
//            System.out.println(employ1.getTitle());
//
    }
}
