package com.example.thinkpad.icompetition.view.activity.impl;


import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.text.TextPaint;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.thinkpad.icompetition.IcompetitionApplication;
import com.example.thinkpad.icompetition.R;
import com.example.thinkpad.icompetition.model.entity.collection.CollectionRoot;
import com.example.thinkpad.icompetition.model.entity.collection.IsCollectionRoot;
import com.example.thinkpad.icompetition.model.entity.exam.ExamRecordItemBean;
import com.example.thinkpad.icompetition.model.entity.focus.MyFocusRoot;
import com.example.thinkpad.icompetition.model.entity.search.IsConcernRoot;
import com.example.thinkpad.icompetition.presenter.impl.CompetitionInfoPresenter;
import com.example.thinkpad.icompetition.util.NetWorkHelper;
import com.example.thinkpad.icompetition.util.ShowReturnLoginUtil;
import com.example.thinkpad.icompetition.view.activity.i.IBaseActivity;
import com.example.thinkpad.icompetition.view.activity.i.ICompetitionActivity;
import com.example.thinkpad.icompetition.view.widget.AsyncImageView;
import com.example.thinkpad.icompetition.view.widget.Share;

import java.util.List;

/**
 * Created by a'su's on 2018/7/12.
 * 竞赛详情界面
 */

