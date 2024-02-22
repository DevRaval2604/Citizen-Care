package com.example.citizencare;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class FeedbackComplaintMainAdapter extends FirebaseRecyclerAdapter<FeedbackComplaintMainModel, FeedbackComplaintMainAdapter.myViewHolder> {


    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     */
    public FeedbackComplaintMainAdapter(@NonNull FirebaseRecyclerOptions<FeedbackComplaintMainModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myViewHolder holder, int position, @NonNull FeedbackComplaintMainModel model) {
        holder.complaintType.setText(model.getComplaintType());
        holder.feedbackStar.setText(String.valueOf(model.getFeedBackStars()));
        holder.feedbackDesc.setText(model.getFeedBackDescription());

    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.feedback_complaint_main_item,parent,false);
        return new myViewHolder(view);
    }

    static class myViewHolder extends RecyclerView.ViewHolder{

        TextView complaintType, feedbackDesc, feedbackStar;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            complaintType = (TextView)itemView.findViewById(R.id.ctype);
            feedbackDesc = (TextView)itemView.findViewById(R.id.fDesc);
            feedbackStar = (TextView)itemView.findViewById(R.id.fStar);
        }
    }
}
