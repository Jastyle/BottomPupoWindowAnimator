package com.jastyle.bottompupowindowanimator;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.ImageView;
import android.widget.LinearLayout;

/**
 * author jastyle
 * description:
 * date 2017/9/4  下午5:19
 */

public class BottomPopuWindow extends View {
    private View mRootView;
    private View fullMaskView;
    private ViewStub mViewStub;
    private ImageView cancel;
    private LinearLayout container, root_ll;
    private int mCount;
    public boolean isShowing;
    private int mHeight;
    private int duration = 300;
    private Context mContext;
    private setaddStatus addstatus;
    private String[] Tag = {"文字", "图片", "视频"};
    private int[] TagBgRes = {R.drawable.text_normal, R.drawable.pic_normal, R.drawable.vedio_normal};
    public BottomPopuWindow(Context mContext) {
        super(mContext);
        this.mContext = mContext;
        setView(mContext);
    }

    private void setView(Context mContext) {
        mRootView = View.inflate(mContext, R.layout.bottom_animator, null);
        fullMaskView = mRootView.findViewById(R.id.full_mask);
        mViewStub = (ViewStub) mRootView.findViewById(R.id.view_stub);
        mViewStub.inflate();
        container = (LinearLayout) mRootView.findViewById(R.id.content_ll);
        root_ll = (LinearLayout) mRootView.findViewById(R.id.root_ll);
        mCount = 3;
        addItem(container);
        ((ViewGroup)((Activity)mContext).getWindow().getDecorView()).addView(mRootView);
        initHeight();
        addstatus = (setaddStatus) mContext;

    }

    private void initHeight() {
        mHeight = getViewHeight(container);
    }

    public void show() {
        isShowing = true;
        mRootView.setVisibility(VISIBLE);
        popuViewAnimator(mHeight, 0, 0.0f, 0.5f);

    }

    private void hide() {
        isShowing = false;
        popuViewAnimator(0, mHeight, 0.5f, 0.0f);
    }

    /*往容器里添加view*/
    private void addItem(LinearLayout container) {
        for (int i = 0; i<mCount;i++) {
            BottomAnimatorItem item = new BottomAnimatorItem(mContext);
            item.setX(0);
            item.setY(500);
            item.getTextView().setText(Tag[i]);
            item.getImageView().setImageResource(TagBgRes[i]);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f);
            item.setLayoutParams(lp);
            item.setOnClickListener(new MyClickListener(i));
            container.addView(item);
        }
        root_ll.setOrientation(LinearLayout.VERTICAL);
        cancel = new ImageView(mContext);
        cancel.setImageResource(R.mipmap.icon_cancel);
        LinearLayout.LayoutParams cancellp= new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        cancellp.gravity = Gravity.CENTER_HORIZONTAL;
        cancel.setX(0);
        cancel.setY(500);
        cancel.setLayoutParams(cancellp);
        root_ll.addView(cancel);
        cancel.setOnClickListener( new MyClickListener(3));

    }

    private void popuViewAnimator(final float start, final float end, final float startAlpha, final float endAlpha) {
        popuViewTranlationAnimator(start, end);
        popiViewAlphaAnimator(startAlpha,endAlpha);
    }

    private void popuViewTranlationAnimator(final float start, final float end) {
        ObjectAnimator bottomAnim = ObjectAnimator.ofFloat(container, "translationY", start, end);
        bottomAnim.setDuration(duration);
        bottomAnim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                /*弹框动画结束，开始item动画*/
                if (isShowing) {
                    startAnimation(container);
                }else {/*关闭弹框*/
                    if (start<end) {
                        mRootView.setVisibility(GONE);
                    }
                }
            }

            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                /*关闭弹框，item关闭动画*/
                if (!isShowing) {
                    closeItemAnimation(container);
                }
            }
        });
        bottomAnim.start();
    }

    private void popiViewAlphaAnimator(final float startAlpha, final float endAlpha) {
        ObjectAnimator bottomAnimAlpha = ObjectAnimator.ofFloat(fullMaskView, "alpha", startAlpha, endAlpha);
        bottomAnimAlpha.setDuration(duration);
        bottomAnimAlpha.start();
    }
    /*获取底部弹框的高*/
    private int getViewHeight(View mView) {
        int w = View.MeasureSpec.makeMeasureSpec(0,
                View.MeasureSpec.UNSPECIFIED);
        int h = View.MeasureSpec.makeMeasureSpec(0,
                View.MeasureSpec.UNSPECIFIED);
        mView.measure(w, h);
        return mView.getMeasuredHeight();
    }

    /*子view切入动画*/
    private void startAnimation(LinearLayout bal) {
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(800);
        for (int i =0;i<mCount;i++) {
            if (bal.getChildAt(i) instanceof BottomAnimatorItem) {
                ObjectAnimator anim = ObjectAnimator.ofFloat(bal.getChildAt(i), "translationY", mHeight, 100);
                animatorSet.playTogether(anim);
                animatorSet.setInterpolator(new MyInterpolator());
            }

        }
        ObjectAnimator cancelAnimTrans = ObjectAnimator.ofFloat(cancel, "translationY", mHeight, -30);
        final ObjectAnimator cancelAnimRota = ObjectAnimator.ofFloat(cancel, "rotation",0f, 30f);
        cancelAnimTrans.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                cancelAnimRota.setDuration(100);
                cancelAnimRota.start();
            }
        });
        animatorSet.playTogether(cancelAnimTrans);
        animatorSet.setInterpolator(new MyInterpolator());
        animatorSet.start();

    }
    /*子view退出动画*/
    private void closeItemAnimation(LinearLayout bal) {
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(100);
        for (int i = 0; i < mCount; i++) {
            if (bal.getChildAt(i) instanceof BottomAnimatorItem) {
                if (bal.getChildAt(i) instanceof BottomAnimatorItem) {
                    ObjectAnimator anim = ObjectAnimator.ofFloat(bal.getChildAt(i), "translationY", 100, mHeight);
                    animatorSet.playTogether(anim);
                }

            }
        }
        ObjectAnimator cancelRotation = ObjectAnimator.ofFloat(cancel, "rotation", 30f, 0f);
        ObjectAnimator cancelTrans = ObjectAnimator.ofFloat(cancel, "translationY", 0, mHeight);
        animatorSet.playTogether(cancelRotation,cancelTrans);
        animatorSet.start();

    }
    /* 子view动画插值器*/
    private class MyInterpolator implements android.view.animation.Interpolator {

        @Override
        public float getInterpolation(float t) {
            if(t<0.2094) return (float)(-34*(t-0.18)*(t-0.18)+1.08);
            else if(t <0.404) return (float)(5.9*(t-0.34)*(t-0.34)+0.95);
            else if(t < 0.6045) return (float)(-3*(t-0.53)*(t-0.53)+1.02);
            else if(t < 0.8064) return (float)((t-0.72)*(t-0.72)+0.99);
            else return (float)(-0.3*(t-0.915)*(t-0.915)+1.001);
        }
    }

    private class MyClickListener implements OnClickListener {
        private int position;

        private MyClickListener(int position) {
            this.position = position;
        }

        @Override
        public void onClick(View v) {
            if (position == 3) {
                hide();
                addstatus.enable(true);
            }
        }
    }

}
