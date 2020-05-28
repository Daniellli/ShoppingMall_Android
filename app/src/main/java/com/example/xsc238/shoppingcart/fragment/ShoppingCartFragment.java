package com.example.xsc238.shoppingcart.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alipay.sdk.app.AuthTask;
import com.alipay.sdk.app.EnvUtils;
import com.alipay.sdk.app.PayTask;
import com.example.xsc238.R;

import com.example.xsc238.base.fragment.BaseFragment;
import com.example.xsc238.home.bean.GoodsBean;
import com.example.xsc238.shoppingcart.adapter.ShoppingCartRecyclerViewAapter;
import com.example.xsc238.shoppingcart.alipay.PayResult;
import com.example.xsc238.shoppingcart.alipay.util.OrderInfoUtil2_0;
import com.example.xsc238.shoppingcart.utils.CartStorage;

import java.util.List;
import java.util.Map;


import static java.util.Collections.sort;

/**
 * 作者：许少聪
 * 作用：购物车fragment
 * 邮箱：18750910084@163.com
 */

public class ShoppingCartFragment extends BaseFragment implements View.OnClickListener {
    private final String STATE_EDIT = "完成";
    private final String STATE_COMPLETE = "编辑";

    private final String TAG = getClass().getSimpleName();
    private TextView textView;

    private TextView tvShopcartEdit;
    private RecyclerView recyclerview;
    private LinearLayout llCheckAll;
    private CheckBox checkboxAll;
    private TextView tvShopcartTotal;
    private Button btnCheckOut;
    private LinearLayout llDelete;
    private CheckBox cbAll;
    private Button btnDelete;
    private Button btnCollection;
    private ImageView ivEmpty;//购物成为空时的界面 控件
    private TextView tvEmptyCartTobuy;//购物成为空时的界面 控件--去逛逛
    private LinearLayout ll_empty_shopcart;


    private ShoppingCartRecyclerViewAapter shoppingCartRecyclerViewAapter;

    /**
     * Find the Views in the layout<br />
     * <br />
     * Auto-created on 2020-05-24 10:28:43 by Android Layout Finder
     * (http://www.buzzingandroid.com/tools/android-layout-finder)
     */
    private void findViews(View view) {
        tvShopcartEdit = (TextView) view.findViewById(R.id.tv_shopcart_edit);//右上角的编辑按钮
        recyclerview = (RecyclerView) view.findViewById(R.id.recyclerview);
        llCheckAll = (LinearLayout) view.findViewById(R.id.ll_check_all);//结算栏
        checkboxAll = (CheckBox) view.findViewById(R.id.checkbox_all);
        tvShopcartTotal = (TextView) view.findViewById(R.id.tv_shopcart_total);
        btnCheckOut = (Button) view.findViewById(R.id.btn_check_out);//结算按钮
        llDelete = (LinearLayout) view.findViewById(R.id.ll_delete);//删除|收藏栏
        cbAll = (CheckBox) view.findViewById(R.id.cb_all);//编辑视图的全选
        btnDelete = (Button) view.findViewById(R.id.btn_delete);//将选中发商品删除按钮
        btnCollection = (Button) view.findViewById(R.id.btn_collection);//将选中发商品收藏按钮
        ivEmpty = (ImageView) view.findViewById(R.id.iv_empty);
        tvEmptyCartTobuy = (TextView) view.findViewById(R.id.tv_empty_cart_tobuy);
        ll_empty_shopcart = view.findViewById(R.id.ll_empty_shopcart);//空购物车视图

        btnCheckOut.setOnClickListener(this);
        btnDelete.setOnClickListener(this);
        btnCollection.setOnClickListener(this);
        tvShopcartEdit.setOnClickListener(this);
    }


