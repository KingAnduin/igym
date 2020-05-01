package com.example.thinkpad.icompetition.model.impl;

import android.os.Handler;

import com.example.thinkpad.icompetition.model.entity.collection.CollectionRoot;
import com.example.thinkpad.icompetition.model.entity.collection.IsCollectionRoot;
import com.example.thinkpad.icompetition.model.entity.focus.MyFocusRoot;
import com.example.thinkpad.icompetition.model.entity.search.IsConcernRoot;
import com.example.thinkpad.icompetition.model.event.OrderInfoEvent;
import com.example.thinkpad.icompetition.model.event.FocusEvent;
import com.example.thinkpad.icompetition.model.i.IBaseModel;
import com.example.thinkpad.icompetition.model.i.ICompetitionModel;
import com.example.thinkpad.icompetition.network.CallbackIntercept;
import com.example.thinkpad.icompetition.network.NetworkInterfaces;
import com.google.gson.Gson;

import okhttp3.Call;
import okhttp3.Callback;

public class CompetitionModel extends BaseModel implements IBaseModel,ICompetitionModel {
    public CompetitionModel(Handler handler) {
        super(handler);
    }

    @Override
    protected NetworkInterfaces getNetworkInterface() {
        return new NetworkInterfaces();
    }

    @Override
    public void getIsCollection(String com_id) {

    }

    @Override
    public void addCollection(String user_num, String com_id) {

    }

    @Override
    public void cancelCollection(String com_id) {

    }

    @Override
    public void getIsFocus(String other_num) {
        Callback callback = new CallbackIntercept() {
            @Override
            public void onSuccess(Call call, String jsonBody) {
                Gson gson = new Gson();
                IsConcernRoot root = gson.fromJson(jsonBody, IsConcernRoot.class);
                if(root != null ){
                    FocusEvent event = new FocusEvent();
                    event.setRoot(root);
                    event.setWhat(FocusEvent.GET_ISCONCERN_OK);
                    postEvent(event);
                }
            }

            @Override
            public void onFail(Call call, Exception e) {
                FocusEvent event = new FocusEvent();
                event.setWhat(FocusEvent.GET_ISCONCERN_FAIL);
                postEvent(event);
            }
        };

    }

    @Override
    public void addFocus(String other_num) {
        Callback callback = new CallbackIntercept() {
            @Override
            public void onSuccess(Call call, String jsonBody) {
                Gson gson = new Gson();
                MyFocusRoot root = gson.fromJson(jsonBody, MyFocusRoot.class);
                if(root != null ){
                    FocusEvent event = new FocusEvent();
                    event.setMyFocusRoot(root);
                    event.setWhat(FocusEvent.ADD_CONCERN_OK);
                    postEvent(event);
                }
            }

            @Override
            public void onFail(Call call, Exception e) {
                FocusEvent event = new FocusEvent();
                event.setWhat(FocusEvent.ADD_CONCERN_FAIL);
                postEvent(event);
            }
        };


    }

    @Override
    public void cancelFocus(String other_num) {
        Callback callback = new CallbackIntercept() {
            @Override
            public void onSuccess(Call call, String jsonBody) {
                Gson gson = new Gson();
                MyFocusRoot root = gson.fromJson(jsonBody, MyFocusRoot.class);
                if(root != null ){
                    FocusEvent event = new FocusEvent();
                    event.setMyFocusRoot(root);
                    event.setWhat(FocusEvent.DELETE_CONCERN_OK);
                    postEvent(event);
                }
            }

            @Override
            public void onFail(Call call, Exception e) {
                FocusEvent event = new FocusEvent();
                event.setWhat(FocusEvent.DELETE_CONCERN_FAIL);
                postEvent(event);
            }
        };


    }
}
