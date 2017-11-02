
public class sp {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String str = "广东省广州市天河区元岗路200号慧通产业广场第九栋2楼";
 
		String sheng  ="";
		if(str.indexOf("省")>=0){
			sheng = str.substring(0,str.indexOf("省")+1);
			System.out.println(sheng);
		}
		if(str.indexOf("市")>=0){
			System.out.println(str.substring(str.indexOf("省")==-1?0:str.indexOf("省")+1,str.indexOf("市")==-1?0:str.indexOf("市")+1));
		}
		if(str.indexOf("区")>=0){
			System.out.println(str.substring(str.indexOf("市")==-1?0:str.indexOf("市")+1,str.indexOf("区")==-1?0:str.indexOf("区")+1));
		}
		
		
		
	}

}
