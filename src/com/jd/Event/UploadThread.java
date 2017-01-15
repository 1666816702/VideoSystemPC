package com.jd.Event;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.jd.model.FileInfo;
import com.jd.sdk.LetvCloudV1;
import com.jd.window.MainFrame;

public class UploadThread extends Thread{
	
	public String USER_UNIQUE = "ievel39qfn";
	public String SECRET_KEY = "400c0826066c64f8a3b8c64d55342ea1";
    public LetvCloudV1 client = null;
    
	/*
	 * 构造函数，开始上传第一个文件
	 */
	public UploadThread()
	{
		client = new LetvCloudV1(USER_UNIQUE, SECRET_KEY);
		client.format = "json";
		
		uploadInit(0);
	}
	
	/*
	 * 上传文件初始化
	 */
	public void uploadInit(int index)
	{
		FileInfo fileInfo = MainFrame.fileinfoVector.get(index);
		String response;
		try {
			response = client.videoUploadInit(fileInfo.file.getName());
			//解析返回的数据
			List<Map<String, String>> responseList = jsonStringToList(response);
			Map<String, String> map = (Map<String, String>)responseList.get(0);
			//初始化成功
			if(map.get("code").equals("0"))
			{
				List<Map<String, String>> dataList = jsonStringToList(map.get("data"));
				MainFrame.fileinfoVector.get(index).fileStatus = "初始化成功";
				MainFrame.fileinfoVector.get(index).upload_url = dataList.get(0).get("upload_url");
				MainFrame.fileinfoVector.get(index).progress_url = dataList.get(0).get("progress_url");
				MainFrame.fileinfoVector.get(index).video_id = dataList.get(0).get("video_id");
				MainFrame.fileinfoVector.get(index).video_unique = dataList.get(0).get("video_unique");
				MainFrame.fileinfoVector.get(index).token = dataList.get(0).get("token");
				
				MainFrame.mainPanel.ftp.init();
				MainFrame.mainPanel.validate();
				//开始上传
				upload(index);
			}
			else{  //初始化不成功,再次初始化
				uploadInit(index);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * 上传视频
	 */
	public void upload(int index)
	{
		String response2;
		try {
			this.start();
			response2 = client.videoUpload(MainFrame.fileinfoVector.get(index).file.getAbsolutePath(),
					MainFrame.fileinfoVector.get(index).upload_url);
			System.out.println(response2);
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}
	
	public List<Map<String, String>> jsonStringToList(String rsContent) throws Exception
    {
        JSONArray arry = JSONArray.fromObject("["+rsContent+"]");

        List<Map<String, String>> rsList = new ArrayList<Map<String, String>>();
        for (int i = 0; i < arry.size(); i++)
        {
            JSONObject jsonObject = arry.getJSONObject(i);
            Map<String, String> map = new HashMap<String, String>();
            for (Iterator<?> iter = jsonObject.keys(); iter.hasNext();)
            {
                String key = (String) iter.next();
                String value = jsonObject.get(key).toString();
                map.put(key, value);
            }
            rsList.add(map);
        }
        return rsList;
    }
	
	
	@Override
	public void run() {
		
		while(true)
		{
			try {
				//发送查询进度请求
				String res = client.doGet(MainFrame.fileinfoVector.get(0).progress_url);
				
				List<Map<String, String>> prograssList = jsonStringToList(res);
				List<Map<String, String>> dataList = jsonStringToList(prograssList.get(0).get("data"));
				
				int upload_size = Integer.valueOf(dataList.get(0).get("upload_size"));
				int total_size = Integer.valueOf(dataList.get(0).get("total_size"));
				
				MainFrame.fileinfoVector.get(0).prodrass = (upload_size/(total_size*1.0+0.01))*100;
				
				MainFrame.mainPanel.ftp.init();
				MainFrame.mainPanel.validate();
				
				Thread.sleep(40);
			} catch (UnsupportedEncodingException e1) {
				e1.printStackTrace();
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
