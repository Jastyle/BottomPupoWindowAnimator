package com.jastyle.bottompupowindowanimator;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * author jastyle
 * description:
 * date 2017/9/4  下午4:10
 */

public class BottomAnimatorLayout extends LinearLayout {
    private int mChildCount;
    private List<ImageView> imageViewList;
    public BottomAnimatorLayout(Context context) {
        this(context, null);
    }

    public BottomAnimatorLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BottomAnimatorLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mChildCount = getChildCount();
        imageViewList = new ArrayList<>();
        for (int i=0;i<mChildCount;i++) {
            if (getChildAt(i) instanceof BottomAnimatorItem) {

                imageViewList.add(((BottomAnimatorItem) getChildAt(i)).getImageView());
            }
        }
    }
}
