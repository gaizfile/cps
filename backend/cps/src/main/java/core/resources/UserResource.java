package core.resources;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.List;

import lib.driver.driver_class.UserDBDriver;
import core.classes.User;
import core.classes.UserLog;
import core.classes.UserRoles;
import lib.classes.securitymodel.encryption.DataHashing;

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

@Path("UserService")
public class UserResource {
	
	UserDBDriver userDBDriver=new UserDBDriver();
	DataHashing dataHashing = new DataHashing();
	
	
	@POST
	@Path("/userValidation")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public  String userValidation(JSONObject jsnObj) {
		
		String result="false";
		User usr=new User();
		try {
			usr.setUserName(jsnObj.getString("userName"));
			usr.setUserPassword(jsnObj.getString("userPassword"));
		} catch (JSONException e) {
			e.printStackTrace();
		}
		if(userDBDriver.validateUserLoginDetails(usr)){
			User userDetails  = userDBDriver.getValidUserDetails(usr.getUserName());
			JSONSerializer serializor=new JSONSerializer();
			result = serializor.include("userID","userName","userFullName","userTitle","userRoles.userRoleID","userRoles.userRoleName","userCreateDate","userLastUpdateDate").exclude("*").serialize(userDetails);
			return result;
		}
		else{
			return result;
		}
	}
	
	
	@GET
	@Path("/getAllUsers")
	@Produces(MediaType.APPLICATION_JSON)
	public String getUserDetails(){
		String result=null;
		List<User> usrList =userDBDriver.getUserDetails();
		JSONSerializer serializor=new JSONSerializer();
		result = serializor.include("userRoles.userRoleID","userRoles.userRoleName","userID","userName","userMobileNo","userCreateDate","userLastUpdateDate")
									.exclude("*.class","employees.*","userPassword").serialize(usrList);
		return result;
	}
	
	
	@GET
	@Path("/getAllUserRoles")
	@Produces(MediaType.APPLICATION_JSON)
	public String getAllUserRoles(){
		String result=null;
		List<UserRoles> usrRoleList =userDBDriver.getAllUserRoles();
		JSONSerializer serializor=new JSONSerializer();
		result = serializor.include("userRoleID","userRoleName").serialize(usrRoleList);
		return result;
		
	}
	
	
	
