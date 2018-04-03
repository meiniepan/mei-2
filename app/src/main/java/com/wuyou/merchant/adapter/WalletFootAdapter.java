package com.wuyou.merchant.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.gs.buluo.common.network.ApiException;
import com.gs.buluo.common.network.BaseResponse;
import com.gs.buluo.common.network.BaseSubscriber;
import com.gs.buluo.common.network.QueryMapBuilder;
import com.gs.buluo.common.widget.CustomAlertDialog;
import com.gs.buluo.common.widget.StatusLayout;
import com.wuyou.merchant.CarefreeApplication;
import com.wuyou.merchant.CarefreeDaoSession;
import com.wuyou.merchant.Constant;
import com.wuyou.merchant.R;
import com.wuyou.merchant.bean.entity.FundEntity;
import com.wuyou.merchant.bean.entity.ResponseListEntity;
import com.wuyou.merchant.bean.entity.TradeEntity;
import com.wuyou.merchant.bean.entity.WalletInfoEntity;
import com.wuyou.merchant.mvp.wallet.FundIntroduceActivity;
import com.wuyou.merchant.mvp.wallet.Loan2Activity;
import com.wuyou.merchant.mvp.wallet.TradeDetailActivity;
import com.wuyou.merchant.network.CarefreeRetrofit;
import com.wuyou.merchant.network.apis.WalletApis;
import com.wuyou.merchant.view.widget.MyRefreshRecyclerView;
import com.wuyou.merchant.view.widget.recyclerHelper.BaseHolder;
import com.wuyou.merchant.view.widget.recyclerHelper.BaseQuickAdapter;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Solang on 2017/10/24.
 */

public class WalletFootAdapter extends BaseQuickAdapter<WalletInfoEntity, BaseHolder> {
    private final Activity activity;
    private String lastId_list;
    List<FundEntity> data;
    List<TradeEntity> data2;
    StatusLayout statusLayout1;
    MyRefreshRecyclerView recyclerView1;
    StatusLayout statusLayout2;
    MyRefreshRecyclerView recyclerView2;
    FundListRvAdapter fundListRvAdapter;
    TradeListRvAdapter tradeListRvAdapter;

    public WalletFootAdapter(Activity activity, int layoutResId, @Nullable List<WalletInfoEntity> data) {
        super(layoutResId, data);
        this.activity = activity;
    }

