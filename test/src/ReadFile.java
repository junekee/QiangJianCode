import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.hwpf.HWPFDocument;



public class ReadFile {

	public static void main(String[] args)
	{
		 FileInputStream in = null;  
	        try  
	        {  
	            in = new FileInputStream(new File("e:/ccc.doc"));  
	        }  
	        catch (FileNotFoundException e1)  
	        {  
	            e1.printStackTrace();  
	        }  
		try {
			  HWPFDocument hdt = null;  
		        try  
		        {  
		            hdt = new HWPFDocument(in);  
		        }  
		        catch (IOException e1)  
		        {  
		            e1.printStackTrace();  
		        }  

//			FileInputStream in = new FileInputStream(new File("E:/DEP_TP/web/WebRoot/tabletemp/tzs/行政许可受理通知书.docx")); 
			  ByteArrayOutputStream ostream = new ByteArrayOutputStream();  
		       
//		        FileOutputStream out = null;  
		        try  
		        {  
//		            out = new FileOutputStream("E:/11111.doc", true);  
		        }  
		        catch (Exception e)  
		        {  
		            e.printStackTrace();  
		        }  
		        try  
		        {  
		            hdt.write(ostream);  
		        }  
		        catch (IOException e)  
		        {  
		            e.printStackTrace();  
		        }  
		        // 输出字节流  
		        try  
		        {  
		        	System.out.println(ostream.toByteArray());
//		            out.write(ostream.toByteArray());  
		        }  
		        catch (Exception e)  
		        {  
		            e.printStackTrace();  
		        }  
		        try  
		        {  
//		            out.close();  
		        }  
		        catch (Exception e)  
		        {  
		            e.printStackTrace();  
		        }  
		        try  
		        {  
		            ostream.close();  
		        }  
		        catch (IOException e)  
		        {  
		            e.printStackTrace();  
		        }  
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 

		
		
	}
}
