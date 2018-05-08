//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.wuyou.merchant.view.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.gs.buluo.common.R.id;
import com.gs.buluo.common.R.layout;
import com.gs.buluo.common.R.styleable;
import com.gs.buluo.common.widget.ViewAnimProvider;
import com.wuyou.merchant.R;

public class StatusLayout_gone extends FrameLayout {
    private View contentView;
    private View emptyView;
    private View emptyContentView;
    private View errorView;
    private View errorContentView;
    private View loginView;
    private View loginContentView;
    private View progressView;
    private View progressContentView;
    private TextView emptyTextView;
    private TextView emptyActView;
    private TextView errorTextView;
    private TextView errorActView;
    private TextView loginTextView;
    private TextView loginActView;
    private TextView progressTextView;
    private ImageView errorImageView;
    private ImageView emptyImageView;
    private ImageView loginImageView;
    private ProgressBar progressBar;
    private View currentShowingView;
    private boolean shouldPlayAnim;
    private Animation hideAnimation;
    private Animation showAnimation;

    public StatusLayout_gone(@NonNull Context context) {
        this(context, (AttributeSet)null);
    }

    public StatusLayout_gone(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public StatusLayout_gone(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.shouldPlayAnim = true;
        this.init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        this.parseAttrs(context, attrs);
        this.emptyView.setVisibility(GONE);
        this.errorView.setVisibility(GONE);
        this.progressView.setVisibility(GONE);
        this.loginView.setVisibility(GONE);
        this.currentShowingView = this.contentView;
    }

    private void parseAttrs(Context context, AttributeSet attrs) {
        LayoutInflater inflater = LayoutInflater.from(context);
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, styleable.StateLayout, 0, 0);

        int progressViewId;
        Drawable errorDrawable;
        Drawable emptyDrawable;
        Drawable loginDrawable;
        try {
            errorDrawable = a.getDrawable(styleable.StateLayout_errorDrawable);
            emptyDrawable = a.getDrawable(styleable.StateLayout_emptyDrawable);
            loginDrawable = a.getDrawable(styleable.StateLayout_loginDrawable);
            progressViewId = a.getResourceId(styleable.StateLayout_progressView, -1);
        } finally {
            a.recycle();
        }

        if(progressViewId != -1) {
            this.progressView = inflater.inflate(progressViewId, this, false);
        } else {
            this.progressView = inflater.inflate(layout.status_view_progress, this, false);
            this.progressBar = (ProgressBar)this.progressView.findViewById(id.progress_wheel);
            this.progressTextView = (TextView)this.progressView.findViewById(id.progressTextView);
            this.progressContentView = this.progressView.findViewById(id.progress_content);
        }

        this.addView(this.progressView);
        this.errorView = inflater.inflate(R.layout.status_view_error, this, false);
        this.errorContentView = this.errorView.findViewById(id.error_content);
        this.errorTextView = (TextView)this.errorView.findViewById(id.errorTextView);
        this.errorActView = (TextView)this.errorView.findViewById(id.error_click_view);
        this.errorImageView = (ImageView)this.errorView.findViewById(id.errorImageView);
        if(errorDrawable != null) {
            this.errorImageView.setImageDrawable(errorDrawable);
        }

        this.addView(this.errorView);
        this.emptyView = inflater.inflate(R.layout.status_view_empty_gone, this, false);
        this.emptyContentView = this.emptyView.findViewById(id.empty_content);
        this.emptyTextView = (TextView)this.emptyView.findViewById(id.emptyTextView);
        this.emptyImageView = (ImageView)this.emptyView.findViewById(id.emptyImageView);
        this.emptyActView = (TextView)this.emptyView.findViewById(id.empty_click_view);
        if(emptyDrawable != null) {
            this.emptyImageView.setImageDrawable(emptyDrawable);
        }

        this.addView(this.emptyView);
        this.loginView = inflater.inflate(layout.status_view_login, this, false);
        this.loginContentView = this.loginView.findViewById(id.login_content);
        this.loginTextView = (TextView)this.loginView.findViewById(id.loginTextView);
        this.loginImageView = (ImageView)this.loginView.findViewById(id.loginImageView);
        this.loginActView = (TextView)this.loginView.findViewById(id.login_click_view);
        if(loginDrawable != null) {
            this.loginImageView.setImageDrawable(loginDrawable);
        }

        this.addView(this.loginView);
    }

