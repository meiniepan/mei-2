package com.wuyou.merchant.network.apis;

import com.gs.buluo.common.network.BaseResponse;
import com.gs.buluo.common.network.SortedTreeMap;
import com.wuyou.merchant.bean.entity.BaseKunResponse;
import com.wuyou.merchant.bean.entity.ContractMerchantEntity;
import com.wuyou.merchant.bean.entity.MechanicEntity;
import com.wuyou.merchant.bean.entity.MechanicReq;
import com.wuyou.merchant.bean.entity.MerchantDetailEntity;
import com.wuyou.merchant.bean.entity.OrderBeanDetail;
import com.wuyou.merchant.bean.entity.OrderInfoListEntity;
import com.wuyou.merchant.bean.entity.PartnerListEntity;
import com.wuyou.merchant.bean.entity.KunListEntity;
import com.wuyou.merchant.bean.entity.ServiceNewEntity;
import com.wuyou.merchant.bean.entity.ServiceOffReq;
import com.wuyou.merchant.bean.entity.ServiceReq;
import com.wuyou.merchant.bean.entity.StaffEntity;
import com.wuyou.merchant.bean.entity.StaffReq;
import com.wuyou.merchant.bean.entity.WorkerListEntity;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

/**
 * Created by solang on 2018/2/8.
 */

public interface OrderApis {
    /**
     * 获取订单列表
     *
     * @param map
     * @return
     */
    @GET("orders")
    Observable<BaseResponse<OrderInfoListEntity>> getOrders(
            @QueryMap SortedTreeMap<String, String> map);

    @GET("order/{orderId}")
    Observable<BaseResponse<OrderBeanDetail>> getOrderDetail(@Path("orderId") String id, @QueryMap SortedTreeMap<String, String> map);

    /**
     * 合伙人列表
     *
     * @param uid
     * @param map
     * @return
     */
    @GET("union/providers/{uid}")
    Observable<BaseResponse<PartnerListEntity>> getUnionPartner(
            @Path("uid") String uid, @QueryMap SortedTreeMap<String, String> map);

    /**
     * 员工列表
     *
     * @param
     * @param map
     * @return
     */
    @GET("workers")
    Observable<BaseResponse<WorkerListEntity>> getWorkersInfo(
            @QueryMap SortedTreeMap<String, String> map);

    @POST("mechanic/get")
    Observable<BaseKunResponse<KunListEntity<MechanicEntity>>> getMerchanic(
            @Body MechanicReq req);

    @GET("union/shops/{uid}/{action}")
    Observable<BaseResponse<WorkerListEntity>> getDispatchMerchantInfo(
            @Path("uid") String uid, @Path("action") String action, @QueryMap SortedTreeMap<String, String> map);

    /**
     * 加入联盟时的商户列表
     *
     * @param uid
     * @param startId
     * @param flag
     * @param map
     * @return
     */
    @GET("union/prepare/{uid}/{start_id}/{flag}")
    Observable<BaseResponse<WorkerListEntity>> getPrepareMerchantInfo(
            @Path("uid") String uid, @Path("start_id") String startId, @Path("flag") String flag, @QueryMap SortedTreeMap<String, String> map);

    /**
     * 服务商详情
     *
     * @param uid
     * @param merchant_id
     * @param map
     * @return
     */
    @GET("detail/{uid}/{merchant_id}")
    Observable<BaseResponse<MerchantDetailEntity>> getMerchantDetail(
            @Path("uid") String uid, @Path("merchant_id") String merchant_id,
            @QueryMap SortedTreeMap<String, String> map);

    @GET("contract/{contract_id}")
    Observable<BaseResponse<ContractMerchantEntity>> getContractDetail(
            @Path("contract_id") String contract_id,
            @QueryMap SortedTreeMap<String, String> map);

    /**
     * 加入联盟
     *
     * @param uid
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST("union/{uid}")
    Observable<BaseResponse> signContract(
            @Path("uid") String uid, @FieldMap SortedTreeMap<String, String> map);

    @FormUrlEncoded
    @PUT("order/dispatch")
    Observable<BaseResponse> dispatchOrder(
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

    @Multipart
    @POST("order/voucher")
    Observable<BaseResponse> updateVoucher(
            @Part MultipartBody.Part file,
            @QueryMap SortedTreeMap<String, String> map);

    @POST("service/get")
    Observable<BaseKunResponse<KunListEntity<ServiceNewEntity>>> getService(
            @Body ServiceReq req);

    /**
     * 下架服务
     * @param req
     * @return
     */
    @POST("service/off")
    Observable<BaseKunResponse> OffService(
            @Body ServiceOffReq req);

    /**
     * 获取管理员列表
     * @param req
     * @return
     */
    @POST("shop/staff/get")
    Observable<BaseKunResponse<KunListEntity<StaffEntity>>> getStaffList(
            @Body StaffReq req);
}

