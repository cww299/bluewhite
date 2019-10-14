package com.bluewhite.common.utils;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;
/**
 * ip工具类
 * @author zhangliang
 *
 */

public class IpUtil {

	/**
	 *  
	 * 
	 * @Title: getLocalIP 
	 * @Description: 获取本机IP(局域网)
	 * @param @return 
	 *              设定文件 
	 * @return String    返回类型  @throws 
	 */
	public static String getLocalIP() {
		String sIP = "";
		InetAddress ip = null;
		try {
			boolean bFindIP = false;
			Enumeration<NetworkInterface> netInterfaces = (Enumeration<NetworkInterface>) NetworkInterface
					.getNetworkInterfaces();
			while (netInterfaces.hasMoreElements()) {
				if (bFindIP) {
					break;
				}
				NetworkInterface ni = (NetworkInterface) netInterfaces.nextElement();
				Enumeration<InetAddress> ips = ni.getInetAddresses();
				while (ips.hasMoreElements()) {
					ip = (InetAddress) ips.nextElement();
					if (!ip.isLoopbackAddress() && ip.getHostAddress().matches("(\\d{1,3}\\.){3}\\d{1,3}")) {
						bFindIP = true;
						break;
					}
				}
			}
		} catch (Exception e) {
			
		}
		if (null != ip) {
			sIP = ip.getHostAddress();
		}
		return sIP;
	}
}
