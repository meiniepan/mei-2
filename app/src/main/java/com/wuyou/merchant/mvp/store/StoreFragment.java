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
                intent.setClass(getContext(), SettingActivity.class);
                startActivity(intent);
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
}
