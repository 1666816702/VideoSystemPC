package com.jd.sdk;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.net.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;


public class LetvCloudV1 {	    
	    private String userUnique;
	    private String secretKey;
	    private String restUrl = "http://api.letvcloud.com/open.php";
	    public String format = "json";
	    public String apiVersion = "2.0";	
	    
	    //定义视频状�?�常�?
	    public static final  int ALL = 0;
	    public static final  int PLAY_OK = 10;
	    public static final  int FAILED = 20;
	    public static final  int WAIT = 30;
	    
	    public LetvCloudV1(String userUnique, String secretKey) {
	        this.userUnique = userUnique;
	        this.secretKey = secretKey;
	    }
	    
        /**
         * 视频上传初始�?
         * @param  string video_name 视频名称
         * @param  string client_ip  用户IP地址
         * @param  int file_size  文件大小，单位为字节
         * @return String
         * @throws Exception 
         */
        public String videoUploadInit(String video_name,String client_ip,int file_size) throws Exception{
        	String api = "video.upload.init";
        	HashMap<Object, Object> params = new HashMap<Object, Object>();

        	params.put("video_name", video_name);
        	if(client_ip.length()>0){
        		params.put("client_ip", client_ip); 
        	}
        	if(file_size>0){
        		params.put("file_size", file_size+"");
        	}       		
        	
        	return makeRequest(api,params);
        }
	    
        /**
         * 视频上传初始�?
         * @param  string video_name 视频名称
         * @param  string client_ip  用户IP地址
         * @return String
         * @throws Exception 
         */
        public String videoUploadInit(String video_name,String client_ip) throws Exception{
        	return videoUploadInit(video_name,client_ip,0);
        }
        
        /**
         * 视频上传初始�?
         * @param  string video_name 视频名称
         * @param  string client_ip  用户IP地址
         * @return String
         * @throws Exception 
         */
        public String videoUploadInit(String video_name) throws Exception{
        	return videoUploadInit(video_name,"",0);
        }
        
        /**
         * 视频上传 (web方式)
         * @param  string video_file 文件绝对路径
         * @param  string upload_url 视频上传地址，视频上传时提交地址
         * @return String
         * @throws IOException 
         */
        public String videoUpload(String video_file,String upload_url) throws IOException{
        	File file = new File(video_file);
      		return doUploadFile(file,upload_url);       	
        }
        
        /**
         * 视频断点续传
         * @param  string token 视频上传标识
         * @return String
         * @throws Exception 
         */
        public String videoUploadResume(String token) throws Exception{
        	String api = "video.upload.resume";
        	HashMap<Object, Object> params = new HashMap<Object, Object>();
        	params.put("token", token);
        	return makeRequest(api,params);
        }
        
        /**
         * 视频上传（Flash方式�?
         * @param  string video_name 视频名称
         * @param  string js_callback Javascript回调函数，视频上传完毕后调用
         * @param  int flash_width Flash宽度，默认�?�为600
         * @param  int flash_height Flash高度，默认�?�为450
         * @param  string client_ip 用户IP地址
         * @return String
         * @throws Exception 
         */
        public String videoUploadFlash(String video_name,String js_callback,int flash_width,int flash_height,String client_ip) throws Exception{
        	String api = "video.upload.flash";
        	HashMap<Object, Object> params = new HashMap<Object, Object>();
        	params.put("video_name", video_name);
        	if(js_callback.length()>0){
        		params.put("js_callback", js_callback);
        	}     
        	if(flash_width >0){
        		params.put("flash_width", flash_width+"");
        	}
        	if(flash_height >0){
        		params.put("flash_height", flash_height+"");
        	}
        	if(client_ip.length()>0){
        		params.put("client_ip", client_ip);
        	}        	
        	return makeRequest(api,params); 	
        }
        
        /**
         * 视频上传（Flash方式�?
         * @param  string video_name 视频名称
         * @param  string js_callback Javascript回调函数，视频上传完毕后调用
         * @param  int flash_width Flash宽度，默认�?�为600
         * @param  int flash_height Flash高度，默认�?�为450
         * @return String
         * @throws Exception 
         */
        public String videoUploadFlash(String video_name,String js_callback,int flash_width,int flash_height) throws Exception{
        	return videoUploadFlash(video_name,js_callback,flash_width,flash_height,""); 	
        }
        
