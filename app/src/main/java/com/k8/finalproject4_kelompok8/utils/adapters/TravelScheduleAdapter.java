package com.k8.finalproject4_kelompok8.utils.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.k8.finalproject4_kelompok8.R;
import com.k8.finalproject4_kelompok8.utils.Constants;
import com.k8.finalproject4_kelompok8.utils.responses.ScheduleResponseData;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class TravelScheduleAdapter extends RecyclerView.Adapter<TravelScheduleAdapter.ViewHolder> implements Serializable, Filterable {


    List<ScheduleResponseData> scheduleResponseData;
    List<ScheduleResponseData> searchData;
    Context context;
    LayoutInflater layoutInflater;
    ScheduleClick clickListeners;


    public TravelScheduleAdapter(List<ScheduleResponseData> data, Context context){
        this.scheduleResponseData=data;
        this.context=context;
        searchData= new ArrayList<>(data);
        layoutInflater=LayoutInflater.from(context);
        }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;

        view = layoutInflater.inflate(R.layout.layout_schedule, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ScheduleResponseData holderlist= scheduleResponseData.get(position);
        holder.bus_name.setText(holderlist.getBusName());
        holder.fare.setText("Rp"+holderlist.getFareAmount());
        holder.starting_point.setText("From: "+holderlist.getStartingPoint());
        holder.destination.setText("To: "+holderlist.getDestination());
        holder.schedule_date.setText(holderlist.getScheduleDate());
        holder.departure_time.setText(holderlist.getDepartureTime());
        Picasso.get().load(Constants.BASE_URL+"/bus_ticket_booking/"+holderlist.getBusImage()).into(holder.busimage);
    }

    @Override
    public int getItemCount() {
        return scheduleResponseData.size();
    }


    @Override
    public Filter getFilter() {
        return filter;
    }

    private Filter filter= new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<ScheduleResponseData> list= new ArrayList<>();
            if(constraint==null || constraint.length()==0){
                list.addAll(searchData);
            }else{
                // create a REGEX String
                String REGEX = ",";
                // create a Pattern using REGEX
                Pattern pattern = Pattern.compile(REGEX);

                // split the text
                String[] array = pattern.split(constraint);

                String pattern1=array[0].toLowerCase().trim();
                String pattern2=array[1].toLowerCase().trim();
                String pattern3=array[2].toLowerCase();
                for (ScheduleResponseData responseData:searchData){
                    if(responseData.getStartingPoint().toLowerCase().contains(pattern1) && responseData.getDestination().toLowerCase().contains(pattern2) && responseData.getScheduleDate().toLowerCase().contains(pattern3)){
                        list.add(responseData);
                    }
                }
            }
            FilterResults results=new FilterResults();
            results.values= list;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            scheduleResponseData.clear();
            scheduleResponseData.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView bus_name,busnumber,cap,fare, starting_point, destination,schedule_date,departure_time;
        LinearLayout scheduleclick;
        ImageView  busimage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            bus_name=itemView.findViewById(R.id.bus_name);
            busnumber=itemView.findViewById(R.id.bus_number);
            busimage=itemView.findViewById(R.id.bus_image);
            cap=itemView.findViewById(R.id.capacity);
            fare=itemView.findViewById(R.id.fare);
            starting_point=itemView.findViewById(R.id.starting_point);
            destination=itemView.findViewById(R.id.destination);
            schedule_date=itemView.findViewById(R.id.schedule_date);
            departure_time=itemView.findViewById(R.id.departure_time);

            scheduleclick=itemView.findViewById(R.id.scheduleClick);


            scheduleclick.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    clickListeners.onEachScheduleClicked(getAdapterPosition());
                }
            });

        }
    }

    public interface ScheduleClick {
        void onEachScheduleClicked(int position);
    }

    public void setScheduleClick(ScheduleClick clickListeners){
        this.clickListeners= clickListeners;
    }
}
