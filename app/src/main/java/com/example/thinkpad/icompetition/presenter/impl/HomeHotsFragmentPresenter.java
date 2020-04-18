package com.example.thinkpad.icompetition.presenter.impl;

import android.support.annotation.NonNull;

import com.example.thinkpad.icompetition.model.event.HomeHotsEvent;
import com.example.thinkpad.icompetition.model.impl.BaseFragmentModel;
import com.example.thinkpad.icompetition.model.impl.HomeHotsFragmentModel;
import com.example.thinkpad.icompetition.presenter.i.IHomeHotsFragmentPresenter;
import com.example.thinkpad.icompetition.view.fragment.impl.HomeHotsFragment;

/**
 * Created By hjg on 2018/11/29
 */
public class HomeHotsFragmentPresenter
        extends BaseFragmentPresenter<HomeHotsFragment, HomeHotsFragmentModel, HomeHotsEvent>
        implements IHomeHotsFragmentPresenter {

    public HomeHotsFragmentPresenter(HomeHotsFragment view) {
        super(view);
    }

    @Override
    public void getHotsInfo(int page_no, int page_size) {
        mModel.getHotItem(page_no, page_size);
    }

    @Override
    protected HomeHotsFragmentModel getModel(@NonNull BaseFragmentModel.OnEventReceiveListener<HomeHotsEvent> eventReceiveListener) {
        return new HomeHotsFragmentModel(eventReceiveListener);
    }

    @Override
    public void eventReceive(HomeHotsEvent event, int what) {

        if(event != null){
            switch (what){
                case HomeHotsEvent.GET_HOTS_OK:
                    mView.pagingQueryHomeHotsResponse(event.getRoot());
                    break;
                case HomeHotsEvent.GET_HOTS_FAIL:
                    mView.failBecauseNotNetworkReturn(what);
                    break;
            }
        }
    }
}