        /**
         * 视频上传（Flash方式�?
         * @param  string video_name 视频名称
         * @param  string js_callback Javascript回调函数，视频上传完毕后调用
         * @param  int flash_width Flash宽度，默认�?�为600
         * @return String
         * @throws Exception 
         */
        public String videoUploadFlash(String video_name,String js_callback,int flash_width) throws Exception{
        	return videoUploadFlash(video_name,js_callback,flash_width,0,""); 	
        }
        
        /**
         * 视频上传（Flash方式�?
         * @param  string video_name 视频名称
         * @param  string js_callback Javascript回调函数，视频上传完毕后调用
         * @return String
         * @throws Exception 
         */
        public String videoUploadFlash(String video_name,String js_callback) throws Exception{
        	return videoUploadFlash(video_name,js_callback,0,0,""); 	
        }
        
        /**
         * 视频上传（Flash方式�?
         * @param  string video_name 视频名称
         * @return String
         * @throws Exception 
         */
        public String videoUploadFlash(String video_name) throws Exception{
        	return videoUploadFlash(video_name,"",0,0,""); 	
        }
        
        /**
         * 视频信息更新
         * @param  int video_id 视频ID
         * @param  string video_name 视频名称
         * @param  string video_desc 视频�?�?
         * @param  string tag 标签
         * @param  int is_pay 视频是否收费�?0表示不收费；1表示收费（收费视频播放时会进行用户鉴权，请不要随便设置）
         * @return String
         * @throws Exception 
         */
        public String videoUpdate(int video_id,String video_name,String video_desc,String tag,int is_pay) throws Exception{
        	String api = "video.update";
        	HashMap<Object, Object> params = new HashMap<Object, Object>();
        	params.put("video_id", video_id+"");
        	if(video_name.length()>0){
        		params.put("video_name", video_name);
        	}
        	if(video_desc.length()>0){
        		params.put("video_desc", video_desc);
        	}
        	if(tag.length()>0){
        		params.put("tag", tag);
        	}
        	if(is_pay == 0 ||is_pay == 1){
        		params.put("is_pay", is_pay+"");
        	}        	
        	return makeRequest(api,params); 	
        }
        
        /**
         * 视频信息更新
         * @param  int video_id 视频ID
         * @param  string video_name 视频名称
         * @param  string video_desc 视频�?�?
         * @param  string tag 标签
         * @return String
         * @throws Exception 
         */
        public String videoUpdate(int video_id,String video_name,String video_desc,String tag) throws Exception{
        	return videoUpdate(video_id,video_name,video_desc,tag,-1); 	
        }
        
        /**
         * 视频信息更新
         * @param  int video_id 视频ID
         * @param  string video_name 视频名称
         * @param  string video_desc 视频�?�?
         * @return String
         * @throws Exception 
         */
        public String videoUpdate(int video_id,String video_name,String video_desc) throws Exception{
        	return videoUpdate(video_id,video_name,video_desc,"",-1); 	
        }
        
        /**
         * 视频信息更新
         * @param  int video_id 视频ID
         * @param  string video_name 视频名称
         * @return String
         * @throws Exception 
         */
        public String videoUpdate(int video_id,String video_name) throws Exception{
        	return videoUpdate(video_id,video_name,"","",-1); 	
        }
        
        /**
         * 视频信息更新
         * @param  int video_id 视频ID
         * @return String
         * @throws Exception 
         */
        public String videoUpdate(int video_id) throws Exception{
        	return videoUpdate(video_id,"","","",-1); 	
        }
        
        /**
         * 获取视频列表
         * @param  int index �?始页索引，默认�?�为1
         * @param  int size 分页大小，默认�?�为10，最大�?�为100
         * @param  const status 视频状�?�：ALL表示全部；PLAY_OK表示可以正常播放；FAILED表示处理失败；WAIT表示正在处理过程中�?�默认�?�为ALL
         * @return String
         * @throws Exception 
         */
        public String videoList(int index,int size,int status) throws Exception{
        	String api = "video.list";
        	HashMap<Object, Object> params = new HashMap<Object, Object>();
        	if(index > 0){
        		params.put("index", index+"");
        	}
        	if(size >0){
        		params.put("size", size+"");
        	}
        	if(status == ALL || status == PLAY_OK || status == FAILED || status == WAIT){
        		params.put("status", status+"");
        	}        	
        	return makeRequest(api,params); 	
        }
        
