package com.wuyou.merchant.mvp.vote;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.codbking.widget.DatePickDialog;
import com.codbking.widget.bean.DateType;
import com.google.gson.JsonObject;
import com.gs.buluo.common.network.BaseSubscriber;
import com.gs.buluo.common.network.ErrorBody;
import com.gs.buluo.common.utils.DensityUtils;
import com.gs.buluo.common.utils.ToastUtils;
import com.gs.buluo.common.utils.TribeDateUtils;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.wuyou.merchant.CarefreeApplication;
import com.wuyou.merchant.CarefreeDaoSession;
import com.wuyou.merchant.Constant;
import com.wuyou.merchant.R;
import com.wuyou.merchant.data.EoscDataManager;
import com.wuyou.merchant.data.api.EosVoteListBean;
import com.wuyou.merchant.data.api.VoteOptionContent;
import com.wuyou.merchant.data.api.VoteQuestion;
import com.wuyou.merchant.util.EosUtil;
import com.wuyou.merchant.util.RxUtil;
import com.wuyou.merchant.view.activity.BaseActivity;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.ipfs.api.IPFS;
import io.ipfs.api.MerkleNode;
import io.ipfs.api.NamedStreamable;
import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;

/**
 * Created by DELL on 2018/10/17.
 */

public class VoteCreateActivity extends BaseActivity {
    @BindView(R.id.root_linear)
    LinearLayout linearLayout;
    @BindView(R.id.create_vote_title)
    EditText createVoteTitle;
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

    @OnClick({R.id.create_vote_picture, R.id.create_vote_preview, R.id.create_vote_release, R.id.create_vote_add_single_question, R.id.create_vote_add_multi_question, R.id.create_vote_calendar})
    public void onViewClicked(View view) {
        NestedScrollView parent = (NestedScrollView) linearLayout.getParent();
        switch (view.getId()) {
            case R.id.create_vote_picture:
                choosePhoto();
                break;
            case R.id.create_vote_preview:
                previewVote();
                break;
            case R.id.create_vote_release:
                createVote();
                break;
            case R.id.create_vote_calendar:
                chooseDate();
                break;
            case R.id.create_vote_add_single_question:
                QuestionViewHolder holder = new QuestionViewHolder(1);
                questionViewHolders.add(holder);
                parent.arrowScroll(View.FOCUS_DOWN);
                parent.smoothScrollBy(0, DensityUtils.dip2px(getCtx(), 100));
                break;
            case R.id.create_vote_add_multi_question:
                QuestionViewHolder multiHolder = new QuestionViewHolder(0);
                questionViewHolders.add(multiHolder);
                parent.arrowScroll(View.FOCUS_DOWN);
                parent.smoothScrollBy(0, DensityUtils.dip2px(getCtx(), 100));
                break;
        }
    }

    private void previewVote() {
        if (createVoteTitle.length() == 0 || createVoteIntro.length() == 0 || createVoteOrg.length() == 0 || chooseDateTime == 0) {
            ToastUtils.ToastMessage(getCtx(), "信息不可为空");
            return;
        }
        EosVoteListBean.RowsBean rowsBean = new EosVoteListBean.RowsBean();
        rowsBean.creator = CarefreeDaoSession.getInstance().getMainAccount().getName();
        rowsBean.title = createVoteTitle.getText().toString().trim();
        rowsBean.end_time = EosUtil.formatTimePoint(chooseDateTime);
        rowsBean.logo = pictureCode;
        rowsBean.organization = createVoteOrg.getText().toString().trim();
        rowsBean.description = createVoteIntro.getText().toString().trim();
        if (contents.size() == 0) {
            fillQuestionContent();
        }
        rowsBean.contents = contents;
        Intent intent = new Intent(getCtx(), VoteDetailActivity.class);
        intent.putExtra(Constant.HAS_VOTE, false);
        intent.putExtra(Constant.VOTE_ROW_BEAN, rowsBean);
        startActivity(intent);
    }

    private long chooseDateTime;

    private void chooseDate() {
        DatePickDialog dialog = new DatePickDialog(this);
        dialog.setYearLimt(5);
        dialog.setTitle("选择时间");
        dialog.setType(DateType.TYPE_ALL);
        dialog.setMessageFormat("yyyy-MM-dd HH:mm");
        dialog.setStartDate(new Date(System.currentTimeMillis()));
        dialog.setOnSureLisener(date -> {
            if (date.getTime() < System.currentTimeMillis()) {
                ToastUtils.ToastMessage(getCtx(), "日期选择错误");
                return;
            }
            chooseDateTime = date.getTime();
            createVoteCalendar.setText(TribeDateUtils.dateFormat(date));
        });
        dialog.show();
    }

    ArrayList<VoteQuestion> contents = new ArrayList<>();

