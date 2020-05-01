package com.example.thinkpad.icompetition.view.activity.i;

import com.example.thinkpad.icompetition.model.entity.order.OrderRoot;

/**
 * Created By hjg on 2018/12/15
 */
public interface IMyOrderActivity extends IBaseActivity{
    void getCollectionInfo(String userNum, int page_no, int page_size);
    void queryByPageOrderResponse(OrderRoot root);
    void deleteOrder(int ID);
    void deleteResponse();
}
