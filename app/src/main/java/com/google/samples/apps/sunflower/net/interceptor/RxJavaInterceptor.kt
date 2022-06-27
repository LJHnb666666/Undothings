package com.google.samples.apps.sunflower.net.interceptor

import com.google.samples.apps.sunflower.net.module.ResponseModel
import io.reactivex.Observer
import io.reactivex.disposables.Disposable


abstract class RxJavaInterceptor<T> : Observer<ResponseModel<T>> {
    abstract fun success(data: T?)
    abstract fun failure(errorMsg: String?)

    override fun onSubscribe(d: Disposable?) {
        //这里可以弄个加载dialog的start，别忘了在请求结束（不管成功还是失败）的时候让这个dialog消失
    }

    override fun onNext(t: ResponseModel<T>?) {
        if(t?.data != null){
            //请求成功了
            success(t?.data)
        }else{
            //失败
            failure(t?.message)
        }
    }

    override fun onError(e: Throwable?) {
        failure(e?.message)
    }

    override fun onComplete() {
        //可以加个dialog的消失
    }

}
