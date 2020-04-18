package com.example.thinkpad.icompetition.model.i;

/**
 * Created By hjg on 2018/12/15
 */
public interface IMyCollectionModel extends IBaseModel {
    void queryByPageCollection(String userNum, int page, int pageSize);
}
