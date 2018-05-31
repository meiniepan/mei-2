package com.wuyou.merchant.mvp.order;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.gs.buluo.common.network.BaseResponse;
import com.gs.buluo.common.network.BaseSubscriber;
import com.gs.buluo.common.network.QueryMapBuilder;
import com.gs.buluo.common.utils.ToastUtils;
import com.gs.buluo.common.widget.LoadingDialog;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.entity.LocalMedia;
import com.wuyou.merchant.CarefreeDaoSession;
import com.wuyou.merchant.Constant;
import com.wuyou.merchant.R;
import com.wuyou.merchant.network.CarefreeRetrofit;
import com.wuyou.merchant.network.apis.OrderApis;
import com.wuyou.merchant.util.CommonUtil;
import com.wuyou.merchant.util.glide.GlideUtils;
import com.wuyou.merchant.view.activity.BaseActivity;
import com.wuyou.merchant.view.activity.MainActivity;

import java.io.File;
import java.util.List;

import butterknife.BindView;
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
                CommonUtil.choosePhoto(this, PictureConfig.CHOOSE_REQUEST);
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
                                startActivity(new Intent(getCtx(), MainActivity.class).putExtra(Constant.MAIN_ACTIVITY_FROM_WHERE,Constant.MAIN_ACTIVITY_FROM_VOUCHER));
                                finish();
                            }


                        });
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == PictureConfig.CHOOSE_REQUEST) {
                List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);

                if (selectList != null && selectList.size() > 0) {
                    LocalMedia localMedia = selectList.get(0);
                    if (localMedia.isCompressed()) {
                        imagePath = localMedia.getCompressPath();
                    } else if (localMedia.isCut()) {
                        imagePath = localMedia.getCutPath();
                    } else {
                        imagePath = localMedia.getPath();
                    }
                }
                CommonUtil.compressAndSaveImgToLocal(imagePath,1);
                GlideUtils.loadImage(getCtx(), imagePath, ivUpdate);
            }
        }
    }
}
