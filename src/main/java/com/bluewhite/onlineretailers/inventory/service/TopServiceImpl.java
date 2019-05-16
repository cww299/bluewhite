package com.bluewhite.onlineretailers.inventory.service;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.bluewhite.common.Constants;
import com.bluewhite.common.utils.SSLClient;

@Service
public class TopServiceImpl implements TopService {

	@Override
	public String getAccessToken() {
		Map map = new LinkedHashMap();
		map.put("grant_type", "refresh_token");
		map.put("need_refresh_token", "true");
		map.put("client_id", Constants.ALI_APP_KEY);
		map.put("client_secret", Constants.ALI_APP_SECRET);
		map.put("refresh_token", Constants.ALI_REFRESH_TOKEN);
		JSONObject result = null;
		try {
			String res = SSLClient.post(Constants.ALI_URL, map, null);
			res = res.substring(res.indexOf("{"));// 截取
			result = JSONObject.parseObject(res);// 转JSON
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result.get("access_token").toString();
	}

}
