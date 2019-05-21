package net;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.Connection.Method;
import org.jsoup.Connection.Response;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import server.StartServer;
import util.TimeUtil;

public class MyNetUtil {


	public static String proxyServer = "ujnlibrarytencent.chaochaogege.net";
	public static int proxyPort = 6000;
	public static Proxy proxy;
	
	
	String account = "220161222159";
	String password = "132633";
	String token;
	
	int maxTry=3;
	Integer roomid;
	Map<Integer, Integer> seatidsMap;
	
	public MyNetUtil(Integer roomid, Map<Integer, Integer> seatidsMap) {
		this.roomid = roomid;
		this.seatidsMap = seatidsMap;
	}
	
	public static void main(String[] args) {
		StartServer.trustEveryone();
		
		MyNetUtil myNetUtil =new MyNetUtil(41, new HashMap<Integer, Integer>());
//		myNetUtil.setProxy();
		myNetUtil.getRoomSeatInfo();
		
		for(Integer key:myNetUtil.seatidsMap.keySet()) {
			System.out.println(key+"  "+myNetUtil.seatidsMap.get(key));
		}
	}
	
	public void setProxy() {
		// 创建代理服务器地址对象
        InetSocketAddress addr = new InetSocketAddress(proxyServer, proxyPort);
        // 创建HTTP类型代理对象
        proxy = new Proxy(Proxy.Type.HTTP, addr);
        
	}
	
	
	public void getRoomSeatInfo() {
		
//		System.out.println(maxTry);
		if(maxTry<=0) {
			return;
		}
		--maxTry;
		seatidsMap.clear();
		
		String rString = login();
		if(rString.equals("success")) {
			String  result  = getRoomInfo();
			if(result.equals("success")) {
				return;
			}else {
				getRoomSeatInfo();
			}
		}else {
			
			getRoomSeatInfo();
		}
		
	}
	
	
	public String login() {

		Map<String, String> header = new HashMap<String, String>();

		header.put("x-request-id","cfc58ab0-4800-11e8-ae09-69ee3a8bc273");
		header.put("x-request-date","1526418378459");
		header.put("x-hmac-request-key","1d8bf16de488f87c9a804aa143d7de1c4e30864c5bb669245ba315578c1cc5a8");
		
		
		Map<String, String> datas = new HashMap<String, String>();

		datas.put("username", account);
		datas.put("password", password);

		// https://seat.ujn.edu.cn:8443/rest/auth?username=220171511055&password=097229
		String urlString = URLManager.loginUrl + account + "&password=" + password;

		Connection con = Jsoup.connect(urlString);// 获取连接
		con.headers(header)
//			.proxy(proxy)
			.ignoreContentType(true)
			.method(Method.GET);

		String loginrString = "error";
		
		try {
			Response rs = con.data(datas).execute();
			String bodyString = rs.body();
			System.out.println("获取阅览室座位信息  登陆结果 : "+bodyString);
//			{"status":"success","data":{"token":"85SHMI1G6F03141302"},"code":"0","message":""}
			JSONObject jsonObject = JSONObject.fromObject(bodyString);
			loginrString = jsonObject.getString("status");
			if(loginrString.equals("success")) {
				String dataString = jsonObject.getString("data");
				jsonObject = JSONObject.fromObject(dataString);
				token = jsonObject.getString("token");
				
			}
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return loginrString;
	}

	
	public String getRoomInfo() {
		
		String todayTimeStr = TimeUtil.getTodayTimeStr();
		//https://seat.ujn.edu.cn:8443/rest/v2/room/layoutByDate/41/2019-03-14
		String url = URLManager.getRoomInfo+roomid+"/"+todayTimeStr+"?token="+token;
		

		Map<String, String> header = new HashMap<String, String>();

		header.put("x-request-id","cfc58ab0-4800-11e8-ae09-69ee3a8bc273");
		header.put("x-request-date","1526418378459");
		header.put("x-hmac-request-key","1d8bf16de488f87c9a804aa143d7de1c4e30864c5bb669245ba315578c1cc5a8");
		

		Connection con = Jsoup.connect(url);// 获取连接
		con.headers(header);

		String loginrString = "error";
		
		try {
			Response rs = con.ignoreContentType(true)
					.execute();
			
			String bodyString = rs.body();
			
			JSONObject jsonObject = JSONObject.fromObject(bodyString);
			loginrString = jsonObject.getString("status");
			if(loginrString.equals("success")) {
				String dataString = jsonObject.getString("data");
				jsonObject = JSONObject.fromObject(dataString);
				String layout = jsonObject.getString("layout");
				jsonObject = JSONObject.fromObject(layout);
				
				JSONObject jsonObject2;
				for(String key : (Set<String>)jsonObject.keySet()) {
					String json = jsonObject.getString(key);
					jsonObject2 = JSONObject.fromObject(json);
					String typeString = jsonObject2.getString("type");
					if(typeString.equals("seat")) {
						int name = jsonObject2.getInt("name");
						int id = jsonObject2.getInt("id");
						
						seatidsMap.put(name, id);
						
					}
				}
				System.out.println("获取阅览室座位 信息 成功  ");
				return loginrString;
			}else {
				System.out.println("获取阅览室座位 信息 失败 ： "+bodyString);
			}
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return loginrString;

	}
	
}
