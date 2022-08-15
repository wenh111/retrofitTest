package com.org.retrofittest.RetrofittestPaging;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.org.retrofittest.RetrofittestPaging.Api.API;
import com.org.retrofittest.RetrofittestPaging.UserBeen;
import com.org.retrofittest.UserMessageAdapter;
import com.org.retrofittest.databinding.ActivityTest01Binding;

import java.net.HttpURLConnection;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Test01 extends AppCompatActivity {
    private Button but_get,but_post;
    private TextView tv;
    private RecyclerView recyclerView;
    private ActivityTest01Binding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTest01Binding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        /*but_get = findViewById(R.id.but_get);
        but_post = findViewById(R.id.but_post);
        recyclerView = findViewById(R.id.recycleView);*/
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        binding.recycleView.setLayoutManager(linearLayoutManager);
        binding.butGet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("http://114.132.54.246:9090")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                API api = retrofit.create(API.class);
                Call<UserBeen> task = api.getJson();
                task.enqueue(new Callback<UserBeen>() {
                    @Override
                    public void onResponse(Call<UserBeen> call, Response<UserBeen> response) {
                        if(response.code() == HttpURLConnection.HTTP_OK){
                            UpdateMessage(response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<UserBeen> call, Throwable t) {
                        Log.i("unsuccessful",t.toString());
                    }
                });
            }
        });

        /*but_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("http://114.132.54.246:9090")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                API api = retrofit.create(API.class);
               *//* Map<String,Object> map = new HashMap<>();
                map.put("name","wenh");
                map.put("account","963@qq.com");
                map.put("password","222222");
                map.put("telephone_number","15989966668");*//*
                UserResults userResults = new UserResults("wenh","963@qq.com","222222","15989966668");
                Call<Integer> task = api.post(userResults);
                task.enqueue(new Callback<Integer>() {
                    @Override
                    public void onResponse(Call<Integer> call, Response<Integer> response) {
                        Log.i("onResponse","code =========>" + response.code());
                        Log.i("onResponse","onResponse =========>" + response.errorBody());
                        if(response.code() == HttpURLConnection.HTTP_OK){
                            Log.i("Successful","onResponse =========>" + response.body());
                        }

                    }

                    @Override
                    public void onFailure(Call<Integer> call, Throwable t) {
                        Log.i("UnSuccessful","errorCode =========>" + t.toString());
                    }
                });
            }
        });*/
    }

    private void UpdateMessage(UserBeen User) {

        UserMessageAdapter adapter = new UserMessageAdapter(this, new ArrayList<>());
        //adapter.setUserData(userResults.getUserResultsList());

        adapter.setUserData(User.getUserMessage());
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);
    }
}