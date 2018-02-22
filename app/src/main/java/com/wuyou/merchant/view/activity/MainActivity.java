package com.wuyou.merchant.view.activity;

import android.content.Intent;
import android.os.Bundle;

import com.gs.buluo.common.utils.DensityUtils;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;
import com.wuyou.merchant.R;
import com.wuyou.merchant.adapter.MainPagerAdapter;
import com.wuyou.merchant.mvp.circle.CircleFragment;
import com.wuyou.merchant.mvp.message.MessageFragment;
import com.wuyou.merchant.mvp.order.OrderFragment;
import com.wuyou.merchant.mvp.store.StoreFragment;
import com.wuyou.merchant.mvp.wallet.WalletFragment;
import com.wuyou.merchant.view.fragment.BaseFragment;
import com.wuyou.merchant.view.widget.NoScrollViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MainActivity extends BaseActivity {
    @BindView(R.id.main_tab)
    BottomNavigationViewEx bottomView;
    @BindView(R.id.main_pager)
    NoScrollViewPager viewPager;
    List<BaseFragment> fragments = new ArrayList<>();
    OrderFragment orderFragment = new OrderFragment();

    @Override
    protected int getContentLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void bindView(Bundle savedInstanceState) {
        fragments.add(orderFragment);
        fragments.add(new CircleFragment());
        fragments.add(new WalletFragment());
        fragments.add(new MessageFragment());
        fragments.add(new StoreFragment());
        viewPager.setAdapter(new MainPagerAdapter(getSupportFragmentManager(), fragments));
        bottomView.setupWithViewPager(viewPager, false);
        bottomView.enableAnimation(false);
        bottomView.setIconVisibility(true);
        bottomView.enableShiftingMode(false);
        bottomView.enableItemShiftingMode(false);
        bottomView.setIconSize(DensityUtils.dip2px(getCtx(), 20), DensityUtils.dip2px(getCtx(), 20));
        bottomView.setIconsMarginTop(DensityUtils.dip2px(getCtx(), -8));
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        orderFragment.loadDatas();
    }
}
