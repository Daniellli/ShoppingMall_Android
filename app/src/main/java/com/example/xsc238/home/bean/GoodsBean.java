package com.example.xsc238.home.bean;

import androidx.annotation.NonNull;

import java.io.Serializable;

/**
 * 作者：许少聪
 * 邮箱：18750910084@163.com
 * 作用：
 */
public class GoodsBean implements Serializable {
    private String name;
    private String covor_price;
    private String figure;
    private String product_id;//产品ID
    private Integer number;

    private boolean selected;

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public GoodsBean(String name, String covor_price, String figure, String product_id) {
        this.name = name;
        this.covor_price = covor_price;
        this.figure = figure;
        this.product_id = product_id;
        number = 1;//数量为1
        selected = true;//选中 默认
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCovor_price() {
        return covor_price;
    }

    public void setCovor_price(String covor_price) {
        this.covor_price = covor_price;
    }

    public String getFigure() {
        return figure;
    }

    public void setFigure(String figure) {
        this.figure = figure;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }


    @Override
    public String toString() {
        return "GoodsBean{" +
                "name='" + name + '\'' +
                ", covor_price='" + covor_price + '\'' +
                ", figure='" + figure + '\'' +
                ", product_id='" + product_id + '\'' +
                ", number=" + number +
                ", isSelected=" + selected +
                '}';
    }
}
