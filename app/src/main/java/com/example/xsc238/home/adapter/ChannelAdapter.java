package com.example.xsc238.home.adapter;

/**
 * 作者：许少聪
 * 邮箱：18750910084@163.com
 * 作用：
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.xsc238.R;
import com.example.xsc238.home.bean.ResultBeanData;
import com.example.xsc238.utils.Constants;

import java.util.List;

/**
 * 频道recyclerView  适配器
 * ChannelAdapter
 */
public class ChannelAdapter extends RecyclerView.Adapter {
    public interface OnChannelItemClick {
        public void onChannelClick(int position);
    }

    private OnChannelItemClick onChannelItemClick;
    private List<ResultBeanData.ResultBean.ChannelInfoBean> channel_info;
    private Context mContext;

    public void setOnChannelItemClick(OnChannelItemClick onChannelItemClick) {
        this.onChannelItemClick = onChannelItemClick;
    }

    public ChannelAdapter(List<ResultBeanData.ResultBean.ChannelInfoBean> channel_info, Context mContext) {
        this.channel_info = channel_info;
        this.mContext = mContext;
    }

    /**
     * ChannelItemViewHolder
     */
    class ChannelItemViewHolder extends RecyclerView.ViewHolder {
        private ImageView iv_channel;
        private TextView tv_channel;

        public ChannelItemViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_channel = itemView.findViewById(R.id.iv_channel);
            tv_channel = itemView.findViewById(R.id.tv_channel);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onChannelItemClick != null) {
                        onChannelItemClick.onChannelClick(getLayoutPosition());
                    }
                }
            });
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_channel, null, false);
        return new ChannelItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ChannelItemViewHolder channelItemViewHolder = (ChannelItemViewHolder) holder;
        ResultBeanData.ResultBean.ChannelInfoBean channelInfoBean = channel_info.get(position);
        Glide.with(mContext).load(Constants.Base_URl_IMAGE + channelInfoBean.getImage()).into(channelItemViewHolder.iv_channel);
        channelItemViewHolder.tv_channel.setText(channelInfoBean.getChannel_name());
    }

    @Override
    public int getItemCount() {
        return channel_info.size();
    }
}