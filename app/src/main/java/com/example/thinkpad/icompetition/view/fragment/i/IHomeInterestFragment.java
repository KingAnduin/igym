package com.example.thinkpad.icompetition.view.fragment.i;

import com.example.thinkpad.icompetition.model.entity.exam.ExamRecordRoot;
import com.example.thinkpad.icompetition.presenter.i.IBaseFragmentPresenter;

public interface IHomeInterestFragment extends IBaseFragment {
    //类型
    void getTypeInfo(int page_no, int page_size, String type);
    //分页查询的回调
    void PagingQueryHomeInterestResponse(ExamRecordRoot root);
}