        /**
         * 获取视频列表
         * @param  int index �?始页索引，默认�?�为1
         * @param  int size 分页大小，默认�?�为10，最大�?�为100
         * @return String
         * @throws Exception 
         */
        public String videoList(int index,int size) throws Exception{
        	return videoList(index,size,-1); 	
        }
        
        /**
         * 获取视频列表
         * @param  int index �?始页索引，默认�?�为1
         * @return String
         * @throws Exception 
         */
        public String videoList(int index) throws Exception{
        	return videoList(index,0,-1); 	
        }
        
        /**
         * 获取视频列表
         * @return String
         * @throws Exception 
         */
        public String videoList() throws Exception{
        	return videoList(0,0,-1); 	
        }
        
	    /**
	     * 获取单个视频信息
	     * @param videoid 视频id
	     * @return
	     * @throws Exception 
	     */
		public String videoGet(int videoid) throws Exception{
	        String api = "video.get";
	        HashMap<Object, Object> params = new HashMap<Object, Object>();
	        params.put("video_id", videoid+"");
	        return makeRequest(api,params);
		}	    
		
        /**
         * 删除视频
         * @param  int video_id 视频ID
         * @return String
         * @throws Exception 
         */
        public String videoDel(int video_id) throws Exception{
        	String api = "video.del";
        	HashMap<Object, Object> params = new HashMap<Object, Object>();
        	params.put("video_id", video_id+"");
        	return makeRequest(api,params);
        }
        
        /**
         * 批量删除视频
         * @param  string video_id_list 视频ID列表，使用符�?-作为间隔符，每次�?多操�?50条记�?
         * @return String
         * @throws Exception 
         */
        public String videoDelBatch(String video_id_list) throws Exception{
        	String api = "video.del.batch";
        	HashMap<Object, Object> params = new HashMap<Object, Object>();
        	params.put("video_id_list", video_id_list);
        	return makeRequest(api,params);
        }
        
        /**
         * 视频暂停
         * @param  int video_id 视频ID
         * @return String
         * @throws Exception 
         */
        public String videoPause(int video_id) throws Exception{
        	String api = "video.pause";
        	HashMap<Object, Object> params = new HashMap<Object, Object>();
        	params.put("video_id", video_id+"");
        	return makeRequest(api,params);
        }
        
        /**
         * 视频恢复
         * @param  int video_id 视频ID
         * @return String
         * @throws Exception 
         */
        public String videoRestore(int video_id) throws Exception{
        	String api = "video.restore";
        	HashMap<Object, Object> params = new HashMap<Object, Object>();
        	params.put("video_id", video_id+"");
        	return makeRequest(api,params);
        }
        
        /**
         * 获取视频截图
         * @param  int video_id 视频ID
         * @param  string size 截图尺寸，每种尺寸各�?8张图�?
         * @return String
         * @throws Exception 
         */
        public String imageGet(int video_id,String size) throws Exception{
        	String api = "image.get";
        	HashMap<Object, Object> params = new HashMap<Object, Object>();
        	params.put("video_id", video_id+"");
        	params.put("size", size);
        	//System.out.println("");
        	return makeRequest(api,params);
        }
        
        /**
         * 视频小时数据
         * @param  string date 日期，格式为：yyyy-mm-dd
         * @param  int hour 小时�?0-23之间
         * @param  int video_id 视频ID
         * @param  int index �?始页索引，默认�?�为1
         * @param  int size 分页大小，默认�?�为10，最大�?�为100
         * @return String
         * @throws Exception 
         */
        public String dataVideoHour(String date,int hour,int video_id,int index,int size) throws Exception{
        	String api = "data.video.hour";
        	HashMap<Object, Object> params = new HashMap<Object, Object>();
        	params.put("date", date);
        	if(hour >=0 && hour <=23){
        		params.put("hour", hour+"");
        	}
        	if(video_id>0){
        		params.put("video_id", video_id+"");
        	}
        	if(index>0){
        		params.put("index", index+"");
        	}
        	if(size>0){
        		params.put("size", size+"");
        	}        	
        	return makeRequest(api,params);
        }
        
