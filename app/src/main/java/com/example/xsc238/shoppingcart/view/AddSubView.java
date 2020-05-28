package com.example.xsc238.shoppingcart.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.xsc238.R;

/**
 * 作者：许少聪
 * 邮箱：18750910084@163.com
 * 作用：
 */
public class AddSubView extends LinearLayout implements View.OnClickListener {


    private ImageView btnSub;
    private TextView tvCount;
    private ImageView btnAdd;


    private int value = 1;//当前商品数量
    private int minValue = 1;//最小值
    private int maxValue = 10;//最大值--根据库存动态设定


    /**
     * Find the Views in the layout<br />
     * <br />
     * Auto-created on 2020-05-24 09:58:52 by Android Layout Finder
     * (http://www.buzzingandroid.com/tools/android-layout-finder)
     */
    private void findViews() {
        btnSub = (ImageView) findViewById(R.id.btn_sub);
        tvCount = (TextView) findViewById(R.id.tv_count);
        btnAdd = (ImageView) findViewById(R.id.btn_add);
    }


    public AddSubView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AddSubView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        View.inflate(context, R.layout.add_sub_layout, this);
        findViews();

        btnSub.setOnClickListener(this);
        btnAdd.setOnClickListener(this);
        tvCount.setText(value + "");
    }

    public AddSubView(Context context) {
        this(context, null);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_sub:
                value = --value < minValue ? minValue : value;
                break;
            case R.id.btn_add:
                value = ++value > maxValue ? maxValue : value;
                break;
        }
        updateValue();
    }

    /**
     * 更新值
     */
    private void updateValue() {
        tvCount.setText(value + "");
        if (onNumberChangeListener != null) {
            onNumberChangeListener.onNumberChanged(value);
        }

    }


    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
        updateValue();
    }

    public int getMinValue() {
        return minValue;
    }

    public void setMinValue(int minValue) {
        this.minValue = minValue;
    }

    public int getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(int maxValue) {
        this.maxValue = maxValue;
    }

    /**
     * 对外提供获取当前商品数量的接口接口
     */
    public interface OnNumberChangeListener {
        public void onNumberChanged(int number);//number为改变后的数量
    }

    public OnNumberChangeListener onNumberChangeListener;

    public void setOnNumberChangeListener(OnNumberChangeListener onNumberChangeListener) {
        this.onNumberChangeListener = onNumberChangeListener;
    }
}
