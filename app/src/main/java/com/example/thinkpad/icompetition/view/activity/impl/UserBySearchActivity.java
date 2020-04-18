package com.example.thinkpad.icompetition.view.activity.impl;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.thinkpad.icompetition.IcompetitionApplication;
import com.example.thinkpad.icompetition.R;
import com.example.thinkpad.icompetition.model.entity.search.IsConcernRoot;
import com.example.thinkpad.icompetition.model.entity.search.UsersBean;
import com.example.thinkpad.icompetition.presenter.impl.UserBySearchPresenter;
import com.example.thinkpad.icompetition.util.NetWorkHelper;
import com.example.thinkpad.icompetition.util.ShowReturnLoginUtil;
import com.example.thinkpad.icompetition.view.activity.i.IUserBySearchActivity;
import com.example.thinkpad.icompetition.view.widget.AsyncImageView;

import io.rong.imageloader.core.DisplayImageOptions;
import io.rong.imageloader.core.ImageLoader;
import io.rong.imageloader.core.ImageLoaderConfiguration;
import io.rong.imageloader.core.display.FadeInBitmapDisplayer;

/**
 * created by a'su's
 */
public class UserBySearchActivity extends BaseActivity<UserBySearchPresenter> implements IUserBySearchActivity, View.OnClickListener {
    private ProgressBar mProgressBar;
    private DisplayImageOptions options;
    private ImageLoader imageLoader;
    private Toolbar mToolbar;
    private AsyncImageView mUserHeadImageAIV;
    private TextView mUserNameTV;
    private TextView mUserNumTV;
    private TextView mUserRoleTV;
    private TextView mUserSexTV;
    private Button mConcernBtn;
    private Intent intent;
    private UsersBean mUsersBean;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_by_search);
        intent = getIntent();
        mUsersBean = (UsersBean) intent.getSerializableExtra("user_bysearch");
        findView();
        setListener();
        initDisplayImageOptions();
        init();
    }

    private void setListener() {
        mConcernBtn.setOnClickListener(this);
    }

    public void initDisplayImageOptions() {
        this.imageLoader = ImageLoader.getInstance();
        this.imageLoader.init(ImageLoaderConfiguration.createDefault(this));
        this.options = new DisplayImageOptions.Builder()
                .showImageForEmptyUri(R.mipmap.no_user_head)
                .showImageOnFail(R.mipmap.no_user_head)
                .showImageOnLoading(R.mipmap.ic_news_listview_img_loading)
                .displayer(new FadeInBitmapDisplayer(300))
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .build();
    }

    //初始化加载数据
    private void init() {
//        mConcernBtn.setText(getResources().getString(R.string.concern_not_concern));
        mToolbar.setNavigationIcon(R.mipmap.back);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mToolbar.setTitle(R.string.other_user);
        mToolbar.setTitleTextColor(getResources().getColor(R.color.white));
        //填入数据
        if (mUsersBean != null) {
            if (!TextUtils.isEmpty(mUsersBean.getUser_name())) {
                mUserNameTV.setText(mUsersBean.getUser_name());
            }
            if (!TextUtils.isEmpty(String.valueOf(mUsersBean.getUser_num()))) {
                mUserNumTV.setText(String.valueOf(mUsersBean.getUser_num()));
            }
            if (!TextUtils.isEmpty(String.valueOf(mUsersBean.getUser_roleid()))) {
                int roleID = mUsersBean.getUser_roleid();
                //根据roleID填写角色权限名
                if(roleID==1){
                    mUserRoleTV.setText(R.string.role_ordinary_user);
                }
                if(roleID==2){
                    mUserRoleTV.setText(R.string.role_release_user);
                }
                if(roleID==3){
                    mUserRoleTV.setText(R.string.role_manage_user);
                }
            }
            if (!TextUtils.isEmpty(mUsersBean.getUser_sex())) {
                mUserSexTV.setText(mUsersBean.getUser_sex());
            }
            if (!TextUtils.isEmpty(mUsersBean.getUser_headimage())) {
                imageLoader.init(ImageLoaderConfiguration.createDefault(this));
                imageLoader.clearDiskCache();
                imageLoader.clearMemoryCache();
                imageLoader.displayImage(mUsersBean.getUser_headimage(),mUserHeadImageAIV,options);
            }
        }
        getIsConcern(String.valueOf(mUsersBean.getUser_num()));
    }

    //查看该用户是否被关注
    private void getIsConcern(String other_num) {
        mProgressBar.setVisibility(View.VISIBLE);
        if(NetWorkHelper.isNetworkAvailable(this)) {
            mConcernBtn.setClickable(false);
            mPresenter.getIsConcern(other_num);
        }else {
            showSnackBar(mConcernBtn,getString(R.string.not_have_network),getMainColor());
        }
    }

    private void findView() {
        mProgressBar = findViewById(R.id.progressbar_loading);
        mToolbar = findViewById(R.id.toolbar_main);
        mUserHeadImageAIV = findViewById(R.id.aiv_search_user_headimage_bysearch);
        mUserNameTV = findViewById(R.id.tv_user_name_bysearh);
        mUserNumTV = findViewById(R.id.tv_user_num_bysearch);
        mUserRoleTV = findViewById(R.id.tv_user_role_bysearch);
        mUserSexTV = findViewById(R.id.tv_user_sex_bysearch);
        mConcernBtn = findViewById(R.id.btn_user_concern);
    }

    @Override
    public void failBecauseNotNetworkReturn(int code) {
        //在这里加入一种特殊情况：用户未登录
        if(((IcompetitionApplication)getApplication()).getToken()==null||((IcompetitionApplication)getApplication()).getToken().equals("")){
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mConcernBtn.setClickable(true);
                    mProgressBar.setVisibility(View.GONE);
                    mConcernBtn.setText(getString(R.string.concern_not_concern));
                    mConcernBtn.setBackground(getResources().getDrawable(R.drawable.background_loginbt));
                    showSnackBar(mConcernBtn,"还未登录，暂时不能关注", getMainColor());
                }
            });
        } else {
            showSnackBar(mConcernBtn, getString(R.string.not_network), getMainColor());
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mConcernBtn.setClickable(true);
                }
            });
        }
    }

    @Override
    protected UserBySearchPresenter getPresenter() {
        return new UserBySearchPresenter(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_user_concern:
                if(((IcompetitionApplication)getApplication()).getToken()==null||((IcompetitionApplication)getApplication()).getToken().equals("")){
                    ShowReturnLoginUtil showReturnLoginUtil = new ShowReturnLoginUtil(this);
                    showReturnLoginUtil.show();//因为没有登陆所以回到登陆界面
                }else {
                    changeConcernState();
                }
                break;
        }
    }

    private void changeConcernState() {
        if(mConcernBtn.getText().equals(getString(R.string.concern_is_concern))){
            //取消关注
            deleteConcern(String.valueOf(mUsersBean.getUser_num()));
        }else {
            //增加关注
            addConcern(String.valueOf(mUsersBean.getUser_num()));
        }
    }

    private void deleteConcern(String other_num) {
        if(NetWorkHelper.isNetworkAvailable(this)) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mProgressBar.setVisibility(View.VISIBLE);
                    mConcernBtn.setText("");
                }
            });
            mConcernBtn.setClickable(false);
            mPresenter.deleteConcern(other_num);
        }else {
            showSnackBar(mConcernBtn,getString(R.string.not_have_network),getMainColor());
        }
    }

    private void addConcern(String other_num) {
        if(NetWorkHelper.isNetworkAvailable(this)) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mProgressBar.setVisibility(View.VISIBLE);
                    mConcernBtn.setText("");
                }
            });
            mConcernBtn.setClickable(false);
            mPresenter.addConcern(other_num);
        }else {
            showSnackBar(mConcernBtn,getString(R.string.not_have_network),getMainColor());
        }
    }

    @Override
    public void getConcernReturn(final IsConcernRoot root) {
        if(root.getCode()==200){
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mConcernBtn.setClickable(true);
                    mProgressBar.setVisibility(View.GONE);
                    if (root.getData().equals(getString(R.string.concern_is_concern))) {
                        mConcernBtn.setText(root.getData());
                        mConcernBtn.setBackground(getResources().getDrawable(R.drawable.background_is_concern));
                    }
                    else {
                        mConcernBtn.setText(getResources().getString(R.string.concern_not_concern));
                        mConcernBtn.setBackground(getResources().getDrawable(R.drawable.background_loginbt));
                    }
                }
            });
        }
    }

    @Override
    public void getAddConcernReturn(IsConcernRoot root) {
        if(root.getCode()==2){
            showSnackBar(mConcernBtn,root.getMsg(),getMainColor());
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mConcernBtn.setClickable(true);
                    mProgressBar.setVisibility(View.GONE);
                    mConcernBtn.setText(getString(R.string.concern_not_concern));
                    mConcernBtn.setBackground(getResources().getDrawable(R.drawable.background_loginbt));
                }
            });
        }
        if (root.getCode()==200){
            showSnackBar(mConcernBtn,getString(R.string.add_concern_success),getMainColor());
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mConcernBtn.setClickable(true);
                    mProgressBar.setVisibility(View.GONE);
                    mConcernBtn.setText(getString(R.string.concern_is_concern));
                    mConcernBtn.setBackground(getResources().getDrawable(R.drawable.background_is_concern));
                }
            });
        }
    }

    @Override
    public void getDeleteConcernReturn(IsConcernRoot root) {
        if (root.getCode()==200){
            showSnackBar(mConcernBtn,getString(R.string.delete_concern_success),getMainColor());
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mConcernBtn.setClickable(true);
                    mProgressBar.setVisibility(View.GONE);
                    mConcernBtn.setText(getString(R.string.concern_not_concern));
                    mConcernBtn.setBackground(getResources().getDrawable(R.drawable.background_loginbt));
                }
            });
        }
        if (root.getCode()==0){
            showSnackBar(mConcernBtn,getString(R.string.action_fail),getMainColor());
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mConcernBtn.setClickable(true);
                    mProgressBar.setVisibility(View.GONE);
                    mConcernBtn.setText(getString(R.string.concern_is_concern));
                    mConcernBtn.setBackground(getResources().getDrawable(R.drawable.background_is_concern));
                }
            });
        }
    }
}
