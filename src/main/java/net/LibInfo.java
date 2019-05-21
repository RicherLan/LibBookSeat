package net;

import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import server.StartServer;

public class LibInfo {

	public static Map<String, Integer> roomMap = new HashMap<String, Integer>();
	public static Map<Integer, Map<Integer, Integer>> seatMap = new HashMap<Integer, Map<Integer,Integer>>();
	
	
	public static void initRoomInfo() {
		String json = "{\"status\":\"success\",\"data\":[{\"roomId\":41,\"room\":\"第一阅览室\",\"floor\":2,\"maxHour\":4,\"reserved\":5,\"inUse\":123,\"away\":0,\"totalSeats\":136,\"free\":6},{\"roomId\":12,\"room\":\"第二阅览室中区\",\"floor\":3,\"maxHour\":4,\"reserved\":2,\"inUse\":43,\"away\":0,\"totalSeats\":48,\"free\":3},{\"roomId\":11,\"room\":\"第二阅览室北区\",\"floor\":3,\"maxHour\":4,\"reserved\":0,\"inUse\":181,\"away\":0,\"totalSeats\":196,\"free\":13},{\"roomId\":13,\"room\":\"第二阅览室南区\",\"floor\":3,\"maxHour\":4,\"reserved\":2,\"inUse\":143,\"away\":0,\"totalSeats\":172,\"free\":27},{\"roomId\":15,\"room\":\"第十一阅览室中区\",\"floor\":3,\"maxHour\":4,\"reserved\":0,\"inUse\":39,\"away\":0,\"totalSeats\":48,\"free\":5},{\"roomId\":14,\"room\":\"第十一阅览室北区\",\"floor\":3,\"maxHour\":4,\"reserved\":2,\"inUse\":158,\"away\":0,\"totalSeats\":188,\"free\":26},{\"roomId\":16,\"room\":\"第十一阅览室南区\",\"floor\":3,\"maxHour\":4,\"reserved\":0,\"inUse\":137,\"away\":0,\"totalSeats\":156,\"free\":17},{\"roomId\":18,\"room\":\"第三阅览室中区\",\"floor\":4,\"maxHour\":4,\"reserved\":0,\"inUse\":42,\"away\":0,\"totalSeats\":48,\"free\":5},{\"roomId\":17,\"room\":\"第三阅览室北区\",\"floor\":4,\"maxHour\":4,\"reserved\":1,\"inUse\":119,\"away\":0,\"totalSeats\":148,\"free\":28},{\"roomId\":19,\"room\":\"第三阅览室南区\",\"floor\":4,\"maxHour\":4,\"reserved\":1,\"inUse\":101,\"away\":0,\"totalSeats\":120,\"free\":18},{\"roomId\":21,\"room\":\"第十阅览室中区\",\"floor\":4,\"maxHour\":4,\"reserved\":1,\"inUse\":33,\"away\":0,\"totalSeats\":48,\"free\":14},{\"roomId\":22,\"room\":\"第十阅览室南区\",\"floor\":4,\"maxHour\":4,\"reserved\":5,\"inUse\":126,\"away\":0,\"totalSeats\":164,\"free\":28},{\"roomId\":35,\"room\":\"第九阅览室中区\",\"floor\":5,\"maxHour\":4,\"reserved\":1,\"inUse\":41,\"away\":0,\"totalSeats\":48,\"free\":6},{\"roomId\":34,\"room\":\"第九阅览室北区\",\"floor\":5,\"maxHour\":4,\"reserved\":4,\"inUse\":166,\"away\":0,\"totalSeats\":195,\"free\":24},{\"roomId\":36,\"room\":\"第九阅览室南区\",\"floor\":5,\"maxHour\":4,\"reserved\":4,\"inUse\":151,\"away\":0,\"totalSeats\":172,\"free\":15},{\"roomId\":32,\"room\":\"第四阅览室中区\",\"floor\":5,\"maxHour\":4,\"reserved\":2,\"inUse\":39,\"away\":0,\"totalSeats\":48,\"free\":5},{\"roomId\":31,\"room\":\"第四阅览室北区\",\"floor\":5,\"maxHour\":4,\"reserved\":4,\"inUse\":133,\"away\":0,\"totalSeats\":148,\"free\":9},{\"roomId\":33,\"room\":\"第四阅览室南区\",\"floor\":5,\"maxHour\":4,\"reserved\":0,\"inUse\":146,\"away\":0,\"totalSeats\":164,\"free\":15},{\"roomId\":38,\"room\":\"第五阅览室中区\",\"floor\":6,\"maxHour\":15,\"reserved\":0,\"inUse\":48,\"away\":0,\"totalSeats\":48,\"free\":0},{\"roomId\":8,\"room\":\"第五阅览室北区\",\"floor\":6,\"maxHour\":15,\"reserved\":0,\"inUse\":59,\"away\":0,\"totalSeats\":59,\"free\":0},{\"roomId\":37,\"room\":\"第五阅览室南区\",\"floor\":6,\"maxHour\":15,\"reserved\":1,\"inUse\":171,\"away\":0,\"totalSeats\":172,\"free\":0},{\"roomId\":47,\"room\":\"第八阅览室中区\",\"floor\":6,\"maxHour\":15,\"reserved\":0,\"inUse\":48,\"away\":0,\"totalSeats\":48,\"free\":0},{\"roomId\":9,\"room\":\"第八阅览室北区\",\"floor\":6,\"maxHour\":15,\"reserved\":0,\"inUse\":204,\"away\":0,\"totalSeats\":204,\"free\":0},{\"roomId\":40,\"room\":\"第八阅览室南区\",\"floor\":6,\"maxHour\":15,\"reserved\":0,\"inUse\":175,\"away\":0,\"totalSeats\":176,\"free\":0},{\"roomId\":27,\"room\":\"第七阅览室中区\",\"floor\":7,\"maxHour\":15,\"reserved\":0,\"inUse\":48,\"away\":0,\"totalSeats\":48,\"free\":0},{\"roomId\":46,\"room\":\"第七阅览室北区\",\"floor\":7,\"maxHour\":15,\"reserved\":0,\"inUse\":131,\"away\":0,\"totalSeats\":132,\"free\":1},{\"roomId\":28,\"room\":\"第七阅览室南区\",\"floor\":7,\"maxHour\":15,\"reserved\":1,\"inUse\":106,\"away\":0,\"totalSeats\":108,\"free\":1},{\"roomId\":24,\"room\":\"第六阅览室中区\",\"floor\":7,\"maxHour\":15,\"reserved\":0,\"inUse\":48,\"away\":0,\"totalSeats\":48,\"free\":0},{\"roomId\":23,\"room\":\"第六阅览室北区\",\"floor\":7,\"maxHour\":15,\"reserved\":1,\"inUse\":129,\"away\":0,\"totalSeats\":132,\"free\":2},{\"roomId\":25,\"room\":\"第六阅览室南区\",\"floor\":7,\"maxHour\":15,\"reserved\":0,\"inUse\":107,\"away\":0,\"totalSeats\":108,\"free\":1}],\"message\":\"\",\"code\":\"0\"}";
		
		JSONObject jsonObject = JSONObject.fromObject(json);
		String data = jsonObject.getString("data");
		JSONArray jsonArray = JSONArray.fromObject(data);
		for(int i=0;i<jsonArray.size();++i) {
			json = jsonArray.getString(i);
			jsonObject = JSONObject.fromObject(json);
			String room = jsonObject.getString("room");
			int roomid = jsonObject.getInt("roomId");
			int floor = jsonObject.getInt("floor");
			//if(floor==6||floor==7) {
				System.out.println(room+"   "+roomid);
			//}
			roomMap.put(room, roomid);
			
		}
	}
	
	public static void initSeatId() {
		
		
		
		for(int roomid:roomMap.values()) {
			Map<Integer, Integer> seatidsMap = new HashMap<Integer, Integer>();
			//获得第2阅览室的座位信息 
			MyNetUtil util = new MyNetUtil(roomid,seatidsMap);
			util.getRoomSeatInfo();	
			
			seatMap.put(roomid, seatidsMap);
		}
		
		
	}
	
	
	
	
	
	public static void main(String[] args) {
		StartServer.trustEveryone();
		initRoomInfo();
		
//		initSeatId();
//		
//		for(int roomid:seatMap.keySet()) {
//			Map<Integer, Integer> map = seatMap.get(roomid);
//			int sum = 0;
//			System.out.println(roomid+"   5555555555555555555555");
//			for(Integer seatid:map.keySet()) {
//				++sum;
//				System.out.println(seatid+" "+map.get(seatid));
//				if(sum==4) {
//					break;
//				}
//			}
//		}
		
	}
	
	
}
