package com.example.thinkpad.icompetition.presenter.i;

public interface IInterstsSelectActivityPresenter extends IBasePresenter{
    void getUserInterest(int page,int pageSize);
    void addInterest(String typename);
    void deleteInterest(String typename);
    void getInterestType();
}
