package com.donate.service;

import java.math.BigDecimal;
import java.sql.*;
import java.util.List;

import com.donate.bean.*;
import com.donate.dao.*;
import com.donate.util.*;

public class DonateService {
    private DonorDAO donorDAO = new DonorDAO();
    private CampaignDAO campaignDAO = new CampaignDAO();
    private PledgeDAO pledgeDAO = new PledgeDAO();

    public Donor viewDonorDetails(String donorID) {
        if (donorID == null || donorID.trim().isEmpty()) {
            return null;
        }
        return donorDAO.findDonor(donorID);
    }

    public List<Donor> viewAllDonors() {
        return donorDAO.viewAllDonors();
    }

    public boolean registerNewDonor(Donor donor) throws ValidationException {
        if (donor.getDonorId() == null || donor.getDonorId().trim().isEmpty()) {
            throw new ValidationException("Donor ID cannot be empty.");
        }
        if (donor.getFullName() == null || donor.getFullName().trim().isEmpty()) {
            throw new ValidationException("Full Name cannot be empty");
        }
        if (donor.getMobile() == null || donor.getMobile().trim().isEmpty()) {
            throw new ValidationException("Mobile cannot be empty");
        }
        if (donor.getEmail() != null && !donor.getEmail().isEmpty()) {
            if (!donor.getEmail().contains("@")) {
                throw new ValidationException("Email format is invalid!");
            }
        }
        Donor existing = donorDAO.findDonor(donor.getDonorId());
        if (existing != null) {
            throw new ValidationException("Donor ID already exists!");
        }
        donor.setStatus("ACTIVE");
        return donorDAO.insertDonor(donor);
    }

    public boolean removeDonor(String donorID) 
            throws ValidationException, ActivePledgeExistException {
        if (donorID == null || donorID.trim().isEmpty()) {
            throw new ValidationException("Donor ID cannot be empty");
        }
        List<Pledge> activePledges = pledgeDAO.findActivePledgesForDonor(donorID);
        if (activePledges != null && !activePledges.isEmpty()) {
            throw new ActivePledgeExistException("Cannot remove donor! Active pledges exist!");
        }
        return donorDAO.deleteDonor(donorID);
    }

    public List<Campaign> viewAllCampaigns() {
        return campaignDAO.viewAllCampaigns();
    }

    public Campaign viewCampaignDetails(String campaignID) {
        return campaignDAO.findCampaign(campaignID);
    }

    public boolean createCampaign(Campaign campaign) throws ValidationException {
        if (campaign.getCampaignId() == null || campaign.getCampaignId().trim().isEmpty()) {
            throw new ValidationException("Campaign ID cannot be empty");
        }
        if (campaign.getCampaignName() == null || campaign.getCampaignName().trim().isEmpty()) {
            throw new ValidationException("Campaign Name cannot be empty");
        }
        if (campaign.getStartDate() == null || campaign.getEndDate() == null) {
            throw new ValidationException("Start and End Dates cannot be null");
        }
        if (campaign.getStartDate().after(campaign.getEndDate())) {
            throw new ValidationException("Start date cannot be after End date!");
        }
        if (campaign.getTargetAmount() == null || 
            campaign.getTargetAmount().compareTo(BigDecimal.ZERO) < 0) {
            throw new ValidationException("Target amount cannot be negative");
        }
        Campaign existing = campaignDAO.findCampaign(campaign.getCampaignId());
        if (existing != null) {
            throw new ValidationException("Campaign ID already exists!");
        }
        return campaignDAO.insertCampaign(campaign);
    }

    public boolean closeCampaign(String campaignID) 
            throws ValidationException, ActivePledgeExistException {
        if (campaignID == null || campaignID.trim().isEmpty()) {
            throw new ValidationException("Campaign ID cannot be empty");
        }
        Campaign campaign = campaignDAO.findCampaign(campaignID);
        if (campaign == null || campaign.getStatus().equals("CLOSED")) {
            return false;
        }
        List<Pledge> activePledges = pledgeDAO.findActivePledgesForCampaign(campaignID);
        if (activePledges != null && !activePledges.isEmpty()) {
            throw new ActivePledgeExistException("Cannot close campaign! Active pledges exist");
        }
        return campaignDAO.updateCampaign(campaignID, "CLOSED");
    }

