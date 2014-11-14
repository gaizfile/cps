package core.classes;


public class Allergy {

	private String allergyName;
	private String allergyStatus;
	private String allergyRemarks;
	private int allergyActive;
	private int allergyID;
	private Patient patient;
	
	public Patient getPatient() {
		return patient;
	}
	public void setPatient(Patient patient) {
		this.patient = patient;
	}
	public String getAllergyName() {
		return allergyName;
	}
	public void setAllergyName(String allergyName) {
		this.allergyName = allergyName;
	}
	public String getAllergyStatus() {
		return allergyStatus;
	}
	public void setAllergyStatus(String allergyStatus) {
		this.allergyStatus = allergyStatus;
	}
	public String getAllergyRemarks() {
		return allergyRemarks;
	}
	public void setAllergyRemarks(String allergyRemarks) {
		this.allergyRemarks = allergyRemarks;
	}
	public int getAllergyActive() {
		return allergyActive;
	}
	public void setAllergyActive(int allergyActive) {
		this.allergyActive = allergyActive;
	}
	public int getAllergyID() {
		return allergyID;
	}
	public void setAllergyID(int allergyID) {
		this.allergyID = allergyID;
	}
}
