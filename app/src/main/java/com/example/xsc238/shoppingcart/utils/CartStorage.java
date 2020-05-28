package com.example.xsc238.shoppingcart.utils;

import android.content.Context;
import android.text.TextUtils;
import android.util.SparseArray;

import com.example.xsc238.app.MyApplication;
import com.example.xsc238.home.bean.GoodsBean;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：许少聪
 * 邮箱：18750910084@163.com
 * 作用：
 */
public class CartStorage {


    private Context mContext;

    private static CartStorage instance;

    //优化过的hashmap数据集
    private SparseArray<GoodsBean> datas;
    private final String JSON_CART = "json_cart";//sharedreference的key


    public CartStorage(Context getmContext) {
        this.mContext = getmContext;

        datas = new SparseArray<>(100);
        //初始化datas
        listToParse();
    }

    /**
     * 初始化存储数据集
     */
    private void listToParse() {
        List<GoodsBean> carts = getAllData();
        for (int i = 0; i < carts.size(); i++) {
            datas.put(Integer.parseInt(carts.get(i).getProduct_id()),
                    carts.get(i));
        }
    }

    /**
     * 将parse对象转为list
     * 主要用于存回缓存
     *
     * @return
     */

    private List<GoodsBean> parseToList() {
        List<GoodsBean> res = new ArrayList<GoodsBean>();
        if (datas != null && datas.size() > 0)
            for (int i = 0; i < datas.size(); i++) {
                GoodsBean goodsBean = datas.valueAt(i);
                res.add(goodsBean);
            }
        return res;
    }

    /**
     * 从本地缓存读取数据
     *
     * @return
     */
    public  List<GoodsBean> getAllData() {
        List<GoodsBean> res = new ArrayList<GoodsBean>();
        String data_in_json = CacheUtils.getString(mContext, JSON_CART);
        if (!TextUtils.isEmpty(data_in_json)) {
            res = new Gson().fromJson(data_in_json, new TypeToken<List<GoodsBean>>() {
            }.getType());
        }
        return res;
    }

    /**
     * 获取数据存储实例
     */

    public static CartStorage getInstance() {
        if (instance == null) {
            instance = new CartStorage(MyApplication.getmContext());
        }
        return instance;
    }


    /**
     * 数据增加
     */

    public boolean addData(GoodsBean goodsBean) {
        GoodsBean tmp = datas.get(Integer.parseInt(goodsBean.getProduct_id()));
        if (tmp != null) {//数量加1
            tmp.setNumber(tmp.getNumber() + 1);
        } else {
            tmp = goodsBean;
        }
        datas.put(Integer.parseInt(tmp.getProduct_id()), tmp);

        commit();
        return true;
    }

    /**
     * 数据修改提交
     */
    private void commit() {
        //将data转化为list的数据
        List<GoodsBean> data_list = parseToList();
        String t = new Gson().toJson(data_list);
        CacheUtils.putString(mContext, JSON_CART, t);
    }


    /**
     * 数据删除
     */

    public boolean deleteData(GoodsBean cart) {
        datas.delete(Integer.parseInt(cart.getProduct_id()));
        commit();
        return true;
    }


    /**
     * 修改数据
     */

    public boolean updateData(GoodsBean cart) {
        datas.put(Integer.parseInt(cart.getProduct_id()), cart);

        commit();
        return true;

    }

}