    private void checkIsContentView(View view) {
        if(this.contentView == null && view != this.errorView && view != this.progressView && view != this.emptyView && view != this.loginView) {
            this.contentView = view;
            this.currentShowingView = this.contentView;
        }

    }

    public ImageView getErrorImageView() {
        return this.errorImageView;
    }

    public ImageView getEmptyImageView() {
        return this.emptyImageView;
    }

    public void setViewSwitchAnimProvider(ViewAnimProvider viewSwitchAnimProvider) {
        if(viewSwitchAnimProvider != null) {
            this.showAnimation = viewSwitchAnimProvider.showAnimation();
            this.hideAnimation = viewSwitchAnimProvider.hideAnimation();
        }

    }

    public boolean isShouldPlayAnim() {
        return this.shouldPlayAnim;
    }

    public void setShouldPlayAnim(boolean shouldPlayAnim) {
        this.shouldPlayAnim = shouldPlayAnim;
    }

    public Animation getShowAnimation() {
        return this.showAnimation;
    }

    public void setShowAnimation(Animation showAnimation) {
        this.showAnimation = showAnimation;
    }

    public Animation getHideAnimation() {
        return this.hideAnimation;
    }

    public void setHideAnimation(Animation hideAnimation) {
        this.hideAnimation = hideAnimation;
    }

    private void switchWithAnimation(View toBeShown) {
        final View toBeHided = this.currentShowingView;
        if(toBeHided != toBeShown) {
            if(this.shouldPlayAnim) {
                if(toBeHided != null) {
                    if(this.hideAnimation != null) {
                        this.hideAnimation.setAnimationListener(new AnimationListener() {
                            public void onAnimationStart(Animation animation) {
                            }

                            public void onAnimationEnd(Animation animation) {
                                toBeHided.setVisibility(GONE);
                            }

                            public void onAnimationRepeat(Animation animation) {
                            }
                        });
                        this.hideAnimation.setFillAfter(false);
                        toBeHided.startAnimation(this.hideAnimation);
                    } else {
                        toBeHided.setVisibility(GONE);
                    }
                }

                if(toBeShown != null) {
                    if(toBeShown.getVisibility() != VISIBLE) {
                        toBeShown.setVisibility(VISIBLE);
                    }

                    this.currentShowingView = toBeShown;
                    if(this.showAnimation != null) {
                        this.showAnimation.setFillAfter(false);
                        toBeShown.startAnimation(this.showAnimation);
                    }
                }
            } else {
                if(toBeHided != null) {
                    toBeHided.setVisibility(GONE);
                }

                if(toBeShown != null) {
                    this.currentShowingView = toBeShown;
                    toBeShown.setVisibility(VISIBLE);
                }
            }

        }
    }

    public void setEmptyContentViewMargin(int left, int top, int right, int bottom) {
        ((LayoutParams)this.emptyImageView.getLayoutParams()).setMargins(left, top, right, bottom);
    }

    public void setLoginContentViewMargin(int left, int top, int right, int bottom) {
        ((LayoutParams)this.loginImageView.getLayoutParams()).setMargins(left, top, right, bottom);
    }

    public void setErrorContentViewMargin(int left, int top, int right, int bottom) {
        ((LayoutParams)this.errorImageView.getLayoutParams()).setMargins(left, top, right, bottom);
    }

    public void setProgressContentViewMargin(int left, int top, int right, int bottom) {
        if(this.progressBar != null) {
            ((LayoutParams)this.progressBar.getLayoutParams()).setMargins(left, top, right, bottom);
        }

    }

