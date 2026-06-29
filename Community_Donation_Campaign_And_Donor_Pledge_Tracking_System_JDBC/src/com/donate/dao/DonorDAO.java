package com.donate.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.donate.bean.Donor;
import com.donate.util.DBUtil;

public class DonorDAO {
	public Donor findDonor(String donorID) {
		Donor donor = null;
		try {
			Connection con = DBUtil.getConnection();
			String sql = "SELECT * FROM DONOR_TBL WHERE donorID=?";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, donorID);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				donor = new Donor();
				donor.setDonorId(rs.getString("donorID"));
				donor.setFullName(rs.getString("fullName"));
				donor.setEmail(rs.getString("email"));
				donor.setMobile(rs.getString("mobile"));
				donor.setCity(rs.getString("city"));
				donor.setStatus(rs.getString("status"));
			}
		} catch (Exception e) {
			System.out.println("Error in findDonor: " + e.getMessage());
		}
		return donor;
	}
	
	public List<Donor> viewAllDonors() {
		List<Donor> list = new ArrayList<>();
		try {
			Connection con = DBUtil.getConnection();
			String sql = "SELECT * FROM DONOR_TBL";
			PreparedStatement ps = con.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				Donor donor = new Donor();
				donor.setDonorId(rs.getString("donorID"));
				donor.setFullName(rs.getString("fullName"));
				donor.setEmail(rs.getString("email"));
				donor.setMobile(rs.getString("mobile"));
				donor.setCity(rs.getString("city"));
				donor.setStatus(rs.getString("status"));
				list.add(donor);
			}
		} catch(Exception e) {
			System.out.println("Error in viewAllDonors: " + e.getMessage());
		}
		return list; 
	}
	
	public boolean insertDonor(Donor donor) {
		boolean result = false;
		try {
			Connection con = DBUtil.getConnection();
			String sql = "INSERT INTO DONOR_TBL VALUES(?,?,?,?,?,?)";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1,  donor.getDonorId());
			ps.setString(2,  donor.getFullName());
			ps.setString(3, donor.getEmail());
			ps.setString(4,  donor.getMobile());
			ps.setString(5,  donor.getCity());
			ps.setString(6,  donor.getStatus());
			int rows = ps.executeUpdate();
			result = rows > 0;
		} catch(Exception e) {
			System.out.println("Error in insertDonor: " + e.getMessage());
		}
		return result;
	}
	
	public boolean updateDonorStatus(String donorID, String status) {
		boolean result = false;
		try {
			Connection con = DBUtil.getConnection();
			String sql = "UPDATE DONOR_TBL SET status=? WHERE donorId=?";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, status);
			ps.setString(2,  donorID);
			int rows = ps.executeUpdate();
			result = rows > 0;
		}  catch (Exception e) {
			System.out.println("Error in updateDonorStatus: " + e.getMessage());
		}
		return result;
	}
	
	public boolean deleteDonor(String donorID) {
		boolean result = false;
		try {
			Connection con = DBUtil.getConnection();
			String sql = "DELETE FROM DONOR_TBL WHERE donorID=?";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1,  donorID);
			int rows = ps.executeUpdate();
			result = rows > 0;
		} catch (Exception e) {
			System.out.println("Error in deleteDonor: " + e.getMessage());
		}
		return result;
	}

}
