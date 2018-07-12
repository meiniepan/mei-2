package com.wuyou.merchant.mvp.login;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.View;
import android.widget.Button;

import com.gs.buluo.common.utils.ToastUtils;
import com.wuyou.merchant.R;
import com.wuyou.merchant.view.activity.BaseActivity;
import com.wuyou.merchant.view.widget.NoScrollViewPager;
import com.wuyou.merchant.view.widget.panel.EnvironmentChoosePanel;

import butterknife.BindView;

/**
 * Created by Administrator on 2018\1\26 0026.
 */

public class LoginActivity extends BaseActivity {
    @BindView(R.id.tl_login_tab)
    TabLayout mTabLayout;
    @BindView(R.id.vp_login_pager)
    NoScrollViewPager mViewPager;
    @BindView(R.id.v_login_musk)
    Button btnLoginMusk;
    String[] mTitle = {"手机号快捷登录", "账户密码登录"};

    @Override
    protected int getContentLayout() {
        return R.layout.activity_login;
    }

    @Override
    protected void bindView(Bundle savedInstanceState) {
        initView();
        findViewById(R.id.back_door).setOnClickListener(v -> showChangeEnvironment());
    }

    @SuppressLint("ClickableViewAccessibility")
    private void initView() {
        mViewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            //此方法用来显示tab上的名字
            @Override
            public CharSequence getPageTitle(int position) {
                return mTitle[position % mTitle.length];
            }

            @Override
            public Fragment getItem(int position) {
                //创建Fragment并返回
                Fragment fragment = null;
                if (position == 0)
                    fragment = new PhoneLoginFragment();
                else if (position == 1)
                    fragment = new AccountLoginFragment();

                return fragment;
            }

            @Override
            public int getCount() {
                return mTitle.length;
            }
        });
        //将ViewPager关联到TabLayout上

        mTabLayout.setupWithViewPager(mViewPager);
        mViewPager.setCurrentItem(1);

        btnLoginMusk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.ToastMessage(getCtx(), "功能正在开发");
            }
        });

    }
    private int clickTime = 0;
    private long firstTime = 0;
    private void showChangeEnvironment() {
        if (clickTime == 0) {
            firstTime = System.currentTimeMillis();
        }
        clickTime++;
        if (clickTime == 5) {
            long nowTime = System.currentTimeMillis();
            if (nowTime - firstTime <= 2000) {
                EnvironmentChoosePanel choosePanel = new EnvironmentChoosePanel(this);
                choosePanel.show();
                clickTime = 0;
                firstTime = 0;
            }
        }
    }

}
