package com.k8.finalproject4_kelompok8.customerSection;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.k8.finalproject4_kelompok8.R;
import com.k8.finalproject4_kelompok8.utils.responses.BookingResponseData;

public class BookingDetailsActivity extends AppCompatActivity {

    public static final String DATA_KEY = "bd";

    TextView startingpoint, scheduledate, destination,noofseats, fareamount, back,bookingdate,paymethod,paystatus,bookingstatus;

    BookingResponseData bookingResponseData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_details);

        scheduledate=findViewById(R.id.schedule_date);
        startingpoint=findViewById(R.id.startfrom);
        destination=findViewById(R.id.destination);
        noofseats=findViewById(R.id.bookedSeats);
        fareamount=findViewById(R.id.priceTV);
        paymethod=findViewById(R.id.pay_method);
        paystatus=findViewById(R.id.pay_status);
        bookingstatus=findViewById(R.id.booking_status);
        back=findViewById(R.id.backBtn);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        bookingResponseData= (BookingResponseData) getIntent().getSerializableExtra(DATA_KEY);

        scheduledate.setText(bookingResponseData.getScheduleDate());
        startingpoint.setText(bookingResponseData.getStartingPoint());
        destination.setText(bookingResponseData.getDestination());
        noofseats.setText(bookingResponseData.getNoOfSeats()+"");
        fareamount.setText("Rp"+bookingResponseData.getPrice());
        paymethod.setText(bookingResponseData.getPayMethod());
        if(bookingResponseData.getBookingStatus()==1){
            bookingstatus.setText("Booked");
            bookingstatus.setTextColor(Color.GREEN);
        }else{
            bookingstatus.setText("Cancelled");
            bookingstatus.setTextColor(Color.RED);
        }

        if(bookingResponseData.getPayStatus()==0){
            paystatus.setText("Unpaid");
            paystatus.setTextColor(Color.RED);

        }else{
            paystatus.setText("Paid");
            paystatus.setTextColor(Color.GREEN);
        }

    }
}