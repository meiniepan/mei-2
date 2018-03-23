package com.wuyou.merchant.mvp.wallet;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.gs.buluo.common.utils.DensityUtils;
import com.gs.buluo.common.utils.ToastUtils;
import com.wuyou.merchant.R;
import com.wuyou.merchant.util.CommonUtil;
import com.wuyou.merchant.view.activity.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by solang on 2018/3/21.
 */

public class RepayActivity extends BaseActivity {
    @BindView(R.id.tv_total_repay_num)
    TextView tvTotalRepayNum;
    @BindView(R.id.tv_this_repay_num)
    TextView tvThisRepayNum;

    @Override
    protected int getContentLayout() {
        return R.layout.activity_repay;
    }

    @Override
    protected void bindView(Bundle savedInstanceState) {

    }


    @OnClick({R.id.iv_more, R.id.ll_repay_style, R.id.btn_confirm})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_more:
                showMore(view);
                break;
            case R.id.ll_repay_style:
                break;
            case R.id.btn_confirm:
                break;
        }
    }

    private void showMore(View view) {
        View contentView = LayoutInflater.from(getCtx()).inflate(
                R.layout.main_frag_list_button, null);

        final PopupWindow popupWindow = new PopupWindow(contentView,
                DensityUtils.dip2px(getCtx(), 120), DensityUtils.dip2px(getCtx(), 85), true);
        contentView.findViewById(R.id.tv_record).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getCtx(), RepayRecordActivity.class));
                popupWindow.dismiss();
            }
        });
        contentView.findViewById(R.id.tv_help).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.ToastMessage(getCtx(),"暂未开通！");
                popupWindow.dismiss();
            }
        });
        popupWindow.setTouchable(true);
        popupWindow.setAnimationStyle(R.style.PopupWindowAnimation);
        popupWindow.setOnDismissListener(new RepayActivity.popOnDismissListener());
        CommonUtil.backgroundAlpha(RepayActivity.this, 0.61f);
        popupWindow.setTouchInterceptor(new View.OnTouchListener() {

            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
                // 这里如果返回true的话，touch事件将被拦截
                // 拦截后 PopupWindow的onTouchEvent不被调用，这样点击外部区域无法dismiss
            }
        });
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.showAsDropDown(view, -view.getWidth() - DensityUtils.dip2px(getCtx(), 20), 0);
    }

    class popOnDismissListener implements PopupWindow.OnDismissListener {

        @Override
        public void onDismiss() {
            CommonUtil.backgroundAlpha(RepayActivity.this, 1f);
        }

    }
}
