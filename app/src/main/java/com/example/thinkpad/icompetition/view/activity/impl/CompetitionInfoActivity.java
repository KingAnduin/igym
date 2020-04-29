package com.example.thinkpad.icompetition.view.activity.impl;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.text.TextPaint;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.thinkpad.icompetition.R;
import com.example.thinkpad.icompetition.model.entity.news.NewsItem;
import com.example.thinkpad.icompetition.presenter.impl.CompetitionInfoPresenter;
import com.example.thinkpad.icompetition.view.activity.i.IBaseActivity;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;


/**
 * Created by a'su's on 2018/7/12.
 * 竞赛详情界面
 */

public class CompetitionInfoActivity
        extends BaseActivity<CompetitionInfoPresenter>
        implements IBaseActivity  {

    private JCVideoPlayerStandard mJcVideoPlayerStandard;       //播放器
    private NewsItem mItemBean;                         //信息
    private TextView mTitleTv;                          //标题
    private TextView mTypeTv;                           //类型
    private TextView mLevelTv;                          //级别
    private TextView mMajorMuscleTv;                    //主要肌肉群
    private TextView mOtherMuscleTv;                    //其他肌肉
    private TextView mEquipmentTv;                      //器械要求
    private TextView mPointTv;                          //动作要领

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_competition_infor);
        Intent intent = getIntent();
        mItemBean = (NewsItem) intent.getSerializableExtra("item");
        initBar();
        findView();
        setDate();
    }

    @Override
    public void onBackPressed() {
        if (JCVideoPlayer.backPress()) {
            return;
        }
        super.onBackPressed();
    }
    @Override
    protected void onPause() {
        super.onPause();
        JCVideoPlayer.releaseAllVideos();
    }


    private void initBar() {
        Toolbar mToolbar = findViewById(R.id.toolbar_main);
        mToolbar.setTitle("健身推荐");
        mToolbar.setNavigationIcon(R.mipmap.back);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CompetitionInfoActivity.this.finish();
            }
        });
    }


    @Override
    protected CompetitionInfoPresenter getPresenter() {
        return new CompetitionInfoPresenter(this);
    }


    private void findView() {
        mTitleTv = findViewById(R.id.news_item_title);
        mTypeTv = findViewById(R.id.news_item_type);
        mLevelTv = findViewById(R.id.news_item_level);
        mMajorMuscleTv = findViewById(R.id.news_item_major_muscle);
        mOtherMuscleTv = findViewById(R.id.news_item_other_muscle);
        mEquipmentTv = findViewById(R.id.news_item_equipment);
        mPointTv = findViewById(R.id.news_item_point);
        mJcVideoPlayerStandard= findViewById(R.id.news_item_gif);
    }

    private void setDate() {

        if(mItemBean != null){

            String image_str = mItemBean.getNews_image();
            String video_str = mItemBean.getNews_gif();
            String video_name_str = mItemBean.getNews_title();
            //添加视频缩略图
            ImageView thumbImageView = mJcVideoPlayerStandard.thumbImageView;
            //使用Glide添加
            Glide.with(this).load(image_str).into(thumbImageView );
            //加载视频
            mJcVideoPlayerStandard.setUp(video_str, JCVideoPlayerStandard.SCREEN_LAYOUT_NORMAL, video_name_str);

            mTitleTv.setText(mItemBean.getNews_title());
            TextPaint tp1 = mTitleTv.getPaint();
            tp1.setFakeBoldText(true);
            mTypeTv.setText(mItemBean.getNews_type());
            mLevelTv.setText(mItemBean.getNews_level());
            mMajorMuscleTv.setText(mItemBean.getNews_major_muscle());
            mOtherMuscleTv.setText(mItemBean.getNews_other_muscle());
            mEquipmentTv.setText(mItemBean.getNews_equipment());
            mPointTv.setText(mItemBean.getNews_essentials());

        }else {
            showSnackBar(mLevelTv, "信息显示错误", getMainColor());
            finish();
        }
    }

    @Override
    public void failBecauseNotNetworkReturn(int code) {
        showSnackBar(mLevelTv,getResources().getString(R.string.not_network),getMainColor());
    }

}
