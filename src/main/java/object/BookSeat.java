package object;
/*
 * 	ԤԼ��λ
 */
public class BookSeat {

	String name;
	String count;
	String password;
	
	String  timestr;
	int starttime;
	int endtime;
	
	int roomid;
	int seatid;
	int seatcode;

	//优先座位如果预约失败  那么预约这个
	int seatid2;
	int seatcode2;
	
	
	String rString;
	
	
	public int getRoomid() {
		return roomid;
	}
	public void setRoomid(int roomid) {
		this.roomid = roomid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCount() {
		return count;
	}
	public void setCount(String count) {
		this.count = count;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getTimestr() {
		return timestr;
	}
	public void setTimestr(String timestr) {
		this.timestr = timestr;
	}
	public int getStarttime() {
		return starttime;
	}
	public void setStarttime(int starttime) {
		this.starttime = starttime;
	}
	public int getEndtime() {
		return endtime;
	}
	public void setEndtime(int endtime) {
		this.endtime = endtime;
	}
	public int getSeatid() {
		return seatid;
	}
	public void setSeatid(int seatid) {
		this.seatid = seatid;
	}
	public String getrString() {
		return rString;
	}
	public void setrString(String rString) {
		this.rString = rString;
	}
	public int getSeatcode() {
		return seatcode;
	}
	public void setSeatcode(int seatcode) {
		this.seatcode = seatcode;
	}
	public int getSeatid2() {
		return seatid2;
	}
	public void setSeatid2(int seatid2) {
		this.seatid2 = seatid2;
	}
	public int getSeatcode2() {
		return seatcode2;
	}
	public void setSeatcode2(int seatcode2) {
		this.seatcode2 = seatcode2;
	}
	
	
	
}
