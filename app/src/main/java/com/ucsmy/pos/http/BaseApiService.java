package com.ucsmy.pos.http;

import com.ucsmy.pos.entity.CardInfo;

import java.util.Map;

import retrofit2.http.POST;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 * Created by ucs_zhangjiaheng on 2017/3/13.
 */

public interface BaseApiService {

    @POST("ocridcard")
    Observable<CardInfo> dectetCardByUrl(@QueryMap Map<String, String> map);

//    @Multipart
//    @POST("{url}")
//    Observable<CardInfo> upLoadFile();
}
