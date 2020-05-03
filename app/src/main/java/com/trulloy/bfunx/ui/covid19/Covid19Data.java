package com.trulloy.bfunx.ui.covid19;

public class Covid19Data {

    private String replace;
    private int confirmed = -1;
    private int active = -1;
    private int recovered = -1;
    private int deceased = -1;

    public Covid19Data(String replace, int confirmed, int active, int recovered, int deceased) {
        this.replace = replace;
        this.active = active;
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

    public String getreplace() { return replace; }

    public void setreplace(String replace) { this.replace = replace; }

    @Override
    public String toString() {
        return "Covid19Data{" +
                "replace='" + replace + '\'' +
                ", confirmed=" + confirmed +
                ", active=" + active +
                ", recovered=" + recovered +
                ", deceased=" + deceased +
                '}';
    }
}
