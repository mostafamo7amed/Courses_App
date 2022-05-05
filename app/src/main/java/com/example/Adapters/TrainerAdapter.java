package com.example.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.Models.Trainer;
import com.example.courses.R;

import java.util.List;

public class TrainerAdapter extends RecyclerView.Adapter<TrainerAdapter.ViewHolder> {

    private Context context;
    private List<Trainer> list;
    private OnItemClickListener mListener;
    boolean state;

    public interface OnItemClickListener{
        void onItemDeleted(String uid);
    }
    public void setOnItemClickListener(OnItemClickListener listener){
        mListener=listener;
    }
    public TrainerAdapter(Context context, List<Trainer> list, boolean state) {
        this.context = context;
        this.list = list;
        this.state = state;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.trainer_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.name.setText(list.get(position).getName());
        holder.type.setText(list.get(position).getSpecialization());
        holder.nationality.setText(list.get(position).getNationality());
        holder.email.setText(list.get(position).getEmail());
        holder.gender.setText(list.get(position).getGender());
        String uid = list.get(position).getUid();

        if(state){
            holder.trainerBlock.setVisibility(View.INVISIBLE);

        }
        holder.trainerBlock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onItemDeleted(uid);
            }
        });
    }
    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name ,type ,nationality,gender,email;
        ImageView trainerBlock;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.trainer_name);
            type = itemView.findViewById(R.id.trainer_type);
            nationality = itemView.findViewById(R.id.trainer_nationality);
            gender = itemView.findViewById(R.id.trainer_gender);
            email = itemView.findViewById(R.id.trainer_email);
            trainerBlock = itemView.findViewById(R.id.trainer_block);

        }
    }
}
