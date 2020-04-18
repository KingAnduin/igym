package com.example.thinkpad.icompetition.view.fragment.impl;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.thinkpad.icompetition.IcompetitionApplication;
import com.example.thinkpad.icompetition.R;
import com.example.thinkpad.icompetition.model.entity.user.UserInforBean;
import com.example.thinkpad.icompetition.util.ShowReturnLoginUtil;
import com.example.thinkpad.icompetition.view.activity.impl.InterstsSelectActivity;
import com.example.thinkpad.icompetition.view.activity.impl.LoginActivity;
import com.example.thinkpad.icompetition.view.activity.impl.MyCollectionActivity;
import com.example.thinkpad.icompetition.view.activity.impl.MyFocusActivity;
import com.example.thinkpad.icompetition.view.activity.impl.UserInforActivity;
import com.example.thinkpad.icompetition.view.activity.impl.UserSetActivity;
import com.example.thinkpad.icompetition.view.widget.AsyncImageView;

import java.util.List;

import greendao.gen.DaoSession;
import greendao.gen.UserInforBeanDao;
import io.rong.imageloader.core.DisplayImageOptions;
import io.rong.imageloader.core.ImageLoader;
import io.rong.imageloader.core.ImageLoaderConfiguration;
import io.rong.imageloader.core.display.FadeInBitmapDisplayer;

/**
 * Created by a'su's on 2018/7/12.
 * 我的Fragment
 */

public class MeFragment extends Fragment implements View.OnClickListener{
    private TextView mUserPhoneTV;
    private TextView mUserNameTV;
    private DaoSession mDaoSession;
    private UserInforBean mUserBean;
    private DisplayImageOptions options;
    private ImageLoader imageLoader = ImageLoader.getInstance();
    private AsyncImageView mUserHeadImageAIV;
    private View view;
    private ImageView mUerInfoEditorIV;
    private ImageView mUerSetIV;
    private LinearLayout mUserCollectionLl;
    private LinearLayout mUserInterestLl;
    private LinearLayout mUserAttentionLl;
    private ShowReturnLoginUtil showReturnLoginUtil;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_me,container,false);
        findView();
        initReturnLoginDialog();
        setListener();
        initDisplayImageOptions();
        judgeUserByToken();
        return view;
    }

    private void initReturnLoginDialog() {
        showReturnLoginUtil = new ShowReturnLoginUtil(getActivity());
    }

    private void judgeUserByToken(){
        if(!TextUtils.isEmpty(((IcompetitionApplication)getActivity().getApplication()).getToken())) {
            loadUserInfor();
        }
    }

    //加载用户头像,名字以及电话
    private void loadUserInfor() {
        mDaoSession=((IcompetitionApplication)getActivity().getApplication()).getDaoSession();
        UserInforBeanDao userInforBeanDao = mDaoSession.getUserInforBeanDao();
        List<UserInforBean> list = userInforBeanDao.loadAll();
        if(list.get(0)!=null) {
            mUserBean = list.get(0);
        }
        if(!TextUtils.isEmpty(mUserBean.getUser_headimage())) {
            imageLoader.displayImage(mUserBean.getUser_headimage(), mUserHeadImageAIV, options);
        }
        if(!TextUtils.isEmpty(mUserBean.getUser_name())){
            mUserNameTV.setText(mUserBean.getUser_name());
        }
        if(!TextUtils.isEmpty(String.valueOf(mUserBean.getUser_num()))){
            mUserPhoneTV.setText(String.valueOf(mUserBean.getUser_num()));
        }
    }

    private void setListener() {
        mUerInfoEditorIV.setOnClickListener(this);
        mUerSetIV.setOnClickListener(this);
        mUserAttentionLl.setOnClickListener(this);
        mUserCollectionLl.setOnClickListener(this);
        mUserInterestLl.setOnClickListener(this);
    }

    private void findView() {
        mUserPhoneTV = view.findViewById(R.id.tv_user_phone);
        mUerInfoEditorIV=view.findViewById(R.id.iv_me_editor);
        mUserHeadImageAIV=view.findViewById(R.id.aiv_me_headimage);
        mUerSetIV=view.findViewById(R.id.iv_me_set);
        mUserNameTV=view.findViewById(R.id.tv_me_name);
        mUserAttentionLl = view.findViewById(R.id.ll_me_attention);
        mUserCollectionLl = view.findViewById(R.id.ll_me_collection);
        mUserInterestLl = view.findViewById(R.id.ll_me_interest);
    }

    public void initDisplayImageOptions() {
        this.imageLoader.init(ImageLoaderConfiguration.createDefault(getActivity()));
        this.options = new DisplayImageOptions.Builder()
                .showImageForEmptyUri(R.mipmap.no_user_head)
                .showImageOnFail(R.mipmap.no_user_head)
                .showImageOnLoading(R.mipmap.ic_news_listview_img_loading)
                .displayer(new FadeInBitmapDisplayer(300))
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .build();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==100){
            if (resultCode==200){
                //为防止用户修改了个人信息，于是每次从用户信息界面返回都从本地数据库里读取一次头像
                loadUserInfor();
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_me_editor:
                if(!TextUtils.isEmpty(((IcompetitionApplication)getActivity().getApplication()).getToken())){
                    startActivityForResult(new Intent(getActivity(), UserInforActivity.class), 100);
                }else {
                    showReturnLoginUtil.show();//因为没有登陆所以回到登陆界面
                }
                break;
            case R.id.iv_me_set:
                if(!TextUtils.isEmpty(((IcompetitionApplication)getActivity().getApplication()).getToken())){
                    startActivity(new Intent(getActivity(), UserSetActivity.class));
                }else {
                    showReturnLoginUtil.show();//因为没有登陆所以回到登陆界面
                }
                break;
            case R.id.ll_me_attention:
                if(!TextUtils.isEmpty(((IcompetitionApplication)getActivity().getApplication()).getToken())){
                    startActivity(new Intent(getActivity(), MyFocusActivity.class));
                }else {
                    showReturnLoginUtil.show();//因为没有登陆所以回到登陆界面
                }
                break;
            case R.id.ll_me_collection:
                if(!TextUtils.isEmpty(((IcompetitionApplication)getActivity().getApplication()).getToken())){
                    startActivity(new Intent(getActivity(), MyCollectionActivity.class));
                }else {
                    showReturnLoginUtil.show();//因为没有登陆所以回到登陆界面
                }
                break;
            case R.id.ll_me_interest:
                if(!TextUtils.isEmpty(((IcompetitionApplication)getActivity().getApplication()).getToken())){
                    startActivity(new Intent(getActivity(),InterstsSelectActivity.class));
                }else {
                    showReturnLoginUtil.show();//因为没有登陆所以回到登陆界面
                }
                break;
        }
    }
}
