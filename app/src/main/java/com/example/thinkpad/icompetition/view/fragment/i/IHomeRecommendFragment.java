package com.example.thinkpad.icompetition.view.fragment.i;

import com.example.thinkpad.icompetition.model.entity.exam.ExamRecordRoot;

/**
 * Created by Hjg on 2018/11/26.
 */
public interface IHomeRecommendFragment extends IBaseFragment{
    void getExamInfo(int page_no, int page_size);
    void PagingQueryHomeRecommendResponse(ExamRecordRoot root);
}
