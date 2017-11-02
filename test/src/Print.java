import java.util.HashMap;
import java.util.Map;


public class Print {

	public static void main(String[] args){
		String[] value= new String[]{"1111","222","333"};
		Map map = new HashMap();
		map.put("parm", value);
		System.out.println(map.get("parm") instanceof String);
		System.out.println(value.getClass().isArray() );
		System.out.println(map.get("parm").getClass().isArray());
		System.out.println(map.get("parm") instanceof Map);
		
		
		String a="                           文号： noticeno"+
 "            city市司法局行政许可受理通知书"+
""+
 "        {applyusername（申请人姓名或者单位名称）："+
 "     yyyy 年mm月dd日本机关收到你（你单位）提出的itemname申请及提交的下列（补正）申请材料：       "+    
"bzqd"+
 "            经审查，你（你单位）所提交的行政许可申请书及上述（补正）材料齐全，符合法定形式，现予受理。"+
 "   特此通知。"+
";" ;
System.out.println(a);
System.out.println("=================================================");
		a="         文号: ${ noticeno}                                                                                                                                                                                                                                                                                      "+
		"${city}市司法局行政许可补正材料通知书                                                                                                                                                                                                                                                                            \r"+
		"                                                                                                                                                                                                                                                                                                                 "+
		"${applyusername} (申请人姓名或单位名称)：                                                                                                                                                                                                                                                                        \r"+
		"${yyyy}年${mm}月${dd}日，本机关收到你（你单位）提出 ${itemname} 申请及提交的申请材料。经审查，发现提交的材料存在以下问题（根据申请材料不齐全或者不符合法定形式的情形填写）：                根据《中华人民共和国行政许可法》第三十一条、第三十二条和                       的规定，需要你（你单位）补交下列材料：\r"+
		"                                                                                                                                                                                                                                                                    \r                                             "+
		"请你（你单位）将上述材料补正后于${bzyyyy}年${bzmm}月${bzdd}日前提交至本机关。如无正当理由逾期提交的，视为放弃申请。                                                                                                                                                                                            \r  "+
		"特此通知。                                                                                                                                                                                                                                                                                                     \r  "+
		"联系人及联系方式： ${wphone}                                                                                                                                                                                                                                                  \r                                   "+
		"                                                                                                                                                                                                                                                                            \r                                     "+
		"单位（公章）                                                                                                                                                                                                                                                                                                     \r"+
		"  年  月   日                                                                                                                                                                                                                                                                                                    \r"  ;
		System.out.println(a);
		System.out.println("=================================================");
		
		a="                        文号: ${ noticeno}                                                                                                                                                                   \r"+
		"${city}市司法局行政许可不予受理通知书                                                                                                                                                                        \r"+
		"                                                                                                                                                                                                             \r"+
		"      ${applyusername} （申请人姓名或者单位名称）：                                                                                                                                                          \r"+
		"你（你所）于${yyyy}年${mm}月${dd}日向本机关提出的       ${itemname}申请及提交的申请材料，经审查，存在《中华人民共和国行政许可法》或《中华人民共和国律师法》规定的以下不予受理的情形： ${aprvidea}       。   \r"+
		"根据《中华人民共和国行政许可法》第三十二条或《中华人民共和国律师法》的有关规定，本机关决定不予受理。                                                         \r"+
		"如对本决定不服，可以自收到本决定书之日起六十日内，依法向${city}市人民政府或者广东省司法厅申请行政复议，也可以在六个月内依法向有管辖权的人民法院提起行政诉讼。\r"+
		"特此通知。                                  \r"+
		"                                            \r"+
		"                                            \r"+
		"                                            \r"+
		"                                            \r"+
		"                                单位（公章）\r"+
		"                                年  月  日  \r";
		System.out.println(a);
		System.out.println("=================================================");
		a="	                             文号： noticeno                                                       \r"+
		"city 市司法局行政许可受理通知书                                                                               \r"+
		"                                                                                                 \r"+
		"         applyusername（申请人姓名或者单位名称）：                                                             \r"+
		"     yyyy 年mm月dd日本机关收到你（你单位）提出的${itemname}申请及提交的下列（补正）申请材料：                                      \r"+
		" ${bzqd}                                                                                         \r"+
		"             经审查，你（你单位）所提交的行政许可申请书及上述（补正）材料齐全，符合法定形式，现予受理。                                       \r"+
		"    特此通知。                                                                                        \r"+
		"                                                                                                 \r"+
		"                                                                                                 \r"+
		"                bzrule               单位（公章）                                                            \r"+
		"           aprvdeptname       sobjtype              年  月  日\r";
		System.out.println(a);
		System.out.println("=================================================");
		 a="         文号: ${noticeno}                                                                                            \r"+
		" ${city}市司法局行政许可补正材料通知书                                                                                 \r"+
		"                                                                                                                      \r"+
		" ${applyusername} ：                                                                                                   \r"+
		" ${yyyy}年${mm}月${dd}日，本机关收到${sobjtype}提出 ${itemname} 申请及提交的申请材料。经审查，                         \r"+
		"发现提交的材料存在以下问题（根据申请材料不齐全或者不符合法定形式的情形填写）：                                        \r"+
		"    根据《中华人民共和国行政许可法》第三十一条、第三十二条和       ${bzrule}    的规定，需要${sobjtype}补交下列材料： \r"+
		"                                                                                                                      \r"+
		"请${sobjtype}将上述材料补正后于${bzyyyy}年${bzmm}月${bzdd}日前提交至本机关。如无正当理由逾期提交的，视为放弃申请。    \r"+
		"特此通知。                                                                                                            \r"+
		"联系人及联系方式： ${wphone}                                                                                          \r"+
		"                                                                                                                      \r"+
		"${aprvdeptname}                                                                                                       \r"+
		"  年  月   日                                                                                                         \r";
		 System.out.println(a);
			System.out.println("=================================================");
	}}

