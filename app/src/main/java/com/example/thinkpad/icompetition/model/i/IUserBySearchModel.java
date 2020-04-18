package com.example.thinkpad.icompetition.model.i;

public interface IUserBySearchModel extends IBaseModel{
    void getIsConcern(String other_num);
    void addConcern(String other_num);
    void deleteConcern(String other_num);
}
