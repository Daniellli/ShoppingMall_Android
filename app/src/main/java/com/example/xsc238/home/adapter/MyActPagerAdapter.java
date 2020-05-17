package com.example.xsc238.home.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.example.xsc238.home.bean.ResultBeanData;
import com.example.xsc238.utils.Constants;

import java.util.List;

/**
 * 作者：许少聪
 * 邮箱：18750910084@163.com
 * 作用：活动pagerView的适配器
 */
public class MyActPagerAdapter extends PagerAdapter {
    public interface OnPagerActListner {
        public void onPagerClick(int position);
    }

    private Context mContext;
    private List<ResultBeanData.ResultBean.ActInfoBean> act_info;
    public OnPagerActListner onPagerActListner;

    public void setOnPagerActListner(OnPagerActListner onPagerActListner) {
        this.onPagerActListner = onPagerActListner;
    }

    public MyActPagerAdapter(Context mContext, List<ResultBeanData.ResultBean.ActInfoBean> act_info) {
        this.mContext = mContext;
        this.act_info = act_info;
    }

    /**
     * 总的页面数
     *
     * @return
     */
    @Override
    public int getCount() {
        return act_info.size();
    }

    /**
     * @param view
     * @param object instantiateItem返回的对象
     * @return
     */
    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    /**
     * 初始化图片
     *
     * @param container
     * @param position
     * @return
     */
    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        ResultBeanData.ResultBean.ActInfoBean actInfoBean = act_info.get(position);
        ImageView imageView = new ImageView(mContext);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);//拉伸
        Glide.with(mContext).load(Constants.Base_URl_IMAGE + actInfoBean.getIcon_url()).into(imageView);
        container.addView(imageView);
        /**
         *Act pager单击事件 回调 onPagerActListner
         */
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onPagerActListner != null) {
                    onPagerActListner.onPagerClick(position);
                }
            }
        });
        return imageView;
    }

    /**
     * 销毁图片
     *
     * @param container
     * @param position
     * @param object
     */
    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
