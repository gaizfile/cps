package lib.driver.driver_class;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.List;

import lib.SessionFactoryUtil;
import lib.classes.CasttingMethods.CastList;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import core.classes.Hospital;
import core.classes.User;
import core.classes.UserRoles;

public class HospitalDBDriver {
	
	Session session = SessionFactoryUtil.getSessionFactory().openSession();
	
	@SuppressWarnings("rawtypes")
	public Hospital getHospitalDetailFromID(String hospitalID) {
			Transaction tx = null;
		try {
			tx = session.beginTransaction();
			Query query = session.createQuery("select h from Hospital as h where h.hospital_ID = :hid");
			query.setParameter("hid", hospitalID);
			List hospitalList = query.list();

			if (hospitalList.size() == 0)
				return null;

			Hospital hospital = (Hospital) hospitalList.get(0);
			tx.commit();
			
			return hospital;
		} catch (RuntimeException ex) {
			if (tx != null && tx.isActive()) {
				try {
					tx.rollback();
				} catch (HibernateException he) {
					System.err.println("Error rolling back transaction");
				}
				throw ex;
			}
			return null;
		}
	}
	
	public List<Hospital> getAllHospitals(){
		Transaction tx = null;
		try{
			tx = session.beginTransaction();
			Query query =  session.createQuery("select h from Hospital as h");
			List<Hospital> hospitalList =CastList.castList(Hospital.class, query.list()); 
			tx.commit();
			return hospitalList;
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
	
	
	public Hospital getHospitalDetailsByID(String hospitalId){
		Transaction tx = null;
		try{
			tx = session.beginTransaction();
			Query query =  session.createQuery("select h from Hospital as h where h.hospital_ID=:hID");
			query.setParameter("hID", hospitalId);
			List<Hospital> hospitalList =CastList.castList(Hospital.class, query.list()); 
			tx.commit();
			return hospitalList.get(0);
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
	
	
	
	public boolean insertHospital(Hospital h){
		Transaction tx = null;
		try{	
			tx = session.beginTransaction();
			session.save(h);
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
	
	
	public boolean deleteHospital(Hospital h){
		Transaction tx=null;
		try {
			tx=session.beginTransaction();
			session.delete(h);
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
	
	
	public boolean updateHospitalDetails(Hospital h,String oldID){
		Transaction tx=null;
		try{
			tx=session.beginTransaction();
			Hospital hospitalObj=(Hospital) session.get(Hospital.class,oldID);
			hospitalObj.setHospital_ID(h.getHospital_ID());			
			hospitalObj.setHospital_IP(h.getHospital_IP());
			hospitalObj.setHospital_Port(h.getHospital_Port());
			hospitalObj.setHospital_Name(h.getHospital_Name());
			hospitalObj.setHospital_District(h.getHospital_District());
			hospitalObj.setHospital_Province(h.getHospital_Province());
			session.update(hospitalObj);
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
}
