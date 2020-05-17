package com.example.xsc238.home.adapter.viewholder;

import android.content.Context;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.xsc238.R;
import com.example.xsc238.home.adapter.SeckillRecyclerViewAdapter;
import com.example.xsc238.home.bean.ResultBeanData;
import com.example.xsc238.home.adapter.SeckillRecyclerViewAdapter.OnSeckillRecyclerView;

import java.util.Calendar;

/**
 * 作者：许少聪
 * 邮箱：18750910084@163.com
 * 作用：
 */
public class SeckillViewHolder extends RecyclerView.ViewHolder {
    private Context mContext;
    private TextView tv_time_seckill;
    private TextView tv_more_seckill;
    private RecyclerView rv_seckill;
    private SeckillRecyclerViewAdapter seckillRecyclerViewAdapter;


    public SeckillViewHolder(@NonNull View itemView, Context mContext) {
        super(itemView);
        this.mContext = mContext;
        tv_time_seckill = itemView.findViewById(R.id.tv_time_seckill);
        tv_more_seckill = itemView.findViewById(R.id.tv_more_seckill);
        rv_seckill = itemView.findViewById(R.id.rv_seckill);
    }

    /**
     * 绑定数据
     *
     * @param seckill_info
     */
    public void setData(ResultBeanData.ResultBean.SeckillInfoBean seckill_info) {
        seckillRecyclerViewAdapter = new SeckillRecyclerViewAdapter(seckill_info.getList(), mContext);
        rv_seckill.setAdapter(seckillRecyclerViewAdapter);
        rv_seckill.setLayoutManager(new LinearLayoutManager(mContext,
                LinearLayoutManager.HORIZONTAL, false));

//初始化监听器
        initListener();
    }

    private void initListener() {
        tv_more_seckill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "你点击了更多信息", Toast.LENGTH_SHORT).show();
            }
        });


        /**
         * 设置秒杀监听事件
         */
        seckillRecyclerViewAdapter.setOnSeckillRecyclerView(new OnSeckillRecyclerView() {
            @Override
            public void onItemClick(int position) {
                Toast.makeText(mContext, "position == " + position, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
