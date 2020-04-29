package com.example.thinkpad.icompetition.model.event;


import com.example.thinkpad.icompetition.model.entity.news.NewsRoot;

/**
 * Created by Hjg on 2018/11/26.
 */
public class HomeRecommendNewsEvent extends BaseEvent{
    public static final int GET_RECOMMEND_OK = 201;
    public static final int GET_RECOMMEND_FAIL = 200;


    private static String ErrorMsg;
    private NewsRoot root;

    public void setErrorMsg(String errorMsg) {
        ErrorMsg = errorMsg;
    }

    public NewsRoot getRoot() {
        return root;
    }

    public void setRoot(NewsRoot root){
        this.root = root;
    }


}
