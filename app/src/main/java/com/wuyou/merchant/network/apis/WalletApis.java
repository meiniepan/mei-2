package com.wuyou.merchant.network.apis;

import com.gs.buluo.common.network.BaseResponse;
import com.gs.buluo.common.network.SortedTreeMap;
import com.wuyou.merchant.bean.entity.FundEntity;
import com.wuyou.merchant.bean.entity.RepayRecordEntity;
import com.wuyou.merchant.bean.entity.ResponseListEntity;
import com.wuyou.merchant.bean.entity.TradeEntity;
import com.wuyou.merchant.bean.entity.TradeItemEntity;
import com.wuyou.merchant.bean.entity.WalletIncomeEntity;
import com.wuyou.merchant.bean.entity.WalletInfoEntity;

import io.reactivex.Observable;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

/**
 * Created by Administrator on 2018\1\26 0026.
 */

public interface WalletApis {
    @GET("funds/{shop_id}")
    Observable<BaseResponse<ResponseListEntity<FundEntity>>> getFundList(
            @Path("shop_id") String shop_id,
            @QueryMap SortedTreeMap<String, String> map);

    @GET("credit")
    Observable<BaseResponse<WalletInfoEntity>> getCredit(
            @QueryMap SortedTreeMap<String, String> map);

    @GET("loan")
    Observable<BaseResponse> loan(
            @QueryMap SortedTreeMap<String, String> map);

    @GET("repayment/records/{shop_id}")
    Observable<BaseResponse<ResponseListEntity<RepayRecordEntity>>> getRepayRecords(
            @Path("shop_id") String shop_id,
            @QueryMap SortedTreeMap<String, String> map);

    @GET("fund/{fund_id}")
    Observable<BaseResponse<FundEntity>> getFundDetail(
            @Path("fund_id") String fund_id,
            @QueryMap SortedTreeMap<String, String> map);

    @GET("order_profits/{shop_id}")
    Observable<BaseResponse<ResponseListEntity<TradeItemEntity>>> getOrderTradeList(
            @Path("shop_id") String shop_id,
            @QueryMap SortedTreeMap<String, String> map);

    @GET("profits/order/{order_id}")
    Observable<BaseResponse<ResponseListEntity<TradeEntity>>> getOrderTradeDetail(@Path("order_id") String orderId,
                                                                                  @QueryMap SortedTreeMap<String, String>map);

    @GET("contract_profits/{shop_id}")
    Observable<BaseResponse<ResponseListEntity<TradeItemEntity>>> getContractTradeList(
            @Path("shop_id") String shop_id,
            @QueryMap SortedTreeMap<String, String> map);

    @GET("profits/contract/{order_id}")
    Observable<BaseResponse<ResponseListEntity<TradeEntity>>> getContractTradeDetail(@Path("order_id") String orderId,
                                                                                  @QueryMap SortedTreeMap<String, String>map);

    @GET("incomes/{shop_id}")
    Observable<BaseResponse<WalletIncomeEntity>> getWalletIncome(
            @Path("shop_id") String shopId, @QueryMap SortedTreeMap<String, String> map);

    @FormUrlEncoded
    @POST("loan")
    Observable<BaseResponse> applyLoan(
            @FieldMap SortedTreeMap<String, String> map);
}
