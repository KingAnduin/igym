package com.example.thinkpad.icompetition.model.entity.order;

import java.io.Serializable;

/**
 * Created By hjg on 2020/4/30
 */
public class OrderItem implements Serializable {
    private int id;

    private String good_name;

    private String order_time_period_name;

    private String order_status_name;

    private String create_time;

    private String order_date;

    private int order_time_period;

    private int order_status;

    private int user_account;

    private int good_id;

    private String order_is_comment;


    public String getOrder_is_comment() {
        return order_is_comment;
    }

    public void setOrder_is_comment(String order_is_comment) {
        this.order_is_comment = order_is_comment;
    }


    public void setId(int id){
        this.id = id;
    }
    public int getId(){
        return this.id;
    }
    public void setGood_name(String good_name){
        this.good_name = good_name;
    }
    public String getGood_name(){
        return this.good_name;
    }
    public void setOrder_time_period_name(String order_time_period_name){
        this.order_time_period_name = order_time_period_name;
    }
    public String getOrder_time_period_name(){
        return this.order_time_period_name;
    }
    public void setOrder_status_name(String order_status_name){
        this.order_status_name = order_status_name;
    }
    public String getOrder_status_name(){
        return this.order_status_name;
    }
    public void setCreate_time(String create_time){
        this.create_time = create_time;
    }
    public String getCreate_time(){
        return this.create_time;
    }
    public void setOrder_date(String order_date){
        this.order_date = order_date;
    }
    public String getOrder_date(){
        return this.order_date;
    }
    public void setOrder_time_period(int order_time_period){
        this.order_time_period = order_time_period;
    }
    public int getOrder_time_period(){
        return this.order_time_period;
    }
    public void setOrder_status(int order_status){
        this.order_status = order_status;
    }
    public int getOrder_status(){
        return this.order_status;
    }
    public void setUser_account(int user_account){
        this.user_account = user_account;
    }
    public int getUser_account(){
        return this.user_account;
    }
    public void setGood_id(int good_id){
        this.good_id = good_id;
    }
    public int getGood_id(){
        return this.good_id;
    }
}
