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
		
		
		String a="                           �ĺţ� noticeno"+
 "            city��˾����������������֪ͨ��"+
""+
 "        {applyusername���������������ߵ�λ���ƣ���"+
 "     yyyy ��mm��dd�ձ������յ��㣨�㵥λ�������itemname���뼰�ύ�����У�������������ϣ�       "+    
"bzqd"+
 "            ����飬�㣨�㵥λ�����ύ���������������鼰������������������ȫ�����Ϸ�����ʽ������������"+
 "   �ش�֪ͨ��"+
";" ;
System.out.println(a);
System.out.println("=================================================");
		a="         �ĺ�: ${ noticeno}                                                                                                                                                                                                                                                                                      "+
		"${city}��˾�����������ɲ�������֪ͨ��                                                                                                                                                                                                                                                                            \r"+
		"                                                                                                                                                                                                                                                                                                                 "+
		"${applyusername} (������������λ����)��                                                                                                                                                                                                                                                                        \r"+
		"${yyyy}��${mm}��${dd}�գ��������յ��㣨�㵥λ����� ${itemname} ���뼰�ύ��������ϡ�����飬�����ύ�Ĳ��ϴ����������⣨����������ϲ���ȫ���߲����Ϸ�����ʽ��������д����                ���ݡ��л����񹲺͹��������ɷ�������ʮһ��������ʮ������                       �Ĺ涨����Ҫ�㣨�㵥λ���������в��ϣ�\r"+
		"                                                                                                                                                                                                                                                                    \r                                             "+
		"���㣨�㵥λ�����������ϲ�������${bzyyyy}��${bzmm}��${bzdd}��ǰ�ύ�������ء������������������ύ�ģ���Ϊ�������롣                                                                                                                                                                                            \r  "+
		"�ش�֪ͨ��                                                                                                                                                                                                                                                                                                     \r  "+
		"��ϵ�˼���ϵ��ʽ�� ${wphone}                                                                                                                                                                                                                                                  \r                                   "+
		"                                                                                                                                                                                                                                                                            \r                                     "+
		"��λ�����£�                                                                                                                                                                                                                                                                                                     \r"+
		"  ��  ��   ��                                                                                                                                                                                                                                                                                                    \r"  ;
		System.out.println(a);
		System.out.println("=================================================");
		
		a="                        �ĺ�: ${ noticeno}                                                                                                                                                                   \r"+
		"${city}��˾�����������ɲ�������֪ͨ��                                                                                                                                                                        \r"+
		"                                                                                                                                                                                                             \r"+
		"      ${applyusername} ���������������ߵ�λ���ƣ���                                                                                                                                                          \r"+
		"�㣨��������${yyyy}��${mm}��${dd}���򱾻��������       ${itemname}���뼰�ύ��������ϣ�����飬���ڡ��л����񹲺͹��������ɷ������л����񹲺͹���ʦ�����涨�����²������������Σ� ${aprvidea}       ��   \r"+
		"���ݡ��л����񹲺͹��������ɷ�������ʮ�������л����񹲺͹���ʦ�������йع涨�������ؾ�������������                                                         \r"+
		"��Ա������������������յ���������֮������ʮ���ڣ�������${city}�������������߹㶫ʡ˾���������������飬Ҳ���������������������й�ϽȨ������Ժ�����������ϡ�\r"+
		"�ش�֪ͨ��                                  \r"+
		"                                            \r"+
		"                                            \r"+
		"                                            \r"+
		"                                            \r"+
		"                                ��λ�����£�\r"+
		"                                ��  ��  ��  \r";
		System.out.println(a);
		System.out.println("=================================================");
		a="	                             �ĺţ� noticeno                                                       \r"+
		"city ��˾����������������֪ͨ��                                                                               \r"+
		"                                                                                                 \r"+
		"         applyusername���������������ߵ�λ���ƣ���                                                             \r"+
		"     yyyy ��mm��dd�ձ������յ��㣨�㵥λ�������${itemname}���뼰�ύ�����У�������������ϣ�                                      \r"+
		" ${bzqd}                                                                                         \r"+
		"             ����飬�㣨�㵥λ�����ύ���������������鼰������������������ȫ�����Ϸ�����ʽ������������                                       \r"+
		"    �ش�֪ͨ��                                                                                        \r"+
		"                                                                                                 \r"+
		"                                                                                                 \r"+
		"                bzrule               ��λ�����£�                                                            \r"+
		"           aprvdeptname       sobjtype              ��  ��  ��\r";
		System.out.println(a);
		System.out.println("=================================================");
		 a="         �ĺ�: ${noticeno}                                                                                            \r"+
		" ${city}��˾�����������ɲ�������֪ͨ��                                                                                 \r"+
		"                                                                                                                      \r"+
		" ${applyusername} ��                                                                                                   \r"+
		" ${yyyy}��${mm}��${dd}�գ��������յ�${sobjtype}��� ${itemname} ���뼰�ύ��������ϡ�����飬                         \r"+
		"�����ύ�Ĳ��ϴ����������⣨����������ϲ���ȫ���߲����Ϸ�����ʽ��������д����                                        \r"+
		"    ���ݡ��л����񹲺͹��������ɷ�������ʮһ��������ʮ������       ${bzrule}    �Ĺ涨����Ҫ${sobjtype}�������в��ϣ� \r"+
		"                                                                                                                      \r"+
		"��${sobjtype}���������ϲ�������${bzyyyy}��${bzmm}��${bzdd}��ǰ�ύ�������ء������������������ύ�ģ���Ϊ�������롣    \r"+
		"�ش�֪ͨ��                                                                                                            \r"+
		"��ϵ�˼���ϵ��ʽ�� ${wphone}                                                                                          \r"+
		"                                                                                                                      \r"+
		"${aprvdeptname}                                                                                                       \r"+
		"  ��  ��   ��                                                                                                         \r";
		 System.out.println(a);
			System.out.println("=================================================");
	}}
