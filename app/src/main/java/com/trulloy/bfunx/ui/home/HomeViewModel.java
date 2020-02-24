package com.trulloy.bfunx.ui.home;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class HomeViewModel extends ViewModel {

    protected MutableLiveData<String> lastDateVisited, reason;
    protected MutableLiveData<Double> totalAmount;
    protected MutableLiveData<Integer> docVisitCount, selfHealCount;

    public HomeViewModel() {
        docVisitCount = new MutableLiveData<>();
        lastDateVisited = new MutableLiveData<>();
        reason = new MutableLiveData<>();
        selfHealCount = new MutableLiveData<>();
        totalAmount = new MutableLiveData<>();
    }

    public void setData(Integer docVisitCount, String lastVisitedDate, String reason, Integer selfHealCount, Double totalAmount) {
        this.docVisitCount.setValue(docVisitCount);
        this.lastDateVisited.setValue(lastVisitedDate);
        this.reason.setValue(reason);
        this.selfHealCount.setValue(selfHealCount);
        this.totalAmount.setValue(totalAmount);
    }
}
