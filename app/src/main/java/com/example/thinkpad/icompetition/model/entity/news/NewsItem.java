package com.example.thinkpad.icompetition.model.entity.news;

import java.io.Serializable;

/**
 * Created By hjg on 2020/4/25
 * 健身资讯详情
 */
public class NewsItem implements Serializable {
    private int id;
    private String news_title;  //标题
    private String news_type;   //类型
    private String news_level;   //等级
    private String news_major_muscle;   //主要肌群
    private String news_other_muscle;   //其他肌群
    private String news_equipment;   //器械要求
    private String news_gif;   //GIF
    private String news_image;   //图片
    private String news_url;   //网址
    private String news_essentials; //动作要领

    public void setId(int id){
        this.id = id;
    }
    public int getId(){
        return this.id;
    }
    public void setNews_title(String news_title){
        this.news_title = news_title;
    }
    public String getNews_title(){
        return this.news_title;
    }
    public void setNews_type(String news_type){
        this.news_type = news_type;
    }
    public String getNews_type(){
        return this.news_type;
    }
    public void setNews_level(String news_level){
        this.news_level = news_level;
    }
    public String getNews_level(){
        return this.news_level;
    }
    public void setNews_major_muscle(String news_major_muscle){
        this.news_major_muscle = news_major_muscle;
    }
    public String getNews_major_muscle(){
        return this.news_major_muscle;
    }
    public void setNews_other_muscle(String news_other_muscle){
        this.news_other_muscle = news_other_muscle;
    }
    public String getNews_other_muscle(){
        return this.news_other_muscle;
    }
    public void setNews_equipment(String news_equipment){
        this.news_equipment = news_equipment;
    }
    public String getNews_equipment(){
        return this.news_equipment;
    }
    public void setNews_gif(String news_gif){
        this.news_gif = news_gif;
    }
    public String getNews_gif(){
        return this.news_gif;
    }
    public void setNews_image(String news_image){
        this.news_image = news_image;
    }
    public String getNews_image(){
        return this.news_image;
    }
    public void setNews_url(String news_url){
        this.news_url = news_url;
    }
    public String getNews_url(){
        return this.news_url;
    }
    public void setNews_essentials(String news_essentials){
        this.news_essentials = news_essentials;
    }
    public String getNews_essentials(){
        return this.news_essentials;
    }
}
