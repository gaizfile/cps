package lib.driver.HL7;

import org.codehaus.jettison.json.JSONObject;
import org.hibernate.Session;
import lib.SessionFactoryUtil;
import lib.driver.driver_class.HospitalDBDriver;
import lib.driver.driver_class.PatientDBDriver;
import ca.uhn.hl7v2.DefaultHapiContext;
import ca.uhn.hl7v2.HL7Exception;
import ca.uhn.hl7v2.HapiContext;
import ca.uhn.hl7v2.model.v26.message.ADT_A01;
import ca.uhn.hl7v2.model.v26.segment.MSH;
import ca.uhn.hl7v2.model.v26.segment.NK1;
import ca.uhn.hl7v2.model.v26.segment.OBX;
import ca.uhn.hl7v2.model.v26.segment.PID;
import ca.uhn.hl7v2.parser.Parser;
import core.classes.Patient;


public class PatientHL7Convert {

	PatientDBDriver patientDBDriver = new PatientDBDriver();
	HospitalDBDriver hospitalDBDriver= new HospitalDBDriver();
	
	Session session = SessionFactoryUtil.getSessionFactory().getCurrentSession();

	
	public ADT_A01 createPatientHL7Obj(JSONObject  pJson){
		try { 
			ADT_A01 adt = new ADT_A01(); // Patient Admission/Registration Message
			adt.initQuickstart("ADT", "A01", "P");
			
			//Populate the MSH Segment
			MSH mshSegment = adt.getMSH();
			//System.out.println(pJson.toString());
			mshSegment.getSendingApplication().getNamespaceID().setValue(pJson.get("hospital").toString());
			mshSegment.getSequenceNumber().setValue("1");
			
			//Populate the PID Segment
			PID pid = adt.getPID(); 
			pid.getPatientName(0).getPrefixEgDR().setValue(pJson.get("title").toString()); // Title (Mr., Mrs.)
			pid.getPatientName(0).getGivenName().setValue(pJson.get("fullname").toString());
			pid.getPatientAlias(0).getGivenName().setValue(pJson.getString("personalname")); // Personal name
			pid.getPatientID().getIDNumber().setValue(pJson.get("nic").toString()); //NIC number
			pid.getPid18_PatientAccountNumber().getIDNumber().setValue(pJson.get("hin").toString());//HIN number

			pid.getSSNNumberPatient().setValue(pJson.get("passport").toString()); //Passport number
			pid.getPid15_PrimaryLanguage().getOriginalText().setValue(pJson.get("lang").toString());//preferred language
			pid.getDateTimeOfBirth().setValue(pJson.get("dob").toString());     
			pid.getAdministrativeSex().setValue(pJson.get("gender").toString()); 
			pid.getMaritalStatus().getText().setValue(pJson.get("cstatus").toString()); // civil status
			pid.getPhoneNumberHome(0).getTelephoneNumber().setValue(pJson.get("telephone").toString());
			pid.getPatientAddress(0).getStreetAddress().getStreetOrMailingAddress().setValue(pJson.get("address").toString());;
			pid.getCitizenship(0).getText().setValue(pJson.get("citizen").toString());
			
			NK1 nk1 = adt.getNK1(); //Next Kin Details
			nk1.getContactPersonSName(0).getGivenName().setValue(pJson.get("contactpname").toString());
			nk1.getContactPersonSTelephoneNumber(0).getTelephoneNumber().setValue(pJson.get("contactpno").toString());
	          
			OBX obx= adt.getOBX(); //Observations
			obx.getObx11_ObservationResultStatus().setValue(pJson.get("remarks").toString());
			
			//Pass it through a HL7 parser to validate data. In case of incompatible data it will show an error. See TRY-CATCH 
			@SuppressWarnings("resource")
			HapiContext context = new DefaultHapiContext();
			Parser parser = context.getPipeParser();
			String encoded=parser.encode(adt);
			System.out.println("\n"+encoded.toString());
			System.out.println("\n"+"Hl7 Message Validated!\n");
			return adt;
		} catch (HL7Exception e) {
			 //If an error occurred in the parser it will be shown here.
			 e.printStackTrace(); 
			 return null; 
		} catch (Exception e){
			e.printStackTrace();
			return null; 
		}
	}

	
	public ADT_A01 createPatientVisitHL7Obj(JSONObject  vJson){
		try { 
			ADT_A01 adt = new ADT_A01(); // Patient Admission/Registration Message
			adt.initQuickstart("ADT", "A01", "P");
			
			
			//Pass it through a HL7 parser to validate data. In case of incompatible data it will show an error. See TRY-CATCH 
			@SuppressWarnings("resource")
			HapiContext context = new DefaultHapiContext();
			Parser parser = context.getPipeParser();
			String encoded=parser.encode(adt);
			System.out.println("\n"+encoded.toString());
			
			return adt;
		} catch (HL7Exception e) {
			 //If an error occurred in the parser it will be shown here.
			 e.printStackTrace(); 
			 return null; 
		} catch (Exception e){
			e.printStackTrace();
			return null; 
		}
	}
	
	
	public Patient createPatientObj(ADT_A01 adt){
		
		Patient patient  =  new Patient();
		patient.setPatientTitle(adt.getPID().getPatientName(0).getPrefixEgDR().getValue());
		patient.setPatientFullName(adt.getPID().getPatientName(0).getGivenName().getValue());	
		patient.setPatientPersonalUsedName(adt.getPID().getPatientAlias(0).getGivenName().getValue());
		patient.setPatientNIC(adt.getPID().getPatientID().getIDNumber().getValue());
		patient.setPatientHIN(adt.getPID().getPid18_PatientAccountNumber().getIDNumber().getValue());
		patient.setPatientPhoto(null);
		patient.setPatientPassport(adt.getPID().getSSNNumberPatient().getValue());
		patient.setPatientDateOfBirth(adt.getPID().getDateTimeOfBirth().getValue());
		patient.setPatientContactPName(adt.getNK1().getContactPersonSName(0).getGivenName().getValue());
		patient.setPatientContactPNo(adt.getNK1().getContactPersonSTelephoneNumber(0).getTelephoneNumber().getValue());	
		patient.setPatientGender(adt.getPID().getAdministrativeSex().getValue());
		patient.setPatientCivilStatus(adt.getPID().getMaritalStatus().getText().getValue());
		patient.setPatientAddress(adt.getPID().getPatientAddress(0).getStreetAddress().getStreetOrMailingAddress().getValue());
		patient.setPatientTelephone(adt.getPID().getPhoneNumberHome(0).getTelephoneNumber().getValue());
		patient.setPatientPreferredLanguage(adt.getPID().getPid15_PrimaryLanguage().getOriginalText().getValue());
		patient.setPatientCitizenship(adt.getPID().getCitizenship(0).getText().getValue());
		patient.setPatientRemarks(adt.getOBX().getObx11_ObservationResultStatus().getValue());
		patient.setHospitalID(hospitalDBDriver.getHospitalDetailFromID(adt.getMSH().getSendingApplication().getNamespaceID().getValue()));
		patient.setPatientActive(1);
		
		return patient;
	}

}
