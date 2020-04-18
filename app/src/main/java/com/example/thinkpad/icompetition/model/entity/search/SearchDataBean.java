package com.example.thinkpad.icompetition.model.entity.search;

import com.example.thinkpad.icompetition.model.entity.exam.ExamRecordItemBean;

import java.util.List;

public class SearchDataBean {
    private List<ExamRecordItemBean> competitions;
    private List<UsersBean> users;

    public List<ExamRecordItemBean> getCompetitions() {
        return competitions;
    }

    public void setCompetitions(List<ExamRecordItemBean> competitions) {
        this.competitions = competitions;
    }

    public List<UsersBean> getUsers() {
        return users;
    }

    public void setUsers(List<UsersBean> users) {
        this.users = users;
    }
}
