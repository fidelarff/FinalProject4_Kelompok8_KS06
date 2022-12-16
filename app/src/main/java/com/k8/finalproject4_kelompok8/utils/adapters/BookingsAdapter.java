package com.k8.finalproject4_kelompok8.utils.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.k8.finalproject4_kelompok8.R;
import com.k8.finalproject4_kelompok8.utils.responses.BookingResponseData;

import java.util.List;

public class BookingsAdapter extends RecyclerView.Adapter<BookingsAdapter.ViewHolder> {
    List<BookingResponseData> bookingResponseData;
    Context context;
    LayoutInflater layoutInflater;
    EachBookingsClick eachBookingsClick;

    public BookingsAdapter(List<BookingResponseData> data, Context context){
        this.bookingResponseData=data;
        this.context=context;
        layoutInflater=LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= layoutInflater.inflate(R.layout.layout_booking, parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        BookingResponseData holderlist= bookingResponseData.get(position);
        holder.booking_date.setText("Booking Date: "+holderlist.getBookingDate());
        holder.fare.setText("Rp"+holderlist.getPrice());
        holder.schedule_date.setText("Schedule Date: "+holderlist.getScheduleDate());
        if(holderlist.getBookingStatus()==1){
            holder.booking_status.setText("Booked");
            holder.booking_status.setTextColor(Color.parseColor("#5DBE61"));
        }else{
            holder.booking_status.setText("Cancelled");
            holder.booking_status.setTextColor(Color.parseColor("#DD4A47"));
        }

        if(holderlist.getPayStatus()==0){
            holder.pay_status.setText("Unpaid");
            holder.pay_status.setTextColor(Color.parseColor("#DD4A47"));

        }else{
            holder.pay_status.setText("Paid");
            holder.pay_status.setTextColor(Color.parseColor("#5DBE61"));
        }
    }

    @Override
    public int getItemCount() {
        return bookingResponseData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView schedule_date,booking_date,booking_status, pay_status, fare;
        ImageView viewDetails;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            schedule_date=itemView.findViewById(R.id.schedule_date);
            booking_date=itemView.findViewById(R.id.bookingDate);
            booking_status=itemView.findViewById(R.id.bookingstatus);
            pay_status=itemView.findViewById(R.id.paystatus);
            fare=itemView.findViewById(R.id.fare);
            viewDetails=itemView.findViewById(R.id.viewdetails);

            viewDetails.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    eachBookingsClick.onEachBookingsClicked(getAdapterPosition());
                }
            });
        }
    }

    public interface EachBookingsClick {
        void onEachBookingsClicked(int position);
    }

    public void setEachBookingsClick(EachBookingsClick eachBookingsClick){
        this.eachBookingsClick= eachBookingsClick;
    }
}
