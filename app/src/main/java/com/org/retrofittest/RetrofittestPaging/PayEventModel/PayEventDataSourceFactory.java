package com.org.retrofittest.RetrofittestPaging.PayEventModel;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;

public class PayEventDataSourceFactory extends DataSource.Factory<Integer,PayEventBean.AllPayListDTO>{
    public MutableLiveData<PayEventDataSource> liveData = new MutableLiveData<>();

    public String account;
    public String month;
    public String year;

    public PayEventDataSourceFactory(String account, String month, String year) {
        this.account = account;
        this.month = month;
        this.year = year;
    }

    @NonNull
    @Override
    public DataSource<Integer, PayEventBean.AllPayListDTO> create() {
        PayEventDataSource payEventDataSource = new PayEventDataSource(account,month,year);
        liveData.postValue(payEventDataSource);

        return payEventDataSource;
    }
}
