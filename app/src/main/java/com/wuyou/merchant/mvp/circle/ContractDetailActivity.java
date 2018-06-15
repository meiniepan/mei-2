package com.wuyou.merchant.mvp.circle;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;
import com.gs.buluo.common.network.ApiException;
import com.gs.buluo.common.network.BaseResponse;
import com.gs.buluo.common.network.BaseSubscriber;
import com.gs.buluo.common.network.QueryMapBuilder;
import com.gs.buluo.common.utils.ToastUtils;
import com.gs.buluo.common.utils.TribeDateUtils;
import com.wuyou.merchant.CarefreeApplication;
import com.wuyou.merchant.Constant;
import com.wuyou.merchant.R;
import com.wuyou.merchant.adapter.CircleHeadAdapter;
import com.wuyou.merchant.adapter.ContractAddressAdapter;
import com.wuyou.merchant.bean.HomeVideoBean;
import com.wuyou.merchant.bean.entity.ContractEntity;
import com.wuyou.merchant.bean.entity.ContractInfoEntity;
import com.wuyou.merchant.bean.entity.ContractMerchantEntity;
import com.wuyou.merchant.bean.entity.ResponseListEntity;
import com.wuyou.merchant.network.CarefreeRetrofit;
import com.wuyou.merchant.network.apis.CircleApis;
import com.wuyou.merchant.util.CommonUtil;
import com.wuyou.merchant.view.activity.BaseActivity;
import com.wuyou.merchant.view.widget.ContractCountPanel;
import com.wuyou.merchant.view.widget.panel.BigImagePanel;
import com.wuyou.merchant.view.widget.panel.ShareBottomBoard;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by solang on 2018/3/20.
 */

public class ContractDetailActivity extends BaseActivity {

