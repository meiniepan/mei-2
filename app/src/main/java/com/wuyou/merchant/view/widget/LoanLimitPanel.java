package com.wuyou.merchant.view.widget;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.wuyou.merchant.R;
import com.wuyou.merchant.adapter.LoanLimitAdapter;
import com.wuyou.merchant.bean.entity.PrepareSignEntity;
import com.wuyou.merchant.util.CommonUtil;

import java.util.List;

/**
 * Created by DELL on 2018/3/29.
 */

public class LoanLimitPanel extends Dialog {
    RecyclerView recyclerView;

    onLoadSelectListener onLoadSelectListener;

    public LoanLimitPanel(@NonNull Context context, onLoadSelectListener onLoadSelectListener) {
        super(context, R.style.my_dialog);
        initView();
        this.onLoadSelectListener = onLoadSelectListener;
    }


    private void initView() {
        View rootView = LayoutInflater.from(getContext()).inflate(R.layout.loan_limit_board, null);
        setContentView(rootView);
        Window window = getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = ViewGroup.LayoutParams.MATCH_PARENT;
        params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        params.gravity = Gravity.BOTTOM;
        window.setAttributes(params);
        recyclerView = rootView.findViewById(R.id.loan_limit_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.addItemDecoration(CommonUtil.getRecyclerDivider(getContext()));
    }

    public void setData(List<PrepareSignEntity.RatesBean> loanEntities){
        recyclerView.setAdapter(new LoanLimitAdapter(R.layout.item_loan_limit,loanEntities));
    }

    public interface onLoadSelectListener {
        void onSelected(PrepareSignEntity.RatesBean entity);
    }
}
