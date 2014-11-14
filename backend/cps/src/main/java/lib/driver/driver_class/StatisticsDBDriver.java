package lib.driver.driver_class;

import java.util.List;
import java.util.Set;

import lib.SessionFactoryUtil;
import lib.classes.CasttingMethods.CastList;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import core.classes.Hospital;
import core.classes.StatStaff;
import core.classes.StatWard;
import core.classes.User;


public class StatisticsDBDriver {
	Session session = SessionFactoryUtil.getSessionFactory().openSession();
	
	
	
	public List<StatStaff> getAllMainEmployeeDetails(){
		Transaction tx = null;
		try{
			tx = session.beginTransaction();
			Query query =  session.createQuery("select s from StatStaff as s");
			List<StatStaff> statStaffList =CastList.castList(StatStaff.class, query.list()); 
			tx.commit();
			return statStaffList;
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
	
	
	
	
	
	
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List getHospitals() {
			Transaction tx = null;
		try {
			tx = session.beginTransaction();
			Query query = session.createQuery("select h from Hospital as h");
			List<Hospital> hospitalList = query.list();

			if (hospitalList.size() == 0)
				return null;
			tx.commit();
			
			return hospitalList;
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
	
	
	
	@SuppressWarnings("unchecked")
	public boolean getStatStaffFromID(Hospital hid,int yr) {
			Transaction tx = null;
		try {
			tx = session.beginTransaction();
			Query query = session.createQuery("select s from StatStaff as s where s.hospital=:hid AND s.year=:y");
			query.setParameter("hid",hid);
			query.setParameter("y",yr);
			List<StatStaff> staffList = query.list();
			tx.commit();
			
			if (staffList.size() == 0)
				return false;
			else
				return true;
		} catch (RuntimeException ex) {
			if (tx != null && tx.isActive()) {
				try {
					tx.rollback();
				} catch (HibernateException he) {
					System.err.println("Error rolling back transaction");
				}
				throw ex;
			}
			return false;
		}
	}
	

	@SuppressWarnings("unchecked")
	public boolean getStatWardFromID(Hospital hid,int yr) {
			Transaction tx = null;
		try {
			tx = session.beginTransaction();
			Query query = session.createQuery("select w from StatWard as w where w.hospital=:hid AND w.ward_year=:y");
			query.setParameter("hid",hid);
			query.setParameter("y",yr);
			List<StatWard> wardList = query.list();
			tx.commit();
			
			if (wardList.size() == 0)
				return false;
			else
				return true;
		} catch (RuntimeException ex) {
			if (tx != null && tx.isActive()) {
				try {
					tx.rollback();
				} catch (HibernateException he) {
					System.err.println("Error rolling back transaction");
				}
				throw ex;
			}
			return false;
		}
	}
	
	
	@SuppressWarnings("unchecked")
	public List<StatStaff> getDistributionOfSpecialists(Hospital hid) {
			Transaction tx = null;
		try {
			tx = session.beginTransaction();
			Query query = session.createQuery("select s from StatStaff as s where s.hospital=:hid order by year asc");
			query.setParameter("hid",hid);
			List<StatStaff> staffList = query.list();

			tx.commit();
			return staffList;
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
	
	
	
	@SuppressWarnings("unchecked")
	public List<StatWard> getWardBeds(Hospital hid) {
			Transaction tx = null;
		try {
			tx = session.beginTransaction();
			Query query = session.createQuery("select w from StatWard as w where w.hospital=:hid order by ward_year asc");
			query.setParameter("hid",hid);
			List<StatWard> wardList = query.list();

			tx.commit();
			return wardList;
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
	
	
	
	public boolean insertStaff(StatStaff staffObj) {
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			session.save(staffObj);
			tx.commit();
			return true;
		} catch (RuntimeException ex) {
			if (tx != null && tx.isActive()) {
				try {
					tx.rollback();
				} catch (HibernateException he) {
					System.err.println("Error rolling back transaction");
				}
				throw ex;
			}
			return false;
		}
	}
	
	
	
	public boolean insertWard(StatWard ward) {
		Transaction tx = null;
		try {
				tx = session.beginTransaction();
				session.save(ward);
				tx.commit();
			return true;
		} catch (RuntimeException ex) {
			if (tx != null && tx.isActive()) {
				try {
					tx.rollback();
				} catch (HibernateException he) {
					System.err.println("Error rolling back transaction");
				}
				throw ex;
			}
			return false;
		}
	}
	
	
	
	@SuppressWarnings("rawtypes")
	public boolean updateStaff(Hospital hid,int yr,StatStaff newObj) {
		Transaction tx = null;
		try {
			tx = session.beginTransaction();

			Query query = session.createQuery("select s from StatStaff as s where s.hospital=:hid AND s.year=:y");
			query.setParameter("hid",hid);
			query.setParameter("y",yr);
			List StaffList = query.list();

			if (StaffList.size() == 0)
				return false;

			StatStaff staffObj = (StatStaff) StaffList.get(0);
			
			staffObj.setmDoctor(newObj.getmDoctor());
			staffObj.setmNurse(newObj.getmNurse());
			staffObj.setmMLT(newObj.getmMLT());
			staffObj.setmAssistant_Pharmasist(newObj.getmAssistant_Pharmasist());
			staffObj.setmCheif_Pharmasist(newObj.getmCheif_Pharmasist());
			
			staffObj.setfDoctor(newObj.getfDoctor());
			staffObj.setfNurse(newObj.getfNurse());
			staffObj.setfMLT(newObj.getfMLT());
			staffObj.setfAssistant_Pharmasist(newObj.getfAssistant_Pharmasist());
			staffObj.setfCheif_Pharmasist(newObj.getfCheif_Pharmasist());
			
			session.update(staffObj);
			tx.commit();
			return true;
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
			if (tx != null && tx.isActive()) {
				try {
					tx.rollback();
				} catch (HibernateException he) {
					System.err.println("Error rolling back transaction");
				}
				throw ex;
			}
			return false;
		}
	}
	
	
	
	
	
	@SuppressWarnings("rawtypes")
	public boolean updateWard(List<StatWard> wardList) {
		Transaction tx = null;
		
		try {
			for(StatWard w:wardList){
				
				tx = session.beginTransaction();
				
				Query query = session.createQuery("select w from StatWard as w where w.hospital=:hid AND w.ward_year=:y And w.ward_no=:no");
				query.setParameter("hid",w.getHospital());
				query.setParameter("y",w.getWard_year());
				query.setParameter("no",w.getWard_no());
				List WardList = query.list();
				tx.commit();
				
				if (WardList.size() == 0){
					System.out.println("No Ward Data Exists : So Insert");
					insertWard(w);
				}
				else{
					System.out.println("Ward Data Exists : So Update");
					Transaction tz = null;
					tz = session.beginTransaction();
					Query query2 = session.createQuery("select w from StatWard as w where w.hospital=:hid AND w.ward_year=:y And w.ward_no=:no");
					query2.setParameter("hid",w.getHospital());
					query2.setParameter("y",w.getWard_year());
					query2.setParameter("no",w.getWard_no());
					
					List WardList2 = query2.list();
					StatWard newWard= (StatWard) WardList2.get(0);
					newWard.setWard_category(w.getWard_category());
					newWard.setWard_gender(w.getWard_gender());
					newWard.setWard_beds(w.getWard_beds());
					
					session.update(newWard);
					tz.commit();
					
				}
			}
			
			return true;
			
			
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
			if (tx != null && tx.isActive()) {
				try {
					tx.rollback();
				} catch (HibernateException he) {
					System.err.println("Error rolling back transaction");
				}
				throw ex;
			}
			return false;
		}
	}
}
