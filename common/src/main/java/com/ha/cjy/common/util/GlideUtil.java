package com.ha.cjy.common.util;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;

/**
 * Glide加载图片
 * Created by cjy on 2018/7/18.
 */

public class GlideUtil {

    /**
     * 设置默认图
     *
     * @param context
     * @param iv
     * @param path
     * @param defResId
     */
    public static void glide(Context context, ImageView iv, String path, int defResId) {
        try {
            if (path == null) {
                path = "";
            }
            RequestOptions options = new RequestOptions()
                    .centerInside()
                    .placeholder(defResId)
                    .error(defResId)
                    .priority(Priority.HIGH);
            Glide.with(context).load(path.trim()).apply(options).into(iv);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 圆形 设置默认图
     *
     * @param context
     * @param iv
     * @param path 路径
     * @param defResId
     */
    public static void glideCircle(Context context, final ImageView iv, String path, final int defResId) {
        try {
            if (path == null) {
                path = "";
            }
            RequestOptions options = new RequestOptions()
                    .centerInside()
                    .circleCrop()
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .placeholder(defResId)
                    .error(defResId)
                    .priority(Priority.HIGH);
            Glide.with(context).load(path.trim()).apply(options).listener(new RequestListener< Drawable>(){

                @Override
                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                    return false;
                }

                @Override
                public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                    iv.setImageDrawable(resource);
                    return false;
                }
            }).into(iv);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 圆形 设置默认图
     *
     * @param context
     * @param iv
     * @param data byte数组
     * @param defResId
     */
    public static void glideCircle(Context context, final ImageView iv, byte[] data, final int defResId) {
        try {
            if (data == null) {
                return;
            }
            RequestOptions options = new RequestOptions()
                    .centerInside()
                    .circleCrop()
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .placeholder(defResId)
                    .error(defResId)
                    .priority(Priority.HIGH);
            Glide.with(context).load(data).apply(options).listener(new RequestListener< Drawable>(){

                @Override
                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                    return false;
                }

                @Override
                public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                    iv.setImageDrawable(resource);
                    return false;
                }
            }).into(iv);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 加载圆角矩形
     * @param context
     * @param iv
     * @param path
     * @param defResId
     */
    public static void glideRoundedCorner(Context context, ImageView iv, String path, int defResId,int roundRadius){
        try{
            //设置图片圆角角度
            RoundedCorners roundedCorners= new RoundedCorners(roundRadius);
            //通过RequestOptions扩展功能
            RequestOptions options = RequestOptions
                    .placeholderOf(defResId)
                    .error(defResId)
                    .bitmapTransform(roundedCorners)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .priority(Priority.HIGH);
            Glide.with(context).load(path.trim()).apply(options).into(iv);
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
