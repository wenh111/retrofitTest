package com.org.retrofittest.RetrofittestPaging.PayEventModel;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.paging.ItemKeyedDataSource;

import com.org.retrofittest.RetrofittestPaging.Api.RetrofitClient;

import java.net.HttpURLConnection;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PayEventDataSource extends ItemKeyedDataSource<Integer, PayEventBean.AllPayListDTO> {
    public static int PER_PAGE = -1;
    public String account;
    public String month;
    public String year;

    public PayEventDataSource(String account, String month, String year) {
        this.account = account;
        this.month = month;
        this.year = year;
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull LoadInitialCallback<PayEventBean.AllPayListDTO> callback) {
        RetrofitClient.getInstance().getApi().getPayEvent(account, year, month, -1, -1)
                .enqueue(new Callback<PayEventBean>() {
                    @Override
                    public void onResponse(Call<PayEventBean> call, Response<PayEventBean> response) {
                        if (response.code() == HttpURLConnection.HTTP_OK) {
                            if (response.body() != null) {
                                //callback.onResult((List<PayEventBean>) response.body());
                                callback.onResult(response.body().getAllPayList());
                                PER_PAGE = response.body().getPerPage();
                                Log.i("loadInitialSuccessful", "count ------------------> " + response.body().getCount());
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<PayEventBean> call, Throwable t) {
                        Log.i("loadInitialUnSuccessful", "error ------------------> " + t.getMessage());
                    }
                });
    }

    @Override
    public void loadAfter(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<PayEventBean.AllPayListDTO> callback) {
        RetrofitClient.getInstance().getApi().getPayEvent(account, year, month, params.key, PER_PAGE)
                .enqueue(new Callback<PayEventBean>() {
                    @Override
                    public void onResponse(Call<PayEventBean> call, Response<PayEventBean> response) {
                        if (response.code() == HttpURLConnection.HTTP_OK) {
                            if (response.body() != null) {
                                //callback.onResult((List<PayEventBean>) response.body());
                                callback.onResult(response.body().getAllPayList());
                                PER_PAGE = response.body().getPerPage();
                                Log.i("loadAfterSuccessful", "count ------------------> " + response.body().getCount());
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<PayEventBean> call, Throwable t) {
                        Log.i("loadAfterUnSuccessful", "error ------------------> " + t.getMessage());
                    }
                });
    }

    @Override
    public void loadBefore(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<PayEventBean.AllPayListDTO> callback) {

    }

    @NonNull
    @Override
    public Integer getKey(@NonNull PayEventBean.AllPayListDTO item) {
        return item.getInt_date();
    }
}
