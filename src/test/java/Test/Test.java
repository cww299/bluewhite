package Test;

import com.bluewhite.common.utils.ZkemUtils.ZkemSDKUtils;

public class Test {
	
	public static void main(String[] args) {
		ZkemSDKUtils sdk = new ZkemSDKUtils();
		sdk.initSTA();
		try{
			boolean  flag = sdk.connect("192.168.1.204", 4370);
			System.out.println(flag);
		}catch(Exception e){
			e.printStackTrace();
		}
		sdk.disConnect();
		sdk.release();

	}

}
