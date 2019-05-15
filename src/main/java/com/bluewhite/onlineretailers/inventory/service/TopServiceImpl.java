package com.bluewhite.onlineretailers.inventory.service;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.bluewhite.common.Constants;
import com.bluewhite.common.utils.SSLClient;

@Service
public class TopServiceImpl implements  TopService{

	@Override
	public String getAccessToken() {
		Map map = new LinkedHashMap();
		map.put("grant_type", "refresh_token");
		map.put("need_refresh_token", "true");
		map.put("client_id", Constants.ALI_APP_KEY);
		map.put("client_secret",Constants.ALI_APP_SECRET);
		map.put("redirect_uri", "");
		map.put("refresh_token", Constants.ALI_REFRESH_TOKEN);
		String res = "";
		try {
			res = SSLClient.doPost(map, Constants.ALI_URL, "", "UTF-8", "UTF-8");
			System.out.println(res);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	
	

}
