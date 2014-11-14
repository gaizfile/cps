package core.resources;

import java.util.Date;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import lib.driver.HL7.PatientHL7Convert;
import lib.driver.driver_class.PatientDBDriver;
import ca.uhn.hl7v2.model.v26.message.ADT_A01;
import ca.uhn.hl7v2.model.v26.segment.MSH;
import ca.uhn.hl7v2.model.v26.segment.NK1;
import ca.uhn.hl7v2.model.v26.segment.OBX;
import ca.uhn.hl7v2.model.v26.segment.PID;
import core.classes.Patient;
import core.classes.Allergy;
import flexjson.JSONSerializer;

/*
 * This class define all the generic REST Services necessary for handling patient's information.
 */

@SuppressWarnings("unused")
@Path("patient")
public class PatientResource {

	PatientDBDriver patientDBDriver = new PatientDBDriver();
	
		//Add new patient
		@POST
		@Path("/newPatient")
		@Produces(MediaType.APPLICATION_JSON)
		@Consumes(MediaType.APPLICATION_JSON)
		public String newPatient(JSONObject pJson)
		{
			try {
				Patient patient=null;
				PatientHL7Convert hl7Con = new PatientHL7Convert();
				ADT_A01 adt = hl7Con.createPatientHL7Obj(pJson);
				patient=hl7Con.createPatientObj(adt);
				patientDBDriver.insertPatient(patient);
				JSONSerializer jsonSerializer = new JSONSerializer();
				return jsonSerializer.include("patientHIN").exclude("*").serialize(patient);
			} catch (Exception e) {
				System.out.println(e.getMessage());
				return "Error Occured"; 
			}
		}

		
		//Update patient details
		@POST
		@Path("/updatePatient")
		@Produces(MediaType.APPLICATION_JSON)
		@Consumes(MediaType.APPLICATION_JSON)
		public String updatePatient(JSONObject pJson)
		{
			try {
				Patient patient=null;
				PatientHL7Convert hl7Con = new PatientHL7Convert();
				ADT_A01 adt = hl7Con.createPatientHL7Obj(pJson);
				patient=hl7Con.createPatientObj(adt);
				patientDBDriver.updatePatient(patient);
				JSONSerializer jsonSerializer = new JSONSerializer();
				return jsonSerializer.include("patientHIN").exclude("*").serialize(patient);
			} catch (Exception e) {
				 System.out.println(e.getMessage());
				 return "Error Occured"; 
			}
		}
		
		@POST
		@Path("/addAllergy")
		@Produces(MediaType.TEXT_PLAIN)
		@Consumes(MediaType.APPLICATION_JSON)
		public String addAllergy(JSONObject ajson) {
			try {
				Allergy allergy =new Allergy();
				String patientHIN = ajson.getString("pHin");
				allergy.setAllergyName(ajson.get("name").toString());
				allergy.setAllergyStatus(ajson.get("status").toString());
				allergy.setAllergyRemarks(ajson.get("remarks").toString());
				allergy.setAllergyActive(1);
				patientDBDriver.addAllergy(allergy, patientHIN);
				return "True";
			}
			catch (JSONException e) {
				e.printStackTrace();
				return "False";
			}		
			catch (Exception e) {
				return "False";
			}
		}
		
		
		@PUT
		@Path("/updateAllergy")
		@Produces(MediaType.TEXT_PLAIN)
		@Consumes(MediaType.APPLICATION_JSON)
		public String updateAllergy(JSONObject ajson) {
			try {
				Allergy allergy =new Allergy();
				String patientHIN = ajson.getString("pHin");
				allergy.setAllergyID(ajson.getInt("allergyid"));  
				allergy.setAllergyName(ajson.get("name").toString());
				allergy.setAllergyStatus(ajson.get("status").toString());
				allergy.setAllergyRemarks(ajson.get("remarks").toString());
				allergy.setAllergyActive(ajson.getInt("active"));
				//allergyDBDriver.updateAllergy(allergy,patientHIN);		
				return "True";
			}catch (JSONException e) {
				e.printStackTrace();
				return "False";
			} 
			catch (Exception e) {		 
				return "False" ;
			}

		}
		
		
		/*//Add new visit for a patient
		@POST
		@Path("/newPatientVisit")
		@Produces(MediaType.APPLICATION_JSON)
		@Consumes(MediaType.APPLICATION_JSON)
		public String newPatientVisit(JSONObject pJson)
		{
			try {
				Patient patient=null;
				PatientHL7Convert hl7Con = new PatientHL7Convert();
				ADT_A01 adt = hl7Con.createPatientHL7Obj(pJson);
				patient=hl7Con.createPatientObj(adt);
				patientDBDriver.insertPatient(patient);
				JSONSerializer jsonSerializer = new JSONSerializer();
				return jsonSerializer.include("patientHIN").exclude("*").serialize(patient);
			} catch (Exception e) {
				System.out.println(e.getMessage());
				return "Error Occured"; 
			}
		}*/

		//Get Patient details
		@GET
		@Path("/getPatientsByKey/{keyID}")
		@Produces(MediaType.APPLICATION_JSON)
		public String PatientDetails(@PathParam("keyID") String keyID)
		{
			Patient patient =  new Patient();
			patient = patientDBDriver.getPatientDetails(keyID);
			JSONSerializer jsonSerializer = new JSONSerializer();
			System.out.println("\nCPS Sent The Patient Object : "+patient.getPatientNIC()+" Name : "+patient.getPatientFullName());
			return jsonSerializer.include("allergies").exclude("hospitalID","*.class").serialize(patient);
		}



	
}












