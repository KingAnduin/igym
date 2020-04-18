package com.example.thinkpad.icompetition.view.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.thinkpad.icompetition.R;
import com.example.thinkpad.icompetition.model.entity.exam.ExamRecordItemBean;
import com.example.thinkpad.icompetition.model.entity.search.CompetitionsBean;
import com.example.thinkpad.icompetition.model.entity.search.SearchDataBean;
import com.example.thinkpad.icompetition.model.entity.search.UsersBean;
import com.example.thinkpad.icompetition.view.widget.AsyncImageView;
import com.example.thinkpad.icompetition.view.widget.DateCount;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import io.rong.imageloader.core.DisplayImageOptions;
import io.rong.imageloader.core.ImageLoader;
import io.rong.imageloader.core.ImageLoaderConfiguration;
import io.rong.imageloader.core.display.FadeInBitmapDisplayer;

public class SearchResultAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private DisplayImageOptions options;
    private ImageLoader imageLoader = ImageLoader.getInstance();
    private Context context;
    private int state; //0:比赛类 1：用户类
    private List list;
    private LayoutInflater inflater;
    private boolean isDelete = false;
    private LayoutInflater mLayoutInflater;
    private final int FOOT = -1;
    private docItemClickListener itemClickListener;                     //item监听器

    public SearchResultAdapter(Context context, List list ,int state){
        this.context = context;
        this.list = list;
        this.state = state;
        this.inflater =LayoutInflater.from(context);
        this.imageLoader.init(ImageLoaderConfiguration.createDefault(context));
        this.options = new DisplayImageOptions.Builder()
                .showImageForEmptyUri(R.mipmap.ic_launcher)
                .showImageOnFail(R.mipmap.ic_launcher)
                .showImageOnLoading(R.mipmap.ic_news_listview_img_loading)
                .displayer(new FadeInBitmapDisplayer(300))
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .build();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch(viewType){
            case FOOT:
                return new FootViewHolder(inflater.inflate(R.layout.item_typefoot,parent,false));
            default:
                if(state==1){
                    return new UserViewHolder(inflater.inflate(R.layout.item_search_user,parent,false));
                }
                else {
                    return new BodyViewHolder(inflater.inflate(R.layout.item_search_competition,parent,false));
                }
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        switch (getItemViewType(position)){
            case FOOT:
                FootViewHolder footViewHolder =(FootViewHolder)holder;
                footViewHolder.setData();
                break;
            default:
                if(state==1){
                    UserViewHolder userViewHolder = (UserViewHolder)holder;
                    userViewHolder.setData(position);
                    userViewHolder.mItemCV.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            itemClickListener.onClick(position);
                        }
                    });
                }
                else if(state==0){
                    BodyViewHolder bodyViewHolder = (BodyViewHolder)holder;
                    bodyViewHolder.setDate(position);
                    bodyViewHolder.mItemCv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            itemClickListener.onClick(position);
                        }
                    });
                }
        }
    }

    @Override
    public int getItemViewType(int position) {
        if(position + 1 == getItemCount()) {
            return FOOT;
        }else{
            return position;
        }
    }

    /**
     * 用户Item
     */
    public class UserViewHolder extends RecyclerView.ViewHolder{
        private CardView mItemCV;
        private AsyncImageView mUserHeadImageAIV;
        private TextView mUserNameTV;
        private TextView mUserRoleTV;
        public UserViewHolder(View itemView) {
            super(itemView);
            mItemCV = itemView.findViewById(R.id.cv_item_user_list);
            mUserHeadImageAIV = itemView.findViewById(R.id.aiv_search_headimage);
            mUserNameTV = itemView.findViewById(R.id.tv_search_user_name);
            mUserRoleTV = itemView.findViewById(R.id.tv_search_user_role);
        }
        //填写数据
        public void setData(int position){
            UsersBean bean = (UsersBean) list.get(position);
            if(!TextUtils.isEmpty(bean.getUser_name())){
                mUserNameTV.setText(bean.getUser_name());
            }
            if(!TextUtils.isEmpty(String.valueOf(bean.getUser_roleid()))){
                int roleID = bean.getUser_roleid();
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
            if(!TextUtils.isEmpty(bean.getUser_headimage())){
                imageLoader.init(ImageLoaderConfiguration.createDefault(context));
                imageLoader.clearDiskCache();
                imageLoader.clearMemoryCache();
                imageLoader.displayImage(bean.getUser_headimage(),mUserHeadImageAIV,options);
            }
        }
    }

    /**
     * 比赛Item
     */
    public class BodyViewHolder extends RecyclerView.ViewHolder{
        private CardView mItemCv;
        private ImageView mPhotoIv;
        private TextView mIsStartTv;            //正在报名 / 报名结束
        private TextView mDeadlineTv;           //离报名截止时间
        private TextView mTitleTv;              //比赛标题

        public BodyViewHolder(View itemView) {
            super(itemView);
            mItemCv = itemView.findViewById(R.id.cv_item_home_list);
            mPhotoIv = itemView.findViewById(R.id.iv_item_home_list_photo);
            mIsStartTv = itemView.findViewById(R.id.tv_item_home_list_is_start);
            //字体加粗
            TextPaint tp1 = mIsStartTv.getPaint();
            tp1.setFakeBoldText(true);
            mDeadlineTv = itemView.findViewById(R.id.tv_item_home_list_deadline);
            mTitleTv = itemView.findViewById(R.id.tv_item_home_list_title);
            //字体加粗
            TextPaint tp = mTitleTv.getPaint();
            tp.setFakeBoldText(true);
        }

        //填写数据
        public void setDate(int position){
            ExamRecordItemBean itemBean = (ExamRecordItemBean) list.get(position);
            String[] signUpStart = itemBean.getCom_signupstart().split(" ");
            String[] signUpEnd = itemBean.getCom_signupend().split(" ");
            String signUpTime = signUpStart[0] + " -- " + signUpEnd[0];
            String[] examStart = itemBean.getCom_starttime().split(" ");
            String[] examEnd = itemBean.getCom_endtime().split(" ");
            String examTime = examStart[0] + " -- " + examEnd[0];

            //设置字体颜色
            SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd");
            Date curDate = new Date(System.currentTimeMillis());
            String curDates = format.format(curDate);
            DateCount dateCount = new DateCount();
            Long counts = dateCount.count(curDates, signUpEnd[0], "yyyy.MM.dd");
            if(counts > 0){
                mDeadlineTv.setText(formatSpannableString(context, context.getString(R.string.item_home_list_deadline), String.valueOf(counts)));
            }else {
                mIsStartTv.setText(R.string.item_home_list_started);
                mIsStartTv.setTextColor(Color.RED);
                mDeadlineTv.setText(R.string.item_home_list_is_end);
            }

            //加载图片
            String imageUrl = itemBean.getCom_picture();
            if (!imageUrl.equals("")){
                Glide.with(context).load(imageUrl).centerCrop().into(mPhotoIv);
            }
            mTitleTv.setText(itemBean.getCom_title());
        }

        private SpannableString formatSpannableString(Context context, String title, String content) {
            if (content == null) {
                content = "";
            }
            SpannableString result = new SpannableString(String.format(title, content));
            ForegroundColorSpan contentSpan = new ForegroundColorSpan(
                    ContextCompat.getColor(context, R.color.font_black_primary));
            result.setSpan(contentSpan,
                    result.length() - content.length(),
                    result.length(),
                    Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
            return result;
        }

    }

    public class FootViewHolder extends RecyclerView.ViewHolder{
        private ProgressBar mProgressBar;
        private TextView mFootTv;
        private FootViewHolder(View itemView) {
            super(itemView);
            mProgressBar = itemView.findViewById(R.id.pb_attention_item_progress);
            mFootTv = itemView.findViewById(R.id.tv_attention_item_hint);
        }

        void setData(){
            if(isDelete){
                mFootTv.setText(context.getString(R.string.attention_bottom_hint));
                mProgressBar.setVisibility(View.GONE);
            }else {
                mFootTv.setText(context.getString(R.string.attention_fresh_hint));
                mProgressBar.setVisibility(View.VISIBLE);
            }
        }
    }

    //是否取消加载更多
    public void deleteLoadingMore(boolean isDelete){
        this.isDelete = isDelete;
        this.notifyItemChanged(getItemCount()-1);

    }

    //更新展示用的数据
    public void updateData(List list){
        this.list = list;
        this.notifyDataSetChanged();
    }

    public int getItemCount(){
        return list.size()+1;//这里加一是因为还有一个脚的item
    }

    /**
     * item监听
     * @param listener listener
     */
    public void setItemClickListener(docItemClickListener listener){
        itemClickListener = listener;
    }

    public interface docItemClickListener{
        void onClick(int position);
    }
}
