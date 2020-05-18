package com.example.xsc238.home.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.xsc238.R;
import com.example.xsc238.home.bean.ResultBeanData;
import com.example.xsc238.utils.Constants;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 作者：许少聪
 * 邮箱：18750910084@163.com
 * 作用：
 */
public class HotRecyclerViewAdapter extends RecyclerView.Adapter<HotRecyclerViewAdapter.HotItemViewHolder> {

    public interface OnHotItemClick {
        public void onHotItemClick(int position);
    }

    private OnHotItemClick onHotItemClick;
    private final List<ResultBeanData.ResultBean.HotInfoBean> hot_info;
    private final Context mContext;

    public void setOnHotItemClick(OnHotItemClick onHotItemClick) {
        this.onHotItemClick = onHotItemClick;
    }

    public HotRecyclerViewAdapter(Context mContext, List<ResultBeanData.ResultBean.HotInfoBean> hot_info) {
        this.mContext = mContext;
        this.hot_info = hot_info;
    }

    @NonNull
    @Override
    public HotItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new HotItemViewHolder(LayoutInflater.from(mContext)
                .inflate(R.layout.item_hot_recyclerview_view, null, false));
    }

    @Override
    public void onBindViewHolder(@NonNull HotItemViewHolder holder, int position) {
        ResultBeanData.ResultBean.HotInfoBean hotInfoBean = hot_info.get(position);
        Glide.with(mContext)
                .load(Constants.Base_URl_IMAGE + hotInfoBean.getFigure())
                .into(holder.iv_hot);
        holder.tv_name.setText(hotInfoBean.getName());
        holder.tv_price.setText("￥" + hotInfoBean.getCover_price());
    }

    @Override
    public int getItemCount() {
        return hot_info.size();
    }

    class HotItemViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_hot)
        ImageView iv_hot;
        @BindView(R.id.tv_name)
        TextView tv_name;
        @BindView(R.id.tv_price)
        TextView tv_price;

        public HotItemViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onHotItemClick != null) {
                        onHotItemClick.onHotItemClick(getLayoutPosition());
                    }
                }
            });
        }
    }
}
