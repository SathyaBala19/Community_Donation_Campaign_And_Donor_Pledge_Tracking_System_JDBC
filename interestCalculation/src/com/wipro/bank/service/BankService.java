package com.wipro.bank.service;

import com.wipro.bank.acc.RDAccount;

class BankValidationException extends Exception {
    public BankValidationException(String msg) {
        super(msg);
    }
}

public class BankService {

    public boolean validateData(float principal, int tenure, int age, String gender) {
        try {
            if (principal < 500) {
                throw new BankValidationException("Principal must be minimum 500");
            }

            if (tenure != 5 && tenure != 10) {
                throw new BankValidationException("Tenure must be either 5 or 10");
            }

            if (!gender.equalsIgnoreCase("male") && !gender.equalsIgnoreCase("female")) {
                throw new BankValidationException("Gender must be male or female");
            }

            if (age < 1 || age > 100) {
                throw new BankValidationException("Age must be between 1 and 100");
            }

            return true;

        } catch (BankValidationException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public void calculate(float principal, int tenure, int age, String gender) {

        if (validateData(principal, tenure, age, gender)) {

            RDAccount rda = new RDAccount(tenure, principal);
            rda.setInterest(age, gender);

            float interest = rda.calculateInterest();
            float totalPrincipalDeposited = rda.calculateAmountDeposited();
            float maturityAmount =
                    rda.calculateMaturityAmount(totalPrincipalDeposited, interest);

            System.out.println("Interest: " + interest);
            System.out.println("Maturity amount: " + maturityAmount);
        }
    }
}