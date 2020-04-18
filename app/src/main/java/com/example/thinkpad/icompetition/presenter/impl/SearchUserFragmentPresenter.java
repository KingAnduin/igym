package com.example.thinkpad.icompetition.presenter.impl;

import android.support.annotation.NonNull;

import com.example.thinkpad.icompetition.model.event.SearchEvent;
import com.example.thinkpad.icompetition.model.impl.BaseFragmentModel;
import com.example.thinkpad.icompetition.model.impl.SearchUserFragmentModel;
import com.example.thinkpad.icompetition.presenter.i.ISearchUserFragmentPresenter;
import com.example.thinkpad.icompetition.view.fragment.impl.SearchUserFragment;

public class SearchUserFragmentPresenter extends BaseFragmentPresenter<SearchUserFragment,SearchUserFragmentModel,SearchEvent> implements ISearchUserFragmentPresenter {
    public SearchUserFragmentPresenter(SearchUserFragment view) {
        super(view);
    }

    @Override
    protected SearchUserFragmentModel getModel(@NonNull BaseFragmentModel.OnEventReceiveListener<SearchEvent> eventReceiveListener) {
        return new SearchUserFragmentModel(eventReceiveListener);
    }

    @Override
    public void eventReceive(SearchEvent event, int what) {
        if(event!=null){
            switch (what){
                case SearchEvent.SEARCH_OK:
                    mView.searchReturn(event.getRoot());
                    break;
                case SearchEvent.SEARCH_FAIL:
                    mView.failBecauseNotNetworkReturn(what);
                    break;
            }
        }
    }

    @Override
    public void searchInfor(int page, int page_size, String words) {
        mModel.searchInfor(page,page_size,words);
    }
}
