

public class Contains {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
//		System.out.println( new Contains().isAllowUser("l"));
		String a ="";
		String s = "";
		String ss = "";
		String xx = "";
		a = a.replaceAll(s, "");
		a = a.replaceAll(ss, "");
		a = a.replaceAll(xx, "");
		System.out.println(a);

	}
	public boolean isAllowUser(String appName){
//		String[] allowuser = WSDLUtil.getValue("allowuser").split(",");
		String[] allowuser = "".split(",");
		for(String str :allowuser){
			if(!"".equals(appName)&&appName.equals(str)){
				return true;
			}
		}
		return false;
	}
}
