package com.k8.finalproject4_kelompok8.utils.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.k8.finalproject4_kelompok8.R;
import com.k8.finalproject4_kelompok8.utils.Constants;
import com.k8.finalproject4_kelompok8.utils.responses.ScheduleResponseData;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.List;

public class BusToReserveAdapter extends RecyclerView.Adapter<BusToReserveAdapter.ViewHolder> implements Serializable {

    List<ScheduleResponseData> scheduleResponseData;
    Context context;
    LayoutInflater layoutInflater;
    BusReserveClick clickListeners;

    public BusToReserveAdapter(List<ScheduleResponseData> data, Context context){
        this.scheduleResponseData=data;
        this.context=context;
        layoutInflater=LayoutInflater.from(context);
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.layout_bus_foreserving, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ScheduleResponseData holderlist= scheduleResponseData.get(position);
        holder.bus_name.setText(holderlist.getBusName());
        holder.fare.setText("Rp"+holderlist.getPerDayPrice());
        holder.starting_point.setText(holderlist.getStartingPoint());
        holder.destination.setText(holderlist.getDestination());
        holder.cap.setText(holderlist.getCapacity()+"");
        Picasso.get().load(Constants.BASE_URL+"/bus_ticket_booking/"+holderlist.getBusImage()).into(holder.busimage);
    }

    @Override
    public int getItemCount() {
        return scheduleResponseData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView bus_name,cap,fare, starting_point, destination;
        ImageView busimage;
        Button reserveBtn;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            bus_name=itemView.findViewById(R.id.bus_name);
            busimage=itemView.findViewById(R.id.bus_image);
            cap=itemView.findViewById(R.id.capacity);
            fare=itemView.findViewById(R.id.fare);
            starting_point=itemView.findViewById(R.id.starting_point);
            destination=itemView.findViewById(R.id.destination);
            reserveBtn=itemView.findViewById(R.id.reserveBtn);

            reserveBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    clickListeners.onEachBusReserveClicked(getAdapterPosition());
                }
            });
        }
    }

    public interface BusReserveClick {
        void onEachBusReserveClicked(int position);
    }

    public void setBusReserveClick(BusReserveClick clickListeners){
        this.clickListeners= clickListeners;
    }
}
