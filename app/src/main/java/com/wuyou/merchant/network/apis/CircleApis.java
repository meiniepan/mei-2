package com.wuyou.merchant.network.apis;

import com.gs.buluo.common.network.BaseResponse;
import com.gs.buluo.common.network.SortedTreeMap;
import com.wuyou.merchant.bean.entity.ContractDetailEntity;
import com.wuyou.merchant.bean.entity.MerchantDetailEntity;
import com.wuyou.merchant.bean.entity.OrderInfoEntity;
import com.wuyou.merchant.bean.entity.OrderInfoListEntity;
import com.wuyou.merchant.bean.entity.PartnerListEntity;
import com.wuyou.merchant.bean.entity.WorkerListEntity;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * Created by solang on 2018/3/20.
 */

public interface CircleApis {

    @Multipart
    @POST("v1/contract")
    Observable<BaseResponse> createContract(
            @Query("")String s,
            @FieldMap SortedTreeMap<String, String> map, @Body RequestBody body);
}
