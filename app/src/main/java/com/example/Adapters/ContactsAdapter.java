package com.example.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.example.Models.TrainingProvider;
import com.example.courses.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.ViewHolder> {

    private Context context;
    private List<TrainingProvider> list;


    private ContactsAdapter.OnItemClickListener mListener;
    private boolean state;
    public interface OnItemClickListener{
        void onItemEdit(String position);
        void onItemDeleted(String position);
    }
    public void setOnItemClickListener(ContactsAdapter.OnItemClickListener listener){
        mListener=listener;
    }
    public ContactsAdapter(Context context, List<TrainingProvider> list, boolean state) {
        this.context = context;
        this.list = list;
        this.state = state;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.contacts_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.name.setText(list.get(position).getName());
        holder.email.setText(list.get(position).getEmail());
        holder.region.setText(list.get(position).getRegion());
        holder.commercail.setText(list.get(position).getCommercial_register());
        Picasso.get().load(list.get(position).getImage()).into(holder.photo);

        if (state){
            holder.delete.setVisibility(View.INVISIBLE);
            holder.edit.setVisibility(View.INVISIBLE);
            holder.linearLayout.setVisibility(View.GONE);
        }
        String uid = list.get(position).getUid();
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onItemDeleted(uid);
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
        TextView name ,email,region, commercail ;
        CircleImageView photo;
        AppCompatButton edit;
        ImageView delete;
        LinearLayout linearLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.contact_name);
            email = itemView.findViewById(R.id.email_contact);
            region = itemView.findViewById(R.id.region_contact);
            commercail = itemView.findViewById(R.id.commercial_contact);
            photo = itemView.findViewById(R.id.contact_picture);
            edit = itemView.findViewById(R.id.edit_contact);
            delete = itemView.findViewById(R.id.delete_contact);
            linearLayout = itemView.findViewById(R.id.lien2);

        }
    }


}
