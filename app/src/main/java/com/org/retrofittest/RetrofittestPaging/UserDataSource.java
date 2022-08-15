package com.org.retrofittest.RetrofittestPaging;


import android.util.Log;

import androidx.annotation.NonNull;
import androidx.paging.PositionalDataSource;

import com.org.retrofittest.RetrofittestPaging.Api.RetrofitClient;

import java.net.HttpURLConnection;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserDataSource extends PositionalDataSource<UserBeen.UserMessageDTO> {
    //每次加载的数据
    public static final int PER_PAGE = 8;

    @Override
    public void loadInitial(@NonNull final LoadInitialParams params, @NonNull final LoadInitialCallback<UserBeen.UserMessageDTO> callback) {
        //从第0条数据开始加载
        int startPosition = 0;
        RetrofitClient.getInstance()
                .getApi()
                .getJson().enqueue(new Callback<UserBeen>() {
            @Override
            public void onResponse(Call<UserBeen> call, Response<UserBeen> response) {
                if (response.code() == HttpURLConnection.HTTP_OK) {
                    callback.onResult(response.body().userMessage, 0);
                }
            }

            @Override
            public void onFailure(Call<UserBeen> call, Throwable t) {
                Log.i("Unsuccessful", t.getMessage());
            }
        });
    }

    @Override
    public void loadRange(@NonNull final LoadRangeParams params, @NonNull final LoadRangeCallback<UserBeen.UserMessageDTO> callback) {
//调用我们之前封装好的RetrofitClient请求网络数据
        RetrofitClient.getInstance()
                .getApi()
                .getJson()
                .enqueue(new Callback<UserBeen>() {
                    @Override
                    public void onResponse(Call<UserBeen> call, Response<UserBeen> response) {
                        if (response.body() != null) {
                            //如果请求到了数据,就将请求到的数据发送出去
                            callback.onResult(response.body().userMessage);

                        }
                    }

                    @Override
                    public void onFailure(Call<UserBeen> call, Throwable t) {
                        Log.e("true", t.toString());
                    }
                });

    }
}


