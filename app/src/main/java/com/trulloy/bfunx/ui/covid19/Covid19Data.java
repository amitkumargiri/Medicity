package com.trulloy.bfunx.ui.covid19;

public class Covid19Data {
    private int confirmed = -1;
    private int recovered = -1;
    private int deceased = -1;

    public Covid19Data(int confirmed, int recovered, int deceased) {
        this.confirmed = confirmed;
        this.recovered = recovered;
        this.deceased = deceased;
    }

    public int getConfirmed() {
        return confirmed;
    }

    public void setConfirmed(int confirmed) {
        this.confirmed = confirmed;
    }

    public int getRecovered() {
        return recovered;
    }

    public void setRecovered(int recovered) {
        this.recovered = recovered;
    }

    public int getDeceased() {
        return deceased;
    }

    public void setDeceased(int deceased) {
        this.deceased = deceased;
    }
}
