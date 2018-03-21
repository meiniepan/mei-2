package com.wuyou.merchant.network.apis;

import com.gs.buluo.common.network.BaseResponse;
import com.gs.buluo.common.network.SortedTreeMap;
import com.wuyou.merchant.bean.entity.ContractEntity;
import com.wuyou.merchant.bean.entity.ResponseListEntity;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.QueryMap;

/**
 * Created by solang on 2018/3/20.
 */

public interface CircleApis {

    @Multipart
    @POST("v1/contract")
    Observable<BaseResponse> createContract(
            @Part MultipartBody.Part file,
            @QueryMap SortedTreeMap<String,String> map
    );

    @GET("v1/unions")
    Observable<BaseResponse<ResponseListEntity<ContractEntity>>> getContractList(
            @QueryMap SortedTreeMap<String, String> map);

    @GET("v1/contracts")
    Observable<BaseResponse<ResponseListEntity<ContractEntity>>> getContractMarket(
            @QueryMap SortedTreeMap<String, String> map);
}
