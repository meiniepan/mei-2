package com.wuyou.merchant.mvp.vote;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wuyou.merchant.R;
import com.wuyou.merchant.view.activity.BaseActivity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by DELL on 2018/10/17.
 */

public class VoteCreateActivity extends BaseActivity {
    @BindView(R.id.create_vote_intro)
    EditText createVoteIntro;
    @BindView(R.id.create_vote_picture)
    ImageView createVotePicture;
    @BindView(R.id.create_vote_org)
    EditText createVoteOrg;
    @BindView(R.id.create_vote_calendar)
    TextView createVoteCalendar;
    @BindView(R.id.create_vote_question_group)
    LinearLayout createVoteQuestionGroup;
    @BindView(R.id.create_vote_preview)
    TextView createVotePreview;
    @BindView(R.id.create_vote_release)
    TextView createVoteRelease;

    @Override
    protected int getContentLayout() {
        return R.layout.activity_create_vote;
    }

    @Override
    protected void bindView(Bundle savedInstanceState) {
        setTitleText(R.string.create_vote);

    }


    ArrayList<QuestionViewHolder> questionViewHolders = new ArrayList<>();

    @OnClick({R.id.create_vote_picture, R.id.create_vote_preview, R.id.create_vote_release, R.id.create_vote_add_single_question, R.id.create_vote_add_multi_question})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.create_vote_picture:
                break;
            case R.id.create_vote_preview:
                break;
            case R.id.create_vote_release:
                break;
            case R.id.create_vote_add_single_question:
                QuestionViewHolder holder = new QuestionViewHolder();
                questionViewHolders.add(holder);
                break;
            case R.id.create_vote_add_multi_question:
                QuestionViewHolder multiHolder = new QuestionViewHolder();
                questionViewHolders.add(multiHolder);
                break;
        }
    }


    class QuestionViewHolder {
        TextView createVoteQuestionItemTitle;
        LinearLayout createVoteOptionGroup;

        ArrayList<OptionViewHolder> optionViewHolders = new ArrayList<>();

        QuestionViewHolder() {
            createView();
        }

        View createView() {
            View view = LayoutInflater.from(getCtx()).inflate(R.layout.create_vote_question_item, createVoteQuestionGroup);
            createVoteQuestionItemTitle = view.findViewById(R.id.create_vote_question_item_title);
            createVoteOptionGroup = view.findViewById(R.id.create_vote_question_item_group);
            view.findViewById(R.id.create_vote_question_item_delete).setOnClickListener(v -> {
                createVoteQuestionGroup.removeView(view);
                createVoteQuestionGroup.invalidate();
            });
            OptionViewHolder viewHolder = new OptionViewHolder(createVoteOptionGroup);
            optionViewHolders.add(viewHolder);
            return view;
        }
    }

    class OptionViewHolder {
        EditText createVoteOptionContent;
        ViewGroup questionView;

        OptionViewHolder(ViewGroup parent) {
            questionView= parent;
            createView();
        }

        View createView() {
            View view = LayoutInflater.from(getCtx()).inflate(R.layout.create_vote_option_item, questionView);
            createVoteOptionContent = view.findViewById(R.id.create_vote_option_content);
            view.findViewById(R.id.create_vote_option_add).setOnClickListener(v -> {
                OptionViewHolder viewHolder = new OptionViewHolder(questionView);
            });

            view.findViewById(R.id.create_vote_option_delete).setOnClickListener(v -> {
                if (questionView.getChildCount() != 1) {
                    questionView.removeView(view);
                }
            });
            return view;
        }
    }
}
