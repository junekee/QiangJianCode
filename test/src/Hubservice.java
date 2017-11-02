import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.ws.BindingProvider;

import org.apache.cxf.endpoint.Client;
import org.apache.cxf.frontend.ClientProxy;
import org.apache.cxf.interceptor.LoggingOutInterceptor;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;
import org.apache.cxf.transport.http.HTTPConduit;
import org.apache.cxf.transports.http.configuration.HTTPClientPolicy;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;


//import org.apache.cxf.endpoint.Client;
//import org.apache.cxf.interceptor.LoggingOutInterceptor;
//import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;


public class Hubservice {
	public static void main(String[] args) throws Exception {
//        String url = "http://19.16.91.111:9016/apihub/login";
//		CloseableHttpClient httpclient = HttpClients.createDefault();
//		HttpPost httppost = new HttpPost(url);
//		List<NameValuePair> vps = new ArrayList<NameValuePair>();
//		vps.add(new BasicNameValuePair("username","sgsadmin"));
//		vps.add(new BasicNameValuePair("password","sgs#ywxt"));
//		httppost.setEntity(new UrlEncodedFormEntity(vps));
//		CloseableHttpResponse response=null;
//			try{
//				response = httpclient.execute(httppost);
//				HttpEntity entity = response.getEntity();
//				String result = EntityUtils.toString(entity); 
//				System.out.println(result);
//			}catch(Exception e){
//				e.printStackTrace();
//			}finally{
//				if (response != null) {
//					response.close();
//				}
//			}
//		System.out.println("403:login timeout".contains("timeout"));
		Hubservice h = new Hubservice();
		for(int i=0;i<5;i++){
			
//			ClassLoader cl = Thread.currentThread().getContextClassLoader();
			
			h.getConn().login();
			System.out.println("---"+new Date().toString());
			Thread.sleep(1000*60);
		}
		System.out.println("**********************1********************");
//		Thread.currentThread().setContextClassLoader(cl);
//		h.send();
//		System.out.println("*********************2*********************");
////		Thread.currentThread().setContextClassLoader(cl);
//		h.send();
		
		
		
	}
	
