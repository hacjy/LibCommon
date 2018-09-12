package com.ha.cjy.common.util;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.graphics.Bitmap;
import android.graphics.drawable.AnimationDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;

import java.util.List;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

/**
 * 动画效果工具类
 * Created by cjy on 2018/5/7.
 */

public class AnimatorUtil {
    /**
     * @param target
     * @param star
     * @param end
     * @param duration
     * @param startDelay
     * @return
     * 向上飞 淡入 显示 淡出 (加经验值使用)
     */
    public static ObjectAnimator createExpFadeAnimator(final View target, float star, float end, int duration, int startDelay){

        PropertyValuesHolder translationY = PropertyValuesHolder.ofFloat("translationY", star,end);
        PropertyValuesHolder alpha = PropertyValuesHolder.ofFloat("alpha", 0f,1.0f);
        ObjectAnimator animator = ObjectAnimator.ofPropertyValuesHolder(target, translationY, alpha);
        animator.setStartDelay(startDelay);
        animator.setDuration(duration);
        return animator;
    }
    /**
     * @param target
     * @param duration
     * @param startDelay
     * @return
     * 向上飞 淡入 显示 淡出 (加经验值使用)
     */
    public static ObjectAnimator createFadeAnimator(final View target, int duration, int startDelay){
        PropertyValuesHolder sy= PropertyValuesHolder.ofFloat( "scaleY", 0, 1);
        PropertyValuesHolder sx = PropertyValuesHolder.ofFloat( "scaleX", 0, 1);

        PropertyValuesHolder alpha = PropertyValuesHolder.ofFloat("alpha", 0f,1.0f);
        ObjectAnimator animator = ObjectAnimator.ofPropertyValuesHolder(target, sx,sy, alpha);
        animator.setStartDelay(startDelay);
        animator.setDuration(duration);
        return animator;
    }
    public static ObjectAnimator createFadeAnimatorNoHide( View target, float star, float end, int duration, int startDelay){
        final View view = target;
        PropertyValuesHolder translationY = PropertyValuesHolder.ofFloat("translationY", star,end);
        ObjectAnimator animator = ObjectAnimator.ofPropertyValuesHolder(target, translationY);
        animator.setStartDelay(startDelay);
        animator.setDuration(duration);
        animator.setInterpolator(new LinearInterpolator());
        return animator;
    }


