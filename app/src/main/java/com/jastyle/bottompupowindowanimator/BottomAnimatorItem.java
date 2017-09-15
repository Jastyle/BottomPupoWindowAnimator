package com.jastyle.bottompupowindowanimator;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.zip.Inflater;

/**
 * author jastyle
 * description:
 * date 2017/9/4  下午3:14
 */

public class BottomAnimatorItem  extends LinearLayout{

    private Context mContext;
    private TextView tv;
    private ImageView iv;
    private int ivSrc ;
    private String desc;
    private boolean isImgBackground;
    private Drawable mTouchDrawable;
    public BottomAnimatorItem(Context context) {
        this(context, null);
    }

    public BottomAnimatorItem(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public BottomAnimatorItem(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.BottomAnimatorItem);
        ivSrc = ta.getResourceId(R.styleable.BottomAnimatorItem_icon, -1);
        desc = ta.getString(R.styleable.BottomAnimatorItem_desc);
        isImgBackground = ta.getBoolean(R.styleable.BottomAnimatorItem_isImgBackground, false);
        mTouchDrawable = ta.getDrawable(R.styleable.BottomAnimatorItem_ImgBackgroundDrawable);
        ta.recycle();
        mContext = context;
//        check();
        init();
    }


    private void init() {
        setOrientation(VERTICAL);
        setGravity(Gravity.CENTER);
        View view = View.inflate(mContext, R.layout.bottom_item_animator, null);
        tv = (TextView) view.findViewById(R.id.desc_tv);
        iv = (ImageView) view.findViewById(R.id.icon_iv);
        /*if (isImgBackground) {
            iv.setBackground(mTouchDrawable);
        }else {
            iv.setImageResource(ivSrc);
        }*/
        tv.setText(desc);
        addView(view);
    }
    public void setIcon(Drawable id) {
        iv.setImageDrawable(id);
    }
    private void check() {
        if (isImgBackground&&null == mTouchDrawable) {
            throw new IllegalStateException("开启ImageView触摸效果，但没有指定mTouchDrawable");
        }else if (ivSrc == -1) {
            throw new IllegalStateException("没有设置ImageView默认src");
        }
    }
    public ImageView getImageView() {
        return iv;
    }
    public TextView getTextView() {
        return tv;
    }
}
