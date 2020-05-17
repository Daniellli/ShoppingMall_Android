package com.example.xsc238.type.fragment;

import android.graphics.Color;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.example.xsc238.base.fragment.BaseFragment;

/**
 * 作者：许少聪
 * 作用：类型fragment
 * 邮箱：18750910084@163.com
 */

public class TypeFragment extends BaseFragment {
    private final String TAG = getClass().getSimpleName();
    private TextView textView;


    @Override
    public View initView() {
        Log.i(TAG, "类型视图被初始化了");
        textView = new TextView(mContext);
        textView.setGravity(Gravity.CENTER);
        textView.setTextSize(25);
        textView.setTextColor(Color.RED);
        return textView;
    }


    @Override
    public void initData() {
        super.initData();
        Log.e(TAG, "类型数据被初始化了");
        textView.setText("类型");
    }
}
