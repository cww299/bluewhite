package com.bluewhite.common.utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.bluewhite.common.Constants;

public class StringUtil {

	public static String specialStrKeyword(String str) {
		if (str == null || str == "") {
			return null;
		}
		StringBuffer stringBuffer = new StringBuffer(str);
		int length = str.length();
		for (int i = 0; i < length; i++) {
			char chari = stringBuffer.charAt(i);
			if (i == 0) {
				if (chari == '%' || chari == '_' || chari == '\\') {
					stringBuffer.insert(i, "\\");
					i++;
					length++;
				}
			} else {
				if (chari == '%' || chari == '_' || chari == '\\') {
					stringBuffer.insert(i, "%\\");
					i += 2;
					length += 2;
				} else {
					stringBuffer.insert(i, "%");
					i++;
					length++;
				}
			}
		}
		return stringBuffer.toString();

	}

	/**
	 * 传入字段数值为null，返回空字符串，否则返回原数据。
	 */

	public static String keyToNull(Double str) {
		if (str == 0) {
			return "";
		}
		return String.valueOf(str);
	}

	/**
	 * 解析地址
	 * 
	 * @author lin
	 * @param address
	 * @return
	 */
	public static List<Map<String, String>> addressResolution(String address) {
		String regex = "(?<province>[^省]+自治区|.*?省|.*?行政区|.*?市)(?<city>[^市]+自治州|.*?地区|.*?行政单位|.+盟|市辖区|.*?市|.*?县)(?<county>[^县]+县|.+区|.+市|.+旗|.+海域|.+岛)?(?<town>[^区]+区|.+镇)?(?<village>.*)";
		Matcher m = Pattern.compile(regex).matcher(address);
		String province = null, city = null, county = null, town = null, village = null;
		List<Map<String, String>> table = new ArrayList<Map<String, String>>();
		Map<String, String> row = null;
		while (m.find()) {
			row = new LinkedHashMap<String, String>();
			province = m.group("province");
			row.put("province", province == null ? "" : province.trim());
			city = m.group("city");
			row.put("city", city == null ? "" : city.trim());
			county = m.group("county");
			row.put("county", county == null ? "" : county.trim());
			town = m.group("town");
			row.put("town", town == null ? "" : town.trim());
			village = m.group("village");
			row.put("village", village == null ? "" : village.trim());
			table.add(row);
		}
		return table;
	}

	public static void main(String[] args) {
		System.out.println(addressResolution("福建省 厦门市 思明区 嘉莲街道 龙山南路113号66婚博园非常六加一儿童摄影 "));
	}

	/**
	 * 获取单据号前缀
	 * 
	 * @author lin
	 * @param address
	 * @return
	 */
	public static String getDocumentNumber(String type) {
		String documentNumber = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String time = sdf.format(new Date());
		switch (type) {
		case "XS":
			documentNumber = Constants.XS + "-" + time + "-";
			break;
		case "3":
			documentNumber = Constants.CK + "-" + time + "-";
			break;
		case "2":
			documentNumber = Constants.RK + "-" + time + "-";
			break;
		case "0":
			documentNumber = Constants.SC + "-" + time + "-";
			break;
		case "1":
			documentNumber = Constants.ZG + "-" + time + "-";
			break;
		default:
			break;
		}
		return documentNumber;
	}

	// 判断一个字符串是否含有数字
	public static boolean HasDigit(String content) {
		boolean flag = false;
		Pattern p = Pattern.compile(".*\\d+.*");
		Matcher m = p.matcher(content);
		if (m.matches()) {
			flag = true;
		}
		return flag;
	}
	
	// 判断一个字符串是否都为数字  
	public static boolean isDigit(String strNum) {  
	    Pattern pattern = Pattern.compile("[0-9]{1,}");  
	    Matcher matcher = pattern.matcher((CharSequence) strNum);  
	    return matcher.matches();  
	}
	 
	//截取数字  
	public static String getNumbers(String content) {  
	    Pattern pattern = Pattern.compile("\\d+");  
	    Matcher matcher = pattern.matcher(content);  
	    while (matcher.find()) {  
	       return matcher.group(0);  
	    }  
	    return "";  
	}  
	  
	// 截取非数字  
	public  static String splitNotNumber(String content) {  
	    Pattern pattern = Pattern.compile("\\D+");  
	    Matcher matcher = pattern.matcher(content);  
	    while (matcher.find()) {  
	        return matcher.group(0);  
	    }  
	    return "";  
	}

}
