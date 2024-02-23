package com.example.citizencare;
import android.app.AlertDialog;
import android.content.Intent;
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

public class ServicemanAdapter2 extends FirebaseRecyclerAdapter<MainModel,ServicemanAdapter2.myViewHolder> {
    private final String serviceID;
    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     */
    public ServicemanAdapter2(@NonNull FirebaseRecyclerOptions<MainModel> options,String serviceID) {
        super(options);
        this.serviceID=serviceID;
    }

    @Override
    protected void onBindViewHolder(@NonNull myViewHolder holder, int position, @NonNull MainModel model) {
        String ServicemanID=getSnapshots().getSnapshot(holder.getBindingAdapterPosition()).getKey();
        holder.uid.setText(ServicemanID);
        holder.fname.setText(model.getFirstName());
        holder.mname.setText(model.getMiddleName());
        holder.lname.setText(model.getLastName());
        holder.email.setText(model.getEmail());
        holder.gender.setText(model.getGender());
        holder.mno.setText(model.getMobileNumber());
        holder.role.setText(model.getRole());

        holder.btnSelect.setOnClickListener(view -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(holder.fname.getContext());
            builder.setTitle("Are you sure?");
            builder.setMessage("You want to select this serviceman?");
            builder.setPositiveButton("Select", (dialogInterface, i) -> {
                FirebaseDatabase.getInstance().getReference().child("Services").child(serviceID).child("ServiceManID").setValue(ServicemanID);
                FirebaseDatabase.getInstance().getReference().child("Services").child(serviceID).child("Status").setValue("In-Progress");
                Intent intent=new Intent(holder.fname.getContext(), ManageServices.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                holder.fname.getContext().startActivity(intent);
                Toast.makeText(holder.fname.getContext(), "Serviceman Assigned Successfully", Toast.LENGTH_LONG).show();
            });
            builder.setNegativeButton("Cancel", (dialogInterface, i) -> Toast.makeText(holder.fname.getContext(), "Cancelled", Toast.LENGTH_LONG).show());
            builder.show();
        });
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.select_serviceman2,parent,false);
        return new myViewHolder(view);
    }

    static class myViewHolder extends RecyclerView.ViewHolder{
        TextView fname,mname,lname,email,gender,mno,role,uid;
        Button btnSelect;

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
            btnSelect= itemView.findViewById(R.id.button_select_serviceman2);
        }
    }
}

