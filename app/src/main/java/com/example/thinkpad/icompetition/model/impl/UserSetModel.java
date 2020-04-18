package com.example.thinkpad.icompetition.model.impl;

import android.os.Handler;

import com.example.thinkpad.icompetition.model.i.IBaseModel;
import com.example.thinkpad.icompetition.network.NetworkInterfaces;

public class UserSetModel extends BaseModel implements IBaseModel {
    public UserSetModel(Handler handler) {
        super(handler);
    }

    @Override
    protected NetworkInterfaces getNetworkInterface() {
        return new NetworkInterfaces();
    }
}
