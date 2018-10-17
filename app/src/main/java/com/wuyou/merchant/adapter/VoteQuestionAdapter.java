package com.wuyou.merchant.adapter;

import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.gs.buluo.common.widget.recyclerHelper.BaseHolder;
import com.gs.buluo.common.widget.recyclerHelper.BaseQuickAdapter;
import com.wuyou.merchant.R;
import com.wuyou.merchant.data.api.VoteOptionContent;
import com.wuyou.merchant.data.api.VoteQuestion;
import com.wuyou.merchant.mvp.vote.DoChooseListener;

import java.util.List;

/**
 * Created by DELL on 2018/8/13.
 */

public class VoteQuestionAdapter extends BaseQuickAdapter<VoteQuestion, BaseHolder> implements DoChooseListener {
    private final boolean hasVote;
    private boolean isSingle;
    private VoteQuestionOptAdapter adapter;
    private int voteSum;

    public VoteQuestionAdapter(int layoutResId, @Nullable List<VoteQuestion> data, boolean hasVote, int voteSum) {
        super(layoutResId, data);
        this.hasVote = hasVote;
        this.voteSum = voteSum;
    }

    @Override
    protected void convert(BaseHolder holder, VoteQuestion data) {
        holder.setText(R.id.tv_vote_detail_ques, data.question);
        if (data.single == 0) {
            holder.setText(R.id.tv_vote_detail_ques_style, "(单选)");
            isSingle = true;
        } else {
            holder.setText(R.id.tv_vote_detail_ques_style, "(多选)");
            isSingle = false;
        }
        initRv(holder, data);
    }

    private void initRv(BaseHolder holder, VoteQuestion data) {
        RecyclerView recyclerView = holder.getView(R.id.rv_vote_detail_opt);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        adapter = new VoteQuestionOptAdapter(R.layout.item_vote_detail_question_opt, data.option, this,isSingle,hasVote,voteSum);
        recyclerView.setAdapter(adapter);

    }

    @Override
    public void doChoose(VoteOptionContent data, boolean isSingle, List<VoteOptionContent> voteOptionContents, VoteQuestionOptAdapter adapter) {
        if (isSingle) {

            for (VoteOptionContent e : voteOptionContents
                    ) {
                if (data.id != e.id) {
                    e.isChecked = false;
                }
            }
            adapter.notifyDataSetChanged();
        }
    }
}
