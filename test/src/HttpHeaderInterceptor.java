
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.message.Message;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.phase.Phase;

public class HttpHeaderInterceptor extends AbstractPhaseInterceptor<Message> {

	private Map<String, String> params;

	public HttpHeaderInterceptor() {
		super(Phase.POST_PROTOCOL);
	}

	public HttpHeaderInterceptor(Map<String, String> params) {
		super(Phase.POST_PROTOCOL);
		this.params = params;
	}

	public void handleMessage(Message message) throws Fault {
		Map<String, List> headers = (Map<String, List>) message.get(Message.PROTOCOL_HEADERS);
		try {
			for (String key : params.keySet()) {
				headers.put(key, Collections.singletonList(params.get(key)));
			}
		} catch (Exception e) {
			throw new Fault(e);
		}
	}

}
