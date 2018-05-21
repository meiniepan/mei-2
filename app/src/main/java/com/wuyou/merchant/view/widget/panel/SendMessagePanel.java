package com.wuyou.merchant.view.widget.panel;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.gs.buluo.common.utils.ToastUtils;
import com.wuyou.merchant.R;

import butterknife.ButterKnife;
import io.rong.imkit.RongIM;
import io.rong.imlib.IRongCallback;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.Message;
import io.rong.message.TextMessage;

/**
 * Created by Solang on 2018/5/18.
 */
public class SendMessagePanel extends Dialog implements View.OnClickListener {
    Context mCtx;
    private String rcId;
    private EditText etMessage;

    public SendMessagePanel(Context context) {
        super(context, R.style.my_dialog_1);
        mCtx = context;
        initView();
    }

    private void initView() {
        View rootView = LayoutInflater.from(mCtx).inflate(R.layout.panel_send_message, null);
        setContentView(rootView);
        ButterKnife.bind(this);
        Window window = getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = ViewGroup.LayoutParams.MATCH_PARENT;
        params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        params.gravity = Gravity.BOTTOM;
        window.setAttributes(params);
        etMessage = rootView.findViewById(R.id.et_message);
        rootView.findViewById(R.id.tv_send_message).setOnClickListener(this);
        rootView.findViewById(R.id.tv_send_message_cancel).setOnClickListener(this);
        setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                InputMethodManager imm = (InputMethodManager) mCtx.getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null)
                    imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS); //强制隐藏键盘
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_send_message:
                sendMessage();
                break;
            case R.id.tv_send_message_cancel:
                dismiss();
                break;

        }
    }

    private void sendMessage() {
        String message = etMessage.getText().toString();
        if (TextUtils.isEmpty(message)) {
            ToastUtils.ToastMessage(mCtx, "消息不能为空！");
            return;
        }
        TextMessage myTextMessage = TextMessage.obtain(message);

        Message myMessage = Message.obtain(rcId, Conversation.ConversationType.PRIVATE, myTextMessage);

        RongIM.getInstance().sendMessage(myMessage, null, null, new IRongCallback.ISendMessageCallback() {
            @Override
            public void onAttached(Message message) {
                //消息本地数据库存储成功的回调
            }

            @Override
            public void onSuccess(Message message) {
                //消息通过网络发送成功的回调
                ToastUtils.ToastMessage(mCtx, "消息发送成功！");
            }

            @Override
            public void onError(Message message, RongIMClient.ErrorCode errorCode) {
                //消息发送失败的回调
                ToastUtils.ToastMessage(mCtx, "消息发送失败！");
            }
        });
        dismiss();
    }

    public void setData(String rcId) {
        this.rcId = rcId;
    }
}
