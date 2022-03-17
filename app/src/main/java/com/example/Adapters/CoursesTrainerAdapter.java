package com.example.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.example.Models.Course;
import com.example.courses.R;

import java.util.List;

public class CoursesTrainerAdapter extends RecyclerView.Adapter<CoursesTrainerAdapter.ViewHolder> {

    private Context context;
    private List<Course> list;

    public CoursesTrainerAdapter(Context context, List<Course> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.courses_trainer_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.name.setText(list.get(position).getField());
        holder.trainer.setText(list.get(position).getTrainer());
        holder.description.setText(list.get(position).getDescription());

    }
    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name ,trainer ,description ;
        AppCompatButton edit;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.field_course_trainer);
            trainer = itemView.findViewById(R.id.trainer_course_trainer);
            description = itemView.findViewById(R.id.desciption_course_trainer);
        }
    }
}
