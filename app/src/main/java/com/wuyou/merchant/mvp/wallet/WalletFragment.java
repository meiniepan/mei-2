package com.wuyou.merchant.mvp.wallet;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.view.LayoutInflater;
import android.view.View;

import com.gs.buluo.common.network.ApiException;
import com.gs.buluo.common.network.BaseResponse;
import com.gs.buluo.common.network.BaseSubscriber;
import com.gs.buluo.common.network.QueryMapBuilder;
import com.gs.buluo.common.utils.DensityUtils;
import com.gs.buluo.common.utils.ToastUtils;
import com.gs.buluo.common.widget.StatusLayout;
import com.wuyou.merchant.CarefreeApplication;
import com.wuyou.merchant.CarefreeDaoSession;
import com.wuyou.merchant.Constant;
import com.wuyou.merchant.R;
import com.wuyou.merchant.adapter.WalletFootAdapter;
import com.wuyou.merchant.adapter.WalletHeaderAdapter;
import com.wuyou.merchant.bean.entity.WalletIncomeEntity;
import com.wuyou.merchant.bean.entity.WalletInfoEntity;
import com.wuyou.merchant.interfaces.ScrollViewListener;
import com.wuyou.merchant.network.CarefreeRetrofit;
import com.wuyou.merchant.network.apis.WalletApis;
import com.wuyou.merchant.util.CommonUtil;
import com.wuyou.merchant.view.fragment.BaseFragment;
import com.wuyou.merchant.view.widget.WalletFootRecyclerView;
import com.wuyou.merchant.view.widget.WalletHeadRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Administrator on 2018\1\29 0029.
 */

public class WalletFragment extends BaseFragment implements ScrollViewListener, WalletFootAdapter.OnRefreshListener {

    @BindView(R.id.rv_head)
    public WalletHeadRecyclerView rvHead;
    @BindView(R.id.rv_foot)
    public WalletFootRecyclerView rvFoot;
    @BindView(R.id.sl_wallet)
    StatusLayout statusLayout;
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
        statusLayout.setErrorAction(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                statusLayout.showProgressView();
                initWalletInfo();
            }
        });
        initRvHead();
        initRvFoot();
        rvFoot.setOnScrollViewListener(this);
        rvHead.setOnScrollViewListener(this);
    }

    @Override
    public void fetchData() {
        initWalletInfo();
    }

    private void initRvHead() {
        List list = new ArrayList();
        list.add(entity);
        list.add(entity);
        list.add(entity);
        adapter = new WalletHeaderAdapter(R.layout.item_wallet_header, list);
        initHeadFoot();
        rvHead.setAdapter(adapter);
        new PagerSnapHelper().attachToRecyclerView(rvHead);
        adapter.setOnItemClickListener((adapter, view, position) -> {
            if (position == 0) {
                Intent intent1 = new Intent(getContext(), CreditDetailActivity.class);
                intent1.putExtra(Constant.CREDIT_SCORE, sCredit);
                startActivity(intent1);
            }
        });
    }

    private void initRvFoot() {
        ArrayList<WalletInfoEntity> list = new ArrayList<>();
        list.add(entity);
        list.add(entity);
        list.add(entity);
        adapterFoot = new WalletFootAdapter(getActivity(), R.layout.item_wallet_foot, list);
        rvFoot.setAdapter(adapterFoot);
        new PagerSnapHelper().attachToRecyclerView(rvFoot);
        adapterFoot.setOnRefreshListener(this);
    }

    @Override
    public void onRefresh() {
        CarefreeRetrofit.getInstance().createApi(WalletApis.class).getWalletIncome(CarefreeDaoSession.getInstance().getUserInfo().getShop_id(), QueryMapBuilder.getIns().buildGet())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<BaseResponse<WalletIncomeEntity>>() {
                    @Override
                    public void onSuccess(BaseResponse<WalletIncomeEntity> walletInfoEntityBaseResponse) {
                        adapter.setData(1, new WalletInfoEntity(walletInfoEntityBaseResponse.data.order));
                        adapter.setData(2, new WalletInfoEntity(walletInfoEntityBaseResponse.data.contract));
                        adapter.notifyDataSetChanged();
                    }
                });
    }

    private void initHeadFoot() {
        View head = LayoutInflater.from(getContext()).inflate(R.layout.wallet_header_foot, rvHead, false);
        View foot = LayoutInflater.from(getContext()).inflate(R.layout.wallet_header_foot, rvHead, false);
        int width = DensityUtils.dip2px(getContext(), 30);
        int width1 = DensityUtils.dip2px(getContext(), 30);
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
        statusLayout.showProgressView();
        CarefreeRetrofit.getInstance().createApi(WalletApis.class)
                .getCredit(QueryMapBuilder.getIns().put("shop_id", CarefreeDaoSession.getInstance().getUserInfo().getShop_id()).buildGet())
                .subscribeOn(Schedulers.io())
                .flatMap(response -> {
                    entity = response.data;
                    sCredit = entity.score;
                    return CarefreeRetrofit.getInstance().createApi(WalletApis.class).getWalletIncome(CarefreeDaoSession.getInstance().getUserInfo().getShop_id(), QueryMapBuilder.getIns().buildGet());
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<BaseResponse<WalletIncomeEntity>>() {
                    @Override
                    public void onSuccess(BaseResponse<WalletIncomeEntity> response) {
                        statusLayout.showContentView();
                        rvHead.scrollToPosition(0);
                        rvFoot.scrollToPosition(0);
                        initData(response.data);
                    }

                    @Override
                    protected void onFail(ApiException e) {
                        statusLayout.showErrorView(e.getDisplayMessage());
                    }
                });
    }

    private void initData(WalletIncomeEntity data) {
        adapter.setData(1, new WalletInfoEntity(data.order));
        adapter.setData(2, new WalletInfoEntity(data.contract));
    }

    @Override
    public void showError(String message, int res) {
        ToastUtils.ToastMessage(mCtx, R.string.connect_fail);
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

    public void refreshFundList() {
        adapterFoot.getFunList();
    }

}
