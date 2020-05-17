package com.example.xsc238.home.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.xsc238.R;
import com.example.xsc238.home.adapter.viewholder.ActViewHolder;
import com.example.xsc238.home.adapter.viewholder.BannerViewHolder;
import com.example.xsc238.home.adapter.viewholder.ChannelViewHolder;
import com.example.xsc238.home.adapter.viewholder.SeckillViewHolder;
import com.example.xsc238.home.bean.ResultBeanData;

/**
 * 作者：许少聪
 * 邮箱：18750910084@163.com
 * 作用：整个主页的适配器
 */
public class HomeFragmentAdapter extends RecyclerView.Adapter {
    /**
     * 横幅广告
     */
    public static final int BANNER = 0;
    /**
     * 频道
     */
    public static final int CHANNEL = 1;
    /**
     * 活动
     */
    public static final int ACT = 2;

    /**
     * 秒杀
     */
    public static final int SECKILL = 3;
    /**
     * 推荐
     */
    public static final int RECOMMEND = 4;
    /**
     * 热卖
     */
    public static final int HOT = 5;

    /**
     * 当前类型
     */
    public int currentType = BANNER;
    private Context mContext;
    private ResultBeanData.ResultBean resultBean;
    private LayoutInflater layoutInflater;


    public HomeFragmentAdapter(Context mContext, ResultBeanData.ResultBean resultBean) {
        this.mContext = mContext;
        this.resultBean = resultBean;
        layoutInflater = LayoutInflater.from(mContext);
    }

    /**
     * 获取当前处理的数据类型
     *
     * @param position
     * @return
     */
    @Override
    public int getItemViewType(int position) {
        switch (position) {
            case BANNER:
                currentType = BANNER;
                break;
            case CHANNEL:
                currentType = CHANNEL;
                break;
            case ACT:
                currentType = ACT;
                break;
            case SECKILL:
                currentType = SECKILL;
                break;
            case RECOMMEND:
                currentType = RECOMMEND;
                break;
            case HOT:
                currentType = HOT;
                break;
        }

        return currentType;
    }


    /**
     * @param parent
     * @param viewType 类型
     * @return
     */
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == BANNER) {
            return new BannerViewHolder(layoutInflater.inflate(R.layout.banner_viewpager,
                    null, false), mContext);
        } else if (viewType == CHANNEL) {
            return new ChannelViewHolder(layoutInflater.inflate(R.layout.channel_item,
                    null, false), mContext);
        } else if (viewType == ACT) {
            return new ActViewHolder(layoutInflater.inflate(R.layout.act_item, null, false), mContext);
        } else if (viewType == SECKILL) {
            return new SeckillViewHolder(layoutInflater.inflate(R.layout.seckill_item, null, false), mContext);
        }
        return null;
    }

    /**
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == BANNER) {
            BannerViewHolder bannerViewHolder = (BannerViewHolder) holder;
            bannerViewHolder.setData(resultBean.getBanner_info());
        } else if (getItemViewType(position) == CHANNEL) {
            ChannelViewHolder channelViewHolder = (ChannelViewHolder) holder;
            channelViewHolder.setData(resultBean.getChannel_info());
        } else if (getItemViewType(position) == ACT) {
            ActViewHolder actViewHolder = (ActViewHolder) holder;
            actViewHolder.setData(resultBean.getAct_info());
        } else if (getItemViewType(position) == SECKILL) {
            SeckillViewHolder seckillViewHolder = (SeckillViewHolder) holder;
            seckillViewHolder.setData(resultBean.getSeckill_info());
        }
    }

    @Override
    public int getItemCount() {
        return 4;//先完成第一个
    }
}
