package com.example.thinkpad.icompetition.model.i;

public interface IHomeInterestModel extends IBaseFragmentModel{
    //获取推荐
    void getTypeItem(int page_no, int page_size, String type);

}