        /**
         * 视频小时数据
         * @param  string date 日期，格式为：yyyy-mm-dd
         * @param  int hour 小时�?0-23之间
         * @param  int video_id 视频ID
         * @param  int index �?始页索引，默认�?�为1
         * @return String
         * @throws Exception 
         */
        public String dataVideoHour(String date,int hour,int video_id,int index) throws Exception{
        	return dataVideoHour(date,hour,video_id,index,0);
        }
        
        /**
         * 视频小时数据
         * @param  string date 日期，格式为：yyyy-mm-dd
         * @param  int hour 小时�?0-23之间
         * @param  int video_id 视频ID
         * @return String
         * @throws Exception 
         */
        public String dataVideoHour(String date,int hour,int video_id) throws Exception{
        	return dataVideoHour(date,hour,video_id,0,0);
        }
        
        /**
         * 视频小时数据
         * @param  string date 日期，格式为：yyyy-mm-dd
         * @param  int hour 小时�?0-23之间
         * @return String
         * @throws Exception 
         */
        public String dataVideoHour(String date,int hour) throws Exception{
        	return dataVideoHour(date,hour,0,0,0);
        }
        
        /**
         * 视频小时数据
         * @param  string date 日期，格式为：yyyy-mm-dd
         * @return String
         * @throws Exception 
         */
        public String dataVideoHour(String date) throws Exception{
        	return dataVideoHour(date,-1,0,0,0);
        }
        
        /**
         * 视频天数�?
         * @param  string start_date �?始日期，格式为：yyyy-mm-dd 
         * @param  string end_date 结束日期，格式为：yyyy-mm-dd
         * @param  int video_id 视频ID，不输入该参数将返回�?有视频的数据
         * @param  int index �?始页索引，默认�?�为1
         * @param  int size 分页大小，默认�?�为10，最大�?�为100
         * @return String
         * @throws Exception 
         */
        public String dataVideoDate(String start_date,String end_date,int video_id,int index,int size) throws Exception{
        	String api = "data.video.date";
        	HashMap<Object, Object> params = new HashMap<Object, Object>();
        	params.put("start_date", start_date);
        	params.put("end_date", end_date);
        	if(video_id>0){
        		params.put("video_id", video_id+"");  
        	}
        	if(index>0){
        		params.put("index", index+"");
        	}      	
        	if(size>0){
        		params.put("size", size+"");
        	}        	
        	return makeRequest(api,params);
        }
        
        /**
         * 视频天数�?
         * @param  string start_date �?始日期，格式为：yyyy-mm-dd 
         * @param  string end_date 结束日期，格式为：yyyy-mm-dd
         * @param  int video_id 视频ID，不输入该参数将返回�?有视频的数据
         * @param  int index �?始页索引，默认�?�为1
         * @return String
         * @throws Exception 
         */
        public String dataVideoDate(String start_date,String end_date,int video_id,int index) throws Exception{
        	return dataVideoDate(start_date,end_date,video_id,index,0);
        }
        
        /**
         * 视频天数�?
         * @param  string start_date �?始日期，格式为：yyyy-mm-dd 
         * @param  string end_date 结束日期，格式为：yyyy-mm-dd
         * @param  int video_id 视频ID，不输入该参数将返回�?有视频的数据
         * @return String
         * @throws Exception 
         */
        public String dataVideoDate(String start_date,String end_date,int video_id) throws Exception{
        	return dataVideoDate(start_date,end_date,video_id,0,0);
        }
        
        /**
         * 视频天数�?
         * @param  string start_date �?始日期，格式为：yyyy-mm-dd 
         * @param  string end_date 结束日期，格式为：yyyy-mm-dd
         * @return String
         * @throws Exception 
         */
        public String dataVideoDate(String start_date,String end_date) throws Exception{
        	return dataVideoDate(start_date,end_date,0,0,0);
        }
        
