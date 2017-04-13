package com.ucsmy.pos.base;

import android.content.Context;

import com.ucsmy.pos.utils.ToastUtil;

import rx.Subscriber;

/**
 * Created by ucs_zhangjiaheng on 2017/3/13.
 */

public abstract class BaseSubscriber<T> extends Subscriber<T> {
    private Context mContext;

    public BaseSubscriber(Context context){
        this.mContext = context;
    }
    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onNext(T t) {

    }

    @Override
    public void onError(Throwable e) {
        ToastUtil.showShortToast(mContext,e.getMessage());
    }

    @Override
    public void onCompleted() {

    }
}
