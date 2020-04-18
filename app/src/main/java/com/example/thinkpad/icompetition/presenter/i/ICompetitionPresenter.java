package com.example.thinkpad.icompetition.presenter.i;

public interface ICompetitionPresenter {

    void getIsCollection(String com_id);

    void addCollection(String user_num, String com_id);

    void cancelCollection(String com_id);

    void getIsFocus(String num);

    void addFocus(String num);

    void cancelFocus(String num);
}
