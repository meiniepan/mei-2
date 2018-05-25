package com.wuyou.merchant.mvp.store;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.gs.buluo.common.network.ApiException;
import com.gs.buluo.common.network.BaseResponse;
import com.gs.buluo.common.network.BaseSubscriber;
import com.gs.buluo.common.network.QueryMapBuilder;
import com.gs.buluo.common.utils.ToastUtils;
import com.wuyou.merchant.CarefreeDaoSession;
import com.wuyou.merchant.Constant;
import com.wuyou.merchant.R;
import com.wuyou.merchant.bean.DaoSession;
import com.wuyou.merchant.bean.UserInfo;
import com.wuyou.merchant.bean.entity.OfficialEntity;
import com.wuyou.merchant.bean.entity.OfficialEntityOut;
import com.wuyou.merchant.network.CarefreeRetrofit;
import com.wuyou.merchant.network.apis.UserApis;
import com.wuyou.merchant.util.glide.GlideUtils;
import com.wuyou.merchant.view.activity.BaseActivity;
import com.zhihu.matisse.Matisse;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Solang on 2018/5/15.
 */

public class CompanyInfoActivity extends BaseActivity {


    @BindView(R.id.tv_company_info_name)
    TextView tvCompanyInfoName;
    @BindView(R.id.tv_company_info_legal_person)
    TextView tvCompanyInfoLegalPerson;
    @BindView(R.id.tv_company_info_code)
    TextView tvCompanyInfoCode;
    @BindView(R.id.tv_company_info_address)
    TextView tvCompanyInfoAddress;
    @BindView(R.id.iv_company_affix)
    ImageView ivCompanyInfoAffix;
    private OfficialEntity companyInfo;

    @Override
    protected int getContentLayout() {
        return R.layout.activity_company_info;
    }

    @Override
    protected void bindView(Bundle savedInstanceState) {
        getData();
    }

    private void getData() {
        showLoadingDialog();
        CarefreeRetrofit.getInstance().createApi(UserApis.class)
                .getCompanyInfo(CarefreeDaoSession.getInstance().getUserInfo().getShop_id(), QueryMapBuilder.getIns().buildGet())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<BaseResponse<OfficialEntityOut>>() {
                    @Override
                    public void onSuccess(BaseResponse<OfficialEntityOut> response) {
                        if (TextUtils.isEmpty(response.data.official.name)) {
//                            startActivity(new Intent(getCtx(), CompanyInfoUpdateActivity.class));
                        } else {
                            initData(response.data.official);
                            companyInfo = response.data.official;
                        }
                    }
                });
    }

    private void initData(OfficialEntity response) {
        tvCompanyInfoName.setText(response.name);
        tvCompanyInfoLegalPerson.setText(response.corporation);
        tvCompanyInfoCode.setText(response.code);
        tvCompanyInfoAddress.setText(response.registered_address);
        GlideUtils.loadImage(getCtx(), response.license, ivCompanyInfoAffix);
    }

    @OnClick(R.id.iv_company_edit)
    public void onViewClicked() {
        startActivityForResult(new Intent(getCtx(), CompanyInfoUpdateActivity.class).putExtra(Constant.COMPANY_INFO, companyInfo), Constant.IntentRequestCode.REQUEST_UPDATE_COMPANY_INFO);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == Constant.IntentRequestCode.REQUEST_UPDATE_COMPANY_INFO) {
                getData();
            }
        }
    }
}
