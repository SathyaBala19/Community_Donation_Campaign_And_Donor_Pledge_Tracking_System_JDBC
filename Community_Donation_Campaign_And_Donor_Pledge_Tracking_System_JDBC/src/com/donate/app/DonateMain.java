package com.donate.app;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;
import java.util.Scanner;

import com.donate.bean.Campaign;
import com.donate.bean.Donor;
import com.donate.bean.Pledge;
import com.donate.service.ActivePledgeExistException;
import com.donate.service.CampaignClosedException;
import com.donate.service.DonateService;
import com.donate.service.ValidationException;

public class DonateMain {

    private static DonateService donateService = new DonateService();
    private static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("Community Donation & Pledge Tracking");

        boolean running = true;
        while (running) {
            System.out.println("\nMAIN MENU");
            System.out.println("1. Register Donor");
            System.out.println("2. View All Donors");
            System.out.println("3. View Donor Details");
            System.out.println("4. Remove Donor");
            System.out.println("5. Create Campaign");
            System.out.println("6. View All Campaigns");
            System.out.println("7. View Campaign Details");
            System.out.println("8. Close Campaign");
            System.out.println("9. Record Pledge");
            System.out.println("10. Record Payment");
            System.out.println("11. View Pledges By Donor");
            System.out.println("12. View Pledges By Campaign");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");

            int choice = Integer.parseInt(sc.nextLine());

            switch (choice) {
                case 1: registerDonor(); break;
                case 2: viewAllDonors(); break;
                case 3: viewDonorDetails(); break;
                case 4: removeDonor(); break;
                case 5: createCampaign(); break;
                case 6: viewAllCampaigns(); break;
                case 7: viewCampaignDetails(); break;
                case 8: closeCampaign(); break;
                case 9: recordPledge(); break;
                case 10: recordPayment(); break;
                case 11: viewPledgesByDonor(); break;
                case 12: viewPledgesByCampaign(); break;
                case 0: running = false;
                        System.out.println("Goodbye!"); break;
                default: System.out.println("Invalid choice!");
            }
        }
        sc.close();
    }

    private static void registerDonor() {
        try {
            System.out.println("\nRegister New Donor");
            Donor d = new Donor();
            System.out.print("Enter Donor ID: ");
            d.setDonorId(sc.nextLine());
            System.out.print("Enter Full Name: ");
            d.setFullName(sc.nextLine());
            System.out.print("Enter Email: ");
            d.setEmail(sc.nextLine());
            System.out.print("Enter Mobile: ");
            d.setMobile(sc.nextLine());
            System.out.print("Enter City: ");
            d.setCity(sc.nextLine());
            boolean ok = donateService.registerNewDonor(d);
            System.out.println(ok ? "DONOR REGISTERED SUCCESSFULLY!" 
                                  : "DONOR REGISTRATION FAILED!");
        } catch (ValidationException e) {
            System.out.println("Validation Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("System Error: " + e.getMessage());
        }
    }

    private static void viewAllDonors() {
        System.out.println("\nAll Donors");
        List<Donor> list = donateService.viewAllDonor();
        if (list.isEmpty()) {
            System.out.println("No donors found!");
        } else {
            for (Donor d : list) {
                System.out.println("ID: " + d.getDonorId() +
                        " | Name: " + d.getFullName() +
                        " | Mobile: " + d.getMobile() +
                        " | City: " + d.getCity() +
                        " | Status: " + d.getStatus());
            }
        }
    }

    private static void viewDonorDetails() {
        try {
            System.out.print("\nEnter Donor ID: ");
            String id = sc.nextLine();
            Donor d = donateService.viewDonorDetails(id);
            if (d == null) {
                System.out.println("Donor not found!");
            } else {
                System.out.println("\nDonor Details");
                System.out.println("ID      : " + d.getDonorId());
                System.out.println("Name    : " + d.getFullName());
                System.out.println("Email   : " + d.getEmail());
                System.out.println("Mobile  : " + d.getMobile());
                System.out.println("City    : " + d.getCity());
                System.out.println("Status  : " + d.getStatus());
            }
        } catch (Exception e) {
            System.out.println("System Error: " + e.getMessage());
        }
    }

    private static void removeDonor() {
        try {
            System.out.print("\nEnter Donor ID to remove: ");
            String id = sc.nextLine();
            boolean ok = donateService.removeDonor(id);
            System.out.println(ok ? "DONOR REMOVED SUCCESSFULLY!" 
                                  : "DONOR NOT FOUND!");
        } catch (ValidationException e) {
            System.out.println("Validation Error: " + e.getMessage());
        } catch (ActivePledgeExistException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("System Error: " + e.getMessage());
        }
    }

    private static void createCampaign() {
        try {
            System.out.println("\nCreate New Campaign");
            Campaign c = new Campaign();
            System.out.print("Enter Campaign ID: ");
            c.setCampaignId(sc.nextLine());
            System.out.print("Enter Campaign Name: ");
            c.setCampaignName(sc.nextLine());
            System.out.print("Enter Start Date (YYYY-MM-DD): ");
            c.setStartDate(Date.valueOf(sc.nextLine()));
            System.out.print("Enter End Date (YYYY-MM-DD): ");
            c.setEndDate(Date.valueOf(sc.nextLine()));
            System.out.print("Enter Target Amount: ");
            c.setTargetAmount(new BigDecimal(sc.nextLine()));
            System.out.print("Enter Status (PLANNED/ACTIVE): ");
            c.setStatus(sc.nextLine());
            boolean ok = donateService.createCampaign(c);
            System.out.println(ok ? "CAMPAIGN CREATED SUCCESSFULLY!" 
                                  : "CAMPAIGN CREATION FAILED!");
        } catch (ValidationException e) {
            System.out.println("Validation Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("System Error: " + e.getMessage());
        }
    }

    private static void viewAllCampaigns() {
        System.out.println("\nAll Campaigns");
        List<Campaign> list = donateService.viewAllCampaigns();
        if (list.isEmpty()) {
            System.out.println("No campaigns found!");
        } else {
            for (Campaign c : list) {
                System.out.println("ID: " + c.getCampaignId() +
                        " | Name: " + c.getCampaignName() +
                        " | Start: " + c.getStartDate() +
                        " | End: " + c.getEndDate() +
                        " | Target: " + c.getTargetAmount() +
                        " | Status: " + c.getStatus());
            }
        }
    }

    private static void viewCampaignDetails() {
        try {
            System.out.print("\nEnter Campaign ID: ");
            String id = sc.nextLine();
            Campaign c = donateService.viewCampaignDetails(id);
            if (c == null) {
                System.out.println("Campaign not found!");
            } else {
                System.out.println("\nCampaign Details");
                System.out.println("ID        : " + c.getCampaignId());
                System.out.println("Name      : " + c.getCampaignName());
                System.out.println("Start Date: " + c.getStartDate());
                System.out.println("End Date  : " + c.getEndDate());
                System.out.println("Target    : " + c.getTargetAmount());
                System.out.println("Status    : " + c.getStatus());
            }
        } catch (Exception e) {
            System.out.println("System Error: " + e.getMessage());
        }
    }

    private static void closeCampaign() {
        try {
            System.out.print("\nEnter Campaign ID to close: ");
            String id = sc.nextLine();
            boolean ok = donateService.closeCampaign(id);
            System.out.println(ok ? "CAMPAIGN CLOSED SUCCESSFULLY!" 
                                  : "CAMPAIGN NOT FOUND OR ALREADY CLOSED!");
        } catch (ValidationException e) {
            System.out.println("Validation Error: " + e.getMessage());
        } catch (ActivePledgeExistException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("System Error: " + e.getMessage());
        }
    }

    private static void recordPledge() {
        try {
            System.out.println("\nRecord New Pledge");
            System.out.print("Enter Donor ID: ");
            String donorID = sc.nextLine();
            System.out.print("Enter Campaign ID: ");
            String campaignID = sc.nextLine();
            System.out.print("Enter Pledge Date (YYYY-MM-DD): ");
            Date pledgeDate = Date.valueOf(sc.nextLine());
            System.out.print("Enter Pledge Amount: ");
            BigDecimal pledgeAmount = new BigDecimal(sc.nextLine());
            boolean ok = donateService.recordPledge(
                donorID, campaignID, pledgeDate, pledgeAmount);
            System.out.println(ok ? "PLEDGE RECORDED SUCCESSFULLY!" 
                                  : "PLEDGE FAILED!");
        } catch (ValidationException e) {
            System.out.println("Validation Error: " + e.getMessage());
        } catch (CampaignClosedException e) {
            System.out.println("Campaign Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("System Error: " + e.getMessage());
        }
    }

    private static void recordPayment() {
        try {
            System.out.println("\nRecord Payment");
            System.out.print("Enter Pledge ID: ");
            int pledgeID = Integer.parseInt(sc.nextLine());
            System.out.print("Enter Payment Amount: ");
            BigDecimal amount = new BigDecimal(sc.nextLine());
            boolean ok = donateService.recordPayment(pledgeID, amount);
            System.out.println(ok ? "PAYMENT RECORDED SUCCESSFULLY!" 
                                  : "PAYMENT FAILED!");
        } catch (ValidationException e) {
            System.out.println("Validation Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("System Error: " + e.getMessage());
        }
    }

    private static void viewPledgesByDonor() {
        System.out.print("\nEnter Donor ID: ");
        String id = sc.nextLine();
        List<Pledge> list = donateService.listPledgesByDonor(id);
        if (list.isEmpty()) {
            System.out.println("No pledges found!");
        } else {
            for (Pledge p : list) {
                System.out.println("PledgeID: " + p.getPledgeId() +
                        " | Campaign: " + p.getCampaignId() +
                        " | Amount: " + p.getPledgeAmount() +
                        " | Paid: " + p.getAmountPaid() +
                        " | Status: " + p.getPaymentStatus());
            }
        }
    }

    private static void viewPledgesByCampaign() {
        System.out.print("\nEnter Campaign ID: ");
        String id = sc.nextLine();
        List<Pledge> list = donateService.listPledgesByCampaign(id);
        if (list.isEmpty()) {
            System.out.println("No pledges found!");
        } else {
            for (Pledge p : list) {
                System.out.println("PledgeID: " + p.getPledgeId() +
                        " | Donor: " + p.getDonorId() +
                        " | Amount: " + p.getPledgeAmount() +
                        " | Paid: " + p.getAmountPaid() +
                        " | Status: " + p.getPaymentStatus());
            }
        }
    }
}