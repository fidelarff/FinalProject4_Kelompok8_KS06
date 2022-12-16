package com.k8.finalproject4_kelompok8.customerSection;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.k8.finalproject4_kelompok8.R;
import com.k8.finalproject4_kelompok8.api.ApiClient;
import com.k8.finalproject4_kelompok8.utils.Constants;
import com.k8.finalproject4_kelompok8.utils.SharedPreferenceUtils;
import com.k8.finalproject4_kelompok8.utils.adapters.BusToReserveAdapter;
import com.k8.finalproject4_kelompok8.utils.responses.ScheduleResponse;
import com.k8.finalproject4_kelompok8.utils.responses.ScheduleResponseData;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BusForReserveActivity extends AppCompatActivity implements BusToReserveAdapter.BusReserveClick {
    RecyclerView reserveRV;
    List<ScheduleResponseData> scheduleResponseData;
    BusToReserveAdapter busToReserveAdapter;
    SwipeRefreshLayout swipeRefreshLayout;
    TextView back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_for_reserve);
        reserveRV=findViewById(R.id.reserveRV);
        swipeRefreshLayout=findViewById(R.id.refreshBusToReserve);
        back=findViewById(R.id.backBtn);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getBusToReserveFromServer();
            }
        });
        getBusToReserveFromServer();
    }

    private void getBusToReserveFromServer() {
        Call<ScheduleResponse> scheduleResponseCall= ApiClient.getApiService()
                .get_bus_forReservation(SharedPreferenceUtils.getStringPreference(this, Constants.API_KEY));
        scheduleResponseCall.enqueue(new Callback<ScheduleResponse>() {
            @Override
            public void onResponse(Call<ScheduleResponse> call, Response<ScheduleResponse> response) {
                if(response.isSuccessful()){
                    if(!response.body().getError()){
                        if(response.body().getDatas() !=null)
                            setReserveRV(response.body().getDatas());
                    }
                }
                else{
                    Toast.makeText(BusForReserveActivity.this,"cant call server", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ScheduleResponse> call, Throwable t) {

            }
        });
    }

    private void setReserveRV(List<ScheduleResponseData> datas) {
        this.scheduleResponseData=datas;
        reserveRV.setHasFixedSize(true);
        reserveRV.setLayoutManager(new LinearLayoutManager(this) );
        busToReserveAdapter= new BusToReserveAdapter(scheduleResponseData,this);
        busToReserveAdapter.setBusReserveClick(this);
        reserveRV.setAdapter(busToReserveAdapter);
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onEachBusReserveClicked(int position) {
        ScheduleResponseData responseData= scheduleResponseData.get(position);
        Intent intent= new Intent(BusForReserveActivity.this, ReservePayProcessActivity.class);
        intent.putExtra(ReservePayProcessActivity.POST_DATA,responseData);
        startActivity(intent);
    }
}