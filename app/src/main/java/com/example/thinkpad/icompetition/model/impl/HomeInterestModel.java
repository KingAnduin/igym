package com.example.thinkpad.icompetition.model.impl;

import android.util.Log;

import com.example.thinkpad.icompetition.model.entity.exam.ExamRecordRoot;
import com.example.thinkpad.icompetition.model.event.HomeInterestEvent;
import com.example.thinkpad.icompetition.model.i.IHomeInterestModel;
import com.example.thinkpad.icompetition.network.CallbackIntercept;
import com.example.thinkpad.icompetition.network.NetworkInterfaces;

import com.google.gson.Gson;

import okhttp3.Call;
import okhttp3.Callback;

public class HomeInterestModel extends BaseFragmentModel<HomeInterestEvent> implements IHomeInterestModel {
    public HomeInterestModel(OnEventReceiveListener<HomeInterestEvent> eventReceiveListener) {
        super(eventReceiveListener);
    }

    @Override
    protected NetworkInterfaces getNetworkInterface() {
        return new NetworkInterfaces();
    }

    //获取
    @Override
    public void getTypeItem(int page_no, int page_size, String type) {
        Callback callback = new CallbackIntercept(){

            @Override
            public void onSuccess(Call call, String jsonBody) {
                HomeInterestEvent event = new HomeInterestEvent();
                Gson gson = new Gson();
                ExamRecordRoot recordRoot = gson.fromJson(jsonBody, ExamRecordRoot.class);
                if(recordRoot!=null && recordRoot.getCode() != 0){
                    event.setWhat(HomeInterestEvent.GET_TYPE_OK);
                    event.setRoot(recordRoot);
                }else {
                    event.setWhat(HomeInterestEvent.GET_TYPE_FAIL);
                }

                postEvent(event);
            }

            @Override
            public void onFail(Call call, Exception e) {
                HomeInterestEvent event = new HomeInterestEvent();
                event.setWhat(HomeInterestEvent.GET_TYPE_FAIL);
                postEvent(event);
            }
        };
        mNetworkInterface.getTypeExam(callback, page_no, page_size, type);
    }
}
