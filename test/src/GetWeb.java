import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class GetWeb {
	private long startTime;
	private String myDomain; //域名
	private String strHomePage = "";//主页地址
	public static void main(String[] args){
		GetWeb getWeb= new GetWeb();
		
	}
	public void getWebByHomePage(){
		startTime = System.currentTimeMillis();
		this.myDomain= getDomain();
	}
	public String getDomain(){
		String ret= "(?<=http\\://[a-zA-Z0-9]{0,100}[.]{0,1})[^.\\s]*?\\.(com|cn|net|org|biz|info|cc|tv)";
		Pattern p = Pattern.compile(ret,Pattern.CASE_INSENSITIVE);
		Matcher m = p.matcher(strHomePage);
		boolean blnp = m.find();
		if(blnp==true){
			return m.group(0);
		}
		return null;
		
	}
}
