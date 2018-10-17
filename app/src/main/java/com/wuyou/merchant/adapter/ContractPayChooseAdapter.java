package com.wuyou.merchant.adapter;

import android.widget.RadioButton;

import com.wuyou.merchant.R;
import com.wuyou.merchant.bean.entity.ContractPayEntity;
import com.gs.buluo.common.widget.recyclerHelper.BaseHolder;
import com.gs.buluo.common.widget.recyclerHelper.BaseQuickAdapter;

import java.util.List;

/**
 * Created by DELL on 2018/3/29.
 */

public class ContractPayChooseAdapter extends BaseQuickAdapter<ContractPayEntity, BaseHolder> {
    private int selectedPos =-1;

    public ContractPayChooseAdapter(int res, List<ContractPayEntity> list) {
        super(res, list);
    }

    @Override
    protected void convert(BaseHolder helper, ContractPayEntity item) {
        helper.setText(R.id.item_serve_choose_title,item.type_name);
        RadioButton radioButton = helper.getView(R.id.item_serve_choose_check);
        radioButton.setChecked(helper.getAdapterPosition() == selectedPos);
    }

    public void setSelectedPos(int selectedPos) {
        this.selectedPos = selectedPos;
        notifyDataSetChanged();
    }
}
