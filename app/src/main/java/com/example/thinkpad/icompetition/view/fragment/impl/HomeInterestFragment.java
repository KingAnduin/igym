package com.example.thinkpad.icompetition.view.fragment.impl;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.thinkpad.icompetition.IcompetitionApplication;
import com.example.thinkpad.icompetition.R;
import com.example.thinkpad.icompetition.model.entity.exam.ExamRecordItemBean;
import com.example.thinkpad.icompetition.model.entity.exam.ExamRecordRoot;
import com.example.thinkpad.icompetition.model.entity.user.UserInforBean;
import com.example.thinkpad.icompetition.util.NetWorkHelper;
import com.example.thinkpad.icompetition.view.activity.i.IBaseActivity;
import com.example.thinkpad.icompetition.view.activity.impl.CompetitionInfoActivity;
import com.example.thinkpad.icompetition.view.adapter.HomeInterestAdapter;
import com.example.thinkpad.icompetition.view.fragment.i.IHomeInterestFragment;
import com.example.thinkpad.icompetition.presenter.impl.HomeInterestFragmentPresenter;

import java.util.ArrayList;
import java.util.List;

import greendao.gen.DaoSession;
import greendao.gen.UserInforBeanDao;

import static com.example.thinkpad.icompetition.IcompetitionApplication.getApplication;


public class HomeInterestFragment
        extends BaseFragment<HomeInterestFragmentPresenter>
        implements IBaseActivity, IHomeInterestFragment {
    private View rootView;
    private HomeInterestAdapter mAdapter = null;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private LinearLayout notFindLl;
    private RecyclerView mRecyclerView;
    private int mRecyclerViewCurrentY = 0;
    private int mRecyclerViewCurrentX = 0;

    private Handler mHandler = new Handler();
    List<ExamRecordItemBean> mInfo;                         //详细信息
    private int mCurrentPage = 1;                           //页数
    private final int page_size = 10;                       //每页信息数
    private String userInterest = "";                       //用户兴趣
    private boolean mNoMoreData = false;
    private DaoSession mDaoSession;
    private UserInforBean mUserBean;                        //用户信息


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_home_interest, container, false);
        if (mPresenter == null)
            mPresenter = getPresenter();

        initFocus();
        initView();
        setViewListener();
        refreshData();
        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        //refreshData();
    }

    //获取用户兴趣
    public void initFocus(){
        mDaoSession=((IcompetitionApplication)getApplication()).getDaoSession();
        UserInforBeanDao userInforBeanDao = mDaoSession.getUserInforBeanDao();
        List<UserInforBean> list = userInforBeanDao.loadAll();
        String token = ((IcompetitionApplication)getApplication()).getToken();
        if( token != null && !token.equals("")){
            if(list.get(0)!=null) {
                mUserBean = list.get(0);
            }

            //userInterest = mUserBean.getUser_interest();
        }
    }

    public void initView() {
        notFindLl = rootView.findViewById(R.id.ll_home_interest_not_found);
        mRecyclerView = rootView.findViewById(R.id.rc_home_interest_list);
        mSwipeRefreshLayout = rootView.findViewById(R.id.srl_home_interest_list);
        mSwipeRefreshLayout.setColorSchemeColors(Color.BLUE, Color.GREEN, Color.RED);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    public void setViewListener() {
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshData();
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
                        getTypeInfo(++mCurrentPage, page_size, userInterest);
                    }
                }
            }
        });
    }


    @Override
    protected HomeInterestFragmentPresenter getPresenter() {
        return new HomeInterestFragmentPresenter(this);
    }

    @Override
    public void failBecauseNotNetworkReturn(int code) {
        showSnackBar(rootView,"服务器异常，稍后重试 " + code);
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
        //更新用户兴趣
        initFocus();
        mNoMoreData = false;
        mCurrentPage = 1;
        mInfo = null;
        if (mAdapter != null) {
            mAdapter.setNoMoreData(false);
        }
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(true);
            }
        });
        getTypeInfo(mCurrentPage, page_size, userInterest);
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
                mAdapter = new HomeInterestAdapter(getContext(), mInfo);
                mAdapter.setNoMoreData(true);
                mAdapter.notifyDataSetChanged();
                mRecyclerView.setAdapter(mAdapter);
                mRecyclerView.scrollTo(mRecyclerViewCurrentX, mRecyclerViewCurrentY);
            }
        });
    }


    /**
     * 查看详情
     */
    private void gotoDoc() {
        mAdapter.setItemClickListener(new HomeInterestAdapter.docItemClickListener() {
            @Override
            public void onClick(int position) {
                Intent intent = new Intent(getActivity(), CompetitionInfoActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("item", mInfo.get(position));
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }


    //分页查询
    @Override
    public void getTypeInfo(int page_no, int page_size, String type) {
        if(NetWorkHelper.isNetworkAvailable(getActivity())){
            mPresenter.getTypeInfo(page_no, page_size, type);
        }else {
            Toast.makeText(getActivity(), getString(R.string.not_have_network), Toast.LENGTH_SHORT).show();
            //showSnackBar(mRecyclerView,getString(R.string.not_have_network));

        }

    }

    //分页查询的回调
    @Override
    public void PagingQueryHomeInterestResponse(final ExamRecordRoot root) {
        if (root != null && root.getCode() == 200) {
            if (root.getData() != null) {
                if (mInfo == null) {
                    mInfo = new ArrayList<>();
                }
                mInfo.addAll(root.getData());

                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        if(mInfo.size() != 0){
                            notFindLl.setVisibility(View.GONE);
                        }else {
                            notFindLl.setVisibility(View.VISIBLE);
                        }

                        if (mSwipeRefreshLayout.isRefreshing()) {
                            mSwipeRefreshLayout.setRefreshing(false);
                        }
                        if (mAdapter == null) {
                            mAdapter = new HomeInterestAdapter(getContext(), mInfo);
                            if (root.getData().size() < page_size) {
                                //显示没有更多
                                mNoMoreData = true;
                                mAdapter.setNoMoreData(true);
                            }
                            mRecyclerView.setAdapter(mAdapter);
                        } else {
                            //更新数据
                            mAdapter.updateData(mInfo);
                            if (mInfo.size() < page_size) {
                                //显示没有更多
                                mNoMoreData = true;
                                mAdapter.setNoMoreData(true);
                            }
                            mAdapter.notifyDataSetChanged();
                            mRecyclerView.scrollTo(mRecyclerViewCurrentX, mRecyclerViewCurrentY);
                        }
                        //设置点击事件
                        gotoDoc();
                    }
                });

            } else {
                noMoreData();
            }
        } else {
            showSnackBar(mSwipeRefreshLayout, getResources().getString(R.string.request_fail));
        }
    }
}
