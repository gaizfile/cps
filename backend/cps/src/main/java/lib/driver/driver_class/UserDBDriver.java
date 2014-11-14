package lib.driver.driver_class;

import lib.SessionFactoryUtil;
import lib.classes.CasttingMethods.CastList;
import lib.classes.securitymodel.encryption.DataHashing;
import core.classes.User;
import core.classes.UserLog;
import core.classes.UserRoles;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;



public class UserDBDriver {
	
Session session = SessionFactoryUtil.getSessionFactory().openSession();
DataHashing dataHashing=new DataHashing();
	
	
	public List<User> getUserDetails(){
		Transaction tx = null;
		try{
			tx = session.beginTransaction();
			Query query =  session.createQuery("select u from User as u");
			List<User> userList =CastList.castList(User.class, query.list()); 
			tx.commit();
			return userList;
		}
	catch(RuntimeException ex){
		if(tx != null && tx.isActive())
		{
			try{
				tx.rollback();
			}catch(HibernateException he){
				System.err.println("Error rolling back transaction");
				}
				throw ex;
			}
			return null;
		}
	}
	
	public List<UserRoles> getAllUserRoles(){
		Transaction tx = null;
		try{
			tx = session.beginTransaction();
			Query query =  session.createQuery("select ur from UserRoles as ur");
			List<UserRoles> userRoleList =CastList.castList(UserRoles.class, query.list()); 
			tx.commit();
			return userRoleList;
		}
	catch(RuntimeException ex){
		if(tx != null && tx.isActive())
		{
			try{
				tx.rollback();
			}catch(HibernateException he){
				System.err.println("Error rolling back transaction");
				}
				throw ex;
			}
			return null;
		}
	}
	
	public User getUserDetailsByUserID(int usrID){
		Transaction tx = null;
		try{
			tx = session.beginTransaction();
			Query query =  session.createQuery("select u from User as u where u.userID=:userID");
			query.setParameter("userID", usrID);
			List<User> userList =CastList.castList(User.class, query.list()); 
			tx.commit();
			return userList.get(0);
		}
		catch(RuntimeException ex){
			if(tx != null && tx.isActive())
			{
				try{
					tx.rollback();
				}catch(HibernateException he){
					System.err.println("Error rolling back transaction");
				}
				throw ex;
			}
			return null;
		}
	}
	
	


	public boolean insertUser(User usr,int usrRoleID){
		Transaction tx = null;
		try{	
			tx = session.beginTransaction();
			UserRoles role = (UserRoles) session.get(UserRoles.class, usrRoleID);
			usr.setUserRoles(role);
			session.save(usr);
			tx.commit();
			return true;
		}
		catch(RuntimeException ex){
			if(tx != null && tx.isActive())
			{
				try{
					tx.rollback();
				}
				catch(HibernateException he){
					System.err.println("Error rolling back transaction");
				}
				throw ex;
			}
			return false;
		}
	}
	

	public boolean deleteUser(User usr){
		Transaction tx=null;
		try {
			tx=session.beginTransaction();
			session.delete(usr);
			tx.commit();
			return true;	
		} 
		catch (RuntimeException ex) {
			if(tx != null && tx.isActive()){
				try{
					tx.rollback();
				}catch(HibernateException he){
					System.err.println("Error rolling back transaction");
				}
				throw ex;
			}
			return false;
		}		
	}
	

	

