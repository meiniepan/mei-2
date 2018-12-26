package com.wuyou.merchant.adapter;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.gs.buluo.common.widget.recyclerHelper.BaseHolder;
import com.gs.buluo.common.widget.recyclerHelper.BaseQuickAdapter;
import com.wuyou.merchant.R;
import com.wuyou.merchant.data.api.VoteOptionContent;
import com.wuyou.merchant.mvp.vote.DoChooseListener;
import com.wuyou.merchant.util.CommonUtil;

import java.util.List;

/**
 * Created by DELL on 2018/8/13.
 */

public class VoteQuestionOptAdapter extends BaseQuickAdapter<VoteOptionContent, BaseHolder> {
    private final DoChooseListener chooseListener;
    private final boolean isSingle;
    private final boolean hasVote;
    private float voteSum = 0;

    public VoteQuestionOptAdapter(int layoutResId, @Nullable List<VoteOptionContent> data, DoChooseListener chooseListener, boolean isSingle, boolean hasVote) {
        super(layoutResId, data);
        this.chooseListener = chooseListener;
        this.isSingle = isSingle;
        this.hasVote = hasVote;
        for (VoteOptionContent content : data) {
            voteSum += content.number;
        }
    }

    @Override
    protected void convert(BaseHolder holder, VoteOptionContent data) {
        data.id = holder.getAdapterPosition();
        holder.setText(R.id.tv_vote_detail_opt, data.optioncontent);
        CheckBox checkBox = holder.getView(R.id.cb_vote_opt);
        LinearLayout linearLayout = holder.getView(R.id.ll_vote_opt);
        ProgressBar progressBar = holder.getView(R.id.pb_vote_opt);
        TextView tvScale = holder.getView(R.id.tv_vote_opt_scale);
        if (hasVote) {
            checkBox.setVisibility(View.GONE);
            linearLayout.setVisibility(View.VISIBLE);
            progressBar.setProgress(voteSum == 0 ? 0 : (int) (data.number * 100 / voteSum));
            tvScale.setText(((voteSum == 0) ? 0 : CommonUtil.formatPrice(data.number * 100 / voteSum)) + "%");
        } else {
            checkBox.setVisibility(View.VISIBLE);
            linearLayout.setVisibility(View.GONE);
            checkBox.setChecked(data.isChecked);
            checkBox.setOnClickListener(v -> {
                data.isChecked = !data.isChecked;
                chooseListener.doChoose(data, isSingle, getData());
            });

        }


    }


}
