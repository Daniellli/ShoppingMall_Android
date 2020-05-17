package com.example.xsc238.home.adapter.viewholder;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.xsc238.utils.Constants;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.youth.banner.loader.ImageLoaderInterface;

/**
 * 作者：许少聪
 * 邮箱：18750910084@163.com
 * 作用：  banner的imageloader 主要给banner加载图片的
 */
class MyImagerLoad extends ImageLoader implements ImageLoaderInterface {
    private final String TAG = getClass().getSimpleName();

    /**
     * imageView 来自createImageView方法
     *
     * @param context
     * @param path
     * @param imageView
     */
    @Override
    public void displayImage(Context context, Object path, View imageView) {
        String url = Constants.Base_URl_IMAGE + path;//图片地址
        Glide.with(context).load(url).into((ImageView) imageView);
    }

    /**
     * 加载图片并返回给displayImage
     *
     * @param context
     * @return
     */
    @Override
    public View createImageView(Context context) {
        return new ImageView(context);
    }
}
