
import java.security.MessageDigest;

public class MD5Encoder {
	private static final char HEX_DIGITS[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };

	private static String toHexString(byte[] b) {
	    StringBuilder sb = new StringBuilder(b.length * 2);
	    for (int i = 0; i < b.length; i++) {
	        sb.append(HEX_DIGITS[(b[i] & 0xf0) >>> 4]);
	        sb.append(HEX_DIGITS[b[i] & 0x0f]);
	    }
	    return sb.toString();
	}

	public static String Bit32(String SourceString) throws Exception {
	    MessageDigest digest = java.security.MessageDigest.getInstance("MD5");
	    digest.update(SourceString.getBytes("UTF-8"));
	    byte messageDigest[] = digest.digest();
	    return toHexString(messageDigest);
	}
	public  static void main(String[] args){
		try {
			System.out.println(MD5Encoder.Bit32("hello"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
