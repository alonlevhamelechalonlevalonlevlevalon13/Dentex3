package com.example.dentex.Appointments;
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
import com.example.dentex.R;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class PtAppointmentAdapter extends FirestoreRecyclerAdapter<Appointment, PtAppointmentAdapter.MyViewHolder> {
    private Context context;

    public PtAppointmentAdapter(Context context, @NonNull FirestoreRecyclerOptions<Appointment> options) {
        super(options);
        this.context = context;
    }
    // This method creates a new ViewHolder object for each item in the RecyclerView
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_appointment_pt, parent, false);
        return new MyViewHolder(itemView);
    }

    private void setupDialog(Appointment appointment) {
        new AlertDialog.Builder(context)
                .setTitle("האם אתה בטוח?")
                .setMessage("אתה בטוח שאתה רוצה לבטל את התור הזה?")
                .setPositiveButton("כן", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Handle "Yes" action
                        performDeleteAction(appointment);
                    }
                })
                .setNegativeButton("לא", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(context, "הוספת התור התבטלה", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                })
                .create()
                .show();
    }

    private void performDeleteAction(Appointment appointment) {
        AppointmentHelper.getUserAppointments(new AppointmentHelper.AppointmentsCallback() {
            @Override
            public List<Appointment> onAppointmentsLoaded(List<Appointment> appointments) {
                AppointmentHelper.stopAlarm(context, appointment);
                Toast.makeText(context, "התור בוטל בהצלחה", Toast.LENGTH_SHORT).show();
                appointments.remove(appointment);
                notifyAll();
                notifyDataSetChanged();
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
        holder.button.setOnClickListener(v -> {
            setupDialog(appointment);
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
