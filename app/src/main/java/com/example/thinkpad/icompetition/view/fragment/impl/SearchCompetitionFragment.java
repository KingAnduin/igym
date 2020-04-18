package com.example.thinkpad.icompetition.view.fragment.impl;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.thinkpad.icompetition.R;
import com.example.thinkpad.icompetition.model.entity.exam.ExamRecordItemBean;
import com.example.thinkpad.icompetition.model.entity.search.CompetitionsBean;
import com.example.thinkpad.icompetition.model.entity.search.SearchRoot;
import com.example.thinkpad.icompetition.presenter.impl.SearchCompetitionFragmentPresenter;
import com.example.thinkpad.icompetition.util.NetWorkHelper;
import com.example.thinkpad.icompetition.view.activity.impl.CompetitionInfoActivity;
import com.example.thinkpad.icompetition.view.activity.impl.SearchActivity;
import com.example.thinkpad.icompetition.view.adapter.SearchResultAdapter;
import com.example.thinkpad.icompetition.view.fragment.i.ISearchCompetitionFragment;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SearchCompetitionFragment extends BaseFragment<SearchCompetitionFragmentPresenter> implements ISearchCompetitionFragment {
    private LinearLayout mNotFoundLayout;
    private RecyclerView mRecyclerView;
    private View view;
    private LinearLayoutManager layoutManager;
    private boolean isAll = false;         //判断是否当前页滑到最底部
    private int page = 1;
    private int pageSize = 15;
    private String keyWords = null;
    private int mRecyclerViewCurrentY = 0;
    private int mRecyclerViewCurrentX = 0;
    private List<ExamRecordItemBean> mSearchCompetitionsResult = null;
    private SearchResultAdapter mAdapter;
    private Handler mHandler = new Handler();


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_search_competition,container,false);
        mRecyclerView=view.findViewById(R.id.search_recyclerview);
        mNotFoundLayout=view.findViewById(R.id.layout_not_found);
        if (mPresenter == null)
            mPresenter=getPresenter();
        init();
        return view;
    }

    private void init() {
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (recyclerView.computeVerticalScrollExtent() + recyclerView.computeVerticalScrollOffset()
                        >= recyclerView.computeVerticalScrollRange()) {
                    //滑动到了底部 避免重复请求
                    if (!isAll) {
                        mRecyclerViewCurrentY = dy;
                        mRecyclerViewCurrentX = dx;
                        //加载更多
                        searchInfor(++page, pageSize,keyWords);
                    }
                }
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }
        });
    }

    @Override
    protected SearchCompetitionFragmentPresenter getPresenter() {
        return new SearchCompetitionFragmentPresenter(this);
    }

    public void searchInfor(int page, int pageSize, String keyWords) {
        this.keyWords=keyWords;
        if(NetWorkHelper.isNetworkAvailable(getActivity())) {
           mPresenter.searchInfor(page,pageSize,keyWords);
        }else {
            showSnackBar(mRecyclerView,getString(R.string.not_have_network));
        }
    }

    public void clearList(){
        mSearchCompetitionsResult.clear();
    }

    @Override
    public void searchReturn(final SearchRoot root) {
        final SearchActivity activity = (SearchActivity)getActivity();
        activity.dismissDialog();//取消activity上的dialog加载动画
        if(root.getCode()==200){
            activity.setFlag(true);
            if(root.getData()!=null){
                if(mSearchCompetitionsResult ==null){
                    mSearchCompetitionsResult = new ArrayList<>();
                }
                if(root.getData().getCompetitions()!=null) {
                    mSearchCompetitionsResult.addAll(root.getData().getCompetitions());
                }
                //这里根据返回的数据相加判断是否隐藏TabLayout和activity的notfoundLayoout
                if(root.getData().getUsers().size()+root.getData().getCompetitions().size()>0){
                    activity.setTablayoutVisible(true);
                }
                else {
                    activity.setTablayoutVisible(false);
                }
                //这里添加
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        if(mSearchCompetitionsResult.size()!=0) {
                            mNotFoundLayout.setVisibility(View.GONE);
                            mRecyclerView.setVisibility(View.VISIBLE);
                        }
                        else {
                            mNotFoundLayout.setVisibility(View.VISIBLE);
                            mRecyclerView.setVisibility(View.INVISIBLE);
                        }
                        //mRecyclerView.setVisibility(View.VISIBLE);
                        if (layoutManager == null){
                            layoutManager = new LinearLayoutManager(getActivity());
                            mRecyclerView.setLayoutManager(layoutManager);
                        }
                        if(mAdapter==null){
                            mAdapter = new SearchResultAdapter(getActivity(),mSearchCompetitionsResult,0);
                            mRecyclerView.setAdapter(mAdapter);
                            gotoDoc();
                        }else{
                            mAdapter.updateData(mSearchCompetitionsResult);
                            mRecyclerView.scrollTo(mRecyclerViewCurrentX, mRecyclerViewCurrentY);
                        }
                        if(root.getData().getCompetitions()!=null) {
                            if(root.getData().getCompetitions().size()<pageSize){
                                isAll=true;
                                mAdapter.deleteLoadingMore(true);
                            }
                            else {
                                isAll = false;
                                mAdapter.deleteLoadingMore(false);
                            }
                        }
                    }
                });
            }
        }
    }

    @Override
    public void failBecauseNotNetworkReturn(int code) {
        showSnackBar(mRecyclerView,getString(R.string.not_network));
        SearchActivity activity = (SearchActivity)getActivity();
        activity.dismissDialog();//取消activity上的dialog加载动画
    }
    private void gotoDoc() {
        mAdapter.setItemClickListener(new SearchResultAdapter.docItemClickListener() {
            @Override
            public void onClick(int position) {
                Intent intent = new Intent(getActivity(), CompetitionInfoActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("item", mSearchCompetitionsResult.get(position));
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

    }
}