    public void setInfoContentViewMargin(int left, int top, int right, int bottom) {
        this.setEmptyContentViewMargin(left, top, right, bottom);
        this.setErrorContentViewMargin(left, top, right, bottom);
        this.setProgressContentViewMargin(left, top, right, bottom);
    }

    public void showContentView() {
        this.switchWithAnimation(this.contentView);
    }

    public void showEmptyView() {
        this.showEmptyView((String)null);
    }

    public void showEmptyView(String msg) {
        this.onHideContentView();
        if(!TextUtils.isEmpty(msg)) {
            this.emptyTextView.setText(msg);
        }

        this.switchWithAnimation(this.emptyView);
    }

    public void showErrorView() {
        this.showErrorView((String)null);
    }

    public void showErrorView(String msg) {
        this.onHideContentView();
        if(msg != null) {
            this.errorTextView.setText(msg);
        }

        this.switchWithAnimation(this.errorView);
    }

    public void showLoginView(String msg) {
        this.onHideContentView();
        if(msg != null) {
            this.loginTextView.setText(msg);
        }

        this.switchWithAnimation(this.loginView);
    }

    public void showLoginView() {
        this.showLoginView((String)null);
    }

    public void showProgressView() {
        this.showProgressView((String)null);
    }

    public void showProgressView(String msg) {
        this.onHideContentView();
        if(msg != null) {
            this.progressTextView.setText(msg);
        }

        this.switchWithAnimation(this.progressView);
    }

    public void setLoginAction(OnClickListener onLoginButtonClickListener) {
        this.findViewById(id.login_click_view).setVisibility(VISIBLE);
        this.loginActView.setOnClickListener(onLoginButtonClickListener);
    }

    public void setErrorAction(OnClickListener onErrorButtonClickListener) {
        this.findViewById(id.error_click_view).setVisibility(VISIBLE);
        this.errorImageView.setOnClickListener(onErrorButtonClickListener);
    }

    public void setEmptyAction(OnClickListener onEmptyButtonClickListener) {
        this.findViewById(id.empty_click_view).setVisibility(VISIBLE);
        this.emptyActView.setOnClickListener(onEmptyButtonClickListener);
    }

    public void setErrorAndEmptyAction(OnClickListener errorAndEmptyAction) {
        this.findViewById(id.error_click_view).setVisibility(VISIBLE);
        this.findViewById(id.empty_click_view).setVisibility(VISIBLE);
        this.errorView.setOnClickListener(errorAndEmptyAction);
        this.emptyView.setOnClickListener(errorAndEmptyAction);
    }

    public void setErrorActionBackgroud(int resId) {
        this.errorActView.setBackgroundResource(resId);
    }

    public void setEmptyActionBackground(int resId) {
        this.emptyActView.setBackgroundResource(resId);
    }

    public TextView getEmptyActView() {
        return this.emptyActView;
    }

    public TextView getErrorActView() {
        return this.errorActView;
    }

    public TextView getLoginActView() {
        return this.loginActView;
    }

    protected void onHideContentView() {
    }

    public void addView(View child) {
        this.checkIsContentView(child);
        super.addView(child);
    }

    public void addView(View child, int index) {
        this.checkIsContentView(child);
        super.addView(child, index);
    }

    public void addView(View child, int index, android.view.ViewGroup.LayoutParams params) {
        this.checkIsContentView(child);
        super.addView(child, index, params);
    }

    public void addView(View child, android.view.ViewGroup.LayoutParams params) {
        this.checkIsContentView(child);
        super.addView(child, params);
    }

    public void addView(View child, int width, int height) {
        this.checkIsContentView(child);
        super.addView(child, width, height);
    }

    protected boolean addViewInLayout(View child, int index, android.view.ViewGroup.LayoutParams params) {
        this.checkIsContentView(child);
        return super.addViewInLayout(child, index, params);
    }

    protected boolean addViewInLayout(View child, int index, android.view.ViewGroup.LayoutParams params, boolean preventRequestLayout) {
        this.checkIsContentView(child);
        return super.addViewInLayout(child, index, params, preventRequestLayout);
    }
}
