package test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import org.apache.commons.io.FileUtils;

import net.MyNetUtil;
import net.StartBook;
import net.StartSign;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import object.BookSeat;
import object.SignInSeat;
import server.StartServer;
import threadUtil.FixedThreadPool;
import util.MyFileUtil;
import util.TimeUtil;

public class SignTest {

	

	public static Map<Integer, Integer> seatidsMap = new HashMap<Integer, Integer>();
	
	public static void main(String[] args) {
		
		// 开启业务线程池
		FixedThreadPool.startThreadPool();
		// 跳过https认证
		StartServer.trustEveryone();

		StartServer.setProxy();
		// 获得第2阅览室的座位信息
		MyNetUtil util = new MyNetUtil(41, seatidsMap);
		util.getRoomSeatInfo();
		
		
		
		//配置文件
		String filepath = "C:\\Users\\qxc\\Desktop\\UserProperty.txt";
		String os = System.getProperty("os.name");
		if (os.toLowerCase().startsWith("win")) {

		} else {
			filepath = "/usr/myfile/bookseat/LibBookSeat/UserProperty.txt";
		}


		Vector<BookSeat> bookSeats = new Vector<BookSeat>();
		Vector<SignInSeat> signInSeats = new Vector<SignInSeat>();
		getBookSeatFromFile(filepath, bookSeats, signInSeats);

//		for (final BookSeat bookSeat : bookSeats) {
//			FixedThreadPool.threadPool.submit(new Runnable() {
//
//				public void run() {
//					StartBook startKBook = new StartBook(bookSeat);
//					startKBook.start();
//				}
//			});
//		}

		for (final SignInSeat signInSeat : signInSeats) {
			FixedThreadPool.threadPool.submit(new Runnable() {

				public void run() {
					StartSign startSign = new StartSign(signInSeat);
					startSign.start();
				}
			});
		}

	}

	public static void getBookSeatFromFile(String filepath,Vector<BookSeat> bookSeats,Vector<SignInSeat> signInSeats){
		try {
			
			bookSeats.clear();
			signInSeats.clear();
			
			String todayTimeString = TimeUtil.getTodayTimeStr();
			String tomorrowTimeString = TimeUtil.getTomorrowTimeStr();
			int todayWay = TimeUtil.getTodayWay();
			int tomorrowWay = TimeUtil.getTomorrowWay();
			
			
			String json = FileUtils.readFileToString(new File(filepath));
			JSONObject jsonObject  = JSONObject.fromObject(json);
			
			
			String loginString = jsonObject.getString("login");
			JSONArray jsonArray = JSONArray.fromObject(loginString);
			for(int i=0;i<jsonArray.size();++i) {
				json = jsonArray.getString(i);
				jsonObject = JSONObject.fromObject(json);
				String name = jsonObject.getString("name");
				int seatid = jsonObject.getInt("seatid");
//				int seatcode = seatidsMap.get(seatid);
				int seatcode = 1;
				int timeid = 1;
				int roomid = 1;	
				String passString = jsonObject.getString("pass");
				JSONArray jsonArray2 = JSONArray.fromObject(passString);
				for(int j=0;j<jsonArray2.size();++j) {
					json = jsonArray2.getString(j);
					jsonObject = JSONObject.fromObject(json);
					String user = jsonObject.getString("user");
					String pwd = jsonObject.getString("pwd");
			
					System.out.println(name+" "+user+" "+pwd);
					
					BookSeat bookSeat  = new BookSeat();
					bookSeat.setName(name);
					bookSeat.setCount(user);
					bookSeat.setPassword(pwd);
					bookSeat.setTimestr(tomorrowTimeString);
					int startTimeid = TimeUtil.getStartTimeCode(tomorrowWay, timeid);
					int endTimeid = TimeUtil.getEndTimeCode(tomorrowWay, timeid);
					bookSeat.setSeatid(seatid);
					bookSeat.setSeatcode(seatcode);
					bookSeat.setRoomid(roomid);
					bookSeat.setStarttime(startTimeid);
					bookSeat.setEndtime(endTimeid);
					
					bookSeats.add(bookSeat);
					
					SignInSeat signInSeat = new SignInSeat();
					signInSeat.setName(name);
					signInSeat.setCount(user);
					signInSeat.setPassword(pwd);
					signInSeat.setSeatid(seatid);
					signInSeat.setStarttime(14);
					signInSeat.setEndtime(21);
					
					signInSeats.add(signInSeat);
					
					++timeid;
					
				}
				
			}
			
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
	}

	
}
