package com.k8.finalproject4_kelompok8.customerSection.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.k8.finalproject4_kelompok8.R;
import com.k8.finalproject4_kelompok8.api.ApiClient;
import com.k8.finalproject4_kelompok8.customerSection.EachScheduleDetailsActivity;
import com.k8.finalproject4_kelompok8.utils.Constants;
import com.k8.finalproject4_kelompok8.utils.SharedPreferenceUtils;
import com.k8.finalproject4_kelompok8.utils.adapters.TravelScheduleAdapter;
import com.k8.finalproject4_kelompok8.utils.responses.ScheduleResponse;
import com.k8.finalproject4_kelompok8.utils.responses.ScheduleResponseData;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ExploreFragment extends Fragment implements TravelScheduleAdapter.ScheduleClick {
    RecyclerView travelRV;
    List<ScheduleResponseData> scheduleResponseData;
    TravelScheduleAdapter scheduleAdapter;
    SwipeRefreshLayout swipeRefreshLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_explore, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        travelRV=view.findViewById(R.id.scheduleRV);
        swipeRefreshLayout=view.findViewById(R.id.refreshTravels);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getScheduleFromServer();
            }
        });
        getScheduleFromServer();
    }

    private void getScheduleFromServer() {
        Call<ScheduleResponse> scheduleResponseCall= ApiClient.getApiService()
                .get_all_schedule(SharedPreferenceUtils.getStringPreference(getContext(), Constants.API_KEY));
        scheduleResponseCall.enqueue(new Callback<ScheduleResponse>() {
            @Override
            public void onResponse(Call<ScheduleResponse> call, Response<ScheduleResponse> response) {
                if(response.isSuccessful()){
                    if(!response.body().getError()){
                        if(response.body().getDatas() !=null)
                            setScheduleRV(response.body().getDatas());
                    }
                }
                else{
                    Toast.makeText(getContext(),"cant call server", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ScheduleResponse> call, Throwable t) {

            }
        });
    }

    private void setScheduleRV(List<ScheduleResponseData> scheduleResponseData) {
        this.scheduleResponseData=scheduleResponseData;
        travelRV.setHasFixedSize(true);
        travelRV.setLayoutManager(new LinearLayoutManager(getContext()) );
        scheduleAdapter= new TravelScheduleAdapter(scheduleResponseData,getContext());
        scheduleAdapter.setScheduleClick(this);
        travelRV.setAdapter(scheduleAdapter);
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onEachScheduleClicked(int position) {
        ScheduleResponseData responseData= scheduleResponseData.get(position);
        Intent intent= new Intent(getActivity(), EachScheduleDetailsActivity.class);
        intent.putExtra(EachScheduleDetailsActivity.POST_DATA_KEY,responseData);
        startActivity(intent);
    }
}