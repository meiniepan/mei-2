package com.wuyou.merchant.mvp.store;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.gs.buluo.common.network.BaseSubscriber;
import com.gs.buluo.common.utils.ToastUtils;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.wuyou.merchant.CarefreeApplication;
import com.wuyou.merchant.CarefreeDaoSession;
import com.wuyou.merchant.Constant;
import com.wuyou.merchant.R;
import com.wuyou.merchant.mvp.account.CreateOrImportAccountActivity;
import com.wuyou.merchant.mvp.account.ScoreAccountActivity;
import com.wuyou.merchant.mvp.vote.MyVoteListActivity;
import com.wuyou.merchant.mvp.vote.VoteCreateActivity;
import com.wuyou.merchant.mvp.vote.VotePresenter;
import com.wuyou.merchant.util.CommonUtil;
import com.wuyou.merchant.util.NetTool;
import com.wuyou.merchant.util.RxUtil;
import com.wuyou.merchant.util.glide.GlideUtils;
import com.wuyou.merchant.view.activity.HelpRobotActivity;
import com.wuyou.merchant.view.fragment.BaseFragment;

import java.io.File;
import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.ipfs.api.IPFS;
import io.ipfs.api.MerkleNode;
import io.ipfs.api.NamedStreamable;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;


/**
 * Created by Administrator on 2018\1\29 0029.
 */

public class StoreFragment extends BaseFragment {
    @BindView(R.id.iv_avatar)
    ImageView ivAvatar;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_phone)
    TextView tvPhone;
    private String path;

    @Override
    protected int getContentLayout() {
        return R.layout.store_home;
    }

    @Override
    protected void bindView(Bundle savedInstanceState) {
    }

    @Override
    public void onResume() {
        super.onResume();
        initInfo();
    }

    private void initInfo() {
        GlideUtils.loadImage(getContext(), CarefreeDaoSession.getInstance().getUserInfo().getLogo(), ivAvatar, true);
        tvName.setText(CarefreeDaoSession.getInstance().getUserInfo().getShop_name());
        tvPhone.setText(CommonUtil.getPhoneWithStar(CarefreeDaoSession.getInstance().getUserInfo().getTel()));
    }


    @Override
    public void showError(String message, int res) {

    }


    @OnClick({R.id.ll_work_info, R.id.ll_worker_list, R.id.ll_service, R.id.ll_intro, R.id.ll_mark, R.id.ll_setting,
            R.id.ll_vote, R.id.ll_information, R.id.ll_account, R.id.ll_vote_record})
    public void onViewClicked(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.ll_work_info:
                intent.setClass(getContext(), StoreInfoEditActivity.class);
                startActivity(intent);
                break;
            case R.id.ll_worker_list:
                intent.setClass(getContext(), WorkerListActivity.class);
                startActivity(intent);
                break;
            case R.id.ll_service:
                if (!NetTool.isConnected(mCtx)) {
                    ToastUtils.ToastMessage(mCtx, R.string.no_network);
                    return;
                }
                intent.setClass(getContext(), HelpRobotActivity.class);
                startActivity(intent);
                break;
            case R.id.ll_information:
                if (TextUtils.isEmpty(CarefreeDaoSession.getInstance().getUserInfo().getOfficial().name)) {
                    intent.setClass(getContext(), CompanyInfoUpdateActivity.class);
                } else {
                    intent.setClass(getContext(), CompanyInfoActivity.class);
                }

                startActivity(intent);
                break;
            case R.id.ll_intro:
                break;
            case R.id.ll_mark:
                break;
            case R.id.ll_setting:
//                intent.setClass(getContext(), SettingActivity.class);
//                startActivity(intent);
                chosePhoto();
                break;
            case R.id.ll_account:
                if (CarefreeDaoSession.getInstance().getMainAccount() == null) {
                    intent.setClass(mCtx, CreateOrImportAccountActivity.class);
                    startActivity(intent);
                } else {
                    intent.setClass(mCtx, ScoreAccountActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.ll_vote:
                intent.setClass(mCtx, VoteCreateActivity.class);
                startActivity(intent);
                break;
            case R.id.ll_vote_record:
                intent.setClass(mCtx, MyVoteListActivity.class);
                startActivity(intent);
                break;
        }

    }

    private void voteTest() {
        VotePresenter presenter = new VotePresenter();
        presenter.createVote(path);
//        presenter.doVote();
//        chosePhoto();
    }


    private void chosePhoto() {
        PictureSelector.create(this)
                .openGallery(PictureMimeType.ofImage())//全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
                .maxSelectNum(1)// 最大图片选择数量 int
                .imageSpanCount(4)// 每行显示个数 int
                .selectionMode(PictureConfig.SINGLE)// 多选 or 单选 PictureConfig.MULTIPLE or PictureConfig.SINGLE
                .isCamera(true)// 是否显示拍照按钮 true or false
                .isZoomAnim(true)// 图片列表点击 缩放效果 默认true
                .sizeMultiplier(0.5f)// glide 加载图片大小 0~1之间 如设置 .glideOverride()无效
                .setOutputCameraPath("/CustomPath")// 自定义拍照保存路径,可不填
                .enableCrop(true)// 是否裁剪 true or false
                .compress(true)// 是否压缩 true or false
                .withAspectRatio(1, 1)// int 裁剪比例 如16:9 3:2 3:4 1:1 可自定义
                .compressSavePath(CarefreeApplication.getInstance().getApplicationContext().getFilesDir().getAbsolutePath())//压缩图片保存地址
                .freeStyleCropEnabled(false)// 裁剪框是否可拖拽 true or false
                .openClickSound(false)// 是否开启点击声音 true or false
                .cropCompressQuality(50)// 裁剪压缩质量 默认90 int
                .minimumCompressSize(50)// 小于100kb的图片不压缩
                .scaleEnabled(true)// 裁剪是否可放大缩小图片 true or false
                .isDragFrame(false)// 是否可拖动裁剪框(固定)
                .forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == PictureConfig.CHOOSE_REQUEST) {
                List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);
                String path = "";
                if (selectList != null && selectList.size() > 0) {
                    LocalMedia localMedia = selectList.get(0);
                    if (localMedia.isCompressed()) {
                        path = localMedia.getCompressPath();
                    } else if (localMedia.isCut()) {
                        path = localMedia.getCutPath();
                    } else {
                        path = localMedia.getPath();
                    }
                }
                try {
                    uploadFileToIpfs(new File(path));
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }
    }

    public void uploadFileToIpfs(File des) throws IOException {
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {
                IPFS ipfs = new IPFS(Constant.IPFS_URL, 9877);
                ipfs.refs.local();

                NamedStreamable.FileWrapper file = new NamedStreamable.FileWrapper(des);
                MerkleNode addResult = ipfs.add(file).get(0);

                e.onNext(addResult.hash.toBase58());
            }
        }).compose(RxUtil.switchSchedulers())
                .subscribe(new BaseSubscriber<String>() {

                    @Override
                    public void onSuccess(String s) {
                        Log.e("Carefree", "onSuccess: " + s);//QmWuW8X5KVTKjg7LHGVqLCJGS3VHquxNc9QAAqBaPxST6x
                        path = s;
                        //QmVpuR29HsjwwBpTF8Wsnr7ChzUEUXowC95ZCf4iZbBmgu
                    }
                });
    }
}
