package com.example.xsc238.home.adapter.viewholder;

import android.content.Context;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.xsc238.R;
import com.example.xsc238.home.adapter.ChannelAdapter;
import com.example.xsc238.home.bean.ResultBeanData;

import java.util.List;

/**
 * 作者：许少聪
 * 邮箱：18750910084@163.com
 * 作用：
 */
public class ChannelViewHolder extends RecyclerView.ViewHolder {
    public RecyclerView rv_channel;
    private Context mContext;
    private ChannelAdapter channelAdapter;

    public ChannelViewHolder(@NonNull View itemView, Context mContext) {
        super(itemView);
        this.rv_channel = itemView.findViewById(R.id.rv_channel);
        this.mContext = mContext;
    }

    /**
     * 绑定数据到布局
     *
     * @param channel_info
     */
    public void setData(List<ResultBeanData.ResultBean.ChannelInfoBean> channel_info) {
        channelAdapter = new ChannelAdapter(channel_info, mContext);
        rv_channel.setAdapter(channelAdapter);
        rv_channel.setLayoutManager(new GridLayoutManager(mContext, 5));
        initListener();//初始化监听器
    }

    /**
     * 初始化监听器
     */
    private void initListener() {
        channelAdapter.setOnChannelItemClick(new ChannelAdapter.OnChannelItemClick() {
            @Override
            public void onChannelClick(int position) {
                Toast.makeText(mContext, "position==" + position, Toast.LENGTH_SHORT).show();
            }
        });
    }


}
