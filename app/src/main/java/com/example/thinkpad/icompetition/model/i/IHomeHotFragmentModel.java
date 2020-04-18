package com.example.thinkpad.icompetition.model.i;

/**
 * Created By hjg on 2018/11/29
 */
public interface IHomeHotFragmentModel extends IBaseFragmentModel{
    //获取热门
    void getHotItem(int page_no, int page_size);
}
