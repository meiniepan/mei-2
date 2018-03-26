package com.wuyou.merchant.mvp.wallet;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.gs.buluo.common.network.BaseResponse;
import com.gs.buluo.common.network.BaseSubscriber;
import com.gs.buluo.common.network.QueryMapBuilder;
import com.gs.buluo.common.widget.CustomAlertDialog;
import com.wuyou.merchant.CarefreeApplication;
import com.wuyou.merchant.Constant;
import com.wuyou.merchant.R;
import com.wuyou.merchant.bean.entity.WalletInfoEntity;
import com.wuyou.merchant.network.CarefreeRetrofit;
import com.wuyou.merchant.network.apis.WalletApis;
import com.wuyou.merchant.view.fragment.BaseFragment;

import butterknife.BindView;
import butterknife.OnClick;
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
    @BindView(R.id.viewpager)
    ViewPager vpPager;

    private WalletInfoEntity entity = new WalletInfoEntity();
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
        initWalletInfo();

    }

    private void initViewPager() {
        FragmentManager manager = getChildFragmentManager();
        vpPager.setAdapter(new FragmentPagerAdapter(manager) {
            @Override
            public Fragment getItem(int position) {
                //创建Fragment并返回
                Fragment fragment = null;
                if (position == 0) {
                    fragment = new WalletCreditFragment();
                    Bundle bundle = new Bundle();
                    bundle.putParcelable(Constant.WALLET_INFO_ENTITY, entity);
                    fragment.setArguments(bundle);
                } else if (position == 1)
                    fragment = new WalletIncomeFragment();
                Bundle bundle = new Bundle();
                bundle.putParcelable(Constant.WALLET_INFO_ENTITY, entity);
                fragment.setArguments(bundle);
                return fragment;
            }

            @Override
            public int getCount() {
                return 2;
            }
        });
    }

    private void initWalletInfo() {
        CarefreeRetrofit.getInstance().createApi(WalletApis.class)
                .getCredit(QueryMapBuilder.getIns()
                        .put("shop_id", CarefreeApplication.getInstance().getUserInfo().getUid())
                        .buildGet())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<BaseResponse<WalletInfoEntity>>() {
                    @Override
                    public void onSuccess(BaseResponse<WalletInfoEntity> response) {
                        entity = response.data;
                        sCredit = response.data.score;
                        walletCredit.setText(response.data.score);
                        initViewPager();

                    }

                });
    }


    @OnClick({R.id.ll_credit, R.id.ll_income})
    public void onViewClicked(View view) {
        switch (view.getId()) {

            case R.id.ll_income:
                CustomAlertDialog.Builder builder = new CustomAlertDialog.Builder(getContext());
                builder.setMessage("由商家服务和经营，以及履约能力等各方面综合分析以后，决定的借款额度，请保持良好的守约习惯，提高信用分。");
                builder.create().show();
                break;
            case R.id.ll_credit:
                Intent intent1 = new Intent(getContext(), CreditDetailActivity.class);
                intent1.putExtra(Constant.CREDIT_SCORE, sCredit);
                startActivity(intent1);
                break;
        }
    }

    @Override
    public void showError(String message, int res) {

    }
}
