package com.wuyou.merchant.mvp.commodity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

import com.gs.buluo.common.network.BaseSubscriber;
import com.gs.buluo.common.utils.ToastUtils;
import com.gs.buluo.common.widget.CustomAlertDialog;
import com.gs.buluo.common.widget.recyclerHelper.BaseHolder;
import com.gs.buluo.common.widget.recyclerHelper.BaseQuickAdapter;
import com.gs.buluo.common.widget.recyclerHelper.RefreshRecyclerView;
import com.wuyou.merchant.R;
import com.wuyou.merchant.bean.entity.BaseKunResponse;
import com.wuyou.merchant.bean.entity.CommodityEntity;
import com.wuyou.merchant.bean.entity.KunListEntity;
import com.wuyou.merchant.bean.entity.ServiceNewEntity;
import com.wuyou.merchant.bean.entity.ServiceOffReq;
import com.wuyou.merchant.bean.entity.ServiceReq;
import com.wuyou.merchant.network.CarefreeRetrofit2;
import com.wuyou.merchant.network.apis.OrderApis;
import com.wuyou.merchant.util.glide.GlideUtils;
import com.wuyou.merchant.view.activity.BaseActivity;
import com.wuyou.merchant.view.widget.CarefreeRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by DELL on 2018/12/26.
 */

public class CommodityManagerActivity extends BaseActivity {
    @BindView(R.id.commodity_manager_tab)
    TabLayout commodityManagerTab;
    @BindView(R.id.commodity_manager_pager)
    ViewPager commodityManagerPager;

    RefreshRecyclerView commodityListView;
    CustomAlertDialog customAlertDialog;
    CommodityAdapter saleAdapter1, saleAdapter2, saleAdapter3;
    List<ServiceNewEntity> data = new ArrayList<>();
    List<ServiceNewEntity> data1 = new ArrayList<>();
    List<ServiceNewEntity> data2 = new ArrayList<>();
    List<ServiceNewEntity> data3 = new ArrayList<>();

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
        getData();
    }

    private void getData() {
        ServiceReq req = new ServiceReq();
        req.shopId = "5c36bb413b7750468fd79a03";
        req.page = 1;
        CarefreeRetrofit2.getInstance().createApi(OrderApis.class)
                .getService(req)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<BaseKunResponse<KunListEntity<ServiceNewEntity>>>() {
                    @Override
                    public void onSuccess(BaseKunResponse<KunListEntity<ServiceNewEntity>> response) {
                        data1.clear();
                        data2.clear();
                        data3.clear();
                        data = response.data.serviceList;
                        for (ServiceNewEntity e : data
                                ) {
                            if (0 == e.status) {
                                data2.add(e);
                            } else if (1 == e.status) {
                                data1.add(e);
                            }else {
                                data3.add(e);
                            }
                        }
                        saleAdapter1.notifyDataSetChanged();
                        saleAdapter2.notifyDataSetChanged();
                        saleAdapter3.notifyDataSetChanged();
                    }

                });
    }

    class CommodityPagerAdapter extends PagerAdapter {
        @Override
        public int getCount() {
            return 3;
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            commodityListView = new RefreshRecyclerView(getCtx());
            commodityListView.getRecyclerView().setEmptyDrawable(getResources().getDrawable(R.mipmap.empty_score));
            if (0 == position) {
                saleAdapter1 = new CommodityAdapter();
                commodityListView.setAdapter(saleAdapter1);
                saleAdapter1.setNewData(data1);
                saleAdapter1.setOnItemLongClickListener(new BaseQuickAdapter.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {

                        customAlertDialog = new CustomAlertDialog.Builder(getCtx()).setTitle(R.string.prompt).setMessage("您确定要下架该服务吗?")
                                .setPositiveButton(getString(R.string.yes), (dialog, which) -> doServiceOff(saleAdapter1.getData().get(i).serviceId))
                                .setNegativeButton(getString(R.string.cancel), (dialog, which) -> {
                                    customAlertDialog.dismiss();
                                }).create();
                        customAlertDialog.show();
                        return false;
                    }
                });
            } else if (1 == position) {
                saleAdapter2 = new CommodityAdapter();
                commodityListView.setAdapter(saleAdapter2);
                saleAdapter2.setNewData(data2);
            } else if (2 == position) {
                saleAdapter3 = new CommodityAdapter();
                commodityListView.setAdapter(saleAdapter3);
                saleAdapter3.setNewData(data3);
            }
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

    private void doServiceOff(String serviceId) {
        ServiceOffReq req = new ServiceOffReq();
        req.shopId = 12;
        req.serviceId = new String[]{serviceId};
        CarefreeRetrofit2.getInstance().createApi(OrderApis.class)
                .OffService(req)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<BaseKunResponse>() {
                    @Override
                    public void onSuccess(BaseKunResponse response) {
                        ToastUtils.ToastMessage(getCtx(), "服务已下架成功！");
                        getData();
                    }
                });
    }


    class CommodityAdapter extends BaseQuickAdapter<ServiceNewEntity, BaseHolder> {
        CommodityAdapter() {
            super(R.layout.item_manager_commodity);
        }

        @Override
        protected void convert(BaseHolder baseHolder, ServiceNewEntity entity) {
            baseHolder.setText(R.id.item_commodity_name, entity.name)
                    .setText(R.id.item_commodity_sale, entity.sales + "")
                    .setText(R.id.item_commodity_store, entity.inventory + "")
                    .setText(R.id.item_commodity_price, entity.price + "");
            GlideUtils.loadImage(getCtx(), "http://image.ulaidao.cn\\/avatar\\/2018-07-19\\/a3484798-8b37-11e8-ba73-00163e0ead4f", baseHolder.getView(R.id.item_commodity_avatar));
        }
    }

}