    /**
     * 界面的各个控件的单击事件
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        if (v == btnCheckOut) {//结算
            //    WX_Pay pay = new WX_Pay(mContext);
            //  pay.pay(str1,str2,str3);
            double total = shoppingCartRecyclerViewAapter.getTotal();
            payV2(total);

        } else if (v == btnDelete) {//将选中的商品删除
            int size = shoppingCartRecyclerViewAapter.deleteData();

            if (size == 0) {
                showEmptyCart();
            }

        } else if (v == btnCollection) {//将选中发商品收藏按钮
            // Handle clicks for btnCollection
        } else if (v == tvShopcartEdit) {//右上角的编辑按钮
            /**
             * 转成编辑视图
             * 1.隐藏结算 布局
             * 2. 显示 收藏和 删除布局
             */
            if (tvShopcartEdit.getText().equals(STATE_COMPLETE)) {
                //编辑状态
                tvShopcartEdit.setText(STATE_EDIT);
                showEditView();
            } else {
                //完成状态
                tvShopcartEdit.setText(STATE_COMPLETE);
                showCompleteView(View.VISIBLE, View.GONE);

            }
        } else if (v == tvEmptyCartTobuy) {// 空空如也 去逛逛

        }
    }


    /**
     * 初始化视图
     *
     * @return
     */
    @Override
    public View initView() {
        Log.i(TAG, "购物车视图被初始化了");
        View view = View.inflate(mContext, R.layout.fragment_shoppingcart, null);
        findViews(view);//绑定数据
        tvShopcartEdit.setText(STATE_COMPLETE);//完成视图
        return view;
    }

    /**
     * 显示完成视图
     * 1.界面切换
     * 2.商品全选
     *
     * @param visible
     * @param gone
     */
    private void showCompleteView(int visible, int gone) {
        llCheckAll.setVisibility(visible);
        llDelete.setVisibility(gone);
        shoppingCartRecyclerViewAapter.check_all();
    }

    /**
     * 1. 界面切换
     * 2.设置非全选
     * 显示编辑视图
     */
    private void showEditView() {
        llCheckAll.setVisibility(View.GONE);
        llDelete.setVisibility(View.VISIBLE);
        shoppingCartRecyclerViewAapter.check_none();
    }

    /**
     * 初始化数据
     */
    @Override
    public void initData() {
        super.initData();
        /**
         * 设置沙箱环境
         */
        EnvUtils.setEnv(EnvUtils.EnvEnum.SANDBOX);
        Log.e(TAG, "购物车数据被初始化了");
//        textView.setText("购物车");
    }


    /**
     * 每次且回购物车都从缓存中读取数剧
     */
    @Override
    public void onResume() {
        super.onResume();
        Log.i(TAG, "onResume: ");
        showData();
    }

    /**
     * 设置recyclerview 的数据
     */
    private void showData() {
        List<GoodsBean> allData = CartStorage.getInstance().getAllData();
        if (allData != null && allData.size() > 0) {
            hideEmptyCart();//隐藏空购物车
            for (int i = 0; i < allData.size(); i++) {
                Log.i(TAG, "showData: " + allData.get(i).toString());
            }
            shoppingCartRecyclerViewAapter = new ShoppingCartRecyclerViewAapter(allData, mContext, inflater, tvShopcartTotal, checkboxAll, cbAll);
            recyclerview.setAdapter(shoppingCartRecyclerViewAapter);
            recyclerview.setLayoutManager(new LinearLayoutManager(mContext));
        } else {//缓存没有数据  显示空布局
            showEmptyCart();
        }

    }

    /**
     * 隐藏空购物车
     */
    private void hideEmptyCart() {
        ll_empty_shopcart.setVisibility(View.GONE);
        tvShopcartEdit.setVisibility(View.VISIBLE);
    }

    /**
     * 显示空购物车界面
     */
    private void showEmptyCart() {
        ll_empty_shopcart.setVisibility(View.VISIBLE);
        tvShopcartEdit.setVisibility(View.GONE);
    }


    /**
     *
     *
     *
     * 支付宝
     *
     *
     *
     */

    /**
     * 用于支付宝支付业务的入参 app_id。
     */
    public static final String APPID = "2016102300747281";

    /**
     * 用于支付宝账户登录授权业务的入参 pid。
     */
    public static final String PID = "2088012856831055";

    /**
     * 用于支付宝账户登录授权业务的入参 target_id。
     */
    public static final String TARGET_ID = "2088102180809845";

    /**
     * pkcs8 格式的商户私钥。
     * <p>
     * 如下私钥，RSA2_PRIVATE 或者 RSA_PRIVATE 只需要填入一个，如果两个都设置了，本 Demo 将优先
     * 使用 RSA2_PRIVATE。RSA2_PRIVATE 可以保证商户交易在更加安全的环境下进行，建议商户使用
     * RSA2_PRIVATE。
     * <p>
     * 建议使用支付宝提供的公私钥生成工具生成和获取 RSA2_PRIVATE。
     * 工具地址：https://doc.open.alipay.com/docs/doc.htm?treeId=291&articleId=106097&docType=1
     */
    public static final String RSA2_PRIVATE = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCU" +
            "7nBYF0K735dLqL1Vn2+ykBF2ifVnZWw5LVE4kb4DhnXYLirz9kJgY/air8GcJmWgivAEwrcA4yVIZWv03HDhNnE" +
            "1QPFEnsNE5jkpLcuZ8x0tZbHell9O+h62CnForgrWo1na1IZv58YiXvfB02SZ/raBoMquftZL/lREWXXTbkjoR6" +
            "faMamEBO+4ymN6TLFTl/N/Sp8mwB6W/zNq3VuvLaPXzm45QZpX2EYFkkUYLENiFhelBOA0kvMn9xPxY+LY1jO2h" +
            "JX4OPLNZZdbXRj0X1g1qaAzrRfoVSQxiVnHdNlQSyWcR/EQmx7PMjuwUU7WzFGsirdAWcsBmKcJiZ6FAgMBAAEC" +
            "ggEAM2RgQhWswpXyaHpO83f0HGMYk5Yhp89Y3BYmO+YW8rJ4gePUkKD+4LI+Dn+lyNN7KD4pewsSCW1YlTXiXfm" +
            "fvieCQ0UovGwop+LmAK673mEFRYAdM9kiBGfNJzZNUjkUpmJyBFBoH7+UIJIy3qCqy3UY13x71GESY+DchUV1ok" +
            "KWD+brWqcUBVVD44nwPzCsEUcoIG4ITlaRS5bZCF738E7dVNzzDODLmgBl8JdoZwZsXIM4/wC9AoecI74seN+mkI" +
            "e4j3rzihnKpfZ+8FTtdIG0Ri9yuUm8UsUZnqLPjOsRuQJQAHKiHROfQx8UT3kImJJ15e0SkhN+TN/Vq1GGIQKBgQD" +
            "8XqptaL+IQL+HtALkyazBQ76ktwFOo/1Epr5KYRuBsDrn49D/hyqZiZhAssR/08wit8fyTPa8gJouUzzUUql+qN" +
            "cx+zfE+AiNppXD7BTK7GsvJk/0fQhHiTL5R3c/YLrjt2CD3bV7WimS/NxkYxkSHRNTF4KGghBCzqa519bUiQKBg" +
            "QCXEt5Qn6vmMNbL/5jB4i8tVlL7IgGbFQzGSQue0n5NVNiRDb1XCfq+QhY6cq0/noSbec3Z+S+cwVVkZ2VtCPb" +
            "Szb9O/OHDgb3ln8lC8BfkVQGzlUSgyAmN6BmGsBucGL9uUsoKqQAe7/xV/Kr+8vZJXOvZe36dIT7JAFrSdGpzH" +
            "QKBgQCfG3fLPqzyVA/smFryrwtRVUqOQip6z/zPGWkDL7HN07NvmzuavjbnUc5Bi/NCu98JTGCsrEUqltGdvzCs" +
            "k+j0R7JUepOwhMevtoTZXq/FN8Rjl331IBbmMuOGMb5GoM9y3ZvlxxOxzyhC2VSBndCikN4sFrhd4QYreGm45R" +
            "E2MQKBgDSJfESfcoFHxLsGtRHf3bYvigYJqnNLG7ktF9xwqTmtYu72zzygnNFjXCWRUcyJDOagskS2Uqd52H9l" +
            "MbF8Z5GhuYD3km+SA/8B1VTw9+4Q3dmc9FPRnPj7mTzsq31aYeA07ExOv1sC7Ebe6nxk899JcD+eocZ1FEeEVxF" +
            "PKOHFAoGBAI/ZRYq5khSda9TMzW8bSAo35AgyXJhGuaZURYYVtc3/+xp79VaeQCn6r5H7hXdqEqE1XfyJzd45S" +
            "VYEkSrohSAqRACV224GhQ28WeUqCC28gOB9gm6UOGvCgczSnxx20cPyH796Vc+HrnrFN94P0s6wEgQH65fu7dROt8lSa58Q";
    public static final String RSA_PRIVATE = "";

    private static final int SDK_PAY_FLAG = 1;
    private static final int SDK_AUTH_FLAG = 2;

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                /**
                 * 处理支付结果的信息
                 */
                case SDK_PAY_FLAG: {
                    @SuppressWarnings("unchecked")
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    /**
                     * 对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                        Toast.makeText(mContext, R.string.pay_success, Toast.LENGTH_SHORT).show();
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        Toast.makeText(mContext, R.string.pay_failed, Toast.LENGTH_SHORT).show();
                    }
                    break;
                }
                /**
                 * 处理授权结果的信息
                 */
//                case SDK_AUTH_FLAG: {
//                    @SuppressWarnings("unchecked")
//                    AuthResult authResult = new AuthResult((Map<String, String>) msg.obj, true);
//                    String resultStatus = authResult.getResultStatus();
//
//                    // 判断resultStatus 为“9000”且result_code
//                    // 为“200”则代表授权成功，具体状态码代表含义可参考授权接口文档
//                    if (TextUtils.equals(resultStatus, "9000") && TextUtils.equals(authResult.getResultCode(), "200")) {
//                        // 获取alipay_open_id，调支付时作为参数extern_token 的value
//                        // 传入，则支付账户为该授权账户
//                        Toast.makeText(mContext, R.string.auth_success, Toast.LENGTH_SHORT).show();
//                    } else {
//                        // 其他状态值则为授权失败
//                        Toast.makeText(mContext, R.string.auth_failed, Toast.LENGTH_SHORT).show();
//                    }
//                    break;
//                }
                default:
                    break;
            }
        }
    };


    /**
     * 支付宝支付业务示例
     */
    private void payV2(double total) {
        if (TextUtils.isEmpty(APPID) || (TextUtils.isEmpty(RSA2_PRIVATE) && TextUtils.isEmpty(RSA_PRIVATE))) {
            Toast.makeText(mContext,
                    R.string.error_missing_appid_rsa_private, Toast.LENGTH_SHORT).show();
            return;
        }

        /*
         * 这里只是为了方便直接向商户展示支付宝的整个支付流程；所以Demo中加签过程直接放在客户端完成；
         * 真实App里，privateKey等数据严禁放在客户端，加签过程务必要放在服务端完成；
         * 防止商户私密数据泄露，造成不必要的资金损失，及面临各种安全风险；
         *
         * orderInfo 的获取必须来自服务端；
         */
        boolean rsa2 = (RSA2_PRIVATE.length() > 0);
        Map<String, String> params = OrderInfoUtil2_0.buildOrderParamMap(APPID, rsa2,total);
        String orderParam = OrderInfoUtil2_0.buildOrderParam(params);

        String privateKey = rsa2 ? RSA2_PRIVATE : RSA_PRIVATE;
        String sign = OrderInfoUtil2_0.getSign(params, privateKey, rsa2);
        final String orderInfo = orderParam + "&" + sign;

        final Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                PayTask alipay = new PayTask((Activity) mContext);
                Map<String, String> result = alipay.payV2(orderInfo, true);
                Log.i("msp", result.toString());

                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };

        // 必须异步调用
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }

    /**
     * 支付宝账户授权业务示例
     */
