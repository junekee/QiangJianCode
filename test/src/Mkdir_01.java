import java.io.File;


public class Mkdir_01 {

	public static void main(String[] args){
		File f= new File("F:\\transfer\\uploaddata/2016/09/06/");
		if(!f.exists()){
			f.mkdirs();
		}
		
	}
	
}
