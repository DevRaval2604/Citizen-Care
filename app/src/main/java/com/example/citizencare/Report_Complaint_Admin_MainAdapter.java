package com.example.citizencare;

import android.annotation.SuppressLint;
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

public class Report_Complaint_Admin_MainAdapter extends FirebaseRecyclerAdapter<Report_Complaint_Admin_MainModel, Report_Complaint_Admin_MainAdapter.myViewHolder> {
    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     */
    public Report_Complaint_Admin_MainAdapter(@NonNull FirebaseRecyclerOptions<Report_Complaint_Admin_MainModel> options) {
        super(options);
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onBindViewHolder(@NonNull Report_Complaint_Admin_MainAdapter.myViewHolder holder, int position, @NonNull Report_Complaint_Admin_MainModel model) {
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
        holder.FeedbackStars.setText(Integer.toString(model.getFeedBackStars()));
        holder.Feedbackdesc.setText(model.getFeedBackDescription());

        if(!model.getServiceManID().equals("None")) {
            holder.sid1.setVisibility(View.VISIBLE);
            holder.ServiceManID.setVisibility(View.VISIBLE);
        }
        if(!model.getFeedBackStars().equals(0)){
            holder.FeedbackStars1.setVisibility(View.VISIBLE);
            holder.FeedbackStars.setVisibility(View.VISIBLE);
        }
        if(!model.getFeedBackDescription().equals("None")){
            holder.Feedbackdesc1.setVisibility(View.VISIBLE);
            holder.Feedbackdesc.setVisibility(View.VISIBLE);
        }
        if(model.getStatus().equals("Completed")) {
            holder.ResDate1.setVisibility(View.VISIBLE);
            holder.ResDate.setVisibility(View.VISIBLE);
        }

    }

    @NonNull
    @Override
    public Report_Complaint_Admin_MainAdapter.myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.card_complaintreport_admin,parent,false);
        return new Report_Complaint_Admin_MainAdapter.myViewHolder(view);
    }

    static class myViewHolder extends RecyclerView.ViewHolder{
        ImageView Image;
        TextView cid,UserID,UserID1,ComplaintType1,Date1,Description1,ComplaintType,Description,Date,Latitude1,Latitude,Longitude1,Longitude,Address1,Address,ServiceManID,Status1,Status,sid1,ResDate,ResDate1,FeedbackStars,FeedbackStars1,Feedbackdesc1,Feedbackdesc;
        CardView cardView;
        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView=itemView.findViewById(R.id.cardview_ComplaintReport_Admin);
            Image=itemView.findViewById(R.id.complaint_img);
            cid=itemView.findViewById(R.id.Cid);
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
            FeedbackStars=itemView.findViewById(R.id.feedbackStars);
            FeedbackStars1=itemView.findViewById(R.id.feedbackStars1);
            Feedbackdesc=itemView.findViewById(R.id.feedbackdesc);
            Feedbackdesc1=itemView.findViewById(R.id.feedbackdesc1);
        }
    }
}
