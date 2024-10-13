package com.buyerservice.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Complaint {
	
	 @Id
	 @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long complaintId;
	private String userName;
	private String complaintText;
	
	@ManyToOne(cascade=CascadeType.MERGE)
	@JoinColumn(name = "sellerId" , referencedColumnName = "userId")
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
