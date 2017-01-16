package com.jd.window;

import com.jd.sdk.LetvCloudV1;

public class Test {

	public static void main(String[] args) throws Exception
	{
		String USER_UNIQUE = "ievel39qfn";
        String SECRET_KEY = "400c0826066c64f8a3b8c64d55342ea1";
        LetvCloudV1 client = new LetvCloudV1(USER_UNIQUE, SECRET_KEY);
        
        String res = client.videoUpdate(39778733,"","","",1);
        System.out.println(res);
	}
}
