package net;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.Connection.Method;
import org.jsoup.Connection.Response;

import net.sf.json.JSONObject;
import object.BookSeat;
import object.SignInSeat;
import server.StartServer;
import util.TimeUtil;

public class StartSign {

	String loginBody="";
	String signInBody = "";
	
	String token;
	SignInSeat signInSeat;
	boolean ok = false;
	int MaxTry = 3;
	Timer timer;
	
	public StartSign(SignInSeat signInSeat) {
		this.signInSeat = signInSeat;
	}
	
	public void start() {
		
		startTimer();
		
	}
	
	//开启定时器
	public void startTimer() {
		
		timer=new Timer();
		int hour = signInSeat.getStarttime()-1;
		int minute = 18;
		
//		System.out.println(hour+"   5555555555555555555");
		
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
        int nowhour =  calendar.get(Calendar.HOUR_OF_DAY);
        int nowminute = calendar.get(Calendar.MINUTE);
        
        long diffTimeMills = TimeUtil.getDiffTimeMills(hour,minute,nowhour,nowminute);
       
        if(diffTimeMills<0) {
        	diffTimeMills=0;
        }
        
        timer.schedule(new TimerTask() {
			
			@Override
			public void run() {
				
				beginSign();
			}
		}, diffTimeMills);
        
	}
	
	public void beginSign() {
		

		if (MaxTry <= 0 || ok) {
			System.out.println("开始签到 "+signInSeat.getName()+" 账号 "+signInSeat.getCount()+" "+"要签到的时间点为 "+signInSeat.getStarttime()+"点到"+signInSeat.getEndtime()+"点 "+"当前的时间为 "+TimeUtil.getNowTime()+"\n"+loginBody+"\n"+signInBody);			

			return;
		}
		
		--MaxTry;
		String rs = login();
		if (rs.equals("success")) {
			rs = signIn();
			if (rs.equals("success")) {
				ok = true;
				System.out.println("开始签到 "+signInSeat.getName()+" 账号 "+signInSeat.getCount()+" "+"要签到的时间点为 "+signInSeat.getStarttime()+"点到"+signInSeat.getEndtime()+"点 "+"当前的时间为 "+TimeUtil.getNowTime()+"\n"+loginBody+"\n"+signInBody);			

				return;
			} else {
				beginSign();
			}
		} else {
			beginSign();
		}
		
	}
	
	public String login() {
		Map<String, String> header = new HashMap<String, String>();

		
		header.put("x-request-id","cfc58ab0-4800-11e8-ae09-69ee3a8bc273");
		header.put("x-request-date","1526418378459");
		header.put("x-hmac-request-key","1d8bf16de488f87c9a804aa143d7de1c4e30864c5bb669245ba315578c1cc5a8");
		
		
		Map<String, String> datas = new HashMap<String, String>();

		datas.put("username", signInSeat.getCount());
		datas.put("password", signInSeat.getPassword());

		// https://seat.ujn.edu.cn:8443/rest/auth?username=220171511055&password=097229
		String urlString = URLManager.loginUrl + signInSeat.getCount() + "&password=" + signInSeat.getPassword();

		Connection con = Jsoup.connect(urlString);// 获取连接
		con.headers(header);

		String loginrString = "error";
		
		try {
			Response rs = con.ignoreContentType(true).method(Method.POST).data(datas).execute();
			loginBody = rs.body();
//			{"status":"success","data":{"token":"85SHMI1G6F03141302"},"code":"0","message":""}
			JSONObject jsonObject = JSONObject.fromObject(loginBody);
			loginrString = jsonObject.getString("status");
			if(loginrString.equals("success")) {
				String dataString = jsonObject.getString("data");
				jsonObject = JSONObject.fromObject(dataString);
				token = jsonObject.getString("token");
			}else {
//				System.out.println(loginBody);
			}
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return loginrString;

		
	}
	
	
	//{"status":"success","data":{"id":5893515,"receipt":"1055-515-4","onDate":"2019 年 03 月 14 日","begin":"20 : 03","end":"22 : 00","location":"西校区4层407室区第三阅览室南区，座位号223"},"message":"成功登记入场","code":"0"}
	public String signIn() {
	
		Map<String, String> header = new HashMap<String, String>();
		
		header.put("x-request-id","cfc58ab0-4800-11e8-ae09-69ee3a8bc273");
		header.put("x-request-date","1526418378459");
		header.put("x-hmac-request-key","1d8bf16de488f87c9a804aa143d7de1c4e30864c5bb669245ba315578c1cc5a8");
		
		
		
		
		Connection con = Jsoup.connect(URLManager.signIn+"?token="+token);
		con.headers(header)
//			.proxy(StartServer.proxy)
			.ignoreContentType(true);

		String loginrString = "error";
		
		try {
			Response rs = con.execute();
			signInBody = rs.body();
			
			JSONObject jsonObject = JSONObject.fromObject(signInBody);
			loginrString = jsonObject.getString("status");
			if(loginrString.equals("success")) {
				ok = true;
			}else {
//				System.out.println(signInBody);
			}
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return loginrString;


		
	}
	
}
