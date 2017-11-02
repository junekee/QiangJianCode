import java.util.HashMap;
import java.util.List;
import java.util.Map;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


public class JsonMap {
	public static void main(String[] args){
		String data  = "[{\"procdate\":\"2016-08-22 19:57:44\",\"scustmobile\":\"18891009998\",\"scustaddr\":\"广东省广州市天河区 龙口西路101号\",\"businesstype\":1,\"tcustmobile\":\"18680286992\",\"scontactor\":null,\"tcustcounty\":\"\",\"tcontactor\":\"ceshi\",\"tcustaddr\":\"广州18#\",\"tcustprovince\":\"\",\"datetype\":\"2\",\"billno\":\"TEST101916931\",\"tcustcity\":\"\",\"bigaccountdataid\":\"TEST101916931\"}]";
		Gson gson = new Gson();
		System.out.println(data);
		List<HashMap> printDataMap = gson.fromJson(data, new TypeToken<List<HashMap>>(){}.getType());
//		List<Dto> printDatas = new ArrayList<Dto>();
			for(Map map:printDataMap){
				System.out.println(map);
			}
	}
}
