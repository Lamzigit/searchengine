package entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by 林志杰 on 2017/3/27.
 */
@Entity
@Table(name="recruitment")
public class Employ implements Serializable{
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name="id",unique = true)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(name="source",length = 100)
    private String source;
    @Column(name="link",length = 100)
    private String link;
    @Column(name = "title",length = 100)
    private String title;
    @Column(name = "company",length = 100)
    private String company;
    @Column(name = "publishtime",length = 100)
    private String publishtime;//考虑用Date类型数组
    @Column(name = "description",length = 1000)
    private String description;
    @Column(name = "address",length = 500)
    private String address;
    @Column(name = "salary",length = 100)
    private String salary;//考虑用int数组
    @Column(name = "city",length = 100)
    private String city;
    @Column(name = "experience",length = 100)
    private String experience;
    @Column(name = "education",length = 100)
    private String education;
    @Column(name = "attribute",length = 100)
    private String attribute;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getPublishtime() {
        return publishtime;
    }

    public void setPublishtime(String publishtime) {
        this.publishtime = publishtime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getAttribute() {
        return attribute;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }

    /*public String toString(){

        return  "++++++++++++++++++++++++++++++++++++\n"+"来源：" +source +"\n链接："+link +"\n标题：" +title +"\n公司：" +company +"\n发布时间：" +publishtime +"\n详细描述：" +description +"\n地址：" +address +"\n工资：" +salary +"\n城市："
                +city +"\n经验：" +experience +"\n学历：" +education +"\n性质：" +attribute;
    }*/
}