    private void createVote() {
        if (contents.size() == 0) {
            fillQuestionContent();
        }
        if (createVoteTitle.length() == 0 || createVoteIntro.length() == 0 || createVoteOrg.length() == 0 || chooseDateTime == 0) {
            ToastUtils.ToastMessage(getCtx(), "信息不可为空");
            return;
        }
        showLoadingDialog();
        EoscDataManager.getIns().createVote(createVoteTitle.getText().toString().trim(), pictureCode,
                createVoteIntro.getText().toString().trim(), createVoteOrg.getText().toString().trim(), EosUtil.formatTimePoint(chooseDateTime), contents)
                .compose(RxUtil.switchSchedulers())
                .subscribe(new BaseSubscriber<JsonObject>() {
                    @Override
                    public void onSuccess(JsonObject jsonObject) {
                        ToastUtils.ToastMessage(getCtx(), getString(R.string.create_vote_success));
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

    private void fillQuestionContent() {
        for (QuestionViewHolder questionViewHolder : questionViewHolders) {
            String question = questionViewHolder.createVoteQuestionItemTitle.getText().toString().trim();
            if (TextUtils.isEmpty(question)) {
                ToastUtils.ToastMessage(getCtx(), "问题不能为空");
                return;
            }
            VoteQuestion voteQuestion = new VoteQuestion();
            voteQuestion.question = question;
            voteQuestion.option = new ArrayList<>();
            voteQuestion.single = questionViewHolder.single;
            for (OptionViewHolder optionViewHolder : questionViewHolder.optionViewHolders) {
                String option = optionViewHolder.createVoteOptionContent.getText().toString().trim();
                if (TextUtils.isEmpty(option)) {
                    ToastUtils.ToastMessage(getCtx(), "选项不能为空");
                    return;
                }
                voteQuestion.option.add(new VoteOptionContent(option));
            }
            contents.add(voteQuestion);
        }
    }

    private void choosePhoto() {
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
                .withAspectRatio(16, 9)// int 裁剪比例 如16:9 3:2 3:4 1:1 可自定义
                .compressSavePath(CarefreeApplication.getInstance().getApplicationContext().getFilesDir().getAbsolutePath())//压缩图片保存地址
                .freeStyleCropEnabled(false)// 裁剪框是否可拖拽 true or false
                .openClickSound(false)// 是否开启点击声音 true or false
                .cropCompressQuality(50)// 裁剪压缩质量 默认90 int
                .minimumCompressSize(100)// 小于100kb的图片不压缩
                .scaleEnabled(true)// 裁剪是否可放大缩小图片 true or false
                .isDragFrame(true)// 是否可拖动裁剪框(固定)
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
                createVotePicture.setImageURI(Uri.parse(path));
                try {
                    uploadFileToIpfs(new File(path));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void uploadFileToIpfs(File des) throws IOException {
        Observable.create((ObservableOnSubscribe<String>) e -> {
            IPFS ipfs = new IPFS(Constant.IPFS_URL, 5001);
            ipfs.refs.local();
            NamedStreamable.FileWrapper file = new NamedStreamable.FileWrapper(des);
            MerkleNode addResult = ipfs.add(file).get(0);
            e.onNext(addResult.hash.toBase58());
        }).compose(RxUtil.switchSchedulers())
                .subscribe(new BaseSubscriber<String>() {
                    @Override
                    public void onSuccess(String hashCode) {
                        pictureCode = hashCode;
                    }
                });
    }

    private String pictureCode;

    class QuestionViewHolder {
        EditText createVoteQuestionItemTitle;
        int single;
        LinearLayout createVoteOptionGroup;

        ArrayList<OptionViewHolder> optionViewHolders = new ArrayList<>();

        QuestionViewHolder(int single) {
            this.single = single;
            createView();
        }

        void createView() {
            View view = LayoutInflater.from(getCtx()).inflate(R.layout.create_vote_question_item, null);
            createVoteQuestionItemTitle = view.findViewById(R.id.create_vote_question_item_title);
            createVoteOptionGroup = view.findViewById(R.id.create_vote_question_item_group);
            view.findViewById(R.id.create_vote_question_item_delete).setOnClickListener(v -> createVoteQuestionGroup.removeView(view));
            optionViewHolders.add(new OptionViewHolder(createVoteOptionGroup, optionViewHolders));
            createVoteQuestionGroup.addView(view);
        }
    }

    class OptionViewHolder {
        EditText createVoteOptionContent;
        ViewGroup questionView;
        ArrayList<OptionViewHolder> optionViewHolders;

        OptionViewHolder(ViewGroup parent, ArrayList<OptionViewHolder> optionViewHolders) {
            this.optionViewHolders = optionViewHolders;
            questionView = parent;
            createView();
        }

        void createView() {
            View view = LayoutInflater.from(getCtx()).inflate(R.layout.create_vote_option_item, null);
            createVoteOptionContent = view.findViewById(R.id.create_vote_option_content);
            view.findViewById(R.id.create_vote_option_add).setOnClickListener(v -> optionViewHolders.add(new OptionViewHolder(questionView, optionViewHolders)));

            view.findViewById(R.id.create_vote_option_delete).setOnClickListener(v -> {
                if (questionView.getChildCount() > 2) {
                    questionView.removeView(view);
                }
            });
            questionView.addView(view);
        }
    }
}
