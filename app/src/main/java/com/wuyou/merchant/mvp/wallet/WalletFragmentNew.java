package com.wuyou.merchant.mvp.wallet;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gs.buluo.common.widget.StatusLayout;
import com.wuyou.merchant.R;
import com.wuyou.merchant.bean.LinePoint;
import com.wuyou.merchant.view.fragment.BaseFragment;
import com.wuyou.merchant.view.widget.lineChart.Axis;
import com.wuyou.merchant.view.widget.lineChart.AxisValue;
import com.wuyou.merchant.view.widget.lineChart.Line;
import com.wuyou.merchant.view.widget.lineChart.LineChartData;
import com.wuyou.merchant.view.widget.lineChart.LineChartOnValueSelectListener;
import com.wuyou.merchant.view.widget.lineChart.LineChartView;
import com.wuyou.merchant.view.widget.lineChart.PointValue;
import com.wuyou.merchant.view.widget.lineChart.Viewport;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by Solang on 2018/12/26.
 */

public class WalletFragmentNew extends BaseFragment {
    @BindView(R.id.tv_wallet_total_income)
    TextView tvWalletTotalIncome;
    @BindView(R.id.tv_wallet_left_list)
    TextView tvWalletLeftList;
    @BindView(R.id.tv_wallet_right_list)
    TextView tvWalletRightList;
    @BindView(R.id.tv_wallet_deal_quantity)
    TextView tvWalletDealQuantity;
    @BindView(R.id.tv_wallet_deal_amount)
    TextView tvWalletDealAmount;
    @BindView(R.id.chart_bottom)
    LineChartView chartBottom;
    @BindView(R.id.sl_wallet)
    StatusLayout slWallet;
    List<AxisValue> axisValues = new ArrayList<>();
    private static LineChartData lineData;
    int numValues = 5;
    float maxY = 4;//Y坐标最大值
    private final float baseMaxY = 4;//Y坐标的最小范围

    @Override
    protected int getContentLayout() {
        return R.layout.fragment_wallet_new;

    }

    @Override
    protected void bindView(Bundle savedInstanceState) {
        ArrayList<LinePoint> linePoints = new ArrayList<>();
        for (int i = 0; i <5 ; i++) {
            LinePoint linePoint = new LinePoint(i+"",i*10+"");
            linePoints.add(linePoint);
        }
        generateInitialLineData(linePoints);
    }

    private void generateInitialLineData(ArrayList<LinePoint> linePoints) {
        axisValues = new ArrayList<>();
        List<PointValue> values = new ArrayList<>();
        for (int i = 0; i < numValues; ++i) {
            float y = Float.parseFloat(linePoints.get(i).y);
            if (y > maxY) maxY = (float) (y * 1.2);
            values.add(new PointValue(i, Float.parseFloat(linePoints.get(i).y)));
            axisValues.add(new AxisValue(i).setLabel(linePoints.get(i).x));
        }

        Line line = new Line(values);
        line.setColor(0xFF3285FF).setCubic(true);
        List<Line> lines = new ArrayList<>();
        lines.add(line);
        lineData = new LineChartData(lines);
        lineData.setAxisXBottom(new Axis(axisValues));
        lineData.setAxisYLeft(new Axis().setHasLines(true).setMaxLabelChars(3));

        chartBottom.setLineChartData(lineData);
        // For build-up animation you have to disable viewport recalculation.
        chartBottom.setViewportCalculationEnabled(false);
        // And set initial max viewport and current viewport- remember to set viewports after data.
        Viewport v = new Viewport(0, maxY, 4, 0);
        chartBottom.setMaximumViewport(v);
        chartBottom.setCurrentViewport(v);
        chartBottom.setZoomEnabled(false);
    }

    @Override
    public void showError(String message, int res) {

    }

    @OnClick({R.id.ll_wallet_left_list, R.id.ll_wallet_right_list})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_wallet_left_list:
                break;
            case R.id.ll_wallet_right_list:
                break;
        }
    }
}
