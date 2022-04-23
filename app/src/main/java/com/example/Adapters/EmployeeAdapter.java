package com.example.Adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.Models.Employee;
import com.example.Models.Trainer;
import com.example.courses.R;
import com.example.courses.UI.Fragments.Admin.EditEmployeeFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class EmployeeAdapter extends RecyclerView.Adapter<EmployeeAdapter.ViewHolder> {

    private Context context;
    private List<Employee> list;
    DatabaseReference databaseReference;
    DocumentReference documentReference;

    private OnItemClickListener mListener;

    public interface OnItemClickListener{
        void onItemEdit(String position);
    }
    public void setOnItemClickListener(OnItemClickListener listener){
        mListener=listener;
    }

    public EmployeeAdapter(Context context, List<Employee> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.employee_item,parent,false);
        return new ViewHolder(view , mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.name.setText(list.get(position).getName());
        holder.email.setText(list.get(position).getEmail());
        holder.age.setText(String.valueOf(list.get(position).getAge()));
        holder.position.setText(list.get(position).getPosition());
        holder.phone.setText(String.valueOf(list.get(position).getNumber()));

        String uid = list.get(position).getUid();
        holder.employeeDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteEmployee(uid);
            }
        });

        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onItemEdit(uid);
            }
        });

    }
    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name ,email ,age,position ,phone ;
        ImageView employeeDelete;
        AppCompatButton edit;
        public ViewHolder(@NonNull View itemView , final OnItemClickListener listener) {
            super(itemView);

            name = itemView.findViewById(R.id.employee_name);
            email = itemView.findViewById(R.id.employee_email);
            age = itemView.findViewById(R.id.employee_age);
            position = itemView.findViewById(R.id.employee_position);
            employeeDelete = itemView.findViewById(R.id.employee_delete);
            phone = itemView.findViewById(R.id.phone);
            edit = itemView.findViewById(R.id.edit_employee);

        }
    }

    public void deleteEmployee(String uid){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("سيتم حذف الموظف من النظام !");
        builder.setTitle("تنبيه !");
        builder.setCancelable(false);
        builder.setPositiveButton("تأكيد", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                databaseReference = FirebaseDatabase.getInstance().getReference("Employees");
                                databaseReference.child(uid).removeValue();
                                documentReference = FirebaseFirestore.getInstance().collection("Employees").document(uid);
                                documentReference.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        Toast.makeText(context, "تم حذف الموظف", Toast.LENGTH_SHORT).show();
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
