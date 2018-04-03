package com.wuyou.merchant.network.apis;

import com.gs.buluo.common.network.BaseResponse;
import com.gs.buluo.common.network.SortedTreeMap;
import com.wuyou.merchant.bean.entity.FundEntity;
import com.wuyou.merchant.bean.entity.RepayRecordEntity;
import com.wuyou.merchant.bean.entity.ResponseListEntity;
import com.wuyou.merchant.bean.entity.TradeEntity;
import com.wuyou.merchant.bean.entity.WalletInfoEntity;

import io.reactivex.Observable;
import retrofit2.http.GET;
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

    @GET("coin/transactions/{shop_id}")
    Observable<BaseResponse<ResponseListEntity<TradeEntity>>> getTradelist(
            @Path("shop_id") String shop_id,
            @QueryMap SortedTreeMap<String, String> map);

    @GET("coin/transaction")
    Observable<BaseResponse<TradeEntity>> getTradeDetail(
            @QueryMap SortedTreeMap<String, String> map);
}
