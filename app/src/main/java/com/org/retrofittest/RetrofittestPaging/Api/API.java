package com.org.retrofittest.RetrofittestPaging.Api;

import com.org.retrofittest.RetrofittestPaging.PayEventModel.PayEventBean;
import com.org.retrofittest.RetrofittestPaging.UserBeen;
import com.org.retrofittest.UserResults;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface API {

    @GET("/user")
    Call<UserBeen> getJson();

    @POST("/user/userMessageInsert")
    Call<Integer> post(@Body UserResults userResults);

    @GET("AllPay/monthPayEvent")
    Call<PayEventBean> getPayEvent(@Query("account") String account,
                                   @Query("year") String year,
                                   @Query("month") String month,
                                   @Query("since") int since,
                                   @Query("perPages") int perPages,
                                   @Query("category") String category);
}
