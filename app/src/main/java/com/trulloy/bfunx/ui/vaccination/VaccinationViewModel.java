package com.trulloy.bfunx.ui.vaccination;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

public class VaccinationViewModel extends ViewModel {

    protected MutableLiveData<List<String>> childList;

    public VaccinationViewModel() {
        childList = new MutableLiveData<>();
    }

    public void setChildList(List<String> childList) {
        this.childList.setValue(childList);
    }
}
