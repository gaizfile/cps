package core.resources;

import java.util.List;

import lib.driver.driver_class.HospitalDBDriver;
import core.classes.Hospital;


import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import flexjson.JSONSerializer;

@Path("HospitalService")
public class HospitalResource {
	
	HospitalDBDriver hospitalDBDriver=new HospitalDBDriver();

	@GET
	@Path("/getAllHospitals")
	@Produces(MediaType.APPLICATION_JSON)
	public String getUserDetails(){
		String result=null;
		List<Hospital> hList =hospitalDBDriver.getAllHospitals();

		JSONSerializer serializor=new JSONSerializer();
		result = serializor.include("hospital_ID","hospital_IP","hospital_Port","hospital_Name","hospital_District","hospital_Province")
									.exclude("*.class").serialize(hList);
		return result;
	}
	
	
	@GET
	@Path("/getHospitalById/{hID}")
	@Produces(MediaType.APPLICATION_JSON)
	public String getHospitalDetailsByID(@PathParam("hID")  String hID){
		String result=null;
		Hospital hospital =hospitalDBDriver.getHospitalDetailsByID(hID);
		JSONSerializer serializor=new JSONSerializer();
		result = serializor.include("hospital_ID","hospital_IP","hospital_Port","hospital_Name","hospital_District","hospital_Province").exclude("*").serialize(hospital);
		return result;
	}
	
	
	@POST
	@Path("/insertHospital")
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes(MediaType.APPLICATION_JSON)
	public  String registerHospital(JSONObject uJson){
		String result="false";
		boolean r=false;
		Hospital h=new Hospital();

		try{
			 h.setHospital_ID(uJson.getString("hospital_ID"));			
			 h.setHospital_IP(uJson.getString("hospital_IP"));
			 h.setHospital_Port(uJson.getInt("hospital_Port"));
			 h.setHospital_Name(uJson.getString("hospital_Name"));
			 h.setHospital_District(uJson.getString("hospital_District"));
			 h.setHospital_Province(uJson.getString("hospital_Province"));

			 r=hospitalDBDriver.insertHospital(h);
			 result=String.valueOf(r);
			 return result;
		}
		
		catch( JSONException ex){
			ex.printStackTrace();	
			return result;
		}
		catch( Exception ex){
			ex.printStackTrace();
			return ex.getMessage();
		}
	}
	
	
	@DELETE
	@Path("/deleteHospital")
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes(MediaType.APPLICATION_JSON)
	public  String deleteHospital(JSONObject jsnObj){
		String result="false";
		boolean r=false;
		Hospital h=new Hospital();
		try{	
			h.setHospital_ID(jsnObj.getString("hospital_ID"));		
			r=hospitalDBDriver.deleteHospital(h);
			result=String.valueOf(r);
			return result;
		}
		catch( JSONException ex){
			ex.printStackTrace();	
			return result;
		}	
	}
	
	
	
	@PUT
	@Path("/updateHospital")
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes(MediaType.APPLICATION_JSON)
	public  String updateHospitalDetails(JSONObject uJson){
		String result="false";
		boolean r=false;
		Hospital h=new Hospital();

		try{
			 String oldID = uJson.getString("old_ID");
			 h.setHospital_ID(uJson.getString("hospital_ID"));			
			 h.setHospital_IP(uJson.getString("hospital_IP"));
			 h.setHospital_Port(uJson.getInt("hospital_Port"));
			 h.setHospital_Name(uJson.getString("hospital_Name"));
			 h.setHospital_District(uJson.getString("hospital_District"));
			 h.setHospital_Province(uJson.getString("hospital_Province"));

			 r=hospitalDBDriver.updateHospitalDetails(h,oldID);
			 result=String.valueOf(r);
			 return result;
		}
		catch( JSONException ex){
			ex.printStackTrace();	
			return result;
		}
		catch( Exception ex){
			ex.printStackTrace();
			return ex.getMessage();
		}

	}
	
}

