package com.example.Adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.example.Models.Contacts;
import com.example.Models.Trainer;
import com.example.courses.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.ViewHolder> {

    private Context context;
    private List<Contacts> list;


    private ContactsAdapter.OnItemClickListener mListener;

    public interface OnItemClickListener{
        void onItemEdit(String position);
        void onItemDeleted(String position);
    }
    public void setOnItemClickListener(ContactsAdapter.OnItemClickListener listener){
        mListener=listener;
    }
    public ContactsAdapter(Context context, List<Contacts> list) {
        this.context = context;
        this.list = list;

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
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.contact_name);
            email = itemView.findViewById(R.id.email_contact);
            region = itemView.findViewById(R.id.region_contact);
            commercail = itemView.findViewById(R.id.commercial_contact);
            photo = itemView.findViewById(R.id.contact_picture);
            edit = itemView.findViewById(R.id.edit_contact);
            delete = itemView.findViewById(R.id.delete_contact);

        }
    }


}
