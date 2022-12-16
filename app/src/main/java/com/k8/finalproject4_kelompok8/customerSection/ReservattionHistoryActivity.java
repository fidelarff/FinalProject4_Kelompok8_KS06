package com.k8.finalproject4_kelompok8.customerSection;

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
import com.k8.finalproject4_kelompok8.utils.adapters.ReserveHistoryAdapter;
import com.k8.finalproject4_kelompok8.utils.responses.ReserveHistoryData;
import com.k8.finalproject4_kelompok8.utils.responses.ReserveResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReservattionHistoryActivity extends AppCompatActivity {
    RecyclerView reservationRV;
    List<ReserveHistoryData> reserveHistoryData;
    ReserveHistoryAdapter reserveHistoryAdapter;
    SwipeRefreshLayout swipeRefreshLayout;
    TextView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation_history);
        reservationRV=findViewById(R.id.reservationRV);
        swipeRefreshLayout=findViewById(R.id.refreshReservations);
        back= findViewById(R.id.backBtn);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getReservationsFromServer( );
            }
        });
        getReservationsFromServer( );
    }

    private void getReservationsFromServer() {
        Call<ReserveResponse> reserveResponseCall= ApiClient.getApiService()
                .view_reservations(SharedPreferenceUtils.getStringPreference(this, Constants.API_KEY));

        reserveResponseCall.enqueue(new Callback<ReserveResponse>() {
            @Override
            public void onResponse(Call<ReserveResponse> call, Response<ReserveResponse> response) {
                if(response.isSuccessful()){
                    if(!response.body().getError()){
                        if(response.body().getData() !=null)
                            setHistoryRV(response.body().getData());
                    }
                }
                else{
                    Toast.makeText(ReservattionHistoryActivity.this,"cant call server", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ReserveResponse> call, Throwable t) {

            }
        });
    }

    private void setHistoryRV(List<ReserveHistoryData> reserveHistoryData) {
        this.reserveHistoryData=reserveHistoryData;
        reservationRV.setHasFixedSize(true);
        reservationRV.setLayoutManager(new LinearLayoutManager(this) );
        reserveHistoryAdapter= new ReserveHistoryAdapter(reserveHistoryData,this);
        reservationRV.setAdapter(reserveHistoryAdapter);
        swipeRefreshLayout.setRefreshing(false);
    }

}