    public static ObjectAnimator createFadeAnimator1( View target, float star, float end, int duration, int startDelay){
        final View view = target;
        PropertyValuesHolder translationY = PropertyValuesHolder.ofFloat("translationY", star,end);
        ObjectAnimator animator = ObjectAnimator.ofPropertyValuesHolder(target, translationY);
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                view.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                view.setVisibility(View.VISIBLE);
            }
        });
        animator.setStartDelay(startDelay);
        animator.setDuration(duration);
        animator.setInterpolator(new LinearInterpolator());
        return animator;
    }


    /**
     * @param target
     * @param star         动画起始坐标
     * @param end          动画终止坐标
     * @param duration     持续时间
     * @return
     * 创建一个从左到右的飞入动画
     */
    public  static ObjectAnimator createFlyFromLtoR(final View target, float star, float end, int duration, TimeInterpolator interpolator) {
        //1.个人信息先飞出来
        ObjectAnimator anim1 = ObjectAnimator.ofFloat(target, "translationX",
                star, end);
        anim1.setInterpolator(interpolator);
        anim1.setDuration(duration);
        return  anim1;
    }

    /**
     * @param target
     * @param star         动画起始坐标
     * @param end          动画终止坐标
     * @param duration     持续时间
     * @return
     * 创建一个从左到右的飞入动画
     * 礼物飞入动画
     */
    public  static ObjectAnimator createFlyFromYtoR(final View target, float star, float end, int duration, TimeInterpolator interpolator) {
        ObjectAnimator anim1 = ObjectAnimator.ofFloat(target, "translationX",
                star, end);
        anim1.setInterpolator(interpolator);
        anim1.setDuration(duration);
        return  anim1;
    }
    /**
     * 控件垂直移动的动画
     * @param view      控件
     * @param moveToY   要移动到的Y坐标
     * @param currentY  要开始移动的Y坐标
     * @param duration  动画的时间
     */
    public static void moveComponentVerticalAnimator(View view,float moveToY,float currentY,int duration){
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(view,"translationY",currentY,moveToY);
        objectAnimator.setDuration(duration);
        objectAnimator.start();
    }

    public static void moveComponentVerticalVisible(final View view, float moveToY, float currentY, int duration, final boolean isNeedGone){
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(view,"translationY",currentY,moveToY);
        objectAnimator.setDuration(duration);
        objectAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                view.setVisibility(isNeedGone ? View.GONE : View.VISIBLE);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        objectAnimator.start();
    }

    /**
     * 用属性动画代替帧动画
     * @param view      控件
     * @param time  持续时间
     * @param animatorListenerAdapter  动画回调
     */
    public static ValueAnimator framAnim(ImageView view, List<Bitmap> bitmaps, int time, final AnimatorListenerAdapter animatorListenerAdapter) {
        final ImageView iv = view;
        final List<Bitmap> mBitmaps = bitmaps;
        final AnimatorListenerAdapter adapter = animatorListenerAdapter;
        ValueAnimator anim = ValueAnimator.ofInt(0, mBitmaps.size() - 1);
        anim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                iv.setVisibility(GONE);
                if (adapter != null) {
                    adapter.onAnimationEnd(animation);
                }
            }

            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                iv.setVisibility(VISIBLE);
                if (adapter != null) {
                    adapter.onAnimationStart(animation);
                }
            }
        });
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int currentValue = (int) animation.getAnimatedValue();
                iv.setImageBitmap(mBitmaps.get(currentValue));
                return;
            }
        });
        anim.setDuration(time);
        anim.setInterpolator(new LinearInterpolator());
        return anim;
    }


    /**
     * 用属性动画代替帧动画
     * @param view      控件
     * @param resids   资源集合
     * @param time  持续时间
     * @param animatorListenerAdapter  动画回调
     */
    public static ValueAnimator framAnim(ImageView view, int[] resids, int time, final AnimatorListenerAdapter animatorListenerAdapter) {
        final ImageView iv = view;
        final int[] resIdArr = resids;
        final AnimatorListenerAdapter adapter = animatorListenerAdapter;
        ValueAnimator anim = ValueAnimator.ofInt(0, resids.length - 1);
        anim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                iv.setVisibility(GONE);
                if (adapter != null) {
                    adapter.onAnimationEnd(animation);
                }
            }

            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                iv.setVisibility(VISIBLE);
                if (adapter != null) {
                    adapter.onAnimationStart(animation);
                }
            }
        });
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int currentValue = (int) animation.getAnimatedValue();
                iv.setImageResource(resIdArr[currentValue]);
                return;
            }
        });
        anim.setDuration(time);
        anim.setInterpolator(new LinearInterpolator());
        return anim;
    }



    /**
     * 用属性动画代替帧动画
     * @param view      控件
     * @param resids   资源集合
     * @param time  持续时间
     * @param animatorListenerAdapter  动画回调
     */
    public static ValueAnimator framAnimNoGone(ImageView view, int[] resids, int time, final AnimatorListenerAdapter animatorListenerAdapter) {
        final ImageView iv = view;
        final int[] resIdArr = resids;
        final AnimatorListenerAdapter adapter = animatorListenerAdapter;
        ValueAnimator anim = ValueAnimator.ofInt(0, resids.length - 1);
        anim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                if (adapter != null) {
                    adapter.onAnimationEnd(animation);
                }
            }

            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                iv.setVisibility(VISIBLE);
                if (adapter != null) {
                    adapter.onAnimationStart(animation);
                }
            }
        });
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int currentValue = (int) animation.getAnimatedValue();
                iv.setImageResource(resIdArr[currentValue]);
                return;
            }
        });
        anim.setDuration(time);
        anim.setInterpolator(new LinearInterpolator());
        return anim;
    }
    /**
     * @param target
     * @param star         动画起始坐标
     * @param end          动画终止坐标
     * @param duration     持续时间
     * @return
     * 创建一个从左到右的飞入动画
     */
    public  static ObjectAnimator createFlyFromYtoR(final View target, float star, float end, int duration) {
        ObjectAnimator anim1 = ObjectAnimator.ofFloat(target, "translationX",
                star, end);
        anim1.setDuration(duration);
        return  anim1;
    }
    /**
     * @param target
     * @return
     * 播放帧动画
     */
    public static AnimationDrawable startAnimationDrawable(ImageView target){
        AnimationDrawable animationDrawable = (AnimationDrawable) target.getDrawable();
        if(animationDrawable!=null) {
            target.setVisibility(View.VISIBLE);
            animationDrawable.start();
        }
        return animationDrawable;
    }

    /**
     * @param target
     * @param star
     * @param end
     * @param duration
     * @param startDelay
     * @return
     * 向上飞 淡出
     */
    public static ObjectAnimator createFadeAnimator(final View target, float star, float end, int duration, int startDelay){

        PropertyValuesHolder translationY = PropertyValuesHolder.ofFloat("translationY", star,end);
        PropertyValuesHolder alpha = PropertyValuesHolder.ofFloat("alpha", 1.0f,0f);
        ObjectAnimator animator = ObjectAnimator.ofPropertyValuesHolder(target, translationY, alpha);
        animator.setStartDelay(startDelay);
        animator.setDuration(duration);
        return animator;
    }

    /**
     * @param target
     * @param star
     * @param end
     * @param duration
     * @param startDelay
     * @return
     * 向上飞 淡入 显示 淡出 (加经验值使用)
     */
    public static ObjectAnimator createInFadeAnimator(final View target, float star, float end, int duration, int startDelay){

        PropertyValuesHolder translationY = PropertyValuesHolder.ofFloat("translationY", star,end);
        PropertyValuesHolder alpha = PropertyValuesHolder.ofFloat("alpha", 0f,1.0f);
        ObjectAnimator animator = ObjectAnimator.ofPropertyValuesHolder(target, translationY, alpha);
        animator.setStartDelay(startDelay);
        animator.setDuration(duration);
        return animator;
    }


    /**
     * 旋转动画
     * @param target
     * @param duration
     * @return
     */
    public static ObjectAnimator createRotationAnimator( View target, int duration){

        ObjectAnimator rot = ObjectAnimator.ofFloat(target, "rotation", 0, 359);
        rot.setDuration(duration);
        rot.setRepeatCount(ValueAnimator.INFINITE);//无限循环
        rot.setInterpolator(new LinearInterpolator());
        return rot;
    }
    /**
     * 旋转动画
     * @param target
     * @param duration
     * @return
     */
    public static ObjectAnimator createRotationAnimator1( View target, int duration){

        ObjectAnimator rot = ObjectAnimator.ofFloat(target, "rotation", 0, 360);
        rot.setDuration(duration);
        return rot;
    }
    /**
     * 旋转动画 (以某个点摆动)
     * @param target
     * @param duration
     * @return
     */
    public static ObjectAnimator createSwingRotationAnimator( View target, int duration,float pivotX,float pivotY,float startAngle,float endAngle){
        ObjectAnimator rot = ObjectAnimator.ofFloat(target, "rotation", startAngle, endAngle);
        target.setPivotX(pivotX);
        target.setPivotY(pivotY);
        rot.setDuration(duration);
        return rot;
    }

    /**
     *
     * @param target
     * @param duration
     * @param start
     * @param end
     * @return
     * Y轴位移动画
     */
    public static ObjectAnimator createTranslationAnimator(View target,int duration,float start,float end){
        PropertyValuesHolder translationY = PropertyValuesHolder.ofFloat("translationY", start,end);
        ObjectAnimator animator = ObjectAnimator.ofPropertyValuesHolder(target, translationY);
        animator.setDuration(duration);
        return animator;
    }

    /**
     * 逐渐显示动画
     * @param v
     * @param start
     * @param end
     * @return
     */
    public static  ValueAnimator createDropAnimator(final View v,int duration, int start, int end) {
        ValueAnimator animator = ValueAnimator.ofInt(start, end);
        animator.setDuration(duration);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator arg0) {
                int value = (int) arg0.getAnimatedValue();
                ViewGroup.LayoutParams layoutParams = v.getLayoutParams();
                layoutParams.height = value;
                v.setLayoutParams(layoutParams);
            }
        });
        return animator;
    }
}
