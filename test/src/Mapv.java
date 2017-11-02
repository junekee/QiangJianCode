import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;



public class Mapv {

	public static void main(String[] args){
		
		Map map = new HashMap();
		map.put("a", 1);
		map.put("a1", 1);
		map.put("a2", 2);
		map.put("a3", 3);
		map.put("a4", 4);
		Set set =  map.entrySet();
		Iterator it=set.iterator();
		 
		while(it.hasNext()){
			Entry entry = (Entry) it.next();
			System.out.println("${" + entry.getKey() + "}"+"---"+entry.getValue());
		}
//		for (Map.Entry entry1 : map.entrySet()) {
//		    System.out.println("Key = " + entry1.getKey() + ", Value = " + entry1.getValue());
//		}
	}
}
