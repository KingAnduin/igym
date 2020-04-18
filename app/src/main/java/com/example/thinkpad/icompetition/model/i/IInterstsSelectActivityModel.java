package com.example.thinkpad.icompetition.model.i;

public interface IInterstsSelectActivityModel extends IBaseModel {
    void getUserInterest(int page,int pageSize);
    void addInterest(String typename);
    void deleteInterest(String typename);
    void getInterestType();
}
