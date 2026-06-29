package com.donate.bean;

import java.sql.Date;

public class Donor {
	private String donorId;
	private String fullName;
	private String email;
	private String mobile;
	private String city;
	private String status;
	
	public String getDonorId() {
		return donorId;
	}
	
	public void setDonorId(String donorId) {
		this.donorId = donorId;
	}
	
	public String getFullName() {
		return fullName;
	}
	
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getMobile() {
		return mobile;
	}
	
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
	public String getCity() {
		return city;
	}
	
	public void setCity(String city) {
		this.city = city;
	}
	
	public String getStatus() {
		return status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}

}
