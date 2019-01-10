package com.wuyou.merchant.adapter;

import android.app.Activity;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.ImageView;

import com.gs.buluo.common.widget.recyclerHelper.BaseHolder;
import com.gs.buluo.common.widget.recyclerHelper.BaseQuickAdapter;
import com.wuyou.merchant.R;
import com.wuyou.merchant.bean.entity.MechanicEntity;

import java.util.List;

/**
 * Created by solang on 2018/2/5.
 */

public class MechanicRvAdapter extends BaseQuickAdapter<MechanicEntity, BaseHolder> {

    public MechanicRvAdapter(int layoutResId, @Nullable List<MechanicEntity> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseHolder helper, MechanicEntity item) {
        helper.setText(R.id.worker_item_name, item.name)
                .setText(R.id.worker_item_flag, item.tag);
        if (!TextUtils.isEmpty(item.mobile)){
            helper.setText(R.id.worker_item_mobile, item.mobile);
        }
        ImageView imageView = helper.getView(R.id.worker_item_avatar);
//        GlideUtils.loadImage(activity,item.avatar,imageView,true);
    }
}
