package com.example.dentex.view.Adapters;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dentex.model.Appointment;
import com.example.dentex.utils.AppointmentHelper;
import com.example.dentex.R;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class PtCalendarAdapter extends FirestoreRecyclerAdapter<Appointment, PtCalendarAdapter.MyViewHolder> {
    private Context context;

    public PtCalendarAdapter(Context context, @NonNull FirestoreRecyclerOptions<Appointment> options) {
        super(options);
        this.context = context;
    }
    // This method creates a new ViewHolder object for each item in the RecyclerView
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_appointment_pt, parent, false);
        return new MyViewHolder(itemView);
    }

    private void setupDialog(Appointment appointment, String id) {
        new AlertDialog.Builder(context)
                .setTitle("are you sure?")
                .setMessage("are you sure you want to cancel this appointment?")
                .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Handle "Yes" action
                        performDeleteAction(appointment, id);
                    }
                })
                .setNegativeButton("no", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .create()
                .show();
    }

    private void performDeleteAction(Appointment appointment,String id) {
        AppointmentHelper.getUserAppointments(new AppointmentHelper.AppointmentsCallback() {
            @Override
            public List<Appointment> onAppointmentsLoaded(List<Appointment> appointments) {
                AppointmentHelper.stopAlarm(context, appointment);
                AppointmentHelper.removeAppointment(id, new AppointmentHelper.RemoveAppointmentsCallback() {
                    @Override
                    public void onAppointmentsRemoved() {
                        Toast.makeText(context, "appointment successfully canceled", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onAppointmentsError(Exception e) {
                    }
                });
                return appointments;
            }

            @Override
            public void onAppointmentsError(Exception e) {
                //Handle error
            }
        });
    }


    @Override
    protected void onBindViewHolder(@NonNull MyViewHolder holder, int position, @NonNull Appointment appointment) {
        holder.Drname.setText(appointment.getDrname());
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yy HH:mm", Locale.getDefault());
        holder.Date.setText(dateFormat.format(appointment.getDate()));
        holder.TreatmentType.setText(appointment.getTreatmentType());
        String id = this.getSnapshots().getSnapshot(position).getId();
        holder.button.setOnClickListener(v -> {
            setupDialog(appointment,id);
        });
    }

    // This class defines the ViewHolder object for each item in the RecyclerView
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView Date;
        TextView Drname;
        TextView TreatmentType;
        Button button;

        public MyViewHolder(View itemView) {
            super(itemView);
            TreatmentType = itemView.findViewById(R.id.Treatment);
            Drname = itemView.findViewById(R.id.Doctor);
            Date = itemView.findViewById(R.id.Date);
            button = itemView.findViewById(R.id.button3);
        }
    }
}
