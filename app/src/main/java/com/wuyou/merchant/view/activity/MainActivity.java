package com.wuyou.merchant.view.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import com.gs.buluo.common.utils.ToastUtils;
import com.wuyou.merchant.CarefreeApplication;
import com.wuyou.merchant.R;
import com.wuyou.merchant.adapter.MainPagerAdapter;
import com.wuyou.merchant.mvp.circle.CircleFragment;
import com.wuyou.merchant.mvp.order.MyOrderFragment;
import com.wuyou.merchant.mvp.store.StoreFragment;
import com.wuyou.merchant.mvp.wallet.WalletFragment;
import com.wuyou.merchant.view.widget.NoScrollViewPager;
import com.yinglan.alphatabs.AlphaTabsIndicator;
import com.yinglan.alphatabs.OnTabChangedListner;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.rong.imkit.RongIM;
import io.rong.imkit.fragment.ConversationListFragment;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;

public class MainActivity extends BaseActivity implements OnTabChangedListner {
    @BindView(R.id.main_tab)
    AlphaTabsIndicator bottomView;
    @BindView(R.id.main_pager)
    NoScrollViewPager viewPager;
    @BindView(R.id.main_title)
    TextView mainTitle;
    @BindView(R.id.main_title_area)
    View titleView;
    List<Fragment> fragments = new ArrayList<>();
    MyOrderFragment orderFragment = new MyOrderFragment();
    private long mkeyTime = 0;

    @Override
    protected int getContentLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void bindView(Bundle savedInstanceState) {
        initIM();
        fragments.add(orderFragment);
        fragments.add(new CircleFragment());
        fragments.add(new WalletFragment());
//        fragments.add(getMessageFragment());
        fragments.add(new StoreFragment());
        viewPager.setAdapter(new MainPagerAdapter(getSupportFragmentManager(), fragments));
        viewPager.setOffscreenPageLimit(2);
        bottomView.setViewPager(viewPager);
        bottomView.setOnTabChangedListner(this);
        bottomView.setTabCurrenItem(0);

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

            connect(CarefreeApplication.getInstance().getUserInfo().getRc_token());
            //设置用户信息
            RongIM.setUserInfoProvider(new RongIM.UserInfoProvider() {
                @Override
                public io.rong.imlib.model.UserInfo getUserInfo(String s) {
                    return new io.rong.imlib.model.UserInfo(s,"haha",null);
                }
            },true);
            //全局推送

        }
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if ((System.currentTimeMillis() - mkeyTime) > 2000) {
                mkeyTime = System.currentTimeMillis();
                ToastUtils.ToastMessage(getCtx(),"再按一次退出");
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
        orderFragment.loadDatas();
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

    @Override
    public void onTabSelected(int tabNum) {
        switch (tabNum) {
            case 0:
                mainTitle.setText(R.string.main_deal_order);
                titleView.setVisibility(View.VISIBLE);
                break;
            case 1:
                mainTitle.setText(R.string.main_circle);
                titleView.setVisibility(View.VISIBLE);
                break;
            case 2:
                mainTitle.setText(R.string.main_wallet);
                titleView.setVisibility(View.VISIBLE);
                break;
            case 3:
                titleView.setVisibility(View.GONE);
                break;

        }
    }
}
