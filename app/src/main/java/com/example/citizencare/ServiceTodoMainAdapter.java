package com.example.citizencare;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class ServiceTodoMainAdapter extends FirebaseRecyclerAdapter<ServiceTodoMainModel,ServiceTodoMainAdapter.myViewHolder> {
    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     */
    public ServiceTodoMainAdapter(@NonNull FirebaseRecyclerOptions<ServiceTodoMainModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull ServiceTodoMainAdapter.myViewHolder holder, int position, @NonNull ServiceTodoMainModel model) {
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
        }
    }

    @NonNull
    @Override
    public ServiceTodoMainAdapter.myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.card_todolist_services,parent,false);
        return new ServiceTodoMainAdapter.myViewHolder(view);
    }

    static class myViewHolder extends RecyclerView.ViewHolder{
        TextView Sid,UserID,ServiceType,Description,Date,Address,ServiceManID,Status,sid1,ResDate,ResDate1;
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
        }
    }
}
