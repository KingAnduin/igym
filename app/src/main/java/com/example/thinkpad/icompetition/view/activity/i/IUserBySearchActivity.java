package com.example.thinkpad.icompetition.view.activity.i;

import com.example.thinkpad.icompetition.model.entity.search.IsConcernRoot;

public interface IUserBySearchActivity extends IBaseActivity{
    void getConcernReturn(IsConcernRoot root);
    void getAddConcernReturn(IsConcernRoot root);
    void getDeleteConcernReturn(IsConcernRoot root);
}
