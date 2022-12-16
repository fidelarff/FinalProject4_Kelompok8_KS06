package com.k8.finalproject4_kelompok8.customerSection;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.k8.finalproject4_kelompok8.R;

public class ReservationSuccessActivity extends AppCompatActivity {
    Button explore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation_success);

        explore=findViewById(R.id.explore);


        explore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ReservationSuccessActivity.this, ReservattionHistoryActivity.class));
                finish();
            }
        });

    }
}