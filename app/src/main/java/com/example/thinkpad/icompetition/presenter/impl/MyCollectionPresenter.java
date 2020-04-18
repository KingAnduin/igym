package com.example.thinkpad.icompetition.presenter.impl;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.example.thinkpad.icompetition.model.entity.collection.CollectionRoot;
import com.example.thinkpad.icompetition.model.entity.exam.ExamRecordRoot;
import com.example.thinkpad.icompetition.model.event.CompetitionInfoEvent;
import com.example.thinkpad.icompetition.model.impl.MyCollectionModel;
import com.example.thinkpad.icompetition.presenter.i.IBasePresenter;
import com.example.thinkpad.icompetition.presenter.i.IMyCollectionPresenter;
import com.example.thinkpad.icompetition.view.activity.impl.MyCollectionActivity;

/**
 * Created By hjg on 2018/12/15
 */
public class MyCollectionPresenter
        extends BasePresenter<MyCollectionActivity, MyCollectionModel>
        implements IMyCollectionPresenter, IBasePresenter {

    public MyCollectionPresenter(MyCollectionActivity view) {
        super(view);
    }

    @Override
    public void queryByPage(String userNum, int page, int pageSize) {
        mModel.queryByPageCollection(userNum, page, pageSize);
    }

    @Override
    protected MyCollectionModel getModel(Handler handler) {
        return new MyCollectionModel(handler);
    }

    @Override
    protected void eventReceive(Message msg) {
        switch (msg.what){
            case CompetitionInfoEvent.QUERY_COLLECTION_OK:
                ExamRecordRoot root = ((CompetitionInfoEvent)msg.obj).getExamRecordRoot();
                mView.queryByPageCollectionResponse(root);
                break;
            case CompetitionInfoEvent.QUERY_COLLECTION_FAIL:
                mView.failBecauseNotNetworkReturn(msg.what);
                break;
        }
    }
}
