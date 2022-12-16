package com.k8.finalproject4_kelompok8.customerSection;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.k8.finalproject4_kelompok8.R;
import com.k8.finalproject4_kelompok8.utils.Constants;
import com.k8.finalproject4_kelompok8.utils.responses.ScheduleResponseData;
import com.squareup.picasso.Picasso;

public class EachScheduleDetailsActivity extends AppCompatActivity {
    public static String POST_DATA_KEY="data";

    ImageView busimage;
    TextView busname, busnumber, capacity,available, startingpoint, scheduledate, destination, departuretime, fareamount, back;
    Button bookBtn;

    ScheduleResponseData scheduleResponseData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_each_schedule_details);

        busimage=findViewById(R.id.bus_image);
        busname=findViewById(R.id.bus_name);
        busnumber=findViewById(R.id.bus_number);
        capacity=findViewById(R.id.capacity);
        available=findViewById(R.id.available);
        scheduledate=findViewById(R.id.schedule_date);
        startingpoint=findViewById(R.id.startfrom);
        destination=findViewById(R.id.destination);
        departuretime=findViewById(R.id.departure_time);
        fareamount=findViewById(R.id.priceTV);
        bookBtn=findViewById(R.id.bookBtn);
        back=findViewById(R.id.backBtn);

        scheduleResponseData= (ScheduleResponseData) getIntent().getSerializableExtra(POST_DATA_KEY);

        busname.setText(scheduleResponseData.getBusName());
        busnumber.setText("#"+scheduleResponseData.getBusNumber());
        capacity.setText("Total Seats: "+scheduleResponseData.getCapacity());
        available.setText("Available Seats: "+scheduleResponseData.getAvailableSeats());
        scheduledate.setText(scheduleResponseData.getScheduleDate());
        startingpoint.setText("From: "+scheduleResponseData.getStartingPoint());
        destination.setText("To: "+scheduleResponseData.getDestination());
        departuretime.setText(scheduleResponseData.getDepartureTime());
        fareamount.setText("Rp"+scheduleResponseData.getFareAmount());
        Picasso.get().load(Constants.BASE_URL+"/bus_ticket_booking/"+scheduleResponseData.getBusImage()).into(busimage);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        bookBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(EachScheduleDetailsActivity.this, SeatsBookingActivity.class);
                intent.putExtra(EachScheduleDetailsActivity.POST_DATA_KEY,scheduleResponseData);
                startActivity(intent);
            }
        });

    }
}