    @Override
    protected void convert(BaseHolder helper, final WalletInfoEntity item) {
        View title = helper.getView(R.id.ll_wallet_foot_title);
        View limit = helper.getView(R.id.ll_wallet_limit0);
        View borrowed = helper.getView(R.id.ll_wallet_borrow0);


        if (helper.getAdapterPosition() == 0) {
            statusLayout1 = helper.getView(R.id.sl_wallet_foot);
            recyclerView1 = helper.getView(R.id.rv_wallet_foot);
            title.setVisibility(View.VISIBLE);
            helper.setText(R.id.tv_wallet_limit0, item.available_amount)
                    .setText(R.id.tv_wallet_borrow0, item.used_amount);
            limit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CustomAlertDialog.Builder builder = new CustomAlertDialog.Builder(activity);
                    builder.setMessage("由商家服务和经营，以及履约能力等各方面综合分析以后，决定的借款额度，请保持良好的守约习惯，提高信用分。");
                    builder.setPositiveButton("确定", null);
                    builder.create().show();
                }
            });
            borrowed.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(activity, Loan2Activity.class);
                    intent.putExtra(Constant.WALLET_INFO_ENTITY, item);
                    activity.startActivity(intent);
                }
            });
            initAdapter1();
        } else if (helper.getAdapterPosition() == 1) {
            title.setVisibility(View.GONE);
            statusLayout2 = helper.getView(R.id.sl_wallet_foot);
            recyclerView2 = helper.getView(R.id.rv_wallet_foot);
            initAdapter2();
        }
    }

    private void initAdapter2() {
        getTradeList();
        tradeListRvAdapter = new TradeListRvAdapter(R.layout.item_trade, data2);
        statusLayout2.setErrorAndEmptyAction(v -> {
            statusLayout2.showProgressView();
            tradeListRvAdapter.clearData();
            getTradeList();
        });
        tradeListRvAdapter.setOnItemClickListener((adapter1, view, position) -> {
            Intent intent = new Intent(activity, TradeDetailActivity.class);
            intent.putExtra(Constant.TRANSACTION_ID, tradeListRvAdapter.getItem(position).txid);
            activity.startActivity(intent);
        });
        recyclerView2.getRecyclerView().setLayoutManager(new LinearLayoutManager(activity));
        recyclerView2.setAdapter(tradeListRvAdapter);
//        tradeListRvAdapter.setOnLoadMoreListener(() -> loadTradeMore(), recyclerView2.getRecyclerView());
        recyclerView2.setRefreshAction(() -> {
            tradeListRvAdapter.clearData();
            getTradeList();
        });
    }



    private void initAdapter1() {
        getFunList();
        fundListRvAdapter = new FundListRvAdapter(R.layout.item_fund, data);
        statusLayout1.setErrorAndEmptyAction(v -> {
            statusLayout1.showProgressView();
            fundListRvAdapter.clearData();
            getFunList();
        });
        fundListRvAdapter.setOnItemClickListener((adapter1, view, position) -> {
            Intent intent = new Intent(activity, FundIntroduceActivity.class);
            intent.putExtra(Constant.FUND_ID, fundListRvAdapter.getItem(position).fund_id);
            activity.startActivity(intent);
        });
        recyclerView1.getRecyclerView().setLayoutManager(new LinearLayoutManager(activity));
        recyclerView1.setAdapter(fundListRvAdapter);
        fundListRvAdapter.setOnLoadMoreListener(() -> loadFundMore(), recyclerView1.getRecyclerView());
        recyclerView1.setRefreshAction(() -> {
            fundListRvAdapter.clearData();
            getFunList();
        });
    }

    private void loadFundMore() {
        CarefreeRetrofit.getInstance().createApi(WalletApis.class)
                .getFundList(CarefreeDaoSession.getInstance().getUserInfo().getShop_id(),QueryMapBuilder.getIns()
                        .put("start_id", lastId_list)
                        .put("flag", "2")
                        .put("size", "10")
                        .buildGet())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<BaseResponse<ResponseListEntity<FundEntity>>>() {
                    @Override
                    public void onSuccess(BaseResponse<ResponseListEntity<FundEntity>> response) {
                        if (response.data.list.size() > 0)
                            lastId_list = response.data.list.get(response.data.list.size() - 1).fund_id;
                        fundListRvAdapter.addData(response.data.list);
                        if (response.data.has_more.equals("0")) {
                            fundListRvAdapter.loadMoreEnd(true);
                        }
                    }

                    @Override
                    protected void onFail(ApiException e) {
                        fundListRvAdapter.loadMoreFail();
                    }
                });
    }

    private void getFunList() {
        CarefreeRetrofit.getInstance().createApi(WalletApis.class)
                .getFundList(CarefreeDaoSession.getInstance().getUserInfo().getShop_id(),QueryMapBuilder.getIns()
                        .put("start_id", "0")
                        .put("flag", "1")
                        .put("size", "10")
                        .buildGet())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<BaseResponse<ResponseListEntity<FundEntity>>>() {
                    @Override
                    public void onSuccess(BaseResponse<ResponseListEntity<FundEntity>> response) {
                        if (response.data.list.size() > 0)
                            lastId_list = response.data.list.get(response.data.list.size() - 1).fund_id;
                        recyclerView1.setRefreshFinished();
                        fundListRvAdapter.setNewData(response.data.list);
                        statusLayout1.showContentView();
                        if (response.data.has_more.equals("0")) {
                            fundListRvAdapter.loadMoreEnd(true);
                        }
                        if (fundListRvAdapter.getData().size() == 0) {
                            statusLayout1.showEmptyView("没有基金");
                        }
                    }

                    @Override
                    protected void onFail(ApiException e) {
                        recyclerView1.setRefreshFinished();
                        statusLayout1.showErrorView(e.getDisplayMessage());
                    }
                });
    }
    private void getTradeList() {
        CarefreeRetrofit.getInstance().createApi(WalletApis.class)
                .getTradelist(CarefreeApplication.getInstance().getUserInfo().getShop_id(),QueryMapBuilder.getIns().buildGet())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<BaseResponse<ResponseListEntity<TradeEntity>>>() {
                    @Override
                    public void onSuccess(BaseResponse<ResponseListEntity<TradeEntity>> response) {
                        recyclerView2.setRefreshFinished();
                        tradeListRvAdapter.setNewData(response.data.list);
                        statusLayout2.showContentView();
//                        if (response.data.has_more.equals("0")) {
//                            tradeListRvAdapter.loadMoreEnd(true);
//                        }
                        if (tradeListRvAdapter.getData().size() == 0) {
                            statusLayout2.showEmptyView("没有交易记录");
                        }
                    }

                    @Override
                    protected void onFail(ApiException e) {
                        recyclerView2.setRefreshFinished();
                        statusLayout2.showErrorView(e.getDisplayMessage());
                    }
                });
    }
}
