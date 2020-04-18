package com.example.thinkpad.icompetition.view.activity.i;

import com.example.thinkpad.icompetition.model.entity.Interest.Interestroot;

public interface IInterstsSelectActivity extends IBaseActivity{
    void getUserInterestReturn(Interestroot root);
    void addInterestReturn(Interestroot root);
    void deleteInterestReturn(Interestroot root);
    void getInterestTypeReturn(Interestroot root);
}
