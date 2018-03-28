
public class IndexOf {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		String Url = "http://127.0.0.1:8080/uploaddata/2017/11/14/2017111414542330450.png";
		String fileName = "2017111414542330450";
		// TODO Auto-generated method stub
		System.out.println(Url.substring(Url.lastIndexOf("uploaddata"), Url.lastIndexOf(fileName)-1)); 
		System.out.println("B1,B2".indexOf("11")); 
		String str = "G:\\Project\\DEP_TP\\web\\WebRoot/uploaddata/2017\\11\\07\\2017110711542129940.jpg";
		System.out.println(str.substring(str.indexOf("uploaddata"), str.length()).replace("\\", "/")); 
		
		
	}

}
