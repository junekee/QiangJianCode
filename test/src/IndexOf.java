
public class IndexOf {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("B1,B2".indexOf("B2")); 
		String str = "G:\\Project\\DEP_TP\\web\\WebRoot/uploaddata/2017\\11\\07\\2017110711542129940.jpg";
		System.out.println(str.substring(str.indexOf("uploaddata"), str.length()).replace("\\", "/")); 
		
		
	}

}
