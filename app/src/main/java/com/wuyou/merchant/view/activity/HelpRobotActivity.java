package com.wuyou.merchant.view.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.webkit.DownloadListener;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.gnway.bangwoba.activity.Bw8WebView;
import com.gnway.bangwoba.activity.ChatActivity;
import com.gnway.bangwoba.global.Variable;
import com.wuyou.merchant.CarefreeDaoSession;
import com.wuyou.merchant.Constant;
import com.wuyou.merchant.R;
import com.wuyou.merchant.service.HelpChatService;

/**
 * Created by DELL on 2018/3/22.
 */

public class HelpRobotActivity extends BaseActivity implements View.OnClickListener {
    private WebView mWebView;
    private String url = "http://www.bangwo8.com/client/smartRobot_phone_v1.php?VendorID=" + Variable.AgentId + "&companyName=公司名称(金万维)&uname=" + Variable.loginUser + "&from=sdk";

    @Override
    protected void bindView(Bundle savedInstanceState) {
        mWebView = findViewById(R.id.robot_web_view);
        findViewById(R.id.back).setOnClickListener(this);
        settingWebView();
        mWebView.loadUrl(url);
        initHelpService();
        startService();
    }

    private void initHelpService() {
        Variable.AgentId = Constant.HELP_SERVE_AGENT_ID;
        if (CarefreeDaoSession.getInstance().getUserInfo() != null) {
            Variable.loginUser = "u4_" + CarefreeDaoSession.getInstance().getUserInfo().getPhone();
        } else {
            Variable.loginUser = "u4_" + Settings.System.getString(getCtx().getContentResolver(), Settings.Secure.ANDROID_ID).substring(0, 11);
        }
    }

    private void startService() {
        Intent serviceIntent = new Intent(getCtx(), HelpChatService.class);
        startService(serviceIntent);
    }


    @Override
    protected int getContentLayout() {
        return R.layout.activity_help_robot;
    }

    @SuppressLint("NewApi")
    private void settingWebView() {
        try {
            //支持获取手势焦点，输入用户名、密码或其他
            mWebView.requestFocusFromTouch();
            mWebView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
            mWebView.setScrollbarFadingEnabled(false);
            final WebSettings settings = mWebView.getSettings();
            settings.setJavaScriptEnabled(true);  //支持js
            mWebView.removeJavascriptInterface("searchBoxJavaBridge_");
            mWebView.removeJavascriptInterface("accessibilityTraversal");
            mWebView.removeJavascriptInterface("accessibility");
            //  设置自适应屏幕，两者合用
            settings.setUseWideViewPort(true); //将图片调整到适合webview的大小
            settings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小
            settings.supportMultipleWindows();  //多窗口
            settings.setAllowFileAccess(true);  //设置可以访问文件
            settings.setNeedInitialFocus(true); //当webview调用requestFocus时为webview设置节点
            settings.setJavaScriptCanOpenWindowsAutomatically(true); //支持通过JS打开新窗口
            settings.setSavePassword(false);
            //设置编码格式
            settings.setDefaultTextEncodingName("UTF-8");
            // 关于是否缩放
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                settings.setDisplayZoomControls(false);
            }
            /**
             *  Webview在安卓5.0之前默认允许其加载混合网络协议内容
             *  在安卓5.0之后，默认不允许加载http与https混合内容，需要设置webview允许其加载混合网络协议内容
             */
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                settings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);

            }
            settings.setLoadsImagesAutomatically(true);  //支持自动加载图片

            settings.setDomStorageEnabled(true); //开启DOM Storage

            mWebView.setDownloadListener(new DownloadListener() {
                @Override
                public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {
                    // 监听下载功能，当用户点击下载链接的时候，直接调用系统的浏览器来下载
                    Uri uri = Uri.parse(url);
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(intent);
                    finish();
                }
            });

            mWebView.setWebViewClient(new WebViewClient() {
                @Override
                public void onPageFinished(WebView view, String url) {
                    super.onPageFinished(view, url);
                }

                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    if (url.contains("http://www.bangwo8.com/osp2016/chat/chat_phone_v1.php?")) {
                        Intent intent = new Intent();
                        intent.setClass(HelpRobotActivity.this, ChatActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Intent intent = new Intent();
                        intent.putExtra("url", url);
                        intent.setClass(HelpRobotActivity.this, Bw8WebView.class);
                        startActivity(intent);
                    }
                    return true;
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.back) {
            if (mWebView.canGoBack()) {
                mWebView.goBack();
            } else {
                finish();
            }
        }
    }

    @Override
    public void onBackPressed() {
        if (mWebView.canGoBack()) {
            mWebView.goBack();
        } else {
            super.onBackPressed();
        }
    }
}
