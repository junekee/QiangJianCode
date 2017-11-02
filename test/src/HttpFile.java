import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;


public class HttpFile {

	public static void main(String[] args) {
		new HttpFile().fo();
	}
	public void fo(){
		for(int i=0;i<5;i++){
			this.chkFile();
			System.out.println(i);
		}
	}
	public void chkFile(){
		URL url;
		DataOutputStream out = null;
		DataInputStream in = null;
		try{
			File file = new File("e://tmp.tmp");
			url = new URL("http://127.0.0.1:8090/uploaddata/2016/11/24/81c0e599-2002-4686-aaeb-6199952ae510.pdf");
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			Object input = connection.toString();
			if( input!= null){
				in = new DataInputStream(connection.getInputStream());
				
				out = new DataOutputStream(new FileOutputStream(file));
				byte[] buffer = new byte[4096];
				int count = 0;
				while ((count = in.read(buffer)) > 0) {
					out.write(buffer, 0, count);
				}
				if(out != null){
					out.flush();
					out.close();
				}
			}else{
				System.out.println("file null");
			}
			if(in != null){
				in.close();
			}
		}catch (Exception e){
			e.printStackTrace();
		}
	}
}
