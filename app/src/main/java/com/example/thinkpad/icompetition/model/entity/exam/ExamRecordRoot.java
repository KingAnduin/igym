package com.example.thinkpad.icompetition.model.entity.exam;

import java.util.List;

/**
 * Created by Hjg on 2018/11/26.
 */
public class ExamRecordRoot {


    private int code;
    private String msg;
    private List<ExamRecordItemBean> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<ExamRecordItemBean> getData() {
        return data;
    }

    public void setData(List<ExamRecordItemBean> data) {
        this.data = data;
    }

}
