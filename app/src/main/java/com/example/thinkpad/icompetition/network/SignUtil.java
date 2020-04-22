package com.example.thinkpad.icompetition.network;

import android.support.annotation.NonNull;
import android.support.v4.util.ArrayMap;

import com.example.thinkpad.icompetition.IcompetitionApplication;
import com.example.thinkpad.icompetition.view.activity.ActivityManager;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;

/**
 * 网络请求参数的签名计算类,传入接口名字,session,业务参数,返回签名后的请求参数(可以直接发送给服务器)
 *
 * @see #getRequestParam(ArrayMap) 外部调用该方法
 * *
 * Created by z
 * on 2017/3/8 0008.
 */

public class SignUtil {

	private static final String appSecret = "ADMLOSpsU1f05gSuDyFyA5yjKGeSuxlp";   //密钥-开发版
	private static final String appKey = "D8vNFm8jklo9uNmg2ibpDp0FHm65R2Ac";      //app key-开发版

	private static final String version = "2.0";           //API协议版本
	private static final String encryptMethod = "md5";     //加密方法,可选:"md5","hmac"
	private static final String format = "json";           //响应格式,目前仅支持json

	/**
	 * 该工具返回请求参数
	 * 传入业务参数,将业务参数添加上系统参数,然后返回scgscgscg    ssnbxzmcbzkjcbkjasjkbcscscd
	 *
	 * @param businessParam 业务参数
	 * @return 最终的请求参数, 使用了ArrayMap而不是HashMAp,ArrayMap查找速度慢,但是暂用内存少,HashMap则相反
	 * 在数据量不大时,可以接受ArrayMap的查找速度,从而节省内存
	 */
	static ArrayMap<String, String> getRequestParam(
			@NonNull ArrayMap<String, String> businessParam) {

		ArrayMap<String, String> requestParam = new ArrayMap<>();   //最终的请求参数
		requestParam.putAll((Map<? extends String, ? extends String>) businessParam);  //加入业务参数
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
		String time = dateFormat.format(new java.util.Date());      //系统时间,系统参数

		//以下加入系统参数,某些值需要计算得出.登录界面连续点击登录,在activity finish()之后还会进入登录函数
		//这是Activity栈是空的,所以没办法获取session,这种情况会抛出NullPointerException,这里catch异常,
		//将session置为空串
		String token;
		try {
			token = ((IcompetitionApplication) ActivityManager.getActivityManager().peekActivity().getApplication()).getToken();            //从application中获取session
		} catch (NullPointerException e) {
			token = "";
		}
		if (token != null && !token.equals("")) {
			requestParam.put("token", token);
		}
		requestParam.put("timestamp", time);
		requestParam.put("format", format);
		requestParam.put("app_key", appKey);
		requestParam.put("version", version);
		requestParam.put("encrypt_method", encryptMethod);
		requestParam.put("sign", getSign(requestParam));    //计算数据签名
		return requestParam;
	}

	/**
	 * 根据请求参数计算数据签名
	 * 算法:除了sign之外的所有key value升序排列拼接,根据加密方式决定在首尾加上appSecret
	 * 或者只在首部加上appSecret,然后根据加密算法计算密文,参见"接口文档"
	 * 数据签名也属于系统参数,实际上是根据除了sign之外的参数计算出数据签名,然后将数据签名加入到请求参数里面
	 *
	 * @param requestParam 除了sign之外的请求参数
	 * @return 数据签名sign
	 */
	private static String getSign(ArrayMap<String, String> requestParam) {
		TreeMap<String, String> requestMap = new TreeMap<>(
				new Comparator<String>() {
					@Override
					public int compare(String s, String t1) {
						return s.compareTo(t1); //升序排列
					}
				}
		);
		requestMap.putAll(requestParam);
		StringBuilder signBuilder = new StringBuilder();
		signBuilder.append(appSecret);   //使用md5加密,在头部加上appSecret,接口文档要求
		for (String key : requestMap.keySet()) {     //遍历Set(key),拼接
			signBuilder.append(key);
			signBuilder.append(requestMap.get(key));
		}
		signBuilder.append(appSecret);   //使用md5加密,在尾部加上appSecret,接口文档要求
		return createMD5(signBuilder.toString());
	}


	/**
	 * 计算给定字符串的32位大写MD5值
	 *
	 * @param str 需要加密的字符串
	 * @return 给定字符串的MD5值
	 */
	private static String createMD5(@NonNull String str) {
		byte[] hash;
		try {
			hash = MessageDigest.getInstance("MD5").digest(str.getBytes("UTF-8"));
		} catch (NoSuchAlgorithmException e) {
			//e.printStackTrace();
			throw new RuntimeException("Huh, MD5 should be supported?", e);
		} catch (UnsupportedEncodingException e) {
			//e.printStackTrace();
			throw new RuntimeException("Huh, UTF-8 should be supported?", e);
		}
		StringBuilder hex = new StringBuilder(hash.length * 2);
		for (byte b : hash) {
			if ((b & 0xFF) < 0x10) {
				hex.append("0");
			}
			hex.append(Integer.toHexString(b & 0xFF));
		}
		return hex.toString().toUpperCase();    //转换成大写
	}
}
