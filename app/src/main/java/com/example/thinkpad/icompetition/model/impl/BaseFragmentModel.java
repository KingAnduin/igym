package com.example.thinkpad.icompetition.model.impl;

import com.example.thinkpad.icompetition.model.event.BaseEvent;
import com.example.thinkpad.icompetition.network.NetworkInterfaces;


/**
 * 所有fragment的model需要继承的父类
 * 该类通过OnEventReceiveListener接口回调发送消息，与presenter和view层进行消息传递
 * <p>
 * Created by liao on 2017/4/21.
 */

public abstract class BaseFragmentModel<T extends BaseEvent> {
    NetworkInterfaces mNetworkInterface;
    private OnEventReceiveListener<T> mEventReceiveListener;

    public BaseFragmentModel(OnEventReceiveListener<T> eventReceiveListener) {
        mNetworkInterface = getNetworkInterface();
        this.mEventReceiveListener = eventReceiveListener;
    }

    /**
     * 回调接口传递消息
     *
     * @param event 需要传递的消息
     */
    void postEvent(T event) {
        mEventReceiveListener.eventReceive(event, event.getWhat());
    }

    /**
     * 获取网络接口,已在构造函数调用,只需要复写,不需要调用
     *
     * @return 网络接口
     */
    protected abstract NetworkInterfaces getNetworkInterface();

    public interface OnEventReceiveListener<Y extends BaseEvent> {
        public void eventReceive(Y event, int what);
    }
}
