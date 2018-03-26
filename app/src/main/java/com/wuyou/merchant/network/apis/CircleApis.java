package com.wuyou.merchant.network.apis;

import com.gs.buluo.common.network.BaseResponse;
import com.gs.buluo.common.network.SortedTreeMap;
import com.wuyou.merchant.bean.entity.ContractEntity;
import com.wuyou.merchant.bean.entity.ResponseListEntity;
import com.wuyou.merchant.bean.entity.ServiceEntity;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

/**
 * Created by solang on 2018/3/20.
 */

public interface CircleApis {

    @Multipart
    @POST("contract")
    Observable<BaseResponse> createContract(
            @Part MultipartBody.Part file,
            @QueryMap SortedTreeMap<String, String> map
    );

    @GET("unions")
    Observable<BaseResponse<ResponseListEntity<ContractEntity>>> getContractList(
            @QueryMap SortedTreeMap<String, String> map);

    @GET("contracts")
    Observable<BaseResponse<ResponseListEntity<ContractEntity>>> getContractMarket(
            @QueryMap SortedTreeMap<String, String> map);

    @GET("contract_services")
    Observable<BaseResponse<ResponseListEntity<ServiceEntity>>> getContractService(
            @QueryMap SortedTreeMap<String, String> map);

    @GET("contract/{contract_id}")
    Observable<BaseResponse<ContractEntity>> getContractDetail(
            @Path("contract_id") String contract_id,
            @QueryMap SortedTreeMap<String, String> map);

    @FormUrlEncoded
    @POST("union")
    Observable<BaseResponse<ContractEntity>> joinContract(
            @FieldMap SortedTreeMap<String, String> map);
}
