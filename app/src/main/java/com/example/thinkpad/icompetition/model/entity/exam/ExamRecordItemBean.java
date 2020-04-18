package com.example.thinkpad.icompetition.model.entity.exam;

import java.io.Serializable;

/**
 * Created by Hjg on 2018/11/26.
 * 比赛的详细信息
 */
public class ExamRecordItemBean implements Serializable {
    private int com_browse;         //浏览次数
    private String com_endtime;     //比赛结束时间
    private String com_id;          //比赛id
    private String com_level;       //竞赛级别
    private String com_mode;        //竞赛模式
    private String com_object;      //参赛对象
    private String com_picture;     //竞赛图片
    private String com_publisher;   //竞赛发布者
    private String com_signupend;   //报名结束时间
    private String com_signupstart; //报名开始时间
    private String com_sponsor;     //主办方
    private String com_squrl;       //
    private String com_starttime;   //比赛开始时间
    private String com_title;       //标题
    private String com_type;        //竞赛类别
    private String com_url;         //竞赛主办方网址

    public int getCom_browse() {
        return com_browse;
    }

    public void setCom_browse(int com_browse) {
        this.com_browse = com_browse;
    }

    public String getCom_endtime() {
        return com_endtime;
    }

    public void setCom_endtime(String com_endtime) {
        this.com_endtime = com_endtime;
    }

    public String getCom_id() {
        return com_id;
    }

    public void setCom_id(String com_id) {
        this.com_id = com_id;
    }

    public String getCom_level() {
        return com_level;
    }

    public void setCom_level(String com_level) {
        this.com_level = com_level;
    }

    public String getCom_mode() {
        return com_mode;
    }

    public void setCom_mode(String com_mode) {
        this.com_mode = com_mode;
    }

    public String getCom_object() {
        return com_object;
    }

    public void setCom_object(String com_object) {
        this.com_object = com_object;
    }

    public String getCom_picture() {
        return com_picture;
    }

    public void setCom_picture(String com_picture) {
        this.com_picture = com_picture;
    }

    public String getCom_publisher() {
        return com_publisher;
    }

    public void setCom_publisher(String com_publisher) {
        this.com_publisher = com_publisher;
    }

    public String getCom_signupend() {
        return com_signupend;
    }

    public void setCom_signupend(String com_signupend) {
        this.com_signupend = com_signupend;
    }

    public String getCom_signupstart() {
        return com_signupstart;
    }

    public void setCom_signupstart(String com_signupstart) {
        this.com_signupstart = com_signupstart;
    }

    public String getCom_sponsor() {
        return com_sponsor;
    }

    public void setCom_sponsor(String com_sponsor) {
        this.com_sponsor = com_sponsor;
    }

    public String getCom_squrl() {
        return com_squrl;
    }

    public void setCom_squrl(String com_squrl) {
        this.com_squrl = com_squrl;
    }

    public String getCom_starttime() {
        return com_starttime;
    }

    public void setCom_starttime(String com_starttime) {
        this.com_starttime = com_starttime;
    }

    public String getCom_title() {
        return com_title;
    }

    public void setCom_title(String com_title) {
        this.com_title = com_title;
    }

    public String getCom_type() {
        return com_type;
    }

    public void setCom_type(String com_type) {
        this.com_type = com_type;
    }

    public String getCom_url() {
        return com_url;
    }

    public void setCom_url(String com_url) {
        this.com_url = com_url;
    }
}
