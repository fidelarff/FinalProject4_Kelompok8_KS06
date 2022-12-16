package com.k8.finalproject4_kelompok8.customerSection;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.k8.finalproject4_kelompok8.R;
import com.k8.finalproject4_kelompok8.api.ApiClient;
import com.k8.finalproject4_kelompok8.utils.Constants;
import com.k8.finalproject4_kelompok8.utils.SharedPreferenceUtils;
import com.k8.finalproject4_kelompok8.utils.responses.ReserveResponse;
import com.k8.finalproject4_kelompok8.utils.responses.ScheduleResponseData;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
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

public class ReservePayProcessActivity extends AppCompatActivity {
    public static String POST_DATA="reservedata";

    TextView busname, capacity, startingpoint, destination, fareamount, back, total_price, start_date, end_date;
    Button reserveBtn, reserveInstantBtn;
    CheckBox cash, instant;
    DatePickerDialog datePickerDialog;
    String days;
    int price=0;
    long totalAmountInPaisa=0;
    String ProductName;
    String id="";

    ScheduleResponseData responseData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserve_pay_process);
        back=findViewById(R.id.back);
        busname=findViewById(R.id.bus_name);
        startingpoint=findViewById(R.id.starting_point);
        destination=findViewById(R.id.destination);
        capacity=findViewById(R.id.capacity);
        fareamount=findViewById(R.id.fare);
        total_price=findViewById(R.id.total);
        start_date=findViewById(R.id.start_date_picker);
        end_date=findViewById(R.id.end_date_picker);
        reserveBtn=findViewById(R.id.confirm);
        reserveInstantBtn=findViewById(R.id.confirm_instant);
        cash=findViewById(R.id.cash);
        instant=findViewById(R.id.instant);

        responseData= (ScheduleResponseData) getIntent().getSerializableExtra(POST_DATA);

        busname.setText(responseData.getBusName());
        startingpoint.setText(responseData.getStartingPoint());
        destination.setText(responseData.getDestination());
        capacity.setText(responseData.getCapacity()+"");
        fareamount.setText("Rp"+responseData.getPerDayPrice());
        total_price.setText("Rp0" );

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        setDateAndPriceMethod();

        reserveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmReservations("Cash", 0);
                int SDK_INT = android.os.Build.VERSION.SDK_INT;
                if (SDK_INT > 8)
                {
                    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                            .permitAll().build();
                    StrictMode.setThreadPolicy(policy);

                }
            }
        });

        reserveInstantBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmReservations("Instant", 1);
                int SDK_INT = android.os.Build.VERSION.SDK_INT;
                if (SDK_INT > 8)
                {
                    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                            .permitAll().build();
                    StrictMode.setThreadPolicy(policy);

                }
            }
        });



    }

    private void confirmReservations(String paymethod, int paystatus) {
        Call<ReserveResponse> reserveResponseCall= ApiClient.getApiService().reserve(
                SharedPreferenceUtils.getStringPreference(getApplicationContext(), Constants.API_KEY
                ), responseData.getrId(), Integer.parseInt(days),
                start_date.getText().toString(), end_date.getText().toString(),
                paymethod, paystatus, price );

        reserveResponseCall.enqueue(new Callback<ReserveResponse>() {
            @Override
            public void onResponse(Call<ReserveResponse> call, Response<ReserveResponse> response) {
                if (response.isSuccessful())
                    if (!response.body().getError()) {
                        //reservation was complete
                      //  sendMessageToEmail();

                        startActivity(new Intent(ReservePayProcessActivity.this,ReservationSuccessActivity.class));
                        finish();
                    } else {
                        //reservation was unsuccessful
                        Toast.makeText(ReservePayProcessActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();

                    }
            }

            @Override
            public void onFailure(Call<ReserveResponse> call, Throwable t) {

            }
        });
    }

    private void setDateAndPriceMethod() {
        start_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // calender class's instance and get current date , month and year from calender
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                // date picker dialog
                datePickerDialog = new DatePickerDialog(ReservePayProcessActivity.this,
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {
                            if(String.valueOf(monthOfYear).length()==1 && String.valueOf(dayOfMonth).length()==1){
                                start_date.setText(year + "-0"
                                        + (monthOfYear + 1) + "-0" + dayOfMonth);
                            }else if(String.valueOf(monthOfYear).length()==1){
                                start_date.setText(year + "-0"
                                        + (monthOfYear + 1) + "-" + dayOfMonth);
                            }else if(String.valueOf(dayOfMonth).length()==1){
                                start_date.setText(year + "-"
                                        + (monthOfYear + 1) + "-0" + dayOfMonth);
                            }else {
                                // set day of month , month and year value in the edit text
                                start_date.setText(year + "-"
                                        + (monthOfYear + 1) + "-" + dayOfMonth);
                            }
                        }
                    }, mYear, mMonth, mDay);

                datePickerDialog.getDatePicker().setMinDate(c.getTimeInMillis());
                datePickerDialog.show();
            }
        });

        end_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // calender class's instance and get current date , month and year from calender
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                // date picker dialog
                datePickerDialog = new DatePickerDialog(ReservePayProcessActivity.this,
                        new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        if(String.valueOf(monthOfYear).length()==1 && String.valueOf(dayOfMonth).length()==1){
                            end_date.setText(year + "-0"
                                    + (monthOfYear + 1) + "-0" + dayOfMonth);
                        }else if(String.valueOf(monthOfYear).length()==1){
                            end_date.setText(year + "-0"
                                    + (monthOfYear + 1) + "-" + dayOfMonth);
                        }else if(String.valueOf(dayOfMonth).length()==1){
                            end_date.setText(year + "-"
                                    + (monthOfYear + 1) + "-0" + dayOfMonth);
                        }else {
                            // set day of month , month and year value in the edit text
                            end_date.setText(year + "-"
                                    + (monthOfYear + 1) + "-" + dayOfMonth);
                        }

                        try {
                            calculateTotal();
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                }, mYear, mMonth, mDay);
                datePickerDialog.getDatePicker().setMinDate(c.getTimeInMillis());
                datePickerDialog.show();
            }
        });


    }

    private void calculateTotal() throws ParseException {
        Date date1;
        Date date2;
        SimpleDateFormat dates = new SimpleDateFormat("yyyy-mm-dd");
        date1 = dates.parse(String.valueOf(start_date.getText()));
        date2 = dates.parse(String.valueOf(end_date.getText()));
        long difference = Math.abs(date1.getTime() - date2.getTime());
        long differenceDates = difference / (24 * 60 * 60 * 1000);
        String dayDifference = Long.toString(differenceDates);
        days=dayDifference;
        price= Integer.parseInt(dayDifference)*responseData.getPerDayPrice();
        total_price.setText("Rp" + String.valueOf(price));
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
            message.setSubject("Reservation Success");
            message.setText("Bus reserved Successfully! Please wait for our call for double confirmation");
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
                    reserveBtn.setVisibility(View.VISIBLE);
                    reserveInstantBtn.setVisibility(View.GONE);
                    instant.setChecked(false);
                break;
            case R.id.instant:
                if (checked)
                    reserveInstantBtn.setVisibility(View.VISIBLE);
                    reserveBtn.setVisibility(View.GONE);
                    cash.setChecked(false);
                break;
        }
    }
}