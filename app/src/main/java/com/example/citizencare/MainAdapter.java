package com.example.citizencare;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class MainAdapter extends FirebaseRecyclerAdapter<MainModel,MainAdapter.myViewHolder> {
    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     */
    public MainAdapter(@NonNull FirebaseRecyclerOptions<MainModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myViewHolder holder, int position, @NonNull MainModel model) {
        String userID=getSnapshots().getSnapshot(position).getKey();
        holder.uid.setText(userID);
        holder.fname.setText(model.getFirstName());
        holder.mname.setText(model.getMiddleName());
        holder.lname.setText(model.getLastName());
        holder.email.setText(model.getEmail());
        holder.gender.setText(model.getGender());
        holder.mno.setText(model.getMobileNumber());
        holder.role.setText(model.getRole());
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.main_item,parent,false);
        return new myViewHolder(view);
    }

    static class myViewHolder extends RecyclerView.ViewHolder{

        TextView fname,mname,lname,email,gender,mno,role,uid;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            uid=itemView.findViewById(R.id.uid);
            fname= itemView.findViewById(R.id.fname);
            mname= itemView.findViewById(R.id.mname);
            lname= itemView.findViewById(R.id.lname);
            email= itemView.findViewById(R.id.email);
            gender= itemView.findViewById(R.id.gender);
            mno= itemView.findViewById(R.id.mno);
            role= itemView.findViewById(R.id.role);
        }
    }
}
