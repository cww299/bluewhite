package com.bluewhite.common.utils;

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
	
	public static void main(String[] args) {
		String dou = "皮毛你说的";
		System.out.println(specialStrKeyword(dou));
		
		
	}

}
