package com.example.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.Models.Fields;
import com.example.Models.Trainer;
import com.example.courses.R;

import java.util.List;

public class FieldsAdapter extends RecyclerView.Adapter<FieldsAdapter.ViewHolder> {

    private Context context;
    private List<Fields> list;

    public FieldsAdapter(Context context, List<Fields> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fields_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.fieldsName.setText(list.get(position).getFieldName());
    }
    @Override
    public int getItemCount() {
        return list.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView fieldsName;
        Button delete;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            fieldsName = itemView.findViewById(R.id.fields_name);
            delete  = itemView.findViewById(R.id.fields_delete);

        }
    }
}
