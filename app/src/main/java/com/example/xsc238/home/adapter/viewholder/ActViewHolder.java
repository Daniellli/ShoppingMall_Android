package com.example.xsc238.home.adapter.viewholder;

import android.content.Context;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.xsc238.R;
import com.example.xsc238.home.adapter.MyActPagerAdapter;
import com.example.xsc238.home.bean.ResultBeanData;
import com.zhy.magicviewpager.transformer.RotateDownPageTransformer;
import com.zhy.magicviewpager.transformer.ScaleInTransformer;

import java.util.List;

/**
 * 作者：许少聪
 * 邮箱：18750910084@163.com
 * 作用：活动的viewholder
 */
public class ActViewHolder extends RecyclerView.ViewHolder {

    private Context mConText;
    private ViewPager viewPager;
    private MyActPagerAdapter myActPagerAdapter;

    public ActViewHolder(@NonNull View itemView, Context mConText) {
        super(itemView);
        this.mConText = mConText;
        viewPager = itemView.findViewById(R.id.act_viewpager);
    }

    //给 sViVi
    public void setData(List<ResultBeanData.ResultBean.ActInfoBean> act_info) {
        myActPagerAdapter = new MyActPagerAdapter(mConText, act_info);
        viewPager.setAdapter(myActPagerAdapter);

        beautify_pager();//美化
        initListener();//初始化监听器
    }

    /**
     * 美化
     */
    private void beautify_pager() {
        viewPager.setPageMargin(20);//设置page间间距，自行根据需求设置
        viewPager.setOffscreenPageLimit(3);//>=3
//setPageTransformer 决定动画效果
        viewPager.setPageTransformer(true, new
                ScaleInTransformer());
    }

    /**
     * 初始化监听器
     */
    private void initListener() {
        myActPagerAdapter.setOnPagerActListner(new MyActPagerAdapter.OnPagerActListner() {
            @Override
            public void onPagerClick(int position) {
                Toast.makeText(mConText, "Position ==" + position, Toast.LENGTH_SHORT).show();
            }
        });
//        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//            @Override
//            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//
//            }
//
//            @Override
//            public void onPageSelected(int position) {
//                Toast.makeText(mConText, "position =="+position, Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int state) {
//                Toast.makeText(mConText, "state =="+state, Toast.LENGTH_SHORT).show();
//            }
//        });
    }
}
