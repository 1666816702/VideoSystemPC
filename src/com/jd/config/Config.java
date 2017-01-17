package com.jd.config;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class Config {
	public static String USER_UNIQUE = null;
	public static String SECRET_KEY = null;
	public static String WEB_SERVER = null;
	public static String WEB_SERVER_TOKEN = null;
	
	public Config()
	{
		String JsonContext = Config.ReadFile("config/config.json");
		JSONArray jsonArray = JSONArray.fromObject(JsonContext);
		
		JSONObject jsonObject = jsonArray.getJSONObject(0);
		Config.USER_UNIQUE = (String)jsonObject.get("user_uniqe");
		Config.SECRET_KEY = (String)jsonObject.get("secret_key");
		Config.WEB_SERVER = (String)jsonObject.get("web_server");
		Config.WEB_SERVER_TOKEN = (String)jsonObject.get("web_server_token");
	}
	
	public static String ReadFile(String Path){
		BufferedReader reader = null;
		String laststr = "";
		try{
			FileInputStream fileInputStream = new FileInputStream(Path);
			InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, "UTF-8");
			reader = new BufferedReader(inputStreamReader);
			String tempString = null;
			while((tempString = reader.readLine()) != null){
				laststr += tempString;
			}
			reader.close();
		}catch(IOException e){
			e.printStackTrace();
		}finally{
			if(reader != null){
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return laststr;
	}
}
