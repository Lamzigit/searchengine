package entity;

/**
 * Created by 林志杰 on 2017/3/26.
 */
public class Curl {
    private String link;
    private String page;
    private int type;//0为目录，1为详情

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
