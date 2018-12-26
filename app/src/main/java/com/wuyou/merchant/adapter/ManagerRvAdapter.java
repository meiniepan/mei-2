package com.wuyou.merchant.adapter;

import android.app.Activity;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.ImageView;

import com.gs.buluo.common.widget.recyclerHelper.BaseHolder;
import com.gs.buluo.common.widget.recyclerHelper.BaseQuickAdapter;
import com.wuyou.merchant.R;
import com.wuyou.merchant.bean.entity.WorkerEntity;
import com.wuyou.merchant.util.glide.GlideUtils;

import java.util.List;

/**
 * Created by solang on 2018/2/5.
 */

public class ManagerRvAdapter extends BaseQuickAdapter<WorkerEntity, BaseHolder> {
    private Activity activity;

    public ManagerRvAdapter(Activity activity, int layoutResId, @Nullable List<WorkerEntity> data) {
        super(layoutResId, data);
        this.activity = activity;
    }

    @Override
    protected void convert(BaseHolder helper,   WorkerEntity item) {
        helper.setText(R.id.worker_item_name, item.mobile);
        if (!TextUtils.isEmpty(item.mobile)){
            helper.setText(R.id.worker_item_mobile, String.format(",",item.permissions));
        }
        ImageView imageView = helper.getView(R.id.worker_item_avatar);
        GlideUtils.loadImage(activity,item.avatar,imageView,true);
    }
}
