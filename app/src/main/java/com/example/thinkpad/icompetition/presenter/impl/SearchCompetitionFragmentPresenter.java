package com.example.thinkpad.icompetition.presenter.impl;

import android.support.annotation.NonNull;
import com.example.thinkpad.icompetition.model.event.SearchEvent;
import com.example.thinkpad.icompetition.model.impl.BaseFragmentModel;
import com.example.thinkpad.icompetition.model.impl.SearchCompetitionFragmentModel;
import com.example.thinkpad.icompetition.presenter.i.ISearchCompetitionFragmentPresenter;
import com.example.thinkpad.icompetition.view.fragment.impl.SearchCompetitionFragment;

public class SearchCompetitionFragmentPresenter extends BaseFragmentPresenter<SearchCompetitionFragment,SearchCompetitionFragmentModel,SearchEvent>
        implements ISearchCompetitionFragmentPresenter {
    public SearchCompetitionFragmentPresenter(SearchCompetitionFragment view) {
        super(view);
    }

    @Override
    protected SearchCompetitionFragmentModel getModel(@NonNull BaseFragmentModel.OnEventReceiveListener<SearchEvent> eventReceiveListener) {
        return new SearchCompetitionFragmentModel(eventReceiveListener);
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
