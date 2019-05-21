package util;

import java.io.File;
import java.io.IOException;
import java.util.Vector;

import org.apache.commons.io.FileUtils;

import net.LibInfo;
import net.StartBook;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import object.BookSeat;
import object.SignInSeat;
import server.StartServer;

public class MyFileUtil {

	public static void getBookSeatFromFile(String filepath, Vector<BookSeat> bookSeats,
			Vector<SignInSeat> signInSeats) {
		try {

			bookSeats.clear();
			signInSeats.clear();

			String todayTimeString = TimeUtil.getTodayTimeStr();
			String tomorrowTimeString = TimeUtil.getTomorrowTimeStr();
			int todayWay = TimeUtil.getTodayWay();
			int tomorrowWay = TimeUtil.getTomorrowWay();
//			System.out.println(todayWay+" 5555555555555555");

			String json = FileUtils.readFileToString(new File(filepath), "UTF-8");
			// System.out.println(json);
			JSONObject jsonObject = JSONObject.fromObject(json);

			String loginString = jsonObject.getString("login");
			JSONArray jsonArray = JSONArray.fromObject(loginString);
			for (int i = 0; i < jsonArray.size(); ++i) {
				json = jsonArray.getString(i);
				jsonObject = JSONObject.fromObject(json);
				String name = jsonObject.getString("name");
				String seatidstr = jsonObject.getString("seatid");
				System.out.println(seatidstr);
				int enable = jsonObject.getInt("enable");

				//enable 为1代表生效且6楼和7楼预约晚上即16点到21点
				//enable 为2代表生效且6楼和7楼预约上午即9点到12点
				String roomname = jsonObject.getString("roomname");
				if (enable == 0) {
					continue;
				}

				roomname = convertRoomname(roomname);
				System.out.println(roomname);
				int roomid = LibInfo.roomMap.get(roomname);
				String[] seatids = seatidstr.split(" ");
				
				int seatid = Integer.parseInt(seatids[0]);
				int seatcode = LibInfo.seatMap.get(roomid).get(seatid);
				int seatid2 = -1;
				int seatcode2 = -1;
				if(seatids.length>1) {
					 seatid2 = Integer.parseInt(seatids[1]);
					 seatcode2 = LibInfo.seatMap.get(roomid).get(seatid2);
				}
				
				int timeid = 1;

				String passString = jsonObject.getString("pass");
				JSONArray jsonArray2 = JSONArray.fromObject(passString);
				for (int j = 0; j < jsonArray2.size(); ++j) {
					json = jsonArray2.getString(j);
					jsonObject = JSONObject.fromObject(json);
					String user = jsonObject.getString("user");
					String pwd = jsonObject.getString("pwd");

					//System.out.println(name + " " + user + " " + pwd);

					BookSeat bookSeat = new BookSeat();
					bookSeat.setName(name);
					bookSeat.setCount(user);
					bookSeat.setPassword(pwd);
					bookSeat.setTimestr(tomorrowTimeString);
					int startTimeid = TimeUtil.getStartTimeCode(tomorrowWay, timeid);
					int endTimeid = TimeUtil.getEndTimeCode(tomorrowWay, timeid);

					// 六楼和七楼可以预约1天
					/*
					 * 第五阅览室中区 38 第五阅览室北区 8 第五阅览室南区 37 第八阅览室中区 47 第八阅览室北区 9 第八阅览室南区 40 第七阅览室中区 27
					 * 第六阅览室中区 24 第七阅览室北区 46 第七阅览室南区 28 第六阅览室北区 23 第六阅览室南区 25
					 */
					if (roomid == 38 || roomid == 8 || roomid == 37 || roomid == 47 || roomid == 9 || roomid == 40
							|| roomid == 27 || roomid == 24 || roomid == 46 || roomid == 28 || roomid == 23
							|| roomid == 25) {
						if (tomorrowWay == 2) {
							
							startTimeid = 17;
							endTimeid = 21;
							if(enable==2) {
								startTimeid = 9;
								endTimeid = 12;
							}
							
						} else {
							startTimeid = 9;
							endTimeid = 21;
						}
					}

					bookSeat.setSeatid(seatid);
					bookSeat.setSeatcode(seatcode);
					bookSeat.setSeatid2(seatid2);
					bookSeat.setSeatcode2(seatcode2);
					bookSeat.setRoomid(roomid);
					bookSeat.setStarttime(startTimeid);
					bookSeat.setEndtime(endTimeid);

					bookSeats.add(bookSeat);

					
					startTimeid = TimeUtil.getStartTimeCode(todayWay, timeid);
					endTimeid = TimeUtil.getEndTimeCode(todayWay, timeid);

					if (roomid == 38 || roomid == 8 || roomid == 37 || roomid == 47 || roomid == 9 || roomid == 40
							|| roomid == 27 || roomid == 24 || roomid == 46 || roomid == 28 || roomid == 23
							|| roomid == 25) {
						if (tomorrowWay == 2) {
							
							startTimeid = 17;
							endTimeid = 21;
							if(enable==2) {
								startTimeid = 9;
								endTimeid = 12;
							}
							
						} else {
							startTimeid = 9;
							endTimeid = 21;
						}
					}
					
					SignInSeat signInSeat = new SignInSeat();
					signInSeat.setName(name);
					signInSeat.setCount(user);
					signInSeat.setPassword(pwd);
					signInSeat.setSeatid(seatid);
					signInSeat.setStarttime(startTimeid);
					signInSeat.setEndtime(endTimeid);

					signInSeats.add(signInSeat);

					++timeid;

				}

			}

		} catch (IOException e) {

			e.printStackTrace();
		}

	}

