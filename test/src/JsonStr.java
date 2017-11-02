import java.util.HashMap;
import java.util.Map;

import oracle.sql.TIMESTAMP;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


public class JsonStr {

	private GsonBuilder gsonBuilder = new GsonBuilder().serializeNulls();
	private String dateFormat;
	public static void main(String[] args){
		Gson gson = new Gson();
		Map result= new HashMap();
		result.put("code", "0");
		result.put("errorInfo", "HHHooo");
		System.out.println( gson.toJson(result));
		
	}
//	public void toJson1()
//	{
//		Map result= new HashMap();
//		result.put("code", "0");
//		result.put("errorInfo", "HHHooo");
//		System.out.println( this.toJson(result	));
//	}
//	public String toJson(Object obj) {
//		if (dateFormat != null && dateFormat.trim().length() > 0) {
//			gsonBuilder.setDateFormat(dateFormat).registerTypeAdapter(
//					TIMESTAMP.class, new TimestampAdapter(dateFormat));
//		}
//		Gson gson = gsonBuilder.create();
//		return gson.toJson(obj);
//	}
}
