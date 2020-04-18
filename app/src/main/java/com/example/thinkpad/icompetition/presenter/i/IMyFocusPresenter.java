package com.example.thinkpad.icompetition.presenter.i;

/**
 * Created By hjg on 2018/12/28
 */
public interface IMyFocusPresenter extends IBasePresenter{
    void queryByPage(int page, int pageSize, String userNum);
}
