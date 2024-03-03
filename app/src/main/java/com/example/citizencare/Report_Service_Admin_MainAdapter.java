package com.example.citizencare;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class Report_Service_Admin_MainAdapter extends FirebaseRecyclerAdapter<Report_Service_Admin_Model, Report_Service_Admin_MainAdapter.myViewHolder> {

    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     */
    public Report_Service_Admin_MainAdapter(@NonNull FirebaseRecyclerOptions<Report_Service_Admin_Model> options) {
        super(options);
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onBindViewHolder(@NonNull Report_Service_Admin_MainAdapter.myViewHolder holder, int position, @NonNull Report_Service_Admin_Model model) {
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
        holder.FeedbackStars.setText(Integer.toString(model.getFeedBackStars()));
        holder.Feedbackdesc.setText(model.getFeedBackDescription());

        if(!model.getServiceManID().equals("None")){
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
    }

    @NonNull
    @Override
    public Report_Service_Admin_MainAdapter.myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.card_servicereport_admin,parent,false);
        return new Report_Service_Admin_MainAdapter.myViewHolder(view);
    }

    static class myViewHolder extends RecyclerView.ViewHolder{
        TextView Sid,UserID,UserID1,ServiceType1,Date1,Description1,Address1,Status1,ServiceType,Description,Date,Address,ServiceManID,Status,sid1,ResDate,ResDate1,FeedbackStars,FeedbackStars1,Feedbackdesc1,Feedbackdesc;
        CardView cardView;
        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView=itemView.findViewById(R.id.cardview_ServiceReport_Admin);
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
            FeedbackStars=itemView.findViewById(R.id.feedbackStars);
            FeedbackStars1=itemView.findViewById(R.id.feedbackStars1);
            Feedbackdesc=itemView.findViewById(R.id.feedbackdesc);
            Feedbackdesc1=itemView.findViewById(R.id.feedbackdesc1);
        }
    }
}
