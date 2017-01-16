package com.jd.Event;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.jd.model.FileInfo;
import com.jd.sdk.LetvCloudV1;
import com.jd.window.FileTablePanel;
import com.jd.window.MainFrame;
import com.jd.window.MainPanel;

public class UploadThread extends Thread{
	
	public String USER_UNIQUE = "ievel39qfn";
	public String SECRET_KEY = "400c0826066c64f8a3b8c64d55342ea1";
    public LetvCloudV1 client = null;
    public boolean isStop = true;
    
	/*
	 * 
	 */
	public UploadThread()
	{
		client = new LetvCloudV1(USER_UNIQUE, SECRET_KEY);
		client.format = "json";
		uploadInit(MainFrame.currentUploadFile);
	}
	
	/*
	 * 
	 */
	public void uploadInit(int index)
	{
		FileInfo fileInfo = MainFrame.fileinfoVector.get(index);
		String response;
		try {
			response = client.videoUploadInit(fileInfo.file.getName());
			//System.out.println(response);
			//
			List<Map<String, String>> responseList = jsonStringToList(response);
			Map<String, String> map = (Map<String, String>)responseList.get(0);
			//
			if(map.get("code").equals("0"))
			{
				List<Map<String, String>> dataList = jsonStringToList(map.get("data"));
				MainFrame.fileinfoVector.get(index).fileStatus = "正在上传";
				MainFrame.fileinfoVector.get(index).upload_url = dataList.get(0).get("upload_url");
				MainFrame.fileinfoVector.get(index).progress_url = dataList.get(0).get("progress_url");
				MainFrame.fileinfoVector.get(index).video_id = dataList.get(0).get("video_id");
				MainFrame.fileinfoVector.get(index).video_unique = dataList.get(0).get("video_unique");
				MainFrame.fileinfoVector.get(index).token = dataList.get(0).get("token");
				
				MainPanel.ftp.init();
				MainPanel.ftp.validate();
				//
				if(!this.isAlive())
				{
					this.start();
				}
				upload(index);
			}
			else{  //
				uploadInit(index);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * 
	 */
	public void upload(int index)
	{
		String response2;
		try {
			response2 = client.videoUpload(MainFrame.fileinfoVector.get(index).file.getAbsolutePath(),
					MainFrame.fileinfoVector.get(index).upload_url);
			
			List<Map<String, String>> responseList = jsonStringToList(response2);
			//
			if(responseList.get(0).get("code").equals("0"))
			{
				//向网站服务器发送信息
				String info = sendInfoToWebServer();
				if(info.equals("ok"))
				{
					System.out.println("完成一个");
					MainFrame.fileinfoVector.get(index).fileStatus = "上传完成";
					MainFrame.fileinfoVector.get(index).prodrass = 100;
					
					if(index < MainFrame.fileinfoVector.size()-1)
					{
						MainFrame.currentUploadFile++;
						uploadInit(MainFrame.currentUploadFile);
					}
					else{
						MainPanel.ftp.init();
						MainPanel.ftp.validate();
						
						MainFrame.fileinfoVector.removeAllElements();
						FileTablePanel.labels.removeAllElements();
						
						MainFrame.isUpload = false;
						this.stop();
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * 
	 */
	public String sendInfoToWebServer()
	{
		String res = null;
		int video_id = Integer.valueOf(MainFrame.fileinfoVector.get(MainFrame.currentUploadFile).video_id);
		String video_uuid = MainFrame.fileinfoVector.get(MainFrame.currentUploadFile).video_unique;
		String video_name = MainFrame.fileinfoVector.get(MainFrame.currentUploadFile).file.getName();
		
		try {
			String url = "http://localhost:61626/VideoManager/UploadVideo";
			String param = "videoInfo="+video_id+"_"+video_uuid+"_"+URLEncoder.encode(video_name,"UTF-8");
			param += "&token="+"123456789";
			res = client.doGet(url+"?"+param);
			
			if(res.equals("ok"))
			{
				res = "ok";
			}
			else{
				res = "error";
			}
			
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		return res;
	}
	
	/*
	 * 
	 */
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
				//
				if(MainFrame.fileinfoVector.get(MainFrame.currentUploadFile).prodrass < 100)
				{
					String res = client.doGet(MainFrame.fileinfoVector.get(MainFrame.currentUploadFile).progress_url);
					
					List<Map<String, String>> prograssList = jsonStringToList(res);
					if(prograssList.get(0).get("code").equals("0")
							&&prograssList.get(0).get("data").length()!=0)
					{
						List<Map<String, String>> dataList = jsonStringToList(prograssList.get(0).get("data"));
						int upload_size = Integer.valueOf(dataList.get(0).get("upload_size"));
						int total_size = Integer.valueOf(dataList.get(0).get("total_size"));
						
						MainFrame.fileinfoVector.get(MainFrame.currentUploadFile).prodrass = (upload_size/(total_size*1.0+0.01))*100;
						
						MainPanel.ftp.init();
						MainPanel.ftp.validate();
					}
				}
				
				Thread.sleep(20);
			} catch (UnsupportedEncodingException e1) {
				e1.printStackTrace();
			}catch (IllegalArgumentException e2) {
				e2.printStackTrace();
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}

