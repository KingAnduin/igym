package com.example.thinkpad.icompetition.model.impl;

import android.os.Handler;

import com.example.thinkpad.icompetition.model.entity.search.SearchRoot;
import com.example.thinkpad.icompetition.model.event.SearchEvent;
import com.example.thinkpad.icompetition.model.i.ISearchModel;
import com.example.thinkpad.icompetition.network.CallbackIntercept;
import com.example.thinkpad.icompetition.network.NetworkInterfaces;
import com.google.gson.Gson;

import okhttp3.Call;
import okhttp3.Callback;

public class SearchModel extends BaseModel implements ISearchModel {
    public SearchModel(Handler handler) {
        super(handler);
    }

    @Override
    protected NetworkInterfaces getNetworkInterface() {
        return new NetworkInterfaces();
    }
}
