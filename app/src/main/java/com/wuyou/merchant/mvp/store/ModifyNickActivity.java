package com.wuyou.merchant.mvp.store;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.gs.buluo.common.network.ApiException;
import com.gs.buluo.common.network.BaseResponse;
import com.gs.buluo.common.network.BaseSubscriber;
import com.gs.buluo.common.network.QueryMapBuilder;
import com.gs.buluo.common.utils.ToastUtils;
import com.wuyou.merchant.CarefreeDaoSession;
import com.wuyou.merchant.R;
import com.wuyou.merchant.network.CarefreeRetrofit;
import com.wuyou.merchant.network.apis.UserApis;
import com.wuyou.merchant.view.activity.BaseActivity;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by DELL on 2018/3/14.
 */

public class ModifyNickActivity extends BaseActivity {
    @BindView(R.id.et_input_nick)
    EditText editText;

    @Override
    protected void bindView(Bundle savedInstanceState) {
    }

    @Override
    protected int getContentLayout() {
        return R.layout.activity_modify_nick;
    }


    @OnClick({R.id.btn_modify_nick})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_modify_nick:
                String input = editText.getText().toString();
                if (editText.length() < 4 || editText.length() > 12) {
                    ToastUtils.ToastMessage(getCtx(), getString(R.string.nickname_not_right));
                    return;
                }
                Pattern p = Pattern.compile("^[a-zA-Z0-9_\\-\\u4e00-\\u9fa5]+$");
                Matcher m = p.matcher(input);
                if (!m.matches()) {
                    ToastUtils.ToastMessage(getCtx(), getString(R.string.nickname_not_right));
                    return;
                }
                updateInfo("nickname", input);
                break;
        }
    }

    private void updateInfo(String key, String value) {
        showLoadingDialog();
        CarefreeRetrofit.getInstance().createApi(UserApis.class)
                .updateUserInfo(CarefreeDaoSession.getInstance().getUserInfo().getShop_id(), QueryMapBuilder.getIns()
                        .put("field", key)
                        .put("value", value)
                        .buildPost())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<BaseResponse>() {
                    @Override
                    public void onSuccess(BaseResponse baseResponse) {
                        setResult(RESULT_OK, new Intent().putExtra("info", editText.getText().toString()));
                        finish();
                    }

                    @Override
                    protected void onFail(ApiException e) {
                        ToastUtils.ToastMessage(getCtx(), R.string.connect_fail);
                    }
                });

    }
}
