package com.example.thinkpad.icompetition.presenter.impl;

import android.os.Handler;
import android.os.Message;

import com.example.thinkpad.icompetition.model.event.SearchEvent;
import com.example.thinkpad.icompetition.model.impl.SearchModel;
import com.example.thinkpad.icompetition.presenter.i.ISearchPresenter;
import com.example.thinkpad.icompetition.view.activity.impl.SearchActivity;

public class SearchActivityPresenter extends BasePresenter<SearchActivity,SearchModel> implements ISearchPresenter {
    public SearchActivityPresenter(SearchActivity view) {
        super(view);
    }

    @Override
    protected SearchModel getModel(Handler handler) {
        return new SearchModel(handler);
    }

    @Override
    protected void eventReceive(Message msg) {
    }

}
