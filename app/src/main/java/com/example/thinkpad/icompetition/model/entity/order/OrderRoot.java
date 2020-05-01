package com.example.thinkpad.icompetition.model.entity.order;

import java.util.List;

/**
 * Created By hjg on 2020/4/30
 */
public class OrderRoot {
    private int code;

    private String msg;

    private int total_page;

    private int count;

    private List<OrderItem> data;

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
    public void setData(List<OrderItem> data){
        this.data = data;
    }
    public List<OrderItem> getData(){
        return this.data;
    }
}
