package com.example.thinkpad.icompetition.model.impl;

import android.os.Handler;
import android.util.Log;

import com.example.thinkpad.icompetition.model.entity.collection.CollectionRoot;
import com.example.thinkpad.icompetition.model.entity.exam.ExamRecordRoot;
import com.example.thinkpad.icompetition.model.event.CompetitionInfoEvent;
import com.example.thinkpad.icompetition.model.i.IBaseModel;
import com.example.thinkpad.icompetition.model.i.IMyCollectionModel;
import com.example.thinkpad.icompetition.network.CallbackIntercept;
import com.example.thinkpad.icompetition.network.NetworkInterfaces;
import com.google.gson.Gson;

import okhttp3.Call;
import okhttp3.Callback;

/**
 * Created By hjg on 2018/12/15
 */
public class MyCollectionModel extends BaseModel
        implements IMyCollectionModel, IBaseModel {

    public MyCollectionModel(Handler handler) {
        super(handler);
    }

    @Override
    public void queryByPageCollection(String userNum, int page, int pageSize) {
        Callback callback = new CallbackIntercept() {
            @Override
            public void onSuccess(Call call, String jsonBody) {
                Gson gson = new Gson();
                ExamRecordRoot root = gson.fromJson(jsonBody, ExamRecordRoot.class);
                if(root != null){
                    CompetitionInfoEvent event = new CompetitionInfoEvent();
                    event.setExamRecordRoot(root);
                    event.setWhat(CompetitionInfoEvent.QUERY_COLLECTION_OK);
                    postEvent(event);
                }
            }

            @Override
            public void onFail(Call call, Exception e) {
                CompetitionInfoEvent event = new CompetitionInfoEvent();
                event.setWhat(CompetitionInfoEvent.QUERY_COLLECTION_FAIL);
                postEvent(event);
            }
        };
        mNetworkInterface.queryByPageCollection(callback, userNum, page, pageSize);
    }

    @Override
    protected NetworkInterfaces getNetworkInterface() {
        return new NetworkInterfaces();
    }
}
