package com.example.thinkpad.icompetition.model.impl;

import android.os.Handler;

import com.example.thinkpad.icompetition.model.i.IBaseModel;
import com.example.thinkpad.icompetition.model.i.IUserInforModel;
import com.example.thinkpad.icompetition.network.NetworkInterfaces;

public class UerInforModel extends BaseModel implements IBaseModel,IUserInforModel {
    public UerInforModel(Handler handler) {
        super(handler);
    }

    @Override
    protected NetworkInterfaces getNetworkInterface() {
        return new NetworkInterfaces();
    }
}