	public static String convertRoomname(String str) {
		String roomname = "";
		if (str.contains("1")) {
			roomname = "第一阅览室";
		}
		if (str.contains("2")) {
			roomname = "第二阅览室";
		}
		if (str.contains("3")) {
			roomname = "第三阅览室";
		}
		if (str.contains("4")) {
			roomname = "第四阅览室";
		}
		if (str.contains("5")) {
			roomname = "第五阅览室";
		}
		if (str.contains("6")) {
			roomname = "第六阅览室";
		}
		if (str.contains("7")) {
			roomname = "第七阅览室";
		}
		if (str.contains("8")) {
			roomname = "第八阅览室";
		}
		if (str.contains("9")) {
			roomname = "第九阅览室";
		}
		if (str.contains("10")) {
			roomname = "第十阅览室";
		}
		if (str.contains("11")) {
			roomname = "第十一阅览室";
		}

		if (str.contains("N")) {
			roomname += "北区";
		}
		if (str.contains("S")) {
			roomname += "南区";
		}
		if (str.contains("C")) {
			roomname += "中区";
		}

		return roomname;
	}

	public static void main(String[] args) {
		StartServer.trustEveryone();
		LibInfo.initRoomInfo();
		LibInfo.initSeatId();
		// 配置文件
		String filepath = "C:\\Users\\qxc\\Desktop\\UserProperty.txt";
		String os = System.getProperty("os.name");
		if (os.toLowerCase().startsWith("win")) {

		} else {
			filepath = "/usr/myfile/bookseat/UserProperty.txt";
		}

		Vector<BookSeat> bookSeats = new Vector<BookSeat>();
		Vector<SignInSeat> signInSeats = new Vector<SignInSeat>();
		MyFileUtil.getBookSeatFromFile(filepath, bookSeats, signInSeats);
		for (final BookSeat bookSeat : bookSeats) {

			System.out.println(bookSeat.getName() + " " + bookSeat.getRoomid() + " " + bookSeat.getCount() + " "
					+ bookSeat.getPassword());
			System.out.println(bookSeat.getSeatid() + " " + bookSeat.getSeatcode() + " " +bookSeat.getSeatid2() + " " + bookSeat.getSeatcode2() + " " +  bookSeat.getStarttime()
					+ "  " + bookSeat.getEndtime());

		}
		
//		for (final BookSeat bookSeat : bookSeats) {
//			if(bookSeat.getName().equals("xiaoxiao")) {
//				StartBook startBook = new StartBook(bookSeat);
//				startBook.start();
//			}
//		}
//		
	}

}
