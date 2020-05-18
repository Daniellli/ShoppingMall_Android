package com.example.xsc238.home.adapter;

import android.content.Context;
import android.util.Log;
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
public class RecommendRecyclerViewAdapter extends RecyclerView.Adapter<RecommendRecyclerViewAdapter.RecommendItemViewHolder> {

    public interface OnRecommendItemClick {
        public void onRecommendItemClick(int position);
    }

    private OnRecommendItemClick onRecommendItemClick;
    private final List<ResultBeanData.ResultBean.RecommendInfoBean> recommend_info;
    private final Context mContext;
    private final String TAG = getClass().getSimpleName();

    public void setOnRecommendItemClick(OnRecommendItemClick onRecommendItemClick) {
        this.onRecommendItemClick = onRecommendItemClick;
    }

    public RecommendRecyclerViewAdapter(Context mContext, List<ResultBeanData.ResultBean.RecommendInfoBean> recommend_info) {
        this.mContext = mContext;
        this.recommend_info = recommend_info;
        Log.i(TAG, "RecommendRecyclerViewAdapter: ");
    }

    @NonNull
    @Override
    public RecommendItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.i(TAG, "onCreateViewHolder: ");
        return new RecommendItemViewHolder(
                LayoutInflater.from(mContext)
                        .inflate(R.layout.item_recommend_recyclerview_view,
                                null, false));
    }

    @Override
    public int getItemCount() {
        return recommend_info.size();
    }

    @Override
    public void onBindViewHolder(@NonNull RecommendItemViewHolder holder, int position) {
        Log.i(TAG, "onBindViewHolder: ");
        ResultBeanData.ResultBean.RecommendInfoBean recommendInfoBean = recommend_info.get(position);
        Glide.with(mContext).load(Constants.Base_URl_IMAGE + recommendInfoBean.getFigure())
                .into(holder.iv_recommend);
        holder.tv_name.setText(recommendInfoBean.getName());
        holder.tv_price.setText("￥" + recommendInfoBean.getCover_price());
    }

    /**
     * 单款推荐的vieWholder
     */
    class RecommendItemViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_recommend)
        ImageView iv_recommend;
        @BindView(R.id.tv_name)
        TextView tv_name;
        @BindView(R.id.tv_price)
        TextView tv_price;

        public RecommendItemViewHolder(@NonNull View itemView) {
            super(itemView);
            Log.i(TAG, "RecommendItemViewHolder: ");
            ButterKnife.bind(this, itemView);//将这个类的成员变量与 ItemView布局绑定
            /**
             * 点击事件 回调适配器 的onRecommendItemClick 接口成员变量
             * 该接口向外提供
             */
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onRecommendItemClick != null) {
                        onRecommendItemClick.onRecommendItemClick(getLayoutPosition());
                    }
                }
            });


        }
    }


}

