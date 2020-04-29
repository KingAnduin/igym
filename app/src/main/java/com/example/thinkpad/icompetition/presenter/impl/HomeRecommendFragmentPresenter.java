package com.example.thinkpad.icompetition.presenter.impl;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.example.thinkpad.icompetition.model.event.HomeRecommendEvent;
import com.example.thinkpad.icompetition.model.event.HomeRecommendNewsEvent;
import com.example.thinkpad.icompetition.model.impl.BaseFragmentModel;
import com.example.thinkpad.icompetition.model.impl.HomeRecommendFragmentModel;
import com.example.thinkpad.icompetition.presenter.i.IHomeRecommendFragmentPresent;
import com.example.thinkpad.icompetition.view.fragment.impl.HomeRecommendFragment;

/**
 * Created by Hjg on 2018/11/26.
 */
public class HomeRecommendFragmentPresenter
        extends BaseFragmentPresenter<HomeRecommendFragment, HomeRecommendFragmentModel, HomeRecommendNewsEvent>
        implements IHomeRecommendFragmentPresent {
    public HomeRecommendFragmentPresenter(HomeRecommendFragment view) {
        super(view);
    }

    @Override
    public void getRecommendInfo(int page_no, int page_size) {
        mModel.getRecommendItem(page_no, page_size);
    }

    @Override
    protected HomeRecommendFragmentModel getModel(@NonNull BaseFragmentModel.OnEventReceiveListener<HomeRecommendNewsEvent> eventReceiveListener) {
        return new HomeRecommendFragmentModel(eventReceiveListener);
    }


    @Override
    public void eventReceive(HomeRecommendNewsEvent event, int what) {
        if(event!=null){
            switch (what){
                case HomeRecommendEvent.GET_RECOMMEND_OK:
                    mView.PagingQueryHomeRecommendResponse(event.getRoot());
                    break;
                case HomeRecommendEvent.GET_RECOMMEND_FAIL:
                    mView.failBecauseNotNetworkReturn(what);
                    break;
            }
        }
    }
}
