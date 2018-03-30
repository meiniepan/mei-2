package com.wuyou.merchant.mvp.circle;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.gs.buluo.common.network.BaseResponse;
import com.gs.buluo.common.network.BaseSubscriber;
import com.gs.buluo.common.network.QueryMapBuilder;
import com.gs.buluo.common.utils.AppManager;
import com.gs.buluo.common.utils.ToastUtils;
import com.wuyou.merchant.CarefreeDaoSession;
import com.wuyou.merchant.Constant;
import com.wuyou.merchant.R;
import com.wuyou.merchant.bean.entity.ContractEntity;
import com.wuyou.merchant.bean.entity.PrepareSignEntity;
import com.wuyou.merchant.bean.entity.WalletInfoEntity;
import com.wuyou.merchant.network.CarefreeRetrofit;
import com.wuyou.merchant.network.apis.CircleApis;
import com.wuyou.merchant.network.apis.WalletApis;
import com.wuyou.merchant.util.CommonUtil;
import com.wuyou.merchant.view.activity.BaseActivity;
import com.wuyou.merchant.view.widget.CustomAlertDialog;
import com.wuyou.merchant.view.widget.LoanLimitPanel;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by DELL on 2018/3/29.
 */

public class SignContractActivity extends BaseActivity implements LoanLimitPanel.onLoadSelectListener {
    @BindView(R.id.sign_amount)
    TextView signAmount;
    @BindView(R.id.sign_self_money)
    TextView signSelfMoney;
    @BindView(R.id.sign_government_money)
    TextView signGovernmentMoney;
    @BindView(R.id.sign_credit_amount)
    EditText signCreditAmount;
    @BindView(R.id.sign_platform_pay)
    TextView signPlatformPay;
    @BindView(R.id.sign_limit_date)
    TextView signLimitDate;
    @BindView(R.id.sign_money_rate)
    TextView signMoneyRate;
    private List<PrepareSignEntity.RatesBean> ratesBeans;
    private ContractEntity contractEntity;
    private int number;
    private String stage;
    private float availableAmount;

    @Override
    protected int getContentLayout() {
        return R.layout.activity_sign_contract;
    }

    @Override
    protected void bindView(Bundle savedInstanceState) {
        contractEntity = getIntent().getParcelableExtra(Constant.CONTRACT);
        number = getIntent().getIntExtra(Constant.SIGN_NUMBER, 0);
        signAmount.setText(CommonUtil.formatPrice(contractEntity.price * number));
        getPrepareData(contractEntity.service.service_id);
        getWalletData();
    }

    @OnClick({R.id.sing_self_money_area, R.id.sign_limit_date_area})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.sing_self_money_area:
                Intent intent = new Intent(getCtx(), SelfFundActivity.class);
                startActivityForResult(intent, 201);
                break;
            case R.id.sign_limit_date_area:
                showLoanData();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 201 && resultCode == RESULT_OK) {
            signSelfMoney.setText(data.getStringExtra(Constant.FUND));
        }
    }

    public void showLoanData() {
        LoanLimitPanel panel = new LoanLimitPanel(this, this);
        panel.setData(ratesBeans);
        panel.show();
    }

    @Override
    public void onSelected(PrepareSignEntity.RatesBean entity) {
        stage = entity.stage;
        signLimitDate.setText(stage);
        signMoneyRate.setText(entity.rate);
    }

    public void signAndBuy(View view) {
        if (signLimitDate.length() == 0) {
            ToastUtils.ToastMessage(getCtx(), "请确认借款期限");
            return;
        }
        showLoadingDialog();
        CarefreeRetrofit.getInstance().createApi(CircleApis.class)
                .signContract(QueryMapBuilder.getIns()
                        .put("merchant_id", CarefreeDaoSession.getInstance().getUserInfo().getShop_id())
                        .put("contract_id", contractEntity.contract_id)
                        .put("number", number + "")
                        .put("amount", contractEntity.price * number + "")
                        .put("pay_amount", signSelfMoney.getText().toString().trim())
                        .put("subsidy_amount", signGovernmentMoney.getText().toString().trim())
                        .put("loan_amount", signPlatformPay.getText().toString().trim())
                        .put("stage", stage).buildPost())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<BaseResponse>() {
                    @Override
                    public void onSuccess(BaseResponse contractEntityBaseResponse) {
                        signSuccess();
                    }
                });
    }

    private void signSuccess() {
        new CustomAlertDialog.Builder(this).setMessage("您的申请已经提交！ 请保持手机畅通，等待工作人员联系。")
                .setPositiveButton(getString(R.string.yes), (dialog, which) -> {
                    finish();
                    AppManager.getAppManager().finishActivity(ContractDetailActivity.class);
                }).create().show();
    }

    public void getPrepareData(String serviceId) {
        CarefreeRetrofit.getInstance().createApi(CircleApis.class)
                .prepareSignContract(QueryMapBuilder.getIns().put("service_id", serviceId).buildGet())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<BaseResponse<PrepareSignEntity>>() {
                    @Override
                    public void onSuccess(BaseResponse<PrepareSignEntity> prepareSignEntityBaseResponse) {
                        signGovernmentMoney.setText(prepareSignEntityBaseResponse.data.government_subsidy + "");
                        ratesBeans = prepareSignEntityBaseResponse.data.rates;
                    }
                });
    }

    public void getWalletData() {
        CarefreeRetrofit.getInstance().createApi(WalletApis.class)
                .getCredit(QueryMapBuilder.getIns().put("shop_id", CarefreeDaoSession.getInstance().getUserInfo().getShop_id()).buildGet())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<BaseResponse<WalletInfoEntity>>() {
                    @Override
                    public void onSuccess(BaseResponse<WalletInfoEntity> walletInfoEntityBaseResponse) {
                        availableAmount = Float.parseFloat(walletInfoEntityBaseResponse.data.available_amount);
                        signCreditAmount.setText(CommonUtil.formatPrice(availableAmount));
                    }
                });
    }
}
