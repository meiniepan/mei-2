package com.wuyou.merchant.mvp.vote;

import android.util.Log;

import com.google.gson.JsonObject;
import com.gs.buluo.common.network.BaseSubscriber;
import com.gs.buluo.common.network.ErrorBody;
import com.wuyou.merchant.CarefreeDaoSession;
import com.wuyou.merchant.Constant;
import com.wuyou.merchant.data.EoscDataManager;
import com.wuyou.merchant.data.api.OptionContent;
import com.wuyou.merchant.data.api.VoteOption;
import com.wuyou.merchant.data.api.VoteQuestion;
import com.wuyou.merchant.util.RxUtil;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import io.ipfs.api.IPFS;
import io.ipfs.api.MerkleNode;
import io.ipfs.api.NamedStreamable;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;

/**
 * Created by DELL on 2018/10/11.
 */

public class VotePresenter {

    public void getVoteLis() {
        EoscDataManager.getIns().getTable("ayiuivl52fnq", "votevotevote", "infos")
                .compose(RxUtil.switchSchedulers()).subscribe(new BaseSubscriber<String>() {
            @Override
            public void onSuccess(String s) {
                Log.e("Carefree", "onSuccess: " + s);
            }
        });

        EoscDataManager.getIns().getTable("votevotevote", "votevotevote", "votelist")
                .compose(RxUtil.switchSchedulers()).subscribe(new BaseSubscriber<String>() {
            @Override
            public void onSuccess(String s) {
                Log.e("Carefree", "onSuccess: " + s);
            }
        });
    }

    public void doVote() {
        VoteOption voteOption = new VoteOption(new int[]{1});
        ArrayList<VoteOption> list = new ArrayList<>();
        list.add(voteOption);
        EoscDataManager.getIns().doVote("0", list)
                .compose(RxUtil.switchSchedulers())
                .subscribe(new BaseSubscriber<JsonObject>() {
                    @Override
                    public void onSuccess(JsonObject jsonObject) {
                        Log.e("Carefree", "onSuccess: " + jsonObject);
                    }


                    @Override
                    protected void onNodeFail(int code, ErrorBody.DetailErrorBean message) {
                        if (message.message.contains("You have voted")) {
//                            ToastUtils.ToastMessage(getContext(), "您已经投过票了");
                        } else {
//                            ToastUtils.ToastMessage(getContext(), message.message);
                        }
                    }
                });
    }

    public void uploadFileToIpfs(File des) throws IOException {
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {
                IPFS ipfs = new IPFS(Constant.IPFS_URL);
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


                    }
                });
    }


    public void createVote(String logo) {
        ArrayList<OptionContent> optionContents = new ArrayList<>();
        OptionContent content = new OptionContent("yes");
        OptionContent content1 = new OptionContent("what");

        ArrayList<VoteQuestion> contents = new ArrayList<>();
        VoteQuestion question = new VoteQuestion();
        question.question = "hello?";
        optionContents.add(content);
        optionContents.add(content1);
        question.option = optionContents;

        VoteQuestion question1 = new VoteQuestion();
        question1.question = "你是 zz 么";
        question1.option = optionContents;


        VoteQuestion question2 = new VoteQuestion();
        question2.question = "hello merchant?";
        question2.option = optionContents;

        contents.add(question1);
//        contents.add(question1);
//        contents.add(question2);
//        contents.add(question);

        EoscDataManager.getIns().createVote("merchant 问卷test", "QmWuW8X5KVTKjg7LHGVqLCJGS3VHquxNc9QAAqBaPxST6x", "desc", "org", contents)
                .compose(RxUtil.switchSchedulers())
                .subscribe(new BaseSubscriber<JsonObject>() {
                    @Override
                    public void onSuccess(JsonObject jsonObject) {
                        Log.e("Carefree", "onSuccess: " + jsonObject);
                    }

                    @Override
                    protected void onNodeFail(int code, ErrorBody.DetailErrorBean message) {
                        if (message.message.contains("same title vote existed")) {
//                            ToastUtils.ToastMessage(getContext(), "投票标题不能重复");
                        } else {
//                            ToastUtils.ToastMessage(getContext(), message.message);
                        }
                    }
                });
    }

//    private void chosePhoto() {
//        PictureSelector.create()
//                .openGallery(PictureMimeType.ofImage())//全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
//                .maxSelectNum(1)// 最大图片选择数量 int
//                .imageSpanCount(4)// 每行显示个数 int
//                .selectionMode(PictureConfig.SINGLE)// 多选 or 单选 PictureConfig.MULTIPLE or PictureConfig.SINGLE
//                .isCamera(true)// 是否显示拍照按钮 true or false
//                .isZoomAnim(true)// 图片列表点击 缩放效果 默认true
//                .sizeMultiplier(0.5f)// glide 加载图片大小 0~1之间 如设置 .glideOverride()无效
//                .setOutputCameraPath("/CustomPath")// 自定义拍照保存路径,可不填
//                .enableCrop(true)// 是否裁剪 true or false
//                .compress(true)// 是否压缩 true or false
//                .withAspectRatio(1, 1)// int 裁剪比例 如16:9 3:2 3:4 1:1 可自定义
//                .compressSavePath(CarefreeApplication.getInstance().getApplicationContext().getFilesDir().getAbsolutePath())//压缩图片保存地址
//                .freeStyleCropEnabled(false)// 裁剪框是否可拖拽 true or false
//                .openClickSound(false)// 是否开启点击声音 true or false
//                .cropCompressQuality(50)// 裁剪压缩质量 默认90 int
//                .minimumCompressSize(50)// 小于100kb的图片不压缩
//                .scaleEnabled(true)// 裁剪是否可放大缩小图片 true or false
//                .isDragFrame(false)// 是否可拖动裁剪框(固定)
//                .forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code
//    }

//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (resultCode == Activity.RESULT_OK) {
//            if (requestCode == PictureConfig.CHOOSE_REQUEST) {
//                List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);
//                String path = "";
//                if (selectList != null && selectList.size() > 0) {
//                    LocalMedia localMedia = selectList.get(0);
//                    if (localMedia.isCompressed()) {
//                        path = localMedia.getCompressPath();
//                    } else if (localMedia.isCut()) {
//                        path = localMedia.getCutPath();
//                    } else {
//                        path = localMedia.getPath();
//                    }
//                }
//                try {
//                    uploadFileToIpfs(new File(path));
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//
//            }
//        }
//    }
}
