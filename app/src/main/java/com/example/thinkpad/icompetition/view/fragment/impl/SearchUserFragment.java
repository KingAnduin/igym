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
import com.example.thinkpad.icompetition.model.entity.search.CompetitionsBean;
import com.example.thinkpad.icompetition.model.entity.search.SearchRoot;
import com.example.thinkpad.icompetition.model.entity.search.UsersBean;
import com.example.thinkpad.icompetition.presenter.impl.SearchUserFragmentPresenter;
import com.example.thinkpad.icompetition.util.NetWorkHelper;
import com.example.thinkpad.icompetition.view.activity.impl.UserBySearchActivity;
import com.example.thinkpad.icompetition.view.adapter.SearchResultAdapter;
import com.example.thinkpad.icompetition.view.fragment.i.ISearchUserFragment;

import java.util.ArrayList;
import java.util.List;

public class SearchUserFragment extends BaseFragment<SearchUserFragmentPresenter> implements ISearchUserFragment {
    private LinearLayout mNotFoundLayout;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager layoutManager;
    private boolean isAll = false;         //判断是否当前页滑到最底部
    private int page = 1;
    private int pageSize = 15;
    private String keyWords = null;
    private int mRecyclerViewCurrentY = 0;
    private int mRecyclerViewCurrentX = 0;
    private List<UsersBean> mSearchUserResult = null;
    private SearchResultAdapter mAdapter;
    private Handler mHandler = new Handler();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search_competition, container, false);
        mRecyclerView= view.findViewById(R.id.search_recyclerview);
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

    public void searchInfor(int page, int pageSize, String keyWords) {
        this.keyWords=keyWords;
        if(NetWorkHelper.isNetworkAvailable(getActivity())) {
            mPresenter.searchInfor(page, pageSize, keyWords);
        }else {
            showSnackBar(mRecyclerView,getString(R.string.not_have_network));
        }
    }

    @Override
    public void searchReturn(final SearchRoot root) {
        if(root.getCode()==200){
            if(root.getData()!=null){

                if(mSearchUserResult ==null){
                    mSearchUserResult = new ArrayList<>();
                }
                if(root.getData().getCompetitions()!=null) {
                    mSearchUserResult.addAll(root.getData().getUsers());
                }
                //这里添加
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        if(mSearchUserResult.size()!=0) {
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
                            mAdapter = new SearchResultAdapter(getActivity(),mSearchUserResult,1);
                            mRecyclerView.setAdapter(mAdapter);
                            gotoDoc();
                        }else{
                            mAdapter.updateData(mSearchUserResult);
                            mRecyclerView.scrollTo(mRecyclerViewCurrentX, mRecyclerViewCurrentY);
                        }
                        if(root.getData().getUsers()!=null) {
                            if(root.getData().getUsers().size()<pageSize){
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

    public void clearList(){
        mSearchUserResult.clear();
    }

    @Override
    protected SearchUserFragmentPresenter getPresenter() {
        return new SearchUserFragmentPresenter(this);
    }

    @Override
    public void failBecauseNotNetworkReturn(int code) {
        showSnackBar(mRecyclerView,getString(R.string.not_network));
    }

    private void gotoDoc() {
        mAdapter.setItemClickListener(new SearchResultAdapter.docItemClickListener() {
            @Override
            public void onClick(int position) {
                Intent intent = new Intent(getActivity(), UserBySearchActivity.class);
                intent.putExtra("user_bysearch", mSearchUserResult.get(position));
                startActivity(intent);
            }
        });

    }
}
