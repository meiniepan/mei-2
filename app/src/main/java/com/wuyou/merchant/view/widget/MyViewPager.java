package com.wuyou.merchant.view.widget;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;

import com.wuyou.merchant.interfaces.ScrollViewListener;

/**
 * Created by Solang on 2018/3/29.
 */

public class MyViewPager extends ViewPager {
    private ScrollViewListener scrollViewListener;

    public MyViewPager(@NonNull Context context) {
        super(context);
    }

    public MyViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }


    public void setOnScrollViewListener(ScrollViewListener scrollViewListener) {
        this.scrollViewListener = scrollViewListener;
    }



    @Override
    protected void onPageScrolled(int position, float offset, int offsetPixels) {
        super.onPageScrolled(position, offset, offsetPixels);
        Log.e("====",offset+"    "+offsetPixels);
        Log.e("====getx",getScrollX()+"");
        if (scrollViewListener != null) {
            scrollViewListener.onScrollChanged(this, offsetPixels,0);
        }
    }
}
