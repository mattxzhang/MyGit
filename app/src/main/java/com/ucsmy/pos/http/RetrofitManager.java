package com.ucsmy.pos.http;

import android.text.TextUtils;

import com.google.gson.GsonBuilder;
import com.ucsmy.pos.Constant;
import com.ucsmy.pos.utils.StorageUtil;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by ucs_zhangjiaheng on 2017/3/13.
 */

public class RetrofitManager {
    private static Map<String, RetrofitManager> retrofitManagerMap =
            new HashMap<>(FosBusinessAgent.urlCount);
    private static final int DEFAULT_TIMEOUT = 30;
    private static final int HTTP_RESPONSE_DISK_CACHE_MAX_SIZE = 10 * 1024 * 1024;
    private static OkHttpClient okHttpClient;
    private BaseApiService apiService;

    public static RetrofitManager getInstance(String url) {
        url = TextUtils.isEmpty(url) ? Constant.FACE_URL : url;
        if (!url.endsWith(File.separator)) {
            url += File.separator;
        }
        RetrofitManager retrofitManager = retrofitManagerMap.get(url);
        if (retrofitManager == null) {
            retrofitManager = new RetrofitManager(url);
            retrofitManagerMap.put(url, retrofitManager);
        }
        return retrofitManager;
    }

    private RetrofitManager(String url) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .client(getOkHttpClient())
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().serializeNulls().create()))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        apiService = retrofit.create(BaseApiService.class);
    }

    private OkHttpClient getOkHttpClient() {
        if (okHttpClient == null) {
            synchronized (RetrofitManager.class) {
                if (okHttpClient == null) {
                    okHttpClient = new OkHttpClient.Builder()
                            .addNetworkInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.HEADERS))
                            .cookieJar(new NovateCookieManger())
                            .cache(cache())
                            .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                            .build();
                }
            }
        }
        return okHttpClient;
    }

    /**
     * 返回生成的OkHttpClient头
     */
    private Map<String, String> getHeaders() {
        Map<String, String> headers = new HashMap<>();
        headers.put("Accept-Encoding", "gzip");
//            headers.put("Cache-Control", getCacheControl());
//        headers.put("User-Agent", DeviceInfoUtil.USER_AGENT);
        return headers;
    }

    public BaseApiService getBaseApi() {
        return apiService;
    }

    private Cache cache() {
        //设置缓存路径
        final File baseDir = StorageUtil.getAvailableCacheDir();
        final File cacheDir = new File(baseDir, "HttpResponseCache");
        //设置缓存 10M
        return new Cache(cacheDir, HTTP_RESPONSE_DISK_CACHE_MAX_SIZE);
    }

}
