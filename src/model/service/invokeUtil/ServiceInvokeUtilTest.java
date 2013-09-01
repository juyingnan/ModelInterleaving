package model.service.invokeUtil;

import javax.xml.namespace.QName;
import org.apache.axis2.AxisFault;
import org.apache.axis2.addressing.EndpointReference;
import org.apache.axis2.client.Options;
import org.apache.axis2.rpc.client.RPCServiceClient;

public class ServiceInvokeUtilTest {
	/* Test for invoke services: createBill
	 * Address: "http://localhost:8080/interleaveServices/services/Bill";
	 * NamespaceURI: "http://interleave"
	 * Method: "createBill"
	 * Parameter Value: "Prescription"
	 */
	public static void main(String[] args) throws AxisFault {
		RPCServiceClient serviceClient = new RPCServiceClient();
		Options options = serviceClient.getOptions();
		EndpointReference targetEPR = new EndpointReference(
				"http://192.168.0.104:8080/interleaveServices/services/Bill");
		options.setTo(targetEPR);

		//method to be invoked
		QName methodName = new QName("http://interleave","createBill");
		//values of parameters
		Object[] parameterValue = new Object[]{"Prescription"};
		//result of method invoked
		String result = (String) serviceClient.invokeBlocking(methodName, parameterValue, new Class[]{String.class})[0];
		System.out.println(result);
	}
}