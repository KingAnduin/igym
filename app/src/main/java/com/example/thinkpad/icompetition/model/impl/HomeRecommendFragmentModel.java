package com.example.thinkpad.icompetition.model.impl;

import com.example.thinkpad.icompetition.model.entity.exam.ExamRecordRoot;
import com.example.thinkpad.icompetition.model.event.HomeRecommendEvent;
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
        extends BaseFragmentModel<HomeRecommendEvent> implements IHomeRecommendFragmentModel {

    public HomeRecommendFragmentModel(OnEventReceiveListener<HomeRecommendEvent> eventReceiveListener) {
        super(eventReceiveListener);
    }

    //获取推荐
    @Override
    public void getRecommendItem(int page_no, int page_size) {
        Callback callback = new CallbackIntercept(){
            @Override
            public void onSuccess(Call call, String jsonBody) {
                HomeRecommendEvent event = new HomeRecommendEvent();
                Gson gson = new Gson();
                ExamRecordRoot recordRoot = gson.fromJson(jsonBody, ExamRecordRoot.class);
                if(recordRoot!=null && recordRoot.getCode() != 0){
                    event.setWhat(HomeRecommendEvent.GET_RECOMMEND_OK);
                    event.setRoot(recordRoot);
                }else {
                    event.setWhat(HomeRecommendEvent.GET_RECOMMEND_FAIL);
                }

                postEvent(event);
            }

            @Override
            public void onFail(Call call, Exception e) {
                HomeRecommendEvent event = new HomeRecommendEvent();
                event.setWhat(HomeRecommendEvent.GET_RECOMMEND_FAIL);
                postEvent(event);
            }
        };
        mNetworkInterface.getItemExam(callback, page_no, page_size);
    }



    @Override
    protected NetworkInterfaces getNetworkInterface() {
        return new NetworkInterfaces();
    }
}
