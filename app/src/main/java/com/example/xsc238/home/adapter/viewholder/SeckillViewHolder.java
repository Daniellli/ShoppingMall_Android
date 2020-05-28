package com.example.xsc238.home.adapter.viewholder;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.xsc238.R;
import com.example.xsc238.home.adapter.HomeFragmentAdapter;
import com.example.xsc238.home.adapter.SeckillRecyclerViewAdapter;
import com.example.xsc238.home.bean.GoodsBean;
import com.example.xsc238.home.bean.ResultBeanData;
import com.example.xsc238.home.adapter.SeckillRecyclerViewAdapter.OnSeckillRecyclerView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


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
    private long lastTime = 0;//秒杀剩余时间 单位毫秒
    private Handler Skeckillhandler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            lastTime -= 1000;
            tv_time_seckill.setText(new SimpleDateFormat("HH:mm:ss").format(new Date(lastTime)));
            removeMessages(0);
            Skeckillhandler.sendEmptyMessageDelayed(0, 1000);

            if (lastTime <= 0) {
                Skeckillhandler.removeCallbacksAndMessages(null);
            }

        }
    };


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
        /**
         * 秒杀剩余时间
         */
        lastTime = Integer.parseInt(seckill_info.getEnd_time()) - Integer.parseInt(seckill_info.getStart_time());
        tv_time_seckill.setText(new SimpleDateFormat("HH:mm:ss").format(new Date(lastTime)));
        Skeckillhandler.sendEmptyMessageDelayed(0, 1000);


//初始化监听器
        initListener(seckill_info);
    }

    private void initListener(ResultBeanData.ResultBean.SeckillInfoBean seckill_info) {
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
                ResultBeanData.ResultBean.SeckillInfoBean.ListBean listBean = seckill_info.getList().get(position);
                GoodsBean goodsBean = new GoodsBean(listBean.getName(),
                        listBean.getCover_price(), listBean.getFigure(),
                        listBean.getProduct_id());

                HomeFragmentAdapter.startGoodsInfoActivity(goodsBean);//跳转只商品详情页面
            }
        });
    }
}
