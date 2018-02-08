package com.wuyou.merchant.network.apis;

import com.gs.buluo.common.network.BaseResponse;
import com.gs.buluo.common.network.SortedTreeMap;
import com.wuyou.merchant.bean.UserInfo;
import com.wuyou.merchant.bean.entity.OrderInfoListEntity;

import io.reactivex.Observable;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

/**
 * Created by solang on 2018/2/8.
 */

public interface OrderApis {
    @GET("orders/{merchant_id}/{status}/{start_id}/{flag}")
    Observable<BaseResponse<OrderInfoListEntity>> getOrders(
            @Path("merchant_id") int merchant_id, @Path("status") int status,
            @Path("start_id") int start_id, @Path("flag") int flag,
            @QueryMap SortedTreeMap<String, String> map);

    @GET("profile/{uid}")
    Observable<BaseResponse<UserInfo>> getUserInfo(
            @Path("uid") String uid, @QueryMap SortedTreeMap<String, String> map);

    @FormUrlEncoded
    @POST("login")
    Observable<BaseResponse<UserInfo>> doLogin(
            @FieldMap SortedTreeMap<String, String> map);

    @FormUrlEncoded
    @PUT("login/{uid}")
    Observable<BaseResponse> doLogout(
            @Path("uid") String uid, @FieldMap SortedTreeMap<String, String> map);

    @FormUrlEncoded
    @PUT("profile/{uid}")
    Observable<BaseResponse> updateUserInfo(
            @Path("uid") String uid, @FieldMap SortedTreeMap<String, String> map);

    @FormUrlEncoded
    @PUT("password/edit/{uid}")
    Observable<BaseResponse> updatePwd(
            @Path("uid") String uid, @FieldMap SortedTreeMap<String, String> map);

}
