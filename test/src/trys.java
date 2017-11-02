
public class trys {

	public static void main(String[] args){
		for(int i=0;i<2;i++){
			try{
				
				String a= "a";
				Integer.parseInt(a);
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	
		System.out.println("111");
		
		
	}
}
