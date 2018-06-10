package com.wuyou.merchant.mvp.circle;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.wuyou.merchant.R;
import com.wuyou.merchant.mvp.order.OrderStatusFragment;
import com.wuyou.merchant.view.fragment.BaseFragment;

import butterknife.BindView;


/**
 * Created by Administrator on 2018\1\29 0029.
 */

public class CircleFragment extends BaseFragment {
    @BindView(R.id.tl_tab)
    TabLayout mTabLayout;
    @BindView(R.id.vp_pager)
    ViewPager mViewPager;
    String[] mTitle = {"我创建的合约", "我加入的合约", "合约市场"};
    FragmentPagerAdapter fragmentPagerAdapter;
    private CircleStatusFragment fragment1;
    private CircleStatusFragment fragment2;
    private CircleStatusFragment fragment3;

    @Override
    protected int getContentLayout() {
        return R.layout.fragment_circle_2;
    }

    @Override
    protected void bindView(Bundle savedInstanceState) {
        initView();
    }

    private void initView() {
        Bundle bundle1 = new Bundle();
        bundle1.putInt("h",1);
        Bundle bundle2 = new Bundle();
        bundle2.putInt("h",2);
        Bundle bundle3 = new Bundle();
        bundle3.putInt("h",3);
        fragment1 = new CircleStatusFragment();
        fragment1.setArguments(bundle1);
        fragment2 = new CircleStatusFragment();
        fragment2.setArguments(bundle2);
        fragment3 = new CircleStatusFragment();
        fragment3.setArguments(bundle3);
        fragmentPagerAdapter = new FragmentPagerAdapter(getChildFragmentManager()) {
            //此方法用来显示tab上的名字
            @Override
            public CharSequence getPageTitle(int position) {
                return mTitle[position % mTitle.length];
            }

            @Override
            public Fragment getItem(int position) {
                Fragment fragment = null;
                if (position == 0) {

                    fragment = fragment1;
                } else if (position == 1) {

                    fragment = fragment2;
                } else if (position == 2) {

                    fragment = fragment3;
                }
                return fragment;
            }

            @Override
            public int getCount() {
                return mTitle.length;
            }
        };
        mViewPager.setAdapter(fragmentPagerAdapter);
        //将ViewPager关联到TabLayout上
        mTabLayout.setupWithViewPager(mViewPager);
    }

    @Override
    public void showError(String message, int res) {

    }

    public void refreshCreatedList() {
        fragment1.loadData();
    }
}
