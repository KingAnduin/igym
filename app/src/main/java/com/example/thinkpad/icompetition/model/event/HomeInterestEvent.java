package com.example.thinkpad.icompetition.model.event;

import com.example.thinkpad.icompetition.model.entity.exam.ExamRecordRoot;

public class HomeInterestEvent extends BaseEvent {
    public static final int GET_TYPE_OK = 201;
    public static final int GET_TYPE_FAIL = 200;


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
