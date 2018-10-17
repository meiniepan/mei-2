package com.wuyou.merchant.view.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.support.annotation.ColorRes;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import com.gs.buluo.common.widget.recyclerHelper.BaseQuickAdapter;
import com.gs.buluo.common.widget.recyclerHelper.EaeRecyclerView;
import com.gs.buluo.common.widget.recyclerHelper.OnRefreshListener;
import com.gs.buluo.common.widget.recyclerHelper.refreshLayout.EasyRefreshLayout;
import com.wuyou.merchant.R;
import com.wuyou.merchant.util.CommonUtil;


/**
 * Created by hjn on 2017/7/10.
 */

public class CarefreeRecyclerView extends FrameLayout {
    private EasyRefreshLayout mSwipeRefreshLayout;
    private EaeRecyclerView mRecyclerView;
    private BaseQuickAdapter mAdapter;

    public CarefreeRecyclerView(Context context) {
        this(context, null);
    }

    public CarefreeRecyclerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, com.gs.buluo.common.R.styleable.StateLayout, 0, 0);
        Drawable errorDrawable;
        Drawable emptyDrawable;
        Drawable loginDrawable;
        try {
            errorDrawable = a.getDrawable(R.styleable.RefreshRecyclerView_refreshErrorDrawable);
            emptyDrawable = a.getDrawable(R.styleable.RefreshRecyclerView_refreshEmptyDrawable);
            loginDrawable = a.getDrawable(R.styleable.RefreshRecyclerView_refreshLoginDrawable);
        } finally {
            a.recycle();
        }

        View view = inflate(context, com.gs.buluo.common.R.layout.common_status_refresh_recycler, this);
        mRecyclerView = view.findViewById(com.gs.buluo.common.R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.addItemDecoration(CommonUtil.getRecyclerDivider(context));
        mSwipeRefreshLayout = view.findViewById(com.gs.buluo.common.R.id.recycler_swipe);
        mSwipeRefreshLayout.setEnabled(false);
        mSwipeRefreshLayout.setEnablePullToRefresh(false);
        setSwipeRefreshColorsFromRes(com.gs.buluo.common.R.color.common_custom_color, com.gs.buluo.common.R.color.common_custom_color_shallow, com.gs.buluo.common.R.color.common_night_blue);
        mRecyclerView.setEmptyDrawable(emptyDrawable);
        mRecyclerView.setErrorDrawable(errorDrawable);
        mRecyclerView.setLoginDrawable(loginDrawable);
    }

    public CarefreeRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs);
    }

    public EaeRecyclerView getRecyclerView() {
        return mRecyclerView;
    }

    public void setSwipeRefreshColorsFromRes(@ColorRes int... colors) {
//        mSwipeRefreshLayout.setColorSchemeResources(colors);
//        mSwipeRefreshLayout.setProgressBackgroundColorSchemeResource(R.color.white);
    }

    public void setAdapter(BaseQuickAdapter adapter) {
        mRecyclerView.setAdapter(adapter);
        mAdapter = adapter;
        mAdapter.bindToRecyclerView(mRecyclerView);
    }

    public void setRefreshAction(final OnRefreshListener action) {
        mSwipeRefreshLayout.setEnabled(true);
        mSwipeRefreshLayout.setEnablePullToRefresh(true);
        mSwipeRefreshLayout.addEasyEvent(new EasyRefreshLayout.EasyEvent() {
            public void onRefreshing() {
                (new Handler()).postDelayed(new Runnable() {
                    public void run() {
                        action.onAction();
                        mSwipeRefreshLayout.refreshComplete();
                    }
                }, 1000L);
            }
        });
    }

    //刷新完成先调
    public void setRefreshFinished() {
        mSwipeRefreshLayout.setRefreshing(false);
        mAdapter.setEnableLoadMore(true);
        mAdapter.clearData();
    }

    public EasyRefreshLayout getRefreshLayout() {
        return mSwipeRefreshLayout;
    }

    public void showEmptyView() {
        mRecyclerView.showEmptyView();
    }

    public void showEmptyView(String msg) {
        mRecyclerView.showEmptyView(msg);
    }

    public void showErrorView() {
        mRecyclerView.showErrorView();
    }

    public void showErrorView(String msg) {
        mRecyclerView.showErrorView(msg);
    }

    public void showContentView() {
        mRecyclerView.showContentView();
    }

    public void showProgressView() {
        mRecyclerView.showProgressView();
    }


    public void setLoadMoreListener(BaseQuickAdapter.RequestLoadMoreListener loadMoreListener) {
        mAdapter.setOnLoadMoreListener(loadMoreListener, mRecyclerView);
    }

    public void setEmptyIcon(int emptyIcon) {
        mRecyclerView.setEmptyDrawable(getResources().getDrawable(emptyIcon));
    }
}
