package lib.driver.driver_class;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import lib.SessionFactoryUtil;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import core.classes.Patient;
import core.classes.Visit;
import core.classes.Allergy;


/*
 * This class control basic CRUD operations of the patient. it uses the
 * hibernate session factory utility and support for CRUD operation of patient
 * class.
 */
public class PatientDBDriver {

	Session session = SessionFactoryUtil.getSessionFactory().openSession();
	HospitalDBDriver hospitalDBDriver = new HospitalDBDriver();
	
	public boolean insertPatient(Patient patient) {
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			session.save(patient);
			tx.commit();
			System.out.println("\nPatient Inserted In To CPS");
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
	public Patient getPatientDetails(String keyID) {
			Transaction tx = null;
		try {
			tx = session.beginTransaction();
			Query query = session.createQuery("select p from Patient as p where p.patientHIN = :key OR p.patientNIC = :key OR p.patientPassport = :key");
			query.setParameter("key", keyID);
			List patientList = query.list();

			if (patientList.size() == 0)
				return null;

			
			Patient patient = (Patient) patientList.get(0);
			tx.commit();
			System.out.println(patient.getPatientDateOfBirth().toString());
			return patient;
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
	
	

	@SuppressWarnings("rawtypes")
	public boolean updatePatient(Patient pat) {
		Transaction tx = null;
		try {
			tx = session.beginTransaction();

			Query query = session.createQuery("select p from Patient as p where p.patientHIN = :hin");
			query.setParameter("hin", pat.getPatientHIN());
			List patientList = query.list();

			if (patientList.size() == 0)
				return false;

			Patient patient = (Patient) patientList.get(0);
			
			patient.setPatientTitle(pat.getPatientTitle());
			patient.setPatientFullName(pat.getPatientFullName());
			patient.setPatientPersonalUsedName(pat.getPatientPersonalUsedName());
			patient.setPatientNIC(pat.getPatientNIC());
			patient.setPatientHIN(pat.getPatientHIN());
			patient.setPatientPhoto(null);
			patient.setPatientPassport(pat.getPatientPassport());
			patient.setPatientDateOfBirth(pat.getPatientDateOfBirth());
			patient.setPatientContactPName(pat.getPatientContactPName());
			patient.setPatientContactPNo(pat.getPatientContactPNo());
			patient.setPatientGender(pat.getPatientGender());
			patient.setPatientCivilStatus(pat.getPatientCivilStatus());
			patient.setPatientAddress(pat.getPatientAddress());
			patient.setPatientTelephone(pat.getPatientTelephone());
			patient.setPatientPreferredLanguage(pat.getPatientPreferredLanguage());
			patient.setPatientCitizenship(pat.getPatientCitizenship());
			patient.setPatientRemarks(pat.getPatientRemarks());
			patient.setPatientActive(pat.getPatientActive());

			session.update(patient);
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
	
	public boolean addAllergy(Allergy allergy,String pHin){
		Transaction tx=null;
		try {
			tx = session.beginTransaction();
			Patient patient = (Patient) session.get(Patient.class, pHin);		
			allergy.setPatient(patient);
			session.save(allergy);
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
	public boolean updateAllergy(Allergy alergy,String pHin){
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			Patient patient = (Patient) session.get(Patient.class, pHin);	
			Query query = session.createQuery("select a from Allergy as a where a.allergyName = :allergyName AND a.patient=:pObj");
			query.setParameter("allergyName",alergy.getAllergyName());
			query.setParameter("pObj",patient);
			List AllergyList = query.list();

			if (AllergyList.size() == 0)
				return false;

			Allergy allergy = (Allergy) AllergyList.get(0);
			allergy.setAllergyName(alergy.getAllergyName());
			allergy.setAllergyRemarks(alergy.getAllergyRemarks());
			allergy.setAllergyStatus(alergy.getAllergyStatus());
			allergy.setAllergyActive(alergy.getAllergyActive());
			session.update(allergy);
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
	
	
	/*public boolean insertVisit(Visit visit, int doctorid , int pID) {
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			Patient patient = (Patient) session.get(OutPatient.class, pID);
			User doctor = (User) session.get(User.class, doctorid);
			visit.setVisitDoctor(doctor);
			visit.setVisitCreateUser(doctor);
			visit.setVisitLastUpDateUser(doctor);
			visit.setPatient(patient); 
			session.save(visit);
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
	}*/
	
	public static <T> List<T> castList(Class<? extends T> clazz, Collection<?> c) {
		List<T> r = new ArrayList<T>(c.size());
		for (Object o : c)
			r.add(clazz.cast(o));
		return r;
	}

}
