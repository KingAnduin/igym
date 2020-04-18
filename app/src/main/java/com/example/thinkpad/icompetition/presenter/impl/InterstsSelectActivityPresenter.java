package com.example.thinkpad.icompetition.presenter.impl;

import android.os.Handler;
import android.os.Message;

import com.example.thinkpad.icompetition.model.entity.Interest.Interestroot;
import com.example.thinkpad.icompetition.model.event.InterestEvent;
import com.example.thinkpad.icompetition.model.impl.InterstsSelectActivityModel;
import com.example.thinkpad.icompetition.presenter.i.IInterstsSelectActivityPresenter;
import com.example.thinkpad.icompetition.view.activity.impl.InterstsSelectActivity;

public class InterstsSelectActivityPresenter extends BasePresenter<InterstsSelectActivity,InterstsSelectActivityModel> implements IInterstsSelectActivityPresenter {
    public InterstsSelectActivityPresenter(InterstsSelectActivity view) {
        super(view);
    }

    @Override
    protected InterstsSelectActivityModel getModel(Handler handler) {
        return new InterstsSelectActivityModel(handler);
    }

    @Override
    protected void eventReceive(Message msg) {
        switch (msg.what){
            case InterestEvent.INTERESTS_USER_OK:
                mView.getUserInterestReturn(((InterestEvent)msg.obj).getInterestroot());
                break;
            case InterestEvent.INTERESTS_USER_FAIL:
                mView.failBecauseNotNetworkReturn(msg.what);
                break;
            case InterestEvent.INTERESTS_TYPE_OK:
                mView.getInterestTypeReturn(((InterestEvent)msg.obj).getInterestroot());
                break;
            case InterestEvent.INTERESTS_TYPE_FAIL:
                mView.failBecauseNotNetworkReturn(msg.what);
                break;
            case InterestEvent.INTERESTS_ADD_OK:
                mView.addInterestReturn(((InterestEvent)msg.obj).getInterestroot());
                break;
            case InterestEvent.INTERESTS_ADD_FAIL:
                mView.failBecauseNotNetworkReturn(msg.what);
                break;
            case InterestEvent.INTERESTS_DELETE_OK:
                mView.deleteInterestReturn(((InterestEvent)msg.obj).getInterestroot());
                break;
            case InterestEvent.INTERESTS_DELETE_FAIL:
                mView.failBecauseNotNetworkReturn(msg.what);
                break;
        }
    }

    @Override
    public void getUserInterest(int page, int pageSize) {
        mModel.getUserInterest(page,pageSize);
    }

    @Override
    public void addInterest(String typename) {
        mModel.addInterest(typename);
    }

    @Override
    public void deleteInterest(String typename) {
        mModel.deleteInterest(typename);
    }

    @Override
    public void getInterestType() {
        mModel.getInterestType();
    }
}
