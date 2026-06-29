package com.donate.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import com.donate.bean.Pledge;
import com.donate.util.DBUtil;

public class PledgeDAO {

    public int generatePledgeID() {
        int pledgeID = 1;
        try {
            Connection con = DBUtil.getConnection();
            String sql = "SELECT MAX(pledgeID) FROM PLEDGE_TBL";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            if (rs.next() && rs.getObject(1) != null) {
                pledgeID = rs.getInt(1) + 1;
            }
        } catch (Exception e) {
            System.out.println("Error in generatePledgeID: " + e.getMessage());
        }
        return pledgeID;
    }

    public boolean insertPledge(Pledge pledge) {
        boolean result = false;
        try {
            Connection con = DBUtil.getConnection();
            String sql = "INSERT INTO PLEDGE_TBL VALUES(?,?,?,?,?,?,?,?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, pledge.getPledgeId());
            ps.setString(2, pledge.getDonorId());
            ps.setString(3, pledge.getCampaignId());
            ps.setDate(4, (java.sql.Date) pledge.getPledgeDate());
            ps.setBigDecimal(5, pledge.getPledgeAmount());
            ps.setBigDecimal(6, pledge.getAmountPaid());
            ps.setString(7, pledge.getPaymentStatus());
            ps.setString(8, pledge.getWriteoffFlag());
            int rows = ps.executeUpdate();
            result = rows > 0;
        } catch (Exception e) {
            System.out.println("Error in insertPledge: " + e.getMessage());
        }
        return result;
    }

    public boolean updatePledgePayment(int pledgeID, BigDecimal newAmountPaid, String newPaymentStatus) {
        boolean result = false;
        try {
            Connection con = DBUtil.getConnection();
            String sql = "UPDATE PLEDGE_TBL SET amountPaid=?, paymentStatus=? WHERE pledgeID=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setBigDecimal(1, newAmountPaid);
            ps.setString(2, newPaymentStatus);
            ps.setInt(3, pledgeID);
            int rows = ps.executeUpdate();
            result = rows > 0;
        } catch (Exception e) {
            System.out.println("Error in updatePledgePayment: " + e.getMessage());
        }
        return result;
    }

    public Pledge findPledge(int pledgeID) {
        Pledge pledge = null;
        try {
            Connection con = DBUtil.getConnection();
            String sql = "SELECT * FROM PLEDGE_TBL WHERE pledgeID=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, pledgeID);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                pledge = buildPledge(rs);
            }
        } catch (Exception e) {
            System.out.println("Error in findPledge: " + e.getMessage());
        }
        return pledge;
    }

    public List<Pledge> findPledgesByDonor(String donorID) {
        List<Pledge> list = new ArrayList<>();
        try {
            Connection con = DBUtil.getConnection();
            String sql = "SELECT * FROM PLEDGE_TBL WHERE donorID=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, donorID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(buildPledge(rs));
            }
        } catch (Exception e) {
            System.out.println("Error in findPledgesByDonor: " + e.getMessage());
        }
        return list;
    }

    public List<Pledge> findPledgesByCampaign(String campaignID) {
        List<Pledge> list = new ArrayList<>();
        try {
            Connection con = DBUtil.getConnection();
            String sql = "SELECT * FROM PLEDGE_TBL WHERE campaignID=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, campaignID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(buildPledge(rs));
            }
        } catch (Exception e) {
            System.out.println("Error in findPledgesByCampaign: " + e.getMessage());
        }
        return list;
    }

    public List<Pledge> findActivePledgesForDonor(String donorID) {
        List<Pledge> list = new ArrayList<>();
        try {
            Connection con = DBUtil.getConnection();
            String sql = "SELECT * FROM PLEDGE_TBL WHERE donorID=? " +
                         "AND paymentStatus IN ('NOT_PAID','PARTIALLY_PAID') " +
                         "AND writeoffFlag='NO'";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, donorID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(buildPledge(rs));
            }
        } catch (Exception e) {
            System.out.println("Error in findActivePledgesForDonor: " + e.getMessage());
        }
        return list;
    }

    public List<Pledge> findActivePledgesForCampaign(String campaignID) {
        List<Pledge> list = new ArrayList<>();
        try {
            Connection con = DBUtil.getConnection();
            String sql = "SELECT * FROM PLEDGE_TBL WHERE campaignID=? " +
                         "AND paymentStatus IN ('NOT_PAID','PARTIALLY_PAID') " +
                         "AND writeoffFlag='NO'";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, campaignID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(buildPledge(rs));
            }
        } catch (Exception e) {
            System.out.println("Error in findActivePledgesForCampaign: " + e.getMessage());
        }
        return list;
    }

    // Helper method to avoid repeating ResultSet mapping
    private Pledge buildPledge(ResultSet rs) throws Exception {
        Pledge pledge = new Pledge();
        pledge.setPledgeId(rs.getInt("pledgeID"));
        pledge.setDonorId(rs.getString("donorID"));
        pledge.setCampaignId(rs.getString("campaignID"));
        pledge.setPledgeDate(rs.getDate("pledgeDate"));
        pledge.setPledgeAmount(rs.getBigDecimal("pledgeAmount"));
        pledge.setAmountPaid(rs.getBigDecimal("amountPaid"));
        pledge.setPaymentStatus(rs.getString("paymentStatus"));
        pledge.setWriteoffFlag(rs.getString("writeoffFlag"));
        return pledge;
    }
}