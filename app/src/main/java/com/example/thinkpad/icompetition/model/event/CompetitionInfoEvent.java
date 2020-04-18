package com.example.thinkpad.icompetition.model.event;

import com.example.thinkpad.icompetition.model.entity.collection.CollectionRoot;
import com.example.thinkpad.icompetition.model.entity.collection.IsCollectionRoot;
import com.example.thinkpad.icompetition.model.entity.exam.ExamRecordRoot;

/**
 * Created By hjg on 2018/12/13
 */
public class CompetitionInfoEvent extends BaseEvent{
    //收藏   添加、删除、判断
    public static final int COLLECTION_ADD_OK = 101;
    public static final int COLLECTION_ADD_FAIL = 102;
    public static final int COLLECTION_CANCEL_OK = 201;
    public static final int COLLECTION_CANCEL_FAIL = 202;
    public static final int COLLECTION_IS_OK = 301;
    public static final int COLLECTION_IS_FAIL = 302;
    public static final int QUERY_COLLECTION_OK = 401;
    public static final int QUERY_COLLECTION_FAIL = 402;
    private String message = "";
    private IsCollectionRoot isCollectionRoot;
    private CollectionRoot root;
    private ExamRecordRoot examRecordRoot;


    public ExamRecordRoot getExamRecordRoot() {
        return examRecordRoot;
    }

    public void setExamRecordRoot(ExamRecordRoot examRecordRoot) {
        this.examRecordRoot = examRecordRoot;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public IsCollectionRoot getIsCollectionRoot() {
        return isCollectionRoot;
    }

    public void setIsCollectionRoot(IsCollectionRoot isCollectionRoot) {
        this.isCollectionRoot = isCollectionRoot;
    }

    public CollectionRoot getRoot() {
        return root;
    }

    public void setRoot(CollectionRoot root) {
        this.root = root;
    }



}
