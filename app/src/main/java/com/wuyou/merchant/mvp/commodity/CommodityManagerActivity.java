package com.wuyou.merchant.mvp.commodity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

import com.gs.buluo.common.utils.ToastUtils;
import com.gs.buluo.common.widget.recyclerHelper.BaseHolder;
import com.gs.buluo.common.widget.recyclerHelper.BaseQuickAdapter;
import com.wuyou.merchant.R;
import com.wuyou.merchant.bean.entity.CommodityEntity;
import com.wuyou.merchant.util.glide.GlideUtils;
import com.wuyou.merchant.view.activity.BaseActivity;
import com.wuyou.merchant.view.widget.CarefreeRecyclerView;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by DELL on 2018/12/26.
 */

public class CommodityManagerActivity extends BaseActivity {
    @BindView(R.id.commodity_manager_tab)
    TabLayout commodityManagerTab;
    @BindView(R.id.commodity_manager_pager)
    ViewPager commodityManagerPager;

    CarefreeRecyclerView commodityListView;

    @Override
    protected int getContentLayout() {
        return R.layout.activity_commodity_manager;
    }

    String[] titles = {"销售中", "仓库中", "已售罄"};

    @Override
    protected void bindView(Bundle savedInstanceState) {
        setTitleText(R.string.commdodity_manager);
        commodityManagerPager.setAdapter(new CommodityPagerAdapter());
        commodityManagerTab.setupWithViewPager(commodityManagerPager);
        commodityManagerPager.setOffscreenPageLimit(2);
    }

    class CommodityPagerAdapter extends PagerAdapter {
        @Override
        public int getCount() {
            return 3;
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            commodityListView = new CarefreeRecyclerView(getCtx());
            commodityListView.setEmptyIcon(R.mipmap.empty_score);
            CommodityAdapter saleAdapter = new CommodityAdapter();
            commodityListView.setAdapter(saleAdapter);
            getData(position, saleAdapter);
            container.addView(commodityListView);
            return commodityListView;
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }
    }


    class CommodityAdapter extends BaseQuickAdapter<CommodityEntity, BaseHolder> {
        CommodityAdapter() {
            super(R.layout.item_manager_commodity);
        }

        @Override
        protected void convert(BaseHolder baseHolder, CommodityEntity commodityEntity) {
            baseHolder.setText(R.id.item_commodity_name, commodityEntity.title);
            GlideUtils.loadImage(getCtx(), "http://image.iwantmei.com\\/avatar\\/2018-07-19\\/a3484798-8b37-11e8-ba73-00163e0ead4f", baseHolder.getView(R.id.item_commodity_avatar));
        }
    }


    public void getData(int status, CommodityAdapter adapter) {
        ArrayList<CommodityEntity> list = new ArrayList<>();
        if (status == 0) {
            list.add(new CommodityEntity("哈哈哈哈"));
            list.add(new CommodityEntity("哈哈哈哈"));
            list.add(new CommodityEntity("哈哈哈哈"));
        } else if (status == 1) {
            list.add(new CommodityEntity("噢噢噢噢哦哦哦哦哦哦"));
            list.add(new CommodityEntity("噢噢噢噢哦哦哦哦哦哦"));
            list.add(new CommodityEntity("噢噢噢噢哦哦哦哦哦哦"));
        } else {
            list.add(new CommodityEntity("委屈翁无群二群翁群翁"));
            list.add(new CommodityEntity("委屈翁无群二群翁群翁"));
            list.add(new CommodityEntity("委屈翁无群二群翁群翁"));
            list.add(new CommodityEntity("委屈翁无群二群翁群翁"));
            list.add(new CommodityEntity("委屈翁无群二群翁群翁"));
        }
        adapter.setNewData(list);
    }
}