	@GET
	@Path("/getUserById/{uID}")
	@Produces(MediaType.APPLICATION_JSON)
	public String getUserDetailsByUserID(@PathParam("uID")  int uID){
		String result=null;
		User usr =userDBDriver.getUserDetailsByUserID(uID);
		JSONSerializer serializor=new JSONSerializer();
		result = serializor.include("userID","userName","userRoles.userRoleID","userRoles.userRoleName","userTitle","userFullName",
				"userNIC","userPassport","userDOB","userTelephone","userGender","userAddress","userMobileNo","userEmail","userCreateDate","userLastUpdateDate").exclude("*").serialize(usr);
		return result;
	}
	
	
	@POST
	@Path("/insertUser")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public  String registerUser( JSONObject uJson){
		User usr=new User();
		try{
			 int userRoleID=uJson.getInt("userRoles");
			 usr.setUserName(uJson.getString("userName"));		
			 
			try {
				usr.setUserPassword(dataHashing.createHash(uJson.getString("userPassword")));
			} catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
				e.printStackTrace();
			}

			 usr.setUserTitle(uJson.getString("userTitle"));
			 usr.setUserFullName(uJson.getString("userFullName"));
			 usr.setUserNIC(uJson.getString("userNIC"));
			 usr.setUserPassport(uJson.getString("userPassport"));
			 usr.setUserDOB(uJson.getString("userDOB"));
			 usr.setUserTelephone(uJson.getString("userTelephone"));
			 usr.setUserGender(uJson.getString("userGender").charAt(0));
			 usr.setUserAddress(uJson.getString("userAddress"));
			 usr.setUserMobileNo(uJson.getString("userMobileNo"));
			 usr.setUserEmail(uJson.getString("userEmail"));
			 usr.setUserCreateDate(uJson.getString("userCreateDate"));
			 usr.setUserLastUpdateDate(uJson.getString("userLastUpdateDate"));
			 userDBDriver.insertUser(usr,userRoleID);
			 JSONSerializer serializor=new JSONSerializer();
			 return serializor.include("userName").exclude("*").serialize(usr);
		}
		catch( JSONException ex){
			ex.printStackTrace();	
			return ex.getMessage();
		}
		catch( Exception ex){
			ex.printStackTrace();
			return ex.getMessage();
		}
	}
	
	
	@DELETE
	@Path("/deleteUser")
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes(MediaType.APPLICATION_JSON)
	public  String deleteUser(JSONObject jsnObj){
		String result="false";
		boolean r=false;
		User usrObj=new User();
		try{	
			System.out.println(jsnObj.getString("uID"));
			usrObj.setUserID(jsnObj.getInt("uID"));			
			r=userDBDriver.deleteUser(usrObj);
			result=String.valueOf(r);
			System.out.println(result);
			return result;
		}
		catch( JSONException ex){
			ex.printStackTrace();	
			return result;
		}	
	}
	
	
	
	@PUT
	@Path("/updateUser")
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes(MediaType.APPLICATION_JSON)
	public  String updateUserDetails(JSONObject uJson){
		String result="false";
		boolean r=false;
		User usr=new User();
		try{
			 int userRoleID=uJson.getInt("userRoles");
			 usr.setUserName(uJson.getString("userName"));	
			 usr.setUserID(uJson.getInt("userID"));	
			 //usr.setUserPassword(uJson.getString("userPassword"));
			 usr.setUserTitle(uJson.getString("userTitle"));
			 usr.setUserFullName(uJson.getString("userFullName"));
			 usr.setUserNIC(uJson.getString("userNIC"));
			 usr.setUserPassport(uJson.getString("userPassport"));
			 usr.setUserDOB(uJson.getString("userDOB"));
			 usr.setUserTelephone(uJson.getString("userTelephone"));
			 usr.setUserGender(uJson.getString("userGender").charAt(0));
			 usr.setUserAddress(uJson.getString("userAddress"));
			 usr.setUserMobileNo(uJson.getString("userMobileNo"));
			 usr.setUserEmail(uJson.getString("userEmail"));
			 //usr.setUserCreateDate(uJson.getString("userCreateDate"));
			 usr.setUserLastUpdateDate(uJson.getString("userLastUpdateDate"));
			 System.out.println(uJson.getString("userDOB"));
			 
			r=userDBDriver.updateUserDetails(usr,userRoleID);
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
	
	
	@POST
	@Path("/checkOldPassword")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String checkOldPassword(JSONObject jsnObj){
		String result="";
		boolean r=false;
		User usr=new User();
		try{
			usr.setUserID(jsnObj.getInt("userID"));
			usr.setUserName(jsnObj.getString("uUserName"));
			usr.setUserPassword(jsnObj.getString("uUserPassword"));
			r=userDBDriver.validateUserLoginDetails(usr);
			result=String.valueOf(r);
			return result;
		}
		catch(JSONException ex){
			ex.printStackTrace();	
			return result;
		}
	}
	
	
	
	
	@POST
	@Path("/updateOldPassword")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String updateOldPassword(JSONObject jsnObj){
		String result="";
		boolean r=false;
		User usr=new User();
		try{
			usr.setUserID(jsnObj.getInt("userID"));
			usr.setUserName(jsnObj.getString("uUserName"));
			usr.setUserPassword(jsnObj.getString("newUserPassword"));			
			r=userDBDriver.updateUserPassword(usr);
			result=String.valueOf(r);
			return result;
		}
		catch(JSONException ex){
			ex.printStackTrace();	
			return result;
		}
	}
	
	
	@GET
	@Path("/getUserLogById/{uID}")
	@Produces(MediaType.APPLICATION_JSON)
	public String getUserLogByUserID(@PathParam("uID")  int uID){
		String result=null;
		User usr = userDBDriver.getUserDetailsByUserID(uID);
		System.out.println("assdas");
		List<UserLog> usrlog = userDBDriver.getUserLogByUserID(usr);
		System.out.println("assdas");
		JSONSerializer serializor=new JSONSerializer();
		result = serializor.include("logID","logSection","log","logDatetime").exclude("*").serialize(usrlog);
		return result;
	}
	
	
	@PUT
	@Path("/insertUserLog")
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes(MediaType.APPLICATION_JSON)
	public  String insertUserLog(JSONObject uJson){
		System.out.println("Called");
		String result="false";
		boolean r=false;
		UserLog usrLog=new UserLog();

		try{
			 int userID=uJson.getInt("userID");
			 usrLog.setLogSection(uJson.getString("logSection"));			
			 usrLog.setLog(uJson.getString("log"));
			 usrLog.setLogDatetime(uJson.getString("dateTime"));
			 r=userDBDriver.insertUserLog(usrLog,userID);
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

