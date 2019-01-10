package com.wuyou.merchant.adapter;

import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.ImageView;

import com.gs.buluo.common.widget.recyclerHelper.BaseHolder;
import com.gs.buluo.common.widget.recyclerHelper.BaseQuickAdapter;
import com.wuyou.merchant.R;
import com.wuyou.merchant.bean.entity.MechanicEntity;
import com.wuyou.merchant.bean.entity.StaffEntity;

import java.util.List;

/**
 * Created by solang on 2018/2/5.
 */

public class StaffRvAdapter extends BaseQuickAdapter<StaffEntity, BaseHolder> {

    public StaffRvAdapter(int layoutResId, @Nullable List<StaffEntity> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseHolder helper, StaffEntity item) {
        String sPermission = "权限: ";
        if (item.permissionList != null && item.permissionList.length > 0) {
            for (int i = 0; i < item.permissionList.length; i++) {
                sPermission = sPermission + item.permissionList[i];
            }
        }
        helper.setText(R.id.worker_item_mobile, sPermission);
        if (!TextUtils.isEmpty(item.mobile)) {
            helper.setText(R.id.worker_item_name, item.mobile);
        }
        ImageView imageView = helper.getView(R.id.worker_item_avatar);
//        GlideUtils.loadImage(activity,item.avatar,imageView,true);
    }
}
