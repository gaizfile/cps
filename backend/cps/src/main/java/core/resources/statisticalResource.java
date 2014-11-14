package core.resources;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import core.classes.Hospital;
import core.classes.StatStaff;
import core.classes.StatWard;
import flexjson.JSONSerializer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import lib.driver.driver_class.HospitalDBDriver;
import lib.driver.driver_class.StatisticsDBDriver;

@Path("/stats")
public class statisticalResource extends TimerTask {

	StatisticsDBDriver statisticsDBDriver = new StatisticsDBDriver();

	@Path("/run")
	@GET
	@Produces(MediaType.TEXT_HTML)
	public void returnTitle() {
		TimerTask timerTask = new statisticalResource();
		Timer timer = new Timer(true);
		// the wait time you want to wait between cycles.
		timer.scheduleAtFixedRate(timerTask, 0, 40 * 1000);
		System.out.println("Timer started");
	}

	public void run() {
		System.out.println("Statistical Analyser Started !" + new Date());
		getStaffCounts();
		getWardCounts();
		System.out.println("Statistical Analyser Stoped !" + new Date());
	}

	private void PAUSE() {
		try {
			// the time allocated for one cycle
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	private void getStaffCounts() {
		List<Hospital> HospitalList = statisticsDBDriver.getHospitals();
		for (Hospital hospital : HospitalList) {

			try {
				URL url = new URL("http://" + hospital.getHospital_IP() + ":"
						+ hospital.getHospital_Port()
						+ "/HIS_API/rest/statistics/getStaffStats");
				System.out.println(url.toString());
				HttpURLConnection conn = (HttpURLConnection) url
						.openConnection();
				conn.setRequestMethod("GET");
				conn.setRequestProperty("Accept", "application/json");

				if (conn.getResponseCode() != 200) {
					throw new RuntimeException("Failed : HTTP error code : "
							+ conn.getResponseCode());
				}

				BufferedReader br = new BufferedReader(new InputStreamReader(
						(conn.getInputStream())));
				String output = "";
				if ((output = br.readLine()) != null) {
					int mdoctor = 0;
					int mnurse = 0;
					int mmlt = 0;
					int ma_phamacist = 0;
					int mc_phamacist = 0;
					int fdoctor = 0;
					int fnurse = 0;
					int fmlt = 0;
					int fa_phamacist = 0;
					int fc_phamacist = 0;
					System.out.println("\nJSon Array Recieved By CPS: "
							+ output + "\n\n");
					try {
						JSONObject jsnobject = new JSONObject(output);
						JSONArray jsonArray = jsnobject.getJSONArray("Staff");
						for (int i = 0; i < jsonArray.length(); i++) {
							JSONObject staffObj = jsonArray.getJSONObject(i);
							if (Integer
									.valueOf(staffObj.get("Role").toString()) == 1)
								mdoctor++;
							else if (Integer.valueOf(staffObj.get("Role")
									.toString()) == 2)
								mnurse++;
							else if (Integer.valueOf(staffObj.get("Role")
									.toString()) == 3)
								mmlt++;
							else if (Integer.valueOf(staffObj.get("Role")
									.toString()) == 4)
								mc_phamacist++;
							else if (Integer.valueOf(staffObj.get("Role")
									.toString()) == 5)
								ma_phamacist++;
							else if (Integer.valueOf(staffObj.get("Role")
									.toString()) == 6)
								fdoctor++;
							else if (Integer.valueOf(staffObj.get("Role")
									.toString()) == 7)
								fnurse++;
							else if (Integer.valueOf(staffObj.get("Role")
									.toString()) == 8)
								fmlt++;
							else if (Integer.valueOf(staffObj.get("Role")
									.toString()) == 9)
								fc_phamacist++;
							else if (Integer.valueOf(staffObj.get("Role")
									.toString()) == 10)
								fa_phamacist++;
						}
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					StatStaff staff = new StatStaff();
					staff.setHospital(hospital);
					staff.setYear(Calendar.getInstance().get(Calendar.YEAR));
					staff.setmDoctor(String.valueOf(mdoctor));
					staff.setmNurse(String.valueOf(mnurse));
					staff.setmMLT(String.valueOf(mmlt));
					staff.setmAssistant_Pharmasist(String.valueOf(ma_phamacist));
					staff.setmCheif_Pharmasist(String.valueOf(mc_phamacist));
					staff.setfDoctor(String.valueOf(fdoctor));
					staff.setfNurse(String.valueOf(fnurse));
					staff.setfMLT(String.valueOf(fmlt));
					staff.setfAssistant_Pharmasist(String.valueOf(fa_phamacist));
					staff.setfCheif_Pharmasist(String.valueOf(fc_phamacist));

					// Check whether the row already exists (Hospital ID, Year)
					boolean exists = statisticsDBDriver.getStatStaffFromID(
							hospital,
							Integer.valueOf(Calendar.getInstance().get(
									Calendar.YEAR)));
					if (exists) {
						System.out.println("Staff Exists : So Update");
						statisticsDBDriver.updateStaff(
								hospital,
								Integer.valueOf(Calendar.getInstance().get(
										Calendar.YEAR)), staff);
					} else {
						System.out.println("No Staff Exists : So Insert");
						statisticsDBDriver.insertStaff(staff);
					}
				}

				conn.disconnect();
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		PAUSE();
	}

	@POST
	@Path("/geWardBeds")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String geWardBeds(JSONObject json) {
		HospitalDBDriver hDriver = new HospitalDBDriver();
		String result = null;
		Hospital h = null;
		int year = 0;
		try {
			h = hDriver.getHospitalDetailFromID(json.getString("hospitalID"));
		} catch (JSONException e) {
			e.printStackTrace();
		}
		List<StatWard> statWardList = statisticsDBDriver.getWardBeds(h);
		JSONSerializer serializor = new JSONSerializer();
		result = serializor.exclude("*.class").serialize(statWardList);
		return result;
	}

	
	@POST
	@Path("/getDistributionOfSpecialists")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String getDistributionOfSpecialists(JSONObject json) {
		HospitalDBDriver hDriver = new HospitalDBDriver();
		String result = null;
		Hospital h = null;
		int year = 0;
		try {
			h = hDriver.getHospitalDetailFromID(json.getString("hospitalID"));
		} catch (JSONException e) {
			e.printStackTrace();
		}
		List<StatStaff> statStaffList = statisticsDBDriver
				.getDistributionOfSpecialists(h);
		JSONSerializer serializor = new JSONSerializer();
		result = serializor.exclude("*.class").serialize(statStaffList);
		return result;
	}
	
	@SuppressWarnings("unchecked")
	private void getWardCounts(){
    	List<Hospital> HospitalList= statisticsDBDriver.getHospitals();
    	for(Hospital hospital : HospitalList){

    		try{
	    		URL url = new URL("http://"+hospital.getHospital_IP()+":"+hospital.getHospital_Port()+"/HIS_API/rest/statistics/getWardStats");
	    		System.out.println(url.toString());
	    		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	    		conn.setRequestMethod("GET");
	    		conn.setRequestProperty("Accept", "application/json");
	     
	    		if (conn.getResponseCode() != 200) {
	    			throw new RuntimeException("Failed : HTTP error code : "+ conn.getResponseCode());
	    		}
	     
	    		BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
	    		String output="";
	    		if((output = br.readLine()) != null) {
	    			List<StatWard> wardlist=new ArrayList<StatWard>();
	    			System.out.println("\nJSon Array Recieved By CPS: "+output+"\n\n");
					try {
	    			JSONObject jsnobject = new JSONObject(output);
	    			JSONArray jsonArray = jsnobject.getJSONArray("Ward");
	    			    for (int i = 0; i < jsonArray.length(); i++) {
	    			        JSONObject wardObj = jsonArray.getJSONObject(i);
	    			        StatWard ward= new StatWard();
	    			        ward.setHospital(hospital);
	    			        ward.setWard_year(Integer.valueOf(Calendar.getInstance().get(Calendar.YEAR)));
	    			        ward.setWard_no(wardObj.get("ward_no").toString());
	    			        ward.setWard_category(wardObj.get("category").toString());
	    			        ward.setWard_gender(wardObj.get("gender").toString().charAt(0));
	    			        ward.setWard_beds(Integer.valueOf(wardObj.get("beds").toString()));
	    			        wardlist.add(ward);
	    			    }
					} catch (JSONException e) {
						e.printStackTrace();
					}
					
					statisticsDBDriver.updateWard(wardlist);
					
	    		}
	    		
	    		conn.disconnect();
	    		System.out.println("\nDisconnected From HIS Server");
    	  } catch (MalformedURLException e) {
    		e.printStackTrace();
    	  } catch (IOException e) {
    		e.printStackTrace();
    	  }
    	}
    	PAUSE();
    }

}
