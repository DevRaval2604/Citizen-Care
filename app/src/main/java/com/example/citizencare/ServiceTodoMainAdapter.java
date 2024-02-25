package com.example.citizencare;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class ServiceTodoMainAdapter extends FirebaseRecyclerAdapter<ServiceTodoMainModel,ServiceTodoMainAdapter.myViewHolder> {
    private final TextView text2;
    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     */
    public ServiceTodoMainAdapter(@NonNull FirebaseRecyclerOptions<ServiceTodoMainModel> options,TextView text2) {
        super(options);
        this.text2=text2;
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
            holder.Sid.setVisibility(View.GONE);
            holder.UserID.setVisibility(View.GONE);
            holder.ServiceType.setVisibility(View.GONE);
            holder.Description.setVisibility(View.GONE);
            holder.Date.setVisibility(View.GONE);
            holder.Address.setVisibility(View.GONE);
            holder.ResDate.setVisibility(View.GONE);
            holder.sid1.setVisibility(View.GONE);
            holder.ResDate1.setVisibility(View.GONE);
            holder.ServiceManID.setVisibility(View.GONE);
            holder.Status.setVisibility(View.GONE);
            holder.UserID1.setVisibility(View.GONE);
            holder.ServiceType1.setVisibility(View.GONE);
            holder.Date1.setVisibility(View.GONE);
            holder.Description1.setVisibility(View.GONE);
            holder.Address1.setVisibility(View.GONE);
            holder.Status1.setVisibility(View.GONE);
            holder.cardView.setVisibility(View.GONE);
            text2.setVisibility(View.VISIBLE);
        }
    }

    @NonNull
    @Override
    public ServiceTodoMainAdapter.myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.card_todolist_services,parent,false);
        return new ServiceTodoMainAdapter.myViewHolder(view);
    }

    static class myViewHolder extends RecyclerView.ViewHolder{
        TextView Sid,UserID,UserID1,ServiceType1,Date1,Description1,Address1,Status1,ServiceType,Description,Date,Address,ServiceManID,Status,sid1,ResDate,ResDate1;
        CardView cardView;
        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView=itemView.findViewById(R.id.cardview_todo_services);
            Sid=itemView.findViewById(R.id.Sid);
            UserID=itemView.findViewById(R.id.uid);
            UserID1=itemView.findViewById(R.id.uid1);
            ServiceType1=itemView.findViewById(R.id.stype1);
            Date1=itemView.findViewById(R.id.date1);
            Description1=itemView.findViewById(R.id.desc1);
            Address1=itemView.findViewById(R.id.address1);
            Status1=itemView.findViewById(R.id.status1);
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
