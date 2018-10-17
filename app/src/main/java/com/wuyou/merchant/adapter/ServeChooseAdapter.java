package com.wuyou.merchant.adapter;

import android.widget.RadioButton;

import com.wuyou.merchant.R;
import com.wuyou.merchant.bean.entity.ServiceEntity;
import com.gs.buluo.common.widget.recyclerHelper.BaseHolder;
import com.gs.buluo.common.widget.recyclerHelper.BaseQuickAdapter;

import java.util.List;

/**
 * Created by DELL on 2018/3/29.
 */

public class ServeChooseAdapter extends BaseQuickAdapter<ServiceEntity, BaseHolder> {
    public ServeChooseAdapter(int res, List<ServiceEntity> data) {
        super(res, data);
    }

    @Override
    protected void convert(BaseHolder helper, ServiceEntity item) {
        helper.setText(R.id.item_serve_choose_title, item.service_name);
        RadioButton radioButton = helper.getView(R.id.item_serve_choose_check);
        radioButton.setChecked(helper.getAdapterPosition() == selectedPos);
    }

    private int selectedPos = -1;

    public void setSelectedPos(int pos) {
        selectedPos = pos;
        notifyDataSetChanged();
    }
}
