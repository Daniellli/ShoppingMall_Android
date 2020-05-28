package com.example.xsc238.shoppingcart.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.xsc238.R;
import com.example.xsc238.home.bean.GoodsBean;
import com.example.xsc238.shoppingcart.utils.CartStorage;
import com.example.xsc238.shoppingcart.view.AddSubView;
import com.example.xsc238.utils.Constants;

import java.util.List;

/**
 * 作者：许少聪
 * 邮箱：18750910084@163.com
 * 作用：
 */
public class ShoppingCartRecyclerViewAapter extends RecyclerView.Adapter<ShoppingCartRecyclerViewAapter.CartItemViewHolder> {


    private final CheckBox checkAllEdit;
    private TextView tvShopcartTotal; // 完成视图的
    private CheckBox checkboxAll; //完成视图的
    private List<GoodsBean> data;
    private Context mContext;
    private LayoutInflater inflater;

    /**
     * 构造函数
     *
     * @param allData
     * @param mContext
     * @param inflater
     */
    public ShoppingCartRecyclerViewAapter(List<GoodsBean> allData, Context mContext, LayoutInflater inflater, TextView tvShopcartTotal, CheckBox checkboxAll, CheckBox checkAllEdit) {
        this.data = allData;
        this.mContext = mContext;
        this.inflater = inflater;
        this.tvShopcartTotal = tvShopcartTotal;
        this.checkboxAll = checkboxAll;
        this.checkAllEdit = checkAllEdit;
        check_all();//初始默认全选
        itemListender();
        checkAllBoxListener();
    }

