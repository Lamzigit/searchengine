package com.zhaopin.controller;

import entity.Employ;
import entity.SearchResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.wltea.analyzer.lucene.IKAnalyzer;
import service.SearchService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 林志杰 on 2017/3/29.
 */
@Controller
public class SearchController {

    @Autowired
    SearchService searchService;

    @RequestMapping(value = "/search")
    public String search(String term,String  page_no, HttpServletRequest request, HttpSession session){

        List<Employ> employList = new ArrayList<>();
        List<Employ> sessioncache = new ArrayList<>();
        /*
         * firstpage值记录当前session缓存块中的第一页对应的实际页码
         * 当前会话的第一次搜索，session中没有firstpage属性，需要将firstpage初始化为0
         */
        int firstpage = session.getAttribute("firstpage")==null?0:(int) session.getAttribute("firstpage");
        /*
         * 如果page_no为空，说明请求为一次新搜索
         * 如果page_no对5求余为一，且不等于当前session缓存中的首页页码，说明请求为同一次搜索的更新缓存
         */
        if(Integer.parseInt(page_no)>firstpage+4||Integer.parseInt(page_no)<firstpage||
                (Integer.parseInt(page_no)%5==1 &&
                (Integer.parseInt(page_no)!=firstpage||!term.equals(session.getAttribute("term"))))){
            int searchcount = firstpage/5+1;
            //更新firstpage
            firstpage = (Integer.parseInt(page_no));
            while (firstpage%5!=1){
                firstpage--;
            }

            searchService = new SearchService();
            searchService.initiate(new IKAnalyzer(true),"../webapps/searchengine/index");
            SearchResult searchResult = searchService.search(term,"default",searchcount,75);
            List<Employ> resultList = searchResult.getEmployList();
            //将第一页（15条）的结果存入employList，输出到前台
            for(int i=0;i<15;i++){
                Employ employ = resultList.get(i);
                if(employ != null){
                    employList.add(employ);
                }
            }
            //将全部结果（包括第一页）存入sessioncache，作为session缓存块
            for(Employ employ: resultList){
                sessioncache.add(employ);
            }
            //在结果页中再次搜索，需要更新session中的各种数据，先删除，再重设
            request.setAttribute("employList",employList);
            if(term.equals(session.getAttribute("term"))){
                session.removeAttribute("term");
                session.removeAttribute("searchtime");
                session.removeAttribute("docmax");
                session.removeAttribute("term");
                session.removeAttribute("sessioncache");
                session.removeAttribute("firstpage");
            }
            session.setAttribute("searchtime",searchResult.getSearchtime());
            session.setAttribute("docmax",searchResult.getDocMax());
            session.setAttribute("term",term);
            session.setAttribute("sessioncache",sessioncache);
            session.setAttribute("firstpage",firstpage);
            return "result";
        }

        else{
            sessioncache = (List<Employ>) session.getAttribute("sessioncache");
            int page = Integer.parseInt(page_no)%5;
            /*
             * 每一个缓存块（一次只包含5页内容）的最后一页对应的实际页码对5求余为零，例如5,10,15等等。
             * 因此将实际页码转化为缓冲块中的页码时，最后一页需要在求余后再重新计算
             */
            if(page == 0){
                page = (firstpage/5+1)*5;
            }

            int start = (page-1)*15;
            /*
             * 实际页码的最后一页的结果条数往往不足一页，需要判断上界
             * 当页码不是最后一页时，(page*15-1)小于sessioncache.size()，上界取(page*15-1)
             * 当页码为最后一页时，(page*15-1)大于sessioncache.size()，上界取sessioncache.size()
             */
            int end = (page*15)<sessioncache.size()?(page*15):sessioncache.size();
            /**
             * 调试输出
             */
            System.out.println("===================");
            System.out.println("firstpage:"+firstpage);
            System.out.println("page:"+page);
            System.out.println("end:"+end);

            for(int i = start;i<end;i++){
                employList.add(sessioncache.get(i));
            }
            request.setAttribute("employList",employList);
            return "result";
        }
    }
}
