package com.example.citizencare;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class ComplaintTodoMainAdapter extends FirebaseRecyclerAdapter<ComplaintTodoMainModel,ComplaintTodoMainAdapter.myViewHolder> {
    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     */
    public ComplaintTodoMainAdapter(@NonNull FirebaseRecyclerOptions<ComplaintTodoMainModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull ComplaintTodoMainAdapter.myViewHolder holder, int position, @NonNull ComplaintTodoMainModel model) {
        String complaintID=getSnapshots().getSnapshot(holder.getBindingAdapterPosition()).getKey();
        String imageUrl= model.getImage();
        Glide.with(holder.itemView.getContext())
                .load(imageUrl)
                .into(holder.Image);
        holder.cid.setText(complaintID);
        holder.UserID.setText(model.getUserID());
        holder.ComplaintType.setText(model.getComplaintType());
        holder.Description.setText(model.getDescription());
        holder.Date.setText(model.getDate());
        holder.Latitude.setText(model.getLatitude());
        holder.Longitude.setText(model.getLongitude());
        holder.Address.setText(model.getAddress());
        holder.ResDate.setText(model.getResolutionDate());
        holder.ServiceManID.setText(model.getServiceManID());
        holder.Status.setText(model.getStatus());

        if(!model.getServiceManID().equals("None")) {
            holder.sid1.setVisibility(View.VISIBLE);
            holder.ServiceManID.setVisibility(View.VISIBLE);
        }

        if(model.getStatus().equals("Completed")) {
            holder.cardView.setVisibility(View.GONE);
        }
    }

    @NonNull
    @Override
    public ComplaintTodoMainAdapter.myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.card_todolist_complaints,parent,false);
        return new ComplaintTodoMainAdapter.myViewHolder(view);
    }

    static class myViewHolder extends RecyclerView.ViewHolder{
        ImageView Image;
        TextView cid,UserID,ComplaintType,Description,Date,Latitude,Longitude,Address,ServiceManID,Status,sid1,ResDate,ResDate1;
        CardView cardView;
        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView=itemView.findViewById(R.id.cardview_todo_complaints);
            Image=itemView.findViewById(R.id.complaint_img);
            cid=itemView.findViewById(R.id.cid);
            UserID=itemView.findViewById(R.id.uid);
            ComplaintType=itemView.findViewById(R.id.ctype);
            Description=itemView.findViewById(R.id.desc);
            Date=itemView.findViewById(R.id.date);
            Latitude=itemView.findViewById(R.id.latitude);
            Longitude=itemView.findViewById(R.id.longitude);
            Address=itemView.findViewById(R.id.address);
            ServiceManID=itemView.findViewById(R.id.sid);
            sid1=itemView.findViewById(R.id.sid1);
            Status=itemView.findViewById(R.id.status);
            ResDate=itemView.findViewById(R.id.resdate);
            ResDate1=itemView.findViewById(R.id.resdate1);
        }
    }
}
