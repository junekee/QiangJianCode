import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;


public class FielData {
	public static void main(String[] args) throws IOException{
		
		String a="table_list";
		String b="table";
		System.out.println(a.contains(b));
		System.out.println("table_list".indexOf("table_"));
//		File outDir=new File("e:/a/123");
//		if (!outDir.exists()){
//			outDir.mkdirs();
//		}
//		try {
//			new FielData().filebyte();
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}

	public void filebyte() throws Exception{
		File fileData =new File("E:/A-Document/05.工具资料/Photoshop/photoshop课程/photoshop火星高级课程/PS抠图方法.docx");
		String value = null;
		FileInputStream in = new FileInputStream(fileData);
		try {
			MappedByteBuffer byteBuffer = in.getChannel().map(
					FileChannel.MapMode.READ_ONLY, 0, fileData.length());
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			md5.update(byteBuffer);
			BigInteger bi = new BigInteger(1, md5.digest());
			value = bi.toString(16);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != in) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		//System.out.println("59b6046b8cb5bf48c3b2e63bb4424779");
		System.out.println(value);
		long len = fileData.length();
		byte[] bytes = new byte[(int) len];

		BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(fileData));
		int r = bufferedInputStream.read(bytes);
		if (r != len)
			throw new IOException("读取文件不正确");
		bufferedInputStream.close();
		System.out.println(bytes);
		
	}
}
