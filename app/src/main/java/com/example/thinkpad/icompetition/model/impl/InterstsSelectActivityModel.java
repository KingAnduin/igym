package com.example.thinkpad.icompetition.model.impl;

import android.os.Handler;

import com.example.thinkpad.icompetition.model.entity.Interest.Interestroot;
import com.example.thinkpad.icompetition.model.event.InterestEvent;
import com.example.thinkpad.icompetition.model.i.IInterstsSelectActivityModel;
import com.example.thinkpad.icompetition.network.CallbackIntercept;
import com.example.thinkpad.icompetition.network.NetworkInterfaces;
import com.google.gson.Gson;

import okhttp3.Call;
import okhttp3.Callback;

public class InterstsSelectActivityModel extends BaseModel implements IInterstsSelectActivityModel {
    public InterstsSelectActivityModel(Handler handler) {
        super(handler);
    }

    @Override
    protected NetworkInterfaces getNetworkInterface() {
        return new NetworkInterfaces();
    }

    @Override
    public void getUserInterest(int page, int pageSize) {
        Callback callback = new CallbackIntercept() {
            @Override
            public void onSuccess(Call call, String jsonBody) {
                Gson gson = new Gson();
                Interestroot root = gson.fromJson(jsonBody,Interestroot.class);
                InterestEvent event = new InterestEvent();
                if(root!=null){
                    event.setWhat(InterestEvent.INTERESTS_USER_OK);
                    event.setInterestroot(root);
                    postEvent(event);
                }else {
                    event.setWhat(InterestEvent.INTERESTS_USER_FAIL);
                    postEvent(event);
                }
            }

            @Override
            public void onFail(Call call, Exception e) {
                InterestEvent event = new InterestEvent();
                event.setWhat(InterestEvent.INTERESTS_USER_FAIL);
                postEvent(event);
            }
        };

    }

    @Override
    public void addInterest(String typename) {
        Callback callback = new CallbackIntercept() {
            @Override
            public void onSuccess(Call call, String jsonBody) {
                Gson gson = new Gson();
                Interestroot root = gson.fromJson(jsonBody,Interestroot.class);
                InterestEvent event = new InterestEvent();
                if(root!=null){
                    event.setWhat(InterestEvent.INTERESTS_ADD_OK);
                    event.setInterestroot(root);
                    postEvent(event);
                }else {
                    event.setWhat(InterestEvent.INTERESTS_ADD_FAIL);
                    postEvent(event);
                }
            }

            @Override
            public void onFail(Call call, Exception e) {
                InterestEvent event = new InterestEvent();
                event.setWhat(InterestEvent.INTERESTS_ADD_FAIL);
                postEvent(event);
            }
        };
      }

    @Override
    public void deleteInterest(String typename) {
        Callback callback = new CallbackIntercept() {
            @Override
            public void onSuccess(Call call, String jsonBody) {
                Gson gson = new Gson();
                Interestroot root = gson.fromJson(jsonBody,Interestroot.class);
                InterestEvent event = new InterestEvent();
                if(root!=null){
                    event.setWhat(InterestEvent.INTERESTS_DELETE_OK);
                    event.setInterestroot(root);
                    postEvent(event);
                }else {
                    event.setWhat(InterestEvent.INTERESTS_DELETE_FAIL);
                    postEvent(event);
                }
            }

            @Override
            public void onFail(Call call, Exception e) {
                InterestEvent event = new InterestEvent();
                event.setWhat(InterestEvent.INTERESTS_DELETE_FAIL);
                postEvent(event);
            }
        };
    }

    @Override
    public void getInterestType() {
        Callback callback = new CallbackIntercept() {
            @Override
            public void onSuccess(Call call, String jsonBody) {
                Gson gson = new Gson();
                Interestroot root = gson.fromJson(jsonBody,Interestroot.class);
                InterestEvent event = new InterestEvent();
                if(root!=null){
                    event.setWhat(InterestEvent.INTERESTS_TYPE_OK);
                    event.setInterestroot(root);
                    postEvent(event);
                }else {
                    event.setWhat(InterestEvent.INTERESTS_TYPE_FAIL);
                    postEvent(event);
                }
            }

            @Override
            public void onFail(Call call, Exception e) {
                InterestEvent event = new InterestEvent();
                event.setWhat(InterestEvent.INTERESTS_TYPE_FAIL);
                postEvent(event);
            }
        };
       }
}
