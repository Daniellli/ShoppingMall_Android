package com.example.xsc238.wxapi;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.example.xsc238.R;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import android.content.Intent;

/**
 * 作者：许少聪
 * 邮箱：18750910084@163.com
 * 作用：在微信支付后SDK为了回调通知APP的接口，SDK内部会调用该Activity，该Activity的界面布局可以自定义的设置。
 * 其具体的回调接口是该类中的onResp函数，在该函数中可判断是否支付成功。
 */
public class WXPayEntryActivity extends AppCompatActivity implements IWXAPIEventHandler {

    private IWXAPI api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      //  setContentView(R.layout.activity_wxpay_entry);
        api = WXAPIFactory.createWXAPI(this, "wxAPPID");//这里填入自己的微信APPID
        api.handleIntent(getIntent(), this);
    }

    @Override
    public void onReq(BaseReq baseReq) {

    }

    @Override
    public void onResp(BaseResp baseResp) {
        Log.d("coyc", "onPayFinish, errCode = " + baseResp.errCode);

        if (baseResp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
            int errCord = baseResp.errCode;
            if (errCord == 0) {
              //  App.getInstance().tos("支付成功！");
            } else {
               // App.getInstance().tos("支付失败");
            }
            //这里接收到了返回的状态码可以进行相应的操作，如果不想在这个页面操作可以把状态码存在本地然后finish掉这个页面，这样就回到了你调起支付的那个页面
            //获取到你刚刚存到本地的状态码进行相应的操作就可以了
            finish();
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }
}