package com.example.thinkpad.icompetition.model.event;

import com.example.thinkpad.icompetition.model.entity.search.SearchRoot;

public class SearchEvent extends BaseEvent {
    public static final int SEARCH_OK = 200;
    public static final int SEARCH_FAIL=-200;
    private SearchRoot root;
    public SearchRoot getRoot() {
        return root;
    }

    public void setRoot(SearchRoot root) {
        this.root = root;
    }
}
