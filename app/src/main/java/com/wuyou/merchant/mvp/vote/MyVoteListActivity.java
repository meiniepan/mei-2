package com.wuyou.merchant.mvp.vote;

import android.os.Bundle;

import com.google.gson.GsonBuilder;
import com.gs.buluo.common.network.ApiException;
import com.gs.buluo.common.network.BaseSubscriber;
import com.gs.buluo.common.widget.recyclerHelper.BaseHolder;
import com.gs.buluo.common.widget.recyclerHelper.BaseQuickAdapter;
import com.wuyou.merchant.CarefreeDaoSession;
import com.wuyou.merchant.Constant;
import com.wuyou.merchant.R;
import com.wuyou.merchant.data.EoscDataManager;
import com.wuyou.merchant.data.api.EosVoteListBean;
import com.wuyou.merchant.data.api.VoteRecord;
import com.wuyou.merchant.util.CommonUtil;
import com.wuyou.merchant.util.RxUtil;
import com.wuyou.merchant.util.glide.GlideUtils;
import com.wuyou.merchant.view.activity.BaseActivity;
import com.wuyou.merchant.view.widget.CarefreeRecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.functions.BiFunction;

/**
 * Created by DELL on 2018/10/15.
 */

public class MyVoteListActivity extends BaseActivity {
    @BindView(R.id.vote_my_record)
    CarefreeRecyclerView voteMyRecord;

    @Override
    protected int getContentLayout() {
        return R.layout.activity_my_vote;
    }


    @Override
    protected void bindView(Bundle savedInstanceState) {
        setTitleText("我的创建");
        voteMyRecord.getRecyclerView().addItemDecoration(CommonUtil.getRecyclerDivider(getCtx(), 8));
        voteMyRecord.showProgressView();
        recordAdapter = new VoteRecordAdapter();
        voteMyRecord.setAdapter(recordAdapter);
        getAllVoteList();
    }

    public void getAllVoteList() {
        voteMyRecord.showProgressView();
        Observable<String> voteList = EoscDataManager.getIns().getTable(Constant.ACTIVITY_CREATE_VOTE, Constant.ACTIVITY_CREATE_VOTE, "votelist", "1", "", "", 20);
        Observable<String> recordInfo = EoscDataManager.getIns().getTable(CarefreeDaoSession.getInstance().getMainAccount().getName(), Constant.ACTIVITY_CREATE_VOTE, "infos");
        Observable.zip(voteList, recordInfo, (BiFunction<String, String, List<EosVoteListBean.RowsBean>>) (allVote, myVote) -> {
            HashMap<String, EosVoteListBean.RowsBean> myVotedMap = new HashMap<>();
            ArrayList<EosVoteListBean.RowsBean> data = new ArrayList<>();
            EosVoteListBean listBean = new GsonBuilder().create().fromJson(allVote, EosVoteListBean.class);
            VoteRecord voteRecord = new GsonBuilder().create().fromJson(myVote, VoteRecord.class);
            for (EosVoteListBean.RowsBean rowsBean : listBean.rows) {
                myVotedMap.put(rowsBean.id, rowsBean);
            }
            for (VoteRecord.RowsBean rowsBean : voteRecord.rows) {
                EosVoteListBean.RowsBean voteData = myVotedMap.get(rowsBean.voteid);
                if (voteData != null) {
                    data.add(voteData);
                }
            }
            return data;
        }).compose(RxUtil.switchSchedulers())
                .subscribe(new BaseSubscriber<List<EosVoteListBean.RowsBean>>() {
                    @Override
                    public void onSuccess(List<EosVoteListBean.RowsBean> rowsBeans) {
                        voteMyRecord.showContentView();
                        recordAdapter.setNewData(rowsBeans);
                        if (rowsBeans.size() == 0) {
                            voteMyRecord.showEmptyView("当前暂无投票记录");
                        }
                    }

                    @Override
                    protected void onFail(ApiException e) {
                        voteMyRecord.showErrorView(e.getDisplayMessage());
                    }
                });
    }

    private VoteRecordAdapter recordAdapter;

    class VoteRecordAdapter extends BaseQuickAdapter<EosVoteListBean.RowsBean, BaseHolder> {

        VoteRecordAdapter() {
            super(R.layout.item_vote_record);
        }

        @Override
        protected void convert(BaseHolder baseHolder, EosVoteListBean.RowsBean rowsBean) {
            baseHolder.setText(R.id.item_vote_record_title, rowsBean.title);
            GlideUtils.loadRoundCornerImage(mContext, Constant.IPFS_URL + rowsBean.logo, baseHolder.getView(R.id.item_vote_record_picture));
            baseHolder.getView(R.id.item_vote_record_statistic).setOnClickListener(v -> navigateToDetail(rowsBean));
        }

        private void navigateToDetail(EosVoteListBean.RowsBean rowsBean) {
//            Intent intent = new Intent(mContext, VoteDetailActivity.class);
//            intent.putExtra(Constant.HAS_VOTE, true);
//            intent.putExtra(Constant.VOTE_ROW_BEAN, rowsBean);
//            startActivity(intent);
        }
    }
}