        /**
         * �?有数�?
         * @param  string start_date �?始日期，格式为：yyyy-mm-dd
         * @param  string end_date 结束日期，格式为：yyyy-mm-dd
         * @param  int index �?始页索引，默认�?�为1
         * @param  int size 分页大小，默认�?�为10，最大�?�为100
         * @return String
         * @throws Exception 
         */
        public String dataTotalDate(String start_date,String end_date,int index,int size) throws Exception{
        	String api = "data.total.date";
        	HashMap<Object, Object> params = new HashMap<Object, Object>();
        	params.put("start_date", start_date);
        	params.put("end_date", end_date);
        	if(index>0){
        		params.put("index", index+"");
        	}
        	if(size>0){
        		params.put("size", size+"");
        	}        	
        	return makeRequest(api,params);
        }
        
        /**
         * �?有数�?
         * @param  string start_date �?始日期，格式为：yyyy-mm-dd
         * @param  string end_date 结束日期，格式为：yyyy-mm-dd
         * @param  int index �?始页索引，默认�?�为1
         * @return String
         * @throws Exception 
         */
        public String dataTotalDate(String start_date,String end_date,int index) throws Exception{      	
        	return dataTotalDate(start_date,end_date,index,0);
        }
        
        /**
         * �?有数�?
         * @param  string start_date �?始日期，格式为：yyyy-mm-dd
         * @param  string end_date 结束日期，格式为：yyyy-mm-dd
         * @return String
         * @throws Exception 
         */
        public String dataTotalDate(String start_date,String end_date) throws Exception{      	
        	return dataTotalDate(start_date,end_date,0,0);
        }
        
        /**
         * 获取视频播放接口
         * @param string uu 用户唯一标识码，由乐视网统一分配并提�?
         * @param string vu 视频唯一标识�?
         * @param string type 接口类型：url表示播放URL地址；js表示JavaScript代码；flash表示视频地址；html表示HTML代码
         * @param string pu 播放器唯�?标识�?
         * @param int auto_play 是否自动播放�?1表示自动播放�?0表示不自动播放�?�默认�?�由双方事先约定
         * @param int width 播放器宽�?
         * @param int height 播放器高�? 
         * @return String
         */
        public String videoGetPlayinterface(String uu,String vu,String type,String pu,int auto_play,int width,int height){
        	HashMap<Object, Object> params = new HashMap<Object, Object>();
        	params.put("uu", uu);
        	params.put("vu", vu);
        	if(pu.length()>0){
        		params.put("pu", pu);
        	}        	
        	if(auto_play != -1){
        		params.put("auto_play", auto_play+"");
        	}
        	if(width>0){
        		params.put("width", width+"");
        	}else{
        		width = 800;
        	}
        	if(height>0){
        		params.put("height", height+"");
        	}else{
        		height = 450;
        	}
        	String queryString = mapToQueryString(params);
        	String jsonString = mapToJsonString(params);
        	String response = "";
        	if(type == "url"){
        		response = "http://yuntv.letv.com/bcloud.html?" + queryString; 
        	}
        	if (type == "js"){
        		response = "<script type=\"text/javascript\">var letvcloud_player_conf = "+jsonString+";</script><script type=\"text/javascript\" src=\"http://yuntv.letv.com/bcloud.js\"></script>";
        	}
        	if (type == "flash"){
        		response = "http://yuntv.letv.com/bcloud.swf?"+queryString;
        	}
            if (type == "html"){
        		response = "<embed src=\"http://yuntv.letv.com/bcloud.swf\" allowFullScreen=\"true\" quality=\"high\" width=\""+width+"\" height=\""+height+"\" align=\"middle\" allowScriptAccess=\"always\" flashvars=\""+queryString+"\" type=\"application/x-shockwave-flash\"></embed>";
        	}
        	return response;
        }
        
        /**
         * 获取视频播放接口
         * @param string uu 用户唯一标识码，由乐视网统一分配并提�?
         * @param string vu 视频唯一标识�?
         * @param string type 接口类型：url表示播放URL地址；js表示JavaScript代码；flash表示视频地址；html表示HTML代码
         * @param string pu 播放器唯�?标识�?
         * @param int auto_play 是否自动播放�?1表示自动播放�?0表示不自动播放�?�默认�?�由双方事先约定
         * @param int width 播放器宽�?
         * @return String
         */
        public String videoGetPlayinterface(String uu,String vu,String type,String pu,int auto_play,int width){
        	return videoGetPlayinterface(uu,vu,type,pu,auto_play,width,0);
        }
        
