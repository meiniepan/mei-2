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
import com.wuyou.merchant.bean.entity.TradeItemEntity;
import com.wuyou.merchant.bean.entity.WalletInfoEntity;
import com.wuyou.merchant.mvp.wallet.ContractTradeDetailActivity;
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
    private StatusLayout statusLayout1;
    private MyRefreshRecyclerView recyclerView1;
    private FundListRvAdapter fundListRvAdapter;

    private StatusLayout orderStatusLayout;
    private MyRefreshRecyclerView orderRecyclerView;
    private TradeListRvAdapter tradeListRvAdapter;

    private StatusLayout contractStatusLayout;
    private MyRefreshRecyclerView contractRecyclerView;
    private TradeListRvAdapter contractListRvAdapter;


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
            orderStatusLayout = helper.getView(R.id.sl_wallet_foot);
            orderRecyclerView = helper.getView(R.id.rv_wallet_foot);
            initOrderInComeAdapter();
        } else if (helper.getAdapterPosition() == 2) {
            title.setVisibility(View.GONE);
            contractStatusLayout = helper.getView(R.id.sl_wallet_foot);
            contractRecyclerView = helper.getView(R.id.rv_wallet_foot);
            initContractIncomeAdapter();
        }
    }

    private void initContractIncomeAdapter() {
        getContractTradeList("0", "1");
        contractListRvAdapter = new TradeListRvAdapter(2, R.layout.item_trade);
        contractStatusLayout.setErrorAndEmptyAction(v -> {
            contractStatusLayout.showProgressView();
            getContractTradeList("0", "1");
        });
        contractListRvAdapter.setOnItemClickListener((adapter1, view, position) -> {
            Intent intent = new Intent(activity, ContractTradeDetailActivity.class);
            intent.putExtra(Constant.TRANSACTION_ENTITY, contractListRvAdapter.getItem(position));
            activity.startActivity(intent);
        });
        contractRecyclerView.getRecyclerView().setLayoutManager(new LinearLayoutManager(activity));
        contractRecyclerView.setAdapter(contractListRvAdapter);
        contractListRvAdapter.setOnLoadMoreListener(() -> getContractTradeList(lastContractId, "2"), contractRecyclerView.getRecyclerView());
        contractRecyclerView.setRefreshAction(() -> {
            if (onRefreshListener != null) onRefreshListener.onRefresh();
            getContractTradeList("0", "1");
        });
    }

    private void initOrderInComeAdapter() {
        getOrderTradeList("0", "1");
        tradeListRvAdapter = new TradeListRvAdapter(1, R.layout.item_trade);
        orderStatusLayout.setErrorAndEmptyAction(v -> {
            orderStatusLayout.showProgressView();
            getOrderTradeList("0", "1");
        });
        tradeListRvAdapter.setOnItemClickListener((adapter1, view, position) -> {
            Intent intent = new Intent(activity, TradeDetailActivity.class);
            intent.putExtra(Constant.TRANSACTION_ENTITY, tradeListRvAdapter.getItem(position));
            activity.startActivity(intent);
        });
        orderRecyclerView.getRecyclerView().setLayoutManager(new LinearLayoutManager(activity));
        orderRecyclerView.setAdapter(tradeListRvAdapter);
        tradeListRvAdapter.setOnLoadMoreListener(() -> getOrderTradeList(lastTradeId, "2"), orderRecyclerView.getRecyclerView());
        orderRecyclerView.setRefreshAction(() -> {
            getOrderTradeList("0", "1");
            if (onRefreshListener != null) onRefreshListener.onRefresh();
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
            intent.putExtra(Constant.FUND_STATUS, fundListRvAdapter.getItem(position).status);
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
                .getFundList(CarefreeDaoSession.getInstance().getUserInfo().getShop_id(), QueryMapBuilder.getIns()
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

    public void getFunList() {
        CarefreeRetrofit.getInstance().createApi(WalletApis.class)
                .getFundList(CarefreeDaoSession.getInstance().getUserInfo().getShop_id(), QueryMapBuilder.getIns()
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

    private String lastTradeId = "0";

    private void getOrderTradeList(String startId, String flag) {
        CarefreeRetrofit.getInstance().createApi(WalletApis.class)
                .getOrderTradeList(CarefreeApplication.getInstance().getUserInfo().getShop_id(), QueryMapBuilder.getIns()
                        .put("start_id", startId)
                        .put("flag", flag)
                        .put("size", "10")
                        .buildGet())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<BaseResponse<ResponseListEntity<TradeItemEntity>>>() {
                    @Override
                    public void onSuccess(BaseResponse<ResponseListEntity<TradeItemEntity>> response) {
                        ResponseListEntity<TradeItemEntity> res = response.data;
                        if ("2".equals(flag)) {
                            tradeListRvAdapter.addData(res.list);
                        } else {
                            orderRecyclerView.setRefreshFinished();
                            tradeListRvAdapter.setNewData(res.list);
                        }

                        if (tradeListRvAdapter.getData().size() == 0) {
                            orderStatusLayout.showEmptyView("暂无营收记录");
                        } else {
                            orderStatusLayout.showContentView();
                            lastTradeId = res.list.get(res.list.size() - 1).order_id;
                        }
                        if (res.has_more.equals("0")) {
                            tradeListRvAdapter.loadMoreEnd(true);
                        }
                    }

                    @Override
                    protected void onFail(ApiException e) {
                        if ("2".equals(flag)) {
                            tradeListRvAdapter.loadMoreFail();
                        } else {
                            orderRecyclerView.setRefreshFinished();
                            orderStatusLayout.showErrorView(e.getDisplayMessage());
                        }
                    }
                });
    }

    private String lastContractId;

    public void getContractTradeList(String startId, String flag) {
        CarefreeRetrofit.getInstance().createApi(WalletApis.class)
                .getContractTradeList(CarefreeDaoSession.getInstance().getUserInfo().getShop_id(), QueryMapBuilder.getIns()
                        .put("start_id", startId)
                        .put("flag", flag)
                        .put("size", "10")
                        .buildGet())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<BaseResponse<ResponseListEntity<TradeItemEntity>>>() {
                    @Override
                    public void onSuccess(BaseResponse<ResponseListEntity<TradeItemEntity>> response) {
                        ResponseListEntity<TradeItemEntity> res = response.data;
                        if ("2".equals(flag)) {
                            contractListRvAdapter.addData(res.list);
                        } else {
                            contractRecyclerView.setRefreshFinished();
                            contractListRvAdapter.setNewData(res.list);
                        }

                        if (contractListRvAdapter.getData().size() == 0) {
                            contractStatusLayout.showEmptyView("暂无营收记录");
                        } else {
                            contractStatusLayout.showContentView();
                            lastContractId = res.list.get(res.list.size() - 1).order_id;
                        }
                        if (res.has_more.equals("0")) {
                            contractListRvAdapter.loadMoreEnd(true);
                        }
                    }

                    @Override
                    protected void onFail(ApiException e) {
                        if ("2".equals(flag)) {
                            contractListRvAdapter.loadMoreFail();
                        } else {
                            contractRecyclerView.setRefreshFinished();
                            contractStatusLayout.showErrorView(e.getDisplayMessage());
                        }
                    }
                });
    }

    public void setOnRefreshListener(OnRefreshListener onRefreshListener) {
        this.onRefreshListener = onRefreshListener;
    }

    private OnRefreshListener onRefreshListener;

    public interface OnRefreshListener {
        void onRefresh();
    }
}
