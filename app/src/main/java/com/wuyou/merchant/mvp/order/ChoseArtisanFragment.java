package com.wuyou.merchant.mvp.order;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.gs.buluo.common.utils.ToastUtils;
import com.gs.buluo.common.widget.CustomAlertDialog;
import com.wuyou.merchant.R;
import com.wuyou.merchant.adapter.OrderBeforeRvAdapter;
import com.wuyou.merchant.adapter.OtherRvAdapter;
import com.wuyou.merchant.view.fragment.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by solang on 2018/1/31.
 */

public class ChoseArtisanFragment extends BaseFragment {
    @BindView(R.id.rv_orders)
    RecyclerView recyclerView;
    List<Integer> data = new ArrayList();

    @Override
    protected int getContentLayout() {
        return R.layout.fragment_chose_artisan;
    }

    @Override
    protected void bindView(Bundle savedInstanceState) {
        data.add(1);
        data.add(2);
        data.add(3);
        OtherRvAdapter adapter = new OtherRvAdapter(getActivity(),R.layout.item_chose_artisan, data);
        adapter.setOnItemClickListener((adapter1, view, position) -> {
            showAlert();
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
    }

    private void showAlert() {
        CustomAlertDialog.Builder builder = new CustomAlertDialog.Builder(getContext());
        builder.setTitle("是否分单给服务者").setMessage("王晓明");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ToastUtils.ToastMessage(getContext(),"完成");
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.create().show();
    }

    @Override
    public void showError(String message, int res) {

    }
}
