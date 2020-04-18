package com.example.thinkpad.icompetition.model.i;

public interface ICompetitionModel extends IBaseModel{
    void getIsCollection(String com_id);
    void addCollection(String user_num, String com_id);
    void cancelCollection(String com_id);

    void getIsFocus(String other_num);
    void addFocus(String other_num);
    void cancelFocus(String other_num);
}
