package com.example.thinkpad.icompetition.view.activity.impl;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.example.thinkpad.icompetition.IcompetitionApplication;
import com.example.thinkpad.icompetition.R;
import com.example.thinkpad.icompetition.model.entity.focus.MyFocusItemRoot;
import com.example.thinkpad.icompetition.model.entity.focus.MyFocusRoot;
import com.example.thinkpad.icompetition.model.entity.search.UsersBean;
import com.example.thinkpad.icompetition.model.entity.user.UserInforBean;
import com.example.thinkpad.icompetition.presenter.impl.MyFocusPresenter;
import com.example.thinkpad.icompetition.util.NetWorkHelper;
import com.example.thinkpad.icompetition.view.activity.i.IBaseActivity;
import com.example.thinkpad.icompetition.view.activity.i.IMyFocusActivity;
import com.example.thinkpad.icompetition.view.adapter.SearchResultAdapter;

import java.util.ArrayList;
import java.util.List;

import greendao.gen.DaoSession;
import greendao.gen.UserInforBeanDao;

/**
 * Created By hjg on 2018/12/28
 * 用户关注界面
 */
public class MyFocusActivity
        extends BaseActivity<MyFocusPresenter>
        implements IBaseActivity, IMyFocusActivity {

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;
    private Toolbar mToolbar;
    private TextView mTitleTv;
    private int mRecyclerViewCurrentY = 0;
    private int mRecyclerViewCurrentX = 0;


    private Handler mHandler = new Handler();
    List<UsersBean> mInfo;                                  //详细信息
    private int mCurrentPage = 1;                           //页数
    private final int page_size = 10;                       //每页信息数
    private boolean mNoMoreData = false;
    private DaoSession mDaoSession;
    private UserInforBean mUserBean;                        //用户信息
    private SearchResultAdapter mAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_me_focus);

        init();
        initView();
        setViewListener();
    }

    @Override
    protected void onStart() {
        super.onStart();
        refreshData();
    }


    public void init(){
        mDaoSession=((IcompetitionApplication)getApplication()).getDaoSession();
        UserInforBeanDao userInforBeanDao = mDaoSession.getUserInforBeanDao();
        List<UserInforBean> list = userInforBeanDao.loadAll();
        if(list.get(0)!=null) {
            mUserBean = list.get(0);
        }
    }

    public void initView(){
        mRecyclerView = findViewById(R.id.rc_me_focus_list);
        mToolbar = findViewById(R.id.toolbar_main);
        mTitleTv = findViewById(R.id.tv_toolbar_title);
        mTitleTv.setText(R.string.my_focus_title);
        mSwipeRefreshLayout = findViewById(R.id.srl_me_focus_list);
        mSwipeRefreshLayout.setColorSchemeColors(Color.BLUE, Color.GREEN, Color.RED);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        mToolbar.setNavigationIcon(R.mipmap.back);

        setSupportActionBar(mToolbar);
    }

    public void setViewListener() {
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshData();
            }
        });
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (recyclerView.computeVerticalScrollExtent() + recyclerView.computeVerticalScrollOffset()
                        >= recyclerView.computeVerticalScrollRange()) {
                    //滑动到了底部 避免重复请求
                    if (!mNoMoreData) {
                        mRecyclerViewCurrentY = dy;
                        mRecyclerViewCurrentX = dx;
                        //加载更多
                        getFocusInfo( ++mCurrentPage, page_size, String.valueOf(mUserBean.getContact_phone()));
                    }
                }
            }
        });
    }


    @Override
    protected MyFocusPresenter getPresenter() {
        return new MyFocusPresenter(this);
    }

    @Override
    public void queryByPageFocusResponse(final MyFocusRoot root) {
        if(root != null && root.getCode() == 200){
            if(root.getData() != null){
                if(mInfo == null){
                    mInfo = new ArrayList<>();
                }
                mInfo.addAll(root.getData());

                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (mSwipeRefreshLayout.isRefreshing()) {
                            mSwipeRefreshLayout.setRefreshing(false);
                        }
                        if (mAdapter == null) {
                            mAdapter = new SearchResultAdapter(getApplicationContext(), mInfo, 1);
                            if (root.getData().size() < page_size) {
                                //显示没有更多
                                mNoMoreData = true;
                                mAdapter.deleteLoadingMore(true);
                            }
                            mRecyclerView.setAdapter(mAdapter);
                        } else {
                            //更新数据
                            mAdapter.updateData(mInfo);
                            if (mInfo.size() < page_size) {
                                //显示没有更多
                                mNoMoreData = true;
                                mAdapter.deleteLoadingMore(true);
                            }
                            mAdapter.notifyDataSetChanged();
                            mRecyclerView.scrollTo(mRecyclerViewCurrentX, mRecyclerViewCurrentY);
                        }
                        //设置点击事件
                        gotoItem();
                    }


                });

            }else {
                noMoreData();
            }
        } else {
            showSnackBar(mSwipeRefreshLayout, getResources().getString(R.string.request_fail), getMainColor());
        }
    }

    @Override
    public void getFocusInfo(int page_no, int page_size, String userNum) {
        if(NetWorkHelper.isNetworkAvailable(this)){
            mPresenter.queryByPage(page_no, page_size, userNum);
        }else{
            showSnackBar(mRecyclerView,getString(R.string.not_have_network),getMainColor());
        }

    }

    @Override
    public void failBecauseNotNetworkReturn(int code) {
        showSnackBar(mSwipeRefreshLayout,"服务器异常，稍后重试 " + code, getMainColor());
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(false);
                if (mInfo == null || mInfo.size() == 0) {
                    mRecyclerView.setVisibility(View.GONE);
                }
            }
        });
    }


    /**
     * 刷新数据
     */
    public void refreshData() {
        mNoMoreData = false;
        mCurrentPage = 1;
        mInfo = null;
        if (mAdapter != null) {
            mAdapter.deleteLoadingMore(false);
        }
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(true);
            }
        });
        getFocusInfo( mCurrentPage, page_size, String.valueOf(mUserBean.getContact_phone()));
    }

    /**
     * 设置没有更多数据
     */
    public void noMoreData() {
        mNoMoreData = true;
        mInfo = new ArrayList<>();
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                mAdapter = new SearchResultAdapter(getApplicationContext(), mInfo, 1);
                mAdapter.deleteLoadingMore(true);
                mAdapter.notifyDataSetChanged();
                mRecyclerView.setAdapter(mAdapter);
                mRecyclerView.scrollTo(mRecyclerViewCurrentX, mRecyclerViewCurrentY);
            }
        });
    }

    /**
     * 跳转界面
     */
    private void gotoItem() {
        mAdapter.setItemClickListener(new SearchResultAdapter.docItemClickListener() {
            @Override
            public void onClick(int position) {
                Intent intent = new Intent(getApplicationContext(), UserBySearchActivity.class);
                intent.putExtra("user_bysearch", mInfo.get(position));
                startActivity(intent);
            }
        });
    }
}
