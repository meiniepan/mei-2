package com.wuyou.merchant.mvp.account;

import com.wuyou.merchant.mvp.BasePresenter;
import com.wuyou.merchant.mvp.IBaseView;

/**
 * Created by DELL on 2018/9/3.
 */

public interface WalletContract {

    interface View extends IBaseView {
        void createAccountSuccess();

        void checkCaptchaSuccess();
    }

    abstract class Presenter extends BasePresenter<View> {
        public abstract void createAccount(String name, String phone);

        abstract void getWalletInfo();

        abstract void getCaptcha(String type, String phone);

        abstract void checkCaptcha(String type, String phone, String captcha);
    }
}
