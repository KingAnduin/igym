package com.example.thinkpad.icompetition.model.event;

import com.example.thinkpad.icompetition.model.entity.NormalRoot;
import com.example.thinkpad.icompetition.model.entity.order.OrderRoot;

/**
 * Created By hjg on 2018/12/13
 */
public class OrderInfoEvent extends BaseEvent{

    public static final int QUERY_USER_ORDER_OK = 401;
    public static final int QUERY_USER_ORDER_FAIL = 402;
    public static final int DELETE_USER_ORDER_OK = 201;
    public static final int DELETE_USER_ORDER_FAIL = 202;

    private String message = "";
    private OrderRoot orderRoot;
    private NormalRoot normalRoot;

    public OrderRoot getOrderRoot() {
        return orderRoot;
    }

    public void setOrderRoot(OrderRoot orderRoot) {
        this.orderRoot = orderRoot;
    }

    public NormalRoot getNormalRoot() {
        return normalRoot;
    }

    public void setNormalRoot(NormalRoot normalRoot) {
        this.normalRoot = normalRoot;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
