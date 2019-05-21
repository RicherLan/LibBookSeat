package server;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.X509TrustManager;

import net.LibInfo;
import net.MyNetUtil;
import net.StartBook;
import net.StartSign;
import object.BookSeat;
import object.SignInSeat;
import threadUtil.FixedThreadPool;
import util.MyFileUtil;
import util.TimeUtil;

public class StartServer {
	
	//public static String proxyServer = "ujnlibrarytencent.chaochaogege.net";
	public static String proxyServerIP = "118.24.155.131";
	public static int proxyPort = 6000;
	public static Proxy proxy;
	
	
	public static Map<Integer, Integer> seatidsMap = new HashMap<Integer, Integer>();

	public static void main(String[] args) {

		// 开启业务线程池
		FixedThreadPool.startThreadPool();
		//跳过https认证
		trustEveryone();
		
		//System.setProperty("https.protocols", "TLSv1.2,TLSv1.1,SSLv3");
//		System.setProperty("https.protocols", "TLSv1"); 
//		System.setProperty("javax.net.debug", "all");
		//setProxy();

		StartServer server = new StartServer();
		server.start();
		
	}

	public static void setProxy() {
		// 创建代理服务器地址对象
        InetSocketAddress addr = new InetSocketAddress(proxyServerIP, proxyPort);
        // 创建HTTP类型代理对象
        proxy = new Proxy(Proxy.Type.HTTP, addr);
	}
	
	public void start() {
		
		LibInfo.initRoomInfo();
		LibInfo.initSeatId();
			
		System.out.println("Start Timer!!!!!!!!!!");
		
		
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, 6);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 57);
		Date time = calendar.getTime();

		//如果第一次执行任务的时间早于当前时间，那么第一次+"?token="+token执行任务的时间推迟一天
		if(time.before(new Date())) {
			time = TimeUtil.addDay(time, 1);
		}
		
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {

			@Override
			public void run() {

				System.out.println("execute timer run!!!!!!nowtime："+TimeUtil.getNowTime());
				//配置文件
				String filepath = "C:\\Users\\qxc\\Desktop\\UserProperty.txt";
				String os = System.getProperty("os.name"); 
				if (os.toLowerCase().startsWith("win")) {

				} else {
					filepath = "/usr/myfile/bookseat/test/UserProperty.txt";
				}

				Vector<BookSeat> bookSeats = new Vector<BookSeat>();
				Vector<SignInSeat> signInSeats = new Vector<SignInSeat>();
				MyFileUtil.getBookSeatFromFile(filepath, bookSeats, signInSeats);

				System.out.println("load user data from file ok!!!!!!!!!!!");
				for (final BookSeat bookSeat : bookSeats) {
					
//					System.out.println(bookSeat.getName()+" "+bookSeat.getRoomid()+" "+bookSeat.getCount()+" "+bookSeat.getPassword());
//					System.out.println(bookSeat.getSeatid()+" "+bookSeat.getSeatcode()+" "+bookSeat.getStarttime()+"  "+bookSeat.getEndtime());
					
//					FixedThreadPool.threadPool.submit(new Runnable() {
//
//						public void run() {
//							StartBook startKBook = new StartBook(bookSeat);
//							startKBook.start();
//						}
//					});
					
					new Thread() {
						public void run() {
							StartBook startKBook = new StartBook(bookSeat);
							startKBook.start();
						}
					}.start();
					
				}

				
//				for (final SignInSeat signInSeat : signInSeats) {
//					FixedThreadPool.threadPool.submit(new Runnable() {
//
//						public void run() {
//							StartSign startSign = new StartSign(signInSeat);
//							startSign.start();
//						}
//					});
//					try {
//						Thread.sleep(5000);
//					} catch (InterruptedException e) {
//						// TODO Auto-generated catch block
//						//e.printStackTrace();
//					}
//				}
				

			}
		}, time, 1000 * 60 * 60 * 24);

	}

	/**
	 * 信任任何站点，实现https页面的正常访问
	 * 
	 */

	public static void trustEveryone() {
		try {
			HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
				public boolean verify(String hostname, SSLSession session) {
					return true;
				}
			});

			SSLContext context = SSLContext.getInstance("TLS");
			context.init(null, new X509TrustManager[] { new X509TrustManager() {
				public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
				}

				public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
				}

				public X509Certificate[] getAcceptedIssuers() {
					return new X509Certificate[0];
				}
			} }, new SecureRandom());
			HttpsURLConnection.setDefaultSSLSocketFactory(context.getSocketFactory());
		} catch (Exception e) {
			// e.printStackTrace();
		}
	}

}
