package com.wuyou.merchant.mvp.wallet;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.TextView;

import com.gs.buluo.common.network.BaseResponse;
import com.gs.buluo.common.network.BaseSubscriber;
import com.gs.buluo.common.network.QueryMapBuilder;
import com.gs.buluo.common.utils.TribeDateUtils;
import com.gs.buluo.common.widget.CustomAlertDialog;
import com.wuyou.merchant.CarefreeDaoSession;
import com.wuyou.merchant.Constant;
import com.wuyou.merchant.R;
import com.wuyou.merchant.adapter.FundRateListRvAdapter;
import com.wuyou.merchant.bean.entity.FundEntity;
import com.wuyou.merchant.network.CarefreeRetrofit;
import com.wuyou.merchant.network.apis.WalletApis;
import com.wuyou.merchant.view.activity.BaseActivity;
import com.wuyou.merchant.view.activity.MainActivity;

import java.util.Date;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by solang on 2018/2/9.
 */

public class FundIntroduceActivity extends BaseActivity {
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.description)
    TextView description;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;
    @BindView(R.id.btn_apply)
    Button btnApply;
    FundRateListRvAdapter adapter;
    String id;
    private int statu;
    String[] status = {"申请基金","审核中","审核通过","审核失败"};
    @Override
    protected int getContentLayout() {
        return R.layout.activity_fund_introduce;
    }

    @Override
    protected void bindView(Bundle savedInstanceState) {
        id = getIntent().getStringExtra(Constant.FUND_ID);
        statu = getIntent().getIntExtra(Constant.FUND_STATUS,-1);
        btnApply.setEnabled(false);
        if (statu == 0){
            btnApply.setText(status[statu]);
            btnApply.setEnabled(true);
        }else if(statu != -1){
            btnApply.setText(status[statu]);
            btnApply.setEnabled(false);
        }
        if (!TextUtils.isEmpty(id))
            getData(id);
    }

    private void getData(String id) {
        showLoadingDialog();
        CarefreeRetrofit.getInstance().createApi(WalletApis.class)
                .getFundDetail(id,
                        QueryMapBuilder.getIns()
                                .buildGet())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<BaseResponse<FundEntity>>() {
                    @Override
                    public void onSuccess(BaseResponse<FundEntity> response) {
                        initUI(response.data);
                    }
                });
    }

    private void initUI(FundEntity data) {
        adapter = new FundRateListRvAdapter(R.layout.item_fund_rate, data.rates);
        recyclerView.setLayoutManager(new LinearLayoutManager(getCtx()));
        recyclerView.setAdapter(adapter);
        String s = TribeDateUtils.SDF5.format(new Date(Long.parseLong(data.start_at) * 1000));
        name.setText(data.fund_name);
        tvTime.setText(s);
        description.setText(data.desc);
    }

    @OnClick(R.id.btn_apply)
    public void onViewClicked() {
        showLoadingDialog();
        CarefreeRetrofit.getInstance().createApi(WalletApis.class)
                .applyLoan(QueryMapBuilder.getIns()
                        .put("shop_id", CarefreeDaoSession.getInstance().getUserInfo().getShop_id())
                        .put("fund_id", id)
                        .buildPost())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<BaseResponse>() {
                    @Override
                    public void onSuccess(BaseResponse response) {
                        showDialog();
                    }
                });
    }

    private void showDialog() {
        CustomAlertDialog.Builder builder = new CustomAlertDialog.Builder(getCtx());
        builder.setCancelable(false);
        builder.setMessage("您的申请已提交！\n请保持您的手机通畅，等待工作人员联系。");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(getCtx(), MainActivity.class);
                intent.putExtra(Constant.MAIN_ACTIVITY_FROM_WHERE,Constant.MAIN_ACTIVITY_FROM_APPLY_FUND);
                startActivity(intent);
            }
        });
        builder.create().show();
    }
}
