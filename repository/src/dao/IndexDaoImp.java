package dao;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import util.DocumentHelper;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;

/**
 * Created by 林志杰 on 2017/3/31.
 */
public class IndexDaoImp {
    private String indexpath = "";
    private String sourcepath = "";
    private Analyzer analyzer = null;

    public void initiate(String sourcepath,String indexpath,Analyzer analyzer){
        this.indexpath = indexpath;
        this.analyzer = analyzer;
        this.sourcepath = sourcepath;
    }

    public void create(){
        if (analyzer == null||indexpath.equals("")){
            System.out.println("请初始化 analyzer 或 indexpath");
        }else {
            try {
                //获取IndexWriter
                IndexWriter indexWriter = getIndexWriter();
                //被索引的txt文件所在的目录
                sourcepath = sourcepath + "/" + getDateString();
                File datedir = new File(sourcepath);
                File[] companydirs = null;
                //遍历每个网站对应的目录
                if(datedir.exists()&&datedir.isDirectory()&&(companydirs = datedir.listFiles())!=null){
                    for(File companydir : companydirs){
                        File[] txtfiles = null;
                        System.out.println("================================================正在索引"+companydir.getName()+"目录下的文件================================================");
                        //遍历每个网站对应的目录下的文件
                        if(companydir.isDirectory()&&(txtfiles=companydir.listFiles())!=null){
                            for(File txtfile : txtfiles){
                                System.out.println("正在索引======"+txtfile.getName());
                                //txt转化为document对象，其中包含数据库保存逻辑
                                Document document = DocumentHelper.toDocument(txtfile);
                                if(document != null){
                                    indexWriter.addDocument(document);
                                }
                            }
                        }
                        System.out.println("================================================目录"+companydir.getName()+"遍历完成"+"================================================");
                    }
                }
                System.out.println("================================================索引建立完成"+"================================================");
                //关闭IndexWriter
                indexWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void delete(){

    }

    private String getDateString(){
        //用日期标识新爬取的文件夹
        Calendar calendar = Calendar.getInstance();
        //格式化月份
        int mon = calendar.get(Calendar.MONTH)+1;
        String month = mon>9?String.valueOf(mon):"0"+String.valueOf(mon);
        //格式化日号
        int dat = calendar.get(Calendar.DATE);
        String date = dat>9?String.valueOf(dat):"0"+String.valueOf(dat);
        //拼接日期字符串
        String datestr = String.valueOf(calendar.get(Calendar.YEAR))+month+date;
        return datestr;
    }

    private IndexWriter getIndexWriter(){
        IndexWriter indexWriter = null;
        try {
            //索引文件路径
            Directory indexdir = null;
            indexdir = FSDirectory.open(new File(indexpath));
            //indexwriter的配置器，配置分词器
            IndexWriterConfig indexWriterConfig = new IndexWriterConfig(org.apache.lucene.util.Version.LUCENE_36,analyzer);
            indexWriterConfig.setOpenMode(IndexWriterConfig.OpenMode.CREATE);
            indexWriter = new IndexWriter(indexdir,indexWriterConfig);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return indexWriter;
    }
}
