package com.example.xsc238.home.adapter.viewholder;

import android.content.Context;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.xsc238.R;
import com.example.xsc238.home.bean.ResultBeanData;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.List;


/**
 * 作者：许少聪
 * 邮箱：18750910084@163.com
 * 作用：横幅的viewholder
 */


/**
 * 横幅 布局viewholder
 * 并且处理布局和数据绑定的任务
 */
public class BannerViewHolder extends RecyclerView.ViewHolder {
    private Context mContext;
    private View itemView;
    private Banner banner;

    /**
     * @param itemView 布局文件
     * @param mContext
     */
    public BannerViewHolder(@NonNull View itemView, Context mContext) {
        super(itemView);
        banner = itemView.findViewById(R.id.banner);
        this.mContext = mContext;
        this.itemView = itemView;
    }

    /**
     * 布局和数据绑定
     * 设置数据：图片 监听,等等
     *
     * @param banner_info banner所需要的所有数据
     */
    public void setData(List<ResultBeanData.ResultBean.BannerInfoBean> banner_info) {
        setBannerInfo(banner_info);
    }

    /**
     * 设置banner数据 以及监听
     *
     * @param banner_info
     */
    private void setBannerInfo(List<ResultBeanData.ResultBean.BannerInfoBean> banner_info) {
        List<String> imgUrls = new ArrayList<>();
        //获取图片名字
        for (int i = 0; i < banner_info.size(); i++) {
            imgUrls.add(banner_info.get(i).getImage());
        }
        banner.setBannerAnimation(Transformer.Accordion);//设置滑动动画  --手风琴
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        banner.setImageLoader(new MyImagerLoad());
        banner.setImages(imgUrls);
        initListener();
        banner.start();
    }

    /**
     * 初始化监听器
     */
    private void initListener() {
        //横幅点击事件
        banner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                Toast.makeText(mContext, "position ==" + position, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
