package com.example.dentex.Appointments;

import static android.app.PendingIntent.getActivity;

import android.Manifest;
import android.app.Activity;
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
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dentex.R;

import java.util.List;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class PtAppointmentAdapter extends FirestoreRecyclerAdapter<Appointment, PtAppointmentAdapter.MyViewHolder> {
    private Context context;

    public PtAppointmentAdapter(@NonNull FirestoreRecyclerOptions<Appointment> options) {
        super(options);
    }

    // This method creates a new ViewHolder object for each item in the RecyclerView
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_appointment_pt, parent, false);
        return new MyViewHolder(itemView);
    }

    private void setupDialog2(int pos) {
        new AlertDialog.Builder(context)
                .setTitle("האם אתה בטוח?")
                .setMessage("אתה בטוח שאתה רוצה לקבוע את התור הזה?")
                .setPositiveButton("כן", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Handle "Yes" action
                        performAction2(pos);
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

    private void performAction2(int pos) {
        ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.POST_NOTIFICATIONS}, 100);
        AppointmentHelper.getUserAppointments(new AppointmentHelper.AppointmentsCallback() {
            @Override
            public List<Appointment> onAppointmentsLoaded(List<Appointment> appointments) {
                AppointmentHelper.addAppointmentToUser(appointments.get(pos), new AppointmentHelper.AddAppointmentCallback() {
                    @Override
                    public void onAppointmentAdded(String appointmentId) {
                        AppointmentHelper.setAlarmForAppointment(context, appointments.get(pos));
                        appointments.remove(pos);
                        Toast.makeText(context, "התוק נקבע בהצלחה", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onAppointmentError(Exception e) {

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

    private void setupDialog(int pos) {
        new AlertDialog.Builder(context)
                .setTitle("האם אתה בטוח?")
                .setMessage("אתה בטוח שאתה רוצה לבטל את התור הזה?")
                .setPositiveButton("כן", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Handle "Yes" action
                        performDeleteAction(pos);
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

    private void performDeleteAction(int pos) {
        AppointmentHelper.getUserAppointments(new AppointmentHelper.AppointmentsCallback() {
            @Override
            public List<Appointment> onAppointmentsLoaded(List<Appointment> appointments) {
                AppointmentHelper.stopAlarm(context, appointments.get(pos));
                Toast.makeText(context, "התור בוטל בהצלחה", Toast.LENGTH_SHORT).show();
                appointments.remove(pos);
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
    protected void onBindViewHolder(@NonNull MyViewHolder holder, int position, @NonNull Appointment model) {

    }

    // This class defines the ViewHolder object for each item in the RecyclerView
    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView Date;
        private TextView Drname;
        private TextView TreatmentType;
        private Button button;

        public MyViewHolder(View itemView) {
            super(itemView);
            TreatmentType = itemView.findViewById(R.id.Treatment);
            Drname = itemView.findViewById(R.id.Doctor);
            Date = itemView.findViewById(R.id.Date);
            button = itemView.findViewById(R.id.button3);
        }
    }
}