        /**
         * 获取视频播放接口
         * @param string uu 用户唯一标识码，由乐视网统一分配并提�?
         * @param string vu 视频唯一标识�?
         * @param string type 接口类型：url表示播放URL地址；js表示JavaScript代码；flash表示视频地址；html表示HTML代码
         * @param string pu 播放器唯�?标识�?
         * @param int auto_play 是否自动播放�?1表示自动播放�?0表示不自动播放�?�默认�?�由双方事先约定
         * @return String
         */
        public String videoGetPlayinterface(String uu,String vu,String type,String pu,int auto_play){
        	return videoGetPlayinterface(uu,vu,type,pu,auto_play,0,0);
        }
        
        /**
         * 获取视频播放接口
         * @param string uu 用户唯一标识码，由乐视网统一分配并提�?
         * @param string vu 视频唯一标识�?
         * @param string type 接口类型：url表示播放URL地址；js表示JavaScript代码；flash表示视频地址；html表示HTML代码
         * @param string pu 播放器唯�?标识�?
         * @return String
         */
        public String videoGetPlayinterface(String uu,String vu,String type,String pu){
        	return videoGetPlayinterface(uu,vu,type,pu,-1,0,0);
        }
        
        /**
         * 获取视频播放接口
         * @param string uu 用户唯一标识码，由乐视网统一分配并提�?
         * @param string vu 视频唯一标识�?
         * @param string type 接口类型：url表示播放URL地址；js表示JavaScript代码；flash表示视频地址；html表示HTML代码
         * @return String
         */
        public String videoGetPlayinterface(String uu,String vu,String type){
        	return videoGetPlayinterface(uu,vu,type,"",-1,0,0);
        }
		
		/** 
		 * 构�?�云视频Sign
		 * @param params 业务参数
		 * @return string
		 * @throws Exception 
		 */
		public String generateSign(HashMap<Object, Object> params) throws Exception{
	    	Object[] array = params.keySet().toArray();	    	
	    	java.util.Arrays.sort(array);
			String keyStr = "";
			for(int i=0;i<array.length;i++){
				String key = array[i].toString();
				keyStr += key+params.get(key);
			}
			keyStr += this.secretKey;
			return MD5.md5(keyStr);
		}
		
		//构�?�请求串
		public String makeRequest(String api,HashMap<Object, Object> params) throws Exception{
			params.put("user_unique", this.userUnique);
			//获取时间�?
	    	Date date = new Date();
	    	long time = date.getTime();
	    	String ts = time + "";
			params.put("timestamp", ts);	
			params.put("ver", this.apiVersion);
			params.put("format", this.format);
			params.put("api", api);
			params.put("isdrm", 1+"");
	    	//签名	
			params.put("sign", generateSign(params));	
	    	//构�?�请求URL
	    	String resurl = "";
	    	resurl += this.restUrl + "?" + mapToQueryString(params);
	    	//System.out.println(resurl);
	    	return doGet(resurl);
		}
		
