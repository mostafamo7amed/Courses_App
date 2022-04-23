package com.example.Adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.Models.Comments;
import com.example.Models.Trainer;
import com.example.courses.R;
import com.example.courses.UI.Activities.AdminActivity;
import com.example.courses.UI.Activities.ContactsActivity;
import com.example.courses.UI.Activities.EmployeeActivity;
import com.example.courses.UI.Activities.LoginActivity;
import com.example.courses.UI.Activities.TraineeActivity;
import com.example.courses.UI.Activities.TrainerActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class ContactPageAdapter extends RecyclerView.Adapter<ContactPageAdapter.ViewHolder> {

    private Context context;
    private List<Comments> list;
    DatabaseReference databaseReference;
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();


    public ContactPageAdapter(Context context, List<Comments> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.comment_item,parent,false);
        return new ViewHolder(view );
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.name.setText(list.get(position).getName());
        if(list.get(position).getEmail().equals("")) {
            holder.lineer.setVisibility(View.GONE);
        }
        holder.email.setText(list.get(position).getEmail());
        holder.message.setText(list.get(position).getComment());
        holder.time_date_comment.setText(new StringBuilder().append(list.get(position).getDate()).append(" ").append(list.get(position).getTime()).toString());

        String key =list.get(position).getKey().toString();

        String currentUser= firebaseAuth.getCurrentUser().getUid();
        String uid = list.get(position).getUID();
        String type = list.get(position).getType();

            final DatabaseReference RootRef;
            RootRef = FirebaseDatabase.getInstance().getReference();
            if(currentUser!=null){
                RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.child("Admins").child(currentUser).exists()) {
                            holder.delete.setVisibility(View.VISIBLE);
                        }else if (snapshot.child("Employees").child(currentUser).exists()){
                            holder.delete.setVisibility(View.VISIBLE);
                        }else {
                            if(type.equals("Trainers") || type.equals("Trainees") || type.equals("Training Provider")) {
                                if (currentUser.equals(uid)) {
                                    holder.delete.setVisibility(View.VISIBLE);
                                } else {
                                    holder.delete.setVisibility(View.INVISIBLE);
                                }
                            }else {
                                holder.delete.setVisibility(View.INVISIBLE);
                            }
                        }

                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });


        }
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removeItem(key);
            }
        });
    }
    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name ,email,message ,time_date_comment;
        Button delete;
        LinearLayout lineer;
        public ViewHolder(@NonNull View itemView ) {
            super(itemView);

            name = itemView.findViewById(R.id.comment_name);
            email = itemView.findViewById(R.id.comment_email);
            message = itemView.findViewById(R.id.comment_comment);
            delete = itemView.findViewById(R.id.comment_delete);
            time_date_comment = itemView.findViewById(R.id.time_date_comment);
            lineer = itemView.findViewById(R.id.liener2);



        }
    }
    public void removeItem(String key){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("سيتم حذف التعليق !");
        builder.setTitle("تنبيه !");
        builder.setCancelable(false);
        builder.setPositiveButton("تأكيد", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                databaseReference = FirebaseDatabase.getInstance().getReference("All Comments");
                databaseReference.child(key).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(context, "تم حذف التعليق", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        builder.setNegativeButton("إلغاء", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }
}
