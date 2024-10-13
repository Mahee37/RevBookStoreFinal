package com.revbookstoreclientapp.dto;



public class Complaint {
	
    private long complaintId;
	private String userName;
	private String complaintText;
	
	
	private User user;


	public long getComplaintId() {
		return complaintId;
	}


	public void setComplaintId(long complaintId) {
		this.complaintId = complaintId;
	}


	public String getUserName() {
		return userName;
	}


	public void setUserName(String userName) {
		this.userName = userName;
	}


	public String getComplaintText() {
		return complaintText;
	}


	public void setComplaintText(String complaintText) {
		this.complaintText = complaintText;
	}


	public User getUser() {
		return user;
	}


	public void setUser(User user) {
		this.user = user;
	}
	
}
