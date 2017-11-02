
public class Main1 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		 String paramsXml = "<?xml version=\"1.0\" encoding=\"utf-8\"?>"
			+ "<ExchangeMessage>"
			+ "<Header>"
			+ "<sender>33</sender>"
			+ "<receiver>267255</receiver>"
			+ "<cc></cc>"
			+ "<flow>2b2dec37-9ae4-40cd-a236-43fc3266cef1</flow>"
			+ "<item>9ae36cf5-3588-4315-aa09-c51357b25f88</item>"
			+ "<sn></sn>"
			+ "<securityType>1</securityType>"
			+ "<operator>sifating</operator>"
			+ "<contacts>12345678</contacts>"
			+ "</Header>"
			+ "<Body>"
			+ "<fields>"
			+ "<CF_WSH>654636</CF_WSH>"
			+ "<CF_CFMC>广东省司法厅行政处罚决定书</CF_CFMC>"
			+ "<CF_FR>法定代表人姓名</CF_FR>"
			+ "<CF_JDRQ>2017-01-10</CF_JDRQ>"
			+ "<BZ>备注</BZ>"
			+ "<CF_ZT>当前状态</CF_ZT>"
			+ "<CF_XDR_MC>广东五度律师事务所</CF_XDR_MC>"
			+ "<CF_XDR_SFZ>居民身份证号</CF_XDR_SFZ>"
			+ "<CF_XDR_SHXYM>统一社会信用代码</CF_XDR_SHXYM>"
			+ "<CF_XDR_SWDJ>税务登记号</CF_XDR_SWDJ>"
			+ "<CF_XDR_GSDJ>工商登记码</CF_XDR_GSDJ>"
			+ "<CF_SY>都挺好</CF_SY>"
			+ "<CF_XDR_ZDM>组织机构代码</CF_XDR_ZDM>"
			+ "<CF_JG>处罚结果</CF_JG>"
			+ "<CF_AREACODE>地方编码</CF_AREACODE>"
			+ "<CF_CFLB1>处罚类别1</CF_CFLB1>"
			+ "<CF_YJ>京东方</CF_YJ>"
			+ "<CF_XZJG>处罚机关</CF_XZJG>"
			+ "<CF_CFLB2>处罚类别2</CF_CFLB2>"
			+ "</fields>"
			+ "</Body>"
			+ "</ExchangeMessage>";
		System.out.println(paramsXml);

	}

}
