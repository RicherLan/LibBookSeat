package net;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.jsoup.Connection;
import org.jsoup.Jsoup;

import net.sf.json.JSONObject;

import org.jsoup.Connection.Method;

import org.jsoup.Connection.Response;

import object.BookSeat;
import server.StartServer;
import util.TimeUtil;

/*
 * 	预约执行对象
 */
public class StartBook {

	String token;
	BookSeat bookSeat;
	boolean ok = false;

	String loginBody="";
	String bookBody="";
	
	//尝试3次  直到成功为止
	//int MaxTry = 3;
	
	//不断尝试预约一直到直到截至时间
	int endhour = 7;
	int endminute = 1;
	int endsecond = 3;
	
	/*
	 * 	预约结果   0代表未开始  1代表成功   -1代表失败
	 */
	int bookStatus = 0;
	int isseatusing = 1;   //当前正在预约第几个座位
	
	

	public StartBook(BookSeat bookSeat) {
		this.bookSeat = bookSeat;
	}

	public void start() {
		
		try {
			Thread.currentThread().sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		int nowhour =  calendar.get(Calendar.HOUR_OF_DAY);
        int nowminute = calendar.get(Calendar.MINUTE);
		int nowsecond = calendar.get(Calendar.SECOND);
		
        if(nowhour>=endhour&&nowminute >= endminute&&nowsecond>=endsecond) {
        	System.out.println("开始预约 "+bookSeat.getName()+" 账号 "+bookSeat.getCount()+" "+"要预约的时间点为 "+bookSeat.getStarttime()+"  "+bookSeat.getEndtime()+" "+"当前的时间为 "+bookSeat.getTimestr()+" "+TimeUtil.getNowTime()+"\n"+loginBody+"\n"+bookBody);			
			System.out.println("11111111111111111111111111111");
        	return;
        }
        
		if (ok) {
			System.out.println("222222222222222222222222222222222");
			System.out.println("开始预约 "+bookSeat.getName()+" 账号 "+bookSeat.getCount()+" "+"要预约的时间点为 "+bookSeat.getStarttime()+"  "+bookSeat.getEndtime()+" "+"当前的时间为 "+bookSeat.getTimestr()+" "+TimeUtil.getNowTime()+"\n"+loginBody+"\n"+bookBody);			
			return;
			
		}
		
//		if(messageString!=null&&messageString.contains("预约失败，请尽快选择其他时段或座位")) {
//			bookStatus = 0;
//			isseatusing = 2;
//		}
		if(bookStatus==-1&&isseatusing==2) {
			System.out.println("222222222222222222222222222222222");
			System.out.println("开始预约 "+bookSeat.getName()+" 账号 "+bookSeat.getCount()+" "+"要预约的时间点为 "+bookSeat.getStarttime()+"  "+bookSeat.getEndtime()+" "+"当前的时间为 "+bookSeat.getTimestr()+" "+TimeUtil.getNowTime()+"\n"+loginBody+"\n"+bookBody);			
			return;
		}
		
		//--MaxTry;
		String rs = login();
		
		if (rs.equals("success")) {
			rs = bookSeat();
			//System.out.println(bookSeat.getName()+loginBody+"\n"+bookSeat.getName()+bookBody);
			if (rs.equals("success")) {
				ok = true;
				System.out.println("开始预约 "+bookSeat.getName()+" 账号 "+bookSeat.getCount()+" "+"要预约的时间点为 "+bookSeat.getStarttime()+"  "+bookSeat.getStarttime()+"  "+bookSeat.getEndtime()+" "+"当前的时间为 "+TimeUtil.getNowTime()+"\n"+loginBody+"\n"+bookBody);			
				return;
			} else {
				
				start();
			}
		} else {
			start();
		}

	}

	
	public String login() {

		Map<String, String> header = new HashMap<String, String>();

//		header.put("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:61.0) Gecko/20100101 Firefox/61.0");
//		header.put("Connection", "keep-Alive");
//		header.put("Host", "seat.ujn.edu.cn:8443");
//		header.put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
		
		header.put("x-request-id","cfc58ab0-4800-11e8-ae09-69ee3a8bc273");
		header.put("x-request-date","1526418378459");
		header.put("x-hmac-request-key","1d8bf16de488f87c9a804aa143d7de1c4e30864c5bb669245ba315578c1cc5a8");
		
		Map<String, String> datas = new HashMap<String, String>();

		datas.put("username", bookSeat.getCount());
		datas.put("password", bookSeat.getPassword());
		
		// https://seat.ujn.edu.cn:8443/rest/auth?username=220171511055&password=097229
		String urlString = URLManager.loginUrl + bookSeat.getCount() + "&password=" + bookSeat.getPassword();

		Connection con = Jsoup.connect(urlString);// 获取连接
		con.headers(header);

		String loginrString = "error";
		
		try {
			Response rs = con.ignoreContentType(true).method(Method.POST).data(datas).execute();
			loginBody = rs.body();
//			System.out.println(loginBody);
//			{"status":"success","data":{"token":"85SHMI1G6F03141302"},"code":"0","message":""}
			JSONObject jsonObject = JSONObject.fromObject(loginBody);
			loginrString = jsonObject.getString("status");
		
			if(loginrString.equals("success")) {
				String dataString = jsonObject.getString("data");
				jsonObject = JSONObject.fromObject(dataString);
				token = jsonObject.getString("token");
			}else {
				//System.out.println(bodyString);
			}
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return loginrString;
	}

	
	public String bookSeat() {
		
		Map<String, String> header = new HashMap<String, String>();

//		header.put("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:61.0) Gecko/20100101 Firefox/61.0");
//		header.put("Connection", "keep-Alive");
//		header.put("Host", "seat.ujn.edu.cn:8443");
//		header.put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
//		header.put("token", token);
		
		header.put("x-request-id","cfc58ab0-4800-11e8-ae09-69ee3a8bc273");
		header.put("x-request-date","1526418378459");
		header.put("x-hmac-request-key","1d8bf16de488f87c9a804aa143d7de1c4e30864c5bb669245ba315578c1cc5a8");
		
		Map<String, String> datas = new HashMap<String, String>();

		int seatid = bookSeat.getSeatid();
		int seatcode = bookSeat.getSeatcode();
		
		int seatid2 = bookSeat.getSeatid2();
		int seatcode2 = bookSeat.getSeatcode2();
		
		datas.put("t", "1");
		datas.put("startTime",bookSeat.getStarttime()*60+"" );
		datas.put("endTime", bookSeat.getEndtime()*60+"");
		if(isseatusing==2) {
			datas.put("seat",seatcode2+"" );
		}else {
			datas.put("seat",seatcode+"" );
		}
		
		datas.put("date",bookSeat.getTimestr() );
		datas.put("t2","2" );
		
		String urlString = URLManager.freeBookString+"?token="+token ;

		Connection con = Jsoup.connect(urlString);// 获取连接
		con.headers(header);

		String bookSeatRs = "error";
		
		try {
			Response rs = con.ignoreContentType(true).method(Method.POST).data(datas).execute();
			bookBody = rs.body();
//			{"status":"success","data":{"token":"85SHMI1G6F03141302"},"code":"0","message":""}
			JSONObject jsonObject = JSONObject.fromObject(bookBody);
			bookSeatRs = jsonObject.getString("status");
			String messageString = jsonObject.getString("message");
			if(messageString!=null&&messageString.contains("已有1个有效预约")) {
				bookSeatRs = "success";
			}
			
			if(messageString!=null&&messageString.contains("预约失败，请尽快选择其他时段或座位")) {
				if(isseatusing==1) {
					bookStatus = 0;
					isseatusing = 2;
				}else {
					bookStatus = -1;
					isseatusing = 2;
				}
				
			}
			
			if(bookSeatRs.equals("success")) {
				ok = true;
//				String dataString = jsonObject.getString("data");
//				jsonObject = JSONObject.fromObject(dataString);
//				token = jsonObject.getString("token");
			}else {
//				System.out.println(bookBody);
			}
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return bookSeatRs;

		
	}

}
