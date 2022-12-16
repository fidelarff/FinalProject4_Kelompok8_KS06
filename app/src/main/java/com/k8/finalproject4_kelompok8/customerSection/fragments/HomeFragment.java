package com.k8.finalproject4_kelompok8.customerSection.fragments;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.k8.finalproject4_kelompok8.R;
import com.k8.finalproject4_kelompok8.api.ApiClient;
import com.k8.finalproject4_kelompok8.customerSection.BusForReserveActivity;
import com.k8.finalproject4_kelompok8.customerSection.EachScheduleDetailsActivity;
import com.k8.finalproject4_kelompok8.utils.Constants;
import com.k8.finalproject4_kelompok8.utils.SharedPreferenceUtils;
import com.k8.finalproject4_kelompok8.utils.adapters.TravelScheduleAdapter;
import com.k8.finalproject4_kelompok8.utils.responses.ScheduleResponse;
import com.k8.finalproject4_kelompok8.utils.responses.ScheduleResponseData;

import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment implements TravelScheduleAdapter.ScheduleClick{

    EditText placeTo,placeFrom;
    Button searchSchedule, reservebtn;
    LinearLayout infolayout;
    RecyclerView scheduleRV;
    DatePickerDialog datePickerDialog;
    SwipeRefreshLayout swipeRefreshLayout;
    TextView datepicker;
    boolean searched=false;

    List<ScheduleResponseData> scheduleResponseData;
    TravelScheduleAdapter scheduleAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        datepicker=view.findViewById(R.id.date_picker);
        placeTo=view.findViewById(R.id.placeTo);
        placeFrom=view.findViewById(R.id.placeFrom);
        searchSchedule=view.findViewById(R.id.searchSchedule);
        infolayout=view.findViewById(R.id.infoLayout);
        scheduleRV=view.findViewById(R.id.scheduleRV);
        swipeRefreshLayout=view.findViewById(R.id.refreshHome);
        reservebtn=view.findViewById(R.id.reservebus);

        reservebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), BusForReserveActivity.class));
            }
        });

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                placeTo.setText("");
                placeTo.setHint("Enter place name");
                placeFrom.setText("");
                placeFrom.setHint("Enter place name");
                datepicker.setText("");
                datepicker.setHint("Select the date");

                searched=false;
                getScheduleFromServer();
                searchStatus();
            }
        });

        datepicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // calender class's instance and get current date , month and year from calender
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                // date picker dialog
                datePickerDialog = new DatePickerDialog(getContext(),
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                if(String.valueOf(monthOfYear).length()==1 && String.valueOf(dayOfMonth).length()==1){
                                    datepicker.setText(year + "-0"
                                            + (monthOfYear + 1) + "-0" + dayOfMonth);
                                }else if(String.valueOf(monthOfYear).length()==1){
                                    datepicker.setText(year + "-0"
                                            + (monthOfYear + 1) + "-" + dayOfMonth);
                                }else if(String.valueOf(dayOfMonth).length()==1){
                                    datepicker.setText(year + "-"
                                            + (monthOfYear + 1) + "-0" + dayOfMonth);
                                }else {
                                    // set day of month , month and year value in the edit text
                                    datepicker.setText(year + "-"
                                            + (monthOfYear + 1) + "-" + dayOfMonth);
                                }
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        getScheduleFromServer();
        searchStatus();
        onSearch();
    }

    private void searchStatus() {
        if(searched){
            scheduleRV.setVisibility(View.VISIBLE);
            infolayout.setVisibility(View.GONE);
        }else{
            infolayout.setVisibility(View.VISIBLE);
            scheduleRV.setVisibility(View.GONE);
        }
        swipeRefreshLayout.setRefreshing(false);
    }

    private void onSearch() {
        searchSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validate()){
                    scheduleAdapter.getFilter().filter(placeFrom.getText().toString()+","+placeTo.getText().toString()+","+datepicker.getText().toString());
                    searched=true;
                    searchStatus();
                }
            }
        });
        swipeRefreshLayout.setRefreshing(false);
    }


    private Boolean validate(){
        String placefrom=placeFrom.getText().toString();
        String placeto= placeTo.getText().toString();
        String date= datepicker.getText().toString();

        if(placefrom.isEmpty() || placeto.isEmpty() ||date.isEmpty()){
            Toast.makeText(getActivity(), "You must fill the places and date to search!", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    @Override
    public void onEachScheduleClicked(int position) {
        ScheduleResponseData responseData= scheduleResponseData.get(position);
        Intent intent= new Intent(getActivity(), EachScheduleDetailsActivity.class);
        intent.putExtra(EachScheduleDetailsActivity.POST_DATA_KEY,responseData);
        startActivity(intent);
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
        scheduleRV.setHasFixedSize(true);
        scheduleRV.setLayoutManager(new LinearLayoutManager(getContext()) );
        scheduleAdapter= new TravelScheduleAdapter(scheduleResponseData,getContext());
        scheduleAdapter.setScheduleClick(this);
        scheduleRV.setAdapter(scheduleAdapter);
        swipeRefreshLayout.setRefreshing(false);
    }

}