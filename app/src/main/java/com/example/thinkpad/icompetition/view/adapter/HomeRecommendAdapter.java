package com.example.thinkpad.icompetition.view.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.TextPaint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.thinkpad.icompetition.R;
import com.example.thinkpad.icompetition.model.entity.news.NewsItem;
import java.util.List;

/**
 * Created by Hjg on 2018/11/26.
 * 首页推荐的Adapter
 */
public class HomeRecommendAdapter
    extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private final int FOOT = -1;
    private List<NewsItem> mNewsInfo;                   //信息
    private boolean mNoMoreData = false;                                //数据是否加载完毕
    private docItemClickListener itemClickListener;                     //item监听器
    private int mPosition = 1;                                          //用于记录哪一个item被点击

    public HomeRecommendAdapter(Context context, List<NewsItem> info){
        mContext = context;
        mNewsInfo = info;
        mLayoutInflater = LayoutInflater.from(context);
    }

    /**
     * 更新数据
     */
    public void updateData(List<NewsItem> info){
        this.mNewsInfo = info;
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
            return new HomeRecommendAdapter.FootViewHolder(mLayoutInflater.inflate(R.layout.item_typefoot, parent, false));
        }
        return new HomeRecommendAdapter.BodyViewHolder(mLayoutInflater.inflate(R.layout.item_home_news, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        if(holder instanceof HomeRecommendAdapter.FootViewHolder){
            ((HomeRecommendAdapter.FootViewHolder) holder).setData();
        }
        else {
            ((HomeRecommendAdapter.BodyViewHolder)holder).setDate(position);
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
        return mNewsInfo.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if(position == mNewsInfo.size()){
            return FOOT;
        }
        return 1;
    }



    /**
     * Item
     */
    public class BodyViewHolder extends RecyclerView.ViewHolder{
        private CardView mItemCv;
        private ImageView mPhotoIv;             //图片
        private TextView mTitleTv;              //标题
        private TextView mTypeTv;               //类型
        private TextView mLevelTv;              //等级
        private TextView mEquipmentTv;          //器械要求

        BodyViewHolder(View itemView) {
            super(itemView);
            mItemCv = itemView.findViewById(R.id.cv_item_home_news);
            mPhotoIv = itemView.findViewById(R.id.iv_item_home_news_photo);
            mTitleTv = itemView.findViewById(R.id.tv_item_home_news_title);
            //字体加粗
            TextPaint tp = mTitleTv.getPaint();
            tp.setFakeBoldText(true);
            mTypeTv = itemView.findViewById(R.id.tv_item_home_news_type);
            mLevelTv = itemView.findViewById(R.id.tv_item_home_news_level);
            mEquipmentTv = itemView.findViewById(R.id.tv_item_home_news_equipment);

        }

        //填写数据
        void setDate(int position){
            NewsItem itemBean = mNewsInfo.get(position);

            //加载图片
            String imageUrl = itemBean.getNews_image();
            if (!imageUrl.equals("")){
                Glide.with(mContext).load(imageUrl).centerCrop().into(mPhotoIv);
            }

            mTitleTv.setText(itemBean.getNews_title());
            mTypeTv.setText(itemBean.getNews_type());
            mLevelTv.setText(itemBean.getNews_level());
            mEquipmentTv.setText(itemBean.getNews_equipment());
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
