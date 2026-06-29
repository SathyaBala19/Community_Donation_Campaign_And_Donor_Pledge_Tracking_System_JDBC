package com.donate.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import com.donate.bean.Campaign;
import com.donate.util.DBUtil;

public class CampaignDAO {
	public Campaign findCampaign(String campaignID) {
		Campaign campaign = null;
		try {
			Connection con = DBUtil.getConnection();
			String sql = "SELECT * FROM CAMPAIGN_TBL WHERER campaignID = ?";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1,  campaignID);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				campaign = new Campaign();
				campaign.setCampaignId(rs.getString("campaignID"));
				campaign.setCampaignName(rs.getString("campaignName"));
				campaign.setStartDate(rs.getDate("startDate"));
				campaign.setEndDate(rs.getDate("endDate"));
				campaign.setTargetAmount(rs.getBigDecimal("targetAmount"));
				campaign.setStatus(rs.getString("status"));
			}
		} catch (Exception e) {
			System.out.println("Error in findCampaign: " + e.getMessage());
		}
		return campaign;
	}
	
	public List<Campaign> viewAllCampaigns() {
		List<Campaign> list = new ArrayList<>();
		try {
			Connection con = DBUtil.getConnection();
			String sql = "SELECT * FROM CAMPAIGN_TBL";
			PreparedStatement ps = con.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Campaign campaign = new Campaign();
				campaign.setCampaignId(rs.getString("campaignID"));
				campaign.setCampaignName(rs.getString("campaignName"));
				campaign.setStartDate(rs.getDate("startDate"));
				campaign.setEndDate(rs.getDate("endDate"));
				campaign.setTargetAmount(rs.getBigDecimal("targetAmount"));
				campaign.setStatus(rs.getString("status"));
				list.add(campaign);
			} 
		} catch (Exception e) {
			System.out.println("Error in viewAllCampaigns: " + e.getMessage());
		}
		return list;
	}
	
	public boolean insertCampaign(Campaign campaign) {
		boolean result = false;
		try {
			Connection con = DBUtil.getConnection();
			String sql = "INSERT INTO CAMPAIGN_TBL VALUES(?,?,?,?,?,?)";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1,  campaign.getCampaignId());
			ps.setString(2,  campaign.getCampaignName());
			ps.setDate(3,  campaign.getStartDate());
			ps.setDate(4,  campaign.getEndDate());
			ps.setBigDecimal(5, campaign.getTargetAmount());
			ps.setString(6, campaign.getStatus());
			int rows =  ps.executeUpdate();
			result = rows > 0;
		} catch (Exception e) {
			System.out.println("Error in insertCampaign: " + e.getMessage());
		}
		return result;
	}
	
	public boolean updateCampaign(String campaignID, String status) {
		boolean result = false;
		try {
			Connection con = DBUtil.getConnection();
			String sql = "UPDATE CAMPAIGN_TBL SET status = ? WHERE campaignID = ?";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1,  status);
			ps.setString(2, campaignID);
			int rows = ps.executeUpdate();
			result = rows > 0;
		} catch (Exception e) {
			System.out.println("Error in updateCampaign: " + e.getMessage());
		}
		return result;
	}

}
