package com.wuyou.merchant.mvp.wallet;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.gs.buluo.common.network.BaseResponse;
import com.gs.buluo.common.network.BaseSubscriber;
import com.gs.buluo.common.network.QueryMapBuilder;
import com.gs.buluo.common.utils.DensityUtils;
import com.wuyou.merchant.CarefreeApplication;
import com.wuyou.merchant.CarefreeDaoSession;
import com.wuyou.merchant.Constant;
import com.wuyou.merchant.R;
import com.wuyou.merchant.adapter.WalletFootAdapter;
import com.wuyou.merchant.adapter.WalletHeaderAdapter;
import com.wuyou.merchant.bean.entity.WalletInfoEntity;
import com.wuyou.merchant.interfaces.ScrollViewListener;
import com.wuyou.merchant.network.CarefreeRetrofit;
import com.wuyou.merchant.network.apis.WalletApis;
import com.wuyou.merchant.util.CommonUtil;
import com.wuyou.merchant.view.fragment.BaseFragment;
import com.wuyou.merchant.view.widget.WalletFootRecyclerView;
import com.wuyou.merchant.view.widget.WalletHeadRecyclerView;
import com.wuyou.merchant.view.widget.recyclerHelper.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Administrator on 2018\1\29 0029.
 */

public class WalletFragment extends BaseFragment implements ScrollViewListener {

    @BindView(R.id.rv_head)
    WalletHeadRecyclerView rvHead;
    @BindView(R.id.rv_foot)
    WalletFootRecyclerView rvFoot;
    private WalletInfoEntity entity = new WalletInfoEntity();
    private WalletHeaderAdapter adapter;
    private WalletFootAdapter adapterFoot;
    private String sCredit;

    @Override
    protected int getContentLayout() {
        return R.layout.fragment_wallet;
    }

    @Override
    protected WalletContract.Presenter getPresenter() {
        return new WalletPresenter();
    }

    @Override
    protected void bindView(Bundle savedInstanceState) {
        rvFoot.setOnScrollViewListener(this);
        rvHead.setOnScrollViewListener(this);
        initWalletInfo();
    }


    @Override
    public void loadData() {
        super.loadData();

    }


    private void initRvHead() {
        List list = new ArrayList();
        list.add(entity);
        list.add(entity);
        list.add(entity);
        adapter = new WalletHeaderAdapter(R.layout.item_wallet_header, list);
        initHeadFoot();
        rvHead.setAdapter(adapter);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
//                LinearLayoutManager layoutManager = (LinearLayoutManager) rvHead.getLayoutManager();
//                int first = layoutManager.findFirstVisibleItemPosition() - 1;
                if (position == 0){
                    Intent intent1 = new Intent(getContext(), CreditDetailActivity.class);
                intent1.putExtra(Constant.CREDIT_SCORE, sCredit);
                startActivity(intent1);
                }

            }
        });
//        mRecyclerView.setOnSelectListener(new CustomWheelRecyclerView.OnSelectListener() {
//            @Override
//            public void onSelect(int position) {
//
//            }
//        });
    }

    private void initRvFoot() {
        List list = new ArrayList();
        list.add(entity);
        list.add(entity);
        list.add(entity);
        adapterFoot = new WalletFootAdapter(getActivity(),R.layout.item_wallet_foot, list);
        rvFoot.setAdapter(adapterFoot);
        adapterFoot.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                LinearLayoutManager layoutManager = (LinearLayoutManager) rvHead.getLayoutManager();
                int first = layoutManager.findFirstVisibleItemPosition() - 1;

            }
        });

    }

    private void initHeadFoot() {
        View head = LayoutInflater.from(getContext()).inflate(R.layout.wallet_header_foot, rvHead, false);
        View foot = LayoutInflater.from(getContext()).inflate(R.layout.wallet_header_foot, rvHead, false);
        int width = DensityUtils.dip2px(getContext(), 20);
        int width1 = DensityUtils.dip2px(getContext(), 40);

        RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) head.getLayoutParams();
        params.width = width;
        head.setLayoutParams(params);
        adapter.addHeaderView(head, 0, 0);
        RecyclerView.LayoutParams params1 = (RecyclerView.LayoutParams) foot.getLayoutParams();
        params1.width = width1;
        foot.setLayoutParams(params1);
        adapter.addFooterView(foot, 0, 0);
    }

    private void initWalletInfo() {
        CarefreeRetrofit.getInstance().createApi(WalletApis.class)
                .getCredit(QueryMapBuilder.getIns()
                        .put("shop_id", CarefreeDaoSession.getInstance().getUserInfo().getShop_id())
                        .buildGet())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<BaseResponse<WalletInfoEntity>>() {
                    @Override
                    public void onSuccess(BaseResponse<WalletInfoEntity> response) {
                        entity = response.data;
                        sCredit = entity.score;
                        initRvHead();
                        initRvFoot();

                    }

                });
    }


    @Override
    public void showError(String message, int res) {

    }

    @Override
    public void onScrollChanged(Object scrollView, int x, int y) {
        int width1 = CommonUtil.getScreenWidth(getContext()) - DensityUtils.dip2px(CarefreeApplication.getInstance().getApplicationContext(), 60);
        int width2 = CommonUtil.getScreenWidth(getContext());
        if (scrollView == rvHead) {
            rvFoot.setmark(false);

            rvFoot.scrollTo(x * width2 / width1, y);
        } else if (scrollView == rvFoot) {
            rvHead.setmark(false);

            rvHead.scrollTo(x * width1 / width2, y);
        }
        rvHead.setmark(true);
        rvFoot.setmark(true);
    }
}
