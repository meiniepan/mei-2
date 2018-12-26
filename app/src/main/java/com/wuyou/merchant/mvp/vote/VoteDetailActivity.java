package com.wuyou.merchant.mvp.vote;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.JsonObject;
import com.gs.buluo.common.network.BaseSubscriber;
import com.gs.buluo.common.network.ErrorBody;
import com.gs.buluo.common.utils.AppManager;
import com.gs.buluo.common.utils.ToastUtils;
import com.wuyou.merchant.CarefreeApplication;
import com.wuyou.merchant.Constant;
import com.wuyou.merchant.R;
import com.wuyou.merchant.adapter.VoteQuestionAdapter;
import com.wuyou.merchant.data.EoscDataManager;
import com.wuyou.merchant.data.api.EosVoteListBean;
import com.wuyou.merchant.util.EosUtil;
import com.wuyou.merchant.util.RxUtil;
import com.wuyou.merchant.util.glide.GlideUtils;
import com.wuyou.merchant.view.activity.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Solang on 2018/10/15.
 */

public class VoteDetailActivity extends BaseActivity {
    @BindView(R.id.iv_vote_detail_bac)
    ImageView ivVoteDetailBac;
    @BindView(R.id.tv_vote_detail_deadline)
    TextView tvVoteDetailDeadline;
    @BindView(R.id.tv_vote_detail_people_num)
    TextView tvVoteDetailPeopleNum;
    @BindView(R.id.tv_vote_detail_title)
    TextView tvTitle;
    @BindView(R.id.tv_vote_detail_intro)
    TextView tvVoteDetailIntro;
    @BindView(R.id.rv_vote_detail)
    RecyclerView rvVoteDetail;
    @BindView(R.id.tv_vote_detail_community_name)
    TextView tvVoteDetailCommunityName;
    @BindView(R.id.tv_vote_detail_confirm)
    TextView tvVoteDetailConfirm;
    @BindView(R.id.vote_detail_back)
    TextView voteDetailBack;

    EosVoteListBean.RowsBean rowsBean;
    VoteQuestionAdapter adapter;
    boolean hasVote;
    private boolean update;

    @Override
    protected int getContentLayout() {
        return R.layout.activity_vote_detail;
    }

    @Override
    protected void bindView(Bundle savedInstanceState) {
        setTitleText(getString(R.string.vote_detail));
        initData();
    }

    private void initData() {
        update = getIntent().getBooleanExtra(Constant.FOR_UPDATE, false);
        rowsBean = getIntent().getParcelableExtra(Constant.VOTE_ROW_BEAN);
        hasVote = getIntent().getBooleanExtra(Constant.HAS_VOTE, false);
        if (hasVote) {
            voteDetailBack.setVisibility(View.GONE);
            tvVoteDetailConfirm.setText(R.string.yes);
        }
        GlideUtils.loadImage(getCtx(),  Constant.IPFS_URL.contains(Constant.ONLINE_IPFS_URL)?Constant.HTTP_IPFS_URL:Constant.DEV_HTTP_IPFS_URL + rowsBean.logo, ivVoteDetailBac);
        tvTitle.setText(rowsBean.title);
        tvVoteDetailDeadline.setText(EosUtil.UTCToCST(rowsBean.end_time));
        String peopleNum = "0";
        if (rowsBean.voters != null) {
            if (rowsBean.voters.size() > 999) {
                peopleNum = "999+";
            } else {
                peopleNum = rowsBean.voters.size() + "";
            }
        }
        tvVoteDetailPeopleNum.setText(peopleNum);
        tvVoteDetailCommunityName.setText(rowsBean.organization);
        tvVoteDetailIntro.setText(rowsBean.description);
        initRv();
    }

    private void initRv() {
        adapter = new VoteQuestionAdapter(R.layout.item_vote_detail_question, rowsBean.contents, hasVote);
        rvVoteDetail.setLayoutManager(new LinearLayoutManager(getCtx()));
        rvVoteDetail.setAdapter(adapter);
    }


    @OnClick({R.id.tv_vote_detail_confirm, R.id.vote_detail_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_vote_detail_confirm:
                if (hasVote) {
                    finish();
                } else {
                    releaseVote();
                }
                break;
            case R.id.vote_detail_back:
                finish();
                break;
        }
    }

    private void releaseVote() {
        showLoadingDialog();
        if (update) {
            updateVote();
        } else {
            createVote();
        }
    }

    private void updateVote() {
        EoscDataManager.getIns().updateVote(rowsBean.id,rowsBean.title, rowsBean.logo, rowsBean.description, rowsBean.organization, rowsBean.end_time, rowsBean.contents)
                .compose(RxUtil.switchSchedulers())
                .subscribe(new BaseSubscriber<JsonObject>() {
                    @Override
                    public void onSuccess(JsonObject jsonObject) {
                        ToastUtils.ToastMessage(getCtx(), R.string.update_vote_success);
                        Intent intent = new Intent(getCtx(), MyVoteListActivity.class);
                        startActivity(intent);
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

    private void createVote() {
        EoscDataManager.getIns().createVote(rowsBean.title, rowsBean.logo, rowsBean.description, rowsBean.organization, rowsBean.end_time, rowsBean.contents)
                .compose(RxUtil.switchSchedulers())
                .subscribe(new BaseSubscriber<JsonObject>() {
                    @Override
                    public void onSuccess(JsonObject jsonObject) {
                        ToastUtils.ToastMessage(getCtx(), R.string.create_vote_success);
                        AppManager.getAppManager().finishActivity(VoteCreateActivity.class);
                        finish();
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
