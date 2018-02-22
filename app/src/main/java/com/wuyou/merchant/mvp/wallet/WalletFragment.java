package com.wuyou.merchant.mvp.wallet;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gs.buluo.common.network.BaseResponse;
import com.gs.buluo.common.network.BaseSubscriber;
import com.wuyou.merchant.CarefreeApplication;
import com.wuyou.merchant.R;
import com.wuyou.merchant.bean.entity.WalletInfoEntity;
import com.wuyou.merchant.network.CarefreeRetrofit;
import com.wuyou.merchant.network.apis.WalletApis;
import com.wuyou.merchant.view.fragment.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Administrator on 2018\1\29 0029.
 */

public class WalletFragment extends BaseFragment {


    @BindView(R.id.wallet_credit)
    TextView walletCredit;
    @BindView(R.id.wallet_benefit)
    TextView walletBenefit;
    @BindView(R.id.wallet_income)
    TextView walletIncome;
    @BindView(R.id.wallet_limit)
    TextView walletLimit;
    @BindView(R.id.wallet_borrow)
    TextView walletBorrow;
    @BindView(R.id.wallet_pay_back)
    TextView walletPayBack;

    @Override
    protected int getContentLayout() {
        return R.layout.fragment_wallet;
    }

    @Override
    protected void bindView(Bundle savedInstanceState) {

    }

    @Override
    public void fetchData() {
        CarefreeRetrofit.getInstance().createApi(WalletApis.class)
                .getWalletInfo(CarefreeApplication.getInstance().getUserInfo().getUid())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<BaseResponse<WalletInfoEntity>>() {
                    @Override
                    public void onSuccess(BaseResponse<WalletInfoEntity> walletInfoEntityBaseResponse) {
                        setData(walletInfoEntityBaseResponse.data);
                    }
                });
    }

    @Override
    public void showError(String message, int res) {

    }

    public void setData(WalletInfoEntity data) {
        walletCredit.setText(data.credit);
        walletBenefit.setText(data.earnings+"");
        walletBorrow.setText(data.borrows+"");
        walletIncome.setText(data.revenue+"");
        walletLimit.setText(data.lines+"");
        walletPayBack.setText(data.repayments+"");
    }
}
