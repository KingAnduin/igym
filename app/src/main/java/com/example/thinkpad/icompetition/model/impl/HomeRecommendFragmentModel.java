package com.example.thinkpad.icompetition.model.impl;

import com.example.thinkpad.icompetition.model.entity.exam.ExamRecordRoot;
import com.example.thinkpad.icompetition.model.entity.news.NewsRoot;
import com.example.thinkpad.icompetition.model.event.HomeRecommendNewsEvent;
import com.example.thinkpad.icompetition.model.i.IHomeRecommendFragmentModel;
import com.example.thinkpad.icompetition.network.CallbackIntercept;
import com.example.thinkpad.icompetition.network.NetworkInterfaces;
import com.google.gson.Gson;

import okhttp3.Call;
import okhttp3.Callback;

/**
 * Created by Hjg on 2018/11/26.
 */
public class HomeRecommendFragmentModel
        extends BaseFragmentModel<HomeRecommendNewsEvent> implements IHomeRecommendFragmentModel {

    public HomeRecommendFragmentModel(OnEventReceiveListener<HomeRecommendNewsEvent> eventReceiveListener) {
        super(eventReceiveListener);
    }

    //获取推荐
    @Override
    public void getRecommendItem(int page_no, int page_size) {
        Callback callback = new CallbackIntercept(){
            @Override
            public void onSuccess(Call call, String jsonBody) {
                HomeRecommendNewsEvent event = new HomeRecommendNewsEvent();
                Gson gson = new Gson();
                NewsRoot recordRoot = gson.fromJson(jsonBody, NewsRoot.class);
                if(recordRoot!=null && recordRoot.getCode() != 0){
                    event.setWhat(HomeRecommendNewsEvent.GET_RECOMMEND_OK);
                    event.setRoot(recordRoot);
                }else {
                    event.setWhat(HomeRecommendNewsEvent.GET_RECOMMEND_FAIL);
                }

                postEvent(event);
            }

            @Override
            public void onFail(Call call, Exception e) {
                HomeRecommendNewsEvent event = new HomeRecommendNewsEvent();
                event.setWhat(HomeRecommendNewsEvent.GET_RECOMMEND_FAIL);
                postEvent(event);
            }
        };
        mNetworkInterface.getItemNews(callback, page_no, page_size);
    }



    @Override
    protected NetworkInterfaces getNetworkInterface() {
        return new NetworkInterfaces();
    }
}
