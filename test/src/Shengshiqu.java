import java.util.HashMap;
import java.util.Map;


public class Shengshiqu {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Map p = new HashMap();
		String str= "广州市科韵路22号";
		p.put("tcustprovince", 1);
		p.put("tcustcity", 1);
		p.put("tcustcounty", 1);
		if(str.indexOf("省")>=0){
			p.put("tcustprovince",str.substring(0,str.indexOf("省")+1));//收件人所在省
		}else{
			p.put("tcustprovince","广东省");//收件人所在省
		}
		if(str.indexOf("市")>=0){
			p.put("tcustcity", str.substring(str.indexOf("省")==-1?0:str.indexOf("省")+1,str.indexOf("市")==-1?0:str.indexOf("市")+1));//收件人所在市
		}else{
			p.put("tcustcity", "");//收件人所在市
		}
		if(str.indexOf("区")>=0){
			p.put("tcustcounty",str.substring(str.indexOf("市")==-1?0:str.indexOf("市")+1,str.indexOf("区")==-1?0:str.indexOf("区")+1));//收件人所在区县
		}else{
			if(str.indexOf("县")>=0){
				p.put("tcustcounty",str.substring(str.indexOf("市")==-1?0:str.indexOf("市")+1,str.indexOf("县")==-1?0:str.indexOf("县")+1));//收件人所在区县
			}else{
				p.put("tcustcounty","");
			}
			//收件人所在区县
		}
		
		System.out.println(p.get("tcustprovince"));
		System.out.println(p.get("tcustcity"));
		System.out.println(p.get("tcustcounty"));
	}

}
