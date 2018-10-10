package com.wuyou.merchant.network;

import android.support.v4.util.ArrayMap;

import com.google.gson.GsonBuilder;
import com.gs.buluo.common.network.LogInterceptor;
import com.wuyou.merchant.Constant;
import com.wuyou.merchant.util.GsonEosTypeAdapterFactory;

import org.reactivestreams.Publisher;

import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.FlowableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by admin on 2016/11/1.
 */
public class ChainRetrofit {

    private static ChainRetrofit instance;
    private ArrayMap<Class, Object> apis = new ArrayMap<>();
    private final Retrofit retrofit;

    private ChainRetrofit() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
//        builder.interceptors().add(new CarefreeHttpInterceptor());
        builder.interceptors().add(new LogInterceptor());
        builder.connectTimeout(10, TimeUnit.SECONDS);
        builder.readTimeout(20, TimeUnit.SECONDS);

        retrofit = new Retrofit.Builder()
                .baseUrl(Constant.CHAIN_URL)
                .client(builder.build())
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder()
                        .registerTypeAdapterFactory(new GsonEosTypeAdapterFactory())
                        .serializeNulls().create()))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    public synchronized static ChainRetrofit getInstance() {
        if (null == instance) {
            instance = new ChainRetrofit();
        }
        return instance;
    }

    public <T> T createApi(Class<T> service) {
        if (!apis.containsKey(service)) {
            T instance = retrofit.create(service);
            apis.put(service, instance);
        }

        return (T) apis.get(service);
    }

    /**
     * 线程切换
     *
     * @return
     */
    public static <T> FlowableTransformer<T, T> getScheduler() {
        return new FlowableTransformer<T, T>() {
            @Override
            public Publisher<T> apply(Flowable<T> upstream) {
                return upstream.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }
}
