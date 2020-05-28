package com.example.xsc238.home.adapter.viewholder;

import android.content.Context;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.xsc238.R;
import com.example.xsc238.home.adapter.HomeFragmentAdapter;
import com.example.xsc238.home.adapter.RecommendRecyclerViewAdapter;
import com.example.xsc238.home.bean.GoodsBean;
import com.example.xsc238.home.bean.ResultBeanData;

import java.util.List;

/**
 * 作者：许少聪
 * 邮箱：18750910084@163.com
 * 作用：
 */
public class RecommendViewHolder extends RecyclerView.ViewHolder {
    private TextView tv_more_recommend;
    private RecyclerView rv_recommend;
    private Context mContext;
    private RecommendRecyclerViewAdapter recommendRecyclerViewAdapter;

    public RecommendViewHolder(@NonNull View itemView, Context mContect) {
        super(itemView);
        tv_more_recommend = itemView.findViewById(R.id.tv_more_recommend);
        rv_recommend = itemView.findViewById(R.id.rv_recommend);
        this.mContext = mContect;
    }

    /**
     * 绑定数据
     *
     * @param recommend_info
     */
    public void setData(List<ResultBeanData.ResultBean.RecommendInfoBean> recommend_info) {
        recommendRecyclerViewAdapter = new RecommendRecyclerViewAdapter(mContext, recommend_info);
        rv_recommend.setAdapter(recommendRecyclerViewAdapter);
        rv_recommend.setLayoutManager(new GridLayoutManager(mContext, 3));
        initListener(recommend_info);
    }

    /**
     * 初始化监听器
     */
    private void initListener(List<ResultBeanData.ResultBean.RecommendInfoBean> recommend_info) {
        tv_more_recommend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "你点击了查看更多", Toast.LENGTH_SHORT).show();
            }
        });

        recommendRecyclerViewAdapter.setOnRecommendItemClick(new RecommendRecyclerViewAdapter.OnRecommendItemClick() {
            @Override
            public void onRecommendItemClick(int position) {
                Toast.makeText(mContext, "position ==" + position, Toast.LENGTH_SHORT).show();
                ResultBeanData.ResultBean.RecommendInfoBean recommendInfoBean = recommend_info.get(position);
                GoodsBean goodsBean = new GoodsBean(recommendInfoBean.getName(),
                        recommendInfoBean.getCover_price(), recommendInfoBean.getFigure(),
                        recommendInfoBean.getProduct_id());
                HomeFragmentAdapter.startGoodsInfoActivity(goodsBean);//跳转只商品详情页面
            }
        });
    }
}
