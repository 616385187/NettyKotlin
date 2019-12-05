package com.fx.nettykotlin.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;

public class PolyViewGroup extends ViewGroup {
    public PolyViewGroup(Context context) {
        super(context);
    }

    public PolyViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PolyViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public PolyViewGroup(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

}