    public List<Pledge> listPledgesByDonor(String donorID) {
        return pledgeDAO.findPledgesByDonor(donorID);
    }

    public List<Pledge> listPledgesByCampaign(String campaignID) {
        return pledgeDAO.findPledgesByCampaign(campaignID);
    }

    public boolean recordPledge(String donorID, String campaignID,
            Date pledgeDate, BigDecimal pledgeAmount)
            throws ValidationException, CampaignClosedException {
        if (donorID == null || donorID.trim().isEmpty() ||
            campaignID == null || campaignID.trim().isEmpty()) {
            throw new ValidationException("Donor ID and Campaign ID cannot be empty!");
        }
        if (pledgeDate == null) {
            throw new ValidationException("Pledge date cannot be null!");
        }
        if (pledgeAmount == null || pledgeAmount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new ValidationException("Pledge amount must be positive!");
        }
        Donor donor = donorDAO.findDonor(donorID);
        if (donor == null || donor.getStatus().equals("INACTIVE")) {
            return false;
        }
        Campaign campaign = campaignDAO.findCampaign(campaignID);
        if (campaign == null) {
            return false;
        }
        if (!campaign.getStatus().equals("ACTIVE")) {
            throw new CampaignClosedException("Campaign is not active!");
        }
        Connection con = null;
        try {
            con = DBUtil.getConnection();
            con.setAutoCommit(false);
            int pledgeID = pledgeDAO.generatePledgeID();
            Pledge pledge = new Pledge();
            pledge.setPledgeId(pledgeID);
            pledge.setDonorId(donorID);
            pledge.setCampaignId(campaignID);
            pledge.setPledgeDate(pledgeDate);
            pledge.setPledgeAmount(pledgeAmount);
            pledge.setAmountPaid(BigDecimal.ZERO);
            pledge.setPaymentStatus("NOT_PAID");
            pledge.setWriteoffFlag("NO");
            boolean inserted = pledgeDAO.insertPledge(pledge);
            if (inserted) {
                con.commit();
                return true;
            } else {
                con.rollback();
                return false;
            }
        } catch (Exception e) {
            try { if (con != null) con.rollback(); } catch (Exception ex) {}
            System.out.println("Error in recordPledge: " + e.getMessage());
            return false;
        } finally {
            try { if (con != null) con.setAutoCommit(true); } catch (Exception ex) {}
        }
    }

    public boolean recordPayment(int pledgeID, BigDecimal paymentAmount)
            throws ValidationException {
        if (pledgeID <= 0) {
            throw new ValidationException("Pledge ID must be positive!");
        }
        if (paymentAmount == null || paymentAmount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new ValidationException("Payment amount must be positive!");
        }
        Pledge pledge = pledgeDAO.findPledge(pledgeID);
        if (pledge == null) {
            return false;
        }
        BigDecimal newAmountPaid = pledge.getAmountPaid().add(paymentAmount);
        if (newAmountPaid.compareTo(pledge.getPledgeAmount()) > 0) {
            throw new ValidationException("Payment exceeds pledge amount!");
        }
        String newPaymentStatus;
        if (newAmountPaid.compareTo(pledge.getPledgeAmount()) == 0) {
            newPaymentStatus = "FULLY_PAID";
        } else if (newAmountPaid.compareTo(BigDecimal.ZERO) > 0) {
            newPaymentStatus = "PARTIALLY_PAID";
        } else {
            newPaymentStatus = "NOT_PAID";
        }
        Connection con = null;
        try {
            con = DBUtil.getConnection();
            con.setAutoCommit(false);
            boolean updated = pledgeDAO.updatePledgePayment(
                pledgeID, newAmountPaid, newPaymentStatus);
            if (updated) {
                con.commit();
                return true;
            } else {
                con.rollback();
                return false;
            }
        } catch (Exception e) {
            try { if (con != null) con.rollback(); } catch (Exception ex) {}
            System.out.println("Error in recordPayment: " + e.getMessage());
            return false;
        } finally {
            try { if (con != null) con.setAutoCommit(true); } catch (Exception ex) {}
        }
    }
}