package com.wuyou.merchant.mvp.login;


import com.wuyou.merchant.mvp.BasePresenter;
import com.wuyou.merchant.mvp.IBaseView;

/**
 * Created by Administrator on 2018\1\29 0029.
 */

public interface LoginContract {
    interface View extends IBaseView {
        void loginSuccess();
        void getVerifySuccess();
    }

    abstract class Presenter extends BasePresenter<View> {
        abstract void doLogin(String phone,String captcha);
        abstract void getVerifyCode(String phone);
    }
}
