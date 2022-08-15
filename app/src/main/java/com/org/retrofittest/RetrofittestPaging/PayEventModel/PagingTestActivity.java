package com.org.retrofittest.RetrofittestPaging.PayEventModel;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.org.retrofittest.R;
import com.org.retrofittest.RetrofittestPaging.Api.RetrofitClient;
import com.org.retrofittest.RetrofittestPaging.LoadInterface;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PagingTestActivity extends AppCompatActivity {
    List<PayEventBean.AllPayListDTO> payListDTOS = new ArrayList<>();
    public LoadInterface loadInterface;
    RecyclerView recyclerView;
    PagingViewModel pagingViewModel;

    public void setLoadInterface(LoadInterface loadInterface) {
        this.loadInterface = loadInterface;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paging_test);
        pagingViewModel = new ViewModelProvider(this).get(PagingViewModel.class);
        recyclerView = findViewById(R.id.recycleView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        PayEventPagedListAdapter payEventPagedListAdapter = new PayEventPagedListAdapter(getApplicationContext(), new ArrayList<>());
        recyclerView.setAdapter(payEventPagedListAdapter);
        String account = "456@qq.com";
        String month = "7";
        String year = "2022";

        Button button = findViewById(R.id.but_get);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    if (!recyclerView.canScrollVertically(1)) {

                        int since = pagingViewModel.getSince().getValue();
                        int perPage = pagingViewModel.getPerPage().getValue();
                        if (loadInterface != null) {
                            loadInterface.OnLoadLister(since, perPage);
                        }
                        Log.i("ScrollStateChanged", "------------------->" + "到底了");
                    }
                }
            }

        });
        setLoadInterface((since, perPage) -> {
            if (!pagingViewModel.getIsOver().getValue()) {
                RetrofitClient.getInstance().getApi().getPayEvent(account, year, month, since, perPage)
                        .enqueue(new Callback<PayEventBean>() {
                            @SuppressLint("NotifyDataSetChanged")
                            @Override
                            public void onResponse(Call<PayEventBean> call, Response<PayEventBean> response) {
                                if (response.code() == HttpURLConnection.HTTP_OK) {
                                    payListDTOS = response.body().getAllPayList();
                                    if (response.body().getPerPage() == 0) {
                                        pagingViewModel.dataLoadOver(true);
                                        payEventPagedListAdapter.setHasMore(false);
                                    }else{
                                        payEventPagedListAdapter.setHasMore(true);
                                    }
                                    pagingViewModel.dataChange(response.body().getSince(), response.body().getPerPage());
                                    Log.i("OnLoadListerPayListDTOS", "pages ------------------>" + response.body().getPerPage());
                                    payEventPagedListAdapter.addData(payListDTOS);
                                    Log.i("OnLoadListerPayListDTOS", "since ------------------>" + response.body().getSince());
                                    //payEventPagedListAdapter.notifyItemRangeInserted(response.body().getCount()-1,);

                                    Log.i("OnLoadListerPayListDTOS", "isOver ------------------>" + pagingViewModel.getIsOver().getValue());
                                }
                            }

                            @Override
                            public void onFailure(Call<PayEventBean> call, Throwable t) {
                                Log.i("OnLoadListerOnFailure", "onFailure ------------------>" + t.getMessage());
                            }
                        });
            } else {
                Log.i("OnLoadListerPayListDTOS", "isOver ------------------>" + "数据加载完毕");
            }

        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recyclerView.setVisibility(View.VISIBLE);
                pagingViewModel.dataLoadOver(false);
                RetrofitClient.getInstance().getApi().getPayEvent(account, year, month, -1, -1)
                        .enqueue(new Callback<PayEventBean>() {
                            @SuppressLint("NotifyDataSetChanged")
                            @Override
                            public void onResponse(Call<PayEventBean> call, Response<PayEventBean> response) {
                                if (response.code() == HttpURLConnection.HTTP_OK) {
                                    payListDTOS = response.body().getAllPayList();
                                    if (response.body().getPerPage() == 0) {
                                        pagingViewModel.dataLoadOver(true);
                                        payEventPagedListAdapter.setHasMore(false);
                                    }else{
                                        payEventPagedListAdapter.setHasMore(true);
                                    }
                                    pagingViewModel.dataChange(response.body().getSince(), response.body().getPerPage());
                                    Log.i("payListDTOS", "pages ------------------>" + response.body().getPerPage());
                                    payEventPagedListAdapter.setData(payListDTOS);
                                    Log.i("payListDTOS", "since ------------------>" + response.body().getSince());
                                    payEventPagedListAdapter.notifyDataSetChanged();

                                    Log.i("payListDTOS", "isOver ------------------>" + pagingViewModel.getIsOver().getValue());
                                }
                            }

                            @Override
                            public void onFailure(Call<PayEventBean> call, Throwable t) {
                                Log.i("onFailure", "onFailure ------------------>" + t.getMessage());
                            }
                        });
            }
        });


    }


}