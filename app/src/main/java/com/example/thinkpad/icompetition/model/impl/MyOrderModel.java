package com.example.thinkpad.icompetition.model.impl;

import android.os.Handler;

import com.example.thinkpad.icompetition.model.entity.NormalRoot;
import com.example.thinkpad.icompetition.model.entity.order.OrderRoot;
import com.example.thinkpad.icompetition.model.event.OrderInfoEvent;
import com.example.thinkpad.icompetition.model.i.IBaseModel;
import com.example.thinkpad.icompetition.model.i.IMyOrderModel;
import com.example.thinkpad.icompetition.network.CallbackIntercept;
import com.example.thinkpad.icompetition.network.NetworkInterfaces;
import com.google.gson.Gson;

import okhttp3.Call;
import okhttp3.Callback;

/**
 * Created By hjg on 2018/12/15
 */
public class MyOrderModel extends BaseModel
        implements IMyOrderModel, IBaseModel {

    public MyOrderModel(Handler handler) {
        super(handler);
    }

    @Override
    public void queryByPageOrder(String userNum, int page, int pageSize) {
        Callback callback = new CallbackIntercept() {
            @Override
            public void onSuccess(Call call, String jsonBody) {
                Gson gson = new Gson();
                OrderRoot root = gson.fromJson(jsonBody, OrderRoot.class);
                if(root != null){
                    OrderInfoEvent event = new OrderInfoEvent();
                    event.setOrderRoot(root);
                    event.setWhat(OrderInfoEvent.QUERY_USER_ORDER_OK);
                    postEvent(event);
                }
            }

            @Override
            public void onFail(Call call, Exception e) {
                OrderInfoEvent event = new OrderInfoEvent();
                event.setWhat(OrderInfoEvent.QUERY_USER_ORDER_FAIL);
                postEvent(event);
            }
        };
        mNetworkInterface.queryByPageOrder(callback, page);
    }

    @Override
    public void deleteOrder(int order_id) {
        Callback callback = new CallbackIntercept() {
            @Override
            public void onSuccess(Call call, String jsonBody) {
                Gson gson = new Gson();
                NormalRoot root = gson.fromJson(jsonBody, NormalRoot.class);
                if(root != null){
                    OrderInfoEvent event = new OrderInfoEvent();
                    event.setNormalRoot(root);
                    event.setWhat(OrderInfoEvent.DELETE_USER_ORDER_OK);
                    postEvent(event);
                }
            }

            @Override
            public void onFail(Call call, Exception e) {
                OrderInfoEvent event = new OrderInfoEvent();
                event.setWhat(OrderInfoEvent.DELETE_USER_ORDER_FAIL);
                postEvent(event);
            }
        };
        mNetworkInterface.deleteOrder(callback, order_id);
    }

    @Override
    protected NetworkInterfaces getNetworkInterface() {
        return new NetworkInterfaces();
    }
}
