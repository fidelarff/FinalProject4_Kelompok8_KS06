package com.k8.finalproject4_kelompok8.customerSection;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.k8.finalproject4_kelompok8.R;
import com.k8.finalproject4_kelompok8.utils.responses.ScheduleResponseData;

public class SeatsBookingActivity extends AppCompatActivity {

    TextView plus, minus,counterTv, proceedBtn;
    int count=1;
    int total=0;

    public static String TOTAL_PRICE="t";
    public static String SEATS="s";

    ScheduleResponseData scheduleResponseData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seats_booking);
        plus=findViewById(R.id.countplus);
        minus=findViewById(R.id.countminus);
        counterTv=findViewById(R.id.counterTV);
        proceedBtn=findViewById(R.id.proceedBtn);

        scheduleResponseData= (ScheduleResponseData) getIntent().getSerializableExtra(EachScheduleDetailsActivity.POST_DATA_KEY);

        counterTv.setText(count+"");
        total=count*scheduleResponseData.getFareAmount();
        proceedBtn.setText("Proceed ( Rp"+total+" )");

        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(count<scheduleResponseData.getAvailableSeats()){
                    count++;
                    counterTv.setText(count+"");
                    total=count*scheduleResponseData.getFareAmount();
                    proceedBtn.setText("Proceed ( Rp"+total+" )");
                }

            }
        });

        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(count>1){
                    count--;
                    counterTv.setText(count+"");
                    total=count*scheduleResponseData.getFareAmount();
                    proceedBtn.setText("Proceed ( Rp"+total+" )");
                }
            }
        });


        proceedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(SeatsBookingActivity.this, Bill_Pay_Activity.class);
                intent.putExtra(EachScheduleDetailsActivity.POST_DATA_KEY,scheduleResponseData);
                intent.putExtra(SEATS,String.valueOf(count));
                intent.putExtra(TOTAL_PRICE,String.valueOf(total));
                startActivity(intent);
            }
        });


    }


}