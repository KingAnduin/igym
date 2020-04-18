package com.example.thinkpad.icompetition.view.fragment.i;

import com.example.thinkpad.icompetition.model.entity.exam.ExamRecordRoot;

/**
 * Created By hjg on 2018/11/29
 */
public interface IHomeHotsFragment extends IBaseFragment{
    void getExamInfo(int page_no, int page_size);
    void pagingQueryHomeHotsResponse(ExamRecordRoot root);
}