		//GET请求
		public String doGet(String url) throws UnsupportedEncodingException {			
			HttpClient client = new HttpClient();	//实例化httpClient
			HttpMethod method = new GetMethod(url);	//
			String responseContent = "";
			try {
				client.executeMethod(method);		//执行		

				InputStream jsonStr;

				jsonStr = method.getResponseBodyAsStream();			
					
				ByteArrayOutputStream   baos   =   new   ByteArrayOutputStream(); 
				
		        int   i=-1; 
		        while((i=jsonStr.read())!=-1){ 
		        	baos.write(i); 
		        }
		        
		        responseContent = baos.toString(); 
		        
		        jsonStr.close();
		        baos.close();
		        method.releaseConnection();
		        
			} catch (HttpException e) {
				
				e.printStackTrace();
				
			} catch (IOException e) {
				
				e.printStackTrace();
				
			}
			
			if (responseContent != null) {
			    //用默认字符编码解码字符串�?
				byte[] bs = responseContent.getBytes();
				//用新的字符编码生成字符串
				return new String(bs, "UTF-8");
			}
		    return null;
		}
		//POST上传文件
		public String doUploadFile(File file, String url) throws IOException{
			    String response = "";
			    if (!file.exists()) {  
		            return "file not exists";  
		        }  
		        PostMethod postMethod = new PostMethod(url);  
		        try {  
		            //FilePart：用来上传文件的�?  
		        FilePart fp = new FilePart("filedata", file);  
		            Part[] parts = {fp};  
		  
		            //对于MIME类型的请求，httpclient建议全用MulitPartRequestEntity进行包装  
		            MultipartRequestEntity mre = new MultipartRequestEntity(parts, postMethod.getParams());  
		            postMethod.setRequestEntity(mre);  
		            HttpClient client = new HttpClient();  
		            client.getHttpConnectionManager().getParams().setConnectionTimeout(50000);// 设置连接时间  
		            int status = client.executeMethod(postMethod);  
		            if (status == HttpStatus.SC_OK) {  
		            	//response =  postMethod.getResponseBodyAsString();
		            	InputStream inputStream = postMethod.getResponseBodyAsStream();  
		            	BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));  
		            	StringBuffer stringBuffer = new StringBuffer();  
		            	String str= "";  
		            	while((str = br.readLine()) != null){  
		            		stringBuffer .append(str);  
		            	} 
		            	response = stringBuffer.toString();
		            } else {  
		                response =  "fail";  
		            }  
		        } catch (Exception e) {  
		            e.printStackTrace();  
		        } finally {  
		            //释放连接  
		            postMethod.releaseConnection();  
		        }	
		        return response;
		}
		
		
	    //�? map 中的参数及对应�?�转换为查询字符�?
	    private String mapToQueryString(HashMap<Object, Object> params){
	    	Object[] array = params.keySet().toArray();
	    	
	    	java.util.Arrays.sort(array);
	    	String str = "";
	    	for(int i = 0; i < array.length;i++){
	    		String key = array[i].toString();
	    		try {
		    		if(i!=array.length-1){
		    			
							str += key+"="+URLEncoder.encode((String)params.get(key),"UTF-8")+"&";
						
		    		}else{
		    			str += key+"="+URLEncoder.encode((String)params.get(key),"UTF-8");
		    		}
	    		} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
	    	}
	    	return str;
	    }
	    
	    //�? map 中的参数及对应�?�转换为json字符�?
	    private String mapToJsonString(HashMap<Object, Object> params){
	    	Object[] array = params.keySet().toArray();
	    	
	    	java.util.Arrays.sort(array);
	    	String str = "";
	    	for(int i = 0; i < array.length;i++){
	    		String key = array[i].toString();
	    		if(i!=array.length-1){
	    			str += "\"" + key+ "\""+":"+"\""+params.get(key)+"\""+",";
	    		}else{
	    			str += "\"" + key+ "\""+":"+"\""+params.get(key)+"\"";
	    		}
	    	}
	    	str = "{"+str+"}";
	    	return str;
	    }
	    
	    //MD5加密�?
	    private static class MD5 {
	        private static char md5Chars[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

	        public static String md5(String str) throws Exception {
	            MessageDigest md5 = getMD5Instance();
	            md5.update(str.getBytes("UTF-8"));
	            byte[] digest = md5.digest();
	            char[] chars = toHexChars(digest);
	            return new String(chars);
	        }

	        private static MessageDigest getMD5Instance() {
	            try {
	                return MessageDigest.getInstance("MD5");
	            } catch (NoSuchAlgorithmException ignored) {
	                throw new RuntimeException(ignored);
	            }
	        }

	        private static char[] toHexChars(byte[] digest) {
	            char[] chars = new char[digest.length * 2];
	            int i = 0;
	            for (byte b : digest) {
	                char c0 = md5Chars[(b & 0xf0) >> 4];
	                chars[i++] = c0;
	                char c1 = md5Chars[b & 0xf];
	                chars[i++] = c1;
	            }
	            return chars;
	        }
	    }
			
}
