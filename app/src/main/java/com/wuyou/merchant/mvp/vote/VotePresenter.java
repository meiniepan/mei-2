package com.wuyou.merchant.mvp.vote;

import android.util.Log;

import com.google.gson.JsonObject;
import com.gs.buluo.common.network.BaseSubscriber;
import com.gs.buluo.common.network.ErrorBody;
import com.gs.buluo.common.utils.ToastUtils;
import com.wuyou.merchant.CarefreeApplication;
import com.wuyou.merchant.Constant;
import com.wuyou.merchant.data.EoscDataManager;
import com.wuyou.merchant.data.api.VoteOptionContent;
import com.wuyou.merchant.data.api.VoteOption;
import com.wuyou.merchant.data.api.VoteQuestion;
import com.wuyou.merchant.util.RxUtil;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import io.ipfs.api.IPFS;
import io.ipfs.api.MerkleNode;
import io.ipfs.api.NamedStreamable;
import io.ipfs.multiaddr.MultiAddress;
import io.ipfs.multihash.Multihash;
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

    public void getFile(){
        Observable.create(new ObservableOnSubscribe<byte[]>() {
            @Override
            public void subscribe(ObservableEmitter<byte[]> e) throws Exception {
                IPFS ipfs = new IPFS(Constant.IPFS_URL);
                Multihash filePointer = Multihash.fromBase58("");
                byte[] fileContents = ipfs.cat(filePointer);

            }
        }).compose(RxUtil.switchSchedulers())
                .subscribe(new BaseSubscriber<byte[]>() {
                    @Override
                    public void onSuccess(byte[] bytes) {

                    }
                });

    }


    public void createVote(String logo) {
        ArrayList<VoteOptionContent> optionContents = new ArrayList<>();
        VoteOptionContent content = new VoteOptionContent("yes");
        VoteOptionContent content1 = new VoteOptionContent("what");

        ArrayList<VoteQuestion> contents = new ArrayList<>();
        VoteQuestion question = new VoteQuestion();
        question.question = "hello?";
        optionContents.add(content);
        optionContents.add(content1);
        question.option = optionContents;

        VoteQuestion question1 = new VoteQuestion();
        question1.question = "你是 zz 么";
        question1.option = optionContents;
        question1.single = 1;


        VoteQuestion question2 = new VoteQuestion();
        question2.question = "hello merchant?";
        question2.option = optionContents;
        question2.single = 0;

        ArrayList<VoteOptionContent> optionContents1 = new ArrayList<>();
        VoteOptionContent content11 = new VoteOptionContent("yes");
        VoteOptionContent content22 = new VoteOptionContent("what");
        VoteOptionContent content33 = new VoteOptionContent("nono");
        VoteOptionContent content44 = new VoteOptionContent("I don't know");
        VoteOptionContent content55 = new VoteOptionContent("what the f**k");
        optionContents1.add(content11);
        optionContents1.add(content22);
        optionContents1.add(content33);
        optionContents1.add(content44);
        optionContents1.add(content55);
        VoteQuestion question3 = new VoteQuestion();
        question3.question = "hello ,how are u?";
        question3.option = optionContents1;
        question3.single = 1;

        contents.add(question);
        contents.add(question1);
        contents.add(question2);
        contents.add(question3);

        EoscDataManager.getIns().createVote("merchant 问卷test8888", "QmefdCKsLhfdSLxEH9k8ediJ3pRtBsq788rhAuh63UX9xr", "desc11111", "org","",contents)
                .compose(RxUtil.switchSchedulers())
                .subscribe(new BaseSubscriber<JsonObject>() {
                    @Override
                    public void onSuccess(JsonObject jsonObject) {
                        Log.e("Carefree", "onSuccess: " + jsonObject);
                    }

                    @Override
                    protected void onNodeFail(int code, ErrorBody.DetailErrorBean message) {
                        if (message.message.contains("same title vote existed")) {
                            ToastUtils.ToastMessage(CarefreeApplication.getInstance().getApplicationContext(), "投票标题不能重复");
                        } else {
                            ToastUtils.ToastMessage(CarefreeApplication.getInstance().getApplicationContext(), message.message);
                        }
                    }
                });
    }
}
