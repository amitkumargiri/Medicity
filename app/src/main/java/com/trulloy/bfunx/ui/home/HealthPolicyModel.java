package com.trulloy.bfunx.ui.home;


public class HealthPolicyModel {
    private String number;
    private String policyName;
    private String policyAmount;
    private String personName;
    private String buyDate;
    private String coverage;

    public HealthPolicyModel(String number, String policyName, String policyAmount, String personName, String buyDate, String coverage) {
        this.number = number;
        this.policyName = policyName;
        this.policyAmount = policyAmount;
        this.personName = personName;
        this.buyDate = buyDate;
        this.coverage = coverage;
    }

    public String getNumber() {
        return number;
    }

    public String getPolicyName() {
        return policyName;
    }

    public String getPolicyAmount() {
        return policyAmount;
    }

    public String getPersonName() {
        return personName;
    }

    public String getBuyDate() {
        return buyDate;
    }

    public String getCoverage() {
        return coverage;
    }
}
