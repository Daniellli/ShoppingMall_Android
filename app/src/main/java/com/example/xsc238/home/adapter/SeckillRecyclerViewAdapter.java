package com.example.xsc238.home.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.xsc238.R;
import com.example.xsc238.home.bean.ResultBeanData;
import com.example.xsc238.utils.Constants;

import java.util.List;

/**
 * 作者：许少聪
 * 邮箱：18750910084@163.com
 * 作用：
 */
public class SeckillRecyclerViewAdapter extends
        RecyclerView.Adapter<SeckillRecyclerViewAdapter.SeckillItemViewHolder> {
    private List<ResultBeanData.ResultBean.SeckillInfoBean.ListBean> dataList;
    private Context mContext;
    public OnSeckillRecyclerView onSeckillRecyclerView;


    public SeckillRecyclerViewAdapter(List<ResultBeanData.ResultBean.SeckillInfoBean.ListBean> list,
                                      Context mContext) {
        this.dataList = list;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public SeckillItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new SeckillItemViewHolder(LayoutInflater.from(mContext)
                .inflate(R.layout.item_seckill, null, false));
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    @Override
    public void onBindViewHolder(@NonNull SeckillItemViewHolder holder, int position) {
        ResultBeanData.ResultBean.SeckillInfoBean.ListBean listBean = dataList.get(position);
        Glide.with(mContext)
                .load(Constants.Base_URl_IMAGE + listBean.getFigure()).
                into(holder.iv_figure);
        holder.tv_origin_price.setText("￥" + listBean.getOrigin_price());
        holder.tv_cover_price.setText("￥" + listBean.getCover_price());
    }

    /**
     * 一个秒杀的viewholder
     */
    class SeckillItemViewHolder extends RecyclerView.ViewHolder {
        private ImageView iv_figure;
        private TextView tv_cover_price;
        private TextView tv_origin_price;
        private LinearLayout ll_root;


        public SeckillItemViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_figure = itemView.findViewById(R.id.iv_figure);
            tv_cover_price = itemView.findViewById(R.id.tv_cover_price);
            tv_origin_price = itemView.findViewById(R.id.tv_origin_price);
            ll_root = itemView.findViewById(R.id.ll_root);
            /**
             * 单击事件
             * 回调onItemClick 并传递当前点击的秒杀item位置
             *
             */
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onSeckillRecyclerView != null) {
                        onSeckillRecyclerView.onItemClick(getLayoutPosition());//回调onItemClick 并传递当前点击的秒杀item位置
                    }
                }
            });
        }
    }

    /**
     * 秒杀item单击调用的类的接口
     */
    public interface OnSeckillRecyclerView {
        public void onItemClick(int position);
    }

    public void setOnSeckillRecyclerView(OnSeckillRecyclerView onSeckillRecyclerView) {
        this.onSeckillRecyclerView = onSeckillRecyclerView;
    }
}
