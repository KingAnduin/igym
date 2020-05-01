package com.example.thinkpad.icompetition.model.impl;

import android.os.Handler;

import com.example.thinkpad.icompetition.model.entity.NormalRoot;
import com.example.thinkpad.icompetition.model.event.CommentEvent;
import com.example.thinkpad.icompetition.model.i.IBaseModel;
import com.example.thinkpad.icompetition.model.i.ICommentModel;
import com.example.thinkpad.icompetition.network.CallbackIntercept;
import com.example.thinkpad.icompetition.network.NetworkInterfaces;
import com.google.gson.Gson;

import okhttp3.Call;
import okhttp3.Callback;

/**
 * Created By hjg on 2020/5/1
 */
public class CommentModel extends BaseModel implements IBaseModel, ICommentModel {
    public CommentModel(Handler handler) {
        super(handler);
    }

    @Override
    public void addComment(int order_id, String comment) {
        Callback callback = new CallbackIntercept() {
            @Override
            public void onSuccess(Call call, String jsonBody) {
                Gson gson = new Gson();
                NormalRoot root = gson.fromJson(jsonBody, NormalRoot.class);
                if(root != null ){
                    CommentEvent event = new CommentEvent();
                    event.setNormalRoot(root);
                    if(root.getCode() == 200){
                        event.setWhat(CommentEvent.ADD_COMMENT_OK);
                    }else {
                        event.setWhat(CommentEvent.ADD_COMMENT_FAIL);
                    }
                    postEvent(event);
                }
            }

            @Override
            public void onFail(Call call, Exception e) {
                CommentEvent event = new CommentEvent();
                event.setWhat(CommentEvent.ADD_COMMENT_FAIL);
                postEvent(event);
            }
        };
        mNetworkInterface.addComment(callback, order_id, comment);
    }

    @Override
    protected NetworkInterfaces getNetworkInterface() {
        return new NetworkInterfaces();
    }
}
