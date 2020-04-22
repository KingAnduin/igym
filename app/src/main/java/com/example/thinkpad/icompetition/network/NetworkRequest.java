package com.example.thinkpad.icompetition.network;

import android.support.v4.util.ArrayMap;
import android.util.Log;

import com.example.thinkpad.icompetition.network.request.JsonPostRequest;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;

/**
 * 请求参数的类,只在本包使用,类不用public修饰
 * 传入的业务参数中包含了一个系统参数"method",在调用该类的地方直接加入到map参数中
 * 注意!为了减少参数的传递,系统参数字段 "method" 在{@link NetworkInterfaces}
 * 的接口方法中加入到业务参数的map中
 *
 * @see #sendRequest() 这个方法将会拼装系统参数,并发送网络请求
 * *
 * Created by z
 * on 2017/3/11 0011.
 */

class NetworkRequest {

    private ArrayMap<String, String> mRequestParam;     //业务参数,其中包含了一个系统参数"method"
    private String mUrl;
    private Callback mCallback;

    NetworkRequest(ArrayMap<String, String> businessParam, String url, Callback callback) {
        this.mRequestParam = businessParam;
        this.mUrl = url;
        this.mCallback = callback;
    }

    /**
     * 拼装业务参数与系统参数,添加数字签名并发送请求
     */
    void sendRequest() {
        mRequestParam = SignUtil.getRequestParam(mRequestParam);    //为请求参数添加数字签名
        Request request = new JsonPostRequest(mUrl, mRequestParam).getRequest();    //创建一个请求
        Call call = NetworkManager.getNetWorkManager().getHttpClient().newCall(request);
        call.enqueue(mCallback);    //发送请求
    }

    /**
     * 拼装业务参数与系统参数,添加数字签名并发送请求
     */
    void sendRequestByMethod(String method) {
        mRequestParam = SignUtil.getRequestParam(mRequestParam);    //为请求参数添加数字签名
        Request request = new JsonPostRequest(mUrl, mRequestParam).getRequestByMethod(method);    //创建一个请求
        Call call = NetworkManager.getNetWorkManager().getHttpClient().newCall(request);
        call.enqueue(mCallback);    //发送请求
    }

}
