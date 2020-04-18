package com.example.thinkpad.icompetition.presenter.impl;

import android.support.annotation.NonNull;

import com.example.thinkpad.icompetition.model.entity.exam.ExamRecordRoot;
import com.example.thinkpad.icompetition.model.event.HomeInterestEvent;
import com.example.thinkpad.icompetition.model.impl.BaseFragmentModel;
import com.example.thinkpad.icompetition.model.impl.HomeInterestModel;
import com.example.thinkpad.icompetition.presenter.i.IHomeInterestFragmentPresenter;
import com.example.thinkpad.icompetition.view.fragment.i.IHomeInterestFragment;
import com.example.thinkpad.icompetition.view.fragment.impl.HomeInterestFragment;

public class HomeInterestFragmentPresenter
        extends BaseFragmentPresenter<HomeInterestFragment,HomeInterestModel,HomeInterestEvent>
        implements IHomeInterestFragmentPresenter {
    public HomeInterestFragmentPresenter(HomeInterestFragment view) {
        super(view);
    }

    @Override
    protected HomeInterestModel getModel(@NonNull BaseFragmentModel.OnEventReceiveListener<HomeInterestEvent> eventReceiveListener) {
        return new HomeInterestModel(eventReceiveListener);
    }

    @Override
    public void getTypeInfo(int page_no, int page_size, String type) {
        mModel.getTypeItem(page_no, page_size, type);
    }


    @Override
    public void eventReceive(HomeInterestEvent event, int what) {
        if(event!=null){
            switch (what){
                case HomeInterestEvent.GET_TYPE_OK:
                    mView.PagingQueryHomeInterestResponse(event.getRoot());
                    break;
                case HomeInterestEvent.GET_TYPE_FAIL:
                    mView.failBecauseNotNetworkReturn(what);
                    //mView.failBecauseNotNetworkReturn(what);
                    break;
            }
        }
    }


}
