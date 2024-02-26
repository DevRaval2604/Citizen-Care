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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class FeedbackComplaintServicemenAdapter extends FirebaseRecyclerAdapter<FeedbackComplaintServicemenModel, FeedbackComplaintServicemenAdapter.myViewHolder> {

    private String firstName, middleName, lastName;
    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     */
    public FeedbackComplaintServicemenAdapter(@NonNull FirebaseRecyclerOptions<FeedbackComplaintServicemenModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myViewHolder holder, int position, @NonNull FeedbackComplaintServicemenModel model) {
        String complaintID=getSnapshots().getSnapshot(holder.getBindingAdapterPosition()).getKey();
        String UserId= model.getUserID();
        holder.complaintType.setText(model.getComplaintType());
        holder.feedbackStar.setText(String.valueOf(model.getFeedBackStars()));
        holder.feedbackDesc.setText(model.getFeedBackDescription());

        if(model.getFeedBackDescription().equals("None")&&model.getFeedBackStars().equals(0)){
            holder.feedbackStar.setVisibility(View.GONE);
            holder.feedbackDesc.setVisibility(View.GONE);
            holder.feedbackDesc1.setVisibility(View.GONE);
            holder.feedbackStar1.setVisibility(View.GONE);
            holder.complaintType1.setVisibility(View.GONE);
            holder.complaintType.setVisibility(View.GONE);
            holder.UserName1.setVisibility(View.GONE);
            holder.UserName.setVisibility(View.GONE);
            holder.cardView1.setVisibility(View.GONE);

        }

        //extract data from database
        DatabaseReference servicesRef = FirebaseDatabase.getInstance().getReference().child("Complaints");
        servicesRef.child(Objects.requireNonNull(complaintID)).addListenerForSingleValueEvent(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child("Users");
                userRef.child(UserId).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        ReadWriteCitizenDetails readUserDetails = snapshot.getValue(ReadWriteCitizenDetails.class);
                        if (readUserDetails != null) {
                            firstName = readUserDetails.FirstName;
                            middleName = readUserDetails.MiddleName;
                            lastName = readUserDetails.LastName;
                            holder.UserName.setText(firstName + " " + middleName + " " + lastName);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        //Do nothing
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                //Do nothing
            }
        });
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.feedback_complaint_servicemen_item,parent,false);
        return new myViewHolder(view);
    }

    static class myViewHolder extends RecyclerView.ViewHolder{

        TextView complaintType, feedbackDesc, feedbackStar,UserName,complaintType1,feedbackDesc1,feedbackStar1,UserName1;
        CardView cardView1;


        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView1=itemView.findViewById(R.id.cardview_complaints);
            UserName1=itemView.findViewById(R.id.uname1);
            feedbackStar1=itemView.findViewById(R.id.fStar1);
            feedbackDesc1=itemView.findViewById(R.id.fDesc1);
            complaintType1=itemView.findViewById(R.id.ctype1);
            UserName=itemView.findViewById(R.id.uname);
            complaintType = (TextView)itemView.findViewById(R.id.ctype);
            feedbackDesc = (TextView)itemView.findViewById(R.id.fDesc);
            feedbackStar = (TextView)itemView.findViewById(R.id.fStar);

        }
    }
}
