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
import androidx.recyclerview.widget.RecyclerView;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;
import java.util.Objects;

public class MainAdapter extends FirebaseRecyclerAdapter<MainModel,MainAdapter.myViewHolder> {
    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     */
    public MainAdapter(@NonNull FirebaseRecyclerOptions<MainModel> options) {
        super(options);
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onBindViewHolder(@NonNull myViewHolder holder, int position, @NonNull MainModel model) {
        String userId=getRef(holder.getBindingAdapterPosition()).getKey();
        String userID=getSnapshots().getSnapshot(holder.getBindingAdapterPosition()).getKey();
        holder.uid.setText(userID);
        holder.fname.setText(model.getFirstName());
        holder.mname.setText(model.getMiddleName());
        holder.lname.setText(model.getLastName());
        holder.email.setText(model.getEmail());
        holder.gender.setText(model.getGender());
        holder.mno.setText(model.getMobileNumber());
        holder.role.setText(model.getRole());


        //Visibility of block button
        if(model.getRole().equals("Admin")){
            holder.btnBlock.setVisibility(View.GONE);
            holder.btnUnblock.setVisibility(View.GONE);
        }else if(model.getBlocked()){
            holder.btnBlock.setVisibility(View.GONE);
            holder.btnUnblock.setVisibility(View.VISIBLE);
        }else {
            holder.btnBlock.setVisibility(View.VISIBLE);
            holder.btnUnblock.setVisibility(View.GONE);
        }
        holder.btnBlock.setOnClickListener(view -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(holder.fname.getContext());
            builder.setTitle("Are you sure?");
            builder.setMessage("You want to block this user?");
            builder.setPositiveButton("Block", (dialogInterface, i) -> {
                FirebaseDatabase.getInstance().getReference().child("Users").child(Objects.requireNonNull(userId)).child("Blocked").setValue(true);
                model.setBlocked(true);
                Toast.makeText(holder.fname.getContext(), "User Blocked Successfully", Toast.LENGTH_LONG).show();
            });
            builder.setNegativeButton("Cancel", (dialogInterface, i) -> Toast.makeText(holder.fname.getContext(), "Cancelled", Toast.LENGTH_LONG).show());
            builder.show();
        });
        holder.btnUnblock.setOnClickListener(view -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(holder.fname.getContext());
            builder.setTitle("Are you sure?");
            builder.setMessage("You want to unblock this user?");
            builder.setPositiveButton("Unblock", (dialogInterface, i) -> {
                FirebaseDatabase.getInstance().getReference().child("Users").child(Objects.requireNonNull(userId)).child("Blocked").setValue(false);
                model.setBlocked(false);
                Toast.makeText(holder.fname.getContext(), "User Unblocked Successfully", Toast.LENGTH_LONG).show();
            });
            builder.setNegativeButton("Cancel", (dialogInterface, i) -> Toast.makeText(holder.fname.getContext(), "Cancelled", Toast.LENGTH_LONG).show());
            builder.show();
        });
    }
    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.main_item,parent,false);
        return new myViewHolder(view);
    }

    static class myViewHolder extends RecyclerView.ViewHolder{

        TextView fname,mname,lname,email,gender,mno,role,uid;
        Button btnBlock,btnUnblock;

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
            btnBlock= itemView.findViewById(R.id.button_user_block);
            btnUnblock=itemView.findViewById(R.id.button_user_unblock);
        }
    }
}
