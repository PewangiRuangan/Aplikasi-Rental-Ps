package com.example.rentalps.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.rentalps.models.TV;

import java.util.ArrayList;
import java.util.List;

public class TVViewModel extends ViewModel {
    private final MutableLiveData<List<TV>> tvListLiveData = new MutableLiveData<>();

    public MutableLiveData<List<TV>> getTVList() {
        if (tvListLiveData.getValue() == null) {
            List<TV> list = new ArrayList<>();
            for (int i = 1; i <= 6; i++) {
                list.add(new TV(i));
            }
            tvListLiveData.setValue(list);
        }
        return tvListLiveData;
    }

    public void updateTV(int position, TV updatedTV) {
        List<TV> list = tvListLiveData.getValue();
        if (list != null && position >= 0 && position < list.size()) {
            list.set(position, updatedTV);
            tvListLiveData.setValue(new ArrayList<>(list));
        }

    }
    public void setTVList(List<TV> list) {
        tvListLiveData.setValue(list);
    }
}
