package core.classes;

import java.util.HashSet;
import java.util.Set;

public class Patient  {

	private int patientID;
	private String patientTitle;
	private String patientFullName;
	private String patientPersonalUsedName;
	private String patientNIC;
	private String patientHIN;
	private String patientPassport;
	private String patientPhoto;
	private String patientDateOfBirth;
	private String patientTelephone;
	private String patientGender;
	private String patientCivilStatus;
	private String patientPreferredLanguage;
	private String patientCitizenship;
	private String patientContactPName;
	private String patientContactPNo;
	private String patientAddress;
	private String patientRemarks;
	private int patientActive;
	private Hospital HospitalID;
	private Set<Allergy> allergies = new HashSet<Allergy>();

	public Patient() {
	}
	
	public int getPatientID() {
		return patientID;
	}

	public void setPatientID(int patientID) {
		this.patientID = patientID;
	}
	public String getPatientTitle() {
		return patientTitle;
	}
	public void setPatientTitle(String patientTitle) {
		this.patientTitle = patientTitle;
	}
	public String getPatientFullName() {
		return patientFullName;
	}
	public void setPatientFullName(String patientFullName) {
		this.patientFullName = patientFullName;
	}
	public String getPatientPersonalUsedName() {
		return patientPersonalUsedName;
	}
	public void setPatientPersonalUsedName(String patientPersonalUsedName) {
		this.patientPersonalUsedName = patientPersonalUsedName;
	}
	public String getPatientNIC() {
		return patientNIC;
	}
	public void setPatientNIC(String patientNIC) {
		this.patientNIC = patientNIC;
	}
	public String getPatientHIN() {
		return patientHIN;
	}
	public void setPatientHIN(String patientHIN) {
		this.patientHIN = patientHIN;
	}
	public String getPatientPassport() {
		return patientPassport;
	}
	public void setPatientPassport(String patientPassport) {
		this.patientPassport = patientPassport;
	}
	public String getPatientPhoto() {
		return patientPhoto;
	}
	public void setPatientPhoto(String patientPhoto) {
		this.patientPhoto = patientPhoto;
	}
	public String getPatientDateOfBirth() {
		return patientDateOfBirth;
	}
	public void setPatientDateOfBirth(String patientDateOfBirth) {
		this.patientDateOfBirth = patientDateOfBirth;
	}
	public String getPatientTelephone() {
		return patientTelephone;
	}
	public void setPatientTelephone(String patientTelephone) {
		this.patientTelephone = patientTelephone;
	}
	public String getPatientGender() {
		return patientGender;
	}
	public void setPatientGender(String patientGender) {
		this.patientGender = patientGender;
	}
	public String getPatientCivilStatus() {
		return patientCivilStatus;
	}
	public void setPatientCivilStatus(String patientCivilStatus) {
		this.patientCivilStatus = patientCivilStatus;
	}
	public String getPatientPreferredLanguage() {
		return patientPreferredLanguage;
	}
	public void setPatientPreferredLanguage(String patientPreferredLanguage) {
		this.patientPreferredLanguage = patientPreferredLanguage;
	}
	public String getPatientCitizenship() {
		return patientCitizenship;
	}
	public void setPatientCitizenship(String patientCitizenship) {
		this.patientCitizenship = patientCitizenship;
	}
	public String getPatientContactPName() {
		return patientContactPName;
	}
	public void setPatientContactPName(String patientContactPName) {
		this.patientContactPName = patientContactPName;
	}
	public String getPatientContactPNo() {
		return patientContactPNo;
	}
	public void setPatientContactPNo(String patientContactPNo) {
		this.patientContactPNo = patientContactPNo;
	}
	public String getPatientAddress() {
		return patientAddress;
	}
	public void setPatientAddress(String patientAddress) {
		this.patientAddress = patientAddress;
	}
	public String getPatientRemarks() {
		return patientRemarks;
	}
	public void setPatientRemarks(String patientRemarks) {
		this.patientRemarks = patientRemarks;
	}
	public int getPatientActive() {
		return patientActive;
	}
	public void setPatientActive(int patientActive) {
		this.patientActive = patientActive;
	}

	public Hospital getHospitalID() {
		return HospitalID;
	}

	public void setHospitalID(Hospital hospitalID) {
		HospitalID = hospitalID;
	}

	public Set<Allergy> getAllergies() {
		return allergies;
	}
	public void setAllergies(Set<Allergy> allergies) {
		this.allergies = allergies;
	}
}