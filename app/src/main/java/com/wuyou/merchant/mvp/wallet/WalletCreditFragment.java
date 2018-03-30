//package com.wuyou.merchant.mvp.wallet;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.support.v4.view.ViewPager;
//import android.support.v7.widget.LinearLayoutManager;
//import android.view.MotionEvent;
//import android.view.View;
//import android.widget.HorizontalScrollView;
//import android.widget.TextView;
//
//import com.gs.buluo.common.utils.ToastUtils;
//import com.gs.buluo.common.widget.CustomAlertDialog;
//import com.gs.buluo.common.widget.StatusLayout;
//import com.wuyou.merchant.Constant;
//import com.wuyou.merchant.R;
//import com.wuyou.merchant.adapter.FundListRvAdapter;
//import com.wuyou.merchant.bean.entity.FundEntity;
//import com.wuyou.merchant.bean.entity.ResponseListEntity;
//import com.wuyou.merchant.bean.entity.WalletInfoEntity;
//import com.wuyou.merchant.view.fragment.BaseFragment;
//import com.wuyou.merchant.view.widget.recyclerHelper.NewRefreshRecyclerView;
//
//import java.util.List;
//
//import butterknife.BindView;
//import butterknife.OnClick;
//
///**
// * Created by Solang on 2018/3/26.
// */
//
//public class WalletCreditFragment extends BaseFragment<WalletContract.View, WalletContract.Presenter> implements WalletContract.View {
////    @BindView(R.id.wallet_credit)
////    TextView walletCredit;
////    @BindView(R.id.wallet_benefit)
////    TextView walletIncome;
//    @BindView(R.id.wallet_limit)
//    TextView walletLimit;
//    @BindView(R.id.wallet_borrow)
//    TextView walletBorrow;
//    @BindView(R.id.wallet_pay_back)
//    TextView walletPayBack;
//    @BindView(R.id.recyclerview)
//    NewRefreshRecyclerView recyclerView;
//    @BindView(R.id.sl_list_layout)
//    StatusLayout statusLayout;
//    FundListRvAdapter adapter;
//    List<FundEntity> data;
//    @BindView(R.id.hsv)
//    HorizontalScrollView horizontalScrollView;
//
//    private WalletInfoEntity entity = new WalletInfoEntity();
//    private String sCredit;
//    private ViewPager vp;
//
//    public void setViewPager(ViewPager vp){
//        this.vp = vp;
//    }
//
//    @Override
//    protected int getContentLayout() {
//        return R.layout.fragment_credit_wallet;
//    }
//
//
//    @Override
//    protected WalletContract.Presenter getPresenter() {
//        return new WalletPresenter();
//    }
//
//    @Override
//    protected void bindView(Bundle savedInstanceState) {
//        horizontalScrollView.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                vp.scrollTo((int)event.getX(),(int)event.getY());
//                return true;
//            }
//        });
//
//        entity = getArguments().getParcelable(Constant.WALLET_INFO_ENTITY);
//        initWalletInfo();
//        statusLayout.setErrorAndEmptyAction(v -> {
//            statusLayout.showProgressView();
//            adapter.clearData();
//            fetchDatas();
//        });
//        adapter = new FundListRvAdapter(R.layout.item_fund, data);
//        adapter.setOnItemClickListener((adapter1, view, position) -> {
//            Intent intent = new Intent(getActivity(), FundIntroduceActivity.class);
//            intent.putExtra(Constant.FUND_ID, adapter.getItem(position).fund_id);
//            startActivity(intent);
//        });
//        recyclerView.getRecyclerView().setLayoutManager(new LinearLayoutManager(getContext()));
//        recyclerView.setAdapter(adapter);
//        adapter.setOnLoadMoreListener(() -> mPresenter.loadMore(), recyclerView.getRecyclerView());
//        recyclerView.setRefreshAction(() -> {
//            adapter.clearData();
//            fetchDatas();
//        });
//    }
//
//    private void initWalletInfo() {
//        walletLimit.setText(entity.available_amount);
//        walletBorrow.setText(entity.used_amount);
//        walletPayBack.setText(entity.payable_amount);
//    }
//
//
//    @Override
//    public void loadData() {
//        statusLayout.showProgressView();
//        fetchDatas();
//    }
//
//    private void fetchDatas() {
//        mPresenter.getFundList();
//    }
//
//    @Override
//    public void showError(String message, int res) {
//        recyclerView.setRefreshFinished();
//        statusLayout.showErrorView(message);
//    }
//
//    @Override
//    public void getSuccess(ResponseListEntity data) {
//        recyclerView.setRefreshFinished();
//        adapter.setNewData(data.list);
//        statusLayout.showContentView();
//        if (data.has_more.equals("0")) {
//            adapter.loadMoreEnd(true);
//        }
//        if (adapter.getData().size() == 0) {
//            statusLayout.showEmptyView("没有基金");
//        }
//    }
//
//
//    @Override
//    public void getMore(ResponseListEntity data) {
//        adapter.addData(data.list);
//        if (data.has_more.equals("0")) {
//            adapter.loadMoreEnd(true);
//        }
//    }
//
//    @Override
//    public void loadMoreError(int code) {
//        adapter.loadMoreFail();
//    }
//
//
//    @OnClick({ R.id.ll_wallet_borrow, R.id.ll_wallet_limit, R.id.ll_wallet_pay_back, R.id.btn_apply
//    })
//    public void onViewClicked(View view) {
//        switch (view.getId()) {
//            case R.id.ll_wallet_limit:
//                CustomAlertDialog.Builder builder = new CustomAlertDialog.Builder(getContext());
//                builder.setMessage("由商家服务和经营，以及履约能力等各方面综合分析以后，决定的借款额度，请保持良好的守约习惯，提高信用分。");
//                builder.setPositiveButton("确定", null);
//                builder.create().show();
//                break;
//            case R.id.ll_wallet_borrow:
//                Intent intent = new Intent(getContext(), Loan2Activity.class);
//                intent.putExtra(Constant.WALLET_INFO_ENTITY, entity);
//                startActivity(intent);
//
//                break;
//            case R.id.ll_wallet_pay_back:
//                startActivity(new Intent(getContext(), RepayActivity.class));
//                break;
//            case R.id.btn_apply:
////                startActivity(new Intent(getContext(), LoanActivity.class));
//                ToastUtils.ToastMessage(getContext(), "功能还在开发！");
//                break;
////            case R.id.ll_credit:
////                Intent intent1 = new Intent(getContext(), CreditDetailActivity.class);
////                intent1.putExtra(Constant.CREDIT_SCORE, sCredit);
////                startActivity(intent1);
////                break;
//        }
//    }
//}
