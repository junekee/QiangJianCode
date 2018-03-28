import org.apache.http.impl.auth.GGSSchemeBase;



public class xxxx {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String ss = "1234";
//		System.out.println(ss.substring(ss.length()-3,ss.length()-2));
//		System.out.println("123".substring(0, 1));
		new xxxx().cc();
	}
	public void cc(){

		// TODO Auto-generated method stub
		
		/***
		 *     O O O  i
		 *  x    O O k j
		 *---------------
		 *     5 O O 
		 *   O O 4
		 *--------------------
		 *   O 0 O 6        
		 */
			for (int i=0;i<=999;i++){
				for (int j=0;j<=9;j++){
					int g = 0;
					  //个位
					   g = i*j;
					 
					
					for (int k =0; k<=99;k++){
						int s  =0;
						   //十位
						 s = i*k;
						 
						String ss = s+"";
						String  gg = g+"";
						String ij40="";
						if(ss.length() == 4 && gg.length() == 3 ){
							//System.out.println("ij40="+ss);
							ij40 = ss.substring(ss.length()-2,ss.length()-1); 
							 
							if("6".equals(gg.substring(gg.length()-1, gg.length()))&&"5".equals(gg.substring(0, 1))&&"4".equals(ij40)){
//								 System.out.println(g+"  j="+gg.substring(0, 1));
//								System.out.println(ss+"----"+i+"---"+k+"-----"+j+"----"+gg);
								int kj = Integer.parseInt(k+j+"");
							    String ijk = i*kj+"";
							    
							    if("2018".equals(ijk)){
							    	System.out.println(i+"------"+k+"    "+j+"    ijk"+ijk);
							    }
							}
							
						}
					}
					
					
					
				}
				
			}
	
	}

}
