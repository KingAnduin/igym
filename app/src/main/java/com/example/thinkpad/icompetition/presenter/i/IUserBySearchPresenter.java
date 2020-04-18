package com.example.thinkpad.icompetition.presenter.i;

public interface IUserBySearchPresenter extends IBasePresenter{
    void getIsConcern(String other_num);
    void addConcern(String other_num);
    void deleteConcern(String other_num);
}
