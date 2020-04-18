package com.example.thinkpad.icompetition.view.activity.i;

import com.example.thinkpad.icompetition.model.entity.collection.CollectionRoot;
import com.example.thinkpad.icompetition.model.entity.collection.IsCollectionRoot;
import com.example.thinkpad.icompetition.model.entity.focus.MyFocusRoot;
import com.example.thinkpad.icompetition.model.entity.search.IsConcernRoot;

public interface ICompetitionActivity extends IBaseActivity{

    //获取是否收藏
    void getIsCollectionResponse(IsCollectionRoot root);

    //收藏
    void addCollectionResponse(CollectionRoot root);

    //取消收藏
    void cancelCollectionResponse(CollectionRoot root);

    //关注用户
    void addAttentionResponse(MyFocusRoot root);

    //取消关注
    void cancelAttentionResponse(MyFocusRoot root);

    //获取是否关注
    void getIsAttentionResponse(IsConcernRoot root);

}
