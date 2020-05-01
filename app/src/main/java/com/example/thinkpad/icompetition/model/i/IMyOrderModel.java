package com.example.thinkpad.icompetition.model.i;

/**
 * Created By hjg on 2018/12/15
 */
public interface IMyOrderModel extends IBaseModel {
    void queryByPageOrder(String userNum, int page, int pageSize);
    void deleteOrder(int order_id);
}