	public void login() throws Exception {
		String proxyurl = "http://19.16.91.111:9016/apihub/services/auth?wsdl";
		JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
		Client client = dcf.createClient(proxyurl);
		
		
//		���ó�ʱ
//		HTTPConduit conduit = (HTTPConduit)client.getConduit(); 
//		HTTPClientPolicy policy = new HTTPClientPolicy(); 
//		policy.setConnectionTimeout(15); 
//		policy.setAllowChunking(false); 
//		policy.setReceiveTimeout(15); 
//		conduit.setClient(policy);
		// ��ӡ��������Ϣ
//		client.getOutInterceptors().add(new LoggingOutInterceptor());
		Map<String, String> headerParams = new HashMap<String, String>();
		headerParams.put("username", "sgsadmin");
		headerParams.put("password", "sgs#ywxt");
		client.getOutInterceptors().add(new HttpHeaderInterceptor(headerParams));
		try {
			Object[] objects = client.invoke("login");
			
			
//			QName opName = new QName("http://service.apihub.sunshine.com/", "login");
//			Object[] objects = client.invoke(opName);
			
			String result = objects[0].toString();
			// ������ý��
			System.out.println("resule:"+result);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(client!=null){
				client.destroy();
			}
		}
	}

	
	public void send() throws Exception {
//		String paramsXml ="<ExchangeMessage>";
//		paramsXml +="<Header>";
//		paramsXml +="<sender>���͵�λ</sender>";
//		paramsXml +="<receiver>���յ�λ</receiver>";
//		paramsXml +="<cc>";
//		paramsXml +="	<receiver>���͵�λ</receiver>";
//		paramsXml +="</cc>";
//		paramsXml +="<flow>8800806666660088</flow>";
//		paramsXml +="<item>8800806666660087</item>";
//		paramsXml +="<sn>8800806666660087</sn>";
//		paramsXml +="<securityType>1</securityType>	";
//		paramsXml +="<operator>admin</operator>";
//		paramsXml +="<contacts>13925032879</contacts>";
//		paramsXml +="</Header>";
//		paramsXml +="<Body>";
//		paramsXml +="<fields>";
//		
//		
//		paramsXml +="<sjc>2016-08-04</sjc><xk_xdr_shxym></xk_xdr_shxym><xk_wsh>00261</xk_wsh><xk_nr>����ͨ����׼�� ����� רְ��ʦִҵ���</xk_nr><bz></bz><xk_jdrq>2016-08-04</xk_jdrq><xk_xdr_sfz>652823197506173113</xk_xdr_sfz><xk_xdr_zdm></xk_xdr_zdm><xk_splb>��׼</xk_splb><xk_xmmc>רְ��ʦִҵ���</xk_xmmc><xk_areacode>440000</xk_areacode><xk_xdr_swdj></xk_xdr_swdj><xk_xdr_gsdj></xk_xdr_gsdj><xk_jzq></xk_jzq><xk_zt>0</xk_zt><xk_xdr>�����</xk_xdr><xk_xzjg>�㶫ʡ˾����</xk_xzjg><xk_fr></xk_fr>";
//		
//		
//		paramsXml +="</fields>";
//		paramsXml +="</Body>";
//		paramsXml +="</ExchangeMessage>";
		String paramsXml = "<?xml version=\"1.0\" encoding=\"utf-8\"?><ExchangeMessage><Header><sender>33</sender><receiver>267255</receiver><cc></cc><flow>12eb9af6-d0ea-4908-b04b-2646b67f3cf5</flow><item>e8f96a91-af44-46ad-bb4e-8b11738dec2c</item><sn></sn><securityType>1</securityType><operator>sifating</operator><contacts>12345678</contacts></Header><Body>" +
				"<fields><SJC>2016-08-04</SJC><XK_XDR_SHXYM></XK_XDR_SHXYM><XK_WSH>00261</XK_WSH><XK_NR>����ͨ����׼�� ����� רְ��ʦִҵ���</XK_NR><BZ></BZ><XK_JDRQ>2016-08-04</XK_JDRQ><XK_XDR_SFZ>652823197506173113</XK_XDR_SFZ><XK_XDR_ZDM></XK_XDR_ZDM><XK_SPLB>��׼</XK_SPLB><XK_XMMC>רְ��ʦִҵ���</XK_XMMC><XK_AREACODE>440000</XK_AREACODE><XK_XDR_SWDJ></XK_XDR_SWDJ><XK_XDR_GSDJ></XK_XDR_GSDJ><XK_JZQ>2099-12-31</XK_JZQ><XK_ZT>0</XK_ZT><XK_XDR>�����</XK_XDR><XK_XZJG>�㶫ʡ˾����</XK_XZJG><XK_FR></XK_FR></fields>" +
				"</Body></ExchangeMessage>";
		String proxyurl ="http://19.16.91.111:9016/apihub/services/bsync?wsdl";
		JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
		Client client = dcf.createClient(proxyurl);
		//��ӡ��������Ϣ  
		client.getOutInterceptors().add(new LoggingOutInterceptor());
		Map<String,String> headerParams = new HashMap<String, String>();
		headerParams.put("token", "cef94f8a-f4d0-4b95-97c0-dcedfacd8bee");
		headerParams.put("serviceId", "exsend");//�����ʶ
		client.getOutInterceptors().add(new HttpHeaderInterceptor(headerParams));		
		try { // service�̶���������
			Object[] objects = client.invoke("service", paramsXml);
			String result = objects[0].toString();
			System.out.println("resule:"+result); //������ý��
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(client!=null){
				client.destroy();
			}
		}
	}

public Hubservice getConn(){
		
//		String address = "http://19.16.91.111:9016/apihub/services/auth?wsdl";
		String address = "http://19.16.91.111:9016";
		
		JaxWsProxyFactoryBean proxyFactory = new JaxWsProxyFactoryBean();
		proxyFactory.setAddress(address);
		proxyFactory.setServiceClass(Hubservice.class);
		Hubservice accountWebServiceProxy = (Hubservice) proxyFactory.create();
		
		((BindingProvider) accountWebServiceProxy).getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY,
				address);
		
		Client client = ClientProxy.getClient(accountWebServiceProxy);
		HTTPConduit conduit = (HTTPConduit) client.getConduit();
		HTTPClientPolicy policy = conduit.getClient();
		policy.setReceiveTimeout(600000);
		
		return accountWebServiceProxy;
	}
}


