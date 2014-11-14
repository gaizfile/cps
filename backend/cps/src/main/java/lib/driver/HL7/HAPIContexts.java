package lib.driver.HL7;

import ca.uhn.hl7v2.HapiContext;
import ca.uhn.hl7v2.app.HL7Service;

public class HAPIContexts {

	private static int port = 1011; // The port to listen on
	boolean useTls = false; // Should we use TLS/SSL?
	
	
	public HL7Service createService(HapiContext context, String type, String discriptor){
		HL7Service service = context.newServer(port, useTls);
		service.registerApplication(type, discriptor, new Handler());
		return service;
	}
	

}
