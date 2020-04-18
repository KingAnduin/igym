package com.example.thinkpad.icompetition.model.event;

import com.example.thinkpad.icompetition.model.entity.exam.ExamRecordRoot;

/**
 * Created By hjg on 2018/11/29
 */
public class HomeHotsEvent extends BaseEvent {
    public static final int GET_HOTS_OK = 301;
    public static final int GET_HOTS_FAIL = 300;


    private static String ErrorMsg;
    private ExamRecordRoot root;

    public void setErrorMsg(String errorMsg) {
        ErrorMsg = errorMsg;
    }

    public ExamRecordRoot getRoot() {
        return root;
    }

    public void setRoot(ExamRecordRoot root){
        this.root = root;
    }
}
