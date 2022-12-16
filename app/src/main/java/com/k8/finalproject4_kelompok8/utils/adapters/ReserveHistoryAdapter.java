package com.k8.finalproject4_kelompok8.utils.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.k8.finalproject4_kelompok8.R;
import com.k8.finalproject4_kelompok8.utils.responses.ReserveHistoryData;

import java.util.List;

public class ReserveHistoryAdapter extends RecyclerView.Adapter<ReserveHistoryAdapter.ViewHolder> {
    List<ReserveHistoryData> reserveHistoryData;
    Context context;
    LayoutInflater layoutInflater;
    boolean shown=false;

    public ReserveHistoryAdapter(List<ReserveHistoryData> data, Context context){
        this.reserveHistoryData=data;
        this.context=context;
        layoutInflater=LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= layoutInflater.inflate(R.layout.layout_reservations, parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ReserveHistoryData holderlist=reserveHistoryData.get(position);
        holder.bus_name.setText(holderlist.getBusName());
        holder.fare.setText("Rp"+holderlist.getPrice());
        holder.bus_number.setText("#"+holderlist.getBusNumber());
        holder.destination.setText(holderlist.getDestination());
        holder.end_date.setText(holderlist.getEndDate());
        holder.no_of_days.setText(holderlist.getNoOfDays()+" days");
        holder.reserveDate.setText(holderlist.getReservedDate());
        holder.paymethod.setText(holderlist.getPayMethod());
        holder.starting_point.setText(holderlist.getStartingPoint());
        holder.start_date.setText(holderlist.getStartDate());

        if(holderlist.getPayStatus()==1){
            holder.paystatus.setText("Paid");
            holder.paystatus.setTextColor(Color.parseColor("#5DBE61"));
        }else{
            holder.paystatus.setText("Unpaid");
            holder.paystatus.setTextColor(Color.parseColor("#DD4A47"));
        }

        if (holderlist.getReservedStatus()==1){
            holder.reservestatus.setText("Reserved");
            holder.reservestatus.setTextColor(Color.parseColor("#5DBE61"));
        }else{
            holder.reservestatus.setText("Cancelled");
            holder.reservestatus.setTextColor(Color.parseColor("#DD4A47"));
        }
    }

    @Override
    public int getItemCount() {
        return reserveHistoryData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView reserveDate,starting_point,destination, paymethod, paystatus,
                fare,reservestatus,viewdetails,bus_name,bus_number,start_date,end_date,no_of_days;
        LinearLayout reservedetails;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            reserveDate=itemView.findViewById(R.id.reserveDate);
            starting_point=itemView.findViewById(R.id.starting_point);
            destination=itemView.findViewById(R.id.destination);
            paymethod=itemView.findViewById(R.id.paymethod);
            paystatus=itemView.findViewById(R.id.paystatus);
            reservestatus=itemView.findViewById(R.id.reservestatus);
            bus_name=itemView.findViewById(R.id.bus_name);
            bus_number=itemView.findViewById(R.id.bus_number);
            fare=itemView.findViewById(R.id.fare);
            viewdetails=itemView.findViewById(R.id.viewdetails);
            start_date=itemView.findViewById(R.id.start_date);
            end_date=itemView.findViewById(R.id.end_date);
            no_of_days=itemView.findViewById(R.id.no_of_days);
            reservedetails=itemView.findViewById(R.id.reservedetails);

            viewdetails.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(shown){
                        reservedetails.setVisibility(View.VISIBLE);
                        viewdetails.setText("Hide Details");
                        shown=false;
                    }else{
                        reservedetails.setVisibility(View.GONE);
                        viewdetails.setText("View Details");
                        shown=true;
                    }
                }
            });
        }
    }
}
