package com.example.thinkpad.icompetition.model.event;

import com.example.thinkpad.icompetition.model.entity.focus.MyFocusRoot;
import com.example.thinkpad.icompetition.model.entity.search.IsConcernRoot;

/**
 * 用户关注
 */

public class FocusEvent extends BaseEvent{

    public static final int GET_ISCONCERN_OK = 105;
    public static final int GET_ISCONCERN_FAIL = -105;

    public static final int ADD_CONCERN_OK = 106;
    public static final int ADD_CONCERN_FAIL = -106;

    public static final int DELETE_CONCERN_OK = 107;
    public static final int DELETE_CONCERN_FAIL = -107;

    public static final int QUERY_FOCUS_OK = 108;
    public static final int QUERY_FOCUS_FAIL = -108;

    private IsConcernRoot root;

    private MyFocusRoot focusRoot;

    public MyFocusRoot getMyFocusRoot() {
        return focusRoot;
    }

    public void setMyFocusRoot(MyFocusRoot focusRoot) {
        this.focusRoot = focusRoot;
    }


    public IsConcernRoot getRoot() {
        return root;
    }

    public void setRoot(IsConcernRoot root) {
        this.root = root;
    }

}
