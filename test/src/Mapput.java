import java.util.HashMap;
import java.util.Map;


public class Mapput {

		public static void main(String[] agrs){
			Map map = new HashMap();
			map.put("a", 22222);
			new Mapput().putStr(map);
			System.out.println(map);
		}
	
	public void putStr(Map map){
		map.put("new", 11111);
	}
}
