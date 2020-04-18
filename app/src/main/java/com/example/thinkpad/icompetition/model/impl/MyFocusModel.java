package com.example.thinkpad.icompetition.model.impl;

import android.os.Handler;

import com.example.thinkpad.icompetition.model.entity.focus.MyFocusRoot;
import com.example.thinkpad.icompetition.model.event.FocusEvent;
import com.example.thinkpad.icompetition.model.i.IBaseModel;
import com.example.thinkpad.icompetition.model.i.IMyFocusModel;
import com.example.thinkpad.icompetition.network.CallbackIntercept;
import com.example.thinkpad.icompetition.network.NetworkInterfaces;
import com.google.gson.Gson;

import okhttp3.Call;
import okhttp3.Callback;

/**
 * Created By hjg on 2018/12/28
 */
public class MyFocusModel extends BaseModel
        implements IBaseModel, IMyFocusModel {

    public MyFocusModel(Handler handler) {
        super(handler);
    }

    @Override
    protected NetworkInterfaces getNetworkInterface() {
        return new NetworkInterfaces();
    }

    @Override
    public void queryByPageFocus(int page, int pageSize, String userNum) {
        Callback callback = new CallbackIntercept(){

            @Override
            public void onSuccess(Call call, String jsonBody) {
                Gson gson = new Gson();
                MyFocusRoot root = gson.fromJson(jsonBody, MyFocusRoot.class);
                if(root != null){
                    FocusEvent event = new FocusEvent();
                    event.setMyFocusRoot(root);
                    event.setWhat(FocusEvent.QUERY_FOCUS_OK);
                    postEvent(event);
                }
            }

            @Override
            public void onFail(Call call, Exception e) {
                FocusEvent event = new FocusEvent();
                event.setWhat(FocusEvent.QUERY_FOCUS_FAIL);
                postEvent(event);
            }

        };
        mNetworkInterface.queryByPageFocus(callback, page, pageSize, userNum);
    }
}
