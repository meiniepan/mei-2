package com.wuyou.merchant.view.widget.panel;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.gs.buluo.common.utils.SharePreferenceManager;
import com.wuyou.merchant.Constant;
import com.wuyou.merchant.R;
import com.wuyou.merchant.view.widget.CustomNestRadioGroup;

import butterknife.ButterKnife;

/**
 * Created by hjn on 2017/1/3.
 */

public class EnvironmentChoosePanel extends Dialog {
    public EnvironmentChoosePanel(Context context) {
        super(context, R.style.bottom_dialog);
        initView();
    }

    private void initView() {
        View rootView = LayoutInflater.from(getContext()).inflate(R.layout.env_choose_board, null);
        setContentView(rootView);
        ButterKnife.bind(this);
        Window window = getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = ViewGroup.LayoutParams.MATCH_PARENT;
        params.height = ViewGroup.LayoutParams.MATCH_PARENT;
        params.gravity = Gravity.BOTTOM;
        window.setAttributes(params);

        CustomNestRadioGroup radioGroup = rootView.findViewById(R.id.env_group);

        radioGroup.setOnCheckedChangeListener((group, checkedId) -> setEnv(checkedId));

        findViewById(R.id.env_login).setOnClickListener(v -> dismiss());
    }


    public void setEnv(int env) {
        switch (env) {
            case R.id.env_dev:
                Constant.BASE_URL = Constant.DEV_BASE_URL;
                Constant.CHAIN_URL = Constant.DEV_CHAIN_URL;
                Constant.IPFS_URL = Constant.DEV_IPFS_URL;
                break;
            case R.id.env_test:
                Constant.BASE_URL = Constant.STAGE_BASE_URL;
                Constant.CHAIN_URL = Constant.STAGE_CHAIN_URL;
                Constant.IPFS_URL = Constant.STAGE_IPFS_URL;
                break;
            case R.id.env_online:
                Constant.BASE_URL = Constant.ONLINE_BASE_URL;
                Constant.CHAIN_URL = Constant.ONLINE_CHAIN_URL;
                Constant.IPFS_URL = Constant.ONLINE_IPFS_URL;
                break;
        }
        SharePreferenceManager.getInstance(getContext()).setValue(Constant.SP_BASE_URL, Constant.BASE_URL);
        SharePreferenceManager.getInstance(getContext()).setValue(Constant.SP_CHAIN_URL, Constant.CHAIN_URL);
        SharePreferenceManager.getInstance(getContext()).setValue(Constant.SP_IPFS_URL, Constant.IPFS_URL);
    }
}
