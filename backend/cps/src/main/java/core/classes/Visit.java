package core.classes;

import java.util.Date;

public class Visit{

	private int visitID;
	private Date visitDate;
	private String visitComplaint;
	//private User visitDoctor;
	private String visitRemarks; 
	private String visitType;
	private int visitActive;
	private Patient patient;
	
	public int getVisitID() {
		return visitID;
	}
	public void setVisitID(int visitID) {
		this.visitID = visitID;
	}
	public Date getVisitDate() {
		return visitDate;
	}
	public void setVisitDate(Date visitDate) {
		this.visitDate = visitDate;
	}
	public String getVisitComplaint() {
		return visitComplaint;
	}
	public void setVisitComplaint(String visitComplaint) {
		this.visitComplaint = visitComplaint;
	}
	/*public User getVisitDoctor() {
		return visitDoctor;
	}
	public void setVisitDoctor(User visitDoctor) {
		this.visitDoctor = visitDoctor;
	}*/
	public String getVisitRemarks() {
		return visitRemarks;
	}
	public void setVisitRemarks(String visitRemarks) {
		this.visitRemarks = visitRemarks;
	}
	
	public String getVisitType() {
		return visitType;
	}
	public void setVisitType(String visitType) {
		this.visitType = visitType;
	}
	public int getVisitActive() {
		return visitActive;
	}
	public void setVisitActive(int visitActive) {
		this.visitActive = visitActive;
	}
	public Patient getPatient() {
		return patient;
	}
	public void setPatient(Patient patient) {
		this.patient = patient;
	}
}