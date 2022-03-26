package com.example.Adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.example.Models.Course;
import com.example.Models.Trainer;
import com.example.courses.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class CoursesAdapter extends RecyclerView.Adapter<CoursesAdapter.ViewHolder> {

    private Context context;
    private List<Course> list;
    DatabaseReference databaseReference;

    public CoursesAdapter(Context context, List<Course> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.courses_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.name.setText(list.get(position).getField());
        holder.trainer.setText(list.get(position).getTrainer());
        holder.description.setText(list.get(position).getDescription());
        holder.material.setText(list.get(position).getCourseMaterial());
        holder.date.setText(list.get(position).getDate());
        holder.time.setText(list.get(position).getTime());
        holder.address.setText(list.get(position).getAddress());

        String key = list.get(position).getKey();
        holder.close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteCourse(key);
            }
        });

    }
    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name ,trainer ,description,material,address,time,date ;
        AppCompatButton close;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.field_course);
            trainer = itemView.findViewById(R.id.trainer_course);
            description = itemView.findViewById(R.id.desciption_course);
            close = itemView.findViewById(R.id.delete_course);
            material = itemView.findViewById(R.id.material_course);
            address = itemView.findViewById(R.id.address_course);
            time = itemView.findViewById(R.id.time_course);
            date = itemView.findViewById(R.id.date_course);
        }
    }


    public void deleteCourse(String key){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("سيتم حذف الدورة !");
        builder.setTitle("تنبيه !");
        builder.setCancelable(false);
        builder.setPositiveButton("تأكيد", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                databaseReference = FirebaseDatabase.getInstance().getReference("All Courses");
                databaseReference.child(key).removeValue();
                Toast.makeText(context, "تم حذف الدورة", Toast.LENGTH_SHORT).show();
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
