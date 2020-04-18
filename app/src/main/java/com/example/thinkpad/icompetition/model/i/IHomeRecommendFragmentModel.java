package com.example.thinkpad.icompetition.model.i;

/**
 * Created by Hjg on 2018/11/26.
 */
public interface IHomeRecommendFragmentModel extends IBaseFragmentModel{

    //获取推荐
    void getRecommendItem(int page_no, int page_size);

}
