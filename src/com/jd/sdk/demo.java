package com.jd.sdk;


public class demo {
	
	public static void main(String[] args) throws Exception {
        String USER_UNIQUE = "ievel39qfn";
        String SECRET_KEY = "400c0826066c64f8a3b8c64d55342ea1";
        LetvCloudV1 client = new LetvCloudV1(USER_UNIQUE, SECRET_KEY);
  
        //定义输出格式
        client.format = "xml";

        //视频上传初始化（Web方式）
        String response = client.videoUploadInit("乐视云视频");
        System.out.println("response:"+response);         
        //视频上传（Web方式）
        String response2 = client.videoUpload("E:/easydarwin/gpac.mp4","http://123.125.37.78/api/fileupload?offset=0&token=yGUirhgYtEVljcMUn8G3dL3EMIAJzSbFzzXISvVhjAT5MB-E873eJUJhaZRGoGIwD2JJEP4SiX0NFMdqrRxpJwu1D9r3QkA_baxWg5R2BDSHF1mOiS8HwJzWu56-FPn-Z8DwbzBQ7Lh4N095be1qnH7T1_A9pZsIZOU3LA-V6B-k937_otqBIZKV8NaAXJjEl5sIGs_Y1mvAt8ry3Cj8pKw8893a_a2jdOeCrfP4nQgELvXyp89SMJWPxVyeQrNR3hVdLMjoyL1FTYtVWLciyaKPvKSQLv3gGgPl70bHm7xXNvDl_-3htrta8W4MXrpOhB5c94o8KfYja4szRUqWIbOH2y7qNdd8cbC9NNAFYD4mf3HNoi270ZR2AJt4i78uzGRHfDbt0JLpDlFpsgZ2BbA9iohpTzHFdej3LlmfRV4NsuQFsbN3PkSQovw0a8_PjK_sDTO-DDwN6lGU3MdsLF4XC75kBvk0qmNmfTSDRwERshWYB3oA5aEdr7e2ejbT6LQ7YogUXwYFmvA039cwMceiB5QMk8Z5ZSGRwufUEFM~&fmt=xml");
        System.out.println(response2); 
        //视频上传（Flash方式）
        //String response3 = client.videoUploadFlash("乐视云视频", "", 600, 450, "10.10.80.153");
        //System.out.println(response3); 
        
        //获取单个视频信息
        /*int videoid = 4139137;
        String response4 = client.videoGet(videoid);
        System.out.println(response4); */
        //编辑单个视频的相关信息
        /*String response5 = client.videoUpdate(videoid, "abc", "desc", "tag", 0);
        System.out.println(response5); */
        
        //获取HTML代码播放地址
       /* String response6 = client.videoGetPlayinterface("abcde12345","a1b2c3d4f5","html", "", -1, 960, 480);
        System.out.println(response6);    */     

        //获取以天为单位的所有数据
        /*String response7 = client.dataTotalDate("2012-10-01", "2013-06-01", 1, 20);       
        System.out.println(response7);*/
          
	}
}
