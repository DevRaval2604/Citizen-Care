package com.example.citizencare;

import android.app.AlertDialog;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class MainAdapter3 extends FirebaseRecyclerAdapter<MainModel3,MainAdapter3.myViewHolder> {
    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     */
    public MainAdapter3(@NonNull FirebaseRecyclerOptions<MainModel3> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull MainAdapter3.myViewHolder holder, int position, @NonNull MainModel3 model) {
        String serviceID=getSnapshots().getSnapshot(holder.getBindingAdapterPosition()).getKey();
        holder.Sid.setText(serviceID);
        holder.UserID.setText(model.getUserID());
        holder.ServiceType.setText(model.getServiceType());
        holder.Description.setText(model.getDescription());
        holder.Date.setText(model.getDate());
        holder.Address.setText(model.getAddress());
        holder.ResDate.setText(model.getResolutionDate());
        holder.ServiceManID.setText(model.getServiceManID());
        holder.Status.setText(model.getStatus());
        SimpleDateFormat dateFormat=new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        Calendar calendar=Calendar.getInstance();
        Date currentDate=calendar.getTime();
        String formattedDate=dateFormat.format(currentDate);

        if(!model.getServiceManID().equals("None")){
            holder.sid1.setVisibility(View.VISIBLE);
            holder.ServiceManID.setVisibility(View.VISIBLE);
            holder.Assign.setVisibility(View.GONE);
            holder.Cancel.setVisibility(View.GONE);
            holder.Update.setVisibility(View.VISIBLE);
            holder.Change.setVisibility(View.VISIBLE);
        }

        if(model.getStatus().equals("Completed")){
            holder.ResDate1.setVisibility(View.VISIBLE);
            holder.ResDate.setVisibility(View.VISIBLE);
            holder.Update.setVisibility(View.GONE);
            holder.Change.setVisibility(View.GONE);
        }

        if (model.getStatus().equals("Cancelled")){
            holder.Assign.setVisibility(View.GONE);
            holder.Cancel.setVisibility(View.GONE);
            holder.Update.setVisibility(View.GONE);
            holder.Change.setVisibility(View.GONE);
        }

        holder.Assign.setOnClickListener(view -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(holder.Description.getContext());
            builder.setTitle("Are you sure?");
            builder.setMessage("You want to assign a serviceman?");
            builder.setPositiveButton("Assign", (dialogInterface, i) -> {
                Intent intent=new Intent(holder.Description.getContext(), AssignServiceman2.class);
                intent.putExtra("ServiceID",serviceID);
                holder.Description.getContext().startActivity(intent);
            });
            builder.setNegativeButton("Cancel", (dialogInterface, i) -> Toast.makeText(holder.Description.getContext(), "Cancelled", Toast.LENGTH_LONG).show());
            builder.show();
        });

        holder.Cancel.setOnClickListener(view -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(holder.Description.getContext());
            builder.setTitle("Are you sure?");
            builder.setMessage("You want to cancel this requested service?");
            builder.setPositiveButton("Yes", (dialogInterface, i) -> {
                FirebaseDatabase.getInstance().getReference().child("Services").child(Objects.requireNonNull(serviceID)).child("Status").setValue("Cancelled");
                Toast.makeText(holder.Description.getContext(), "Service Request Cancelled Successfully", Toast.LENGTH_LONG).show();
            });
            builder.setNegativeButton("No", (dialogInterface, i) -> {});
            builder.show();
        });

        holder.Update.setOnClickListener(view -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(holder.Description.getContext());
            builder.setTitle("Are you sure?");
            builder.setMessage("You want to update the status?");
            builder.setPositiveButton("Update", (dialogInterface, i) -> {
                FirebaseDatabase.getInstance().getReference().child("Services").child(Objects.requireNonNull(serviceID)).child("Status").setValue("Completed");
                model.setStatus("Completed");
                FirebaseDatabase.getInstance().getReference().child("Services").child(Objects.requireNonNull(serviceID)).child("ResolutionDate").setValue(formattedDate);
                model.setResolutionDate(formattedDate);
                Toast.makeText(holder.Description.getContext(), "Status Updated Successfully", Toast.LENGTH_LONG).show();
            });
            builder.setNegativeButton("Cancel", (dialogInterface, i) -> Toast.makeText(holder.Description.getContext(), "Cancelled", Toast.LENGTH_LONG).show());
            builder.show();
        });

        holder.Change.setOnClickListener(view -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(holder.Description.getContext());
            builder.setTitle("Are you sure?");
            builder.setMessage("You want to change the serviceman?");
            builder.setPositiveButton("Yes", (dialogInterface, i) -> {
                Intent intent=new Intent(holder.Description.getContext(), AssignServiceman2.class);
                intent.putExtra("ServiceID",serviceID);
                holder.Description.getContext().startActivity(intent);
            });
            builder.setNegativeButton("No", (dialogInterface, i) -> {});
            builder.show();
        });
    }

    @NonNull
    @Override
    public MainAdapter3.myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.main_item3,parent,false);
        return new MainAdapter3.myViewHolder(view);
    }

    static class myViewHolder extends RecyclerView.ViewHolder{
        TextView Sid,UserID,ServiceType,Description,Date,Address,ServiceManID,Status,sid1,ResDate,ResDate1;
        Button Assign,Cancel,Update,Change;
        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            Sid=itemView.findViewById(R.id.Sid);
            UserID=itemView.findViewById(R.id.uid);
            ServiceType=itemView.findViewById(R.id.stype);
            Description=itemView.findViewById(R.id.desc);
            Date=itemView.findViewById(R.id.date);
            Address=itemView.findViewById(R.id.address);
            ServiceManID=itemView.findViewById(R.id.sid);
            sid1=itemView.findViewById(R.id.sid1);
            Status=itemView.findViewById(R.id.status);
            Assign=itemView.findViewById(R.id.button_assign);
            Cancel=itemView.findViewById(R.id.button_cancel);
            Update=itemView.findViewById(R.id.button_update_status);
            Change=itemView.findViewById(R.id.button_change_serviceman);
            ResDate=itemView.findViewById(R.id.resdate);
            ResDate1=itemView.findViewById(R.id.resdate1);
        }
    }
}
