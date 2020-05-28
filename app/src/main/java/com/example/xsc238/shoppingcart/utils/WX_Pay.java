package com.example.xsc238.shoppingcart.utils;

import android.content.Context;

import androidx.annotation.NonNull;

import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 *             // Handle clicks for btnCheckOut
 *             IWXAPI api= WXAPIFactory.createWXAPI(mContext, "wxAPPID",false);//填写自己的APPID
 *             api.registerApp("wxAPPID");//填写自己的APPID，注册本身APP
 *             PayReq req = new PayReq();//PayReq就是订单信息对象
 * //给req对象赋值
 *             req.appId = appid;//APPID
 *             req.partnerId = partnerid;//    商户号
 *             req.prepayId = prepayid;//  预付款ID
 *             req.nonceStr = getRoundString();//随机数
 *             req.timeStamp = getTimeStamp();//时间戳
 *             req.packageValue = "Sign=WXPay";//固定值Sign=WXPay
 *             req.sign = sign;//签名
 *             api.sendReq(req);//将订单信息对象发送给微信服务器，即发送支付请求
 *
 *
 *
 */

public class WX_Pay {
 
    public IWXAPI api;
    private PayReq req;
 
    public WX_Pay(Context context) {
 
        api = WXAPIFactory.createWXAPI(context, "wxAPPID",false);
    }
 
    /**
     * 向微信服务器发起的支付请求
     */
    public void pay(String appid,String partnerid,String prepayid) {
 
        req = new PayReq();
 
        req.appId = appid;//APPID
        req.partnerId = partnerid;//    商户号
        req.prepayId = prepayid;//  预付款ID
        req.nonceStr = getRoundString();//随机数
        req.timeStamp = getTimeStamp();//时间戳
        req.packageValue = "Sign=WXPay";//固定值Sign=WXPay
 
        String sign = getSign();
        req.sign = sign;//签名
 
        api.registerApp("wxAPPID");
        api.sendReq(req);
    }
 
    @NonNull
    private String getSign() {
        Map<String, String> map = new HashMap<>();
        map.put("appid", req.appId);
        map.put("partnerid", req.partnerId);
        map.put("prepayid", req.prepayId);
        map.put("package", req.packageValue);
        map.put("noncestr", req.nonceStr);
        map.put("timestamp", req.timeStamp);
 
        ArrayList<String> sortList = new ArrayList<>();
        sortList.add("appid");
        sortList.add("partnerid");
        sortList.add("prepayid");
        sortList.add("package");
        sortList.add("noncestr");
        sortList.add("timestamp");
        sort(sortList);
 
        String md5 = "";
        int size = sortList.size();
        for (int k = 0; k < size; k++) {
            if (k == 0) {
                md5 += sortList.get(k) + "=" + map.get(sortList.get(k));
            } else {
                md5 += "&" + sortList.get(k) + "=" + map.get(sortList.get(k));
            }
        }
        String stringSignTemp = md5+"&key=商户密钥";
 
        String sign=MD5.Md5(stringSignTemp).toUpperCase();
 
        return sign;
    }
 
    private String getRoundString() {
 
        Random random = new Random();
 
        return random.nextInt(10000) + "";
    }
 
    private String getTimeStamp() {
        return new Date().getTime() / 10 + "";
    }
 
 
    private static void sort(ArrayList<String> strings) {
        Collections.sort(strings);
    }
}