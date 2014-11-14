package core.classes;

public class UserLog {
	
	private int logID;
	private User logUserID;
	private String logSection;
	private String log;
	private String logDatetime;
	
	public int getLogID() {
		return logID;
	}
	public void setLogID(int logID) {
		this.logID = logID;
	}
	public User getLogUserID() {
		return logUserID;
	}
	public void setLogUserID(User logUserID) {
		this.logUserID = logUserID;
	}
	public String getLogSection() {
		return logSection;
	}
	public void setLogSection(String logSection) {
		this.logSection = logSection;
	}
	public String getLog() {
		return log;
	}
	public void setLog(String log) {
		this.log = log;
	}
	public String getLogDatetime() {
		return logDatetime;
	}
	public void setLogDatetime(String logDatetime) {
		this.logDatetime = logDatetime;
	}
}
