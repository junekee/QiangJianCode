import java.util.ArrayList;
import java.util.List;


public class ListTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		List list = new ArrayList();
		list.add("fdsf1dsfd");
		list.add("fdsf2dsfd");
		list.add("fdsf3dsfd");
		list.add("fdsf4dsfd");
		list.add("fdsf5dsfd");
		for(int i=0;i<list.size();i++){
			System.out.println(list.get(i).toString());
		}
	}

}
