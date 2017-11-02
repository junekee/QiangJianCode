import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class DateUtils {
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		DateUtils d= 	new DateUtils();
		d.minutes1("",-5);
		d.minutes1("",0);
	}
	
	public void minutes(){
//		long time = 30*60*1000;//30分钟
//		  Date afterDate = new Date(now .getTime() + time);//30分钟后的时间
//		  Date beforeDate = new Date(now .getTime() - time);//30分钟前的时间
//		  System.out.println(sdf.format(afterDate ));
//		  System.out.println(sdf.format(beforeDate));
//		  
//		System.out.println();
	}
	public void minutes1(String str,int mi) {
		 Calendar nowTime = Calendar.getInstance();
		 Date date =null;
		try {
			if("".equals(str)){
				date = new Date();
			}else{
				date = sdf.parse(str);
			}
			nowTime.setTime(date);
			nowTime.add(Calendar.MINUTE, mi);//30分钟后的时间
			System.out.println(sdf.format(nowTime.getTime()));
//		  Calendar nowTime2 = Calendar.getInstance();
//		  nowTime2.add(Calendar.MINUTE, -30);//30分钟前的时间
//		  System.out.println(sdf.format(nowTime2.getTime()));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
