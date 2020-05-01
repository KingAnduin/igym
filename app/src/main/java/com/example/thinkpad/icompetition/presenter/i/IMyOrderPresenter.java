package com.example.thinkpad.icompetition.presenter.i;

/**
 * Created By hjg on 2018/12/15
 */
public interface IMyOrderPresenter extends IBasePresenter{
    void queryByPage(String userNum, int page, int pageSize);
    void deleteOrder(int order_id);
}
