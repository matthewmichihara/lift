package com.pixeltreelabs.lift.android.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

public class SquaredLinearLayout extends LinearLayout {

    public SquaredLinearLayout(Context context) {
        super(context);
    }

    public SquaredLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
        setMeasuredDimension(getMeasuredWidth(), getMeasuredWidth());
    }
}
