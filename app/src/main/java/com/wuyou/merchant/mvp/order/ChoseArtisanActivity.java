package com.wuyou.merchant.mvp.order;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.gs.buluo.common.network.ApiException;
import com.gs.buluo.common.network.BaseResponse;
import com.gs.buluo.common.network.BaseSubscriber;
import com.gs.buluo.common.network.QueryMapBuilder;
import com.gs.buluo.common.utils.ToastUtils;
import com.gs.buluo.common.widget.CustomAlertDialog;
import com.gs.buluo.common.widget.StatusLayout;
import com.wuyou.merchant.CarefreeApplication;
import com.wuyou.merchant.Constant;
import com.wuyou.merchant.R;
import com.wuyou.merchant.adapter.WorkersRvAdapter;
import com.wuyou.merchant.bean.entity.WorkerEntity;
import com.wuyou.merchant.bean.entity.WorkerListEntity;
import com.wuyou.merchant.network.CarefreeRetrofit;
import com.wuyou.merchant.network.apis.OrderApis;
import com.wuyou.merchant.view.activity.BaseActivity;
import com.wuyou.merchant.view.activity.MainActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by solang on 2018/1/31.
 */

public class ChoseArtisanActivity extends BaseActivity {
    @BindView(R.id.sl_list_layout)
    StatusLayout statusLayout;
    @BindView(R.id.rv_orders)
    RecyclerView recyclerView;
    List<WorkerEntity> data = new ArrayList();
    WorkersRvAdapter adapter;
    String orderId;

    @Override
    protected int getContentLayout() {
        return R.layout.activity_chose_artisan;
    }

    @Override
    protected void bindView(Bundle savedInstanceState) {
        orderId = getIntent().getStringExtra(Constant.ORDER_ID);
        adapter = new WorkersRvAdapter( this,R.layout.item_chose_artisan, data);
        adapter.setOnItemClickListener((adapter1, view, position) -> {
            showAlert(adapter.getItem(position).worker_name, adapter.getItem(position).worker_id);
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(getCtx()));
        recyclerView.setAdapter(adapter);
        getData();
    }

    private void getData() {
        statusLayout.showProgressView();
        CarefreeRetrofit.getInstance().createApi(OrderApis.class)
                .getWorkersInfo(QueryMapBuilder.getIns().put("shop_id", CarefreeApplication.getInstance().getUserInfo().getUid())
                        .put("start_id", "0")
                        .put("flag", "1")
                        .put("size", "10")
                        .buildGet())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<BaseResponse<WorkerListEntity>>() {
                    @Override
                    public void onSuccess(BaseResponse<WorkerListEntity> response) {
                        adapter.setNewData(response.data.list);
                        statusLayout.showContentView();
                        if (adapter.getData().size() == 0) {
                            statusLayout.showEmptyView("没有名单");
                        }
                    }

                    @Override
                    protected void onFail(ApiException e) {
                        statusLayout.showErrorView(e.getDisplayMessage());
                    }
                });
    }

    private void showAlert(String name, String serverId) {
        CustomAlertDialog.Builder builder = new CustomAlertDialog.Builder(getCtx());
        builder.setTitle("是否分单给服务者").setMessage(name);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                CarefreeRetrofit.getInstance().createApi(OrderApis.class)
                        .dispatchOrder(
                                QueryMapBuilder.getIns().put("dispatcher_id", CarefreeApplication.getInstance().getUserInfo().getUid())
                                        .put("worker_id", serverId)
                                        .put("type", "1")
                                        .put("order_id", orderId)
                                        .buildPost())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new BaseSubscriber<BaseResponse>() {
                            @Override
                            public void onSuccess(BaseResponse response) {
                                ToastUtils.ToastMessage(getCtx(), "完成");
                                Intent intent = new Intent( ChoseArtisanActivity.this,MainActivity.class);
                                startActivity(intent);
                            }

                        });

            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.create().show();
    }

    @Override
    public void showError(String message, int res) {

    }
}
