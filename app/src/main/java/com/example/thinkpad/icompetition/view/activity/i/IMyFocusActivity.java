package com.example.thinkpad.icompetition.view.activity.i;

import com.example.thinkpad.icompetition.model.entity.focus.MyFocusRoot;

/**
 * Created By hjg on 2018/12/28
 */
public interface IMyFocusActivity extends IBaseActivity{
    void queryByPageFocusResponse(MyFocusRoot root);
    void getFocusInfo(int page_no, int page_size, String userNum);
}
