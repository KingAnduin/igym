package com.example.thinkpad.icompetition.view.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.thinkpad.icompetition.R;
import com.example.thinkpad.icompetition.model.entity.order.OrderItem;
import com.example.thinkpad.icompetition.view.activity.impl.MyOrderActivity;

import java.util.List;

/**
 * Created By hjg on 2018/12/15
 * 我的订单Adapter
 */
public class MyOrderAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private MyOrderActivity activity;
    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private final int FOOT = -1;
    public List<OrderItem> mOrderInfo;                                  //信息
    private boolean mNoMoreData = false;                                //数据是否加载完毕
    private docItemClickListener itemClickListener;                     //item监听器
    private int mPosition;                                              //用于记录哪一个item被点击

    public MyOrderAdapter(Context context, List<OrderItem> info, MyOrderActivity activity){
        mContext = context;
        mOrderInfo = info;
        this.activity=activity;
        mLayoutInflater = LayoutInflater.from(context);
    }

    /**
     * 更新数据
     */
    public void updateData(List<OrderItem> info){
        this.mOrderInfo = info;
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

    public int getClickPosition(){
        return mPosition;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType == FOOT){
            return new MyOrderAdapter.FootViewHolder(mLayoutInflater.inflate(R.layout.item_typefoot, parent, false));
        }
        return new MyOrderAdapter.BodyViewHolder(mLayoutInflater.inflate(R.layout.item_me_collection, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        if(holder instanceof MyOrderAdapter.FootViewHolder){
            ((FootViewHolder) holder).setData();
        }else {
            ((MyOrderAdapter.BodyViewHolder)holder).setData(position);
            ((BodyViewHolder) holder).mItemCv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(itemClickListener != null){
                        itemClickListener.onClick(position);
                    }
                    setPosition(position);
                }
            });
            final BodyViewHolder bodyViewHolder = (BodyViewHolder) holder;

            // 新增评论
            bodyViewHolder.mOrderCommentBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setPosition(position);
                    activity.addComment(mOrderInfo.get(position).getId());
                }
            });

            //删除订单
            bodyViewHolder.mOrderDeleteBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setPosition(position);
                    activity.deleteOrder(mOrderInfo.get(position).getId());
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mOrderInfo.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if(position == mOrderInfo.size()){
            return FOOT;
        }
        return 1;
    }

    /**
     * Item
     */
    public class BodyViewHolder extends RecyclerView.ViewHolder{

        private CardView mItemCv;
        private TextView mOrderStatusTv;
        private TextView mOrderIdTv;
        private TextView mOrderGoodTv;
        private TextView mOrderDateTv;
        private TextView mOrderPeriodTv;
        private TextView mOrderCreateTimeTv;
        private Button mOrderCommentBtn;
        private Button mOrderDeleteBtn;


        BodyViewHolder(View itemView) {
            super(itemView);
            mItemCv = itemView.findViewById(R.id.cv_me_order);
            mOrderStatusTv = itemView.findViewById(R.id.tv_order_status);
            mOrderIdTv = itemView.findViewById(R.id.tv_order_id);
            mOrderGoodTv = itemView.findViewById(R.id.tv_order_good);
            mOrderDateTv = itemView.findViewById(R.id.tv_order_date);
            mOrderPeriodTv = itemView.findViewById(R.id.tv_order_time_period);
            mOrderCreateTimeTv = itemView.findViewById(R.id.tv_order_create_time);
            mOrderCommentBtn = itemView.findViewById(R.id.btn_order_comment);
            mOrderDeleteBtn = itemView.findViewById(R.id.btn_order_delete);
        }

        public void setData(int position){
            OrderItem itemBean = mOrderInfo.get(position);
            mOrderStatusTv.setText(itemBean.getOrder_status_name());
            mOrderIdTv.setText(String.valueOf(itemBean.getId()));
            mOrderGoodTv.setText(itemBean.getGood_name());
            mOrderDateTv.setText(itemBean.getOrder_date());
            mOrderPeriodTv.setText(itemBean.getOrder_time_period_name());
            mOrderCreateTimeTv.setText(itemBean.getCreate_time().substring(0, 10));
            if(itemBean.getOrder_is_comment().equals("评价一下") &&
                itemBean.getOrder_status_name().equals("已完成")){
                mOrderCommentBtn.setVisibility(View.VISIBLE);
            }
            else {
                mOrderCommentBtn.setVisibility(View.INVISIBLE);
            }
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
