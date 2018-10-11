package com.wuyou.merchant.mvp.account;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.wuyou.merchant.CarefreeDaoSession;
import com.wuyou.merchant.R;
import com.wuyou.merchant.data.local.db.EosAccount;
import com.wuyou.merchant.view.activity.BaseActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Solang on 2018/9/12.
 */

public class ManagerAccountActivity extends BaseActivity {
    @BindView(R.id.rv_account)
    RecyclerView rvAccount;
    ScoreAccountListAdapter adapter;
    List<EosAccount> data;

    @Override
    protected void bindView(Bundle savedInstanceState) {
        setTitleText(getString(R.string.manager_account));
    }

    @Override
    protected void onResume() {
        super.onResume();
        initRecyclerView();
    }

    private void initRecyclerView() {
        data = CarefreeDaoSession.getInstance().getAllEosAccount();
        adapter = new ScoreAccountListAdapter(R.layout.item_score_account_list,data);
        rvAccount.setLayoutManager(new LinearLayoutManager(getCtx()));
        rvAccount.setAdapter(adapter);
    }

    @Override
    protected int getContentLayout() {
        return R.layout.activity_manager_account;
    }


    @OnClick({R.id.tv_import, R.id.tv_create})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_import:
                startActivity(new Intent(getCtx(), ImportAccountActivity.class));
                break;
            case R.id.tv_create:
                startActivity(new Intent(getCtx(), CreateAccountActivity.class));
                break;
        }
    }
}