    /**
     * 全选按钮监听事件
     *
     * @param
     */
    private void checkAllBoxListener() {
        /**
         * 完成视图的全选
         */
        checkboxAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkboxAll.isChecked()) {
                    checkboxAll.setChecked(true);
                    check_all();
                } else {
                    checkboxAll.setChecked(false);
                    check_none();
                }
            }
        });

        /**
         * 编辑视图的全选
         */
        checkAllEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkAllEdit.isChecked()) {
                    checkAllEdit.setChecked(true);
                    check_all();
                } else {
                    checkAllEdit.setChecked(false);
                    check_none();
                }
            }
        });
    }

    /**
     * 全选
     * 1. 修改所有data的状态
     * <p>
     * 2.修改 界面
     * <p>
     * 3.修改总价
     */
    public void check_all() {
        for (int i = 0; i < data.size(); i++) {
            if (!data.get(i).isSelected()) {
                data.get(i).setSelected(true);
                notifyItemChanged(i);
            }
        }
        checkboxAll.setChecked(true);
        checkAllEdit.setChecked(true);
        showTotal();
    }

    /**
     * 取消全选
     * 1. 修改所有data的状态
     * <p>
     * 2.修改 界面
     * <p>
     * 3.修改总价
     */
    public void check_none() {
        for (int i = 0; i < data.size(); i++) {
            if (data.get(i).isSelected()) {
                data.get(i).setSelected(false);
                notifyItemChanged(i);
            }
        }
        checkboxAll.setChecked(false);
        checkAllEdit.setChecked(false);
        showTotal();
    }


    /**
     * 单个商品的点击事件
     * 1.修改点击商品的selected
     * 1.1、修改缓存
     * 2.修改cbGov的check的状态 ---latter
     * 3.修改总价
     * 4.刷新页面
     */
    private void itemListender() {
        setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                GoodsBean goodsBean = data.get(position);
                goodsBean.setSelected(!goodsBean.isSelected());
                CartStorage.getInstance().updateData(goodsBean);
                showTotal();
                checkbox_state_change();
                notifyItemChanged(position);
            }
        });
    }

    /**
     * 判断全选按钮的状态是否因为单个item改变而改变
     */
    public void checkbox_state_change() {
        for (int i = 0; i < data.size(); i++) {
            if (!data.get(i).isSelected()) {
                //未全选
                //修改
                checkboxAll.setChecked(false);
                checkAllEdit.setChecked(false);
                return;
            }
        }
        checkboxAll.setChecked(true);
        checkAllEdit.setChecked(true);
    }


    /**
     * 显示总价
     */
    private void showTotal() {
        tvShopcartTotal.setText("￥" + getTotal());
    }

    /**
     * 计算总价
     *
     * @return
     */
    public double getTotal() {
        double total = 0;
        for (int i = 0; i < data.size(); i++) {
            GoodsBean goodsBean = data.get(i);
            if (goodsBean.isSelected()) {
                total += Double.parseDouble(goodsBean.getCovor_price()) * goodsBean.getNumber();
            }
        }
        return total;
    }


    @NonNull
    @Override
    public CartItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CartItemViewHolder(inflater.inflate(R.layout.item_shop_cart, null, false));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public void onBindViewHolder(@NonNull CartItemViewHolder holder, int position) {
        GoodsBean goodsBean = data.get(position);
        holder.cbGov.setChecked(goodsBean.isSelected());
        Glide.with(mContext).load(Constants.Base_URl_IMAGE + goodsBean.getFigure()).into(holder.ivGov);
        holder.tvDescGov.setText(goodsBean.getName());
        holder.tvPriceGov.setText(goodsBean.getCovor_price());
        holder.numberAddSubView.setMaxValue(5);
        holder.numberAddSubView.setMinValue(1);
        holder.numberAddSubView.setValue(goodsBean.getNumber());
        /**
         * 商品选购数量改变
         * 1修改本地data数据
         * 2修改缓存
         * 3.改变合计
         * 4.页面不用刷新
         */
        holder.numberAddSubView.setOnNumberChangeListener(new AddSubView.OnNumberChangeListener() {
            @Override
            public void onNumberChanged(int number) {
                /**
                 * 是否是被删掉的商品
                 */
                if (position < data.size()) {
                    data.get(position).setNumber(number);
                    CartStorage.getInstance().updateData(data.get(position));
                    showTotal();
                }
            }
        });
    }

    /**
     * 将选中的商品删除
     * 1.本地删除
     * 2.删除缓存
     * 3.更新界面
     * <p>
     * 4.返回删除后 购物车商品的数量
     */
    public int deleteData() {
        if (data != null && data.size() > 0)
            for (int i = data.size() - 1; i >= 0; i--) {
                GoodsBean goodsBean = data.get(i);
                if (goodsBean.isSelected()) {
                    //删除这个
                    data.remove(goodsBean);
                    CartStorage.getInstance().deleteData(goodsBean);
                    notifyItemRemoved(i);
                }
            }
        checkbox_state_change();
        return data.size();
    }

    /**
     * View Holder
     */
    class CartItemViewHolder extends RecyclerView.ViewHolder {
        private CheckBox cbGov;// 商品 的CheckBox
        private ImageView ivGov;//商品的图片
        private TextView tvDescGov;//商品名字
        private TextView tvPriceGov;//商品价格
        private AddSubView numberAddSubView;//增减商品的控件 --DIY

        /**
         * Find the Views in the layout<br />
         * <br />
         * Auto-created on 2020-05-24 12:12:47 by Android Layout Finder
         * (http://www.buzzingandroid.com/tools/android-layout-finder)
         */
        private void findViews(View itemView) {
            cbGov = (CheckBox) itemView.findViewById(R.id.cb_gov);
            ivGov = (ImageView) itemView.findViewById(R.id.iv_gov);
            tvDescGov = (TextView) itemView.findViewById(R.id.tv_desc_gov);
            tvPriceGov = (TextView) itemView.findViewById(R.id.tv_price_gov);
            numberAddSubView = (AddSubView) itemView.findViewById(R.id.numberAddSubView);
        }


        public CartItemViewHolder(@NonNull View itemView) {
            super(itemView);
            findViews(itemView);

            /**
             * 单项商品点击后调用接口的方法
             */
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null) {
                        onItemClickListener.onItemClick(getLayoutPosition());
                    }
                }
            });
        }
    }


    /**
     * 单项商品点击事件接口
     */
    public interface OnItemClickListener {
        public void onItemClick(int position);
    }

    private OnItemClickListener onItemClickListener;


    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
