package com.k8.finalproject4_kelompok8.customerSection;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.k8.finalproject4_kelompok8.R;
import com.k8.finalproject4_kelompok8.api.ApiClient;
import com.k8.finalproject4_kelompok8.utils.Constants;
import com.k8.finalproject4_kelompok8.utils.SharedPreferenceUtils;
import com.k8.finalproject4_kelompok8.utils.responses.BookingResponse;
import com.k8.finalproject4_kelompok8.utils.responses.ScheduleResponseData;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Bill_Pay_Activity extends AppCompatActivity {

    TextView  startingpoint, scheduledate, destination, noofseats, fareamount, back;
    CheckBox cash, instant;
    Button confirmBtn, confirmInstantBtn;
    int totalAmount=0;
    long totalAmountInPaisa=0;
    String ProductName;
    String id="";

    ScheduleResponseData scheduleResponseData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill_pay);
        scheduledate=findViewById(R.id.schedule_date);
        startingpoint=findViewById(R.id.startfrom);
        destination=findViewById(R.id.destination);
        noofseats=findViewById(R.id.bookedSeats);
        fareamount=findViewById(R.id.priceTV);
        back=findViewById(R.id.backBtn);
        cash=findViewById(R.id.cash);
        instant=findViewById(R.id.instant);
        confirmBtn=findViewById(R.id.confirm);
        confirmInstantBtn=findViewById(R.id.confirm_instant);
        scheduleResponseData= (ScheduleResponseData) getIntent().getSerializableExtra(EachScheduleDetailsActivity.POST_DATA_KEY);

        scheduledate.setText(scheduleResponseData.getScheduleDate());
        startingpoint.setText(scheduleResponseData.getStartingPoint());
        destination.setText(scheduleResponseData.getDestination());
        noofseats.setText(getIntent().getStringExtra(SeatsBookingActivity.SEATS)+"");
        fareamount.setText("Rp"+getIntent().getStringExtra(SeatsBookingActivity.TOTAL_PRICE));

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmBooking("Cash", 0);
                int SDK_INT = android.os.Build.VERSION.SDK_INT;
                if (SDK_INT > 8)
                {
                    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                            .permitAll().build();
                    StrictMode.setThreadPolicy(policy);

                }
            }
        });

        confirmInstantBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmBooking("Instant", 1);
                int SDK_INT = android.os.Build.VERSION.SDK_INT;
                if (SDK_INT > 8)
                {
                    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                            .permitAll().build();
                    StrictMode.setThreadPolicy(policy);

                }
            }
        });

        totalAmount= Integer.valueOf(getIntent().getStringExtra(SeatsBookingActivity.TOTAL_PRICE));
        totalAmountInPaisa=(long) (totalAmount*100);
        ProductName= "Bookings";
        id= ProductName.substring(1,2);
    }

    private void confirmBooking( String pay_method, int pay_status) {
        Call<BookingResponse> bookingResponseCall = ApiClient.getApiService()
                .book(SharedPreferenceUtils.getStringPreference(this, Constants.API_KEY),
                        scheduleResponseData.getScheduleId(),
                        Integer.parseInt(getIntent().getStringExtra(SeatsBookingActivity.SEATS)),
                        pay_method, pay_status,
                        Integer.parseInt(getIntent().getStringExtra(SeatsBookingActivity.TOTAL_PRICE)) );


        bookingResponseCall.enqueue(new Callback<BookingResponse>() {
            @Override
            public void onResponse(Call<BookingResponse> call, Response<BookingResponse> response) {
                if (response.isSuccessful())
                    if (!response.body().getError()) {
                        //booking was complete
                       // sendMessageToEmail();

                        startActivity(new Intent(Bill_Pay_Activity.this,BookingSuccessActivity.class));
                        finish();
                    } else {
                        //booking was unsuccessful
                        Toast.makeText(Bill_Pay_Activity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();

                    }
            }

            @Override
            public void onFailure(Call<BookingResponse> call, Throwable t) {

            }
        });

    }

    private void sendMessageToEmail() {
        final String email="write your email";
        final String password="password";

        String emailTo=SharedPreferenceUtils.getStringPreference(this,Constants.EMAIL);

        Properties properties= new Properties();
        properties.put("mail.smtp.auth","true");
        properties.put("mail.smtp.starttls.enable","true");
        properties.put("mail.smtp.host","smtp.gmail.com");
        properties.put("mail.smtp.port","587");
        Session session= Session.getInstance(properties,
                new javax.mail.Authenticator(){
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(email,password);
                    }
                });

        try {
            Message message=new MimeMessage(session);
            message.setFrom(new InternetAddress(email));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(emailTo));
            message.setSubject("Order Success");
            message.setText("Order placed Successfully! Please wait for our call for double confirmation");
            Transport.send(message);

        }catch (MessagingException e){
            throw new RuntimeException(e);
        }

    }

    public void onCheckBoxClicked(View view) {
        boolean checked = ((CheckBox) view).isChecked();

        // Check which checkbox was clicked
        switch(view.getId()) {
            case R.id.cash:
                if (checked)
                    confirmBtn.setVisibility(View.VISIBLE);
                    confirmInstantBtn.setVisibility(View.GONE);
                    instant.setChecked(false);
                break;
            case R.id.instant:
                if (checked)
                    confirmInstantBtn.setVisibility(View.VISIBLE);
                    confirmBtn.setVisibility(View.GONE);
                    cash.setChecked(false);
                break;
        }
    }
}