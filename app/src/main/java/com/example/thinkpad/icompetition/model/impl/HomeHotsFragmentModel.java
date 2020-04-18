package com.example.thinkpad.icompetition.model.impl;

import com.example.thinkpad.icompetition.model.entity.exam.ExamRecordRoot;
import com.example.thinkpad.icompetition.model.event.HomeHotsEvent;
import com.example.thinkpad.icompetition.model.i.IHomeHotFragmentModel;
import com.example.thinkpad.icompetition.network.CallbackIntercept;
import com.example.thinkpad.icompetition.network.NetworkInterfaces;
import com.google.gson.Gson;

import okhttp3.Call;
import okhttp3.Callback;

/**
 * Created By hjg on 2018/11/29
 */
public class HomeHotsFragmentModel
        extends BaseFragmentModel<HomeHotsEvent>
        implements IHomeHotFragmentModel {

    public HomeHotsFragmentModel(OnEventReceiveListener<HomeHotsEvent> eventReceiveListener) {
        super(eventReceiveListener);
    }

    //获取热门竞赛信息
    @Override
    public void getHotItem(int page_no, int page_size) {
        Callback callback = new CallbackIntercept() {
            @Override
            public void onSuccess(Call call, String jsonBody) {
                HomeHotsEvent event = new HomeHotsEvent();
                Gson gson = new Gson();
                ExamRecordRoot recordRoot = gson.fromJson(jsonBody, ExamRecordRoot.class);
                if(recordRoot!=null && recordRoot.getCode() != 0){
                    event.setWhat(HomeHotsEvent.GET_HOTS_OK);
                    event.setRoot(recordRoot);
                }else {
                    event.setWhat(HomeHotsEvent.GET_HOTS_FAIL);
                }

                postEvent(event);
            }

            @Override
            public void onFail(Call call, Exception e) {
                HomeHotsEvent event = new HomeHotsEvent();
                event.setWhat(HomeHotsEvent.GET_HOTS_FAIL);
                postEvent(event);
            }
        };
        mNetworkInterface.getHotsExam(callback, page_no, page_size);
    }

    @Override
    protected NetworkInterfaces getNetworkInterface() {
        return new NetworkInterfaces();
    }
}
