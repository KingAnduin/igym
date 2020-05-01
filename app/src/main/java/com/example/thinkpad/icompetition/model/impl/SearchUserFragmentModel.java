package com.example.thinkpad.icompetition.model.impl;

import com.example.thinkpad.icompetition.model.entity.search.SearchRoot;
import com.example.thinkpad.icompetition.model.event.SearchEvent;
import com.example.thinkpad.icompetition.model.i.ISearchUserFragmentModel;
import com.example.thinkpad.icompetition.network.CallbackIntercept;
import com.example.thinkpad.icompetition.network.NetworkInterfaces;
import com.google.gson.Gson;

import okhttp3.Call;
import okhttp3.Callback;

public class SearchUserFragmentModel extends BaseFragmentModel<SearchEvent> implements ISearchUserFragmentModel {
    public SearchUserFragmentModel(OnEventReceiveListener<SearchEvent> eventReceiveListener) {
        super(eventReceiveListener);
    }

    @Override
    protected NetworkInterfaces getNetworkInterface() {
        return new NetworkInterfaces();
    }

    @Override
    public void searchInfor(int page, int page_size, String words) {
        Callback callback = new CallbackIntercept() {
            @Override
            public void onSuccess(Call call, String jsonBody) {
                Gson gson = new Gson();
                SearchRoot root = gson.fromJson(jsonBody,SearchRoot.class);
                SearchEvent event = new SearchEvent();
                if(root!=null){
                    event.setRoot(root);
                    event.setWhat(SearchEvent.SEARCH_OK);
                }
                else {
                    event.setWhat(SearchEvent.SEARCH_FAIL);
                }
                postEvent(event);
            }

            @Override
            public void onFail(Call call, Exception e) {
                SearchEvent event = new SearchEvent();
                event.setWhat(SearchEvent.SEARCH_FAIL);
                postEvent(event);
            }
        };
     }
}
