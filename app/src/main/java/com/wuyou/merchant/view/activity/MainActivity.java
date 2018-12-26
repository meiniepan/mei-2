package com.wuyou.merchant.view.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;

import com.gs.buluo.common.network.TokenEvent;
import com.gs.buluo.common.utils.ToastUtils;
import com.tencent.bugly.crashreport.CrashReport;
import com.wuyou.merchant.CarefreeApplication;
import com.wuyou.merchant.CarefreeDaoSession;
import com.wuyou.merchant.Constant;
import com.wuyou.merchant.R;
import com.wuyou.merchant.adapter.MainPagerAdapter;
import com.wuyou.merchant.mvp.circle.CircleFragment;
import com.wuyou.merchant.mvp.login.LoginActivity;
import com.wuyou.merchant.mvp.order.MyOrderFragment;
import com.wuyou.merchant.mvp.store.StoreFragment;
import com.wuyou.merchant.mvp.wallet.WalletFragment;
import com.wuyou.merchant.mvp.wallet.WalletFragmentNew;
import com.wuyou.merchant.view.widget.NoScrollViewPager;
import com.yinglan.alphatabs.AlphaTabsIndicator;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.rong.imkit.RongIM;
import io.rong.imkit.fragment.ConversationListFragment;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;
import me.shaohui.shareutil.ShareConfig;
import me.shaohui.shareutil.ShareManager;

public class MainActivity extends BaseActivity {
    @BindView(R.id.main_tab)
    AlphaTabsIndicator bottomView;
    @BindView(R.id.main_pager)
    NoScrollViewPager viewPager;
    List<Fragment> fragments = new ArrayList<>();
    MyOrderFragment orderFragment = new MyOrderFragment();
    CircleFragment circleFragment = new CircleFragment();
//    WalletFragment walletFragment = new WalletFragment();
    WalletFragmentNew walletFragment = new WalletFragmentNew();
    private long mkeyTime = 0;

    @Override
    protected int getContentLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void bindView(Bundle savedInstanceState) {
        CarefreeApplication.getInstance().ManualCheckOnForceUpdate();
        if (!EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().register(this);
        }
        initIM();
        fragments.add(orderFragment);
        fragments.add(circleFragment);
        fragments.add(walletFragment);
//        fragments.add(getMessageFragment());
        fragments.add(new StoreFragment());
        viewPager.setAdapter(new MainPagerAdapter(getSupportFragmentManager(), fragments));
        viewPager.setOffscreenPageLimit(3);
        bottomView.setViewPager(viewPager);
        bottomView.setTabCurrenItem(0);
        ShareConfig config = ShareConfig.instance().wxId(Constant.WX_ID).wxSecret(Constant.WX_SECRET);
        ShareManager.init(config);
        CrashReport.putUserData(getApplicationContext(), "userkey", CarefreeDaoSession.getInstance().getUserInfo() == null ? "unLogin" : CarefreeDaoSession.getInstance().getUserId());
    }

    private Fragment getMessageFragment() {
        ConversationListFragment fragment = new ConversationListFragment();
        Uri uri = Uri.parse("rong://" + getCtx().getApplicationInfo().packageName).buildUpon()
                .appendPath("conversationlist")
                .appendQueryParameter(Conversation.ConversationType.PRIVATE.getName(), "false") //设置私聊会话非聚合显示
                .appendQueryParameter(Conversation.ConversationType.GROUP.getName(), "true")//设置群组会话聚合显示
                .appendQueryParameter(Conversation.ConversationType.DISCUSSION.getName(), "false")//设置讨论组会话非聚合显示
                .appendQueryParameter(Conversation.ConversationType.SYSTEM.getName(), "false")//设置系统会话非聚合显示
                .build();
        fragment.setUri(uri);
        return fragment;
    }

    private void initIM() {
        if (getApplicationInfo().packageName.equals(CarefreeApplication.getCurProcessName(getApplicationContext())) ||
                "io.rong.push".equals(CarefreeApplication.getCurProcessName(getApplicationContext()))) {
            RongIM.init(this);
            //连接服务器

            connect(CarefreeDaoSession.getInstance().getUserInfo().getRc_token());
            //设置用户信息
            RongIM.setUserInfoProvider(new RongIM.UserInfoProvider() {
                @Override
                public io.rong.imlib.model.UserInfo getUserInfo(String s) {
                    return new io.rong.imlib.model.UserInfo(s, "haha", null);
                }
            }, true);
            //全局推送

        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if ((System.currentTimeMillis() - mkeyTime) > 2000) {
                mkeyTime = System.currentTimeMillis();
                ToastUtils.ToastMessage(getCtx(), "再按一次退出");
            } else {
                finish();
                System.exit(0);
            }
            return false;
        }

        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        if (Constant.MAIN_ACTIVITY_FROM_CREATE_CONTRACT.equals(intent.getStringExtra(Constant.MAIN_ACTIVITY_FROM_WHERE))) {
            circleFragment.refreshCreatedList();
        } else if (Constant.MAIN_ACTIVITY_FROM_APPLY_FUND.equals(intent.getStringExtra(Constant.MAIN_ACTIVITY_FROM_WHERE))) {
//            walletFragment.refreshFundList();
        }else if (Constant.MAIN_ACTIVITY_FROM_VOUCHER.equals(intent.getStringExtra(Constant.MAIN_ACTIVITY_FROM_WHERE))) {
            orderFragment.loadDataAll();
        } else {
            orderFragment.loadDatas();
        }
    }

    private void connect(String token) {

        if (getApplicationInfo().packageName.equals(CarefreeApplication.getCurProcessName(getApplicationContext()))) {
            RongIM.connect(token, new RongIMClient.ConnectCallback() {

                /**
                 * Token 错误。可以从下面两点检查 1.  Token 是否过期，如果过期您需要向 App Server 重新请求一个新的 Token
                 *                  2.  token 对应的 appKey 和工程里设置的 appKey 是否一致
                 */
                @Override
                public void onTokenIncorrect() {
                }

                /**
                 * 连接融云成功
                 * @param userid 当前 token 对应的用户 id
                 */
                @Override
                public void onSuccess(String userid) {
                    Log.d("LoginActivity", "--onSuccess" + userid);
                }

                /**
                 * 连接融云失败
                 * @param errorCode 错误码，可到官网 查看错误码对应的注释
                 */
                @Override
                public void onError(RongIMClient.ErrorCode errorCode) {
                    Log.e("err", errorCode + "");
                }
            });
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onTokenExpired(TokenEvent event) {
        CarefreeDaoSession.getInstance().clearUserInfo();
        Intent intent = new Intent(getCtx(), LoginActivity.class);
        startActivity(intent);
        finish();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
