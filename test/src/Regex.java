import java.util.regex.Pattern;
public class Regex {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String idcard = "123456789012345";
		 idcard = "371723199403258144";
		 idcard = "360735197908220487";
		 idcard = "610401198205219111";
		String idcardRegex15 = "^[1-9]\\d{7}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}$"; // 身份证正则表达式(15位) 
		String idcardRegex18 = "^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}([0-9]|X|x)$";// 身份证正则表达式(18位) 
		boolean is15 = Pattern.matches(idcardRegex15, idcard);
		boolean is18 = Pattern.matches(idcardRegex18, idcard);
		System.out.println("is15:"+is15);
		System.out.println("is18:"+is18);
	}

}
