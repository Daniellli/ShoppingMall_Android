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
import com.example.xsc238.home.adapter.HotRecyclerViewAdapter;
import com.example.xsc238.home.bean.ResultBeanData;

import org.w3c.dom.Text;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 作者：许少聪
 * 邮箱：18750910084@163.com
 * 作用：
 */
public class HotViewHolder extends RecyclerView.ViewHolder {


    private final Context mContext;
    @BindView(R.id.tv_more_hot)
    TextView tv_more_hot;
    @BindView(R.id.rv_hot)
    RecyclerView rv_hot;
    private HotRecyclerViewAdapter hotRecyclerViewAdapter;

    public HotViewHolder(View view, Context mContext) {
        super(view);
        this.mContext = mContext;
        ButterKnife.bind(this, view);

    }

    /**
     * 绑定数据
     * rv_hot item之间的间隙还没弄
     *
     * @param hot_info
     */
    public void setData(List<ResultBeanData.ResultBean.HotInfoBean> hot_info) {
        hotRecyclerViewAdapter = new HotRecyclerViewAdapter(mContext, hot_info);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, 2);

        rv_hot.setAdapter(hotRecyclerViewAdapter);
        rv_hot.setLayoutManager(gridLayoutManager);

        initListener();


    }

    private void initListener() {
        hotRecyclerViewAdapter.setOnHotItemClick(new HotRecyclerViewAdapter.OnHotItemClick() {
            @Override
            public void onHotItemClick(int position) {
                Toast.makeText(mContext, "position ==" + position, Toast.LENGTH_SHORT).show();
                HomeFragmentAdapter.startGoodsInfoActivity();//跳转只商品详情页面
            }
        });

        tv_more_hot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "你点击了更多", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
