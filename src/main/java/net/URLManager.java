package net;

public class URLManager {

	//https://seat.ujn.edu.cn:8443/rest/auth?username=220171511055&password=097229
	public static String loginUrl = "https://seat.ujn.edu.cn:8443/rest/auth?username=";
	
	
	//获得所有阅览室信息
	/*
	 * {"status":"success","data":[{"roomId":41,"room":"第一阅览室","floor":2,"maxHour":4,"reserved":5,"inUse":123,"away":0,"totalSeats":136,"free":6},{"roomId":12,"room":"第二阅览室中区","floor":3,"maxHour":4,"reserved":2,"inUse":43,"away":0,"totalSeats":48,"free":3},{"roomId":11,"room":"第二阅览室北区","floor":3,"maxHour":4,"reserved":0,"inUse":181,"away":0,"totalSeats":196,"free":13},{"roomId":13,"room":"第二阅览室南区","floor":3,"maxHour":4,"reserved":2,"inUse":143,"away":0,"totalSeats":172,"free":27},{"roomId":15,"room":"第十一阅览室中区","floor":3,"maxHour":4,"reserved":0,"inUse":39,"away":0,"totalSeats":48,"free":5},{"roomId":14,"room":"第十一阅览室北区","floor":3,"maxHour":4,"reserved":2,"inUse":158,"away":0,"totalSeats":188,"free":26},{"roomId":16,"room":"第十一阅览室南区","floor":3,"maxHour":4,"reserved":0,"inUse":137,"away":0,"totalSeats":156,"free":17},{"roomId":18,"room":"第三阅览室中区","floor":4,"maxHour":4,"reserved":0,"inUse":42,"away":0,"totalSeats":48,"free":5},{"roomId":17,"room":"第三阅览室北区","floor":4,"maxHour":4,"reserved":1,"inUse":119,"away":0,"totalSeats":148,"free":28},{"roomId":19,"room":"第三阅览室南区","floor":4,"maxHour":4,"reserved":1,"inUse":101,"away":0,"totalSeats":120,"free":18},{"roomId":21,"room":"第十阅览室中区","floor":4,"maxHour":4,"reserved":1,"inUse":33,"away":0,"totalSeats":48,"free":14},{"roomId":22,"room":"第十阅览室南区","floor":4,"maxHour":4,"reserved":5,"inUse":126,"away":0,"totalSeats":164,"free":28},{"roomId":35,"room":"第九阅览室中区","floor":5,"maxHour":4,"reserved":1,"inUse":41,"away":0,"totalSeats":48,"free":6},{"roomId":34,"room":"第九阅览室北区","floor":5,"maxHour":4,"reserved":4,"inUse":166,"away":0,"totalSeats":195,"free":24},{"roomId":36,"room":"第九阅览室南区","floor":5,"maxHour":4,"reserved":4,"inUse":151,"away":0,"totalSeats":172,"free":15},{"roomId":32,"room":"第四阅览室中区","floor":5,"maxHour":4,"reserved":2,"inUse":39,"away":0,"totalSeats":48,"free":5},{"roomId":31,"room":"第四阅览室北区","floor":5,"maxHour":4,"reserved":4,"inUse":133,"away":0,"totalSeats":148,"free":9},{"roomId":33,"room":"第四阅览室南区","floor":5,"maxHour":4,"reserved":0,"inUse":146,"away":0,"totalSeats":164,"free":15},{"roomId":38,"room":"第五阅览室中区","floor":6,"maxHour":15,"reserved":0,"inUse":48,"away":0,"totalSeats":48,"free":0},{"roomId":8,"room":"第五阅览室北区","floor":6,"maxHour":15,"reserved":0,"inUse":59,"away":0,"totalSeats":59,"free":0},{"roomId":37,"room":"第五阅览室南区","floor":6,"maxHour":15,"reserved":1,"inUse":171,"away":0,"totalSeats":172,"free":0},{"roomId":47,"room":"第八阅览室中区","floor":6,"maxHour":15,"reserved":0,"inUse":48,"away":0,"totalSeats":48,"free":0},{"roomId":9,"room":"第八阅览室北区","floor":6,"maxHour":15,"reserved":0,"inUse":204,"away":0,"totalSeats":204,"free":0},{"roomId":40,"room":"第八阅览室南区","floor":6,"maxHour":15,"reserved":0,"inUse":175,"away":0,"totalSeats":176,"free":0},{"roomId":27,"room":"第七阅览室中区","floor":7,"maxHour":15,"reserved":0,"inUse":48,"away":0,"totalSeats":48,"free":0},{"roomId":46,"room":"第七阅览室北区","floor":7,"maxHour":15,"reserved":0,"inUse":131,"away":0,"totalSeats":132,"free":1},{"roomId":28,"room":"第七阅览室南区","floor":7,"maxHour":15,"reserved":1,"inUse":106,"away":0,"totalSeats":108,"free":1},{"roomId":24,"room":"第六阅览室中区","floor":7,"maxHour":15,"reserved":0,"inUse":48,"away":0,"totalSeats":48,"free":0},{"roomId":23,"room":"第六阅览室北区","floor":7,"maxHour":15,"reserved":1,"inUse":129,"away":0,"totalSeats":132,"free":2},{"roomId":25,"room":"第六阅览室南区","floor":7,"maxHour":15,"reserved":0,"inUse":107,"away":0,"totalSeats":108,"free":1}],"message":"","code":"0"}
	 */
	public static String getAllRoomINfo = "https://seat.ujn.edu.cn:8443/rest/v2/room/stats2/2";
	
	
	//获得阅览室座位号编码
//	https://seat.ujn.edu.cn:8443/rest/v2/room/layoutByDate/41/2019-03-14
	public static String getRoomInfo = "https://seat.ujn.edu.cn:8443/rest/v2/room/layoutByDate/";
	
	//预约
	public static String freeBookString = "https://seat.ujn.edu.cn:8443/rest/v2/freeBook";
	
	//签到
	public static String signIn = "https://seat.ujn.edu.cn:8443/rest/v2/checkIn";
	
}
