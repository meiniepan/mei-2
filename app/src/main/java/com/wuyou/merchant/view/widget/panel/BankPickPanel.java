package com.wuyou.merchant.view.widget.panel;

import android.app.Dialog;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.gs.buluo.common.widget.wheel.OnWheelChangedListener;
import com.gs.buluo.common.widget.wheel.WheelView;
import com.gs.buluo.common.widget.wheel.adapters.ArrayWheelAdapter;
import com.wuyou.merchant.R;
import com.wuyou.merchant.view.activity.BaseActivity;

import butterknife.ButterKnife;

/**
 * Created by solang on 2019/1/16.
 */

public class BankPickPanel extends Dialog implements View.OnClickListener, OnWheelChangedListener {

    private final BaseActivity mActivity;
    private WheelView mViewProvince;
    private TextView mBtnConfirm;
    private OnSelectedFinished onSelectedFinished;
    protected String[] mBankDatas;
    protected String mCurrentProviceName;
    private View rootView;

    public BankPickPanel(BaseActivity activity, OnSelectedFinished onSelectedFinished) {
        super(activity, R.style.my_dialog);
        mActivity = activity;
        this.onSelectedFinished=onSelectedFinished;
        initView();
        setUpViews();
        setUpListener();
        setUpData();
    }

    private void initView() {
        rootView = LayoutInflater.from(mActivity).inflate(R.layout.picker_board, null);
        setContentView(rootView);
        Window window = getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.width= ViewGroup.LayoutParams.MATCH_PARENT;
        params.height= ViewGroup.LayoutParams.WRAP_CONTENT;
        params.gravity = Gravity.BOTTOM;
        window.setAttributes(params);
        ButterKnife.bind(this, rootView);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_confirm:
                onSelectedFinished.onSelected(mCurrentProviceName);
                dismiss();
                break;
        }
    }

    private void setUpViews() {
        mViewProvince = (WheelView) rootView.findViewById(R.id.id_province);
        mBtnConfirm = (TextView) rootView.findViewById(R.id.btn_confirm);
    }

    private void setUpListener() {
        mViewProvince.addChangingListener(this);
        mBtnConfirm.setOnClickListener(this);
    }

    private void setUpData() {
        initProvinceDatas();
        mViewProvince.setViewAdapter(new ArrayWheelAdapter<>(mActivity, mBankDatas));
        mViewProvince.setVisibleItems(5);

    }

    @Override
    public void onChanged(WheelView wheel, int oldValue, int newValue) {
        // TODO Auto-generated method stub
        if (wheel == mViewProvince) {
            int pCurrent = mViewProvince.getCurrentItem();
            mCurrentProviceName = mBankDatas[pCurrent];
        }

    }


    protected void initProvinceDatas() {
        mBankDatas = new String[]{
                "招商银行","建设银行","交通银行","邮政储蓄银行","工商银行","农业银行","中国银行","中信银行"
                ,"光大银行","华夏银行","民生银行","广发银行","平安银行"
        };
    }

    public interface OnSelectedFinished {
        void onSelected(String string);
    }
}
