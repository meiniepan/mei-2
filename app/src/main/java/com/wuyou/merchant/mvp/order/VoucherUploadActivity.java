package com.wuyou.merchant.mvp.order;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.gs.buluo.common.utils.ToastUtils;
import com.wuyou.merchant.R;
import com.wuyou.merchant.view.activity.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Solang on 2018/5/21.
 */

public class VoucherUploadActivity extends BaseActivity {
    @BindView(R.id.tv_voucher_upload_note)
    EditText tvVoucherUploadNote;

    @Override
    protected int getContentLayout() {
        return R.layout.activity_order_detail_voucher_upload;
    }

    @Override
    protected void bindView(Bundle savedInstanceState) {

    }


    @OnClick({R.id.v_company_update_add, R.id.btn_voucher_upload_commit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.v_company_update_add:
                ToastUtils.ToastMessage(getCtx(),R.string.not_open);
                break;
            case R.id.btn_voucher_upload_commit:
                ToastUtils.ToastMessage(getCtx(),R.string.not_open);
                break;
        }
    }
}
