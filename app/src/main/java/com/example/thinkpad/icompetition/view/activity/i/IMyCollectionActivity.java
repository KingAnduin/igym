package com.example.thinkpad.icompetition.view.activity.i;

import com.example.thinkpad.icompetition.model.entity.exam.ExamRecordRoot;

/**
 * Created By hjg on 2018/12/15
 */
public interface IMyCollectionActivity extends IBaseActivity{
    void getCollectionInfo(String userNum, int page_no, int page_size);
    void queryByPageCollectionResponse(ExamRecordRoot root);
}