	public boolean updateUserDetails(User usrObj,int userRoleID){ 
		Transaction tx=null;
		try{
			tx=session.beginTransaction();
			User user=(User) session.get(User.class,usrObj.getUserID());
			UserRoles role=(UserRoles) session.get(UserRoles.class, userRoleID);
			
			user.setUserName(usrObj.getUserName());
			user.setUserTitle(usrObj.getUserTitle());
			user.setUserFullName(usrObj.getUserFullName());
			user.setUserNIC(usrObj.getUserNIC());
			user.setUserPassport(usrObj.getUserPassport());
			user.setUserDOB(usrObj.getUserDOB());
			user.setUserTelephone(usrObj.getUserTelephone());
			user.setUserGender(usrObj.getUserGender());
			user.setUserAddress(usrObj.getUserAddress());
			user.setUserMobileNo(usrObj.getUserMobileNo());
			user.setUserEmail(usrObj.getUserEmail());
			user.setUserLastUpdateDate(usrObj.getUserLastUpdateDate());
			
			user.setUserRoles(role);
			session.update(user);
			tx.commit();
			return true;
		}
		catch (RuntimeException ex) {
			if(tx != null && tx.isActive()){
				try{
					tx.rollback();
				}catch(HibernateException he){
					System.err.println("Error rolling back transaction");
				}
				throw ex;
			}
			return false;
		}
		
	}
	
	

	
	public boolean validateUserLoginDetails(User usrObj){
		String password_Hash=getUserPassword_Hash(usrObj.getUserName());
		String entered_Password=usrObj.getUserPassword();
		if(password_Hash!=null){
			try
			{
				if(dataHashing.validatePassword(entered_Password, password_Hash)){
					return true;
				}
				else{
					return false;
				}
			}
			catch(NoSuchAlgorithmException ex){
				ex.printStackTrace();
				return false;
			}
			catch(InvalidKeySpecException ex){
				ex.printStackTrace();
				return false;
			}
		}else{
			return false;
		}
	}

	
	private String getUserPassword_Hash(String usrName) {
		Transaction tx = null;
		try{
			tx = session.beginTransaction();
			Query query =  session.createQuery("select uc from User as uc where uc.userName=:usrName");
			query.setParameter("usrName", usrName);
			List UserList = query.list();
			if (UserList.size() == 0)
				return null;
			User user = (User) UserList.get(0);
			tx.commit();
			return user.getUserPassword();
		}
		catch(RuntimeException ex){
			if(tx != null && tx.isActive())
			{
				try{
					tx.rollback();
				}catch(HibernateException he){
					System.err.println("Error rolling back transaction");
				}
				throw ex;
			}
			return null;
		}
	}
	

	
	public User getValidUserDetails(String userName){
		Transaction tx = null;
		try{
			tx = session.beginTransaction();
			Query query =  session.createQuery("select u from User as u where u.userName = :usrName");
			query.setParameter("usrName", userName);
			List UserList = query.list();
			
			if (UserList.size() == 0)
				return null;
			
			User user = (User) UserList.get(0);
			tx.commit();
			return user;
		}		catch(RuntimeException ex){
			if(tx != null && tx.isActive())
			{
				try{
					tx.rollback();
				}catch(HibernateException he){
					System.err.println("Error rolling back transaction");
				}
				throw ex;
			}
			return null;
		}
	}
	

	public boolean updateUserPassword(User usrObj){
		Transaction tx=null;
		
		try{
			tx=session.beginTransaction();
			User user=(User) session.get(User.class,usrObj.getUserID());			
			try {
				user.setUserPassword(dataHashing.createHash(usrObj.getUserPassword()));
			} catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
				e.printStackTrace();
			}
			session.update(user);
			tx.commit();
			return true;
		}
		
		catch (RuntimeException ex) {
			if(tx != null && tx.isActive()){
				try{
					tx.rollback();
				}catch(HibernateException he){
					System.err.println("Error rolling back transaction");
				}
				throw ex;
			}
			return false;
		}
	}
	
	
	public List<UserLog> getUserLogByUserID(User usr){
		Transaction tx = null;
		try{
			tx = session.beginTransaction();
			Query query =  session.createQuery("select u from UserLog as u where u.logUserID=:usr order by u.logDatetime desc ");
			query.setParameter("usr", usr);
			query.setMaxResults(20);
			List<UserLog> userLogList = CastList.castList(UserLog.class, query.list()); 
			tx.commit();
			return userLogList;
		}
		catch(RuntimeException ex){
			if(tx != null && tx.isActive())
			{
				try{
					tx.rollback();
				}catch(HibernateException he){
					System.err.println("Error rolling back transaction");
				}
				throw ex;
			}
			return null;
		}
	}
	
	public boolean insertUserLog(UserLog usrLog,int usrID){
		Transaction tx = null;
		try{	
			tx = session.beginTransaction();
			User user = (User) session.get(User.class, usrID);
			usrLog.setLogUserID(user);
			session.save(usrLog);
			tx.commit();
			return true;
		}
		catch(RuntimeException ex){
			if(tx != null && tx.isActive())
			{
				try{
					tx.rollback();
				}
				catch(HibernateException he){
					System.err.println("Error rolling back transaction");
				}
				throw ex;
			}
			return false;
		}
	}
}
