package service;

import dao.EmployDaoImp;
import entity.Employ;
import util.EmployHelper;

import java.io.File;
import java.util.Date;

/**
 * Created by 林志杰 on 2017/3/28.
 */
public class EmployService {
    private EmployDaoImp emdao = new EmployDaoImp();

    public Employ save(File file){
        Employ employ = EmployHelper.getemploy(file);
        if(emdao.save(employ)>0){
            return employ;
        }else{
            return null;
        }
//        File txtdir = new File(txtdirpath);
//        //遍历TXT文件夹下的文件夹
//        if(txtdir.exists()&&txtdir.isDirectory()){
//            for(File comdir:txtdir.listFiles()){
//                //遍历各个公司的文件夹下的TXT文本
//                if(comdir.exists()&&comdir.isDirectory()){
//                    for(File txtfile : comdir.listFiles()){
//
//                    }
//                }
//            }
//        }
//        else {
//            return -1;
//        }
    }

    public void deleteExpire(){
        Date date = new Date();
    }

}
