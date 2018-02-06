package com.wuyou.merchant.view.fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gs.buluo.common.widget.LoadingDialog;
import com.wuyou.merchant.CarefreeApplication;
import com.wuyou.merchant.mvp.BasePresenter;
import com.wuyou.merchant.mvp.IBaseView;
import com.wuyou.merchant.mvp.login.LoginActivity;

import butterknife.ButterKnife;

/**
 * Created by admin on 2016/11/1.
 */
public abstract class BaseFragment<V extends IBaseView, P extends BasePresenter<V>> extends Fragment implements IBaseView {

    protected View mRootView;
    protected Context mCtx;
    protected P mPresenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        if (mPresenter != null) {
            mPresenter.attach((V) this);
        }
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (mRootView != null) {
            ViewGroup parent = (ViewGroup) mRootView.getParent();
            if (parent != null) {
                parent.removeView(mRootView);
            }
        } else {
            mRootView = createView(inflater, container, savedInstanceState);
        }
        mCtx = mRootView.getContext();
        return mRootView;
    }

    public View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(getContentLayout(), container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        bindView(savedInstanceState);
        isViewInitiated = true;
        prepareFetchData();
    }

    public boolean prepareFetchData() {
        return prepareFetchData(false);
    }

    protected boolean isViewInitiated;
    protected boolean isVisibleToUser;
    protected boolean isDataInitiated;

    public boolean prepareFetchData(boolean forceUpdate) {
        if (isVisibleToUser && isViewInitiated && (!isDataInitiated || forceUpdate)) {
            fetchData();
            isDataInitiated = true;
            return true;
        }
        return false;
    }

    public void fetchData() {
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        this.isVisibleToUser = isVisibleToUser;
        prepareFetchData();
    }

    public View getmRootView() {
        return mRootView;
    }

    protected abstract int getContentLayout();

    protected abstract void bindView(Bundle savedInstanceState);

    @Override
    public void onDestroy() {
        mCtx = null;
        if (mPresenter != null) {
            mPresenter.detachView();
        }
        super.onDestroy();
    }

    protected boolean checkUser(Context context) {
        if (CarefreeApplication.getInstance().getUserInfo() == null) {
            startActivity(new Intent(context, LoginActivity.class));
            return false;
        }
        return true;
    }

    protected void showLoadingDialog() {
        if (!LoadingDialog.getInstance().isShowing())
            LoadingDialog.getInstance().show(mCtx, "", true);
    }

    protected void dismissDialog() {
        LoadingDialog.getInstance().dismissDialog();
    }

    protected P getPresenter() {
        return null;
    }
}
