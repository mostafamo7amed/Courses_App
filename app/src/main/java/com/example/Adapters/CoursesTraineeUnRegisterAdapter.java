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

public class CoursesTraineeUnRegisterAdapter extends RecyclerView.Adapter<CoursesTraineeUnRegisterAdapter.ViewHolder> {

    private Context context;
    private List<Course> list;

    private OnItemClickListener mListener;

    public interface OnItemClickListener{
        void onItemUnRegister(String Key);
    }
    public void setOnItemClickListener(OnItemClickListener listener){
        mListener=listener;
    }
    public CoursesTraineeUnRegisterAdapter(Context context, List<Course> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.courses_trainee_unregister_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.name.setText(list.get(position).getField());
        holder.trainer.setText(list.get(position).getTrainer());
        holder.time.setText(list.get(position).getTime());
        holder.date.setText(list.get(position).getDate()+" الي "+list.get(position).getEnd());
        holder.address.setText(list.get(position).getAddress());
        holder.description.setText(list.get(position).getDescription());
        holder.material.setText(list.get(position).getCourseMaterial());

        String key = list.get(position).getKey();
        holder.unregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onItemUnRegister(key);
            }
        });

    }
    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name ,trainer ,description ,date ,time ,address , material;
        AppCompatButton unregister;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.field_course_trainee_un);
            trainer = itemView.findViewById(R.id.trainer_course_trainee_un);
            date = itemView.findViewById(R.id.date_course_trainee_un);
            time = itemView.findViewById(R.id.time_course_trainee_un);
            address = itemView.findViewById(R.id.address_course_trainee_un);
            description = itemView.findViewById(R.id.desciption_course_trainee_un);
            unregister = itemView.findViewById(R.id.unregister_course);
            material = itemView.findViewById(R.id.material_course_un);
        }
    }
}
