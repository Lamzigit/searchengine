package entity;

import java.util.List;

/**
 * Created by 林志杰 on 2017/3/31.
 */
public class SearchResult {
    List<Employ> employList;
    double searchtime;
    double docMax;
    String query;

    public List<Employ> getEmployList() {
        return employList;
    }

    public void setEmployList(List<Employ> employList) {
        this.employList = employList;
    }

    public double getSearchtime() {
        return searchtime;
    }

    public void setSearchtime(double searchtime) {
        this.searchtime = searchtime;
    }

    public double getDocMax() {
        return docMax;
    }

    public void setDocMax(double docMax) {
        this.docMax = docMax;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }
}
