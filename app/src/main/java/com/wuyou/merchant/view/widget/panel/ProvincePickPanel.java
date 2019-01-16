package com.wuyou.merchant.view.widget.panel;

import android.app.Dialog;
import android.content.res.AssetManager;
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
import com.wuyou.merchant.bean.entity.ProvinceModel;
import com.wuyou.merchant.util.XmlParserHandler;
import com.wuyou.merchant.view.activity.BaseActivity;

import java.io.InputStream;
import java.util.List;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import butterknife.ButterKnife;

/**
 * Created by solang on 2019/1/16.
 */

public class ProvincePickPanel extends Dialog implements View.OnClickListener, OnWheelChangedListener {

    private final BaseActivity mActivity;
    private WheelView mViewProvince;
    private TextView mBtnConfirm;
    private OnSelectedFinished onSelectedFinished;
    /**
     * 解析省市区
     */
    protected String[] mProvinceDatas;
    protected String mCurrentProviceName;
    private View rootView;

    public ProvincePickPanel(BaseActivity activity, OnSelectedFinished onSelectedFinished) {
        super(activity, R.style.my_dialog);
        mActivity = activity;
        this.onSelectedFinished = onSelectedFinished;
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
        params.width = ViewGroup.LayoutParams.MATCH_PARENT;
        params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
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
        mViewProvince.setViewAdapter(new ArrayWheelAdapter<>(mActivity, mProvinceDatas));
        mViewProvince.setVisibleItems(5);

    }

    @Override
    public void onChanged(WheelView wheel, int oldValue, int newValue) {
        // TODO Auto-generated method stub
        if (wheel == mViewProvince) {
            int pCurrent = mViewProvince.getCurrentItem();
            mCurrentProviceName = mProvinceDatas[pCurrent];
        }

    }


    protected void initProvinceDatas() {
        List<ProvinceModel> provinceList = null;
        AssetManager asset = mActivity.getAssets();
        try {
            InputStream input = asset.open("province_data.xml");
            SAXParserFactory spf = SAXParserFactory.newInstance();
            SAXParser parser = spf.newSAXParser();
            XmlParserHandler handler = new XmlParserHandler();
            parser.parse(input, handler);
            input.close();
            provinceList = handler.getDataList();
            if (provinceList != null && !provinceList.isEmpty()) {
                mCurrentProviceName = provinceList.get(0).getName();
            }
            //*/
            mProvinceDatas = new String[provinceList.size()];
            for (int i = 0; i < provinceList.size(); i++) {
                mProvinceDatas[i] = provinceList.get(i).getName();
            }
        } catch (Throwable e) {
            e.printStackTrace();
        } finally {
        }
    }

    public interface OnSelectedFinished {
        void onSelected(String string);
    }
}
