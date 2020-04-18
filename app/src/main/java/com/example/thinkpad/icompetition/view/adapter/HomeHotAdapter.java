package com.example.thinkpad.icompetition.view.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
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
import com.example.thinkpad.icompetition.view.widget.DateCount;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created By hjg on 2018/11/29
 */
public class HomeHotAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private final int FOOT = -1;
    private List<ExamRecordItemBean> mExamRecordInfo;                   //信息
    private boolean mNoMoreData = false;                                //数据是否加载完毕
    private docItemClickListener itemClickListener;                     //item监听器
    private int mPosition;                                              //用于记录哪一个item被点击

    public HomeHotAdapter(Context context, List<ExamRecordItemBean> info){
        mContext = context;
        mExamRecordInfo = info;
        mLayoutInflater = LayoutInflater.from(context);
    }

    /**
     * 更新数据
     */
    public void updateData(List<ExamRecordItemBean> info){
        this.mExamRecordInfo = info;
        this.notifyDataSetChanged();
    }

    /**
     * 没有更多数据了
     */
    public void setNoMoreData(boolean noMoreData){
        mNoMoreData = noMoreData;
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

    public void setPosition(int position){
        this.mPosition = position;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType == FOOT){
            return new HomeHotAdapter.FootViewHolder(mLayoutInflater.inflate(R.layout.item_typefoot, parent, false));
        }
        return new HomeHotAdapter.BodyViewHolder(mLayoutInflater.inflate(R.layout.item_home_hot, parent, false));

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if(holder instanceof HomeHotAdapter.FootViewHolder){
            ((HomeHotAdapter.FootViewHolder) holder).setData();
        }
        else {
            ((HomeHotAdapter.BodyViewHolder)holder).setDate(position);
            ((HomeHotAdapter.BodyViewHolder) holder).mItemCv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(itemClickListener != null){
                        itemClickListener.onClick(position);
                    }
                    setPosition(position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mExamRecordInfo.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if(position == mExamRecordInfo.size()){
            return FOOT;
        }
        return 1;
    }



    /**
     * Item
     */
    public class BodyViewHolder extends RecyclerView.ViewHolder{
        private CardView mItemCv;
        private ImageView mPhotoIv;             //比赛图片
        private TextView mTitleTv;              //比赛标题
        private TextView mRankTv;               //名次

        public BodyViewHolder(View itemView) {
            super(itemView);
            mItemCv = itemView.findViewById(R.id.cv_item_home_hot);
            mPhotoIv = itemView.findViewById(R.id.iv_item_home_hot_photo);
            mTitleTv = itemView.findViewById(R.id.tv_item_home_hot_title);
            mRankTv = itemView.findViewById(R.id.tv_item_home_hot_num);
            //字体加粗
            TextPaint tp = mTitleTv.getPaint();
            tp.setFakeBoldText(true);
        }

        //填写数据
        public void setDate(int position){
            ExamRecordItemBean itemBean = mExamRecordInfo.get(position);

            //设置字体颜色
            mPosition = position + 1;
            mRankTv.setText(String.valueOf(mPosition));
            if(mPosition <= 3){
                mRankTv.setTextColor(Color.RED);
            }
            //加载图片
            String imageUrl = itemBean.getCom_picture();
            if (!imageUrl.equals("")){
                Glide.with(mContext).load(imageUrl).centerCrop().into(mPhotoIv);
            }

            mTitleTv.setText(itemBean.getCom_title());
        }

    }

    /**
     * 页脚
     */
    public class FootViewHolder extends RecyclerView.ViewHolder{
        private ProgressBar mProgressBar;
        private TextView mFootTv;
        private FootViewHolder(View itemView) {
            super(itemView);
            mProgressBar = itemView.findViewById(R.id.pb_attention_item_progress);
            mFootTv = itemView.findViewById(R.id.tv_attention_item_hint);
        }

        void setData(){
            if(mNoMoreData){
                mFootTv.setText(mContext.getString(R.string.attention_bottom_hint));
                mProgressBar.setVisibility(View.GONE);
            }else {
                mFootTv.setText(mContext.getString(R.string.attention_fresh_hint));
                mProgressBar.setVisibility(View.VISIBLE);
            }
        }
    }
}
