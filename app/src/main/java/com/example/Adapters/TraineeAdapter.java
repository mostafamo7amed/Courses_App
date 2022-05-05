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

import com.example.Models.Trainee;
import com.example.Models.Trainer;
import com.example.courses.R;

import java.util.List;

public class TraineeAdapter extends RecyclerView.Adapter<TraineeAdapter.ViewHolder> {

    private Context context;
    private List<Trainee> list;

    private OnItemClickListener mListener;
    boolean state;
    public interface OnItemClickListener{
        void onItemEdit(String UID);
    }
    public void setOnItemClickListener(OnItemClickListener listener){
        mListener=listener;
    }

    public TraineeAdapter(Context context, List<Trainee> list, boolean state) {
        this.context = context;
        this.list = list;
        this.state = state;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.trainee_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.name.setText(list.get(position).getName());
        holder.level.setText(list.get(position).getEducationLevel());
        holder.age.setText(String.valueOf(list.get(position).getAge()));

        if(state){
            holder.traineeEdit.setVisibility(View.INVISIBLE);
        }
        String uid = list.get(position).getUID();
        holder.traineeEdit.setOnClickListener(new View.OnClickListener() {
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
        TextView name ,level ,age;
        AppCompatButton traineeEdit;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.trainee_name);
            level = itemView.findViewById(R.id.trainee_level);
            age = itemView.findViewById(R.id.trainee_age);
            traineeEdit = itemView.findViewById(R.id.trainee_edit);

        }
    }
}
