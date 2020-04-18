package com.example.thinkpad.icompetition.model.event;

import com.example.thinkpad.icompetition.model.entity.Interest.Interestroot;

public class InterestEvent extends BaseEvent{
    public static final int INTERESTS_TYPE_OK = 100;
    public static final int INTERESTS_TYPE_FAIL = -100;
    public static final int INTERESTS_USER_OK = 101;
    public static final int INTERESTS_USER_FAIL = -101;
    public static final int INTERESTS_ADD_OK = 102;
    public static final int INTERESTS_ADD_FAIL = -102;
    public static final int INTERESTS_DELETE_OK = 103;
    public static final int INTERESTS_DELETE_FAIL = -103;
    private Interestroot interestroot;
    public Interestroot getInterestroot() {
        return interestroot;
    }

    public void setInterestroot(Interestroot interestroot) {
        this.interestroot = interestroot;
    }
}
