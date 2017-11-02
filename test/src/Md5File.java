import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.digest.DigestUtils;


public class Md5File {

	public static void main(String[] args){
//		System.out.println(DigestUtils.md5Hex(new FileInputStream(new File("C:/ttt/new1328505655521"))));
//		��ȡ�ַ���MD5�룺

//		System.out.println(DigestUtils.md5Hex(""));
		try {
			Md5File.getMd5ByFile(new File("C:/Users/Administrator/Downloads/commons-codec-1.10-bin (1).zip"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}//59b6046b8cb5bf48c3b2e63bb4424779
	}
	public static String fileMD5(String inputFile) throws IOException {
	      // ��������С��������Գ��һ��������
	      int bufferSize = 256 * 1024;
	      FileInputStream fileInputStream = null;
	      DigestInputStream digestInputStream = null;
	      try {
	         // �õ�һ��MD5ת������ͬ����������Ի���SHA1��
	         MessageDigest messageDigest =MessageDigest.getInstance("MD5");
	         // ʹ��DigestInputStream
	         fileInputStream = new FileInputStream(inputFile);
	         digestInputStream = new DigestInputStream(fileInputStream,messageDigest);
	         // read�Ĺ����н���MD5����ֱ�������ļ�
	         byte[] buffer =new byte[bufferSize];
	         while (digestInputStream.read(buffer) > 0);
	         // ��ȡ���յ�MessageDigest
	         messageDigest= digestInputStream.getMessageDigest();
	         // �õ������Ҳ���ֽ����飬����16��Ԫ��
	         byte[] resultByteArray = messageDigest.digest();
	         // ͬ�������ֽ�����ת�����ַ���
//	         return byteArrayToHex(resultByteArray);
	         return resultByteArray.toString();
	      } catch (NoSuchAlgorithmException e) {
	         return null;
	      } finally {
	         try {
	            digestInputStream.close();
	         } catch (Exception e) {
	         }
	         try {
	            fileInputStream.close();
	         } catch (Exception e) {
	         }
	      }
	   }
	/**
	 * ���ļ�����MD5У����
	 * @param file
	 * @return
	 * @throws FileNotFoundException
	 */
	public static String getMd5ByFile(File file) throws Exception {
		String value = null;
		FileInputStream in = new FileInputStream(file);
		try {
			MappedByteBuffer byteBuffer = in.getChannel().map(
					FileChannel.MapMode.READ_ONLY, 0, file.length());
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
		return value;
	}
  
}
