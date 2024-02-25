package com.example.citizencare;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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

public class FeedbackServiceAdminAdapter extends FirebaseRecyclerAdapter<FeedbackServiceAdminModel, FeedbackServiceAdminAdapter.myViewHolder> {
    private String firstName, middleName, lastName;
    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     */
    public FeedbackServiceAdminAdapter(@NonNull FirebaseRecyclerOptions<FeedbackServiceAdminModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myViewHolder holder, int position, @NonNull FeedbackServiceAdminModel model) {
        String serviceID=getSnapshots().getSnapshot(holder.getBindingAdapterPosition()).getKey();
        String UserId= model.getUserID();
        holder.serviceType.setText(model.getServiceType());
        holder.feedbackStar.setText(String.valueOf(model.getFeedBackStars()));
        holder.feedbackDesc.setText(model.getFeedBackDescription());

        if(model.getFeedBackDescription().equals("None")&&model.getFeedBackStars().equals(0)){
            holder.feedbackStar.setVisibility(View.GONE);
            holder.feedbackDesc.setVisibility(View.GONE);
            holder.feedbackDesc1.setVisibility(View.GONE);
            holder.feedbackStar1.setVisibility(View.GONE);
            holder.serviceType1.setVisibility(View.GONE);
            holder.serviceType.setVisibility(View.GONE);
            holder.UserName1.setVisibility(View.GONE);
            holder.UserName.setVisibility(View.GONE);
            holder.cardView.setVisibility(View.GONE);
            holder.btnDelete.setVisibility(View.GONE);
        }

        //extract data from database
        DatabaseReference servicesRef = FirebaseDatabase.getInstance().getReference().child("Services");
        servicesRef.child(Objects.requireNonNull(serviceID)).addListenerForSingleValueEvent(new ValueEventListener() {
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

        holder.btnDelete.setOnClickListener(view -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(holder.feedbackDesc.getContext());
            builder.setTitle("Are you sure?");
            builder.setMessage("You want to delete the feedback?");
            builder.setPositiveButton("Yes", (dialogInterface, i) -> {
                FirebaseDatabase.getInstance().getReference().child("Services").child(serviceID).child("FeedBackDescription").setValue("None");
                FirebaseDatabase.getInstance().getReference().child("Services").child(serviceID).child("FeedBackStars").setValue(0);
                Toast.makeText(holder.feedbackDesc.getContext(), "Feedback Deleted Successfully", Toast.LENGTH_LONG).show();
            });
            builder.setNegativeButton("No", (dialogInterface, i) -> Toast.makeText(holder.feedbackDesc.getContext(), "Cancelled", Toast.LENGTH_LONG).show());
            builder.show();
        });
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.feedback_service_admin_item,parent,false);
        return new myViewHolder(view);
    }

    static class myViewHolder extends RecyclerView.ViewHolder{

        TextView serviceType,feedbackDesc,feedbackStar,UserName,serviceType1,feedbackDesc1,feedbackStar1,UserName1;
        CardView cardView;

        Button btnDelete;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView=itemView.findViewById(R.id.cardview_services);
            UserName1=itemView.findViewById(R.id.uname1);
            feedbackStar1=itemView.findViewById(R.id.fStar1);
            feedbackDesc1=itemView.findViewById(R.id.fDesc1);
            serviceType1=itemView.findViewById(R.id.stype1);
            UserName=itemView.findViewById(R.id.uname);
            serviceType = itemView.findViewById(R.id.stype);
            feedbackDesc = itemView.findViewById(R.id.fDesc);
            feedbackStar = itemView.findViewById(R.id.fStar);
            btnDelete = itemView.findViewById(R.id.button_delete);
        }
    }
}
