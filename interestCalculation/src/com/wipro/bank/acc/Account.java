package com.wipro.bank.acc;

public abstract class Account {

    private float rateOfInterest;

    public void setInterest(int age, String gender) {

        if (gender.equalsIgnoreCase("male") && age < 60) {
            rateOfInterest = 9.8f;
        } 
        else if (gender.equalsIgnoreCase("male") && age >= 60) {
            rateOfInterest = 10.5f;
        } 
        else if (gender.equalsIgnoreCase("female") && age < 60) {
            rateOfInterest = 10.2f;
        } 
        else if (gender.equalsIgnoreCase("female") && age >= 60) {
            rateOfInterest = 10.8f;
        }
    }

    public float getRateOfInterest() {
        return rateOfInterest;
    }

    public float calculateMaturityAmount(float totalPrincipalDeposited, float interest) {
        return totalPrincipalDeposited + interest;
    }

    public abstract float calculateInterest();

    public abstract float calculateAmountDeposited();
}