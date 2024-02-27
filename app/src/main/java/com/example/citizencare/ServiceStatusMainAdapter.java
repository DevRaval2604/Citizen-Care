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

public class ServiceStatusMainAdapter extends FirebaseRecyclerAdapter<ServiceStatusMainModel,ServiceStatusMainAdapter.myViewHolder> {
    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     */
    public ServiceStatusMainAdapter(@NonNull FirebaseRecyclerOptions<ServiceStatusMainModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull ServiceStatusMainAdapter.myViewHolder holder, int position, @NonNull ServiceStatusMainModel model) {
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

        if(!model.getServiceManID().equals("None")){
            holder.sid1.setVisibility(View.VISIBLE);
            holder.ServiceManID.setVisibility(View.VISIBLE);
        }

        if(model.getStatus().equals("Completed")){
            holder.ResDate1.setVisibility(View.VISIBLE);
            holder.ResDate.setVisibility(View.VISIBLE);
            holder.feedback.setVisibility(View.VISIBLE);
        }

        if(!model.getFeedBackDescription().equals("None")&&!model.getFeedBackStars().equals(0)){
            holder.feedback.setVisibility(View.GONE);
        }

        holder.feedback.setOnClickListener(view -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(holder.Description.getContext());
            builder.setTitle("Are you sure?");
            builder.setMessage("You want to give feedback?");
            builder.setPositiveButton("Yes", (dialogInterface, i) -> {
                Intent intent=new Intent(holder.Description.getContext(), Provide_feedback.class);
                intent.putExtra("ServiceID",serviceID);
                intent.putExtra("ServiceType",model.getServiceType());
                holder.Description.getContext().startActivity(intent);
            });
            builder.setNegativeButton("No", (dialogInterface, i) -> Toast.makeText(holder.Description.getContext(), "Cancelled", Toast.LENGTH_LONG).show());
            builder.show();
        });
    }

    @NonNull
    @Override
    public ServiceStatusMainAdapter.myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.card_service_status,parent,false);
        return new ServiceStatusMainAdapter.myViewHolder(view);
    }

    static class myViewHolder extends RecyclerView.ViewHolder{
        TextView Sid,UserID,ServiceType,Description,Date,Address,ServiceManID,Status,sid1,ResDate,ResDate1;
        Button feedback;
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
            ResDate=itemView.findViewById(R.id.resdate);
            ResDate1=itemView.findViewById(R.id.resdate1);
            feedback=itemView.findViewById(R.id.button_user_feedback);
        }
    }
}
