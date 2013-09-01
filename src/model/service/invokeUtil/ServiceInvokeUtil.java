package model.service.invokeUtil;

import javax.xml.namespace.QName;
import org.apache.axis2.AxisFault;
import org.apache.axis2.addressing.EndpointReference;
import org.apache.axis2.client.Options;
import org.apache.axis2.rpc.client.RPCServiceClient;

public class ServiceInvokeUtil {
	public String invokeService(String address, String nsURI, String method, String paraValue){
		String result = null;
		try {
			RPCServiceClient serviceClient = new RPCServiceClient();
			Options options = serviceClient.getOptions();
			EndpointReference targetEPR = new EndpointReference(address);
			options.setTo(targetEPR);	
			QName qName = new QName(nsURI, method);
			Object[] pValue = new Object[]{paraValue};
//			String t = "606-471-5451";
//			Object[] pValue = new Object[]{t};
			result = (String) serviceClient.invokeBlocking(qName, pValue, new Class[]{String.class})[0];
		} catch (AxisFault e) {
			System.out.println("invokeService Error"+e);
		}
		
		return result;
	}
}