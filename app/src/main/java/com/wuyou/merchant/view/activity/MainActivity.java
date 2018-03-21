package com.wuyou.merchant.view.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;

import com.gs.buluo.common.utils.DensityUtils;
import com.gs.buluo.common.utils.ToastUtils;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;
import com.wuyou.merchant.R;
import com.wuyou.merchant.adapter.MainPagerAdapter;
import com.wuyou.merchant.mvp.circle.CircleFragment;
import com.wuyou.merchant.mvp.order.OrderFragment;
import com.wuyou.merchant.mvp.store.StoreFragment;
import com.wuyou.merchant.mvp.wallet.WalletFragment;
import com.wuyou.merchant.view.widget.NoScrollViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.rong.imkit.fragment.ConversationListFragment;
import io.rong.imlib.model.Conversation;

public class MainActivity extends BaseActivity {
    @BindView(R.id.main_tab)
    BottomNavigationViewEx bottomView;
    @BindView(R.id.main_pager)
    NoScrollViewPager viewPager;
    List<Fragment> fragments = new ArrayList<>();
    OrderFragment orderFragment = new OrderFragment();
    private long mkeyTime = 0;

    @Override
    protected int getContentLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void bindView(Bundle savedInstanceState) {
        fragments.add(orderFragment);
        fragments.add(new CircleFragment());
        fragments.add(new WalletFragment());
        fragments.add(getMessageFragment());
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

    private Fragment getMessageFragment() {
        ConversationListFragment fragment = new ConversationListFragment();
        Uri uri = Uri.parse("rong://" + getCtx().getApplicationInfo().packageName).buildUpon()
                .appendPath("conversationlist")
                .appendQueryParameter(Conversation.ConversationType.PRIVATE.getName(), "false") //设置私聊会话非聚合显示
                .appendQueryParameter(Conversation.ConversationType.GROUP.getName(), "true")//设置群组会话聚合显示
                .appendQueryParameter(Conversation.ConversationType.DISCUSSION.getName(), "false")//设置讨论组会话非聚合显示
                .appendQueryParameter(Conversation.ConversationType.SYSTEM.getName(), "false")//设置系统会话非聚合显示
                .build();
        fragment.setUri(uri);
        return fragment;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if ((System.currentTimeMillis() - mkeyTime) > 2000) {
                mkeyTime = System.currentTimeMillis();
                ToastUtils.ToastMessage(getCtx(),"再按一次退出");
            } else {
                finish();
                System.exit(0);
            }
            return false;
        }

        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        orderFragment.loadDatas();
    }
}
