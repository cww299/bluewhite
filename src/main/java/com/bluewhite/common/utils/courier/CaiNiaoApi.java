package com.bluewhite.common.utils.courier;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.apache.commons.codec.binary.Base64;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class CaiNiaoApi {

	/**
	 * 测试环境请求url
	 */
	private final static String dailyUrl = "https://linkdaily.tbsandbox.com/gateway/pac_message_receiver.do";
	/**
	 * 测试环境secretKey
	 */
	private final static String dailySecretKey = "F53eqq903jQySV100Z8w06f9g914A13Z";
	/**
	 * 测试环境token，token是商家在物流云平台授权ISV后生成的授权码，目前需要商家配置在ISV的软件中
	 */
	private final static String dailyToken = "TmpFU1ZOUGoyRnoybDZmT3lyaW9hWGR4VFNad0xNYTBUek9QZk9kamt2Z1hJMytsVkVHK0FjVW55T25wcUR1Qw==";

	/**
	 * 线上环境请求url
	 */
	private final static String onlineUrl = ":http://link.cainiao.com/gateway/link.do";
	/**
	 * 线上环境appKey，物流云控制台查看
	 */
	private final static String onlineAppKey = "yourAppKey";
	/**
	 * 线上环境secretKey,物流云控制台查看
	 */
	private final static String onlineSecretKey = "yourSecretKey";
	/**
	 * 线上环境token，token是商家在物流云平台授权ISV后生成的授权码，目前需要商家配置在ISV的软件中
	 */
	private final static String onlineToken = "yourOnlineToken";

	/**
	 * 物流云工作台配置的API使用字符编码
	 */
	private final static String myCharset = "UTF-8";

	public static void main(String[] args) throws IOException, NoSuchAlgorithmException {
		String apiContent = "{}";
		String digest = getDigest(apiContent, dailySecretKey);

		FormBody.Builder formBodyBuilder = new FormBody.Builder();
		formBodyBuilder.add("logistic_provider_id", dailyToken);
		formBodyBuilder.add("msg_type", "TMS_WAYBILL_SUBSCRIPTION_QUERY");
		formBodyBuilder.add("logistics_interface", apiContent);
		formBodyBuilder.add("data_digest", digest);

		Request httpRequest = new Request.Builder().url(dailyUrl).post(formBodyBuilder.build()).build();
		Response response = new OkHttpClient().newCall(httpRequest).execute();
		System.out.println(response.body().string());

	}

	private static String getDigest(String apiContent, String secretKey)
			throws UnsupportedEncodingException, NoSuchAlgorithmException {
		String combine = apiContent + secretKey;
		MessageDigest md = MessageDigest.getInstance("MD5");
		md.update(combine.getBytes(myCharset));
		return new String(Base64.encodeBase64(md.digest()), myCharset);
	}

}