//    public void authV2(View v) {
//        if (TextUtils.isEmpty(PID) || TextUtils.isEmpty(APPID)
//                || (TextUtils.isEmpty(RSA2_PRIVATE) && TextUtils.isEmpty(RSA_PRIVATE))
//                || TextUtils.isEmpty(TARGET_ID)) {
//
//            Toast.makeText(mContext, R.string.error_auth_missing_partner_appid_rsa_private_target_id,
//                    Toast.LENGTH_SHORT).show();
//            return;
//        }
//
//        /*
//         * 这里只是为了方便直接向商户展示支付宝的整个支付流程；所以Demo中加签过程直接放在客户端完成；
//         * 真实App里，privateKey等数据严禁放在客户端，加签过程务必要放在服务端完成；
//         * 防止商户私密数据泄露，造成不必要的资金损失，及面临各种安全风险；
//         *
//         * authInfo 的获取必须来自服务端；
//         */
//        boolean rsa2 = (RSA2_PRIVATE.length() > 0);
//        Map<String, String> authInfoMap = OrderInfoUtil2_0.buildAuthInfoMap(PID, APPID, TARGET_ID, rsa2);
//        String info = OrderInfoUtil2_0.buildOrderParam(authInfoMap);
//
//        String privateKey = rsa2 ? RSA2_PRIVATE : RSA_PRIVATE;
//        String sign = OrderInfoUtil2_0.getSign(authInfoMap, privateKey, rsa2);
//        final String authInfo = info + "&" + sign;
//        Runnable authRunnable = new Runnable() {
//
//            @Override
//            public void run() {
//                // 构造AuthTask 对象
//                AuthTask authTask = new AuthTask((Activity) mContext);
//                // 调用授权接口，获取授权结果
//                Map<String, String> result = authTask.authV2(authInfo, true);
//
//                Message msg = new Message();
//                msg.what = SDK_AUTH_FLAG;
//                msg.obj = result;
//                mHandler.sendMessage(msg);
//            }
//        };
//
//        // 必须异步调用
//        Thread authThread = new Thread(authRunnable);
//        authThread.start();
//    }


}
