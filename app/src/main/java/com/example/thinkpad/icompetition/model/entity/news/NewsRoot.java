package com.example.thinkpad.icompetition.model.entity.news;

import java.util.List;

/**
 * Created By hjg on 2020/4/25
 * 健身资讯
 */
public class NewsRoot {
    private int code;

    private String msg;

    private int total_page;

    private int count;

    private List<NewsItem> data;

    public void setCode(int code){
        this.code = code;
    }
    public int getCode(){
        return this.code;
    }
    public void setMsg(String msg){
        this.msg = msg;
    }
    public String getMsg(){
        return this.msg;
    }
    public void setTotal_page(int total_page){
        this.total_page = total_page;
    }
    public int getTotal_page(){
        return this.total_page;
    }
    public void setCount(int count){
        this.count = count;
    }
    public int getCount(){
        return this.count;
    }
    public void setData(List<NewsItem> data){
        this.data = data;
    }
    public List<NewsItem> getData(){
        return this.data;
    }
}
