package com.donate.bean;

import java.math.BigDecimal;
import java.util.Date;

public class Pledge {
	private int pledgeId;
	private String donorId;
	private String campaignId;
	private Date pledgeDate;
	private BigDecimal pledgeAmount;
	private BigDecimal amountPaid;
	private String paymentStatus;
	private  String writeoffFlag;
	
	public int getPledgeId() {
		return pledgeId;
	}
	
	public void setPledgeId(int pledgeId) {
		this.pledgeId = pledgeId;
	}
	
	public String getDonorId() {
		return donorId;
	}
	
	public void setDonorId(String donorId) {
		this.donorId = donorId;
	}
	
	public String getCampaignId() {
		return campaignId;
	}
	
	public void setCampaignId(String campaignId) {
		this.campaignId = campaignId;
	}
	
	public Date getPledgeDate() {
		return pledgeDate;
	}
	
	public void setPledgeDate(Date pledgeDate) {
		this.pledgeDate = pledgeDate;
	}
	
	public BigDecimal getPledgeAmount() {
		return pledgeAmount;
	}
	
	public void setPledgeAmount(BigDecimal pledgeAmount) {
		this.pledgeAmount = pledgeAmount;
	}
	
	public BigDecimal getAmountPaid() {
		return amountPaid;
	}
	
	public void setAmountPaid(BigDecimal amountPaid) {
		this.amountPaid = amountPaid;
	}
	
	public String getPaymentStatus() {
		return paymentStatus;
	}
	
	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}
	
	public String getWriteoffFlag() {
		return writeoffFlag;
	}
	
	public void setWriteoffFlag(String writeoffFlag) {
		this.writeoffFlag = writeoffFlag;
	}

}
