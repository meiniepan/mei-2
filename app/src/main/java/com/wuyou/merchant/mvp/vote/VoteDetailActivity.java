package com.wuyou.merchant.mvp.vote;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.gs.buluo.common.utils.ToastUtils;
import com.wuyou.merchant.Constant;
import com.wuyou.merchant.R;
import com.wuyou.merchant.adapter.VoteQuestionAdapter;
import com.wuyou.merchant.data.api.EosVoteListBean;
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

    EosVoteListBean.RowsBean rowsBean;
    VoteQuestionAdapter adapter;
    boolean hasVote;

    @Override
    protected int getContentLayout() {
        return R.layout.activity_vote_detail;
    }

    @Override
    protected void bindView(Bundle savedInstanceState) {
        setTitleText(getString(R.string.vote_detail));
        setTitleIcon(R.mipmap.icon_share, v -> ToastUtils.ToastMessage(getCtx(), R.string.not_open));
        initData();
    }

    private void initData() {
        rowsBean = getIntent().getParcelableExtra(Constant.VOTE_ROW_BEAN);
        hasVote =  hasVote = getIntent().getBooleanExtra(Constant.HAS_VOTE, false);
        GlideUtils.loadImage(getCtx(), Constant.IPFS_URL + rowsBean.logo, ivVoteDetailBac);
        tvTitle.setText(rowsBean.title);
        tvVoteDetailDeadline.setText(rowsBean.end_time);
        String peopleNum;
        if (rowsBean.voters.size() > 999) {
            peopleNum = "999+";
        } else {
            peopleNum = rowsBean.voters.size() + "";
        }
        tvVoteDetailPeopleNum.setText(peopleNum);
        tvVoteDetailCommunityName.setText(rowsBean.creator);
        tvVoteDetailIntro.setText(rowsBean.description);
        initRv();
    }

    private void initRv() {
        adapter = new VoteQuestionAdapter(R.layout.item_vote_detail_question, rowsBean.contents, hasVote, rowsBean.voters.size());
        rvVoteDetail.setLayoutManager(new LinearLayoutManager(getCtx()));
        rvVoteDetail.setAdapter(adapter);
    }


    @OnClick(R.id.tv_vote_detail_confirm)
    public void onViewClicked() {
        if (hasVote) {
            //todo
            finish();
        }
    }

}
