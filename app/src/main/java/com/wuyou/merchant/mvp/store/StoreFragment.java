package com.wuyou.merchant.mvp.store;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wuyou.merchant.R;
import com.wuyou.merchant.view.activity.MainActivity;
import com.wuyou.merchant.view.fragment.BaseFragment;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


/**
 * Created by Administrator on 2018\1\29 0029.
 */

public class StoreFragment extends BaseFragment {


    @Override
    protected int getContentLayout() {
        return R.layout.store_home;
    }

    @Override
    protected void bindView(Bundle savedInstanceState) {

    }

    @Override
    public void showError(String message, int res) {

    }


    @OnClick({R.id.iv_store_info, R.id.ll_worker_list})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_store_info:
                startActivity(new Intent(getContext(), StoreInfoEditActivity.class));
                break;
            case R.id.ll_worker_list:
                break;
        }
    }
}
