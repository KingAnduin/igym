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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.thinkpad.icompetition.R;
import com.example.thinkpad.icompetition.model.entity.exam.ExamRecordItemBean;
import com.example.thinkpad.icompetition.view.widget.DateCount;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created By hjg on 2018/12/15
 * 我的收藏Adapter
 */
public class MyCollectionAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private final int FOOT = -1;
    private List<ExamRecordItemBean> mExamRecordInfo;                   //信息
    private boolean mNoMoreData = false;                                //数据是否加载完毕
    private docItemClickListener itemClickListener;                     //item监听器
    private int mPosition;                                              //用于记录哪一个item被点击

    public MyCollectionAdapter(Context context, List<ExamRecordItemBean> info){
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

    public interface docItemClickListener{
        void onClick(int position);
    }

    /**
     * item监听
     * @param listener listener
     */
    public void setItemClickListener(docItemClickListener listener){
        itemClickListener = listener;
    }

    public void setPosition(int position){
        this.mPosition = position;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType == FOOT){
            return new MyCollectionAdapter.FootViewHolder(mLayoutInflater.inflate(R.layout.item_typefoot, parent, false));
        }
        return new MyCollectionAdapter.BodyViewHolder(mLayoutInflater.inflate(R.layout.item_me_collection, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if(holder instanceof MyCollectionAdapter.FootViewHolder){
            ((FootViewHolder) holder).setData();
        }else {
            ((MyCollectionAdapter.BodyViewHolder)holder).setData(position);
            ((BodyViewHolder) holder).mItemCv.setOnClickListener(new View.OnClickListener() {
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
        private TextView mIsStartTv;            //正在报名 报名结束
        private TextView mDeadlineTv;           //离报名截止时间
        private TextView mTitleTv;              //比赛标题


        public BodyViewHolder(View itemView) {
            super(itemView);
            mItemCv = itemView.findViewById(R.id.cv_me_collection);
            mIsStartTv = itemView.findViewById(R.id.tv_me_collection_is_start);
            mDeadlineTv = itemView.findViewById(R.id.tv_me_collection_deadline);
            mTitleTv = itemView.findViewById(R.id.tv_me_collection_title);
            //字体加粗
            TextPaint tp1 = mIsStartTv.getPaint();
            tp1.setFakeBoldText(true);
        }

        public void setData(int position){
            ExamRecordItemBean itemBean = mExamRecordInfo.get(position);
            String[] signUpStart = itemBean.getCom_signupstart().split(" ");
            String[] signUpEnd = itemBean.getCom_signupend().split(" ");

            //设置字体颜色
            SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd");
            Date curDate = new Date(System.currentTimeMillis());
            String curDates = format.format(curDate);
            DateCount dateCount = new DateCount();
            Long counts = dateCount.count(curDates, signUpEnd[0], "yyyy.MM.dd");
            if(counts > 0){
                mIsStartTv.setText(R.string.item_home_list_is_start);
                mIsStartTv.setTextColor(Color.GREEN);
                mDeadlineTv.setText(formatSpannableString(mContext, mContext.getString(R.string.item_home_list_deadline), String.valueOf(counts)));
            }else {
                mIsStartTv.setText(R.string.item_home_list_started);
                mIsStartTv.setTextColor(Color.RED);
                mDeadlineTv.setText(R.string.item_home_list_is_end);
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

        public void setData(){
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
