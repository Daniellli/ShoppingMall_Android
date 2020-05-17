package com.example.xsc238.home.fragment;


import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSON;
import com.example.xsc238.R;
import com.example.xsc238.base.fragment.BaseFragment;
import com.example.xsc238.home.adapter.HomeFragmentAdapter;
import com.example.xsc238.home.bean.ResultBeanData;
import com.example.xsc238.utils.Constants;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;
import okhttp3.Request;

/**
 * 作者：许少聪
 * 作用：主页fragment
 * 邮箱：18750910084@163.com
 */
public class HomeFragment extends BaseFragment {
    private final String TAG = getClass().getSimpleName();
    private RecyclerView rv_home;
    private ImageButton ib_top;
    private TextView tv_search_home;
    private TextView tv_message_home;
    private HomeFragmentAdapter homeFragmentAdapter;

    @Override
    public View initView() {
        Log.i(TAG, "主页视图被初始化了");
        View view = inflater.inflate(R.layout.fragment_home, null, false);
        rv_home = view.findViewById(R.id.rv_home);
        ib_top = view.findViewById(R.id.ib_top);
        tv_search_home = view.findViewById(R.id.tv_search_home);
        tv_message_home = view.findViewById(R.id.tv_message_home);
        inirListener();
        return view;
    }

    private void inirListener() {
        /**
         * 滚动到recyclerView顶部
         */
        ib_top.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rv_home.scrollToPosition(0);
            }
        });

        tv_search_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "搜索", Toast.LENGTH_SHORT).show();
            }
        });

        tv_message_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "跳转至信息中心", Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public void initData() {
        super.initData();
        initDataFromNet(Constants.HOME_URL);

        Log.e(TAG, "主页数据被初始化了");
    }

    /**
     * 从网络获取数据
     */
    private void initDataFromNet(String fromUrl) {
        String url = fromUrl;
        OkHttpUtils
                .get()
                .url(url)
                .build()
                .execute(new MyStringCallback());
    }

    /**
     * 网络获取数据后的回调类
     */
    public class MyStringCallback extends StringCallback {
        /**
         * 获取失败
         *
         * @param call
         * @param e
         * @param id
         */
        @Override
        public void onError(Call call, Exception e, int id) {
            Log.i(TAG, "onError: " + "id==" + id);
            e.printStackTrace();
        }

        /**
         * 获取成功
         *
         * @param response
         * @param id
         */
        @Override
        public void onResponse(String response, int id) {
            Log.i(TAG, "onResponse: " + response);
            processData(response);
        }
    }

    /**
     * 解析联网请求后的json数据
     *
     * @param json
     */
    private void processData(String json) {
        if (!TextUtils.isEmpty(json)) {
            ResultBeanData resultBeanData = (ResultBeanData) JSON.parseObject(json, ResultBeanData.class);
            ResultBeanData.ResultBean resultBean = resultBeanData.getResult();
            if (resultBean != null) {//有数据，设置适配器
                homeFragmentAdapter = new HomeFragmentAdapter(mContext, resultBean);
                rv_home.setAdapter(homeFragmentAdapter);
                rv_home.setLayoutManager(new GridLayoutManager(mContext, 1));
            } else {//没数据

            }

        }
    }
}
