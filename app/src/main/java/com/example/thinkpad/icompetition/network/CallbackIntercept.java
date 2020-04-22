package com.example.thinkpad.icompetition.network;

import android.app.Activity;
import android.widget.Toast;

import com.example.thinkpad.icompetition.R;
import com.example.thinkpad.icompetition.view.activity.ActivityManager;
import com.google.gson.JsonSyntaxException;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;


/**
 * 实现了Callback接口的抽象方法
 * 在其中拦截掉一些请求
 * *
 * Created by z
 * on 2017/3/11 0011.
 */

public abstract class CallbackIntercept implements Callback {

    @Override
    public void onFailure(Call call, IOException e) {
        onFail(call, e);
    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {
        if (response.isSuccessful()) {            //对http请求状态码的判断
            interceptRequest(call, response);    //拦截某些返回的请求
        } else {
            if (response.code() == 401)
            {
                System.out.println("用户鉴权失败");
                onFail(call, new IOException(response.body().string())); //请求未成功
            }
            else{
                onFail(call, new IOException(response.body().string())); //请求未成功
            }
        }
        int[] nums=new int[10];
    }

    /**
     * 拦截某些返回数据
     *
     * @param call     call
     * @param response response
     */
    private void interceptRequest(Call call, Response response) throws IOException {
        final String jsonBody = response.body().string();     //返回的json字串
        JSONObject jsonObject = null;
        int code = 0;                                   //用于存放服务器返回的code,根据code拦截请求
        //System.out.println("--->" + jsonBody);
        try {
            jsonObject = new JSONObject(jsonBody);
            code = jsonObject.getInt("code");           //获取code
        } catch (JSONException e) {
            e.printStackTrace();
        }
        switch (code) {     //根据服务器返回的状态码拦截掉一些请求
            case 9:         //用户鉴权信息失效
                //跳到登录页面
                break;
            case 100:       //触发第三方登陆

                break;
            case 408:       //系统时间错误
                try {       //如果获取activity为空,不显示Toast,避免程序崩溃
                    final Activity activity = ActivityManager.getActivityManager().peekActivity();
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(activity, activity.getString(R.string.system_time_error), Toast.LENGTH_SHORT).show();
                            //activity.showToast(activity.getString(R.string.system_time_error));
                        }
                    });
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
                break;
            default:        //放行的请求
                //这里会有一种情况:服务器返回200 ok成功.但是获取到的不是json数据,而是html格式的文本.这个文本
                //给Gson解析的时候会抛出com.google.gson.JsonSyntaxException异常.在这里抓取异常,然后让这个
                //请求从onFail()返回回去
                try {
                    onSuccess(call, jsonBody);
                } catch (JsonSyntaxException e) {
                    onFail(call, e);
                }

        }
    }

    public abstract void onSuccess(Call call, String jsonBody);

    public abstract void onFail(Call call, Exception e);
}
