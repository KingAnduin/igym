package com.example.thinkpad.icompetition.presenter.impl;

import android.os.Handler;
import android.os.Message;

import com.example.thinkpad.icompetition.model.entity.collection.CollectionRoot;
import com.example.thinkpad.icompetition.model.entity.collection.IsCollectionRoot;
import com.example.thinkpad.icompetition.model.entity.focus.MyFocusRoot;
import com.example.thinkpad.icompetition.model.entity.search.IsConcernRoot;
import com.example.thinkpad.icompetition.model.event.OrderInfoEvent;
import com.example.thinkpad.icompetition.model.event.FocusEvent;
import com.example.thinkpad.icompetition.model.impl.CompetitionModel;
import com.example.thinkpad.icompetition.presenter.i.IBasePresenter;
import com.example.thinkpad.icompetition.presenter.i.ICompetitionPresenter;
import com.example.thinkpad.icompetition.view.activity.impl.CompetitionInfoActivity;

public class CompetitionInfoPresenter
        extends BasePresenter<CompetitionInfoActivity,CompetitionModel>
        implements IBasePresenter,ICompetitionPresenter{
    public CompetitionInfoPresenter(CompetitionInfoActivity view) {
        super(view);
    }

    @Override
    protected CompetitionModel getModel(Handler handler) {
        return new CompetitionModel(handler);
    }

    @Override
    protected void eventReceive(Message msg) {

    }

    @Override
    public void getIsCollection(String com_id) {
        mModel.getIsCollection(com_id);
    }

    @Override
    public void addCollection(String user_num, String com_id) {
        mModel.addCollection(user_num, com_id);
    }

    @Override
    public void cancelCollection(String com_id) {
        mModel.cancelCollection(com_id);
    }

    @Override
    public void getIsFocus(String num) {
        mModel.getIsFocus(num);
    }

    @Override
    public void addFocus(String num) {
        mModel.addFocus(num);
    }

    @Override
    public void cancelFocus(String num) {
        mModel.cancelFocus(num);
    }
}
