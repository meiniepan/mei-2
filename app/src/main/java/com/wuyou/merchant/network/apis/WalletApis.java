package com.wuyou.merchant.network.apis;

import com.gs.buluo.common.network.BaseResponse;
import com.wuyou.merchant.bean.Company;
import com.wuyou.merchant.bean.UserInfo;
import com.wuyou.merchant.bean.entity.WalletInfoEntity;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Administrator on 2018\1\26 0026.
 */

public interface WalletApis {
    @GET("wallet")
    Observable<BaseResponse<WalletInfoEntity>> getWalletInfo(
            @Path("uid") String uid);

    @POST("persons/{id}/company_bind_request")
    Observable<BaseResponse<UserInfo>> bindCompany(
            @Path("id") String id);

}
