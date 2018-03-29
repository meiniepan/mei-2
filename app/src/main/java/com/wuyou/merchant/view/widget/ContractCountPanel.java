package com.wuyou.merchant.view.widget;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.wuyou.merchant.Constant;
import com.wuyou.merchant.R;
import com.wuyou.merchant.mvp.circle.SignContractActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by DELL on 2018/3/29.
 */

public class ContractCountPanel extends Dialog {

    @BindView(R.id.contract_count_title)
    TextView contractCountTitle;
    @BindView(R.id.contact_count_price)
    TextView contactCountPrice;
    @BindView(R.id.contract_count_number)
    EditText contractCountNumber;

    private float price;

    public ContractCountPanel(@NonNull Context context, String title, float price) {
        super(context, R.style.bottom_dialog);
        initView();
        contractCountTitle.setText(title);
        this.price = price;
    }

    private void initView() {
        View rootView = LayoutInflater.from(getContext()).inflate(R.layout.contract_count_board, null);
        setContentView(rootView);
        ButterKnife.bind(this, rootView);
        Window window = getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = ViewGroup.LayoutParams.MATCH_PARENT;
        params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        params.gravity = Gravity.BOTTOM;
        window.setAttributes(params);
    }

    private int count = 1;

    @OnClick({R.id.contract_count_minus, R.id.contract_count_add, R.id.contract_count_next})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.contract_count_minus:
                if (count > 1) {
                    count--;
                    contractCountNumber.setText(count + "");
                }
                break;
            case R.id.contract_count_add:
                count++;
                contractCountNumber.setText(count + "");
                break;
            case R.id.contract_count_next:
                Intent intent = new Intent(getContext(), SignContractActivity.class);
                intent.putExtra(Constant.CONTRACT_AMOUNT, price * count);
                getContext().startActivity(intent);
                break;
        }
    }
}
