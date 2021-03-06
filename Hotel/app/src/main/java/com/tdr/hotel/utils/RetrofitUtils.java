package com.tdr.hotel.utils;

import com.tdr.hotel.application.MyApp;
import com.tdr.hotel.base.Api;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Author：Libin on 2018/10/19 16:19
 * Email：1993911441@qq.com
 * Describe：Retrofit网络请求
 */
public class RetrofitUtils {

    private volatile static RetrofitUtils mInstance;
    private Api api;

    //构造方法私有
    private RetrofitUtils() {

        // 指定缓存路径,缓存大小100Mb
        Cache cache = new Cache(new File(MyApp.getInstance().getCacheDir(), "HttpCache"),
                1024L * 1024L * 100L);
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .cache(cache)
                .addInterceptor(getInterceptor())
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(15, TimeUnit.SECONDS)
                .writeTimeout(15, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .build();

        api = new Retrofit.Builder()
                .client(okHttpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(Api.HOST)
                .build().create(Api.class);
    }

    //获取单例
    public static RetrofitUtils getInstance() {
        if (mInstance == null) {
            synchronized (RetrofitUtils.class) {
                if (mInstance == null) {
                    mInstance = new RetrofitUtils();
                }
            }
        }
        return mInstance;
    }

    public Api getService() {
        return api;
    }


    /**
     * 应用拦截器，断网读取缓存，有网请求接口
     */
    private Interceptor getInterceptor() {

        return new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                if (!NetworkUtils.getInstance().isNetworkAvailable()) {

                    CacheControl cacheControl = new CacheControl.Builder()
                            .onlyIfCached()
                            .maxStale(60 * 60 * 24, TimeUnit.SECONDS)
                            .build();

                    request = request.newBuilder()
                            .cacheControl(cacheControl)
                            .build();
                }
                return chain.proceed(request);


            }
        };
    }


    /**
     * 网络拦截器 设置缓存
     */
    private Interceptor getNetWorkInterceptor() {
        return new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {

                Response response = chain.proceed(chain.request());
                int maxAge = 20;//在线缓存时间

                return response.newBuilder()
                        .removeHeader("Pragma")//清除头信息
                        .removeHeader("Cache-Control")
                        .header("Cache-Control", "public,max-age=" + maxAge)//若是0则为不设置缓存
                        .build();
            }
        };
    }
}



