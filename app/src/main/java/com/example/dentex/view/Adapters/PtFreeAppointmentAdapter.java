package com.example.dentex.view.Adapters;
import static com.example.dentex.FireBase.FBUserHelper.db;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

import com.example.dentex.Appointments.Appointment;
import com.example.dentex.Appointments.AppointmentHelper;
import com.example.dentex.R;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class PtFreeAppointmentAdapter extends FirestoreRecyclerAdapter<Appointment, PtCalendarAdapter.MyViewHolder> {
    private Context context;

    public PtFreeAppointmentAdapter(Context context, @NonNull FirestoreRecyclerOptions<Appointment> options) {
        super(options);
        this.context = context;
    }
    // This method creates a new ViewHolder object for each item in the RecyclerView
    @Override
    public PtCalendarAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_appointment_pt, parent, false);
        return new PtCalendarAdapter.MyViewHolder(itemView);
    }

   private void setupDialog2(Appointment appointment, String id) {
       new AlertDialog.Builder(context)
               .setTitle("האם אתה בטוח?")
               .setMessage("אתה בטוח שאתה רוצה לקבוע את התור הזה?")
               .setPositiveButton("כן", new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialog, int which) {
                       // Handle "Yes" action
                       performAction2(appointment,id);
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

   private void performAction2(Appointment appointment,String id) {
       ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.POST_NOTIFICATIONS}, 100);
       AppointmentHelper.getUserAppointments(new AppointmentHelper.AppointmentsCallback() {
           @Override
           public List<Appointment> onAppointmentsLoaded(List<Appointment> appointments) {
               AppointmentHelper.addAppointmentToUser(appointment, new AppointmentHelper.AddAppointmentCallback() {
                   @Override
                   public void onAppointmentAdded(String appointmentId) {
                       AppointmentHelper.scheduleNotification(context, appointment);
                       db.collection("openappointments")
                               .document(id)
                               .delete();
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

    @Override
    protected void onBindViewHolder(@NonNull PtCalendarAdapter.MyViewHolder holder, int position, @NonNull Appointment appointment) {
        holder.Drname.setText(appointment.getDrname());
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yy HH:mm", Locale.getDefault());
        holder.Date.setText(dateFormat.format(appointment.getDate()));
        holder.TreatmentType.setText(appointment.getTreatmentType());
        String id = this.getSnapshots().getSnapshot(position).getId();
        holder.button.setText("קבע תור");
        holder.button.setOnClickListener(v -> {
            Toast.makeText(context, "click!", Toast.LENGTH_SHORT).show();
            setupDialog2(appointment,id);
        });
    }
}