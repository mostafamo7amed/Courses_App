package com.example.courses.UI.Fragments.Employee;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.Adapters.ContactsAdapter;
import com.example.Models.Contacts;
import com.example.courses.R;
import com.example.courses.UI.Activities.LoginActivity;
import com.example.courses.UI.Fragments.Admin.EditContactsFragment;
import com.example.courses.UI.Fragments.Admin.EditEmployeeFragment;
import com.example.courses.UI.Fragments.Contacts.Add_TrainerFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;


public class ContactsFragment extends Fragment {
    ContactsAdapter contactsAdapter;
    RecyclerView recyclerContacts;
    ArrayList<Contacts> contacts;
    FloatingActionButton addContacts;
    DatabaseReference databaseReference;
    DocumentReference documentReference;
    StorageReference storageReference;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerContacts = getActivity().findViewById(R.id.RecyclerContacts);
        recyclerContacts.setLayoutManager(new LinearLayoutManager(getContext()));

        contacts = new ArrayList<>();

        contactsAdapter = new ContactsAdapter(getContext(),contacts);
        getContacts();


        contactsAdapter.setOnItemClickListener(new ContactsAdapter.OnItemClickListener() {
            @Override
            public void onItemEdit(String uid) {
                editContact(uid);
            }

            @Override
            public void onItemDeleted(String position) {
                deleteContact(position);
            }
        });
        addContacts  = getActivity().findViewById(R.id.add_contacts);
        addContacts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment selected = null;
                Bundle bundle = new Bundle();
                bundle.putInt("frame",getArguments().getInt("frame"));
                selected = new Add_ContactsFragment();
                selected.setArguments(bundle);
                getParentFragmentManager().beginTransaction().replace(getArguments().getInt("frame"),selected ).addToBackStack(null).commitAllowingStateLoss();

            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_contacts, container, false);
    }

   public void getContacts(){
       DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Contacts");
       databaseReference.addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot snapshot) {
               contacts.clear();
               for (DataSnapshot snapshot1 :snapshot.getChildren()){
                   databaseReference.child(snapshot1.getKey().toString()).addValueEventListener(new ValueEventListener() {
                       @Override
                       public void onDataChange(@NonNull DataSnapshot snapshot) {

                           if(snapshot.exists()) {
                               contacts.add(new Contacts(snapshot.child("name").getValue().toString(),
                                       snapshot.child("email").getValue().toString(),
                                       snapshot.child("phone").getValue().toString(),
                                       snapshot.child("region").getValue().toString(),
                                       snapshot.child("commercial_register").getValue().toString(),
                                       Integer.parseInt(snapshot.child("contact_number").getValue().toString()),
                                       snapshot.child("uid").getValue().toString(),
                                       snapshot.child("image").getValue().toString()
                               ));
                           }

                           recyclerContacts.setAdapter(contactsAdapter);
                           contactsAdapter.notifyDataSetChanged();
                       }

                       @Override
                       public void onCancelled(@NonNull DatabaseError error) {

                       }
                   });

               }

           }

           @Override
           public void onCancelled(@NonNull DatabaseError error) {

           }
       });
   }


    public void deleteContact(String uid){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage("سيتم حذف الجهة من النظام !");
        builder.setTitle("تنبيه !");
        builder.setCancelable(false);
        builder.setPositiveButton("تأكيد", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                databaseReference = FirebaseDatabase.getInstance().getReference("Contacts");
                databaseReference.child(uid).removeValue();
                documentReference = FirebaseFirestore.getInstance().collection("Contacts").document(uid);
                documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()) {
                            String UrlDeleted = documentSnapshot.getData().get("image").toString();
                            if (UrlDeleted != null) {
                                deleteImage(UrlDeleted);
                            }
                        }
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

    public void deleteImage(String image){
        storageReference= FirebaseStorage.getInstance().getReference("Profile images");
        final StorageReference reference2 = storageReference.child(image);
        reference2.delete();
        documentReference.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(getContext(), "تم حذف الجهة", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void editContact(String uid){
        Fragment fragment;
        Bundle bundle = new Bundle();
        bundle.putString("uid",uid);
        bundle.putInt("frame",getArguments().getInt("frame"));
        fragment = new EditContactsFragment();
        fragment.setArguments(bundle);
        getParentFragmentManager().beginTransaction().replace(getArguments().getInt("frame"), fragment).addToBackStack(null).commitAllowingStateLoss();
    }


}