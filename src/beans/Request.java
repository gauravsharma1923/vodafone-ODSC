package beans;

public class Request {
	private String racName;
	private String userName;
	private String password;
	private String sid;
	private String expiry;
	private String type;
	private String cursid;
	public String getCursid() {
		return cursid;
	}
	public void setCursid(String cursid) {
		this.cursid = cursid;
	}
	public String getRacName() {
		return racName;
	}
	public void setRacName(String racName) {
		this.racName = racName;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getSid() {
		return sid;
	}
	public void setSid(String sid) {
		this.sid = sid;
	}
	public String getExpiry() {
		return expiry;
	}
	public void setExpiry(String expiry) {
		this.expiry = expiry;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
}
