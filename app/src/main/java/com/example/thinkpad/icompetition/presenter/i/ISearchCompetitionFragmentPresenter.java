package com.example.thinkpad.icompetition.presenter.i;

import com.example.thinkpad.icompetition.model.entity.search.SearchRoot;

public interface ISearchCompetitionFragmentPresenter extends IBaseFragmentPresenter{
    void searchInfor(int page,int page_size,String words);
}
