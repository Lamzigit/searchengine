package service;

import dao.EmployDaoImp;
import entity.Employ;
import entity.SearchResult;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.highlight.*;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.dom4j.DocumentHelper;
import org.wltea.analyzer.lucene.IKAnalyzer;
import util.EmployHelper;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

/**
 * Created by 林志杰 on 2017/3/28.
 */
public class SearchService {
    private Analyzer analyzer = null;
    private IndexSearcher indexSearcher = null;
    private boolean isinit = false;
    private EmployService employService = null;

    public void initiate(Analyzer analyzer,String indexpath){
        this.analyzer = analyzer;
        employService = new EmployService();
        IndexReader indexReader = SingleIndexReader.getInstance();
        indexSearcher = new IndexSearcher(indexReader);
        isinit = true;
    }


    public SearchResult search(String term, String field){
        SearchResult searchResult = new SearchResult();
        if(isinit){
            List<Employ> employList = new ArrayList<>();
            double start = 0;
            double end = 0;
            int docmax = 0;
            QueryParser queryParser = new QueryParser(Version.LUCENE_30,field,analyzer);
            Query query = null;
            try {
                query = queryParser.parse(term);
                start = LocalTime.now().toNanoOfDay();
                TopDocs topDocs = indexSearcher.search(query,30);
                //统计条数
                docmax = indexSearcher.docFreq(new Term("default",term));
                for(ScoreDoc scoreDoc : topDocs.scoreDocs){
                    Document document = indexSearcher.doc(scoreDoc.doc);

                    Employ employ = EmployHelper.toEmploy(document);

                    QueryScorer queryScorer = new QueryScorer(query);
                    Fragmenter fragmenter = new SimpleSpanFragmenter(queryScorer,100);
                    Formatter formatter = new SimpleHTMLFormatter("<span style='color:red'>","</span>");
                    Highlighter highlighter = new Highlighter(formatter,queryScorer);
                    highlighter.setTextFragmenter(fragmenter);
                    employ = employService.getEmployById(employ.getId());
                    String desc = highlighter.getBestFragment(new IKAnalyzer(true),"description",employ.getDescription());
                    String title = highlighter.getBestFragment(new IKAnalyzer(true),"title",employ.getTitle());
                    employ.setDescription(desc);
                    employ.setTitle(title);
                    employList.add(employ);
                }
                end = LocalTime.now().toNanoOfDay();
            } catch (ParseException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InvalidTokenOffsetsException e) {
                e.printStackTrace();
            }
            double searchtime = (end-start)/1000000000;
            searchResult.setEmployList(employList);
            searchResult.setSearchtime(searchtime);
            searchResult.setDocMax(docmax);
        }else {
            searchResult.setEmployList(null);
            searchResult.setSearchtime(0);
            searchResult.setDocMax(0);
            System.out.println("SearchService没有初始化");
        }
        return searchResult;
    }

//    public List<Employ> search(String term) throws InvocationTargetException, InstantiationException, IllegalAccessException, IOException, ParseException {
//        String[] terms =null;
//        if(term.contains(" ")){
//            terms = term.split(" ");
//        }
//        else if(term.contains(",")){
//            terms = term.split(",");
//        }
//        else{
//            terms = new String[1];
//            terms[0] = term;
//        }
//        int count = terms.length;
//
//        HashMap<String,Employ> employs = new HashMap<>();
//        List<Employ> employsList = new ArrayList<>();
//        IndexReader reader = IndexReader.open(FSDirectory.open(new File("F:\\Java\\searchdemo01\\index")));
//        IndexSearcher indexSearcher = new IndexSearcher(reader);
//        for(int i=0;i<count;i++){
//            TopDocs topDocs = searchDefaultField(terms[i]);
//            ScoreDoc[] scoreDocs = topDocs.scoreDocs;
//            for(int j=0;j<scoreDocs.length;j++){
//                Employ employ = DocumentHelper.(indexSearcher.doc(scoreDocs[j].doc));
//                if(employ != null){
//                    employs.put(employ.getTitle(),employ);
//                    System.out.println(employ.getTitle());
//                }
//            }
//        }
//        for(String key : employs.keySet()){
//            Employ re = employs.get(key);
//            employsList.add(re);
//        }
//
//        return employsList;
//    }

}
