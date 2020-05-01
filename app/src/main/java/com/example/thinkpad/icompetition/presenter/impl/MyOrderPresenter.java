package com.example.thinkpad.icompetition.presenter.impl;

import android.os.Handler;
import android.os.Message;

import com.example.thinkpad.icompetition.model.entity.order.OrderRoot;
import com.example.thinkpad.icompetition.model.event.OrderInfoEvent;
import com.example.thinkpad.icompetition.model.impl.MyOrderModel;
import com.example.thinkpad.icompetition.presenter.i.IBasePresenter;
import com.example.thinkpad.icompetition.presenter.i.IMyOrderPresenter;
import com.example.thinkpad.icompetition.view.activity.impl.MyOrderActivity;

/**
 * Created By hjg on 2018/12/15
 */
public class MyOrderPresenter
        extends BasePresenter<MyOrderActivity, MyOrderModel>
        implements IMyOrderPresenter, IBasePresenter {

    public MyOrderPresenter(MyOrderActivity view) {
        super(view);
    }

    @Override
    public void queryByPage(String userNum, int page, int pageSize) {
        mModel.queryByPageOrder(userNum, page, pageSize);
    }

    @Override
    public void deleteOrder(int order_id) {
        mModel.deleteOrder(order_id);
    }

    @Override
    protected MyOrderModel getModel(Handler handler) {
        return new MyOrderModel(handler);
    }

    @Override
    protected void eventReceive(Message msg) {
        switch (msg.what){
            case OrderInfoEvent.QUERY_USER_ORDER_OK:
                OrderRoot root = ((OrderInfoEvent)msg.obj).getOrderRoot();
                mView.queryByPageOrderResponse(root);
                break;
            case OrderInfoEvent.QUERY_USER_ORDER_FAIL:
            case OrderInfoEvent.DELETE_USER_ORDER_FAIL:
                mView.failBecauseNotNetworkReturn(msg.what);
                break;
            case OrderInfoEvent.DELETE_USER_ORDER_OK:
                mView.deleteResponse();
                break;
        }
    }
}