    String id;
    @BindView(R.id.tv_contract_name)
    TextView tvContractName;
    @BindView(R.id.tv_contract_code)
    TextView tvContractCode;
    @BindView(R.id.tv_contract_category)
    TextView tvContractCategory;
    @BindView(R.id.tv_contract_create_time)
    TextView tvContractCreateTime;
    @BindView(R.id.tv_contract_end_time)
    TextView tvContractEndTime;
    @BindView(R.id.tv_company_name)
    TextView tvCompanyName;
    @BindView(R.id.tv_company_address)
    TextView tvCompanyAddress;
    @BindView(R.id.tv_company_phone)
    TextView tvCompanyPhone;
    @BindView(R.id.tv_server_sum)
    TextView tvServerSum;
    @BindView(R.id.tv_serve_name)
    TextView tvServerName;
    @BindView(R.id.tv_pay_method)
    TextView tvPayMethod;
    @BindView(R.id.tv_joined_time)
    TextView tvJoinedTime;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.tv_punish)
    TextView tvPunish;
    @BindView(R.id.ll_created)
    View layoutCreated;
    @BindView(R.id.ll_joined)
    View layoutJoined;
    @BindView(R.id.btn_contact)
    Button button;
    @BindView(R.id.rv_address)
    RecyclerView addressRecyclerView;
    @BindView(R.id.tv_merchant_num)
    TextView tvMerchantNum;
    @BindView(R.id.contract_detail_merchant_list)
    RecyclerView merchantRecyclerView;
    ContractAddressAdapter adapter;
    private List<String> addressData;
    private int fromId;
    private String ownerId;
    private ContractEntity resData;

    @Override
    protected int getContentLayout() {
        return R.layout.activity_contract_detail;
    }

    @Override
    protected void bindView(Bundle savedInstanceState) {
        setTitleText(R.string.contract_detail);
        baseStatusLayout.setErrorAction(
                v -> initData(id)
        );
        id = getIntent().getStringExtra(Constant.CONTRACT_ID);
        fromId = getIntent().getIntExtra(Constant.CONTRACT_FROM, 1);
        initData(id);
    }

    private void initData(String id) {
        baseStatusLayout.showProgressView();
        CarefreeRetrofit.getInstance().createApi(CircleApis.class)
                .getContractDetail(id, QueryMapBuilder.getIns().put("shop_id", CarefreeApplication.getInstance().getUserInfo().getUid()).buildGet())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<BaseResponse<ContractEntity>>() {
                    @Override
                    public void onSuccess(BaseResponse<ContractEntity> response) {
                        baseStatusLayout.showContentView();
                        ownerId = response.data.shop.shop_id;
                        resData = response.data;
                        initUI(response.data);
                    }

                    @Override
                    protected void onFail(ApiException e) {
                        showError(e.getDisplayMessage(),e.getCode());
                    }
                });
        CarefreeRetrofit.getInstance().createApi(CircleApis.class)
                .getContractSigner(QueryMapBuilder.getIns().put("contract_id", id).put("start_id", "0").put("flag", "1").put("size", "10").buildGet())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<BaseResponse<ResponseListEntity<ContractMerchantEntity>>>() {
                    @Override
                    public void onSuccess(BaseResponse<ResponseListEntity<ContractMerchantEntity>> contractEntityBaseResponse) {
                        initContractSigner(contractEntityBaseResponse.data.list);
                    }
                });
    }

    private void initContractSigner(List<ContractMerchantEntity> list) {
        tvMerchantNum.setText(list.size() + "");
        if (list.size() == 0)
            findViewById(R.id.contract_detail_merchant_list_area).setVisibility(View.GONE);
        merchantRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        merchantRecyclerView.setAdapter(new CircleHeadAdapter(R.layout.item_circle_head, list));
    }

    private void initUI(ContractEntity data) {
        if (System.currentTimeMillis() > data.end_at * 1000) {
            button.setEnabled(false);
        }
        String b_time = TribeDateUtils.dateFormat(new Date(Long.parseLong(data.created_at) * 1000));
        String e_time = TribeDateUtils.dateFormat(new Date(data.end_at * 1000));
        String j_time = TribeDateUtils.dateFormat7(new Date(data.end_at * 1000));

        Gson gson = new Gson();
        ContractInfoEntity entity = gson.fromJson(data.information, ContractInfoEntity.class);
        addressData = entity.communities;
        adapter = new ContractAddressAdapter(ContractDetailActivity.this, R.layout.item_address, addressData);
        addressRecyclerView.setAdapter(adapter);
        addressRecyclerView.setLayoutManager(new LinearLayoutManager(getCtx()));

        tvContractName.setText(data.contract_name);
        tvContractCode.setText(data.contract_number);
        tvContractCategory.setText(data.service.category_name);
        tvContractCreateTime.setText(b_time);
        tvContractEndTime.setText(e_time);

        tvCompanyName.setText(data.shop.shop_name);
        tvCompanyAddress.setText(data.shop.contact_address);
        tvCompanyPhone.setText(data.shop.mobile);

        tvServerSum.setText(CommonUtil.formatPrice(data.price));
        tvServerName.setText(data.service.service_name);
        tvPayMethod.setText(data.pay_type == 1 ? "一次性支付" : "分期支付");
        String s1 = "为保证用户的良好体验，必须按照用户约定的时间进行上门服务，服务上门时间不可晚于用户要求时间";
        String s2 = "我们的宗旨是用户至上，每一单服务，要做到让用户满意，如果因为服务质量，服务人员态度等影响客户满意度的问题遭到客户投诉，需扣除分成金额";
        SpannableString ss1 = new SpannableString(s1 + entity.time_limit + "分钟");
        SpannableString ss2 = new SpannableString(s2 + entity.penalized_proportion + "%");
        ss1.setSpan(new ForegroundColorSpan(Color.BLUE), s1.length(), s1.length() + entity.time_limit.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss2.setSpan(new ForegroundColorSpan(Color.BLUE), s2.length(), s2.length() + entity.penalized_proportion.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tvTime.setText(ss1);
        tvPunish.setText(ss2);
        if (fromId == 1) {
            layoutCreated.setVisibility(View.VISIBLE);
            button.setText("邀请朋友加入");
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    HomeVideoBean homeVideoBean = new HomeVideoBean();
                    homeVideoBean.title = "推荐给你一个合约";
                    ShareBottomBoard bottomBoard = new ShareBottomBoard(getCtx());
                    bottomBoard.setData(homeVideoBean);
                    bottomBoard.show();
                }
            });
        } else if (fromId == 2) {
            layoutJoined.setVisibility(View.VISIBLE);
            tvJoinedTime.setText(j_time);
            button.setText("再次购买合约");
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showPanel(data);
                }
            });
        } else if (fromId == 3) {
            button.setText("签署并购买");
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showPanel(data);
                }
            });
        }
    }

    private void showPanel(ContractEntity data) {
        ContractCountPanel panel = new ContractCountPanel(this, data);
        panel.show();
    }


    @OnClick({R.id.btn_contact, R.id.ll_authentication, R.id.ll_merchants_detail})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_contact:
                ToastUtils.ToastMessage(getCtx(), R.string.not_open);
                break;
            case R.id.ll_authentication:
//                ToastUtils.ToastMessage(getCtx(), R.string.not_open);
                if (resData ==null)return;
                List data = new ArrayList();
                data.add(resData.shop.license);
                if (!resData.shop.other_image.isEmpty())
                data.add(resData.shop.other_image);
                BigImagePanel panel = new BigImagePanel(getCtx(), data);
                panel.setPos(0);
                panel.show();
                break;
            case R.id.ll_merchants_detail:
                ToastUtils.ToastMessage(getCtx(), R.string.not_open);
                break;
        }
    }
}
