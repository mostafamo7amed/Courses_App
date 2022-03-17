package com.example.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.example.Models.Course;
import com.example.Models.Trainer;
import com.example.courses.R;

import java.util.List;

public class CoursesAdapter extends RecyclerView.Adapter<CoursesAdapter.ViewHolder> {

    private Context context;
    private List<Course> list;

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
}
