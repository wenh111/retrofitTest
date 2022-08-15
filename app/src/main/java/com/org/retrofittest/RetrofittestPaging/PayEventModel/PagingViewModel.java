package com.org.retrofittest.RetrofittestPaging.PayEventModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class PagingViewModel extends ViewModel {
    public MutableLiveData<Integer> since;
    public MutableLiveData<Integer> perPage;
    public MutableLiveData<Boolean> isOver;
    public PagingViewModel() {
        since = new MutableLiveData<>();
        perPage = new MutableLiveData<>();
        isOver = new MutableLiveData<>();
        isOver.setValue(false);
        since.setValue(-1);
        perPage.setValue(-1);
    }

    public MutableLiveData<Boolean> getIsOver() {
        return isOver;
    }

    public MutableLiveData<Integer> getSince() {
        return since;
    }

    public MutableLiveData<Integer> getPerPage() {
        return perPage;
    }

    public void dataChange(int cSince,int cPerPage){
        since.setValue(cSince);
        perPage.setValue(cPerPage);
    }
    public void dataLoadOver(boolean cIsOver){
        isOver.setValue(cIsOver);
    }
}
