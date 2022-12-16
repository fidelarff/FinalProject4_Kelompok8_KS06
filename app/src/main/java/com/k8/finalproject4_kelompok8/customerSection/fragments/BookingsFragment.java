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

import com.google.android.material.snackbar.Snackbar;
import com.k8.finalproject4_kelompok8.R;
import com.k8.finalproject4_kelompok8.api.ApiClient;
import com.k8.finalproject4_kelompok8.customerSection.BookingDetailsActivity;
import com.k8.finalproject4_kelompok8.utils.Constants;
import com.k8.finalproject4_kelompok8.utils.SharedPreferenceUtils;
import com.k8.finalproject4_kelompok8.utils.adapters.BookingsAdapter;
import com.k8.finalproject4_kelompok8.utils.responses.BookingResponse;
import com.k8.finalproject4_kelompok8.utils.responses.BookingResponseData;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookingsFragment extends Fragment implements BookingsAdapter.EachBookingsClick{
    RecyclerView bookingRV;
    List<BookingResponseData> bookingResponseData;
    BookingsAdapter bookingsAdapter;
    SwipeRefreshLayout swipeRefreshLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_bookings, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bookingRV=view.findViewById(R.id.bookingsRV);
        swipeRefreshLayout=view.findViewById(R.id.refreshBookings);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getBookingsFromServer( view);
            }
        });
        getBookingsFromServer( view);
    }

    private void getBookingsFromServer( View view) {
        Call<BookingResponse> bookingResponseCall= ApiClient.getApiService()
                .get_bookings(SharedPreferenceUtils.getStringPreference(getContext(), Constants.API_KEY));

        bookingResponseCall.enqueue(new Callback<BookingResponse>() {
            @Override
            public void onResponse(Call<BookingResponse> call, Response<BookingResponse> response) {
                if(response.isSuccessful()){
                    if(!response.body().getError()){
                        if(response.body().getData() !=null)
                            setBookingsRV(response.body().getData());

                    }else{
                        Snackbar.make(view,"No booking history!", Snackbar.LENGTH_LONG).show();
                    }
                }
                else{
                    Toast.makeText(getContext(),"cant call server", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<BookingResponse> call, Throwable t) {

            }
        });

    }

    private void setBookingsRV(List<BookingResponseData> data) {
        this.bookingResponseData=data;
        bookingRV.setHasFixedSize(true);
        bookingRV.setLayoutManager(new LinearLayoutManager(getContext()) );
        bookingsAdapter= new BookingsAdapter(bookingResponseData,getContext());
        bookingsAdapter.setEachBookingsClick(this);
        bookingRV.setAdapter(bookingsAdapter);
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onEachBookingsClicked(int position) {
        BookingResponseData responseData= bookingResponseData.get(position);
        Intent intent= new Intent(getActivity(), BookingDetailsActivity.class);
        intent.putExtra(BookingDetailsActivity.DATA_KEY,responseData);
        startActivity(intent);
    }
}