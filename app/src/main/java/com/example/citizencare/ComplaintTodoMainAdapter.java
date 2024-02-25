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
    private final TextView text2;
    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     */
    public ComplaintTodoMainAdapter(@NonNull FirebaseRecyclerOptions<ComplaintTodoMainModel> options,TextView text2) {
        super(options);
        this.text2=text2;
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
            holder.cid.setVisibility(View.GONE);
            holder.UserID.setVisibility(View.GONE);
            holder.ComplaintType.setVisibility(View.GONE);
            holder.Description.setVisibility(View.GONE);
            holder.Date.setVisibility(View.GONE);
            holder.Date1.setVisibility(View.GONE);
            holder.Latitude.setVisibility(View.GONE);
            holder.Longitude.setVisibility(View.GONE);
            holder.Address.setVisibility(View.GONE);
            holder.Status1.setVisibility(View.GONE);
            holder.Address1.setVisibility(View.GONE);
            holder.Longitude1.setVisibility(View.GONE);
            holder.Latitude1.setVisibility(View.GONE);
            holder.ComplaintType1.setVisibility(View.GONE);
            holder.UserID1.setVisibility(View.GONE);
            holder.Description1.setVisibility(View.GONE);
            holder.ResDate.setVisibility(View.GONE);
            holder.ServiceManID.setVisibility(View.GONE);
            holder.sid1.setVisibility(View.GONE);
            holder.Image.setVisibility(View.GONE);
            holder.Status.setVisibility(View.GONE);
            holder.ResDate1.setVisibility(View.GONE);
            holder.cardView.setVisibility(View.GONE);
            text2.setVisibility(View.VISIBLE);
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
        TextView cid,UserID,UserID1,ComplaintType1,Date1,Description1,ComplaintType,Description,Date,Latitude1,Latitude,Longitude1,Longitude,Address1,Address,ServiceManID,Status1,Status,sid1,ResDate,ResDate1;
        CardView cardView;
        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView=itemView.findViewById(R.id.cardview_todo_complaints);
            Image=itemView.findViewById(R.id.complaint_img);
            cid=itemView.findViewById(R.id.cid);
            UserID=itemView.findViewById(R.id.uid);
            UserID1=itemView.findViewById(R.id.uid1);
            Date1=itemView.findViewById(R.id.date1);
            ComplaintType1=itemView.findViewById(R.id.ctype1);
            Description1=itemView.findViewById(R.id.desc1);
            Latitude1=itemView.findViewById(R.id.latitude1);
            Longitude1=itemView.findViewById(R.id.longitude1);
            Address1=itemView.findViewById(R.id.address1);
            Status1=itemView.findViewById(R.id.status1);
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
