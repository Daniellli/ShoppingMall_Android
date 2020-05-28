package com.example.xsc238.app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.xsc238.R;
import com.example.xsc238.home.adapter.HomeFragmentAdapter;
import com.example.xsc238.home.bean.GoodsBean;
import com.example.xsc238.shoppingcart.utils.CartStorage;
import com.example.xsc238.utils.Constants;

import java.io.Serializable;

public class GoodsInfoActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageButton ibGoodInfoBack;
    private ImageButton ibGoodInfoMore;
    private ImageView ivGoodInfoImage;
    private TextView tvGoodInfoName;
    private TextView tvGoodInfoDesc;
    private TextView tvGoodInfoPrice;
    private TextView tvGoodInfoStore;
    private TextView tvGoodInfoStyle;
    private WebView wbGoodInfoMore;
    private LinearLayout llGoodsRoot;
    private TextView tvGoodInfoCallcenter;
    private TextView tvGoodInfoCollection;
    private TextView tvGoodInfoCart;
    private Button btnGoodInfoAddcart;
    private TextView tvMoreShare;
    private TextView tvMoreSearch;
    private TextView tvMoreHome;
    private Button btnMore;
    private LinearLayout ll_root_more;
    private GoodsBean goodsBean;//当前页面的商品信息类

    /**
     * Find the Views in the layout<br />
     * <br />
     * Auto-created on 2020-05-18 18:14:51 by Android Layout Finder
     * (http://www.buzzingandroid.com/tools/android-layout-finder)
     */
    private void findViews() {
        ibGoodInfoBack = (ImageButton) findViewById(R.id.ib_good_info_back);
        ibGoodInfoMore = (ImageButton) findViewById(R.id.ib_good_info_more);
        ivGoodInfoImage = (ImageView) findViewById(R.id.iv_good_info_image);//商品图片
        tvGoodInfoName = (TextView) findViewById(R.id.tv_good_info_name);//商品名字
        tvGoodInfoDesc = (TextView) findViewById(R.id.tv_good_info_desc);
        tvGoodInfoPrice = (TextView) findViewById(R.id.tv_good_info_price);//商品价格
        tvGoodInfoStore = (TextView) findViewById(R.id.tv_good_info_store);
        tvGoodInfoStyle = (TextView) findViewById(R.id.tv_good_info_style);
        wbGoodInfoMore = (WebView) findViewById(R.id.wb_good_info_more);//第二个滚动视图  主要是网页数据
        llGoodsRoot = (LinearLayout) findViewById(R.id.ll_goods_root);
        tvGoodInfoCallcenter = (TextView) findViewById(R.id.tv_good_info_callcenter);
        tvGoodInfoCollection = (TextView) findViewById(R.id.tv_good_info_collection);
        tvGoodInfoCart = (TextView) findViewById(R.id.tv_good_info_cart);
        btnGoodInfoAddcart = (Button) findViewById(R.id.btn_good_info_addcart);
        ll_root_more = findViewById(R.id.ll_root);

        tvMoreShare = (TextView) findViewById(R.id.tv_more_share);
        tvMoreSearch = (TextView) findViewById(R.id.tv_more_search);
        tvMoreHome = (TextView) findViewById(R.id.tv_more_home);
        btnMore = (Button) findViewById(R.id.btn_more);


        ibGoodInfoBack.setOnClickListener(this);//返回上一级
        ibGoodInfoMore.setOnClickListener(this);//更多消息 ---弹出顶部选择菜单
        btnGoodInfoAddcart.setOnClickListener(this);//加到购物车
        btnMore.setOnClickListener(this);//返回商品详情  与ibGoodInfoBack相互作用
        tvGoodInfoCollection.setOnClickListener(this);//收藏
        tvGoodInfoCallcenter.setOnClickListener(this);//联系客服
        tvGoodInfoCart.setOnClickListener(this);//查看购物车
        tvMoreShare.setOnClickListener(this);//分享
        tvMoreSearch.setOnClickListener(this);//搜索
        tvMoreHome.setOnClickListener(this);//首页
    }

    /**
     * Handle button click events<br />
     * <br />
     * Auto-created on 2020-05-18 18:14:51 by Android Layout Finder
     * (http://www.buzzingandroid.com/tools/android-layout-finder)
     */
    @Override
    public void onClick(View v) {
        if (v == ibGoodInfoBack) {
            // Handle clicks for ibGoodInfoBack
            finish();
        } else if (v == ibGoodInfoMore) {
            // Handle clicks for ibGoodInfoMore
            Toast.makeText(this, "更多", Toast.LENGTH_SHORT).show();
            ll_root_more.setVisibility(View.VISIBLE);
        } else if (v == btnGoodInfoAddcart) {
            // Handle clicks for btnGoodInfoAddcart
            CartStorage.getInstance().addData(goodsBean);
            Toast.makeText(this, "购物车成功", Toast.LENGTH_SHORT).show();
        } else if (v == btnMore) {
            // Handle clicks for btnMore
            Toast.makeText(this, "返回", Toast.LENGTH_SHORT).show();
            ll_root_more.setVisibility(View.GONE);
        } else if (v == tvGoodInfoCollection) {
            Toast.makeText(this, "收藏", Toast.LENGTH_SHORT).show();
        } else if (v == tvGoodInfoCallcenter) {
            Toast.makeText(this, "联系客服", Toast.LENGTH_SHORT).show();
        } else if (v == tvGoodInfoCart) {
            Toast.makeText(this, "查看购物车", Toast.LENGTH_SHORT).show();
        } else if (v == tvMoreShare) {
            Toast.makeText(this, "分享", Toast.LENGTH_SHORT).show();
        } else if (v == tvMoreSearch) {
            Toast.makeText(this, "搜索", Toast.LENGTH_SHORT).show();
        } else if (v == tvMoreHome) {
            Toast.makeText(this, "首页", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_info);
        getSupportActionBar().hide();
        findViews();

        Intent intent = getIntent();
        goodsBean = (GoodsBean) intent.getSerializableExtra(HomeFragmentAdapter.GOODSBEAN);
        setDataFormView(goodsBean);

    }

    private void setDataFormView(GoodsBean goodsBean) {
        Glide.with(this).load(Constants.Base_URl_IMAGE +
                goodsBean.getFigure()).into(ivGoodInfoImage);
        tvGoodInfoName.setText(goodsBean.getName());
        tvGoodInfoPrice.setText("￥" + goodsBean.getCovor_price());
        setWebView(goodsBean.getProduct_id());

    }

    private void setWebView(String product_id) {
        if (product_id != null) {
            wbGoodInfoMore.loadUrl("http://www.atguigu.com");
            //覆盖WebView默认使用第三方或系统默认浏览器打开网页的行为，使网页用WebView打开
            wbGoodInfoMore.setWebViewClient(new WebViewClient() {
                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    //返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器 view.loadUrl(url); return true; } });
                    view.loadUrl(url);
                    return true;
                }
            });

            //启用支持javascript
            WebSettings settings = wbGoodInfoMore.getSettings();
            settings.setJavaScriptEnabled(true);
            //优先使用缓存
            settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        }
    }


}
