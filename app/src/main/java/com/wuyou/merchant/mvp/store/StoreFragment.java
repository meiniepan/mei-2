package com.wuyou.merchant.mvp.store;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.gs.buluo.common.utils.ToastUtils;
import com.wuyou.merchant.CarefreeDaoSession;
import com.wuyou.merchant.R;
import com.wuyou.merchant.mvp.account.CreateOrImportAccountActivity;
import com.wuyou.merchant.mvp.account.ScoreAccountActivity;
import com.wuyou.merchant.mvp.vote.MyVoteListActivity;
import com.wuyou.merchant.mvp.vote.VoteCreateActivity;
import com.wuyou.merchant.util.CommonUtil;
import com.wuyou.merchant.util.NetTool;
import com.wuyou.merchant.util.glide.GlideUtils;
import com.wuyou.merchant.view.activity.HelpRobotActivity;
import com.wuyou.merchant.view.fragment.BaseFragment;

import butterknife.BindView;
import butterknife.OnClick;


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
    @BindView(R.id.mine_create_account)
    TextView tvCreateAccount;
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
        if (getString(R.string.create_vote).equals(tvCreateAccount.getText().toString().trim()) && CarefreeDaoSession.getInstance().getAllEosAccount().size() != 0) {
            tvCreateAccount.setText(R.string.mine_account);
        }
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
                if (CarefreeDaoSession.getInstance().getAllEosAccount().size() == 0) {
                    ToastUtils.ToastMessage(getContext(), getString(R.string.create_account_first));
                    return;
                }
                intent.setClass(mCtx, VoteCreateActivity.class);
                startActivity(intent);
                break;
            case R.id.ll_vote_record:
                if (CarefreeDaoSession.getInstance().getAllEosAccount().size() == 0) {
                    ToastUtils.ToastMessage(getContext(), getString(R.string.create_account_first));
                    return;
                }
                intent.setClass(mCtx, MyVoteListActivity.class);
                startActivity(intent);
                break;
        }

    }
}
