package com.example.thinkpad.icompetition.view.activity.impl;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.thinkpad.icompetition.R;
import com.example.thinkpad.icompetition.model.entity.search.CompetitionsBean;
import com.example.thinkpad.icompetition.model.entity.search.SearchRoot;
import com.example.thinkpad.icompetition.model.entity.search.UsersBean;
import com.example.thinkpad.icompetition.presenter.impl.SearchActivityPresenter;
import com.example.thinkpad.icompetition.util.NetWorkHelper;
import com.example.thinkpad.icompetition.view.activity.i.ISearchActivity;
import com.example.thinkpad.icompetition.view.adapter.SearchActivityAdapter;
import com.example.thinkpad.icompetition.view.adapter.SearchResultAdapter;
import com.example.thinkpad.icompetition.view.fragment.impl.SearchCompetitionFragment;
import com.example.thinkpad.icompetition.view.fragment.impl.SearchUserFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by a'su's on 2018/7/12.
 */

public class SearchActivity extends BaseActivity<SearchActivityPresenter> implements View.OnClickListener,ISearchActivity {
    private ProgressDialog mProgressDialog;
    private LinearLayout mNotFoundLayout;
    private TabLayout mTablayout;
    private ViewPager mViewPager;
    private Toolbar mToolbar;
    private SearchCompetitionFragment mSearchCompetitionFragment;
    private SearchUserFragment mSearchUserFragment;
    private ArrayList<Fragment> fragments;
    private SearchActivityAdapter adapter;
    private boolean flag =false;//是否已经点击过搜索

    private ImageView mSearchBackIV;
    private EditText mSearchInputET;
    private TextView mSearchTV;
    private int page = 1;
    private int pageSize = 15;
    private String keyWords = null;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        findView();
        init();
        setListener();
    }

    private void init() {
        fragments = new ArrayList<>();
        mTablayout.setupWithViewPager(mViewPager);
        mSearchCompetitionFragment = new SearchCompetitionFragment();
        mSearchUserFragment = new SearchUserFragment();
        fragments.add(mSearchCompetitionFragment);
        fragments.add(mSearchUserFragment);
        adapter = new SearchActivityAdapter(getSupportFragmentManager(),fragments);
        mViewPager.setAdapter(adapter);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mTablayout.setVisibility(View.GONE);
        mViewPager.setVisibility(View.INVISIBLE);
        mNotFoundLayout.setVisibility(View.GONE);
        setSupportActionBar(mToolbar);
    }

    @Override
    protected SearchActivityPresenter getPresenter() {
        return new SearchActivityPresenter(this);
    }

    private void setListener() {
        mSearchBackIV.setOnClickListener(this);
        mSearchTV.setOnClickListener(this);
    }

    private void findView() {
        mTablayout=findViewById(R.id.tab_search);
        mViewPager=findViewById(R.id.vp_search);
        mSearchInputET=findViewById(R.id.et_search);
        mSearchTV=findViewById(R.id.tv_search);
        mSearchBackIV=findViewById(R.id.iv_search_back);
        mToolbar=findViewById(R.id.toolbar_search);
        mNotFoundLayout=findViewById(R.id.layout_not_found);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_search_back:
                finish();
                break;
            case R.id.tv_search:
                getSearchWords();
                break;
        }
    }

    public void setTablayoutVisible(final boolean isVisible){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(isVisible){
                    mTablayout.setVisibility(View.VISIBLE);
                    mViewPager.setVisibility(View.VISIBLE);
                    mNotFoundLayout.setVisibility(View.GONE);
                }
                else {
                    mTablayout.setVisibility(View.GONE);
                    mViewPager.setVisibility(View.GONE);
                    mNotFoundLayout.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    public void setFlag(boolean flag){
        this.flag=flag;
    }

    //得到搜索框里的文本
    private void getSearchWords() {
        if(!TextUtils.isEmpty(mSearchInputET.getText().toString())) {
            keyWords = mSearchInputET.getText().toString();
            page = 1;
            //清空原有list
            if(flag) {
                mSearchCompetitionFragment.clearList();
            }
            if(flag) {
                mSearchUserFragment.clearList();
            }
            //初始化页数，开始搜索
            searchInfor(page,pageSize,keyWords);
        }
        else {
            showSnackBar(mSearchInputET,"请输入搜索内容",getMainColor());
        }
    }

    private void searchInfor(int page, int pageSize, String keyWords) {
        if(NetWorkHelper.isNetworkAvailable(this)) {
            mSearchCompetitionFragment.searchInfor(page,pageSize,keyWords);
            mSearchUserFragment.searchInfor(page,pageSize,keyWords);
            showProgressBarDialog();
        }else {
            showSnackBar(mSearchInputET,getString(R.string.not_have_network),getMainColor());
        }
    }

    @Override
    public void failBecauseNotNetworkReturn(int code) {
        showSnackBar(mSearchInputET,getString(R.string.not_network),getMainColor());
    }

    /**
     * dismissDialog取消dialog
     */
    public void dismissDialog(){
        if (mProgressDialog == null){
            return;
        }
        if (mProgressDialog.isShowing()){
            mProgressDialog.dismiss();
        }
    }

    /**
     * 显示dialog
     */
    private void showProgressBarDialog(){
        try {
            if (mProgressDialog == null){
                mProgressDialog = ProgressDialog.show(this,"提示","搜索中...请稍后");
                mProgressDialog.setCancelable(true);
            }
            if (mProgressDialog.isShowing() || isFinishing()){
                return;
            }
            mProgressDialog.show();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
