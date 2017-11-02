import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;


public class Throw1 {

	
		public static void main(String[] args) {
			try {
				Map map = new HashMap();
				map.put("a", 1);
				map.put("a1", 1);
				map.put("a2", 1);
				map.put("a3", 1);
				map.put("a4", 1);
				System.out.println(map.toString());
				Object[] pa = {"a","bbb","cccc"};
				System.out.println(Arrays.toString(pa) );
				System.out.println(new Throw1().throwTest());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		public int throwTest() {
			String a = "";
			try{
				if("".equals(a)){
					System.out.println(1);
					throw new Exception("Å×³öÁË£¡");
				}
			}catch(Exception e){
				e.printStackTrace();
			}
			
			System.out.println("¼ÌÐø¡£¡£¡£¡£¡£");
			return 3;
		}
}
