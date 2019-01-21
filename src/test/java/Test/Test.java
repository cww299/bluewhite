package Test;

import com.bluewhite.common.utils.ZkemUtils.ZkemSDKUtils;

public class Test {
	
	public static void main(String[] args) {
		ZkemSDKUtils sdk = new ZkemSDKUtils();
		sdk.initSTA();
		try{
			boolean  flag = sdk.connect("192.168.1.204", 4370);
//			boolean  flag2 = sdk.connect("192.168.1.205", 4370);
//			boolean  flag3 = sdk.connect("192.168.1.250", 4370);
			System.out.println(flag);
//			System.out.println(flag2);
//			System.out.println(flag3);
		}catch(Exception e){
			e.printStackTrace();
		}
		sdk.disConnect();
		sdk.release();
	}

}
