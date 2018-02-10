package com.wuyou.merchant.adapter;

import android.app.Activity;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.gs.buluo.common.utils.TribeDateUtils;
import com.wuyou.merchant.R;
import com.wuyou.merchant.bean.entity.OrderInfoEntity;
import com.wuyou.merchant.bean.entity.WorkerEntity;
import com.wuyou.merchant.util.CommonUtil;
import com.wuyou.merchant.view.widget.recyclerHelper.BaseHolder;
import com.wuyou.merchant.view.widget.recyclerHelper.BaseQuickAdapter;

import java.util.Date;
import java.util.List;

/**
 * Created by solang on 2018/2/5.
 */

public class WorkersRvAdapter extends BaseQuickAdapter<WorkerEntity, BaseHolder> {
    private Activity activity;

    public WorkersRvAdapter(Activity activity, int layoutResId, @Nullable List<WorkerEntity> data) {
        super(layoutResId, data);
        this.activity = activity;
    }

    @Override
    protected void convert(BaseHolder helper, WorkerEntity item) {
        helper.setText(R.id.tv_name, item.name)
                .setText(R.id.tv_distance, item.distance);
        ImageView imageView = helper.getView(R.id.avatar);
        CommonUtil.GlideCircleLoad(activity,item.image,imageView);
    }
}
