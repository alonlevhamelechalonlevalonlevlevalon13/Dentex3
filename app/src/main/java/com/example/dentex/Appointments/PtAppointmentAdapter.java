package com.example.dentex.Appointments;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.dentex.R;

import java.util.ArrayList;

public class PtAppointmentAdapter extends RecyclerView.Adapter<PtAppointmentAdapter.MyViewHolder> {
    private ArrayList<Appointment> applist;
    private ItemClickListener mClickListener;
    private Context context;
    public PtAppointmentAdapter(ArrayList<Appointment> applist) {
        this.applist = applist;
    }

    public PtAppointmentAdapter(Context context, ArrayList<Appointment> Data) {
        this.applist = Data;
        this.context = context;
    }

    // This method creates a new ViewHolder object for each item in the RecyclerView
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Inflate the layout for each item and return a new ViewHolder object
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_appointment_pt, parent, false);
        return new MyViewHolder(itemView);
    }

    // This method returns the total 
    // number of items in the data set
    @Override
    public int getItemCount() {
        if (applist == null) {
            return 0;
        }
        return applist.size();
    }

    // This method binds the data to the ViewHolder object 
    // for each item in the RecyclerView
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Appointment currentApp = applist.get(position);
        holder.TreatmentType.setText(currentApp.getTreatmentType());
        holder.Drname.setText(currentApp.getDrname());
        holder.Date.setText(currentApp.getDate().toString());
    }

    // This class defines the ViewHolder object for each item in the RecyclerView
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView Date;
        private TextView Drname;
        private TextView TreatmentType;
        public MyViewHolder(View itemView) {
            super(itemView);
            TreatmentType = itemView.findViewById(R.id.Treatment);
            Drname = itemView.findViewById(R.id.Doctor);
            Date = itemView.findViewById(R.id.Date);
        }
    }
    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}
