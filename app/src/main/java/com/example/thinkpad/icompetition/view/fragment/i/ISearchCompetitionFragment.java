package com.example.thinkpad.icompetition.view.fragment.i;

import com.example.thinkpad.icompetition.model.entity.search.SearchRoot;
import com.example.thinkpad.icompetition.presenter.i.IBaseFragmentPresenter;

public interface ISearchCompetitionFragment extends IBaseFragmentPresenter {
    void searchInfor(int page,int page_size,String words);
    void searchReturn(SearchRoot root);
}