public class CompetitionInfoActivity
        extends BaseActivity<CompetitionInfoPresenter>
        implements IBaseActivity ,ICompetitionActivity, View.OnClickListener {


    private ExamRecordItemBean mItemBean;               //竞赛信息
    private ImageView mExamPhotoIv;                    //比赛图片
    private TextView mTitleTv;                          //竞赛标题
    private TextView mOrganizerTv;                      //主办方
    private TextView mSignUpTimeTv;                     //报名时间
    private TextView mExamTimeTv;                       //比赛时间
    private TextView mUrlTv;                            //官方网址
    private TextView mPublishNameTv;                    //发布者昵称
    private AsyncImageView mPublishHeadIv;              //发布者头像
    private ImageView mCollectionIv;                    //收藏按钮
    private ImageView mAttentionIv;                     //关注按钮
    private ImageView mShareIv;                         //分享按钮
    private Button mGoToWebBtn;                         //前往官网报名按钮

    private Boolean mIsCollection = Boolean.FALSE;      //是否已经收藏
    private Boolean mIsAttention = Boolean.FALSE;       //是否已经关注

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_competition_infor);
        Intent intent = getIntent();
        mItemBean = (ExamRecordItemBean) intent.getSerializableExtra("item");
        initBar();
        findView();
        setViewListener();
        setDate();
    }

    private void initReturnLoginDialog() {
        ShowReturnLoginUtil showReturnLoginUtil = new ShowReturnLoginUtil(this);
        showReturnLoginUtil.show();
    }

    private void initBar() {
        Toolbar mToolbar = findViewById(R.id.toolbar_main);
        mToolbar.setTitle("竞赛信息");
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
        RelativeLayout mFatherRtl = findViewById(R.id.rtl_competition);
        mExamPhotoIv = findViewById(R.id.com_info_photo);
        mTitleTv = findViewById(R.id.com_info_title);
        mOrganizerTv = findViewById(R.id.com_info_organizer);
        mSignUpTimeTv = findViewById(R.id.com_info_time_sign_up);
        mExamTimeTv = findViewById(R.id.com_info_time_exam);
        mUrlTv = findViewById(R.id.com_info_url);
        mPublishNameTv = findViewById(R.id.com_info_publish_name);
        mPublishHeadIv = findViewById(R.id.com_info_publish_head);
        mCollectionIv = findViewById(R.id.com_info_collection);
        mShareIv = findViewById(R.id.com_info_share);
        mAttentionIv = findViewById(R.id.com_info_attention);
        mGoToWebBtn = findViewById(R.id.com_info_webView);
    }


    private void setViewListener() {
        mGoToWebBtn.setOnClickListener(this);
        mAttentionIv.setOnClickListener(this);
        mShareIv.setOnClickListener(this);
        mCollectionIv.setOnClickListener(this);
        mUrlTv.setOnClickListener(this);
    }

    private void setDate() {

        if(mItemBean != null){

            String title = mItemBean.getCom_title();
            String organizer = mItemBean.getCom_sponsor().substring(1, mItemBean.getCom_sponsor().length());
            organizer = organizer.replace(" ", "\n");
            String signUpStart = mItemBean.getCom_signupstart();
            String signUpEnd = mItemBean.getCom_signupend();
            String signUpTime = signUpStart + " -- " + signUpEnd;
            String examStart = mItemBean.getCom_starttime();
            String examEnd = mItemBean.getCom_endtime();
            String examTime = examStart + " -- " + examEnd;
            String url = mItemBean.getCom_url();
            String publishName = mItemBean.getCom_publisher();


            //加载图片
            String imageUrl = mItemBean.getCom_picture();
            if (!imageUrl.equals("")){
                Glide.with(this)
                        .load(imageUrl)
                        .skipMemoryCache(true)
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .centerCrop()
                        .into(mExamPhotoIv);
                Glide.with(this)
                        .load(imageUrl)
                        .skipMemoryCache(true)
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .centerCrop()
                        .into(mPublishHeadIv);
            }

            mTitleTv.setText(title);
            TextPaint tp1 = mTitleTv.getPaint();
            tp1.setFakeBoldText(true);
            mOrganizerTv.setText(organizer);
            mSignUpTimeTv.setText(signUpTime);
            mExamTimeTv.setText(examTime);
            mPublishNameTv.setText(publishName);
            mUrlTv.setText(url);

            if(!TextUtils.isEmpty(((IcompetitionApplication)getApplication()).getToken())){
                if(NetWorkHelper.isNetworkAvailable(this)) {
                    mPresenter.getIsCollection(mItemBean.getCom_id());
                    mPresenter.getIsFocus(mItemBean.getCom_publisher());
                }else{
                    showSnackBar(mAttentionIv,getString(R.string.not_have_network),getMainColor());
                }
            }else {
                changeIcon(1, Boolean.FALSE);
                changeIcon(2, Boolean.FALSE);
            }


        }else {
            showSnackBar(mAttentionIv, "信息显示错误", getMainColor());
            finish();
        }
    }

    @Override
    public void failBecauseNotNetworkReturn(int code) {
        showSnackBar(mAttentionIv,getResources().getString(R.string.not_network),getMainColor());
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){

            //关注
            case R.id.com_info_attention:
                if(!TextUtils.isEmpty(((IcompetitionApplication)getApplication()).getToken())){
                    if(mIsAttention){
                        if(NetWorkHelper.isNetworkAvailable(this)){
                            mPresenter.cancelFocus(mItemBean.getCom_publisher());
                        }else {
                            showSnackBar(mAttentionIv,getString(R.string.not_have_network),getMainColor());
                        }

                    }else {
                        if(NetWorkHelper.isNetworkAvailable(this)){
                            mPresenter.addFocus(mItemBean.getCom_publisher());
                        }else {
                            showSnackBar(mAttentionIv,getString(R.string.not_have_network),getMainColor());
                        }
                    }
                }else {
                    //因为没有登陆所以回到登陆界面
                    initReturnLoginDialog();
                }
                break;

            //收藏
            case R.id.com_info_collection:
                if(!TextUtils.isEmpty(((IcompetitionApplication)getApplication()).getToken())){
                    if(mIsCollection){
                        if(NetWorkHelper.isNetworkAvailable(this)){
                            mPresenter.cancelCollection(mItemBean.getCom_id());
                        }else {
                            showSnackBar(mAttentionIv,getString(R.string.not_have_network),getMainColor());
                        }

                    }else {
                        if(NetWorkHelper.isNetworkAvailable(this)){
                            mPresenter.addCollection("",mItemBean.getCom_id());
                        }else {
                            showSnackBar(mAttentionIv,getString(R.string.not_have_network),getMainColor());
                        }

                    }
                }else {
                    //因为没有登陆所以回到登陆界面
                    initReturnLoginDialog();
                }
                break;

            //分享
            case R.id.com_info_share:
                Share share = new Share(this);
                share.share(mUrlTv.getText().toString());
                break;

            case R.id.com_info_url:
                Intent intents = new Intent();
                intents.setData(Uri.parse(mUrlTv.getText().toString()));//Url 就是你要打开的网址
                intents.setAction(Intent.ACTION_VIEW);
                List<ResolveInfo> list = getPackageManager().queryIntentActivities(intents, 0);
                if(list.size() > 0){
                    startActivity(intents); //启动浏览器
                }else {
                    Toast.makeText(this, "未识别到浏览器", Toast.LENGTH_SHORT).show();
                }

                break;


                //WebView
            case R.id.com_info_webView:
                Intent intent = new Intent(this, CompetitionWebViewActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("url", mUrlTv.getText().toString());
                intent.putExtras(bundle);
                startActivity(intent);
                break;
        }
    }

    //获取是否收藏的回调
    @Override
    public void getIsCollectionResponse(IsCollectionRoot root) {
        if(root.getCode() == 200){
            if (root.getData().equals("True")){
                mIsCollection = Boolean.TRUE;
            }else {
                mIsCollection = Boolean.FALSE;
            }
            changeIcon(1, mIsCollection);
        }else {
            showSnackBar(mAttentionIv, getResources().getString(R.string.request_fail) + root.getMsg(), getMainColor());
        }
    }

    //收藏的回调
    @Override
    public void addCollectionResponse(CollectionRoot root) {
        if(root.getCode() == 200){
            Toast.makeText(this, "收藏成功", Toast.LENGTH_SHORT).show();
            mIsCollection = Boolean.TRUE;
            changeIcon(1, Boolean.TRUE);
        }else {
            showSnackBar(mAttentionIv, getResources().getString(R.string.request_fail) + root.getMsg(), getMainColor());
        }
    }

    //取消收藏的回调
    @Override
    public void cancelCollectionResponse(CollectionRoot root) {
        if(root.getCode() == 200){
            Toast.makeText(this, "已取消收藏", Toast.LENGTH_SHORT).show();
            mIsCollection = Boolean.FALSE;
            changeIcon(1, Boolean.FALSE);
        }else if(root.getCode() == 1){
            showSnackBar(mAttentionIv, "信息错误", getMainColor());
        }else{
            showSnackBar(mAttentionIv, getResources().getString(R.string.request_fail) + root.getMsg(), getMainColor());
        }
    }

    @Override
    public void addAttentionResponse(MyFocusRoot root) {
        if(root.getCode() == 200){
            mIsAttention = Boolean.TRUE;
            changeIcon(2, Boolean.TRUE);
            Toast.makeText(this, "关注成功", Toast.LENGTH_SHORT).show();
        }else {
            showSnackBar(mAttentionIv, getResources().getString(R.string.request_fail) + root.getMsg(), getMainColor());
        }
    }

    @Override
    public void cancelAttentionResponse(MyFocusRoot root) {
        if(root.getCode() == 200){
            Toast.makeText(this, "已取消关注", Toast.LENGTH_SHORT).show();
            mIsAttention = Boolean.FALSE;
            changeIcon(2, Boolean.FALSE);
        }else if(root.getCode() == 1){
            showSnackBar(mAttentionIv, "信息错误", getMainColor());
        }else{
            showSnackBar(mAttentionIv, getResources().getString(R.string.request_fail) + root.getMsg(), getMainColor());
        }
    }

    @Override
    public void getIsAttentionResponse(IsConcernRoot root) {
        if(root.getCode() == 200){

            if (root.getData().equals("已关注")){
                mIsAttention = Boolean.TRUE;
            }else {
                mIsAttention = Boolean.FALSE;
            }
            changeIcon(2, mIsAttention);
        }else {
            showSnackBar(mAttentionIv, getResources().getString(R.string.request_fail) + root.getMsg(), getMainColor());
        }
    }


    //动态改变图标
    public void changeIcon(int type, boolean status){
        if(type == 1){
            if(status){
                mCollectionIv.setImageResource(R.mipmap.collectioned);
            }else {
                mCollectionIv.setImageResource(R.mipmap.collection);
            }
        }
        else if(type == 2){
            if (status){
                mAttentionIv.setImageResource(R.mipmap.attentioned);
            }else {
                mAttentionIv.setImageResource(R.mipmap.attention);
            }
        }
    }
}
