package com.wuyou.merchant.mvp.wallet;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.view.MotionEvent;
import android.view.View;
import android.widget.HorizontalScrollView;

import com.gs.buluo.common.widget.StatusLayout;
import com.wuyou.merchant.Constant;
import com.wuyou.merchant.R;
import com.wuyou.merchant.adapter.TradeListRvAdapter;
import com.wuyou.merchant.bean.entity.ResponseListEntity;
import com.wuyou.merchant.bean.entity.TradeItemEntity;
import com.wuyou.merchant.util.CommonUtil;
import com.wuyou.merchant.view.fragment.BaseFragment;
import com.wuyou.merchant.view.widget.recyclerHelper.NewRefreshRecyclerView;

import java.util.List;

import butterknife.BindView;

/**
 * Created by Solang on 2018/3/26.
 */

public class WalletIncomeFragment extends BaseFragment<WalletContract.View, WalletContract.Presenter> implements WalletContract.View {
//    @BindView(R.id.wallet_credit)
//    TextView walletCredit;
//    @BindView(R.id.wallet_benefit)
//    TextView walletIncome;
    @BindView(R.id.recyclerview)
    NewRefreshRecyclerView recyclerView;
    @BindView(R.id.sl_list_layout)
    StatusLayout statusLayout;
    TradeListRvAdapter adapter;
    List<TradeItemEntity> data;
    @BindView(R.id.hsv)
    HorizontalScrollView horizontalScrollView;
    private ViewPager vp;


    @Override
    protected int getContentLayout() {
        return R.layout.fragment_income_wallet;
    }


    @Override
    protected WalletContract.Presenter getPresenter() {
        return new WalletPresenter();
    }

    @Override
    protected void bindView(Bundle savedInstanceState) {
        statusLayout.setErrorAction(v -> {
            statusLayout.showProgressView();
            adapter.clearData();
            fetchDatas();
        });

        horizontalScrollView.post(new Runnable() {
            @Override
            public void run() {
                horizontalScrollView.scrollTo(CommonUtil.getScreenWidth(getActivity()), 0);
            }
        });
        horizontalScrollView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                vp.scrollTo((int)event.getX(),(int)event.getY());
                return true;
            }
        });

        statusLayout.showProgressView();
        fetchDatas();
        adapter = new TradeListRvAdapter(R.layout.item_trade);
        adapter.setOnItemClickListener((adapter1, view, position) -> {
            Intent intent = new Intent(getActivity(), TradeDetailActivity.class);
            intent.putExtra(Constant.TRANSACTION_ENTITY, adapter.getItem(position).order_id);
            startActivity(intent);
        });
        recyclerView.getRecyclerView().setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
        recyclerView.setRefreshAction(() -> {
            adapter.clearData();
            fetchDatas();
        });
    }
    public void setViewPager(ViewPager vp){
        this.vp = vp;
    }

    @Override
    public void loadData() {
//        statusLayout.showProgressView();
//        fetchDatas();
    }

    private void fetchDatas() {
        mPresenter.getTradeList();
    }

    @Override
    public void showError(String message, int res) {
        recyclerView.setRefreshFinished();
        statusLayout.showErrorView(message);
    }

    @Override
    public void getSuccess(ResponseListEntity data) {
        recyclerView.setRefreshFinished();
        adapter.setNewData(data.list);
        statusLayout.showContentView();
        if (adapter.getData().size() == 0) {
            statusLayout.showEmptyView("没有交易");
        }
    }


    @Override
    public void getMore(ResponseListEntity data) {

    }

    @Override
    public void loadMoreError(int code) {

    }

}
