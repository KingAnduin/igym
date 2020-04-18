package com.example.thinkpad.icompetition.view.fragment.impl;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.thinkpad.icompetition.model.entity.exam.ExamRecordItemBean;
import com.example.thinkpad.icompetition.model.entity.exam.ExamRecordRoot;
import com.example.thinkpad.icompetition.presenter.impl.HomeHotsFragmentPresenter;
import com.example.thinkpad.icompetition.util.NetWorkHelper;
import com.example.thinkpad.icompetition.view.activity.i.IBaseActivity;
import com.example.thinkpad.icompetition.R;
import com.example.thinkpad.icompetition.view.activity.impl.CompetitionInfoActivity;
import com.example.thinkpad.icompetition.view.adapter.HomeHotAdapter;
import com.example.thinkpad.icompetition.view.fragment.i.IHomeHotsFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by a'su's on 2018/7/12.
 * 首页热门Fragment
 */

public class HomeHotsFragment extends BaseFragment<HomeHotsFragmentPresenter>
        implements IBaseActivity, IHomeHotsFragment {
    private View rootView;
    private HomeHotAdapter mAdapter = null;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;
    private int mRecyclerViewCurrentY = 0;
    private int mRecyclerViewCurrentX = 0;

    private Handler mHandler = new Handler();
    List<ExamRecordItemBean> mInfo;                         //详细信息
    private int mCurrentPage = 1;                           //页数
    private final int page_size = 10;                       //每页信息数
    private boolean mNoMoreData = false;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        rootView=inflater.inflate(R.layout.fragment_home_hots,container,false);
        if (mPresenter == null)
            mPresenter = getPresenter();

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



    @Override
    protected HomeHotsFragmentPresenter getPresenter() {
        return new HomeHotsFragmentPresenter(this);
    }

    @Override
    public void getExamInfo(int page_no, int page_size) {
        if(NetWorkHelper.isNetworkAvailable(getActivity())){
            mPresenter.getHotsInfo(page_no, page_size);
        }else{
            Toast.makeText(getActivity(), getString(R.string.not_have_network), Toast.LENGTH_SHORT).show();
            //showSnackBar(mRecyclerView,getString(R.string.not_have_network));
        }

    }

    @Override
    public void pagingQueryHomeHotsResponse(final ExamRecordRoot root) {
        if (root != null && root.getCode() == 200) {
            if (root.getData() != null) {
                if (mInfo == null) {
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
                            mAdapter = new HomeHotAdapter(getContext(), mInfo);
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

    public void initView(){
        mSwipeRefreshLayout = rootView.findViewById(R.id.srl_home_hots_list);
        mRecyclerView = rootView.findViewById(R.id.rc_home_hots_list);
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
                        getExamInfo(++mCurrentPage, page_size);
                    }
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
            mAdapter.setNoMoreData(false);
        }
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(true);
            }
        });
        getExamInfo(mCurrentPage, page_size);
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
                mAdapter = new HomeHotAdapter(getContext(), mInfo);
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
        mAdapter.setItemClickListener(new HomeHotAdapter.docItemClickListener() {
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


}
