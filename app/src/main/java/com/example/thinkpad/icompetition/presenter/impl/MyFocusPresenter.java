package com.example.thinkpad.icompetition.presenter.impl;

import android.os.Handler;
import android.os.Message;

import com.example.thinkpad.icompetition.model.entity.focus.MyFocusRoot;
import com.example.thinkpad.icompetition.model.event.FocusEvent;
import com.example.thinkpad.icompetition.model.impl.MyFocusModel;
import com.example.thinkpad.icompetition.presenter.i.IBasePresenter;
import com.example.thinkpad.icompetition.presenter.i.IMyFocusPresenter;
import com.example.thinkpad.icompetition.view.activity.impl.MyFocusActivity;

/**
 * Created By hjg on 2018/12/28
 */
public class MyFocusPresenter
        extends BasePresenter<MyFocusActivity, MyFocusModel>
        implements IBasePresenter, IMyFocusPresenter {

    public MyFocusPresenter(MyFocusActivity view) {
        super(view);
    }

    @Override
    protected MyFocusModel getModel(Handler handler) {
        return new MyFocusModel(handler);
    }

    @Override
    protected void eventReceive(Message msg) {
        switch (msg.what){
            case FocusEvent.QUERY_FOCUS_OK:
                MyFocusRoot root = ((FocusEvent)msg.obj).getMyFocusRoot();
                mView.queryByPageFocusResponse(root);
                break;
            case FocusEvent.QUERY_FOCUS_FAIL:
                mView.failBecauseNotNetworkReturn(msg.what);
                break;

        }
    }

    @Override
    public void queryByPage(int page, int pageSize, String userNum) {
        mModel.queryByPageFocus(page, pageSize, userNum);
    }
}
