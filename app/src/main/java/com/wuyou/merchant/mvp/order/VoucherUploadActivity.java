package com.wuyou.merchant.mvp.order;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.gs.buluo.common.network.ApiException;
import com.gs.buluo.common.network.BaseResponse;
import com.gs.buluo.common.network.BaseSubscriber;
import com.gs.buluo.common.network.QueryMapBuilder;
import com.gs.buluo.common.utils.ToastUtils;
import com.gs.buluo.common.widget.LoadingDialog;
import com.wuyou.merchant.CarefreeDaoSession;
import com.wuyou.merchant.Constant;
import com.wuyou.merchant.R;
import com.wuyou.merchant.bean.entity.OrderBeanDetail;
import com.wuyou.merchant.network.CarefreeRetrofit;
import com.wuyou.merchant.network.apis.OrderApis;
import com.wuyou.merchant.util.CommonUtil;
import com.wuyou.merchant.util.glide.Glide4Engine;
import com.wuyou.merchant.view.activity.BaseActivity;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.internal.entity.CaptureStrategy;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Created by Solang on 2018/5/21.
 */

public class VoucherUploadActivity extends BaseActivity {
    @BindView(R.id.tv_voucher_upload_note)
    EditText tvVoucherUploadNote;
    @BindView(R.id.iv_company_update_add)
    ImageView ivUpdate;
    private String imagePath;
    private String orderId;

    @Override
    protected int getContentLayout() {
        return R.layout.activity_order_detail_voucher_upload;
    }

    @Override
    protected void bindView(Bundle savedInstanceState) {
        orderId = getIntent().getStringExtra(Constant.ORDER_ID);
    }


    @OnClick({R.id.iv_company_update_add, R.id.btn_voucher_upload_commit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_company_update_add:
                Matisse.from(this)
                        .choose(MimeType.ofImage())
                        .capture(true)
                        .captureStrategy(new CaptureStrategy(true, "com.wuyou.merchant.FileProvider"))
                        .showSingleMediaType(true)
                        .theme(R.style.Matisse_Dracula)
                        .countable(false)
                        .maxSelectable(1)
                        .imageEngine(new Glide4Engine())
                        .forResult(Constant.IntentRequestCode.REQUEST_CODE_CHOOSE_IMAGE);
                break;
            case R.id.btn_voucher_upload_commit:
                if (TextUtils.isEmpty(imagePath)){
                    ToastUtils.ToastMessage(getCtx(),"请添加图片");
                    return;
                }
                File file = new File(Constant.AUTH_IMG_PATH_1);
                RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                MultipartBody.Part body = MultipartBody.Part.createFormData("voucher", file.getName(), requestFile);
                LoadingDialog.getInstance().show(getCtx(), "上传中...", false);
                CarefreeRetrofit.getInstance().createApi(OrderApis.class)
                        .updateVoucher(body, QueryMapBuilder.getIns().put("shop_id", CarefreeDaoSession.getInstance().getUserInfo().getShop_id())
                                .put("order_id", orderId)
                                .put("remark", tvVoucherUploadNote.getText().toString())
                                .buildGet())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new BaseSubscriber<BaseResponse>() {
                            @Override
                            public void onSuccess(BaseResponse orderResponse) {
                                ToastUtils.ToastMessage(getCtx(),"上传完成！");
                                finish();
                            }


                        });
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constant.IntentRequestCode.REQUEST_CODE_CHOOSE_IMAGE && resultCode == RESULT_OK) {
            imagePath = Matisse.obtainPathResult(data).get(0);
            CommonUtil.compressAndSaveImgToLocal(imagePath, 1);
            Glide.with(getCtx()).load(Matisse.obtainResult(data).get(0).toString()).into(ivUpdate);
        }
    }
}
