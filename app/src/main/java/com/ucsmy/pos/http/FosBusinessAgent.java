package com.ucsmy.pos.http;

import com.ucsmy.pos.Constant;
import com.ucsmy.pos.entity.CardInfo;
import com.ucsmy.pos.utils.RxUtil;

import java.util.HashMap;
import java.util.Map;

import rx.Observable;

/**
 * Created by ucs_zhangjiaheng on 2017/3/13.
 */

public class FosBusinessAgent {
    public static int urlCount = 1;//url类型总数

    private BaseApiService getRetrofitManager(String hostUrl) {
        return RetrofitManager.getInstance(hostUrl).getBaseApi();
    }


    public Observable<CardInfo> cardDetect() {
        Map<String, String> parmMap = new HashMap<>();
        parmMap.put("api_key", Constant.FACE_APIKEY);
        parmMap.put("api_secret", Constant.FACE_APISECRET);
        parmMap.put("image_url", Constant.FACE_DEFAULT);
        return getRetrofitManager("").dectetCardByUrl(parmMap).compose(RxUtil.<CardInfo>defaultSchedulers());
    }
}
